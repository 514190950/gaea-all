package com.gxz.gaea.core.execute.receive;


import com.gxz.gaea.core.event.AfterAnalysisEvent;
import com.gxz.gaea.core.event.AfterCollectorEvent;
import com.gxz.gaea.core.event.AfterFreeEvent;
import com.gxz.gaea.core.event.BeforeAnalysisEvent;
import com.gxz.gaea.core.event.BeforeCollectorEvent;
import com.gxz.gaea.core.event.BeforeFreeEvent;
import com.gxz.gaea.core.execute.analyst.Analyst;
import com.gxz.gaea.core.execute.collector.CollectorCombination;
import com.gxz.gaea.core.execute.collector.CollectorException;
import com.gxz.gaea.core.component.ListenerManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * @author gxz gongxuanzhang@foxmail.com
 * @date 2021/5/14 22:06
 * 同时适配器可以执行逻辑
 * In类型是输入类型
 * A类型是最终解析的类型
 */
@Slf4j
public abstract class AbstractReceiver<In, A> implements Receiver<In>, ApplicationContextAware {



    protected CollectorCombination collector;

    protected ListenerManager listenerManager;

    protected Analyst<A> aAnalyst;

    /***
     *
     * @return 解析目标的类型
     **/
    public abstract Class<A> analysisClass();

    @Override
    public void receive(In in) throws Exception {
        A collect = executeCollector(in);
        if (collect == null) {
            log.warn("{}收集器收集到了一个null,此次流程终止", collector.getClass().getName());
            return;
        }
        executeAnalysis(collect);
    }

    protected void executeAnalysis(A in) {
        Analyst<A> analyst = getAnalyst();
        if (analyst == null) {
            throw new IllegalArgumentException(this.getClass().getName() + "没有解析器");
        }
        listenerManager.publish(new BeforeAnalysisEvent(in));
        analyst.analysis(in);
        listenerManager.publish(new AfterAnalysisEvent(in));
        executeFree(in);
    }

    protected void executeFree(A in) {
        listenerManager.publish(new BeforeFreeEvent(in));
        getAnalyst().free(in);
        listenerManager.publish(new AfterFreeEvent(in));
    }

    protected A executeCollector(In in) {
        listenerManager.publish(new BeforeCollectorEvent(in));
        A adapt = null;
        try {
            adapt = collector.adapt(in, analysisClass());
            listenerManager.publish(new AfterCollectorEvent(in, adapt, collector));
        } catch (CollectorException e) {
            e.printStackTrace();
            return null;
        }
        return adapt;
    }


    /***
     * 解析器必用
     * @return 返回解析器
     **/
    public abstract Analyst<A> getAnalyst();

    public AbstractReceiver<In, A> setaAnalyst(Analyst<A> aAnalyst) {
        this.aAnalyst = aAnalyst;
        return this;
    }

    @Autowired
    public void setCollector(CollectorCombination collector) {
        this.collector = collector;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (this.listenerManager == null) {
            listenerManager = applicationContext.getBean(ListenerManager.class);
        }
    }


}

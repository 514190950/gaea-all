package com.gxz.gaea.src.config;


import com.gxz.gaea.src.annotation.GaeaSrc;
import com.gxz.gaea.src.temp.SrcMessageTrigger;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class ImportActiveMqMessageTrigger implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        MergedAnnotation<GaeaSrc> srcSpringBootApplicationMergedAnnotation =
                importingClassMetadata.getAnnotations().get(GaeaSrc.class);
        String destination = srcSpringBootApplicationMergedAnnotation
                .getValue("destination", String.class).orElseGet(String::new);
        String module = srcSpringBootApplicationMergedAnnotation
                .getValue("module", String.class).orElseGet(String::new);

        Assert.hasLength(destination, "SrcSpringBootApplication 注解上面没有内容 或者是空字符串");
        Assert.hasLength(module, "SrcSpringBootApplication 注解上面没有内容 或者是空字符串");

        // 注册ActiveMq触发器
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(SrcMessageTrigger.class);
        ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
        constructorArgumentValues.addGenericArgumentValue(destination);
        rootBeanDefinition.setConstructorArgumentValues(constructorArgumentValues);
        registry.registerBeanDefinition("src-ActiveMqMessageTrigger", rootBeanDefinition);
    }

}

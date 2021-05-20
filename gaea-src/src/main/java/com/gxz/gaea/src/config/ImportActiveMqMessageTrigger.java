package com.gxz.gaea.src.config;


import com.gxz.gaea.src.annotation.SrcSpringBootApplication;
import com.gxz.gaea.src.temp.TestMessageTrigger;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class ImportActiveMqMessageTrigger implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        MergedAnnotation<SrcSpringBootApplication> srcSpringBootApplicationMergedAnnotation =
                importingClassMetadata.getAnnotations().get(SrcSpringBootApplication.class);
        String destination = srcSpringBootApplicationMergedAnnotation
                .getValue("destination", String.class).orElseGet(String::new);
        Assert.state(StringUtils.hasText(destination), "SrcSpringBootApplication 注解上面没有内容 或者是空字符串");
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(TestMessageTrigger.class);
        ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
        constructorArgumentValues.addGenericArgumentValue(destination);
        rootBeanDefinition.setConstructorArgumentValues(constructorArgumentValues);
        registry.registerBeanDefinition("src-ActiveMqMessageTrigger", rootBeanDefinition);

    }

}

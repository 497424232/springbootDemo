package com.example.demo.test;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * BeanPostProcessor依赖的Bean不会执行BeanPostProcessor
 * BeanPostProcessor依赖的Bean是不会执行BeanPostProcessor的，这是因为在创建BeanPOstProcessor之所依赖的Bean就需要完成初始化，而这个时候BeanPostProcessor都还未完初始化完成
 *
 * 最后ClassA是不会打印出来的，而ClassB是会被打印出来。因为MyBeanPostProcessor依赖ClassA实例。
 *
 * @author changmk
 * @version 1.0
 * @date 2023/6/8 10:28
 */
public class BeanPostProcessorBeanPrint {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.example.demo.test");
        context.register(BeanPostProcessorBeanPrint.class);
        context.refresh();
    }
}

@Component
class ClassA {
}

@Component
class ClassB {
}

@Component
class MyBeanPostProcessor implements BeanPostProcessor {
    @Autowired
    private ClassA classA;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("MyBeanPostProcessor" + bean);
        return bean;
    }
}
package com.example.demo.test.beanPostProcessor;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;

import javax.annotation.PostConstruct;

/**
 * CommonAnnotationBeanPostProcessor 此处理器内处理 PostConstruct注解
 *
 * @author changmk
 * @version 1.0
 * @date 2023/6/8 10:22
 */
public class CommonAnnotationBeanPostProcessorTest {
    public static void main(String[] args) {
        //创建基础容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //构建BeanDefinition并注册
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Person.class)
                .getBeanDefinition();
        beanFactory.registerBeanDefinition("person", beanDefinition);
        //注册CommonAnnotationBeanPostProcessor
        CommonAnnotationBeanPostProcessor commonAnnotationBeanPostProcessor = new CommonAnnotationBeanPostProcessor();

        // 注释下面代码，则不打印@PostConstruct
        beanFactory.addBeanPostProcessor(commonAnnotationBeanPostProcessor);
        //获取Bean
        Person person = beanFactory.getBean(Person.class);
        System.out.println(person);
    }
}

class Person {

    @PostConstruct
    public void annotationInitMethod() {
        System.out.println("@PostConstruct");
    }
}
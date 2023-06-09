package com.example.demo.test.springBean;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author changmk
 * @version 1.0
 * @date 2023/6/8 11:45
 */
public class LifecycleDemo3 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Config.class);
        context.refresh();
        context.close();
    }
}
class Student implements InitializingBean, DisposableBean {
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Student() {
        System.out.println("实例化Bean");
    }
    @PostConstruct
    public void postConstruct(){
        System.out.println("@PostConstruct");
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean#afterPropertiesSet()");
    }
    public void customInitMethod(){
        System.out.println("BeanDefinition中自定义的init-method");
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("@PreDestroy");
    }
    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean#destroy()");
    }
    public void customDestroyMethod(){
        System.out.println("BeanDefinition中自定义的destroy-method");
    }
}
class CustomInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        System.out.println("InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation()");
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        System.out.println("InstantiationAwareBeanPostProcessor#postProcessAfterInstantiation()");
        return true;
    }
    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        System.out.println("InstantiationAwareBeanPostProcessor#postProcessProperties()");
        return null;
    }
}
class CustomBeanPostProcessor implements BeanPostProcessor{
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("BeanPostProcessor#postProcessBeforeInitialization()");
        return bean;
    }
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("BeanPostProcessor#postProcessAfterInitialization()");
        return bean;
    }
}
class Config{
    @Bean(initMethod = "customInitMethod",destroyMethod = "customDestroyMethod")
    public Student student(){
        return new Student();
    }
    @Bean
    public CustomInstantiationAwareBeanPostProcessor customInstantiationAwareBeanPostProcessor(){
        return new CustomInstantiationAwareBeanPostProcessor();
    }
    @Bean
    public CustomBeanPostProcessor customBeanPostProcessor(){
        return new CustomBeanPostProcessor();
    }
}

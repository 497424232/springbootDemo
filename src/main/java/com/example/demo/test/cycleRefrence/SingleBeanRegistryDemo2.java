package com.example.demo.test.cycleRefrence;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * 1. singletonObjects：存放的是已经初始化好的Bean，从缓存中取出的Bean可以直接使用。
 * 2. earlySingletonObjects：存放提前暴露的Bean，此时该Bean还是半成品，属性赋值还未完成。
 * 3. singletonFactories：缓存单例工厂Bean。
 *
 * 链接：https://www.jianshu.com/p/12792960ef0a
 * @author changmk
 * @version 1.0
 * @date 2023/6/14 18:13
 */
public class SingleBeanRegistryDemo2 {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.addBeanPostProcessor(new MyInstantiationAwareBeanPostProcessor());
        BeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:/xml/spring-bean-registry-2.xml");
        E e = beanFactory.getBean(E.class);
        F f = beanFactory.getBean(F.class);
        System.out.println(e);
        System.out.println(f);
    }
}
class E {
    private F f;
    public E(F f) {
        this.f = f;
    }
    public E() {
        System.out.println("实例化E");
    }
    public F getF() {
        return f;
    }
    public void setF(F f) {
        this.f = f;
        System.out.println("为E中的F设置属性值,F中的E的值为:"+f.getE());
    }
}
class F {
    private E e;
    public F(E e) {
        this.e = e;
    }
    public F() {
        System.out.println("实例化F");
    }
    public E getE() {
        return e;
    }
    public void setE(E e) {
        this.e = e;
        System.out.println("为F中的E设置属性值,E中的F的值为:"+e.getF());
    }
}
class MyInstantiationAwareBeanPostProcessor implements SmartInstantiationAwareBeanPostProcessor {

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        System.out.printf("%s:实例化后%s%n",beanName,bean);
        return true;
    }
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.printf("%s:初始化前%n",beanName);
        return null;
    }
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.printf("%s:初始化后%n",beanName);
        return null;
    }
    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        System.out.printf("%s:属性赋值前%n",beanName);
        return pvs;
    }
    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        System.out.printf("获取提前暴露的bean引用:%s = %s%n",beanName,bean);
        return bean;
    }
}

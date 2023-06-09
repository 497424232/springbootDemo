package com.example.demo.test.springBean;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import javax.annotation.PostConstruct;

/**
 *
 * https://www.jianshu.com/p/86c1ef7ec935
 *
 * @author changmk
 * @version 1.0
 * @date 2023/6/8 11:19
 */
public class LifecycleDemo {
    public static void main(String[] args) {
        //创建BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //读取配置文件
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("xml/spring-life-cycle.xml");
        //注册 InstantiationAwareBeanPostProcessor
        beanFactory.addBeanPostProcessor(new MyInstantiationAwareBeanPostProcessor());
        //获取Bean并打印
        User user = beanFactory.getBean("user", User.class);
        System.out.println(user);
    }
}

class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        System.out.println("MyInstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation()");
        return null;
    }
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        System.out.println("MyInstantiationAwareBeanPostProcessor#postProcessAfterInstantiation()");
        return true;
    }
    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        System.out.println("MyInstantiationAwareBeanPostProcessor#postProcessProperties()");
        for (PropertyValue pv : pvs) {
            if (pv.getValue() instanceof TypedStringValue){
                TypedStringValue value = (TypedStringValue) pv.getValue();
                System.out.println(pv.getName() + " = " + value.getValue());
            }
        }
        MutablePropertyValues mvs = new MutablePropertyValues();
        mvs.add("userName", "update_name");
        mvs.add("age", 19);
        return mvs;
    }

    //BeanPostProcessor中定义的接口
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("MyInstantiationAwareBeanPostProcessor#postProcessBeforeInitialization()");
        System.out.println(bean);
        if (bean instanceof User){
            ((User) bean).setAge(20);
        }
        return bean;
    }
    //BeanPostProcessor中定义的接口
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("MyInstantiationAwareBeanPostProcessor#postProcessAfterInitialization()");
        System.out.println(bean);
        if (bean instanceof User){
            ((User) bean).setAge(21);
        }
        return bean;
    }
}
class User implements InitializingBean {
    private String userName;
    private Integer age;
    public User() {
        System.out.println("User()");
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        System.out.println("setUserName()");
        this.userName = userName;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        System.out.println("setAge()");
        this.age = age;
    }
    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", age=" + age +
                '}';
    }
    @PostConstruct
    public void postConstruct(){
        System.out.println("User#postConstruct()");
    }
    //在Spring中我们还可以通过实现InitializingBean或者指定init-method方法来就行初始化。
    // ，主要做一下修改：
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet()");
    }
    //修改处
    public void initMethod(){
        System.out.println("initMethod()");
    }
}
package com.example.demo.test.beanFactoryPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * BeanDefinitionRegistryPostProcessor
 * 为BeanFactoryPostProcessor的子接口，该接口提供的postProcessBeanDefinitionRegistry
 * 可以在容器初始化完成之后修改BeanDefinition列表。简单的说就是我们可以通过该接口增加或者删除容器中BeanDefinition的定义。
 *
 * 作者：一个菜鸟JAVA
 * 链接：https://www.jianshu.com/p/4d905df89ab0
 *
 * @author changmk
 * @version 1.0
 * @date 2023/6/14 16:29
 */
public class App2 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("xml/spring-bean-factory-post-processor.xml");
        //获取并打印User
        Person user = context.getBean(Person.class);
        Object user1 = context.getBean("user");
        System.out.println(user);
        System.out.println(user1);
    }
}
class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // 创建的实例
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Person.class)
                .addPropertyValue("userName", "jack")
                .getBeanDefinition();
        registry.registerBeanDefinition("person",beanDefinition);
    }
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition user = beanFactory.getBeanDefinition("user");
        MutablePropertyValues propertyValues = user.getPropertyValues();
        for (PropertyValue p : propertyValues) {
            String name = p.getName();
            Object value = p.getValue();
            System.out.println("postProcessBeanFactory name =" + value);
        }

    }
}
class Person{
    private String userName;
    public Person() {
        System.out.println("Person构造方法被调用");
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    @Override
    public String toString() {
        return "Person{" +
                "userName='" + userName + '\'' +
                '}';
    }
}

package com.example.demo.test.beanFactoryPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Optional;

/**
 * BeanFactoryPostProcessor可以与Bean定义进行交互并进行修改，但不能与Bean实例进行交互。
 * 这样做可能会导致bean实例化过早，从而违反了容器并造成了意外的副作用。如果需要bean实例交互，请考虑改为实现BeanPostProcessor。
 *
 * 作者：一个菜鸟JAVA
 * 链接：https://www.jianshu.com/p/4d905df89ab0
 *
 * @author changmk
 * @version 1.0
 * @date 2023/6/14 16:10
 */
public class App1 {
    public static void main(String[] args) {
        //创建ClassPathXmlApplicationContext
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("xml/spring-bean-factory-post-processor.xml");
        //获取并打印出user Bean
        User user = context.getBean(User.class);
        System.out.println(user);
        //获取user BeanDefinition
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        BeanDefinition userBeanDefinition = beanFactory.getBeanDefinition("user");
        //打印出user BeanDefinition中的值
        for (PropertyValue propertyValue : userBeanDefinition.getPropertyValues()) {
            String name = propertyValue.getName();
            if (propertyValue.getValue() instanceof TypedStringValue){
                TypedStringValue temp = (TypedStringValue) propertyValue.getValue();
                System.out.printf("App1-修改之后：name = %s, value = %s\r\n",name,temp.getValue());
            }
        }
    }
}
class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("user");
        for (PropertyValue propertyValue : beanDefinition.getPropertyValues()) {
            String name = propertyValue.getName();
            if (propertyValue.getValue() instanceof TypedStringValue){
                TypedStringValue temp = (TypedStringValue) propertyValue.getValue();
                System.out.printf("MyBeanFactoryPostProcessor-修改之前：name = %s, value = %s\r\n",name,temp.getValue());
                Optional.ofNullable(temp.getValue()).ifPresent(value -> temp.setValue(value.toUpperCase()));
            }
        }
    }
}
class User{
    private String userName;
    public User() {
        System.out.println("User构造方法被调用");
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                '}';
    }
}

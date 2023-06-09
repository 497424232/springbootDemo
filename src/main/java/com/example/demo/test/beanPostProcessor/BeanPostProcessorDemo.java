package com.example.demo.test.beanPostProcessor;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @author changmk
 * @version 1.0
 * @date 2023/6/7 18:20
 */
public class BeanPostProcessorDemo {
    public static void main(String[] args) {
        //创建基础容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //加载xml配置文件
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("xml/spring-bean-post-processor.xml");
        //添加BeanPostProcessor
        beanFactory.addBeanPostProcessor(new UserBeanPostProcessor());
        User user = beanFactory.getBean(User.class);
        System.out.println(user);
    }
}

@Data
class User{
    private String userName;
    private Integer age;
    private String beforeMessage;
    private String afterMessage;
    public void initMethod(){
        System.out.println("初始化:"+this);
        this.setUserName("小明");
        this.setAge(18);
    }
}

class UserBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof User){
            System.out.println("初始化前:"+bean);
            ((User) bean).setBeforeMessage("初始化前信息");
        }
        return bean;
    }
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof User){
            System.out.println("初始化后:"+bean);
            ((User) bean).setAfterMessage("初始化后信息");
        }
        return bean;
    }
}
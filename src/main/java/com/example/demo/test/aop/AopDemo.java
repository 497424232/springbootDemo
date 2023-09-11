package com.example.demo.test.aop;

import com.example.demo.aop.AopEntity;
import com.example.demo.service.impl.BookServiceImpl;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.web.servlet.context.XmlServletWebServerApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author changmk
 * @version 1.0
 * @date 2023/6/19 18:02
 */
public class AopDemo {
    public static void main(String[] args) {
        //创建基础容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //加载xml配置文件
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("xml/spring-aop.xml");

//        BookServiceImpl bean = beanFactory.getBean(BookServiceImpl.class);
//        bean.sendLog();


        AbstractApplicationContext context = new ClassPathXmlApplicationContext("xml/spring-aop.xml");
//        BookServiceImpl bookService = context.getBean(BookServiceImpl.class);
//        bookService.sendLog();

        AopEntity aopEntity = (AopEntity)context.getBean("aopEntity");
        aopEntity.add();
    }
}

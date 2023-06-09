package com.example.demo.test.beanDefinition;

import com.example.demo.entity.Book;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * BeanDefinition就是用来描述一个Bean的特性的，容器创建Bean就是根据BeanDefinition中记录的信息来创建的。
 * 定义BeanDefinition的相关配置文件或者注解最后都将转成的BeanDefinition。
 * Spring中IOC容器基本上都具有对BeanDefinition的管理功能。
 *
 * 作者：一个菜鸟JAVA
 * 链接：https://www.jianshu.com/p/f2d6901ee285
 * 来源：简书
 *
 * @author changmk
 * @version 1.0
 * @date 2023/6/9 17:55
 */
public class BeanDefinitionDemo {

    public static void main(String[] args){
        //xml构建
        xmlBeanDefinition();
        //builder构建
        builderBeanDefinition();
    }
    public static void xmlBeanDefinition(){
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("xml/spring-bean-definition.xml");
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("book");
        System.out.println(beanDefinition);
        Book book = beanFactory.getBean(Book.class);
        System.out.println(book);
    }
    public static void builderBeanDefinition(){
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Book.class)
                .addPropertyValue("bookName","钢铁是怎样炼成的")
                .addPropertyValue("author","尼古拉·奥斯特洛夫斯基")
                .getBeanDefinition();
        System.out.println(beanDefinition);
        beanFactory.registerBeanDefinition("user",beanDefinition);
        System.out.println(beanFactory.getBean("user"));
    }
}

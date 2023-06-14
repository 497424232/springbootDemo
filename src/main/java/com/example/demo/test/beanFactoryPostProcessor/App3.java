package com.example.demo.test.beanFactoryPostProcessor;

import lombok.Data;
import lombok.ToString;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 对于BeanFactoryPostProcessor来说，它针对BeanFactory做扩展，即Spring会在BeanFactory初始化之后，
 * 所有的BeanDefinition都已经加载，但是Bean实例还未创建前调用，我们可以对BeanDefinition做修改。
 *
 * 而BeanDefinitionRegistryPostProcessor的调用会在BeanFactoryPostProcessor之前，
 * 我们可以通过它在BeanFactoryPostProcessor之前注册更多的BeanDefinition。
 *
 * 作者：一个菜鸟JAVA
 * 链接：https://www.jianshu.com/p/4d905df89ab0
 *
 * @author changmk
 * @version 1.0
 * @date 2023/6/14 17:04
 */
public class App3 {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("xml/spring-bean-factory-post-processor-2.xml");

        Object dbInfo = context.getBean(DbInfo.class);
        System.out.println(dbInfo);
    }
}

@Data
@ToString
class DbInfo{
    private String url;
    private String userName;
}
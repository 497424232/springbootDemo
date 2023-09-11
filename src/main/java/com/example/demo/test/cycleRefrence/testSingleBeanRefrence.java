package com.example.demo.test.cycleRefrence;

import com.example.demo.test.beanDefinition.MyComponent;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;

/**
 * @author changmk
 * @version 1.0
 * @date 2023/6/16 18:02
 */
public class testSingleBeanRefrence {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
        reader.register(MyComponent1.class);
        reader.register(MyComponent2.class);
        MyComponent1 myComponent1 = beanFactory.getBean(MyComponent1.class);
        MyComponent2 myComponent2 = beanFactory.getBean(MyComponent2.class);
        System.out.println(myComponent1.getMyComponent2() == myComponent2);
        System.out.println(myComponent2.getMyComponent1() == myComponent1);
    }

}
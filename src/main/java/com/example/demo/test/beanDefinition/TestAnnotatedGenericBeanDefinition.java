package com.example.demo.test.beanDefinition;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;

/**
 * 从名称可以看出处理注解相关的BeanDefinition，
 * 例如我们下面的MyComponent类中，我们可以设置它的名称，scope，是不是primary等等信息。
 * @author changmk
 * @version 1.0
 * @date 2023/6/9 18:14
 */
public class TestAnnotatedGenericBeanDefinition {

    public static void main(String[] args) {
        annotatedGenericBeanDefinition();
    }

    public static void annotatedGenericBeanDefinition(){
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
        reader.register(MyComponent.class);
        MyComponent myComponent1 = beanFactory.getBean(MyComponent.class);
        MyComponent myComponent2 = beanFactory.getBean(MyComponent.class);
        System.out.println(myComponent1 == myComponent2);
        BeanDefinition definition = beanFactory.getBeanDefinition("myComponent_1");
        System.out.println(definition.getClass());
        System.out.println(definition.getScope());
    }
}

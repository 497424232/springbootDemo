package com.example.demo.test.springBean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * Person只需要实现BeanFactoryAware接口，然后容器会回调setBeanFactory方法，我们就能获取到BeanFactory实例了，而且最后打印的结果返回true也证实了容器给我们注入的BeanFactory就是我们自己手动创建出来的
 *
 * 链接：https://www.jianshu.com/p/86c1ef7ec935
 * 来源：简书
 *
 * @author changmk
 * @version 1.0
 * @date 2023/6/8 11:33
 */
public class LifecycleDemo2 {
    public static void main(String[] args) {
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Person.class).getBeanDefinition();
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("person",beanDefinition);
        Person person = beanFactory.getBean(Person.class);
        System.out.println(person.getBeanFactory() == beanFactory);
    }
}
class Person implements BeanFactoryAware {
    private BeanFactory beanFactory;
    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
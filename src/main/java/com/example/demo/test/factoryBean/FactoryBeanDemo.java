package com.example.demo.test.factoryBean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 *一般情况下，Spring是通过反射机制利用Bean的Class属性指定实现类来实例化Class。
 * 但是在某些情况下，实例化Bean过程比较复杂，传统的方式就不太适合用来实例化Bean。
 * 所以Spring提供了FactoryBean这么一个接口，可以通过该接口实现定制实例化Bean的逻辑
 *
 * 作者：一个菜鸟JAVA
 * 链接：https://www.jianshu.com/p/cbf92e66f61e
 * 来源：简书
 *
 *
 * @author changmk
 * @version 1.0
 * @date 2023/6/8 11:14
 */
public class FactoryBeanDemo {

    public static void main(String[] args) {
        //创建IOC容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //读取配置文件
        BeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("xml/spring-factory-bean.xml");
        //获取UserService实例
        UserService us1 = (UserService) beanFactory.getBean("userService");
        System.out.println(us1);
        //获取UserServiceFactoryBean实例
        UserServiceFactoryBean usfb = (UserServiceFactoryBean) beanFactory.getBean("&userService");
        System.out.println(usfb);
    }
}
class UserServiceFactoryBean implements FactoryBean<UserService> {
    public UserServiceFactoryBean() {
        System.out.println("调用无参构造函数创建UserServiceFactoryBean:"+this);
    }
    @Override
    public UserService getObject() throws Exception {
        return new UserService();
    }
    @Override
    public Class<?> getObjectType() {
        return UserService.class;
    }
}
class UserService{
    public UserService() {
        System.out.println("调用无参构造函数创建UserService:"+this);
    }
}
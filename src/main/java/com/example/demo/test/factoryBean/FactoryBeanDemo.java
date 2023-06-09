package com.example.demo.test.factoryBean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 *
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
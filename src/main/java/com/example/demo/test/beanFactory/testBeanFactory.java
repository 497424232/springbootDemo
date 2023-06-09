package com.example.demo.test.beanFactory;

import com.example.demo.entity.User;
import com.example.demo.entity.UserHolder;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.util.Map;

/**
 *
 * https://www.jianshu.com/p/7b9cbdf6016d
 *
 * ConfigurableBeanFactory
 * ConfigurableBeanFactory定义了许多BeanFactory的配置相关方法，主要包含了父容器、类加载、元数据缓存开关、处理和加载Bean细节方法、处理Bean后处理器、注册范围等等一系列配置项。
 *
 * ConfigurableListableBeanFactory
 * 它是一个组合接口，它继承了ListableBeanFactory， AutowireCapableBeanFactory,，ConfigurableBeanFactory三个接口，同时提供忽律自动配置的方法、注册可分解依赖的方法、判断指定Bean是否有资格作为自动装配候选资格的方法、根据bean名称返回BeanDefinition的方法、冻结所有Bean配置相关方法、使所有非延迟加载的单例类都实例化方法。
 *
 * DefaultListableBeanFactory
 * 前面都是各种接口，这些接口基本上都是直接或者间接继承了BeanFactory，同时不同的接口都有着自己特色的功能，最后组合称成ConfigurableListableBeanFactory接口。而该接口最后的主要实现就是DefaultListableFactory。
 *
 *
 *
 * @author changmk
 * @version 1.0
 * @date 2023/6/9 17:34
 */
public class testBeanFactory {

    public static void main(String[] args) {
        hierarchicalBeanFactory();
        System.out.println("--------------------------");
        listableBeanFactory();
        System.out.println("--------------------------");
        autowireCapableBeanFactory();
    }

    /**
     * HierarchicalBeanFactory
     * BeanFactory的子接口之一，它是父子级联IOC容器的接口，子容器可以通过接口方法访问到父容器。
     *
     *  HierarchicalBeanFactory在获取Bean时会先从本地查找，
     *  如果本地找不到再从父BeanFactory中查找，如果循环直到找到对应的Bean或者BeanFactory没有父级了。
     */
    public static void hierarchicalBeanFactory() {
        //创建父BeanFactory
        DefaultListableBeanFactory parent = new DefaultListableBeanFactory();
        //创建并注册BeanDefinition到父BeanDefinition
        AbstractBeanDefinition userBd = BeanDefinitionBuilder.genericBeanDefinition(User.class)
                .addPropertyValue("id", 1)
                .addPropertyValue("name", "tom")
                .getBeanDefinition();
        parent.registerBeanDefinition("user", userBd);
        //创建子BeanFactory
        HierarchicalBeanFactory child = new DefaultListableBeanFactory(parent);
        User user = (User) child.getBean("user");
        boolean containsLocalBean = child.containsLocalBean("user");
        boolean containsBean = parent.containsLocalBean("user");
        System.out.println("查找到的Bean信息:" + user);
        System.out.println("child本地是否存在:" + containsLocalBean);
        System.out.println("parent本地是否存在:" + containsBean);
    }

    /**
     * ListableBeanFactory
     * BeanFactory接口只提供了获取单个Bean的方法，而ListableBeanFactory则提供了集合相关操作，便于我们一次获取多个Bean。
     */
    public static void listableBeanFactory(){
        //创建ListableBeanFactory
        ListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //定义user1的BeanDefinition
        AbstractBeanDefinition user1Bd = BeanDefinitionBuilder.genericBeanDefinition(User.class)
                .addPropertyValue("id", 1)
                .addPropertyValue("name", "tom")
                .getBeanDefinition();
        //定义user2的BeanDefinition
        AbstractBeanDefinition user2Bd = BeanDefinitionBuilder.genericBeanDefinition(User.class)
                .addPropertyValue("id", 2)
                .addPropertyValue("name", "jack")
                .getBeanDefinition();
        //注册
        ((DefaultListableBeanFactory)beanFactory).registerBeanDefinition("user1",user1Bd);
        ((DefaultListableBeanFactory)beanFactory).registerBeanDefinition("user2",user2Bd);
        //从容器中类型为User的Bean实例
        Map<String, User> users = beanFactory.getBeansOfType(User.class);
        users.forEach((k,v) -> System.out.printf("%s = %s\r\n",k,v));
    }

    /**
     * AutowireCapableBeanFactory
     * 定义了将容器中的Bean按照某种规则进行自动装配的方法，
     * 例如：按名称匹配、按类型匹配等。同时它还支持将容器内的Bean注入到不受容器管理的外部实例。
     *
     */
    public static void autowireCapableBeanFactory(){
        //创建BeanFactory
        AutowireCapableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //创建UserBeanDefinition
        AbstractBeanDefinition userBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition(User.class)
                .addPropertyValue("id", 1)
                .addPropertyValue("name", "tom")
                .getBeanDefinition();
        //创建UserHolderBeanDefinition
        AbstractBeanDefinition userHolderBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition(UserHolder.class)
                // 如果按名称装配，下面的user1需要改成user，和userHolder的定义一致
                .setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE)
                .getBeanDefinition();
        //注册UserBeanDefinition和UserHolderBeanDefinition
        ((DefaultListableBeanFactory)beanFactory).registerBeanDefinition("user1",userBeanDefinition);
        ((DefaultListableBeanFactory)beanFactory).registerBeanDefinition("userHolder",userHolderBeanDefinition);
        //按照类型自动将User装配到UserHolder中
        UserHolder userHolder = beanFactory.getBean(UserHolder.class);
        User user = beanFactory.getBean(User.class);
        System.out.println(user);
        System.out.println(userHolder);
        System.out.println(userHolder.getUser() == user);
        //不受Spring容器管理的myUserHolder,同样也可以将容器中的UserBean注入到容器外部对象
        UserHolder myUserHolder = new UserHolder();
        beanFactory.autowireBeanProperties(myUserHolder,AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE,false);
        System.out.println(myUserHolder.getUser() == user);
    }
}

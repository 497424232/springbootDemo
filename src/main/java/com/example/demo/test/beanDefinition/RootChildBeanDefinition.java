package com.example.demo.test.beanDefinition;

import com.example.demo.entity.Mike;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.ChildBeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @author changmk
 * @version 1.0
 * @date 2023/6/9 18:03
 */
public class RootChildBeanDefinition {

    public static void main(String[] args) {
        xmlRootAndChildBeanDefinition();
        System.out.println("---------------------------------------");
        apiRootAndChildBeanDefinition();
    }

    public static void xmlRootAndChildBeanDefinition(){
        //创建IOC容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //读取配置文件
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("xml/spring-root-child-bean-definition.xml");
        //获取Bean
        Mike yiliMike = (Mike) beanFactory.getBean("yili");
        Mike quecaoMike = (Mike) beanFactory.getBean("quecao");
        System.out.println(yiliMike);
        System.out.println(quecaoMike);
    }
    public static void apiRootAndChildBeanDefinition(){
        //创建容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //创建Root模板BeanDefinition
        MutablePropertyValues rootProperties = new MutablePropertyValues()
                .add("color","白色")
                .add("source","牛");
        RootBeanDefinition root = new RootBeanDefinition();
        root.setPropertyValues(rootProperties);
        root.setAbstract(true);
        beanFactory.registerBeanDefinition("root",root);
        //创建yili
        MutablePropertyValues yiliProperties = new MutablePropertyValues()
                .add("country","中国");
        ChildBeanDefinition yiliBeanDefinition = new ChildBeanDefinition("root", Mike.class,null,yiliProperties);
        beanFactory.registerBeanDefinition("yili",yiliBeanDefinition);
        //创建quecao
        MutablePropertyValues quecaoProperties = new MutablePropertyValues()
                .add("country","美国");
        ChildBeanDefinition quecaoBeanDefinition = new ChildBeanDefinition("root", Mike.class,null,quecaoProperties);
        beanFactory.registerBeanDefinition("quecao",quecaoBeanDefinition);
        //获取Bean
        Mike yiliMike = (Mike) beanFactory.getBean("yili");
        Mike quecaoMike = (Mike) beanFactory.getBean("quecao");
        System.out.println(yiliMike);
        System.out.println(quecaoMike);
    }

}

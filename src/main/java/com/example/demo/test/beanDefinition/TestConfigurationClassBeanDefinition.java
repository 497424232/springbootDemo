package com.example.demo.test.beanDefinition;

import com.example.demo.entity.Mike;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 该类是ConfigurationClassBeanDefinitionReader中的一个内部类，
 * 我们在spring中使用@Bean注解来定义Bean时生成的BeanDefinition就是这种类型，
 *
 * @author changmk
 * @version 1.0
 * @date 2023/6/9 18:10
 */
public class TestConfigurationClassBeanDefinition {

    public static void main(String[] args) {
        configurationClassBeanDefinition();
    }
    public static void configurationClassBeanDefinition(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //TestConfigurationClassBeanDefinition类为配置类   ==   @Configuration
        context.register(TestConfigurationClassBeanDefinition.class);
        context.refresh();
        Mike yili = (Mike) context.getBean("yili");
        System.out.println(yili);
        BeanDefinition definition = context.getBeanDefinition("yili");
        System.out.println(definition.getClass());
    }
    @Bean
    public Mike yili(){
        Mike mike = new Mike();
        mike.setColor("白色");
        mike.setSource("牛");
        mike.setCountry("中国");
        return mike;
    }

}

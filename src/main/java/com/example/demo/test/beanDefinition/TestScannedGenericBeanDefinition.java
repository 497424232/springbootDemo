package com.example.demo.test.beanDefinition;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;

import java.util.Set;

/**
 *
 * 通过名称就能知道该BeanDefinition与扫描相关。
 * 我们在spring 中可以通过指定包名，然后通过扫描的方式将@Component，@Service，@Repository等相关注解标记的类注册成Spring Bean。
 *
 * 作者：一个菜鸟JAVA
 * 链接：https://www.jianshu.com/p/f2d6901ee285

 * @author changmk
 * @version 1.0
 * @date 2023/6/9 18:17
 */
public class TestScannedGenericBeanDefinition {

    public static void main(String[] args) {
        scannedGenericBeanDefinition();
    }

    public static void scannedGenericBeanDefinition() {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
        Set<BeanDefinition> beanDefinitions = provider.findCandidateComponents("com.example.demo");
        for (BeanDefinition beanDefinition : beanDefinitions) {
            System.out.println(beanDefinition.getBeanClassName() + " = " + beanDefinition.getClass());
        }
    }
}

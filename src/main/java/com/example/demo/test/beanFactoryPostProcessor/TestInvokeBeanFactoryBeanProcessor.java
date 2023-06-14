package com.example.demo.test.beanFactoryPostProcessor;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

import java.util.HashSet;
import java.util.*;

/**
 *
 * @See org.springframework.context.support.PostProcessorRegistrationDelegate#invokeBeanFactoryPostProcessors(org.springframework.beans.factory.config.ConfigurableListableBeanFactory, java.util.List)
 *
 * @author changmk
 * @version 1.0
 * @date 2023/6/14 16:46
 */
public class TestInvokeBeanFactoryBeanProcessor {

    public static void invokeBeanFactoryPostProcessors(
            ConfigurableListableBeanFactory beanFactory, List<BeanFactoryPostProcessor> beanFactoryPostProcessors) {
        // 存储已经执行完成的BeanFactoryPostProcessor
        Set<String> processedBeans = new HashSet<>();
        //判断beanFactory是不是BeanDefinitionRegistry，只有是该类型才能执行BeanDefinitionRegistryPostProcessor
        if (beanFactory instanceof BeanDefinitionRegistry) {
            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
            //存储BeanFactoryPostProcessor
            List<BeanFactoryPostProcessor> regularPostProcessors = new ArrayList<>();
            //存储BeanDefinitionRegistryPostProcessor
            List<BeanDefinitionRegistryPostProcessor> registryProcessors = new ArrayList<>();
            //迭代处理已经在AbstractApplicationContext中的beanFactoryPostProcessors
            for (BeanFactoryPostProcessor postProcessor : beanFactoryPostProcessors) {
                //如果BeanDefinitionRegistryPostProcessor则要执行postProcessBeanDefinitionRegistry方法，并将其添加到registryProcessors列表中
                if (postProcessor instanceof BeanDefinitionRegistryPostProcessor) {
                    BeanDefinitionRegistryPostProcessor registryProcessor =
                            (BeanDefinitionRegistryPostProcessor) postProcessor;
                    registryProcessor.postProcessBeanDefinitionRegistry(registry);
                    registryProcessors.add(registryProcessor);
                }
                //如果是BeanFactoryPostProcessor则直接添加到regularPostProcessors列表中
                else {
                    regularPostProcessors.add(postProcessor);
                }
            }
            //用来临时存储BeanDefinitionRegistryPostProcessor
            List<BeanDefinitionRegistryPostProcessor> currentRegistryProcessors = new ArrayList<>();
            //执行实现了PriorityOrdered的BeanDefinitionRegistryPostProcessor。这个里面的是在配置文件中配置的BeanDefinitionRegistryPostProcessor
            String[] postProcessorNames =
                    beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
            //通过将其添加到临时的currentRegistryProcessors和已执行的processedBeans列表中
            for (String ppName : postProcessorNames) {
                if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
                    currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
                    processedBeans.add(ppName);
                }
            }
            //对BeanDefinitionRegistryPostProcessor排序
//            sortPostProcessors(currentRegistryProcessors, beanFactory);
            //添加到registryProcessors中
            registryProcessors.addAll(currentRegistryProcessors);
            //执行BeanDefinitionRegistryPostProcessor的postProcessBeanDefinitionRegistry方法
//            invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
            //清空临时列表
            currentRegistryProcessors.clear();
            //与上一步一致，只不过换做成实现了PriorityOrdered的BeanDefinitionRegistryPostProcessor
            postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
            for (String ppName : postProcessorNames) {
                if (!processedBeans.contains(ppName) && beanFactory.isTypeMatch(ppName, Ordered.class)) {
                    currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
                    processedBeans.add(ppName);
                }
            }
//            sortPostProcessors(currentRegistryProcessors, beanFactory);
            registryProcessors.addAll(currentRegistryProcessors);
//            invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
            currentRegistryProcessors.clear();
            //循环处理剩下的BeanDefinitionRegistryPostProcessor知道没有再出现
            boolean reiterate = true;
            while (reiterate) {
                reiterate = false;
                postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
                for (String ppName : postProcessorNames) {
                    if (!processedBeans.contains(ppName)) {
                        currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
                        processedBeans.add(ppName);
                        reiterate = true;
                    }
                }
//                sortPostProcessors(currentRegistryProcessors, beanFactory);
                registryProcessors.addAll(currentRegistryProcessors);
//                invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
                currentRegistryProcessors.clear();
            }
            //执行所有的BeanFactoryPostProcessor，因为BeanDefinitionRegistryPostProcessor也是BeanFactoryPostProcessor
//            invokeBeanFactoryPostProcessors(registryProcessors, beanFactory);
//            invokeBeanFactoryPostProcessors(regularPostProcessors, beanFactory);
        }
        else {
            //因为beanFactory不是BeanDefinitionRegistry，所以执行所有的BeanFactoryPostProcessor
//            invokeBeanFactoryPostProcessors(beanFactoryPostProcessors, beanFactory);
        }
        //处理再配置文件中所定义的BeanFactoryPostProcessor，之前处理的是已经添加到容器中的BeanFactoryPostProcessor和定义在配置文件中的BeanDefinitionRegistryPostProcessor
        String[] postProcessorNames =
                beanFactory.getBeanNamesForType(BeanFactoryPostProcessor.class, true, false);
        //将BeanFactoryPostProcessor按照不同类型（这里指的是是否有序）分组存储
        List<BeanFactoryPostProcessor> priorityOrderedPostProcessors = new ArrayList<>();
        List<String> orderedPostProcessorNames = new ArrayList<>();
        List<String> nonOrderedPostProcessorNames = new ArrayList<>();
        for (String ppName : postProcessorNames) {
            if (processedBeans.contains(ppName)) {
                // skip - already processed in first phase above
            }
            else if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
                priorityOrderedPostProcessors.add(beanFactory.getBean(ppName, BeanFactoryPostProcessor.class));
            }
            else if (beanFactory.isTypeMatch(ppName, Ordered.class)) {
                orderedPostProcessorNames.add(ppName);
            }
            else {
                nonOrderedPostProcessorNames.add(ppName);
            }
        }
        //首先对实现了PriorityOrdered的进行排序，然后执行
//        sortPostProcessors(priorityOrderedPostProcessors, beanFactory);
//        invokeBeanFactoryPostProcessors(priorityOrderedPostProcessors, beanFactory);
        //然后对实现了Ordered的进行排序，然后执行
        List<BeanFactoryPostProcessor> orderedPostProcessors = new ArrayList<>(orderedPostProcessorNames.size());
        for (String postProcessorName : orderedPostProcessorNames) {
            orderedPostProcessors.add(beanFactory.getBean(postProcessorName, BeanFactoryPostProcessor.class));
        }
//        sortPostProcessors(orderedPostProcessors, beanFactory);
//        invokeBeanFactoryPostProcessors(orderedPostProcessors, beanFactory);
        //最后执行剩下的BeanFactoryPostProcessor
        List<BeanFactoryPostProcessor> nonOrderedPostProcessors = new ArrayList<>(nonOrderedPostProcessorNames.size());
        for (String postProcessorName : nonOrderedPostProcessorNames) {
            nonOrderedPostProcessors.add(beanFactory.getBean(postProcessorName, BeanFactoryPostProcessor.class));
        }
//        invokeBeanFactoryPostProcessors(nonOrderedPostProcessors, beanFactory);
        beanFactory.clearMetadataCache();
    }
}

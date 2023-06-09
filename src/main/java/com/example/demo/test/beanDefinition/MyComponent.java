package com.example.demo.test.beanDefinition;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author changmk
 * @version 1.0
 * @date 2023/6/9 18:12
 */

@Component(value = "myComponent_1")
@Primary
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MyComponent{


}

package com.example.demo.test.cycleRefrence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author changmk
 * @version 1.0
 * @date 2023/6/16 18:02
 */
@Component
public class MyComponent1 {

    @Autowired
    private MyComponent2 myComponent2;

    public MyComponent2 getMyComponent2() {
        return myComponent2;
    }
}

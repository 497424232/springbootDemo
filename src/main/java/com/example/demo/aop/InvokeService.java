package com.example.demo.aop;

import org.springframework.stereotype.Component;

/**
 * @author changmk
 * @version 1.0
 * @date 2023/6/20 11:07
 */
@Component
public class InvokeService {

    public void method() throws Exception {
        int i = 1/0;
        System.out.println("method");
    }
}

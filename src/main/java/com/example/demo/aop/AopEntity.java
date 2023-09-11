package com.example.demo.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author changmk
 * @version 1.0
 * @date 2023/6/19 18:12
 */
@Component
public class AopEntity {

    @Autowired
    private InvokeService invokeService;

    public String add(){
        System.out.println("add");
        try {
            invokeService.method();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "xxx";
    }
}

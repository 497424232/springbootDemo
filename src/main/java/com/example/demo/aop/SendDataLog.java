package com.example.demo.aop;

import java.lang.annotation.*;

/**
 * 发送日志测试
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SendDataLog {

}


package com.example.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author changmk
 * @version 1.0
 * @date 2023/6/19 17:18
 */
@Aspect
@Component
public class MyAspect {

//    @Pointcut("execution(* com.example.demo.service.impl.BookServiceImpl.sendLog())")
    @Pointcut("execution(* com.example.demo.aop.AopEntity.add())")
    public void pointCut() {

    }

//    @Before("pointCut()")
    public void before(ProceedingJoinPoint joinPoint) {
        System.out.println("before");
//        try {
//            joinPoint.proceed();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }

    }

    @Around("pointCut()")
    public void around(ProceedingJoinPoint joinPoint) {
        System.out.println("around");
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
//    @After("pointCut()")
    public void after(ProceedingJoinPoint joinPoint) {
        System.out.println("after");
//        try {
//            joinPoint.proceed();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
    }

    @AfterReturning("pointCut()")
    public void afterReturning(JoinPoint joinPoint) {
        System.out.println("AfterReturning");
//        try {
//            joinPoint.proceed();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
    }

    @AfterThrowing("pointCut()")
    public void afterThrowing(JoinPoint joinPoint) {
        System.out.println("AfterThrowing");
//        try {
//            joinPoint.proceed();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
    }


}

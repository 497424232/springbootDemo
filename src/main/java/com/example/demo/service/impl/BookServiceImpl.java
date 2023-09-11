package com.example.demo.service.impl;

import com.example.demo.aop.SendDataLog;
import com.example.demo.service.BookService;

/**
 * @author changmk
 * @version 1.0
 * @date 2023/6/19 17:41
 */
public class BookServiceImpl implements BookService {

    @Override
    @SendDataLog
    public void sendLog() {
        System.out.println("com.example.demo.service.impl.BookServiceImpl.sendLog");
    }
}

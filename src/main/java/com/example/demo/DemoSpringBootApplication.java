package com.example.demo;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.example.demo.aop.MyAspect;
import com.example.demo.entity.User;
import com.example.demo.service.BookService;
import com.example.demo.service.impl.BookServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class DemoSpringBootApplication {

	public static void main(String[] args) {
//		ConfigurableApplicationContext context = SpringApplication.run(DemoSpringBootApplication.class, args);
//		User user = (User)context.getBean("initUser");
//		System.out.println(user.getAge());
//		user.setAge(32);
//		System.out.println(JSONObject);


		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.example.demo.service", "com.example.demo.aop");
		context.register(BookServiceImpl.class);
		context.register(MyAspect.class);
		context.refresh();
		BookService bookService = (BookService)context.getBean(BookServiceImpl.class);
		bookService.sendLog();

	}

}

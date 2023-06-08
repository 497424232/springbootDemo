package com.example.demo;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.example.demo.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoSpringBootApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DemoSpringBootApplication.class, args);
		User user = (User)context.getBean("initUser");
		System.out.println(user.getAge());
		user.setAge(32);
//		System.out.println(JSONObject);

	}

}

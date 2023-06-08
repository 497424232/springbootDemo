package com.example.demo.config;

import com.example.demo.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author changmk
 * @version 1.0
 * @date 2023/4/19 16:38
 */
@Configuration
public class BeanConfig {

    @Bean
    public User initUser() {
        User user = new User();
        user.setName("milk");
        user.setAge(33);
        return user;
    }
}

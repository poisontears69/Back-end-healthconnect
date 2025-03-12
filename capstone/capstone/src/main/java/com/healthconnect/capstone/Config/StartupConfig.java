package com.healthconnect.capstone.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.healthconnect.capstone.Service.UserService;

import jakarta.annotation.PostConstruct;

@Component
public class StartupConfig {

	@Autowired
	private UserService userService;
	
    @PostConstruct
    public void init() {
        userService.createAdminAccount();
    }
    
    
}

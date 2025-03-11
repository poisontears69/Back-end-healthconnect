package com.healthconnect.capstone.Controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthconnect.capstone.Entity.User;
import com.healthconnect.capstone.Service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
	@Autowired
    private UserService userService;
	
	@CrossOrigin
	@PostMapping("/register")
    public String insertApplicant(@RequestBody User user) throws InterruptedException, ExecutionException {
        return userService.registerApplicant(user);
    }
	
	@PostMapping("/test")
    public String print() {
        return "test";
    }
}

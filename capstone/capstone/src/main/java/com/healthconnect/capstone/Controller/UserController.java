package com.healthconnect.capstone.Controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuthException;
import com.healthconnect.capstone.DTO.JWTResponseDTO;
import com.healthconnect.capstone.DTO.LoginPayloadDTO;
import com.healthconnect.capstone.Entity.UserEntity;
import com.healthconnect.capstone.Service.AuthenticationService;
import com.healthconnect.capstone.Service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired 
	AuthenticationService authenticationService;
	
	@CrossOrigin
	@PostMapping("/register")
    public String insertApplicant(@RequestBody UserEntity user) throws InterruptedException, ExecutionException {
        return authenticationService.registerApplicant(user);
    }
	
	@CrossOrigin
	@PostMapping("/login")
	public ResponseEntity<JWTResponseDTO> login(
			@RequestBody LoginPayloadDTO request
			) throws FirebaseAuthException{
				return ResponseEntity.ok(authenticationService.authenticate(request));
	}
	
	@GetMapping("/test")
    public String print() {
        return "sad";
    }
}

package com.healthconnect.capstone.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@Service
public class FirebaseConfig {
//	File file =   ResourceUtils.getFile("classpath:config/sdk.json");
	@PostConstruct
	public void init() {
	    try {
	        // Firebase initialization logic
	        FileInputStream serviceAccount = new FileInputStream("./sdk.json");
	        FirebaseOptions options = new FirebaseOptions.Builder()
	            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
	            .build();
	        FirebaseApp.initializeApp(options);
	    } catch (IOException e) {
	        throw new RuntimeException("Failed to initialize Firebase", e);
	    }
	}
}

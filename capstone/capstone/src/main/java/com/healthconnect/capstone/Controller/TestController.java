package com.healthconnect.capstone.Controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthconnect.capstone.DTO.ClinicDTO;
import com.healthconnect.capstone.Entity.ClinicEntity;
import com.healthconnect.capstone.Service.TestService;

@RestController
@RequestMapping("/sample")
public class TestController {
	@Autowired
	TestService testService;
	
//	 @GetMapping("/create")
//	    public ClinicEntity createSampleData() throws ExecutionException, InterruptedException {
//	        return testService.createSampleClinic();
//	    }
	 
	 @PostMapping("/create-sample-clinics")
	    public List<ClinicEntity> createSampleClinics() {
	        try {
	            // Call the service method to create 5 sample clinics
	            return testService.createSampleClinics();
	        } catch (ExecutionException | InterruptedException e) {
	            // Handle exceptions (e.g., log the error and return an appropriate response)
	            e.printStackTrace();
	            throw new RuntimeException("Failed to create sample clinics: " + e.getMessage());
	        }
	    }
	 
	 @GetMapping("/all")
	    public List<ClinicDTO> getAllClinics() throws ExecutionException, InterruptedException {
	        return testService.getAllClinics();
	    }
}

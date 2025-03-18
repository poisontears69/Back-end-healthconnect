package com.healthconnect.capstone.Controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.healthconnect.capstone.DTO.ClinicDTO;
import com.healthconnect.capstone.Service.ClinicService;

@RestController  // ✅ Use this for JSON responses
@RequestMapping("/clinic")
@CrossOrigin()  // Optional: Set allowed origins
public class ClinicController {

    @Autowired
    private ClinicService clinicService;

    @GetMapping("/all")
    public List<ClinicDTO> getAllClinics() throws ExecutionException, InterruptedException {
        return clinicService.getAllClinics();
    }
}

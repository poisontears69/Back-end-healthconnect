package com.healthconnect.capstone.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.healthconnect.capstone.DTO.ClinicDTO;

@Service
public class ClinicService {
public List<ClinicDTO> getAllClinics() throws ExecutionException, InterruptedException {
    	
    	Firestore dbFirestore = FirestoreClient.getFirestore();
        List<ClinicDTO> clinicDTOs = new ArrayList<>();

        // Fetch all clinics from Firestore
        ApiFuture<QuerySnapshot> future = dbFirestore.collection("Clinics").get();
        List<QueryDocumentSnapshot> clinicDocuments = future.get().getDocuments();

        // Convert each clinic document to ClinicDTO
        for (QueryDocumentSnapshot clinicDocument : clinicDocuments) {
            ClinicDTO clinicDTO = convertToClinicDTO(clinicDocument);
            clinicDTOs.add(clinicDTO);
        }

        return clinicDTOs;
    }
    
    private ClinicDTO convertToClinicDTO(QueryDocumentSnapshot clinicDocument) throws ExecutionException, InterruptedException {
    	Firestore dbFirestore = FirestoreClient.getFirestore();
        ClinicDTO clinicDTO = new ClinicDTO();

        // Set basic clinic information
        clinicDTO.setName(clinicDocument.getString("name"));
        clinicDTO.setDescription(clinicDocument.getString("description"));

        // Fetch the doctor's name using the first member ID
        List<String> memberIds = (List<String>) clinicDocument.get("memberIds");
        if (memberIds != null && !memberIds.isEmpty()) {
            String doctorId = memberIds.get(0); // Assuming the first member is the doctor
            DocumentSnapshot doctorSnapshot = dbFirestore.collection("Users").document(doctorId).get().get();
            if (doctorSnapshot.exists()) {
                clinicDTO.setDoctorName(doctorSnapshot.getString("username")); // Set the doctor's name
            }
        }

        return clinicDTO;
    }

}

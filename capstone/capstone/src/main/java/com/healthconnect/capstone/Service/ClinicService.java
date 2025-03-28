package com.healthconnect.capstone.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.healthconnect.capstone.DTO.ClinicDTO;
import com.healthconnect.capstone.Entity.ClinicEntity;

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
	
	public ClinicEntity createClinic(ClinicEntity clinic, String doctorId)
            throws ExecutionException, InterruptedException {
		
		Firestore dbFirestore = FirestoreClient.getFirestore();

        // Save the clinic and get the auto-generated ID
        DocumentReference clinicRef = dbFirestore.collection("Clinics").add(clinic).get();
        String clinicId = clinicRef.getId();
        clinic.setId(clinicId);
        clinicRef.set(clinic);
        System.out.println("Clinic saved with ID: " + clinicId);

        return clinic;
    }
    
	private ClinicDTO convertToClinicDTO(QueryDocumentSnapshot clinicDocument) throws ExecutionException, InterruptedException {
	    Firestore dbFirestore = FirestoreClient.getFirestore();
	    ClinicDTO clinicDTO = new ClinicDTO();

	    // Set the document ID (auto-generated ID from Firestore)
	    clinicDTO.setId(clinicDocument.getId());

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

    
//	public void createClinic(ClinicDTO clinicDTO) throws ExecutionException, InterruptedException {
//	    Firestore dbFirestore = FirestoreClient.getFirestore();
//	    Map<String, Object> clinicData = new HashMap<>();
//	    clinicData.put("name", clinicDTO.getName());
//	    clinicData.put("address", clinicDTO.getAddress());
//	    clinicData.put("phoneNumber", clinicDTO.getPhoneNumber());
//	    clinicData.put("specialties", clinicDTO.getSpecialties());
//
//	    dbFirestore.collection("Clinics").document(clinicDTO.getName()).set(clinicData).get();
//	}

}

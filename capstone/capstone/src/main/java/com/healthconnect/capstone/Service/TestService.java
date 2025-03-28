package com.healthconnect.capstone.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.healthconnect.capstone.DTO.ClinicDTO;
import com.healthconnect.capstone.Entity.BookingEntity;
import com.healthconnect.capstone.Entity.ClinicEntity;
import com.healthconnect.capstone.Entity.UserEntity;
import com.healthconnect.capstone.Variables.Role;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class TestService {

	/*
    public ClinicEntity createSampleClinic() throws ExecutionException, InterruptedException {
        // Get Firestore instance
        Firestore dbFirestore = FirestoreClient.getFirestore();

        // Create a sample user with the role of DOCTOR
        UserEntity doctor = new UserEntity();
        doctor.setId(1);
        doctor.setUsername("drjohn");
        doctor.setPassword("encodedPassword");
        doctor.setEmail("drjohn@example.com");
        doctor.setContactNumber("1234567890");
        doctor.setRole(Role.DOCTOR);

        // Save the user to Firestore
        DocumentReference userRef = dbFirestore.collection("Users").document(doctor.getUsername());
        ApiFuture<WriteResult> userFuture = userRef.set(doctor);
        System.out.println("User saved at: " + userFuture.get().getUpdateTime());

        // Create a sample booking
        BookingEntity booking = new BookingEntity();
        booking.setStartTime(new Date()); // Current time
        booking.setEndTime(new Date(System.currentTimeMillis() + 3600 * 1000)); // 1 hour later
        booking.setBooker("patient1");
        booking.setClinic("City Clinic");
        booking.setService("General Checkup");

        // Save the booking to Firestore
        DocumentReference bookingRef = dbFirestore.collection("Bookings").document(booking.getBooker() + "_" + booking.getStartTime().getTime());
        ApiFuture<WriteResult> bookingFuture = bookingRef.set(booking);
        System.out.println("Booking saved at: " + bookingFuture.get().getUpdateTime());

        // Create a sample clinic
        ClinicEntity clinic = new ClinicEntity();
        clinic.setName("City Clinic");
        clinic.setMembers(Arrays.asList(doctor)); // Add the doctor as a member
        clinic.setBookings(Arrays.asList(booking)); // Add the booking

        // Save the clinic to Firestore
        DocumentReference clinicRef = dbFirestore.collection("Clinics").document(clinic.getName());
        ApiFuture<WriteResult> clinicFuture = clinicRef.set(clinic);
        System.out.println("Clinic saved at: " + clinicFuture.get().getUpdateTime());

        return clinic;
    }
    */
   
	public List<ClinicEntity> createSampleClinics() throws ExecutionException, InterruptedException {
	    Firestore dbFirestore = FirestoreClient.getFirestore();
	    List<ClinicEntity> clinics = new ArrayList<>();

	    for (int i = 6; i <= 15; i++) {
	        // Create and save a sample doctor (UserEntity)
	        UserEntity doctor = new UserEntity();
	        doctor.setUsername("drjohn" + i);
	        doctor.setPassword("encodedPassword" + i);
	        doctor.setEmail("drjohn" + i + "@example.com");
	        doctor.setContactNumber("123456789" + i);
	        doctor.setRole(Role.DOCTOR);

	        // Save user and capture the auto-generated ID
	        DocumentReference userRef = dbFirestore.collection("Users").add(doctor).get();
	        String doctorId = userRef.getId();
	        doctor.setId(doctorId); // Set the ID in the entity
	        System.out.println("Doctor saved with ID: " + doctorId);

	        // Update user with auto-generated ID (optional if you want to store it)
	        userRef.set(doctor);

	        // Create and save a sample booking
	        BookingEntity booking = new BookingEntity();
	        booking.setStartTime(new Date());
	        booking.setEndTime(new Date(System.currentTimeMillis() + 3600 * 1000)); // +1 hour
	        booking.setBooker("patient" + i);
	        booking.setClinic("City Clinic " + i);
	        booking.setService("General Checkup");

	        // Save booking and capture the auto-generated ID
	        DocumentReference bookingRef = dbFirestore.collection("Bookings").add(booking).get();
	        String bookingId = bookingRef.getId();
	        booking.setId(bookingId);
	        System.out.println("Booking saved with ID: " + bookingId);

	        // Update booking with auto-generated ID (optional if you want to store it)
	        bookingRef.set(booking);

	        // Create and save a sample clinic
	        ClinicEntity clinic = new ClinicEntity();
	        clinic.setName("City Clinic " + i);
	        clinic.setDescription("A general healthcare clinic");
	        clinic.setMemberIds(Collections.singletonList(doctorId)); // Link doctor by ID
	        clinic.setBookingIds(Collections.singletonList(bookingId)); // Link booking by ID

	        // Save clinic and capture the auto-generated ID
	        DocumentReference clinicRef = dbFirestore.collection("Clinics").add(clinic).get();
	        String clinicId = clinicRef.getId();
	        clinic.setId(clinicId);
	        System.out.println("Clinic saved with ID: " + clinicId);

	        // Update clinic with auto-generated ID (optional if you want to store it)
	        clinicRef.set(clinic);

	        clinics.add(clinic);
	    }

	    return clinics;
	}

    
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
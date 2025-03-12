package com.healthconnect.capstone.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.FirestoreClient;
import com.healthconnect.capstone.DTO.JWTResponseDTO;
import com.healthconnect.capstone.DTO.LoginPayloadDTO;
import com.healthconnect.capstone.Entity.UserEntity;

@Service
public class AuthenticationService {
	@Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

	
    public String registerApplicant(UserEntity user) throws InterruptedException, ExecutionException {
	    // Validate input
		Firestore db = FirestoreClient.getFirestore();
	    if (user == null || user.getUsername() == null || user.getUsername().isEmpty()) {
	        throw new IllegalArgumentException("User or username cannot be null or empty");
	    }

	    // Check if a user with the same username already exists
	    DocumentReference userDocRef = db.collection("Users").document(user.getUsername());
	    ApiFuture<DocumentSnapshot> userFuture = userDocRef.get();
	    DocumentSnapshot userDocument = userFuture.get();

	    if (userDocument.exists()) {
	        return "Username Already Exists";
	    }
	    
	    user.setPassword(passwordEncoder.encode(user.getPassword()));

	    // Save the new user to Firestore
	    ApiFuture<WriteResult> collectionApiFuture = userDocRef.set(user);
	    WriteResult writeResult = collectionApiFuture.get();

	    // Return the update time as a string
	    return writeResult.getUpdateTime().toString();
	}
	
	public JWTResponseDTO authenticate(LoginPayloadDTO request) {
	    try {
	        // Authenticate user
	    	System.out.println(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

	    	authenticationManager.authenticate(
	    		    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
	    		);
	        
	        // Get user data from Firestore
	        Firestore dbFirestore = FirestoreClient.getFirestore();
	        DocumentReference documentReference = dbFirestore.collection("Users").document(request.getUsername());
	        DocumentSnapshot documentSnapshot = documentReference.get().get();
	        
	        if (documentSnapshot.exists()) {
	        	UserEntity user = documentSnapshot.toObject(UserEntity.class);
	
	            // Generate JWT token
	            String token = jwtService.generateToken(user, user.getRole());
	
	            return new JWTResponseDTO(token);
	        } else {
	            throw new RuntimeException("User not found");
	        }
	    } catch (ApiException e) {
	        throw new RuntimeException("Firestore API error", e);
	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt(); // Restore the interrupt status
	        throw new RuntimeException("Thread interrupted", e);
	    } catch (ExecutionException e) {
	        throw new RuntimeException("Execution error", e);
	    }
	}
	
	

}

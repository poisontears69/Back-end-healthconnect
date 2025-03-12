package com.healthconnect.capstone.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.healthconnect.capstone.Entity.UserEntity;
import com.healthconnect.capstone.Variables.Role;

@Service
public class UserService implements UserDetailsService {
	
	private PasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public void createAdminAccount() {
		Firestore db = FirestoreClient.getFirestore();
	    try {
	        // Create a new User object for the admin
	    	UserEntity adminUser = new UserEntity();
	        adminUser.setUsername("admin");
	        adminUser.setPassword("admin");
	        adminUser.setEmail("admin");
	        adminUser.setRole(Role.ADMIN);

	        // Check if an admin account already exists
	        DocumentReference adminDocRef = db.collection("Users").document(adminUser.getUsername());
	        ApiFuture<DocumentSnapshot> adminFuture = adminDocRef.get();
	        DocumentSnapshot adminDocument = adminFuture.get();

	        if (adminDocument.exists()) {
	            return;
	        }

	        // Save the admin user to Firestore
	        ApiFuture<WriteResult> collectionApiFuture = adminDocRef.set(adminUser);
	        WriteResult writeResult = collectionApiFuture.get();

	        System.out.println("Admin account created successfully at: " + writeResult.getUpdateTime());
	    } catch (InterruptedException | ExecutionException e) {
	        System.err.println("Error creating admin account: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            DocumentReference userRef = dbFirestore.collection("Users").document(username);
            DocumentSnapshot userSnapshot = userRef.get().get();



            if (userSnapshot.exists()) {
                UserEntity userEntity = userSnapshot.toObject(UserEntity.class); // Log userEntity to verify mapping
                UserDetails userDetails = buildUserDetails(userEntity);
                return userDetails;
            } else {
                throw new UsernameNotFoundException("Username not found");
            }
        } catch (ApiException | InterruptedException | ExecutionException e) {
            throw new UsernameNotFoundException("Failed to load user by username", e);
        }
    }
	
	public UserEntity getUserByUsername(String username) throws InterruptedException, ExecutionException {
	    Firestore dbFirestore = FirestoreClient.getFirestore();
	    DocumentReference userRef = dbFirestore.collection("Users").document(username);
	    DocumentSnapshot userSnapshot = userRef.get().get();
	    
	    if (userSnapshot.exists()) {
	        return userSnapshot.toObject(UserEntity.class);
	    } else {
	        throw new UsernameNotFoundException("User not found with username: " + username);
	    }
	}

	
	private UserDetails buildUserDetails(UserEntity userEntity) {
	    Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(userEntity.getRole().name()));
	    return new User(userEntity.getUsername(), userEntity.getPassword(), authorities);
	}

	
	
}

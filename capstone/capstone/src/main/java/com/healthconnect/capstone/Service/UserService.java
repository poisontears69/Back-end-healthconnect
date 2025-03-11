package com.healthconnect.capstone.Service;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFunction;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.healthconnect.capstone.Entity.User;

@Service
public class UserService {
	
	private Firestore db = FirestoreClient.getFirestore();
	
	public String registerApplicant(User user) throws InterruptedException, ExecutionException {
	    // Validate input
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

	    // Save the new user to Firestore
	    ApiFuture<WriteResult> collectionApiFuture = userDocRef.set(user);
	    WriteResult writeResult = collectionApiFuture.get();

	    // Return the update time as a string
	    return writeResult.getUpdateTime().toString();
	}
}

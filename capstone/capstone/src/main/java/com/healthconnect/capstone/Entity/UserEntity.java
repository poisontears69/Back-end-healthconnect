package com.healthconnect.capstone.Entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.healthconnect.capstone.Variables.Role;

@SuppressWarnings("serial")
public class UserEntity implements UserDetails, Serializable {

    private String id;
    private String username;
    private String password;
    private String email;
    private String contactNumber;
    private Role role;

    

    // Add authorities field for Firestore mapping

    public UserEntity() {
    	super();
    }


    // Getters and setters for Firestore and UserDetails

    public String getId() {
        return id;
    }

    public void setId(String doctorId) {
        this.id = doctorId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Ensure Firestore can map authorities properly
//    public List<String> getAuthorities() {
//        return authorities;
//    }

    // Convert roles to GrantedAuthority objects
    @Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return java.util.List.of(new SimpleGrantedAuthority(role.name()));
	}


}

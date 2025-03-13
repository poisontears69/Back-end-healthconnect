package com.healthconnect.capstone.Entity;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class OTPEntity {

	private String id;
    private String email;
    private String otp;
    private Date expirationDate;
    private Boolean isUsed;
    
	public OTPEntity() {
		super();
	}
	public OTPEntity(String id, String email, String otp, Date expirationDate) {
		super();
		this.id = id;
		this.email = email;
		this.otp = otp;
		this.expirationDate = expirationDate;
	}
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public Boolean getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}
	
	
    
}

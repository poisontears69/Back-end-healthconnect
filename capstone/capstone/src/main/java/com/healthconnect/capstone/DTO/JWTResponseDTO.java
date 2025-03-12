package com.healthconnect.capstone.DTO;

public class JWTResponseDTO {
	
	private String token;
	
	public JWTResponseDTO(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}
}

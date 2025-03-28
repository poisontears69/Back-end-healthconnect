package com.healthconnect.capstone.DTO;

import com.healthconnect.capstone.Entity.ClinicEntity;

public class CreateClinicDTO {
    private ClinicEntity clinic;
    private String doctorId;    // Optional

    public ClinicEntity getClinic() {
        return clinic;
    }

    public void setClinic(ClinicEntity clinic) {
        this.clinic = clinic;
    }

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}


}
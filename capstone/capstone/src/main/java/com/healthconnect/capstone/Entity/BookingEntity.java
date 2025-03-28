package com.healthconnect.capstone.Entity;

import java.util.Date;

public class BookingEntity {

	private String id;
    private Date startTime; // Start of the time range
    private Date endTime;   // End of the time range
    private String booker;
    private String clinic;
    private String service;

    // Default constructor (required for Firestore)
    public BookingEntity() {
    }

    // Parameterized constructor
    public BookingEntity(Date startTime, Date endTime, String booker, String clinic, String service) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.booker = booker;
        this.clinic = clinic;
        this.service = service;
    }

    // Getters and Setters
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getBooker() {
        return booker;
    }

    public void setBooker(String booker) {
        this.booker = booker;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "BookingEntity{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", booker='" + booker + '\'' +
                ", clinic='" + clinic + '\'' +
                ", service='" + service + '\'' +
                '}';
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
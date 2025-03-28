package com.healthconnect.capstone.Entity;

import java.util.List;

public class ClinicEntity {

	private String id;
    private String name;
    private String specialization;
    private String description;
    private List<String> memberIds; // Store user IDs instead of DocumentReference
    private List<String> bookingIds; // Store booking IDs instead of DocumentReference
    private List<ScheduleEntity> schedules;
    private List<ScheduleEntity> consulationHours;
    private boolean isOnlineConsultationAvailable;

    // Default constructor (required for Firestore)
    public ClinicEntity() {
    }

    public ClinicEntity(String id, String name, String specialization, String description, List<String> memberIds,
			List<String> bookingIds, List<ScheduleEntity> schedules) {
		super();
		this.id = id;
		this.name = name;
		this.specialization = specialization;
		this.description = description;
		this.memberIds = memberIds;
		this.bookingIds = bookingIds;
		this.schedules = schedules;
	}


	// Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<String> memberIds) {
        this.memberIds = memberIds;
    }

    public List<String> getBookingIds() {
        return bookingIds;
    }

    public void setBookingIds(List<String> bookingIds) {
        this.bookingIds = bookingIds;
    }
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public List<ScheduleEntity> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<ScheduleEntity> schedules) {
		this.schedules = schedules;
	}
	
	public boolean isOnlineConsultationAvailable() {
		return isOnlineConsultationAvailable;
	}

	public void setOnlineConsultationAvailable(boolean isOnlineConsultationAvailable) {
		this.isOnlineConsultationAvailable = isOnlineConsultationAvailable;
	}
	
	public List<ScheduleEntity> getConsulationHours() {
		return consulationHours;
	}

	public void setConsulationHours(List<ScheduleEntity> consulationHours) {
		this.consulationHours = consulationHours;
	}

    @Override
    public String toString() {
        return "ClinicEntity{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", memberIds=" + memberIds +
                ", bookingIds=" + bookingIds +
                '}';
    }
}
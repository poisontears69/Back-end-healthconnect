package com.healthconnect.capstone.Entity;

import java.util.List;

public class ClinicEntity {

    private String name;
    private String description;
    private List<String> memberIds; // Store user IDs instead of DocumentReference
    private List<String> bookingIds; // Store booking IDs instead of DocumentReference

    // Default constructor (required for Firestore)
    public ClinicEntity() {
    }

    // Parameterized constructor (optional, for convenience)
    public ClinicEntity(String name, String description, List<String> memberIds, List<String> bookingIds) {
        this.name = name;
        this.description = description;
        this.memberIds = memberIds;
        this.bookingIds = bookingIds;
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
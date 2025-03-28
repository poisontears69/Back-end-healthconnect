package com.healthconnect.capstone.Entity;

import java.time.LocalTime;

public class ScheduleEntity {
	private String day;
    private LocalTime startTime;
    private LocalTime endTime;

    public ScheduleEntity() {
    }

    public ScheduleEntity(String day, LocalTime startTime, LocalTime endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return day + ": " + startTime + " - " + endTime;
    }
}

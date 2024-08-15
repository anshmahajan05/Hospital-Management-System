package com.hospital.entity;

import java.time.LocalDate;
import java.time.LocalTime;

// table name: schedule_tbl
public class ScheduleTbl {
    private long ScheduleId;
    private AuthUserTbl Doctor;
    private LocalDate scheduleDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private int scheduleStatus;
    private String unavailabilityReason;

    public ScheduleTbl(long scheduleId, AuthUserTbl doctor, LocalDate scheduleDate, LocalTime startTime, LocalTime endTime, int scheduleStatus) {
        ScheduleId = scheduleId;
        Doctor = doctor;
        this.scheduleDate = scheduleDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.scheduleStatus = scheduleStatus;
    }

    public ScheduleTbl(long scheduleId, AuthUserTbl doctor, LocalDate scheduleDate, LocalTime startTime, LocalTime endTime, int scheduleStatus, String unavailabilityReason) {
        ScheduleId = scheduleId;
        Doctor = doctor;
        this.scheduleDate = scheduleDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.scheduleStatus = scheduleStatus;
        this.unavailabilityReason = unavailabilityReason;
    }

    public long getScheduleId() {
        return ScheduleId;
    }

    public void setScheduleId(long scheduleId) {
        ScheduleId = scheduleId;
    }

    public AuthUserTbl getDoctor() {
        return Doctor;
    }

    public void setDoctor(AuthUserTbl doctor) {
        Doctor = doctor;
    }

    public LocalDate getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(LocalDate scheduleDate) {
        this.scheduleDate = scheduleDate;
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

    public int getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(int scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }

    public String getUnavailabilityReason() {
        return unavailabilityReason;
    }

    public void setUnavailabilityReason(String unavailabilityReason) {
        this.unavailabilityReason = unavailabilityReason;
    }
}

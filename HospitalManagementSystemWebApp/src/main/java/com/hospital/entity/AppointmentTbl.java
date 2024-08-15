package com.hospital.entity;

// table name: appointment_tbl
public class AppointmentTbl {
    private int AppointmentId;
    private PatientTbl patient;
    private ScheduleTbl schedule;
    private String suggestedTests;
    private String suggestedMedicines;
    private String sufferingFromDisease;
    private int appointmentStatus;

    public AppointmentTbl(int appointmentId, PatientTbl patient, ScheduleTbl schedule, String suggestedTests, String suggestedMedicines, String sufferingFromDisease, int appointmentStatus) {
        AppointmentId = appointmentId;
        this.patient = patient;
        this.schedule = schedule;
        this.suggestedTests = suggestedTests;
        this.suggestedMedicines = suggestedMedicines;
        this.sufferingFromDisease = sufferingFromDisease;
        this.appointmentStatus = appointmentStatus;
    }

    public AppointmentTbl(int appointmentId, PatientTbl patient, ScheduleTbl schedule, int appointmentStatus) {
        AppointmentId = appointmentId;
        this.patient = patient;
        this.schedule = schedule;
        this.appointmentStatus = appointmentStatus;
    }

    public int getAppointmentId() {
        return AppointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        AppointmentId = appointmentId;
    }

    public PatientTbl getPatient() {
        return patient;
    }

    public void setPatient(PatientTbl patient) {
        this.patient = patient;
    }

    public ScheduleTbl getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleTbl schedule) {
        this.schedule = schedule;
    }

    public String getSuggestedTests() {
        return suggestedTests;
    }

    public void setSuggestedTests(String suggestedTests) {
        this.suggestedTests = suggestedTests;
    }

    public String getSuggestedMedicines() {
        return suggestedMedicines;
    }

    public void setSuggestedMedicines(String suggestedMedicines) {
        this.suggestedMedicines = suggestedMedicines;
    }

    public String getSufferingFromDisease() {
        return sufferingFromDisease;
    }

    public void setSufferingFromDisease(String sufferingFromDisease) {
        this.sufferingFromDisease = sufferingFromDisease;
    }

    public int getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(int appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    @Override
    public String toString() {
        return "AppointmentTbl{" +
                "AppointmentId=" + AppointmentId +
                ", patient=" + patient +
                ", schedule=" + schedule +
                ", suggestedTests='" + suggestedTests + '\'' +
                ", suggestedMedicines='" + suggestedMedicines + '\'' +
                ", sufferingFromDisease='" + sufferingFromDisease + '\'' +
                ", appointmentStatus=" + appointmentStatus +
                '}';
    }
}

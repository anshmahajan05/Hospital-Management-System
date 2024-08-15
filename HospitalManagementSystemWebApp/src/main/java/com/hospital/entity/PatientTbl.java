package com.hospital.entity;

// table name: patient_tbl
public class PatientTbl {
    private long PatientId;
    private String name;
    private String email;
    private String mobileNo;
    private AuthUserTbl addedByUser;

    public PatientTbl(long patientId, String name, String email, String mobileNo, AuthUserTbl addedByUser) {
        PatientId = patientId;
        this.name = name;
        this.email = email;
        this.mobileNo = mobileNo;
        this.addedByUser = addedByUser;
    }

    public PatientTbl(long patientId, String name, String mobileNo, AuthUserTbl addedByUser) {
        PatientId = patientId;
        this.name = name;
        this.mobileNo = mobileNo;
        this.addedByUser = addedByUser;
    }

    public long getPatientId() {
        return PatientId;
    }

    public void setPatientId(long patientId) {
        PatientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public AuthUserTbl getAddedByUser() {
        return addedByUser;
    }

    public void setAddedByUser(AuthUserTbl addedByUser) {
        this.addedByUser = addedByUser;
    }

    @Override
    public String toString() {
        return "PatientTbl{" +
                "PatientId=" + PatientId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", addedByUser=" + addedByUser +
                '}';
    }
}

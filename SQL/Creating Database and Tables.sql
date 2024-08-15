drop database if exists hospital_mgmt_system;

create database hospital_mgmt_system;

use hospital_mgmt_system;

create table auth_user_tbl (
	UserId BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    mobileNo VARCHAR(12) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(10) NOT NULL
);

describe auth_user_tbl;

CREATE TABLE patient_tbl (
    PatientId BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    mobileNo VARCHAR(12) NOT NULL,
    addedByUser BIGINT NOT NULL,
    FOREIGN KEY (addedByUser) REFERENCES auth_user_tbl(UserId)
);

describe patient_tbl;

CREATE TABLE schedule_tbl (
    ScheduleId BIGINT NOT NULL PRIMARY KEY,
    DoctorId BIGINT NOT NULL,
    scheduleDate DATE NOT NULL,
    StartTime TIME NOT NULL,
    EndTime TIME NOT NULL,
    scheduleStatus INT NOT NULL,
    unavailableReason TEXT,
    FOREIGN KEY (DoctorId) REFERENCES auth_user_tbl(UserId)
);

describe schedule_tbl;

CREATE TABLE appointment_tbl (
    AppointmentId BIGINT NOT NULL PRIMARY KEY,
    PatientId BIGINT NOT NULL,
    ScheduleId BIGINT NOT NULL,
    suggestedTests VARCHAR(255),
    suggestedMedicines VARCHAR(255),
    sufferingFromDisease VARCHAR(255),
    appointmentStatus INT NOT NULL,
    FOREIGN KEY (PatientId) REFERENCES patient_tbl(PatientId),
    FOREIGN KEY (ScheduleId) REFERENCES schedule_tbl(ScheduleId)
);

describe appointment_tbl;

show tables;
-- Drop the database if it exists
DROP DATABASE IF EXISTS hospital_mgmt_system;

-- Create a new database
CREATE DATABASE hospital_mgmt_system;

-- Use the newly created database
USE hospital_mgmt_system;

-- Create the auth_user_tbl table
CREATE TABLE auth_user_tbl (
    UserId BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    mobileNo VARCHAR(12) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(10) NOT NULL
);

-- Describe the auth_user_tbl table
DESCRIBE auth_user_tbl;

-- Create the patient_tbl table with a foreign key that cascades on delete
CREATE TABLE patient_tbl (
    PatientId BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    mobileNo VARCHAR(12) NOT NULL,
    addedByUser BIGINT NOT NULL,
    FOREIGN KEY (addedByUser) REFERENCES auth_user_tbl(UserId) ON DELETE CASCADE
);

-- Describe the patient_tbl table
DESCRIBE patient_tbl;

-- Create the schedule_tbl table with a foreign key that cascades on delete
CREATE TABLE schedule_tbl (
    ScheduleId BIGINT NOT NULL PRIMARY KEY,
    DoctorId BIGINT NOT NULL,
    scheduleDate DATE NOT NULL,
    StartTime TIME NOT NULL,
    EndTime TIME NOT NULL,
    scheduleStatus INT NOT NULL,
    unavailableReason TEXT,
    FOREIGN KEY (DoctorId) REFERENCES auth_user_tbl(UserId) ON DELETE CASCADE
);

-- Create the appointment_tbl table with foreign keys that cascade on delete
CREATE TABLE appointment_tbl (
    AppointmentId BIGINT NOT NULL PRIMARY KEY,
    PatientId BIGINT NOT NULL,
    ScheduleId BIGINT NOT NULL,
    suggestedTests VARCHAR(255),
    suggestedMedicines VARCHAR(255),
    sufferingFromDisease VARCHAR(255),
    appointmentStatus INT NOT NULL,
    FOREIGN KEY (PatientId) REFERENCES patient_tbl(PatientId) ON DELETE CASCADE,
    FOREIGN KEY (ScheduleId) REFERENCES schedule_tbl(ScheduleId) ON DELETE CASCADE
);
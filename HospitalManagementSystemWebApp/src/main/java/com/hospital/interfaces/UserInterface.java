package com.hospital.interfaces;

import com.hospital.entity.AppointmentTbl;
import com.hospital.entity.PatientTbl;
import com.hospital.exception.ServiceException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public interface UserInterface {
    public boolean addPatient(PatientTbl patient) throws ServiceException;
    public HashMap<LocalDate, List<ScheduleDao>> viewSchedule() throws ServiceException;
    public boolean bookAppointment(AppointmentTbl appointment) throws ServiceException;
    public HashMap<LocalDate, List<AppointmentTbl>> viewAppointments() throws ServiceException;
}

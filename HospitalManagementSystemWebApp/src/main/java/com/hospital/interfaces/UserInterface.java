package com.hospital.interfaces;

import com.hospital.entity.AppointmentTbl;
import com.hospital.entity.AuthUserTbl;
import com.hospital.entity.PatientTbl;
import com.hospital.entity.ScheduleTbl;
import com.hospital.exception.ServiceException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public interface UserInterface {
    public boolean addPatient(PatientTbl patient) throws ServiceException;
    public HashMap<LocalDate, List<ScheduleTbl>> viewSchedule() throws ServiceException;
    public boolean bookAppointment(AppointmentTbl appointment) throws ServiceException;
    public HashMap<LocalDate, List<AppointmentTbl>> viewAppointments() throws ServiceException;
    public AuthUserTbl searchUser(long id) throws ServiceException;
    public PatientTbl searchPatient(long id) throws ServiceException;
    public ScheduleTbl searchSchedule(long id) throws ServiceException;
    public AppointmentTbl searchAppointment(long id) throws ServiceException;
}

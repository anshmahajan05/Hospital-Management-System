package com.hospital.interfaces;

import com.hospital.entity.AppointmentTbl;
import com.hospital.entity.AuthUserTbl;
import com.hospital.entity.PatientTbl;
import com.hospital.exception.ServiceException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public interface AdminInterface {
    public List<AuthUserTbl> getAllAuthUser(String role) throws ServiceException;
    public List<PatientTbl> getAllPatient() throws ServiceException;
    public boolean addAuthUser(AuthUserTbl authUser) throws ServiceException;
    public boolean updateAuthUser(AuthUserTbl authUser) throws ServiceException;
    public AuthUserTbl deleteAuthUser(long id) throws ServiceException;
    public HashMap<LocalDate, List<ScheduleDao>> viewSchedule() throws ServiceException;
    public boolean updateSchedule(ScheduleDao schedule) throws ServiceException;
    public HashMap<LocalDate, List<AppointmentTbl>> viewAppointments() throws ServiceException;
    public AppointmentTbl cancelAppointment(long AppointmentId) throws ServiceException;
}

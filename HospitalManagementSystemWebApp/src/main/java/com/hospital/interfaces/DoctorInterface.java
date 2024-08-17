package com.hospital.interfaces;

import com.hospital.entity.AppointmentTbl;
import com.hospital.entity.ScheduleTbl;
import com.hospital.exception.ServiceException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public interface DoctorInterface {
    public boolean addSchedule(ScheduleTbl schedule) throws ServiceException;
    public HashMap<LocalDate, List<ScheduleTbl>> viewSchedule() throws ServiceException;
    public boolean updateSchedule(ScheduleTbl schedule) throws ServiceException;
    public HashMap<LocalDate, List<AppointmentTbl>> viewAppointments() throws ServiceException;
    public AppointmentTbl cancelAppointment(long AppointmentId) throws ServiceException;
    public boolean updateAppointment(AppointmentTbl appointment) throws ServiceException;
}

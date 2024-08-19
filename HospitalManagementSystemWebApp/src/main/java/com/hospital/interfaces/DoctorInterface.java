package com.hospital.interfaces;

import com.hospital.entity.AppointmentTbl;
import com.hospital.entity.AuthUserTbl;
import com.hospital.entity.ScheduleTbl;
import com.hospital.exception.ServiceException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public interface DoctorInterface {
    public boolean addSchedule(ScheduleTbl schedule) throws ServiceException;
    public HashMap<LocalDate, List<ScheduleTbl>> viewSchedule(AuthUserTbl Doctor) throws ServiceException;
    public boolean updateSchedule(ScheduleTbl schedule) throws ServiceException;
    public HashMap<LocalDate, List<AppointmentTbl>> viewAppointments(AuthUserTbl Doctor) throws ServiceException;
    public AppointmentTbl cancelAppointment(long AppointmentId, AuthUserTbl Doctor) throws ServiceException;
}

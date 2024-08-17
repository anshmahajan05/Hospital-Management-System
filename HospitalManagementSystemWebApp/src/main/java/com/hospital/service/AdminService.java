package com.hospital.service;

import com.hospital.entity.AuthUserTbl;
import com.hospital.exception.DatabaseException;
import com.hospital.exception.ServiceException;
import com.hospital.interfaces.AppointmentDao;
import com.hospital.interfaces.AuthUserDao;
import com.hospital.interfaces.PatientDao;
import com.hospital.interfaces.ScheduleDao;

import java.util.List;
import java.util.stream.Collectors;

public class AdminService {
    private AuthUserDao authUserDao;
    private PatientDao patientDao;
    private ScheduleDao scheduleDao;
    private AppointmentDao appointmentDao;

    public AdminService(AuthUserDao authUserDao, PatientDao patientDao, ScheduleDao scheduleDao, AppointmentDao appointmentDao) {
        this.authUserDao = authUserDao;
        this.patientDao = patientDao;
        this.scheduleDao = scheduleDao;
        this.appointmentDao = appointmentDao;
    }

    public List<AuthUserTbl> getAllAuthUser(String role) throws ServiceException {
        List<AuthUserTbl> authusers = null;
        try {
            authusers = authUserDao.findAll();
            authusers = authusers.stream().filter(user -> user.getRole().equalsIgnoreCase(role)).collect(Collectors.toList());
        } catch (DatabaseException e) {
            throw new ServiceException(e);
        }

        return authusers;
    }
}

package com.hospital.service;

import com.hospital.entity.AppointmentTbl;
import com.hospital.entity.AuthUserTbl;
import com.hospital.entity.PatientTbl;
import com.hospital.entity.ScheduleTbl;
import com.hospital.exception.DatabaseException;
import com.hospital.exception.ServiceException;
import com.hospital.interfaces.*;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceImpl implements LoginInterface, AdminInterface, DoctorInterface, UserInterface {
    private AuthUserDao authUserDao;
    private PatientDao patientDao;
    private ScheduleDao scheduleDao;
    private AppointmentDao appointmentDao;
    private Logger logger = Logger.getLogger(ServiceImpl.class);

    public ServiceImpl(AuthUserDao authUserDao, PatientDao patientDao, ScheduleDao scheduleDao, AppointmentDao appointmentDao) {
        this.authUserDao = authUserDao;
        this.patientDao = patientDao;
        this.scheduleDao = scheduleDao;
        this.appointmentDao = appointmentDao;
        logger.info("ServiceImpl constructor created");
    }
    @Override
    public List<AuthUserTbl> getAllAuthUser(String role) throws ServiceException {
        logger.info("ServiceImpl getAllAuthUser of role " + role);
        List<AuthUserTbl> authusers = null;
        try {
            authusers = authUserDao.findAll();
            logger.info("no of users: " + authusers.size());
            authusers = authusers.stream().filter(user -> user.getRole().equalsIgnoreCase(role)).collect(Collectors.toList());
            logger.info(role + " : " + authusers);
        } catch (DatabaseException e) {
            logger.error("Error Occured at Service Layer: " + e.getMessage());
            logger.error(e);
            throw new ServiceException(e);
        }

        return authusers;
    }

    @Override
    public List<PatientTbl> getAllPatient() throws ServiceException {
        logger.info("ServiceImpl getAllPatient");
        List<PatientTbl> patients = null;
        try {
            patients = patientDao.findAll();
            logger.info("no of patients: " + patients.size());
        } catch (DatabaseException e) {
            logger.error("Error Occured at Service Layer: " + e.getMessage());
            logger.error(e);
            throw new ServiceException(e);
        }
        logger.info("patient details: " + patients);
        return patients;
    }

    @Override
    public boolean addAuthUser(AuthUserTbl authUser) throws ServiceException {
        logger.info("ServiceImpl addAuthUser of authUser " + authUser);
        boolean result = false;
        try {
            if(authUserDao.findByUsername(authUser.getUsername()) != null) {
                logger.error("Username already exists");
                throw new ServiceException("User already exists");
            }
            result = authUserDao.save(authUser);
            logger.info("Saving authUser " + authUser + " successfully");
        } catch (DatabaseException e) {
            logger.error("Error Occured at Service Layer: " + e.getMessage());
            logger.error(e);
            throw new ServiceException(e);
        }

        return result;
    }

    @Override
    public boolean updateAuthUser(AuthUserTbl authUser) throws ServiceException {
        logger.info("ServiceImpl updateAuthUser of authUser " + authUser);
        boolean result = false;

        try {
            result = authUserDao.update(authUser);
            logger.info("Updating authUser " + authUser + " successfully");
        } catch (DatabaseException e) {
            logger.error("Error Occured at Service Layer: " + e.getMessage());
            logger.error(e);
            throw new ServiceException(e);
        }

        return result;
    }

    @Override
    public AuthUserTbl deleteAuthUser(long id) throws ServiceException {
        logger.info("ServiceImpl deleteAuthUser of authUser - id: " + id);
        AuthUserTbl authUser = null;

        try {
            if(authUserDao.findById(id) == null) {
                logger.error("AuthUser with id " + id + " not found");
                throw new ServiceException("AuthUser with id " + id + " not found");
            }

            authUser = authUserDao.delete(id);
            logger.info("Deleting authUser " + authUser + " successfully");
        } catch (DatabaseException e) {
            logger.error("Error Occured at Service Layer: " + e.getMessage());
            logger.error(e);
            throw new ServiceException(e);
        }

        return authUser;
    }

    @Override
    public boolean addSchedule(ScheduleTbl schedule) throws ServiceException {
        return false;
    }

    @Override
    public boolean addPatient(PatientTbl patient) throws ServiceException {
        return false;
    }

    @Override
    public HashMap<LocalDate, List<ScheduleDao>> viewSchedule() throws ServiceException {
        return null;
    }

    @Override
    public boolean bookAppointment(AppointmentTbl appointment) throws ServiceException {
        return false;
    }

    @Override
    public boolean updateSchedule(ScheduleDao schedule) throws ServiceException {
        return false;
    }

    @Override
    public HashMap<LocalDate, List<AppointmentTbl>> viewAppointments() throws ServiceException {
        return null;
    }

    @Override
    public AppointmentTbl cancelAppointment(long AppointmentId) throws ServiceException {
        return null;
    }

    @Override
    public boolean updateAppointment(AppointmentTbl appointment) throws ServiceException {
        return false;
    }

    @Override
    public AuthUserTbl login(String username, String password) throws ServiceException {
        AuthUserTbl authUser = null;
        logger.info("Login Service Login User: " + username);
        logger.info("Login Service Login Password: " + password);

        try {
            authUser = authUserDao.authenticate(username, password);
            logger.info("Login Service Login Success with authuser: " + authUser);
        } catch (DatabaseException e) {
            throw new ServiceException(e);
        }

        return authUser;
    }
}

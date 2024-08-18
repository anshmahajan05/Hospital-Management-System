package com.hospital.factory;

import com.hospital.interfaces.*;
import com.hospital.service.ServiceImpl;
import org.apache.log4j.Logger;

import java.sql.Connection;

public class HospitalServiceFactory {
    private static AdminInterface admin = null;
    private static DoctorInterface doctor = null;
    private static UserInterface user = null;
    private static LoginInterface login = null;
    private static final Logger logger = Logger.getLogger(HospitalServiceFactory.class);

    private static HospitalServiceFactory hospitalServiceFactory = null;

    private HospitalServiceFactory() {}

    public static HospitalServiceFactory getInstance() {
        logger.info("Get HospitalServiceFactory Instance");
        if (hospitalServiceFactory == null) {
            hospitalServiceFactory = new HospitalServiceFactory();
        }
        logger.info("Get HospitalServiceFactory Instance: " + hospitalServiceFactory);
        return hospitalServiceFactory;
    }

    public static AdminInterface getAdmin(Connection conn) {
        logger.info("Get AdminInterface Instance for connection: " + conn);
        if (admin == null) {
            AuthUserDao authUserDao = HospitalDAOFactory.getAuthUserDao(conn);
            PatientDao patientDao = HospitalDAOFactory.getPatientDao(conn);
            ScheduleDao scheduleDao = HospitalDAOFactory.getScheduleDao(conn);
            AppointmentDao appointmentDao = HospitalDAOFactory.getAppointmentDao(conn);

            admin = new ServiceImpl(authUserDao, patientDao, scheduleDao, appointmentDao);
        }
        logger.info("Get AdminInterface Instance: " + admin);
        return admin;
    }

    public static DoctorInterface getDoctor(Connection conn) {
        logger.info("Get DoctorInterface Instance for connection: " + conn);
        if (doctor == null) {
            AuthUserDao authUserDao = HospitalDAOFactory.getAuthUserDao(conn);
            PatientDao patientDao = HospitalDAOFactory.getPatientDao(conn);
            ScheduleDao scheduleDao = HospitalDAOFactory.getScheduleDao(conn);
            AppointmentDao appointmentDao = HospitalDAOFactory.getAppointmentDao(conn);

            doctor = new ServiceImpl(authUserDao, patientDao, scheduleDao, appointmentDao);
        }
        logger.info("Get DoctorInterface Instance: " + doctor);
        return doctor;
    }

    public static UserInterface getUser(Connection conn) {
        logger.info("Get UserInterface Instance for connection: " + conn);
        if (user == null) {
            AuthUserDao authUserDao = HospitalDAOFactory.getAuthUserDao(conn);
            PatientDao patientDao = HospitalDAOFactory.getPatientDao(conn);
            ScheduleDao scheduleDao = HospitalDAOFactory.getScheduleDao(conn);
            AppointmentDao appointmentDao = HospitalDAOFactory.getAppointmentDao(conn);

            user = new ServiceImpl(authUserDao, patientDao, scheduleDao, appointmentDao);
        }
        logger.info("Get UserInterface Instance: " + user);
        return user;
    }

    public static LoginInterface getLogin(Connection conn) {
        logger.info("Get LoginInterface Instance for connection: " + conn);
        if (login == null) {
            AuthUserDao authUserDao = HospitalDAOFactory.getAuthUserDao(conn);
            PatientDao patientDao = HospitalDAOFactory.getPatientDao(conn);
            ScheduleDao scheduleDao = HospitalDAOFactory.getScheduleDao(conn);
            AppointmentDao appointmentDao = HospitalDAOFactory.getAppointmentDao(conn);

            login = new ServiceImpl(authUserDao, patientDao, scheduleDao, appointmentDao);
        }
        logger.info("Get LoginInterface Instance: " + login);
        return login;
    }
}

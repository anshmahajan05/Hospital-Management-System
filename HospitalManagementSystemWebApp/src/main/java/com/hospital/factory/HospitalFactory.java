package com.hospital.factory;

import com.hospital.dao.AppointmentDBDao;
import com.hospital.dao.AuthUserDBDao;
import com.hospital.dao.PatientDBDao;
import com.hospital.dao.ScheduleDBDao;
import com.hospital.interfaces.AppointmentDao;
import com.hospital.interfaces.AuthUserDao;
import com.hospital.interfaces.PatientDao;
import com.hospital.interfaces.ScheduleDao;
import org.apache.log4j.Logger;

import java.sql.Connection;

public class HospitalFactory {
    private static AuthUserDao authUserDao = null;
    private static PatientDao patientDao = null;
    private static ScheduleDao scheduleDao = null;
    private static AppointmentDao appointmentDao = null;

    private static HospitalFactory hospitalFactory = null;

    private Connection conn = null;

    private static Logger logger = Logger.getLogger(HospitalFactory.class);

    private HospitalFactory(Connection conn) {
        this.conn = conn;
    }

    public static HospitalFactory getHospitalFactory(Connection conn) {
        logger.info("Getting Hospital Factory instance: " + hospitalFactory);
        if (hospitalFactory == null) {
            hospitalFactory = new HospitalFactory(conn);
            logger.info("Getting new Hospital Factory : " + hospitalFactory);
        }

        return hospitalFactory;
    }

    public static AuthUserDao getAuthUserDao(Connection conn) {
        logger.info("Getting Auth User DAO instance: " + authUserDao);
        if (authUserDao == null) {
            authUserDao = new AuthUserDBDao(conn);
            logger.info("Getting Auth User DAO instance: " + authUserDao);
        }

        return authUserDao;
    }

    public static PatientDao getPatientDao(Connection conn) {
        logger.info("Getting Patient DAO instance: " + patientDao);
        if (patientDao == null) {
            patientDao = new PatientDBDao(conn, authUserDao);
            logger.info("Getting Patient DAO instance: " + patientDao);
        }

        return patientDao;
    }

    public static ScheduleDao getScheduleDao(Connection conn) {
        logger.info("Getting Schedule DAO instance: " + scheduleDao);
        if (scheduleDao == null) {
            scheduleDao = new ScheduleDBDao(conn, authUserDao);
            logger.info("Getting Schedule DAO instance: " + scheduleDao);
        }

        return scheduleDao;
    }

    public static AppointmentDao getAppointmentDao(Connection conn) {
        logger.info("Getting Appointment DAO instance: " + appointmentDao);
        if (appointmentDao == null) {
            appointmentDao = new AppointmentDBDao(conn, patientDao, scheduleDao);
            logger.info("Getting Appointment DAO instance: " + appointmentDao);
        }

        return appointmentDao;
    }
}

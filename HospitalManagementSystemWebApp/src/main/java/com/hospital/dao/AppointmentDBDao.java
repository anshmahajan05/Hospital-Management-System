package com.hospital.dao;

import com.hospital.entity.AppointmentTbl;
import com.hospital.entity.AuthUserTbl;
import com.hospital.entity.PatientTbl;
import com.hospital.entity.ScheduleTbl;
import com.hospital.exception.DatabaseException;
import com.hospital.interfaces.AppointmentDao;
import com.hospital.interfaces.PatientDao;
import com.hospital.interfaces.ScheduleDao;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDBDao implements AppointmentDao {

    private Connection conn;
    private final Logger logger = Logger.getLogger(AppointmentDBDao.class);
    private PatientDao patientDao;
    private ScheduleDao scheduleDao;

    public AppointmentDBDao(Connection conn, PatientDao patientDao, ScheduleDao scheduleDao) {
        this.conn = conn;
        this.patientDao = patientDao;
        this.scheduleDao = scheduleDao;
    }

    public long getMaxId() throws DatabaseException {
        long max = 0;
        String sqlCommand = "SELECT MAX(AppointmentId) as maxAppointmentId FROM appointment_tbl";
        logger.info("SQL Command to be executed: " + sqlCommand);

        try (PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            if (rs.next()) {
                max = rs.getLong("maxAppointmentId");
            }
        } catch (SQLException e) {
            logger.error("SQL Error: " + e.getMessage());
            logger.error(e);
            throw new DatabaseException(e);
        }
        return max;
    }

    @Override
    public boolean save(AppointmentTbl appointment) throws Exception {
        boolean result = false;
        String sqlCommand = "INSERT INTO appointment_tbl VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            logger.info("SQL Command to be Executed: " + sqlCommand);
            appointment.setAppointmentId(getMaxId() + 1);
            logger.info("Appointment to be saved: "+ appointment);

            try (PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
                ps.setLong(1, appointment.getAppointmentId());
                ps.setLong(2, appointment.getPatient().getPatientId());
                ps.setLong(3, appointment.getSchedule().getScheduleId());
                ps.setString(4, appointment.getSuggestedTests());
                ps.setString(5, appointment.getSuggestedMedicines());
                ps.setString(6, appointment.getSufferingFromDisease());
                ps.setInt(7, appointment.getAppointmentStatus());

                logger.info("SQL Command to be Executed: " + ps);
                ps.executeUpdate();
                result = true;
                logger.info("Successfully saved the appointment: " + appointment);
            } catch (SQLException e) {
                logger.error("Could not save the appointment: " + appointment + " due to error: " + e.getMessage());
                logger.error(e);
                throw new DatabaseException(e);
            }
        } catch (DatabaseException e) {
            logger.error("Could not save the appointment: " + appointment + " due to error: " + e.getMessage());
            logger.error(e);
            throw e;
        }

        return result;
    }

    @Override
    public boolean update(AppointmentTbl appointment) throws Exception {
        boolean result = false;
        String sqlCommand = "UPDATE appointment_tbl SET PatientId = ?, ScheduleId = ?, suggestedTests = ?, suggestedMedicines = ?, sufferingFromDisease = ?, appointmentStatus = ? WHERE AppointmentId = ?";
        logger.info("SQL Command to be Executed: " + sqlCommand);
        logger.info("Appointment to be Updated " + appointment);

        try(PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
            ps.setLong(1, appointment.getPatient().getPatientId());
            ps.setLong(2, appointment.getSchedule().getScheduleId());
            ps.setString(3, appointment.getSuggestedTests());
            ps.setString(4, appointment.getSuggestedMedicines());
            ps.setString(5, appointment.getSufferingFromDisease());
            ps.setInt(6, appointment.getAppointmentStatus());
            ps.setLong(7, appointment.getAppointmentId());

            ps.executeUpdate();
            result = true;
            logger.info("Successfully updated the appointment: " + appointment);
        } catch (SQLException e) {
            logger.error("Could not update the appointment: " + appointment + " due to error: " + e.getMessage());
            logger.error(e);
            throw new DatabaseException(e);
        }
        return result;
    }

    @Override
    public AppointmentTbl delete(long appointmentId) throws Exception {
        AppointmentTbl appointment = null;
        String sqlCommand = "DELETE FROM appointment_tbl WHERE AppointmentId = ?";
        logger.info("SQL Command to be Executed: " + sqlCommand);
        logger.info("Appointment to be deleted with ID: " + appointmentId);

        try {
            appointment = findById(appointmentId);
            try (PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
                ps.setLong(1, appointmentId);

                ps.executeUpdate();
                logger.info("Successfully deleted the appointment: " + appointment);
            } catch (SQLException e) {
                logger.error("Could not delete the appointment: " + appointmentId + " due to error: " + e.getMessage());
                logger.error(e);
                throw new DatabaseException(e);
            }
        } catch (Exception e) {
            throw new DatabaseException(e);
        }

        return appointment;
    }

    @Override
    public List<AppointmentTbl> findAll() throws Exception {
        String sqlCommand = "SELECT * FROM appointment_tbl";
        List<AppointmentTbl> appointments = new ArrayList<AppointmentTbl>();
        logger.info("SQL Command to be Executed: " + sqlCommand);

        try (PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long appointmentId = rs.getLong("AppointmentId");
                long patientId = rs.getLong("PatientId");
                long scheduleId = rs.getLong("ScheduleId");
                AuthUserTbl PatientUser = null;
                PatientTbl patient;
                ScheduleTbl schedule;
                try {
                    patient = patientDao.findById(patientId);
                } catch (Exception e) {
                    throw new DatabaseException("Patient not present with id: " + patientId, e);
                }
                try {
                    schedule = scheduleDao.findById(scheduleId);
                } catch (Exception e) {
                    throw new DatabaseException("Schedule not present with id: " + scheduleId, e);
                }
                String suggestedTests = rs.getString("suggestedTests");
                String suggestedMedicines = rs.getString("suggestedMedicines");
                String sufferingFromDisease = rs.getString("sufferingFromDisease");
                int appointmentStatus = rs.getInt("appointmentStatus");

                AppointmentTbl appointment = new AppointmentTbl(appointmentId, patient, schedule, suggestedTests, suggestedMedicines, sufferingFromDisease, appointmentStatus);

                logger.info("Appointment fetched: " + appointment);
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            logger.error("Could not find all the appointments from the database: " + e.getMessage());
            logger.error(e);
            throw new DatabaseException(e);
        }
        return appointments;
    }

    @Override
    public AppointmentTbl findById(long appointmentId) throws Exception {
        AppointmentTbl appointment = null;
        String sqlCommand = "SELECT * FROM appointment_tbl WHERE AppointmentId = ?";
        logger.info("SQL Command to be Executed: " + sqlCommand);
        logger.info("Appointment to be fetched with ID: " + appointmentId);

        try (PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
            ps.setLong(1, appointmentId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                long patientId = rs.getLong("PatientId");
                long scheduleId = rs.getLong("ScheduleId");
                PatientTbl patient;
                ScheduleTbl schedule;
                try {
                    patient = patientDao.findById(patientId);
                } catch (Exception e) {
                    throw new DatabaseException("Patient not present with id: " + patientId, e);
                }
                try {
                    schedule = scheduleDao.findById(scheduleId);
                } catch (Exception e) {
                    throw new DatabaseException("Schedule not present with id: " + scheduleId, e);
                }
                String suggestedTests = rs.getString("suggestedTests");
                String suggestedMedicines = rs.getString("suggestedMedicines");
                String sufferingFromDisease = rs.getString("sufferingFromDisease");
                int appointmentStatus = rs.getInt("appointmentStatus");

                appointment = new AppointmentTbl(appointmentId, patient, schedule, suggestedTests, suggestedMedicines, sufferingFromDisease, appointmentStatus);

                logger.info("Appointment fetched: " + appointment);
            }
        } catch (SQLException e) {
            logger.error("Could not find the appointment from the database with ID: " + appointmentId + " due to error: " + e.getMessage());
            logger.error(e);
            throw new DatabaseException(e);
        }

        return appointment;
    }
}

package com.hospital.dao;

import com.hospital.entity.PatientTbl;
import com.hospital.entity.AuthUserTbl;
import com.hospital.exception.DatabaseException;
import com.hospital.interfaces.PatientDao;
import com.hospital.interfaces.AuthUserDao;
import org.apache.log4j.Logger;

import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class PatientDBDao implements PatientDao {
    private Connection conn;
    private final Logger logger = Logger.getLogger(PatientDBDao.class);

    private final AuthUserDao authUserDao;

    public PatientDBDao(Connection conn, AuthUserDao authUserDao) {
        logger.info("Database connection: " + conn);
        this.conn = conn;
        this.authUserDao = authUserDao;
    }

    public long getMaxId() throws DatabaseException {
        long max = 0;
        String sqlCommand = "SELECT MAX(PatientId) as maxPatientId FROM patient_tbl";
        logger.info("SQL Command to be executed: " + sqlCommand);

        try (PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            if (rs.next()) {
                max = rs.getLong("maxPatientId");
            }
        } catch (SQLException e) {
            logger.error("SQL Error: " + e.getMessage());
            logger.error(e);
            throw new DatabaseException(e);
        }

        return max;
    }


    @Override
    public boolean save(PatientTbl patient) throws DatabaseException {
        boolean result = false;
        String sqlCommand = "INSERT INTO patient_tbl VALUES (?, ?, ?, ?, ?)";
        try {
            logger.info("SQL Command to be Executed: " + sqlCommand);
            patient.setPatientId(getMaxId()+1);
            logger.info("Patient to be saved: " + patient);

            try (PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
                ps.setLong(1, patient.getPatientId());
                ps.setString(2, patient.getName());
                ps.setString(3, patient.getEmail());
                ps.setString(4, patient.getMobileNo());
                ps.setLong(5, patient.getAddedByUser().getUserId());

                logger.info("SQL Command to be Executed: " + ps);

                ps.executeUpdate();
                result = true;
                logger.info("Successfully saved patient : " + patient);
            } catch (SQLException e) {
                logger.error("Could not save patient: " + patient + " due to error: " + e.getMessage());
                logger.error(e);
                throw new DatabaseException(e);
            }
        } catch (DatabaseException e) {
            logger.error("Could not save the schedule: " + patient + " due to error: " + e.getMessage());
            logger.error(e);
            throw e;
        }

        return result;
    }

    @Override
    public boolean update(PatientTbl patient) throws DatabaseException {
        boolean result = false;
        String sqlCommand = "UPDATE patient_tbl SET name=?, email=?, mobileNo=?, addedByUser=? WHERE PatientId=? ";
        logger.info("SQL Command to be Executed: " + sqlCommand);
        logger.info("Patient to be saved: " + patient);

        try (PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
            ps.setLong(5, patient.getPatientId());
            ps.setString(1, patient.getName());
            ps.setString(2, patient.getEmail());
            ps.setString(3, patient.getMobileNo());
            ps.setLong(4, patient.getAddedByUser().getUserId());

            logger.info("SQL Command to be Executed: " + ps);

            ps.executeUpdate();
            result = true;
            logger.info("Successfully updated patient : " + patient);
        } catch (SQLException e) {
            logger.error("Could not update patient: " + patient + " due to error: " + e.getMessage());
            logger.error(e);
            throw new DatabaseException(e);
        }

        return result;
    }

    @Override
    public PatientTbl delete(long PatientId) throws DatabaseException {
        PatientTbl patient = null;
        String sqlCommand = "DELETE FROM patient_tbl WHERE PatientId = ?";
        logger.info("SQL Command to be Executed: " + sqlCommand);
        logger.info("Schedule to be deleted with ID: " + PatientId);

        try {
            patient = findById(PatientId);
            try (PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
                ps.setLong(1, PatientId);

                ps.executeUpdate();
                logger.info("Successfully deleted the patient: " + patient);
            } catch (SQLException e) {
                logger.error("Could not delete the patient: " + PatientId + " due to error: " + e.getMessage());
                logger.error(e);
                throw new DatabaseException(e);
            }
        } catch (Exception e) {
            throw new DatabaseException(e);
        }

        return patient;
    }

    @Override
    public List<PatientTbl> findAll() throws DatabaseException {
        String sqlCommand = "SELECT * FROM patient_tbl";
        logger.info("SQL Command to be Executed: " + sqlCommand);
        List<PatientTbl> patients = new ArrayList<PatientTbl>();

        try (PreparedStatement ps = conn.prepareStatement(sqlCommand)) {


            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long patientId = rs.getLong("PatientId");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String mobileNo = rs.getString("mobileNo");
                long addedByUserId = rs.getLong("addedByUser");
                AuthUserTbl addedByUser = null;
                try {
                    addedByUser = authUserDao.findById(addedByUserId);
                } catch (Exception e) {
                    throw new DatabaseException("User not present with id: " + addedByUserId, e);
                }


                PatientTbl patient = new PatientTbl(patientId, name, email, mobileNo, addedByUser);

                logger.info("Patient fetched: " + patient);
                patients.add(patient);
            }
        } catch (SQLException e) {
            logger.error("Could not find all the patients from the database due to error: " + e.getMessage());
            logger.error(e);
            throw new DatabaseException(e);
        }
        return patients;

    }

    @Override
    public PatientTbl findById(long PatientId) throws DatabaseException {
        PatientTbl patient = null;
        String sqlCommand = "SELECT * FROM patient_tbl WHERE PatientId = ?";
        logger.info("SQL Command to be Executed: " + sqlCommand);
        logger.info("Patient to be fetched with ID: " + PatientId);

        try (PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
            ps.setLong(1, PatientId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                long patientId = rs.getLong("PatientId");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String mobileNo = rs.getString("mobileNo");
                long addedByUserId = rs.getLong("addedByUser");
                AuthUserTbl addedByUser = null;
                try {
                    addedByUser = authUserDao.findById(addedByUserId);
                } catch (Exception e) {
                    throw new DatabaseException("User not present with id: " + addedByUserId, e);
                }


                patient = new PatientTbl(patientId, name, email, mobileNo, addedByUser);

                logger.info("Patient fetched: " + patient);
            }
        } catch (SQLException e) {
            logger.error("Could not find the patients from the database with ID: " + PatientId + " due to error: " + e.getMessage());
            logger.error(e);
            throw new DatabaseException(e);
        }

        return patient;
    }
}

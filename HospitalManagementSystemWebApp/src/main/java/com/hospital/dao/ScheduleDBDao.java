package com.hospital.dao;

import com.hospital.entity.AuthUserTbl;
import com.hospital.entity.ScheduleTbl;
import com.hospital.exception.DatabaseException;
import com.hospital.interfaces.AuthUserDao;
import com.hospital.interfaces.ScheduleDao;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ScheduleDBDao implements ScheduleDao {
    private Connection conn;
    private final Logger logger = Logger.getLogger(ScheduleDBDao.class);

    private AuthUserDao authUserDao;

    public ScheduleDBDao(Connection conn, AuthUserDao authUserDao) {
        logger.info("Database Connection " + conn);
        this.conn = conn;
        this.authUserDao = authUserDao;
    }

    private long getMaxId() throws DatabaseException {
        String sqlCommand = "SELECT MAX(ScheduleID) as maxScheduleId FROM schedule_tbl";

        try (PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            if (rs.next()) {
                return rs.getLong("maxScheduleId");
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

        return 0;
    }

    @Override
    public boolean save(ScheduleTbl schedule) throws DatabaseException {
        boolean result = false;
        String sqlCommand = "INSERT INTO schedule_tbl VALUES (?, ?, ?, ?, ?, ?, ?)";

        schedule.setScheduleId(getMaxId());
        logger.info("Schedule to be saved: "+ schedule);

        logger.info("SQL Command to be Executed: " + sqlCommand);
        try (PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
            ps.setLong(1, schedule.getScheduleId());
            ps.setLong(2, schedule.getDoctor().getUserId());
            ps.setDate(3, Date.valueOf(schedule.getScheduleDate()));
            ps.setTime(4, Time.valueOf(schedule.getStartTime()));
            ps.setTime(5, Time.valueOf(schedule.getEndTime()));
            ps.setInt(6, schedule.getScheduleStatus());
            ps.setString(7, schedule.getUnavailabilityReason());

            logger.info("SQL Command to be Executed: " + ps);

            ps.executeUpdate();
            result = true;
            logger.info("Successfully saved the schedule: " + schedule);
        } catch (SQLException e) {
            logger.error("Could not save the schedule: " + schedule + " due to error: " + e.getMessage());
            logger.error(e);
            throw new DatabaseException(e);
        }

        return result;
    }

    @Override
    public boolean update(ScheduleTbl schedule) throws DatabaseException {
        boolean result = false;
        String sqlCommand = "UPDATE schedule_tbl SET DoctorId = ?, scheduleDate = ?, StartTime = ?, EndTime = ?, scheduleStatus = ?, unavailableReason = ? WHERE ScheduleId = ?";
        logger.info("SQL Command to be Executed: " + sqlCommand);
        logger.info("Schedule to be Updated " + schedule);

        try(PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
            ps.setLong(1, schedule.getDoctor().getUserId());
            ps.setDate(2, Date.valueOf(schedule.getScheduleDate()));
            ps.setTime(3, Time.valueOf(schedule.getStartTime()));
            ps.setTime(4, Time.valueOf(schedule.getEndTime()));
            ps.setInt(5, schedule.getScheduleStatus());
            ps.setString(6, schedule.getUnavailabilityReason());
            ps.setLong(7, schedule.getScheduleId());

            ps.executeUpdate();
            result = true;
            logger.info("Successfully updated the schedule: " + schedule);
        } catch (SQLException e) {
            logger.error("Could not update the schedule: " + schedule + " due to error: " + e.getMessage());
            logger.error(e);
            throw new DatabaseException(e);
        }

        return result;
    }

    @Override
    public ScheduleTbl delete(long ScheduleId) throws DatabaseException {
        ScheduleTbl schedule = null;
        String sqlCommand = "DELETE FROM schedule_tbl WHERE ScheduleId = ?";
        logger.info("SQL Command to be Executed: " + sqlCommand);
        logger.info("Schedule to be deleted with ID: " + ScheduleId);

        try {
            schedule = findById(ScheduleId);
            try (PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
                ps.setLong(1, ScheduleId);

                ps.executeUpdate();
                logger.info("Successfully deleted the schedule: " + schedule);
            } catch (SQLException e) {
                logger.error("Could not delete the schedule: " + ScheduleId + " due to error: " + e.getMessage());
                logger.error(e);
                throw new DatabaseException(e);
            }
        } catch (Exception e) {
            throw new DatabaseException(e);
        }

        return schedule;
    }

    @Override
    public List<ScheduleTbl> findAll() throws DatabaseException {
        String sqlCommand = "SELECT * FROM schedule_tbl";
        List<ScheduleTbl> schedules = new ArrayList<ScheduleTbl>();
        logger.info("SQL Command to be Executed: " + sqlCommand);

        try (PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long ScheduleId = rs.getLong("ScheduleId");
                long DoctorId = rs.getLong("DoctorId");
                AuthUserTbl Doctor = null;
                try {
                    Doctor = authUserDao.findById(DoctorId);
                } catch (Exception e) {
                    throw new DatabaseException("Doctor not present with id: " + DoctorId, e);
                }
                LocalDate scheduleDate = rs.getDate("scheduleDate").toLocalDate();
                LocalTime startTime = rs.getTime("StartTime").toLocalTime();
                LocalTime endTime = rs.getTime("EndTime").toLocalTime();
                int scheduleStatus = rs.getInt("scheduleStatus");
                String unavailabilityReason = rs.getString("unavailabilityReason");

                ScheduleTbl schedule = new ScheduleTbl(ScheduleId, Doctor, scheduleDate, startTime, endTime, scheduleStatus, unavailabilityReason);

                logger.info("Schedule fetched: " + schedule);
                schedules.add(schedule);
            }
        } catch (SQLException e) {
            logger.error("Could not find all the schedules from the database: " + e.getMessage());
            logger.error(e);
            throw new DatabaseException(e);
        }

        return schedules;
    }

    @Override
    public ScheduleTbl findById(long ScheduleId) throws Exception {
        ScheduleTbl schedule = null;
        String sqlCommand = "SELECT * FROM schedule_tbl WHERE ScheduleId = ?";
        logger.info("SQL Command to be Executed: " + sqlCommand);
        logger.info("Schedule to be fetched with ID: " + ScheduleId);

        try (PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
            ps.setLong(1, ScheduleId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                long DoctorId = rs.getLong("DoctorId");
                AuthUserTbl Doctor = null;
                try {
                    Doctor = authUserDao.findById(DoctorId);
                } catch (Exception e) {
                    throw new DatabaseException("Doctor not present with id: " + DoctorId, e);
                }
                LocalDate scheduleDate = rs.getDate("scheduleDate").toLocalDate();
                LocalTime startTime = rs.getTime("StartTime").toLocalTime();
                LocalTime endTime = rs.getTime("EndTime").toLocalTime();
                int scheduleStatus = rs.getInt("scheduleStatus");
                String unavailabilityReason = rs.getString("unavailabilityReason");

                schedule = new ScheduleTbl(ScheduleId, Doctor, scheduleDate, startTime, endTime, scheduleStatus, unavailabilityReason);

                logger.info("Schedule fetched: " + schedule);
            }
        } catch (SQLException e) {
            logger.error("Could not find the schedules from the database with ID: " + ScheduleId + " due to error: " + e.getMessage());
            logger.error(e);
            throw new DatabaseException(e);
        }

        return schedule;
    }
}

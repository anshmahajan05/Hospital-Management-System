package com.hospital.interfaces;

import com.hospital.entity.ScheduleTbl;
import com.hospital.exception.DatabaseException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ScheduleDao {
    public long getMaxId() throws DatabaseException;
    public boolean save(ScheduleTbl schedule) throws DatabaseException;
    public boolean update(ScheduleTbl schedule) throws DatabaseException;
    public ScheduleTbl delete(long ScheduleId) throws DatabaseException;
    public List<ScheduleTbl> findAll() throws DatabaseException;
    public ScheduleTbl findById(long ScheduleId) throws DatabaseException;
    public ScheduleTbl findByDetails(LocalDate scheduleDate, LocalTime startTime, LocalTime endTime) throws DatabaseException;
    public List<ScheduleTbl> findByStartEndDate(LocalDate startDate, LocalDate endDate) throws DatabaseException;
}

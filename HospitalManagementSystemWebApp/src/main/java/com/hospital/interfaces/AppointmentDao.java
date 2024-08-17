package com.hospital.interfaces;

import com.hospital.entity.AppointmentTbl;
import com.hospital.exception.DatabaseException;

import java.util.List;

public interface AppointmentDao {
    public boolean save(AppointmentTbl appointment) throws DatabaseException;
    public boolean update(AppointmentTbl appointment) throws DatabaseException;
    public AppointmentTbl delete(long AppointmentId) throws DatabaseException;
    public List<AppointmentTbl> findAll() throws DatabaseException;
    public AppointmentTbl findById(long AppointmentId) throws DatabaseException;
    public long getMaxId() throws DatabaseException;
}

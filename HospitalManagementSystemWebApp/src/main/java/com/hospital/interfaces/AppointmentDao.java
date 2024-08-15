package com.hospital.interfaces;

import com.hospital.entity.AppointmentTbl;

import java.util.List;

public interface AppointmentDao {
    public boolean save(AppointmentTbl appointment) throws Exception;
    public boolean update(AppointmentTbl appointment) throws Exception;
    public AppointmentTbl delete(long AppointmentId) throws Exception;
    public List<AppointmentTbl> findAll() throws Exception;
    public AppointmentTbl findById(long AppointmentId) throws Exception;
}

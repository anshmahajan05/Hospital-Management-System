package com.hospital.dao;

import com.hospital.entity.AppointmentTbl;
import com.hospital.interfaces.AppointmentDao;

import java.util.List;

public class AppointmentDBDao implements AppointmentDao {
    @Override
    public boolean save(AppointmentTbl appointment) throws Exception {
        return false;
    }

    @Override
    public boolean update(AppointmentTbl appointment) throws Exception {
        return false;
    }

    @Override
    public AppointmentTbl delete(long AppointmentId) throws Exception {
        return null;
    }

    @Override
    public List<AppointmentTbl> findAll() throws Exception {
        return List.of();
    }

    @Override
    public AppointmentTbl findById(long AppointmentId) throws Exception {
        return null;
    }
}

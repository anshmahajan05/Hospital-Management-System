package com.hospital.dao;

import com.hospital.entity.ScheduleTbl;
import com.hospital.interfaces.ScheduleDao;

import java.util.List;

public class ScheduleDBDao implements ScheduleDao {
    @Override
    public boolean save(ScheduleTbl schedule) throws Exception {
        return false;
    }

    @Override
    public boolean update(ScheduleTbl schedule) throws Exception {
        return false;
    }

    @Override
    public ScheduleTbl delete(long ScheduleId) throws Exception {
        return null;
    }

    @Override
    public List<ScheduleTbl> findAll() throws Exception {
        return List.of();
    }

    @Override
    public ScheduleTbl findById(long ScheduleId) throws Exception {
        return null;
    }
}

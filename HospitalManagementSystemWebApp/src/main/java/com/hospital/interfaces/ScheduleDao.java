package com.hospital.interfaces;

import com.hospital.entity.ScheduleTbl;

import java.util.List;

public interface ScheduleDao {
    public boolean save(ScheduleTbl schedule) throws Exception;
    public boolean update(ScheduleTbl schedule) throws Exception;
    public ScheduleTbl delete(long ScheduleId) throws Exception;
    public List<ScheduleTbl> findAll() throws Exception;
    public ScheduleTbl findById(long ScheduleId) throws Exception;
}

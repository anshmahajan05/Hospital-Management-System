package com.hospital.dao;

import com.hospital.entity.PatientTbl;
import com.hospital.interfaces.PatientDao;

import java.util.List;

public class PatientDBDao implements PatientDao {
    @Override
    public boolean save(PatientTbl patient) throws Exception {
        return false;
    }

    @Override
    public boolean update(PatientTbl patient) throws Exception {
        return false;
    }

    @Override
    public PatientTbl delete(long PatientId) throws Exception {
        return null;
    }

    @Override
    public List<PatientTbl> findAll() throws Exception {
        return List.of();
    }

    @Override
    public PatientTbl findById(long PatientId) throws Exception {
        return null;
    }
}

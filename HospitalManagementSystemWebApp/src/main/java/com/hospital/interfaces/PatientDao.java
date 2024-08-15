package com.hospital.interfaces;

import com.hospital.entity.PatientTbl;

import java.util.List;

public interface PatientDao {
    public boolean save(PatientTbl patient) throws Exception;
    public boolean update(PatientTbl patient) throws Exception;
    public PatientTbl delete(long PatientId) throws Exception;
    public List<PatientTbl> findAll() throws Exception;
    public PatientTbl findById(long PatientId) throws Exception;
}

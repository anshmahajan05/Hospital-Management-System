package com.hospital.interfaces;

import com.hospital.entity.PatientTbl;
import com.hospital.exception.DatabaseException;

import java.util.List;

public interface PatientDao {
    public boolean save(PatientTbl patient) throws DatabaseException;
    public boolean update(PatientTbl patient) throws DatabaseException;
    public PatientTbl delete(long PatientId) throws DatabaseException;
    public List<PatientTbl> findAll() throws DatabaseException;
    public PatientTbl findById(long PatientId) throws DatabaseException;
    public long getMaxId() throws DatabaseException;
}

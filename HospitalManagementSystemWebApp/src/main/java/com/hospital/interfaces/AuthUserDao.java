package com.hospital.interfaces;

import com.hospital.entity.AuthUserTbl;
import com.hospital.exception.DatabaseException;

import java.util.List;

public interface AuthUserDao {

    boolean save (AuthUserTbl authUserTbl ) throws Exception;
    boolean update(AuthUserTbl authUserTbl ) throws Exception;
    AuthUserTbl delete (long id ) throws Exception;
    AuthUserTbl findById (long id ) throws Exception;
    List<AuthUserTbl> findAll () throws Exception;
    public long getMaxId() throws DatabaseException;

}
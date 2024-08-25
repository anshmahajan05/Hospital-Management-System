package com.hospital.interfaces;

import com.hospital.entity.AuthUserTbl;
import com.hospital.exception.DatabaseException;

import java.util.List;

public interface AuthUserDao {

    boolean save (AuthUserTbl authUserTbl ) throws DatabaseException;
    boolean update(AuthUserTbl authUserTbl ) throws DatabaseException;
    AuthUserTbl delete (long id ) throws DatabaseException;
    AuthUserTbl findById (long id ) throws DatabaseException;
    List<AuthUserTbl> findAll () throws DatabaseException;
    public long getMaxId() throws DatabaseException;
    public AuthUserTbl findByUsername (String username) throws DatabaseException;
    public AuthUserTbl authenticate(String username, String password) throws DatabaseException;
}
package com.hospital.interfaces;

import com.hospital.entity.AuthUserTbl;

import java.util.List;

public interface AuthUserDao {
    public boolean save(AuthUserTbl authUser) throws Exception;
    public boolean update(AuthUserTbl authUser) throws Exception;
    public AuthUserTbl delete(long UserId) throws Exception;
    public List<AuthUserTbl> findAll() throws Exception;
    public AuthUserTbl findById(long UserId) throws Exception;
}

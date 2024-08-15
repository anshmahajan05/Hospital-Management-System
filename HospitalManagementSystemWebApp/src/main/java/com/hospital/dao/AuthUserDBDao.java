package com.hospital.dao;

import com.hospital.entity.AuthUserTbl;
import com.hospital.interfaces.AuthUserDao;

import java.util.List;

public class AuthUserDBDao implements AuthUserDao {
    @Override
    public boolean save(AuthUserTbl authUser) throws Exception {
        return false;
    }

    @Override
    public boolean update(AuthUserTbl authUser) throws Exception {
        return false;
    }

    @Override
    public AuthUserTbl delete(long UserId) throws Exception {
        return null;
    }

    @Override
    public List<AuthUserTbl> findAll() throws Exception {
        return List.of();
    }

    @Override
    public AuthUserTbl findById(long UserId) throws Exception {
        return null;
    }
}

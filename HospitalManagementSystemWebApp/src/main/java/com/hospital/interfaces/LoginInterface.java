package com.hospital.interfaces;

import com.hospital.entity.AuthUserTbl;
import com.hospital.exception.ServiceException;

public interface LoginInterface {
    public AuthUserTbl login(String username, String password) throws ServiceException;
}

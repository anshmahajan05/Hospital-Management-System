package com.hospital.entity;

import com.hospital.hashing.Hash;

import java.util.Objects;

// table name: auth_user_tbl
public class AuthUserTbl {
    private long UserId;
    private String name;
    private String email;
    private String mobileNo;
    private String username;
    private String password;
    private String role;

    public AuthUserTbl(long userId, String name, String email, String mobileNo, String username, String password, String role) {
        UserId = userId;
        this.name = name;
        this.email = email;
        this.mobileNo = mobileNo;
        this.username = username;
        setPassword(password);
        this.role = role;
    }

    public AuthUserTbl(String name, String email, String mobileNo, String username, String password, String role) {
        this.name = name;
        this.email = email;
        this.mobileNo = mobileNo;
        this.username = username;
        setPassword(password);
        this.role = role;
    }

    public AuthUserTbl(long userId, String name, String email, String mobileNo, String role) {
        UserId = userId;
        this.name = name;
        this.email = email;
        this.mobileNo = mobileNo;
        this.role = role;
    }

    public AuthUserTbl(String username, String password, long userId) {
        this.username = username;
        setPassword(password);
        UserId = userId;
    }

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Hash.hashPassword(password);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "AuthUserTbl{" +
                "UserId=" + UserId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthUserTbl)) return false;
        AuthUserTbl that = (AuthUserTbl) o;
        return UserId == that.UserId && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(mobileNo, that.mobileNo) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(UserId, name, email, mobileNo, username, password, role);
    }
}

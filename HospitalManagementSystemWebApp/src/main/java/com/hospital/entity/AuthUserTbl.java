package com.hospital.entity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
        this.password = hashPassword(password);
    }

    private String hashPassword(String password) {
        try {
            // Create a SHA-256 MessageDigest instance
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Perform the hash computation
            byte[] hashedBytes = digest.digest(password.getBytes());

            // Convert the byte array into a hex string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
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
}

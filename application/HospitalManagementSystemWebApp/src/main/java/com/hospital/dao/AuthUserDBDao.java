package com.hospital.dao;

import com.hospital.entity.AuthUserTbl;
import com.hospital.interfaces.AuthUserDao;
import java.util.List;
import java.sql.*;
import com.hospital.exception.DatabaseException;
import org.apache.log4j.Logger;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import static com.hospital.hashing.Hash.hashPassword;

public class AuthUserDBDao implements AuthUserDao {

    private Connection conn;
    private final Logger logger = Logger.getLogger(AuthUserDBDao.class);

    public AuthUserDBDao(Connection conn) {
        logger.info("Database Connection " + conn);
        this.conn = conn;
    }

    @Override
    public long getMaxId() throws DatabaseException {
        long max = 0;
        String sqlCommand = "SELECT MAX(UserId) as maxUserId FROM auth_user_tbl";
        logger.info("SQL Command to be executed: " + sqlCommand);

        try (PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            if (rs.next()) {
                max = rs.getLong("maxUserId");
            }
        } catch (SQLException e) {
            logger.error("SQL Error: " + e.getMessage());
            logger.error(e);
            throw new DatabaseException(e);
        }

        return max;
    }

    @Override
    public boolean save(AuthUserTbl  authUserTbl) throws DatabaseException {
        boolean result = false;
        String sqlCommand = "INSERT INTO auth_user_tbl VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            logger.info("SQL Command to be Executed: " + sqlCommand);
            authUserTbl.setUserId(getMaxId() + 1);
            logger.info("User to be saved: "+ authUserTbl);

            try (PreparedStatement ps = conn.prepareStatement(sqlCommand)) {

                ps.setLong(1, authUserTbl.getUserId());
                ps.setString(2, authUserTbl.getName());
                ps.setString(3, authUserTbl.getEmail());
                ps.setString(4, authUserTbl.getMobileNo());
                ps.setString(5, authUserTbl.getUsername());
                ps.setString(6, authUserTbl.getPassword());
                ps.setString(7, authUserTbl.getRole());

                logger.info("SQL Command to be Executed: " + ps);

                ps.executeUpdate();
                result = true;
                logger.info("Successfully saved the user: " + authUserTbl);
            } catch (SQLException e) {
                logger.error("Could not save the user: " + authUserTbl + " due to error: " + e.getMessage());
                logger.error(e);
                throw new DatabaseException(e);
            }
        } catch (DatabaseException e) {
            logger.error("Could not save the user: " + authUserTbl + " due to error: " + e.getMessage());
            logger.error(e);
            throw e;
        }

        return result;
    }

    @Override
    public boolean update(AuthUserTbl authUserTbl) throws DatabaseException {
        boolean result = false;
        String sqlCommand = "UPDATE auth_user_tbl SET name=?, email=?, mobileNo=?, role=? WHERE UserId=? ";
        logger.info("SQL Command to be Executed: " + sqlCommand);
        logger.info("User to be Updated " + authUserTbl);

        try(PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
            ps.setLong(5, authUserTbl.getUserId());
            ps.setString(1, authUserTbl.getName());
            ps.setString(2, authUserTbl.getEmail());
            ps.setString(3, authUserTbl.getMobileNo());
            ps.setString(4, authUserTbl.getRole());

            ps.executeUpdate();
            result = true;
            logger.info("Successfully updated the user: " + authUserTbl);
        } catch (SQLException e) {
            logger.error("Could not update the user: " + authUserTbl + " due to error: " + e.getMessage());
            logger.error(e);
            throw new DatabaseException(e);
        }

        return result;
    }

    @Override
    public AuthUserTbl delete(long UserId) throws DatabaseException {
        AuthUserTbl authUserTbl = null;
        String sqlCommand = "DELETE FROM auth_user_tbl WHERE UserId = ?";
        logger.info("SQL Command to be Executed: " + sqlCommand);
        logger.info("User to be deleted with ID: " + UserId);

        try {
            authUserTbl = findById(UserId);
            try (PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
                ps.setLong(1, UserId);

                ps.executeUpdate();
                logger.info("Successfully deleted the user: " + authUserTbl);
            } catch (SQLException e) {
                logger.error("Could not delete the user: " + UserId + " due to error: " + e.getMessage());
                logger.error(e);
                throw new DatabaseException(e);
            }
        } catch (DatabaseException e) {
            logger.error("Could not delete the user: " + UserId + " due to error: " + e.getMessage());
            logger.error(e);
            throw new DatabaseException(e);
        }

        return authUserTbl;
    }

    @Override
    public List<AuthUserTbl> findAll() throws DatabaseException {
        String sqlCommand = "SELECT * FROM auth_user_tbl";
        List<AuthUserTbl> authUserTbls= new ArrayList<AuthUserTbl>();
        logger.info("SQL Command to be Executed: " + sqlCommand);

        try (PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long userId = rs.getLong("UserId");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String mobileNo = rs.getString("mobileNo");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String role = rs.getString("role");

                AuthUserTbl authUserTbl = new AuthUserTbl(userId, name, email, mobileNo, username, password, role);

                logger.info("User fetched: " + authUserTbl);
                authUserTbls.add(authUserTbl);
            }
        } catch (SQLException e) {
            logger.error("Could not find all the users from the database due to error: " + e.getMessage());
            logger.error(e);
            throw new DatabaseException(e);
        }
        return authUserTbls;
    }

    @Override
    public AuthUserTbl findById(long UserId) throws DatabaseException {
        AuthUserTbl authUserTbl = null;
        String sqlCommand = "SELECT * FROM auth_user_tbl WHERE UserId = ?";
        logger.info("SQL Command to be Executed: " + sqlCommand);
        logger.info("User to be fetched with ID: " + UserId);

        try (PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
            ps.setLong(1, UserId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                long userId = rs.getLong("UserId");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String mobileNo = rs.getString("mobileNo");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String role = rs.getString("role");

                authUserTbl = new AuthUserTbl(UserId, name, email, mobileNo, username, password, role);

                logger.info("User fetched: " + userId);
            }
        } catch (SQLException e) {
            logger.error("Could not find the user from the database with ID: " + UserId + " due to error: " + e.getMessage());
            logger.error(e);
            throw new DatabaseException(e);
        }

        return authUserTbl;
    }

    @Override
    public AuthUserTbl findByUsername(String username) throws DatabaseException {
        AuthUserTbl authUserTbl = null;
        String sqlCommand = "SELECT * FROM auth_user_tbl WHERE username = ?";
        logger.info("SQL Command to be Executed: " + sqlCommand);
        logger.info("User to be fetched with username: " + username);

        try (PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                long userId = rs.getLong("UserId");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String mobileNo = rs.getString("mobileNo");
                String userName = rs.getString("username");
                String password = rs.getString("password");
                String role = rs.getString("role");

                authUserTbl = new AuthUserTbl(userId, name, email, mobileNo, userName, password, role);

                logger.info("User fetched: " + userId);
            }
        } catch (SQLException e) {
            logger.error("Could not find the user from the database with username: " + username + " due to error: " + e.getMessage());
            logger.error(e);
            throw new DatabaseException(e);
        }

        return authUserTbl;
    }

    // while doing this ensure that u use hash the password first with function in com.Hospital.hashing package
    // and verify the hash password with database
    // for example see setPassword of AuthUserTbl
    @Override
    public AuthUserTbl authenticate(String username, String password) throws DatabaseException {
        AuthUserTbl authuser = findByUsername(username);
        if (authuser == null) {
            throw new DatabaseException("Invalid Username or Password");
        }
        String hashCode = hashPassword(password);
        if (!authuser.getPassword().equals(hashCode)) {
            throw new DatabaseException("Invalid username or Password");
        }
        return authuser;
    }

}

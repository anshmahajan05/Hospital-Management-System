package com.hospital.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hospital.dao.AppointmentDBDao;
import com.hospital.dao.AuthUserDBDao;
import com.hospital.dao.PatientDBDao;
import com.hospital.dao.ScheduleDBDao;
import com.hospital.entity.AppointmentTbl;
import com.hospital.entity.AuthUserTbl;
import com.hospital.entity.PatientTbl;
import com.hospital.entity.ScheduleTbl;
import com.hospital.exception.DatabaseException;
import com.hospital.exception.ServiceException;
import com.hospital.interfaces.AppointmentDao;
import com.hospital.interfaces.AuthUserDao;
import com.hospital.interfaces.PatientDao;
import com.hospital.interfaces.ScheduleDao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ServiceImplTest {
    /**
     * Method under test: {@link ServiceImpl#getAllAuthUser(String)}
     */
    @Test
    void testGetAllAuthUser() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        AuthUserDBDao authUserDao = new AuthUserDBDao(conn);
        Connection conn2 = mock(Connection.class);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(mock(Connection.class)));

        Connection conn3 = mock(Connection.class);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn3, new AuthUserDBDao(mock(Connection.class)));

        Connection conn4 = mock(Connection.class);
        Connection conn5 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn5, new AuthUserDBDao(mock(Connection.class)));

        Connection conn6 = mock(Connection.class);

        // Act
        List<AuthUserTbl> actualAllAuthUser = (new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn4, patientDao2, new ScheduleDBDao(conn6, new AuthUserDBDao(mock(Connection.class))))))
                .getAllAuthUser("Role");

        // Assert
        verify(conn).prepareStatement(eq("SELECT * FROM auth_user_tbl"));
        verify(preparedStatement).executeQuery();
        verify(resultSet, atLeast(1)).getLong(eq("UserId"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement).close();
        assertTrue(actualAllAuthUser.isEmpty());
    }

    /**
     * Method under test: {@link ServiceImpl#getAllAuthUser(String)}
     */
    @Test
    void testGetAllAuthUser2() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("role");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        AuthUserDBDao authUserDao = new AuthUserDBDao(conn);
        Connection conn2 = mock(Connection.class);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(mock(Connection.class)));

        Connection conn3 = mock(Connection.class);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn3, new AuthUserDBDao(mock(Connection.class)));

        Connection conn4 = mock(Connection.class);
        Connection conn5 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn5, new AuthUserDBDao(mock(Connection.class)));

        Connection conn6 = mock(Connection.class);

        // Act
        List<AuthUserTbl> actualAllAuthUser = (new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn4, patientDao2, new ScheduleDBDao(conn6, new AuthUserDBDao(mock(Connection.class))))))
                .getAllAuthUser("Role");

        // Assert
        verify(conn).prepareStatement(eq("SELECT * FROM auth_user_tbl"));
        verify(preparedStatement).executeQuery();
        verify(resultSet, atLeast(1)).getLong(eq("UserId"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement).close();
        assertEquals(2, actualAllAuthUser.size());
        AuthUserTbl getResult = actualAllAuthUser.get(0);
        assertEquals("4b168d88dc872a7753c2bc35b36a2d4249487af55baf78f247f38cae2fe962da", getResult.getPassword());
        assertEquals("role", getResult.getEmail());
        assertEquals("role", getResult.getMobileNo());
        assertEquals("role", getResult.getName());
        assertEquals("role", getResult.getRole());
        assertEquals("role", getResult.getUsername());
        assertEquals(1L, getResult.getUserId());
        assertEquals(getResult, actualAllAuthUser.get(1));
    }

    /**
     * Method under test: {@link ServiceImpl#getAllAuthUser(String)}
     */
    @Test
    void testGetAllAuthUser3() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(false).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        AuthUserDBDao authUserDao = new AuthUserDBDao(conn);
        Connection conn2 = mock(Connection.class);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(mock(Connection.class)));

        Connection conn3 = mock(Connection.class);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn3, new AuthUserDBDao(mock(Connection.class)));

        Connection conn4 = mock(Connection.class);
        Connection conn5 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn5, new AuthUserDBDao(mock(Connection.class)));

        Connection conn6 = mock(Connection.class);

        // Act
        List<AuthUserTbl> actualAllAuthUser = (new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn4, patientDao2, new ScheduleDBDao(conn6, new AuthUserDBDao(mock(Connection.class))))))
                .getAllAuthUser("Role");

        // Assert
        verify(conn).prepareStatement(eq("SELECT * FROM auth_user_tbl"));
        verify(preparedStatement).executeQuery();
        verify(resultSet).next();
        verify(preparedStatement).close();
        assertTrue(actualAllAuthUser.isEmpty());
    }

    /**
     * Method under test: {@link ServiceImpl#getAllPatient()}
     */
    @Test
    void testGetAllPatient() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        PatientDBDao patientDao = new PatientDBDao(conn, new AuthUserDBDao(conn2));

        AuthUserDBDao authUserDao = new AuthUserDBDao(mock(Connection.class));
        Connection conn3 = mock(Connection.class);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn3, new AuthUserDBDao(mock(Connection.class)));

        Connection conn4 = mock(Connection.class);
        Connection conn5 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn5, new AuthUserDBDao(mock(Connection.class)));

        Connection conn6 = mock(Connection.class);

        // Act
        List<PatientTbl> actualAllPatient = (new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn4, patientDao2, new ScheduleDBDao(conn6, new AuthUserDBDao(mock(Connection.class))))))
                .getAllPatient();

        // Assert
        verify(conn2, atLeast(1)).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn).prepareStatement(eq("SELECT * FROM patient_tbl"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement2, atLeast(1)).executeQuery();
        verify(preparedStatement2, atLeast(1)).setLong(eq(1), eq(1L));
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).getLong(eq("UserId"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).next();
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement).close();
        verify(preparedStatement2, atLeast(1)).close();
        assertEquals(2, actualAllPatient.size());
        PatientTbl getResult = actualAllPatient.get(0);
        AuthUserTbl addedByUser = getResult.getAddedByUser();
        assertEquals("String", addedByUser.getEmail());
        assertEquals("String", addedByUser.getMobileNo());
        assertEquals("String", addedByUser.getName());
        assertEquals("String", addedByUser.getRole());
        assertEquals("String", addedByUser.getUsername());
        assertEquals("String", getResult.getEmail());
        PatientTbl getResult2 = actualAllPatient.get(1);
        assertEquals("String", getResult2.getEmail());
        assertEquals("String", getResult.getMobileNo());
        assertEquals("String", getResult2.getMobileNo());
        assertEquals("String", getResult.getName());
        assertEquals("String", getResult2.getName());
        assertEquals("b2ef230e7f4f315a28cdcc863028da31f7110f3209feb76e76fed0f37b3d8580", addedByUser.getPassword());
        assertEquals(1L, addedByUser.getUserId());
        assertEquals(1L, getResult.getPatientId());
        assertEquals(1L, getResult2.getPatientId());
        assertEquals(addedByUser, getResult2.getAddedByUser());
    }

    /**
     * Method under test: {@link ServiceImpl#getAllPatient()}
     */
    @Test
    void testGetAllPatient2() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(false).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        PatientDBDao patientDao = new PatientDBDao(conn, new AuthUserDBDao(conn2));

        AuthUserDBDao authUserDao = new AuthUserDBDao(mock(Connection.class));
        Connection conn3 = mock(Connection.class);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn3, new AuthUserDBDao(mock(Connection.class)));

        Connection conn4 = mock(Connection.class);
        Connection conn5 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn5, new AuthUserDBDao(mock(Connection.class)));

        Connection conn6 = mock(Connection.class);

        // Act
        List<PatientTbl> actualAllPatient = (new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn4, patientDao2, new ScheduleDBDao(conn6, new AuthUserDBDao(mock(Connection.class))))))
                .getAllPatient();

        // Assert
        verify(conn).prepareStatement(eq("SELECT * FROM patient_tbl"));
        verify(preparedStatement).executeQuery();
        verify(resultSet).next();
        verify(preparedStatement).close();
        assertTrue(actualAllPatient.isEmpty());
    }

    /**
     * Method under test: {@link ServiceImpl#addAuthUser(AuthUserTbl)}
     */
    @Test
    void testAddAuthUser() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        AuthUserDBDao authUserDao = new AuthUserDBDao(conn);
        Connection conn2 = mock(Connection.class);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(mock(Connection.class)));

        Connection conn3 = mock(Connection.class);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn3, new AuthUserDBDao(mock(Connection.class)));

        Connection conn4 = mock(Connection.class);
        Connection conn5 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn5, new AuthUserDBDao(mock(Connection.class)));

        Connection conn6 = mock(Connection.class);
        ServiceImpl serviceImpl = new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn4, patientDao2, new ScheduleDBDao(conn6, new AuthUserDBDao(mock(Connection.class)))));

        // Act and Assert
        assertThrows(ServiceException.class, () -> serviceImpl.addAuthUser(new AuthUserTbl("janedoe", "iloveyou", 1L)));
        verify(conn).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE username = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setString(eq(1), eq("janedoe"));
        verify(resultSet).getLong(eq("UserId"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link ServiceImpl#addAuthUser(AuthUserTbl)}
     */
    @Test
    void testAddAuthUser2() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(false).thenReturn(true).thenReturn(false);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        when(preparedStatement.getResultSet()).thenReturn(resultSet2);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        AuthUserDBDao authUserDao = new AuthUserDBDao(conn);
        Connection conn2 = mock(Connection.class);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(mock(Connection.class)));

        Connection conn3 = mock(Connection.class);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn3, new AuthUserDBDao(mock(Connection.class)));

        Connection conn4 = mock(Connection.class);
        Connection conn5 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn5, new AuthUserDBDao(mock(Connection.class)));

        Connection conn6 = mock(Connection.class);
        ServiceImpl serviceImpl = new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn4, patientDao2, new ScheduleDBDao(conn6, new AuthUserDBDao(mock(Connection.class)))));
        AuthUserTbl authUser = new AuthUserTbl("janedoe", "iloveyou", 1L);

        // Act
        boolean actualAddAuthUserResult = serviceImpl.addAuthUser(authUser);

        // Assert
        verify(conn, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).setLong(eq(1), eq(2L));
        verify(preparedStatement, atLeast(1)).setString(anyInt(), Mockito.<String>any());
        verify(resultSet2).getLong(eq("maxUserId"));
        verify(resultSet2).next();
        verify(resultSet).next();
        verify(preparedStatement, atLeast(1)).close();
        verify(preparedStatement).getResultSet();
        assertEquals(2L, authUser.getUserId());
        assertTrue(actualAddAuthUserResult);
    }

    /**
     * Method under test: {@link ServiceImpl#updateAuthUser(AuthUserTbl)}
     */
    @Test
    void testUpdateAuthUser() throws ServiceException, SQLException {
        // Arrange
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        AuthUserDBDao authUserDao = new AuthUserDBDao(conn);
        Connection conn2 = mock(Connection.class);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(mock(Connection.class)));

        Connection conn3 = mock(Connection.class);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn3, new AuthUserDBDao(mock(Connection.class)));

        Connection conn4 = mock(Connection.class);
        Connection conn5 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn5, new AuthUserDBDao(mock(Connection.class)));

        Connection conn6 = mock(Connection.class);
        ServiceImpl serviceImpl = new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn4, patientDao2, new ScheduleDBDao(conn6, new AuthUserDBDao(mock(Connection.class)))));

        // Act
        boolean actualUpdateAuthUserResult = serviceImpl.updateAuthUser(new AuthUserTbl("janedoe", "iloveyou", 1L));

        // Assert
        verify(conn).prepareStatement(eq("UPDATE auth_user_tbl SET name=?, email=?, mobileNo=?, role=? WHERE UserId=? "));
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).setLong(eq(5), eq(1L));
        verify(preparedStatement, atLeast(1)).setString(anyInt(), isNull());
        verify(preparedStatement).close();
        assertTrue(actualUpdateAuthUserResult);
    }

    /**
     * Method under test: {@link ServiceImpl#deleteAuthUser(long)}
     */
    @Test
    void testDeleteAuthUser() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        AuthUserDBDao authUserDao = new AuthUserDBDao(conn);
        Connection conn2 = mock(Connection.class);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(mock(Connection.class)));

        Connection conn3 = mock(Connection.class);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn3, new AuthUserDBDao(mock(Connection.class)));

        Connection conn4 = mock(Connection.class);
        Connection conn5 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn5, new AuthUserDBDao(mock(Connection.class)));

        Connection conn6 = mock(Connection.class);

        // Act
        AuthUserTbl actualDeleteAuthUserResult = (new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn4, patientDao2, new ScheduleDBDao(conn6, new AuthUserDBDao(mock(Connection.class))))))
                .deleteAuthUser(1L);

        // Assert
        verify(conn, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement, atLeast(1)).setLong(eq(1), eq(1L));
        verify(resultSet, atLeast(1)).getLong(eq("UserId"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement, atLeast(1)).close();
        assertEquals("String", actualDeleteAuthUserResult.getEmail());
        assertEquals("String", actualDeleteAuthUserResult.getMobileNo());
        assertEquals("String", actualDeleteAuthUserResult.getName());
        assertEquals("String", actualDeleteAuthUserResult.getRole());
        assertEquals("String", actualDeleteAuthUserResult.getUsername());
        assertEquals("b2ef230e7f4f315a28cdcc863028da31f7110f3209feb76e76fed0f37b3d8580",
                actualDeleteAuthUserResult.getPassword());
        assertEquals(1L, actualDeleteAuthUserResult.getUserId());
    }

    /**
     * Method under test: {@link ServiceImpl#deleteAuthUser(long)}
     */
    @Test
    void testDeleteAuthUser2() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(false).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        AuthUserDBDao authUserDao = new AuthUserDBDao(conn);
        Connection conn2 = mock(Connection.class);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(mock(Connection.class)));

        Connection conn3 = mock(Connection.class);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn3, new AuthUserDBDao(mock(Connection.class)));

        Connection conn4 = mock(Connection.class);
        Connection conn5 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn5, new AuthUserDBDao(mock(Connection.class)));

        Connection conn6 = mock(Connection.class);

        // Act and Assert
        assertThrows(ServiceException.class, () -> (new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn4, patientDao2, new ScheduleDBDao(conn6, new AuthUserDBDao(mock(Connection.class))))))
                .deleteAuthUser(1L));
        verify(conn).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link ServiceImpl#deleteAuthUser(long)}
     */
    @Test
    void testDeleteAuthUser3() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(false).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        AuthUserDBDao authUserDao = new AuthUserDBDao(conn);
        Connection conn2 = mock(Connection.class);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(mock(Connection.class)));

        Connection conn3 = mock(Connection.class);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn3, new AuthUserDBDao(mock(Connection.class)));

        Connection conn4 = mock(Connection.class);
        Connection conn5 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn5, new AuthUserDBDao(mock(Connection.class)));

        Connection conn6 = mock(Connection.class);

        // Act
        AuthUserTbl actualDeleteAuthUserResult = (new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn4, patientDao2, new ScheduleDBDao(conn6, new AuthUserDBDao(mock(Connection.class))))))
                .deleteAuthUser(1L);

        // Assert
        verify(conn, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement, atLeast(1)).setLong(eq(1), eq(1L));
        verify(resultSet).getLong(eq("UserId"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement, atLeast(1)).close();
        assertNull(actualDeleteAuthUserResult);
    }

    /**
     * Method under test: {@link ServiceImpl#addSchedule(ScheduleTbl)}
     */
    @Test
    void testAddSchedule() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        AuthUserDBDao authUserDao = new AuthUserDBDao(conn);
        Connection conn2 = mock(Connection.class);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(mock(Connection.class)));

        Connection conn3 = mock(Connection.class);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn3, new AuthUserDBDao(mock(Connection.class)));

        Connection conn4 = mock(Connection.class);
        Connection conn5 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn5, new AuthUserDBDao(mock(Connection.class)));

        Connection conn6 = mock(Connection.class);
        ServiceImpl serviceImpl = new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn4, patientDao2, new ScheduleDBDao(conn6, new AuthUserDBDao(mock(Connection.class)))));
        AuthUserTbl doctor = new AuthUserTbl("janedoe", "iloveyou", 1L);

        // Act and Assert
        assertThrows(ServiceException.class, () -> serviceImpl
                .addSchedule(new ScheduleTbl(doctor, LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, LocalTime.MIDNIGHT, 1)));
        verify(conn).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(resultSet).getLong(eq("UserId"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link ServiceImpl#addSchedule(ScheduleTbl)}
     */
    @Test
    void testAddSchedule2() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(false).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        AuthUserDBDao authUserDao = new AuthUserDBDao(conn);
        Connection conn2 = mock(Connection.class);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(mock(Connection.class)));

        Connection conn3 = mock(Connection.class);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn3, new AuthUserDBDao(mock(Connection.class)));

        Connection conn4 = mock(Connection.class);
        Connection conn5 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn5, new AuthUserDBDao(mock(Connection.class)));

        Connection conn6 = mock(Connection.class);
        ServiceImpl serviceImpl = new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn4, patientDao2, new ScheduleDBDao(conn6, new AuthUserDBDao(mock(Connection.class)))));
        AuthUserTbl doctor = new AuthUserTbl("janedoe", "iloveyou", 1L);

        // Act and Assert
        assertThrows(ServiceException.class, () -> serviceImpl
                .addSchedule(new ScheduleTbl(doctor, LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, LocalTime.MIDNIGHT, 1)));
        verify(conn).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link ServiceImpl#addSchedule(ScheduleTbl)}
     */
    @Test
    void testAddSchedule3() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("Doctor");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        AuthUserDBDao authUserDao = new AuthUserDBDao(conn);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet2.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setDate(anyInt(), Mockito.<Date>any());
        doNothing().when(preparedStatement2).setTime(anyInt(), Mockito.<Time>any());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        ResultSet resultSet3 = mock(ResultSet.class);
        when(resultSet3.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet3.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet3.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement3 = mock(PreparedStatement.class);
        when(preparedStatement3.executeQuery()).thenReturn(resultSet3);
        doNothing().when(preparedStatement3).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement3).close();
        Connection conn3 = mock(Connection.class);
        when(conn3.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement3);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn2, new AuthUserDBDao(conn3));

        Connection conn4 = mock(Connection.class);
        PatientDBDao patientDao = new PatientDBDao(conn4, new AuthUserDBDao(mock(Connection.class)));

        Connection conn5 = mock(Connection.class);
        Connection conn6 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn6, new AuthUserDBDao(mock(Connection.class)));

        Connection conn7 = mock(Connection.class);
        ServiceImpl serviceImpl = new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn5, patientDao2, new ScheduleDBDao(conn7, new AuthUserDBDao(mock(Connection.class)))));
        AuthUserTbl doctor = new AuthUserTbl("janedoe", "iloveyou", 1L);

        // Act and Assert
        assertThrows(ServiceException.class, () -> serviceImpl
                .addSchedule(new ScheduleTbl(doctor, LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, LocalTime.MIDNIGHT, 1)));
        verify(conn).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn3).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn2).prepareStatement(
                eq("SELECT * FROM schedule_tbl WHERE scheduleDate LIKE ? AND startTime LIKE ? AND endTime LIKE ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement2).executeQuery();
        verify(preparedStatement3).executeQuery();
        verify(preparedStatement2).setDate(eq(1), isA(Date.class));
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(preparedStatement3).setLong(eq(1), eq(1L));
        verify(preparedStatement2, atLeast(1)).setTime(anyInt(), isA(Time.class));
        verify(resultSet2).getInt(eq("scheduleStatus"));
        verify(resultSet2, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet).getLong(eq("UserId"));
        verify(resultSet3).getLong(eq("UserId"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet3, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet2).getString(eq("unavailabilityReason"));
        verify(resultSet).next();
        verify(resultSet2).next();
        verify(resultSet3).next();
        verify(preparedStatement).close();
        verify(preparedStatement2).close();
        verify(preparedStatement3).close();
    }

    /**
     * Method under test: {@link ServiceImpl#addSchedule(ScheduleTbl)}
     */
    @Test
    void testAddSchedule4() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("Doctor");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        AuthUserDBDao authUserDao = new AuthUserDBDao(conn);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(false).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setDate(anyInt(), Mockito.<Date>any());
        doNothing().when(preparedStatement2).setTime(anyInt(), Mockito.<Time>any());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        ResultSet resultSet3 = mock(ResultSet.class);
        when(resultSet3.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet3.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet3.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement3 = mock(PreparedStatement.class);
        when(preparedStatement3.executeQuery()).thenReturn(resultSet3);
        doNothing().when(preparedStatement3).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement3).close();
        Connection conn3 = mock(Connection.class);
        when(conn3.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement3);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn2, new AuthUserDBDao(conn3));

        Connection conn4 = mock(Connection.class);
        PatientDBDao patientDao = new PatientDBDao(conn4, new AuthUserDBDao(mock(Connection.class)));

        Connection conn5 = mock(Connection.class);
        Connection conn6 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn6, new AuthUserDBDao(mock(Connection.class)));

        Connection conn7 = mock(Connection.class);
        ServiceImpl serviceImpl = new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn5, patientDao2, new ScheduleDBDao(conn7, new AuthUserDBDao(mock(Connection.class)))));
        AuthUserTbl doctor = new AuthUserTbl("janedoe", "iloveyou", 1L);

        // Act and Assert
        assertThrows(ServiceException.class, () -> serviceImpl
                .addSchedule(new ScheduleTbl(doctor, LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, LocalTime.MIDNIGHT, 1)));
        verify(conn).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn2).prepareStatement(
                eq("SELECT * FROM schedule_tbl WHERE scheduleDate LIKE ? AND startTime LIKE ? AND endTime LIKE ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement2).executeQuery();
        verify(preparedStatement2).setDate(eq(1), isA(Date.class));
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(preparedStatement2, atLeast(1)).setTime(anyInt(), isA(Time.class));
        verify(resultSet).getLong(eq("UserId"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet).next();
        verify(resultSet2).next();
        verify(preparedStatement).close();
        verify(preparedStatement2).close();
    }

    /**
     * Method under test: {@link ServiceImpl#addSchedule(ScheduleTbl)}
     */
    @Test
    void testAddSchedule5() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("Doctor");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        AuthUserDBDao authUserDao = new AuthUserDBDao(conn);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(false).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setDate(anyInt(), Mockito.<Date>any());
        doNothing().when(preparedStatement2).setTime(anyInt(), Mockito.<Time>any());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        ResultSet resultSet3 = mock(ResultSet.class);
        when(resultSet3.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet3.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet3.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement3 = mock(PreparedStatement.class);
        when(preparedStatement3.executeQuery()).thenReturn(resultSet3);
        doNothing().when(preparedStatement3).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement3).close();
        Connection conn3 = mock(Connection.class);
        when(conn3.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement3);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn2, new AuthUserDBDao(conn3));

        Connection conn4 = mock(Connection.class);
        PatientDBDao patientDao = new PatientDBDao(conn4, new AuthUserDBDao(mock(Connection.class)));

        Connection conn5 = mock(Connection.class);
        Connection conn6 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn6, new AuthUserDBDao(mock(Connection.class)));

        Connection conn7 = mock(Connection.class);
        ServiceImpl serviceImpl = new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn5, patientDao2, new ScheduleDBDao(conn7, new AuthUserDBDao(mock(Connection.class)))));
        AuthUserTbl doctor = new AuthUserTbl("janedoe", "iloveyou", 1L);

        // Act and Assert
        assertThrows(ServiceException.class, () -> serviceImpl
                .addSchedule(new ScheduleTbl(doctor, LocalDate.now(), LocalTime.MIDNIGHT, LocalTime.MIDNIGHT, 1)));
        verify(conn).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn2).prepareStatement(
                eq("SELECT * FROM schedule_tbl WHERE scheduleDate LIKE ? AND startTime LIKE ? AND endTime LIKE ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement2).executeQuery();
        verify(preparedStatement2).setDate(eq(1), isA(Date.class));
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(preparedStatement2, atLeast(1)).setTime(anyInt(), isA(Time.class));
        verify(resultSet).getLong(eq("UserId"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet).next();
        verify(resultSet2).next();
        verify(preparedStatement).close();
        verify(preparedStatement2).close();
    }

    /**
     * Method under test: {@link ServiceImpl#addPatient(PatientTbl)}
     */
    @Test
    void testAddPatient() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        when(preparedStatement.executeQuery()).thenReturn(mock(ResultSet.class));
        when(preparedStatement.getResultSet()).thenReturn(resultSet);
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        PatientDBDao patientDao = new PatientDBDao(conn, new AuthUserDBDao(mock(Connection.class)));

        AuthUserDBDao authUserDao = new AuthUserDBDao(mock(Connection.class));
        Connection conn2 = mock(Connection.class);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn2, new AuthUserDBDao(mock(Connection.class)));

        Connection conn3 = mock(Connection.class);
        Connection conn4 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn4, new AuthUserDBDao(mock(Connection.class)));

        Connection conn5 = mock(Connection.class);
        ServiceImpl serviceImpl = new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn3, patientDao2, new ScheduleDBDao(conn5, new AuthUserDBDao(mock(Connection.class)))));
        PatientTbl patient = new PatientTbl("Name", "Mobile No", new AuthUserTbl("janedoe", "iloveyou", 1L));

        // Act
        boolean actualAddPatientResult = serviceImpl.addPatient(patient);

        // Assert
        verify(conn, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), anyLong());
        verify(preparedStatement, atLeast(1)).setString(anyInt(), Mockito.<String>any());
        verify(resultSet).getLong(eq("maxPatientId"));
        verify(resultSet).next();
        verify(preparedStatement, atLeast(1)).close();
        verify(preparedStatement).getResultSet();
        assertEquals(2L, patient.getPatientId());
        assertTrue(actualAddPatientResult);
    }

    /**
     * Method under test: {@link ServiceImpl#addPatient(PatientTbl)}
     */
    @Test
    void testAddPatient2() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        AuthUserDBDao authUserDao = new AuthUserDBDao(conn);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeUpdate()).thenReturn(1);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).setString(anyInt(), Mockito.<String>any());
        when(preparedStatement2.executeQuery()).thenReturn(mock(ResultSet.class));
        when(preparedStatement2.getResultSet()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(mock(Connection.class)));

        Connection conn3 = mock(Connection.class);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn3, new AuthUserDBDao(mock(Connection.class)));

        Connection conn4 = mock(Connection.class);
        Connection conn5 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn5, new AuthUserDBDao(mock(Connection.class)));

        Connection conn6 = mock(Connection.class);
        ServiceImpl serviceImpl = new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn4, patientDao2, new ScheduleDBDao(conn6, new AuthUserDBDao(mock(Connection.class)))));
        PatientTbl patient = new PatientTbl(1L, "INSERT INTO patient_tbl VALUES (?, ?, ?, ?, ?)",
                "INSERT INTO patient_tbl VALUES (?, ?, ?, ?, ?)", new AuthUserTbl("janedoe", "iloveyou", 1L));

        // Act
        boolean actualAddPatientResult = serviceImpl.addPatient(patient);

        // Assert
        verify(conn2, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(conn).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement2).executeQuery();
        verify(preparedStatement2).executeUpdate();
        verify(preparedStatement2, atLeast(1)).setLong(anyInt(), anyLong());
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(preparedStatement2, atLeast(1)).setString(anyInt(), Mockito.<String>any());
        verify(resultSet).getLong(eq("UserId"));
        verify(resultSet2).getLong(eq("maxPatientId"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet).next();
        verify(resultSet2).next();
        verify(preparedStatement).close();
        verify(preparedStatement2, atLeast(1)).close();
        verify(preparedStatement2).getResultSet();
        assertEquals(2L, patient.getPatientId());
        assertTrue(actualAddPatientResult);
    }

    /**
     * Method under test: {@link ServiceImpl#addPatient(PatientTbl)}
     */
    @Test
    void testAddPatient3() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(false).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        AuthUserDBDao authUserDao = new AuthUserDBDao(conn);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        when(preparedStatement2.executeQuery()).thenReturn(mock(ResultSet.class));
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(mock(Connection.class)));

        Connection conn3 = mock(Connection.class);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn3, new AuthUserDBDao(mock(Connection.class)));

        Connection conn4 = mock(Connection.class);
        Connection conn5 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn5, new AuthUserDBDao(mock(Connection.class)));

        Connection conn6 = mock(Connection.class);
        ServiceImpl serviceImpl = new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn4, patientDao2, new ScheduleDBDao(conn6, new AuthUserDBDao(mock(Connection.class)))));

        // Act and Assert
        assertThrows(ServiceException.class,
                () -> serviceImpl.addPatient(new PatientTbl(1L, "INSERT INTO patient_tbl VALUES (?, ?, ?, ?, ?)",
                        "INSERT INTO patient_tbl VALUES (?, ?, ?, ?, ?)", new AuthUserTbl("janedoe", "iloveyou", 1L))));
        verify(conn).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link ServiceImpl#bookAppointment(AppointmentTbl)}
     */
    @Test
    void testBookAppointment() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        ResultSet resultSet3 = mock(ResultSet.class);
        when(resultSet3.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet3.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet3.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement3 = mock(PreparedStatement.class);
        when(preparedStatement3.executeQuery()).thenReturn(resultSet3);
        doNothing().when(preparedStatement3).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement3).close();
        Connection conn3 = mock(Connection.class);
        when(conn3.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement3);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(conn3));

        Connection conn4 = mock(Connection.class);
        AppointmentDBDao appointmentDao = new AppointmentDBDao(conn, patientDao,
                new ScheduleDBDao(conn4, new AuthUserDBDao(mock(Connection.class))));

        AuthUserDBDao authUserDao = new AuthUserDBDao(mock(Connection.class));
        Connection conn5 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn5, new AuthUserDBDao(mock(Connection.class)));

        Connection conn6 = mock(Connection.class);
        ServiceImpl serviceImpl = new ServiceImpl(authUserDao, patientDao2,
                new ScheduleDBDao(conn6, new AuthUserDBDao(mock(Connection.class))), appointmentDao);
        PatientTbl patient = new PatientTbl("Name", "Mobile No", new AuthUserTbl("janedoe", "iloveyou", 1L));

        AuthUserTbl doctor = new AuthUserTbl("janedoe", "iloveyou", 1L);

        // Act and Assert
        assertThrows(ServiceException.class, () -> serviceImpl.bookAppointment(new AppointmentTbl(1L, patient,
                new ScheduleTbl(doctor, LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, LocalTime.MIDNIGHT, 1), 1)));
        verify(conn).prepareStatement(eq("SELECT * FROM appointment_tbl WHERE ScheduleId = ?"));
        verify(conn3).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn2).prepareStatement(eq("SELECT * FROM patient_tbl WHERE PatientId = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement2).executeQuery();
        verify(preparedStatement3).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(0L));
        verify(preparedStatement2).setLong(eq(1), eq(1L));
        verify(preparedStatement3).setLong(eq(1), eq(1L));
        verify(resultSet).getInt(eq("appointmentStatus"));
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet3).getLong(eq("UserId"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet3, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet).next();
        verify(resultSet2).next();
        verify(resultSet3).next();
        verify(preparedStatement).close();
        verify(preparedStatement2).close();
        verify(preparedStatement3).close();
    }

    /**
     * Method under test: {@link ServiceImpl#bookAppointment(AppointmentTbl)}
     */
    @Test
    void testBookAppointment2() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(false).thenReturn(true).thenReturn(false);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        when(preparedStatement.getResultSet()).thenReturn(resultSet2);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        ResultSet resultSet3 = mock(ResultSet.class);
        when(resultSet3.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet3.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeQuery()).thenReturn(resultSet3);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        ResultSet resultSet4 = mock(ResultSet.class);
        when(resultSet4.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet4.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement3 = mock(PreparedStatement.class);
        when(preparedStatement3.executeQuery()).thenReturn(resultSet4);
        doNothing().when(preparedStatement3).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement3).close();
        Connection conn3 = mock(Connection.class);
        when(conn3.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement3);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(conn3));

        Connection conn4 = mock(Connection.class);
        AppointmentDBDao appointmentDao = new AppointmentDBDao(conn, patientDao,
                new ScheduleDBDao(conn4, new AuthUserDBDao(mock(Connection.class))));

        AuthUserDBDao authUserDao = new AuthUserDBDao(mock(Connection.class));
        Connection conn5 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn5, new AuthUserDBDao(mock(Connection.class)));

        Connection conn6 = mock(Connection.class);
        ServiceImpl serviceImpl = new ServiceImpl(authUserDao, patientDao2,
                new ScheduleDBDao(conn6, new AuthUserDBDao(mock(Connection.class))), appointmentDao);
        PatientTbl patient = new PatientTbl("Name", "Mobile No", new AuthUserTbl("janedoe", "iloveyou", 1L));

        AuthUserTbl doctor = new AuthUserTbl("janedoe", "iloveyou", 1L);

        AppointmentTbl appointment = new AppointmentTbl(1L, patient,
                new ScheduleTbl(doctor, LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, LocalTime.MIDNIGHT, 1), 1);

        // Act
        boolean actualBookAppointmentResult = serviceImpl.bookAppointment(appointment);

        // Assert
        verify(conn, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).setInt(eq(7), eq(1));
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), anyLong());
        verify(preparedStatement, atLeast(1)).setString(anyInt(), isNull());
        verify(resultSet2).getLong(eq("maxAppointmentId"));
        verify(resultSet2).next();
        verify(resultSet).next();
        verify(preparedStatement, atLeast(1)).close();
        verify(preparedStatement).getResultSet();
        assertEquals(2L, appointment.getAppointmentId());
        assertTrue(actualBookAppointmentResult);
    }

    /**
     * Method under test: {@link ServiceImpl#bookAppointment(AppointmentTbl)}
     */
    @Test
    void testBookAppointment3() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        ResultSet resultSet3 = mock(ResultSet.class);
        when(resultSet3.getString(Mockito.<String>any())).thenReturn(null);
        when(resultSet3.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet3.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement3 = mock(PreparedStatement.class);
        when(preparedStatement3.executeQuery()).thenReturn(resultSet3);
        doNothing().when(preparedStatement3).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement3).close();
        Connection conn3 = mock(Connection.class);
        when(conn3.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement3);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(conn3));

        Connection conn4 = mock(Connection.class);
        AppointmentDBDao appointmentDao = new AppointmentDBDao(conn, patientDao,
                new ScheduleDBDao(conn4, new AuthUserDBDao(mock(Connection.class))));

        AuthUserDBDao authUserDao = new AuthUserDBDao(mock(Connection.class));
        Connection conn5 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn5, new AuthUserDBDao(mock(Connection.class)));

        Connection conn6 = mock(Connection.class);
        ServiceImpl serviceImpl = new ServiceImpl(authUserDao, patientDao2,
                new ScheduleDBDao(conn6, new AuthUserDBDao(mock(Connection.class))), appointmentDao);
        PatientTbl patient = new PatientTbl("Name", "Mobile No", new AuthUserTbl("janedoe", "iloveyou", 1L));

        AuthUserTbl doctor = new AuthUserTbl("janedoe", "iloveyou", 1L);

        // Act and Assert
        assertThrows(ServiceException.class, () -> serviceImpl.bookAppointment(new AppointmentTbl(1L, patient,
                new ScheduleTbl(doctor, LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, LocalTime.MIDNIGHT, 1), 1)));
        verify(conn).prepareStatement(eq("SELECT * FROM appointment_tbl WHERE ScheduleId = ?"));
        verify(conn3).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn2).prepareStatement(eq("SELECT * FROM patient_tbl WHERE PatientId = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement2).executeQuery();
        verify(preparedStatement3).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(0L));
        verify(preparedStatement2).setLong(eq(1), eq(1L));
        verify(preparedStatement3).setLong(eq(1), eq(1L));
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet3).getLong(eq("UserId"));
        verify(resultSet2, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet3, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet).next();
        verify(resultSet2).next();
        verify(resultSet3).next();
        verify(preparedStatement).close();
        verify(preparedStatement2).close();
        verify(preparedStatement3).close();
    }

    /**
     * Method under test: {@link ServiceImpl#updateSchedule(ScheduleTbl)}
     */
    @Test
    void testUpdateSchedule() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        AuthUserDBDao authUserDao = new AuthUserDBDao(conn);
        Date date = mock(Date.class);
        when(date.toLocalDate()).thenReturn(LocalDate.of(1970, 1, 1));
        Time time = mock(Time.class);
        when(time.toLocalTime()).thenReturn(LocalTime.MIDNIGHT);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet2.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet2.getTime(Mockito.<String>any())).thenReturn(time);
        when(resultSet2.getDate(Mockito.<String>any())).thenReturn(date);
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        doNothing().when(preparedStatement2).setDate(anyInt(), Mockito.<Date>any());
        doNothing().when(preparedStatement2).setTime(anyInt(), Mockito.<Time>any());
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        ResultSet resultSet3 = mock(ResultSet.class);
        when(resultSet3.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet3.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet3.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement3 = mock(PreparedStatement.class);
        when(preparedStatement3.executeQuery()).thenReturn(resultSet3);
        doNothing().when(preparedStatement3).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement3).close();
        Connection conn3 = mock(Connection.class);
        when(conn3.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement3);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn2, new AuthUserDBDao(conn3));

        Connection conn4 = mock(Connection.class);
        PatientDBDao patientDao = new PatientDBDao(conn4, new AuthUserDBDao(mock(Connection.class)));

        Connection conn5 = mock(Connection.class);
        Connection conn6 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn6, new AuthUserDBDao(mock(Connection.class)));

        Connection conn7 = mock(Connection.class);
        ServiceImpl serviceImpl = new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn5, patientDao2, new ScheduleDBDao(conn7, new AuthUserDBDao(mock(Connection.class)))));
        AuthUserTbl doctor = new AuthUserTbl("janedoe", "iloveyou", 1L);

        // Act and Assert
        assertThrows(ServiceException.class, () -> serviceImpl
                .updateSchedule(new ScheduleTbl(doctor, LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, LocalTime.MIDNIGHT, 1)));
        verify(conn2, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(conn).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn3, atLeast(1)).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(date).toLocalDate();
        verify(preparedStatement).executeQuery();
        verify(preparedStatement2, atLeast(1)).executeQuery();
        verify(preparedStatement3, atLeast(1)).executeQuery();
        verify(preparedStatement2).setDate(eq(1), isA(Date.class));
        verify(preparedStatement2).setLong(eq(1), eq(0L));
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(preparedStatement3, atLeast(1)).setLong(eq(1), eq(1L));
        verify(preparedStatement2, atLeast(1)).setTime(anyInt(), isA(Time.class));
        verify(resultSet2).getDate(eq("scheduleDate"));
        verify(resultSet2, atLeast(1)).getInt(eq("scheduleStatus"));
        verify(resultSet2, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet).getLong(eq("UserId"));
        verify(resultSet3, atLeast(1)).getLong(eq("UserId"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet3, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).getString(eq("unavailabilityReason"));
        verify(resultSet2, atLeast(1)).getTime(Mockito.<String>any());
        verify(resultSet).next();
        verify(resultSet2, atLeast(1)).next();
        verify(resultSet3, atLeast(1)).next();
        verify(preparedStatement).close();
        verify(preparedStatement2, atLeast(1)).close();
        verify(preparedStatement3, atLeast(1)).close();
        verify(time, atLeast(1)).toLocalTime();
    }

    /**
     * Method under test: {@link ServiceImpl#updateSchedule(ScheduleTbl)}
     */
    @Test
    void testUpdateSchedule2() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(false).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        AuthUserDBDao authUserDao = new AuthUserDBDao(conn);
        Date date = mock(Date.class);
        when(date.toLocalDate()).thenReturn(LocalDate.of(1970, 1, 1));
        Time time = mock(Time.class);
        when(time.toLocalTime()).thenReturn(LocalTime.MIDNIGHT);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet2.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet2.getTime(Mockito.<String>any())).thenReturn(time);
        when(resultSet2.getDate(Mockito.<String>any())).thenReturn(date);
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        ResultSet resultSet3 = mock(ResultSet.class);
        when(resultSet3.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet3.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet3.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement3 = mock(PreparedStatement.class);
        when(preparedStatement3.executeQuery()).thenReturn(resultSet3);
        doNothing().when(preparedStatement3).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement3).close();
        Connection conn3 = mock(Connection.class);
        when(conn3.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement3);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn2, new AuthUserDBDao(conn3));

        Connection conn4 = mock(Connection.class);
        PatientDBDao patientDao = new PatientDBDao(conn4, new AuthUserDBDao(mock(Connection.class)));

        Connection conn5 = mock(Connection.class);
        Connection conn6 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn6, new AuthUserDBDao(mock(Connection.class)));

        Connection conn7 = mock(Connection.class);
        ServiceImpl serviceImpl = new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn5, patientDao2, new ScheduleDBDao(conn7, new AuthUserDBDao(mock(Connection.class)))));
        AuthUserTbl doctor = new AuthUserTbl("janedoe", "iloveyou", 1L);

        // Act and Assert
        assertThrows(ServiceException.class, () -> serviceImpl
                .updateSchedule(new ScheduleTbl(doctor, LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, LocalTime.MIDNIGHT, 1)));
        verify(conn).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn3).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn2).prepareStatement(eq("SELECT * FROM schedule_tbl WHERE ScheduleId = ?"));
        verify(date).toLocalDate();
        verify(preparedStatement).executeQuery();
        verify(preparedStatement2).executeQuery();
        verify(preparedStatement3).executeQuery();
        verify(preparedStatement2).setLong(eq(1), eq(0L));
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(preparedStatement3).setLong(eq(1), eq(1L));
        verify(resultSet2).getDate(eq("scheduleDate"));
        verify(resultSet2).getInt(eq("scheduleStatus"));
        verify(resultSet2).getLong(eq("DoctorId"));
        verify(resultSet3).getLong(eq("UserId"));
        verify(resultSet3, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet2).getString(eq("unavailabilityReason"));
        verify(resultSet2, atLeast(1)).getTime(Mockito.<String>any());
        verify(resultSet).next();
        verify(resultSet2).next();
        verify(resultSet3).next();
        verify(preparedStatement).close();
        verify(preparedStatement2).close();
        verify(preparedStatement3).close();
        verify(time, atLeast(1)).toLocalTime();
    }

    /**
     * Method under test: {@link ServiceImpl#updateSchedule(ScheduleTbl)}
     */
    @Test
    void testUpdateSchedule3() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        AuthUserDBDao authUserDao = new AuthUserDBDao(conn);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.next()).thenReturn(false).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        ResultSet resultSet3 = mock(ResultSet.class);
        when(resultSet3.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement3 = mock(PreparedStatement.class);
        when(preparedStatement3.executeQuery()).thenReturn(resultSet3);
        doNothing().when(preparedStatement3).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement3).close();
        Connection conn3 = mock(Connection.class);
        when(conn3.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement3);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn2, new AuthUserDBDao(conn3));

        Connection conn4 = mock(Connection.class);
        PatientDBDao patientDao = new PatientDBDao(conn4, new AuthUserDBDao(mock(Connection.class)));

        Connection conn5 = mock(Connection.class);
        Connection conn6 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn6, new AuthUserDBDao(mock(Connection.class)));

        Connection conn7 = mock(Connection.class);
        ServiceImpl serviceImpl = new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn5, patientDao2, new ScheduleDBDao(conn7, new AuthUserDBDao(mock(Connection.class)))));
        AuthUserTbl doctor = new AuthUserTbl("janedoe", "iloveyou", 1L);

        // Act and Assert
        assertThrows(ServiceException.class, () -> serviceImpl
                .updateSchedule(new ScheduleTbl(doctor, LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, LocalTime.MIDNIGHT, 1)));
        verify(conn2).prepareStatement(eq("SELECT * FROM schedule_tbl WHERE ScheduleId = ?"));
        verify(preparedStatement2).executeQuery();
        verify(preparedStatement2).setLong(eq(1), eq(0L));
        verify(resultSet2).next();
        verify(preparedStatement2).close();
    }

    /**
     * Method under test: {@link ServiceImpl#updateSchedule(ScheduleTbl)}
     */
    @Test
    void testUpdateSchedule4() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        AuthUserDBDao authUserDao = new AuthUserDBDao(conn);
        Date date = mock(Date.class);
        when(date.toLocalDate()).thenReturn(LocalDate.of(1970, 1, 1));
        Time time = mock(Time.class);
        when(time.toLocalTime()).thenReturn(LocalTime.MIDNIGHT);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet2.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet2.getTime(Mockito.<String>any())).thenReturn(time);
        when(resultSet2.getDate(Mockito.<String>any())).thenReturn(date);
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(false).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        doNothing().when(preparedStatement2).setDate(anyInt(), Mockito.<Date>any());
        doNothing().when(preparedStatement2).setTime(anyInt(), Mockito.<Time>any());
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        ResultSet resultSet3 = mock(ResultSet.class);
        when(resultSet3.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet3.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet3.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement3 = mock(PreparedStatement.class);
        when(preparedStatement3.executeQuery()).thenReturn(resultSet3);
        doNothing().when(preparedStatement3).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement3).close();
        Connection conn3 = mock(Connection.class);
        when(conn3.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement3);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn2, new AuthUserDBDao(conn3));

        Connection conn4 = mock(Connection.class);
        PatientDBDao patientDao = new PatientDBDao(conn4, new AuthUserDBDao(mock(Connection.class)));

        Connection conn5 = mock(Connection.class);
        Connection conn6 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn6, new AuthUserDBDao(mock(Connection.class)));

        Connection conn7 = mock(Connection.class);
        ServiceImpl serviceImpl = new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn5, patientDao2, new ScheduleDBDao(conn7, new AuthUserDBDao(mock(Connection.class)))));
        AuthUserTbl doctor = new AuthUserTbl("janedoe", "iloveyou", 1L);

        // Act and Assert
        assertThrows(ServiceException.class, () -> serviceImpl
                .updateSchedule(new ScheduleTbl(doctor, LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, LocalTime.MIDNIGHT, 1)));
        verify(conn2, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(conn).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn3).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(date).toLocalDate();
        verify(preparedStatement).executeQuery();
        verify(preparedStatement3).executeQuery();
        verify(preparedStatement2, atLeast(1)).executeQuery();
        verify(preparedStatement2).setDate(eq(1), isA(Date.class));
        verify(preparedStatement2).setLong(eq(1), eq(0L));
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(preparedStatement3).setLong(eq(1), eq(1L));
        verify(preparedStatement2, atLeast(1)).setTime(anyInt(), isA(Time.class));
        verify(resultSet2).getDate(eq("scheduleDate"));
        verify(resultSet2).getInt(eq("scheduleStatus"));
        verify(resultSet2).getLong(eq("DoctorId"));
        verify(resultSet).getLong(eq("UserId"));
        verify(resultSet3).getLong(eq("UserId"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet3, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet2).getString(eq("unavailabilityReason"));
        verify(resultSet2, atLeast(1)).getTime(Mockito.<String>any());
        verify(resultSet).next();
        verify(resultSet3).next();
        verify(resultSet2, atLeast(1)).next();
        verify(preparedStatement).close();
        verify(preparedStatement3).close();
        verify(preparedStatement2, atLeast(1)).close();
        verify(time, atLeast(1)).toLocalTime();
    }

    /**
     * Method under test: {@link ServiceImpl#viewAppointments()}
     */
    @Test
    void testViewAppointments() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        doNothing().when(resultSet).close();
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        ResultSet resultSet3 = mock(ResultSet.class);
        when(resultSet3.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet3.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet3.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement3 = mock(PreparedStatement.class);
        when(preparedStatement3.executeQuery()).thenReturn(resultSet3);
        doNothing().when(preparedStatement3).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement3).close();
        Connection conn3 = mock(Connection.class);
        when(conn3.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement3);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(conn3));

        Date date = mock(Date.class);
        when(date.toLocalDate()).thenReturn(LocalDate.of(1970, 1, 1));
        Time time = mock(Time.class);
        when(time.toLocalTime()).thenReturn(LocalTime.MIDNIGHT);
        ResultSet resultSet4 = mock(ResultSet.class);
        when(resultSet4.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet4.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet4.getTime(Mockito.<String>any())).thenReturn(time);
        when(resultSet4.getDate(Mockito.<String>any())).thenReturn(date);
        when(resultSet4.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet4.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement4 = mock(PreparedStatement.class);
        when(preparedStatement4.executeQuery()).thenReturn(resultSet4);
        doNothing().when(preparedStatement4).close();
        Connection conn4 = mock(Connection.class);
        when(conn4.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement4);
        ResultSet resultSet5 = mock(ResultSet.class);
        when(resultSet5.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet5.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet5.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement5 = mock(PreparedStatement.class);
        when(preparedStatement5.executeQuery()).thenReturn(resultSet5);
        doNothing().when(preparedStatement5).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement5).close();
        Connection conn5 = mock(Connection.class);
        when(conn5.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement5);
        AppointmentDBDao appointmentDao = new AppointmentDBDao(conn, patientDao,
                new ScheduleDBDao(conn4, new AuthUserDBDao(conn5)));

        AuthUserDBDao authUserDao = new AuthUserDBDao(mock(Connection.class));
        Connection conn6 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn6, new AuthUserDBDao(mock(Connection.class)));

        Connection conn7 = mock(Connection.class);

        // Act
        HashMap<LocalDate, List<AppointmentTbl>> actualViewAppointmentsResult = (new ServiceImpl(authUserDao, patientDao2,
                new ScheduleDBDao(conn7, new AuthUserDBDao(mock(Connection.class))), appointmentDao)).viewAppointments();

        // Assert
        verify(conn).prepareStatement(eq("SELECT * FROM appointment_tbl WHERE ScheduleId = ?"));
        verify(conn3, atLeast(1)).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn5, atLeast(1)).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn2, atLeast(1)).prepareStatement(eq("SELECT * FROM patient_tbl WHERE PatientId = ?"));
        verify(conn4).prepareStatement(eq(
                "SELECT * FROM schedule_tbl WHERE scheduleDate BETWEEN ? AND ? ORDER BY scheduleDate ASC, StartTime ASC, EndTime ASC"));
        verify(date, atLeast(1)).toLocalDate();
        verify(preparedStatement4).executeQuery();
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement2, atLeast(1)).executeQuery();
        verify(preparedStatement3, atLeast(1)).executeQuery();
        verify(preparedStatement5, atLeast(1)).executeQuery();
        verify(preparedStatement, atLeast(1)).setLong(eq(1), eq(1L));
        verify(preparedStatement2, atLeast(1)).setLong(eq(1), eq(1L));
        verify(preparedStatement3, atLeast(1)).setLong(eq(1), eq(1L));
        verify(preparedStatement5, atLeast(1)).setLong(eq(1), eq(1L));
        verify(resultSet, atLeast(1)).close();
        verify(resultSet4, atLeast(1)).getDate(eq("scheduleDate"));
        verify(resultSet, atLeast(1)).getInt(eq("appointmentStatus"));
        verify(resultSet4, atLeast(1)).getInt(eq("scheduleStatus"));
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet4, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet3, atLeast(1)).getLong(eq("UserId"));
        verify(resultSet5, atLeast(1)).getLong(eq("UserId"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet3, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet5, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet4, atLeast(1)).getString(eq("unavailabilityReason"));
        verify(resultSet4, atLeast(1)).getTime(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).next();
        verify(resultSet3, atLeast(1)).next();
        verify(resultSet5, atLeast(1)).next();
        verify(resultSet4, atLeast(1)).next();
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement).close();
        verify(preparedStatement4).close();
        verify(preparedStatement2, atLeast(1)).close();
        verify(preparedStatement3, atLeast(1)).close();
        verify(preparedStatement5, atLeast(1)).close();
        verify(time, atLeast(1)).toLocalTime();
        assertEquals(1, actualViewAppointmentsResult.size());
    }

    /**
     * Method under test: {@link ServiceImpl#viewAppointments(AuthUserTbl)}
     */
    @Test
    void testViewAppointments2() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        doNothing().when(resultSet).close();
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        ResultSet resultSet3 = mock(ResultSet.class);
        when(resultSet3.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet3.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet3.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement3 = mock(PreparedStatement.class);
        when(preparedStatement3.executeQuery()).thenReturn(resultSet3);
        doNothing().when(preparedStatement3).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement3).close();
        Connection conn3 = mock(Connection.class);
        when(conn3.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement3);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(conn3));

        Date date = mock(Date.class);
        when(date.toLocalDate()).thenReturn(LocalDate.of(1970, 1, 1));
        Time time = mock(Time.class);
        when(time.toLocalTime()).thenReturn(LocalTime.MIDNIGHT);
        ResultSet resultSet4 = mock(ResultSet.class);
        when(resultSet4.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet4.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet4.getTime(Mockito.<String>any())).thenReturn(time);
        when(resultSet4.getDate(Mockito.<String>any())).thenReturn(date);
        when(resultSet4.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet4.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement4 = mock(PreparedStatement.class);
        when(preparedStatement4.executeQuery()).thenReturn(resultSet4);
        doNothing().when(preparedStatement4).close();
        Connection conn4 = mock(Connection.class);
        when(conn4.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement4);
        ResultSet resultSet5 = mock(ResultSet.class);
        when(resultSet5.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet5.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet5.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement5 = mock(PreparedStatement.class);
        when(preparedStatement5.executeQuery()).thenReturn(resultSet5);
        doNothing().when(preparedStatement5).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement5).close();
        Connection conn5 = mock(Connection.class);
        when(conn5.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement5);
        AppointmentDBDao appointmentDao = new AppointmentDBDao(conn, patientDao,
                new ScheduleDBDao(conn4, new AuthUserDBDao(conn5)));

        AuthUserDBDao authUserDao = new AuthUserDBDao(mock(Connection.class));
        Connection conn6 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn6, new AuthUserDBDao(mock(Connection.class)));

        Connection conn7 = mock(Connection.class);
        ServiceImpl serviceImpl = new ServiceImpl(authUserDao, patientDao2,
                new ScheduleDBDao(conn7, new AuthUserDBDao(mock(Connection.class))), appointmentDao);

        // Act
        HashMap<LocalDate, List<AppointmentTbl>> actualViewAppointmentsResult = serviceImpl
                .viewAppointments(new AuthUserTbl("janedoe", "iloveyou", 1L));

        // Assert
        verify(conn).prepareStatement(eq("SELECT * FROM appointment_tbl WHERE ScheduleId = ?"));
        verify(conn3, atLeast(1)).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn5, atLeast(1)).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn2, atLeast(1)).prepareStatement(eq("SELECT * FROM patient_tbl WHERE PatientId = ?"));
        verify(conn4).prepareStatement(eq(
                "SELECT * FROM schedule_tbl WHERE scheduleDate BETWEEN ? AND ? ORDER BY scheduleDate ASC, StartTime ASC, EndTime ASC"));
        verify(date, atLeast(1)).toLocalDate();
        verify(preparedStatement4).executeQuery();
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement2, atLeast(1)).executeQuery();
        verify(preparedStatement3, atLeast(1)).executeQuery();
        verify(preparedStatement5, atLeast(1)).executeQuery();
        verify(preparedStatement, atLeast(1)).setLong(eq(1), eq(1L));
        verify(preparedStatement2, atLeast(1)).setLong(eq(1), eq(1L));
        verify(preparedStatement3, atLeast(1)).setLong(eq(1), eq(1L));
        verify(preparedStatement5, atLeast(1)).setLong(eq(1), eq(1L));
        verify(resultSet, atLeast(1)).close();
        verify(resultSet4, atLeast(1)).getDate(eq("scheduleDate"));
        verify(resultSet, atLeast(1)).getInt(eq("appointmentStatus"));
        verify(resultSet4, atLeast(1)).getInt(eq("scheduleStatus"));
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet4, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet3, atLeast(1)).getLong(eq("UserId"));
        verify(resultSet5, atLeast(1)).getLong(eq("UserId"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet3, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet5, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet4, atLeast(1)).getString(eq("unavailabilityReason"));
        verify(resultSet4, atLeast(1)).getTime(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).next();
        verify(resultSet3, atLeast(1)).next();
        verify(resultSet5, atLeast(1)).next();
        verify(resultSet4, atLeast(1)).next();
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement).close();
        verify(preparedStatement4).close();
        verify(preparedStatement2, atLeast(1)).close();
        verify(preparedStatement3, atLeast(1)).close();
        verify(preparedStatement5, atLeast(1)).close();
        verify(time, atLeast(1)).toLocalTime();
        assertTrue(serviceImpl.viewAppointments().isEmpty());
        assertTrue(actualViewAppointmentsResult.isEmpty());
    }

    /**
     * Method under test: {@link ServiceImpl#cancelAppointment(long)}
     */
    @Test
    void testCancelAppointment() throws ServiceException, SQLException {
        // Arrange
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        doNothing().when(preparedStatement).setDate(anyInt(), Mockito.<Date>any());
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        doNothing().when(preparedStatement).setTime(anyInt(), Mockito.<Time>any());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn, new AuthUserDBDao(mock(Connection.class)));

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeUpdate()).thenReturn(1);
        doNothing().when(preparedStatement2).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement2).setString(anyInt(), Mockito.<String>any());
        when(preparedStatement2.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement3 = mock(PreparedStatement.class);
        when(preparedStatement3.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement3).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement3).close();
        Connection conn3 = mock(Connection.class);
        when(conn3.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement3);
        ResultSet resultSet3 = mock(ResultSet.class);
        when(resultSet3.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet3.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet3.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement4 = mock(PreparedStatement.class);
        when(preparedStatement4.executeQuery()).thenReturn(resultSet3);
        doNothing().when(preparedStatement4).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement4).close();
        Connection conn4 = mock(Connection.class);
        when(conn4.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement4);
        PatientDBDao patientDao = new PatientDBDao(conn3, new AuthUserDBDao(conn4));

        Date date = mock(Date.class);
        LocalDate ofResult = LocalDate.of(1970, 1, 1);
        when(date.toLocalDate()).thenReturn(ofResult);
        Time time = mock(Time.class);
        when(time.toLocalTime()).thenReturn(LocalTime.MIDNIGHT);
        ResultSet resultSet4 = mock(ResultSet.class);
        when(resultSet4.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet4.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet4.getTime(Mockito.<String>any())).thenReturn(time);
        when(resultSet4.getDate(Mockito.<String>any())).thenReturn(date);
        when(resultSet4.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet4.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement5 = mock(PreparedStatement.class);
        when(preparedStatement5.executeQuery()).thenReturn(resultSet4);
        doNothing().when(preparedStatement5).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement5).close();
        Connection conn5 = mock(Connection.class);
        when(conn5.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement5);
        ResultSet resultSet5 = mock(ResultSet.class);
        when(resultSet5.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet5.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet5.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement6 = mock(PreparedStatement.class);
        when(preparedStatement6.executeQuery()).thenReturn(resultSet5);
        doNothing().when(preparedStatement6).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement6).close();
        Connection conn6 = mock(Connection.class);
        when(conn6.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement6);
        AppointmentDBDao appointmentDao = new AppointmentDBDao(conn2, patientDao,
                new ScheduleDBDao(conn5, new AuthUserDBDao(conn6)));

        AuthUserDBDao authUserDao = new AuthUserDBDao(mock(Connection.class));
        Connection conn7 = mock(Connection.class);

        // Act
        AppointmentTbl actualCancelAppointmentResult = (new ServiceImpl(authUserDao,
                new PatientDBDao(conn7, new AuthUserDBDao(mock(Connection.class))), scheduleDao, appointmentDao))
                .cancelAppointment(1L);

        // Assert
        verify(conn2, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(conn4).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn6).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn3).prepareStatement(eq("SELECT * FROM patient_tbl WHERE PatientId = ?"));
        verify(conn5).prepareStatement(eq("SELECT * FROM schedule_tbl WHERE ScheduleId = ?"));
        verify(conn).prepareStatement(eq(
                "UPDATE schedule_tbl SET DoctorId = ?, scheduleDate = ?, StartTime = ?, EndTime = ?, scheduleStatus = ?, unavailableReason = ? WHERE ScheduleId = ?"));
        verify(date).toLocalDate();
        verify(preparedStatement2).executeQuery();
        verify(preparedStatement3).executeQuery();
        verify(preparedStatement4).executeQuery();
        verify(preparedStatement5).executeQuery();
        verify(preparedStatement6).executeQuery();
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement2).executeUpdate();
        verify(preparedStatement).setDate(eq(2), isA(Date.class));
        verify(preparedStatement).setInt(eq(5), eq(1));
        verify(preparedStatement2).setInt(eq(6), eq(4));
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), eq(1L));
        verify(preparedStatement2, atLeast(1)).setLong(anyInt(), eq(1L));
        verify(preparedStatement3).setLong(eq(1), eq(1L));
        verify(preparedStatement4).setLong(eq(1), eq(1L));
        verify(preparedStatement5).setLong(eq(1), eq(1L));
        verify(preparedStatement6).setLong(eq(1), eq(1L));
        verify(preparedStatement2, atLeast(1)).setString(anyInt(), eq("String"));
        verify(preparedStatement).setString(eq(6), eq("String"));
        verify(preparedStatement, atLeast(1)).setTime(anyInt(), isA(Time.class));
        verify(resultSet4).getDate(eq("scheduleDate"));
        verify(resultSet).getInt(eq("appointmentStatus"));
        verify(resultSet4).getInt(eq("scheduleStatus"));
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet4).getLong(eq("DoctorId"));
        verify(resultSet3).getLong(eq("UserId"));
        verify(resultSet5).getLong(eq("UserId"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet3, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet5, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet4).getString(eq("unavailabilityReason"));
        verify(resultSet4, atLeast(1)).getTime(Mockito.<String>any());
        verify(resultSet).next();
        verify(resultSet2).next();
        verify(resultSet3).next();
        verify(resultSet4).next();
        verify(resultSet5).next();
        verify(preparedStatement).close();
        verify(preparedStatement3).close();
        verify(preparedStatement4).close();
        verify(preparedStatement5).close();
        verify(preparedStatement6).close();
        verify(preparedStatement2, atLeast(1)).close();
        verify(time, atLeast(1)).toLocalTime();
        ScheduleTbl schedule = actualCancelAppointmentResult.getSchedule();
        LocalTime endTime = schedule.getEndTime();
        assertEquals("00:00", endTime.toString());
        LocalDate scheduleDate = schedule.getScheduleDate();
        assertEquals("1970-01-01", scheduleDate.toString());
        assertEquals("String", actualCancelAppointmentResult.getSufferingFromDisease());
        assertEquals("String", actualCancelAppointmentResult.getSuggestedMedicines());
        assertEquals("String", actualCancelAppointmentResult.getSuggestedTests());
        PatientTbl patient = actualCancelAppointmentResult.getPatient();
        AuthUserTbl addedByUser = patient.getAddedByUser();
        assertEquals("String", addedByUser.getEmail());
        assertEquals("String", addedByUser.getMobileNo());
        assertEquals("String", addedByUser.getName());
        assertEquals("String", addedByUser.getRole());
        assertEquals("String", addedByUser.getUsername());
        assertEquals("String", patient.getEmail());
        assertEquals("String", patient.getMobileNo());
        assertEquals("String", patient.getName());
        assertEquals("String", schedule.getUnavailabilityReason());
        assertEquals("b2ef230e7f4f315a28cdcc863028da31f7110f3209feb76e76fed0f37b3d8580", addedByUser.getPassword());
        assertEquals(1, schedule.getScheduleStatus());
        assertEquals(1L, actualCancelAppointmentResult.getAppointmentId());
        assertEquals(1L, addedByUser.getUserId());
        assertEquals(1L, patient.getPatientId());
        assertEquals(1L, schedule.getScheduleId());
        assertEquals(4, actualCancelAppointmentResult.getAppointmentStatus());
        assertEquals(addedByUser, schedule.getDoctor());
        assertSame(endTime, schedule.getStartTime());
        assertSame(ofResult, scheduleDate);
    }

    /**
     * Method under test: {@link ServiceImpl#cancelAppointment(long)}
     */
    @Test
    void testCancelAppointment2() throws DatabaseException, ServiceException, SQLException {
        // Arrange
        ScheduleDBDao scheduleDao = mock(ScheduleDBDao.class);
        when(scheduleDao.update(Mockito.<ScheduleTbl>any())).thenReturn(true);
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        ResultSet resultSet3 = mock(ResultSet.class);
        when(resultSet3.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet3.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet3.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement3 = mock(PreparedStatement.class);
        when(preparedStatement3.executeQuery()).thenReturn(resultSet3);
        doNothing().when(preparedStatement3).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement3).close();
        Connection conn3 = mock(Connection.class);
        when(conn3.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement3);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(conn3));

        Date date = mock(Date.class);
        LocalDate ofResult = LocalDate.of(1970, 1, 1);
        when(date.toLocalDate()).thenReturn(ofResult);
        Time time = mock(Time.class);
        when(time.toLocalTime()).thenReturn(LocalTime.MIDNIGHT);
        ResultSet resultSet4 = mock(ResultSet.class);
        when(resultSet4.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet4.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet4.getTime(Mockito.<String>any())).thenReturn(time);
        when(resultSet4.getDate(Mockito.<String>any())).thenReturn(date);
        when(resultSet4.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet4.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement4 = mock(PreparedStatement.class);
        when(preparedStatement4.executeQuery()).thenReturn(resultSet4);
        doNothing().when(preparedStatement4).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement4).close();
        Connection conn4 = mock(Connection.class);
        when(conn4.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement4);
        ResultSet resultSet5 = mock(ResultSet.class);
        when(resultSet5.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet5.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet5.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement5 = mock(PreparedStatement.class);
        when(preparedStatement5.executeQuery()).thenReturn(resultSet5);
        doNothing().when(preparedStatement5).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement5).close();
        Connection conn5 = mock(Connection.class);
        when(conn5.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement5);
        AppointmentDBDao appointmentDao = new AppointmentDBDao(conn, patientDao,
                new ScheduleDBDao(conn4, new AuthUserDBDao(conn5)));

        AuthUserDBDao authUserDao = new AuthUserDBDao(mock(Connection.class));
        Connection conn6 = mock(Connection.class);

        // Act
        AppointmentTbl actualCancelAppointmentResult = (new ServiceImpl(authUserDao,
                new PatientDBDao(conn6, new AuthUserDBDao(mock(Connection.class))), scheduleDao, appointmentDao))
                .cancelAppointment(1L);

        // Assert
        verify(scheduleDao).update(isA(ScheduleTbl.class));
        verify(conn, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(conn3).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn5).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn2).prepareStatement(eq("SELECT * FROM patient_tbl WHERE PatientId = ?"));
        verify(conn4).prepareStatement(eq("SELECT * FROM schedule_tbl WHERE ScheduleId = ?"));
        verify(date).toLocalDate();
        verify(preparedStatement).executeQuery();
        verify(preparedStatement2).executeQuery();
        verify(preparedStatement3).executeQuery();
        verify(preparedStatement4).executeQuery();
        verify(preparedStatement5).executeQuery();
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).setInt(eq(6), eq(4));
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), eq(1L));
        verify(preparedStatement2).setLong(eq(1), eq(1L));
        verify(preparedStatement3).setLong(eq(1), eq(1L));
        verify(preparedStatement4).setLong(eq(1), eq(1L));
        verify(preparedStatement5).setLong(eq(1), eq(1L));
        verify(preparedStatement, atLeast(1)).setString(anyInt(), eq("String"));
        verify(resultSet4).getDate(eq("scheduleDate"));
        verify(resultSet).getInt(eq("appointmentStatus"));
        verify(resultSet4).getInt(eq("scheduleStatus"));
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet4).getLong(eq("DoctorId"));
        verify(resultSet3).getLong(eq("UserId"));
        verify(resultSet5).getLong(eq("UserId"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet3, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet5, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet4).getString(eq("unavailabilityReason"));
        verify(resultSet4, atLeast(1)).getTime(Mockito.<String>any());
        verify(resultSet).next();
        verify(resultSet2).next();
        verify(resultSet3).next();
        verify(resultSet4).next();
        verify(resultSet5).next();
        verify(preparedStatement2).close();
        verify(preparedStatement3).close();
        verify(preparedStatement4).close();
        verify(preparedStatement5).close();
        verify(preparedStatement, atLeast(1)).close();
        verify(time, atLeast(1)).toLocalTime();
        ScheduleTbl schedule = actualCancelAppointmentResult.getSchedule();
        LocalTime endTime = schedule.getEndTime();
        assertEquals("00:00", endTime.toString());
        LocalDate scheduleDate = schedule.getScheduleDate();
        assertEquals("1970-01-01", scheduleDate.toString());
        assertEquals("String", actualCancelAppointmentResult.getSufferingFromDisease());
        assertEquals("String", actualCancelAppointmentResult.getSuggestedMedicines());
        assertEquals("String", actualCancelAppointmentResult.getSuggestedTests());
        PatientTbl patient = actualCancelAppointmentResult.getPatient();
        AuthUserTbl addedByUser = patient.getAddedByUser();
        assertEquals("String", addedByUser.getEmail());
        assertEquals("String", addedByUser.getMobileNo());
        assertEquals("String", addedByUser.getName());
        assertEquals("String", addedByUser.getRole());
        assertEquals("String", addedByUser.getUsername());
        assertEquals("String", patient.getEmail());
        assertEquals("String", patient.getMobileNo());
        assertEquals("String", patient.getName());
        assertEquals("String", schedule.getUnavailabilityReason());
        assertEquals("b2ef230e7f4f315a28cdcc863028da31f7110f3209feb76e76fed0f37b3d8580", addedByUser.getPassword());
        assertEquals(1, schedule.getScheduleStatus());
        assertEquals(1L, actualCancelAppointmentResult.getAppointmentId());
        assertEquals(1L, addedByUser.getUserId());
        assertEquals(1L, patient.getPatientId());
        assertEquals(1L, schedule.getScheduleId());
        assertEquals(4, actualCancelAppointmentResult.getAppointmentStatus());
        assertEquals(addedByUser, schedule.getDoctor());
        assertSame(endTime, schedule.getStartTime());
        assertSame(ofResult, scheduleDate);
    }

    /**
     * Method under test: {@link ServiceImpl#cancelAppointment(long)}
     */
    @Test
    void testCancelAppointment3() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(false).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        ResultSet resultSet3 = mock(ResultSet.class);
        when(resultSet3.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement3 = mock(PreparedStatement.class);
        when(preparedStatement3.executeQuery()).thenReturn(resultSet3);
        doNothing().when(preparedStatement3).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement3).close();
        Connection conn3 = mock(Connection.class);
        when(conn3.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement3);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(conn3));

        ResultSet resultSet4 = mock(ResultSet.class);
        when(resultSet4.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement4 = mock(PreparedStatement.class);
        when(preparedStatement4.executeQuery()).thenReturn(resultSet4);
        doNothing().when(preparedStatement4).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement4).close();
        Connection conn4 = mock(Connection.class);
        when(conn4.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement4);
        ResultSet resultSet5 = mock(ResultSet.class);
        when(resultSet5.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement5 = mock(PreparedStatement.class);
        when(preparedStatement5.executeQuery()).thenReturn(resultSet5);
        doNothing().when(preparedStatement5).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement5).close();
        Connection conn5 = mock(Connection.class);
        when(conn5.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement5);
        AppointmentDBDao appointmentDao = new AppointmentDBDao(conn, patientDao,
                new ScheduleDBDao(conn4, new AuthUserDBDao(conn5)));

        AuthUserDBDao authUserDao = new AuthUserDBDao(mock(Connection.class));
        Connection conn6 = mock(Connection.class);

        // Act and Assert
        assertThrows(ServiceException.class,
                () -> (new ServiceImpl(authUserDao, new PatientDBDao(conn6, new AuthUserDBDao(mock(Connection.class))),
                        mock(ScheduleDBDao.class), appointmentDao)).cancelAppointment(1L));
        verify(conn).prepareStatement(eq("SELECT * FROM appointment_tbl WHERE AppointmentId = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link ServiceImpl#cancelAppointment(long)}
     */
    @Test
    void testCancelAppointment4() throws DatabaseException, ServiceException, SQLException {
        // Arrange
        ScheduleDBDao scheduleDao = mock(ScheduleDBDao.class);
        when(scheduleDao.update(Mockito.<ScheduleTbl>any())).thenReturn(true);
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        ResultSet resultSet3 = mock(ResultSet.class);
        when(resultSet3.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet3.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet3.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement3 = mock(PreparedStatement.class);
        when(preparedStatement3.executeQuery()).thenReturn(resultSet3);
        doNothing().when(preparedStatement3).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement3).close();
        Connection conn3 = mock(Connection.class);
        when(conn3.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement3);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(conn3));

        Date date = mock(Date.class);
        LocalDate ofResult = LocalDate.of(1970, 1, 1);
        when(date.toLocalDate()).thenReturn(ofResult);
        Time time = mock(Time.class);
        when(time.toLocalTime()).thenReturn(LocalTime.MIDNIGHT);
        ResultSet resultSet4 = mock(ResultSet.class);
        when(resultSet4.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet4.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet4.getTime(Mockito.<String>any())).thenReturn(time);
        when(resultSet4.getDate(Mockito.<String>any())).thenReturn(date);
        when(resultSet4.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet4.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement4 = mock(PreparedStatement.class);
        when(preparedStatement4.executeQuery()).thenReturn(resultSet4);
        doNothing().when(preparedStatement4).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement4).close();
        Connection conn4 = mock(Connection.class);
        when(conn4.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement4);
        ResultSet resultSet5 = mock(ResultSet.class);
        when(resultSet5.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet5.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet5.next()).thenReturn(false).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement5 = mock(PreparedStatement.class);
        when(preparedStatement5.executeQuery()).thenReturn(resultSet5);
        doNothing().when(preparedStatement5).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement5).close();
        Connection conn5 = mock(Connection.class);
        when(conn5.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement5);
        AppointmentDBDao appointmentDao = new AppointmentDBDao(conn, patientDao,
                new ScheduleDBDao(conn4, new AuthUserDBDao(conn5)));

        AuthUserDBDao authUserDao = new AuthUserDBDao(mock(Connection.class));
        Connection conn6 = mock(Connection.class);

        // Act
        AppointmentTbl actualCancelAppointmentResult = (new ServiceImpl(authUserDao,
                new PatientDBDao(conn6, new AuthUserDBDao(mock(Connection.class))), scheduleDao, appointmentDao))
                .cancelAppointment(1L);

        // Assert
        verify(scheduleDao).update(isA(ScheduleTbl.class));
        verify(conn, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(conn3).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn5).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn2).prepareStatement(eq("SELECT * FROM patient_tbl WHERE PatientId = ?"));
        verify(conn4).prepareStatement(eq("SELECT * FROM schedule_tbl WHERE ScheduleId = ?"));
        verify(date).toLocalDate();
        verify(preparedStatement).executeQuery();
        verify(preparedStatement2).executeQuery();
        verify(preparedStatement3).executeQuery();
        verify(preparedStatement4).executeQuery();
        verify(preparedStatement5).executeQuery();
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).setInt(eq(6), eq(4));
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), eq(1L));
        verify(preparedStatement2).setLong(eq(1), eq(1L));
        verify(preparedStatement3).setLong(eq(1), eq(1L));
        verify(preparedStatement4).setLong(eq(1), eq(1L));
        verify(preparedStatement5).setLong(eq(1), eq(1L));
        verify(preparedStatement, atLeast(1)).setString(anyInt(), eq("String"));
        verify(resultSet4).getDate(eq("scheduleDate"));
        verify(resultSet).getInt(eq("appointmentStatus"));
        verify(resultSet4).getInt(eq("scheduleStatus"));
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet4).getLong(eq("DoctorId"));
        verify(resultSet3).getLong(eq("UserId"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet3, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet4).getString(eq("unavailabilityReason"));
        verify(resultSet4, atLeast(1)).getTime(Mockito.<String>any());
        verify(resultSet).next();
        verify(resultSet2).next();
        verify(resultSet3).next();
        verify(resultSet4).next();
        verify(resultSet5).next();
        verify(preparedStatement2).close();
        verify(preparedStatement3).close();
        verify(preparedStatement4).close();
        verify(preparedStatement5).close();
        verify(preparedStatement, atLeast(1)).close();
        verify(time, atLeast(1)).toLocalTime();
        ScheduleTbl schedule = actualCancelAppointmentResult.getSchedule();
        LocalTime endTime = schedule.getEndTime();
        assertEquals("00:00", endTime.toString());
        LocalDate scheduleDate = schedule.getScheduleDate();
        assertEquals("1970-01-01", scheduleDate.toString());
        assertEquals("String", actualCancelAppointmentResult.getSufferingFromDisease());
        assertEquals("String", actualCancelAppointmentResult.getSuggestedMedicines());
        assertEquals("String", actualCancelAppointmentResult.getSuggestedTests());
        PatientTbl patient = actualCancelAppointmentResult.getPatient();
        AuthUserTbl addedByUser = patient.getAddedByUser();
        assertEquals("String", addedByUser.getEmail());
        assertEquals("String", addedByUser.getMobileNo());
        assertEquals("String", addedByUser.getName());
        assertEquals("String", addedByUser.getRole());
        assertEquals("String", addedByUser.getUsername());
        assertEquals("String", patient.getEmail());
        assertEquals("String", patient.getMobileNo());
        assertEquals("String", patient.getName());
        assertEquals("String", schedule.getUnavailabilityReason());
        assertEquals("b2ef230e7f4f315a28cdcc863028da31f7110f3209feb76e76fed0f37b3d8580", addedByUser.getPassword());
        assertNull(schedule.getDoctor());
        assertEquals(1, schedule.getScheduleStatus());
        assertEquals(1L, actualCancelAppointmentResult.getAppointmentId());
        assertEquals(1L, addedByUser.getUserId());
        assertEquals(1L, patient.getPatientId());
        assertEquals(1L, schedule.getScheduleId());
        assertEquals(4, actualCancelAppointmentResult.getAppointmentStatus());
        assertSame(endTime, schedule.getStartTime());
        assertSame(ofResult, scheduleDate);
    }

    /**
     * Method under test: {@link ServiceImpl#cancelAppointment(long)}
     */
    @Test
    void testCancelAppointment5() throws DatabaseException, ServiceException {
        // Arrange
        ScheduleDBDao scheduleDao = mock(ScheduleDBDao.class);
        when(scheduleDao.update(Mockito.<ScheduleTbl>any())).thenReturn(true);
        AppointmentDBDao appointmentDao = mock(AppointmentDBDao.class);
        when(appointmentDao.update(Mockito.<AppointmentTbl>any())).thenReturn(true);
        PatientTbl patient = new PatientTbl("Name", "Mobile No", new AuthUserTbl("janedoe", "iloveyou", 1L));

        AuthUserTbl doctor = new AuthUserTbl("janedoe", "iloveyou", 1L);

        AppointmentTbl appointmentTbl = new AppointmentTbl(1L, patient,
                new ScheduleTbl(doctor, LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, LocalTime.MIDNIGHT, 1), 1);

        when(appointmentDao.findById(anyLong())).thenReturn(appointmentTbl);
        AuthUserDBDao authUserDao = new AuthUserDBDao(mock(Connection.class));
        Connection conn = mock(Connection.class);

        // Act
        AppointmentTbl actualCancelAppointmentResult = (new ServiceImpl(authUserDao,
                new PatientDBDao(conn, new AuthUserDBDao(mock(Connection.class))), scheduleDao, appointmentDao))
                .cancelAppointment(1L);

        // Assert
        verify(appointmentDao).findById(eq(1L));
        verify(appointmentDao).update(isA(AppointmentTbl.class));
        verify(scheduleDao).update(isA(ScheduleTbl.class));
        assertSame(appointmentTbl, actualCancelAppointmentResult);
    }

    /**
     * Method under test: {@link ServiceImpl#cancelAppointment(long)}
     */
    @Test
    void testCancelAppointment6() throws DatabaseException, ServiceException {
        // Arrange
        AppointmentDBDao appointmentDao = mock(AppointmentDBDao.class);
        when(appointmentDao.update(Mockito.<AppointmentTbl>any())).thenThrow(new DatabaseException("An error occurred"));
        PatientTbl patient = new PatientTbl("Name", "Mobile No", new AuthUserTbl("janedoe", "iloveyou", 1L));

        AuthUserTbl doctor = new AuthUserTbl("janedoe", "iloveyou", 1L);

        when(appointmentDao.findById(anyLong())).thenReturn(new AppointmentTbl(1L, patient,
                new ScheduleTbl(doctor, LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, LocalTime.MIDNIGHT, 1), 1));
        AuthUserDBDao authUserDao = new AuthUserDBDao(mock(Connection.class));
        Connection conn = mock(Connection.class);

        // Act and Assert
        assertThrows(ServiceException.class,
                () -> (new ServiceImpl(authUserDao, new PatientDBDao(conn, new AuthUserDBDao(mock(Connection.class))),
                        mock(ScheduleDBDao.class), appointmentDao)).cancelAppointment(1L));
        verify(appointmentDao).findById(eq(1L));
        verify(appointmentDao).update(isA(AppointmentTbl.class));
    }

    /**
     * Method under test: {@link ServiceImpl#cancelAppointment(long)}
     */
    @Test
    void testCancelAppointment7() throws DatabaseException, ServiceException {
        // Arrange
        ScheduleDBDao scheduleDao = mock(ScheduleDBDao.class);
        when(scheduleDao.update(Mockito.<ScheduleTbl>any())).thenReturn(true);
        AppointmentTbl appointmentTbl = mock(AppointmentTbl.class);
        AuthUserTbl doctor = new AuthUserTbl("janedoe", "iloveyou", 1L);

        when(appointmentTbl.getSchedule())
                .thenReturn(new ScheduleTbl(doctor, LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, LocalTime.MIDNIGHT, 1));
        doNothing().when(appointmentTbl).setAppointmentStatus(anyInt());
        AppointmentDBDao appointmentDao = mock(AppointmentDBDao.class);
        when(appointmentDao.update(Mockito.<AppointmentTbl>any())).thenReturn(true);
        when(appointmentDao.findById(anyLong())).thenReturn(appointmentTbl);
        AuthUserDBDao authUserDao = new AuthUserDBDao(mock(Connection.class));
        Connection conn = mock(Connection.class);

        // Act
        (new ServiceImpl(authUserDao, new PatientDBDao(conn, new AuthUserDBDao(mock(Connection.class))), scheduleDao,
                appointmentDao)).cancelAppointment(1L);

        // Assert
        verify(appointmentDao).findById(eq(1L));
        verify(appointmentDao).update(isA(AppointmentTbl.class));
        verify(scheduleDao).update(isA(ScheduleTbl.class));
        verify(appointmentTbl).getSchedule();
        verify(appointmentTbl).setAppointmentStatus(eq(4));
    }

    /**
     * Method under test: {@link ServiceImpl#cancelAppointment(long, AuthUserTbl)}
     */
    @Test
    void testCancelAppointment8() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        ResultSet resultSet3 = mock(ResultSet.class);
        when(resultSet3.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet3.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet3.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement3 = mock(PreparedStatement.class);
        when(preparedStatement3.executeQuery()).thenReturn(resultSet3);
        doNothing().when(preparedStatement3).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement3).close();
        Connection conn3 = mock(Connection.class);
        when(conn3.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement3);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(conn3));

        Date date = mock(Date.class);
        when(date.toLocalDate()).thenReturn(LocalDate.of(1970, 1, 1));
        Time time = mock(Time.class);
        when(time.toLocalTime()).thenReturn(LocalTime.MIDNIGHT);
        ResultSet resultSet4 = mock(ResultSet.class);
        when(resultSet4.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet4.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet4.getTime(Mockito.<String>any())).thenReturn(time);
        when(resultSet4.getDate(Mockito.<String>any())).thenReturn(date);
        when(resultSet4.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet4.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement4 = mock(PreparedStatement.class);
        when(preparedStatement4.executeQuery()).thenReturn(resultSet4);
        doNothing().when(preparedStatement4).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement4).close();
        Connection conn4 = mock(Connection.class);
        when(conn4.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement4);
        ResultSet resultSet5 = mock(ResultSet.class);
        when(resultSet5.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet5.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet5.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement5 = mock(PreparedStatement.class);
        when(preparedStatement5.executeQuery()).thenReturn(resultSet5);
        doNothing().when(preparedStatement5).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement5).close();
        Connection conn5 = mock(Connection.class);
        when(conn5.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement5);
        AppointmentDBDao appointmentDao = new AppointmentDBDao(conn, patientDao,
                new ScheduleDBDao(conn4, new AuthUserDBDao(conn5)));

        AuthUserDBDao authUserDao = new AuthUserDBDao(mock(Connection.class));
        Connection conn6 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn6, new AuthUserDBDao(mock(Connection.class)));

        Connection conn7 = mock(Connection.class);
        ServiceImpl serviceImpl = new ServiceImpl(authUserDao, patientDao2,
                new ScheduleDBDao(conn7, new AuthUserDBDao(mock(Connection.class))), appointmentDao);

        // Act and Assert
        assertThrows(ServiceException.class,
                () -> serviceImpl.cancelAppointment(1L, new AuthUserTbl("janedoe", "iloveyou", 1L)));
        verify(conn).prepareStatement(eq("SELECT * FROM appointment_tbl WHERE AppointmentId = ?"));
        verify(conn3).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn5).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn2).prepareStatement(eq("SELECT * FROM patient_tbl WHERE PatientId = ?"));
        verify(conn4).prepareStatement(eq("SELECT * FROM schedule_tbl WHERE ScheduleId = ?"));
        verify(date).toLocalDate();
        verify(preparedStatement).executeQuery();
        verify(preparedStatement2).executeQuery();
        verify(preparedStatement3).executeQuery();
        verify(preparedStatement4).executeQuery();
        verify(preparedStatement5).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(preparedStatement2).setLong(eq(1), eq(1L));
        verify(preparedStatement3).setLong(eq(1), eq(1L));
        verify(preparedStatement4).setLong(eq(1), eq(1L));
        verify(preparedStatement5).setLong(eq(1), eq(1L));
        verify(resultSet4).getDate(eq("scheduleDate"));
        verify(resultSet).getInt(eq("appointmentStatus"));
        verify(resultSet4).getInt(eq("scheduleStatus"));
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet4).getLong(eq("DoctorId"));
        verify(resultSet3).getLong(eq("UserId"));
        verify(resultSet5).getLong(eq("UserId"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet3, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet5, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet4).getString(eq("unavailabilityReason"));
        verify(resultSet4, atLeast(1)).getTime(Mockito.<String>any());
        verify(resultSet).next();
        verify(resultSet2).next();
        verify(resultSet3).next();
        verify(resultSet4).next();
        verify(resultSet5).next();
        verify(preparedStatement).close();
        verify(preparedStatement2).close();
        verify(preparedStatement3).close();
        verify(preparedStatement4).close();
        verify(preparedStatement5).close();
        verify(time, atLeast(1)).toLocalTime();
    }

    /**
     * Method under test: {@link ServiceImpl#cancelAppointment(long, AuthUserTbl)}
     */
    @Test
    void testCancelAppointment9() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(false).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        ResultSet resultSet3 = mock(ResultSet.class);
        when(resultSet3.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement3 = mock(PreparedStatement.class);
        when(preparedStatement3.executeQuery()).thenReturn(resultSet3);
        doNothing().when(preparedStatement3).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement3).close();
        Connection conn3 = mock(Connection.class);
        when(conn3.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement3);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(conn3));

        ResultSet resultSet4 = mock(ResultSet.class);
        when(resultSet4.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement4 = mock(PreparedStatement.class);
        when(preparedStatement4.executeQuery()).thenReturn(resultSet4);
        doNothing().when(preparedStatement4).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement4).close();
        Connection conn4 = mock(Connection.class);
        when(conn4.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement4);
        ResultSet resultSet5 = mock(ResultSet.class);
        when(resultSet5.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement5 = mock(PreparedStatement.class);
        when(preparedStatement5.executeQuery()).thenReturn(resultSet5);
        doNothing().when(preparedStatement5).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement5).close();
        Connection conn5 = mock(Connection.class);
        when(conn5.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement5);
        AppointmentDBDao appointmentDao = new AppointmentDBDao(conn, patientDao,
                new ScheduleDBDao(conn4, new AuthUserDBDao(conn5)));

        AuthUserDBDao authUserDao = new AuthUserDBDao(mock(Connection.class));
        Connection conn6 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn6, new AuthUserDBDao(mock(Connection.class)));

        Connection conn7 = mock(Connection.class);
        ServiceImpl serviceImpl = new ServiceImpl(authUserDao, patientDao2,
                new ScheduleDBDao(conn7, new AuthUserDBDao(mock(Connection.class))), appointmentDao);

        // Act and Assert
        assertThrows(ServiceException.class,
                () -> serviceImpl.cancelAppointment(1L, new AuthUserTbl("janedoe", "iloveyou", 1L)));
        verify(conn).prepareStatement(eq("SELECT * FROM appointment_tbl WHERE AppointmentId = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link ServiceImpl#cancelAppointment(long, AuthUserTbl)}
     */
    @Test
    void testCancelAppointment10() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        ResultSet resultSet3 = mock(ResultSet.class);
        when(resultSet3.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet3.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet3.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement3 = mock(PreparedStatement.class);
        when(preparedStatement3.executeQuery()).thenReturn(resultSet3);
        doNothing().when(preparedStatement3).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement3).close();
        Connection conn3 = mock(Connection.class);
        when(conn3.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement3);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(conn3));

        Date date = mock(Date.class);
        when(date.toLocalDate()).thenReturn(LocalDate.of(1970, 1, 1));
        Time time = mock(Time.class);
        when(time.toLocalTime()).thenReturn(LocalTime.MIDNIGHT);
        ResultSet resultSet4 = mock(ResultSet.class);
        when(resultSet4.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet4.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet4.getTime(Mockito.<String>any())).thenReturn(time);
        when(resultSet4.getDate(Mockito.<String>any())).thenReturn(date);
        when(resultSet4.getLong(Mockito.<String>any())).thenReturn(32L);
        when(resultSet4.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement4 = mock(PreparedStatement.class);
        when(preparedStatement4.executeQuery()).thenReturn(resultSet4);
        doNothing().when(preparedStatement4).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement4).close();
        Connection conn4 = mock(Connection.class);
        when(conn4.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement4);
        ResultSet resultSet5 = mock(ResultSet.class);
        when(resultSet5.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet5.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet5.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement5 = mock(PreparedStatement.class);
        when(preparedStatement5.executeQuery()).thenReturn(resultSet5);
        doNothing().when(preparedStatement5).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement5).close();
        Connection conn5 = mock(Connection.class);
        when(conn5.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement5);
        AppointmentDBDao appointmentDao = new AppointmentDBDao(conn, patientDao,
                new ScheduleDBDao(conn4, new AuthUserDBDao(conn5)));

        AuthUserDBDao authUserDao = new AuthUserDBDao(mock(Connection.class));
        Connection conn6 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn6, new AuthUserDBDao(mock(Connection.class)));

        Connection conn7 = mock(Connection.class);
        ServiceImpl serviceImpl = new ServiceImpl(authUserDao, patientDao2,
                new ScheduleDBDao(conn7, new AuthUserDBDao(mock(Connection.class))), appointmentDao);

        // Act and Assert
        assertThrows(ServiceException.class,
                () -> serviceImpl.cancelAppointment(1L, new AuthUserTbl("janedoe", "iloveyou", 1L)));
        verify(conn).prepareStatement(eq("SELECT * FROM appointment_tbl WHERE AppointmentId = ?"));
        verify(conn3).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn5).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn2).prepareStatement(eq("SELECT * FROM patient_tbl WHERE PatientId = ?"));
        verify(conn4).prepareStatement(eq("SELECT * FROM schedule_tbl WHERE ScheduleId = ?"));
        verify(date).toLocalDate();
        verify(preparedStatement).executeQuery();
        verify(preparedStatement2).executeQuery();
        verify(preparedStatement3).executeQuery();
        verify(preparedStatement4).executeQuery();
        verify(preparedStatement5).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(preparedStatement2).setLong(eq(1), eq(1L));
        verify(preparedStatement3).setLong(eq(1), eq(1L));
        verify(preparedStatement4).setLong(eq(1), eq(1L));
        verify(preparedStatement5).setLong(eq(1), eq(32L));
        verify(resultSet4).getDate(eq("scheduleDate"));
        verify(resultSet).getInt(eq("appointmentStatus"));
        verify(resultSet4).getInt(eq("scheduleStatus"));
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet4).getLong(eq("DoctorId"));
        verify(resultSet3).getLong(eq("UserId"));
        verify(resultSet5).getLong(eq("UserId"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet3, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet5, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet4).getString(eq("unavailabilityReason"));
        verify(resultSet4, atLeast(1)).getTime(Mockito.<String>any());
        verify(resultSet).next();
        verify(resultSet2).next();
        verify(resultSet3).next();
        verify(resultSet4).next();
        verify(resultSet5).next();
        verify(preparedStatement).close();
        verify(preparedStatement2).close();
        verify(preparedStatement3).close();
        verify(preparedStatement4).close();
        verify(preparedStatement5).close();
        verify(time, atLeast(1)).toLocalTime();
    }

    /**
     * Method under test: {@link ServiceImpl#cancelAppointment(long, AuthUserTbl)}
     */
    @Test
    void testCancelAppointment11() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        ResultSet resultSet3 = mock(ResultSet.class);
        when(resultSet3.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet3.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet3.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement3 = mock(PreparedStatement.class);
        when(preparedStatement3.executeQuery()).thenReturn(resultSet3);
        doNothing().when(preparedStatement3).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement3).close();
        Connection conn3 = mock(Connection.class);
        when(conn3.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement3);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(conn3));

        Date date = mock(Date.class);
        when(date.toLocalDate()).thenReturn(LocalDate.of(1970, 1, 1));
        Time time = mock(Time.class);
        when(time.toLocalTime()).thenReturn(LocalTime.MIDNIGHT);
        ResultSet resultSet4 = mock(ResultSet.class);
        when(resultSet4.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet4.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet4.getTime(Mockito.<String>any())).thenReturn(time);
        when(resultSet4.getDate(Mockito.<String>any())).thenReturn(date);
        when(resultSet4.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet4.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement4 = mock(PreparedStatement.class);
        when(preparedStatement4.executeQuery()).thenReturn(resultSet4);
        doNothing().when(preparedStatement4).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement4).close();
        Connection conn4 = mock(Connection.class);
        when(conn4.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement4);
        ResultSet resultSet5 = mock(ResultSet.class);
        when(resultSet5.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet5.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet5.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement5 = mock(PreparedStatement.class);
        when(preparedStatement5.executeQuery()).thenReturn(resultSet5);
        doNothing().when(preparedStatement5).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement5).close();
        Connection conn5 = mock(Connection.class);
        when(conn5.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement5);
        AppointmentDBDao appointmentDao = new AppointmentDBDao(conn, patientDao,
                new ScheduleDBDao(conn4, new AuthUserDBDao(conn5)));

        AuthUserDBDao authUserDao = new AuthUserDBDao(mock(Connection.class));
        Connection conn6 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn6, new AuthUserDBDao(mock(Connection.class)));

        Connection conn7 = mock(Connection.class);

        // Act and Assert
        assertThrows(ServiceException.class,
                () -> (new ServiceImpl(authUserDao, patientDao2,
                        new ScheduleDBDao(conn7, new AuthUserDBDao(mock(Connection.class))), appointmentDao)).cancelAppointment(1L,
                        null));
        verify(conn).prepareStatement(eq("SELECT * FROM appointment_tbl WHERE AppointmentId = ?"));
        verify(conn3).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn5).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn2).prepareStatement(eq("SELECT * FROM patient_tbl WHERE PatientId = ?"));
        verify(conn4).prepareStatement(eq("SELECT * FROM schedule_tbl WHERE ScheduleId = ?"));
        verify(date).toLocalDate();
        verify(preparedStatement).executeQuery();
        verify(preparedStatement2).executeQuery();
        verify(preparedStatement3).executeQuery();
        verify(preparedStatement4).executeQuery();
        verify(preparedStatement5).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(preparedStatement2).setLong(eq(1), eq(1L));
        verify(preparedStatement3).setLong(eq(1), eq(1L));
        verify(preparedStatement4).setLong(eq(1), eq(1L));
        verify(preparedStatement5).setLong(eq(1), eq(1L));
        verify(resultSet4).getDate(eq("scheduleDate"));
        verify(resultSet).getInt(eq("appointmentStatus"));
        verify(resultSet4).getInt(eq("scheduleStatus"));
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet4).getLong(eq("DoctorId"));
        verify(resultSet3).getLong(eq("UserId"));
        verify(resultSet5).getLong(eq("UserId"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet3, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet5, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet4).getString(eq("unavailabilityReason"));
        verify(resultSet4, atLeast(1)).getTime(Mockito.<String>any());
        verify(resultSet).next();
        verify(resultSet2).next();
        verify(resultSet3).next();
        verify(resultSet4).next();
        verify(resultSet5).next();
        verify(preparedStatement).close();
        verify(preparedStatement2).close();
        verify(preparedStatement3).close();
        verify(preparedStatement4).close();
        verify(preparedStatement5).close();
        verify(time, atLeast(1)).toLocalTime();
    }

    /**
     * Method under test: {@link ServiceImpl#searchUser(long)}
     */
    @Test
    void testSearchUser() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        AuthUserDBDao authUserDao = new AuthUserDBDao(conn);
        Connection conn2 = mock(Connection.class);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(mock(Connection.class)));

        Connection conn3 = mock(Connection.class);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn3, new AuthUserDBDao(mock(Connection.class)));

        Connection conn4 = mock(Connection.class);
        Connection conn5 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn5, new AuthUserDBDao(mock(Connection.class)));

        Connection conn6 = mock(Connection.class);

        // Act
        AuthUserTbl actualSearchUserResult = (new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn4, patientDao2, new ScheduleDBDao(conn6, new AuthUserDBDao(mock(Connection.class))))))
                .searchUser(1L);

        // Assert
        verify(conn).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(resultSet).getLong(eq("UserId"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet).next();
        verify(preparedStatement).close();
        assertEquals("String", actualSearchUserResult.getEmail());
        assertEquals("String", actualSearchUserResult.getMobileNo());
        assertEquals("String", actualSearchUserResult.getName());
        assertEquals("String", actualSearchUserResult.getRole());
        assertEquals("String", actualSearchUserResult.getUsername());
        assertEquals("b2ef230e7f4f315a28cdcc863028da31f7110f3209feb76e76fed0f37b3d8580",
                actualSearchUserResult.getPassword());
        assertEquals(1L, actualSearchUserResult.getUserId());
    }

    /**
     * Method under test: {@link ServiceImpl#searchUser(long)}
     */
    @Test
    void testSearchUser2() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(false).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        AuthUserDBDao authUserDao = new AuthUserDBDao(conn);
        Connection conn2 = mock(Connection.class);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(mock(Connection.class)));

        Connection conn3 = mock(Connection.class);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn3, new AuthUserDBDao(mock(Connection.class)));

        Connection conn4 = mock(Connection.class);
        Connection conn5 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn5, new AuthUserDBDao(mock(Connection.class)));

        Connection conn6 = mock(Connection.class);

        // Act and Assert
        assertThrows(ServiceException.class, () -> (new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn4, patientDao2, new ScheduleDBDao(conn6, new AuthUserDBDao(mock(Connection.class))))))
                .searchUser(1L));
        verify(conn).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link ServiceImpl#searchPatient(long)}
     */
    @Test
    void testSearchPatient() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        PatientDBDao patientDao = new PatientDBDao(conn, new AuthUserDBDao(conn2));

        AuthUserDBDao authUserDao = new AuthUserDBDao(mock(Connection.class));
        Connection conn3 = mock(Connection.class);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn3, new AuthUserDBDao(mock(Connection.class)));

        Connection conn4 = mock(Connection.class);
        Connection conn5 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn5, new AuthUserDBDao(mock(Connection.class)));

        Connection conn6 = mock(Connection.class);

        // Act
        PatientTbl actualSearchPatientResult = (new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn4, patientDao2, new ScheduleDBDao(conn6, new AuthUserDBDao(mock(Connection.class))))))
                .searchPatient(1L);

        // Assert
        verify(conn2).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn).prepareStatement(eq("SELECT * FROM patient_tbl WHERE PatientId = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement2).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(preparedStatement2).setLong(eq(1), eq(1L));
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet2).getLong(eq("UserId"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet).next();
        verify(resultSet2).next();
        verify(preparedStatement).close();
        verify(preparedStatement2).close();
        AuthUserTbl addedByUser = actualSearchPatientResult.getAddedByUser();
        assertEquals("String", addedByUser.getEmail());
        assertEquals("String", addedByUser.getMobileNo());
        assertEquals("String", addedByUser.getName());
        assertEquals("String", addedByUser.getRole());
        assertEquals("String", addedByUser.getUsername());
        assertEquals("String", actualSearchPatientResult.getEmail());
        assertEquals("String", actualSearchPatientResult.getMobileNo());
        assertEquals("String", actualSearchPatientResult.getName());
        assertEquals("b2ef230e7f4f315a28cdcc863028da31f7110f3209feb76e76fed0f37b3d8580", addedByUser.getPassword());
        assertEquals(1L, addedByUser.getUserId());
        assertEquals(1L, actualSearchPatientResult.getPatientId());
    }

    /**
     * Method under test: {@link ServiceImpl#searchPatient(long)}
     */
    @Test
    void testSearchPatient2() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(false).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        PatientDBDao patientDao = new PatientDBDao(conn, new AuthUserDBDao(conn2));

        AuthUserDBDao authUserDao = new AuthUserDBDao(mock(Connection.class));
        Connection conn3 = mock(Connection.class);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn3, new AuthUserDBDao(mock(Connection.class)));

        Connection conn4 = mock(Connection.class);
        Connection conn5 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn5, new AuthUserDBDao(mock(Connection.class)));

        Connection conn6 = mock(Connection.class);

        // Act and Assert
        assertThrows(ServiceException.class, () -> (new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn4, patientDao2, new ScheduleDBDao(conn6, new AuthUserDBDao(mock(Connection.class))))))
                .searchPatient(1L));
        verify(conn).prepareStatement(eq("SELECT * FROM patient_tbl WHERE PatientId = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link ServiceImpl#searchPatient(long)}
     */
    @Test
    void testSearchPatient3() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getString(Mockito.<String>any())).thenReturn(null);
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        PatientDBDao patientDao = new PatientDBDao(conn, new AuthUserDBDao(conn2));

        AuthUserDBDao authUserDao = new AuthUserDBDao(mock(Connection.class));
        Connection conn3 = mock(Connection.class);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn3, new AuthUserDBDao(mock(Connection.class)));

        Connection conn4 = mock(Connection.class);
        Connection conn5 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn5, new AuthUserDBDao(mock(Connection.class)));

        Connection conn6 = mock(Connection.class);

        // Act and Assert
        assertThrows(ServiceException.class, () -> (new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn4, patientDao2, new ScheduleDBDao(conn6, new AuthUserDBDao(mock(Connection.class))))))
                .searchPatient(1L));
        verify(conn2).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn).prepareStatement(eq("SELECT * FROM patient_tbl WHERE PatientId = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement2).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(preparedStatement2).setLong(eq(1), eq(1L));
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet2).getLong(eq("UserId"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet).next();
        verify(resultSet2).next();
        verify(preparedStatement).close();
        verify(preparedStatement2).close();
    }

    /**
     * Method under test: {@link ServiceImpl#searchSchedule(long)}
     */
    @Test
    void testSearchSchedule() throws ServiceException, SQLException {
        // Arrange
        Date date = mock(Date.class);
        LocalDate ofResult = LocalDate.of(1970, 1, 1);
        when(date.toLocalDate()).thenReturn(ofResult);
        Time time = mock(Time.class);
        when(time.toLocalTime()).thenReturn(LocalTime.MIDNIGHT);
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getTime(Mockito.<String>any())).thenReturn(time);
        when(resultSet.getDate(Mockito.<String>any())).thenReturn(date);
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn, new AuthUserDBDao(conn2));

        AuthUserDBDao authUserDao = new AuthUserDBDao(mock(Connection.class));
        Connection conn3 = mock(Connection.class);
        PatientDBDao patientDao = new PatientDBDao(conn3, new AuthUserDBDao(mock(Connection.class)));

        Connection conn4 = mock(Connection.class);
        Connection conn5 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn5, new AuthUserDBDao(mock(Connection.class)));

        Connection conn6 = mock(Connection.class);

        // Act
        ScheduleTbl actualSearchScheduleResult = (new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn4, patientDao2, new ScheduleDBDao(conn6, new AuthUserDBDao(mock(Connection.class))))))
                .searchSchedule(1L);

        // Assert
        verify(conn2).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn).prepareStatement(eq("SELECT * FROM schedule_tbl WHERE ScheduleId = ?"));
        verify(date).toLocalDate();
        verify(preparedStatement).executeQuery();
        verify(preparedStatement2).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(preparedStatement2).setLong(eq(1), eq(1L));
        verify(resultSet).getDate(eq("scheduleDate"));
        verify(resultSet).getInt(eq("scheduleStatus"));
        verify(resultSet).getLong(eq("DoctorId"));
        verify(resultSet2).getLong(eq("UserId"));
        verify(resultSet2, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet).getString(eq("unavailabilityReason"));
        verify(resultSet, atLeast(1)).getTime(Mockito.<String>any());
        verify(resultSet).next();
        verify(resultSet2).next();
        verify(preparedStatement).close();
        verify(preparedStatement2).close();
        verify(time, atLeast(1)).toLocalTime();
        LocalTime endTime = actualSearchScheduleResult.getEndTime();
        assertEquals("00:00", endTime.toString());
        LocalDate scheduleDate = actualSearchScheduleResult.getScheduleDate();
        assertEquals("1970-01-01", scheduleDate.toString());
        AuthUserTbl doctor = actualSearchScheduleResult.getDoctor();
        assertEquals("String", doctor.getEmail());
        assertEquals("String", doctor.getMobileNo());
        assertEquals("String", doctor.getName());
        assertEquals("String", doctor.getRole());
        assertEquals("String", doctor.getUsername());
        assertEquals("String", actualSearchScheduleResult.getUnavailabilityReason());
        assertEquals("b2ef230e7f4f315a28cdcc863028da31f7110f3209feb76e76fed0f37b3d8580", doctor.getPassword());
        assertEquals(1, actualSearchScheduleResult.getScheduleStatus());
        assertEquals(1L, doctor.getUserId());
        assertEquals(1L, actualSearchScheduleResult.getScheduleId());
        assertSame(endTime, actualSearchScheduleResult.getStartTime());
        assertSame(ofResult, scheduleDate);
    }

    /**
     * Method under test: {@link ServiceImpl#searchSchedule(long)}
     */
    @Test
    void testSearchSchedule2() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(false).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn, new AuthUserDBDao(conn2));

        AuthUserDBDao authUserDao = new AuthUserDBDao(mock(Connection.class));
        Connection conn3 = mock(Connection.class);
        PatientDBDao patientDao = new PatientDBDao(conn3, new AuthUserDBDao(mock(Connection.class)));

        Connection conn4 = mock(Connection.class);
        Connection conn5 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn5, new AuthUserDBDao(mock(Connection.class)));

        Connection conn6 = mock(Connection.class);

        // Act and Assert
        assertThrows(ServiceException.class, () -> (new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn4, patientDao2, new ScheduleDBDao(conn6, new AuthUserDBDao(mock(Connection.class))))))
                .searchSchedule(1L));
        verify(conn).prepareStatement(eq("SELECT * FROM schedule_tbl WHERE ScheduleId = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link ServiceImpl#searchAppointment(long)}
     */
    @Test
    void testSearchAppointment() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        ResultSet resultSet3 = mock(ResultSet.class);
        when(resultSet3.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet3.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet3.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement3 = mock(PreparedStatement.class);
        when(preparedStatement3.executeQuery()).thenReturn(resultSet3);
        doNothing().when(preparedStatement3).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement3).close();
        Connection conn3 = mock(Connection.class);
        when(conn3.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement3);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(conn3));

        Date date = mock(Date.class);
        LocalDate ofResult = LocalDate.of(1970, 1, 1);
        when(date.toLocalDate()).thenReturn(ofResult);
        Time time = mock(Time.class);
        when(time.toLocalTime()).thenReturn(LocalTime.MIDNIGHT);
        ResultSet resultSet4 = mock(ResultSet.class);
        when(resultSet4.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet4.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet4.getTime(Mockito.<String>any())).thenReturn(time);
        when(resultSet4.getDate(Mockito.<String>any())).thenReturn(date);
        when(resultSet4.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet4.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement4 = mock(PreparedStatement.class);
        when(preparedStatement4.executeQuery()).thenReturn(resultSet4);
        doNothing().when(preparedStatement4).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement4).close();
        Connection conn4 = mock(Connection.class);
        when(conn4.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement4);
        ResultSet resultSet5 = mock(ResultSet.class);
        when(resultSet5.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet5.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet5.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement5 = mock(PreparedStatement.class);
        when(preparedStatement5.executeQuery()).thenReturn(resultSet5);
        doNothing().when(preparedStatement5).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement5).close();
        Connection conn5 = mock(Connection.class);
        when(conn5.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement5);
        AppointmentDBDao appointmentDao = new AppointmentDBDao(conn, patientDao,
                new ScheduleDBDao(conn4, new AuthUserDBDao(conn5)));

        AuthUserDBDao authUserDao = new AuthUserDBDao(mock(Connection.class));
        Connection conn6 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn6, new AuthUserDBDao(mock(Connection.class)));

        Connection conn7 = mock(Connection.class);

        // Act
        AppointmentTbl actualSearchAppointmentResult = (new ServiceImpl(authUserDao, patientDao2,
                new ScheduleDBDao(conn7, new AuthUserDBDao(mock(Connection.class))), appointmentDao)).searchAppointment(1L);

        // Assert
        verify(conn).prepareStatement(eq("SELECT * FROM appointment_tbl WHERE AppointmentId = ?"));
        verify(conn3).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn5).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn2).prepareStatement(eq("SELECT * FROM patient_tbl WHERE PatientId = ?"));
        verify(conn4).prepareStatement(eq("SELECT * FROM schedule_tbl WHERE ScheduleId = ?"));
        verify(date).toLocalDate();
        verify(preparedStatement).executeQuery();
        verify(preparedStatement2).executeQuery();
        verify(preparedStatement3).executeQuery();
        verify(preparedStatement4).executeQuery();
        verify(preparedStatement5).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(preparedStatement2).setLong(eq(1), eq(1L));
        verify(preparedStatement3).setLong(eq(1), eq(1L));
        verify(preparedStatement4).setLong(eq(1), eq(1L));
        verify(preparedStatement5).setLong(eq(1), eq(1L));
        verify(resultSet4).getDate(eq("scheduleDate"));
        verify(resultSet).getInt(eq("appointmentStatus"));
        verify(resultSet4).getInt(eq("scheduleStatus"));
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet4).getLong(eq("DoctorId"));
        verify(resultSet3).getLong(eq("UserId"));
        verify(resultSet5).getLong(eq("UserId"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet3, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet5, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet4).getString(eq("unavailabilityReason"));
        verify(resultSet4, atLeast(1)).getTime(Mockito.<String>any());
        verify(resultSet).next();
        verify(resultSet2).next();
        verify(resultSet3).next();
        verify(resultSet4).next();
        verify(resultSet5).next();
        verify(preparedStatement).close();
        verify(preparedStatement2).close();
        verify(preparedStatement3).close();
        verify(preparedStatement4).close();
        verify(preparedStatement5).close();
        verify(time, atLeast(1)).toLocalTime();
        ScheduleTbl schedule = actualSearchAppointmentResult.getSchedule();
        LocalTime endTime = schedule.getEndTime();
        assertEquals("00:00", endTime.toString());
        LocalDate scheduleDate = schedule.getScheduleDate();
        assertEquals("1970-01-01", scheduleDate.toString());
        assertEquals("String", actualSearchAppointmentResult.getSufferingFromDisease());
        assertEquals("String", actualSearchAppointmentResult.getSuggestedMedicines());
        assertEquals("String", actualSearchAppointmentResult.getSuggestedTests());
        PatientTbl patient = actualSearchAppointmentResult.getPatient();
        AuthUserTbl addedByUser = patient.getAddedByUser();
        assertEquals("String", addedByUser.getEmail());
        assertEquals("String", addedByUser.getMobileNo());
        assertEquals("String", addedByUser.getName());
        assertEquals("String", addedByUser.getRole());
        assertEquals("String", addedByUser.getUsername());
        assertEquals("String", patient.getEmail());
        assertEquals("String", patient.getMobileNo());
        assertEquals("String", patient.getName());
        assertEquals("String", schedule.getUnavailabilityReason());
        assertEquals("b2ef230e7f4f315a28cdcc863028da31f7110f3209feb76e76fed0f37b3d8580", addedByUser.getPassword());
        assertEquals(1, actualSearchAppointmentResult.getAppointmentStatus());
        assertEquals(1, schedule.getScheduleStatus());
        assertEquals(1L, actualSearchAppointmentResult.getAppointmentId());
        assertEquals(1L, addedByUser.getUserId());
        assertEquals(1L, patient.getPatientId());
        assertEquals(1L, schedule.getScheduleId());
        assertEquals(addedByUser, schedule.getDoctor());
        assertSame(endTime, schedule.getStartTime());
        assertSame(ofResult, scheduleDate);
    }

    /**
     * Method under test: {@link ServiceImpl#searchAppointment(long)}
     */
    @Test
    void testSearchAppointment2() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(false).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        ResultSet resultSet3 = mock(ResultSet.class);
        when(resultSet3.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement3 = mock(PreparedStatement.class);
        when(preparedStatement3.executeQuery()).thenReturn(resultSet3);
        doNothing().when(preparedStatement3).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement3).close();
        Connection conn3 = mock(Connection.class);
        when(conn3.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement3);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(conn3));

        ResultSet resultSet4 = mock(ResultSet.class);
        when(resultSet4.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement4 = mock(PreparedStatement.class);
        when(preparedStatement4.executeQuery()).thenReturn(resultSet4);
        doNothing().when(preparedStatement4).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement4).close();
        Connection conn4 = mock(Connection.class);
        when(conn4.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement4);
        ResultSet resultSet5 = mock(ResultSet.class);
        when(resultSet5.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement5 = mock(PreparedStatement.class);
        when(preparedStatement5.executeQuery()).thenReturn(resultSet5);
        doNothing().when(preparedStatement5).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement5).close();
        Connection conn5 = mock(Connection.class);
        when(conn5.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement5);
        AppointmentDBDao appointmentDao = new AppointmentDBDao(conn, patientDao,
                new ScheduleDBDao(conn4, new AuthUserDBDao(conn5)));

        AuthUserDBDao authUserDao = new AuthUserDBDao(mock(Connection.class));
        Connection conn6 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn6, new AuthUserDBDao(mock(Connection.class)));

        Connection conn7 = mock(Connection.class);

        // Act and Assert
        assertThrows(ServiceException.class,
                () -> (new ServiceImpl(authUserDao, patientDao2,
                        new ScheduleDBDao(conn7, new AuthUserDBDao(mock(Connection.class))), appointmentDao))
                        .searchAppointment(1L));
        verify(conn).prepareStatement(eq("SELECT * FROM appointment_tbl WHERE AppointmentId = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link ServiceImpl#searchAppointment(long)}
     */
    @Test
    void testSearchAppointment3() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection conn2 = mock(Connection.class);
        when(conn2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);
        ResultSet resultSet3 = mock(ResultSet.class);
        when(resultSet3.getString(Mockito.<String>any())).thenReturn(null);
        when(resultSet3.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet3.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement3 = mock(PreparedStatement.class);
        when(preparedStatement3.executeQuery()).thenReturn(resultSet3);
        doNothing().when(preparedStatement3).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement3).close();
        Connection conn3 = mock(Connection.class);
        when(conn3.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement3);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(conn3));

        ResultSet resultSet4 = mock(ResultSet.class);
        when(resultSet4.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet4.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet4.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement4 = mock(PreparedStatement.class);
        when(preparedStatement4.executeQuery()).thenReturn(resultSet4);
        doNothing().when(preparedStatement4).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement4).close();
        Connection conn4 = mock(Connection.class);
        when(conn4.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement4);
        ResultSet resultSet5 = mock(ResultSet.class);
        when(resultSet5.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet5.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet5.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement5 = mock(PreparedStatement.class);
        when(preparedStatement5.executeQuery()).thenReturn(resultSet5);
        doNothing().when(preparedStatement5).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement5).close();
        Connection conn5 = mock(Connection.class);
        when(conn5.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement5);
        AppointmentDBDao appointmentDao = new AppointmentDBDao(conn, patientDao,
                new ScheduleDBDao(conn4, new AuthUserDBDao(conn5)));

        AuthUserDBDao authUserDao = new AuthUserDBDao(mock(Connection.class));
        Connection conn6 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn6, new AuthUserDBDao(mock(Connection.class)));

        Connection conn7 = mock(Connection.class);

        // Act and Assert
        assertThrows(ServiceException.class,
                () -> (new ServiceImpl(authUserDao, patientDao2,
                        new ScheduleDBDao(conn7, new AuthUserDBDao(mock(Connection.class))), appointmentDao))
                        .searchAppointment(1L));
        verify(conn).prepareStatement(eq("SELECT * FROM appointment_tbl WHERE AppointmentId = ?"));
        verify(conn3).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE UserId = ?"));
        verify(conn2).prepareStatement(eq("SELECT * FROM patient_tbl WHERE PatientId = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement2).executeQuery();
        verify(preparedStatement3).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(preparedStatement2).setLong(eq(1), eq(1L));
        verify(preparedStatement3).setLong(eq(1), eq(1L));
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet2, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet3).getLong(eq("UserId"));
        verify(resultSet2, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet3, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet).next();
        verify(resultSet2).next();
        verify(resultSet3).next();
        verify(preparedStatement).close();
        verify(preparedStatement2).close();
        verify(preparedStatement3).close();
    }

    /**
     * Method under test: {@link ServiceImpl#login(String, String)}
     */
    @Test
    void testLogin() throws ServiceException, SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        doNothing().when(preparedStatement).close();
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        AuthUserDBDao authUserDao = new AuthUserDBDao(conn);
        Connection conn2 = mock(Connection.class);
        PatientDBDao patientDao = new PatientDBDao(conn2, new AuthUserDBDao(mock(Connection.class)));

        Connection conn3 = mock(Connection.class);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn3, new AuthUserDBDao(mock(Connection.class)));

        Connection conn4 = mock(Connection.class);
        Connection conn5 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn5, new AuthUserDBDao(mock(Connection.class)));

        Connection conn6 = mock(Connection.class);

        // Act and Assert
        assertThrows(ServiceException.class, () -> (new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn4, patientDao2, new ScheduleDBDao(conn6, new AuthUserDBDao(mock(Connection.class))))))
                .login("janedoe", "iloveyou"));
        verify(conn).prepareStatement(eq("SELECT * FROM auth_user_tbl WHERE username = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setString(eq(1), eq("janedoe"));
        verify(resultSet).getLong(eq("UserId"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test:
     * {@link ServiceImpl#ServiceImpl(AuthUserDao, PatientDao, ScheduleDao, AppointmentDao)}
     */
    @Test
    void testNewServiceImpl() {


        // Arrange
        AuthUserDBDao authUserDao = new AuthUserDBDao(mock(Connection.class));
        Connection conn = mock(Connection.class);
        PatientDBDao patientDao = new PatientDBDao(conn, new AuthUserDBDao(mock(Connection.class)));

        Connection conn2 = mock(Connection.class);
        ScheduleDBDao scheduleDao = new ScheduleDBDao(conn2, new AuthUserDBDao(mock(Connection.class)));

        Connection conn3 = mock(Connection.class);
        Connection conn4 = mock(Connection.class);
        PatientDBDao patientDao2 = new PatientDBDao(conn4, new AuthUserDBDao(mock(Connection.class)));

        Connection conn5 = mock(Connection.class);

        // Act
        new ServiceImpl(authUserDao, patientDao, scheduleDao,
                new AppointmentDBDao(conn3, patientDao2, new ScheduleDBDao(conn5, new AuthUserDBDao(mock(Connection.class)))));

    }
}

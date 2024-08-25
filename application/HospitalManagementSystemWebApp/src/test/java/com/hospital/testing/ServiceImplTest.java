package com.hospital.testing;

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
import com.hospital.service.ServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ServiceImplTest {

    @Mock
    private AuthUserDao authUserDao;
    @Mock
    private PatientDao patientDao;
    @Mock
    private ScheduleDao scheduleDao;
    @Mock
    private AppointmentDao appointmentDao;

    private ServiceImpl service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new ServiceImpl(authUserDao, patientDao, scheduleDao, appointmentDao);
    }

    @Test
    public void testGetAllAuthUser() throws DatabaseException, ServiceException {
        List<AuthUserTbl> mockUsers = new ArrayList<>();
        mockUsers.add(new AuthUserTbl(1L, "John", "john@example.com", "1234567890", "john", "password", "Doctor"));
        mockUsers.add(new AuthUserTbl(2L, "Jane", "jane@example.com", "0987654321", "jane", "password", "Nurse"));

        when(authUserDao.findAll()).thenReturn(mockUsers);

        List<AuthUserTbl> result = service.getAllAuthUser("Doctor");

        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getName());
        verify(authUserDao, times(1)).findAll();
    }

    @Test(expected = ServiceException.class)
    public void testGetAllAuthUserDatabaseException() throws DatabaseException, ServiceException {
        when(authUserDao.findAll()).thenThrow(new DatabaseException("Database error"));
        service.getAllAuthUser("Doctor");
    }

    @Test
    public void testGetAllPatient() throws DatabaseException, ServiceException {
        List<PatientTbl> mockPatients = new ArrayList<>();
        mockPatients.add(new PatientTbl(1L, "Patient1", "patient1@example.com", "1234567890", new AuthUserTbl()));

        when(patientDao.findAll()).thenReturn(mockPatients);

        List<PatientTbl> result = service.getAllPatient();

        assertEquals(1, result.size());
        assertEquals("Patient1", result.get(0).getName());
        verify(patientDao, times(1)).findAll();
    }

    @Test
    public void testAddAuthUser() throws DatabaseException, ServiceException {
        AuthUserTbl newUser = new AuthUserTbl("NewUser", "new@example.com", "1234567890", "newuser", "password", "Doctor");

        when(authUserDao.findByUsername("newuser")).thenReturn(null);
        when(authUserDao.save(newUser)).thenReturn(true);

        boolean result = service.addAuthUser(newUser);

        assertTrue(result);
        verify(authUserDao, times(1)).findByUsername("newuser");
        verify(authUserDao, times(1)).save(newUser);
    }

    @Test(expected = ServiceException.class)
    public void testAddAuthUserAlreadyExists() throws DatabaseException, ServiceException {
        AuthUserTbl existingUser = new AuthUserTbl("ExistingUser", "existing@example.com", "1234567890", "existinguser", "password", "Doctor");

        when(authUserDao.findByUsername("existinguser")).thenReturn(existingUser);

        service.addAuthUser(existingUser);
    }

    @Test
    public void testUpdateAuthUser() throws DatabaseException, ServiceException {
        AuthUserTbl updatedUser = new AuthUserTbl(1L, "UpdatedUser", "updated@example.com", "1234567890", "updateduser", "password", "Doctor");

        when(authUserDao.update(updatedUser)).thenReturn(true);

        boolean result = service.updateAuthUser(updatedUser);

        assertTrue(result);
        verify(authUserDao, times(1)).update(updatedUser);
    }

    @Test
    public void testDeleteAuthUser() throws DatabaseException, ServiceException {
        AuthUserTbl userToDelete = new AuthUserTbl(1L, "DeleteUser", "delete@example.com", "1234567890", "deleteuser", "password", "Doctor");

        when(authUserDao.findById(1L)).thenReturn(userToDelete);
        when(authUserDao.delete(1L)).thenReturn(userToDelete);

        AuthUserTbl result = service.deleteAuthUser(1L);

        assertEquals(userToDelete, result);
        verify(authUserDao, times(1)).findById(1L);
        verify(authUserDao, times(1)).delete(1L);
    }

    @Test(expected = ServiceException.class)
    public void testDeleteAuthUserNotFound() throws DatabaseException, ServiceException {
        when(authUserDao.findById(1)).thenReturn(null);

        service.deleteAuthUser(1);
    }

    @Test
    public void testAddSchedule() throws DatabaseException, ServiceException {
        AuthUserTbl doctor = new AuthUserTbl(1L, "Doctor", "doctor@example.com", "1234567890", "doctor", "password", "Doctor");
        ScheduleTbl newSchedule = new ScheduleTbl(doctor, LocalDate.now().plusDays(1), LocalTime.of(10, 0), LocalTime.of(11, 0), 1);

        when(authUserDao.findById(1L)).thenReturn(doctor);
        when(scheduleDao.findByDetails(any(), any(), any())).thenReturn(null);
        when(scheduleDao.save(newSchedule)).thenReturn(true);

        boolean result = service.addSchedule(newSchedule);

        assertTrue(result);
        verify(authUserDao, times(1)).findById(1L);
        verify(scheduleDao, times(1)).findByDetails(any(), any(), any());
        verify(scheduleDao, times(1)).save(newSchedule);
    }

    @Test(expected = ServiceException.class)
    public void testAddSchedulePastDate() throws ServiceException {
        AuthUserTbl doctor = new AuthUserTbl(1L, "Doctor", "doctor@example.com", "1234567890", "doctor", "password", "Doctor");
        ScheduleTbl pastSchedule = new ScheduleTbl(doctor, LocalDate.now().minusDays(1), LocalTime.of(10, 0), LocalTime.of(11, 0), 1);

        service.addSchedule(pastSchedule);
    }

    @Test
    public void testAddPatient() throws DatabaseException, ServiceException {
        PatientTbl newPatient = new PatientTbl("NewPatient", "patient@example.com", "1234567890", new AuthUserTbl());

        when(patientDao.save(newPatient)).thenReturn(true);

        boolean result = service.addPatient(newPatient);

        assertTrue(result);
        verify(patientDao, times(1)).save(newPatient);
    }

    @Test
    public void testViewSchedule() throws DatabaseException, ServiceException {
        AuthUserTbl doctor = new AuthUserTbl(1L, "Doctor", "doctor@example.com", "1234567890", "doctor", "password", "Doctor");
        List<ScheduleTbl> schedules = new ArrayList<>();
        schedules.add(new ScheduleTbl(doctor, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0), 1));

        when(scheduleDao.findByStartEndDate(any(), any())).thenReturn(schedules);

        HashMap<LocalDate, List<ScheduleTbl>> result = service.viewSchedule(doctor);

        assertEquals(1, result.size());
        assertTrue(result.containsKey(LocalDate.now()));
        verify(scheduleDao, times(1)).findByStartEndDate(any(), any());
    }

    @Test
    public void testBookAppointment() throws DatabaseException, ServiceException {
        AuthUserTbl doctor = new AuthUserTbl(1L, "Doctor", "doctor@example.com", "1234567890", "doctor", "password", "Doctor");

        AppointmentTbl newAppointment = new AppointmentTbl(1L, new PatientTbl(1L, "Patient1", "patient1@example.com", "1234567890",new AuthUserTbl()),
                new ScheduleTbl(doctor, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0), 1), 1);

        when(appointmentDao.findBySchedule(newAppointment.getSchedule())).thenReturn(null);
        when(appointmentDao.save(newAppointment)).thenReturn(true);

        boolean result = service.bookAppointment(newAppointment);

        assertTrue(result);
        verify(appointmentDao, times(1)).findBySchedule(newAppointment.getSchedule());
        verify(appointmentDao, times(1)).save(newAppointment);
    }

    @Test(expected = ServiceException.class)
    public void testBookAppointmentAlreadyExists() throws DatabaseException, ServiceException {
        AuthUserTbl doctor = new AuthUserTbl(1L, "Doctor", "doctor@example.com", "1234567890", "doctor", "password", "Doctor");

        AppointmentTbl existingAppointment = new AppointmentTbl(1L, new PatientTbl(1L, "Patient1", "patient1@example.com", "1234567890",new AuthUserTbl()),
                new ScheduleTbl(doctor, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0), 1), 1);

        when(appointmentDao.findBySchedule(existingAppointment.getSchedule())).thenReturn(existingAppointment);

        service.bookAppointment(existingAppointment);
    }

    @Test
    public void testUpdateSchedule() throws DatabaseException, ServiceException {
        AuthUserTbl doctor = new AuthUserTbl(1L, "Doctor", "doctor@example.com", "1234567890", "doctor", "password", "Doctor");
        ScheduleTbl updatedSchedule = new ScheduleTbl(1L, doctor, LocalDate.now().plusDays(1), LocalTime.of(10, 0), LocalTime.of(11, 0), 1);

        when(scheduleDao.findById(1L)).thenReturn(updatedSchedule);
        when(authUserDao.findById(1L)).thenReturn(doctor);
        when(scheduleDao.findByDetails(any(), any(), any())).thenReturn(null);
        when(appointmentDao.findBySchedule(updatedSchedule)).thenReturn(null);
        when(scheduleDao.update(updatedSchedule)).thenReturn(true);

        boolean result = service.updateSchedule(updatedSchedule);

        assertTrue(result);
        verify(scheduleDao, times(1)).findById(1L);
        verify(authUserDao, times(1)).findById(1L);
        verify(scheduleDao, times(1)).findByDetails(any(), any(), any());
        verify(appointmentDao, times(1)).findBySchedule(updatedSchedule);
        verify(scheduleDao, times(1)).update(updatedSchedule);
    }

    @Test
    public void testViewAppointments() throws DatabaseException, ServiceException {
        PatientTbl newPatient = new PatientTbl("NewPatient", "patient@example.com", "1234567890", new AuthUserTbl());

        AuthUserTbl doctor = new AuthUserTbl(1L, "Doctor", "doctor@example.com", "1234567890", "doctor", "password", "Doctor");
        List<AppointmentTbl> appointments = new ArrayList<>();
        appointments.add(new AppointmentTbl(1L,newPatient, new ScheduleTbl(doctor, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0), 1), 1));

        when(appointmentDao.findByStartEndDate(any(), any())).thenReturn(appointments);

        HashMap<LocalDate, List<AppointmentTbl>> result = service.viewAppointments(doctor);

        assertEquals(1, result.size());
        assertTrue(result.containsKey(LocalDate.now()));
        verify(appointmentDao, times(1)).findByStartEndDate(any(), any());
    }

    @Test
    public void testCancelAppointment() throws DatabaseException, ServiceException {
        PatientTbl newPatient = new PatientTbl("NewPatient", "patient@example.com", "1234567890", new AuthUserTbl());
        AuthUserTbl doctor = new AuthUserTbl(1L, "Doctor", "doctor@example.com", "1234567890", "doctor", "password", "Doctor");
        ScheduleTbl updatedSchedule = new ScheduleTbl(1L, doctor, LocalDate.now().plusDays(1), LocalTime.of(10, 0), LocalTime.of(11, 0), 1);


        AppointmentTbl appointmentToCancel = new AppointmentTbl(1L, newPatient, updatedSchedule, 1);

        when(appointmentDao.findById(1L)).thenReturn(appointmentToCancel);
        when(appointmentDao.update(appointmentToCancel)).thenReturn(true);
        when(scheduleDao.update(appointmentToCancel.getSchedule())).thenReturn(true);

        AppointmentTbl result = service.cancelAppointment(1L);

        assertEquals(4, result.getAppointmentStatus());
        assertEquals(1, result.getSchedule().getScheduleStatus());
        verify(appointmentDao, times(1)).findById(1L);
        verify(appointmentDao, times(1)).update(appointmentToCancel);
        verify(scheduleDao, times(1)).update(appointmentToCancel.getSchedule());
    }

    @Test
    public void testSearchUser() throws DatabaseException, ServiceException {
        AuthUserTbl user = new AuthUserTbl(1L, "User", "user@example.com", "1234567890", "user", "password", "User");

        when(authUserDao.findById(1L)).thenReturn(user);

        AuthUserTbl result = service.searchUser(1L);

        assertEquals(user, result);
        verify(authUserDao, times(1)).findById(1L);
    }

    @Test(expected = ServiceException.class)
    public void testSearchUserNotFound() throws DatabaseException, ServiceException {
        when(authUserDao.findById(1L)).thenReturn(null);

        service.searchUser(1L);
    }

    @Test
    public void testLogin() throws DatabaseException, ServiceException {
        AuthUserTbl user = new AuthUserTbl(1L, "User", "user@example.com", "1234567890", "user", "password", "User");

        when(authUserDao.authenticate("user", "password")).thenReturn(user);

        AuthUserTbl result = service.login("user", "password");

        assertEquals(user, result);
        verify(authUserDao, times(1)).authenticate("user", "password");
    }

    @Test(expected = ServiceException.class)
    public void testLoginFailed() throws DatabaseException, ServiceException {
        when(authUserDao.authenticate("user", "wrongpassword")).thenThrow(new DatabaseException("Authentication failed"));

        service.login("user", "wrongpassword");
    }
}

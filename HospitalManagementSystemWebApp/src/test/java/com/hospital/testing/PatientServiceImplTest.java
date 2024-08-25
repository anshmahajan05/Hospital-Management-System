package com.hospital.testing;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import com.hospital.entity.AuthUserTbl;
import com.hospital.entity.PatientTbl;
import com.hospital.exception.DatabaseException;
import com.hospital.exception.ServiceException;
import com.hospital.interfaces.PatientDao;
import com.hospital.service.ServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.apache.log4j.Logger;
import org.apache.log4j.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceImplTest {

    private static final Logger logger = Logger.getLogger(PatientServiceImplTest.class);

    @Mock
    private PatientDao patientDao;

    @InjectMocks
    private ServiceImpl patientServiceImpl;

    private List<PatientTbl> allPatients;

    @BeforeEach
    public void setUp() {
        AuthUserTbl user1 = new AuthUserTbl("user12","anushka",789L);
        AuthUserTbl user2 = new AuthUserTbl(345L, "Divya","divya@gmail.com","93598817789","Admin");

        allPatients = Arrays.asList(
                new PatientTbl(1L, "John Doe", "john@example.com", "1234567890", user1),
                new PatientTbl(2L, "Jane Doe", "jane@example.com", "0987654321", user2),
                new PatientTbl(3L, "Alice Smith", "alice@example.com", "5555555555", user1)
        );
    }

    @Test
    public void testGetAllPatient_withValidPatients() throws ServiceException, DatabaseException {
        when(patientDao.findAll()).thenReturn(allPatients);

        List<PatientTbl> patients = patientServiceImpl.getAllPatient();

        assertEquals(3, patients.size());
        assertEquals("John Doe", patients.get(0).getName());
        assertEquals("Jane Doe", patients.get(1).getName());

        verify(patientDao, times(1)).findAll();
        logger.info("Patients: " + patients);
    }

    @Test
    public void testGetAllPatient_withEmptyList() throws ServiceException, DatabaseException {
        when(patientDao.findAll()).thenReturn(Arrays.asList());

        List<PatientTbl> patients = patientServiceImpl.getAllPatient();

        assertEquals(0, patients.size());

        verify(patientDao, times(1)).findAll();
    }

    @Test
    public void testGetAllPatient_throwsServiceException() throws DatabaseException {
        when(patientDao.findAll()).thenThrow(new DatabaseException("Database Error"));

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            patientServiceImpl.getAllPatient();
        });

        assertEquals("Database Error", exception.getCause().getMessage());

        verify(patientDao, times(1)).findAll();
    }

    @Test
    public void testGetAllPatient_withNullPatients() throws ServiceException, DatabaseException {
        when(patientDao.findAll()).thenReturn(null);

        List<PatientTbl> patients = patientServiceImpl.getAllPatient();

        assertEquals(null, patients);

        verify(patientDao, times(1)).findAll();
    }
}

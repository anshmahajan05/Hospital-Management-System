package com.hospital.testing;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import com.hospital.entity.AuthUserTbl;
import com.hospital.exception.DatabaseException;
import com.hospital.exception.ServiceException;
import com.hospital.interfaces.AuthUserDao;
import com.hospital.service.ServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.apache.log4j.Logger;

@ExtendWith(MockitoExtension.class)
public class AuthUserServiceImplTest {

 private static final Logger logger = Logger.getLogger(AuthUserServiceImplTest.class);

 @Mock
 private AuthUserDao authUserDao;

 @InjectMocks
 private ServiceImpl authUserServiceImpl;

 private List<AuthUserTbl> allUsers;

 @BeforeEach
 public void setUp() {
  allUsers = Arrays.asList(
          new AuthUserTbl(123L,"Anushka","anudix@gmail.com","9359881794","ADMIN"),
          new AuthUserTbl(345L, "Divya","divya@gmail.com","93598817789","Admin"),
          new AuthUserTbl(789L,"Deepak","deepak@gmail.com","93598817700","User")
  );
 }

 @Test
 public void testGetAllAuthUser_withValidRole() throws ServiceException, DatabaseException {
  String role = "ADMIN";
  when(authUserDao.findAll()).thenReturn(allUsers);

  List<AuthUserTbl> filteredUsers = authUserServiceImpl.getAllAuthUser(role);

  assertEquals(2, filteredUsers.size());
  assertEquals("ADMIN", filteredUsers.get(0).getRole());
  assertEquals("ADMIN", filteredUsers.get(1).getRole());

  verify(authUserDao, times(1)).findAll();
  logger.info("Filtered users: " + filteredUsers);
 }


 @Test
 public void testGetAllAuthUser_withInValidRole() throws ServiceException, DatabaseException {
  String role = "MANAGER";
  when(authUserDao.findAll()).thenReturn(allUsers);

  List<AuthUserTbl> filteredUsers = authUserServiceImpl.getAllAuthUser(role);

  assertEquals(0, filteredUsers.size());


  verify(authUserDao, times(1)).findAll();
  logger.info("Filtered users: " + filteredUsers);
 }
 @Test
 public void testGetAllAuthUser_withCasing() throws ServiceException, DatabaseException {
  String role = "admin";
  when(authUserDao.findAll()).thenReturn(allUsers);

  List<AuthUserTbl> filteredUsers = authUserServiceImpl.getAllAuthUser(role);

  assertEquals(2, filteredUsers.size());
  assertEquals("ADMIN", filteredUsers.get(0).getRole());
  assertEquals("ADMIN", filteredUsers.get(1).getRole());

  verify(authUserDao, times(1)).findAll();
  logger.info("Filtered users: " + filteredUsers);
 }

 @Test
 public void testGetAllAuthUser_withNullRoleInUser() throws ServiceException, DatabaseException {
  String role = "ADMIN";
  allUsers = Arrays.asList(
          new AuthUserTbl("user12","anushka",789L),
          new AuthUserTbl(345L, "Divya","divya@gmail.com","93598817789","Admin"),
          new AuthUserTbl(789L,"Deepak","deepak@gmail.com","93598817700","User")
  );

  when(authUserDao.findAll()).thenReturn(allUsers);

  List<AuthUserTbl> filteredUsers = authUserServiceImpl.getAllAuthUser(role);

  assertEquals(1, filteredUsers.size());
  assertEquals("ADMIN", filteredUsers.get(0).getRole());

  verify(authUserDao, times(1)).findAll();
 }

}


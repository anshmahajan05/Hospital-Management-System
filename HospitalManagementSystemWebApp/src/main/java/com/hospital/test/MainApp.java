package com.hospital.test;

import com.hospital.connection.DBConnection;
import com.hospital.entity.AuthUserTbl;
import com.hospital.exception.DatabaseException;
import com.hospital.exception.ServiceException;
import com.hospital.factory.HospitalDAOFactory;
import com.hospital.factory.HospitalServiceFactory;
import com.hospital.interfaces.*;
import com.hospital.service.ServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.getDBConnection();

            AdminInterface admin = HospitalServiceFactory.getAdmin(conn);

            // import user using json file
            String content = new String(Files.readAllBytes(Paths.get("src/main/resources/users.json")));
            JSONArray jsonArray = new JSONArray(content);
            System.out.println(jsonArray);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Extract data from JSON object
                String name = jsonObject.getString("name");
                String email = jsonObject.getString("email");
                String mobileNo = jsonObject.getString("mobileNo");
                String username = jsonObject.getString("username");
                String password = jsonObject.getString("password");
                String role = jsonObject.getString("role");

                AuthUserTbl authUser = new AuthUserTbl(name, email, mobileNo, username, password, role);

                // Save the data to the database
                admin.addAuthUser(authUser);
            }

            // display all doctors
            List<AuthUserTbl> doctors = admin.getAllAuthUser("User");
            System.out.println(doctors);

            //update the authuser basic details
            AuthUserTbl authUser = new AuthUserTbl(2, "pavan kartik", "pavankartik@gmail.com", "9999999999", "doctor");
            boolean result = admin.updateAuthUser(authUser);
            System.out.println("Update Status-->" + result);

            // delete the authuser
            authUser = admin.deleteAuthUser(2);
            System.out.println("Delete User: " + authUser);
            authUser = admin.deleteAuthUser(1);
            System.out.println("Delete User: " + authUser);
            authUser = admin.deleteAuthUser(3);
            System.out.println("Delete User: " + authUser);
        } catch (DatabaseException | ServiceException | IOException e) {
            e.printStackTrace();
        }
    }
}

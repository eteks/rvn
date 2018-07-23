/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import com.db_connection.ConnectionConfiguration;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ETS-4
 */
public class LoginDb {
    
     private static String username;
    private static String password;
    private static String email;
    
    public static boolean login(LoginModel loginmodel) {
        Connection con = null;
        String pass=loginmodel.getPassword();
        boolean status = false;
        try {
            con = ConnectionConfiguration.getConnection();
            String query = "select username,password,email from users where username=? OR email=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, loginmodel.getUsername());
            ps.setString(2, loginmodel.getUsername());
            ResultSet rs = ps.executeQuery();
            AppUser appUser=new AppUser();
            if (rs.next()) {
                appUser.setId(rs.getInt("id"));
                appUser.setUsername(username);
                appUser.setPassword(password);
              password=rs.getString("password");
              if(password.equals(pass)){
                        return true;
              }
              else
              {
                  return false;
              }
            }
            else
            {
                return false;
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return true;
    }
    
}

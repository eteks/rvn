/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import com.db_connection.ConnectionConfiguration;
import com.model.pojo.user.Groups;
import com.model.pojo.user.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

/**
 *
 * @author ETS-4
 */
public class LoginDb {

    private static String username;
    private static String password;
    private static String email;

//    public static boolean login(LoginModel loginmodel) {
//        Connection con = null;
//        String pass=loginmodel.getPassword();
//        boolean status = false;
//        try {
//            con = ConnectionConfiguration.getConnection();
//            String query = "select username,password,email from users where username=? OR email=?";
//            PreparedStatement ps = con.prepareStatement(query);
//            ps.setString(1, loginmodel.getUsername());
//            ps.setString(2, loginmodel.getUsername());
//            ResultSet rs = ps.executeQuery();
//            AppUser appUser=new AppUser();
//            if (rs.next()) {
//                appUser.setId(rs.getInt("id"));
//                appUser.setUsername(username);
//                appUser.setPassword(password);
//              password=rs.getString("password");
//              if(password.equals(pass)){
//                        return true;
//              }
//              else
//              {
//                  return false;
//              }
//            }
//            else
//            {
//                return false;
//            }
//            
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//
//        return true;
//    }
//    public static int login(LoginModel loginmodel) {
//        Connection con = null;
//        String pass=loginmodel.getPassword();
//        int userid = 0;
//        try {
//            con = ConnectionConfiguration.getConnection();
//            String query = "select username,password,email,id from users where username=? OR email=?";
//            PreparedStatement ps = con.prepareStatement(query);
//            ps.setString(1, loginmodel.getUsername());
//            ps.setString(2, loginmodel.getUsername());
//            ResultSet rs = ps.executeQuery();
//            AppUser appUser=new AppUser();
//            if (rs.next()) {
//                appUser.setId(rs.getInt("id"));
//                appUser.setUsername(username);
//                appUser.setPassword(password);
//                password=rs.getString("password");
//                if(password.equals(pass)){
//                    userid = rs.getInt("id");
//                          return userid;
//                }
//            }           
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return userid;
//    }
    public static Map<String, Object> login(LoginModel loginmodel) {
        String pass = loginmodel.getPassword();
        Map<String, Object> columns = new HashMap<String, Object>();
        Base.open();
        try {
            LazyList<Users> userDetail = Users.where("username= ? OR email= ?", loginmodel.getUsername(), loginmodel.getUsername());
            if (userDetail.size() > 0) {
                Users user = userDetail.get(0);
                password = user.getPassword();
                if (password.equals(pass)) {
                    columns = userDetail.toMaps().get(0);
                    columns.put("is_superadmin", user.parent(Groups.class).getBoolean("is_superadmin"));
                }
            }
        } finally {
            Base.close();
        }
        return columns;
    }

}

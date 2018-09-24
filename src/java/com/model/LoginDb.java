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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Connection con = null;
        String pass=loginmodel.getPassword();
        Map<String, Object> columns = new HashMap<String, Object>();
        try {
            con = ConnectionConfiguration.getConnection();
            Statement statement = con.createStatement();
            String query = "select u.*,g.is_superadmin from users as u INNER JOIN groups as g ON g.id=u.group_id where username='"+loginmodel.getUsername()+"' OR email='"+loginmodel.getUsername()+"'";
            System.out.println("pdb_sql"+query);
            ResultSet rs = statement.executeQuery(query);        
            ResultSetMetaData metaData = rs.getMetaData();
            int colCount = metaData.getColumnCount();              
            rs.last(); 
            System.out.println("rs.getRow()"+rs.getRow());
            if(rs.getRow() > 0){
                password=rs.getString("password");
                if(password.equals(pass)){
                    System.out.println("success_if");                   
                    for (int i = 1; i <= colCount; i++) {
                        columns.put(metaData.getColumnLabel(i), rs.getObject(i));
                    }
                }
            }                     
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("column_data"+columns);
        return columns;
    }
    
}

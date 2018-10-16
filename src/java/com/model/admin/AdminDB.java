package com.model.admin;

import com.db_connection.ConnectionConfiguration;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ets-2
 */
public class AdminDB {
    public static Map<String, Object> GetAdmin_Dashboarddata() throws SQLException {
        System.out.println("GetAdmin_Dashboarddata");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        connection = ConnectionConfiguration.getConnection();
        Statement statement = connection.createStatement();
        Map<String, Object> columns = new HashMap<String, Object>();
        
        //Get User Groups count
        String group_sql = "select * from groups";
        ResultSet group_rs = statement.executeQuery(group_sql);
        group_rs.last(); 
        System.out.println("resultset_count"+group_rs.getRow());
        columns.put("groups_count", group_rs.getRow());  
        
        //Get Users count
        String user_sql = "select * from users";
        ResultSet user_rs = statement.executeQuery(user_sql);
        user_rs.last(); 
        System.out.println("resultset_count"+user_rs.getRow());
        columns.put("users_count", user_rs.getRow()); 
        
        return columns;
    }
}

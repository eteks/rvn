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
 * @author ets-2
 */
public class GlobalDBActivities {
     public static Map<String, Integer> GetModuleCount() throws SQLException {
        System.out.println("GetModuleCount");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        connection = ConnectionConfiguration.getConnection();
        Statement statement = connection.createStatement();
        Map<String, Integer> columns = new HashMap<String, Integer>();
        
        //Get Vehicle count
        String veh_sql = "select * from vehicle";
        ResultSet veh_rs = statement.executeQuery(veh_sql);
        veh_rs.last(); 
        System.out.println("resultset_count"+veh_rs.getRow());
        columns.put("vehiclecount", veh_rs.getRow());
        
        //Get Model count
        String mod_sql = "select * from vehiclemodel";
        ResultSet mod_rs = statement.executeQuery(mod_sql);
        mod_rs.last(); 
        System.out.println("resultset_count"+mod_rs.getRow());
        columns.put("modelcount", mod_rs.getRow());
        return columns;
    }
}

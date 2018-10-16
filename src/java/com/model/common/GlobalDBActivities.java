/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.common;

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
  
        //Get ECU count
        String ecu_sql = "select * from engine_control_unit";
        ResultSet ecu_rs = statement.executeQuery(ecu_sql);
        ecu_rs.last(); 
        System.out.println("ecucount"+ecu_rs.getRow());
        columns.put("ecucount", ecu_rs.getRow());
            
        return columns;
    }
    public static List<Map<String, Object>> GetUserGroups() throws SQLException {
        System.out.println("GetVehicleVersion_Listing");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        connection = ConnectionConfiguration.getConnection();
        Statement statement = connection.createStatement();
        String sql = "select id,group_name,status,route_pages,is_superadmin from groups";
        ResultSet resultSet = statement.executeQuery(sql);
        ResultSetMetaData metaData = resultSet.getMetaData();
        int colCount = metaData.getColumnCount();
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        while (resultSet.next()) {
          Map<String, Object> columns = new HashMap<String, Object>();
          for (int i = 1; i <= colCount; i++) {
            columns.put(metaData.getColumnLabel(i), resultSet.getObject(i));
          }
          row.add(columns);
        }
        return row;
    }
}

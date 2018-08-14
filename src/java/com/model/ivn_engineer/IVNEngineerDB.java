/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.ivn_engineer;

import com.db_connection.ConnectionConfiguration;
import com.model.ivn_supervisor.Vehicleversion;
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
public class IVNEngineerDB {
    public static List<Map<String, Object>> LoadIVNVersion() throws SQLException {
        System.out.println("LoadIVNVersion");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        connection = ConnectionConfiguration.getConnection();
        //Check whether model name already exists in db or not
        Statement statement = connection.createStatement();
//        String sql = "select v.id,v.versionname,v.status from vehicleversion v where v.status=1";
        String sql = "select iv.id,iv.ivn_versionname,iv.status from ivnversion iv";
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
        System.out.println("row_data"+row);
        return row;
    }
    public static Map<String, Object> LoadNetwork() throws SQLException {
        System.out.println("LoadNetwork");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        connection = ConnectionConfiguration.getConnection();
        Statement statement = connection.createStatement();
        
        String can_sql = "select c.id as cid,c.can_network_name as listitem from network_can as c where status=1";
        System.out.println(can_sql);
        ResultSet resultSet = statement.executeQuery(can_sql);
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
        
        String lin_sql = "select l.id as lid, l.lin_network_name as listitem from network_lin as l where status=1";
        System.out.println(lin_sql);
        ResultSet resultSet1 = statement.executeQuery(lin_sql);
        ResultSetMetaData metaData1 = resultSet1.getMetaData();
        int colCount1 = metaData1.getColumnCount();
        List<Map<String, Object>> row1 = new ArrayList<Map<String, Object>>();
        while (resultSet1.next()) {
          Map<String, Object> columns1 = new HashMap<String, Object>();
          for (int i = 1; i <= colCount1; i++) {
            columns1.put(metaData1.getColumnLabel(i), resultSet1.getObject(i));
          }
          row1.add(columns1);
        }
        
        String hardware_sql = "select hw.id as hid, hw.hardware_network_name as listitem from network_hardware as hw where status=1";
        System.out.println(hardware_sql);
        ResultSet resultSet2 = statement.executeQuery(hardware_sql);
        ResultSetMetaData metaData2 = resultSet2.getMetaData();
        int colCount2 = metaData2.getColumnCount();
        List<Map<String, Object>> row2 = new ArrayList<Map<String, Object>>();
        while (resultSet2.next()) {
          Map<String, Object> columns2 = new HashMap<String, Object>();
          for (int i = 1; i <= colCount2; i++) {
            columns2.put(metaData2.getColumnLabel(i), resultSet2.getObject(i));
          }
          row2.add(columns2);
        }
        
        Map<String, Object> columns2 = new HashMap<String, Object>();
        columns2.put("can_list",row);
        columns2.put("lin_list",row1);
        columns2.put("hardware_list",row2);
        System.out.println("columns"+columns2);
        return columns2;
    }
    public static List<Map<String, Object>> LoadECU() throws SQLException {
        System.out.println("LoadECU");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        connection = ConnectionConfiguration.getConnection();
        //Check whether model name already exists in db or not
        Statement statement = connection.createStatement();
//        String sql = "select v.id,v.versionname,v.status from vehicleversion v where v.status=1";
        String sql = "select ecu.id as eid,ecu.ecu_name as listitem,ecu.ecu_description as description from engine_control_unit as ecu where ecu.status=1";
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
        System.out.println("row_data"+row);
        return row;
    }
    public static List<Map<String, Object>> LoadSignals() throws SQLException {
        System.out.println("LoadSignals");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        connection = ConnectionConfiguration.getConnection();
        //Check whether model name already exists in db or not
        Statement statement = connection.createStatement();
//        String sql = "select v.id,v.versionname,v.status from vehicleversion v where v.status=1";
        String sql = "select s.id as sid,s.signal_name as listitem,s.signal_description as description from signals as s where s.status=1";
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
        System.out.println("row_data"+row);
        return row;
    }
    public static int insertNetworkData(Network_Ecu n) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionConfiguration.getConnection();            
            Statement statement = connection.createStatement();
            if(n.getNetwork_type().equals("can"))
               preparedStatement = connection.prepareStatement("INSERT INTO network_can (can_network_name,can_network_description,created_date,created_or_updated_by)" +
                        "VALUES (?, ?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
            else if(n.getNetwork_type().equals("lin"))   
                 preparedStatement = connection.prepareStatement("INSERT INTO network_lin (lin_network_name,lin_network_description,created_date,created_or_updated_by)" +
                        "VALUES (?, ?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
            else if(n.getNetwork_type().equals("hardware"))   
                 preparedStatement = connection.prepareStatement("INSERT INTO network_hardware (hardware_network_name,hardware_network_description,created_date,created_or_updated_by)" +
                        "VALUES (?, ?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
            else if(n.getNetwork_type().equals("ecu"))  
                preparedStatement = connection.prepareStatement("INSERT INTO engine_control_unit (ecu_name,ecu_description,created_date,created_or_updated_by)" +
                        "VALUES (?, ?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
            if(n.getNetwork_type().equals("ecu")){
                preparedStatement.setString(1, n.getEcuname());
                preparedStatement.setString(2, n.getEcudescription());
                preparedStatement.setString(3, n.getCreated_date());
                preparedStatement.setInt(4, n.getCreated_or_updated_by());       
            }
            else{ 
                preparedStatement.setString(1, n.getNetworkname());
                preparedStatement.setString(2, n.getNetworkdescription());
                preparedStatement.setString(3, n.getCreated_date());
                preparedStatement.setInt(4, n.getCreated_or_updated_by());     
            }
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next())
            {
                int last_inserted_id = rs.getInt(1);
                return last_inserted_id;
            }
        } catch (Exception e) {
            System.out.println("vehicle version error message"+e.getMessage()); 
            e.printStackTrace();
            return 0;
            
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
 
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        }
        return 0;
    }
    public static int insertSignalData(Signal s) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionConfiguration.getConnection();            
            Statement statement = connection.createStatement();   
            System.out.println("before preparedStatement");
            preparedStatement = connection.prepareStatement("INSERT INTO signals (signal_name,signal_alias,signal_description,"
                    + "signal_length,signal_byteorder,signal_unit,signal_valuetype,signal_initvalue,signal_factor,signal_offset,"
                    + "signal_minimum,signal_maximum,signal_valuetable,can_id_group,lin_id_group,hw_id_group,"
                    + "created_date,created_or_updated_by)" 
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);            
            System.out.println("above middle preparedStatement"+s.getSignal_factor());
            preparedStatement.setString(1, s.getSignal_name());
            System.out.println("above middle1 preparedStatement");
            preparedStatement.setString(2, s.getSignal_alias());
            System.out.println("above middle2 preparedStatement");
            preparedStatement.setString(3, s.getSignal_description());
            preparedStatement.setInt(4, s.getSignal_length());
            preparedStatement.setString(5, s.getSignal_byteorder());
            preparedStatement.setString(6, s.getSignal_unit());
            preparedStatement.setString(7, s.getSignal_valuetype());
            preparedStatement.setInt(8, s.getSignal_initvalue());
            preparedStatement.setDouble(9, s.getSignal_factor());
            preparedStatement.setInt(10, s.getSignal_offset());
            preparedStatement.setInt(11, s.getSignal_minimum());
            preparedStatement.setInt(12, s.getSignal_maximum());
            preparedStatement.setString(13, s.getSignal_valuetable());
            preparedStatement.setString(14, s.getSignal_can_id());
            preparedStatement.setString(15, s.getSignal_lin_id());
            preparedStatement.setString(16, s.getSignal_hw_id());
            preparedStatement.setString(17, s.getCreated_date());
            preparedStatement.setInt(18, s.getCreated_or_updated_by());          
            System.out.println("middle preparedStatement");
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            System.out.println("after preparedStatement");
            if(rs.next())
            {
                int last_inserted_id = rs.getInt(1);
                return last_inserted_id;
            } 
        } catch (Exception e) {
            System.out.println("vehicle version error message"+e.getMessage()); 
            e.printStackTrace();
            return 0;
            
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
 
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        }
        return 0;
    }
}

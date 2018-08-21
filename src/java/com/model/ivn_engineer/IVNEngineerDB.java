/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.ivn_engineer;

import com.db_connection.ConnectionConfiguration;
import com.model.common.GlobalDataStore;
import com.model.ivn_supervisor.Vehicleversion;
import static com.model.pdbowner.PDBVersionDB.perm_status;
import static com.model.pdbowner.PDBVersionDB.temp_status;
import com.model.pdbowner.PDBVersionGroup;
import com.model.pdbowner.PDBversion;
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
    
    public static int temp_status = 0;
    public static int perm_status = 1;
    
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
        
        String can_sql = "select CAST(c.id as CHAR(100)) as cid,c.can_network_name as listitem from network_can as c where status=1";
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
        
        String lin_sql = "select CAST(l.id as CHAR(100)) as lid, l.lin_network_name as listitem from network_lin as l where status=1";
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
        
        String hardware_sql = "select CAST(hw.id as CHAR(100)) as hid, hw.hardware_network_name as listitem from network_hardware as hw where status=1";
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
    public static List<Map<String, Object>> LoadIVNPreviousVehicleversionStatus(IVNversion iv) throws SQLException {
        System.out.println("LoadIVNPreviousVehicleversionStatus");
//        String status = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        connection = ConnectionConfiguration.getConnection();
        //Check whether model name already exists in db or not
        Statement statement = connection.createStatement();
//        String sql = "select v.id,v.versionname,v.status from vehicleversion v where v.status=1";
        String sql = "select iv.status,iv.flag from ivnversion iv where iv.id="+iv.getId();
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
    public static int insertIVNVersion(IVNversion iv) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        float versionname;
        try {
            connection = ConnectionConfiguration.getConnection();
            
            Statement statement = connection.createStatement();
            if(iv.getOperation_status().equals("create")){
                String sql = "SELECT id, ivn_versionname FROM ivnversion ORDER BY ivn_versionname DESC LIMIT 1";
                ResultSet resultSet = statement.executeQuery(sql);          
                resultSet.last();    
                if(resultSet.getRow()==0){
                    versionname = (float) 1.0;
                }
                else{
                    versionname = (float) 1.0 + resultSet.getFloat("ivn_versionname");
                }           
                preparedStatement = connection.prepareStatement("INSERT INTO ivnversion (ivn_versionname,status,created_date,created_or_updated_by,flag)" +
                        "VALUES (?, ?, ?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
    //            preparedStatement.setString(1, v.getVersionname());
                preparedStatement.setDouble(1, versionname);
                preparedStatement.setBoolean(2, iv.getStatus());
                preparedStatement.setString(3, iv.getCreated_date());
                preparedStatement.setInt(4, iv.getCreated_or_updated_by());
                preparedStatement.setBoolean(5, iv.getFlag());
                preparedStatement.executeUpdate();


                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next())
                {
                    int last_inserted_id = rs.getInt(1);
                    return last_inserted_id;
                }
            }
            else{       
                System.out.println("object_value_in_update"+iv.getId()+iv.getStatus()+iv.getCreated_or_updated_by());
                String sql = "UPDATE ivnversion SET " +
                    "status = ?, created_or_updated_by = ?, flag=?   WHERE id = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setBoolean(1, iv.getStatus());
                preparedStatement.setInt(2, iv.getCreated_or_updated_by());
                preparedStatement.setBoolean(3, iv.getFlag());
                preparedStatement.setInt(4, iv.getId());
                preparedStatement.executeUpdate();                
                return iv.getId();
            }                
        } catch (Exception e) {
            System.out.println("ivn version error message"+e.getMessage()); 
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
    public static int insertIVNCanModel(IVNNetwork_VehicleModel invm) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionConfiguration.getConnection();            
            Statement statement = connection.createStatement();
            if(invm.getNetwork_type().equals("can"))
               preparedStatement = connection.prepareStatement("INSERT INTO ivn_canmodels (ivnversion_id, network_can_id,vehicle_and_model_mapping_id,available_status)" +
                        "VALUES (?, ?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
            else if(invm.getNetwork_type().equals("lin"))   
                 preparedStatement = connection.prepareStatement("INSERT INTO ivn_linmodels (ivnversion_id, network_lin_id,vehicle_and_model_mapping_id,available_status)" +
                        "VALUES (?, ?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
            else
                 preparedStatement = connection.prepareStatement("INSERT INTO ivn_hardwaremodels (ivnversion_id, network_hardware_id,vehicle_and_model_mapping_id,available_status)" +
                        "VALUES (?, ?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, invm.getIvnversion_id());
            preparedStatement.setInt(2, invm.getNetwork_id());
            preparedStatement.setInt(3, invm.getVehicle_model_mapping_id());
            preparedStatement.setBoolean(4, invm.getAvailable_status()); 

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next())
            {
                int last_inserted_id = rs.getInt(1);
                return last_inserted_id;
            }
        } catch (Exception e) {
            System.out.println("IVN can model error message"+e.getMessage()); 
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
    
     public static int insertIVNVersionGroup(IVNVersionGroup ig) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        float versionname;
        try {
            connection = ConnectionConfiguration.getConnection();
            
            Statement statement = connection.createStatement();
            if(ig.getOperation_status().equals("create")){       
                System.out.println("object_value_in_insert"+ig.getCanmodel_group()+ig.getLinmodel_group()+ig.getHardwaremodel_group());
                preparedStatement = connection.prepareStatement("INSERT INTO ivnversion_group (ivnversion_id,canmodel_group,"
                        + "linmodel_group,hardwaremodel_group,signal_group,ecu_group)" +
                        "VALUES (?, ?, ?, ?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, ig.getIVNversion_id());
                preparedStatement.setString(2, ig.getCanmodel_group());
                preparedStatement.setString(3, ig.getLinmodel_group());
                preparedStatement.setString(4, ig.getHardwaremodel_group());
                preparedStatement.setString(5, ig.getSignal_group());
                preparedStatement.setString(6, ig.getEcu_group());
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next())
                {
                    int last_inserted_id = rs.getInt(1);
                    return last_inserted_id;
                }
            }
            else{     
                System.out.println("object_value_in_update"+ig.getCanmodel_group()+ig.getLinmodel_group()+ig.getHardwaremodel_group());
                String sql = "UPDATE pdbversion SET " +
                    "canmodel_group = ?, linmodel_group = ?, hardwaremodel_group = ?, signal_group = ?, ecu_group =? "
                        + "WHERE ivnversion_id = ?"; 
                preparedStatement.setString(1, ig.getCanmodel_group());
                preparedStatement.setString(2, ig.getLinmodel_group());
                preparedStatement.setString(3, ig.getHardwaremodel_group());
                preparedStatement.setString(4, ig.getSignal_group());
                preparedStatement.setString(5, ig.getEcu_group());
                preparedStatement.setInt(6, ig.getIVNversion_id());
                preparedStatement.executeUpdate();                
//                return ig.getId();
                if(ig.getButton_type().equals("save")){
                    return temp_status;
                }
                else if(ig.getButton_type().equals("submit")){
                    return perm_status;
                }
            }                
        } catch (Exception e) {
            System.out.println("pdb version error message"+e.getMessage()); 
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
     
     public static Map<String, Object> LoadIVNPreviousVehicleversionData(IVNversion ivnver) throws SQLException {
        System.out.println("LoadIVNPreviousVehicleversionData");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        connection = ConnectionConfiguration.getConnection();
        Statement statement = connection.createStatement();
        List<Map<String, Object>> result_row = new ArrayList<Map<String, Object>>();
        String canmodel_sql = "SELECT CAST(cn.network_can_id as CHAR(100)) as network_id,\n" +
            "CAST(cn.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n" +
            "cn.available_status as status \n" +
            "FROM ivn_canmodels AS cn \n" +
            "where cn.ivnversion_id="+ivnver.getId();      
        System.out.println(canmodel_sql);
        ResultSet resultSet1 = statement.executeQuery(canmodel_sql);
        ResultSetMetaData metaData1 = resultSet1.getMetaData();
        int colCount1 = metaData1.getColumnCount();
        List<Map<String, Object>> row1 = new ArrayList<Map<String, Object>>();
        while (resultSet1.next()) {
          Map<String, Object> columns1 = new HashMap<String, Object>();
          for (int i = 1; i <= colCount1; i++) {
            columns1.put(metaData1.getColumnLabel(i), resultSet1.getObject(i));
          }
          columns1.put("network_type","can");
          row1.add(columns1);
        }
        
        String linmodel_sql = "SELECT CAST(ln.network_lin_id as CHAR(100)) as network_id,\n" +
            "CAST(ln.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n" +
            "ln.available_status as status \n" +
            "FROM ivn_linmodels AS ln \n" +
            "where ln.ivnversion_id="+ivnver.getId();       
        System.out.println(linmodel_sql);
        ResultSet resultSet2 = statement.executeQuery(linmodel_sql);
        ResultSetMetaData metaData2 = resultSet2.getMetaData();
        int colCount2 = metaData2.getColumnCount();
        List<Map<String, Object>> row2 = new ArrayList<Map<String, Object>>();
        while (resultSet2.next()) {
          Map<String, Object> columns2 = new HashMap<String, Object>();
          for (int i = 1; i <= colCount2; i++) {
            columns2.put(metaData2.getColumnLabel(i), resultSet2.getObject(i));
          }
          columns2.put("network_type","lin");
          row2.add(columns2);
        }
        
        String hwmodel_sql = "SELECT CAST(hw.network_hardware_id as CHAR(100)) as network_id,\n" +
            "CAST(hw.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n" +
            "hw.available_status as status \n" +
            "FROM ivn_hardwaremodels AS hw \n" +
            "where hw.ivnversion_id="+ivnver.getId();       
        System.out.println(hwmodel_sql);
        ResultSet resultSet3 = statement.executeQuery(hwmodel_sql);
        ResultSetMetaData metaData3 = resultSet3.getMetaData();
        int colCount3 = metaData3.getColumnCount();
        List<Map<String, Object>> row3 = new ArrayList<Map<String, Object>>();
        while (resultSet3.next()) {
          Map<String, Object> columns3 = new HashMap<String, Object>();
          for (int i = 1; i <= colCount3; i++) {
            columns3.put(metaData3.getColumnLabel(i), resultSet3.getObject(i));
          }
          columns3.put("network_type","hardware");
          row3.add(columns3);
        }
        
        Map<String, Object> columns_res = new HashMap<String, Object>();
        String ivngroup_sql = "SELECT signal_group, ecu_group " +
            "FROM ivnversion_group AS ig \n" +
            "where ig.ivnversion_id="+ivnver.getId();       
        System.out.println(ivngroup_sql);
        ResultSet resultSet4 = statement.executeQuery(ivngroup_sql);
        ResultSetMetaData metaData4 = resultSet4.getMetaData();
        int colCount4 = metaData4.getColumnCount();
        while (resultSet4.next()) { 
//          String strarray[] =resultSet4.getString("signal_group").split(",") ;
//          String strarray1[] =resultSet4.getString("ecu_group").split(",") ;
//          columns_res.put("signal",strarray);
//          columns_res.put("ecu",strarray1);
            columns_res.put("signal","["+resultSet4.getString("signal_group")+"]");
            columns_res.put("ecu","["+resultSet4.getString("ecu_group")+"]");
        }
        
//        String ivnsignalgroup_sql = "select s.id as sid,s.signal_name as listitem,s.signal_description as description from ivnversion_group as ig inner join signals as s "
//                + "on FIND_IN_SET(s.id,ig.signal_group) > 0 where ig.ivnversion_id="+ivnver.getId();       
//        System.out.println(ivnsignalgroup_sql);
//        ResultSet resultSet5 = statement.executeQuery(ivnsignalgroup_sql);
//        ResultSetMetaData metaData5 = resultSet5.getMetaData();
//        int colCount5 = metaData5.getColumnCount();
//        List<Map<String, Object>> row5 = new ArrayList<Map<String, Object>>();
//        while (resultSet5.next()) {
//          Map<String, Object> columns5 = new HashMap<String, Object>();
//          for (int i = 1; i <= colCount5; i++) {
//            columns5.put(metaData5.getColumnLabel(i), resultSet5.getObject(i));
//          }
//          columns5.put("network_type","signal");
//          row5.add(columns5);
//        }
        
//        String ivnecugroup_sql = "select e.id as eid,e.ecu_name as listitem,e.ecu_description as description from ivnversion_group as ig inner join engine_control_unit as e "
//                + "on FIND_IN_SET(e.id,ig.ecu_group) > 0 where ig.ivnversion_id="+ivnver.getId();       
//        System.out.println(ivnecugroup_sql);
//        ResultSet resultSet6 = statement.executeQuery(ivnecugroup_sql);
//        ResultSetMetaData metaData6 = resultSet6.getMetaData();
//        int colCount6 = metaData6.getColumnCount();
//        List<Map<String, Object>> row6 = new ArrayList<Map<String, Object>>();
//        while (resultSet6.next()) {
//          Map<String, Object> columns6 = new HashMap<String, Object>();
//          for (int i = 1; i <= colCount6; i++) {
//            columns6.put(metaData6.getColumnLabel(i), resultSet6.getObject(i));
//          }
//          columns6.put("network_type","ecu");
//          row6.add(columns6);
//        }
        
        String vehciledetail_sql = "SELECT \n" +
            "vv.id as vehver_id,\n" +
            "v.id as vehicle_id,\n" +
            "vm.modelname as modelname,\n" +
            "CAST(vmm.id as CHAR(100)) as vmm_id \n" +
            "FROM ivn_canmodels AS cn \n" +
            "INNER JOIN vehicle_and_model_mapping AS vmm ON vmm.id = cn.vehicle_and_model_mapping_id \n" +
            "INNER JOIN vehicleversion as vv on vv.id=vmm.vehicleversion_id \n" +
            "INNER JOIN vehicle as v on v.id=vmm.vehicle_id \n" +
            "INNER JOIN vehiclemodel as vm on vm.id=vmm.model_id\n" +
            "where cn.ivnversion_id="+ivnver.getId()+" group by modelname,vehicle_and_model_mapping_id";
        System.out.println(vehciledetail_sql);
        ResultSet resultSet = statement.executeQuery(vehciledetail_sql);
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
        
        columns_res.put("vehicledetail_list",row);      
        columns_res.put("can",row1);
        columns_res.put("lin",row2);
        columns_res.put("hardware",row3);
//        columns_res.put("signaldetail_list",row5);
//        columns_res.put("ecudetail_list",row6);
//        System.out.println("columns"+columns_res);
        return columns_res;
    }
}

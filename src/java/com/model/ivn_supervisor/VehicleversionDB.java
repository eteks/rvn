package com.model.ivn_supervisor;

import com.db_connection.ConnectionConfiguration;
import com.model.common.GlobalDataStore;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.catalina.tribes.util.Arrays;
import org.apache.commons.lang3.StringUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ets-2
 */
public class VehicleversionDB {
        public static int temp_status = 0;
        public static int perm_status = 1;
//        public static int[] vehicleversion_id;
//        public static List<Integer> vehiclemodel_mapping_id = new ArrayList<Integer>();
    	public static int insertVehicleVersion(Vehicleversion v) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        float versionname;
        try {
            connection = ConnectionConfiguration.getConnection();
            
            Statement statement = connection.createStatement();
            if(v.getOperation_status().equals("create")){
                String sql = "SELECT id, versionname FROM vehicleversion ORDER BY versionname DESC LIMIT 1";
                ResultSet resultSet = statement.executeQuery(sql);          
                resultSet.last();    
                if(resultSet.getRow()==0){
                    versionname = (float) 1.0;
                }
                else{
                    versionname = (float) 1.0 + resultSet.getFloat("versionname");
                }           
                preparedStatement = connection.prepareStatement("INSERT INTO vehicleversion (versionname,status,created_date,created_or_updated_by,flag)" +
                        "VALUES (?, ?, ?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
    //            preparedStatement.setString(1, v.getVersionname());
                preparedStatement.setDouble(1, versionname);
                preparedStatement.setBoolean(2, v.getStatus());
                preparedStatement.setString(3, v.getCreated_date());
                preparedStatement.setInt(4, v.getCreated_or_updated_by());
                preparedStatement.setBoolean(5, v.getFlag());
                preparedStatement.executeUpdate();


                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next())
                {
                    int last_inserted_id = rs.getInt(1);
                    return last_inserted_id;
                }
            }
            else{       
                System.out.println("object_value_in_update"+v.getId()+v.getStatus()+v.getCreated_or_updated_by()+v.getFlag());
                String sql = "UPDATE vehicleversion SET " +
                    "status = ?, created_or_updated_by = ?, flag=?  WHERE id = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setBoolean(1, v.getStatus());
                preparedStatement.setInt(2, v.getCreated_or_updated_by());
                 preparedStatement.setBoolean(3, v.getFlag());
                preparedStatement.setInt(4, v.getId());             
                preparedStatement.executeUpdate();                
                return v.getId();
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
    public static int insertVehicle(Vehicle v) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
 
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether vehicle name already exists in db or not
            Statement statement = connection.createStatement();
            String sql = "SELECT id FROM vehicle WHERE vehiclename ='"+v.getVehiclename().trim() +"'";
            ResultSet resultSet = statement.executeQuery(sql);          
            resultSet.last(); 
//            System.out.println("vehicle_row_count"+resultSet.getRow());
//            System.out.println(statement);
            if(resultSet.getRow()>0){
//                System.out.println("if");
                int last_inserted_id = resultSet.getInt(1);
                return last_inserted_id;
            }else{
//                System.out.println("else");
                preparedStatement = connection.prepareStatement("INSERT INTO vehicle (vehiclename,created_date,created_or_updated_by)" +
                    "VALUES (?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, v.getVehiclename());
                preparedStatement.setString(2, v.getCreated_date());
                preparedStatement.setInt(3, v.getCreated_or_updated_by());
                preparedStatement.executeUpdate();


                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next())
                {
                    int last_inserted_id = rs.getInt(1);
                    return last_inserted_id;
                }
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
    public static int insertVehicleModel(VehicleModel v) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
 
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
            String sql = "select * from vehiclemodel where modelname = '" + v.getModelname().trim() + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.last(); 
//            System.out.println("model_row_count"+resultSet.getRow());
            if(resultSet.getRow()>0){
                System.out.println("if");
                int last_inserted_id = resultSet.getInt(1);
                return last_inserted_id;
            }else{
                System.out.println("else");
                preparedStatement = connection.prepareStatement("INSERT INTO vehiclemodel (modelname,created_date,created_or_updated_by)" +
                    "VALUES (?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, v.getModelname());
                preparedStatement.setString(2, v.getCreated_date());
                preparedStatement.setInt(3, v.getCreated_or_updated_by());
                preparedStatement.executeUpdate();


                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next())
                {
                    int last_inserted_id = rs.getInt(1);
                    return last_inserted_id;
                }
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
    public static int insertVehicleModelMapping(Vehicle_and_Model_Mapping v) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
//        System.out.println("button_type"+v.getButton_type());
        int resultSet_count = 0;
        try {
            boolean flagvalue;
            connection = ConnectionConfiguration.getConnection();
            if(v.getOperation_status().equals("update")){
                System.out.println("update_if");
                Statement statement = connection.createStatement();
                String sql = "select vmm.id from vehicle_and_model_mapping as vmm where "
                        + "vmm.vehicleversion_id="+v.getVehicleversion_id()
                        + " AND vmm.vehicle_id="+v.getVehicle_id()+" AND vmm.model_id="+v.getModel_id();
                System.out.println("sql_query"+sql);
                ResultSet resultSet = statement.executeQuery(sql); 
                if(resultSet.next())
                {
                    System.out.println("resultset next available");
                    GlobalDataStore.globalData.add(resultSet.getInt("id"));
//                    while (resultSet.next()) {                       
//                          System.out.println("while");
//                          GlobalDataStore.globalData.add(resultSet.getInt("id"));
//                    }
                }                                
                resultSet.last(); 
                resultSet_count = resultSet.getRow();
                System.out.println("getrow_count"+resultSet.getRow());
                           
            }            
            if(resultSet_count == 0){
                preparedStatement = connection.prepareStatement("INSERT INTO vehicle_and_model_mapping (vehicleversion_id, vehicle_id, model_id)" +
                    "VALUES (?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, v.getVehicleversion_id());
                preparedStatement.setInt(2, v.getVehicle_id());
                preparedStatement.setInt(3, v.getModel_id());
//                if(v.getButton_type().equals("save"))
//                    flagvalue = false;
//                else
//                    flagvalue = true;
//                preparedStatement.setBoolean(4, flagvalue);
                preparedStatement.executeUpdate();
                
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next())
                {
                    GlobalDataStore.globalData.add(rs.getInt(1));
                }
            }
            System.out.println("globalData"+GlobalDataStore.globalData);
            if(v.getButton_type().equals("save")){
                return temp_status;
            }
            else if(v.getButton_type().equals("submit")){
                return perm_status;
            }
//            ResultSet rs = preparedStatement.getGeneratedKeys();
//            if(rs.next())
//            {
//                int last_inserted_id = rs.getInt(1);
//                return last_inserted_id;
//            }
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

    public static List<Map<String, Object>> GetVehicleVersion_Listing(Vehicleversion vver) throws SQLException {
        System.out.println("GetVehicleVersion_Listing");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        connection = ConnectionConfiguration.getConnection();
        //Check whether model name already exists in db or not
        Statement statement = connection.createStatement();
        String sql = "SELECT vv.id as id, CAST(versionname as CHAR(100)) as versionname, GROUP_CONCAT( DISTINCT (v.vehiclename) ) AS vehiclename, "
                + " GROUP_CONCAT( DISTINCT (vm.modelname) ) AS modelname, vv.status FROM vehicle_and_model_mapping AS vmm "
                + " INNER JOIN vehicle AS v ON v.id = vmm.vehicle_id INNER JOIN vehicleversion AS vv ON"
                + " vv.id = vmm.vehicleversion_id INNER JOIN vehiclemodel AS vm ON vm.id = vmm.model_id "
                + " GROUP BY vmm.vehicleversion_id, vmm.vehicle_id ORDER BY vv.id DESC";
//        String sql = "select * from vehiclemodel where modelname = '" + v.getModelname().trim() + "'";
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
        
//        for (Object o : row) {
//            o.vehicle
//        }
        
//        resultSet.last(); 
//        System.out.println("resultset_data"+row);
        return row;
    }
    public static List<Map<String, Object>> GetVehicle_Listing(Vehicle veh) throws SQLException {
        System.out.println("GetVehicle_Listing");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        connection = ConnectionConfiguration.getConnection();
        //Check whether model name already exists in db or not
        Statement statement = connection.createStatement();
        String sql = "select v.vehiclename,v.status,group_concat(DISTINCT(vv.versionname)) as versionname from vehicle as v INNER JOIN "
                + "vehicle_and_model_mapping as vmm ON vmm.vehicle_id=v.id INNER JOIN vehicleversion as vv ON "
                + "vv.id=vmm.vehicleversion_id group by v.vehiclename order by v.id desc";
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
    public static List<Map<String, Object>> GetVehicleModel_Listing(VehicleModel veh) throws SQLException {
        System.out.println("GetVehicleModel_Listing");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        connection = ConnectionConfiguration.getConnection();
        //Check whether model name already exists in db or not
        Statement statement = connection.createStatement();
        String sql = "select m.modelname,m.status,group_concat(DISTINCT(v.vehiclename)) as vehiclename,"
                + "group_concat(DISTINCT(vv.versionname)) as versionname from vehiclemodel as m INNER JOIN "
                + "vehicle_and_model_mapping as vmm ON vmm.model_id=m.id INNER JOIN vehicle as v ON "
                + "v.id=vmm.vehicle_id INNER JOIN vehicleversion as vv ON vv.id=vmm.vehicleversion_id "
                + "group by m.modelname order by m.id desc";
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
    
    public static List<Map<String, Object>> LoadVehicleVersion() throws SQLException {
        System.out.println("LoadVehicleVersion");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        connection = ConnectionConfiguration.getConnection();
        //Check whether model name already exists in db or not
        Statement statement = connection.createStatement();
//        String sql = "select v.id,v.versionname,v.status from vehicleversion v where v.status=1";
        String sql = "select v.id,v.versionname,v.status from vehicleversion v";
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
    public static List<Map<String, Object>> LoadPreviousVehicleversionData(Vehicleversion vver) throws SQLException {
        System.out.println("LoadPreviousVehicleversionData");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        connection = ConnectionConfiguration.getConnection();
        //Check whether model name already exists in db or not
        Statement statement = connection.createStatement();
        String sql = "SELECT CAST(versionname as CHAR(100)) as versionname, v.id as vehicle_id, GROUP_CONCAT( DISTINCT (v.vehiclename) ) "
                + "AS vehiclename, GROUP_CONCAT( DISTINCT (vm.modelname) ) AS modelname,GROUP_CONCAT( DISTINCT (vm.id) ) AS model_id,"
                + "GROUP_CONCAT( vmm.id ) AS vehicle_mapping_id, vv.status "
                + "FROM vehicle_and_model_mapping AS vmm INNER JOIN vehicle AS v ON v.id = vmm.vehicle_id "
                + "INNER JOIN vehicleversion AS vv ON vv.id = vmm.vehicleversion_id INNER JOIN vehiclemodel AS vm "
                + "ON vm.id = vmm.model_id where vmm.vehicleversion_id="+vver.getId()+" GROUP BY vmm.vehicleversion_id, vmm.vehicle_id";
//        String sql = "select * from vehiclemodel where modelname = '" + v.getModelname().trim() + "'";
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
    public static void deleteVehicleModelMapping(int vehicleversion_id) throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        connection = ConnectionConfiguration.getConnection();
        preparedStatement = connection.prepareStatement("delete from vehicle_and_model_mapping where vehicleversion_id="+vehicleversion_id+" AND id NOT IN ("+StringUtils.join(GlobalDataStore.globalData, ',')+")");
        preparedStatement.executeUpdate();

        GlobalDataStore.globalData.clear();
    }
}

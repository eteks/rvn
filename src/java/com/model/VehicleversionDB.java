package com.model;

import com.db_connection.ConnectionConfiguration;
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
    	public static int insertVehicleVersion(Vehicleversion v) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        float versionname;
        try {
            connection = ConnectionConfiguration.getConnection();
            
            Statement statement = connection.createStatement();
            String sql = "SELECT id, versionname FROM vehicleversion ORDER BY versionname DESC LIMIT 1";
            ResultSet resultSet = statement.executeQuery(sql);          
            resultSet.last();    
            if(resultSet.getRow()==0){
                versionname = (float) 1.0;
            }
            else{
                System.out.println("else in insertVehicleVersion");
                System.out.println("count after"+resultSet.getRow());
                System.out.println("resultset versionname"+resultSet.getFloat("versionname"));
//                float f = resultSet.getString("versionname");
                versionname = (float) 1.0 + resultSet.getFloat("versionname");
//                while ( resultSet.next() ) {
//                    System.out.println("while");
//                    System.out.println("resultset vehiclename"+resultSet.getString("vehiclename"));
////                    test 
//                    versionname = resultSet.getString("vehiclename") + 1;
//                }
            }           
            preparedStatement = connection.prepareStatement("INSERT INTO vehicleversion (versionname,status,created_date,created_or_updated_by)" +
                    "VALUES (?, ?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
//            preparedStatement.setString(1, v.getVersionname());
            preparedStatement.setDouble(1, versionname);
            preparedStatement.setBoolean(2, v.getStatus());
            preparedStatement.setString(3, v.getCreated_date());
            preparedStatement.setInt(4, v.getCreated_or_updated_by());
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
            System.out.println("model_row_count"+resultSet.getRow());
            if(resultSet.getRow()>0){
//                System.out.println("if");
                int last_inserted_id = resultSet.getInt(1);
                return last_inserted_id;
            }else{
//                System.out.println("else");
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
 
        try {
            connection = ConnectionConfiguration.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO vehicle_and_model_mapping (vehicleversion_id, vehicle_id, model_id, flag)" +
                "VALUES (?, ?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, v.getVehicleversion_id());
            preparedStatement.setInt(2, v.getVehicle_id());
            preparedStatement.setInt(3, v.getModel_id());
            preparedStatement.setBoolean(4, v.getFlag());
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

    public static List<Map<String, Object>> GetVehicleVersion_Listing(Vehicleversion vver) throws SQLException {
        System.out.println("GetVehicleVersion_Listing");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        connection = ConnectionConfiguration.getConnection();
        //Check whether model name already exists in db or not
        Statement statement = connection.createStatement();
        String sql = "select vmm.id,vmm.vehicleversion_id,vmm.vehicle_id,vmm.model_id,v.vehiclename,vv.versionname,vv.status,vm.modelname from vehicle_and_model_mapping as vmm INNER JOIN vehicle as v ON v.id=vmm.vehicle_id INNER JOIN vehicleversion as vv ON vv.id=vmm.vehicleversion_id INNER JOIN vehiclemodel as vm ON vm.id=vmm.model_id";
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
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        connection = ConnectionConfiguration.getConnection();
        //Check whether model name already exists in db or not
        Statement statement = connection.createStatement();
        String sql = "select v.vehiclename,v.status,group_concat(DISTINCT(vv.versionname)) as versionname from vehicle as v INNER JOIN "
                + "vehicle_and_model_mapping as vmm ON vmm.vehicle_id=v.id INNER JOIN vehicleversion as vv ON "
                + "vv.id=vmm.vehicleversion_id group by v.vehiclename order by v.id desc";
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
}

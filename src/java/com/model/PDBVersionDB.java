/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import com.db_connection.ConnectionConfiguration;
import static com.model.VehicleversionDB.perm_status;
import static com.model.VehicleversionDB.temp_status;
import static com.model.VehicleversionDB.vehicleversion_id;
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
public class PDBVersionDB {
    public static int temp_status = 0;
    public static int perm_status = 1;
    
    public static int insertDomain(Domain d) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether vehicle name already exists in db or not
            Statement statement = connection.createStatement();
            String sql = "SELECT id FROM domain WHERE domain_name ='"+d.getDomainname().trim() +"'";
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
                preparedStatement = connection.prepareStatement("INSERT INTO domain (domain_name,created_date,created_or_updated_by)" +
                    "VALUES (?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, d.getDomainname());
                preparedStatement.setString(2, d.getCreated_date());
                preparedStatement.setInt(3, d.getCreated_or_updated_by());
                preparedStatement.executeUpdate();


                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next())
                {
                    int last_inserted_id = rs.getInt(1);
                    return last_inserted_id;
                }
            }
        } catch (Exception e) {
            System.out.println("Domain creation error message"+e.getMessage()); 
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
    public static int insertFeatures(Features f) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether vehicle name already exists in db or not
            Statement statement = connection.createStatement();
            String sql = "SELECT id FROM features WHERE feature_name ='"+f.getFeaturename().trim() +"'";
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
                preparedStatement = connection.prepareStatement("INSERT INTO features (feature_name,feature_description,created_date,created_or_updated_by)" +
                    "VALUES (?, ?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, f.getFeaturename());
                preparedStatement.setString(2, f.getFeatureDescription());
                preparedStatement.setString(3, f.getCreated_date());
                preparedStatement.setInt(4, f.getCreated_or_updated_by());
                preparedStatement.executeUpdate();


                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next())
                {
                    int last_inserted_id = rs.getInt(1);
                    return last_inserted_id;
                }
            }
        } catch (Exception e) {
            System.out.println("Domain creation error message"+e.getMessage()); 
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
    public static int insertDomainFeaturesMapping(Domain_and_Features_Mapping dfm) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether vehicle name already exists in db or not
            Statement statement = connection.createStatement();
            String sql = "SELECT id FROM domain_and_features_mapping WHERE domain_id ="+dfm.getDomainId() +" AND feature_id="+dfm.getFeatureId();
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
                preparedStatement = connection.prepareStatement("INSERT INTO domain_and_features_mapping (domain_id,feature_id,created_date)" +
                    "VALUES (?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, dfm.getDomainId());
                preparedStatement.setInt(2, dfm.getFeatureId());
                preparedStatement.setString(3, dfm.getCreated_date());
                preparedStatement.executeUpdate();


                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next())
                {
                    int last_inserted_id = rs.getInt(1);
                    return last_inserted_id;
                }
            }
        } catch (Exception e) {
            System.out.println("Domain creation error message"+e.getMessage()); 
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
    public static List<Map<String, Object>> LoadFeaturesList() throws SQLException {
        System.out.println("LoadFeaturesList");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        connection = ConnectionConfiguration.getConnection();
        //Check whether model name already exists in db or not
        Statement statement = connection.createStatement();
        String sql = "SELECT d.domain_name as domain, f.feature_name as fea, dfm.id as fid from domain_and_features_mapping as dfm INNER JOIN domain AS d ON d.id = dfm.domain_id "
                + "INNER JOIN features AS f ON f.id = dfm.feature_id";
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
    public static int insertPDBVersion(PDBversion pv) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        float versionname;
        try {
            connection = ConnectionConfiguration.getConnection();
            
            Statement statement = connection.createStatement();
            if(pv.getOperation_status().equals("create")){
                String sql = "SELECT id, pdb_versionname FROM pdbversion ORDER BY pdb_versionname DESC LIMIT 1";
                ResultSet resultSet = statement.executeQuery(sql);          
                resultSet.last();    
                if(resultSet.getRow()==0){
                    versionname = (float) 1.0;
                }
                else{
                    versionname = (float) 1.0 + resultSet.getFloat("pdb_versionname");
                }           
                preparedStatement = connection.prepareStatement("INSERT INTO pdbversion (pdb_versionname,status,created_date,created_or_updated_by)" +
                        "VALUES (?, ?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
    //            preparedStatement.setString(1, v.getVersionname());
                preparedStatement.setDouble(1, versionname);
                preparedStatement.setBoolean(2, pv.getStatus());
                preparedStatement.setString(3, pv.getCreated_date());
                preparedStatement.setInt(4, pv.getCreated_or_updated_by());
                preparedStatement.executeUpdate();


                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next())
                {
                    int last_inserted_id = rs.getInt(1);
                    return last_inserted_id;
                }
            }
            else{       
                System.out.println("object_value_in_update"+pv.getId()+pv.getStatus()+pv.getCreated_or_updated_by());
                String sql = "UPDATE pdbversion SET " +
                    "status = ?, created_or_updated_by = ?  WHERE id = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setBoolean(1, pv.getStatus());
                preparedStatement.setInt(2, pv.getCreated_or_updated_by());
                preparedStatement.setInt(3, pv.getId());
                preparedStatement.executeUpdate();                
                return pv.getId();
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
    public static int insertPDBVersionGroup(PDBVersionGroup pg) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
//        System.out.println("button_type"+v.getButton_type());
        int resultSet_count = 0;
        try {
            boolean flagvalue;
            connection = ConnectionConfiguration.getConnection();
            if(pg.getOperation_status().equals("update")){
//                System.out.println("update_if");
//                Statement statement = connection.createStatement();
//                String sql = "select vmm.id from vehicle_and_model_mapping as vmm where "
//                        + "vmm.vehicleversion_id="+v.getVehicleversion_id()
//                        + " AND vmm.vehicle_id="+v.getVehicle_id()+" AND vmm.model_id="+v.getModel_id();
//                System.out.println("sql_query"+sql);
//                ResultSet resultSet = statement.executeQuery(sql); 
//                if(resultSet.next())
//                {
//                    System.out.println("resultset next available");
//                    while (resultSet.next()) {                       
//                          System.out.println("while");
//                          vehicleversion_id.add(resultSet.getInt("id"));
//                    }
//                }                                
//                resultSet.last(); 
//                resultSet_count = resultSet.getRow();
//                System.out.println("getrow_count"+resultSet.getRow());                           
            }            
            if(resultSet_count == 0){
                preparedStatement = connection.prepareStatement("INSERT INTO pdbversion_group (pdbversion_id, vehicle_and_model_mapping_id, domain_and_features_mapping_id,available_status, flag)" +
                    "VALUES (?, ?, ?, ?,?)",preparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, pg.getPDBversion_id());
                preparedStatement.setInt(2, pg.getVehicle_and_model_mapping_id());
                preparedStatement.setInt(3, pg.getDomain_and_features_mapping_id());
                preparedStatement.setString(4, pg.getAvailable_status());
                if(pg.getButton_type().equals("save"))
                    flagvalue = false;
                else
                    flagvalue = true;
                preparedStatement.setBoolean(5, flagvalue);
                preparedStatement.executeUpdate();
                
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next())
                {
                    vehicleversion_id.add(rs.getInt(1));
                }
            }
            System.out.println("vehicleversion_id"+vehicleversion_id);
            if(pg.getButton_type().equals("save")){
                return temp_status;
            }
            else if(pg.getButton_type().equals("submit")){
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
    public static List<Map<String, Object>> LoadPDBVersion() throws SQLException {
        System.out.println("LoadPDBVersion");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        connection = ConnectionConfiguration.getConnection();
        //Check whether model name already exists in db or not
        Statement statement = connection.createStatement();
//        String sql = "select v.id,v.versionname,v.status from vehicleversion v where v.status=1";
        String sql = "select p.id,p.pdb_versionname,p.status from pdbversion p";
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
    public static Map<String, Object> LoadPDBPreviousVehicleversionData(PDBversion pdbver) throws SQLException {
        System.out.println("LoadPDBPreviousVehicleversionData");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        connection = ConnectionConfiguration.getConnection();
        //Check whether model name already exists in db or not
        Statement statement = connection.createStatement();
//        String sql = "SELECT CAST(versionname as CHAR(100)) as versionname, v.id as vehicle_id, GROUP_CONCAT( DISTINCT (v.vehiclename) ) "
//                + "AS vehiclename, GROUP_CONCAT( DISTINCT (vm.modelname) ) AS modelname,GROUP_CONCAT( DISTINCT (vm.id) ) AS model_id,"
//                + "GROUP_CONCAT( vmm.id ) AS vehicle_mapping_id, vv.status "
//                + "FROM vehicle_and_model_mapping AS vmm INNER JOIN vehicle AS v ON v.id = vmm.vehicle_id "
//                + "INNER JOIN vehicleversion AS vv ON vv.id = vmm.vehicleversion_id INNER JOIN vehiclemodel AS vm "
//                + "ON vm.id = vmm.model_id where vmm.vehicleversion_id="+pdbver.getId()+" GROUP BY vmm.vehicleversion_id, vmm.vehicle_id";        
        
//        String sql = "SELECT GROUP_CONCAT( DISTINCT (pg.vehicle_and_model_mapping_id) ) as vmm_id,"
//                + "GROUP_CONCAT( DISTINCT (pg.domain_and_features_mapping_id ) ) as dfm_id,"
//                + "GROUP_CONCAT(pg.available_status) as status,CAST(vv.versionname as CHAR(100)) as versionname,"
//                + "vv.id as version_id,GROUP_CONCAT( DISTINCT (v.vehiclename) ) as vehiclename, "
//                + "GROUP_CONCAT( DISTINCT (v.id) ) as vehicle_id,"
//                + "GROUP_CONCAT( DISTINCT (vm.modelname) ) as modelname,"
//                + "GROUP_CONCAT( DISTINCT (d.domain_name) ) as domainname,"
//                + "GROUP_CONCAT( DISTINCT (f.feature_name) ) as featurename FROM pdbversion_group AS pg "
//                + "right JOIN vehicle_and_model_mapping AS vmm ON vmm.id = pg.vehicle_and_model_mapping_id "
//                + "INNER JOIN domain_and_features_mapping AS dfm ON dfm.id = pg.domain_and_features_mapping_id "
//                + "INNER JOIN vehicleversion as vv on vv.id=vmm.vehicleversion_id INNER JOIN vehicle as v on v.id=vmm.vehicle_id "
//                + "INNER JOIN vehiclemodel as vm on vm.id=vmm.model_id INNER JOIN domain as d on d.id=dfm.domain_id "
//                + "INNER JOIN features as f on f.id=dfm.feature_id "
//                + "where pg.pdbversion_id="+pdbver.getId()+" GROUP BY pg.pdbversion_id"; 
        List<Map<String, Object>> result_row = new ArrayList<Map<String, Object>>();
        String vehciledetail_sql = "SELECT \n" +
            "vv.id as vehver_id,\n" +
            "GROUP_CONCAT( DISTINCT (v.id) ) as vehicle_id,\n" +
            "GROUP_CONCAT( DISTINCT (vm.modelname) ) as modelname \n" +
            "FROM pdbversion_group AS pg \n" +
            "INNER JOIN vehicle_and_model_mapping AS vmm ON vmm.id = pg.vehicle_and_model_mapping_id \n" +
            "INNER JOIN vehicleversion as vv on vv.id=vmm.vehicleversion_id \n" +
            "INNER JOIN vehicle as v on v.id=vmm.vehicle_id \n" +
            "INNER JOIN vehiclemodel as vm on vm.id=vmm.model_id\n" +
            "where pg.pdbversion_id="+pdbver.getId()+"  GROUP BY pg.pdbversion_id";
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
        
        String featuredetail_sql = "SELECT CAST(pg.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n" +
            "CAST(pg.domain_and_features_mapping_id as CHAR(100)) as dfm_id,\n" +
            "pg.available_status as status,\n" +
            "d.domain_name as domainname,\n" +
            "f.feature_name as featurename,f.id as fid FROM pdbversion_group AS pg \n" +
            "right JOIN vehicle_and_model_mapping AS vmm ON vmm.id = pg.vehicle_and_model_mapping_id \n" +
            "INNER JOIN domain_and_features_mapping AS dfm ON dfm.id = pg.domain_and_features_mapping_id \n" +
            "INNER JOIN domain as d on d.id=dfm.domain_id \n" +
            "INNER JOIN features as f on f.id=dfm.feature_id \n" +
            "where pg.pdbversion_id="+pdbver.getId();
        System.out.println(featuredetail_sql);
        ResultSet resultSet1 = statement.executeQuery(featuredetail_sql);
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
        Map<String, Object> columns2 = new HashMap<String, Object>();
        columns2.put("vehicledetail_list",row);
        columns2.put("featuredetail_list",row1);
        System.out.println("columns"+columns2);
        return columns2;
    }
    public static String LoadPDBPreviousVehicleversionStatus(PDBversion p) throws SQLException {
        System.out.println("LoadPDBPreviousVehicleversionStatus");
        String status = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        connection = ConnectionConfiguration.getConnection();
        //Check whether model name already exists in db or not
        Statement statement = connection.createStatement();
//        String sql = "select v.id,v.versionname,v.status from vehicleversion v where v.status=1";
        String sql = "select p.status from pdbversion p where p.id="+p.getId();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            status = resultSet.getString("status");
        } 
//        System.out.println("row_data"+row);
        return status;
    }
    public static List<Map<String, Object>> GetDomainFeaturesListing(Features fea) throws SQLException {
        System.out.println("GetFeatures_Listing");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        connection = ConnectionConfiguration.getConnection();
        //Check whether model name already exists in db or not
        Statement statement = connection.createStatement();
        String sql = "select dfm.id as fid, d.domain_name as domain, f.feature_name as fea from domain_and_features_mapping as dfm INNER JOIN domain as d ON d.id=dfm.domain_id"
                + " INNER JOIN features as f ON f.id=dfm.feature_id order by d.id desc";
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

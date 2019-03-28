/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pdb_owner;

import com.controller.common.CookieRead;
import com.db_connection.ConnectionConfiguration;
import com.model.common.GlobalDBActivities;
import com.model.common.GlobalDataStore;
import static com.model.ivn_supervisor.VehicleversionDB.perm_status;
import static com.model.ivn_supervisor.VehicleversionDB.temp_status;
//import static com.model.ivn_supervisor.VehicleversionDB.vehicleversion_id;
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
import org.apache.commons.lang3.StringUtils;

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
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
    //        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
            String sql = "SELECT d.domain_name as domain, f.feature_name as fea, CAST(dfm.id as CHAR(100)) as fid from domain_and_features_mapping as dfm INNER JOIN domain AS d ON d.id = dfm.domain_id "
                    + "INNER JOIN features AS f ON f.id = dfm.feature_id";
    //        String sql = "select * from vehiclemodel where modelname = '" + v.getModelname().trim() + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int colCount = metaData.getColumnCount();          
            while (resultSet.next()) {
              Map<String, Object> columns = new HashMap<String, Object>();
              for (int i = 1; i <= colCount; i++) {
                columns.put(metaData.getColumnLabel(i), resultSet.getObject(i));
              }
              row.add(columns);
            }
        } catch (Exception e) {
            System.out.println("acb version error message"+e.getMessage()); 
            e.printStackTrace();
            
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
 
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return row;
    }
    public static Object[] insertPDBVersion(PDBversion pv) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        float versionname = 0.0f;
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
                preparedStatement = connection.prepareStatement("INSERT INTO pdbversion (pdb_versionname,status,created_date,created_or_updated_by,flag)" +
                        "VALUES (?, ?, ?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
    //            preparedStatement.setString(1, v.getVersionname());
                preparedStatement.setDouble(1, versionname);
                preparedStatement.setBoolean(2, pv.getStatus());
                preparedStatement.setString(3, pv.getCreated_date());
                preparedStatement.setInt(4, pv.getCreated_or_updated_by());
                preparedStatement.setBoolean(5, pv.getFlag());
                preparedStatement.executeUpdate();


                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next())
                {
                    int last_inserted_id = rs.getInt(1);
                    return new Object[]{last_inserted_id, versionname};
                }
            }
            else{   
                String versionName = "SELECT pdb_versionname FROM pdbversion WHERE id ="+pv.getId();
                ResultSet resultSet = statement.executeQuery(versionName);
                resultSet.last();
                if (resultSet.getRow() != 0) {
                    versionname = (float) resultSet.getFloat("pdb_versionname");
                }
                System.out.println("object_value_in_update"+pv.getId()+pv.getStatus()+pv.getCreated_or_updated_by());
                String sql = "UPDATE pdbversion SET " +
                    "status = ?, created_or_updated_by = ?, flag=?   WHERE id = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setBoolean(1, pv.getStatus());
                preparedStatement.setInt(2, pv.getCreated_or_updated_by());
                preparedStatement.setBoolean(3, pv.getFlag());
                preparedStatement.setInt(4, pv.getId());
                preparedStatement.executeUpdate();                
                return new Object[]{pv.getId(), versionname};
            }                
        } catch (Exception e) {
            System.out.println("pdb version error message"+e.getMessage()); 
            e.printStackTrace();
            return new Object[]{0, versionname};
            
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return new Object[]{0, versionname};
                }
            }
 
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return new Object[]{0, versionname};
                }
            }
        }
        return new Object[]{0, versionname};
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
                System.out.println("update_if");
                Statement statement = connection.createStatement();
//                String sql = "select pg.id from pdbversion_group as pg where "
//                        + "pg.pdbversion_id="+pg.getPDBversion_id()
//                        + " AND pg.vehicle_and_model_mapping_id="+pg.getVehicle_and_model_mapping_id()+" AND pg.domain_and_features_mapping_id="+pg.getDomain_and_features_mapping_id()
//                        + " AND pg.available_status='"+pg.getAvailable_status()+"'";
                String sql = "select * from pdbversion_group as pg where "
                        + "pg.pdbversion_id="+pg.getPDBversion_id()
                        + " AND pg.vehicle_and_model_mapping_id="+pg.getVehicle_and_model_mapping_id()+" AND pg.domain_and_features_mapping_id="+pg.getDomain_and_features_mapping_id();
                System.out.println("sql_query"+sql);
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
//                    if(resultSet.getInt("pdbversion_id") == pg.getPDBversion_id() && 
//                            resultSet.getInt("vehicle_and_model_mapping_id") == pg.getVehicle_and_model_mapping_id() &&
//                            resultSet.getInt("domain_and_features_mapping_id") == pg.getDomain_and_features_mapping_id()){ 
                            System.out.println("while");
                            if(resultSet.getString("available_status") != pg.getAvailable_status()){
                                System.out.println("if");
                                String update_sql = "UPDATE pdbversion_group SET " +
                                    "available_status = ?  WHERE id = ?";
                                preparedStatement = connection.prepareStatement(update_sql);
                                preparedStatement.setString(1, pg.getAvailable_status()); 
                                preparedStatement.setInt(2, resultSet.getInt("id"));             
                                preparedStatement.executeUpdate(); 
                            }
                            GlobalDataStore.globalData.add(resultSet.getInt("id"));
//                    }                   
                }
//                if(resultSet.next())
//                {
//                    System.out.println("resultset next available");
//                    GlobalDataStore.globalData.add(resultSet.getInt("id"));
//                }                                
                resultSet.last(); 
                resultSet_count = resultSet.getRow();
                System.out.println("getrow_count"+resultSet.getRow());                           
            }            
            if(resultSet_count == 0){
                preparedStatement = connection.prepareStatement("INSERT INTO pdbversion_group (pdbversion_id, vehicle_and_model_mapping_id, domain_and_features_mapping_id,available_status)" +
                    "VALUES (?, ?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, pg.getPDBversion_id());
                preparedStatement.setInt(2, pg.getVehicle_and_model_mapping_id());
                preparedStatement.setInt(3, pg.getDomain_and_features_mapping_id());
                preparedStatement.setString(4, pg.getAvailable_status());
//                if(pg.getButton_type().equals("save"))
//                    flagvalue = false;
//                else
//                    flagvalue = true;
//                preparedStatement.setBoolean(5, flagvalue);
                preparedStatement.executeUpdate();
                
                if(pg.getButton_type().equals("other")){
                    ResultSet rs = preparedStatement.getGeneratedKeys();
                    if(rs.next())
                    {
                        int last_inserted_id = rs.getInt(1);
                        return last_inserted_id;
                    }
                }
                //Avoid this condition for storing pdb data from system owner
                else{
                    ResultSet rs = preparedStatement.getGeneratedKeys();
                    if(rs.next())
                    {
                        GlobalDataStore.globalData.add(rs.getInt(1));
                    }
                }
            }
            if(pg.getButton_type().equals("save")){
                return temp_status;
            }
            else if(pg.getButton_type().equals("submit")){
                return perm_status;
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
    public static List<Map<String, Object>> LoadPDBVersion(String filter) throws SQLException {
        System.out.println("LoadPDBVersion");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
            String sql;
            if(filter.equals("active"))
                sql = "select p.id,p.pdb_versionname,p.status from pdbversion p where p.flag=1 and p.status=1";
            else
                sql = "select p.id,p.pdb_versionname,p.status from pdbversion p";
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int colCount = metaData.getColumnCount();            
            while (resultSet.next()) {
              Map<String, Object> columns = new HashMap<String, Object>();
              for (int i = 1; i <= colCount; i++) {
                columns.put(metaData.getColumnLabel(i), resultSet.getObject(i));
              }
              row.add(columns);
            }
            System.out.println("row_data"+row);
        } catch (Exception e) {
            System.out.println("acb version error message"+e.getMessage()); 
            e.printStackTrace();
            
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
 
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return row;
    }
    public static Map<String, Object> LoadPDBPreviousVehicleversionData(PDBversion pdbver) throws SQLException {
        System.out.println("LoadPDBPreviousVehicleversionData");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Map<String, Object> columns3 = new HashMap<String, Object>();
        try {
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
    //        String vehciledetail_sql = "SELECT \n" +
    //                    "vv.id as vehver_id,\n" +
    //                    "GROUP_CONCAT( DISTINCT (v.id) ) as vehicle_id,\n" +
    //                    "GROUP_CONCAT( DISTINCT (vm.modelname) ) as modelname,\n" +
    //                    "GROUP_CONCAT( DISTINCT (vmm.id) ) as vmm_id \n" +
    //                    "FROM pdbversion_group AS pg \n" +
    //                    "INNER JOIN vehicle_and_model_mapping AS vmm ON vmm.id = pg.vehicle_and_model_mapping_id \n" +
    //                    "INNER JOIN vehicleversion as vv on vv.id=vmm.vehicleversion_id \n" +
    //                    "INNER JOIN vehicle as v on v.id=vmm.vehicle_id \n" +
    //                    "INNER JOIN vehiclemodel as vm on vm.id=vmm.model_id\n" +
    //                    "where pg.pdbversion_id="+pdbver.getId()+"  GROUP BY pg.pdbversion_id";
            String vehciledetail_sql = "SELECT \n" +
                        "vv.id as vehver_id,\n" +
                        "v.id as vehicle_id,\n" +
                        "vm.modelname as modelname,\n" +
                        "CAST(vmm.id as CHAR(100)) as vehicle_model_mapping_id \n" +
                        "FROM pdbversion_group AS pg \n" +
                        "INNER JOIN vehicle_and_model_mapping AS vmm ON vmm.id = pg.vehicle_and_model_mapping_id \n" +
                        "INNER JOIN vehicleversion as vv on vv.id=vmm.vehicleversion_id \n" +
                        "INNER JOIN vehicle as v on v.id=vmm.vehicle_id \n" +
                        "INNER JOIN vehiclemodel as vm on vm.id=vmm.model_id\n" +
                        "where pg.pdbversion_id="+pdbver.getId()+" group by modelname,vehicle_model_mapping_id order by vehicle_model_mapping_id";
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

    //        String featuredetail_sql = "SELECT CAST(pg.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n" +
    //            "CAST(pg.domain_and_features_mapping_id as CHAR(100)) as dfm_id,\n" +
    //            "pg.available_status as status,\n" +
    //            "d.domain_name as domainname,\n" +
    //            "f.feature_name as featurename,f.id as fid FROM pdbversion_group AS pg \n" +
    //            "right JOIN vehicle_and_model_mapping AS vmm ON vmm.id = pg.vehicle_and_model_mapping_id \n" +
    //            "INNER JOIN domain_and_features_mapping AS dfm ON dfm.id = pg.domain_and_features_mapping_id \n" +
    //            "INNER JOIN domain as d on d.id=dfm.domain_id \n" +
    //            "INNER JOIN features as f on f.id=dfm.feature_id \n" +
    //            "where pg.pdbversion_id="+pdbver.getId();
    //        String featuredetail_sql = "SELECT pg.id,CAST(GROUP_CONCAT(pg.vehicle_and_model_mapping_id) as CHAR(100)) as vmm_id,\n" +
    //            "CAST(pg.domain_and_features_mapping_id as CHAR(100)) as dfm_id,\n" +
    //            "GROUP_CONCAT(pg.available_status) as status,\n" +
    //            "d.domain_name as domainname,\n" +
    //            "f.feature_name as featurename,f.id as fid FROM pdbversion_group AS pg \n" +
    //            "right JOIN vehicle_and_model_mapping AS vmm ON vmm.id = pg.vehicle_and_model_mapping_id \n" +
    //            "INNER JOIN domain_and_features_mapping AS dfm ON dfm.id = pg.domain_and_features_mapping_id \n" +
    //            "INNER JOIN domain as d on d.id=dfm.domain_id \n" +
    //            "INNER JOIN features as f on f.id=dfm.feature_id \n" +
    //            "where pg.pdbversion_id="+pdbver.getId()+" group by dfm_id order by pg.id DESC";

            String featuredetail_sql = "SELECT CAST(pg.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n" +
                "CAST(pg.domain_and_features_mapping_id as CHAR(100)) as fid,\n" +
                "pg.available_status as status,\n" +
                "d.domain_name as domainname,\n" +
                "f.feature_name as featurename FROM pdbversion_group AS pg \n" +
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

            String pdb_status_sql = "select p.status from pdbversion p where p.id="+pdbver.getId();
            ResultSet resultSet2 = statement.executeQuery(pdb_status_sql);
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
            
            columns3.put("vehicledetail_list",row);
            columns3.put("featuredetail_list",row1);
            columns3.put("pdbversion_status",row2);
            System.out.println("columns"+columns3);
        } catch (Exception e) {
            System.out.println("acb version error message"+e.getMessage()); 
            e.printStackTrace();
            
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
 
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return columns3;
    }
    public static List<Map<String, Object>> LoadPDBPreviousVehicleversionStatus(PDBversion p) throws SQLException {
        System.out.println("LoadPDBPreviousVehicleversionStatus");
//        String status = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
    //        String sql = "select v.id,v.versionname,v.status from vehicleversion v where v.status=1";
            String sql = "select p.status,p.flag from pdbversion p where p.id="+p.getId();
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int colCount = metaData.getColumnCount();           
            while (resultSet.next()) {
              Map<String, Object> columns = new HashMap<String, Object>();
              for (int i = 1; i <= colCount; i++) {
                columns.put(metaData.getColumnLabel(i), resultSet.getObject(i));
              }
              row.add(columns);
            }
        } catch (Exception e) {
            System.out.println("acb version error message"+e.getMessage()); 
            e.printStackTrace();
            
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
 
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return row;
    }
    public static List<Map<String, Object>> GetDomainFeaturesListing(Features fea) throws SQLException 
    {
        System.out.println("GetFeatures_Listing");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
            String sql = "select dfm.id as fid, d.domain_name as domain, f.feature_name as fea from domain_and_features_mapping as dfm INNER JOIN domain as d ON d.id=dfm.domain_id"
                    + " INNER JOIN features as f ON f.id=dfm.feature_id order by d.id desc";
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int colCount = metaData.getColumnCount();           
            while (resultSet.next()) {
              Map<String, Object> columns = new HashMap<String, Object>();
              for (int i = 1; i <= colCount; i++) {
                columns.put(metaData.getColumnLabel(i), resultSet.getObject(i));
              }
              row.add(columns);
            }
        } catch (Exception e) {
            System.out.println("acb version error message"+e.getMessage()); 
            e.printStackTrace();
            
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
 
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return row;
    }
    public static List<Map<String, Object>> GetPDBVersion_Listing() throws SQLException {
        System.out.println("GetPDBVersion_Listing");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
            String sql = "SELECT pdb.id as id, CAST(pdb.pdb_versionname as CHAR(100)) as pdb_version, \n" +
                        "GROUP_CONCAT(DISTINCT(vv.id)) as vehicleversion_id,\n" +
                        "GROUP_CONCAT(DISTINCT(vv.versionname)) as veh_version,\n" +
                        "GROUP_CONCAT(DISTINCT(v.vehiclename)) as vehicle,\n" +
                        "GROUP_CONCAT(DISTINCT(vm.modelname)) as model,pdb.status as status,pdb.flag FROM pdbversion as pdb \n" +
                        "LEFT JOIN pdbversion_group as pg ON pg.pdbversion_id=pdb.id \n" +
                        "LEFT JOIN vehicle_and_model_mapping as vmm ON vmm.id=pg.vehicle_and_model_mapping_id \n" +
                        "LEFT JOIN vehicle as v ON v.id=vmm.vehicle_id \n" +
                        "LEFT JOIN vehiclemodel as vm ON vm.id=vmm.model_id \n" +
                        "LEFT JOIN vehicleversion as vv ON vv.id=vmm.vehicleversion_id group by pg.pdbversion_id order by pdb.id desc";
    //        String sql = "SELECT ivn.id as id, CAST(ivn.ivn_versionname as CHAR(100)) as ivn_version, \n" +
    //                    "GROUP_CONCAT(DISTINCT(vv.id)) as vehicleversion_id,\n" +
    //                    "GROUP_CONCAT(DISTINCT(vv.versionname)) as veh_version,\n" +
    //                    "GROUP_CONCAT(DISTINCT(v.vehiclename)) as vehicle,\n" +
    //                    "GROUP_CONCAT(DISTINCT(vm.modelname)) as model,ivn.status as status,ivn.flag FROM ivnversion as ivn \n" +
    //                    "INNER JOIN ivn_canmodels as cn ON cn.ivnversion_id=ivn.id \n" +
    //                    "INNER JOIN vehicle_and_model_mapping as vmm ON vmm.id=cn.vehicle_and_model_mapping_id \n" +
    //                    "INNER JOIN vehicle as v ON v.id=vmm.vehicle_id \n" +
    //                    "INNER JOIN vehiclemodel as vm ON vm.id=vmm.model_id \n" +
    //                    "INNER JOIN vehicleversion as vv ON vv.id=vmm.vehicleversion_id group by cn.ivnversion_id order by ivn.id desc";
            System.out.println("ivnsql"+sql);
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int colCount = metaData.getColumnCount();           
            while (resultSet.next()) {
              Map<String, Object> columns = new HashMap<String, Object>();
              for (int i = 1; i <= colCount; i++) {
                columns.put(metaData.getColumnLabel(i), resultSet.getObject(i));
              }
              if (CookieRead.getGroupIdFromSession() == 2) {
                    columns.put("delBut", 1);
                }else{
                    columns.put("delBut", 0);
                }
              row.add(columns);
            }
        } catch (Exception e) {
            System.out.println("pdb version error message"+e.getMessage()); 
            e.printStackTrace();
            
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
 
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return row;
    }
    
    public static Map<String, Object> GetPDB_Dashboarddata() throws SQLException {
        System.out.println("GetPDB_Dashboarddata");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        connection = ConnectionConfiguration.getConnection();
        Statement statement = connection.createStatement();
        Map<String, Object> columns = new HashMap<String, Object>();
        
        //Get PDB Versions count
        String pdbver_sql = "select * from pdbversion";
        ResultSet pdbver_rs = statement.executeQuery(pdbver_sql);
        pdbver_rs.last(); 
        System.out.println("pdbversion_count"+pdbver_rs.getRow());
        columns.put("pdbversion_count", pdbver_rs.getRow());
        
        //Get PDB Versions count
        String pdbfea_sql = "select * from features";
        ResultSet pdbfea_rs = statement.executeQuery(pdbfea_sql);
        pdbfea_rs.last(); 
        System.out.println("pdbfeatures_count"+pdbfea_rs.getRow());
        columns.put("pdbfeatures_count", pdbfea_rs.getRow());
        
        //Get Vehicle Versions count
        String vehver_sql = "select * from vehicleversion";
        ResultSet vehver_rs = statement.executeQuery(vehver_sql);
        vehver_rs.last(); 
        System.out.println("resultset_count"+vehver_rs.getRow());
        columns.put("vehicleversion_count", vehver_rs.getRow());
        
        return columns;
    }
    
    public static void deletePDBVersion_Group(int pdbversion_id, String action_type) throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        System.out.println("deletepdbversiongroup"+GlobalDataStore.globalData);
        System.out.println("action_type"+action_type);
        if(action_type.equals("update")){
            connection = ConnectionConfiguration.getConnection();
            preparedStatement = connection.prepareStatement("delete from pdbversion_group where pdbversion_id="+pdbversion_id+" AND id NOT IN ("+StringUtils.join(GlobalDataStore.globalData, ',')+")");
            preparedStatement.executeUpdate();
        }
        GlobalDataStore.globalData.clear();
    }

    public static Object[] getDomainFeatureId(String domain_name, String feature_name) {
        Connection connection = null;
        ResultSet resultSet = null;
        int domain_id = 0,feature_id = 0;
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();

            String fetch_domainId = "SELECT id FROM domain WHERE domain_name = '"+domain_name+"'";
            resultSet = statement.executeQuery(fetch_domainId);
            resultSet.last();
            if (resultSet.getRow() != 0) {
                domain_id = resultSet.getInt("id");
            }
            resultSet = null;
            String fetch_featureId = "SELECT id FROM features WHERE feature_name = '"+feature_name+"'";
            resultSet = statement.executeQuery(fetch_featureId);
            resultSet.last();
            if (resultSet.getRow() != 0) {
                feature_id = resultSet.getInt("id");
            }
            return new Object[]{domain_id,feature_id};
        } catch (Exception e) {
            System.out.println("Error on Fetching Domain & Feature Id" + e.getMessage());
            e.printStackTrace();
            return new Object[]{domain_id,feature_id};

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return new Object[]{domain_id,feature_id};
                }
            }
        }
    }
    
    public static int getFeatureId(String feature_name) {
        Connection connection = null;
        ResultSet resultSet = null;
        int feature_id = 0;
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();

            String fetch_featureId = "SELECT id FROM features WHERE feature_name = '"+feature_name+"'";
            resultSet = statement.executeQuery(fetch_featureId);
            resultSet.last();
            if (resultSet.getRow() != 0) {
                feature_id = resultSet.getInt("id");
            }
            return feature_id;
        } catch (Exception e) {
            System.out.println("Error on Fetching Domain & Feature Id" + e.getMessage());
            e.printStackTrace();
            return feature_id;

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return feature_id;
                }
            }
        }
    }

    public static int getDomainFeatureMappingId(Object[] obj) {
        Connection connection = null;
        ResultSet resultSet = null;
        int vmm_id = 0;
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();

            String fetch_dfmId = "SELECT id FROM domain_and_features_mapping WHERE domain_id = " + (int) obj[0] + " AND feature_id = " + (int) obj[1];
            resultSet = statement.executeQuery(fetch_dfmId);
            resultSet.last();
            if (resultSet.getRow() != 0) {
                vmm_id = resultSet.getInt("id");
            }

            return vmm_id;
        } catch (Exception e) {
            System.out.println("Error on Fetching Vehicle & Model Id" + e.getMessage());
            e.printStackTrace();
            return vmm_id;

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return vmm_id;
                }
            }
        }
    }
    
    public static Object[] getFeaturesChartCount() {
        Connection connection = null;
        ResultSet resultSet = null;
        int featuresCount = 0,touchedCount = 0;
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();

            String fetch_featuresCount = "SELECT COUNT(*) FROM domain_and_features_mapping";
            String fetch_touchedCount = "SELECT COUNT(DISTINCT domain_and_features_mapping_id) FROM acbversion_group";
            resultSet = statement.executeQuery(fetch_featuresCount);
            if (resultSet.next()) {
                featuresCount = resultSet.getInt(1);
            }
            resultSet = statement.executeQuery(fetch_touchedCount);
            if (resultSet.next()) {
                touchedCount = resultSet.getInt(1);
            }

            return new Object[]{featuresCount,touchedCount};
        } catch (Exception e) {
            System.out.println("Error on Fetching Features Touched Count" + e.getMessage());
            e.printStackTrace();
            return new Object[]{featuresCount,touchedCount};

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return new Object[]{featuresCount,touchedCount};
                }
            }
        }
    }
    
    public static float getPDBVersionNameFromId(int id){
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();

            String fetch_pdbversionname = "SELECT pdb_versionname FROM pdbversion WHERE id = " + id;
            resultSet = statement.executeQuery(fetch_pdbversionname);
            resultSet.last();
            if (resultSet.getRow() != 0) {
                return resultSet.getFloat(1);
            }
        } catch (Exception e) {
            System.out.println("Error on Fetching PDB Version Name " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    public static int getIdFromPDBVersionName(float versionName){
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();

            String fetch_pdbversionname = "SELECT id FROM pdbversion WHERE pdb_versionname = " + versionName;
            resultSet = statement.executeQuery(fetch_pdbversionname);
            resultSet.last();
            if (resultSet.getRow() != 0) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error on Fetching PDB Version Name Id" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }
    
    public static int getIdFromPDBVersionGroup(int pdbversion_id, int vmm_id, int dfm_id){
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();

            String fetch_pdbversiongroup_id = "SELECT id FROM pdbversion_group WHERE pdbversion_id = " + pdbversion_id +" AND vehicle_and_model_mapping_id = "+vmm_id + " AND domain_and_features_mapping_id = "+dfm_id;
            resultSet = statement.executeQuery(fetch_pdbversiongroup_id);
            resultSet.last();
            if (resultSet.getRow() != 0) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error on Fetching PDB Version Group Id" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }
    
    public static void deleteDependentPDBVersion(int versionId){
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();
            String fetch_pdbversiongroup_id = "SELECT GROUP_CONCAT(DISTINCT acbversion_id) FROM acbversion_group WHERE pdbversion_id = "+ versionId;
            String acbVersionId = "";
            resultSet = statement.executeQuery(fetch_pdbversiongroup_id);
            if (resultSet.next()) {
                acbVersionId = resultSet.getString(1);
            }
            String delete_acbVerQuery = "DELETE FROM acbversion WHERE id IN ("+acbVersionId+")";
            statement.executeUpdate(delete_acbVerQuery);
        } catch (Exception e) {
            System.out.println("Error on deleting PDB Dependency " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

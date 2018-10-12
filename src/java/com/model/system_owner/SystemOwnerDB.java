/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.system_owner;

import com.db_connection.ConnectionConfiguration;
import static com.model.acb_owner.ACBOwnerDB.LoadIVNDataForACBVersion;
import static com.model.acb_owner.ACBOwnerDB.LoadPDBDataForACBVersion;
import static com.model.acb_owner.ACBOwnerDB.LoadPDBandIVN_Version;
import com.model.acb_owner.ACBversion;
import com.model.common.GlobalDataStore;
import com.model.ivn_engineer.IVNversion;
import com.model.ivn_supervisor.ModelVersionGroup;
import com.model.ivn_supervisor.Modelversion;
import com.model.ivn_supervisor.Vehicle_and_Model_Mapping;
import com.model.ivn_supervisor.Vehicleversion;
import com.model.ivn_supervisor.VehicleversionDB;
import static com.model.ivn_supervisor.VehicleversionDB.perm_status;
import static com.model.ivn_supervisor.VehicleversionDB.temp_status;
import com.model.pdb_owner.Domain;
import com.model.pdb_owner.Domain_and_Features_Mapping;
import com.model.pdb_owner.Features;
import com.model.pdb_owner.PDBversion;
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
public class SystemOwnerDB {
    public static List<Map<String, Object>> GetECU_Listing() throws SQLException 
    {
        System.out.println("GetECU_Listing");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
            String sql = "select CAST(ecu.id as CHAR(100)) as eid,GROUP_CONCAT(DISTINCT(ecu.ecu_name)) as listitem,ecu.ecu_description as description, "
                    + "ecu.status,GROUP_CONCAT(v.variant_name) as variants,GROUP_CONCAT(v.id) as variant_id from engine_control_unit as ecu "
                    + "LEFT JOIN ecu_and_variants_mapping as evm ON evm.ecu_id=ecu.id LEFT JOIN variants as v "
                    + "ON v.id=evm.variant_id group by ecu.id order by ecu.id desc";
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
            System.out.println("ecu error message"+e.getMessage()); 
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
    public static int insertVariants(Variants v) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether vehicle name already exists in db or not
            Statement statement = connection.createStatement();
            String sql = "SELECT id FROM variants WHERE variant_name ='"+v.getVariantname()+"'";
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
                preparedStatement = connection.prepareStatement("INSERT INTO variants (variant_name,created_date,created_or_updated_by)" +
                    "VALUES (?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, v.getVariantname());
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
            System.out.println("Variants error message"+e.getMessage()); 
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
    public static int insertEcuVariantsMapping(ECU_and_Variants_Mapping evm) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether vehicle name already exists in db or not
            Statement statement = connection.createStatement();
            String sql = "SELECT id FROM ecu_and_variants_mapping WHERE ecu_id ="+evm.getEcuId() +" AND variant_id="+evm.getVariantId();
            ResultSet resultSet = statement.executeQuery(sql);          
            resultSet.last(); 
//            System.out.println("vehicle_row_count"+resultSet.getRow());
//            System.out.println(statement);
            if(resultSet.getRow()>0){
//                System.out.println("if");
                GlobalDataStore.globalData.add(resultSet.getInt(1));
                int last_inserted_id = resultSet.getInt(1);
                return last_inserted_id;
                
            }else{
//                System.out.println("else");
                preparedStatement = connection.prepareStatement("INSERT INTO ecu_and_variants_mapping (ecu_id,variant_id,created_date)" +
                    "VALUES (?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, evm.getEcuId());
                preparedStatement.setInt(2, evm.getVariantId());
                preparedStatement.setString(3, evm.getCreated_date());
                preparedStatement.executeUpdate();


                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next())
                {
                    GlobalDataStore.globalData.add(rs.getInt(1));
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
    public static List<Map<String, Object>> LoadACBVersion_for_System(Vehicle_and_Model_Mapping vmm) throws SQLException {
        System.out.println("LoadACBVersion_for_System");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> acb_row = new ArrayList<Map<String, Object>>();
//        Map<String, Object> columns = new HashMap<String, Object>();
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();                                  
            
//            String acb_sql = "select acb.id,CAST(acb.acb_versionname as CHAR(100)) as acb_versionname from acbversion_group as acbg INNER JOIN acbversion as acb ON acb.id=acbg.acbversion_id where acbg.vehicleversion_id="+vmm.getVehicleversion_id()+" and acbg.vehicle_id="+vmm.getVehicle_id()+" AND acb.status=1 AND acb.subversion_of IS NULL group by acbg.acbversion_id";
            String acb_sql = "select acb.id,CAST(acb.acb_versionname as CHAR(100)) as acb_versionname from acbversion_group as acbg INNER JOIN acbversion as acb ON acb.id=acbg.acbversion_id where acbg.vehicleversion_id="+vmm.getVehicleversion_id()+" and acbg.vehicle_id="+vmm.getVehicle_id()+" AND acb.status=1 AND acb.flag=1 AND acb.features_fully_touchedstatus=1 group by acbg.acbversion_id order by acb.id DESC";
            System.out.println("acb_sql"+acb_sql);
            ResultSet acb_rs = statement.executeQuery(acb_sql);        
            ResultSetMetaData acb_metaData = acb_rs.getMetaData();
            int acb_colCount = acb_metaData.getColumnCount();          
            while (acb_rs.next()) {
              Map<String, Object> ivn_columns = new HashMap<String, Object>();
              for (int i = 1; i <= acb_colCount; i++) {
                ivn_columns.put(acb_metaData.getColumnLabel(i), acb_rs.getObject(i));
              }
              acb_row.add(ivn_columns);
            }

//            columns.put("acbversion_list",acb_row);
//            System.out.println("columns"+columns);
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
        return acb_row;
    }
    public static Map<String, Object> LoadACBDataForSystemVersion(ACBversion acbver,int vehver_id,int veh_id) throws SQLException {
        System.out.println("LoadACBDataForSystemVersion");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Map<String, Object> columns_res = new HashMap<String, Object>();
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();
            
            String ecu_sql = "select CAST(ag.ecu_id as CHAR(100)) as eid, ecu.ecu_name as listitem,GROUP_CONCAT(DISTINCT(v.id)) as variant_id,GROUP_CONCAT(DISTINCT(v.variant_name)) as variant_name from acbversion_group as ag INNER JOIN engine_control_unit as ecu ON ecu.id=ag.ecu_id INNER JOIN "
                    + "ecu_and_variants_mapping as ev ON ev.ecu_id=ecu.id INNER JOIN variants as v ON v.id=ev.variant_id where ag.vehicleversion_id="+vehver_id+" "
                    + "AND ag.vehicle_id="+veh_id+" AND ag.acbversion_id="+acbver.getId()+" GROUP by ag.ecu_id";           
            System.out.println(ecu_sql);
            ResultSet resultSet = statement.executeQuery(ecu_sql);
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
            
            String feature_sql = "select CAST(ag.ecu_id as CHAR(100)) as eid, CAST(ag.domain_and_features_mapping_id as CHAR(100)) as fid,f.feature_name as featurename,d.domain_name as domainname"
                    + " from acbversion_group as ag INNER JOIN domain_and_features_mapping as dfm ON dfm.id=ag.domain_and_features_mapping_id"
                    + " INNER JOIN domain as d ON d.id=dfm.domain_id INNER JOIN features as f ON f.id=dfm.feature_id where ag.vehicleversion_id="+vehver_id+" "
                    + "AND ag.vehicle_id="+veh_id+" AND ag.acbversion_id="+acbver.getId();           
            System.out.println(feature_sql);
            ResultSet resultSet_fea = statement.executeQuery(feature_sql);
            ResultSetMetaData metaData_fea = resultSet_fea.getMetaData();
            int colCount_fea = metaData_fea.getColumnCount();
            List<Map<String, Object>> row_fea = new ArrayList<Map<String, Object>>();
            while (resultSet_fea.next()) {
              Map<String, Object> columns_fea = new HashMap<String, Object>();
              for (int i = 1; i <= colCount_fea; i++) {
                columns_fea.put(metaData_fea.getColumnLabel(i), resultSet_fea.getObject(i));
              }
              row_fea.add(columns_fea);
            }   
                      
            columns_res.put("ecu_list",row);
            columns_res.put("feature_list",row_fea);
        
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
        return columns_res;
    }
    public static List<Map<String, Object>> LoadSystemPreviousVehicleversionStatus(Systemversion m) throws SQLException {
        System.out.println("LoadSystemPreviousVehicleversionStatus");
//        String status = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
    //        String sql = "select v.id,v.versionname,v.status from vehicleversion v where v.status=1";
            String sql = "select s.status,s.flag from systemversion s where s.id="+m.getId();
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
            System.out.println("System version error message"+e.getMessage()); 
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
    public static int insertSystemVersion(Systemversion sv) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        float versionname;
        try {
            connection = ConnectionConfiguration.getConnection();
            
            Statement statement = connection.createStatement();
            System.out.println("status_value"+sv.getStatus());
            System.out.println("flag_value"+sv.getFlag());
            if(sv.getOperation_status().equals("create")){
                String sql = "SELECT id, system_versionname FROM systemversion ORDER BY system_versionname DESC LIMIT 1";
                ResultSet resultSet = statement.executeQuery(sql);          
                resultSet.last();    
                if(resultSet.getRow()==0){
                    versionname = (float) 1.0;
                }
                else{
                    versionname = (float) 1.0 + resultSet.getFloat("system_versionname");
                }           
                preparedStatement = connection.prepareStatement("INSERT INTO systemversion (system_versionname,status,created_date,created_or_updated_by,flag)" +
                        "VALUES (?, ?, ?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
    //            preparedStatement.setString(1, v.getVersionname());
                preparedStatement.setDouble(1, versionname);
                preparedStatement.setBoolean(2, sv.getStatus());
                preparedStatement.setString(3, sv.getCreated_date());
                preparedStatement.setInt(4, sv.getCreated_or_updated_by());
                preparedStatement.setBoolean(5, sv.getFlag());
                preparedStatement.executeUpdate();


                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next())
                {
                    int last_inserted_id = rs.getInt(1);
                    return last_inserted_id;
                }
            }
            else{       
                System.out.println("object_value_in_update"+sv.getId()+sv.getStatus()+sv.getCreated_or_updated_by());
                String sql = "UPDATE modelversion SET " +
                    "status = ?, created_or_updated_by = ?, flag=?   WHERE id = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setBoolean(1, sv.getStatus());
                preparedStatement.setInt(2, sv.getCreated_or_updated_by());
                preparedStatement.setBoolean(3, sv.getFlag());
                preparedStatement.setInt(4, sv.getId());
                preparedStatement.executeUpdate();                
                return sv.getId();
            }                
        } catch (Exception e) {
            System.out.println("System version error message"+e.getMessage()); 
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
    public static int insertSystemVersionGroup(SystemVersionGroup sg) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
//        System.out.println("button_type"+v.getButton_type());
        int resultSet_count = 0;
        try {
            boolean flagvalue;
            connection = ConnectionConfiguration.getConnection();
            if(sg.getOperation_status().equals("update")){
                System.out.println("update_if");
                Statement statement = connection.createStatement();
//                String sql = "select pg.id from pdbversion_group as pg where "
//                        + "pg.pdbversion_id="+pg.getPDBversion_id()
//                        + " AND pg.vehicle_and_model_mapping_id="+pg.getVehicle_and_model_mapping_id()+" AND pg.domain_and_features_mapping_id="+pg.getDomain_and_features_mapping_id()
//                        + " AND pg.available_status='"+pg.getAvailable_status()+"'";
                String sql = "select * from systemversion_group as sg where "
                        + "sg.systemversion_id="+sg.getSystemversion_id()
                        + " AND sg.vehicleversion_id="+sg.getVehicleversion_id()+" AND sg.vehicle_id="+sg.getVehicle_id()
                        + " AND sg.acbversion_id="+sg.getACBversion_id()
                        + " AND sg.domain_and_features_mapping_id="+sg.getDomain_and_features_mapping_id()+" AND sg.ecu_id="+sg.getEcu_id()+" AND sg.variant_id="+sg.getVariant_id();
                System.out.println("sql_query"+sql);
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                        System.out.println("while");
                        if(resultSet.getString("available_status") != sg.getAvailable_status()){
                            System.out.println("if");
                            String update_sql = "UPDATE systemversion_group SET " +
                                "available_status = ?  WHERE id = ?";
                            preparedStatement = connection.prepareStatement(update_sql);
                            preparedStatement.setString(1, sg.getAvailable_status()); 
                            preparedStatement.setInt(2, resultSet.getInt("id"));             
                            preparedStatement.executeUpdate(); 
                        }
                        GlobalDataStore.globalData.add(resultSet.getInt("id"));                  
                }                            
                resultSet.last(); 
                resultSet_count = resultSet.getRow();
                System.out.println("getrow_count"+resultSet.getRow());                           
            }            
            if(resultSet_count == 0){
                preparedStatement = connection.prepareStatement("INSERT INTO systemversion_group (systemversion_id, vehicleversion_id, vehicle_id, acbversion_id, domain_and_features_mapping_id, ecu_id ,variant_id, available_status)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, sg.getSystemversion_id());
                preparedStatement.setInt(2, sg.getVehicleversion_id());
                preparedStatement.setInt(3, sg.getVehicle_id());
                preparedStatement.setInt(4, sg.getACBversion_id());
                preparedStatement.setInt(5, sg.getDomain_and_features_mapping_id());
                preparedStatement.setInt(6, sg.getEcu_id());
                preparedStatement.setInt(7, sg.getVariant_id());
                preparedStatement.setString(8, sg.getAvailable_status());
                preparedStatement.executeUpdate();
                
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next())
                {
                    GlobalDataStore.globalData.add(rs.getInt(1));
                }
            }
            System.out.println("globalData"+GlobalDataStore.globalData);
            if(sg.getButton_type().equals("save")){
                return temp_status;
            }
            else if(sg.getButton_type().equals("submit")){
                return perm_status;
            }
        } catch (Exception e) {
            System.out.println("system version error message"+e.getMessage()); 
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
    public static List<Map<String, Object>> LoadSystemVersion(String filter) throws SQLException {
        System.out.println("LoadSystemVersion");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
            String sql;
            if(filter.equals("active"))
                sql = "select s.id,s.system_versionname,s.status from systemversion s where s.flag=1 and s.status=1";
            else
                sql = "select s.id,s.system_versionname,s.status from systemversion s";
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
            System.out.println("System version error message"+e.getMessage()); 
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
    public static Map<String, Object> LoadSystemPreviousversionData(Systemversion systemver) throws SQLException {
        System.out.println("LoadSystemPreviousversionData");
//        int pdbversion_id = 0;
//        int ivnversion_id = 0;
//        int vehicleversion_id = 0;
//        int vehicle_id = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Map<String, Object> columns_res = new HashMap<String, Object>();
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();
    //        List<Map<String, Object>> result_row = new ArrayList<Map<String, Object>>();
            String systemversion_sql = "select CAST(svg.vehicleversion_id as CHAR(100)) as vehicleversion,CAST(svg.vehicle_id as CHAR(100)) as vehiclename,"
                    + "CAST(svg.acbversion_id as CHAR(100)) as acbversion, "
                    + "CAST(svg.ecu_id as CHAR(100)) as ecu "
                    + "from systemversion_group as svg where svg.systemversion_id="+systemver.getId()+" LIMIT 1"; 
            System.out.println("modelversion_sql"+systemversion_sql);
            ResultSet rs_svg = statement.executeQuery(systemversion_sql);
            ResultSetMetaData metaData_svg = rs_svg.getMetaData();
            int colCount_svg = metaData_svg.getColumnCount();
            List<Map<String, Object>> row_svg = new ArrayList<Map<String, Object>>();
            while (rs_svg.next()) {
              Map<String, Object> columns_svg = new HashMap<String, Object>();
              for (int i = 1; i <= colCount_svg; i++) {
                columns_svg.put(metaData_svg.getColumnLabel(i), rs_svg.getObject(i));
              }
              row_svg.add(columns_svg);
            }           

            String feature_sql = "select CAST(svg.domain_and_features_mapping_id as CHAR(100)) as fid,d.domain_name as domainname,f.feature_name as featurename from systemversion_group as svg "
                    + "inner join domain_and_features_mapping as dfm on dfm.id = svg.domain_and_features_mapping_id INNER JOIN domain as d ON d.id = dfm.domain_id \n"
                    + "INNER JOIN features as f ON f.id = dfm.feature_id \n" 
                    + "where svg.systemversion_id="+systemver.getId()+" group by svg.domain_and_features_mapping_id"; 
            System.out.println("feature_sql"+feature_sql);
            ResultSet rs_fea = statement.executeQuery(feature_sql);
            ResultSetMetaData metaData_fea = rs_fea.getMetaData();
            int colCount_fea = metaData_fea.getColumnCount();
            List<Map<String, Object>> row_fea = new ArrayList<Map<String, Object>>();
            while (rs_fea.next()) {
              Map<String, Object> columns_fea = new HashMap<String, Object>();
              for (int i = 1; i <= colCount_fea; i++) {
                columns_fea.put(metaData_fea.getColumnLabel(i), rs_fea.getObject(i));
              }
              row_fea.add(columns_fea);
            }
            
            String ecu_sql = "select CAST(svg.ecu_id as CHAR(100)) as eid, ecu.ecu_name as listitem,GROUP_CONCAT(DISTINCT(v.id)) as variant_id,GROUP_CONCAT(DISTINCT(v.variant_name)) as variant_name from systemversion_group as svg "
                    + "INNER JOIN engine_control_unit as ecu ON ecu.id=svg.ecu_id "
                    + "INNER JOIN variants as v ON v.id=svg.variant_id where svg.systemversion_id="+systemver.getId()+" GROUP by svg.ecu_id ORDER BY svg.ecu_id DESC";           
            System.out.println("ecu_sql"+ecu_sql);
            ResultSet resultSet = statement.executeQuery(ecu_sql);
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

            String svglist_sql = "select CAST(svg.domain_and_features_mapping_id as CHAR(100)) as dfm_id,CAST(svg.variant_id as CHAR(100)) as variant_id, svg.available_status as status from systemversion_group as svg where svg.systemversion_id="+systemver.getId()+" ORDER BY svg.id DESC";           
            System.out.println("svglist_sql"+svglist_sql);
            ResultSet rs_svglist = statement.executeQuery(svglist_sql);
            ResultSetMetaData metaData_svglist = rs_svglist.getMetaData();
            int colCount_svglist = metaData_svglist.getColumnCount();
            List<Map<String, Object>> row_svglist = new ArrayList<Map<String, Object>>();
            while (rs_svglist.next()) {
              Map<String, Object> columns_svglist = new HashMap<String, Object>();
              for (int i = 1; i <= colCount_svglist; i++) {
                columns_svglist.put(metaData_svglist.getColumnLabel(i), rs_svglist.getObject(i));
              }
              row_svglist.add(columns_svglist);
            }             
            
            String sv_status_sql = "select sv.status from systemversion sv where sv.id="+systemver.getId();
            ResultSet resultSet_st = statement.executeQuery(sv_status_sql);
            ResultSetMetaData metaData_st = resultSet_st.getMetaData();
            int colCount_st = metaData_st.getColumnCount();
            List<Map<String, Object>> row_st = new ArrayList<Map<String, Object>>();
            while (resultSet_st.next()) {
              Map<String, Object> columns_st = new HashMap<String, Object>();
              for (int i = 1; i <= colCount_st; i++) {
                columns_st.put(metaData_st.getColumnLabel(i), resultSet_st.getObject(i));
              }
              row_st.add(columns_st);
            }
                       

            columns_res.put("systemversion",row_svg);
            columns_res.put("feature_list",row_fea);
            columns_res.put("ecu_variant_list",row);
            columns_res.put("systemdata_list",row_svglist);
            columns_res.put("systemversion_status",row_st);
        
        } catch (Exception e) {
            System.out.println("Load System version error message"+e.getMessage()); 
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
        return columns_res;
    }
    public static void deleteEcuVariantsMapping(int ecu_id) throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        System.out.println("deleteEcuVariantsMapping"+GlobalDataStore.globalData);
        connection = ConnectionConfiguration.getConnection();
        preparedStatement = connection.prepareStatement("delete from ecu_and_variants_mapping where ecu_id="+ecu_id+" AND id NOT IN ("+StringUtils.join(GlobalDataStore.globalData, ',')+")");
        preparedStatement.executeUpdate();
        GlobalDataStore.globalData.clear();
    }
    public static void deleteSystemVersion_Group(int systemversion_id, String action_type) throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        System.out.println("deletesystemversiongroup"+GlobalDataStore.globalData);
        System.out.println("action_type"+action_type);
        if(action_type.equals("update")){
            connection = ConnectionConfiguration.getConnection();
            preparedStatement = connection.prepareStatement("delete from systemversion_group where systemversion_id="+systemversion_id+" AND id NOT IN ("+StringUtils.join(GlobalDataStore.globalData, ',')+")");
            preparedStatement.executeUpdate();
        }
        GlobalDataStore.globalData.clear();
    }
}

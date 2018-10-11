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
import com.model.ivn_supervisor.Vehicle_and_Model_Mapping;
import com.model.ivn_supervisor.Vehicleversion;
import com.model.ivn_supervisor.VehicleversionDB;
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
            
//            String acb_sub_sql = "select * from acbversion a INNER JOIN acbversion_group as ag ON ag.acbversion_id=a.id where a.subversion_of="+acbver.getId()+" AND ag.vehicleversion_id="+vehver_id+" AND ag.vehicle_id="+veh_id+" group by ag.acbversion_id";
//            ResultSet resultSet_sub = statement.executeQuery(acb_sub_sql);
//            ResultSetMetaData metaData_sub = resultSet_sub.getMetaData();
//            int colCount_sub = metaData_sub.getColumnCount();
//            List<Map<String, Object>> row_sub = new ArrayList<Map<String, Object>>();
//            while (resultSet_sub.next()) {
//              Map<String, Object> columns_sub = new HashMap<String, Object>();
//              for (int i = 1; i <= colCount_sub; i++) {
//                columns_sub.put(metaData_sub.getColumnLabel(i), resultSet_sub.getObject(i));
//              }
//              row_sub.add(columns_sub);
//            }
//            columns_res.put("acbsubversion",row_sub);
        
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
    public static void deleteEcuVariantsMapping(int ecu_id) throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        System.out.println("deleteEcuVariantsMapping"+GlobalDataStore.globalData);
        connection = ConnectionConfiguration.getConnection();
        preparedStatement = connection.prepareStatement("delete from ecu_and_variants_mapping where ecu_id="+ecu_id+" AND id NOT IN ("+StringUtils.join(GlobalDataStore.globalData, ',')+")");
        preparedStatement.executeUpdate();
        GlobalDataStore.globalData.clear();
    }
}

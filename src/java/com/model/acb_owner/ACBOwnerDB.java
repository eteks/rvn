/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.acb_owner;

import com.db_connection.ConnectionConfiguration;
import com.model.common.GlobalDataStore;
import static com.model.ivn_engineer.IVNEngineerDB.perm_status;
import static com.model.ivn_engineer.IVNEngineerDB.temp_status;
import com.model.ivn_engineer.IVNNetwork_VehicleModel;
import com.model.ivn_engineer.IVNVersionGroup;
import com.model.ivn_engineer.IVNversion;
import com.model.ivn_supervisor.Vehicle_and_Model_Mapping;
import com.model.ivn_supervisor.Vehicleversion;
import com.model.ivn_supervisor.VehicleversionDB;
import com.model.pdb_owner.PDBversion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ets-2
 */
public class ACBOwnerDB {
//    public static ArrayList ip_signals=new ArrayList();
//     public static ArrayList op_signals=new ip_signalsArrayList();
    public static List<Map<String, Object>> LoadACBVersion(String filter) throws SQLException {
        System.out.println("LoadACBVersion");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
            String sql;
            if(filter.equals("active"))
                sql = "select a.id,a.acb_versionname,a.status from acbversion a where a.flag=1 and a.status=1 and a.subversion_of IS NULL";
            else
                sql = "select a.id,a.acb_versionname,a.status from acbversion a where a.subversion_of IS NULL";
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
    
    public static Map<String, Object> LoadPDBDataForACBVersion(PDBversion pdbver) throws SQLException {
        System.out.println("LoadPDBPreviousVehicleversionData");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Map<String, Object> columns3 = new HashMap<String, Object>();
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();
            List<Map<String, Object>> result_row = new ArrayList<Map<String, Object>>();
            String vehciledetail_sql = "SELECT \n" +
                        "vv.id as vehver_id,\n" +
                        "v.id as vehicle_id,\n" +
                        "vm.modelname as modelname,\n" +
                        "CAST(vmm.id as CHAR(100)) as vmm_id \n" +
                        "FROM pdbversion_group AS pg \n" +
                        "INNER JOIN vehicle_and_model_mapping AS vmm ON vmm.id = pg.vehicle_and_model_mapping_id \n" +
                        "INNER JOIN vehicleversion as vv on vv.id=vmm.vehicleversion_id \n" +
                        "INNER JOIN vehicle as v on v.id=vmm.vehicle_id \n" +
                        "INNER JOIN vehiclemodel as vm on vm.id=vmm.model_id\n" +
                        "where pg.pdbversion_id="+pdbver.getId()+" group by modelname,vmm_id order by vmm_id";
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

            String featuredetail_sql = "SELECT GROUP_CONCAT(DISTINCT(pg.id)) as pdbgroup_id, GROUP_CONCAT(DISTINCT(CAST(pg.vehicle_and_model_mapping_id as CHAR(100)))) as vmm_id,\n" +
                "CAST(pg.domain_and_features_mapping_id as CHAR(100)) as fid,\n" +
                "GROUP_CONCAT(pg.available_status) as status,\n" +
                "d.domain_name as domainname,\n" +
                "f.feature_name as featurename FROM pdbversion_group AS pg \n" +
                "right JOIN vehicle_and_model_mapping AS vmm ON vmm.id = pg.vehicle_and_model_mapping_id \n" +
                "INNER JOIN domain_and_features_mapping AS dfm ON dfm.id = pg.domain_and_features_mapping_id \n" +
                "INNER JOIN domain as d on d.id=dfm.domain_id \n" +
                "INNER JOIN features as f on f.id=dfm.feature_id \n" +
                "where pg.pdbversion_id="+pdbver.getId()+" group by fid";

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
    public static Map<String, Object> LoadIVNDataForACBVersion(IVNversion ivnver) throws SQLException {
        System.out.println("LoadIVNPreviousVehicleversionData");
        Connection connection = null;
        PreparedStatement preparedStatement = null;     
        Map<String, Object> columns_res = new HashMap<String, Object>();    
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();
            List<Map<String, Object>> result_row = new ArrayList<Map<String, Object>>();
    //        String canmodel_sql = "SELECT CAST(cn.network_can_id as CHAR(100)) as network_id,\n" +
    //            "CAST(cn.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n" +
    //            "cn.available_status as status \n" +
    //            "FROM ivn_canmodels AS cn \n" +
    //            "where cn.ivnversion_id="+ivnver.getId();      
            String canmodel_sql = "SELECT CAST(cn.network_can_id as CHAR(100)) as id,\n" +
                "c.network_name as listitem,\n" +
                "CAST(cn.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n" +
                "cn.available_status as status \n" +
                "FROM ivn_canmodels AS cn INNER JOIN network as c ON c.id=cn.network_can_id \n" +
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
              columns1.put("ntype","can");
              row1.add(columns1);
            }

    //        String linmodel_sql = "SELECT CAST(ln.network_lin_id as CHAR(100)) as network_id,\n" +
    //            "CAST(ln.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n" +
    //            "ln.available_status as status \n" +
    //            "FROM ivn_linmodels AS ln \n" +
    //            "where ln.ivnversion_id="+ivnver.getId(); 
            String linmodel_sql = "SELECT CAST(ln.network_lin_id as CHAR(100)) as id,\n" +
                "l.network_name as listitem,\n" +
                "CAST(ln.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n" +
                "ln.available_status as status \n" +
                "FROM ivn_linmodels AS ln INNER JOIN network as l ON l.id=ln.network_lin_id \n" +
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
              columns2.put("ntype","lin");
              row2.add(columns2);
            }

    //        String hwmodel_sql = "SELECT CAST(hw.network_hardware_id as CHAR(100)) as network_id,\n" +
    //            "CAST(hw.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n" +
    //            "hw.available_status as status \n" +
    //            "FROM ivn_hardwaremodels AS hw \n" +
    //            "where hw.ivnversion_id="+ivnver.getId();       
            String hwmodel_sql = "SELECT CAST(hw.network_hardware_id as CHAR(100)) as id,\n" +
                "h.network_name as listitem,\n" +
                "CAST(hw.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n" +
                "hw.available_status as status \n" +
                "FROM ivn_hardwaremodels AS hw INNER JOIN network as h ON h.id=hw.network_hardware_id \n" +
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
              columns3.put("ntype","hardware");
              row3.add(columns3);
            }

            String ivnsignalgroup_sql = "select CAST(s.id as CHAR(100)) as sid,s.signal_name as listitem,s.signal_description as description from ivnversion_group as ig inner join signals as s "
                    + "on FIND_IN_SET(s.id,ig.signal_group) > 0 where ig.ivnversion_id="+ivnver.getId();       
            System.out.println(ivnsignalgroup_sql);
            ResultSet resultSet_sig = statement.executeQuery(ivnsignalgroup_sql);
            ResultSetMetaData metaData_sig = resultSet_sig.getMetaData();
            int colCount_sig = metaData_sig.getColumnCount();
            List<Map<String, Object>> row_sig = new ArrayList<Map<String, Object>>();
            while (resultSet_sig.next()) {
              Map<String, Object> columns_sig = new HashMap<String, Object>();
              for (int i = 1; i <= colCount_sig; i++) {
                columns_sig.put(metaData_sig.getColumnLabel(i), resultSet_sig.getObject(i));
              }
              columns_sig.put("network_type","signal");
              row_sig.add(columns_sig);
            }

            String ivnecugroup_sql = "select CAST(e.id as CHAR(100)) as eid,e.ecu_name as listitem,e.ecu_description as description from ivnversion_group as ig inner join engine_control_unit as e "
                    + "on FIND_IN_SET(e.id,ig.ecu_group) > 0 where ig.ivnversion_id="+ivnver.getId();       
            System.out.println(ivnecugroup_sql);
            ResultSet resultSet_ecu = statement.executeQuery(ivnecugroup_sql);
            ResultSetMetaData metaData_ecu = resultSet_ecu.getMetaData();
            int colCount_ecu = metaData_ecu.getColumnCount();
            List<Map<String, Object>> row_ecu = new ArrayList<Map<String, Object>>();
            while (resultSet_ecu.next()) {
              Map<String, Object> columns_ecu = new HashMap<String, Object>();
              for (int i = 1; i <= colCount_ecu; i++) {
                columns_ecu.put(metaData_ecu.getColumnLabel(i), resultSet_ecu.getObject(i));
              }
              columns_ecu.put("network_type","ecu");
              row_ecu.add(columns_ecu);
            }

            String v_sql = "SELECT \n" +
                "vmm.vehicleversion_id,vmm.vehicle_id \n" +
                "FROM ivn_canmodels AS cn \n" +
                "INNER JOIN vehicle_and_model_mapping AS vmm ON vmm.id = cn.vehicle_and_model_mapping_id \n" +
                "where cn.ivnversion_id="+ivnver.getId()+" limit 1";

            System.out.println("vehciledetail_sql"+v_sql);
            ResultSet vrs = statement.executeQuery(v_sql);
            String vehciledetail_sql = null;
            if(vrs.next()){
                vehciledetail_sql = "SELECT \n" +
                "vv.id as vehver_id,\n" +
                "v.id as vehicle_id,\n" +
                "vm.modelname as modelname,\n" +
                "CAST(vmm.id as CHAR(100)) as vmm_id \n" +
                "from vehicle_and_model_mapping as vmm \n" +
                "INNER JOIN vehicleversion as vv on vv.id=vmm.vehicleversion_id \n" +
                "INNER JOIN vehicle as v on v.id=vmm.vehicle_id \n" +
                "INNER JOIN vehiclemodel as vm on vm.id=vmm.model_id\n" +
                "where vmm.vehicleversion_id="+vrs.getInt("vehicleversion_id")+" AND vmm.vehicle_id="+vrs.getInt("vehicle_id");
            }
            ResultSet resultSet = statement.executeQuery(vehciledetail_sql);
            System.out.println("vehciledetail_sql1"+vehciledetail_sql);
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

    //        String ivn_status_sql = "select i.status from ivnversion i where i.id="+ivnver.getId();
    //        ResultSet resultSet5 = statement.executeQuery(ivn_status_sql);
    //        ResultSetMetaData metaData5 = resultSet5.getMetaData();
    //        int colCount5 = metaData5.getColumnCount();
    //        List<Map<String, Object>> row5 = new ArrayList<Map<String, Object>>();
    //        while (resultSet5.next()) {
    //          Map<String, Object> columns5 = new HashMap<String, Object>();
    //          for (int i = 1; i <= colCount5; i++) {
    //            columns5.put(metaData5.getColumnLabel(i), resultSet5.getObject(i));
    //          }
    //          row5.add(columns5);
    //        }
               
            columns_res.put("vehicledetail_list",row);      
            columns_res.put("can",row1);
            columns_res.put("lin",row2);
            columns_res.put("hardware",row3);
    //        columns_res.put("ivnversion_status",row5);
            columns_res.put("signal",row_sig);
            columns_res.put("ecu",row_ecu);
    //        System.out.println("columns"+columns_res);
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
    
    public static Map<String, Object> LoadPDBandIVN_Version(Vehicle_and_Model_Mapping vmm) throws SQLException {
        System.out.println("LoadACBVersion");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Map<String, Object> columns = new HashMap<String, Object>();
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();

            String sql = "select id from vehicle_and_model_mapping as vmm where vmm.vehicleversion_id="+vmm.getVehicleversion_id()+" and vmm.vehicle_id="+vmm.getVehicle_id();
            ResultSet resultSet = statement.executeQuery(sql);
            ArrayList<Integer> vmm_id = new ArrayList<Integer>();       
            while (resultSet.next()) {
                vmm_id.add(resultSet.getInt(1));
            }
            String join_vmm_id = StringUtils.join(vmm_id,",");
            System.out.println("join_vmm_id"+join_vmm_id);
            String pdb_sql = "select pdb.id,CAST(pdb.pdb_versionname as CHAR(100)) as pdb_versionname from pdbversion_group as pg INNER JOIN pdbversion as pdb ON pdb.id=pg.pdbversion_id where pg.vehicle_and_model_mapping_id IN ("+join_vmm_id+") AND pdb.status=1 group by pg.pdbversion_id";
            System.out.println("pdb_sql"+pdb_sql);
            ResultSet pdb_rs = statement.executeQuery(pdb_sql);        
            ResultSetMetaData pdb_metaData = pdb_rs.getMetaData();
            int pdb_colCount = pdb_metaData.getColumnCount();
            List<Map<String, Object>> pdb_row = new ArrayList<Map<String, Object>>();
            while (pdb_rs.next()) {
              Map<String, Object> pdb_columns = new HashMap<String, Object>();
              for (int i = 1; i <= pdb_colCount; i++) {
                pdb_columns.put(pdb_metaData.getColumnLabel(i), pdb_rs.getObject(i));
              }
              pdb_row.add(pdb_columns);
            }

            String ivn_sql = "select ivn.id,CAST(ivn.ivn_versionname as CHAR(100)) as ivn_versionname from ivn_canmodels as cn INNER JOIN ivnversion as ivn ON ivn.id=cn.ivnversion_id where cn.vehicle_and_model_mapping_id IN ("+join_vmm_id+") AND ivn.status=1 group by cn.ivnversion_id";
            System.out.println("ivn_sql"+ivn_sql);
            ResultSet ivn_rs = statement.executeQuery(ivn_sql);        
            ResultSetMetaData ivn_metaData = ivn_rs.getMetaData();
            int ivn_colCount = ivn_metaData.getColumnCount();
            List<Map<String, Object>> ivn_row = new ArrayList<Map<String, Object>>();
            while (ivn_rs.next()) {
              Map<String, Object> ivn_columns = new HashMap<String, Object>();
              for (int i = 1; i <= ivn_colCount; i++) {
                ivn_columns.put(ivn_metaData.getColumnLabel(i), ivn_rs.getObject(i));
              }
              ivn_row.add(ivn_columns);
            }

            columns.put("pdbversion_list",pdb_row);
            columns.put("ivnversion_list",ivn_row);
            System.out.println("columns"+columns);
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
        return columns;
    }
    public static List<Map<String, Object>> LoadACBPreviousVehicleversionStatus(ACBversion acb) throws SQLException {
        System.out.println("LoadACBPreviousVehicleversionStatus");
//        String status = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
    //        String sql = "select v.id,v.versionname,v.status from vehicleversion v where v.status=1";
            String sql = "select acb.status,acb.flag from acbversion acb where acb.id="+acb.getId();
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
    public static Object[] insertACBVersion(ACBversion acb) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        float versionname = 0.0f;
        String subversion_of = null;
        try {
            connection = ConnectionConfiguration.getConnection();
            
            Statement statement = connection.createStatement();
            if(acb.getOperation_status().equals("create")){
                String sql = "SELECT id, acb_versionname FROM acbversion where subversion_of IS NULL ORDER BY acb_versionname DESC LIMIT 1";
                ResultSet resultSet = statement.executeQuery(sql);          
                resultSet.last();                  
                if(resultSet.getRow()==0){
                    versionname = (float) 1.0;
                }
                else{
                    float acbversionname = resultSet.getFloat("acb_versionname");
                    if(acb.getSubversion_status() == "yes"){
                        if(acb.getIs_acbsubversion() == true){
                            String acbsql = "SELECT acb2.id, acb2.acb_versionname,acb2.subversion_of FROM acbversion acb1 INNER JOIN acbversion acb2 ON acb2.subversion_of=acb1.subversion_of where acb1.id="+acb.getId();
                            ResultSet rs_acb = statement.executeQuery(acbsql);          
                            rs_acb.last();
                            versionname = (float) 0.1 + rs_acb.getFloat("acb_versionname");
                            subversion_of = String.valueOf(rs_acb.getInt("subversion_of"));
                        }
                        else{
                            String subsql = "SELECT id, acb_versionname FROM acbversion where subversion_of="+acb.getId()+" ORDER BY acb_versionname DESC LIMIT 1";
                            ResultSet rs_sub = statement.executeQuery(subsql);          
                            rs_sub.last();  
                            System.out.println("subversion_rows"+rs_sub.getRow());
                            if(rs_sub.getRow()==0){
                                String acbsql = "SELECT id, acb_versionname FROM acbversion where id="+acb.getId();
                                ResultSet rs_acb = statement.executeQuery(acbsql);          
                                rs_acb.last();  
                                versionname = (float) 0.1 + rs_acb.getFloat("acb_versionname");
                            }
                            else
                                versionname = (float) 0.1 + rs_sub.getFloat("acb_versionname");
                            subversion_of = String.valueOf(acb.getId());
                        }                 
                    }
                    else
                        versionname = (float) 1.0 + acbversionname;
                }           
                preparedStatement = connection.prepareStatement("INSERT INTO acbversion (acb_versionname,status,created_date,created_or_updated_by,flag,subversion_of)" +
                        "VALUES (?, ?, ?, ?, ?,?)",preparedStatement.RETURN_GENERATED_KEYS);
    //            preparedStatement.setString(1, v.getVersionname());
                preparedStatement.setDouble(1, versionname);
                preparedStatement.setBoolean(2, acb.getStatus());
                preparedStatement.setString(3, acb.getCreated_date());
                preparedStatement.setInt(4, acb.getCreated_or_updated_by());
                preparedStatement.setBoolean(5, acb.getFlag());
                preparedStatement.setString(6, subversion_of);
                preparedStatement.executeUpdate();


                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next())
                {
                    int last_inserted_id = rs.getInt(1);
                    return new Object[]{last_inserted_id, versionname};
                }
            }
            else{    
                String versionName = "SELECT acb_versionname FROM acbversion WHERE id ="+acb.getId();
                ResultSet resultSet = statement.executeQuery(versionName);
                resultSet.last();
                if (resultSet.getRow() != 0) {
                    versionname = (float) resultSet.getFloat("acb_versionname");
                }
                System.out.println("object_value_in_update"+acb.getId()+acb.getStatus()+acb.getCreated_or_updated_by());
                String sql = "UPDATE acbversion SET " +
                    "status = ?, created_or_updated_by = ?, flag=?   WHERE id = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setBoolean(1, acb.getStatus());
                preparedStatement.setInt(2, acb.getCreated_or_updated_by());
                preparedStatement.setBoolean(3, acb.getFlag());
                preparedStatement.setInt(4, acb.getId());
                preparedStatement.executeUpdate();                
                return new Object[]{acb.getId(), versionname};
            }                
        } catch (Exception e) {
            System.out.println("acb version error message"+e.getMessage()); 
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
    public static int insertACBSignal(ACBInput_and_Ouput_Signal as) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int resultSet_count = 0;
        int last_inserted_id = 0;
        try {
            connection = ConnectionConfiguration.getConnection();            
            Statement statement = connection.createStatement();
//            System.out.println("globaldatastore"+StringUtils.join(GlobalDataStore.globalData, ','));
            if(as.getSignal_type().equals("input")){
                String ips_sql = "select * from acb_inputsignal as ips where"
                     + " ips.input_signal_id="+as.getSignal_id()
                     + " AND ips.input_network_id="+as.getNetwork_id()
                     + " AND ips.network_type='"+as.getNetwork_type()+"'"
                     + " AND ips.pdbversion_group_id="+as.getPdbversion_group_id(); 
                System.out.println("ips_sql"+ips_sql);
                ResultSet resultSet = statement.executeQuery(ips_sql);
                while (resultSet.next()){ 
//                    GlobalDataStore.globalData.add(resultSet.getInt("id")); 
//                    ip_signals.add(resultSet.getInt(1));
                    last_inserted_id = resultSet.getInt(1);
                }
                resultSet.last(); 
                resultSet_count = resultSet.getRow();
                if(resultSet_count == 0)
                    preparedStatement = connection.prepareStatement("INSERT INTO acb_inputsignal (input_signal_id, input_network_id, network_type, pdbversion_group_id)" +
                         "VALUES (?, ?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
            }
            else{
                String ips_sql = "select * from acb_outputsignal as ips where "
                     + "ips.output_signal_id="+as.getSignal_id()
                     + " AND ips.output_network_id="+as.getNetwork_id()
                     + " AND ips.network_type='"+as.getNetwork_type()+"'"
                     + " AND ips.pdbversion_group_id="+as.getPdbversion_group_id(); 
                ResultSet resultSet = statement.executeQuery(ips_sql);
                while (resultSet.next()){ 
//                    GlobalDataStore.globalData.add(resultSet.getInt("id")); 
//                    op_signals.add(resultSet.getInt(1));
                    last_inserted_id = resultSet.getInt(1);
                }
                resultSet.last(); 
                resultSet_count = resultSet.getRow();
                if(resultSet_count == 0)
                     preparedStatement = connection.prepareStatement("INSERT INTO acb_outputsignal (output_signal_id, output_network_id, network_type, pdbversion_group_id)" +
                            "VALUES (?, ?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
            }
            if(resultSet_count == 0){
                preparedStatement.setInt(1, as.getSignal_id());
                preparedStatement.setInt(2, as.getNetwork_id());
                preparedStatement.setString(3, as.getNetwork_type());
                preparedStatement.setInt(4, as.getPdbversion_group_id()); 

                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next())
                {
//                    GlobalDataStore.globalData.add(rs.getInt(1));
//                    if(as.getSignal_type().equals("input"))
//                        ip_signals.add(rs.getInt(1));
//                    else
//                        op_signals.add(rs.getInt(1));
                    last_inserted_id = rs.getInt(1);
                }
            }
            return last_inserted_id;
        } catch (Exception e) {
            System.out.println("ACB signal error message"+e.getMessage()); 
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
//        return 0;
    }
    public static int insertACBVersionGroup(ACBVersionGroup ag) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int resultSet_count = 0;
        try {
            connection = ConnectionConfiguration.getConnection();  
            if(ag.getOperation_status().equals("update")){
                System.out.println("update_if");
                Statement statement = connection.createStatement();
                String sql = "select * from acbversion_group as ag where "
                        + "ag.acbversion_id="+ag.getACBversion_id()
                        + " AND ag.domain_and_features_mapping_id="+ag.getDomain_and_features_mapping_id();
                System.out.println("sql_query"+sql);
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) { 
                    System.out.println("while");
                    String update_sql = "UPDATE acbversion_group SET " +
                        "ivnversion_id = ?, pdbversion_id = ?, vehicleversion_id = ?, vehicle_id = ?, ecu_id = ?, inputsignal_group = ?,"
                            + "outputsignal_group = ? ,touchedstatus = ?  WHERE id = ?";
                    preparedStatement = connection.prepareStatement(update_sql);
                    preparedStatement.setInt(1, ag.getIVNversion_id()); 
                    preparedStatement.setInt(2, ag.getPDBversion_id()); 
                    preparedStatement.setInt(3, ag.getVehicleversion_id()); 
                    preparedStatement.setInt(4, ag.getVehicle_id());                      
                    preparedStatement.setInt(5, ag.getEcu_id()); 
                    preparedStatement.setString(6, ag.getInputsignal_group());
                    preparedStatement.setString(7, ag.getOutputsignal_group());
                    preparedStatement.setBoolean(8, ag.getTouchedstatus());
                    preparedStatement.setInt(9, resultSet.getInt("id"));             
                    preparedStatement.executeUpdate(); 
                    GlobalDataStore.globalData.add(resultSet.getInt("id"));               
                }                              
                resultSet.last(); 
                resultSet_count = resultSet.getRow();
                System.out.println("getrow_count"+resultSet.getRow()); 
            }
            if(resultSet_count == 0){
                System.out.println("object_value_in_insert"+ag.getACBversion_id()+ag.getIVNversion_id()+ag.getPDBversion_id()+ag.getVehicleversion_id()
                +ag.getVehicle_id()+ag.getEcu_id()+ag.getInputsignal_group()+ag.getOutputsignal_group()+ag.getTouchedstatus());
                preparedStatement = connection.prepareStatement("INSERT INTO acbversion_group (acbversion_id,ivnversion_id,pdbversion_id,vehicleversion_id,"
                        + "vehicle_id,domain_and_features_mapping_id,ecu_id,inputsignal_group,outputsignal_group,touchedstatus)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, ag.getACBversion_id());
                preparedStatement.setInt(2, ag.getIVNversion_id());
                preparedStatement.setInt(3, ag.getPDBversion_id());
                preparedStatement.setInt(4, ag.getVehicleversion_id());
                preparedStatement.setInt(5, ag.getVehicle_id());
                preparedStatement.setInt(6, ag.getDomain_and_features_mapping_id());
                preparedStatement.setInt(7, ag.getEcu_id());
                preparedStatement.setString(8, ag.getInputsignal_group());
                preparedStatement.setString(9, ag.getOutputsignal_group());
                preparedStatement.setBoolean(10, ag.getTouchedstatus());
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next())
                {
                    GlobalDataStore.globalData.add(rs.getInt(1));
                }
            }
            System.out.println("globalData"+GlobalDataStore.globalData);
            if(ag.getButton_type().equals("save")){
                return temp_status;
            }
            else if(ag.getButton_type().equals("submit")){
                return perm_status;
            }           
        } catch (Exception e) {
            System.out.println("acb version group error message"+e.getMessage()); 
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
    public static Map<String, Object> LoadACBPreviousVehicleversionData(ACBversion acbver) throws SQLException {
        System.out.println("LoadACBPreviousVehicleversionData");
        int pdbversion_id = 0;
        int ivnversion_id = 0;
        int vehicleversion_id = 0;
        int vehicle_id = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Map<String, Object> columns_res = new HashMap<String, Object>();
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();
    //        List<Map<String, Object>> result_row = new ArrayList<Map<String, Object>>();
            String acbversion_sql = "select CAST(acb.vehicleversion_id as CHAR(100)) as vehicleversion,CAST(acb.vehicle_id as CHAR(100)) as vehiclename,"
                    + "CAST(acb.pdbversion_id as CHAR(100)) as pdbversion,CAST(acb.ivnversion_id as CHAR(100)) as ivnversion "
                    + "from acbversion_group as acb where acb.acbversion_id="+acbver.getId()+" LIMIT 1"; 
            System.out.println(acbversion_sql);
            ResultSet rs_acb = statement.executeQuery(acbversion_sql);
            ResultSetMetaData metaData_acb = rs_acb.getMetaData();
            int colCount_acb = metaData_acb.getColumnCount();
            List<Map<String, Object>> row_acb = new ArrayList<Map<String, Object>>();
            while (rs_acb.next()) {
              Map<String, Object> columns_acb = new HashMap<String, Object>();
              for (int i = 1; i <= colCount_acb; i++) {
                columns_acb.put(metaData_acb.getColumnLabel(i), rs_acb.getObject(i));
              }
    //          columns1.put("network_type","can");
              pdbversion_id = rs_acb.getInt("pdbversion");
              System.out.println("pdbversion_id"+pdbversion_id);
              ivnversion_id = rs_acb.getInt("ivnversion");
              System.out.println("ivnversion_id"+ivnversion_id);
              vehicleversion_id = rs_acb.getInt("vehicleversion");
              System.out.println("vehicleversion_id"+vehicleversion_id);
              vehicle_id = rs_acb.getInt("vehiclename");
              System.out.println("vehicleversion_id"+vehicleversion_id);
              row_acb.add(columns_acb);
            }

            PDBversion pdbver = new PDBversion(pdbversion_id);
            Map<String, Object> pdb_map_result = LoadPDBDataForACBVersion(pdbver);
            System.out.println("pdb_map_result"+pdb_map_result);

            IVNversion ivnver = new IVNversion(ivnversion_id);
            Map<String, Object> ivn_map_result = LoadIVNDataForACBVersion(ivnver);
            System.out.println("ivn_map_result"+ivn_map_result);

            Vehicleversion vver = new Vehicleversion(vehicleversion_id);
            List<Map<String, Object>> vehmod_map_result = VehicleversionDB.LoadPreviousVehicleversionData(vver);

            Vehicle_and_Model_Mapping veh_mod_map = new Vehicle_and_Model_Mapping(vehicleversion_id,vehicle_id);
            Map<String, Object> pdb_ivn_result = LoadPDBandIVN_Version(veh_mod_map);

            String acbgroup_sql = "select CAST(acb.ecu_id as CHAR(100)) as ecu,acb.touchedstatus,ip.id,CAST(ip.pdbversion_group_id as CHAR(100)) as pdbgroupid,CAST(pdb.domain_and_features_mapping_id as CHAR(100)) as fid,ecu.ecu_name from acbversion_group as acb "
                    + "inner join acb_inputsignal as ip on FIND_IN_SET(ip.id,acb.inputsignal_group) > 0 INNER JOIN pdbversion_group as pdb ON pdb.id = ip.pdbversion_group_id \n" +
                    "inner join engine_control_unit as ecu ON ecu.id=acb.ecu_id where acb.acbversion_id="+acbver.getId(); 
            System.out.println(acbgroup_sql);
            ResultSet rs_acbgp = statement.executeQuery(acbgroup_sql);
            ResultSetMetaData metaData_acbgp = rs_acbgp.getMetaData();
            int colCount_acbgp = metaData_acbgp.getColumnCount();
            List<Map<String, Object>> row_acbgp = new ArrayList<Map<String, Object>>();
            while (rs_acbgp.next()) {
              Map<String, Object> columns_acbgp = new HashMap<String, Object>();
              for (int i = 1; i <= colCount_acbgp; i++) {
                columns_acbgp.put(metaData_acbgp.getColumnLabel(i), rs_acbgp.getObject(i));
              }
              row_acbgp.add(columns_acbgp);
            }

            String acbip_sql = "SELECT a.*,CAST(ip.input_signal_id as CHAR(100)) as input_signal_id,CAST(ip.pdbversion_group_id as CHAR(100)) as pdbversion_group_id,CAST(ip.input_network_id as CHAR(100)) as input_network_id,CAST(ip.network_type as CHAR(100)) as network_type,CAST(pdb.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,CAST(pdb.domain_and_features_mapping_id as CHAR(100)) as fid FROM acb_inputsignal AS ip "
                    + "INNER JOIN ( SELECT SUBSTRING_INDEX( SUBSTRING_INDEX( acb.inputsignal_group, ',', n.n ) , ',', -1 ) value,acb.ecu_id as ecu FROM acbversion_group as acb "
                    + "CROSS JOIN numbers n WHERE n.n <=1 + ( LENGTH( acb.inputsignal_group ) - LENGTH( REPLACE( acb.inputsignal_group, ',', ''))) AND acb.acbversion_id="+acbver.getId()+") AS a "
                    + "ON a.value = ip.id INNER JOIN pdbversion_group as pdb ON pdb.id = ip.pdbversion_group_id"; 
            System.out.println(acbip_sql);
            ResultSet rs_acbip = statement.executeQuery(acbip_sql);
            ResultSetMetaData metaData_acbip = rs_acbip.getMetaData();
            int colCount_acbip = metaData_acbip.getColumnCount();
            List<Map<String, Object>> row_acbip = new ArrayList<Map<String, Object>>();
            while (rs_acbip.next()) {
              Map<String, Object> columns_acbip = new HashMap<String, Object>();
              for (int i = 1; i <= colCount_acbip; i++) {
                columns_acbip.put(metaData_acbip.getColumnLabel(i), rs_acbip.getObject(i));
              }
              row_acbip.add(columns_acbip);
            }

            String acbop_sql = "SELECT a.*,CAST(op.output_signal_id as CHAR(100)) as output_signal_id,CAST(op.pdbversion_group_id as CHAR(100)) as pdbversion_group_id,CAST(op.output_network_id as CHAR(100)) as output_network_id,CAST(op.network_type as CHAR(100)) as network_type,pdb.vehicle_and_model_mapping_id as vmm_id,pdb.domain_and_features_mapping_id as fid FROM acb_outputsignal AS op "
                    + "INNER JOIN ( SELECT SUBSTRING_INDEX( SUBSTRING_INDEX( acb.outputsignal_group, ',', n.n ) , ',', -1 ) value,acb.ecu_id as ecu FROM acbversion_group as acb "
                    + "CROSS JOIN numbers n WHERE n.n <=1 + ( LENGTH( acb.outputsignal_group ) - LENGTH( REPLACE( acb.outputsignal_group, ',', ''))) AND acb.acbversion_id="+acbver.getId()+") AS a "
                    + "ON a.value = op.id INNER JOIN pdbversion_group as pdb ON pdb.id = op.pdbversion_group_id"; 
            System.out.println(acbop_sql);
            ResultSet rs_acbop = statement.executeQuery(acbop_sql);
            ResultSetMetaData metaData_acbop = rs_acbop.getMetaData();
            int colCount_acbop = metaData_acbop.getColumnCount();
            List<Map<String, Object>> row_acbop = new ArrayList<Map<String, Object>>();
            while (rs_acbop.next()) {
              Map<String, Object> columns_acbop = new HashMap<String, Object>();
              for (int i = 1; i <= colCount_acbop; i++) {
                columns_acbop.put(metaData_acbop.getColumnLabel(i), rs_acbop.getObject(i));
              }
              row_acbop.add(columns_acbop);
            }
            
            String acb_status_sql = "select a.status from acbversion a where a.id="+acbver.getId();
            ResultSet resultSet_st = statement.executeQuery(acb_status_sql);
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
            
            String acb_sub_sql = "select * from acbversion a where a.subversion_of="+acbver.getId();
            ResultSet resultSet_sub = statement.executeQuery(acb_sub_sql);
            ResultSetMetaData metaData_sub = resultSet_sub.getMetaData();
            int colCount_sub = metaData_sub.getColumnCount();
            List<Map<String, Object>> row_sub = new ArrayList<Map<String, Object>>();
            while (resultSet_sub.next()) {
              Map<String, Object> columns_sub = new HashMap<String, Object>();
              for (int i = 1; i <= colCount_sub; i++) {
                columns_sub.put(metaData_sub.getColumnLabel(i), resultSet_sub.getObject(i));
              }
              row_sub.add(columns_sub);
            }

            columns_res.put("acbversion",row_acb);
            columns_res.put("pdb_map_result",pdb_map_result);
            columns_res.put("ivn_map_result",ivn_map_result);
            columns_res.put("vehmod_map_result",vehmod_map_result);
            columns_res.put("pdb_ivn_result",pdb_ivn_result);
            columns_res.put("acbgroup",row_acbgp);
            columns_res.put("acb_inputsignal",row_acbip);
            columns_res.put("acb_outputsignal",row_acbop);
            columns_res.put("acbversion_status",row_st);
            columns_res.put("acbsubversion",row_sub);
        
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
    public static List<Map<String, Object>> GetACBVersion_Listing() throws SQLException {
        System.out.println("GetACBVersion_Listing");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
            String sql = "SELECT ag.id,CAST(acb.acb_versionname as CHAR(100)) as acb_versionname,CAST(pdb.pdb_versionname as CHAR(100)) as pdb_versionname,"
                    + "CAST(ivn.ivn_versionname as CHAR(100)) as ivn_versionname," 
                    + "CAST(ivn.ivn_versionname as CHAR(100)) as ivn_versionname," 
                    + "GROUP_CONCAT(CONCAT(f.feature_name,CONCAT(\" (\",domain_name,\")\"))) as touched_features,"
                    + "acb.status as status,acb.flag"
                    + " FROM acbversion_group as ag INNER JOIN domain_and_features_mapping as dfm ON dfm.id=ag.domain_and_features_mapping_id "
                    + "INNER JOIN domain as d ON d.id=dfm.domain_id INNER JOIN features as f ON f.id=dfm.feature_id "
                    + "INNER JOIN acbversion as acb ON acb.id=ag.acbversion_id "
                    + "INNER JOIN pdbversion as pdb ON pdb.id=ag.pdbversion_id "
                    + "INNER JOIN ivnversion as ivn ON ivn.id=ag.pdbversion_id "
                    + "group by ag.acbversion_id order by ag.acbversion_id desc";
            System.out.println("acbsql"+sql);
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
    public static void deleteACBVersion_Group(int acbversion_id, String action_type) throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        System.out.println("deleteACBVersion_Group"+GlobalDataStore.globalData);
        System.out.println("action_type"+action_type);
        if(action_type.equals("update") && GlobalDataStore.globalData.size() !=0){
            connection = ConnectionConfiguration.getConnection();
            preparedStatement = connection.prepareStatement("delete from acbversion_group where acbversion_id="+acbversion_id+" AND id NOT IN ("+StringUtils.join(GlobalDataStore.globalData, ',')+")");
            preparedStatement.executeUpdate();
        }
        GlobalDataStore.globalData.clear();
    }
}

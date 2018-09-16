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
        connection = ConnectionConfiguration.getConnection();
        //Check whether model name already exists in db or not
        Statement statement = connection.createStatement();
        String sql;
        if(filter.equals("active"))
            sql = "select a.id,a.acb_versionname,a.status from acbversion a where a.flag=1 and a.status=1";
        else
            sql = "select a.id,a.acb_versionname,a.status from acbversion a";
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
    
    public static Map<String, Object> LoadPDBDataForACBVersion(PDBversion pdbver) throws SQLException {
        System.out.println("LoadPDBPreviousVehicleversionData");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
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
        
        Map<String, Object> columns3 = new HashMap<String, Object>();
        columns3.put("vehicledetail_list",row);
        columns3.put("featuredetail_list",row1);
        columns3.put("pdbversion_status",row2);
        System.out.println("columns"+columns3);
        return columns3;
    }
    public static Map<String, Object> LoadIVNDataForACBVersion(IVNversion ivnver) throws SQLException {
        System.out.println("LoadIVNPreviousVehicleversionData");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        connection = ConnectionConfiguration.getConnection();
        Statement statement = connection.createStatement();
        List<Map<String, Object>> result_row = new ArrayList<Map<String, Object>>();
//        String canmodel_sql = "SELECT CAST(cn.network_can_id as CHAR(100)) as network_id,\n" +
//            "CAST(cn.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n" +
//            "cn.available_status as status \n" +
//            "FROM ivn_canmodels AS cn \n" +
//            "where cn.ivnversion_id="+ivnver.getId();      
        String canmodel_sql = "SELECT CAST(cn.network_can_id as CHAR(100)) as id,\n" +
            "c.can_network_name as listitem,\n" +
            "CAST(cn.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n" +
            "cn.available_status as status \n" +
            "FROM ivn_canmodels AS cn INNER JOIN network_can as c ON c.id=cn.network_can_id \n" +
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
            "l.lin_network_name as listitem,\n" +
            "CAST(ln.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n" +
            "ln.available_status as status \n" +
            "FROM ivn_linmodels AS ln INNER JOIN network_lin as l ON l.id=ln.network_lin_id \n" +
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
            "h.hardware_network_name as listitem,\n" +
            "CAST(hw.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n" +
            "hw.available_status as status \n" +
            "FROM ivn_hardwaremodels AS hw INNER JOIN network_hardware as h ON h.id=hw.network_hardware_id \n" +
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
        
        Map<String, Object> columns_res = new HashMap<String, Object>();       
        columns_res.put("vehicledetail_list",row);      
        columns_res.put("can",row1);
        columns_res.put("lin",row2);
        columns_res.put("hardware",row3);
//        columns_res.put("ivnversion_status",row5);
        columns_res.put("signal",row_sig);
        columns_res.put("ecu",row_ecu);
//        System.out.println("columns"+columns_res);
        return columns_res;
    }
    
    public static Map<String, Object> LoadPDBandIVN_Version(Vehicle_and_Model_Mapping vmm) throws SQLException {
        System.out.println("LoadACBVersion");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
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
        
        Map<String, Object> columns = new HashMap<String, Object>();
        columns.put("pdbversion_list",pdb_row);
        columns.put("ivnversion_list",ivn_row);
        System.out.println("columns"+columns);
        return columns;
    }
    public static List<Map<String, Object>> LoadACBPreviousVehicleversionStatus(ACBversion acb) throws SQLException {
        System.out.println("LoadACBPreviousVehicleversionStatus");
//        String status = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        connection = ConnectionConfiguration.getConnection();
        //Check whether model name already exists in db or not
        Statement statement = connection.createStatement();
//        String sql = "select v.id,v.versionname,v.status from vehicleversion v where v.status=1";
        String sql = "select acb.status,acb.flag from acbversion acb where acb.id="+acb.getId();
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
    public static int insertACBVersion(ACBversion acb) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        float versionname;
        try {
            connection = ConnectionConfiguration.getConnection();
            
            Statement statement = connection.createStatement();
            if(acb.getOperation_status().equals("create")){
                String sql = "SELECT id, acb_versionname FROM acbversion ORDER BY acb_versionname DESC LIMIT 1";
                ResultSet resultSet = statement.executeQuery(sql);          
                resultSet.last();    
                if(resultSet.getRow()==0){
                    versionname = (float) 1.0;
                }
                else{
                    versionname = (float) 1.0 + resultSet.getFloat("acb_versionname");
                }           
                preparedStatement = connection.prepareStatement("INSERT INTO acbversion (acb_versionname,status,created_date,created_or_updated_by,flag)" +
                        "VALUES (?, ?, ?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
    //            preparedStatement.setString(1, v.getVersionname());
                preparedStatement.setDouble(1, versionname);
                preparedStatement.setBoolean(2, acb.getStatus());
                preparedStatement.setString(3, acb.getCreated_date());
                preparedStatement.setInt(4, acb.getCreated_or_updated_by());
                preparedStatement.setBoolean(5, acb.getFlag());
                preparedStatement.executeUpdate();


                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next())
                {
                    int last_inserted_id = rs.getInt(1);
                    return last_inserted_id;
                }
            }
            else{       
                System.out.println("object_value_in_update"+acb.getId()+acb.getStatus()+acb.getCreated_or_updated_by());
                String sql = "UPDATE acbversion SET " +
                    "status = ?, created_or_updated_by = ?, flag=?   WHERE id = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setBoolean(1, acb.getStatus());
                preparedStatement.setInt(2, acb.getCreated_or_updated_by());
                preparedStatement.setBoolean(3, acb.getFlag());
                preparedStatement.setInt(4, acb.getId());
                preparedStatement.executeUpdate();                
                return acb.getId();
            }                
        } catch (Exception e) {
            System.out.println("acb version error message"+e.getMessage()); 
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
        float versionname;
        try {
            connection = ConnectionConfiguration.getConnection();            
            Statement statement = connection.createStatement();
            if(ag.getOperation_status().equals("create")){       
                System.out.println("object_value_in_insert"+ag.getACBversion_id()+ag.getIVNversion_id()+ag.getPDBversion_id()+ag.getVehicleversion_id()
                +ag.getVehicle_id()+ag.getEcu_id()+ag.getInputsignal_group()+ag.getOutputsignal_group()+ag.getTouchedstatus());
                preparedStatement = connection.prepareStatement("INSERT INTO acbversion_group (acbversion_id,ivnversion_id,pdbversion_id,vehicleversion_id,"
                        + "vehicle_id,ecu_id,inputsignal_group,outputsignal_group,touchedstatus)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, ag.getACBversion_id());
                preparedStatement.setInt(2, ag.getIVNversion_id());
                preparedStatement.setInt(3, ag.getPDBversion_id());
                preparedStatement.setInt(4, ag.getVehicleversion_id());
                preparedStatement.setInt(5, ag.getVehicle_id());
                preparedStatement.setInt(6, ag.getEcu_id());
                preparedStatement.setString(7, ag.getInputsignal_group());
                preparedStatement.setString(8, ag.getOutputsignal_group());
                preparedStatement.setBoolean(9, ag.getTouchedstatus());
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next())
                {
                    int last_inserted_id = rs.getInt(1);
                    return last_inserted_id;
                }
            }
            else{     
//                System.out.println("object_value_in_update"+ig.getCanmodel_group()+ig.getLinmodel_group()+ig.getHardwaremodel_group());
//                String sql = "UPDATE ivnversion_group SET " +
//                    "canmodel_group = ?, linmodel_group = ?, hardwaremodel_group = ?, signal_group = ?, ecu_group =? "
//                        + "WHERE ivnversion_id = ?"; 
//                preparedStatement = connection.prepareStatement(sql);
//                preparedStatement.setString(1, ig.getCanmodel_group());
//                preparedStatement.setString(2, ig.getLinmodel_group());
//                preparedStatement.setString(3, ig.getHardwaremodel_group());
//                preparedStatement.setString(4, ig.getSignal_group());
//                preparedStatement.setString(5, ig.getEcu_group());
//                preparedStatement.setInt(6, ig.getIVNversion_id());
//                preparedStatement.executeUpdate();                
//                System.out.println("button_type"+ig.getButton_type());
////                return ig.getId();
//                if(ig.getButton_type().equals("save")){
//                    return temp_status;
//                }
//                else if(ig.getButton_type().equals("submit")){
//                    return perm_status;
//                }
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
        
//        String canmodel_sql = "SELECT CAST(cn.network_can_id as CHAR(100)) as network_id,\n" +
//            "CAST(cn.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n" +
//            "cn.available_status as status \n" +
//            "FROM ivn_canmodels AS cn \n" +
//            "where cn.ivnversion_id="+ivnver.getId();      
//        System.out.println(canmodel_sql);
//        ResultSet resultSet1 = statement.executeQuery(canmodel_sql);
//        ResultSetMetaData metaData1 = resultSet1.getMetaData();
//        int colCount1 = metaData1.getColumnCount();
//        List<Map<String, Object>> row1 = new ArrayList<Map<String, Object>>();
//        while (resultSet1.next()) {
//          Map<String, Object> columns1 = new HashMap<String, Object>();
//          for (int i = 1; i <= colCount1; i++) {
//            columns1.put(metaData1.getColumnLabel(i), resultSet1.getObject(i));
//          }
//          columns1.put("network_type","can");
//          row1.add(columns1);
//        }
//        
//        String linmodel_sql = "SELECT CAST(ln.network_lin_id as CHAR(100)) as network_id,\n" +
//            "CAST(ln.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n" +
//            "ln.available_status as status \n" +
//            "FROM ivn_linmodels AS ln \n" +
//            "where ln.ivnversion_id="+ivnver.getId();       
//        System.out.println(linmodel_sql);
//        ResultSet resultSet2 = statement.executeQuery(linmodel_sql);
//        ResultSetMetaData metaData2 = resultSet2.getMetaData();
//        int colCount2 = metaData2.getColumnCount();
//        List<Map<String, Object>> row2 = new ArrayList<Map<String, Object>>();
//        while (resultSet2.next()) {
//          Map<String, Object> columns2 = new HashMap<String, Object>();
//          for (int i = 1; i <= colCount2; i++) {
//            columns2.put(metaData2.getColumnLabel(i), resultSet2.getObject(i));
//          }
//          columns2.put("network_type","lin");
//          row2.add(columns2);
//        }
//        
//        String hwmodel_sql = "SELECT CAST(hw.network_hardware_id as CHAR(100)) as network_id,\n" +
//            "CAST(hw.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n" +
//            "hw.available_status as status \n" +
//            "FROM ivn_hardwaremodels AS hw \n" +
//            "where hw.ivnversion_id="+ivnver.getId();       
//        System.out.println(hwmodel_sql);
//        ResultSet resultSet3 = statement.executeQuery(hwmodel_sql);
//        ResultSetMetaData metaData3 = resultSet3.getMetaData();
//        int colCount3 = metaData3.getColumnCount();
//        List<Map<String, Object>> row3 = new ArrayList<Map<String, Object>>();
//        while (resultSet3.next()) {
//          Map<String, Object> columns3 = new HashMap<String, Object>();
//          for (int i = 1; i <= colCount3; i++) {
//            columns3.put(metaData3.getColumnLabel(i), resultSet3.getObject(i));
//          }
//          columns3.put("network_type","hardware");
//          row3.add(columns3);
//        }
//        
//        Map<String, Object> columns_res = new HashMap<String, Object>();
//        String ivngroup_sql = "SELECT signal_group, ecu_group " +
//            "FROM ivnversion_group AS ig \n" +
//            "where ig.ivnversion_id="+ivnver.getId();       
//        System.out.println(ivngroup_sql);
//        ResultSet resultSet4 = statement.executeQuery(ivngroup_sql);
//        ResultSetMetaData metaData4 = resultSet4.getMetaData();
//        int colCount4 = metaData4.getColumnCount();
//        while (resultSet4.next()) { 
////          String strarray[] =resultSet4.getString("signal_group").split(",") ;
////          String strarray1[] =resultSet4.getString("ecu_group").split(",") ;
////          columns_res.put("signal",strarray);
////          columns_res.put("ecu",strarray1);
////            columns_res.put("signal","["+resultSet4.getString("signal_group")+"]");
////            columns_res.put("ecu","["+resultSet4.getString("ecu_group")+"]");
//
//            columns_res.put("signal",resultSet4.getString("signal_group").split(","));
//            columns_res.put("ecu",resultSet4.getString("ecu_group").split(","));
//        }
//        
////        String ivnsignalgroup_sql = "select s.id as sid,s.signal_name as listitem,s.signal_description as description from ivnversion_group as ig inner join signals as s "
////                + "on FIND_IN_SET(s.id,ig.signal_group) > 0 where ig.ivnversion_id="+ivnver.getId();       
////        System.out.println(ivnsignalgroup_sql);
////        ResultSet resultSet5 = statement.executeQuery(ivnsignalgroup_sql);
////        ResultSetMetaData metaData5 = resultSet5.getMetaData();
////        int colCount5 = metaData5.getColumnCount();
////        List<Map<String, Object>> row5 = new ArrayList<Map<String, Object>>();
////        while (resultSet5.next()) {
////          Map<String, Object> columns5 = new HashMap<String, Object>();
////          for (int i = 1; i <= colCount5; i++) {
////            columns5.put(metaData5.getColumnLabel(i), resultSet5.getObject(i));
////          }
////          columns5.put("network_type","signal");
////          row5.add(columns5);
////        }
//        
////        String ivnecugroup_sql = "select e.id as eid,e.ecu_name as listitem,e.ecu_description as description from ivnversion_group as ig inner join engine_control_unit as e "
////                + "on FIND_IN_SET(e.id,ig.ecu_group) > 0 where ig.ivnversion_id="+ivnver.getId();       
////        System.out.println(ivnecugroup_sql);
////        ResultSet resultSet6 = statement.executeQuery(ivnecugroup_sql);
////        ResultSetMetaData metaData6 = resultSet6.getMetaData();
////        int colCount6 = metaData6.getColumnCount();
////        List<Map<String, Object>> row6 = new ArrayList<Map<String, Object>>();
////        while (resultSet6.next()) {
////          Map<String, Object> columns6 = new HashMap<String, Object>();
////          for (int i = 1; i <= colCount6; i++) {
////            columns6.put(metaData6.getColumnLabel(i), resultSet6.getObject(i));
////          }
////          columns6.put("network_type","ecu");
////          row6.add(columns6);
////        }
//        
////        String vehciledetail_sql = "SELECT \n" +
////            "vv.id as vehver_id,\n" +
////            "v.id as vehicle_id,\n" +
////            "vm.modelname as modelname,\n" +
////            "CAST(vmm.id as CHAR(100)) as vmm_id \n" +
////            "FROM ivn_canmodels AS cn \n" +
////            "INNER JOIN vehicle_and_model_mapping AS vmm ON vmm.id = cn.vehicle_and_model_mapping_id \n" +
////            "INNER JOIN vehicleversion as vv on vv.id=vmm.vehicleversion_id \n" +
////            "INNER JOIN vehicle as v on v.id=vmm.vehicle_id \n" +
////            "INNER JOIN vehiclemodel as vm on vm.id=vmm.model_id\n" +
////            "where cn.ivnversion_id="+ivnver.getId()+" group by modelname,vehicle_and_model_mapping_id";
//
////        String vehciledetail_sql = "SELECT \n" +
////            "vv.id as vehver_id,\n" +
////            "v.id as vehicle_id,\n" +
////            "vm.modelname as modelname,\n" +
////            "CAST(vmm.id as CHAR(100)) as vmm_id \n" +
////            "FROM ivn_canmodels AS cn \n" +
////            "INNER JOIN vehicle_and_model_mapping AS vmm ON vmm.id = cn.vehicle_and_model_mapping_id \n" +
////            "INNER JOIN vehicleversion as vv on vv.id=vmm.vehicleversion_id \n" +
////            "INNER JOIN vehicle as v on v.id=vmm.vehicle_id \n" +
////            "INNER JOIN vehiclemodel as vm on vm.id=vmm.model_id\n" +
////            "where cn.ivnversion_id="+ivnver.getId()+" group by modelname,vehicle_and_model_mapping_id";
//
//        String v_sql = "SELECT \n" +
//            "vmm.vehicleversion_id,vmm.vehicle_id \n" +
//            "FROM ivn_canmodels AS cn \n" +
//            "INNER JOIN vehicle_and_model_mapping AS vmm ON vmm.id = cn.vehicle_and_model_mapping_id \n" +
//            "where cn.ivnversion_id="+ivnver.getId()+" limit 1";
//        
//        System.out.println("vehciledetail_sql"+v_sql);
//        ResultSet vrs = statement.executeQuery(v_sql);
//        String vehciledetail_sql = null;
//        if(vrs.next()){
//            vehciledetail_sql = "SELECT \n" +
//            "vv.id as vehver_id,\n" +
//            "v.id as vehicle_id,\n" +
//            "vm.modelname as modelname,\n" +
//            "CAST(vmm.id as CHAR(100)) as vmm_id \n" +
//            "from vehicle_and_model_mapping as vmm \n" +
//            "INNER JOIN vehicleversion as vv on vv.id=vmm.vehicleversion_id \n" +
//            "INNER JOIN vehicle as v on v.id=vmm.vehicle_id \n" +
//            "INNER JOIN vehiclemodel as vm on vm.id=vmm.model_id\n" +
//            "where vmm.vehicleversion_id="+vrs.getInt("vehicleversion_id")+" AND vmm.vehicle_id="+vrs.getInt("vehicle_id");
//        }
//        ResultSet resultSet = statement.executeQuery(vehciledetail_sql);
//        System.out.println("vehciledetail_sql1"+vehciledetail_sql);
//        ResultSetMetaData metaData = resultSet.getMetaData();
//        int colCount = metaData.getColumnCount();
//        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
//        while (resultSet.next()) {
//          Map<String, Object> columns = new HashMap<String, Object>();
//          for (int i = 1; i <= colCount; i++) {
//            columns.put(metaData.getColumnLabel(i), resultSet.getObject(i));
//          }
//          row.add(columns);
//        }
//        
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
//        
//        columns_res.put("vehicledetail_list",row);      
//        columns_res.put("can",row1);
//        columns_res.put("lin",row2);
//        columns_res.put("hardware",row3);
//        columns_res.put("ivnversion_status",row5);
        
        Map<String, Object> columns_res = new HashMap<String, Object>();
        columns_res.put("acbversion",row_acb);
        columns_res.put("pdb_map_result",pdb_map_result);
        columns_res.put("ivn_map_result",ivn_map_result);
        columns_res.put("vehmod_map_result",vehmod_map_result);
        columns_res.put("pdb_ivn_result",pdb_ivn_result);
        
        
        return columns_res;
    }
}

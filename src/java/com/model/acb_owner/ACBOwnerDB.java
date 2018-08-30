/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.acb_owner;

import com.db_connection.ConnectionConfiguration;
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

/**
 *
 * @author ets-2
 */
public class ACBOwnerDB {
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
        
        String featuredetail_sql = "SELECT GROUP_CONCAT(DISTINCT(CAST(pg.vehicle_and_model_mapping_id as CHAR(100)))) as vmm_id,\n" +
            "CAST(pg.domain_and_features_mapping_id as CHAR(100)) as fid,\n" +
            "GROUP_CONCAT(DISTINCT(pg.available_status)) as status,\n" +
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
}

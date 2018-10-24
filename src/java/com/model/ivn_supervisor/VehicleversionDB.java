package com.model.ivn_supervisor;

import com.db_connection.ConnectionConfiguration;
import static com.model.acb_owner.ACBOwnerDB.LoadIVNDataForACBVersion;
import static com.model.acb_owner.ACBOwnerDB.LoadPDBDataForACBVersion;
import static com.model.acb_owner.ACBOwnerDB.LoadPDBandIVN_Version;
import com.model.acb_owner.ACBversion;
import com.model.common.GlobalDataStore;
import com.model.ivn_engineer.IVNversion;
import static com.model.pdb_owner.PDBVersionDB.perm_status;
import static com.model.pdb_owner.PDBVersionDB.temp_status;
import com.model.pdb_owner.PDBVersionGroup;
import com.model.pdb_owner.PDBversion;
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

    /*public static int insertVehicleVersion(Vehicleversion v) {
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
    }*/
    public static Object[] insertVehicleVersion(Vehicleversion v) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        float versionname = 0.0f;
        try {
            connection = ConnectionConfiguration.getConnection();

            Statement statement = connection.createStatement();
            if (v.getOperation_status().equals("create")) {
                String sql = "SELECT id, versionname FROM vehicleversion ORDER BY versionname DESC LIMIT 1";
                ResultSet resultSet = statement.executeQuery(sql);
                resultSet.last();
                if (resultSet.getRow() == 0) {
                    versionname = (float) 1.0;
                } else {
                    versionname = (float) 1.0 + resultSet.getFloat("versionname");
                }
                preparedStatement = connection.prepareStatement("INSERT INTO vehicleversion (versionname,status,created_date,created_or_updated_by,flag)"
                        + "VALUES (?, ?, ?, ?, ?)", preparedStatement.RETURN_GENERATED_KEYS);
                //            preparedStatement.setString(1, v.getVersionname());
                preparedStatement.setDouble(1, versionname);
                preparedStatement.setBoolean(2, v.getStatus());
                preparedStatement.setString(3, v.getCreated_date());
                preparedStatement.setInt(4, v.getCreated_or_updated_by());
                preparedStatement.setBoolean(5, v.getFlag());
                preparedStatement.executeUpdate();

                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    int last_inserted_id = rs.getInt(1);
                    return new Object[]{last_inserted_id, versionname};
                }
            } else {
                String versionName = "SELECT versionname FROM vehicleversion WHERE id ="+v.getId();
                ResultSet resultSet = statement.executeQuery(versionName);
                resultSet.last();
                if (resultSet.getRow() != 0) {
                    versionname = (float) resultSet.getFloat("versionname");
                }
                System.out.println("object_value_in_update" + v.getId() + v.getStatus() + v.getCreated_or_updated_by() + v.getFlag());
                String sql = "UPDATE vehicleversion SET "
                        + "status = ?, created_or_updated_by = ?, flag=?  WHERE id = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setBoolean(1, v.getStatus());
                preparedStatement.setInt(2, v.getCreated_or_updated_by());
                preparedStatement.setBoolean(3, v.getFlag());
                preparedStatement.setInt(4, v.getId());
                preparedStatement.executeUpdate();
                return new Object[]{v.getId(), versionname};
            }
        } catch (Exception e) {
            System.out.println("vehicle version error message" + e.getMessage());
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

    public static int insertVehicle(Vehicle v) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether vehicle name already exists in db or not
            Statement statement = connection.createStatement();
            String sql = "SELECT id FROM vehicle WHERE vehiclename ='" + v.getVehiclename().trim() + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.last();
//            System.out.println("vehicle_row_count"+resultSet.getRow());
//            System.out.println(statement);
            if (resultSet.getRow() > 0) {
//                System.out.println("if");
                int last_inserted_id = resultSet.getInt(1);
                return last_inserted_id;
            } else {
//                System.out.println("else");
                preparedStatement = connection.prepareStatement("INSERT INTO vehicle (vehiclename,created_date,created_or_updated_by)"
                        + "VALUES (?, ?, ?)", preparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, v.getVehiclename());
                preparedStatement.setString(2, v.getCreated_date());
                preparedStatement.setInt(3, v.getCreated_or_updated_by());
                preparedStatement.executeUpdate();

                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    int last_inserted_id = rs.getInt(1);
                    return last_inserted_id;
                }
            }
        } catch (Exception e) {
            System.out.println("vehicle version error message" + e.getMessage());
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
            if (resultSet.getRow() > 0) {
                System.out.println("if");
                int last_inserted_id = resultSet.getInt(1);
                return last_inserted_id;
            } else {
                System.out.println("else");
                preparedStatement = connection.prepareStatement("INSERT INTO vehiclemodel (modelname,created_date,created_or_updated_by)"
                        + "VALUES (?, ?, ?)", preparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, v.getModelname());
                preparedStatement.setString(2, v.getCreated_date());
                preparedStatement.setInt(3, v.getCreated_or_updated_by());
                preparedStatement.executeUpdate();

                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    int last_inserted_id = rs.getInt(1);
                    return last_inserted_id;
                }
            }
        } catch (Exception e) {
            System.out.println("vehicle version error message" + e.getMessage());
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
            if (v.getOperation_status().equals("update")) {
                System.out.println("update_if");
                Statement statement = connection.createStatement();
                String sql = "select vmm.id from vehicle_and_model_mapping as vmm where "
                        + "vmm.vehicleversion_id=" + v.getVehicleversion_id()
                        + " AND vmm.vehicle_id=" + v.getVehicle_id() + " AND vmm.model_id=" + v.getModel_id();
                System.out.println("sql_query" + sql);
                ResultSet resultSet = statement.executeQuery(sql);
                if (resultSet.next()) {
                    System.out.println("resultset next available");
                    GlobalDataStore.globalData.add(resultSet.getInt("id"));
//                    while (resultSet.next()) {                       
//                          System.out.println("while");
//                          GlobalDataStore.globalData.add(resultSet.getInt("id"));
//                    }
                }
                resultSet.last();
                resultSet_count = resultSet.getRow();
                System.out.println("getrow_count" + resultSet.getRow());

            }
            if (resultSet_count == 0) {
                preparedStatement = connection.prepareStatement("INSERT INTO vehicle_and_model_mapping (vehicleversion_id, vehicle_id, model_id)"
                        + "VALUES (?, ?, ?)", preparedStatement.RETURN_GENERATED_KEYS);
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
                if (rs.next()) {
                    GlobalDataStore.globalData.add(rs.getInt(1));
                }
            }
            System.out.println("globalData" + GlobalDataStore.globalData);
            if (v.getButton_type().equals("save")) {
                return temp_status;
            } else if (v.getButton_type().equals("submit")) {
                return perm_status;
            }
//            ResultSet rs = preparedStatement.getGeneratedKeys();
//            if(rs.next())
//            {
//                int last_inserted_id = rs.getInt(1);
//                return last_inserted_id;
//            }
        } catch (Exception e) {
            System.out.println("vehicle version error message" + e.getMessage());
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
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            //        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
            
            String sql = "SELECT vv.id as id, CAST(versionname as CHAR(100)) as versionname, GROUP_CONCAT( DISTINCT (v.vehiclename) ) AS vehiclename, "
                    + " GROUP_CONCAT( DISTINCT (vm.modelname) ) AS modelname, vv.status, vv.flag FROM vehicle_and_model_mapping AS vmm "
                    + " INNER JOIN vehicle AS v ON v.id = vmm.vehicle_id INNER JOIN vehicleversion AS vv ON"
                    + " vv.id = vmm.vehicleversion_id INNER JOIN vehiclemodel AS vm ON vm.id = vmm.model_id "
                    + " GROUP BY vmm.vehicleversion_id, vmm.vehicle_id ORDER BY vv.id DESC";
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
            System.out.println("acb version error message" + e.getMessage());
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
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
            String sql = "select v.vehiclename,v.status,group_concat(DISTINCT(vv.versionname)) as versionname from vehicle as v INNER JOIN "
                    + "vehicle_and_model_mapping as vmm ON vmm.vehicle_id=v.id INNER JOIN vehicleversion as vv ON "
                    + "vv.id=vmm.vehicleversion_id group by v.vehiclename order by v.id desc";
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
            System.out.println("acb version error message" + e.getMessage());
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

    public static List<Map<String, Object>> GetVehicleModel_Listing(VehicleModel veh) throws SQLException {
        System.out.println("GetVehicleModel_Listing");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
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
            while (resultSet.next()) {
                Map<String, Object> columns = new HashMap<String, Object>();
                for (int i = 1; i <= colCount; i++) {
                    columns.put(metaData.getColumnLabel(i), resultSet.getObject(i));
                }
                row.add(columns);
            }
        } catch (Exception e) {
            System.out.println("acb version error message" + e.getMessage());
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

    public static List<Map<String, Object>> LoadVehicleVersion(String filter) throws SQLException {
        System.out.println("LoadVehicleVersion");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
            String sql;
            //        String sql = "select v.id,v.versionname,v.status from vehicleversion v where v.status=1";
            if (filter.equals("active")) {
                sql = "select v.id,v.versionname,v.status from vehicleversion v where v.flag=1 and v.status=1";
            } else {
                sql = "select v.id,v.versionname,v.status from vehicleversion v";
            }
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
            System.out.println("row_data" + row);
        } catch (Exception e) {
            System.out.println("acb version error message" + e.getMessage());
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

    public static List<Map<String, Object>> LoadPreviousVehicleversionData(Vehicleversion vver) throws SQLException {
        System.out.println("LoadPreviousVehicleversionData");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            //        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
            String sql = "SELECT CAST(versionname as CHAR(100)) as versionname, v.id as vehicle_id, GROUP_CONCAT( DISTINCT (v.vehiclename) ) "
                    + "AS vehiclename, GROUP_CONCAT( DISTINCT (vm.modelname) ) AS modelname,GROUP_CONCAT( DISTINCT (vm.id) ) AS model_id,"
                    + "GROUP_CONCAT( vmm.id ) AS vehicle_mapping_id, vv.status,vv.flag "
                    + "FROM vehicle_and_model_mapping AS vmm INNER JOIN vehicle AS v ON v.id = vmm.vehicle_id "
                    + "INNER JOIN vehicleversion AS vv ON vv.id = vmm.vehicleversion_id INNER JOIN vehiclemodel AS vm "
                    + "ON vm.id = vmm.model_id where vmm.vehicleversion_id=" + vver.getId() + " GROUP BY vmm.vehicleversion_id, vmm.vehicle_id";
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
            System.out.println("acb version error message" + e.getMessage());
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

    public static Map<String, Object> LoadVehicleModels_and_ACB(int vehver_id,int vehicle_id) throws SQLException {
        System.out.println("LoadVehicleModels_and_ACB");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Map<String, Object> columns3 = new HashMap<String, Object>();
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();
            
//            String ecu_sql = "select CAST(ag.ecu_id as CHAR(100)) as eid, ecu.ecu_name as listitem,GROUP_CONCAT(DISTINCT(v.id)) as variant_id,GROUP_CONCAT(DISTINCT(v.variant_name)) as variant_name from acbversion_group as ag INNER JOIN engine_control_unit as ecu ON ecu.id=ag.ecu_id INNER JOIN "
//                    + "ecu_and_variants_mapping as ev ON ev.ecu_id=ecu.id INNER JOIN variants as v ON v.id=ev.variant_id where ag.vehicleversion_id="+vehver_id+" "
//                    + "AND ag.vehicle_id="+vehicle_id+" GROUP by ag.ecu_id";           
//            System.out.println(ecu_sql);
//            ResultSet resultSet = statement.executeQuery(ecu_sql);
//            ResultSetMetaData metaData = resultSet.getMetaData();
//            int colCount = metaData.getColumnCount();
//            List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
//            while (resultSet.next()) {
//              Map<String, Object> columns = new HashMap<String, Object>();
//              for (int i = 1; i <= colCount; i++) {
//                columns.put(metaData.getColumnLabel(i), resultSet.getObject(i));
//              }
//              row.add(columns);
//            }           
            
            //String acb_sql = "select acb.id,CAST(acb.acb_versionname as CHAR(100)) as acb_versionname from acbversion_group as acbg INNER JOIN acbversion as acb ON acb.id=acbg.acbversion_id where acbg.vehicleversion_id="+vmm.getVehicleversion_id()+" and acbg.vehicle_id="+vmm.getVehicle_id()+" AND acb.status=1 AND acb.subversion_of IS NULL group by acbg.acbversion_id";
            String acb_sql = "select acb.id,CAST(acb.acb_versionname as CHAR(100)) as acb_versionname from acbversion_group as acbg INNER JOIN acbversion as acb ON acb.id=acbg.acbversion_id where acbg.vehicleversion_id="+vehver_id+" and acbg.vehicle_id="+vehicle_id+" AND acb.status=1 AND acb.flag=1 AND acb.features_fully_touchedstatus=1 group by acbg.acbversion_id order by acb.id DESC";
            System.out.println("acb_sql"+acb_sql);
            ResultSet acb_rs = statement.executeQuery(acb_sql);        
            ResultSetMetaData acb_metaData = acb_rs.getMetaData();
            int acb_colCount = acb_metaData.getColumnCount();        
            List<Map<String, Object>> acb_row = new ArrayList<Map<String, Object>>();
            while (acb_rs.next()) {
              Map<String, Object> ivn_columns = new HashMap<String, Object>();
              for (int i = 1; i <= acb_colCount; i++) {
                ivn_columns.put(acb_metaData.getColumnLabel(i), acb_rs.getObject(i));
              }
              acb_row.add(ivn_columns);
            }
            
//            columns3.put("ecu_list",row);
            columns3.put("acb_list",acb_row);
            System.out.println("columns"+columns3);
        } catch (Exception e) {
            System.out.println("LoadVehicleModels_and_ACB error message"+e.getMessage()); 
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

    public static List<Map<String, Object>> LoadModelPreviousVehicleversionStatus(Modelversion m) throws SQLException {
        System.out.println("LoadModelPreviousVehicleversionStatus");
//        String status = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
            //        String sql = "select v.id,v.versionname,v.status from vehicleversion v where v.status=1";
            String sql = "select m.status,m.flag from modelversion m where m.id=" + m.getId();
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
            System.out.println("Model version error message" + e.getMessage());
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

    public static Object[] insertModelVersion(Modelversion mv) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        float versionname = 0;
        try {
            connection = ConnectionConfiguration.getConnection();

            Statement statement = connection.createStatement();
            System.out.println("status_value"+mv.getStatus());
            System.out.println("flag_value"+mv.getFlag());
            if(mv.getOperation_status().equals("create")){
                String sql = "SELECT id, model_versionname FROM modelversion ORDER BY model_versionname DESC LIMIT 1";
                ResultSet resultSet = statement.executeQuery(sql);
                resultSet.last();
                if (resultSet.getRow() == 0) {
                    versionname = (float) 1.0;
                } else {
                    versionname = (float) 1.0 + resultSet.getFloat("model_versionname");
                }
                preparedStatement = connection.prepareStatement("INSERT INTO modelversion (model_versionname,status,created_date,created_or_updated_by,flag)"
                        + "VALUES (?, ?, ?, ?, ?)", preparedStatement.RETURN_GENERATED_KEYS);
                //            preparedStatement.setString(1, v.getVersionname());
                preparedStatement.setDouble(1, versionname);
                preparedStatement.setBoolean(2, mv.getStatus());
                preparedStatement.setString(3, mv.getCreated_date());
                preparedStatement.setInt(4, mv.getCreated_or_updated_by());
                preparedStatement.setBoolean(5, mv.getFlag());
                preparedStatement.executeUpdate();

                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    int last_inserted_id = rs.getInt(1);
                    return new Object[]{last_inserted_id, versionname};
                }
            }
            else{   
                String versionName = "SELECT model_versionname FROM modelversion WHERE id ="+mv.getId();
                ResultSet resultSet = statement.executeQuery(versionName);
                resultSet.last();
                if (resultSet.getRow() != 0) {
                    versionname = (float) resultSet.getFloat("model_versionname");
                }
                System.out.println("object_value_in_update"+mv.getId()+mv.getStatus()+mv.getCreated_or_updated_by());
                String sql = "UPDATE modelversion SET " +
                    "status = ?, created_or_updated_by = ?, flag=?   WHERE id = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setBoolean(1, mv.getStatus());
                preparedStatement.setInt(2, mv.getCreated_or_updated_by());
                preparedStatement.setBoolean(3, mv.getFlag());
                preparedStatement.setInt(4, mv.getId());
                preparedStatement.executeUpdate();
                return new Object[]{mv.getId(), versionname};
            }
        } catch (Exception e) {
            System.out.println("Model version error message" + e.getMessage());
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

    public static int insertModelVersionGroup(ModelVersionGroup mg) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
//        System.out.println("button_type"+v.getButton_type());
        int resultSet_count = 0;
        try {
            boolean flagvalue;
            connection = ConnectionConfiguration.getConnection();
            if (mg.getOperation_status().equals("update")) {
                System.out.println("update_if");
                Statement statement = connection.createStatement();
//                String sql = "select pg.id from pdbversion_group as pg where "
//                        + "pg.pdbversion_id="+pg.getPDBversion_id()
//                        + " AND pg.vehicle_and_model_mapping_id="+pg.getVehicle_and_model_mapping_id()+" AND pg.domain_and_features_mapping_id="+pg.getDomain_and_features_mapping_id()
//                        + " AND pg.available_status='"+pg.getAvailable_status()+"'";
                String sql = "select * from modelversion_group as mg where "
                        + "mg.modelversion_id="+mg.getModelversion_id()
                        + " AND mg.vehicleversion_id="+mg.getVehicleversion_id()+" AND mg.vehicle_id="+mg.getVehicle_id()
                        + " AND mg.acbversion_id="+mg.getACBversion_id()
                        + " AND mg.vehicle_and_model_mapping_id="+mg.getVehicle_and_model_mapping_id()+" AND mg.ecu_id="+mg.getEcu_id();
                System.out.println("sql_query"+sql);
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
//                    if(resultSet.getInt("pdbversion_id") == pg.getPDBversion_id() && 
//                            resultSet.getInt("vehicle_and_model_mapping_id") == pg.getVehicle_and_model_mapping_id() &&
//                            resultSet.getInt("domain_and_features_mapping_id") == pg.getDomain_and_features_mapping_id()){ 
                    System.out.println("while");
                    if (resultSet.getInt("variant_id") != mg.getVariant_id()) {
                        System.out.println("if");
                        String update_sql = "UPDATE modelversion_group SET "
                                + "variant_id = ?  WHERE id = ?";
                        preparedStatement = connection.prepareStatement(update_sql);
                        preparedStatement.setInt(1, mg.getVariant_id());
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
                preparedStatement = connection.prepareStatement("INSERT INTO modelversion_group (modelversion_id, vehicleversion_id, vehicle_id, acbversion_id, vehicle_and_model_mapping_id, ecu_id ,variant_id)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS);

                preparedStatement.setInt(1, mg.getModelversion_id());
                preparedStatement.setInt(2, mg.getVehicleversion_id());
                preparedStatement.setInt(3, mg.getVehicle_id());
                preparedStatement.setInt(4, mg.getACBversion_id());
                preparedStatement.setInt(5, mg.getVehicle_and_model_mapping_id());
                preparedStatement.setInt(6, mg.getEcu_id());
                preparedStatement.setInt(7, mg.getVariant_id());
//                if(pg.getButton_type().equals("save"))
//                    flagvalue = false;
//                else
//                    flagvalue = true;
//                preparedStatement.setBoolean(5, flagvalue);
                preparedStatement.executeUpdate();

                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    GlobalDataStore.globalData.add(rs.getInt(1));
                }
            }
////            System.out.println("vehicleversion_id"+vehicleversion_id);
//            if(pg.getButton_type().equals("save")){
//                return temp_status;
//            }
//            else if(pg.getButton_type().equals("submit")){
//                return perm_status;
//            }
////            ResultSet rs = preparedStatement.getGeneratedKeys();
////            if(rs.next())
////            {
////                int last_inserted_id = rs.getInt(1);
////                return last_inserted_id;
////            }
            System.out.println("globalData" + GlobalDataStore.globalData);
            if (mg.getButton_type().equals("save")) {
                return temp_status;
            } else if (mg.getButton_type().equals("submit")) {
                return perm_status;
            }
        } catch (Exception e) {
            System.out.println("Model version error message" + e.getMessage());
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

    public static List<Map<String, Object>> LoadModelVersion(String filter) throws SQLException {
        System.out.println("LoadModelVersion");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
            String sql;
            if (filter.equals("active")) {
                sql = "select m.id,m.model_versionname,m.status from modelversion m where m.flag=1 and m.status=1";
            } else {
                sql = "select m.id,m.model_versionname,m.status from modelversion m";
            }
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
            System.out.println("row_data" + row);
        } catch (Exception e) {
            System.out.println("Model version error message" + e.getMessage());
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
    
    public static Map<String, Object> LoadACBDataForModelVersion(int vehver_id,int vehicle_id, int acbver_id) throws SQLException {
        System.out.println("LoadACBDataForModelVersion");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Map<String, Object> columns3 = new HashMap<String, Object>();
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();
            
            String ecu_sql = "select CAST(ag.ecu_id as CHAR(100)) as eid, ecu.ecu_name as listitem,GROUP_CONCAT(DISTINCT(v.id)) as variant_id,GROUP_CONCAT(DISTINCT(v.variant_name)) as variant_name from acbversion_group as ag INNER JOIN engine_control_unit as ecu ON ecu.id=ag.ecu_id INNER JOIN "
                    + "ecu_and_variants_mapping as ev ON ev.ecu_id=ecu.id INNER JOIN variants as v ON v.id=ev.variant_id where ag.vehicleversion_id="+vehver_id+" "
                    + "AND ag.vehicle_id="+vehicle_id+" AND ag.acbversion_id="+acbver_id+" GROUP by ag.ecu_id";           
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
                      
            columns3.put("ecu_list",row);
            System.out.println("columns"+columns3);
        } catch (Exception e) {
            System.out.println("LoadVehicleModels_and_ACB error message"+e.getMessage()); 
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
    public static Map<String, Object> LoadModelPreviousversionData(Modelversion modelver) throws SQLException {
        System.out.println("LoadModelPreviousversionData");
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
            String modelversion_sql = "select CAST(mvg.vehicleversion_id as CHAR(100)) as vehicleversion,CAST(mvg.vehicle_id as CHAR(100)) as vehiclename,"
                    + "CAST(mvg.acbversion_id as CHAR(100)) as acbversion "
                    + "from modelversion_group as mvg where mvg.modelversion_id="+modelver.getId()+" LIMIT 1"; 
            System.out.println("modelversion_sql"+modelversion_sql);
            ResultSet rs_mvg = statement.executeQuery(modelversion_sql);
            ResultSetMetaData metaData_mvg = rs_mvg.getMetaData();
            int colCount_mvg = metaData_mvg.getColumnCount();
            List<Map<String, Object>> row_mvg = new ArrayList<Map<String, Object>>();
            while (rs_mvg.next()) {
              Map<String, Object> columns_mvg = new HashMap<String, Object>();
              for (int i = 1; i <= colCount_mvg; i++) {
                columns_mvg.put(metaData_mvg.getColumnLabel(i), rs_mvg.getObject(i));
              }
              row_mvg.add(columns_mvg);
            }           

            String model_sql = "select CAST(mvg.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,vm.modelname from modelversion_group as mvg "
                    + "inner join vehicle_and_model_mapping as vmm on vmm.id = mvg.vehicle_and_model_mapping_id INNER JOIN vehiclemodel as vm ON vm.id = vmm.model_id \n" +
                    "where mvg.modelversion_id="+modelver.getId()+" group by mvg.vehicle_and_model_mapping_id"; 
            System.out.println("model_sql"+model_sql);
            ResultSet rs_model = statement.executeQuery(model_sql);
            ResultSetMetaData metaData_model = rs_model.getMetaData();
            int colCount_model = metaData_model.getColumnCount();
            List<Map<String, Object>> row_model = new ArrayList<Map<String, Object>>();
            while (rs_model.next()) {
              Map<String, Object> columns_model = new HashMap<String, Object>();
              for (int i = 1; i <= colCount_model; i++) {
                columns_model.put(metaData_model.getColumnLabel(i), rs_model.getObject(i));
              }
              row_model.add(columns_model);
            }
            
            String ecu_sql = "select CAST(mvg.ecu_id as CHAR(100)) as eid, ecu.ecu_name as listitem,GROUP_CONCAT(DISTINCT(v.id)) as variant_id,GROUP_CONCAT(DISTINCT(v.variant_name)) as variant_name from modelversion_group as mvg INNER JOIN engine_control_unit as ecu ON ecu.id=mvg.ecu_id INNER JOIN "
                    + "ecu_and_variants_mapping as ev ON ev.ecu_id=ecu.id INNER JOIN variants as v ON v.id=ev.variant_id where mvg.modelversion_id="+modelver.getId()+" GROUP by mvg.ecu_id ORDER BY mvg.ecu_id DESC";           
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

            String mvglist_sql = "select CAST(mvg.ecu_id as CHAR(100)) as ecu_id,CAST(mvg.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,CAST(mvg.variant_id as CHAR(100)) as variant_id from modelversion_group as mvg where mvg.modelversion_id="+modelver.getId()+" ORDER BY mvg.id DESC";           
            System.out.println("mvglist_sql"+mvglist_sql);
            ResultSet rs_mvglist = statement.executeQuery(mvglist_sql);
            ResultSetMetaData metaData_mvglist = rs_mvglist.getMetaData();
            int colCount_mvglist = metaData_mvglist.getColumnCount();
            List<Map<String, Object>> row_mvglist = new ArrayList<Map<String, Object>>();
            while (rs_mvglist.next()) {
              Map<String, Object> columns_mvglist = new HashMap<String, Object>();
              for (int i = 1; i <= colCount_mvglist; i++) {
                columns_mvglist.put(metaData_mvglist.getColumnLabel(i), rs_mvglist.getObject(i));
              }
              row_mvglist.add(columns_mvglist);
            }             
            
            String mv_status_sql = "select mv.status from modelversion mv where mv.id="+modelver.getId();
            ResultSet resultSet_st = statement.executeQuery(mv_status_sql);
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
                       

            columns_res.put("modelversion",row_mvg);
            columns_res.put("vehiclemodel_list",row_model);
            columns_res.put("ecu_list",row);
            columns_res.put("modeldata_list",row_mvglist);
            columns_res.put("modelversion_status",row_st);
        
        } catch (Exception e) {
            System.out.println("Load Model version error message"+e.getMessage()); 
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
    public static List<Map<String, Object>> GetModelVersion_Listing() throws SQLException {
        System.out.println("GetModelVersion_Listing");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
            String sql = "SELECT mv.id as id,CAST(model_versionname as CHAR(100)) as model_versionname, CAST(versionname as CHAR(100)) as vehicle_versionname, "
                    + "GROUP_CONCAT( DISTINCT (acb.acb_versionname) ) AS acb_versionname, "
                    + "GROUP_CONCAT( DISTINCT (v.vehiclename) ) AS vehiclename, "
                    + "GROUP_CONCAT( DISTINCT (vm.modelname) ) AS models, "
                    + "GROUP_CONCAT( DISTINCT (ecu.ecu_name) ) AS ecu,"
                    + " mv.status, mv.flag FROM modelversion_group AS mvg "
                    + " INNER JOIN vehicle AS v ON v.id = mvg.vehicle_id INNER JOIN vehicleversion AS vv ON"
                    + " vv.id = mvg.vehicleversion_id INNER JOIN acbversion AS acb ON acb.id = mvg.acbversion_id"
                    + " INNER JOIN modelversion AS mv ON mv.id = mvg.modelversion_id"
                    + " INNER JOIN vehicle_and_model_mapping AS vmm ON vmm.id = mvg.vehicle_and_model_mapping_id"
                    + " INNER JOIN vehiclemodel AS vm ON vm.id = vmm.model_id"
                    + " INNER JOIN engine_control_unit AS ecu ON ecu.id = mvg.ecu_id"
                    + " GROUP BY mvg.modelversion_id DESC";
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
            System.out.println("Model version listing error message"+e.getMessage()); 
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
    public static Map<String, Object> GetVehMod_Dashboarddata() throws SQLException {
        System.out.println("GetVehMod_Dashboarddata");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        connection = ConnectionConfiguration.getConnection();
        Statement statement = connection.createStatement();
        Map<String, Object> columns = new HashMap<String, Object>();
        
        //Get Vehicle count
        String veh_sql = "select * from vehicle";
        ResultSet veh_rs = statement.executeQuery(veh_sql);
        veh_rs.last(); 
        System.out.println("resultset_count"+veh_rs.getRow());
        columns.put("vehiclecount", veh_rs.getRow());
        
        //Get Model count
        String mod_sql = "select * from vehiclemodel";
        ResultSet mod_rs = statement.executeQuery(mod_sql);
        mod_rs.last(); 
        System.out.println("resultset_count"+mod_rs.getRow());
        columns.put("modelcount", mod_rs.getRow());
        
        //Get Vehicle Versions count
        String vehver_sql = "select * from vehicleversion";
        ResultSet vehver_rs = statement.executeQuery(vehver_sql);
        vehver_rs.last(); 
        System.out.println("resultset_count"+vehver_rs.getRow());
        columns.put("vehicleversion_count", vehver_rs.getRow());
        
        //Get Model Versions count
        String modver_sql = "select * from vehicleversion";
        ResultSet modver_rs = statement.executeQuery(modver_sql);
        modver_rs.last(); 
        System.out.println("resultset_count"+modver_rs.getRow());
        columns.put("modelversion_count", modver_rs.getRow());
       
        return columns;
    }
    public static void deleteVehicleModelMapping(int vehicleversion_id) throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        connection = ConnectionConfiguration.getConnection();
        preparedStatement = connection.prepareStatement("delete from vehicle_and_model_mapping where vehicleversion_id=" + vehicleversion_id + " AND id NOT IN (" + StringUtils.join(GlobalDataStore.globalData, ',') + ")");
        preparedStatement.executeUpdate();

        GlobalDataStore.globalData.clear();
    }

    public static void deleteModelVersion_Group(int modelversion_id, String action_type) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        System.out.println("deletemodelversiongroup" + GlobalDataStore.globalData);
        System.out.println("action_type" + action_type);
        if (action_type.equals("update")) {
            connection = ConnectionConfiguration.getConnection();
            preparedStatement = connection.prepareStatement("delete from modelversion_group where modelversion_id=" + modelversion_id + " AND id NOT IN (" + StringUtils.join(GlobalDataStore.globalData, ',') + ")");
            preparedStatement.executeUpdate();
        }
        GlobalDataStore.globalData.clear();
    }

    public static Object[] getVehicleModelId(String vehicleName, String modelName) {
        Connection connection = null;
        ResultSet resultSet = null;
        int vehicle_id = 0, model_id = 0;
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();

            String fetch_vehicleId = "SELECT id FROM vehicle WHERE vehiclename = '" + vehicleName + "'";
            resultSet = statement.executeQuery(fetch_vehicleId);
            resultSet.last();
            if (resultSet.getRow() != 0) {
                vehicle_id = resultSet.getInt("id");
            }
            resultSet = null;
            String fetch_modelId = "SELECT id FROM vehiclemodel WHERE modelname = '" + modelName + "'";
            resultSet = statement.executeQuery(fetch_modelId);
            resultSet.last();
            if (resultSet.getRow() != 0) {
                model_id = resultSet.getInt("id");
            }
            return new Object[]{vehicle_id, model_id};
        } catch (Exception e) {
            System.out.println("Error on Fetching Vehicle & Model Id" + e.getMessage());
            e.printStackTrace();
            return new Object[]{vehicle_id, model_id};

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return new Object[]{vehicle_id, model_id};
                }
            }
        }
    }

    public static int getVehicleModelMappingId(Object[] obj) {
        Connection connection = null;
        ResultSet resultSet = null;
        int vmm_id = 0;
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();

            String fetch_vmmId = "SELECT id FROM vehicle_and_model_mapping WHERE vehicle_id = " + (int) obj[0] + " AND model_id = " + (int) obj[1];
            resultSet = statement.executeQuery(fetch_vmmId);
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

    public static float getVehicleVersionNameFromId(int id){
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();

            String fetch_versionname = "SELECT versionname FROM vehicleversion WHERE id = " + id;
            resultSet = statement.executeQuery(fetch_versionname);
            resultSet.last();
            if (resultSet.getRow() != 0) {
                return resultSet.getFloat(1);
            }
        } catch (Exception e) {
            System.out.println("Error on Fetching Version Name " + e.getMessage());
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

    public static String getVehicleNameFromId(int id){
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();

            String fetch_vehiclename = "SELECT vehiclename FROM vehicle WHERE id = " + id;
            resultSet = statement.executeQuery(fetch_vehiclename);
            resultSet.last();
            if (resultSet.getRow() != 0) {
                return resultSet.getString(1);
            }
        } catch (Exception e) {
            System.out.println("Error on Fetching Vehicle Name " + e.getMessage());
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
        return null;
    }
}

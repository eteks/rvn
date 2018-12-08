/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.ivn_engineer;

import com.controller.common.CookieRead;
import com.db_connection.ConnectionConfiguration;
import com.model.common.GlobalDataStore;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ets-2
 */
public class IVNEngineerDB {

    public static int temp_status = 0;
    public static int perm_status = 1;

    public static List<Map<String, Object>> LoadIVNVersion(String filter) throws SQLException {
        System.out.println("LoadIVNVersion");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
            String sql;
            if (filter.equals("active")) {
                sql = "select iv.id,iv.ivn_versionname,iv.status from ivnversion iv where iv.flag=1 and iv.status=1";
            } else {
                sql = "select iv.id,iv.ivn_versionname,iv.status from ivnversion iv";
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

    public static Map<String, Object> LoadNetwork() throws SQLException {
        System.out.println("LoadNetwork");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Map<String, Object> columns2 = new HashMap<String, Object>();
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();

            String can_sql = "select CAST(c.id as CHAR(100)) as cid,c.network_name as listitem from network as c where status=1 AND network_type='can'";
            System.out.println(can_sql);
            ResultSet resultSet = statement.executeQuery(can_sql);
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

            String lin_sql = "select CAST(l.id as CHAR(100)) as lid, l.network_name as listitem from network as l where status=1 AND network_type='lin'";
            System.out.println(lin_sql);
            ResultSet resultSet1 = statement.executeQuery(lin_sql);
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

            String hardware_sql = "select CAST(hw.id as CHAR(100)) as hid, hw.network_name as listitem from network as hw where status=1 AND network_type='hardware'";
            System.out.println(hardware_sql);
            ResultSet resultSet2 = statement.executeQuery(hardware_sql);
            ResultSetMetaData metaData2 = resultSet2.getMetaData();
            int colCount2 = metaData2.getColumnCount();
            List<Map<String, Object>> row2 = new ArrayList<Map<String, Object>>();
            while (resultSet2.next()) {
                Map<String, Object> columns3 = new HashMap<String, Object>();
                for (int i = 1; i <= colCount2; i++) {
                    columns3.put(metaData2.getColumnLabel(i), resultSet2.getObject(i));
                }
                row2.add(columns3);
            }

            columns2.put("can_list", row);
            columns2.put("lin_list", row1);
            columns2.put("hardware_list", row2);
            System.out.println("columns" + columns2);
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
        return columns2;
    }

    public static List<Map<String, Object>> LoadECU() throws SQLException {
        System.out.println("LoadECU");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
            //        String sql = "select v.id,v.versionname,v.status from vehicleversion v where v.status=1";
            String sql = "select CAST(ecu.id as CHAR(100)) as eid,ecu.ecu_name as listitem,ecu.ecu_description as description from engine_control_unit as ecu where ecu.status=1";
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

    public static List<Map<String, Object>> LoadSignals() throws SQLException {
        System.out.println("LoadSignals");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
            //        String sql = "select v.id,v.versionname,v.status from vehicleversion v where v.status=1";
            String sql = "select CAST(s.id as CHAR(100)) as sid,s.signal_name as listitem,s.signal_description as description from signals as s where s.status=1";
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

    public static Map<String, Object> insertNetworkData(Network_Ecu n) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
//        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        Map<String, Object> columns = new HashMap<String, Object>();
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();
            if (n.getNetwork_type().equals("can") || n.getNetwork_type().equals("lin") || n.getNetwork_type().equals("hardware")) {
                String sql = "SELECT id FROM network WHERE network_name ='" + n.getNetworkname().trim() + "' AND network_type='" + n.getNetwork_type() + "'";
                resultSet = statement.executeQuery(sql);
                resultSet.last();
                if (resultSet.getRow() > 0) {
                    int last_inserted_id = resultSet.getInt(1);
//                   return last_inserted_id;
                } else {
                    preparedStatement = connection.prepareStatement("INSERT INTO network (network_name,network_description,network_type,created_date,created_or_updated_by)"
                            + "VALUES (?, ?, ?, ?, ?)", preparedStatement.RETURN_GENERATED_KEYS);
                }
            } else if (n.getNetwork_type().equals("ecu")) {
                String sql = "SELECT id FROM engine_control_unit WHERE ecu_name ='" + n.getEcuname().trim() + "'";
                resultSet = statement.executeQuery(sql);
                resultSet.last();
                if (resultSet.getRow() > 0) {
                    int last_inserted_id = resultSet.getInt(1);
//                   return last_inserted_id;
                } else {
                    preparedStatement = connection.prepareStatement("INSERT INTO engine_control_unit (ecu_name,ecu_description,created_date,created_or_updated_by)"
                            + "VALUES (?, ?, ?, ?)", preparedStatement.RETURN_GENERATED_KEYS);
                }
            }
            if (resultSet.getRow() == 0) {
                if (n.getNetwork_type().equals("ecu")) {
                    preparedStatement.setString(1, n.getEcuname());
                    preparedStatement.setString(2, n.getEcudescription());
                    preparedStatement.setString(3, n.getCreated_date());
                    preparedStatement.setInt(4, n.getCreated_or_updated_by());
                } else {
                    preparedStatement.setString(1, n.getNetworkname());
                    preparedStatement.setString(2, n.getNetworkdescription());
                    preparedStatement.setString(3, n.getNetwork_type());
                    preparedStatement.setString(4, n.getCreated_date());
                    preparedStatement.setInt(5, n.getCreated_or_updated_by());
                }
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();

                if (rs.next()) {
                    int last_inserted_id = rs.getInt(1);
//                    return last_inserted_id;
                    if (n.getNetwork_type().equals("can")) {
                        columns.put("cid", Integer.toString(last_inserted_id));
                    } else if (n.getNetwork_type().equals("lin")) {
                        columns.put("lid", Integer.toString(last_inserted_id));
                    } else if (n.getNetwork_type().equals("hardware")) {
                        columns.put("hid", Integer.toString(last_inserted_id));
                    } else {
                        columns.put("eid", Integer.toString(last_inserted_id));
                        columns.put("description", n.getEcudescription());
                        columns.put("listitem", n.getEcuname());
                    }
                    if (!n.getNetwork_type().equals("ecu")) {
                        columns.put("listitem", n.getNetworkname());
                    }
                }
//                row.add(columns);

//                if(rs.next())
//                {
//                    int last_inserted_id = rs.getInt(1);
////                    return last_inserted_id;
//                }
            }
        } catch (Exception e) {
            System.out.println("vehicle version error message" + e.getMessage());
            e.printStackTrace();
//            return 0;

        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
//                    return 0;
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
//                    return 0;
                }
            }
        }
        return columns;
    }

    public static int getNetworkDataCSV(String network_name, String network_type) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();
            String fetch_id_query = "SELECT id FROM network WHERE network_name ='" + network_name + "' AND network_type='" + network_type + "'";
            resultSet = statement.executeQuery(fetch_id_query);
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                preparedStatement = connection.prepareStatement("INSERT INTO network (network_name,network_description,network_type,created_date,created_or_updated_by)"
                        + "VALUES (?, ?, ?, ?, ?)", preparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, network_name);
                preparedStatement.setString(2, "");
                preparedStatement.setString(3, network_type);
                preparedStatement.setString(4, dtf.format(now));
                preparedStatement.setInt(5, 1);

                preparedStatement.executeUpdate();
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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

    public static List<Map<String, Object>> insertSignalData(Signal s) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();
            System.out.println("before preparedStatement");
            preparedStatement = connection.prepareStatement("INSERT INTO signals (signal_name,signal_alias,signal_description,"
                    + "signal_length,signal_byteorder,signal_unit,signal_valuetype,signal_initvalue,signal_factor,signal_offset,"
                    + "signal_minimum,signal_maximum,signal_valuetable,can_id_group,lin_id_group,hw_id_group,"
                    + "created_date,created_or_updated_by)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", preparedStatement.RETURN_GENERATED_KEYS);
            System.out.println("above middle preparedStatement" + s.getSignal_factor());
            preparedStatement.setString(1, s.getSignal_name());
            System.out.println("above middle1 preparedStatement");
            preparedStatement.setString(2, s.getSignal_alias());
            System.out.println("above middle2 preparedStatement");
            preparedStatement.setString(3, s.getSignal_description());
            preparedStatement.setInt(4, s.getSignal_length());
            preparedStatement.setString(5, s.getSignal_byteorder());
            preparedStatement.setString(6, s.getSignal_unit());
            preparedStatement.setString(7, s.getSignal_valuetype());
            preparedStatement.setInt(8, s.getSignal_initvalue());
            preparedStatement.setDouble(9, s.getSignal_factor());
            preparedStatement.setInt(10, s.getSignal_offset());
            preparedStatement.setInt(11, s.getSignal_minimum());
            preparedStatement.setInt(12, s.getSignal_maximum());
            preparedStatement.setString(13, s.getSignal_valuetable());
            preparedStatement.setString(14, s.getSignal_can_id());
            preparedStatement.setString(15, s.getSignal_lin_id());
            preparedStatement.setString(16, s.getSignal_hw_id());
            preparedStatement.setString(17, s.getCreated_date());
            preparedStatement.setInt(18, s.getCreated_or_updated_by());
            System.out.println("middle preparedStatement");
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            System.out.println("after preparedStatement");
            Map<String, Object> columns = new HashMap<String, Object>();
            if (rs.next()) {
                int last_inserted_id = rs.getInt(1);
                //Get all the information of signals
                String sql = "select s.id as sid, s.signal_name as listitem,s.signal_alias as salias,s.signal_description as description,\n"
                        + "GROUP_CONCAT(DISTINCT(cn.network_name)) as can,\n"
                        + "GROUP_CONCAT(DISTINCT(ln.network_name)) as lin,\n"
                        + "GROUP_CONCAT(DISTINCT(hw.network_name)) as hardware from signals as s \n"
                        + "inner join network as cn on FIND_IN_SET(cn.id,s.can_id_group) > 0 \n"
                        + "inner join network as ln on FIND_IN_SET(ln.id,s.lin_id_group) > 0 \n"
                        + "inner join network as hw on FIND_IN_SET(hw.id,s.hw_id_group) > 0 \n"
                        + "where s.id=" + last_inserted_id + " LIMIT 1";
                ResultSet resultSet = statement.executeQuery(sql);
                resultSet.last();
                columns.put("listitem", s.getSignal_name());
                columns.put("sid", Integer.toString(last_inserted_id));
                columns.put("description", s.getSignal_description());
                columns.put("salias", resultSet.getString("salias"));
                columns.put("can", resultSet.getString("can"));
                columns.put("lin", resultSet.getString("lin"));
                columns.put("hardware", resultSet.getString("hardware"));
                row.add(columns);
            }
        } catch (Exception e) {
            System.out.println("vehicle version error message" + e.getMessage());
            e.printStackTrace();
//            return 0;

        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
//                    return 0;
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
//                    return 0;
                }
            }
        }
        return row;
    }

    public static List<Map<String, Object>> LoadIVNPreviousVehicleversionStatus(IVNversion iv) throws SQLException {
        System.out.println("LoadIVNPreviousVehicleversionStatus");
//        String status = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
            //        String sql = "select v.id,v.versionname,v.status from vehicleversion v where v.status=1";
            String sql = "select iv.status,iv.flag from ivnversion iv where iv.id=" + iv.getId();
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

    public static Object[] insertIVNVersion(IVNversion iv) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        float versionname = 0.0f;
        try {
            connection = ConnectionConfiguration.getConnection();

            Statement statement = connection.createStatement();
            if (iv.getOperation_status().equals("create")) {
                String sql = "SELECT id, ivn_versionname FROM ivnversion ORDER BY ivn_versionname DESC LIMIT 1";
                ResultSet resultSet = statement.executeQuery(sql);
                resultSet.last();
                if (resultSet.getRow() == 0) {
                    versionname = (float) 1.0;
                } else {
                    versionname = (float) 1.0 + resultSet.getFloat("ivn_versionname");
                }
                preparedStatement = connection.prepareStatement("INSERT INTO ivnversion (ivn_versionname,status,created_date,created_or_updated_by,flag)"
                        + "VALUES (?, ?, ?, ?, ?)", preparedStatement.RETURN_GENERATED_KEYS);
                //            preparedStatement.setString(1, v.getVersionname());
                preparedStatement.setDouble(1, versionname);
                preparedStatement.setBoolean(2, iv.getStatus());
                preparedStatement.setString(3, iv.getCreated_date());
                preparedStatement.setInt(4, iv.getCreated_or_updated_by());
                preparedStatement.setBoolean(5, iv.getFlag());
                preparedStatement.executeUpdate();

                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    int last_inserted_id = rs.getInt(1);
                    return new Object[]{last_inserted_id, versionname};
                }
            } else {
                String versionName = "SELECT ivn_versionname FROM ivnversion WHERE id =" + iv.getId();
                ResultSet resultSet = statement.executeQuery(versionName);
                resultSet.last();
                if (resultSet.getRow() != 0) {
                    versionname = (float) resultSet.getFloat("ivn_versionname");
                }
                System.out.println("object_value_in_update" + iv.getId() + iv.getStatus() + iv.getCreated_or_updated_by());
                String sql = "UPDATE ivnversion SET "
                        + "status = ?, created_or_updated_by = ?, flag=?   WHERE id = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setBoolean(1, iv.getStatus());
                preparedStatement.setInt(2, iv.getCreated_or_updated_by());
                preparedStatement.setBoolean(3, iv.getFlag());
                preparedStatement.setInt(4, iv.getId());
                preparedStatement.executeUpdate();
                return new Object[]{iv.getId(), versionname};
            }
        } catch (Exception e) {
            System.out.println("ivn version error message" + e.getMessage());
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

    public static int insertIVNNetworkModel(IVNNetwork_VehicleModel invm) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int resultSet_count = 0;
        int last_inserted_id = 0;
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();
            System.out.println("globaldatastore" + StringUtils.join(GlobalDataStore.globalData, ','));
            if (invm.getOperation_status().equals("update")) {
                System.out.println("update");
                String network_sql = "select * from ivn_" + invm.getNetwork_type() + "models as ntm where "
                        + "ntm.ivnversion_id=" + invm.getIvnversion_id()
                        + " AND ntm.vehicle_and_model_mapping_id=" + invm.getVehicle_model_mapping_id()
                        + " AND ntm.network_" + invm.getNetwork_type() + "_id=" + invm.getNetwork_id();
                ResultSet resultSet = statement.executeQuery(network_sql);
                while (resultSet.next()) {
                    GlobalDataStore.globalData.add(resultSet.getInt("id"));
                    last_inserted_id = resultSet.getInt(1);
                }
                resultSet.last();
                resultSet_count = resultSet.getRow();
                System.out.println("resultSet_count" + resultSet_count);
            }
            if (resultSet_count == 0) {
                System.out.println("if");
                if (invm.getNetwork_type().equals("can")) {
                    preparedStatement = connection.prepareStatement("INSERT INTO ivn_canmodels (ivnversion_id, network_can_id,vehicle_and_model_mapping_id,available_status)"
                            + "VALUES (?, ?, ?, ?)", preparedStatement.RETURN_GENERATED_KEYS);
                } else if (invm.getNetwork_type().equals("lin")) {
                    preparedStatement = connection.prepareStatement("INSERT INTO ivn_linmodels (ivnversion_id, network_lin_id,vehicle_and_model_mapping_id,available_status)"
                            + "VALUES (?, ?, ?, ?)", preparedStatement.RETURN_GENERATED_KEYS);
                } else {
                    preparedStatement = connection.prepareStatement("INSERT INTO ivn_hardwaremodels (ivnversion_id, network_hardware_id,vehicle_and_model_mapping_id,available_status)"
                            + "VALUES (?, ?, ?, ?)", preparedStatement.RETURN_GENERATED_KEYS);
                }
                preparedStatement.setInt(1, invm.getIvnversion_id());
                preparedStatement.setInt(2, invm.getNetwork_id());
                preparedStatement.setInt(3, invm.getVehicle_model_mapping_id());
                preparedStatement.setBoolean(4, invm.getAvailable_status());

                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    GlobalDataStore.globalData.add(rs.getInt(1));
                    last_inserted_id = rs.getInt(1);
                }
            }
            return last_inserted_id;
        } catch (Exception e) {
            System.out.println("IVN can model error message" + e.getMessage());
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

    public static int insertIVNVersionGroup(IVNVersionGroup ig) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        float versionname;
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();
            if (ig.getOperation_status().equals("create")) {
                System.out.println("object_value_in_insert" + ig.getCanmodel_group() + ig.getLinmodel_group() + ig.getHardwaremodel_group());
                preparedStatement = connection.prepareStatement("INSERT INTO ivnversion_group (ivnversion_id,canmodel_group,"
                        + "linmodel_group,hardwaremodel_group,signal_group,ecu_group)"
                        + "VALUES (?, ?, ?, ?, ?, ?)", preparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, ig.getIVNversion_id());
                preparedStatement.setString(2, ig.getCanmodel_group());
                preparedStatement.setString(3, ig.getLinmodel_group());
                preparedStatement.setString(4, ig.getHardwaremodel_group());
                preparedStatement.setString(5, ig.getSignal_group());
                preparedStatement.setString(6, ig.getEcu_group());
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    int last_inserted_id = rs.getInt(1);
                    return last_inserted_id;
                }
            } else {
                System.out.println("object_value_in_update" + ig.getCanmodel_group() + ig.getLinmodel_group() + ig.getHardwaremodel_group());
                String sql = "UPDATE ivnversion_group SET "
                        + "canmodel_group = ?, linmodel_group = ?, hardwaremodel_group = ?, signal_group = ?, ecu_group =? "
                        + "WHERE ivnversion_id = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, ig.getCanmodel_group());
                preparedStatement.setString(2, ig.getLinmodel_group());
                preparedStatement.setString(3, ig.getHardwaremodel_group());
                preparedStatement.setString(4, ig.getSignal_group());
                preparedStatement.setString(5, ig.getEcu_group());
                preparedStatement.setInt(6, ig.getIVNversion_id());
                preparedStatement.executeUpdate();
                System.out.println("button_type" + ig.getButton_type());
//                return ig.getId();
                if (ig.getButton_type().equals("save")) {
                    return temp_status;
                } else if (ig.getButton_type().equals("submit")) {
                    return perm_status;
                }
            }
        } catch (Exception e) {
            System.out.println("pdb version error message" + e.getMessage());
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

    public static Map<String, Object> LoadIVNPreviousVehicleversionData(IVNversion ivnver) throws SQLException {
        System.out.println("LoadIVNPreviousVehicleversionData");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Map<String, Object> columns_res = new HashMap<String, Object>();
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();
            List<Map<String, Object>> result_row = new ArrayList<Map<String, Object>>();
            String canmodel_sql = "SELECT CAST(cn.network_can_id as CHAR(100)) as network_id,\n"
                    + "CAST(cn.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n"
                    + "cn.available_status as status \n"
                    + "FROM ivn_canmodels AS cn \n"
                    + "where cn.ivnversion_id=" + ivnver.getId();
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
                columns1.put("network_type", "can");
                row1.add(columns1);
            }

            String linmodel_sql = "SELECT CAST(ln.network_lin_id as CHAR(100)) as network_id,\n"
                    + "CAST(ln.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n"
                    + "ln.available_status as status \n"
                    + "FROM ivn_linmodels AS ln \n"
                    + "where ln.ivnversion_id=" + ivnver.getId();
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
                columns2.put("network_type", "lin");
                row2.add(columns2);
            }

            String hwmodel_sql = "SELECT CAST(hw.network_hardware_id as CHAR(100)) as network_id,\n"
                    + "CAST(hw.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n"
                    + "hw.available_status as status \n"
                    + "FROM ivn_hardwaremodels AS hw \n"
                    + "where hw.ivnversion_id=" + ivnver.getId();
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
                columns3.put("network_type", "hardware");
                row3.add(columns3);
            }

            String ivngroup_sql = "SELECT signal_group, ecu_group "
                    + "FROM ivnversion_group AS ig \n"
                    + "where ig.ivnversion_id=" + ivnver.getId();
            System.out.println(ivngroup_sql);
            ResultSet resultSet4 = statement.executeQuery(ivngroup_sql);
            ResultSetMetaData metaData4 = resultSet4.getMetaData();
            int colCount4 = metaData4.getColumnCount();
            while (resultSet4.next()) {
                //          String strarray[] =resultSet4.getString("signal_group").split(",") ;
                //          String strarray1[] =resultSet4.getString("ecu_group").split(",") ;
                //          columns_res.put("signal",strarray);
                //          columns_res.put("ecu",strarray1);
                //            columns_res.put("signal","["+resultSet4.getString("signal_group")+"]");
                //            columns_res.put("ecu","["+resultSet4.getString("ecu_group")+"]");

                columns_res.put("signal", resultSet4.getString("signal_group").split(","));
                columns_res.put("ecu", resultSet4.getString("ecu_group").split(","));
            }

            //        String ivnsignalgroup_sql = "select s.id as sid,s.signal_name as listitem,s.signal_description as description from ivnversion_group as ig inner join signals as s "
            //                + "on FIND_IN_SET(s.id,ig.signal_group) > 0 where ig.ivnversion_id="+ivnver.getId();       
            //        System.out.println(ivnsignalgroup_sql);
            //        ResultSet resultSet5 = statement.executeQuery(ivnsignalgroup_sql);
            //        ResultSetMetaData metaData5 = resultSet5.getMetaData();
            //        int colCount5 = metaData5.getColumnCount();
            //        List<Map<String, Object>> row5 = new ArrayList<Map<String, Object>>();
            //        while (resultSet5.next()) {
            //          Map<String, Object> columns5 = new HashMap<String, Object>();
            //          for (int i = 1; i <= colCount5; i++) {
            //            columns5.put(metaData5.getColumnLabel(i), resultSet5.getObject(i));
            //          }
            //          columns5.put("network_type","signal");
            //          row5.add(columns5);
            //        }
            //        String ivnecugroup_sql = "select e.id as eid,e.ecu_name as listitem,e.ecu_description as description from ivnversion_group as ig inner join engine_control_unit as e "
            //                + "on FIND_IN_SET(e.id,ig.ecu_group) > 0 where ig.ivnversion_id="+ivnver.getId();       
            //        System.out.println(ivnecugroup_sql);
            //        ResultSet resultSet6 = statement.executeQuery(ivnecugroup_sql);
            //        ResultSetMetaData metaData6 = resultSet6.getMetaData();
            //        int colCount6 = metaData6.getColumnCount();
            //        List<Map<String, Object>> row6 = new ArrayList<Map<String, Object>>();
            //        while (resultSet6.next()) {
            //          Map<String, Object> columns6 = new HashMap<String, Object>();
            //          for (int i = 1; i <= colCount6; i++) {
            //            columns6.put(metaData6.getColumnLabel(i), resultSet6.getObject(i));
            //          }
            //          columns6.put("network_type","ecu");
            //          row6.add(columns6);
            //        }
            //        String vehciledetail_sql = "SELECT \n" +
            //            "vv.id as vehver_id,\n" +
            //            "v.id as vehicle_id,\n" +
            //            "vm.modelname as modelname,\n" +
            //            "CAST(vmm.id as CHAR(100)) as vmm_id \n" +
            //            "FROM ivn_canmodels AS cn \n" +
            //            "INNER JOIN vehicle_and_model_mapping AS vmm ON vmm.id = cn.vehicle_and_model_mapping_id \n" +
            //            "INNER JOIN vehicleversion as vv on vv.id=vmm.vehicleversion_id \n" +
            //            "INNER JOIN vehicle as v on v.id=vmm.vehicle_id \n" +
            //            "INNER JOIN vehiclemodel as vm on vm.id=vmm.model_id\n" +
            //            "where cn.ivnversion_id="+ivnver.getId()+" group by modelname,vehicle_and_model_mapping_id";
            //        String vehciledetail_sql = "SELECT \n" +
            //            "vv.id as vehver_id,\n" +
            //            "v.id as vehicle_id,\n" +
            //            "vm.modelname as modelname,\n" +
            //            "CAST(vmm.id as CHAR(100)) as vmm_id \n" +
            //            "FROM ivn_canmodels AS cn \n" +
            //            "INNER JOIN vehicle_and_model_mapping AS vmm ON vmm.id = cn.vehicle_and_model_mapping_id \n" +
            //            "INNER JOIN vehicleversion as vv on vv.id=vmm.vehicleversion_id \n" +
            //            "INNER JOIN vehicle as v on v.id=vmm.vehicle_id \n" +
            //            "INNER JOIN vehiclemodel as vm on vm.id=vmm.model_id\n" +
            //            "where cn.ivnversion_id="+ivnver.getId()+" group by modelname,vehicle_and_model_mapping_id";
            String v_sql = "SELECT \n"
                    + "vmm.vehicleversion_id,vmm.vehicle_id \n"
                    + "FROM ivn_canmodels AS cn \n"
                    + "INNER JOIN vehicle_and_model_mapping AS vmm ON vmm.id = cn.vehicle_and_model_mapping_id \n"
                    + "where cn.ivnversion_id=" + ivnver.getId() + " limit 1";

            System.out.println("vehciledetail_sql" + v_sql);
            ResultSet vrs = statement.executeQuery(v_sql);
            String vehciledetail_sql = null;
            if (vrs.next()) {
                vehciledetail_sql = "SELECT \n"
                        + "vv.id as vehver_id,\n"
                        + "v.id as vehicle_id,\n"
                        + "vm.modelname as modelname,\n"
                        + "CAST(vmm.id as CHAR(100)) as vmm_id \n"
                        + "from vehicle_and_model_mapping as vmm \n"
                        + "INNER JOIN vehicleversion as vv on vv.id=vmm.vehicleversion_id \n"
                        + "INNER JOIN vehicle as v on v.id=vmm.vehicle_id \n"
                        + "INNER JOIN vehiclemodel as vm on vm.id=vmm.model_id\n"
                        + "where vmm.vehicleversion_id=" + vrs.getInt("vehicleversion_id") + " AND vmm.vehicle_id=" + vrs.getInt("vehicle_id");
            }
            ResultSet resultSet = statement.executeQuery(vehciledetail_sql);
            System.out.println("vehciledetail_sql1" + vehciledetail_sql);
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

            String ivn_status_sql = "select i.status from ivnversion i where i.id=" + ivnver.getId();
            ResultSet resultSet5 = statement.executeQuery(ivn_status_sql);
            ResultSetMetaData metaData5 = resultSet5.getMetaData();
            int colCount5 = metaData5.getColumnCount();
            List<Map<String, Object>> row5 = new ArrayList<Map<String, Object>>();
            while (resultSet5.next()) {
                Map<String, Object> columns5 = new HashMap<String, Object>();
                for (int i = 1; i <= colCount5; i++) {
                    columns5.put(metaData5.getColumnLabel(i), resultSet5.getObject(i));
                }
                row5.add(columns5);
            }

            columns_res.put("vehicledetail_list", row);
            columns_res.put("can", row1);
            columns_res.put("lin", row2);
            columns_res.put("hardware", row3);
            columns_res.put("ivnversion_status", row5);
            //        columns_res.put("signaldetail_list",row5);
            //        columns_res.put("ecudetail_list",row6);
            //        System.out.println("columns"+columns_res);
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
        return columns_res;
    }

    public static List<Map<String, Object>> GetIVNVersion_Listing() throws SQLException {
        System.out.println("GetIVNVersion_Listing");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
            //        String sql = "SELECT pdb.id as id, CAST(pdb.pdb_versionname as CHAR(100)) as pdb_version, \n" +
            //                    "GROUP_CONCAT(DISTINCT(vv.id)) as vehicleversion_id,\n" +
            //                    "GROUP_CONCAT(DISTINCT(vv.versionname)) as veh_version,\n" +
            //                    "GROUP_CONCAT(DISTINCT(v.vehiclename)) as vehicle,\n" +
            //                    "GROUP_CONCAT(DISTINCT(vm.modelname)) as model,pdb.status as status,pdb.flag FROM pdbversion as pdb \n" +
            //                    "INNER JOIN pdbversion_group as pg ON pg.pdbversion_id=pdb.id \n" +
            //                    "INNER JOIN vehicle_and_model_mapping as vmm ON vmm.id=pg.vehicle_and_model_mapping_id \n" +
            //                    "INNER JOIN vehicle as v ON v.id=vmm.vehicle_id \n" +
            //                    "INNER JOIN vehiclemodel as vm ON vm.id=vmm.model_id \n" +
            //                    "INNER JOIN vehicleversion as vv ON vv.id=vmm.vehicleversion_id group by pg.pdbversion_id order by pdb.id desc";
            String sql = "SELECT ivn.id as id, CAST(ivn.ivn_versionname as CHAR(100)) as ivn_version, \n"
                    + "GROUP_CONCAT(DISTINCT(vv.id)) as vehicleversion_id,\n"
                    + "GROUP_CONCAT(DISTINCT(vv.versionname)) as veh_version,\n"
                    + "GROUP_CONCAT(DISTINCT(v.vehiclename)) as vehicle,\n"
                    + "GROUP_CONCAT(DISTINCT(vm.modelname)) as model,ivn.status as status,ivn.flag FROM ivnversion as ivn \n"
                    + "INNER JOIN ivn_canmodels as cn ON cn.ivnversion_id=ivn.id \n"
                    + "INNER JOIN vehicle_and_model_mapping as vmm ON vmm.id=cn.vehicle_and_model_mapping_id \n"
                    + "INNER JOIN vehicle as v ON v.id=vmm.vehicle_id \n"
                    + "INNER JOIN vehiclemodel as vm ON vm.id=vmm.model_id \n"
                    + "INNER JOIN vehicleversion as vv ON vv.id=vmm.vehicleversion_id group by cn.ivnversion_id order by ivn.id desc";
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

    public static List<Map<String, Object>> GetSignal_Listing(Signal s) throws SQLException {
        System.out.println("GetSignal_Listing");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
            String sql = "select s.id as sid, s.signal_name as listitem,s.signal_alias as salias,s.signal_description as description,\n"
                    + "GROUP_CONCAT(DISTINCT(cn.network_name)) as can,\n"
                    + "GROUP_CONCAT(DISTINCT(ln.network_name)) as lin,\n"
                    + "GROUP_CONCAT(DISTINCT(hw.network_name)) as hardware from signals as s \n"
                    + "left join network as cn on FIND_IN_SET(cn.id,s.can_id_group) > 0 \n"
                    + "left join network as ln on FIND_IN_SET(ln.id,s.lin_id_group) > 0 \n"
                    + "left join network as hw on FIND_IN_SET(hw.id,s.hw_id_group) > 0 \n"
                    + "group by s.id order by s.id DESC";
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

    public static Map<String, Object> GetIVN_Dashboarddata() throws SQLException {
        System.out.println("GetIVN_Dashboarddata");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        connection = ConnectionConfiguration.getConnection();
        Statement statement = connection.createStatement();
        Map<String, Object> columns = new HashMap<String, Object>();

        //Get ACB version count
        String ivnver_sql = "select * from ivnversion";
        ResultSet ivnver_rs = statement.executeQuery(ivnver_sql);
        ivnver_rs.last();
        System.out.println("resultset_count" + ivnver_rs.getRow());
        columns.put("ivnversion_count", ivnver_rs.getRow());

        //Get IVN version count
        String nt_sql = "select * from network";
        ResultSet nt_rs = statement.executeQuery(nt_sql);
        nt_rs.last();
        System.out.println("resultset_count" + nt_rs.getRow());
        columns.put("network_count", nt_rs.getRow());

        //Get Signal version count
        String sig_sql = "select * from signals";
        ResultSet sig_rs = statement.executeQuery(sig_sql);
        sig_rs.last();
        System.out.println("resultset_count" + sig_rs.getRow());
        columns.put("signal_count", sig_rs.getRow());

        return columns;
    }

    public static void deleteIVN_network_models(int ivnversion_id, String network_type) throws SQLException {
        System.out.println("globaldatastore" + StringUtils.join(GlobalDataStore.globalData, ','));
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        connection = ConnectionConfiguration.getConnection();
        preparedStatement = connection.prepareStatement("delete from ivn_" + network_type + "models where ivnversion_id=" + ivnversion_id + " AND id NOT IN (" + StringUtils.join(GlobalDataStore.globalData, ',') + ")");
        preparedStatement.executeUpdate();

        GlobalDataStore.globalData.clear();
    }

    public static float getIVNVersionNameFromId(int id) {
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();

            String fetch_ivnversionname = "SELECT ivn_versionname FROM ivnversion WHERE id = " + id;
            resultSet = statement.executeQuery(fetch_ivnversionname);
            resultSet.last();
            if (resultSet.getRow() != 0) {
                return resultSet.getFloat(1);
            }
        } catch (Exception e) {
            System.out.println("Error on Fetching IVN Version Name " + e.getMessage());
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

    public static int getIdFromIVNVersionName(float versionName) {
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();

            String fetch_ivnversionname = "SELECT id FROM ivnversion WHERE ivn_versionname = " + versionName;
            resultSet = statement.executeQuery(fetch_ivnversionname);
            resultSet.last();
            if (resultSet.getRow() != 0) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error on Fetching IVN Version Name Id" + e.getMessage());
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

    public static int getIdTypeFromNetworkName(String networkName, String networkType) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();

            String fetch_IdType = "SELECT id FROM network WHERE network_name = '" + networkName + "' AND network_type='" + networkType + "'";
            resultSet = statement.executeQuery(fetch_IdType);
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                preparedStatement = connection.prepareStatement("INSERT INTO network (network_name,network_description,network_type,created_date,created_or_updated_by)"
                        + "VALUES (?, ?, ?, ?, ?)", preparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, networkName);
                preparedStatement.setString(2, "");
                preparedStatement.setString(3, networkType);
                preparedStatement.setString(4, dtf.format(now));
                preparedStatement.setInt(5, CookieRead.getUserIdFromSession());
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();

                if (rs.next()) {
                    int last_inserted_id = rs.getInt(1);
                    return last_inserted_id;
                }
            }
        } catch (Exception e) {
            System.out.println("Error on Fetching ID & Type for Network" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
//                    return 0;
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
        return 0;
    }

    public static Object[] getIdTypeFromNetworkName(String networkName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();

            String fetch_IdType = "SELECT id,network_type FROM network WHERE network_name = '" + networkName + "'";
            resultSet = statement.executeQuery(fetch_IdType);
            resultSet.last();
            if (resultSet.getRow() != 0) {
                return new Object[]{resultSet.getInt(1), resultSet.getString(2)};
            }
        } catch (Exception e) {
            System.out.println("Error on Fetching ID & Type for Network" + e.getMessage());
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

    public static int getIdFromSignalName(String signalName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();

            String fetch_signalid = "SELECT id FROM signals WHERE signal_name = '" + signalName + "'";
            resultSet = statement.executeQuery(fetch_signalid);
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                preparedStatement = connection.prepareStatement("INSERT INTO signals (signal_name,created_or_updated_by)"
                        + "VALUES (?,?)", preparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, signalName);
                preparedStatement.setInt(2, CookieRead.getUserIdFromSession());
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();

                if (rs.next()) {
                    int last_inserted_id = rs.getInt(1);
                    System.out.println("Gen ID "+last_inserted_id);
                    return last_inserted_id;
                }
            }
        } catch (Exception e) {
            System.out.println("Error on Fetching Signal Id" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
//                    return 0;
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
        return 0;
    }
}

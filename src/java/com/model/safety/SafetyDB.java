/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.safety;

import com.model.safety.*;
import com.db_connection.ConnectionConfiguration;
import com.model.ivn_supervisor.Vehicleversion;
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
public class SafetyDB {
    public static List<Map<String, Object>> LoadPreviousSafetyCombinationData(SafetyCombination lc) throws SQLException {
        System.out.println("LoadPreviousSafetyCombinationData");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            //        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
            String sql = "SELECT querybuilder_name, querybuilder_type, querybuilder_condition,querybuilder_status"
                    + " FROM querybuilder AS qb where qb.id=" + lc.getId();
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
    public static Object[] insertSafetyCombination(SafetyCombination lc) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        float versionname = 0;
        try {
            connection = ConnectionConfiguration.getConnection();

            Statement statement = connection.createStatement();
            System.out.println("status_value" + lc.getStatus());
            if (lc.getOperation_status().equals("create")) {
                preparedStatement = connection.prepareStatement("INSERT INTO querybuilder (querybuilder_name,querybuilder_type,querybuilder_condition,querybuilder_status,created_date,created_or_updated_by)"
                        + "VALUES (?, ?, ?, ?, ?, ?)", preparedStatement.RETURN_GENERATED_KEYS);
                //            preparedStatement.setString(1, v.getVersionname());
                preparedStatement.setString(1, lc.getQuerybuilder_name());
                preparedStatement.setString(2, lc.getQuerybuilder_type());
                preparedStatement.setString(3, lc.getQuerybuilder_condition());
                preparedStatement.setBoolean(4, lc.getStatus());
                preparedStatement.setString(5, lc.getCreated_date());
                preparedStatement.setInt(6, lc.getCreated_or_updated_by());
                
                preparedStatement.executeUpdate();

                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    int last_inserted_id = rs.getInt(1);
                    return new Object[]{last_inserted_id};
                }
            } else {
                System.out.println("update start");
                String sql = "UPDATE querybuilder SET "
                        + "querybuilder_name = ?, querybuilder_type = ?, querybuilder_condition = ?, "
                        + "querybuilder_status = ?, created_or_updated_by = ? WHERE id = ?";
                preparedStatement = connection.prepareStatement(sql);
                System.out.println("update1");
                preparedStatement.setString(1, lc.getQuerybuilder_name());
                System.out.println("update2");
                preparedStatement.setString(2, lc.getQuerybuilder_type());
                System.out.println("update3");
                preparedStatement.setString(3, lc.getQuerybuilder_condition());
                System.out.println("update4");
                preparedStatement.setBoolean(4, lc.getStatus());
                System.out.println("update5");
                preparedStatement.setInt(5, lc.getCreated_or_updated_by());
                System.out.println("update6");
                preparedStatement.setInt(6, lc.getId());
                System.out.println("update7");
                preparedStatement.executeUpdate();
                if(preparedStatement.executeUpdate()>0)
                {
                      System.out.println("success");
                }
                else
                {
                     System.out.println("stuck somewhere");

                }
                System.out.println("update8");
                return new Object[]{lc.getId()};
            }
        } catch (Exception e) {
            System.out.println("Safety Combination error message" + e.getMessage());
            e.printStackTrace();
            return new Object[]{0};

        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return new Object[]{0};
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return new Object[]{0};
                }
            }
        }
        return new Object[]{0};
    }
    public static List<Map<String, Object>> GetSafetyCombinationListing(SafetyCombination lc) throws SQLException 
    {
        System.out.println("GetSafetyCombinationListing");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
            String sql = "select id as safety_id,querybuilder_name as safety,querybuilder_status as status,querybuilder_condition as combination,created_date,modified_date from querybuilder where querybuilder_type='safety' order by id desc";
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
            System.out.println("Safety combination Listing error message"+e.getMessage()); 
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
}

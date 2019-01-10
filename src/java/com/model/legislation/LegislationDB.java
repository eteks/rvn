/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.legislation;

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
public class LegislationDB {
    public static List<Map<String, Object>> LoadPreviousLegislationCombinationData(LegislationCombination lc) throws SQLException {
        System.out.println("LoadPreviousLegislationCombinationData");
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
    public static Object[] insertLegislationCombination(LegislationCombination lc) {
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
                String sql = "UPDATE querybuilder SET "
                        + "querybuilder_name = ?, querybuilder_type = ?, querybuilder_condition = ?, "
                        + "querybuilder_status = ?, created_or_updated_by = ? WHERE id = ?";
                preparedStatement.setString(1, lc.getQuerybuilder_name());
                preparedStatement.setString(2, lc.getQuerybuilder_type());
                preparedStatement.setString(3, lc.getQuerybuilder_condition());
                preparedStatement.setBoolean(4, lc.getStatus());
                preparedStatement.setInt(5, lc.getCreated_or_updated_by());
                preparedStatement.executeUpdate();
                return new Object[]{lc.getId()};
            }
        } catch (Exception e) {
            System.out.println("Legislation Combination error message" + e.getMessage());
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
    public static List<Map<String, Object>> GetLegislationCombinationListing(LegislationCombination lc) throws SQLException 
    {
        System.out.println("GetLegislationCombinationListing");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
            String sql = "select id as leg_id,querybuilder_name as leg,querybuilder_status as status,querybuilder_condition as combination from querybuilder order by id desc";
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
            System.out.println("Legislation combination Listing error message"+e.getMessage()); 
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

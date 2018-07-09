/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import com.db_connection.ConnectionConfiguration;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ets-2
 */
public class PDBVersionDB {
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
}

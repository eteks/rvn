/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.notification;

import com.db_connection.ConnectionConfiguration;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author ETS-4
 */
public class StatusNotificationDB {
    
    public static void insertStatus(StatusNotification statusNotification){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
            String insertStatusNotificationQuery = "INSERT INTO status_notification (receiver_id, notification_id) "+
                    "VALUES (?, ?)";
            connection = ConnectionConfiguration.getConnection();
            
            preparedStatement = connection.prepareStatement(insertStatusNotificationQuery);
            preparedStatement.setInt(1, statusNotification.getReceiver_id());
            preparedStatement.setInt(2, statusNotification.getNotification_id());
            preparedStatement.executeUpdate();
            
            /*ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int last_inserted_id = rs.getInt(1);
                return last_inserted_id;
            }*/
        }catch (Exception e) {
            System.out.println("Status Notification creation error message"+e.getMessage()); 
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
    }
}

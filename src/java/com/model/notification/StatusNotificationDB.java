/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.notification;

import com.db_connection.ConnectionConfiguration;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ETS-4
 */
public class StatusNotificationDB {

    public static void insertStatus(StatusNotification statusNotification) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        try {
            connection = ConnectionConfiguration.getConnection();
            statement = connection.createStatement();
            String getStatusDetails = "SELECT * FROM status_notification WHERE receiver_id=" + statusNotification.getReceiver_id() + " AND notification_id=" + statusNotification.getNotification_id();
            ResultSet resultSet = statement.executeQuery(getStatusDetails);
            if (!resultSet.next()) {
                String insertStatusNotificationQuery = "INSERT INTO status_notification (receiver_id, notification_id) "
                        + "VALUES (?, ?)";

                preparedStatement = connection.prepareStatement(insertStatusNotificationQuery);
                preparedStatement.setInt(1, statusNotification.getReceiver_id());
                preparedStatement.setInt(2, statusNotification.getNotification_id());
                preparedStatement.executeUpdate();
            }
            /*ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int last_inserted_id = rs.getInt(1);
                return last_inserted_id;
            }*/
        } catch (Exception e) {
            System.out.println("Status Notification creation error message" + e.getMessage());
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

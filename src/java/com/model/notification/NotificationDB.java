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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationDB {

    public static int insertNotification(Notification notification) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            String insertNotificationQuery = "INSERT INTO notification (sender_id, receiver_id, version_type_id, version_id, created_date)"
                    + "VALUES (?, ?, ?, ?, ?)";
            connection = ConnectionConfiguration.getConnection();

            preparedStatement = connection.prepareStatement(insertNotificationQuery, preparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, notification.getSender_id());
            preparedStatement.setString(2, notification.getReceiver_id());
            preparedStatement.setInt(3, notification.getVersion_type_id());
            preparedStatement.setFloat(4, notification.getVersion_id());
            preparedStatement.setString(5, notification.getCreated_date());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int last_inserted_id = rs.getInt(1);
                return last_inserted_id;
            }
        } catch (Exception e) {
            System.out.println("Notification creation error message" + e.getMessage());
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

    public static int getGroupIdForUser(int userId) {
        Connection connection = null;
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();
            String getUserDetails = "SELECT group_id FROM users WHERE id=" + userId;
            ResultSet resultSet = statement.executeQuery(getUserDetails);
            if (resultSet.getRow() != 0) {
                return resultSet.getInt("group_id");
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
    
    public static int getVersionId(VersionType versionType, float versionNumber) {
        Connection connection = null;
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();
            String findColumnNameQuery = "SELECT column_name FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema = 'global_ivn' AND table_name='"+versionType.toString().toLowerCase()+"' AND column_name LIKE '%versionname'";
            String getUserDetails = "SELECT id FROM "+versionType.toString().toLowerCase();
            ResultSet resultSet = statement.executeQuery(findColumnNameQuery);
            if (resultSet.getRow() != 0) {
                 getUserDetails += " WHERE "+resultSet.getString(1)+"="+versionNumber+".0";
            }
            resultSet = statement.executeQuery(getUserDetails);
            if(resultSet.getRow() != 0)
                return resultSet.getInt(1);
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

    public static List<Map<String, Object>> getUnreadNotification(int user_id, int group_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();

        try {
            String unreadNotificationQuery = "SELECT u.firstname,n.version_type_id,n.version_id,n.created_date "
                    + "FROM notification n INNER JOIN users u ON u.id = n.sender_id WHERE "
                    + "n.id NOT IN (SELECT notification_id FROM status_notification WHERE receiver_id = ?) AND "
                    + "sender_id <> ? AND receiver_id LIKE '%" + 1 + "%'"
                    + "ORDER BY created_date DESC";
            connection = ConnectionConfiguration.getConnection();

            preparedStatement = connection.prepareStatement(unreadNotificationQuery);
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, user_id);
            ResultSet rs = preparedStatement.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int colCount = metaData.getColumnCount();
            VersionType versionType = null;
            float versionNumber = 0;
            while (rs.next()) {
                Map<String, Object> columns = new HashMap<>();
                for (int i = 1; i <= colCount+1; i++) {
                    if (i == 2) {
                        versionType = VersionType.fromId(Integer.parseInt(rs.getObject(i).toString()));
                        columns.put(metaData.getColumnLabel(i), versionType);
                    } else if (i == 3) {
                        versionNumber = (float) rs.getObject(i);
                    }else if(i == colCount+1){
                        columns.put("id", NotificationDB.getVersionId(versionType, versionNumber));
                    } else {
                        columns.put(metaData.getColumnLabel(i), rs.getObject(i));
                    }
                    row.add(columns);
                }
            }
        } catch (Exception e) {
            System.out.println("Notification Unread Fetch error message" + e.getMessage());
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

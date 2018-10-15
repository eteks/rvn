/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.notification;

import com.controller.common.VersionType;
import com.controller.common.VersionViewPage;
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
            if (resultSet.next()) {
                return resultSet.getInt(1);
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
        Statement statement = null;
        try {
            connection = ConnectionConfiguration.getConnection();
            statement = connection.createStatement();
            String findColumnNameQuery = "SELECT column_name FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema = 'global_ivn' AND table_name='" + versionType.toString().toLowerCase() + "' AND column_name LIKE '%versionname'";
            String getUserDetails = "SELECT id FROM " + versionType.toString().toLowerCase();
            ResultSet resultSet = statement.executeQuery(findColumnNameQuery);
            if (resultSet.next()) {
                getUserDetails += " WHERE " + resultSet.getString("column_name") + "=" + versionNumber;
            }
            resultSet = null;
            resultSet = statement.executeQuery(getUserDetails);
            if (resultSet.next()) {
                return resultSet.getInt(1);
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

    public static List<Map<String, Object>> getUnreadNotification(int user_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<>();
        int group_id = NotificationDB.getGroupIdForUser(user_id);
        try {
            String unreadNotificationQuery = "SELECT n.id,u.firstname,n.version_type_id,n.version_id,n.created_date "
                    + "FROM notification n INNER JOIN users u ON u.id = n.sender_id WHERE "
                    + "n.id NOT IN (SELECT notification_id FROM status_notification WHERE receiver_id = ?) AND "
                    + "sender_id <> ? AND receiver_id LIKE '%" + group_id + "%'"
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
                for (int i = 1; i <= colCount; i++) {
                    if (i == 3) {
                        versionType = VersionType.fromId(Integer.parseInt(rs.getObject(i).toString()));
                        columns.put(metaData.getColumnLabel(i), versionType);
                    } else {
                        columns.put(metaData.getColumnLabel(i), rs.getObject(i));
                    }
                }
                row.add(columns);
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

    public static List<Map<String, Object>> readNotification(int notification_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();
            String getUserDetails = "SELECT version_type_id,version_id FROM notification WHERE id=" + notification_id;
            ResultSet resultSet = statement.executeQuery(getUserDetails);
            if (resultSet.next()) {
                Map<String, Object> columns = new HashMap<>();
                int version_type_id = resultSet.getInt(1);
                columns.put("version_type", VersionViewPage.fromId(version_type_id));
                columns.put("version_id", NotificationDB.getVersionId(VersionType.fromId(version_type_id), resultSet.getFloat(2)));
                row.add(columns);
            }
            return row;
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

        return null;
    }
}

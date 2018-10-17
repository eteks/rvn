/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.admin;

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
import org.json.simple.JSONArray;

/**
 *
 * @author ETS-4
 */
public class UserDB {

    public static Object[] checkEmployeeIdMailExists(String employeeId, String email) {
        boolean emp_id_status = false, email_id_status = false;
        Connection connection = null;
        ResultSet rs;
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();

            String checkEmp_id_query = "SELECT * FROM users WHERE employee_id='" + employeeId + "'";
            String checkEmail_id_query = "SELECT * FROM users WHERE email = '" + email + "'";
            //System.out.println("Query " + checkEmp_id_query);
            rs = statement.executeQuery(checkEmp_id_query);
            if (rs.next()) {
                emp_id_status = true;
            }

            rs = statement.executeQuery(checkEmail_id_query);
            if (rs.next()) {
                email_id_status = true;
            }
            return new Object[]{emp_id_status, email_id_status};
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }
    
    public static Object[] getEmployeeIdMail(int id) {
        String employeeId = null,mail = null;
        Connection connection = null;
        ResultSet rs;
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();

            String checkEmp_id_query = "SELECT employee_id,email FROM users WHERE id=" + id;
            //System.out.println("Query " + checkEmp_id_query);
            rs = statement.executeQuery(checkEmp_id_query);
            if (rs.next()) {
                employeeId = rs.getString(1);
                mail = rs.getString(2);
            }
            return new Object[]{employeeId, mail};
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return new Object[]{employeeId, mail};
                }
            }
        }
    }

    public static JSONArray getUserCountbyGroup() {
        JSONArray usersList = new JSONArray();
        Connection connection = null;
        ResultSet rs;
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();

            String fetch_userscount_query = "SELECT g.group_name, COUNT(u.group_id) FROM groups g LEFT OUTER JOIN users u ON u.group_id = g.id GROUP BY g.group_name";
            rs = statement.executeQuery(fetch_userscount_query);
            while (rs.next()) {
                JSONArray list = new JSONArray();
                list.add(rs.getString(1));
                list.add(rs.getInt(2));
                usersList.add(list);
            }
            return usersList;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean createUser(Users user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionConfiguration.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO users (username,employee_id,firstname,lastname,password,email,supervisor_email,mobile_number,group_id,status)"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            //            preparedStatement.setString(1, v.getVersionname());
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmployee_id());
            preparedStatement.setString(3, user.getFirstname());
            preparedStatement.setString(4, user.getLastname());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.setString(7, user.getSupervisor_email());
            preparedStatement.setDouble(8, user.getMobile_number());
            preparedStatement.setInt(9, user.getGroup_id());
            preparedStatement.setBoolean(10, user.isStatus());

            int stat = preparedStatement.executeUpdate();

            if (stat > 0) {
                return true;
            }

        } catch (Exception e) {
            System.out.println("Create User error message" + e.getMessage());
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
        return false;
    }

    public static List<FetchUser> getUserList() {
        List<FetchUser> userList = new ArrayList<>();
        Connection connection = null;
        ResultSet rs;
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();

            String fetchusers_query = "SELECT u.employee_id,u.firstname,u.email,u.mobile_number,g.group_name,u.status,u.id FROM users u "
                    + "INNER JOIN groups g ON g.id = u.group_id";
            rs = statement.executeQuery(fetchusers_query);
            while (rs.next()) {
                FetchUser user = new FetchUser();
                user.setEmployee_id(rs.getString(1));
                user.setFirstname(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setMobile_number(rs.getDouble(4));
                user.setGroup_name(rs.getString(5));
                user.setStatus(rs.getBoolean(6));
                user.setId(rs.getInt(7));
                userList.add(user);
            }
            return userList;
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

    public static List<Map<String, Object>> getUserDetails(String id) {
        List<Map<String, Object>> row = new ArrayList<>();
        Connection connection = null;
        ResultSet rs;
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement statement = connection.createStatement();

            String fetchusersdetails_query = "SELECT id,username,employee_id,firstname,lastname,password,email,supervisor_email,mobile_number,group_id,status FROM users WHERE id='" + id + "'";
            rs = statement.executeQuery(fetchusersdetails_query);
            ResultSetMetaData metaData = rs.getMetaData();
            int colCount = metaData.getColumnCount();
            while (rs.next()) {
                Map<String, Object> columns = new HashMap<>();
                for (int i = 1; i <= colCount; i++) {
                    columns.put(metaData.getColumnLabel(i), rs.getObject(i));
                }
                row.add(columns);
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
        return row;
    }

    public static boolean updateDetails(Users user,int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            String update_user_query = "UPDATE users SET "
                    +"username = ?, employee_id = ?, firstname = ?, lastname=?, password = ?, email  = ?, supervisor_email = ?,  mobile_number = ?, group_id = ?, status = ?  WHERE id = ?";
            connection = ConnectionConfiguration.getConnection();
            preparedStatement = connection.prepareStatement(update_user_query);
            //            preparedStatement.setString(1, v.getVersionname());
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmployee_id());
            preparedStatement.setString(3, user.getFirstname());
            preparedStatement.setString(4, user.getLastname());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.setString(7, user.getSupervisor_email());
            preparedStatement.setDouble(8, user.getMobile_number());
            preparedStatement.setInt(9, user.getGroup_id());
            preparedStatement.setBoolean(10, user.isStatus());
            preparedStatement.setInt(11, id);

            int stat = preparedStatement.executeUpdate();

            if (stat > 0) {
                return true;
            }

        } catch (Exception e) {
            System.out.println("Update User error message" + e.getMessage());
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
        return false;
    }
}

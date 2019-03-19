/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.db_connection;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author ets-2
 */
public class ConnectionConfiguration 
{	
    public static final String URL = "jdbc:mysql://localhost:3306/global_ivn";
    /**
     * In my case username is "root" *
     */
    public static final String USERNAME = "root";
    /**
     * In my case password is "" *
     */
    public static final String PASSWORD = "root";
 
    public static Connection getConnection() {
        Connection connection = null;
 
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
 
        return connection;
    }
}

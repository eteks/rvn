/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import com.db_connection.ConnectionConfiguration;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ETS-4
 */
public class AdminDb {

    static Connection connection = null;
    static PreparedStatement preparedStatement = null;
    static Statement st;
    static ResultSet rs;

    public static int addGroupPermission(GroupPermision groupPermission) {
        System.out.println("group_id:" + groupPermission.getGroup_id() + " module_id:" + groupPermission.getModule_id() + " op_id:" + groupPermission.getModulepermisionid());
        connection = ConnectionConfiguration.getConnection();
        try {
            String sql = "insert into group_permissions(group_id,module_id,modulepermission_id) values(?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, groupPermission.getGroup_id());
            preparedStatement.setInt(2, groupPermission.getModule_id());
            preparedStatement.setInt(3, groupPermission.getModulepermisionid());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                return 1;
            } else {
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }

    public static int addModulePermission(ModulePermision mp) {
        connection = ConnectionConfiguration.getConnection();
        try {
            String sql = "insert into module_permission(module_id,operation_id) values(?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, mp.getModuleid());
            preparedStatement.setInt(2, mp.getOperationid());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                return 1;
            } else {
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }

    public static boolean CheckGroup_name(CreateUserGroup userGroup) {
        connection = ConnectionConfiguration.getConnection();
        boolean result = false;
        try {
            st = connection.createStatement();
            String sql = "select * from groups";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                String user_Group = rs.getString("group_name");
                if (userGroup.getGroupname().equals(user_Group)) {
                    result = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    public static List<Map<String, Object>> getAllUserGroup() throws SQLException 
    {
//        connection = ConnectionConfiguration.getConnection();
//        List<CreateUserGroup> list = new ArrayList<>();
//        try {
//            connection = ConnectionConfiguration.getConnection();
//            st = connection.createStatement();
//            String sql = "select * from groups";
//            rs = st.executeQuery(sql);
//            CreateUserGroup createUserGroup = null;
//            while (rs.next()) {
//                createUserGroup = new CreateUserGroup();
//                createUserGroup.setId(rs.getInt("id"));
//                createUserGroup.setGroupname(rs.getString("group_name"));
//                createUserGroup.setStatus(rs.getInt("status"));
//                list.add(createUserGroup);
//
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//        return list;
//        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        System.out.println("getAllUserGroup");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            connection = ConnectionConfiguration.getConnection();
            //Check whether model name already exists in db or not
            Statement statement = connection.createStatement();
            String sql = "select * from groups";
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
            System.out.println("acb version error message"+e.getMessage()); 
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

    public static boolean addUserGroup(CreateUserGroup userGroup) {
        connection = ConnectionConfiguration.getConnection();
        boolean type = false;
        try {
            String createUser = "insert into groups (group_name,status) values(?,?)";
            preparedStatement = connection.prepareStatement(createUser);
            preparedStatement.setString(1, userGroup.getGroupname());
            preparedStatement.setInt(2, 0);
            int stat = preparedStatement.executeUpdate();
            if (stat > 0) {
                type = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return type;
    }

    public static boolean addModule(ModuleModel userModule) {
        connection = ConnectionConfiguration.getConnection();
        boolean type=false;
        try {
            String createUser = "insert into module (modulename,icon_code,route_pages) values(?,?,?)";
            preparedStatement = connection.prepareStatement(createUser);
            preparedStatement.setString(1, userModule.getModuleName());
            preparedStatement.setString(2, userModule.getIconCode());
            preparedStatement.setString(3, userModule.getModuleName());
            int status=preparedStatement.executeUpdate();
            if(status>0)
            {
                type=true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return type;
    }
    public static List<ModuleModel> getAllModule() {
        List<ModuleModel> list=new ArrayList<>();
        try
        {
        connection=ConnectionConfiguration.getConnection();
        st=connection.createStatement();
        String sql="select * from module";
        rs=st.executeQuery(sql);
        ModuleModel moduleModel=null;
        while(rs.next())
        {
            moduleModel=new ModuleModel();
            moduleModel.setId(rs.getInt("id"));
            moduleModel.setModuleName(rs.getString("modulename"));
            moduleModel.setIconCode(rs.getString("icon_code"));
            moduleModel.setRoutePage(rs.getString("route_pages"));
            moduleModel.setStatus(rs.getInt("status"));
            list.add(moduleModel);
        }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public static List<ModuleModel> getAllModuleName() {
        List<ModuleModel> list=new ArrayList<>();
        try
        {
        connection=ConnectionConfiguration.getConnection();
        st=connection.createStatement();
        String sql="select * from module";
        rs=st.executeQuery(sql);
        ModuleModel module=null;
        while(rs.next())
        {
            module=new ModuleModel();
            module.setModuleName(rs.getString("modulename"));
            module.setId(rs.getInt("id"));
            list.add(module);
        }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public static boolean checkModuleId_On_ModulePermission(int m_id) {
       connection = ConnectionConfiguration.getConnection();
        boolean result = false;
        try {
            st = connection.createStatement();
            String sql = "select * from module_permission";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                int module_id = rs.getInt("module_id");
                if (module_id==m_id) {
                    result = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<CreateUserGroup> getAllGroupName() {
    List<CreateUserGroup> list=new ArrayList<>();
        try
        {
        connection=ConnectionConfiguration.getConnection();
        st=connection.createStatement();
        String sql="select * from groups";
        rs=st.executeQuery(sql);
        CreateUserGroup group=null;
        while(rs.next())
        {
            group=new CreateUserGroup();
            group.setGroupname(rs.getString("group_name"));
            group.setId(rs.getInt("id"));
            list.add(group);
        }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public static List<ModulePermision> getPermisionByModuleId(String moduleId) {
        System.out.println("check--- dao impl");
        List<ModulePermision> list=new ArrayList<>();
        try
        {
        connection=ConnectionConfiguration.getConnection();
        st=connection.createStatement();
        String sql="select * from module_permission where module_id='"+moduleId+"'";
        rs=st.executeQuery(sql);
        ModulePermision modulePermision=null;
        while(rs.next())
        {
            modulePermision=new ModulePermision();
            modulePermision.setModuleid(rs.getInt("module_id"));
            modulePermision.setOperationid(rs.getInt("operation_id"));
            list.add(modulePermision);
        }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return list;
    }

}

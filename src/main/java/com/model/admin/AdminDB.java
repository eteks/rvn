package com.model.admin;

import com.model.pojo.user.Groups;
import com.model.pojo.user.Users;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.javalite.activejdbc.Base;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ets-2
 */
public class AdminDB {

    public static Map<String, Object> GetAdmin_Dashboarddata() throws SQLException {
        System.out.println("GetAdmin_Dashboarddata");
        Map<String, Object> columns = new HashMap<>();
        
        Base.open();
        //Get User Groups count
        long groupCount = Groups.count();
        System.out.println("resultset_count" + groupCount);
        columns.put("groups_count", groupCount);

        //Get Users count
        long userCount = Users.count();
        System.out.println("resultset_count" + userCount);
        columns.put("users_count", userCount);
        Base.close();
        
        return columns;
    }
}

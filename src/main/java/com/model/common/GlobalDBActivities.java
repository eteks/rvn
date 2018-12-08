/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.common;

import com.db_connection.ConnectionConfiguration;
import com.model.pojo.ivn_version.EngineControlUnit;
import com.model.pojo.user.Groups;
import com.model.pojo.user.Users;
import com.model.pojo.vehicle_modal.Vehicle;
import com.model.pojo.vehicle_modal.VehicleModel;
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
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

/**
 *
 * @author ets-2
 */
public class GlobalDBActivities {

    public static Map<String, Long> GetModuleCount() throws SQLException {
        System.out.println("GetModuleCount");
        Map<String, Long> columns = new HashMap<>();
        Base.open();
        try {
            //Get Vehicle count
            long userCount = Users.count();
            System.out.println("resultset_count" + userCount);
            columns.put("usercount", userCount);

            //Get Vehicle count
            long vehicleCount = Vehicle.count();
            System.out.println("resultset_count" + vehicleCount);
            columns.put("vehiclecount", vehicleCount);

            //Get Model count
            long vehicleModelCount = VehicleModel.count();
            System.out.println("resultset_count" + vehicleModelCount);
            columns.put("modelcount", vehicleModelCount);

            //Get ECU count
            long ecuCount = EngineControlUnit.count();
            System.out.println("ecucount" + ecuCount);
            columns.put("ecucount", ecuCount);
        } catch (Exception e) {
            System.out.println("vehicle version error message" + e.getMessage());
            e.printStackTrace();
        } finally {
            Base.close();
        }
        return columns;
    }

    public static List<Map<String, Object>> GetUserGroups() throws SQLException {
        System.out.println("GetVehicleVersion_Listing");
        List<Map<String, Object>> row = new ArrayList<>();
        Base.open();
        try {
            String sql = "select id,group_name,status,route_pages,is_superadmin from groups";
            LazyList<Groups> groupList = Groups.findBySQL(sql);
            row = groupList.toMaps();
        } catch (Exception e) {
            System.out.println("vehicle version error message" + e.getMessage());
            e.printStackTrace();

        } finally {
            Base.close();
        }
        return row;
    }
}

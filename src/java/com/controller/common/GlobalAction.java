/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.common;

import com.google.gson.Gson;
import com.model.common.GlobalDBActivities;
import com.model.ivn_supervisor.VehicleversionDB;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ets-2
 */
public class GlobalAction {       
        public String count;
//        public String group_result_obj;
        private List<Map<String, Object>> group_result = new ArrayList<Map<String, Object>>();
        private Map<String, Integer> count_result = new HashMap<String, Integer>();
        public String GetDashboardData() throws SQLException { 
            System.out.println("GetDashboardData");
            count_result = GlobalDBActivities.GetModuleCount();
//            count = new Gson().toJson(count_result);
//            System.out.println("count"+count);
            //Get user groupss data
            group_result = GlobalDBActivities.GetUserGroups();
//            group_result_obj = new Gson().toJson(group_result);
            System.out.println("group_result"+group_result);
            return "success";
        }
//        public String getCount() {
//            return count;
//        }
//        public void setCount(String count) {
//            this.count = count;
//        }
//        public String getGroup_result_obj() {
//            return group_result_obj;
//        }
//        public void setGroup_result_obj(String group_result_obj) {
//            this.group_result_obj = group_result_obj;
//        }    
        public List<Map<String, Object>> getGroup_result() {
            return group_result;
        }
        public void setGroup_result(List<Map<String, Object>> group_result) {
            this.group_result = group_result;
        }
        public Map<String, Integer> getCount_result() {
            return count_result;
        }
        public void setCount_result(Map<String, Integer> count_result) {
            this.count_result = count_result;
        }
}

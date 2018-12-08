/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.admin;

import com.model.pojo.user.EmailVerify;
import com.model.pojo.user.Groups;
import com.model.pojo.user.Users;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.json.simple.JSONArray;

/**
 *
 * @author ETS-4
 */
public class UserDB {

    public static Object[] checkEmployeeIdMailExists(String employeeId, String email) {
        boolean emp_id_status = false, email_id_status = false;

        Base.open();
        long emp_id_count = Users.count("employee_id = ?", employeeId);
        long email_id_count = Users.count("email = ?", email);

        if (emp_id_count != 0) {
            emp_id_status = true;
        }
        if (email_id_count != 0) {
            email_id_status = true;
        }
        Base.close();
        return new Object[]{emp_id_status, email_id_status};
    }

    public static Object[] getEmployeeIdMail(int id) {
        Base.open();
        Users user = Users.findById(id);
        Base.close();

        return new Object[]{user.getString("employee_id"), user.getString("email")};
    }

    public static String getUserNamebyID(int id) {
        Base.open();
        Users user = Users.findById(id);
        Base.close();

        return user.getString("firstname");
    }

    public static JSONArray getUserCountbyGroup() {
        JSONArray usersList = new JSONArray();

        Base.open();
        LazyList<Groups> groupList = Groups.findAll();
        for (Groups group : groupList) {
            JSONArray list = new JSONArray();
            list.add(group.getString("group_name"));
            list.add(group.getAll(Users.class).size());
            usersList.add(list);
        }
        Base.close();
        return usersList;
    }

    public static int createUser(Users user) {
        Base.open();
        user.saveIt();
        int generated_id = (int) user.getId();
        Base.close();
        System.out.println("Generated IDDD " + generated_id);
        return generated_id;
    }

    public static boolean insertVerificationId(int userId, String verificationId) {
        Base.open();
        EmailVerify emailVerify = new EmailVerify();
        emailVerify.set("user_id", userId);
        emailVerify.set("verification_id", verificationId);
        try {
            return emailVerify.saveIt();
        } finally {
            Base.close();
        }
    }

    public static boolean checkVerificationId(int userId, String verificationId) {
        Base.open();
        long existCount = EmailVerify.count("user_id = ? AND verification_id = ? ", userId, verificationId);
        Base.close();

        return existCount != 0;
    }

    public static List<FetchUser> getUserList() {
        List<FetchUser> userList = new ArrayList<>();

        Base.open();
        List<Users> allUser = Users.findAll();

        for (Users eUser : allUser) {
            FetchUser user = new FetchUser();
            user.setEmployee_id(eUser.getEmployeeId());
            user.setFirstname(eUser.getFirstName());
            user.setEmail(eUser.getEmail());
            user.setMobile_number(eUser.getMobileNumber());
            user.setGroup_name(eUser.parent(Groups.class).getString("group_name"));
            user.setStatus(eUser.getStatus());
            user.setId(eUser.getUserId());
            user.setEmail_status(eUser.getEmailVerifyStatus());
            userList.add(user);
        }
        Base.close();
        return userList;
    }

    public static List<Map<String, Object>> getUserDetails(String id) {
        List<Map<String, Object>> row = new ArrayList<>();
        Base.open();
        String fetchusersdetails_query = "SELECT id,username,employee_id,firstname,lastname,password,email,supervisor_email,mobile_number,group_id,status,email_status FROM users WHERE id= ? ";
        LazyList<Users> cUser = Users.findBySQL(fetchusersdetails_query, id);
        row = cUser.toMaps();
        Base.close();
        return row;
    }

    public static boolean updateDetails(Users user, int id) {
        Base.open();

        Users updateUser = Users.findById(id);
        updateUser.set("username", user.getUserName());
        updateUser.set("employee_id", user.getEmployeeId());
        updateUser.set("firstname", user.getFirstName());
        updateUser.set("lastname", user.getLastName());
        updateUser.set("password", user.getPassword());
        updateUser.set("email", user.getEmail());
        updateUser.set("supervisor_email", user.getSupervisorEmail());
        updateUser.set("mobile_number", user.getMobileNumber());
        updateUser.set("group_id", user.getGroupId());
        updateUser.set("status", user.getStatus());
        updateUser.set("email_status", user.getEmailVerifyStatus());

        try {
            return updateUser.saveIt();
        } finally {
            Base.close();
        }
    }

    public static boolean updateUserStatus(int id) {
        Base.open();
        Users eUserStatus = Users.findById(id);
        eUserStatus.set("email_status", true);

        try {
            return eUserStatus.saveIt();
        } finally {
            Base.close();
        }
    }

    public static List<String> getEmailListforNotification(int sender_id, String group_id) {
        Base.open();
        try {
            String fetchusers_query = "SELECT email FROM users "
                    + "WHERE id <> ? AND group_id IN (?) AND status = true AND email_status = true";
            LazyList<Users> list = Users.findBySQL(fetchusers_query, sender_id, group_id);
            return list.collect("email");
        } finally {
            Base.close();
        }
    }
}

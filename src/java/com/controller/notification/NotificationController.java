/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.notification;

import com.controller.common.CookieRead;
import com.model.notification.Notification;
import com.model.notification.NotificationDB;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ETS-4
 */
public class NotificationController {

    private List<Map<String, Object>> notification_result = new ArrayList<Map<String, Object>>();

    public void createNotification(int version_type_id, float version_name, String creation_date) {
        int senderId = Integer.parseInt(CookieRead.getCookie("userid"));
        String receiverId = "2,1";
        NotificationDB.insertNotification(new Notification(senderId, receiverId, version_type_id, version_name, creation_date));
    }

    public String unreadNotification() {

        try {
            int userid = Integer.parseInt(CookieRead.getCookie("userid"));
            notification_result = (List<Map<String, Object>>)  NotificationDB.getUnreadNotification(userid, NotificationDB.getGroupIdForUser(userid));
            //System.out.println("---------->"+notification_result.toString());

        } catch (Exception ex) {
            System.out.println("entered into catch");
            System.out.println(ex.getMessage());
        }
        return "success";
    }

    public List<Map<String, Object>> getNotification_result() {
        return notification_result;
    }

    public void setNotification_result(List<Map<String, Object>> notification_result) {
        this.notification_result = notification_result;
    }

}

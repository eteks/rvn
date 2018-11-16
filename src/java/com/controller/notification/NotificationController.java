/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.notification;

import com.controller.common.CookieRead;
import com.controller.common.MailUtil;
import com.controller.common.VersionType;
import com.controller.common.VersionViewPage;
import com.model.admin.UserDB;
import com.model.notification.Notification;
import com.model.notification.NotificationDB;
import com.model.notification.StatusNotification;
import com.model.notification.StatusNotificationDB;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author ETS-4
 */
public class NotificationController {

    private List<Map<String, Object>> notification_result = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> view_notification = new ArrayList<Map<String, Object>>();
    private int notification_id;
    
    private static String getURLPath(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String url = request.getRequestURL().toString();
        String ctxPath = request.getContextPath();
        url = url.replaceFirst(uri, "");
        return  url + ctxPath;
    }
    
    public void createNotification(int version_type_id, float version_name, String creation_date, String receiverId) throws UnsupportedEncodingException {
        int senderId = CookieRead.getUserIdFromSession();
        HttpServletRequest request = ServletActionContext.getRequest();
        Notification notification = new Notification(senderId, receiverId, version_type_id, version_name, creation_date);
        NotificationDB.insertNotification(notification);
        List<String> emailList = UserDB.getEmailListforNotification(senderId, receiverId);
        String versionLink = NotificationController.getURLPath(request)+"/"+VersionViewPage.fromId(version_type_id)+".action?id="+NotificationDB.getVersionId(VersionType.fromId(version_type_id), version_name)+"&action=view";
//        if (!emailList.isEmpty()) {
//            MailUtil.sendNotificationMail(emailList, "Notification Email", notification, versionLink);
//        }
    }

    public String unreadNotification() {

        try {
            int userid = CookieRead.getUserIdFromSession();
            notification_result = (List<Map<String, Object>>) NotificationDB.getNotificationList(userid);

        } catch (Exception ex) {
            System.out.println("entered into catch");
            System.out.println(ex.getMessage());
        }
        return "success";
    }

    public String readNotification() {
        int userid = CookieRead.getUserIdFromSession();
        view_notification = NotificationDB.readNotification(getNotification_id());
        StatusNotification sn = new StatusNotification();
        sn.setNotification_id(getNotification_id());
        sn.setReceiver_id(userid);
        StatusNotificationDB.insertStatus(sn);
        return "success";
    }

    public List<Map<String, Object>> getNotification_result() {
        return notification_result;
    }

    public void setNotification_result(List<Map<String, Object>> notification_result) {
        this.notification_result = notification_result;
    }

    public List<Map<String, Object>> getView_notification() {
        return view_notification;
    }

    public void setView_notification(List<Map<String, Object>> view_notification) {
        this.view_notification = view_notification;
    }

    public int getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(int notification_id) {
        this.notification_id = notification_id;
    }

}

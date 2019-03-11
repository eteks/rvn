/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.common;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author ETS-4
 */
public class CookieRead {

    static HttpServletRequest request;

    public static String getCookie(String name) {
        request = ServletActionContext.getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static int getUserIdFromSession(){
        //System.out.println("User ID Test "+ActionContext.getContext().getSession().get("userid").toString());
        return Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
    }
    
    public static int getGroupIdFromSession(){
        //System.out.println("User ID Test "+ActionContext.getContext().getSession().get("userid").toString());
        return Integer.parseInt(ActionContext.getContext().getSession().get("user_groupid").toString());
    }
}

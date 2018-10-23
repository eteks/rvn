/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.admin;

import com.model.admin.UserDB;

/**
 *
 * @author ETS-4
 */
public class MailController {

    private String scope;
    private int userId;
    private String verifyId;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getVerifyId() {
        return verifyId;
    }

    public void setVerifyId(String verifyId) {
        this.verifyId = verifyId;
    }

    public String verifyEmail() {
        if (scope.equals("activation")) {
            if (UserDB.checkVerificationId(userId, verifyId)) {
                if (UserDB.updateUserStatus(userId)) {
                    return "success";
                }
            }
        }
        return "error";
    }
}

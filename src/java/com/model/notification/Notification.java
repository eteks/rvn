/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.notification;

public class Notification {

    private int id;
    private int sender_id;
    private String receiver_id;
    private int version_type_id;
    private float version_id;
    private String created_date;

    public Notification(int sender_id, String receiver_id, int version_type_id, float version_id, String created_date) {
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.version_type_id = version_type_id;
        this.version_id = version_id;
        this.created_date = created_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public int getVersion_type_id() {
        return version_type_id;
    }

    public void setVersion_type_id(int version_type_id) {
        this.version_type_id = version_type_id;
    }

    public float getVersion_id() {
        return version_id;
    }

    public void setVersion_id(float version_id) {
        this.version_id = version_id;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }
}

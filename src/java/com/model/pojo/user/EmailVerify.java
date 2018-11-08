/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo.user;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author ETS-4
 */
@Entity
public class EmailVerify implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "eVer_generator")
    @SequenceGenerator(name = "eVer_generator", sequenceName = "eVer_seq")
    @Column(updatable = false, nullable = false)
    private int id;
    @OneToOne
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    @JoinColumn(name = "user_id")
    private Users users;
    private String verification_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getVerification_id() {
        return verification_id;
    }

    public void setVerification_id(String verification_id) {
        this.verification_id = verification_id;
    }

    public EmailVerify(Users users, String verification_id) {
        this.users = users;
        this.verification_id = verification_id;
    }

}

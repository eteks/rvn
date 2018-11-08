/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.admin;

import com.controller.common.HibernateUtil;
import com.model.pojo.user.EmailVerify;
import com.model.pojo.user.Groups;
import com.model.pojo.user.Users;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.json.simple.JSONArray;

/**
 *
 * @author ETS-4
 */
public class UserDB {

    public static Object[] checkEmployeeIdMailExists(String employeeId, String email) {
        boolean emp_id_status = false, email_id_status = false;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        List emp_id = session.createQuery("FROM Users WHERE employee_id =:emp_id").setParameter("emp_id", employeeId).list();
        List email_id = session.createQuery("FROM Users WHERE email =:email").setParameter("email", email).list();

        if (!emp_id.isEmpty()) {
            emp_id_status = true;
        }
        if (!email_id.isEmpty()) {
            email_id_status = true;
        }

        return new Object[]{emp_id_status, email_id_status};
    }

    public static Object[] getEmployeeIdMail(int id) {
        String employeeId = null, mail = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        Users user = (Users) session.get(Users.class, id);

        employeeId = user.getEmployee_id();
        mail = user.getEmail();
        return new Object[]{employeeId, mail};
    }

    public static String getUserNamebyID(int id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Users user = (Users) session.get(Users.class, id);
        return user.getFirstname();
    }

    public static JSONArray getUserCountbyGroup() {
        JSONArray usersList = new JSONArray();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String fetch_userscount_query = "SELECT g.group_name, COUNT(u.groups.id) FROM Groups g LEFT OUTER JOIN Users u ON u.groups.id = g.id GROUP BY g.group_name";

        List<Object[]> userCount = session.createQuery(fetch_userscount_query).list();
        for (Object[] uc : userCount) {
            JSONArray list = new JSONArray();
            list.add(uc[0]);
            list.add(uc[1]);
            usersList.add(list);
        }
        return usersList;
    }

    public static int createUser(Users user) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        int userId = 0;
        try {
            tx = session.beginTransaction();
            userId = (int) session.save(user);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            System.out.println("Create User error message" + e.getMessage());
            e.printStackTrace();
        }
        return userId;
    }

    public static boolean insertVerificationId(int userId, String verificationId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Users user = (Users) session.load(Users.class, userId);
            EmailVerify emailVerify = new EmailVerify(user, verificationId);
            session.save(emailVerify);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            tx.rollback();
            System.out.println("Insert Verification ID error message" + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkVerificationId(int userId, String verificationId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        List verifyList = session.createQuery("FROM EmailVerify ev WHERE ev.users.id =:user_id AND verification_id =:verificationId").setParameter("user_id", userId).setParameter("verificationId", verificationId).list();

        if (!verifyList.isEmpty()) {
            return true;
        }
        return false;
    }

    public static List<FetchUser> getUserList() {
        List<FetchUser> userList = new ArrayList<>();

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String fetchusers_query = "SELECT u.employee_id,u.firstname,u.email,u.mobile_number,u.groups.group_name,u.status,u.id,u.email_status FROM Users u";
        List<Object[]> fetchList = session.createQuery(fetchusers_query).list();
        for (Object[] fl : fetchList) {
            FetchUser user = new FetchUser();
            user.setEmployee_id(fl[0].toString());
            user.setFirstname(fl[1].toString());
            user.setEmail(fl[2].toString());
            user.setMobile_number((double) fl[3]);
            user.setGroup_name(fl[4].toString());
            user.setStatus((boolean) fl[5]);
            user.setId((int) fl[6]);
            user.setEmail_status((boolean) fl[7]);
            userList.add(user);
        }
        return userList;
    }

    public static List<Map<String, Object>> getUserDetails(String id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        String fetchusersdetails_query = "SELECT id,username,employee_id,firstname,lastname,password,email,supervisor_email,mobile_number,group_id,status FROM Users WHERE id =:id";

        return session.createQuery(fetchusersdetails_query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
    }

    public static boolean updateDetails(Users user, int id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Groups group = (Groups) session.load(Groups.class, user.getGroup_id());
            session.flush();
            Users userFin = (Users) session.get(Users.class, id);
            userFin.setUsername(user.getUsername());
            userFin.setEmployee_id(user.getEmployee_id());
            userFin.setFirstname(user.getFirstname());
            userFin.setLastname(user.getLastname());
            userFin.setPassword(user.getPassword());
            userFin.setEmail(user.getEmail());
            userFin.setSupervisor_email(user.getSupervisor_email());
            userFin.setMobile_number(user.getMobile_number());
            userFin.setGroups(group);
            userFin.setStatus(user.isStatus());
            session.saveOrUpdate(userFin);
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            System.out.println("Update User error message" + e.getMessage());
            e.printStackTrace();

        }
        return false;
    }

    public static boolean updateUserStatus(int id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Users user = (Users) session.load(Users.class, id);
            user.setEmail_status(true);
            session.saveOrUpdate(user);
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            System.out.println("Update User Status error message" + e.getMessage());
            e.printStackTrace();

        }
        return false;
    }

    public static List<String> getEmailListforNotification(int sender_id, String group_id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        String fetchusers_query = "SELECT email FROM Users "
                + "WHERE id <> " + sender_id + " AND group_id IN (" + group_id + ") AND status = true AND email_status = true";
        
        return session.createQuery(fetchusers_query).list();
    }
}

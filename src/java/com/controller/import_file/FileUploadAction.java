/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.import_file;
import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import java.io.IOException;
import javax.servlet.ServletException;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

/**
 *
 * @author ETS-4
 */
public class FileUploadAction extends ActionSupport implements ServletRequestAware{
    private HttpServletRequest servletRequest;

    public HttpServletRequest getServletRequest() {
        return servletRequest;
    }

    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    public String execute() throws ServletException, IOException {
        try {
            MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) ServletActionContext.getRequest();
            
            String[] fileName = multiWrapper.getFileNames("file");
            /*for(String s : fileName) {
                    System.out.println(s);
            }*/
            
            File[] files = multiWrapper.getFiles("file");
            String filePath = servletRequest.getSession().getServletContext().getRealPath("/import");

            File fileToCreate = new File(filePath, fileName[0]);
            FileUtils.copyFile(files[0], fileToCreate);

            File f = new File(fileToCreate.getAbsolutePath());
            System.out.println("Path :"+f.getAbsolutePath());
            
            new ImportUtil().readCSV(f.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
            addActionError(e.getMessage());
        }
        return SUCCESS;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.import_file;

import com.controller.common.FilePath;
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
public class FileUploadAction extends ActionSupport implements ServletRequestAware {

    private HttpServletRequest servletRequest;

    public HttpServletRequest getServletRequest() {
        return servletRequest;
    }

    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    public String importPDB() throws ServletException, IOException {
        try {
            MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) ServletActionContext.getRequest();

            String[] fileName = multiWrapper.getFileNames("file");
            /*for(String s : fileName) {
                    System.out.println(s);
            }*/

            File[] files = multiWrapper.getFiles("file");
            File folder = new File(FilePath.getPath(), "import");
            folder.mkdir();
            File fileToCreate = new File(folder, fileName[0]);
            fileToCreate.createNewFile();
            FileUtils.copyFile(files[0], fileToCreate);

            File f = new File(fileToCreate.getAbsolutePath());
            System.out.println("Path :" + f.getAbsolutePath());

            new ImportUtil().readPDBCSV(f.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
            addActionError(e.getMessage());
        }
        return SUCCESS;
    }

    public String importIVN() throws ServletException, IOException {
        try {
            MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) ServletActionContext.getRequest();

            String[] fileName = multiWrapper.getFileNames("file");
            /*for(String s : fileName) {
                    System.out.println(s);
            }*/

            File[] files = multiWrapper.getFiles("file");
            File folder = new File(FilePath.getPath(), "import");
            folder.mkdir();
            File fileToCreate = new File(folder, fileName[0]);
            fileToCreate.createNewFile();
            FileUtils.copyFile(files[0], fileToCreate);

            File f = new File(fileToCreate.getAbsolutePath());
            System.out.println("Path :" + f.getAbsolutePath());

            new ImportUtil().readIVNCSV(f.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
            addActionError(e.getMessage());
        }
        return SUCCESS;
    }

    public String importACB() throws ServletException, IOException {
        try {
            MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) ServletActionContext.getRequest();

            String[] fileName = multiWrapper.getFileNames("file");
            /*for(String s : fileName) {
                    System.out.println(s);
            }*/

            File[] files = multiWrapper.getFiles("file");
            File folder = new File(FilePath.getPath(), "import");
            folder.mkdir();
            File fileToCreate = new File(folder, fileName[0]);
            fileToCreate.createNewFile();
            FileUtils.copyFile(files[0], fileToCreate);

            File f = new File(fileToCreate.getAbsolutePath());
            System.out.println("Path :" + f.getAbsolutePath());

            new ImportUtil().readACBCSV(f.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
            addActionError(e.getMessage());
        }
        return SUCCESS;
    }
}

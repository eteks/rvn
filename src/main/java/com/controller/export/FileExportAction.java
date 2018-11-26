/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.export;

import com.controller.common.FilePath;
import com.controller.common.JSONConfigure;
import com.opensymphony.xwork2.ActionSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author ETS-4
 */
public class FileExportAction extends ActionSupport implements ServletRequestAware {

    private HttpServletRequest servletRequest;
    private InputStream fileInputStream;

    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    public HttpServletRequest getServletRequest() {
        return servletRequest;
    }

    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    public String exportACB() throws FileNotFoundException, UnsupportedEncodingException, IOException {
        //String filePath = servletRequest.getSession().getServletContext().getRealPath("/export");
        //File fileToCreate = new File(System.getProperty("user.home") + "/Desktop" + "\\exportACB.csv");
        File folder = new File(FilePath.getPath(), "export");
        folder.mkdir();
        File fileToCreate = new File(folder, "exportACB.csv");
        fileToCreate.createNewFile();
        //System.out.println("File Export Path :" + fileToCreate.getAbsolutePath());

        try {
            JSONParser parser = new JSONParser();
            String jsondata = JSONConfigure.getAngularJSONFile();
            Object obj = parser.parse(jsondata);
            JSONObject json = (JSONObject) obj;
            int vehicle_version = Integer.parseInt(json.get("vehicle_version").toString()),
                    vehicle_id = Integer.parseInt(json.get("vehicle").toString()),
                    pdb_version = Integer.parseInt(json.get("pdb_version").toString()),
                    ivn_version = Integer.parseInt(json.get("ivn_version").toString());

            ExportUtil.exportACBVersionCSV(vehicle_version, vehicle_id, pdb_version, ivn_version, fileToCreate.getAbsolutePath(), parser);
            fileInputStream = new FileInputStream(fileToCreate);
        } catch (ParseException | IOException ex) {
            Logger.getLogger(FileExportAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "success";
    }

    public String exportModelVersion() throws FileNotFoundException, UnsupportedEncodingException, IOException {
        //String filePath = servletRequest.getSession().getServletContext().getRealPath("/export");
        //File fileToCreate = new File(System.getProperty("user.home") + "/Desktop" + "\\exportACB.csv");
        File folder = new File(FilePath.getPath(), "export");
        folder.mkdir();
        File fileToCreate = new File(folder, "exportModelVersion.csv");
        fileToCreate.createNewFile();
        //System.out.println("File Export Path :" + fileToCreate.getAbsolutePath());

        try {
            JSONParser parser = new JSONParser();
            String jsondata = JSONConfigure.getAngularJSONFile();
            Object obj = parser.parse(jsondata);
            JSONObject json = (JSONObject) obj;
            int vehicle_version = Integer.parseInt(json.get("vehicle_version").toString()),
                    vehicle_id = Integer.parseInt(json.get("vehicle").toString()),
                    acb_version = Integer.parseInt(json.get("acb_version").toString());

            ExportUtil.exportModelVersionCSV(vehicle_version, vehicle_id, acb_version, fileToCreate.getAbsolutePath(), parser);
            fileInputStream = new FileInputStream(fileToCreate);
        } catch (ParseException | IOException ex) {
            Logger.getLogger(FileExportAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "success";
    }

    public String exportSystemVersion() throws FileNotFoundException, UnsupportedEncodingException, IOException {
        //String filePath = servletRequest.getSession().getServletContext().getRealPath("/export");
        //File fileToCreate = new File(System.getProperty("user.home") + "/Desktop" + "\\exportACB.csv");
        File folder = new File(FilePath.getPath(), "export");
        folder.mkdir();
        File fileToCreate = new File(folder, "exportSystemVersion.csv");
        fileToCreate.createNewFile();
        //System.out.println("File Export Path :" + fileToCreate.getAbsolutePath());

        try {
            JSONParser parser = new JSONParser();
            String jsondata = JSONConfigure.getAngularJSONFile();
            Object obj = parser.parse(jsondata);
            JSONObject json = (JSONObject) obj;
            int vehicle_version = Integer.parseInt(json.get("vehicle_version").toString()),
                    vehicle_id = Integer.parseInt(json.get("vehicle").toString()),
                    acb_version = Integer.parseInt(json.get("acb_version").toString()),
                    ecu = Integer.parseInt(json.get("ecu").toString());

            ExportUtil.exportSystemVersionCSV(vehicle_version, vehicle_id, acb_version, ecu, fileToCreate.getAbsolutePath(), parser);
            fileInputStream = new FileInputStream(fileToCreate);
        } catch (ParseException | IOException ex) {
            Logger.getLogger(FileExportAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "success";
    }
}

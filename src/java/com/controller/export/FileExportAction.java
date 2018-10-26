/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.export;

import com.controller.common.JSONConfigure;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.model.acb_owner.ACBOwnerDB;
import com.model.ivn_engineer.IVNEngineerDB;
import com.model.ivn_engineer.IVNversion;
import com.model.ivn_supervisor.Vehicleversion;
import com.model.ivn_supervisor.VehicleversionDB;
import com.model.pdb_owner.PDBVersionDB;
import com.model.pdb_owner.PDBversion;
import com.opensymphony.xwork2.ActionSupport;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author ETS-4
 */
public class FileExportAction extends ActionSupport implements ServletRequestAware {

    private HttpServletRequest servletRequest;

    public HttpServletRequest getServletRequest() {
        return servletRequest;
    }

    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    public String exportACB() {
        //String filePath = servletRequest.getSession().getServletContext().getRealPath("/import");
        File fileToCreate = new File(System.getProperty("user.home") + "/Desktop" + "\\exportACB.csv");
        System.out.println("File Export Path :" + fileToCreate.getAbsolutePath());

        try {
            JSONParser parser = new JSONParser();
            String jsondata = JSONConfigure.getAngularJSONFile();
            Object obj = parser.parse(jsondata);
            JSONObject json = (JSONObject) obj;
            int vehicle_version = Integer.parseInt(json.get("vehicle_version").toString()),
                    vehicle_id = Integer.parseInt(json.get("vehicle").toString()),
                    pdb_version = Integer.parseInt(json.get("pdb_version").toString()),
                    ivn_version = Integer.parseInt(json.get("ivn_version").toString());
            
            ExportUtil.exportACBVersionCSV(vehicle_version,vehicle_id,pdb_version,ivn_version,fileToCreate.getAbsolutePath(),parser);
        } catch (ParseException ex) {
            Logger.getLogger(FileExportAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileExportAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "success";
    }
}

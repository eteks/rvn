/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.export;

import com.controller.common.JSONConfigure;
import com.model.ivn_engineer.IVNEngineerDB;
import com.model.ivn_supervisor.VehicleversionDB;
import com.model.pdb_owner.PDBVersionDB;
import com.opensymphony.xwork2.ActionSupport;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
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

    public String exportACB(){
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
            
            Writer writer = new BufferedWriter(new FileWriter(fileToCreate));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.EXCEL);
            
            JSONArray versionDetails = new JSONArray();
            versionDetails.add("Vehicle Version");
            versionDetails.add(VehicleversionDB.getVehicleVersionNameFromId(vehicle_version));
            versionDetails.add("Vehicle");
            versionDetails.add(VehicleversionDB.getVehicleNameFromId(vehicle_id));
            versionDetails.add("PDB Version");
            versionDetails.add(PDBVersionDB.getPDBVersionNameFromId(pdb_version));
            versionDetails.add("IVN Version");
            versionDetails.add(IVNEngineerDB.getIVNVersionNameFromId(ivn_version));
            
            csvPrinter.printRecord(versionDetails);
            
            
            csvPrinter.flush();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(FileExportAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "success";
    }
}

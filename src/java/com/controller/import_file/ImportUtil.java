/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.import_file;

import com.model.ivn_engineer.IVNEngineerDB;
import com.model.ivn_engineer.Signal;
import com.model.pdb_owner.Domain;
import com.model.pdb_owner.Domain_and_Features_Mapping;
import com.model.pdb_owner.Features;
import com.model.pdb_owner.PDBVersionDB;
import com.model.pdb_owner.PDBVersionGroup;
import com.model.pdb_owner.PDBversion;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.commons.csv.CSVRecord;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author ETS-4
 */
public class ImportUtil {

    public void readPDBCSV(String filePath) throws IOException {
        JSONObject pdbObject;
        Object[] resultCSV = ImportCSV.getDetailsFromCSV(filePath);
        pdbObject = (JSONObject) resultCSV[0];
        List<CSVRecord> csvRecord = (List<CSVRecord>) resultCSV[1];
        //System.out.println("Imported CSV -->" + pdbObject);
        this.CreateDomain_and_Features(pdbObject);
        JSONObject finalJson = ImportCSV.addPDBversion_group(pdbObject, csvRecord);
        //System.out.println("Finall "+finalJson);
        JSONArray vehicle = (JSONArray) finalJson.get("vehicle");
        JSONArray pdbdata_list = (JSONArray) finalJson.get("pdbdata_list");
        //System.out.println("Vehicle Size  " + vehicle.size());
        for (int i = 0; i < vehicle.size(); i++) {
            //System.out.println("Inside For....");
            JSONObject each = (JSONObject) vehicle.get(i);
            String v_name = (String) each.get("name");
            JSONObject pdb = (JSONObject) pdbdata_list.get(i);
            JSONArray modal = (JSONArray) pdb.get(v_name);
            this.insertPDBVersion(modal);
        }
    }

    public void CreateDomain_and_Features(JSONObject pdbObject) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            JSONArray domain_feature = (JSONArray) pdbObject.get("domain_feature");
            for (int i = 0; i < domain_feature.size(); i++) {
                JSONObject eachObject = (JSONObject) domain_feature.get(i);
                String domain_name = (String) eachObject.get("domain_name");
                JSONArray features = (JSONArray) eachObject.get("feature");

                //Insert Data in Domain table
                Domain dom = new Domain(domain_name, dtf.format(now), 1);
                int dom_result = PDBVersionDB.insertDomain(dom);

                //Insert Data in Features table
                for (int j = 0; j < features.size(); j++) {
                    String feature_name = features.get(j).toString();
                    Features fd = new Features(feature_name, " ", dtf.format(now), 1);
                    int fd_result = PDBVersionDB.insertFeatures(fd);

                    //Insert Data in Domain and Features Mapping Table
                    Domain_and_Features_Mapping dfm = new Domain_and_Features_Mapping(dom_result, fd_result, dtf.format(now));
                    PDBVersionDB.insertDomainFeaturesMapping(dfm);
                }
            }

        } catch (Exception ex) {
            System.out.println("entered into catch");
            System.out.println(ex.getMessage());
        }
    }

    public void insertPDBVersion(JSONArray pdbdata_list) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        PDBversion pv = new PDBversion((float) 1.0, true, true, dtf.format(now), 1, "create");
        Object[] id_version = PDBVersionDB.insertPDBVersion(pv);
        int pdb_id = (int) id_version[0];
        for (int j = 0; j < pdbdata_list.size(); j++) {
            JSONObject eachpdb_list = (JSONObject) pdbdata_list.get(j);
            int vmm_id = (int) eachpdb_list.get("vmm_id");
            int dfm_id = (int) eachpdb_list.get("dfm_id");
            String av_status = (String) eachpdb_list.get("status");
            PDBVersionGroup pvg = new PDBVersionGroup(pdb_id, vmm_id, dfm_id, av_status, "submit", "create");
            PDBVersionDB.insertPDBVersionGroup(pvg);

        }
    }

    public void readACBCSV(String filePath) throws IOException {
        JSONObject ivnObject = (JSONObject) ImportCSV.getACBDetailsFromCSV(filePath);

        JSONArray ivn_data = (JSONArray) ivnObject.get("ivn_data");

        JSONArray ivn_final = new JSONArray();
        for (int i = 0; i < ivn_data.size(); i++) {
            JSONObject finalIVN = new JSONObject();
            JSONObject currentIVN = (JSONObject) ivn_data.get(i);
            finalIVN.put("name", currentIVN.get("signal_name"));
            finalIVN.put("alias", currentIVN.get("signal_alias"));
            finalIVN.put("description", currentIVN.get("signal_desc"));
            JSONArray can = (JSONArray) currentIVN.get("can");
            JSONArray lin = (JSONArray) currentIVN.get("lin");
            JSONArray hardware = (JSONArray) currentIVN.get("hardware");
            String canid = "", linid = "", hardwareid = "";
            for (int cani = 0; cani < can.size(); cani++) {
                canid += IVNEngineerDB.getNetworkDataCSV(can.get(cani) + "", "can");
                if (cani + 1 != can.size()) {
                    canid += ",";
                }
            }
            for (int lini = 0; lini < lin.size(); lini++) {
                linid += IVNEngineerDB.getNetworkDataCSV(lin.get(lini) + "", "lin");
                if (lini + 1 != lin.size()) {
                    linid += ",";
                }
            }
            for (int hi = 0; hi < hardware.size(); hi++) {
                hardwareid += IVNEngineerDB.getNetworkDataCSV(hardware.get(hi) + "", "hardware");
                if (hi + 1 != hardware.size()) {
                    hardwareid += ",";
                }
            }
            finalIVN.put("can", canid);
            finalIVN.put("lin", linid);
            finalIVN.put("hardware", hardwareid);
            ivn_final.add(finalIVN);
        }

        ivnObject.put("final", ivn_final);

        JSONArray insertSignalJson = (JSONArray) ivnObject.get("final");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < insertSignalJson.size(); i++) {
            JSONObject currentIVN = (JSONObject) insertSignalJson.get(i);
            String signal_name = (String) currentIVN.get("name");
            String signal_alias = (String) currentIVN.get("alias");
            String signal_desc = (String) currentIVN.get("description");
            String can = (String) currentIVN.get("can");
            String lin = (String) currentIVN.get("lin");
            String hardware = (String) currentIVN.get("hardware");

            Signal signal = new Signal(signal_name, signal_alias, signal_desc, can, lin, hardware, dtf.format(now), 1, true);
            IVNEngineerDB.insertSignalData(signal);
        }
        //System.out.println("ACBBBB"+ivnObject);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.export;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.model.acb_owner.ACBOwnerDB;
import com.model.ivn_engineer.IVNEngineerDB;
import com.model.ivn_engineer.IVNversion;
import com.model.ivn_supervisor.Vehicleversion;
import com.model.ivn_supervisor.VehicleversionDB;
import com.model.pdb_owner.PDBVersionDB;
import com.model.pdb_owner.PDBversion;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

/**
 *
 * @author ETS-4
 */
public class ExportUtil {

    public static void exportACBVersionCSV(int vehicle_version, int vehicle_id, int pdb_version, int ivn_version, String path, JSONParser parser) throws IOException {
        ICsvMapWriter mapWriter = null;
        //InputStream inputStream = new FileInputStream(path);
        try {
            mapWriter = new CsvMapWriter(new FileWriter(path, false), CsvPreference.EXCEL_PREFERENCE);

            String[] details = new String[]{"Vehicle Version", VehicleversionDB.getVehicleVersionNameFromId(vehicle_version) + "", "Vehicle", VehicleversionDB.getVehicleNameFromId(vehicle_id),
                "PDB Version", PDBVersionDB.getPDBVersionNameFromId(pdb_version) + "",
                "IVN Version", IVNEngineerDB.getIVNVersionNameFromId(ivn_version) + ""};

            mapWriter.writeHeader(details);

            List<Map<String, Object>> loadPreviousVehicleVersion = VehicleversionDB.LoadPreviousVehicleversionData(new Vehicleversion(vehicle_version));
            JsonArray loadVehVer = new GsonBuilder().create().toJsonTree(loadPreviousVehicleVersion).getAsJsonArray();

            Map<String, Object> loadpdbVersion = ACBOwnerDB.LoadPDBDataForACBVersion(new PDBversion(pdb_version));
            JSONObject loadPDBVer = (JSONObject) parser.parse(new GsonBuilder().create().toJson(loadpdbVersion));
            JSONArray featureDetail = (JSONArray) loadPDBVer.get("featuredetail_list");
            JSONArray vehicleDetail = (JSONArray) loadPDBVer.get("vehicledetail_list");

            Map<String, Object> loadivnVersion = ACBOwnerDB.LoadIVNDataForACBVersion(new IVNversion(ivn_version));
            JSONObject loadIVNVer = (JSONObject) parser.parse(new GsonBuilder().create().toJson(loadivnVersion));
            JSONArray ecuDetail = (JSONArray) loadIVNVer.get("ecu");
            JSONArray signalDetail = (JSONArray) loadIVNVer.get("signal");
            JSONArray canDetail = (JSONArray) loadIVNVer.get("can");
            JSONArray linDetail = (JSONArray) loadIVNVer.get("lin");
            JSONArray hardwareDetail = (JSONArray) loadIVNVer.get("hardware");
            List<String> modelList = new ArrayList<>();
            List<String> vmmList = new ArrayList<>();
            Map<String, List<String>> networkList = new HashMap<>();

            for (int i = 0; i < vehicleDetail.size(); i++) {
                JSONObject list = (JSONObject) vehicleDetail.get(i);
                modelList.add(list.get("modelname").toString());
                vmmList.add(list.get("vmm_id").toString());
                networkList.put(list.get("vmm_id").toString(), new ArrayList<>());
            }
            List<String> headerDetails = new ArrayList<>();
            headerDetails.add("Domain");
            headerDetails.add("Features");
            headerDetails.addAll(modelList);
            headerDetails.add("ECU");
            headerDetails.add("Input");
            headerDetails.addAll(modelList);
            headerDetails.add("Output");
            headerDetails.addAll(modelList);
            headerDetails.add(null);
            headerDetails.add("Signal List");
            headerDetails.add("ECU List");
            headerDetails.addAll(modelList);
            String[] headerDetailsArray = Arrays.copyOf(headerDetails.toArray(), headerDetails.toArray().length, String[].class);
            mapWriter.writeHeader(headerDetailsArray);

            List<List<String>> domFeatureList = new ArrayList<>();
            for (int i = 0; i < featureDetail.size(); i++) {
                List<String> currentDetail = new ArrayList<>();
                JSONObject featureObj = (JSONObject) featureDetail.get(i);
                currentDetail.add((String) featureObj.get("domainname"));
                currentDetail.add((String) featureObj.get("featurename"));
                currentDetail.addAll(Arrays.asList(featureObj.get("status").toString().toUpperCase().split(",")));

                domFeatureList.add(currentDetail);
                //mapWriter.writeHeader(Arrays.copyOf(currentDetail.toArray(), currentDetail.toArray().length, String[].class));
            }

            List<String> ecuList = new ArrayList<>();
            for (int i = 0; i < ecuDetail.size(); i++) {
                JSONObject ecuObj = (JSONObject) ecuDetail.get(i);
                ecuList.add(ecuObj.get("description").toString());
            }

            List<String> signalList = new ArrayList<>();
            for (int i = 0; i < signalDetail.size(); i++) {
                JSONObject signalObj = (JSONObject) signalDetail.get(i);
                signalList.add(signalObj.get("listitem").toString());
            }

            for (int i = 0; i < canDetail.size(); i++) {
                JSONObject canObj = (JSONObject) canDetail.get(i);
                networkList.get(canObj.get("vmm_id")).add(canObj.get("listitem").toString());
            }
            for (int i = 0; i < linDetail.size(); i++) {
                JSONObject linObj = (JSONObject) linDetail.get(i);
                networkList.get(linObj.get("vmm_id")).add(linObj.get("listitem").toString());
            }
            for (int i = 0; i < hardwareDetail.size(); i++) {
                JSONObject hardObj = (JSONObject) hardwareDetail.get(i);
                networkList.get(hardObj.get("vmm_id")).add(hardObj.get("listitem").toString());
            }

            List<Integer> sizeOfList = new ArrayList<>();
            sizeOfList.add(domFeatureList.size());
            sizeOfList.add(ecuList.size());
            sizeOfList.add(signalList.size());
            for (List<String> value : networkList.values()) {
                sizeOfList.add(value.size());
            }

            Integer size = Collections.max(sizeOfList);
            int gap = modelList.size() * 2;
            gap += 4;
            for (int i = 0; i < size; i++) {
                List<String> currentDetail = new ArrayList<>();
                try {
                    currentDetail.addAll(domFeatureList.get(i));
                } catch (IndexOutOfBoundsException iobe) {
                    for (int m = 0; m < modelList.size() + 2; m++) {
                        currentDetail.add(null);
                    }
                }
                for (int g = 0; g < gap; g++) {
                    currentDetail.add(null);
                }
                try {
                    currentDetail.add(signalList.get(i));
                } catch (IndexOutOfBoundsException iobe) {
                    currentDetail.add(null);
                }
                try {
                    currentDetail.add(ecuList.get(i));
                } catch (IndexOutOfBoundsException iobe) {
                    currentDetail.add(null);
                }
                for (int l = 0; l < vmmList.size(); l++) {
                    try {
                        currentDetail.add(networkList.get(vmmList.get(l)).get(i));
                    } catch (IndexOutOfBoundsException iobe) {
                        currentDetail.add(null);
                    }
                }
                mapWriter.writeHeader(Arrays.copyOf(currentDetail.toArray(), currentDetail.toArray().length, String[].class));
                //System.out.println("Final " + currentDetail);
            }

        } catch (IOException ex) {
            Logger.getLogger(ExportUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ExportUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ExportUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (mapWriter != null) {
                mapWriter.close();
            }
        }
    }
}

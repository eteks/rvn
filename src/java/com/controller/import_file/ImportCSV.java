/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.import_file;

import com.model.ivn_supervisor.VehicleversionDB;
import com.model.pdb_owner.PDBVersionDB;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author ETS-4
 */
public class ImportCSV {

    public static Object[] getDetailsFromCSV(String filePath) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(filePath));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.EXCEL);
        List<CSVRecord> csvRecord = csvParser.getRecords();

        JSONObject pdbObject = new JSONObject();

        pdbObject.put("vehicle-version", csvRecord.get(0).get(1));

        //To Find Modal Size of Vehicle 
        Map<String, Integer> name_size = new LinkedHashMap<>();
        String vehicle_name = "";
        for (int j = 2; j < csvRecord.get(0).size(); j++) {
            if (!csvRecord.get(0).get(j).isEmpty()) {
                vehicle_name = csvRecord.get(0).get(j);
                name_size.put(vehicle_name, 1);
            } else {
                name_size.put(vehicle_name, name_size.get(vehicle_name) + 1);
            }
        }

        JSONArray vehicle = new JSONArray();
        int column_size = 2;
        for (Map.Entry<String, Integer> entry : name_size.entrySet()) {
            JSONObject name_modal = new JSONObject();
            JSONArray modal = new JSONArray();
            name_modal.put("name", entry.getKey());
            for (int i = 1; i <= entry.getValue(); i++) {
                modal.add(csvRecord.get(1).get(column_size));
                column_size++;
            }
            name_modal.put("modal", modal);
            vehicle.add(name_modal);
        }
        pdbObject.put("vehicle", vehicle);

        //To Find Feature Size of Domain 
        Map<String, Integer> domain_size = new LinkedHashMap<>();
        String domain_name = "";
        for (int i = 2; i < csvRecord.size(); i++) {
            if (!csvRecord.get(i).get(0).isEmpty()) {
                domain_name = csvRecord.get(i).get(0);
                domain_size.put(domain_name, 1);
            } else {
                domain_size.put(domain_name, domain_size.get(domain_name) + 1);
            }
        }

        JSONArray domain_features = new JSONArray();
        column_size = 2;
        for (Map.Entry<String, Integer> entry : domain_size.entrySet()) {
            JSONObject dom_fea = new JSONObject();
            JSONArray features = new JSONArray();
            dom_fea.put("domain_name", entry.getKey());
            for (int i = 1; i <= entry.getValue(); i++) {
                features.add(csvRecord.get(column_size).get(1));
                column_size++;
            }
            dom_fea.put("feature", features);
            domain_features.add(dom_fea);
        }
        pdbObject.put("domain_feature", domain_features);
        return new Object[]{pdbObject,csvRecord};
        //System.out.print(pdbObject);
    }

    public static JSONObject addPDBversion_group(JSONObject pdbObject,List<CSVRecord> csvRecord) throws IOException {
        JSONArray pdb_list = new JSONArray();
        JSONArray vehicle = (JSONArray) pdbObject.get("vehicle");
        JSONArray domain_features = (JSONArray) pdbObject.get("domain_feature");
        int column_size = 2;
        int row_size = 2;
        for (int v = 0; v < vehicle.size(); v++) {
            JSONObject each = new JSONObject();
            JSONArray listmain = new JSONArray();
            JSONObject eachVehicle = (JSONObject) vehicle.get(v);
            String v_name = (String) eachVehicle.get("name");
            JSONArray modal = (JSONArray) eachVehicle.get("modal");
            for (int d = 0; d < domain_features.size(); d++) {
                JSONObject eachDomain = (JSONObject) domain_features.get(d);
                String d_name = (String) eachDomain.get("domain_name");
                JSONArray feature = (JSONArray) eachDomain.get("feature");
                for (int f = 0; f < feature.size(); f++) {
                    String feature_name = feature.get(f).toString();
                    for (int m = 0; m < modal.size(); m++) {
                        String modal_name = modal.get(m).toString();
                        JSONObject list = new JSONObject();
                        list.put("vmm_id", VehicleversionDB.getVehicleModelMappingId(VehicleversionDB.getVehicleModelId(v_name, modal_name)));
                        list.put("dfm_id", PDBVersionDB.getDomainFeatureMappingId(PDBVersionDB.getDomainFeatureId(d_name, feature_name)));
                        list.put("status", csvRecord.get(row_size).get(column_size).toLowerCase());
                        listmain.add(list);
                        column_size++;
                    }
                    ++row_size;
                    column_size -= modal.size() - 1;
                    column_size--;
                }
            }
            each.put(v_name, listmain);
            pdb_list.add(each);
            column_size += modal.size();
            row_size = 2;
        }
        pdbObject.put("pdbdata_list", pdb_list);
        return pdbObject;
    }
}

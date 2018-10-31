/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.import_file;

import com.controller.common.JSONConfigure;
import com.controller.common.VersionType;
import com.controller.notification.NotificationController;
import com.model.acb_owner.ACBInput_and_Ouput_Signal;
import com.model.acb_owner.ACBOwnerDB;
import com.model.acb_owner.ACBVersionGroup;
import com.model.acb_owner.ACBversion;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVRecord;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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

    public void readIVNCSV(String filePath) throws IOException {
        JSONObject ivnObject = (JSONObject) ImportCSV.getIVNDetailsFromCSV(filePath);

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

    public void readACBCSV(String filePath) throws IOException {
        JSONObject acbObject = ImportCSV.getACBDetailsFromCSV(filePath);
        this.InsertACBVersion(acbObject);
    }

    public void InsertACBVersion(JSONObject acbObj) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        boolean status = (boolean) false;
        int acbversion_id = 0;
        float version_name;
        String previousversion_status = null;
        String previousversion_flag = null;
        String subversion = null;
        boolean flag;
        boolean is_acbsubversion = (boolean) false;
        try {
            JSONObject acbversion_value = (JSONObject) acbObj.get("acbversion");
            JSONArray features_group = (JSONArray) acbObj.get("acbdata_list");
            String button_type = (String) acbObj.get("button_type");
            boolean fully_touchedstatus = (boolean) acbObj.get("features_fully_touchedstatus");
            if (button_type.equals("save")) {
                flag = false;
            } else {
                flag = true;
            }
            System.out.println("before_if");
            if (acbversion_value != null && acbversion_value.containsKey("acbversion")) {
                System.out.println("enter_if");
                if (acbversion_value.get("acbsubversion") != null) {
                    acbversion_id = Integer.parseInt((String) acbversion_value.get("acbsubversion"));
                    is_acbsubversion = true;
                } else {
                    acbversion_id = Integer.parseInt((String) acbversion_value.get("acbversion"));
                }
            }

            if (acbversion_value != null && acbversion_value.containsKey("status")) {
                status = (boolean) acbversion_value.get("status");
            }

            if (acbversion_id != 0) {
                //Get the data of previous vehicle version by id
                int acbver_id = acbversion_id;
                ACBversion iver = new ACBversion(acbver_id);
                List<Map<String, Object>> acb_previous_result = ACBOwnerDB.LoadACBPreviousVehicleversionStatus(iver);
                System.out.println("acb_previous_result" + acb_previous_result);
                previousversion_status = String.valueOf(acb_previous_result.get(0).get("status"));
                previousversion_flag = String.valueOf(acb_previous_result.get(0).get("flag"));
            }
            System.out.println(previousversion_status);
            System.out.println(previousversion_flag);
            System.out.println(button_type);
            System.out.println(acbversion_id);
            if (previousversion_status == "false" && acbversion_id != 0) {
                System.out.println("Ready to update");
                System.out.println("if");
                ACBversion acb = new ACBversion(acbversion_id, status, flag, dtf.format(now), 1, "update", subversion, is_acbsubversion, fully_touchedstatus);
                System.out.println("acbversion_id" + acbversion_id);
                Object[] id_version = ACBOwnerDB.insertACBVersion(acb);
                int acb_id = (int) id_version[0];
                version_name = (float) id_version[1];
                System.out.println("acb_id" + acb_id);
                int i = 0;
                for (Object f : features_group) {
                    JSONObject f_group = (JSONObject) f;
                    System.out.println("f_group" + f_group);
                    JSONArray cloned_data = (JSONArray) f_group.get("cloned_data");
                    System.out.println("cloned_data" + cloned_data);
                    ArrayList ip_signals = new ArrayList();
                    ArrayList op_signals = new ArrayList();
                    for (Object c : cloned_data) {
                        JSONObject cl_group = (JSONObject) c;
                        System.out.println("cl_group" + cl_group);
                        System.out.println("cl_group_signal" + cl_group.get("signal"));
                        System.out.println("before the data loop");
                        int signal_id = Integer.parseInt(String.valueOf(cl_group.get("signal")));
//                        int signal_id = (int) cl_group.get("signal");
                        System.out.println("signal_id" + signal_id);
                        String signal_type = (String) cl_group.get("signal_type");
                        System.out.println("signal_type" + signal_type);
                        JSONArray group_data = (JSONArray) cl_group.get("group_data");
                        for (Object g : group_data) {
                            JSONObject g_group = (JSONObject) g;
                            int network_id = Integer.parseInt((String) g_group.get("nt_id"));
                            String network_type = (String) g_group.get("nt_type");
                            int pdbgroup_id = Integer.parseInt((String) g_group.get("pdbgroup_id"));
                            ACBInput_and_Ouput_Signal acb_signal = new ACBInput_and_Ouput_Signal(signal_type, signal_id, network_id, network_type, pdbgroup_id, button_type, "update");
                            int acb_signal_result = ACBOwnerDB.insertACBSignal(acb_signal);
                            if (signal_type.equals("input")) {
                                ip_signals.add(acb_signal_result);
                            } else {
                                op_signals.add(acb_signal_result);
                            }
                        }
                    }
                    System.out.println("ip_signals" + ip_signals);
                    System.out.println("op_signals" + op_signals);
                    int ivnversion_id = Integer.parseInt((String) acbversion_value.get("ivnversion"));
                    int pdbversion_id = Integer.parseInt((String) acbversion_value.get("pdbversion"));
                    int vehicleversion_id = Integer.parseInt((String) acbversion_value.get("vehicleversion"));
                    int vehicle_id = Integer.parseInt((String) acbversion_value.get("vehiclename"));
                    System.out.println("vehicle_id" + vehicle_id);
                    System.out.println("ecu_id" + f_group.get("ecu"));
                    int ecu_id = Integer.parseInt(String.valueOf(f_group.get("ecu")));
                    int dfm_id = Integer.parseInt(String.valueOf(f_group.get("fid")));
                    System.out.println("ecu_id" + ecu_id);
                    boolean touchedstatus = true;
                    String input_signals = ip_signals.toString().substring(1, ip_signals.toString().length() - 1).replace("\"", "");
                    String output_signals = op_signals.toString().substring(1, op_signals.toString().length() - 1).replace("\"", "");
                    ACBVersionGroup acbgroup = new ACBVersionGroup(acb_id, ivnversion_id, pdbversion_id, vehicleversion_id,
                            vehicle_id, dfm_id, ecu_id, input_signals, output_signals, touchedstatus, button_type, "update");
                    int acbgroup_result = ACBOwnerDB.insertACBVersionGroup(acbgroup);
                }
                ACBOwnerDB.deleteACBVersion_Group(acbversion_id, "update");
            } else {
                System.out.println("else");
                if (previousversion_status == "true" && acbversion_id != 0) {
                    subversion = "yes";
                }
//                Here the variable 'subversion' denotes we are going to create subversion or mainversion
//                Here the variable 'is_acbsubversion' denotes to fine we are passing main version id or subversion id from dropdown
                ACBversion acb = new ACBversion(acbversion_id, status, flag, dtf.format(now), 1, "create", subversion, is_acbsubversion, fully_touchedstatus);
                System.out.println("acbversion_id" + acbversion_id);
                Object[] id_version = ACBOwnerDB.insertACBVersion(acb);
                int acb_id = (int) id_version[0];
                version_name = (float) id_version[1];
                int i = 0;
                for (Object f : features_group) {
                    JSONObject f_group = (JSONObject) f;
                    System.out.println("f_group" + f_group);
                    JSONArray cloned_data = (JSONArray) f_group.get("cloned_data");
                    System.out.println("cloned_data" + cloned_data);
                    ArrayList ip_signals = new ArrayList();
                    ArrayList op_signals = new ArrayList();
                    for (Object c : cloned_data) {
                        JSONObject cl_group = (JSONObject) c;
                        System.out.println("cl_group" + cl_group);
                        System.out.println("cl_group_signal" + cl_group.get("signal"));
                        System.out.println("before the data loop");
                        int signal_id = Integer.parseInt(String.valueOf(cl_group.get("signal")));
//                        int signal_id = (int) cl_group.get("signal");
                        System.out.println("signal_id" + signal_id);
                        String signal_type = (String) cl_group.get("signal_type");
                        System.out.println("signal_type" + signal_type);
                        JSONArray group_data = (JSONArray) cl_group.get("group_data");
                        for (Object g : group_data) {
                            JSONObject g_group = (JSONObject) g;
                            int network_id = Integer.parseInt((String) g_group.get("nt_id"));
                            String network_type = (String) g_group.get("nt_type");
                            int pdbgroup_id = Integer.parseInt((String) g_group.get("pdbgroup_id"));
                            ACBInput_and_Ouput_Signal acb_signal = new ACBInput_and_Ouput_Signal(signal_type, signal_id, network_id, network_type, pdbgroup_id, button_type, "create");
                            int acb_signal_result = ACBOwnerDB.insertACBSignal(acb_signal);
                            if (signal_type.equals("input")) {
                                ip_signals.add(acb_signal_result);
                            } else {
                                op_signals.add(acb_signal_result);
                            }
                        }
                    }
                    System.out.println("ip_signals" + ip_signals);
                    System.out.println("op_signals" + op_signals);
                    int ivnversion_id = Integer.parseInt((String) acbversion_value.get("ivnversion"));
                    int pdbversion_id = Integer.parseInt((String) acbversion_value.get("pdbversion"));
                    int vehicleversion_id = Integer.parseInt((String) acbversion_value.get("vehicleversion"));
                    int vehicle_id = Integer.parseInt((String) acbversion_value.get("vehiclename"));
                    System.out.println("vehicle_id" + vehicle_id);
                    System.out.println("ecu_id" + f_group.get("ecu"));
                    int ecu_id = Integer.parseInt(String.valueOf(f_group.get("ecu")));
                    int dfm_id = Integer.parseInt(String.valueOf(f_group.get("fid")));
                    System.out.println("ecu_id" + ecu_id);
                    boolean touchedstatus = true;
                    String input_signals = ip_signals.toString().substring(1, ip_signals.toString().length() - 1).replace("\"", "");
                    String output_signals = op_signals.toString().substring(1, op_signals.toString().length() - 1).replace("\"", "");
                    ACBVersionGroup acbgroup = new ACBVersionGroup(acb_id, ivnversion_id, pdbversion_id, vehicleversion_id,
                            vehicle_id, dfm_id, ecu_id, input_signals, output_signals, touchedstatus, button_type, "create");
                    int acbgroup_result = ACBOwnerDB.insertACBVersionGroup(acbgroup);
                }
            }
        } catch (Exception ex) {
            System.out.println("entered into catch");
            System.out.println(ex.getMessage());
        }
    }
}

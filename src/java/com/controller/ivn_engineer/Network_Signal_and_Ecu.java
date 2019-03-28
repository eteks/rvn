/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.ivn_engineer;

import com.controller.common.JSONConfigure;
import com.controller.common.VersionType;
import com.controller.notification.NotificationController;
import com.google.gson.Gson;
import com.model.acb_owner.ACBOwnerDB;
import com.model.common.GlobalDeleteVersion;
import com.model.ivn_engineer.Network_Ecu;
import com.model.ivn_engineer.IVNEngineerDB;
import com.model.ivn_engineer.IVNversion;
import com.model.ivn_engineer.Signal;
import com.model.ivn_supervisor.Vehicleversion;
import com.model.ivn_supervisor.VehicleversionDB;
import com.model.pdb_owner.PDBVersionDB;
import com.model.ivn_engineer.IVNNetwork_VehicleModel;
import com.model.ivn_engineer.IVNVersionGroup;
import com.model.ivn_supervisor.Vehicle;
import com.model.pdb_owner.PDBversion;
import com.opensymphony.xwork2.ActionContext;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author ets-2
 */
public class Network_Signal_and_Ecu {

    private Map<String, String> maps = new HashMap<String, String>();
    private Map<String, Integer> dlStatus = new HashMap<String, Integer>();
    private List<Map<String, Object>> vehicleversion_result = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> ivnversion_result = new ArrayList<Map<String, Object>>();
//    private List<Map<String, Object>> domainfeatures_result = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> eculist_result = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> signallist_result = new ArrayList<Map<String, Object>>();
    private Map<String, Object> network_list = new HashMap<String, Object>();
    private Map<String, Object> ivn_map_result = new HashMap<String, Object>();
//    private List<Map<String, Object>> result_data = new ArrayList<Map<String, Object>>();
    public String eculist_result_obj;
    public String signallist_result_obj;
    public String network_list_obj;
    private Map<String, Object> dashboard_result = new HashMap<String, Object>();

    private List<Map<String, Object>> result_data = new ArrayList<Map<String, Object>>();
    public String result_data_obj;

    public String IVNVersionCreationPage() {
        try {
            HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                    .get(ServletActionContext.HTTP_REQUEST);
            System.out.println("request" + request);
            System.out.println("id_value" + request.getParameter("id"));
            System.out.println("action_value" + request.getParameter("action"));
            IVNversion ivnver = new IVNversion(Integer.parseInt(request.getParameter("id")));
            ivn_map_result = IVNEngineerDB.LoadIVNPreviousVehicleversionData(ivnver);
            System.out.println("ivn_map_result" + ivn_map_result);
            result_data_obj = new Gson().toJson(ivn_map_result);
            System.out.println("result_data_obj" + result_data_obj);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        try {
            vehicleversion_result = VehicleversionDB.LoadVehicleVersion("active");
            ivnversion_result = IVNEngineerDB.LoadIVNVersion("all");
            network_list = IVNEngineerDB.LoadNetwork();
            network_list_obj = new Gson().toJson(network_list);

            eculist_result = IVNEngineerDB.LoadECU();
            eculist_result_obj = new Gson().toJson(eculist_result);

            signallist_result = IVNEngineerDB.LoadSignals();
            signallist_result_obj = new Gson().toJson(signallist_result);

            System.out.println("pdbversion_result" + ivnversion_result);
            System.out.println("vehicleversion_result" + vehicleversion_result);

            System.out.println("network_list_obj" + network_list_obj);
            System.out.println("eculist_result_obj" + eculist_result_obj);
            System.out.println("signallist_result_obj" + signallist_result_obj);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            maps.put("status", "Some error occurred !!");
        }
        return "success";
    }

    public String CreateIVNVersion_Attributes() {
        maps.put("status", "Function called");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            Object obj = parser.parse(jsondata);
            JSONObject json = (JSONObject) obj;
            System.out.println("json" + json);
            String nw_type = (String) json.get("network");
            System.out.println("nw_type" + nw_type);
            if (!nw_type.equals("signals")) {
                JSONArray ivn_attribute_data = (JSONArray) json.get("ivn_attribute_data");
                if (!nw_type.equals("ecu")) {
                    for (Object o : ivn_attribute_data) {
                        JSONObject item = (JSONObject) o;
                        String nw_name = (String) item.get("name");
                        String nw_description = (String) item.get("description");
                        Network_Ecu n = new Network_Ecu(nw_name, nw_description, dtf.format(now), nw_type);
                        Map<String, Object> nt_data = IVNEngineerDB.insertNetworkData(n);
                        if (nt_data.isEmpty() == false) {
                            result_data.add(nt_data);
                        }
//                        result_data_obj = new Gson().toJson(result_data);
                        System.out.println("result_data" + result_data);
                    }
                } else {
                    for (Object o : ivn_attribute_data) {
                        JSONObject item = (JSONObject) o;
                        String ecu_name = (String) item.get("name");
                        String ecu_description = (String) item.get("description");
                        Network_Ecu n = new Network_Ecu(ecu_name, ecu_description, nw_type, dtf.format(now), 1);
                        Map<String, Object> nt_data = IVNEngineerDB.insertNetworkData(n);
                        if (nt_data.isEmpty() == false) {
                            result_data.add(nt_data);
                        }
//                        result_data_obj = new Gson().toJson(result_data);
                        System.out.println("result_data" + result_data);
                    }
                }
            } else {
                System.out.println("Signals");
                JSONObject json_obj = (JSONObject) json.get("ivn_attribute_data");
                String signal_name = (String) json_obj.get("name");
                String signal_alias = (String) json_obj.get("alias");
                String signal_description = (String) json_obj.get("description");
                String signal_byteorder = (String) json_obj.get("byteorder");
                String signal_unit = (String) json_obj.get("unit");
                String signal_valuetype = (String) json_obj.get("valuetype");
                String signal_valuetable = (String) json_obj.get("valuetable");
                String signal_can_id = (String) json_obj.get("can");
                String signal_lin_id = (String) json_obj.get("lin");
                String signal_hw_id = (String) json_obj.get("hardware");
                JSONArray signal_tags = (JSONArray) json_obj.get("tags");
                System.out.println("signal_tags"+signal_tags);
                System.out.println("int value started");
                int signal_length = (json_obj.get("length") != null ) ? Integer.parseInt((String) json_obj.get("length")) : 0;
                System.out.println("signal_length"+signal_length);
                int signal_initvalue = (json_obj.get("initvalue") != null) ? Integer.parseInt((String) json_obj.get("initvalue")) : 0; 
                System.out.println("signal_initvalue"+signal_initvalue);
                double signal_factor = (json_obj.get("factor")!= null) ? Double.parseDouble((String) json_obj.get("factor")) : 0;
                System.out.println("signal_factor"+signal_factor);
                int signal_offset = (json_obj.get("offset")!= null) ? Integer.parseInt((String) json_obj.get("offset")) : 0;
                System.out.println("signal_offset"+signal_offset);
                int signal_minimum = (json_obj.get("minimum")!= null) ? Integer.parseInt((String) json_obj.get("minimum")) : 0;
                System.out.println("signal_minimum"+signal_minimum);
                int signal_maximum = (json_obj.get("minimum")!= null) ? Integer.parseInt((String) json_obj.get("maximum")): 0;
                System.out.println("signal_maximum"+signal_maximum);

                Signal s = new Signal(signal_name, signal_alias, signal_description, signal_length,
                        signal_byteorder, signal_unit, signal_valuetype, signal_initvalue,
                        signal_factor, signal_offset, signal_minimum, signal_maximum,
                        signal_valuetable, signal_can_id, signal_lin_id, signal_hw_id,signal_tags,
                        dtf.format(now), 1);
                result_data = IVNEngineerDB.insertSignalData(s);
                System.out.println("result_data" + result_data);
                //                String ecu_name = (String) json.get("name");
                //                String ecu_description = (String) json.get("description");
                //                Network_Ecu n = new Network_Ecu(ecu_name,ecu_description,nw_type,dtf.format(now),1);
                //                int result = IVNEngineerDB.insertNetworkData(n);
            }
            maps.put("status", "Record Inserted Successfully");
        } catch (Exception ex) {
            System.out.println("entered into catch");
            System.out.println(ex.getMessage());
            maps.put("status", "Some error occurred !!");
        }
        return "success";
    }

    public String CreateIVNVersion() {
        System.out.println("CreateIVNVersion");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();
//        String button_type = (String) json.get("button_type");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        boolean status = (boolean) false;
        int ivnversion_id = 0;
        float version_name;
        String previousversion_status = null;
        String previousversion_flag = null;
        boolean flag;
        List<Map<String, Object>> network_data = new ArrayList<Map<String, Object>>();
        try {
            Object obj = parser.parse(jsondata);
            JSONObject json = (JSONObject) obj;
            System.out.println("pdbdata" + json);
            JSONObject ivnversion_value = (JSONObject) json.get("ivnversion");
            JSONObject ivndata_list = (JSONObject) json.get("ivndata_list");
//            JSONArray ivndata_list = (JSONArray) json.get("ivndata_list");
            System.out.println("ivndata_list" + ivndata_list);
            String button_type = (String) json.get("button_type");
            String notification_to = (String) json.get("notification_to");
            if (button_type.equals("save")) {
                flag = false;
            } else {
                flag = true;
            }
            if (ivnversion_value != null && ivnversion_value.containsKey("ivnversion")) {
                ivnversion_id = Integer.parseInt((String) ivnversion_value.get("ivnversion"));
            }

            if (ivnversion_value != null && ivnversion_value.containsKey("status") && button_type.equals("submit")) {
                status = (boolean) ivnversion_value.get("status");
            }

            if (ivnversion_id != 0) {
                //Get the data of previous vehicle version by id
                int ivnver_id = ivnversion_id;
                IVNversion iver = new IVNversion(ivnver_id);
//                private List<Map<String, Object>> vehmod_map_result = new ArrayList<Map<String, Object>>();
                List<Map<String, Object>> ivn_previous_result = IVNEngineerDB.LoadIVNPreviousVehicleversionStatus(iver);
                System.out.println("pdb_previous_status" + ivn_previous_result);
                previousversion_status = String.valueOf(ivn_previous_result.get(0).get("status"));
                previousversion_flag = String.valueOf(ivn_previous_result.get(0).get("flag"));
            }
            System.out.println(previousversion_status);
            System.out.println(previousversion_status);
            System.out.println(button_type);
            System.out.println(ivnversion_id);
//            if(previousversion_status != null && button_type.equals("save") && pdbversion_id != 0){
            if (previousversion_status == "false" && ivnversion_id != 0) {
//                System.out.println("Ready to update");
                maps.put("status", "Ready to update");
                IVNversion iv = new IVNversion(ivnversion_id, status, flag, dtf.format(now), "update");
                System.out.println("ivnversion_id" + ivnversion_id);
                Object[] id_version = IVNEngineerDB.insertIVNVersion(iv);
                int ivn_id = (int) id_version[0];
                version_name = (float) id_version[1];
                JSONArray ivndata_list_can = (JSONArray) ivndata_list.get("can");
                JSONArray ivndata_list_lin = (JSONArray) ivndata_list.get("lin");
                JSONArray ivndata_list_hardware = (JSONArray) ivndata_list.get("hardware");
//                JSONArray ivndata_list_signal = (JSONArray) ivndata_list.get("signal");
//                JSONArray ivndata_list_ecu = (JSONArray) ivndata_list.get("ecu");
                Map<String, Object> columns = new HashMap<String, Object>();

                ArrayList al_can = new ArrayList();
                int i = 0;
                System.out.println("ivndata_list_can" + ivndata_list_can);
                for (Object o : ivndata_list_can) {
                    JSONObject ivndata_can = (JSONObject) o;
                    System.out.println("pdbdata" + ivndata_can);
                    int vmm_id = Integer.parseInt((String) ivndata_can.get("vmm_id"));
                    int network_id = Integer.parseInt((String) ivndata_can.get("network_id"));
                    Boolean av_status = (Boolean) ivndata_can.get("status");
                    String network_type = (String) ivndata_can.get("network_type");
                    IVNNetwork_VehicleModel invm = new IVNNetwork_VehicleModel(ivn_id, network_id, vmm_id, av_status, network_type, button_type, "update");
                    int ivn_canmodel_id = IVNEngineerDB.insertIVNNetworkModel(invm);
                    System.out.println("ivn_canmodel_id" + ivn_canmodel_id);
                    if (ivn_canmodel_id != 0) {
                        al_can.add(ivn_canmodel_id);
                    }
                    if (i++ == ivndata_list_can.size() - 1) {
                        IVNEngineerDB.deleteIVN_network_models(ivn_id, network_type);
                    }
                }

                System.out.println("ivndata_list_lin" + ivndata_list_lin);
                ArrayList al_lin = new ArrayList();
                int j = 0;
                for (Object o : ivndata_list_lin) {
                    JSONObject ivndata_lin = (JSONObject) o;
                    System.out.println("ivndata_lin" + ivndata_lin);
                    int vmm_id = Integer.parseInt((String) ivndata_lin.get("vmm_id"));
                    int network_id = Integer.parseInt((String) ivndata_lin.get("network_id"));
                    Boolean av_status = (Boolean) ivndata_lin.get("status");
                    String network_type = (String) ivndata_lin.get("network_type");
                    IVNNetwork_VehicleModel invm = new IVNNetwork_VehicleModel(ivn_id, network_id, vmm_id, av_status, network_type, button_type, "update");
                    int ivn_linmodel_id = IVNEngineerDB.insertIVNNetworkModel(invm);
                    if (ivn_linmodel_id != 0) {
                        al_lin.add(ivn_linmodel_id);
                    }
                    if (j++ == ivndata_list_lin.size() - 1) {
                        IVNEngineerDB.deleteIVN_network_models(ivn_id, network_type);
                    }
                }
                ArrayList al_hw = new ArrayList();
                int k = 0;
                for (Object o : ivndata_list_hardware) {
                    JSONObject ivndata_hw = (JSONObject) o;
                    System.out.println("ivndata_hw" + ivndata_hw);
                    int vmm_id = Integer.parseInt((String) ivndata_hw.get("vmm_id"));
                    int network_id = Integer.parseInt((String) ivndata_hw.get("network_id"));
                    Boolean av_status = (Boolean) ivndata_hw.get("status");
                    String network_type = (String) ivndata_hw.get("network_type");
                    IVNNetwork_VehicleModel invm = new IVNNetwork_VehicleModel(ivn_id, network_id, vmm_id, av_status, network_type, button_type, "update");
                    int ivn_hwmodel_id = IVNEngineerDB.insertIVNNetworkModel(invm);
                    if (ivn_hwmodel_id != 0) {
                        al_hw.add(ivn_hwmodel_id);
                    }
                    if (k++ == ivndata_list_hardware.size() - 1) {
                        IVNEngineerDB.deleteIVN_network_models(ivn_id, network_type);
                    }
                }
                columns.put("can", al_can);
                columns.put("lin", al_lin);
                columns.put("hardware", al_hw);
                columns.put("signal", ivndata_list.get("signal"));
                columns.put("ecu", ivndata_list.get("ecu"));
                System.out.println("all_data" + columns);
                String can_result = columns.get("can").toString().substring(1, columns.get("can").toString().length() - 1);
                System.out.println("can_result" + can_result);
                String lin_result = columns.get("lin").toString().substring(1, columns.get("lin").toString().length() - 1);
                System.out.println("lin_result" + lin_result);
                String hw_result = columns.get("hardware").toString().substring(1, columns.get("hardware").toString().length() - 1);
                System.out.println("hw_result" + hw_result);
                String signal_result = columns.get("signal").toString().substring(1, columns.get("signal").toString().length() - 1).replace("\"", "");
                System.out.println("signal_result" + signal_result);
                String ecu_result = columns.get("ecu").toString().substring(1, columns.get("ecu").toString().length() - 1).replace("\"", "");
                System.out.println("ecu_result" + ecu_result);
                IVNVersionGroup ig = new IVNVersionGroup(ivn_id, can_result, lin_result, hw_result,
                        signal_result, ecu_result, button_type, "update");
                int ivngroup_id = IVNEngineerDB.insertIVNVersionGroup(ig);
                if (button_type.equals("save")) {
                    if (previousversion_flag == "true") {
                        maps.put("status", "Record updated in same version and stored as Temporary");
                    } else {
                        maps.put("status", "Record updated successfully in same Temporary version");
                    }
                } else {
                    System.out.println("previousversion_flag" + previousversion_flag);
                    if (status) {
                        new NotificationController().createNotification(VersionType.ivnVersion.getVersionCode(), version_name, dtf.format(now), notification_to);
                    }
                    if (previousversion_flag == "false") {
                        maps.put("status", "Record updated in same version and stored as permanent");
                    } else {
                        maps.put("status", "Record updated successfully in same Permanent version");
                    }
                }
            } else {
                System.out.println("else");
                IVNversion iv = new IVNversion(ivnversion_id, status, flag, dtf.format(now), "create");
                System.out.println("ivnversion_id" + ivnversion_id);
                Object[] id_version = IVNEngineerDB.insertIVNVersion(iv);
                int ivn_id = (int) id_version[0];
                version_name = (float) id_version[1];
                JSONArray ivndata_list_can = (JSONArray) ivndata_list.get("can");
                JSONArray ivndata_list_lin = (JSONArray) ivndata_list.get("lin");
                JSONArray ivndata_list_hardware = (JSONArray) ivndata_list.get("hardware");
//                JSONArray ivndata_list_signal = (JSONArray) ivndata_list.get("signal");
//                JSONArray ivndata_list_ecu = (JSONArray) ivndata_list.get("ecu");
                Map<String, Object> columns = new HashMap<String, Object>();
                ArrayList al_can = new ArrayList();
                int i = 0;
                for (Object o : ivndata_list_can) {
                    JSONObject ivndata_can = (JSONObject) o;
                    System.out.println("pdbdata" + ivndata_can);
                    int vmm_id = Integer.parseInt((String) ivndata_can.get("vmm_id"));
                    int network_id = Integer.parseInt((String) ivndata_can.get("network_id"));
                    Boolean av_status = (Boolean) ivndata_can.get("status");
                    String network_type = (String) ivndata_can.get("network_type");
                    IVNNetwork_VehicleModel invm = new IVNNetwork_VehicleModel(ivn_id, network_id, vmm_id, av_status, network_type, button_type, "create");
                    int ivn_canmodel_id = IVNEngineerDB.insertIVNNetworkModel(invm);
                    System.out.println("ivn_canmodel_id" + ivn_canmodel_id);
                    al_can.add(ivn_canmodel_id);
//                    if(i++ == ivndata_list_can.size() - 1){
//                       columns.put("can",al_can);
//                    }
                }
                ArrayList al_lin = new ArrayList();
                int j = 0;
                for (Object o : ivndata_list_lin) {
                    JSONObject ivndata_lin = (JSONObject) o;
                    System.out.println("ivndata_lin" + ivndata_lin);
                    int vmm_id = Integer.parseInt((String) ivndata_lin.get("vmm_id"));
                    int network_id = Integer.parseInt((String) ivndata_lin.get("network_id"));
                    Boolean av_status = (Boolean) ivndata_lin.get("status");
                    String network_type = (String) ivndata_lin.get("network_type");
                    IVNNetwork_VehicleModel invm = new IVNNetwork_VehicleModel(ivn_id, network_id, vmm_id, av_status, network_type, button_type, "create");
                    int ivn_linmodel_id = IVNEngineerDB.insertIVNNetworkModel(invm);
                    al_lin.add(ivn_linmodel_id);
//                    if(j++ == ivndata_list_can.size() - 1){
//                       columns.put("lin",al_lin);                      
//                    }
                }
                ArrayList al_hw = new ArrayList();
                int k = 0;
                for (Object o : ivndata_list_hardware) {
                    JSONObject ivndata_hw = (JSONObject) o;
                    System.out.println("ivndata_hw" + ivndata_hw);
                    int vmm_id = Integer.parseInt((String) ivndata_hw.get("vmm_id"));
                    int network_id = Integer.parseInt((String) ivndata_hw.get("network_id"));
                    Boolean av_status = (Boolean) ivndata_hw.get("status");
                    String network_type = (String) ivndata_hw.get("network_type");
                    IVNNetwork_VehicleModel invm = new IVNNetwork_VehicleModel(ivn_id, network_id, vmm_id, av_status, network_type, button_type, "create");
                    int ivn_hwmodel_id = IVNEngineerDB.insertIVNNetworkModel(invm);
                    al_hw.add(ivn_hwmodel_id);
//                    if(k++ == ivndata_list_can.size() - 1){
//                       columns.put("hardware",al_hw);                     
//                    }
                }
                columns.put("can", al_can);
                columns.put("lin", al_lin);
                columns.put("hardware", al_hw);
                columns.put("signal", ivndata_list.get("signal"));
                columns.put("ecu", ivndata_list.get("ecu"));
                System.out.println("all_data" + columns);
                String can_result = columns.get("can").toString().substring(1, columns.get("can").toString().length() - 1);
                System.out.println("can_result" + can_result);
                String lin_result = columns.get("lin").toString().substring(1, columns.get("lin").toString().length() - 1);
                System.out.println("lin_result" + lin_result);
                String hw_result = columns.get("hardware").toString().substring(1, columns.get("hardware").toString().length() - 1);
                System.out.println("hw_result" + hw_result);
                String signal_result = columns.get("signal").toString().substring(1, columns.get("signal").toString().length() - 1).replace("\"", "");
                System.out.println("signal_result" + signal_result);
                String ecu_result = columns.get("ecu").toString().substring(1, columns.get("ecu").toString().length() - 1).replace("\"", "");
                System.out.println("ecu_result" + ecu_result);
                IVNVersionGroup ig = new IVNVersionGroup(ivn_id, can_result, lin_result, hw_result,
                        signal_result, ecu_result, button_type, "create");
                int ivngroup_id = IVNEngineerDB.insertIVNVersionGroup(ig);
                System.out.println("ivngroup_id" + ivngroup_id);
                if (button_type.equals("save")) {
                    maps.put("status", "New Temporary IVN Version Created Successfully");
                } else {
                    if (status) {
                        new NotificationController().createNotification(VersionType.ivnVersion.getVersionCode(), version_name, dtf.format(now), notification_to);
                    }
                    maps.put("status", "New Permanent IVN Version Created Successfully");
                }
            }
        } catch (Exception ex) {
            System.out.println("entered into catch");
            System.out.println(ex.getMessage());
            maps.put("status", "Some error occurred !!");
        }
        return "success";
    }
    
    public String DeleteIVNVersion() {
        System.out.println("deleteivnversion");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();
        try {
            Object obj = parser.parse(jsondata);
            JSONObject json = (JSONObject) obj;
            System.out.println("json" + json);
            int versionId = Integer.parseInt((String) json.get("id"));
            IVNEngineerDB.deleteDependentIVNVersion(versionId);
            if(!GlobalDeleteVersion.deleteVersion("ivnversion", versionId)){
                dlStatus.put("status", 1);
            }
            else{
                dlStatus.put("status", 0);
            }
        } catch (Exception ex) {
            System.out.println("entered into catch");
            System.out.println(ex.getMessage());
            maps.put("status", "Some error occurred !!");
        }
        return "success";
    }

    public String LoadIVNPreviousVehicleversionData() throws ParseException {
        System.out.println("LoadIVNPreviousVehicleversionData controller");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();

        Object obj = parser.parse(jsondata);
        JSONObject json = (JSONObject) obj;
        int ivnver_id = Integer.parseInt((String) json.get("ivnversion_id"));
        IVNversion ivnver = new IVNversion(ivnver_id);

        try {
            ivn_map_result = IVNEngineerDB.LoadIVNPreviousVehicleversionData(ivnver);
//            pdb_map_result_obj = new Gson().toJson(pdb_map_result);
//                vehmod_map_result_obj =  Gson().toJSON(vehmod_map_result);
            System.out.println("ivn_map_result" + ivn_map_result);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            maps.put("status", "Some error occurred !!");
        }
//            return vehmod_map_result;
//            System.out.println("Result"+vehmod_map_result);
        return "success";
    }

    public String GetIVNVersion_Listing() {
        System.out.println("GetIVNVersion_Listing controller");
        try {
            result_data = (List<Map<String, Object>>) IVNEngineerDB.GetIVNVersion_Listing();
            result_data_obj = new Gson().toJson(result_data);
//                vehmod_map_result_obj =  Gson().toJSON(vehmod_map_result);
            System.out.println("oject" + result_data_obj);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            maps.put("status", "Some error occurred !!");
        }
//            return vehmod_map_result;
//            System.out.println("Result"+vehmod_map_result);
        return "success";
    }

    public String GetSignal_Listing() {
        System.out.println("GetSignal_Listing controller");
        Signal veh = new Signal();
        try {
            result_data = (List<Map<String, Object>>) IVNEngineerDB.GetSignal_Listing(veh);
            result_data_obj = new Gson().toJson(result_data);

            network_list = IVNEngineerDB.LoadNetwork();
            network_list_obj = new Gson().toJson(network_list);

//                result_data = (List<Map<String, Object>>) ACBOwnerDB.GetACBVersion_Listing();
//                result_data_obj = new Gson().toJson(result_data);
//                vehmod_map_result_obj =  Gson().toJSON(vehmod_map_result);
            System.out.println("oject" + result_data_obj);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            maps.put("status", "Some error occurred !!");
        }
//            return vehmod_map_result;
//            System.out.println("Result"+vehmod_map_result);
        return "success";
    }
    public String GetIVN_Dashboarddata(){
        try {
            dashboard_result =  IVNEngineerDB.GetIVN_Dashboarddata();
            System.out.println("dashboard_result"+dashboard_result);
        }
        catch (Exception ex) { 
            System.out.println("entered into catch");
            System.out.println(ex.getMessage()); 
            maps.put("status", "Some error occurred !!"); 
        }   
        return "success";
    }
    public Map<String, String> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, String> maps) {
        this.maps = maps;
    }
    
    public Map<String, Integer> getDlStatus() {
            return dlStatus;
    }
    public void setDlStatus(Map<String, Integer> maps) {
            this.dlStatus = dlStatus;
    }

    public List<Map<String, Object>> getVehicleversion_result() {
        return vehicleversion_result;
    }

    public void setVehicleversion_result(List<Map<String, Object>> vehicleversion_result) {
        this.vehicleversion_result = vehicleversion_result;
    }

    public List<Map<String, Object>> getIvnversion_result() {
        return ivnversion_result;
    }

    public void setIvnversion_result(List<Map<String, Object>> ivnversion_result) {
        this.ivnversion_result = ivnversion_result;
    }

    public String getEculist_result_obj() {
        return eculist_result_obj;
    }

    public void setEculist_result_obj(String eculist_result_obj) {
        this.eculist_result_obj = eculist_result_obj;
    }

    public String getSignallist_result_obj() {
        return signallist_result_obj;
    }

    public void setSignallist_result_obj(String signallist_result_obj) {
        this.signallist_result_obj = signallist_result_obj;
    }
//    public List<Map<String, Object>> getDomainFeatures_result() {
//            return domainfeatures_result;
//    }
//
//    public void setDomainFeatures_result(List<Map<String, Object>> domainfeatures_result) {
//            this.domainfeatures_result = domainfeatures_result;
//    }

    public Map<String, Object> getNetwork_list() {
        return network_list;
    }

    public void setNetwork_list(Map<String, Object> network_list) {
        this.network_list = network_list;
    }
//    

    public List<Map<String, Object>> getResult_data() {
        return result_data;
    }

    public void setResult_data(List<Map<String, Object>> result_data) {
        this.result_data = result_data;
    }

    public String getNetwork_list_obj() {
        return network_list_obj;
    }

    public void setNetwork_list_obj(String network_list_obj) {
        this.network_list_obj = network_list_obj;
    }

    public Map<String, Object> getIvn_map_result() {
        return ivn_map_result;
    }

    public void setIvn_map_result(Map<String, Object> ivn_map_result) {
        this.ivn_map_result = ivn_map_result;
    }

    public String getResult_data_obj() {
        return result_data_obj;
    }

    public void setResult_data_obj(String result_data_obj) {
        this.result_data_obj = result_data_obj;
    }
    public Map<String, Object> getDashboard_result() {
            return dashboard_result;
    }
    public void setDashboard_result(Map<String, Object> dashboard_result) {
            this.dashboard_result = dashboard_result;
    }
}

package com.controller.acb_owner;

import com.controller.common.JSONConfigure;
import com.controller.common.VersionType;
import com.controller.notification.NotificationController;
import com.google.gson.Gson;
import com.model.acb_owner.ACBInput_and_Ouput_Signal;
import com.model.acb_owner.ACBOwnerDB;
import com.model.acb_owner.ACBVersionGroup;
import com.model.acb_owner.ACBversion;
import com.model.ivn_engineer.IVNEngineerDB;
import com.model.ivn_engineer.IVNNetwork_VehicleModel;
import com.model.ivn_engineer.IVNVersionGroup;
import com.model.ivn_engineer.IVNversion;
import com.model.ivn_engineer.Signal;
import com.model.ivn_supervisor.Vehicle_and_Model_Mapping;
import com.model.ivn_supervisor.VehicleversionDB;
import com.model.pdb_owner.PDBVersionDB;
import com.model.pdb_owner.PDBversion;
import com.model.system_owner.SystemOwnerDB;
import com.opensymphony.xwork2.ActionContext;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ets-2
 */
public class Input_and_Output_Signal {

    private Map<String, String> maps = new HashMap<String, String>();
    private List<Map<String, Object>> pdbversion_result = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> ivnversion_result = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> acbversion_result = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> vehicleversion_result = new ArrayList<Map<String, Object>>();
    private Map<String, Object> pdb_map_result = new HashMap<String, Object>();
    private Map<String, Object> ivn_map_result = new HashMap<String, Object>();
    private Map<String, Object> result_data = new HashMap<String, Object>();
    private List<Map<String, Object>> listing_result_data = new ArrayList<Map<String, Object>>();
    public String listing_result_data_obj;
    public String result_data_obj;
    public String signaltags_obj;
    private Map<String, Object> dashboard_result = new HashMap<String, Object>();
    private List<Map<String, Object>> signaltags = new ArrayList<Map<String, Object>>();

    public String ACBVersionCreationPage() {
        System.out.println("Entered");
        System.out.println("ACBVersionCreationPage");
        //This will execute if url contains parameter(id and action-edit, view)
        try {
            HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                    .get(ServletActionContext.HTTP_REQUEST);
            System.out.println("request" + request);
            System.out.println("id_value" + request.getParameter("id"));
            System.out.println("action_value" + request.getParameter("action"));
//            vehicleversion_result = VehicleversionDB.LoadVehicleVersion("active");
            ACBversion acbver = new ACBversion(Integer.parseInt(request.getParameter("id")));
            result_data = ACBOwnerDB.LoadACBPreviousVehicleversionData(acbver);
            System.out.println("acb_map_result" + result_data);
            result_data_obj = new Gson().toJson(result_data);
            System.out.println("result_data_obj" + result_data_obj);
//            System.out.println("pdbversion_result"+pdbversion_result);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        try {
            pdbversion_result = PDBVersionDB.LoadPDBVersion("active");
            ivnversion_result = IVNEngineerDB.LoadIVNVersion("active");
            acbversion_result = ACBOwnerDB.LoadACBVersion("all");
            vehicleversion_result = VehicleversionDB.LoadVehicleVersion("active");
            System.out.println("pdbversion_result" + pdbversion_result);
            signaltags = ACBOwnerDB.LoadSignalTags();
            System.out.println("signaltags" + signaltags);
            signaltags_obj = new Gson().toJson(signaltags);
            System.out.println("signaltags_obj" + signaltags_obj);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            maps.put("status", "Some error occurred !!");
        }
        return "success";
    }

    public String LoadPDBDataForACBVersion() throws ParseException {
        System.out.println("LoadPDBDataForACBVersion controller");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();

        Object obj = parser.parse(jsondata);
        JSONObject json = (JSONObject) obj;
        int pdbver_id = Integer.parseInt((String) json.get("pdbversion_id"));
        PDBversion pdbver = new PDBversion(pdbver_id);

        try {
            pdb_map_result = ACBOwnerDB.LoadPDBDataForACBVersion(pdbver);
//            pdb_map_result_obj = new Gson().toJson(pdb_map_result);
//                vehmod_map_result_obj =  Gson().toJSON(vehmod_map_result);
            System.out.println("pdb_map_result" + pdb_map_result);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            maps.put("status", "Some error occurred !!");
        }
//            return vehmod_map_result;
//            System.out.println("Result"+vehmod_map_result);
        return "success";
    }

    public String LoadIVNDataForACBVersion() throws ParseException {
        System.out.println("LoadIVNDataForACBVersion controller");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();

        Object obj = parser.parse(jsondata);
        JSONObject json = (JSONObject) obj;
        int ivnver_id = Integer.parseInt((String) json.get("ivnversion_id"));
        IVNversion ivnver = new IVNversion(ivnver_id);

        try {
            ivn_map_result = ACBOwnerDB.LoadIVNDataForACBVersion(ivnver);
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

    public String LoadPDBandIVN_Version() throws ParseException {
        System.out.println("LoadPDBandIVN_Version controller");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();

        Object obj = parser.parse(jsondata);
        JSONObject json = (JSONObject) obj;
        System.out.println("json_data" + json);
        int vehver_id = Integer.parseInt((String) json.get("vehicleversion_id"));
        int vehicle_id = Integer.parseInt((String) json.get("vehicle_id"));
        System.out.println("vehver_id" + vehver_id);
        System.out.println("vehicle_id" + vehicle_id);
        Vehicle_and_Model_Mapping veh_mod_map = new Vehicle_and_Model_Mapping(vehver_id, vehicle_id);
//        IVNversion ivnver = new IVNversion(ivnver_id);
        System.out.println("before try2");
        try {
            result_data = ACBOwnerDB.LoadPDBandIVN_Version(veh_mod_map);
//            pdb_map_result_obj = new Gson().toJson(pdb_map_result);
//                vehmod_map_result_obj =  Gson().toJSON(vehmod_map_result);
            System.out.println("result_data" + result_data);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            maps.put("status", "Some error occurred !!");
        }
//            return vehmod_map_result;
//            System.out.println("Result"+vehmod_map_result);
        return "success";
    }

    public String CreateACBVersion(){
        System.out.println("CreateACBVersion");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();
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
            Object obj = parser.parse(jsondata);
            JSONObject json = (JSONObject) obj;
            System.out.println("acbdata" + json);
            JSONObject acbversion_value = (JSONObject) json.get("acbversion");
            JSONArray features_group = (JSONArray) json.get("acbdata_list");
            String button_type = (String) json.get("button_type");
            String notification_to = (String) json.get("notification_to");
            boolean fully_touchedstatus = (boolean) json.get("features_fully_touchedstatus");
            if(button_type.equals("save")){
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

            if (acbversion_value != null && acbversion_value.containsKey("status") && button_type.equals("submit")) {
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
                ACBversion acb = new ACBversion(acbversion_id,status,flag,dtf.format(now),"update",subversion,is_acbsubversion,fully_touchedstatus);
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

                    if (button_type.equals("save")) {
                        if (previousversion_flag == "true") {
                            maps.put("status", "Record updated in same version and stored as Temporary");
                        } else {
                            maps.put("status", "Record updated successfully in same Temporary version");
                        }
                    } else {
                        System.out.println("previousversion_flag" + previousversion_flag);
                        if (status) {
                            new NotificationController().createNotification(VersionType.acbVersion.getVersionCode(), version_name, dtf.format(now), notification_to);
                        }
                        if (previousversion_flag == "false") {
                            maps.put("status", "Record updated in same version and stored as permanent");
                        } else {
                            maps.put("status", "Record updated successfully in same Permanent version");
                        }
                    }
                }
                ACBOwnerDB.deleteACBVersion_Group(acbversion_id, "update");
            } else {
                System.out.println("else");
                if (previousversion_status == "true" && acbversion_id != 0) {
                    subversion = "yes";
                }
//                Here the variable 'subversion' denotes we are going to create subversion or mainversion
//                Here the variable 'is_acbsubversion' denotes to fine we are passing main version id or subversion id from dropdown
                ACBversion acb = new ACBversion(acbversion_id,status,flag,dtf.format(now),"create",subversion,is_acbsubversion,fully_touchedstatus);
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
                    if (i++ == features_group.size() - 1) {
                        System.out.println("final loop");
                        if (button_type.equals("save")) {
                            System.out.println("subversion_value" + subversion);
                            if (subversion != null && subversion.equals("yes")) {
                                maps.put("status", "New Temporary ACB Sub Version Created Successfully");
                            } else {
                                maps.put("status", "New Temporary ACB Version Created Successfully");
                            }
                        } else {
                            if (status) {
                                new NotificationController().createNotification(VersionType.acbVersion.getVersionCode(), version_name, dtf.format(now), notification_to);
                            }
                            if (subversion != null && subversion.equals("yes")) {
                                maps.put("status", "New Permanent ACB Sub Version Created Successfully");
                            } else {
                                maps.put("status", "New Permanent ACB Version Created Successfully");
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("entered into catch");
            System.out.println(ex.getMessage());
            maps.put("status", "Some error occurred !!");
        }
        return "success";
    }

    public String LoadACBPreviousVehicleversionData() throws ParseException {
        System.out.println("LoadACBPreviousVehicleversionData controller");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();

        Object obj = parser.parse(jsondata);
        JSONObject json = (JSONObject) obj;
        int acbver_id = Integer.parseInt((String) json.get("acbversion_id"));
        ACBversion acbver = new ACBversion(acbver_id);

        try {
            result_data = ACBOwnerDB.LoadACBPreviousVehicleversionData(acbver);
//            pdb_map_result_obj = new Gson().toJson(pdb_map_result);
//                vehmod_map_result_obj =  Gson().toJSON(vehmod_map_result);
            System.out.println("result_data" + result_data);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            maps.put("status", "Some error occurred !!");
        }
//            return vehmod_map_result;
//            System.out.println("Result"+vehmod_map_result);
        return "success";
    }

    public String GetACBVersion_Listing() {
        System.out.println("GetACBVersion_Listing controller");
        Signal veh = new Signal();
        try {
            listing_result_data = (List<Map<String, Object>>) ACBOwnerDB.GetACBVersion_Listing();
            listing_result_data_obj = new Gson().toJson(listing_result_data);

//                vehmod_map_result_obj =  Gson().toJSON(vehmod_map_result);
            System.out.println("oject" + listing_result_data_obj);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            maps.put("status", "Some error occurred !!");
        }
//            return vehmod_map_result;
//            System.out.println("Result"+vehmod_map_result);
        return "success";
    }
    
    public String GetACB_Dashboarddata(){
        try {
            dashboard_result = ACBOwnerDB.GetACB_Dashboarddata();
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

    public List<Map<String, Object>> getPdbversion_result() {
        return pdbversion_result;
    }

    public void setPdbversion_result(List<Map<String, Object>> pdbversion_result) {
        this.pdbversion_result = pdbversion_result;
    }

    public List<Map<String, Object>> getIvnversion_result() {
        return ivnversion_result;
    }

    public void setIvnversion_result(List<Map<String, Object>> ivnversion_result) {
        this.ivnversion_result = ivnversion_result;
    }

    public List<Map<String, Object>> getAcbversion_result() {
        return acbversion_result;
    }

    public void setAcbversion_result(List<Map<String, Object>> acbversion_result) {
        this.acbversion_result = acbversion_result;
    }

    public Map<String, Object> getPdb_map_result() {
        return pdb_map_result;
    }

    public void setPdb_map_result(Map<String, Object> pdb_map_result) {
        this.pdb_map_result = pdb_map_result;
    }

    public Map<String, Object> getIvn_map_result() {
        return ivn_map_result;
    }

    public void setIvn_map_result(Map<String, Object> ivn_map_result) {
        this.ivn_map_result = ivn_map_result;
    }

    public List<Map<String, Object>> getVehicleversion_result() {
        return vehicleversion_result;
    }

    public void setVehicleversion_result(List<Map<String, Object>> vehicleversion_result) {
        this.vehicleversion_result = vehicleversion_result;
    }

    public Map<String, Object> getResult_data() {
        return result_data;
    }

    public void setResult_data(Map<String, Object> result_data) {
        this.result_data = result_data;
    }

    public List<Map<String, Object>> getListing_result_data() {
        return listing_result_data;
    }

    public void setListing_result_data(List<Map<String, Object>> listing_result_data) {
        this.listing_result_data = listing_result_data;
    }

    public String getListing_result_data_obj() {
        return listing_result_data_obj;
    }

    public void setListing_result_data_obj(String listing_result_data_obj) {
        this.listing_result_data_obj = listing_result_data_obj;
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
    public String getSignaltags_obj() {
        return signaltags_obj;
    }

    public void setSignaltags_obj(String signaltags_obj) {
        this.signaltags_obj = signaltags_obj;
    }
}

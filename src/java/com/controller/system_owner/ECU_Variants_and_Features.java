/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.system_owner;

import com.controller.common.JSONConfigure;
import com.controller.common.VersionType;
import com.controller.notification.NotificationController;
import com.google.gson.Gson;
import com.model.acb_owner.ACBInput_and_Ouput_Signal;
import com.model.acb_owner.ACBOwnerDB;
import com.model.acb_owner.ACBVersionGroup;
import com.model.acb_owner.ACBversion;
import com.model.ivn_engineer.IVNEngineerDB;
import com.model.ivn_engineer.Signal;
import com.model.ivn_supervisor.ModelVersionGroup;
import com.model.ivn_supervisor.Modelversion;
import com.model.ivn_supervisor.Vehicle_and_Model_Mapping;
import com.model.ivn_supervisor.VehicleversionDB;
import com.model.pdb_owner.Domain;
import com.model.pdb_owner.Domain_and_Features_Mapping;
import com.model.pdb_owner.Features;
import com.model.pdb_owner.PDBVersionDB;
import com.model.pdb_owner.PDBVersionGroup;
import com.model.system_owner.ECU_and_Variants_Mapping;
import com.model.system_owner.SystemOwnerDB;
import com.model.system_owner.SystemVersionGroup;
import com.model.system_owner.Systemversion;
import com.model.system_owner.Variants;
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

/**
 *
 * @author ets-2
 */
public class ECU_Variants_and_Features {
    private Map<String, String> maps = new HashMap<String, String>();
    private Map<String, Object> acb_result_data = new HashMap<String, Object>();
    private Map<String, Object> system_result_data = new HashMap<String, Object>();
    private Map<String, Object> dashboard_result = new HashMap<String, Object>();
    private List<Map<String, Object>> result_data = new ArrayList<Map<String, Object>>();
    public String result_data_obj;
    private List<Map<String, Object>> vehicleversion_result = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> acbversion_result = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> systemversion_result = new ArrayList<Map<String, Object>>();
    public String GetECU_Listing() 
    {
        System.out.println("GetECU_Listing controller");
//        Features fea = new Features();
        try{
            result_data = (List<Map<String, Object>>) SystemOwnerDB.GetECU_Listing();
            result_data_obj = new Gson().toJson(result_data);
//            vehmod_map_result_obj = new Gson().toJson(vehmod_map_result);
//                vehmod_map_result_obj =  Gson().toJSON(vehmod_map_result);
            System.out.println("oject"+result_data_obj);
        }
        catch (Exception ex) { 
            System.out.println(ex.getMessage()); 
            maps.put("status", "Some error occurred !!"); 
        }
//            return vehmod_map_result;
//            System.out.println("Result"+vehmod_map_result);
        return "success";
    }
    public String CreateVariants() throws ParseException { 
        System.out.println("CreateVariants");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        try {                
            Object obj = parser.parse(jsondata);
            JSONObject json = (JSONObject) obj;     
            System.out.println(json);
            int ecu_id = Integer.parseInt((String) json.get("ecu_id"));
            JSONArray variants_list = (JSONArray) json.get("variants"); 
            String operation_type = (String) json.get("operation_type");
            System.out.println("variants_list"+variants_list);
            System.out.println("ecu_id"+ecu_id);  
            int i=0;
            for (Object o : variants_list) {    
                JSONObject variant_data = (JSONObject) o;
                System.out.println("for_loop");
                System.out.println("variant_name"+variant_data.get("variants"));
                String variant_name = (String) variant_data.get("variants");
                if(variant_name != null){
                    //Insert data in Variants table
                    Variants v = new Variants(variant_name,dtf.format(now));
                    int variants_result =  SystemOwnerDB.insertVariants(v);

                    //Insert data in ECU and Variants mapping table
                    ECU_and_Variants_Mapping evm = new ECU_and_Variants_Mapping(ecu_id,variants_result,dtf.format(now));
                    int evm_result =  SystemOwnerDB.insertEcuVariantsMapping(evm);

                    //Delete data from Ecu and variants mapping table if variants is not passed for ecu
                    if(i++ == variants_list.size() - 1){
                        SystemOwnerDB.deleteEcuVariantsMapping(ecu_id);
                        if(operation_type.equals("create"))
                            maps.put("status", "ECU Variants Created Successfully");
                        else
                            maps.put("status", "ECU Variants Updated Successfully");
                    }
                }
            }          
        }
        catch (Exception ex) { 
            System.out.println("entered into catch");
            System.out.println(ex.getMessage()); 
            maps.put("status", "Some error occurred !!"); 
        }
        return "success";
    }  
    public String SystemVersionCreationPage(){
        System.out.println("Entered");
        System.out.println("SystemVersionCreationPage");
        //This will execute if url contains parameter(id and action-edit, view)
        try{
            HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                              .get(ServletActionContext.HTTP_REQUEST);
            System.out.println("request"+request);
            System.out.println("id_value"+request.getParameter("id"));
            System.out.println("action_value"+request.getParameter("action"));
            Systemversion systemver = new Systemversion(Integer.parseInt(request.getParameter("id")));
            system_result_data = SystemOwnerDB.LoadSystemPreviousversionData(systemver);
            System.out.println("system_result_data" + system_result_data);
            result_data_obj = new Gson().toJson(system_result_data);
            System.out.println("result_data_obj" + result_data_obj);
//            vehicleversion_result = VehicleversionDB.LoadVehicleVersion("active");
//            ACBversion acbver = new ACBversion(Integer.parseInt(request.getParameter("id")));
//            result_data = ACBOwnerDB.LoadACBPreviousVehicleversionData(acbver);
//            System.out.println("acb_map_result"+result_data);
//            result_data_obj = new Gson().toJson(result_data);
//            System.out.println("result_data_obj"+result_data_obj);
//            System.out.println("pdbversion_result"+pdbversion_result);
        } 
        catch (Exception ex){
             System.out.println(ex.getMessage()); 
        }
        try{
//            pdbversion_result = PDBVersionDB.LoadPDBVersion("active");
//            ivnversion_result = IVNEngineerDB.LoadIVNVersion("active");
//            acbversion_result = ACBOwnerDB.LoadACBVersion("all");
            vehicleversion_result = VehicleversionDB.LoadVehicleVersion("active");
            systemversion_result = SystemOwnerDB.LoadSystemVersion("all");
            System.out.println("systemversion_result"+systemversion_result);
        }
        catch (Exception ex) { 
            System.out.println(ex.getMessage()); 
            maps.put("status", "Some error occurred !!"); 
        }
        return "success";
    }
    public String LoadSystemPreviousversionData() throws ParseException {
        System.out.println("LoadSystemPreviousversionData controller");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();

        Object obj = parser.parse(jsondata);
        JSONObject json = (JSONObject) obj; 
        int systemver_id = Integer.parseInt((String) json.get("systemversion_id")); 
        Systemversion systemver = new Systemversion(systemver_id);

        try{
            system_result_data = SystemOwnerDB.LoadSystemPreviousversionData(systemver);
//            pdb_map_result_obj = new Gson().toJson(pdb_map_result);
//                vehmod_map_result_obj =  Gson().toJSON(vehmod_map_result);
            System.out.println("system_result_data"+system_result_data);
        }
        catch (Exception ex) { 
            System.out.println(ex.getMessage()); 
            maps.put("status", "Some error occurred !!"); 
        }
//            return vehmod_map_result;
//            System.out.println("Result"+vehmod_map_result);
        return "success";
    }
    public String LoadACBVersion_for_System() throws ParseException {
        System.out.println("LoadACBVersion_for_System controller");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();

        Object obj = parser.parse(jsondata);
        JSONObject json = (JSONObject) obj; 
        System.out.println("json_data"+json);
        int vehver_id = Integer.parseInt((String) json.get("vehicleversion_id")); 
        int vehicle_id = Integer.parseInt((String) json.get("vehicle_id")); 
        System.out.println("vehver_id"+vehver_id);
        System.out.println("vehicle_id"+vehicle_id);
        Vehicle_and_Model_Mapping veh_mod_map = new Vehicle_and_Model_Mapping(vehver_id,vehicle_id);
//        IVNversion ivnver = new IVNversion(ivnver_id);
        System.out.println("before try2");
        try{
            result_data = SystemOwnerDB.LoadACBVersion_for_System(veh_mod_map);
//            result_data_obj = new Gson().toJson(result_data);
//                vehmod_map_result_obj =  Gson().toJSON(vehmod_map_result);
//            System.out.println("result_data"+result_data_obj );
        }
        catch (Exception ex) { 
            System.out.println(ex.getMessage()); 
            maps.put("status", "Some error occurred !!"); 
        }
//            return vehmod_map_result;
//            System.out.println("Result"+vehmod_map_result);
        return "success";
    }   
    public String LoadACBDataForSystemVersion() throws ParseException {
        System.out.println("LoadACBDataForSystemVersion controller");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();

        Object obj = parser.parse(jsondata);
        JSONObject json = (JSONObject) obj; 
        int acbver_id = Integer.parseInt((String) json.get("acbversion_id")); 
        int vehver_id = Integer.parseInt((String) json.get("vehicleversion_id")); 
        int veh_id = Integer.parseInt((String) json.get("vehicle_id"));
        ACBversion acbver = new ACBversion(acbver_id);

        try{
            acb_result_data = SystemOwnerDB.LoadACBDataForSystemVersion(acbver,vehver_id,veh_id);
//            pdb_map_result_obj = new Gson().toJson(pdb_map_result);
//                vehmod_map_result_obj =  Gson().toJSON(vehmod_map_result);
            System.out.println("acb_result_data"+acb_result_data);
        }
        catch (Exception ex) { 
            System.out.println(ex.getMessage()); 
            maps.put("status", "Some error occurred !!"); 
        }
//            return vehmod_map_result;
//            System.out.println("Result"+vehmod_map_result);
        return "success";
    }
    public String CreateSystemVersion() { 
        System.out.println("CreateSystemVersion");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();
//        String button_type = (String) json.get("button_type");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        boolean status = (boolean) false;
        int systemversion_id = 0;
        float version_name;
        String previousversion_status = null;
        String previousversion_flag = null;
        boolean flag;
        try {     
            Object obj = parser.parse(jsondata);
            JSONObject json = (JSONObject) obj;  
            System.out.println("systemversion_data"+json);
            JSONObject systemversion_value = (JSONObject) json.get("systemversion");  
            JSONArray systemdata_list = (JSONArray) json.get("systemdata_list");
            int vehicleversion_id = Integer.parseInt((String) systemversion_value.get("vehicleversion"));
            int vehicle_id = Integer.parseInt((String) systemversion_value.get("vehiclename"));
            int acbversion_id = Integer.parseInt((String) systemversion_value.get("acbversion"));
            int ecu_id = Integer.parseInt((String) systemversion_value.get("ecu"));
            System.out.println("systemdata_list"+systemdata_list);
            String button_type = (String) json.get("button_type");
            String notification_to = (String) json.get("notification_to");
            if(button_type.equals("save"))
                    flag = false;
                else
                    flag = true;
            if( systemversion_value != null && systemversion_value.containsKey("systemversion")){
                systemversion_id = Integer.parseInt((String) systemversion_value.get("systemversion"));
            } 

            if( systemversion_value != null && systemversion_value.containsKey("status") && button_type.equals("submit")){
                status = (boolean) systemversion_value.get("status");
            }    

            if(systemversion_id !=0)
            {
                //Get the data of previous vehicle version by id
                int systemver_id = systemversion_id; 
                Systemversion sver = new Systemversion(systemver_id);
//                private List<Map<String, Object>> vehmod_map_result = new ArrayList<Map<String, Object>>();
                List<Map<String, Object>> system_previous_result = SystemOwnerDB.LoadSystemPreviousVehicleversionStatus(sver);
                System.out.println("system_previous_result"+system_previous_result);
                previousversion_status = String.valueOf(system_previous_result.get(0).get("status"));
                previousversion_flag = String.valueOf(system_previous_result.get(0).get("flag"));
            }    
            System.out.println(previousversion_status);
            System.out.println(button_type);
            System.out.println(systemversion_id);
//            if(previousversion_status != null && button_type.equals("save") && pdbversion_id != 0){
            if(previousversion_status == "false" && systemversion_id != 0){
//                System.out.println("Ready to update");
//                    maps.put("status", "Ready to update");
                Systemversion sv = new Systemversion(systemversion_id,status,flag,dtf.format(now),"update");
                System.out.println("systemversion_id"+systemversion_id);
                Object[] id_version = SystemOwnerDB.insertSystemVersion(sv);
                int system_id = (int) id_version[0];
                version_name = (float) id_version[1];
                System.out.println("systemresult_id"+system_id);
                int i = 0;
                for (Object o : systemdata_list) {
                    JSONObject systemdata = (JSONObject) o;
                    System.out.println("systemdata"+systemdata);
                    int dfm_id = Integer.parseInt((String) systemdata.get("dfm_id"));
                    int variant_id = Integer.parseInt((String) systemdata.get("variant_id"));
                    String av_status = (String) systemdata.get("status");
                    SystemVersionGroup svg = new SystemVersionGroup(system_id,vehicleversion_id,vehicle_id,acbversion_id,dfm_id,ecu_id,variant_id,av_status,button_type,"update");
                    int systemversiongroup_result = SystemOwnerDB.insertSystemVersionGroup(svg);
                    if(i++ == systemdata_list.size() - 1){
                            if(button_type.equals("save")){
                                if(previousversion_flag == "true")
                                    maps.put("status", "Record updated in same version and stored as Temporary");
                                else
                                    maps.put("status", "Record updated successfully in same Temporary version"); 
                            }
                            else{
                                System.out.println("previousversion_flag"+previousversion_flag);
                                if (status) {
                                    new NotificationController().createNotification(VersionType.SystemVersion.getVersionCode(), version_name, dtf.format(now),notification_to);
                                }
                                if(previousversion_flag == "false")
                                    maps.put("status", "Record updated in same version and stored as permanent");
                                else
                                    maps.put("status", "Record updated successfully in same Permanent version");
                            }
                       }
                }
                SystemOwnerDB.deleteSystemVersion_Group(system_id,"update");
            }
            else{
                Systemversion sv = new Systemversion(systemversion_id,status,flag,dtf.format(now),"create");
                System.out.println("systemversion_id"+systemversion_id);
                Object[] id_version = SystemOwnerDB.insertSystemVersion(sv);
                int system_id = (int) id_version[0];
                version_name = (float) id_version[1];
                System.out.println("systemresult_id"+system_id);
                int i = 0;
                for (Object o : systemdata_list) {
                    JSONObject systemdata = (JSONObject) o;
                    System.out.println("systemdata"+systemdata);
                    int dfm_id = Integer.parseInt((String) systemdata.get("dfm_id"));
                    int variant_id = Integer.parseInt((String) systemdata.get("variant_id"));
                    String av_status = (String) systemdata.get("status");
                    SystemVersionGroup svg = new SystemVersionGroup(system_id,vehicleversion_id,vehicle_id,acbversion_id,dfm_id,ecu_id,variant_id,av_status,button_type,"create");
                    int systemversiongroup_result = SystemOwnerDB.insertSystemVersionGroup(svg);
                    if(i++ == systemdata_list.size() - 1){
                        if (status) {
                            new NotificationController().createNotification(VersionType.SystemVersion.getVersionCode(), version_name, dtf.format(now),notification_to);
                        }
                        if(systemversiongroup_result == 0)
                            maps.put("status", "New Temporary System Version Created Successfully"); 
                        else
                            maps.put("status", "New Permanent System Version Created Successfully");
                       }
                }                
            }
        }
        catch (Exception ex) { 
            System.out.println("entered into catch");
            System.out.println(ex.getMessage()); 
            maps.put("status", "Some error occurred !!"); 
        }
        return "success";
    }   
    public String GetSystemVersion_Listing() {
        System.out.println("GetSystemVersion_Listing controller");
        Signal veh = new Signal();
        try {
            result_data = (List<Map<String, Object>>) SystemOwnerDB.GetSystemVersion_Listing();
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
    public String CreatePDBDataFromSystem(){
        System.out.println("CreatePDBDataFromSystem");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();
        ArrayList pdbgroup_id = new ArrayList();
        try {     
            Object obj = parser.parse(jsondata);
            JSONObject json = (JSONObject) obj;  
            System.out.println("pdgroup_data"+json);
            JSONArray pdbdata_list = (JSONArray) json.get("pdbdata_list");
            System.out.println("pdbdata_list"+pdbdata_list);
            int dfm_id = Integer.parseInt((String) json.get("dfm_id"));
            System.out.println("dfm_id"+dfm_id);
            int pdb_id = Integer.parseInt((String) json.get("pdbversion"));
            System.out.println("pdb_id"+pdb_id);            
            int i = 0;
            for (Object o : pdbdata_list) {
                Map<String, Object> columns = new HashMap<String, Object>();
                JSONObject pdbdata = (JSONObject) o;
                System.out.println("pdbdata" + pdbdata);
                int vmm_id = Integer.parseInt((String) pdbdata.get("vmm_id"));
                String av_status = (String) pdbdata.get("status");
                PDBVersionGroup pvg = new PDBVersionGroup(pdb_id, vmm_id, dfm_id, av_status, "other", "create");
                int pdbversiongroup_result = PDBVersionDB.insertPDBVersionGroup(pvg);
//                pdbgroup_id.add(pdbversiongroup_result);
                columns.put("vmm_id",vmm_id);
                columns.put("pdbgroup_id",pdbversiongroup_result);
                result_data.add(columns);
            }
        }
        catch (Exception ex) { 
            System.out.println("entered into catch");
            System.out.println(ex.getMessage()); 
            maps.put("status", "Some error occurred !!"); 
        }    
        return "success";
    }
    public String CreateACBDataFromSystem(){
        System.out.println("CreateACBDataFromSystem");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();
        try {     
            Object obj = parser.parse(jsondata);
            JSONObject json = (JSONObject) obj;  
            System.out.println("acbgroup_data"+json);
            JSONArray cloned_data = (JSONArray) json.get("cloned_data");
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
                        ACBInput_and_Ouput_Signal acb_signal = new ACBInput_and_Ouput_Signal(signal_type, signal_id, network_id, network_type, pdbgroup_id, "other", "create");
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
                int acbversion_id = Integer.parseInt((String) json.get("acbversion"));
                int ivnversion_id = Integer.parseInt((String) json.get("ivnversion"));
                int pdbversion_id = Integer.parseInt((String) json.get("pdbversion"));
                int vehicleversion_id = Integer.parseInt((String) json.get("vehicleversion"));
                int vehicle_id = Integer.parseInt((String) json.get("vehicle"));
                System.out.println("vehicle_id" + vehicle_id);
                System.out.println("ecu_id" + json.get("ecu"));
                int ecu_id = Integer.parseInt(String.valueOf(json.get("ecu")));
                int dfm_id = Integer.parseInt(String.valueOf(json.get("fid")));
                System.out.println("ecu_id" + ecu_id);
                boolean touchedstatus = true;
                String input_signals = ip_signals.toString().substring(1, ip_signals.toString().length() - 1).replace("\"", "");
                String output_signals = op_signals.toString().substring(1, op_signals.toString().length() - 1).replace("\"", "");
                ACBVersionGroup acbgroup = new ACBVersionGroup(acbversion_id, ivnversion_id, pdbversion_id, vehicleversion_id,
                        vehicle_id, dfm_id, ecu_id, input_signals, output_signals, touchedstatus, "other", "create");
                int acbgroup_result = ACBOwnerDB.insertACBVersionGroup(acbgroup);
        }
        catch (Exception ex) { 
            System.out.println("entered into catch");
            System.out.println(ex.getMessage()); 
            maps.put("status", "Some error occurred !!"); 
        }    
        return "success";
    }
    public String GetSystemEng_Dashboarddata(){
        try {
            dashboard_result =  SystemOwnerDB.GetSystemEng_Dashboarddata();
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
    public List<Map<String, Object>> getResult_data() {
            return result_data;
    }

    public void setResult_data(List<Map<String, Object>> result_data) {
            this.result_data = result_data;
    }
    public String getResult_data_obj() {
            return result_data_obj;
    }

    public void setResult_data_obj(String result_data_obj) {
            this.result_data_obj = result_data_obj;
    }
    public List<Map<String, Object>> getVehicleversion_result() {
            return vehicleversion_result;
    }

    public void setVehicleversion_result(List<Map<String, Object>> vehicleversion_result) {
            this.vehicleversion_result = vehicleversion_result;
    }   
    public Map<String, Object> getAcb_result_data() {
            return acb_result_data;
    }
    public void setAcb_result_data(Map<String, Object> acb_result_data) {
            this.acb_result_data = acb_result_data;
    }
    public List<Map<String, Object>> getSystemversion_result() {
            return systemversion_result;
    }

    public void setSystemversion_result(List<Map<String, Object>> systemversion_result) {
            this.systemversion_result = systemversion_result;
    }  
    public Map<String, Object> getSystem_result_data() {
            return system_result_data;
    }
    public void setSystem_result_data(Map<String, Object> system_result_data) {
            this.system_result_data = system_result_data;
    }
    public Map<String, Object> getDashboard_result() {
            return dashboard_result;
    }
    public void setDashboard_result(Map<String, Object> dashboard_result) {
            this.dashboard_result = dashboard_result;
    }
}

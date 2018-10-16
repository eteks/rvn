/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.ivn_supervisor;

import com.controller.common.JSONConfigure;
import com.controller.notification.NotificationController;
import com.google.gson.Gson;
import com.model.acb_owner.ACBOwnerDB;
import com.model.acb_owner.ACBversion;
import com.model.ivn_supervisor.ModelVersionGroup;
import com.model.ivn_supervisor.Modelversion;
import com.model.ivn_supervisor.Vehicle;
import com.model.ivn_supervisor.VehicleModel;
import com.model.ivn_supervisor.Vehicle_and_Model_Mapping;
import com.model.ivn_supervisor.Vehicleversion;
import com.model.ivn_supervisor.VehicleversionDB;
import com.model.pdb_owner.PDBVersionDB;
import com.model.pdb_owner.PDBVersionGroup;
import com.model.pdb_owner.PDBversion;
import com.model.system_owner.SystemOwnerDB;
import com.controller.common.VersionType;
import com.model.ivn_engineer.Signal;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.SQLException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.json.simple.parser.ParseException;

/**
 *
 * @author ets-2
 */
public class Vehicle_and_Model extends ActionSupport {
//    public String execute() throws IOException {
//        return "success";
//    }

    private List<Map<String, Object>> vehmod_map_result = new ArrayList<Map<String, Object>>();
    private Map<String, String> maps = new HashMap<String, String>();
    public String vehmod_map_result_obj;
    private List<Map<String, Object>> vehicleversion_result = new ArrayList<Map<String, Object>>();
    private Map<String, Object> result_data = new HashMap<String, Object>();
    private List<Map<String, Object>> modelversion_result = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> listing_result_data = new ArrayList<Map<String, Object>>();
    public String listing_result_data_obj;
    public String result_data_obj;
    private Map<String, Object> dashboard_result = new HashMap<String, Object>();

    public String CreateVehicleVersion() {
        System.out.println("createvehicleversion");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        boolean status = (boolean) false;
        int vehicleversion_id = 0;
        float version_name;
        String previousversion_status = null;
        String previousversion_flag = null;
        boolean flag;
        try {
            System.out.println("entered try");
            Object obj = parser.parse(jsondata);
            JSONObject json = (JSONObject) obj;
            System.out.println("json" + json);
//                Insert Data in vehicle version table
            JSONObject vehicleversion_value = (JSONObject) json.get("vehicleversion");
            JSONArray vehicle_and_model_value = (JSONArray) json.get("vehicle_and_model");
            String button_type = (String) json.get("button_type");
            String notification_to = (String) json.get("notification_to");
            if (button_type.equals("save")) {
                flag = false;
            } else {
                flag = true;
            }
            if (vehicleversion_value != null && vehicleversion_value.containsKey("vehicleversion")) {
                vehicleversion_id = Integer.parseInt((String) vehicleversion_value.get("vehicleversion"));
            }

            if (vehicleversion_value != null && vehicleversion_value.containsKey("status")) {
                System.out.println("if satisfied");
                status = (boolean) vehicleversion_value.get("status");
            }

            if (vehicleversion_id != 0) {
                //Get the data of previous vehicle version by id
                int vehver_id = vehicleversion_id;
                Vehicleversion vver = new Vehicleversion(vehver_id);
                vehmod_map_result = (List<Map<String, Object>>) VehicleversionDB.LoadPreviousVehicleversionData(vver);
                System.out.println("vehmod_map_result" + vehmod_map_result);
                previousversion_status = String.valueOf(vehmod_map_result.get(0).get("status"));
                previousversion_flag = String.valueOf(vehmod_map_result.get(0).get("flag"));
                System.out.println("previousversion_status" + previousversion_status);
                System.out.println("previousversion_flag" + previousversion_flag);
            }

            //Update existing version
//                if(previousversion_status == "false" && button_type.equals("save") && vehicleversion_id != 0){
            if (previousversion_status == "false" && vehicleversion_id != 0) {
                System.out.println("Ready to update");
//                    maps.put("status", "Record Updated successfully in same version");
                //Update vehicleversion,vehicle, model and mapping
//                    int vehmod_map_result = VehicleversionDB.UpdateVehicleModel_and_Mapping();
                Vehicleversion v = new Vehicleversion(vehicleversion_id, status, flag, dtf.format(now), 1, "update");
                System.out.println("vehicleversion_id" + vehicleversion_id);
                //int result = VehicleversionDB.insertVehicleVersion(v);
                Object[] id_version = VehicleversionDB.insertVehicleVersion(v);
                int result = (int) id_version[0];
                version_name = (float) id_version[1];
                System.out.println("update_result_id" + result);
                for (Object o : vehicle_and_model_value) {
                    JSONObject vehicleitem = (JSONObject) o;
                    String vehiclename = (String) vehicleitem.get("vehiclename");
                    JSONArray modelvalue = (JSONArray) vehicleitem.get("modelname");
                    //Insert Data in vehicle table
                    Vehicle veh = new Vehicle(vehiclename, dtf.format(now), 1);
                    int veh_result = VehicleversionDB.insertVehicle(veh);
                    int i = 0;
                    //Insert Data in Model table
                    for (Object o1 : modelvalue) {
                        System.out.println("o1_value" + o1);
                        VehicleModel veh_mod = new VehicleModel((String) o1, dtf.format(now), 1);
                        int vehmod_result = VehicleversionDB.insertVehicleModel(veh_mod);
                        //Insert Data in VehicleModel Mapping table
                        Vehicle_and_Model_Mapping veh_mod_map = new Vehicle_and_Model_Mapping(result, veh_result, vehmod_result, button_type, "update");
                        int vehmod_map_result = VehicleversionDB.insertVehicleModelMapping(veh_mod_map);
                        if (i++ == modelvalue.size() - 1) {
                            System.out.println("previousversion_flag" + previousversion_flag);
                            if (button_type.equals("save")) {
                                if (previousversion_flag == "true") {
                                    maps.put("status", "Record updated in same version and stored as Temporary");
                                } else {
                                    maps.put("status", "Record updated successfully in same Temporary version");
                                }
                            } else {
                                System.out.println("previousversion_flag" + previousversion_flag);
                                if (status) {
                                    new NotificationController().createNotification(VersionType.VehicleVersion.getVersionCode(), version_name, dtf.format(now),notification_to);
                                }
                                if (previousversion_flag == "false") {
                                    maps.put("status", "Record updated in same version and stored as permanent");
                                } else {
                                    maps.put("status", "Record updated successfully in same Permanent version");
                                }
                            }
                        }
                    }
                }
                VehicleversionDB.deleteVehicleModelMapping(result);
            } //Create new temporary or permanent version
            else {
                System.out.println("else");
                System.out.println("status_value" + status);
                System.out.println("flag_value" + flag);
                Vehicleversion v = new Vehicleversion((float) 1.0, status, flag, dtf.format(now), 1, "create");
                //int result = VehicleversionDB.insertVehicleVersion(v);
                Object[] id_version = VehicleversionDB.insertVehicleVersion(v);
                int result = (int) id_version[0];
                version_name = (float) id_version[1];
                for (Object o : vehicle_and_model_value) {
                    JSONObject vehicleitem = (JSONObject) o;
                    String vehiclename = (String) vehicleitem.get("vehiclename");
                    JSONArray modelvalue = (JSONArray) vehicleitem.get("modelname");
                    //Insert Data in vehicle table
                    Vehicle veh = new Vehicle(vehiclename, dtf.format(now), 1);
                    int veh_result = VehicleversionDB.insertVehicle(veh);
                    int i = 0;
                    //Insert Data in Model table
                    for (Object o1 : modelvalue) {
                        VehicleModel veh_mod = new VehicleModel((String) o1, dtf.format(now), 1);
                        int vehmod_result = VehicleversionDB.insertVehicleModel(veh_mod);
                        //Insert Data in VehicleModel Mapping table
                        Vehicle_and_Model_Mapping veh_mod_map = new Vehicle_and_Model_Mapping(result, veh_result, vehmod_result, button_type, "create");
                        int vehmod_map_result = VehicleversionDB.insertVehicleModelMapping(veh_mod_map);
                        if (i++ == modelvalue.size() - 1) {
                            if (status) {
                                new NotificationController().createNotification(VersionType.VehicleVersion.getVersionCode(), version_name, dtf.format(now),notification_to);
                            }
                            if (vehmod_map_result == 0) {
                                maps.put("status", "New Temporary Version Created Successfully");
                            } else {
                                maps.put("status", "New Permanent Version Created Successfully");
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

    public String GetVehicleVersion_Listing() {
        System.out.println("GetVehicleVersion_Listing controller");
        System.out.println("GetVehicleVersion_Listing after controller");
        Vehicleversion vver = new Vehicleversion();
        try {
            vehmod_map_result = (List<Map<String, Object>>) VehicleversionDB.GetVehicleVersion_Listing(vver);
            vehmod_map_result_obj = new Gson().toJson(vehmod_map_result);
//                vehmod_map_result_obj =  Gson().toJSON(vehmod_map_result);
            System.out.println("oject" + vehmod_map_result_obj);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            maps.put("status", "Some error occurred !!");
        }
//            return vehmod_map_result;
//            System.out.println("Result"+vehmod_map_result);
        return "success";
    }

    public String GetVehicle_Listing() {
        System.out.println("GetVehicle_Listing controller");
        Vehicle veh = new Vehicle();
        try {
            vehmod_map_result = (List<Map<String, Object>>) VehicleversionDB.GetVehicle_Listing(veh);
            vehmod_map_result_obj = new Gson().toJson(vehmod_map_result);
//                vehmod_map_result_obj =  Gson().toJSON(vehmod_map_result);
            System.out.println("oject" + vehmod_map_result_obj);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            maps.put("status", "Some error occurred !!");
        }
//            return vehmod_map_result;
//            System.out.println("Result"+vehmod_map_result);
        return "success";
    }

    public String GetVehicleModel_Listing() {
        System.out.println("GetVehicleModel_Listing controller");
        VehicleModel veh_mod = new VehicleModel();
        try {
            vehmod_map_result = (List<Map<String, Object>>) VehicleversionDB.GetVehicleModel_Listing(veh_mod);
            vehmod_map_result_obj = new Gson().toJson(vehmod_map_result);
//                vehmod_map_result_obj =  Gson().toJSON(vehmod_map_result);
            System.out.println("oject" + vehmod_map_result_obj);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            maps.put("status", "Some error occurred !!");
        }
//            return vehmod_map_result;
//            System.out.println("Result"+vehmod_map_result);
        return "success";
    }

    public String DisplayCreateVehicleversion() {
        System.out.println("DisplayCreateVehicleversion controller");
        //This will execute if url contains parameter(id and action-edit, view)
        try {
            HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                    .get(ServletActionContext.HTTP_REQUEST);
            System.out.println("request" + request);
            System.out.println("id_value" + request.getParameter("id"));
            System.out.println("action_value" + request.getParameter("action"));
            Vehicleversion vver = new Vehicleversion(Integer.parseInt(request.getParameter("id")));
            vehmod_map_result = (List<Map<String, Object>>) VehicleversionDB.LoadPreviousVehicleversionData(vver);
            vehmod_map_result_obj = new Gson().toJson(vehmod_map_result);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

//            VehicleModel veh_mod = new VehicleModel();
        try {
            vehicleversion_result = VehicleversionDB.LoadVehicleVersion("all");
            System.out.println("oject" + vehicleversion_result);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            maps.put("status", "Some error occurred !!");
        }
//            return vehmod_map_result;
//            System.out.println("Result"+vehmod_map_result);
        return "success";
    }

    public String LoadPreviousVehicleversionData() throws ParseException {
        System.out.println("LoadPreviousVehicleversionData controller");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();

        Object obj = parser.parse(jsondata);
        JSONObject json = (JSONObject) obj;
        int vehver_id = Integer.parseInt((String) json.get("vehicleversion_id"));
        Vehicleversion vver = new Vehicleversion(vehver_id);

        try {
            vehmod_map_result = (List<Map<String, Object>>) VehicleversionDB.LoadPreviousVehicleversionData(vver);
            vehmod_map_result_obj = new Gson().toJson(vehmod_map_result);
//                vehmod_map_result_obj =  Gson().toJSON(vehmod_map_result);
            System.out.println("oject" + vehmod_map_result_obj);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            maps.put("status", "Some error occurred !!");
        }
//            return vehmod_map_result;
//            System.out.println("Result"+vehmod_map_result);
            return "success";
    }
    public String LoadVehicleModels_and_ACB() throws ParseException {
        System.out.println("LoadVehicleModels_and_ACB controller");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();

        Object obj = parser.parse(jsondata);
        JSONObject json = (JSONObject) obj; 
        System.out.println("json_data"+json);
        int vehver_id = Integer.parseInt((String) json.get("vehicleversion_id")); 
        int vehicle_id = Integer.parseInt((String) json.get("vehicle_id")); 
        System.out.println("vehver_id"+vehver_id);
        System.out.println("vehicle_id"+vehicle_id);
//        Vehicle_and_Model_Mapping veh_mod_map = new Vehicle_and_Model_Mapping(vehver_id,vehicle_id);
//        IVNversion ivnver = new IVNversion(ivnver_id);
        System.out.println("before try2");
        try{
            result_data = VehicleversionDB.LoadVehicleModels_and_ACB(vehver_id,vehicle_id);
//            result_data_obj = new Gson().toJson(result_data);
//                vehmod_map_result_obj =  Gson().toJSON(vehmod_map_result);
            System.out.println("result_data"+result_data);
        }
        catch (Exception ex) { 
            System.out.println(ex.getMessage()); 
            maps.put("status", "Some error occurred !!"); 
        }
//            return vehmod_map_result;
//            System.out.println("Result"+vehmod_map_result);
        return "success";
    }      
    public String CreateModelVersion() { 
        System.out.println("CreateModelVersion");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();
//        String button_type = (String) json.get("button_type");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        boolean status = (boolean) false;
        int modelversion_id = 0;
        String previousversion_status = null;
        String previousversion_flag = null;
        boolean flag;
        try {     
            Object obj = parser.parse(jsondata);
            JSONObject json = (JSONObject) obj;  
            System.out.println("modelversion_data"+json);
            JSONObject modelversion_value = (JSONObject) json.get("modelversion");  
            JSONArray modeldata_list = (JSONArray) json.get("modeldata_list");
            int vehicleversion_id = Integer.parseInt((String) modelversion_value.get("vehicleversion"));
            int vehicle_id = Integer.parseInt((String) modelversion_value.get("vehiclename"));
            int acbversion_id = Integer.parseInt((String) modelversion_value.get("acbversion"));
            System.out.println("modeldata_list"+modeldata_list);
            String button_type = (String) json.get("button_type");
            if(button_type.equals("save"))
                    flag = false;
                else
                    flag = true;
            if( modelversion_value != null && modelversion_value.containsKey("modelversion")){
                modelversion_id = Integer.parseInt((String) modelversion_value.get("modelversion"));
            } 

            if( modelversion_value != null && modelversion_value.containsKey("status")){
                status = (boolean) modelversion_value.get("status");
            }    

            if(modelversion_id !=0)
            {
                //Get the data of previous vehicle version by id
                int modelver_id = modelversion_id; 
                Modelversion mver = new Modelversion(modelver_id);
//                private List<Map<String, Object>> vehmod_map_result = new ArrayList<Map<String, Object>>();
                List<Map<String, Object>> model_previous_result = VehicleversionDB.LoadModelPreviousVehicleversionStatus(mver);
                System.out.println("model_previous_result"+model_previous_result);
                previousversion_status = String.valueOf(model_previous_result.get(0).get("status"));
                previousversion_flag = String.valueOf(model_previous_result.get(0).get("flag"));
            }    
            System.out.println(previousversion_status);
            System.out.println(button_type);
            System.out.println(modelversion_id);
//            if(previousversion_status != null && button_type.equals("save") && pdbversion_id != 0){
            if(previousversion_status == "false" && modelversion_id != 0){
//                System.out.println("Ready to update");
//                    maps.put("status", "Ready to update");
                Modelversion mv = new Modelversion(modelversion_id,status,flag,dtf.format(now),1,"update");
                System.out.println("modelversion_id"+modelversion_id);
                int model_id = VehicleversionDB.insertModelVersion(mv);
                System.out.println("modelresult_id"+model_id);
                int i = 0;
                for (Object o : modeldata_list) {
                    JSONObject modeldata = (JSONObject) o;
                    System.out.println("modeldata"+modeldata);
                    int vmm_id = Integer.parseInt((String) modeldata.get("vmm_id"));
                    int ecu_id = Integer.parseInt((String) modeldata.get("ecu_id"));
                    int variant_id = Integer.parseInt((String) modeldata.get("variant_id"));
//                        String av_status = (String) modeldata.get("status");
                    ModelVersionGroup mvg = new ModelVersionGroup(model_id,vehicleversion_id,vehicle_id,acbversion_id,vmm_id,ecu_id,variant_id,button_type,"update");
                    int modelversiongroup_result = VehicleversionDB.insertModelVersionGroup(mvg);
                    if(i++ == modeldata_list.size() - 1){
                            if(button_type.equals("save")){
                                if(previousversion_flag == "true")
                                    maps.put("status", "Record updated in same version and stored as Temporary");
                                else
                                    maps.put("status", "Record updated successfully in same Temporary version"); 
                            }
                            else{
                                System.out.println("previousversion_flag"+previousversion_flag);
                                if(previousversion_flag == "false")
                                    maps.put("status", "Record updated in same version and stored as permanent");
                                else
                                    maps.put("status", "Record updated successfully in same Permanent version");
                            }
                       }
                }
                VehicleversionDB.deleteModelVersion_Group(model_id,"update");
            }
            else{
                Modelversion mv = new Modelversion((float) 1.0, status,flag,dtf.format(now),1,"create");
                System.out.println("modelversion_id"+modelversion_id);
                int model_id = VehicleversionDB.insertModelVersion(mv);
                System.out.println("modelresult_id"+model_id);
                int i = 0;
                for (Object o : modeldata_list) {
                    JSONObject modeldata = (JSONObject) o;
                    System.out.println("modeldata"+modeldata);
                    int vmm_id = Integer.parseInt((String) modeldata.get("vmm_id"));
                    int ecu_id = Integer.parseInt((String) modeldata.get("ecu_id"));
                    int variant_id = Integer.parseInt((String) modeldata.get("variant_id"));
//                        String av_status = (String) modeldata.get("status");
                    ModelVersionGroup mvg = new ModelVersionGroup(model_id,vehicleversion_id,vehicle_id,acbversion_id,vmm_id,ecu_id,variant_id,button_type,"create");
                    int modelversiongroup_result = VehicleversionDB.insertModelVersionGroup(mvg);
                    if(i++ == modeldata_list.size() - 1){
                            if(modelversiongroup_result == 0)
                                maps.put("status", "New Temporary Model Version Created Successfully"); 
                            else
                                maps.put("status", "New Permanent Model Version Created Successfully");
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
    public String ModelVersionCreationPage(){
        System.out.println("Entered");
        System.out.println("ModelVersionCreationPage");
        //This will execute if url contains parameter(id and action-edit, view)
        try{
            HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                              .get(ServletActionContext.HTTP_REQUEST);
            System.out.println("request"+request);
            System.out.println("id_value"+request.getParameter("id"));
            System.out.println("action_value"+request.getParameter("action"));
            Modelversion modelver = new Modelversion(Integer.parseInt(request.getParameter("id")));
            result_data = VehicleversionDB.LoadModelPreviousversionData(modelver);
            result_data_obj = new Gson().toJson(result_data);
        }
        catch (Exception ex){
             System.out.println(ex.getMessage()); 
        }
        try{
            vehicleversion_result = VehicleversionDB.LoadVehicleVersion("active");
            modelversion_result = VehicleversionDB.LoadModelVersion("all");
//                featureslist_result = PDBVersionDB.LoadFeaturesList();
//                featureslist_result_obj = new Gson().toJson(featureslist_result);
//                System.out.println("pdbversion_result"+pdbversion_result);
//                System.out.println("vehicleversion_result"+vehicleversion_result);
//                System.out.println("featureslist_result"+featureslist_result_obj);
        }
        catch (Exception ex) { 
            System.out.println(ex.getMessage()); 
            maps.put("status", "Some error occurred !!"); 
        }
        return "success";
    }
    public String LoadACBDataForModelVersion() throws ParseException {
        System.out.println("LoadACBVersion_for_System controller");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();

        Object obj = parser.parse(jsondata);
        JSONObject json = (JSONObject) obj; 
        System.out.println("json_data"+json);
        int vehver_id = Integer.parseInt((String) json.get("vehicleversion_id")); 
        int vehicle_id = Integer.parseInt((String) json.get("vehicle_id")); 
        int acbversion_id = Integer.parseInt((String) json.get("acbversion_id")); 
        System.out.println("vehver_id"+vehver_id);
        System.out.println("vehicle_id"+vehicle_id);
        System.out.println("acbversion_id"+acbversion_id);
        try{
            result_data = VehicleversionDB.LoadACBDataForModelVersion(vehver_id,vehicle_id,acbversion_id);
//            result_data_obj = new Gson().toJson(result_data);
//                vehmod_map_result_obj =  Gson().toJSON(vehmod_map_result);
//            System.out.println("result_data"+result_data);
        }
        catch (Exception ex) { 
            System.out.println(ex.getMessage()); 
            maps.put("status", "Some error occurred !!"); 
        }
//            return vehmod_map_result;
//            System.out.println("Result"+vehmod_map_result);
        return "success";
    } 
    public String LoadModelPreviousversionData() throws ParseException {
        System.out.println("LoadModelPreviousversionData controller");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();

        Object obj = parser.parse(jsondata);
        JSONObject json = (JSONObject) obj; 
        int modelver_id = Integer.parseInt((String) json.get("modelversion_id")); 
        Modelversion modelver = new Modelversion(modelver_id);

        try{
            result_data = VehicleversionDB.LoadModelPreviousversionData(modelver);
//            pdb_map_result_obj = new Gson().toJson(pdb_map_result);
//                vehmod_map_result_obj =  Gson().toJSON(vehmod_map_result);
            System.out.println("result_data"+result_data);
        }
        catch (Exception ex) { 
            System.out.println(ex.getMessage()); 
            maps.put("status", "Some error occurred !!"); 
        }
//            return vehmod_map_result;
//            System.out.println("Result"+vehmod_map_result);
        return "success";
    }
    public String GetModelVersion_Listing() {
        System.out.println("GetModelVersion_Listing controller");
        Signal veh = new Signal();
        try {
            listing_result_data = (List<Map<String, Object>>) VehicleversionDB.GetModelVersion_Listing();
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
    public String GetVehMod_Dashboarddata(){
        try {
            dashboard_result = VehicleversionDB.GetVehMod_Dashboarddata();
            System.out.println("dashboard_result"+dashboard_result);
        }
        catch (Exception ex) { 
            System.out.println("entered into catch");
            System.out.println(ex.getMessage()); 
            maps.put("status", "Some error occurred !!"); 
        }   
        return "success";
    }
    public List<Map<String, Object>> getVehmod_map_result() {
        return vehmod_map_result;
    }

    public void setVehmod_map_result(List<Map<String, Object>> vehmod_map_result) {
        this.vehmod_map_result = vehmod_map_result;
    }

    public Map<String, String> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, String> maps) {
        this.maps = maps;
    }

    public Map<String, Object> getResult_data() {
        return result_data;
    }
    public void setResult_data(Map<String, Object> result_data) {
            this.result_data = result_data;
    }
    public List<Map<String, Object>> getModelversion_result() {
            return modelversion_result;
    }

    public void setModelversion_result(List<Map<String, Object>> modelversion_result) {
            this.modelversion_result = modelversion_result;
    }
    public String getVehmod_map_result_obj() {
        return vehmod_map_result_obj;
    }

    public void setVehmod_map_result_obj(String vehmod_map_result) {
        this.vehmod_map_result_obj = vehmod_map_result_obj;
    }

    public List<Map<String, Object>> getVehicleversion_result() {
        return vehicleversion_result;
    }

    public void setVehicleversion_result(List<Map<String, Object>> vehicleversion_result) {
        this.vehicleversion_result = vehicleversion_result;
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
    public Map<String, Object> getDashboard_result() {
            return dashboard_result;
    }
    public void setDashboard_result(Map<String, Object> dashboard_result) {
            this.dashboard_result = dashboard_result;
    }
}

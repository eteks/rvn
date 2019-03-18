/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.pdb_owner;

import com.controller.common.JSONConfigure;
import com.controller.common.VersionType;
import com.controller.notification.NotificationController;
import com.google.gson.Gson;
import com.model.pdb_owner.PDBVersionDB;
import com.model.pdb_owner.PDBversion;
import com.model.common.GlobalDeleteVersion;
import com.model.ivn_supervisor.VehicleversionDB;
import com.model.pojo.pdb_version.Domain;
import com.model.pojo.pdb_version.DomainFeaturesMapping;
import com.model.pojo.pdb_version.Features;
import com.model.pojo.pdb_version.PDBVersion;
import com.model.pojo.pdb_version.PDBVersionGroup;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
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
public class Domain_and_Features extends ActionSupport {

    private Map<String, String> maps = new HashMap<String, String>();
    private Map<String, Integer> dlStatus = new HashMap<String, Integer>();
    private List<Map<String, Object>> vehicleversion_result = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> pdbversion_result = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> domainfeatures_result = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> featureslist_result = new ArrayList<Map<String, Object>>();
//    private List<Map<String, Object>> pdb_map_result = new ArrayList<Map<String, Object>>();
    private Map<String, Object> pdb_map_result = new HashMap<String, Object>();
    private List<Map<String, Object>> result_data = new ArrayList<>();
    private List<Map> pdb_listing = new ArrayList<>();
    public String featureslist_result_obj;
    public String result_data_obj;
    private Map<String, Object> dashboard_result = new HashMap<String, Object>();

    public String PDBAssignPage() {
        System.out.println("Entered");
        System.out.println("PDBAssignPage");
        //This will execute if url contains parameter(id and action-edit, view)
        try {
            HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                    .get(ServletActionContext.HTTP_REQUEST);
            System.out.println("request" + request);
            System.out.println("id_value" + request.getParameter("id"));
            System.out.println("action_value" + request.getParameter("action"));
            PDBVersion pdbver = new PDBVersion(Integer.parseInt(request.getParameter("id")));
            pdb_map_result = PDBVersionDB.LoadPDBPreviousVehicleversionData(pdbver);
            result_data_obj = new Gson().toJson(pdb_map_result);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        try {
            vehicleversion_result = VehicleversionDB.LoadVehicleVersion("active");
            pdbversion_result = PDBVersionDB.LoadPDBVersion("all");
            featureslist_result = PDBVersionDB.LoadFeaturesList();
            featureslist_result_obj = new Gson().toJson(featureslist_result);
            System.out.println("pdbversion_result" + pdbversion_result);
            System.out.println("vehicleversion_result" + vehicleversion_result);
            System.out.println("featureslist_result" + featureslist_result_obj);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            maps.put("status", "Some error occurred !!");
        }
        return "success";
    }

    public String CreateDomain_and_Features() {
        System.out.println("CreateDomain_and_Features");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        boolean status = (boolean) false;
        int vehicleversion_id = 0;
        String previousversion_status = null;
        try {
            Object obj = parser.parse(jsondata);
            JSONObject json = (JSONObject) obj;
            System.out.println(json);
            String domain_name = (String) json.get("domain_name");
            JSONArray features_and_description = (JSONArray) json.get("features_and_description");
            System.out.println("vehiclename" + domain_name);
            System.out.println("vehicle_and_model_value" + features_and_description);

//            Insert Data in Domain table
            Domain dom = new Domain(domain_name);
            int dom_result = PDBVersionDB.insertDomain(dom);
            String dom_result1 = Integer.toString(dom_result);
            List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();

            //Insert Data in Features table
            for (Object o : features_and_description) {
                Map<String, Object> columns = new HashMap<String, Object>();
                JSONObject fd_item = (JSONObject) o;
                String feature_name = (String) fd_item.get("feature");
                String feature_description = (String) fd_item.get("description");
                Features fd = new Features(feature_name, feature_description);
                int fd_result = PDBVersionDB.insertFeatures(fd);

                //Insert Data in Domain and Features Mapping Table
                DomainFeaturesMapping dfm = new DomainFeaturesMapping(dom_result, fd_result);
                String fdm_result = String.valueOf(PDBVersionDB.insertDomainFeaturesMapping(dfm));
                columns.put("domain", domain_name);
                columns.put("fid", fdm_result);
                columns.put("fea", feature_name);
                domainfeatures_result.add(columns);
                row.add(columns);
                System.out.println("domainfeatures_result" + domainfeatures_result);
            }
//            domainfeatures_result_obj = new Gson().toJson(domainfeatures_result);
//            System.out.println("domain_result"+domainfeatures_result_obj);
        } catch (Exception ex) {
            System.out.println("entered into catch");
            System.out.println(ex.getMessage());
            maps.put("status", "Some error occurred !!");
        }
        return "success";
    }

    public String CreatePDBVersion() {
        System.out.println("CreatePDBVersion");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();
//        String button_type = (String) json.get("button_type");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        boolean status = (boolean) false;
        int pdbversion_id = 0;
        float version_name;
        String previousversion_status = null;
        String previousversion_flag = null;
        boolean flag;
        try {
            Object obj = parser.parse(jsondata);
            JSONObject json = (JSONObject) obj;
            System.out.println("pdbdata" + json);
            JSONObject pdbversion_value = (JSONObject) json.get("pdbversion");
            JSONArray pdbdata_list = (JSONArray) json.get("pdbdata_list");
            System.out.println("pdbdata_list" + pdbdata_list);
            String button_type = (String) json.get("button_type");
            String notification_to = (String) json.get("notification_to");
            if (button_type.equals("save")) {
                flag = false;
            } else {
                flag = true;
            }
            if (pdbversion_value != null && pdbversion_value.containsKey("pdbversion")) {
                pdbversion_id = Integer.parseInt((String) pdbversion_value.get("pdbversion"));
            }

            if (pdbversion_value != null && pdbversion_value.containsKey("status")) {
                status = (boolean) pdbversion_value.get("status");
            }

            if (pdbversion_id != 0) {
                //Get the data of previous vehicle version by id
                int pdbver_id = pdbversion_id;
                PDBVersion pver = new PDBVersion(pdbver_id);
//                private List<Map<String, Object>> vehmod_map_result = new ArrayList<Map<String, Object>>();
                List<Map<String, Object>> pdb_previous_result = PDBVersionDB.LoadPDBPreviousVehicleversionStatus(pver);
                System.out.println("pdb_previous_status" + pdb_previous_result);
                previousversion_status = String.valueOf(pdb_previous_result.get(0).get("status"));
                previousversion_flag = String.valueOf(pdb_previous_result.get(0).get("flag"));
            }
            System.out.println(previousversion_status);
            System.out.println(button_type);
            System.out.println(pdbversion_id);
//            if(previousversion_status != null && button_type.equals("save") && pdbversion_id != 0){
            if (previousversion_status == "false" && pdbversion_id != 0) {
//                System.out.println("Ready to update");
                maps.put("status", "Ready to update");
                PDBVersion pv = new PDBVersion(pdbversion_id, status, flag, "update");
                System.out.println("pdbversion_id" + pdbversion_id);
                Object[] id_version = PDBVersionDB.insertPDBVersion(pv);
                int pdb_id = (int) id_version[0];
                version_name = (float) id_version[1];
                int i = 0;
                for (Object o : pdbdata_list) {
                    JSONObject pdbdata = (JSONObject) o;
                    System.out.println("pdbdata" + pdbdata);
                    int vmm_id = Integer.parseInt((String) pdbdata.get("vmm_id"));
                    int dfm_id = Integer.parseInt((String) pdbdata.get("dfm_id"));
                    String av_status = (String) pdbdata.get("status");
                    PDBVersionGroup pvg = new PDBVersionGroup(pdb_id, vmm_id, dfm_id, av_status, button_type, "update");
                    int pdbversiongroup_result = PDBVersionDB.insertPDBVersionGroup(pvg);
                    if (i++ == pdbdata_list.size() - 1) {
                        if (button_type.equals("save")) {
                            if (previousversion_flag == "true") {
                                maps.put("status", "Record updated in same version and stored as Temporary");
                            } else {
                                maps.put("status", "Record updated successfully in same Temporary version");
                            }
                        } else {
                            System.out.println("previousversion_flag" + previousversion_flag);
                            if (status) {
                                new NotificationController().createNotification(VersionType.pdbVersion.getVersionCode(), version_name, dtf.format(now), notification_to);
                            }
                            if (previousversion_flag == "false") {
                                maps.put("status", "Record updated in same version and stored as permanent");
                            } else {
                                maps.put("status", "Record updated successfully in same Permanent version");
                            }
                        }
                    }
                }
                PDBVersionDB.deletePDBVersion_Group(pdb_id, "update");
            } else {
                PDBVersion pv = new PDBVersion((float) 1.0, status, flag, "create");
                Object[] id_version = PDBVersionDB.insertPDBVersion(pv);
                int pdb_id = (int) id_version[0];
                version_name = (float) id_version[1];
                int i = 0;
                for (Object o : pdbdata_list) {
                    JSONObject pdbdata = (JSONObject) o;
                    System.out.println("pdbdata" + pdbdata);
                    int vmm_id = Integer.parseInt((String) pdbdata.get("vmm_id"));
                    int dfm_id = Integer.parseInt((String) pdbdata.get("dfm_id"));
                    String av_status = (String) pdbdata.get("status");
                    PDBVersionGroup pvg = new PDBVersionGroup(pdb_id, vmm_id, dfm_id, av_status, button_type, "create");
                    int pdbversiongroup_result = PDBVersionDB.insertPDBVersionGroup(pvg);
                    if (i++ == pdbdata_list.size() - 1) {
                        if (status) {
                            new NotificationController().createNotification(VersionType.pdbVersion.getVersionCode(), version_name, dtf.format(now), notification_to);
                        }
                        if (pdbversiongroup_result == 0) {
                            maps.put("status", "New Temporary PDB Version Created Successfully");
                        } else {
                            maps.put("status", "New Permanent PDB Version Created Successfully");
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
    
    public String DeletePDBVersion() {
        System.out.println("deletepdbversion");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();
        try {
            Object obj = parser.parse(jsondata);
            JSONObject json = (JSONObject) obj;
            System.out.println("json" + json);
            if(!GlobalDeleteVersion.deleteVersion("pdbversion", Integer.parseInt((String) json.get("id")))){
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

    public String LoadPDBPreviousVehicleversionData() throws ParseException {
        System.out.println("LoadPDBPreviousVehicleversionData controller");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();

        Object obj = parser.parse(jsondata);
        JSONObject json = (JSONObject) obj;
        int pdbver_id = Integer.parseInt((String) json.get("pdbversion_id"));
        PDBVersion pdbver = new PDBVersion(pdbver_id);

        try {
            PDBVersionDB.deletePDBVersion_Group(pdbver_id, "load");
            pdb_map_result = PDBVersionDB.LoadPDBPreviousVehicleversionData(pdbver);
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

    public String GetDomainFeaturesListing() {
        System.out.println("GetFeaturesListing controller");
        Features fea = new Features();
        try {
            result_data = (List<Map<String, Object>>) PDBVersionDB.GetDomainFeaturesListing(fea);
            result_data_obj = new Gson().toJson(result_data);
//            vehmod_map_result_obj = new Gson().toJson(vehmod_map_result);
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

    public String GetPDBVersion_Listing() {
        System.out.println("GetPDBVersion_Listing controller");
        try {
            pdb_listing = (List<Map>) PDBVersionDB.GetPDBVersion_Listing();
            result_data_obj = new Gson().toJson(pdb_listing);
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

    public String GetPDB_Dashboarddata() {
        try {
            dashboard_result = PDBVersionDB.GetPDB_Dashboarddata();
            System.out.println("dashboard_result" + dashboard_result);
        } catch (Exception ex) {
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

	public void setDlStatus(Map<String, Integer> dlStatus) {
		this.dlStatus = dlStatus;
	}

	public List<Map<String, Object>> getVehicleversion_result() {
        return vehicleversion_result;
    }

    public void setVehicleversion_result(List<Map<String, Object>> vehicleversion_result) {
        this.vehicleversion_result = vehicleversion_result;
    }

    public String getFeatureslist_result_obj() {
        return featureslist_result_obj;
    }

    public void setFeatureslist_result_obj(String featureslist_result_obj) {
        this.featureslist_result_obj = featureslist_result_obj;
    }

    public List<Map<String, Object>> getDomainFeatures_result() {
        return domainfeatures_result;
    }

    public void setDomainFeatures_result(List<Map<String, Object>> domainfeatures_result) {
        this.domainfeatures_result = domainfeatures_result;
    }

    public List<Map<String, Object>> getPdbversion_result() {
        return pdbversion_result;
    }

    public void setPdbversion_result(List<Map<String, Object>> pdbversion_result) {
        this.pdbversion_result = pdbversion_result;
    }

    public Map<String, Object> getPdb_map_result() {
        return pdb_map_result;
    }

    public void setPdb_map_result(Map<String, Object> pdb_map_result) {
        this.pdb_map_result = pdb_map_result;
    }

    public List<Map<String, Object>> getResult_data() {
        return result_data;
    }

    public List<Map> getPdb_listing() {
        return pdb_listing;
    }

    public void setPdb_listing(List<Map> pdb_listing) {
        this.pdb_listing = pdb_listing;
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

    public Map<String, Object> getDashboard_result() {
        return dashboard_result;
    }

    public void setDashboard_result(Map<String, Object> dashboard_result) {
        this.dashboard_result = dashboard_result;
    }

}

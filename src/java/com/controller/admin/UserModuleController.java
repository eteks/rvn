
package com.controller.admin;

import com.controller.common.JSONConfigure;
import com.model.AdminDb;
import com.model.ModuleModel;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.convention.annotation.Result;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author ETS-4
 */
@Result(type = "json")
public class UserModuleController extends ActionSupport {

    private Map<String, String> maps = new HashMap<>();
    private List<ModuleModel> userModuleList;

    public List<ModuleModel> getUserModuleList() {
        return userModuleList;
    }

    public void setUserModuleList(List<ModuleModel> userModuleList) {
        this.userModuleList = userModuleList;
    }

    public Map<String, String> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, String> maps) {
        this.maps = maps;
    }
    public String createModule() {
        try {
            JSONParser parser = new JSONParser();
            String jsondata = JSONConfigure.getAngularJSONFile();
            Object obj = parser.parse(jsondata);
            JSONObject json = (JSONObject) obj;
            String modulename = (String) json.get("moduleName");
            String iconCode = (String) json.get("iconCode");
            String routePage = (String) json.get("routePage");
            int status = 0;
            ModuleModel userModule = new ModuleModel(modulename, iconCode, routePage);
            if (modulename!=null && !modulename.isEmpty()) {
                boolean result = AdminDb.addModule(userModule);
                if (result) {
                    maps.put("status", "success");
                } else {
                    maps.put("status", "error");
                }
            } else {
                maps.put("status", "empty");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return Action.SUCCESS;
    }

    public String getAllUserModule() {
        userModuleList = AdminDb.getAllModule();
        return Action.SUCCESS;
    }

}

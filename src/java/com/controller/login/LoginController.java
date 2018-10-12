package com.controller.login;

import com.controller.common.JSONConfigure;
import com.model.LoginDb;
import com.model.LoginModel;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author ETS-4
 */
//public class LoginController extends ActionSupport implements ServletResponseAware, ServletRequestAware{
//    //ActionContext context=null;
//     private Map<String,String> maps=new HashMap<String, String>();
//     public Map<String, String> getMaps() {
//        return maps;
//    }
//    public void setMaps(Map<String, String> maps) {
//        this.maps = maps;
//    }
//    public String login()
//    {
//        try
//        {
//       JSONParser parser = new JSONParser();
//        String jsondata = JSONConfigure.getAngularJSONFile();
//            Object obj = parser.parse(jsondata);
//            JSONObject json = (JSONObject) obj; 
//            String username = (String) json.get("username"); 
//            String password = (String) json.get("password"); 
//            System.out.println("username---:"+username);
//            System.out.println("password---:"+password);
//            LoginModel loginmodel=new LoginModel(username,password);
//            boolean logindata=LoginDb.login(loginmodel);
//            
//             if(logindata==true)
//             { 
//                 System.out.println("login id:-"+loginmodel.getId());
//                 Map<String,Object> context = ActionContext.getContext().getSession();
//                 context.put("user", loginmodel.getUsername());
//                 maps.put("status","success");
//                 return "success";
//             }
//             else if(logindata!=true)
//             {
//                 System.out.println("invalid login --------");
//                 maps.put("status","error");
//                 return "success";
//             }
//        }
//        catch(Exception ex)
//        {
//            System.out.println(ex);
//        }
//        return null;
//         }
//         public String logout()
//         {
//             Map<String,Object> session = ActionContext.getContext().getSession();
//             session.remove("user");
//             return "success";
//         }
//         
//         protected HttpServletResponse servletResponse;
//    @Override
//    public void setServletResponse(HttpServletResponse servletResponse) {
//        this.servletResponse=servletResponse;
//    }
//    protected HttpServletRequest servletRequest;
//    @Override
//    public void setServletRequest(HttpServletRequest servletRequest) {
//        this.servletRequest=servletRequest;
//    }
//  }
public class LoginController extends ActionSupport implements ServletResponseAware, ServletRequestAware {

    //ActionContext context=null;
    private Map<String, String> maps = new HashMap<String, String>();

    public Map<String, String> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, String> maps) {
        this.maps = maps;
    }

    public String login() {
        try {
            JSONParser parser = new JSONParser();
            String jsondata = JSONConfigure.getAngularJSONFile();
            Object obj = parser.parse(jsondata);
            JSONObject json = (JSONObject) obj;
            String username = (String) json.get("username");
            String password = (String) json.get("password");
            System.out.println("username---:" + username);
            System.out.println("password---:" + password);
            LoginModel loginmodel = new LoginModel(username, password);
            Map<String, Object> logindata = LoginDb.login(loginmodel);
            System.out.println("logindata_size" + logindata.isEmpty());
            if (!logindata.isEmpty()) {
                System.out.println("logindata" + logindata);
//                 System.out.println("login id:-"+loginmodel.getId());
                Map<String, Object> context = ActionContext.getContext().getSession();
                context.put("user", loginmodel.getUsername());
                context.put("userid", logindata.get("id"));
                context.put("user_groupid", logindata.get("group_id"));
                System.out.println("is_superadmin" + logindata.get("is_superadmin"));
                context.put("superadmin", logindata.get("is_superadmin"));

                // Save to cookie
                Cookie userId = new Cookie("userid", logindata.get("id") + "");
                Cookie groupId = new Cookie("user_groupid", logindata.get("group_id") + "");
                userId.setMaxAge(60 * 60 * 24 * 365); // Make the cookie last a year
                groupId.setMaxAge(60 * 60 * 24 * 365);
                servletResponse.addCookie(userId);
                servletResponse.addCookie(groupId);

                maps.put("status", "success");
                return "success";
            } else {
                System.out.println("invalid login --------");
                maps.put("status", "error");
                return "success";
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

    public String logout() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.remove("user");

        //deleting value of cookie
        Cookie userId = new Cookie("userid", "");
        Cookie groupId = new Cookie("user_groupid", "");
        //changing the maximum age to 0 seconds  
        userId.setMaxAge(0);
        groupId.setMaxAge(0);

        //adding cookie in the response
        servletResponse.addCookie(userId);
        servletResponse.addCookie(groupId);

        return "success";
    }

    protected HttpServletResponse servletResponse;

    @Override
    public void setServletResponse(HttpServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }
    protected HttpServletRequest servletRequest;

    @Override
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }
}

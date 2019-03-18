/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.system_owner;

import com.controller.common.CookieRead;
import com.db_connection.ConnectionConfiguration;
import static com.model.acb_owner.ACBOwnerDB.LoadIVNDataForACBVersion;
import static com.model.acb_owner.ACBOwnerDB.LoadPDBDataForACBVersion;
import static com.model.acb_owner.ACBOwnerDB.LoadPDBandIVN_Version;
import com.model.common.GlobalDataStore;
import com.model.ivn_supervisor.VehicleversionDB;
import static com.model.ivn_supervisor.VehicleversionDB.perm_status;
import static com.model.ivn_supervisor.VehicleversionDB.temp_status;
import com.model.pdb_owner.PDBversion;
import com.model.pojo.acb_version.ACBVersion;
import com.model.pojo.acb_version.ACBVersionGroup;
import com.model.pojo.ivn_version.EngineControlUnit;
import com.model.pojo.system_version.ECUVariantsMapping;
import com.model.pojo.system_version.SystemVersion;
import com.model.pojo.system_version.SystemVersionGroup;
import com.model.pojo.system_version.Variants;
import com.model.pojo.vehicle_modal.VehicleModelMapping;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

/**
 *
 * @author ets-2
 */
public class SystemOwnerDB {
    public static List<Map> GetECU_Listing() throws SQLException 
    {
        System.out.println("GetECU_Listing");
        Base.open();
        List<Map> row = new ArrayList<>();
        try {
            //Check whether model name already exists in db or not
            String sql = "select CAST(ecu.id as CHAR(100)) as eid,GROUP_CONCAT(DISTINCT(ecu.ecu_name)) as listitem,ecu.ecu_description as description, "
                    + "ecu.status,GROUP_CONCAT(v.variant_name) as variants,GROUP_CONCAT(v.id) as variant_id from engine_control_unit as ecu "
                    + "LEFT JOIN ecu_and_variants_mapping as evm ON evm.ecu_id=ecu.id LEFT JOIN variants as v "
                    + "ON v.id=evm.variant_id group by ecu.id order by ecu.id desc";
            row = Base.findAll(sql);
        } catch (Exception e) {
            System.out.println("ecu error message"+e.getMessage()); 
            e.printStackTrace();
        } finally {
           Base.close();
        }
        return row;
    }
    public static int insertVariants(Variants v) {
       Base.open();
        try {
            //Check whether vehicle name already exists in db or not
            String sql = "SELECT id FROM variants WHERE variant_name ='"+v.getVariantname()+"'";
            Variants var = Variants.findFirst("variant_name = ?", v.getVariantname());
            if(var != null){
//                System.out.println("if");
                return var.getVariantId();
            }else{
//                System.out.println("else");
                if(v.saveIt())
                {
                    return (int) v.getId();
                }
            }
        } catch (Exception e) {
            System.out.println("Variants error message"+e.getMessage()); 
            e.printStackTrace();
            return 0;
        } finally {
           Base.close();
        }
        return 0;
    }
    public static int insertEcuVariantsMapping(ECUVariantsMapping evm) {
        Base.open();
        try {
            //Check whether vehicle name already exists in db or not
            String sql = "SELECT id FROM ecu_and_variants_mapping WHERE ecu_id ="+evm.getEcuId() +" AND variant_id="+evm.getVariantId();
            ECUVariantsMapping evmId = ECUVariantsMapping.findFirst("ecu_id=? AND variant_id=?", evm.getEcuId(),evm.getVariantId());
            if(evmId != null){
//                System.out.println("if");
                GlobalDataStore.globalData.add(evmId.getEVMId());
                return evmId.getEVMId();
            }else{
//                System.out.println("else");
                if(evm.saveIt())
                {
                	int last_inserted_id = (int) evm.getId();
                    GlobalDataStore.globalData.add(last_inserted_id);
                    return last_inserted_id;
                }
            }
        } catch (Exception e) {
            System.out.println("Domain creation error message"+e.getMessage()); 
            e.printStackTrace();
            return 0;
        } finally {
        	Base.close();
        }
        return 0;
    }
    
    public static List<Map> LoadACBVersion_for_System(VehicleModelMapping vmm) throws SQLException {
        System.out.println("LoadACBVersion_for_System");
       Base.open();
        List<Map> acb_row = new ArrayList<>();
//        Map<String, Object> columns = new HashMap<String, Object>();
        try {
            //Check whether model name already exists in db or not                             
//            String acb_sql = "select acb.id,CAST(acb.acb_versionname as CHAR(100)) as acb_versionname from acbversion_group as acbg INNER JOIN acbversion as acb ON acb.id=acbg.acbversion_id where acbg.vehicleversion_id="+vmm.getVehicleversion_id()+" and acbg.vehicle_id="+vmm.getVehicle_id()+" AND acb.status=1 AND acb.subversion_of IS NULL group by acbg.acbversion_id";
            String acb_sql = "select acb.id,CAST(acb.acb_versionname as CHAR(100)) as acb_versionname from acbversion_group as acbg INNER JOIN acbversion as acb ON acb.id=acbg.acbversion_id where acbg.vehicleversion_id="+vmm.getVehicleversion_id()+" and acbg.vehicle_id="+vmm.getVehicle_id()+" AND acb.status=1 AND acb.flag=1 AND acb.features_fully_touchedstatus=1 group by acbg.acbversion_id order by acb.id DESC";
            System.out.println("acb_sql"+acb_sql);
            
              acb_row.add((Map) Base.findAll(acb_sql));

//            columns.put("acbversion_list",acb_row);
//            System.out.println("columns"+columns);
        } catch (Exception e) {
            System.out.println("acb version error message"+e.getMessage()); 
            e.printStackTrace();
        } finally {
        	Base.close();
        }
        return acb_row;
    }
    public static Map<String, Object> LoadACBDataForSystemVersion(ACBVersion acbver,int vehver_id,int veh_id) throws SQLException {
        System.out.println("LoadACBDataForSystemVersion");
        Base.open();
        Map<String, Object> columns_res = new HashMap<String, Object>();
        try {
            String ecu_sql = "select CAST(ag.ecu_id as CHAR(100)) as eid, ecu.ecu_name as listitem,GROUP_CONCAT(DISTINCT(v.id)) as variant_id,GROUP_CONCAT(DISTINCT(v.variant_name)) as variant_name from acbversion_group as ag INNER JOIN engine_control_unit as ecu ON ecu.id=ag.ecu_id INNER JOIN "
                    + "ecu_and_variants_mapping as ev ON ev.ecu_id=ecu.id INNER JOIN variants as v ON v.id=ev.variant_id where ag.vehicleversion_id="+vehver_id+" "
                    + "AND ag.vehicle_id="+veh_id+" AND ag.acbversion_id="+acbver.getACBId()+" GROUP by ag.ecu_id";           
            System.out.println(ecu_sql);
            List<Map> row = new ArrayList<>();
           row = Base.findAll(ecu_sql);
            
            String feature_sql = "select CAST(ag.ecu_id as CHAR(100)) as eid, CAST(ag.domain_and_features_mapping_id as CHAR(100)) as fid,f.feature_name as featurename,d.domain_name as domainname"
                    + " from acbversion_group as ag INNER JOIN domain_and_features_mapping as dfm ON dfm.id=ag.domain_and_features_mapping_id"
                    + " INNER JOIN domain as d ON d.id=dfm.domain_id INNER JOIN features as f ON f.id=dfm.feature_id where ag.vehicleversion_id="+vehver_id+" "
                    + "AND ag.vehicle_id="+veh_id+" AND ag.acbversion_id="+acbver.getACBId();           
            System.out.println(feature_sql);
            
            List<Map> row_fea = new ArrayList<>();
            row_fea = Base.findAll(feature_sql);
            
            /*String ag_sql = "select CAST(ag.ivnversion_id as CHAR(100)) as ivnversion,CAST(ag.pdbversion_id as CHAR(100)) as pdbversion "
                    + "from acbversion_group as ag where ag.acbversion_id="+acbver.getACBId()+" LIMIT 1";*/
            String ag_sql = "select ag.ivnversion_id  as ivnversion, ag.pdbversion_id as pdbversion "
                    + "from acbversion_group as ag where ag.acbversion_id="+acbver.getACBId()+" LIMIT 1";
            LazyList<ACBVersionGroup> ag = ACBVersionGroup.findBySQL(ag_sql);
            int ivnversion_id = ag.get(0).getIVNversion_id();
            List<Map<String, Object>> row_ag = new ArrayList<Map<String, Object>>();
            Map<String, Object> columns_ag = new HashMap<String, Object>();
            columns_ag.put("pdbversion", ag.get(0).getPDBversion_id());
            columns_ag.put("ivnversion", ag.get(0).getIVNversion_id());
            row_ag.add(columns_ag);
                       
            System.out.println("row_ag"+row_ag);
            
            String canmodel_sql = "SELECT CAST(cn.network_can_id as CHAR(100)) as id,\n" +
                "c.network_name as listitem,\n" +
                "CAST(cn.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n" +
                "cn.available_status as status \n" +
                "FROM ivn_canmodels AS cn INNER JOIN network as c ON c.id=cn.network_can_id \n" +
                "where cn.ivnversion_id="+ivnversion_id;      
            System.out.println(canmodel_sql);
            
            List<Map<String, Object>> row1 = new ArrayList<Map<String, Object>>();
            List<Map> canModel = Base.findAll(canmodel_sql);
            for (Map cm : canModel) {
              Map<String, Object> columns1 = new HashMap<String, Object>();
              columns1.put("id",cm.get("id"));
              columns1.put("listitem", cm.get("listitem"));
              columns1.put("vmm_id", cm.get("vmm_id"));
              columns1.put("status", cm.get("status"));
              columns1.put("ntype","can");
              row1.add(columns1);
            }
            
            String linmodel_sql = "SELECT CAST(ln.network_lin_id as CHAR(100)) as id,\n" +
                "l.network_name as listitem,\n" +
                "CAST(ln.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n" +
                "ln.available_status as status \n" +
                "FROM ivn_linmodels AS ln INNER JOIN network as l ON l.id=ln.network_lin_id \n" +
                "where ln.ivnversion_id="+ivnversion_id;
            System.out.println(linmodel_sql);
           
            List<Map<String, Object>> row2 = new ArrayList<Map<String, Object>>();
            List<Map> linModel = Base.findAll(linmodel_sql);
            for (Map lm : linModel) {
              Map<String, Object> columns2 = new HashMap<String, Object>();
              columns2.put("id", lm.get("id"));
              columns2.put("listitem", lm.get("listitem"));
              columns2.put("vmm_id", lm.get("vmm_id"));
              columns2.put("status", lm.get("status"));
              columns2.put("ntype","lin");
              row2.add(columns2);
            }
            
            String hwmodel_sql = "SELECT CAST(hw.network_hardware_id as CHAR(100)) as id,\n" +
                "h.network_name as listitem,\n" +
                "CAST(hw.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n" +
                "hw.available_status as status \n" +
                "FROM ivn_hardwaremodels AS hw INNER JOIN network as h ON h.id=hw.network_hardware_id \n" +
                "where hw.ivnversion_id="+ivnversion_id;
            System.out.println(hwmodel_sql);
           
            List<Map<String, Object>> row3 = new ArrayList<Map<String, Object>>();
            List<Map> hwModel = Base.findAll(hwmodel_sql);
            for (Map hw : hwModel) {
              Map<String, Object> columns3 = new HashMap<String, Object>();
              columns3.put("id", hw.get("id"));
              columns3.put("listitem", hw.get("listitem"));
              columns3.put("vmm_id", hw.get("vmm_id"));
              columns3.put("status", hw.get("status"));
              columns3.put("ntype","hardware");
              row3.add(columns3);
            }
            
            String ivnsignalgroup_sql = "select CAST(s.id as CHAR(100)) as sid,s.signal_name as listitem,s.signal_description as description from ivnversion_group as ig inner join signals as s "
                    + "on FIND_IN_SET(s.id,ig.signal_group) > 0 where ig.ivnversion_id="+ivnversion_id;       
            System.out.println(ivnsignalgroup_sql);
            
            List<Map<String, Object>> row_sig = new ArrayList<Map<String, Object>>();
            List<Map> ivnsignalgroup = Base.findAll(ivnsignalgroup_sql);
            for (Map isg : ivnsignalgroup) {
              Map<String, Object> columns_sig = new HashMap<String, Object>();
              columns_sig.put("sid", isg.get("sid"));
              columns_sig.put("listitem", isg.get("listitem"));
              columns_sig.put("description", isg.get("description"));
              columns_sig.put("network_type","signal");
              row_sig.add(columns_sig);
            }
            
            String v_sql = "SELECT \n" +
                "vmm.vehicleversion_id,vmm.vehicle_id \n" +
                "FROM ivn_canmodels AS cn \n" +
                "INNER JOIN vehicle_and_model_mapping AS vmm ON vmm.id = cn.vehicle_and_model_mapping_id \n" +
                "where cn.ivnversion_id="+ivnversion_id+" limit 1";

            System.out.println("vehciledetail_sql"+v_sql);
             List<Map> vrs = Base.findAll(v_sql);
            String vehciledetail_sql = null;
            if(!vrs.isEmpty()){
                vehciledetail_sql = "SELECT \n" +
                "vv.id as vehver_id,\n" +
                "v.id as vehicle_id,\n" +
                "vm.modelname as modelname,\n" +
                "CAST(vmm.id as CHAR(100)) as vmm_id \n" +
                "from vehicle_and_model_mapping as vmm \n" +
                "INNER JOIN vehicleversion as vv on vv.id=vmm.vehicleversion_id \n" +
                "INNER JOIN vehicle as v on v.id=vmm.vehicle_id \n" +
                "INNER JOIN vehiclemodel as vm on vm.id=vmm.model_id\n" +
                "where vmm.vehicleversion_id="+vrs.get(0).get("vehicleversion_id")+" AND vmm.vehicle_id="+vrs.get(0).get("vehicle_id");
            }
            System.out.println("vehciledetail_sql1"+vehciledetail_sql);

            List<Map> row_veh = Base.findAll(vehciledetail_sql);
            
            columns_res.put("ecu_list",row);
            columns_res.put("feature_list",row_fea);
            
            columns_res.put("can",row1);
            columns_res.put("lin",row2);
            columns_res.put("hardware",row3);
            columns_res.put("signal",row_sig);
            columns_res.put("vehicledetail_list",row_veh);   
            columns_res.put("acbversion_group",row_ag);
        
        } catch (Exception e) {
            System.out.println("acb version error message"+e.getMessage()); 
            e.printStackTrace();
        } finally {
        	Base.close();
        }
        return columns_res;
    }
    public static List<Map<String, Object>> LoadSystemPreviousVehicleversionStatus(SystemVersion m) throws SQLException {
        System.out.println("LoadSystemPreviousVehicleversionStatus");
//        String status = null;
        Base.open();
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            String sql = "SELECT s.status,s.flag FROM systemversion s WHERE s.id=?";
            LazyList<SystemVersion> sysVer = SystemVersion.findBySQL(sql, m.getSystemVersionId());
            row = sysVer.toMaps();
        } catch (Exception e) {
            System.out.println("System version error message"+e.getMessage()); 
            e.printStackTrace();
        } finally {
            Base.close();
        }
        return row;
    }
    public static Object[] insertSystemVersion(SystemVersion sv) {
       Base.open();
        float versionname=0.0f;
        try {
            System.out.println("status_value"+sv.getStatus());
            System.out.println("flag_value"+sv.getFlag());
            if(sv.getOperation_status().equals("create")){
                String sql = "SELECT id, system_versionname FROM systemversion ORDER BY system_versionname DESC LIMIT 1";
                LazyList<SystemVersion> sys = SystemVersion.findAll().limit(1).orderBy("system_versionname  DESC");
                if(sys.isEmpty()){
                    versionname = (float) 1.0;
                }
                else{
                    versionname = (float) 1.0 + sys.get(0).getSystemVersionname();
                }           
                SystemVersion sysIns = new SystemVersion();
                sysIns.set("system_versionname", versionname);
                sysIns.set("status", sv.getStatus());
                sysIns.set("created_date", sv.getCreated_date());
                sysIns.set("created_or_updated_by", sv.getCreated_or_updated_by());
                sysIns.set("flag", sv.getFlag());

                if(sysIns.saveIt())
                {
                    return new Object[]{sysIns.getId(), versionname};
                }
            }
            else{   
                SystemVersion version = SystemVersion.findById(sv.getSystemVersionId());
                versionname = version.getSystemVersionname();
                System.out.println("object_value_in_update"+sv.getSystemVersionId()+sv.getStatus()+sv.getCreated_or_updated_by());
                version.set("status", sv.getStatus());
                version.set("created_or_updated_by", sv.getCreated_or_updated_by());
                version.set("flag", sv.getFlag());
                version.saveIt();            
                return new Object[]{sv.getSystemVersionId(), versionname};
            }                
        } catch (Exception e) {
            System.out.println("System version error message"+e.getMessage()); 
            e.printStackTrace();
            return new Object[]{0, versionname};
            
        } finally {
        	Base.close();
        }
        return new Object[]{0, versionname};
    }
    public static int insertSystemVersionGroup(SystemVersionGroup sg) {
       Base.open();
        int resultSet_count = 0;
        try {
            boolean flagvalue;
            if(sg.getOperation_status().equals("update")){
                System.out.println("update_if");
//                String sql = "select pg.id from pdbversion_group as pg where "
//                        + "pg.pdbversion_id="+pg.getPDBversion_id()
//                        + " AND pg.vehicle_and_model_mapping_id="+pg.getVehicle_and_model_mapping_id()+" AND pg.domain_and_features_mapping_id="+pg.getDomain_and_features_mapping_id()
//                        + " AND pg.available_status='"+pg.getAvailable_status()+"'";
                String sql = "select * from systemversion_group as sg where "
                        + "sg.systemversion_id="+sg.getSystemVersionId()
                        + " AND sg.vehicleversion_id="+sg.getVehicleVersionId()+" AND sg.vehicle_id="+sg.getVehicleId()
                        + " AND sg.acbversion_id="+sg.getACBversionId()
                        + " AND sg.domain_and_features_mapping_id="+sg.getDomain_and_features_mapping_id()+" AND sg.ecu_id="+sg.getEcuId()+" AND sg.variant_id="+sg.getVariantId();
                System.out.println("sql_query"+sql);
                
                LazyList<SystemVersionGroup> sysvg_list = SystemVersionGroup.findBySQL(sql);
                for (SystemVersionGroup sysvg : sysvg_list) {
                        System.out.println("for Each");
                        if(sysvg.getAvailableStatus() != sg.getAvailableStatus()){
                            System.out.println("if");
                            String update_sql = "UPDATE systemversion_group SET " +
                                "available_status = ?  WHERE id = ?";
                           SystemVersionGroup.update("available_status = ?", "id = ?", sg.getAvailableStatus(),
   								sysvg.getSystemVersionId());
                        }
                        GlobalDataStore.globalData.add(sysvg.getSystemVersionId());                  
                }                            
                resultSet_count = sysvg_list.size();
                System.out.println("getrow_count"+sysvg_list.size());                           
            }            
            if(resultSet_count == 0){
                if(sg.saveIt())
                {
                    GlobalDataStore.globalData.add((Integer) sg.getId());
                }
            }
            System.out.println("globalData"+GlobalDataStore.globalData);
            if(sg.getButton_type().equals("save")){
                return temp_status;
            }
            else if(sg.getButton_type().equals("submit")){
                return perm_status;
            }
        } catch (Exception e) {
            System.out.println("system version error message"+e.getMessage()); 
            e.printStackTrace();
            return 0;
            
        } finally {
            Base.close();
        }
        return 0;
    }
    public static List<Map<String, Object>> LoadSystemVersion(String filter) throws SQLException {
        System.out.println("LoadSystemVersion");
        Base.open();
        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
        try {
            String sql;
            if(filter.equals("active"))
                sql = "select s.id,s.system_versionname,s.status from systemversion s where s.flag=1 and s.status=1";
            else
                sql = "select s.id,s.system_versionname,s.status from systemversion s";
            
           row = SystemVersion.findBySQL(sql).toMaps();
            System.out.println("row_data"+row);
        } catch (Exception e) {
            System.out.println("System version error message"+e.getMessage()); 
            e.printStackTrace();
            
        } finally {
            Base.close();
        }
        return row;
    }
    public static Map<String, Object> LoadSystemPreviousversionData(SystemVersion systemver) throws SQLException {
        System.out.println("LoadSystemPreviousversionData");
//        int pdbversion_id = 0;
//        int ivnversion_id = 0;
//        int vehicleversion_id = 0;
//        int vehicle_id = 0;
        Base.open();
        Map<String, Object> columns_res = new HashMap<String, Object>();
        try {
    //        List<Map<String, Object>> result_row = new ArrayList<Map<String, Object>>();
            String systemversion_sql = "select CAST(svg.systemversion_id as CHAR(100)) as systemversion,CAST(svg.vehicleversion_id as CHAR(100)) as vehicleversion,CAST(svg.vehicle_id as CHAR(100)) as vehiclename,"
                    + "CAST(svg.acbversion_id as CHAR(100)) as acbversion, "
                    + "CAST(svg.ecu_id as CHAR(100)) as ecu "
                    + "from systemversion_group as svg where svg.systemversion_id="+systemver.getSystemVersionId()+" LIMIT 1"; 
            System.out.println("modelversion_sql"+systemversion_sql);
            List<Map<String, Object>> row_svg = SystemVersionGroup.findBySQL(systemversion_sql).toMaps(); 

            String feature_sql = "select CAST(svg.domain_and_features_mapping_id as CHAR(100)) as fid,d.domain_name as domainname,f.feature_name as featurename from systemversion_group as svg "
                    + "inner join domain_and_features_mapping as dfm on dfm.id = svg.domain_and_features_mapping_id INNER JOIN domain as d ON d.id = dfm.domain_id \n"
                    + "INNER JOIN features as f ON f.id = dfm.feature_id \n" 
                    + "where svg.systemversion_id="+systemver.getSystemVersionId()+" group by svg.domain_and_features_mapping_id"; 
            System.out.println("feature_sql"+feature_sql);
            
            List<Map> row_fea = Base.findAll(feature_sql);
            
            String ecu_sql = "select CAST(svg.ecu_id as CHAR(100)) as eid, ecu.ecu_name as listitem,GROUP_CONCAT(DISTINCT(v.id)) as variant_id,GROUP_CONCAT(DISTINCT(v.variant_name)) as variant_name from systemversion_group as svg "
                    + "INNER JOIN engine_control_unit as ecu ON ecu.id=svg.ecu_id "
                    + "INNER JOIN variants as v ON v.id=svg.variant_id where svg.systemversion_id="+systemver.getSystemVersionId()+" GROUP by svg.ecu_id ORDER BY svg.ecu_id DESC";           
            System.out.println("ecu_sql"+ecu_sql);
            
            List<Map> row = Base.findAll(ecu_sql);

            String svglist_sql = "select CAST(svg.domain_and_features_mapping_id as CHAR(100)) as dfm_id,CAST(svg.variant_id as CHAR(100)) as variant_id, svg.available_status as status from systemversion_group as svg where svg.systemversion_id="+systemver.getSystemVersionId()+" ORDER BY svg.id DESC";           
            System.out.println("svglist_sql"+svglist_sql);

            List<Map<String, Object>> row_svglist = SystemVersionGroup.findBySQL(svglist_sql).toMaps();
            
            String sv_status_sql = "select sv.status from systemversion sv where sv.id="+systemver.getSystemVersionId();
           
            List<Map<String, Object>> row_st = SystemVersion.findBySQL(sv_status_sql).toMaps();

            columns_res.put("systemversion",row_svg);
            columns_res.put("feature_list",row_fea);
            columns_res.put("ecu_variant_list",row);
            columns_res.put("systemdata_list",row_svglist);
            columns_res.put("systemversion_status",row_st);
        
        } catch (Exception e) {
            System.out.println("Load System version error message"+e.getMessage()); 
            e.printStackTrace();
        } finally {
           Base.close();
        }
        return columns_res;
    }
    public static List<Map<String, Object>> GetSystemVersion_Listing() throws SQLException {
        System.out.println("GetSystemVersion_Listing");
        Base.open();
        List<Map<String, Object>> row = new ArrayList<>();
        try {
            String sql = "SELECT sv.id as id,CAST(system_versionname as CHAR(100)) as system_versionname, CAST(versionname as CHAR(100)) as vehicle_versionname, "
                        + "GROUP_CONCAT( DISTINCT (acb.acb_versionname) ) AS acb_versionname, "
                        + "GROUP_CONCAT( DISTINCT (v.vehiclename) ) AS vehiclename, "
                        + "GROUP_CONCAT( DISTINCT (f.feature_name) ) AS features, "
                        + "GROUP_CONCAT( DISTINCT (vt.variant_name) ) AS variants,"
                        + " sv.status, sv.flag FROM systemversion_group AS svg "
                        + " INNER JOIN vehicle AS v ON v.id = svg.vehicle_id INNER JOIN vehicleversion AS vv ON"
                        + " vv.id = svg.vehicleversion_id INNER JOIN acbversion AS acb ON acb.id = svg.acbversion_id"
                        + " INNER JOIN systemversion AS sv ON sv.id = svg.systemversion_id"
                        + " INNER JOIN domain_and_features_mapping AS dfm ON dfm.id = svg.domain_and_features_mapping_id"
                        + " INNER JOIN features AS f ON f.id = dfm.feature_id"
                        + " INNER JOIN variants AS vt ON vt.id = svg.variant_id"
                        + " GROUP BY svg.systemversion_id DESC";
            //        String sql = "select * from vehiclemodel where modelname = '" + v.getModelname().trim() + "'";
            List<Map> sys_list = Base.findAll(sql);
            for(Map sys: sys_list) {
                Map<String, Object> columns = new HashMap<String, Object>();
                columns.put("id", sys.get("id"));
                columns.put("system_versionname", sys.get("system_versionname"));
                columns.put("vehicle_versionname", sys.get("vehicle_versionname"));
                columns.put("acb_versionname", sys.get("acb_versionname"));
                columns.put("vehiclename", sys.get("vehiclename"));
                columns.put("features", sys.get("features"));
                columns.put("variants", sys.get("variants"));
                columns.put("status", sys.get("status"));
                columns.put("flag", sys.get("flag"));
                if (CookieRead.getGroupIdFromSession() == 2) {
                    columns.put("delBut", 1);
                }else{
                    columns.put("delBut", 0);
                }
                row.add(columns);
            }
        } catch (Exception e) {
            System.out.println("system version listing error message"+e.getMessage()); 
            e.printStackTrace();
        } finally {
           Base.close();
        }
        return row;
    }
    public static Map<String, Object> GetSystemEng_Dashboarddata() throws SQLException {
        System.out.println("GetSystemEng_Dashboarddata");
        Base.open();
        Map<String, Object> columns = new HashMap<String, Object>();
        
        //Get ACB version count
        //String acbver_sql = "select * from acbversion";
        //System.out.println("resultset_count"+acbver_rs.getRow());
        columns.put("acbversion_count", ACBVersion.count());      
        
        //Get System version count
        //String sysver_sql = "select * from systemversion";
        //System.out.println("resultset_count"+sysver_rs.getRow());
        columns.put("systemversion_count", SystemVersion.count());  
        
        //Get ECU variants count
        //String var_sql = "select * from variants";
        //ystem.out.println("resultset_count"+var_rs.getRow());
        columns.put("variants_count",Variants.count());    
        
        Base.close();
        return columns;
    }
    public static void deleteEcuVariantsMapping(int ecu_id) throws SQLException{
        Base.open();
        System.out.println("deleteEcuVariantsMapping"+GlobalDataStore.globalData);
        //preparedStatement = connection.prepareStatement("delete from ecu_and_variants_mapping where ecu_id="+ecu_id+" AND id NOT IN ("+StringUtils.join(GlobalDataStore.globalData, ',')+")");
        ECUVariantsMapping.delete("ecu_id = ? AND id NOT IN(?)", ecu_id,StringUtils.join(GlobalDataStore.globalData, ','));
        Base.close();
        GlobalDataStore.globalData.clear();
    }
    public static void deleteSystemVersion_Group(int systemversion_id, String action_type) throws SQLException{
        Base.open();
        System.out.println("deletesystemversiongroup"+GlobalDataStore.globalData);
        System.out.println("action_type"+action_type);
        if(action_type.equals("update")){
            //preparedStatement = connection.prepareStatement("delete from systemversion_group where systemversion_id="+systemversion_id+" AND id NOT IN ("+StringUtils.join(GlobalDataStore.globalData, ',')+")");
            SystemVersionGroup.delete("systemversion_id = ? AND id NOT IN(?)", StringUtils.join(GlobalDataStore.globalData, ','));
        }
        Base.close();
        GlobalDataStore.globalData.clear();
    }   
    
    public static String getECUNameFromId(int id) {
        Base.open();
        try {
            //String fetch_ecu_name = "SELECT ecu_name FROM engine_control_unit WHERE id = " + id;
           EngineControlUnit ecu = EngineControlUnit.findById(id);
            if (ecu != null) {
                return ecu.getEcuName();
            }
        } catch (Exception e) {
            System.out.println("Error on Fetching ECU Name " + e.getMessage());
            e.printStackTrace();
        } finally {
           Base.close();
        }
        return null;
    }
    
    public static int getIdFromECUName(String ecuName) {
       Base.open();
        try {
            //String fetch_ecu_id = "SELECT id FROM engine_control_unit WHERE ecu_name = '" + ecuName+"'";
            EngineControlUnit ecu = EngineControlUnit.findFirst("ecu_name = ?", ecuName);
            if (ecu != null) {
                return ecu.getECUId();
            }
        } catch (Exception e) {
            System.out.println("Error on Fetching ECU Name ID " + e.getMessage());
            e.printStackTrace();
        } finally {
            Base.close();
        }
        return 0;
    }
    
    public static int getIdFromVariantName(String variantName) {
        Base.open();
        try {
            //String fetch_variant_id = "SELECT id FROM variants WHERE variant_name = '" + variantName+"'";
            Variants variants = Variants.findFirst("variant_name = ?", variantName);
            if (variants != null) {
                return variants.getVariantId();
            }
        } catch (Exception e) {
            System.out.println("Error on Fetching Variant Name ID " + e.getMessage());
            e.printStackTrace();
        } finally {
           Base.close();
        }
        return 0;
    }
}

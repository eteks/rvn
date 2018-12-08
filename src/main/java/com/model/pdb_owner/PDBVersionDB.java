/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pdb_owner;

import com.db_connection.ConnectionConfiguration;
import com.model.common.GlobalDataStore;
import com.model.pojo.acb_version.ACBVersionGroup;
import com.model.pojo.pdb_version.Domain;
import com.model.pojo.pdb_version.DomainFeaturesMapping;
import com.model.pojo.pdb_version.Features;
import com.model.pojo.pdb_version.PDBVersion;
import com.model.pojo.pdb_version.PDBVersionGroup;
import com.model.pojo.vehicle_modal.Vehicle;
import com.model.pojo.vehicle_modal.VehicleModel;
import com.model.pojo.vehicle_modal.VehicleModelMapping;
import com.model.pojo.vehicle_modal.VehicleVersion;
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
public class PDBVersionDB {

    public static int temp_status = 0;
    public static int perm_status = 1;

    public static int insertDomain(Domain d) {
        Base.open();
        try {
            //Check whether vehicle name already exists in db or not
            LazyList<Domain> dom = Domain.where("domain_name = ?", d.getDomainname().trim());
            if (dom.size() > 0) {
                return dom.get(0).getDomainId();
            } else {
                d.saveIt();
                return (int) d.getId();
            }
        } catch (Exception e) {
            System.out.println("Domain creation error message" + e.getMessage());
            e.printStackTrace();
            return 0;

        } finally {
            Base.close();
        }
    }

    public static int insertFeatures(Features f) {
        try {
            //Check whether vehicle name already exists in db or not
            LazyList<Features> feature = Features.where("feature_name = ?", f.getFeaturename().trim());
            if (feature.size() > 0) {
                return feature.get(0).getFeatureId();
            } else {
                f.saveIt();
                return (int) f.getId();
            }
        } catch (Exception e) {
            System.out.println("Domain creation error message" + e.getMessage());
            e.printStackTrace();
            return 0;
        } finally {
            Base.close();
        }
    }

    public static int insertDomainFeaturesMapping(DomainFeaturesMapping dfm) {
        Base.open();
        try {
            //Check whether vehicle name already exists in db or not
            LazyList<DomainFeaturesMapping> dfmList = DomainFeaturesMapping.where("domain_id = ? AND feature_id = ?", dfm.getDomainId(), dfm.getFeatureId());
            if (dfmList.size() > 0) {
                return dfmList.get(0).getDFMId();
            } else {
                dfm.saveIt();
                return (int) dfm.getId();
            }
        } catch (Exception e) {
            System.out.println("Domain creation error message" + e.getMessage());
            e.printStackTrace();
            return 0;
        } finally {
            Base.close();
        }
    }

    public static List<Map<String, Object>> LoadFeaturesList() throws SQLException {
        System.out.println("LoadFeaturesList");
        List<Map<String, Object>> row = new ArrayList<>();
        Base.open();
        try {
            String sql = "SELECT d.domain_name as domain, f.feature_name as fea, CAST(dfm.id as CHAR(100)) as fid from domain_and_features_mapping as dfm INNER JOIN domain AS d ON d.id = dfm.domain_id "
                    + "INNER JOIN features AS f ON f.id = dfm.feature_id";
            LazyList<DomainFeaturesMapping> domainFeatureList = DomainFeaturesMapping.findAll();
            for (DomainFeaturesMapping dfm : domainFeatureList) {
                Map<String, Object> columns = new HashMap<>();
                columns.put("fid", dfm.getDFMId());
                columns.put("domain", dfm.parent(Domain.class).getDomainname());
                columns.put("fea", dfm.parent(Features.class).getFeaturename());
                row.add(columns);
            }
        } catch (Exception e) {
            System.out.println("Load Feature List Error Message" + e.getMessage());
            e.printStackTrace();
        } finally {
            Base.close();
        }
        return row;
    }

    public static Object[] insertPDBVersion(PDBVersion pv) {
        float versionname = 0.0f;
        Base.open();
        try {
            if (pv.getOperation_status().equals("create")) {
                LazyList<PDBVersion> pdb = PDBVersion.findAll().limit(1).orderBy("pdb_versionname DESC");
                if (pdb.isEmpty()) {
                    versionname = (float) 1.0;
                } else {
                    versionname = (float) 1.0 + pdb.get(0).getPDBVersionname();
                }
                PDBVersion insertPDB = new PDBVersion();
                insertPDB.set("pdb_versionname", versionname);
                insertPDB.set("status", pv.getStatus());
                insertPDB.set("created_or_updated_by", pv.getCreated_or_updated_by());
                insertPDB.set("flag", pv.getFlag());

                if (insertPDB.saveIt()) {
                    return new Object[]{insertPDB.getId(), versionname};
                }
            } else {
                PDBVersion version = PDBVersion.findById(pv.getPDBId());
                versionname = version.getPDBVersionname();
                version.set("status", pv.getStatus());
                version.set("created_or_updated_by", pv.getCreated_or_updated_by());
                version.set("flag", pv.getFlag());
                version.saveIt();
                return new Object[]{pv.getId(), versionname};
            }
        } catch (Exception e) {
            System.out.println("pdb version error message" + e.getMessage());
            e.printStackTrace();
            return new Object[]{0, versionname};
        } finally {
            Base.close();
        }
        return new Object[]{0, versionname};
    }

    public static int insertPDBVersionGroup(PDBVersionGroup pg) {
        int resultSet_count = 0;
        Base.open();
        try {
            boolean flagvalue;
            if (pg.getOperation_status().equals("update")) {
                LazyList<PDBVersionGroup> pdbVerGrpList = PDBVersionGroup.where("pdbversion_id = ? AND vehicle_and_model_mapping_id = ? AND domain_and_features_mapping_id= ?", pg.getPDBversion_id(), pg.getVehicle_and_model_mapping_id(), pg.getDomain_and_features_mapping_id());
                for (PDBVersionGroup pdbvg : pdbVerGrpList) {
                    if (!pdbvg.getString("available_status").equals(pg.getAvailable_status())) {
                        PDBVersionGroup.update("available_status = ?", "id = ?", pg.getAvailable_status(), pdbvg.getPDBversion_id());
                    }
                    GlobalDataStore.globalData.add(pdbvg.getPDBVersionGroupId());
                }
                resultSet_count = pdbVerGrpList.size();
                System.out.println("getrow_count" + pdbVerGrpList.size());
            }
            if (resultSet_count == 0) {
                boolean status = pg.saveIt();
                if (pg.getButton_type().equals("other")) {
                    if (status) {
                        return (int) pg.getId();
                    }
                } //Avoid this condition for storing pdb data from system owner
                else {
                    if (status) {
                        GlobalDataStore.globalData.add((Integer) pg.getId());
                    }
                }
            }
            if (pg.getButton_type().equals("save")) {
                return temp_status;
            } else if (pg.getButton_type().equals("submit")) {
                return perm_status;
            }
        } catch (Exception e) {
            System.out.println("vehicle version error message" + e.getMessage());
            e.printStackTrace();
            return 0;

        } finally {
            Base.close();
        }
        return 0;
    }

    public static List<Map<String, Object>> LoadPDBVersion(String filter) throws SQLException {
        System.out.println("LoadPDBVersion");
        List<Map<String, Object>> row = new ArrayList<>();
        try {
            //Check whether model name already exists in db or not
            String sql;
            if (filter.equals("active")) {
                sql = "SELECT p.id,p.pdb_versionname,p.status FROM pdbversion p WHERE p.flag=1 AND p.status=1";
            } else {
                sql = "SELECT p.id,p.pdb_versionname,p.status FROM pdbversion p";
            }
            row = PDBVersion.findBySQL(sql).toMaps();
            System.out.println("row_data" + row);
        } catch (Exception e) {
            System.out.println("acb version error message" + e.getMessage());
        } finally {
            Base.close();
        }
        return row;
    }

    public static Map<String, Object> LoadPDBPreviousVehicleversionData(PDBVersion pdbver) throws SQLException {
        System.out.println("LoadPDBPreviousVehicleversionData");
        Map<String, Object> columns3 = new HashMap<>();
        Base.open();
        try {
            String vehciledetail_sql = "SELECT pg.* FROM pdbversion_group AS pg WHERE pg.pdbversion_id = ? GROUP BY pg.vehicle_and_model_mapping_id ORDER BY pg.vehicle_and_model_mapping_id";
            LazyList<PDBVersionGroup> vehicleDetailList = PDBVersionGroup.findBySQL(vehciledetail_sql, pdbver.getPDBId());
            List<Map<String, Object>> row = new ArrayList<>();
            for (PDBVersionGroup pdbVeh : vehicleDetailList) {
                Map<String, Object> columns = new HashMap<>();
                columns.put("vehver_id", pdbVeh.parent(VehicleModelMapping.class).getInteger("vehicleversion_id"));
                columns.put("vehicle_id", pdbVeh.parent(VehicleModelMapping.class).getInteger("vehicle_id"));
                columns.put("modelname", pdbVeh.parent(VehicleModelMapping.class).parent(VehicleModel.class).getString("modelname"));
                columns.put("vehicle_model_mapping_id", pdbVeh.getInteger("vehicle_and_model_mapping_id"));
                row.add(columns);
            }

            LazyList<PDBVersionGroup> featureDetailList = PDBVersionGroup.where("pdbversion_id = ?", pdbver.getPDBId());
            List<Map<String, Object>> row1 = new ArrayList<>();
            for (PDBVersionGroup pdvFea : featureDetailList) {
                Map<String, Object> columns1 = new HashMap<>();
                columns1.put("vmm_id", pdvFea.getVehicle_and_model_mapping_id());
                columns1.put("fid", pdvFea.getDomain_and_features_mapping_id());
                columns1.put("status", pdvFea.getAvailable_status());
                columns1.put("domainname", pdvFea.parent(DomainFeaturesMapping.class).parent(Domain.class).getDomainname());
                columns1.put("featurename", pdvFea.parent(DomainFeaturesMapping.class).parent(Features.class).getFeaturename());
                row1.add(columns1);
            }

            boolean pdbStatus = PDBVersion.findById(pdbver.getPDBId()).getBoolean("status");
            List<Map<String, Object>> row2 = new ArrayList<>();
            Map<String, Object> columns2 = new HashMap<>();
            columns2.put("status", pdbStatus);
            row2.add(columns2);

            columns3.put("vehicledetail_list", row);
            columns3.put("featuredetail_list", row1);
            columns3.put("pdbversion_status", row2);
            System.out.println("columns" + columns3);
        } catch (Exception e) {
            System.out.println("acb version error message" + e.getMessage());
            e.printStackTrace();
        } finally {
            Base.close();
        }
        return columns3;
    }

    public static List<Map<String, Object>> LoadPDBPreviousVehicleversionStatus(PDBVersion p) throws SQLException {
        System.out.println("LoadPDBPreviousVehicleversionStatus");
        List<Map<String, Object>> row = new ArrayList<>();
        Base.open();
        try {
            //Check whether model name already exists in db or not
            PDBVersion pdbVersion = PDBVersion.findById(p.getPDBId());
            Map<String, Object> columns = new HashMap<>();
            columns.put("status", pdbVersion.getStatus());
            columns.put("flag", pdbVersion.getFlag());
            row.add(columns);
        } catch (Exception e) {
            System.out.println("acb version error message" + e.getMessage());
        } finally {
            Base.close();
        }
        return row;
    }

    public static List<Map<String, Object>> GetDomainFeaturesListing(Features fea) throws SQLException {
        System.out.println("GetFeatures_Listing");
        List<Map<String, Object>> row = new ArrayList<>();
        Base.open();
        try {
            //Check whether model name already exists in db or not
            LazyList<DomainFeaturesMapping> dfmList = DomainFeaturesMapping.findAll().include(Domain.class).include(Features.class).orderBy("domain_id desc");
            for (DomainFeaturesMapping dfm : dfmList) {
                Map<String, Object> columns = new HashMap<>();
                columns.put("fid", dfm.getDFMId());
                columns.put("domain", dfm.parent(Domain.class).getDomainname());
                columns.put("fea", dfm.parent(Features.class).getFeaturename());
                row.add(columns);
            }
        } catch (Exception e) {
            System.out.println("acb version error message" + e.getMessage());
            e.printStackTrace();
        } finally {
            Base.close();
        }
        return row;
    }

    public static List<Map> GetPDBVersion_Listing() throws SQLException {
        System.out.println("GetPDBVersion_Listing");
        List<Map> row = new ArrayList<>();
        Base.open();
        try {
            //Check whether model name already exists in db or not
            String sql = "SELECT pdb.id as id, CAST(pdb.pdb_versionname as CHAR(100)) as pdb_version, "
                    + "GROUP_CONCAT(DISTINCT(vv.id)) as vehicleversion_id, "
                    + "GROUP_CONCAT(DISTINCT(vv.versionname)) as veh_version, "
                    + "GROUP_CONCAT(DISTINCT(v.vehiclename)) as vehicle, "
                    + "GROUP_CONCAT(DISTINCT(vm.modelname)) as model,pdb.status as status,pdb.flag FROM pdbversion as pdb "
                    + "LEFT JOIN pdbversion_group as pg ON pg.pdbversion_id=pdb.id "
                    + "LEFT JOIN vehicle_and_model_mapping as vmm ON vmm.id=pg.vehicle_and_model_mapping_id "
                    + "LEFT JOIN vehicle as v ON v.id=vmm.vehicle_id "
                    + "LEFT JOIN vehiclemodel as vm ON vm.id=vmm.model_id "
                    + "LEFT JOIN vehicleversion as vv ON vv.id=vmm.vehicleversion_id group by pg.pdbversion_id order by pdb.id desc";
            //String sql = "SELECT * FROM pdbversion AS pdb, pdbversion_group AS pg GROUP BY pdb.pg.pdbversion_id ORDER BY pdb.id DESC";
            row = Base.findAll(sql);
            //System.out.println("Finallll "+row);
        } catch (Exception e) {
            System.out.println("acb version error message" + e.getMessage());
        } finally {
            Base.close();
        }
        return row;
    }

    public static Map<String, Object> GetPDB_Dashboarddata() throws SQLException {
        System.out.println("GetPDB_Dashboarddata");
        Map<String, Object> columns = new HashMap<>();
        Base.open();
        //Get PDB Versions count
        columns.put("pdbversion_count", PDBVersion.count());

        //Get PDB Versions count
        columns.put("pdbfeatures_count", Features.count());

        //Get Vehicle Versions count
        columns.put("vehicleversion_count", VehicleVersion.count());
        Base.close();
        return columns;
    }

    public static void deletePDBVersion_Group(int pdbversion_id, String action_type) throws SQLException {
        System.out.println("deletepdbversiongroup" + GlobalDataStore.globalData);
        System.out.println("action_type" + action_type);
        Base.open();
        if (action_type.equals("update")) {
            String sql = "pdbversion_id= ? AND id NOT IN (" + StringUtils.join(GlobalDataStore.globalData, ',') + ")";
            LazyList<PDBVersionGroup> pdbVersionGroup = PDBVersionGroup.where(sql, pdbversion_id);
            //preparedStatement = connection.prepareStatement("delete from pdbversion_group where pdbversion_id=" + pdbversion_id + " AND id NOT IN (" + StringUtils.join(GlobalDataStore.globalData, ',') + ")");
            for (PDBVersionGroup pdbvg : pdbVersionGroup) {
                pdbvg.delete();
            }
        }
        GlobalDataStore.globalData.clear();
    }

    public static Object[] getDomainFeatureId(String domain_name, String feature_name) {
        int domain_id = 0, feature_id = 0;
        Base.open();
        try {
            Domain domainIdData = Domain.findFirst("domain_name = ?", domain_name);
            if (domainIdData != null) {
                domain_id = domainIdData.getDomainId();
            }

            Features featureIdData = Features.findFirst("feature_name = ?", feature_name);
            if (featureIdData != null) {
                feature_id = featureIdData.getFeatureId();
            }
            return new Object[]{domain_id, feature_id};
        } catch (Exception e) {
            System.out.println("Error on Fetching Domain & Feature Id" + e.getMessage());
            return new Object[]{domain_id, feature_id};

        } finally {
            Base.close();
        }
    }

    public static int getDomainFeatureMappingId(Object[] obj) {
        int vmm_id = 0;
        Base.open();
        try {
            DomainFeaturesMapping dfmIdData = DomainFeaturesMapping.findFirst("domain_id = ? AND feature_id = ?", (int) obj[0], (int) obj[1]);
            if (dfmIdData != null) {
                vmm_id = dfmIdData.getDFMId();
            }

            return vmm_id;
        } catch (Exception e) {
            System.out.println("Error on Fetching Vehicle & Model Id" + e.getMessage());
            return vmm_id;

        } finally {
            Base.close();
        }
    }

    public static Object[] getFeaturesChartCount() {
        int featuresCount = 0, touchedCount = 0;
        Base.open();
        try {
            /*String fetch_featuresCount = "SELECT COUNT(*) FROM domain_and_features_mapping";
            String fetch_touchedCount = "SELECT COUNT(DISTINCT domain_and_features_mapping_id) FROM acbversion_group";*/
            featuresCount = Math.toIntExact(DomainFeaturesMapping.count());
            touchedCount = ACBVersionGroup.findAll().collectDistinct("domain_and_features_mapping_id").size();
            System.out.println("NOOOO "+featuresCount+"   YESS "+touchedCount);
            return new Object[]{featuresCount, touchedCount};
        } catch (Exception e) {
            System.out.println("Error on Fetching Features Touched Count" + e.getMessage());
            return new Object[]{featuresCount, touchedCount};

        } finally {
            Base.close();
        }
    }

    public static float getPDBVersionNameFromId(int id) {
        Base.open();
        try {
            //String fetch_pdbversionname = "SELECT pdb_versionname FROM pdbversion WHERE id = " + id;
            PDBVersion pdbv = PDBVersion.findById(id);
            if (pdbv != null) {
                return pdbv.getPDBVersionname();
            }
        } catch (Exception e) {
            System.out.println("Error on Fetching PDB Version Name " + e.getMessage());
        } finally {
            Base.close();
        }
        return 0;
    }

    public static int getIdFromPDBVersionName(float versionName) {
        Base.open();
        try {
            //String fetch_pdbversionname = "SELECT id FROM pdbversion WHERE pdb_versionname = " + versionName;
            PDBVersion pdbv = PDBVersion.findFirst("pdb_versionname = ?", versionName);
            if (pdbv != null) {
                return pdbv.getPDBId();
            }
        } catch (Exception e) {
            System.out.println("Error on Fetching PDB Version Name Id" + e.getMessage());
        } finally {
            Base.close();
        }
        return 0;
    }

    public static int getIdFromPDBVersionGroup(int pdbversion_id, int vmm_id, int dfm_id) {
        Base.open();
        try {
            String fetch_pdbversiongroup_id = "SELECT id FROM pdbversion_group WHERE pdbversion_id = ? AND vehicle_and_model_mapping_id = ? AND domain_and_features_mapping_id = ?";
            PDBVersionGroup pdbvg = PDBVersionGroup.findFirst(fetch_pdbversiongroup_id, pdbversion_id, vmm_id, dfm_id);
            if (pdbvg != null) {
                return pdbvg.getPDBVersionGroupId();
            }
        } catch (Exception e) {
            System.out.println("Error on Fetching PDB Version Group Id" + e.getMessage());
        } finally {
            Base.close();
        }
        return 0;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.acb_owner;

import static com.model.ivn_engineer.IVNEngineerDB.perm_status;
import static com.model.ivn_engineer.IVNEngineerDB.temp_status;

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

import com.controller.common.CookieRead;
import com.db_connection.ConnectionConfiguration;
import com.model.common.GlobalDataStore;
import com.model.ivn_supervisor.VehicleversionDB;
import com.model.pdb_owner.PDBversion;
import com.model.pojo.acb_version.ACBVersion;
import com.model.pojo.acb_version.ACBVersionGroup;
import com.model.pojo.acb_version.InputSignal;
import com.model.pojo.acb_version.OutputSignal;
import com.model.pojo.ivn_version.CanModels;
import com.model.pojo.ivn_version.HardwareModels;
import com.model.pojo.ivn_version.IVNVersion;
import com.model.pojo.ivn_version.LinModels;
import com.model.pojo.pdb_version.Domain;
import com.model.pojo.pdb_version.Features;
import com.model.pojo.pdb_version.PDBVersion;
import com.model.pojo.pdb_version.PDBVersionGroup;
import com.model.pojo.vehicle_modal.VehicleModel;
import com.model.pojo.vehicle_modal.VehicleModelMapping;
import com.model.pojo.vehicle_modal.VehicleVersion;

/**
 *
 * @author ets-2
 */
public class ACBOwnerDB {
//    public static ArrayList ip_signals=new ArrayList();
//     public static ArrayList op_signals=new ip_signalsArrayList();

	public static List<Map<String, Object>> LoadACBVersion(String filter) throws SQLException {
		System.out.println("LoadACBVersion");
		Base.open();
		List<Map<String, Object>> row = new ArrayList<>();
		try {
			String sql;
			if (filter.equals("active")) {
				sql = "SELECT a.id,a.acb_versionname,a.status FROM acbversion a WHERE a.flag=1 AND a.status=1 AND a.subversion_of IS NULL";
			} else {
				sql = "SELECT a.id,a.acb_versionname,a.status FROM acbversion a WHERE a.subversion_of IS NULL";
			}
			row = ACBVersion.findBySQL(sql).toMaps();
			System.out.println("row_data" + row);
		} catch (Exception e) {
			System.out.println("acb version error message" + e.getMessage());
			e.printStackTrace();
		} finally {
			Base.close();
		}
		return row;
	}

	public static Map<String, Object> LoadPDBDataForACBVersion(PDBVersion pdbver) throws SQLException {
		System.out.println("LoadPDBPreviousVehicleversionData");
		Base.open();
		Map<String, Object> columns3 = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> result_row = new ArrayList<Map<String, Object>>();
			String vehciledetail_sql = "SELECT vv.id as vehver_id,v.id as vehicle_id,vm.modelname as modelname,CAST(vmm.id as CHAR(100)) as vmm_id "
					+ "FROM pdbversion_group AS pg "
					+ "INNER JOIN vehicle_and_model_mapping AS vmm ON vmm.id = pg.vehicle_and_model_mapping_id \n"
					+ "INNER JOIN vehicleversion as vv on vv.id=vmm.vehicleversion_id \n"
					+ "INNER JOIN vehicle as v on v.id=vmm.vehicle_id \n"
					+ "INNER JOIN vehiclemodel as vm on vm.id=vmm.model_id \n" + "where pg.pdbversion_id="
					+ pdbver.getId() + " group by modelname,vmm_id order by vmm_id";
			System.out.println(vehciledetail_sql);
			List<Map> row = new ArrayList<>();
			row = Base.findAll(vehciledetail_sql);

			String featuredetail_sql = "SELECT GROUP_CONCAT(DISTINCT(pg.id)) as pdbgroup_id, GROUP_CONCAT(DISTINCT(CAST(pg.vehicle_and_model_mapping_id as CHAR(100)))) as vmm_id,\n"
					+ "CAST(pg.domain_and_features_mapping_id as CHAR(100)) as fid,\n"
					+ "GROUP_CONCAT(pg.available_status) as status,\n" + "d.domain_name as domainname,\n"
					+ "f.feature_name as featurename FROM pdbversion_group AS pg \n"
					+ "right JOIN vehicle_and_model_mapping AS vmm ON vmm.id = pg.vehicle_and_model_mapping_id \n"
					+ "INNER JOIN domain_and_features_mapping AS dfm ON dfm.id = pg.domain_and_features_mapping_id \n"
					+ "INNER JOIN domain as d on d.id=dfm.domain_id \n"
					+ "INNER JOIN features as f on f.id=dfm.feature_id \n" + "where pg.pdbversion_id=" + pdbver.getId()
					+ " group by fid";

			System.out.println(featuredetail_sql);
			List<Map> row1 = new ArrayList<>();
			row1 = Base.findAll(featuredetail_sql);

			// String pdb_status_sql = "select p.status from pdbversion p where p.id=" +
			// pdbver.getId();
			boolean pdbStatus = PDBVersion.findById(pdbver.getPDBId()).getBoolean("status");
			List<Map<String, Object>> row2 = new ArrayList<Map<String, Object>>();
			Map<String, Object> columns2 = new HashMap<String, Object>();
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

	public static Map<String, Object> LoadIVNDataForACBVersion(IVNVersion ivnver) throws SQLException {
		System.out.println("LoadIVNPreviousVehicleversionData");
		Base.open();
		Map<String, Object> columns_res = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> result_row = new ArrayList<Map<String, Object>>();
			// String canmodel_sql = "SELECT CAST(cn.network_can_id as CHAR(100)) as
			// network_id,\n" +
			// "CAST(cn.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n" +
			// "cn.available_status as status \n" +
			// "FROM ivn_canmodels AS cn \n" +
			// "where cn.ivnversion_id="+ivnver.getId();
			String canmodel_sql = "SELECT CAST(cn.network_can_id as CHAR(100)) as id,\n"
					+ "c.network_name as listitem,\n"
					+ "CAST(cn.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n"
					+ "cn.available_status as status \n"
					+ "FROM ivn_canmodels AS cn INNER JOIN network as c ON c.id=cn.network_can_id \n"
					+ "where cn.ivnversion_id=" + ivnver.getIVNId();
			System.out.println(canmodel_sql);
			List<Map> canModelList = Base.findAll(canmodel_sql);
			// System.out.println("Test List : "+canModelList.get(0).get("listitem"));
			List<Map<String, Object>> row1 = new ArrayList<Map<String, Object>>();
			for (Map canModel : canModelList) {
				Map<String, Object> columns1 = new HashMap<String, Object>();
				columns1.put("id", canModel.get("id"));
				columns1.put("listitem", canModel.get("listitem"));
				columns1.put("vmm_id", canModel.get("vmm_id"));
				columns1.put("status", canModel.get("status"));
				columns1.put("ntype", "can");
				row1.add(columns1);
			}

			// String linmodel_sql = "SELECT CAST(ln.network_lin_id as CHAR(100)) as
			// network_id,\n" +
			// "CAST(ln.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n" +
			// "ln.available_status as status \n" +
			// "FROM ivn_linmodels AS ln \n" +
			// "where ln.ivnversion_id="+ivnver.getId();
			String linmodel_sql = "SELECT CAST(ln.network_lin_id as CHAR(100)) as id,\n"
					+ "l.network_name as listitem,\n"
					+ "CAST(ln.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n"
					+ "ln.available_status as status \n"
					+ "FROM ivn_linmodels AS ln INNER JOIN network as l ON l.id=ln.network_lin_id \n"
					+ "where ln.ivnversion_id=" + ivnver.getIVNId();
			System.out.println(linmodel_sql);
			List<Map> linModelList = Base.findAll(linmodel_sql);
			List<Map<String, Object>> row2 = new ArrayList<Map<String, Object>>();
			for (Map linmodel : linModelList) {
				Map<String, Object> columns2 = new HashMap<String, Object>();
				columns2.put("id", linmodel.get("id"));
				columns2.put("listitem", linmodel.get("listitem"));
				columns2.put("vmm_id", linmodel.get("vmm_id"));
				columns2.put("status", linmodel.get("status"));
				columns2.put("ntype", "lin");
				row2.add(columns2);
			}

			// String hwmodel_sql = "SELECT CAST(hw.network_hardware_id as CHAR(100)) as
			// network_id,\n" +
			// "CAST(hw.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n" +
			// "hw.available_status as status \n" +
			// "FROM ivn_hardwaremodels AS hw \n" +
			// "where hw.ivnversion_id="+ivnver.getId();
			String hwmodel_sql = "SELECT CAST(hw.network_hardware_id as CHAR(100)) as id,\n"
					+ "h.network_name as listitem,\n"
					+ "CAST(hw.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n"
					+ "hw.available_status as status \n"
					+ "FROM ivn_hardwaremodels AS hw INNER JOIN network as h ON h.id=hw.network_hardware_id \n"
					+ "where hw.ivnversion_id=" + ivnver.getIVNId();
			System.out.println(hwmodel_sql);
			List<Map> hwModelList = Base.findAll(hwmodel_sql);
			List<Map<String, Object>> row3 = new ArrayList<Map<String, Object>>();
			for (Map hwModel : hwModelList) {
				Map<String, Object> columns3 = new HashMap<String, Object>();
				columns3.put("id", hwModel.get("id"));
				columns3.put("listitem", hwModel.get("listitem"));
				columns3.put("vmm_id", hwModel.get("vmm_id"));
				columns3.put("status", hwModel.get("status"));
				columns3.put("ntype", "hardware");
				row3.add(columns3);
			}

			String ivnsignalgroup_sql = "select CAST(s.id as CHAR(100)) as sid,s.signal_name as listitem,s.signal_description as description from ivnversion_group as ig inner join signals as s "
					+ "on FIND_IN_SET(s.id,ig.signal_group) > 0 where ig.ivnversion_id=" + ivnver.getIVNId();
			System.out.println(ivnsignalgroup_sql);
			List<Map> ivnSignalGroupList = Base.findAll(ivnsignalgroup_sql);
			List<Map<String, Object>> row_sig = new ArrayList<Map<String, Object>>();
			for (Map ivnSignalGroup : ivnSignalGroupList) {
				Map<String, Object> columns_sig = new HashMap<String, Object>();
				columns_sig.put("sid", ivnSignalGroup.get("sid"));
				columns_sig.put("listitem", ivnSignalGroup.get("listitem"));
				columns_sig.put("description", ivnSignalGroup.get("description"));
				columns_sig.put("network_type", "signal");
				row_sig.add(columns_sig);
			}

			String ivnecugroup_sql = "select CAST(e.id as CHAR(100)) as eid,e.ecu_name as listitem,e.ecu_description as description from ivnversion_group as ig inner join engine_control_unit as e "
					+ "on FIND_IN_SET(e.id,ig.ecu_group) > 0 where ig.ivnversion_id=" + ivnver.getIVNId();
			System.out.println(ivnecugroup_sql);
			List<Map> ecuGroupList = Base.findAll(ivnecugroup_sql);
			List<Map<String, Object>> row_ecu = new ArrayList<Map<String, Object>>();
			for (Map ecuGroup : ecuGroupList) {
				Map<String, Object> columns_ecu = new HashMap<String, Object>();
				columns_ecu.put("eid", ecuGroup.get("eid"));
				columns_ecu.put("listitem", ecuGroup.get("listitem"));
				columns_ecu.put("description", ecuGroup.get("description"));
				columns_ecu.put("network_type", "ecu");
				row_ecu.add(columns_ecu);
			}

			String v_sql = "SELECT \n" + "vmm.vehicleversion_id,vmm.vehicle_id \n" + "FROM ivn_canmodels AS cn \n"
					+ "INNER JOIN vehicle_and_model_mapping AS vmm ON vmm.id = cn.vehicle_and_model_mapping_id \n"
					+ "where cn.ivnversion_id=" + ivnver.getIVNId() + " limit 1";

			System.out.println("vehciledetail_sql" + v_sql);
			List<Map> vehicleDetail = Base.findAll(v_sql);
			String vehciledetail_sql = null;
			if (!vehicleDetail.isEmpty()) {
				vehciledetail_sql = "SELECT \n" + "vv.id as vehver_id,\n" + "v.id as vehicle_id,\n"
						+ "vm.modelname as modelname,\n" + "CAST(vmm.id as CHAR(100)) as vmm_id \n"
						+ "from vehicle_and_model_mapping as vmm \n"
						+ "INNER JOIN vehicleversion as vv on vv.id=vmm.vehicleversion_id \n"
						+ "INNER JOIN vehicle as v on v.id=vmm.vehicle_id \n"
						+ "INNER JOIN vehiclemodel as vm on vm.id=vmm.model_id\n" + "where vmm.vehicleversion_id="
						+ vehicleDetail.get(0).get("vehicleversion_id") + " AND vmm.vehicle_id="
						+ vehicleDetail.get(0).get("vehicle_id");
			}
			System.out.println("vehciledetail_sql1" + vehciledetail_sql);
			List<Map> vehicleList = Base.findAll(vehciledetail_sql);
			List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
			for (Map vehicle : vehicleList) {
				Map<String, Object> columns = new HashMap<String, Object>();
				columns.put("vehver_id", vehicle.get("vehver_id"));
				columns.put("vehicle_id", vehicle.get("vehicle_id"));
				columns.put("modelname", vehicle.get("modelname"));
				columns.put("vmm_id", vehicle.get("vmm_id"));
				row.add(columns);
			}

			// String ivn_status_sql = "select i.status from ivnversion i where
			// i.id="+ivnver.getId();
			// ResultSet resultSet5 = statement.executeQuery(ivn_status_sql);
			// ResultSetMetaData metaData5 = resultSet5.getMetaData();
			// int colCount5 = metaData5.getColumnCount();
			// List<Map<String, Object>> row5 = new ArrayList<Map<String, Object>>();
			// while (resultSet5.next()) {
			// Map<String, Object> columns5 = new HashMap<String, Object>();
			// for (int i = 1; i <= colCount5; i++) {
			// columns5.put(metaData5.getColumnLabel(i), resultSet5.getObject(i));
			// }
			// row5.add(columns5);
			// }
			columns_res.put("vehicledetail_list", row);
			columns_res.put("can", row1);
			columns_res.put("lin", row2);
			columns_res.put("hardware", row3);
			// columns_res.put("ivnversion_status",row5);
			columns_res.put("signal", row_sig);
			columns_res.put("ecu", row_ecu);
			// System.out.println("columns"+columns_res);
		} catch (Exception e) {
			System.out.println("acb version error message" + e.getMessage());
			e.printStackTrace();
		} finally {
			Base.close();
		}
		return columns_res;
	}

	public static Map<String, Object> LoadPDBandIVN_Version(VehicleModelMapping vmm) throws SQLException {
		System.out.println("LoadACBVersion");
		Base.open();
		Map<String, Object> columns = new HashMap<String, Object>();
		try {
			/*
			 * String sql =
			 * "select id from vehicle_and_model_mapping as vmm where vmm.vehicleversion_id="
			 * + vmm.getVehicleversion_id() + " and vmm.vehicle_id=" + vmm.getVehicle_id();
			 */
			LazyList<VehicleModelMapping> vmmId = VehicleModelMapping.where("vehicleversion_id= ? AND vehicle_id= ?",
					vmm.getVehicleversion_id(), vmm.getVehicle_id());
			ArrayList<Integer> vmm_id = new ArrayList<Integer>();
			for (VehicleModelMapping vmm1 : vmmId) {
				vmm_id.add(vmm1.getVMMId());
			}
			String join_vmm_id = StringUtils.join(vmm_id, ",");
			System.out.println("join_vmm_id" + join_vmm_id);
			String pdb_sql = "SELECT pdb.id,CAST(pdb.pdb_versionname as CHAR(100)) as pdb_versionname FROM pdbversion_group as pg INNER JOIN pdbversion as pdb ON pdb.id=pg.pdbversion_id where pg.vehicle_and_model_mapping_id IN ("
					+ join_vmm_id + ") AND pdb.status=1 group by pg.pdbversion_id";
			System.out.println("pdb_sql" + pdb_sql);

			List<Map> pdb_row = Base.findAll(pdb_sql);

			String ivn_sql = "select ivn.id,CAST(ivn.ivn_versionname as CHAR(100)) as ivn_versionname from ivn_canmodels as cn INNER JOIN ivnversion as ivn ON ivn.id=cn.ivnversion_id where cn.vehicle_and_model_mapping_id IN ("
					+ join_vmm_id + ") AND ivn.status=1 group by cn.ivnversion_id";
			System.out.println("ivn_sql" + ivn_sql);
			List<Map> ivn_row = Base.findAll(ivn_sql);

			columns.put("pdbversion_list", pdb_row);
			columns.put("ivnversion_list", ivn_row);
			System.out.println("columns" + columns);
		} catch (Exception e) {
			System.out.println("acb version error message" + e.getMessage());
			e.printStackTrace();

		} finally {
			Base.close();
		}
		return columns;
	}

	public static List<Map<String, Object>> LoadACBPreviousVehicleversionStatus(ACBVersion acb) throws SQLException {
		System.out.println("LoadACBPreviousVehicleversionStatus");
//        String status = null;
		Base.open();
		List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
		try {
			// String sql = "select v.id,v.versionname,v.status from vehicleversion v where
			// v.status=1";
			String sql = "SELECT acb.status,acb.flag FROM acbversion acb WHERE acb.id= ?";
			row = ACBVersion.findBySQL(sql, acb.getACBId()).toMaps();
		} catch (Exception e) {
			System.out.println("acb version error message" + e.getMessage());
			e.printStackTrace();
		} finally {
			Base.close();
		}
		return row;
	}

	public static Object[] insertACBVersion(ACBVersion acb) {
		Base.open();
		float versionname = 0.0f;
		String subversion_of = null;
		float oldversionname = 0.0f;
		try {
			if (acb.getOperation_status().equals("create")) {
				String sql = "SELECT id, acb_versionname FROM acbversion where subversion_of IS NULL ORDER BY acb_versionname DESC LIMIT 1";
				LazyList<ACBVersion> acbVersion = ACBVersion.findBySQL(sql);
				if (acbVersion.isEmpty()) {
					versionname = (float) 1.0;
				} else {
					float acbversionname = acbVersion.get(0).getVersionname();
					if (acb.getSubversion_status() == "yes") {
						if (acb.getIs_acbsubversion() == true) {
							String acbsql = "SELECT acb2.id, acb2.acb_versionname,acb2.subversion_of FROM acbversion acb1 INNER JOIN acbversion acb2 ON acb2.subversion_of=acb1.subversion_of where acb1.id="
									+ acb.getACBId();
							List<Map> acbDetails = Base.findAll(acbsql);
							versionname = (float) 0.1 + (float) acbDetails.get(0).get("acb_versionname");
							oldversionname = (float) acbDetails.get(0).get("acb_versionname");
							String s = new Float(oldversionname).toString();
							String p = s.substring(s.indexOf('.') + 1, s.length());
							int f_value = Integer.parseInt(p);
							if (f_value != 9) {
								subversion_of = String.valueOf(acbDetails.get(0).get("subversion_of"));
							}
						} else {
							String subsql = "SELECT id, acb_versionname FROM acbversion where subversion_of="
									+ acb.getACBId() + " ORDER BY acb_versionname DESC LIMIT 1";
							LazyList<ACBVersion> acbDetails = ACBVersion.findBySQL(subsql);
							// System.out.println("subversion_rows" + rs_sub.getRow());
							if (acbDetails.isEmpty()) {
								// String acbsql = "SELECT id, acb_versionname FROM acbversion where id=" +
								// acb.getACBId();
								ACBVersion acbv = ACBVersion.findById(acb.getACBId());
								versionname = (float) 0.1 + acbv.getVersionname();
								oldversionname = acbv.getVersionname();
							} else {
								versionname = (float) 0.1 + acbDetails.get(0).getVersionname();
								oldversionname = acbDetails.get(0).getVersionname();
							}
							String s = new Float(oldversionname).toString();
							String p = s.substring(s.indexOf('.') + 1, s.length());
							int f_value = Integer.parseInt(p);
							if (f_value != 9) {
								subversion_of = String.valueOf(acb.getId());
							}
						}
					} else {
						versionname = (float) 1.0 + acbversionname;
					}
				}
				/*
				 * String d_sql =
				 * "SELECT acb_versionname FROM acbversion where acb_versionname=" +
				 * versionname; System.out.println("sql_query" + d_sql); ResultSet d_resultSet =
				 * statement.executeQuery(d_sql); d_resultSet.last(); if (d_resultSet.getRow() >
				 * 0) { String d1_sql =
				 * "SELECT id, acb_versionname FROM acbversion where subversion_of IS NULL ORDER BY acb_versionname DESC LIMIT 1"
				 * ; ResultSet d1_resultSet = statement.executeQuery(sql); d1_resultSet.last();
				 * versionname = (float) 1.0 + d1_resultSet.getFloat("acb_versionname"); }
				 */
				ACBVersion acbIns = new ACBVersion();
				acbIns.set("acb_versionname", versionname);
				acbIns.set("created_date", acb.getCreated_date());
				acbIns.set("created_or_updated_by", acb.getCreated_or_updated_by());
				acbIns.set("status", acb.getStatus());
				acbIns.set("flag", acb.getFlag());
				acbIns.set("subversion_of", subversion_of);
				acbIns.set("features_fully_touchedstatus", acb.getFully_touchedstatus());

				if (acbIns.saveIt()) {
					int last_inserted_id = (int) acbIns.getId();
					return new Object[] { last_inserted_id, versionname };
				}
			} else {
				// String versionName = "SELECT acb_versionname FROM acbversion WHERE id =" +
				// acb.getACBId();
				ACBVersion acbvn = ACBVersion.findById(acb.getACBId());
				if (acbvn != null) {
					versionname = acbvn.getVersionname();
				}
				System.out.println(
						"object_value_in_update" + acb.getACBId() + acb.getStatus() + acb.getCreated_or_updated_by());
				/*
				 * String sql = "UPDATE acbversion SET " +
				 * "status = ?, created_or_updated_by = ?, flag=?, features_fully_touchedstatus=?  WHERE id = ?"
				 * ;
				 */

				acbvn.set("created_or_updated_by", acb.getCreated_or_updated_by());
				acbvn.set("status", acb.getStatus());
				acbvn.set("flag", acb.getFlag());
				acbvn.set("features_fully_touchedstatus", acb.getFully_touchedstatus());
				acbvn.saveIt();

				return new Object[] { acb.getACBId(), versionname };
			}
		} catch (Exception e) {
			System.out.println("acb version error message" + e.getMessage());
			e.printStackTrace();
			return new Object[] { 0, versionname };

		} finally {
			Base.close();
		}
		return new Object[] { 0, versionname };
	}

	public static int insertACBSignal(ACBInput_and_Ouput_Signal as) {
		Base.open();
		int last_inserted_id = 0;
		try {
//            System.out.println("globaldatastore"+StringUtils.join(GlobalDataStore.globalData, ','));
			if (as.getSignal_type().equals("input")) {
				/*
				 * String ips_sql = "select * from acb_inputsignal as ips where" +
				 * " ips.input_signal_id=" + as.getSignal_id() + " AND ips.input_network_id=" +
				 * as.getNetwork_id() + " AND ips.network_type='" + as.getNetwork_type() + "'" +
				 * " AND ips.pdbversion_group_id=" + as.getPdbversion_group_id();
				 * System.out.println("ips_sql" + ips_sql);
				 */
				LazyList<InputSignal> ipsId = InputSignal.where(
						"input_signal_id = ? AND input_network_id = ? AND network_type = ? AND pdbversion_group_id = ?",
						as.getSignal_id(), as.getNetwork_id(), as.getNetwork_type(), as.getPdbversion_group_id());
				if (!ipsId.isEmpty()) {
//                    GlobalDataStore.globalData.add(resultSet.getInt("id")); 
//                    ip_signals.add(resultSet.getInt(1));
					last_inserted_id = ipsId.get(0).getaISid();
				} else {
					InputSignal ips = new InputSignal(as.getSignal_id(), as.getNetwork_id(), as.getNetwork_type(),
							as.getPdbversion_group_id());
					ips.saveIt();
					last_inserted_id = (int) ips.getId();
				}
			} else {
				/*
				 * String ips_sql = "select * from acb_outputsignal as ips where " +
				 * "ips.output_signal_id=" + as.getSignal_id() + " AND ips.output_network_id=" +
				 * as.getNetwork_id() + " AND ips.network_type='" + as.getNetwork_type() + "'" +
				 * " AND ips.pdbversion_group_id=" + as.getPdbversion_group_id();
				 */
				LazyList<OutputSignal> optId = OutputSignal.where(
						"output_signal_id = ? AND output_network_id = ? AND network_type = ? AND pdbversion_group_id = ?",
						as.getSignal_id(), as.getNetwork_id(), as.getNetwork_type(), as.getPdbversion_group_id());
				if (!optId.isEmpty()) {
//                    GlobalDataStore.globalData.add(resultSet.getInt("id")); 
//                    op_signals.add(resultSet.getInt(1));
					last_inserted_id = optId.get(0).getaOSid();
				} else {
					OutputSignal ops = new OutputSignal(as.getSignal_id(), as.getNetwork_id(), as.getNetwork_type(),
							as.getPdbversion_group_id());
					ops.saveIt();
					last_inserted_id = (int) ops.getId();
				}
			}
			return last_inserted_id;
		} catch (Exception e) {
			System.out.println("ACB signal error message" + e.getMessage());
			e.printStackTrace();
			return 0;

		} finally {
			Base.close();
		}
//        return 0;
	}

	public static int insertACBVersionGroup(ACBVersionGroup ag) {
		Base.open();
		int resultSet_count = 0;
		try {
			if (ag.getOperation_status().equals("update")) {
				System.out.println("update_if");
				/*
				 * String sql = "select * from acbversion_group as ag where " +
				 * "ag.acbversion_id=" + ag.getACBversion_id() +
				 * " AND ag.domain_and_features_mapping_id=" +
				 * ag.getDomain_and_features_mapping_id(); System.out.println("sql_query" +
				 * sql);
				 */
				LazyList<ACBVersionGroup> avgList = ACBVersionGroup.where(
						"acbversion_id = ? AND domain_and_features_mapping_id = ?", ag.getACBversion_id(),
						ag.getDomain_and_features_mapping_id());
				for (ACBVersionGroup avg : avgList) {
					System.out.println("while");
					/*
					 * String update_sql = "UPDATE acbversion_group SET " +
					 * "ivnversion_id = ?, pdbversion_id = ?, vehicleversion_id = ?, vehicle_id = ?, ecu_id = ?, inputsignal_group = ?,"
					 * + "outputsignal_group = ? ,touchedstatus = ?  WHERE id = ?";
					 */

					ACBVersionGroup acbvgUp = ACBVersionGroup.findById(avg.getACBVGId());
					acbvgUp.set("ivnversion_id", ag.getIVNversion_id());
					acbvgUp.set("pdbversion_id", ag.getPDBversion_id());
					acbvgUp.set("vehicleversion_id", ag.getVehicleversion_id());
					acbvgUp.set("vehicle_id", ag.getVehicle_id());
					acbvgUp.set("ecu_id", ag.getEcu_id());
					acbvgUp.set("inputsignal_group", ag.getInputsignal_group());
					acbvgUp.set("outputsignal_group", ag.getOutputsignal_group());
					acbvgUp.set("touchedstatus", ag.getTouchedstatus());
					acbvgUp.saveIt();

					GlobalDataStore.globalData.add(avg.getACBVGId());
				}
				if (!avgList.isEmpty()) {
					resultSet_count = avgList.size();
				}
			}
			if (resultSet_count == 0) {
				System.out.println("object_value_in_insert" + ag.getACBversion_id() + ag.getIVNversion_id()
						+ ag.getPDBversion_id() + ag.getVehicleversion_id() + ag.getVehicle_id() + ag.getEcu_id()
						+ ag.getInputsignal_group() + ag.getOutputsignal_group() + ag.getTouchedstatus());

				if (ag.getButton_type().equals("other")) {
					if (ag.saveIt()) {
						int last_inserted_id = (int) ag.getId();
						return last_inserted_id;
					}
				} // Avoid this condition for storing acb data from system owner
				else {
					if (ag.saveIt()) {
						GlobalDataStore.globalData.add((Integer) ag.getId());
					}
				}
			}
			System.out.println("globalData" + GlobalDataStore.globalData);
			if (ag.getButton_type().equals("save")) {
				return temp_status;
			} else if (ag.getButton_type().equals("submit")) {
				return perm_status;
			}
		} catch (Exception e) {
			System.out.println("acb version group error message" + e.getMessage());
			e.printStackTrace();
			return 0;

		} finally {
			Base.close();
		}
		return 0;
	}

	public static Map<String, Object> LoadACBPreviousVehicleversionData(ACBVersion acbver) throws SQLException {
		System.out.println("LoadACBPreviousVehicleversionData");
		int pdbversion_id = 0;
		int ivnversion_id = 0;
		int vehicleversion_id = 0;
		int vehicle_id = 0;
		Base.open();
		Map<String, Object> columns_res = new HashMap<String, Object>();
		try {
			// List<Map<String, Object>> result_row = new ArrayList<Map<String, Object>>();
			String acbversion_sql = "select CAST(acb.acbversion_id as CHAR(100)) as acbversion,CAST(acb.vehicleversion_id as CHAR(100)) as vehicleversion,CAST(acb.vehicle_id as CHAR(100)) as vehiclename,"
					+ "CAST(acb.pdbversion_id as CHAR(100)) as pdbversion,CAST(acb.ivnversion_id as CHAR(100)) as ivnversion "
					+ "from acbversion_group as acb where acb.acbversion_id=" + acbver.getACBId() + " LIMIT 1";
			System.out.println(acbversion_sql);
			List<Map> row_acb = new ArrayList();
			row_acb = Base.findAll(acbversion_sql);
			// columns1.put("network_type","can");
			pdbversion_id = (int) row_acb.get(0).get("pdbversion");
			System.out.println("pdbversion_id" + pdbversion_id);
			ivnversion_id = (int) row_acb.get(0).get("ivnversion");
			System.out.println("ivnversion_id" + ivnversion_id);
			vehicleversion_id = (int) row_acb.get(0).get("vehicleversion");
			System.out.println("vehicleversion_id" + vehicleversion_id);
			vehicle_id = (int) row_acb.get(0).get("vehiclename");
			System.out.println("vehicleversion_id" + vehicleversion_id);

			PDBVersion pdbver = new PDBVersion(pdbversion_id);
			Map<String, Object> pdb_map_result = LoadPDBDataForACBVersion(pdbver);
			System.out.println("pdb_map_result" + pdb_map_result);

			IVNVersion ivnver = new IVNVersion(ivnversion_id);
			Map<String, Object> ivn_map_result = LoadIVNDataForACBVersion(ivnver);
			System.out.println("ivn_map_result" + ivn_map_result);

			VehicleVersion vver = new VehicleVersion(vehicleversion_id);
			List<Map> vehmod_map_result = VehicleversionDB.LoadPreviousVehicleversionData(vver);

			VehicleModelMapping veh_mod_map = new VehicleModelMapping(vehicleversion_id, vehicle_id);
			Map<String, Object> pdb_ivn_result = LoadPDBandIVN_Version(veh_mod_map);

			String acbgroup_sql = "select CAST(acb.ecu_id as CHAR(100)) as ecu,acb.touchedstatus,ip.id,CAST(ip.pdbversion_group_id as CHAR(100)) as pdbgroupid,CAST(pdb.domain_and_features_mapping_id as CHAR(100)) as fid,ecu.ecu_name from acbversion_group as acb "
					+ "inner join acb_inputsignal as ip on FIND_IN_SET(ip.id,acb.inputsignal_group) > 0 INNER JOIN pdbversion_group as pdb ON pdb.id = ip.pdbversion_group_id \n"
					+ "inner join engine_control_unit as ecu ON ecu.id=acb.ecu_id where acb.acbversion_id="
					+ acbver.getACBId();
			System.out.println(acbgroup_sql);
			List<Map> row_acbgp = new ArrayList<>();
			row_acbgp = Base.findAll(acbgroup_sql);

			String acbip_sql = "SELECT a.*,CAST(ip.input_signal_id as CHAR(100)) as input_signal_id,CAST(ip.pdbversion_group_id as CHAR(100)) as pdbversion_group_id,CAST(ip.input_network_id as CHAR(100)) as input_network_id,CAST(ip.network_type as CHAR(100)) as network_type,CAST(pdb.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,CAST(pdb.domain_and_features_mapping_id as CHAR(100)) as fid FROM acb_inputsignal AS ip "
					+ "INNER JOIN ( SELECT SUBSTRING_INDEX( SUBSTRING_INDEX( acb.inputsignal_group, ',', n.n ) , ',', -1 ) value,acb.ecu_id as ecu FROM acbversion_group as acb "
					+ "CROSS JOIN numbers n WHERE n.n <=1 + ( LENGTH( acb.inputsignal_group ) - LENGTH( REPLACE( acb.inputsignal_group, ',', ''))) AND acb.acbversion_id="
					+ acbver.getACBId() + ") AS a "
					+ "ON a.value = ip.id INNER JOIN pdbversion_group as pdb ON pdb.id = ip.pdbversion_group_id";
			System.out.println(acbip_sql);
			List<Map> row_acbip = new ArrayList<>();
			row_acbip = Base.findAll(acbip_sql);
			
			String acbop_sql = "SELECT a.*,CAST(op.output_signal_id as CHAR(100)) as output_signal_id,CAST(op.pdbversion_group_id as CHAR(100)) as pdbversion_group_id,CAST(op.output_network_id as CHAR(100)) as output_network_id,CAST(op.network_type as CHAR(100)) as network_type,pdb.vehicle_and_model_mapping_id as vmm_id,pdb.domain_and_features_mapping_id as fid FROM acb_outputsignal AS op "
					+ "INNER JOIN ( SELECT SUBSTRING_INDEX( SUBSTRING_INDEX( acb.outputsignal_group, ',', n.n ) , ',', -1 ) value,acb.ecu_id as ecu FROM acbversion_group as acb "
					+ "CROSS JOIN numbers n WHERE n.n <=1 + ( LENGTH( acb.outputsignal_group ) - LENGTH( REPLACE( acb.outputsignal_group, ',', ''))) AND acb.acbversion_id="
					+ acbver.getACBId() + ") AS a "
					+ "ON a.value = op.id INNER JOIN pdbversion_group as pdb ON pdb.id = op.pdbversion_group_id";
			System.out.println(acbop_sql);			
			List<Map> row_acbop = new ArrayList<>();
			row_acbop = Base.findAll(acbop_sql);

			String acb_status_sql = "select a.status,CAST(a.subversion_of as CHAR(100)) as subversion_of from acbversion a where a.id="
					+ acbver.getACBId();
			List<Map<String, Object>> row_st = new ArrayList<Map<String, Object>>();
			row_st = ACBVersion.findBySQL(acbop_sql).toMaps();
			System.out.println("resultset_status_subversion_of" + row_st.get(0).get("subversion_of"));

			String acb_sub_sql = "select * from acbversion a where a.subversion_of=" + acbver.getACBId();
			List<Map<String, Object>> row_sub = new ArrayList<Map<String, Object>>();
			row_sub = ACBVersion.findBySQL(acb_sub_sql).toMaps();

			List<Map<String, Object>> row_sub1 = new ArrayList<Map<String, Object>>();
			if (row_st.get(0).get("subversion_of") != null && row_sub.size() == 0) {
				String acb_sub1_sql = "select * from acbversion a where a.subversion_of="
						+ row_st.get(0).get("subversion_of");
				ResultSet resultSet_sub1 = statement.executeQuery(acb_sub1_sql);
				ResultSetMetaData metaData_sub1 = resultSet_sub1.getMetaData();
				int colCount_sub1 = metaData_sub1.getColumnCount();
				while (resultSet_sub1.next()) {
					Map<String, Object> columns_sub1 = new HashMap<String, Object>();
					for (int i = 1; i <= colCount_sub1; i++) {
						columns_sub1.put(metaData_sub.getColumnLabel(i), resultSet_sub1.getObject(i));
					}
					row_sub1.add(columns_sub1);
				}
			}

			columns_res.put("acbversion", row_acb);
			columns_res.put("pdb_map_result", pdb_map_result);
			columns_res.put("ivn_map_result", ivn_map_result);
			columns_res.put("vehmod_map_result", vehmod_map_result);
			columns_res.put("pdb_ivn_result", pdb_ivn_result);
			columns_res.put("acbgroup", row_acbgp);
			columns_res.put("acb_inputsignal", row_acbip);
			columns_res.put("acb_outputsignal", row_acbop);
			columns_res.put("acbversion_status", row_st);
			if (row_sub.size() != 0) {
				columns_res.put("acbsubversion", row_sub);
			} else {
				columns_res.put("acbsubversion", row_sub1);
			}
		} catch (Exception e) {
			System.out.println("acb version error message" + e.getMessage());
			e.printStackTrace();

		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return columns_res;
	}

	public static List<Map<String, Object>> GetACBVersion_Listing() throws SQLException {
		System.out.println("GetACBVersion_Listing");
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
		try {
			connection = ConnectionConfiguration.getConnection();
			// Check whether model name already exists in db or not
			Statement statement = connection.createStatement();
			String sql = "SELECT acb.id,CAST(acb.acb_versionname as CHAR(100)) as acb_versionname,CAST(pdb.pdb_versionname as CHAR(100)) as pdb_versionname,"
					+ "CAST(ivn.ivn_versionname as CHAR(100)) as ivn_versionname,"
					+ "GROUP_CONCAT(CONCAT(f.feature_name,CONCAT(\" (\",domain_name,\")\"))) as touched_features,"
					+ "acb.status as status,acb.flag"
					+ " FROM acbversion_group as ag INNER JOIN domain_and_features_mapping as dfm ON dfm.id=ag.domain_and_features_mapping_id "
					+ "INNER JOIN domain as d ON d.id=dfm.domain_id INNER JOIN features as f ON f.id=dfm.feature_id "
					+ "INNER JOIN acbversion as acb ON acb.id=ag.acbversion_id "
					+ "INNER JOIN pdbversion as pdb ON pdb.id=ag.pdbversion_id "
					+ "INNER JOIN ivnversion as ivn ON ivn.id=ag.ivnversion_id "
					+ "group by ag.acbversion_id order by ag.acbversion_id desc";
			System.out.println("acbsql" + sql);
			ResultSet resultSet = statement.executeQuery(sql);
			ResultSetMetaData metaData = resultSet.getMetaData();
			int colCount = metaData.getColumnCount();
			while (resultSet.next()) {
				Map<String, Object> columns = new HashMap<String, Object>();
				for (int i = 1; i <= colCount; i++) {
					columns.put(metaData.getColumnLabel(i), resultSet.getObject(i));
				}
				row.add(columns);
			}
		} catch (Exception e) {
			System.out.println("acb version error message" + e.getMessage());
			e.printStackTrace();

		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return row;
	}

	public static Map<String, Object> GetACB_Dashboarddata() throws SQLException {
		System.out.println("GetACB_Dashboarddata");
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		connection = ConnectionConfiguration.getConnection();
		Statement statement = connection.createStatement();
		Map<String, Object> columns = new HashMap<String, Object>();

		// Get ACB version count
		String acbver_sql = "select * from acbversion";
		ResultSet acbver_rs = statement.executeQuery(acbver_sql);
		acbver_rs.last();
		System.out.println("resultset_count" + acbver_rs.getRow());
		columns.put("acbversion_count", acbver_rs.getRow());

		// Get IVN version count
		String ivnver_sql = "select * from ivnversion";
		ResultSet ivnver_rs = statement.executeQuery(ivnver_sql);
		ivnver_rs.last();
		System.out.println("resultset_count" + ivnver_rs.getRow());
		columns.put("ivnversion_count", ivnver_rs.getRow());

		// Get IVN version count
		String pdbver_sql = "select * from pdbversion";
		ResultSet pdbver_rs = statement.executeQuery(pdbver_sql);
		pdbver_rs.last();
		System.out.println("resultset_count" + pdbver_rs.getRow());
		columns.put("pdbversion_count", pdbver_rs.getRow());

		return columns;
	}

	public static void deleteACBVersion_Group(int acbversion_id, String action_type) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		System.out.println("deleteACBVersion_Group" + GlobalDataStore.globalData);
		System.out.println("action_type" + action_type);
		if (action_type.equals("update") && GlobalDataStore.globalData.size() != 0) {
			connection = ConnectionConfiguration.getConnection();
			preparedStatement = connection.prepareStatement("delete from acbversion_group where acbversion_id="
					+ acbversion_id + " AND id NOT IN (" + StringUtils.join(GlobalDataStore.globalData, ',') + ")");
			preparedStatement.executeUpdate();
		}
		GlobalDataStore.globalData.clear();
	}

	public static int getIdFromECU(String ecu) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionConfiguration.getConnection();
			Statement statement = connection.createStatement();

			String fetch_ecu_id = "SELECT id FROM engine_control_unit WHERE ecu_name = '" + ecu + "'";
			resultSet = statement.executeQuery(fetch_ecu_id);
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				preparedStatement = connection.prepareStatement(
						"INSERT INTO engine_control_unit (ecu_name,created_or_updated_by)" + "VALUES (?,?)",
						preparedStatement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, ecu);
				preparedStatement.setInt(2, CookieRead.getUserIdFromSession());
				preparedStatement.executeUpdate();
				ResultSet rs = preparedStatement.getGeneratedKeys();

				if (rs.next()) {
					int last_inserted_id = rs.getInt(1);
					return last_inserted_id;
				}
			}
		} catch (Exception e) {
			System.out.println("Error on Fetching ECU Id" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
//                    return 0;
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}

	public static int getIdFromACBVersionName(float versionName) {
		Connection connection = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionConfiguration.getConnection();
			Statement statement = connection.createStatement();

			String fetch_ecu_id = "SELECT id FROM acbversion WHERE acb_versionname = " + versionName;
			resultSet = statement.executeQuery(fetch_ecu_id);
			resultSet.last();
			if (resultSet.getRow() != 0) {
				return resultSet.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("Error on Fetching ECU Id" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}

	public static float getACBVersionNameFromId(int acbVersion) {
		Connection connection = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionConfiguration.getConnection();
			Statement statement = connection.createStatement();

			String fetch_acbversion_id = "SELECT acb_versionname FROM acbversion WHERE id = " + acbVersion;
			resultSet = statement.executeQuery(fetch_acbversion_id);
			resultSet.last();
			if (resultSet.getRow() != 0) {
				return resultSet.getFloat(1);
			}
		} catch (Exception e) {
			System.out.println("Error on Fetching ACB Version Name" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}
}

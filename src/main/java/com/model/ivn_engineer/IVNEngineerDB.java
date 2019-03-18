/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.ivn_engineer;

import com.controller.common.CookieRead;
import com.db_connection.ConnectionConfiguration;
import com.model.common.GlobalDataStore;
import com.model.pojo.acb_version.SignalTag;
import com.model.pojo.acb_version.SignalTagMapping;
import com.model.pojo.acb_version.Signals;
import com.model.pojo.ivn_version.CanModels;
import com.model.pojo.ivn_version.EngineControlUnit;
import com.model.pojo.ivn_version.HardwareModels;
import com.model.pojo.ivn_version.IVNVersion;
import com.model.pojo.ivn_version.IVNVersionGroup;
import com.model.pojo.ivn_version.LinModels;
import com.model.pojo.ivn_version.Network;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.json.simple.JSONArray;

/**
 *
 * @author ets-2
 */
public class IVNEngineerDB {

	public static int temp_status = 0;
	public static int perm_status = 1;

	public static List<Map<String, Object>> LoadIVNVersion(String filter) throws SQLException {
		System.out.println("LoadIVNVersion");
		Base.open();
		List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
		try {
			String sql;
			if (filter.equals("active")) {
				sql = "SELECT iv.id,iv.ivn_versionname,iv.status FROM ivnversion iv WHERE iv.flag=1 AND iv.status=1";
			} else {
				sql = "SELECT iv.id,iv.ivn_versionname,iv.status FROM ivnversion iv";
			}
			row = IVNVersion.findBySQL(sql).toMaps();
			System.out.println("row_data" + row);
		} catch (Exception e) {
			System.out.println("acb version error message" + e.getMessage());
			e.printStackTrace();
		} finally {
			Base.close();
		}
		return row;
	}

	public static Map<String, Object> LoadNetwork() throws SQLException {
		System.out.println("LoadNetwork");
		Base.open();
		Map<String, Object> columns2 = new HashMap<String, Object>();
		try {
			String can_sql = "SELECT CAST(c.id as CHAR(100)) as cid,c.network_name as listitem FROM network as c WHERE status=1 AND network_type='can'";
			System.out.println(can_sql);
			List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
			row = Network.findBySQL(can_sql).toMaps();

			String lin_sql = "SELECT CAST(l.id as CHAR(100)) as lid, l.network_name as listitem FROM network as l WHERE status=1 AND network_type='lin'";
			System.out.println(lin_sql);
			List<Map<String, Object>> row1 = new ArrayList<Map<String, Object>>();
			row1 = Network.findBySQL(lin_sql).toMaps();

			String hardware_sql = "SELECT CAST(hw.id as CHAR(100)) as hid, hw.network_name as listitem FROM network as hw WHERE status=1 AND network_type='hardware'";
			System.out.println(hardware_sql);
			List<Map<String, Object>> row2 = new ArrayList<Map<String, Object>>();
			row2 = Network.findBySQL(hardware_sql).toMaps();

			columns2.put("can_list", row);
			columns2.put("lin_list", row1);
			columns2.put("hardware_list", row2);
			System.out.println("columns" + columns2);
		} catch (Exception e) {
			System.out.println("acb version error message" + e.getMessage());
			e.printStackTrace();

		} finally {
			Base.close();
		}
		return columns2;
	}

	public static List<Map<String, Object>> LoadECU() throws SQLException {
		System.out.println("LoadECU");
		Base.open();
		List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
		try {
			// String sql = "select v.id,v.versionname,v.status from vehicleversion v where
			// v.status=1";
			String sql = "SELECT CAST(ecu.id as CHAR(100)) as eid,ecu.ecu_name as listitem,ecu.ecu_description as description FROM engine_control_unit as ecu WHERE ecu.status=1";

			row = EngineControlUnit.findBySQL(sql).toMaps();
			System.out.println("row_data" + row);
		} catch (Exception e) {
			System.out.println("acb version error message" + e.getMessage());
			e.printStackTrace();
		} finally {
			Base.close();
		}
		return row;
	}

	public static List<Map<String, Object>> LoadSignals() throws SQLException {
		System.out.println("LoadSignals");
		Base.open();
		List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
		try {
			// String sql = "select v.id,v.versionname,v.status from vehicleversion v where
			// v.status=1";
			String sql = "SELECT CAST(s.id as CHAR(100)) as sid,s.signal_name as listitem,s.signal_description as description FROM signals as s WHERE s.status=1";

			row = Signals.findBySQL(sql).toMaps();
			System.out.println("row_data" + row);
		} catch (Exception e) {
			System.out.println("acb version error message" + e.getMessage());
			e.printStackTrace();
		} finally {
			Base.close();
		}
		return row;
	}

	public static Map<String, Object> insertNetworkData(Network_Ecu n) {
		Base.open();
//        List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
		Map<String, Object> columns = new HashMap<String, Object>();
		int last_inserted_id = 0;
		try {
			if (n.getNetwork_type().equals("can") || n.getNetwork_type().equals("lin")
					|| n.getNetwork_type().equals("hardware")) {
				/*
				 * String sql = "SELECT id FROM network WHERE network_name ='" +
				 * n.getNetworkname().trim() + "' AND network_type='" + n.getNetwork_type() +
				 * "'";
				 */
				Network network = Network.findFirst("network_name = ? AND network_type = ?", n.getNetworkname().trim(),
						n.getNetwork_type());
				if (network != null) {
					last_inserted_id = network.getNetworkId();
//                   return last_inserted_id;
				} else {
					Network nI = new Network(n.getNetworkname(), n.getNetworkdescription(), n.getNetwork_type(),
							n.getCreated_date());
					nI.saveIt();
					last_inserted_id = (int) nI.getId();
				}
			} else if (n.getNetwork_type().equals("ecu")) {
				// String sql = "SELECT id FROM engine_control_unit WHERE ecu_name ='" +
				// n.getEcuname().trim() + "'";
				EngineControlUnit ecu = EngineControlUnit.findFirst("ecu_name = ?", n.getEcuname().trim());
				if (ecu != null) {
					last_inserted_id = ecu.getECUId();
//                   return last_inserted_id;
				} else {
					EngineControlUnit ecuI = new EngineControlUnit(n.getEcuname(), n.getEcudescription(),
							n.getCreated_date(), n.getCreated_or_updated_by());
					ecuI.saveIt();
					last_inserted_id = (int) ecuI.getId();
				}
			}

			if (n.getNetwork_type().equals("can")) {
				columns.put("cid", Integer.toString(last_inserted_id));
			} else if (n.getNetwork_type().equals("lin")) {
				columns.put("lid", Integer.toString(last_inserted_id));
			} else if (n.getNetwork_type().equals("hardware")) {
				columns.put("hid", Integer.toString(last_inserted_id));
			} else {
				columns.put("eid", Integer.toString(last_inserted_id));
				columns.put("description", n.getEcudescription());
				columns.put("listitem", n.getEcuname());
			}
			if (!n.getNetwork_type().equals("ecu")) {
				columns.put("listitem", n.getNetworkname());
			}
//                row.add(columns);

//                if(rs.next())
//                {
//                    int last_inserted_id = rs.getInt(1);
////                    return last_inserted_id;
//                }
		} catch (Exception e) {
			System.out.println("vehicle version error message" + e.getMessage());
			e.printStackTrace();
//            return 0;
		} finally {
			Base.close();
		}
		return columns;
	}

	public static int getNetworkDataCSV(String network_name, String network_type) {
		Base.open();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		try {
			/*
			 * String fetch_id_query = "SELECT id FROM network WHERE network_name ='" +
			 * network_name + "' AND network_type='" + network_type + "'";
			 */
			Network network = Network.findFirst("network_name = ? AND network_type = ?", network_name, network_type);
			if (network != null) {
				return network.getNetworkId();
			} else {
				Network nI = new Network(network_name, "", network_type, dtf.format(now));

				if (nI.saveIt()) {
					return (int) nI.getId();
				}
			}
		} catch (Exception e) {
			System.out.println("Network Data CSV error message" + e.getMessage());
			e.printStackTrace();
//            return 0;
		} finally {
			Base.close();
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> insertSignalData(Signals s) {
		Base.open();
		List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
		try {
			System.out.println("before Signal Insert");
			Map<String, Object> columns = new HashMap<String, Object>();
			if (s.saveIt()) {
				int last_inserted_id = (int) s.getId();
                //insert signal tags
                s.getSignal_tags().forEach(tagName -> {
                    //String tags = "SELECT id FROM signaltags WHERE tagname ='" + tagName.toString() + "'";
                	int tag_id = 0;
                	SignalTag st = SignalTag.findFirst("tagname = ?", tagName.toString());            
                    if (st != null) {
                        tag_id = st.getSignalTagId();
                    }    
                    else{
                    	SignalTag stIns = new SignalTag(tagName.toString(), dtf.format(now));
                        if (stIns.saveIt()) {
                            tag_id = (int) stIns.getId();
                        }
                    }
                    SignalTagMapping stm_map = new SignalTagMapping(last_inserted_id, tag_id, dtf.format(now));
                    stm_map.saveIt();
                });
                
				// Get all the information of signals
				String sql = "select s.id as sid, s.signal_name as listitem,s.signal_alias as salias,s.signal_description as description,\n"
						+ "GROUP_CONCAT(DISTINCT(cn.network_name)) as can,\n"
						+ "GROUP_CONCAT(DISTINCT(ln.network_name)) as lin,\n"
						+ "GROUP_CONCAT(DISTINCT(hw.network_name)) as hardware from signals as s \n"
						+ "inner join network as cn on FIND_IN_SET(cn.id,s.can_id_group) > 0 \n"
						+ "inner join network as ln on FIND_IN_SET(ln.id,s.lin_id_group) > 0 \n"
						+ "inner join network as hw on FIND_IN_SET(hw.id,s.hw_id_group) > 0 \n" + "where s.id="
						+ last_inserted_id + " LIMIT 1";
				List<Map> result = Base.findAll(sql);
				columns.put("listitem", s.getSignal_name());
				columns.put("sid", Integer.toString(last_inserted_id));
				columns.put("description", s.getSignal_description());
				columns.put("salias", result.get(0).get("salias"));
				columns.put("can", result.get(0).get("can"));
				columns.put("lin", result.get(0).get("lin"));
				columns.put("hardware", result.get(0).get("hardware"));
				row.add(columns);
			}
		} catch (Exception e) {
			System.out.println("Insert Signal error message" + e.getMessage());
			e.printStackTrace();
//            return 0;
		} finally {
			Base.close();
		}
		return row;
	}

	public static List<Map<String, Object>> LoadIVNPreviousVehicleversionStatus(IVNVersion iv) throws SQLException {
		System.out.println("LoadIVNPreviousVehicleversionStatus");
//        String status = null;
		Base.open();
		List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
		try {
			// String sql = "select v.id,v.versionname,v.status from vehicleversion v where
			// v.status=1";
			String sql = "SELECT iv.status,iv.flag FROM ivnversion iv WHERE iv.id=" + iv.getId();
			row = IVNVersion.findBySQL(sql).toMaps();
		} catch (Exception e) {
			System.out.println("acb version error message" + e.getMessage());
			e.printStackTrace();
		} finally {
			Base.close();
		}
		return row;
	}

	public static Object[] insertIVNVersion(IVNVersion iv) {
		Base.open();
		float versionname = 0.0f;
		try {
			if (iv.getOperation_status().equals("create")) {
				String sql = "SELECT id, ivn_versionname FROM ivnversion ORDER BY ivn_versionname DESC LIMIT 1";
				LazyList<IVNVersion> ivn = IVNVersion.findBySQL(sql);
				if (ivn.size() == 0) {
					versionname = (float) 1.0;
				} else {
					versionname = (float) 1.0 + ivn.get(0).getVersionname();
				}

				IVNVersion ivnVersion = new IVNVersion();
				ivnVersion.set("ivn_versionname", versionname);
				ivnVersion.set("status", iv.getStatus());
				ivnVersion.set("created_or_updated_by", iv.getCreated_or_updated_by());
				ivnVersion.set("flag", iv.getFlag());

				if (ivnVersion.saveIt()) {
					int last_inserted_id = (int) ivnVersion.getId();
					return new Object[] { last_inserted_id, versionname };
				}
			} else {
				// String versionName = "SELECT ivn_versionname FROM ivnversion WHERE id =" +
				// iv.getId();
				IVNVersion ivnvn = IVNVersion.findById(iv.getIVNId());
				if (ivnvn != null) {
					versionname = ivnvn.getVersionname();
				}
				System.out.println(
						"object_value_in_update" + iv.getIVNId() + iv.getStatus() + iv.getCreated_or_updated_by());
				// String sql = "UPDATE ivnversion SET " + "status = ?, created_or_updated_by =
				// ?, flag=? WHERE id = ?";
				IVNVersion.update("status = ? , created_or_updated_by = ?, flag = ?", "id = ?", iv.getStatus(),
						iv.getCreated_or_updated_by(), iv.getFlag(), iv.getIVNId());

				return new Object[] { iv.getId(), versionname };
			}
		} catch (Exception e) {
			System.out.println("ivn version error message" + e.getMessage());
			e.printStackTrace();
			return new Object[] { 0, versionname };
		} finally {
			Base.close();
		}
		return new Object[] { 0, versionname };
	}

	public static int insertIVNNetworkModel(IVNNetwork_VehicleModel invm) {
		Base.open();
		int resultSet_count = 0;
		int last_inserted_id = 0;
		try {
			System.out.println("globaldatastore" + StringUtils.join(GlobalDataStore.globalData, ','));
			if (invm.getOperation_status().equals("update")) {
				System.out.println("update");
				String network_sql = "SELECT * from ivn_" + invm.getNetwork_type() + "models as ntm WHERE "
						+ "ntm.ivnversion_id=" + invm.getIvnversion_id() + " AND ntm.vehicle_and_model_mapping_id="
						+ invm.getVehicle_model_mapping_id() + " AND ntm.network_" + invm.getNetwork_type() + "_id="
						+ invm.getNetwork_id();

				List<Map> getId = Base.findAll(network_sql);
				while (!getId.isEmpty()) {
					GlobalDataStore.globalData.add((Integer) getId.get(0).get("id"));
					last_inserted_id = (int) getId.get(0).get("id");
				}
				resultSet_count = getId.size();
				System.out.println("resultSet_count" + resultSet_count);
			}
			if (resultSet_count == 0) {
				System.out.println("if");
				if (invm.getNetwork_type().equals("can")) {
					/*
					 * preparedStatement = connection.prepareStatement(
					 * "INSERT INTO ivn_canmodels (ivnversion_id, network_can_id,vehicle_and_model_mapping_id,available_status)"
					 * + "VALUES (?, ?, ?, ?)", preparedStatement.RETURN_GENERATED_KEYS);
					 */
					CanModels can = new CanModels(invm.getIvnversion_id(), invm.getNetwork_id(),
							invm.getVehicle_model_mapping_id(), invm.getAvailable_status());
					can.saveIt();
					last_inserted_id = (int) can.getId();
				} else if (invm.getNetwork_type().equals("lin")) {
					/*
					 * preparedStatement = connection.prepareStatement(
					 * "INSERT INTO ivn_linmodels (ivnversion_id, network_lin_id,vehicle_and_model_mapping_id,available_status)"
					 * + "VALUES (?, ?, ?, ?)", preparedStatement.RETURN_GENERATED_KEYS);
					 */
					LinModels lin = new LinModels(invm.getIvnversion_id(), invm.getNetwork_id(),
							invm.getVehicle_model_mapping_id(), invm.getAvailable_status());
					lin.saveIt();
					last_inserted_id = (int) lin.getId();
				} else {
					/*
					 * preparedStatement = connection.prepareStatement(
					 * "INSERT INTO ivn_hardwaremodels (ivnversion_id, network_hardware_id,vehicle_and_model_mapping_id,available_status)"
					 * + "VALUES (?, ?, ?, ?)", preparedStatement.RETURN_GENERATED_KEYS);
					 */
					HardwareModels hardware = new HardwareModels(invm.getIvnversion_id(), invm.getNetwork_id(),
							invm.getVehicle_model_mapping_id(), invm.getAvailable_status());
					hardware.saveIt();
					last_inserted_id = (int) hardware.getId();
				}
			}
			return last_inserted_id;
		} catch (Exception e) {
			System.out.println("IVN can model error message" + e.getMessage());
			e.printStackTrace();
			return 0;
		} finally {
			Base.close();
		}
//        return 0;
	}

	public static int insertIVNVersionGroup(IVNVersionGroup ig) {
		Base.open();
		float versionname;
		try {
			if (ig.getOperation_status().equals("create")) {
				System.out.println("object_value_in_insert" + ig.getCanmodel_group() + ig.getLinmodel_group()
						+ ig.getHardwaremodel_group());

				if (ig.saveIt()) {
					int last_inserted_id = (int) ig.getId();
					return last_inserted_id;
				}
			} else {
				System.out.println("object_value_in_update" + ig.getCanmodel_group() + ig.getLinmodel_group()
						+ ig.getHardwaremodel_group());
				/*
				 * String sql = "UPDATE ivnversion_group SET " +
				 * "canmodel_group = ?, linmodel_group = ?, hardwaremodel_group = ?, signal_group = ?, ecu_group =? "
				 * + "WHERE ivnversion_id = ?"; preparedStatement =
				 * connection.prepareStatement(sql); preparedStatement.setString(1,
				 * ig.getCanmodel_group()); preparedStatement.setString(2,
				 * ig.getLinmodel_group()); preparedStatement.setString(3,
				 * ig.getHardwaremodel_group()); preparedStatement.setString(4,
				 * ig.getSignal_group()); preparedStatement.setString(5, ig.getEcu_group());
				 * preparedStatement.setInt(6, ig.getIVNversion_id());
				 */
				IVNVersionGroup.update(
						"canmodel_group = ?, linmodel_group = ?, hardwaremodel_group = ?, signal_group = ?, ecu_group =?",
						"ivnversion_id = ?", ig.getCanmodel_group(), ig.getLinmodel_group(),
						ig.getHardwaremodel_group(), ig.getSignal_group(), ig.getEcu_group(), ig.getIvnversion_id());
				System.out.println("button_type" + ig.getButton_type());
//                return ig.getId();
				if (ig.getButton_type().equals("save")) {
					return temp_status;
				} else if (ig.getButton_type().equals("submit")) {
					return perm_status;
				}
			}
		} catch (Exception e) {
			System.out.println("pdb version error message" + e.getMessage());
			e.printStackTrace();
			return 0;
		} finally {
			Base.close();
		}
		return 0;
	}

	public static Map<String, Object> LoadIVNPreviousVehicleversionData(IVNVersion ivnver) throws SQLException {
		System.out.println("LoadIVNPreviousVehicleversionData");
		Base.open();
		Map<String, Object> columns_res = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> result_row = new ArrayList<Map<String, Object>>();
			String canmodel_sql = "SELECT CAST(cn.network_can_id as CHAR(100)) as network_id,\n"
					+ "CAST(cn.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n"
					+ "cn.available_status as status \n" + "FROM ivn_canmodels AS cn \n" + "WHERE cn.ivnversion_id="
					+ ivnver.getIVNId();
			System.out.println(canmodel_sql);
			List<Map> canModel = Base.findAll(canmodel_sql);
			List<Map<String, Object>> row1 = new ArrayList<Map<String, Object>>();
			for (Map can : canModel) {
				Map<String, Object> columns1 = new HashMap<String, Object>();
				columns1.put("network_id", can.get("network_id"));
				columns1.put("vmm_id", can.get("vmm_id"));
				columns1.put("status", can.get("status"));
				columns1.put("network_type", "can");
				row1.add(columns1);
			}

			String linmodel_sql = "SELECT CAST(ln.network_lin_id as CHAR(100)) as network_id,\n"
					+ "CAST(ln.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n"
					+ "ln.available_status as status \n" + "FROM ivn_linmodels AS ln \n" + "WHERE ln.ivnversion_id="
					+ ivnver.getIVNId();
			System.out.println(linmodel_sql);
			List<Map> linModel = Base.findAll(linmodel_sql);
			List<Map<String, Object>> row2 = new ArrayList<Map<String, Object>>();
			for (Map lin : linModel) {
				Map<String, Object> columns2 = new HashMap<String, Object>();
				columns2.put("network_id", lin.get("network_id"));
				columns2.put("vmm_id", lin.get("vmm_id"));
				columns2.put("status", lin.get("status"));
				columns2.put("network_type", "lin");
				row2.add(columns2);
			}

			String hwmodel_sql = "SELECT CAST(hw.network_hardware_id as CHAR(100)) as network_id,\n"
					+ "CAST(hw.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,\n"
					+ "hw.available_status as status \n" + "FROM ivn_hardwaremodels AS hw \n"
					+ "where hw.ivnversion_id=" + ivnver.getIVNId();
			System.out.println(hwmodel_sql);
			List<Map> hwModel = Base.findAll(hwmodel_sql);
			List<Map<String, Object>> row3 = new ArrayList<Map<String, Object>>();
			for (Map hw : hwModel) {
				Map<String, Object> columns3 = new HashMap<String, Object>();
				columns3.put("network_id", hw.get("network_id"));
				columns3.put("vmm_id", hw.get("vmm_id"));
				columns3.put("status", hw.get("status"));
				columns3.put("network_type", "hardware");
				row3.add(columns3);
			}

			/*
			 * String ivngroup_sql = "SELECT signal_group, ecu_group " +
			 * "FROM ivnversion_group AS ig \n" + "WHERE ig.ivnversion_id=" +
			 * ivnver.getIVNId(); System.out.println(ivngroup_sql);
			 */
			LazyList<IVNVersionGroup> ivnGroup = IVNVersionGroup.where("ivnversion_id = ?", ivnver.getIVNId());
			for (IVNVersionGroup ivng : ivnGroup) {
				// String strarray[] =resultSet4.getString("signal_group").split(",") ;
				// String strarray1[] =resultSet4.getString("ecu_group").split(",") ;
				// columns_res.put("signal",strarray);
				// columns_res.put("ecu",strarray1);
				// columns_res.put("signal","["+resultSet4.getString("signal_group")+"]");
				// columns_res.put("ecu","["+resultSet4.getString("ecu_group")+"]");

				columns_res.put("signal", ivng.getSignal_group().split(","));
				columns_res.put("ecu", ivng.getEcu_group().split(","));
			}

			// String ivnsignalgroup_sql = "select s.id as sid,s.signal_name as
			// listitem,s.signal_description as description from ivnversion_group as ig
			// inner join signals as s "
			// + "on FIND_IN_SET(s.id,ig.signal_group) > 0 where
			// ig.ivnversion_id="+ivnver.getId();
			// System.out.println(ivnsignalgroup_sql);
			// ResultSet resultSet5 = statement.executeQuery(ivnsignalgroup_sql);
			// ResultSetMetaData metaData5 = resultSet5.getMetaData();
			// int colCount5 = metaData5.getColumnCount();
			// List<Map<String, Object>> row5 = new ArrayList<Map<String, Object>>();
			// while (resultSet5.next()) {
			// Map<String, Object> columns5 = new HashMap<String, Object>();
			// for (int i = 1; i <= colCount5; i++) {
			// columns5.put(metaData5.getColumnLabel(i), resultSet5.getObject(i));
			// }
			// columns5.put("network_type","signal");
			// row5.add(columns5);
			// }
			// String ivnecugroup_sql = "select e.id as eid,e.ecu_name as
			// listitem,e.ecu_description as description from ivnversion_group as ig inner
			// join engine_control_unit as e "
			// + "on FIND_IN_SET(e.id,ig.ecu_group) > 0 where
			// ig.ivnversion_id="+ivnver.getId();
			// System.out.println(ivnecugroup_sql);
			// ResultSet resultSet6 = statement.executeQuery(ivnecugroup_sql);
			// ResultSetMetaData metaData6 = resultSet6.getMetaData();
			// int colCount6 = metaData6.getColumnCount();
			// List<Map<String, Object>> row6 = new ArrayList<Map<String, Object>>();
			// while (resultSet6.next()) {
			// Map<String, Object> columns6 = new HashMap<String, Object>();
			// for (int i = 1; i <= colCount6; i++) {
			// columns6.put(metaData6.getColumnLabel(i), resultSet6.getObject(i));
			// }
			// columns6.put("network_type","ecu");
			// row6.add(columns6);
			// }
			// String vehciledetail_sql = "SELECT \n" +
			// "vv.id as vehver_id,\n" +
			// "v.id as vehicle_id,\n" +
			// "vm.modelname as modelname,\n" +
			// "CAST(vmm.id as CHAR(100)) as vmm_id \n" +
			// "FROM ivn_canmodels AS cn \n" +
			// "INNER JOIN vehicle_and_model_mapping AS vmm ON vmm.id =
			// cn.vehicle_and_model_mapping_id \n" +
			// "INNER JOIN vehicleversion as vv on vv.id=vmm.vehicleversion_id \n" +
			// "INNER JOIN vehicle as v on v.id=vmm.vehicle_id \n" +
			// "INNER JOIN vehiclemodel as vm on vm.id=vmm.model_id\n" +
			// "where cn.ivnversion_id="+ivnver.getId()+" group by
			// modelname,vehicle_and_model_mapping_id";
			// String vehciledetail_sql = "SELECT \n" +
			// "vv.id as vehver_id,\n" +
			// "v.id as vehicle_id,\n" +
			// "vm.modelname as modelname,\n" +
			// "CAST(vmm.id as CHAR(100)) as vmm_id \n" +
			// "FROM ivn_canmodels AS cn \n" +
			// "INNER JOIN vehicle_and_model_mapping AS vmm ON vmm.id =
			// cn.vehicle_and_model_mapping_id \n" +
			// "INNER JOIN vehicleversion as vv on vv.id=vmm.vehicleversion_id \n" +
			// "INNER JOIN vehicle as v on v.id=vmm.vehicle_id \n" +
			// "INNER JOIN vehiclemodel as vm on vm.id=vmm.model_id\n" +
			// "where cn.ivnversion_id="+ivnver.getId()+" group by
			// modelname,vehicle_and_model_mapping_id";
			String v_sql = "SELECT \n" + "vmm.vehicleversion_id,vmm.vehicle_id \n" + "FROM ivn_canmodels AS cn \n"
					+ "INNER JOIN vehicle_and_model_mapping AS vmm ON vmm.id = cn.vehicle_and_model_mapping_id \n"
					+ "WHERE cn.ivnversion_id=" + ivnver.getIVNId() + " limit 1";

			System.out.println("vehciledetail_sql" + v_sql);
			List<Map> vrs = Base.findAll(v_sql);
			String vehciledetail_sql = null;
			if (!vrs.isEmpty()) {
				vehciledetail_sql = "SELECT \n" + "vv.id as vehver_id,\n" + "v.id as vehicle_id,\n"
						+ "vm.modelname as modelname,\n" + "CAST(vmm.id as CHAR(100)) as vmm_id \n"
						+ "from vehicle_and_model_mapping as vmm \n"
						+ "INNER JOIN vehicleversion as vv on vv.id=vmm.vehicleversion_id \n"
						+ "INNER JOIN vehicle as v on v.id=vmm.vehicle_id \n"
						+ "INNER JOIN vehiclemodel as vm on vm.id=vmm.model_id\n" + "where vmm.vehicleversion_id="
						+ vrs.get(0).get("vehicleversion_id") + " AND vmm.vehicle_id=" + vrs.get(0).get("vehicle_id");
			}
			System.out.println("vehciledetail_sql1" + vehciledetail_sql);

			List<Map> row = Base.findAll(vehciledetail_sql);

			// String ivn_status_sql = "SELECT i.status from ivnversion i WHERE i.id=" +
			// ivnver.getIVNId();
			IVNVersion ivn = IVNVersion.findById(ivnver.getIVNId());
			List<Map<String, Object>> row5 = new ArrayList<Map<String, Object>>();
			if (ivn != null) {
				Map<String, Object> columns5 = new HashMap<String, Object>();
				columns5.put("status", ivn.getStatus());
				row5.add(columns5);
			}

			columns_res.put("vehicledetail_list", row);
			columns_res.put("can", row1);
			columns_res.put("lin", row2);
			columns_res.put("hardware", row3);
			columns_res.put("ivnversion_status", row5);
			// columns_res.put("signaldetail_list",row5);
			// columns_res.put("ecudetail_list",row6);
			// System.out.println("columns"+columns_res);
		} catch (Exception e) {
			System.out.println("acb version error message" + e.getMessage());
			e.printStackTrace();
		} finally {
			Base.close();
		}
		return columns_res;
	}

	public static List<Map> GetIVNVersion_Listing() throws SQLException {
		System.out.println("GetIVNVersion_Listing");
		Base.open();
		List<Map> row = new ArrayList<>();
		try {
			// String sql = "SELECT pdb.id as id, CAST(pdb.pdb_versionname as CHAR(100)) as
			// pdb_version, \n" +
			// "GROUP_CONCAT(DISTINCT(vv.id)) as vehicleversion_id,\n" +
			// "GROUP_CONCAT(DISTINCT(vv.versionname)) as veh_version,\n" +
			// "GROUP_CONCAT(DISTINCT(v.vehiclename)) as vehicle,\n" +
			// "GROUP_CONCAT(DISTINCT(vm.modelname)) as model,pdb.status as status,pdb.flag
			// FROM pdbversion as pdb \n" +
			// "INNER JOIN pdbversion_group as pg ON pg.pdbversion_id=pdb.id \n" +
			// "INNER JOIN vehicle_and_model_mapping as vmm ON
			// vmm.id=pg.vehicle_and_model_mapping_id \n" +
			// "INNER JOIN vehicle as v ON v.id=vmm.vehicle_id \n" +
			// "INNER JOIN vehiclemodel as vm ON vm.id=vmm.model_id \n" +
			// "INNER JOIN vehicleversion as vv ON vv.id=vmm.vehicleversion_id group by
			// pg.pdbversion_id order by pdb.id desc";
			String sql = "SELECT ivn.id as id, CAST(ivn.ivn_versionname as CHAR(100)) as ivn_version, \n"
					+ "GROUP_CONCAT(DISTINCT(vv.id)) as vehicleversion_id,\n"
					+ "GROUP_CONCAT(DISTINCT(vv.versionname)) as veh_version,\n"
					+ "GROUP_CONCAT(DISTINCT(v.vehiclename)) as vehicle,\n"
					+ "GROUP_CONCAT(DISTINCT(vm.modelname)) as model,ivn.status as status,ivn.flag FROM ivnversion as ivn \n"
					+ "INNER JOIN ivn_canmodels as cn ON cn.ivnversion_id=ivn.id \n"
					+ "INNER JOIN vehicle_and_model_mapping as vmm ON vmm.id=cn.vehicle_and_model_mapping_id \n"
					+ "INNER JOIN vehicle as v ON v.id=vmm.vehicle_id \n"
					+ "INNER JOIN vehiclemodel as vm ON vm.id=vmm.model_id \n"
					+ "INNER JOIN vehicleversion as vv ON vv.id=vmm.vehicleversion_id group by cn.ivnversion_id order by ivn.id desc";

			List<Map> ivn_list = Base.findAll(sql);
			for(Map ivn : ivn_list) {
				Map<String,Object> columns = new HashMap<>();
				columns.put("id", ivn.get("id"));
				columns.put("ivn_version", ivn.get("ivn_version"));
				columns.put("vehicleversion_id", ivn.get("vehicleversion_id"));
				columns.put("veh_version",ivn.get("veh_version"));
				columns.put("vehicle", ivn.get("vehicle"));
				columns.put("model", ivn.get("model"));
				columns.put("status", ivn.get("status"));
				columns.put("flag", ivn.get("flag"));
				if (CookieRead.getGroupIdFromSession() == 2) {
                    columns.put("delBut", 1);
                }else{
                    columns.put("delBut", 0);
                }
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

	public static List<Map> GetSignal_Listing(Signals s) throws SQLException {
		System.out.println("GetSignal_Listing");
		Base.open();
		List<Map> row = new ArrayList<>();
		try {
			String sql = "select s.id as sid, s.signal_name as listitem,s.signal_alias as salias,s.signal_description as description,\n"
					+ "GROUP_CONCAT(DISTINCT(cn.network_name)) as can,\n"
					+ "GROUP_CONCAT(DISTINCT(ln.network_name)) as lin,\n"
					+ "GROUP_CONCAT(DISTINCT(hw.network_name)) as hardware from signals as s \n"
					+ "left join network as cn on FIND_IN_SET(cn.id,s.can_id_group) > 0 \n"
					+ "left join network as ln on FIND_IN_SET(ln.id,s.lin_id_group) > 0 \n"
					+ "left join network as hw on FIND_IN_SET(hw.id,s.hw_id_group) > 0 \n"
					+ "group by s.id order by s.id DESC";

			row = Base.findAll(sql);
		} catch (Exception e) {
			System.out.println("acb version error message" + e.getMessage());
			e.printStackTrace();

		} finally {
			Base.close();
		}
		return row;
	}

	public static Map<String, Object> GetIVN_Dashboarddata() throws SQLException {
		System.out.println("GetIVN_Dashboarddata");
		Base.open();
		Map<String, Object> columns = new HashMap<String, Object>();

		// Get IVN version count
		// System.out.println("resultset_count" + ivnver_rs.getRow());
		columns.put("ivnversion_count", IVNVersion.count());

		// Get Network version count
		// System.out.println("resultset_count" + nt_rs.getRow());
		columns.put("network_count", Network.count());

		// Get Signal version count
		// System.out.println("resultset_count" + sig_rs.getRow());
		columns.put("signal_count", Signals.count());

		Base.close();
		return columns;
	}

	public static void deleteIVN_network_models(int ivnversion_id, String network_type) throws SQLException {
		System.out.println("globaldatastore" + StringUtils.join(GlobalDataStore.globalData, ','));
		Base.open();
		if (network_type.equals("can")) {
			CanModels.delete("ivnversion_id = ? AND id NOT IN (?)", ivnversion_id,
					StringUtils.join(GlobalDataStore.globalData, ','));
		} else if (network_type.equals("lin")) {
			LinModels.delete("ivnversion_id = ? AND id NOT IN (?)", ivnversion_id,
					StringUtils.join(GlobalDataStore.globalData, ','));
		} else {
			HardwareModels.delete("ivnversion_id = ? AND id NOT IN (?)", ivnversion_id,
					StringUtils.join(GlobalDataStore.globalData, ','));
		}
		Base.close();
		/*
		 * preparedStatement = connection .prepareStatement("delete from ivn_" +
		 * network_type + "models where ivnversion_id=" + ivnversion_id +
		 * " AND id NOT IN (" + StringUtils.join(GlobalDataStore.globalData, ',') +
		 * ")"); preparedStatement.executeUpdate();
		 */
		GlobalDataStore.globalData.clear();
	}

	public static float getIVNVersionNameFromId(int id) {
		Base.open();
		try {
			// String fetch_ivnversionname = "SELECT ivn_versionname FROM ivnversion WHERE
			// id = " + id;
			IVNVersion ivn = IVNVersion.findById(id);
			if (ivn != null) {
				return ivn.getVersionname();
			}
		} catch (Exception e) {
			System.out.println("Error on Fetching IVN Version Name " + e.getMessage());
			e.printStackTrace();
		} finally {
			Base.close();
		}
		return 0;
	}

	public static int getIdFromIVNVersionName(float versionName) {
		Base.open();
		try {
			// String fetch_ivnversionname = "SELECT id FROM ivnversion WHERE
			// ivn_versionname = " + versionName;
			IVNVersion ivn = IVNVersion.findFirst("ivn_versionname = ?", versionName);
			if (ivn != null) {
				return ivn.getIVNId();
			}
		} catch (Exception e) {
			System.out.println("Error on Fetching IVN Version Name Id" + e.getMessage());
			e.printStackTrace();
		} finally {
			Base.close();
		}
		return 0;
	}

	public static int getIdTypeFromNetworkName(String networkName, String networkType) {
		Base.open();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		try {
			/*String fetch_IdType = "SELECT id FROM network WHERE network_name = '" + networkName + "' AND network_type='"
					+ networkType + "'";*/
			Network network = Network.findFirst("network_name = ? AND network_type = ?", networkName, networkType);
			if (network != null) {
				return network.getNetworkId();
			} else {
				Network net = new Network(networkName, "", networkType, dtf.format(now));
				if (net.saveIt()) {
					int last_inserted_id = (int) net.getId();
					return last_inserted_id;
				}
			}
		} catch (Exception e) {
			System.out.println("Error on Fetching ID & Type for Network" + e.getMessage());
			e.printStackTrace();
		} finally {
			Base.close();
		}
		return 0;
	}

	public static Object[] getIdTypeFromNetworkName(String networkName) {
		Base.open();
		try {
			//String fetch_IdType = "SELECT id,network_type FROM network WHERE network_name = '" + networkName + "'";
			Network network = Network.findFirst("network_name = ?", networkName);
			if (network != null) {
				return new Object[] { network.getNetworkId(), network.getNetworkType() };
			}
		} catch (Exception e) {
			System.out.println("Error on Fetching ID & Type for Network" + e.getMessage());
			e.printStackTrace();
		} finally {
			Base.close();
		}
		return null;
	}

	public static int getIdFromSignalName(String signalName) {
		Base.open();
		try {
			//String fetch_signalid = "SELECT id FROM signals WHERE signal_name = '" + signalName + "'";
			Signals signals = Signals.findFirst("signal_name = ?", signalName);
			if (signals != null) {
				return signals.getSignalId();
			} else {
				Signals sIns = new Signals();
				sIns.set("signal_name", signalName);
				sIns.set("created_or_updated_by", CookieRead.getUserIdFromSession());
				if (sIns.saveIt()) {
					int last_inserted_id = (int) sIns.getId();
					System.out.println("Gen ID " + last_inserted_id);
					return last_inserted_id;
				}
			}
		} catch (Exception e) {
			System.out.println("Error on Fetching Signal Id" + e.getMessage());
			e.printStackTrace();
		} finally {
			Base.close();
		}
		return 0;
	}
}

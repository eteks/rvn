package com.model.ivn_supervisor;

import com.controller.common.CookieRead;
import com.db_connection.ConnectionConfiguration;
import com.model.common.GlobalDataStore;
import com.model.pojo.model_version.ModelVersion;
import com.model.pojo.model_version.ModelVersionGroup;
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
import org.javalite.common.Util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ets-2
 */
public class VehicleversionDB {

	public static int temp_status = 0;
	public static int perm_status = 1;
//        public static int[] vehicleversion_id;
//        public static List<Integer> vehiclemodel_mapping_id = new ArrayList<Integer>();

	/*
	 * public static int insertVehicleVersion(Vehicleversion v) { Connection
	 * connection = null; PreparedStatement preparedStatement = null; float
	 * versionname; try { connection = ConnectionConfiguration.getConnection();
	 * 
	 * Statement statement = connection.createStatement();
	 * if(v.getOperation_status().equals("create")){ String sql =
	 * "SELECT id, versionname FROM vehicleversion ORDER BY versionname DESC LIMIT 1"
	 * ; ResultSet resultSet = statement.executeQuery(sql); resultSet.last();
	 * if(resultSet.getRow()==0){ versionname = (float) 1.0; } else{ versionname =
	 * (float) 1.0 + resultSet.getFloat("versionname"); } preparedStatement =
	 * connection.
	 * prepareStatement("INSERT INTO vehicleversion (versionname,status,created_date,created_or_updated_by,flag)"
	 * + "VALUES (?, ?, ?, ?, ?)",preparedStatement.RETURN_GENERATED_KEYS); //
	 * preparedStatement.setString(1, v.getVersionname());
	 * preparedStatement.setDouble(1, versionname); preparedStatement.setBoolean(2,
	 * v.getStatus()); preparedStatement.setString(3, v.getCreated_date());
	 * preparedStatement.setInt(4, v.getCreated_or_updated_by());
	 * preparedStatement.setBoolean(5, v.getFlag());
	 * preparedStatement.executeUpdate();
	 * 
	 * 
	 * ResultSet rs = preparedStatement.getGeneratedKeys(); if(rs.next()) { int
	 * last_inserted_id = rs.getInt(1); return last_inserted_id; } } else{
	 * System.out.println("object_value_in_update"+v.getId()+v.getStatus()+v.
	 * getCreated_or_updated_by()+v.getFlag()); String sql =
	 * "UPDATE vehicleversion SET " +
	 * "status = ?, created_or_updated_by = ?, flag=?  WHERE id = ?";
	 * preparedStatement = connection.prepareStatement(sql);
	 * preparedStatement.setBoolean(1, v.getStatus()); preparedStatement.setInt(2,
	 * v.getCreated_or_updated_by()); preparedStatement.setBoolean(3, v.getFlag());
	 * preparedStatement.setInt(4, v.getId()); preparedStatement.executeUpdate();
	 * return v.getId(); } } catch (Exception e) {
	 * System.out.println("vehicle version error message"+e.getMessage());
	 * e.printStackTrace(); return 0;
	 * 
	 * } finally { if (preparedStatement != null) { try { preparedStatement.close();
	 * } catch (SQLException e) { e.printStackTrace(); return 0; } }
	 * 
	 * if (connection != null) { try { connection.close(); } catch (SQLException e)
	 * { e.printStackTrace(); return 0; } } } return 0; }
	 */
	public static Object[] insertVehicleVersion(VehicleVersion v) {
		float versionname = 0.0f;
		Base.open();
		try {
			if (v.getOperation_status().equals("create")) {
				LazyList<VehicleVersion> vvname = VehicleVersion.findAll().orderBy("versionname DESC").limit(1);

				if (vvname.isEmpty()) {
					versionname = (float) 1.0;
				} else {
					versionname = (float) 1.0 + vvname.get(0).getVersionname();
				}
				v.set("versionname", versionname);
				if (v.saveIt()) {
					return new Object[] { v.getId(), versionname };
				}
			} else {
				VehicleVersion vv = VehicleVersion.findById(v.getVVId());
				if (vv != null) {
					versionname = vv.getVersionname();
				}
				vv.saveIt();
				return new Object[] { v.getVVId(), versionname };
			}
		} catch (Exception e) {
			System.out.println("vehicle version error message" + e.getMessage());
			return new Object[] { 0, versionname };

		} finally {
			Base.close();
		}
		return new Object[] { 0, versionname };
	}

	public static int insertVehicle(Vehicle v) {
		Base.open();
		try {
			// Check whether vehicle name already exists in db or not
			String sql = "SELECT id FROM vehicle WHERE vehiclename ='" + v.getVehiclename().trim() + "'";
			Vehicle cv = Vehicle.findFirst("vehiclename = ?", v.getVehiclename());
			if (cv != null) {
				return cv.getVId();
			} else if (v.saveIt()) {
				return (int) v.getId();
			}
		} catch (Exception e) {
			System.out.println("vehicle version error message" + e.getMessage());
			return 0;

		} finally {
			Base.close();
		}
		return 0;
	}

	public static int insertVehicleModel(VehicleModel v) {
		Base.open();
		try {
			// Check whether model name already exists in db or not
			VehicleModel vm = VehicleModel.findFirst("modelname = ?", v.getModelname());
			if (vm != null) {
				return vm.getVMId();
			} else if (v.saveIt()) {
				return (int) v.getId();
			}
		} catch (Exception e) {
			System.out.println("vehicle version error message" + e.getMessage());
			return 0;
		} finally {
			Base.close();
		}
		return 0;
	}

	public static int insertVehicleModelMapping(VehicleModelMapping v) {
		Base.open();
		int resultSet_count = 0;
		try {
			if (v.getOperation_status().equals("update")) {
				VehicleModelMapping vmm = VehicleModelMapping.findFirst(
						"vehicleversion_id = ? AND vehicle_id = ? AND model_id = ?", v.getVehicleversion_id(),
						v.getVehicle_id(), v.getModel_id());
				if (vmm != null) {
					GlobalDataStore.globalData.add(vmm.getVMMId());
					resultSet_count = 1;
				}
			}
			if (resultSet_count == 0) {
				if (v.saveIt()) {
					GlobalDataStore.globalData.add((int) v.getId());
				}
			}
			if (v.getButton_type().equals("save")) {
				return temp_status;
			} else if (v.getButton_type().equals("submit")) {
				return perm_status;
			}
		} catch (Exception e) {
			System.out.println("vehicle version error message" + e.getMessage());
			return 0;
		} finally {
			Base.close();
		}
		return 0;
	}

	public static List<Map> GetVehicleVersion_Listing() throws SQLException {
		System.out.println("GetVehicleVersion_Listing");
		List<Map> row = new ArrayList<>();
		Base.open();
		try {
			String sql = "SELECT vv.id as id, CAST(versionname as CHAR(100)) as versionname, GROUP_CONCAT( DISTINCT (v.vehiclename) ) AS vehiclename, "
					+ " GROUP_CONCAT( DISTINCT (vm.modelname) ) AS modelname, vv.status, vv.flag FROM vehicle_and_model_mapping AS vmm "
					+ " INNER JOIN vehicle AS v ON v.id = vmm.vehicle_id INNER JOIN vehicleversion AS vv ON"
					+ " vv.id = vmm.vehicleversion_id INNER JOIN vehiclemodel AS vm ON vm.id = vmm.model_id "
					+ " GROUP BY vmm.vehicleversion_id, vmm.vehicle_id ORDER BY vv.id DESC";
			// String sql = "SELECT vmm.vehicleversion_id,vmm.vehicle_id,GROUP_CONCAT(
			// DISTINCT (vmm.model_id) ) AS modelname FROM vehicle_and_model_mapping AS vmm
			// GROUP BY vmm.vehicleversion_id, vmm.vehicle_id ORDER BY
			// vmm.vehicleversion_id";
			List<Map> vehicle_list = Base.findAll(sql);
			for (Map vehicle : vehicle_list) {
				Map<String, Object> columns = new HashMap<>();
				columns.put("id", vehicle.get("id"));
				columns.put("versionname", vehicle.get("versionname"));
				columns.put("vehiclename", vehicle.get("vehiclename"));
				columns.put("modelname", vehicle.get("modelname"));
				columns.put("status", vehicle.get("status"));
				columns.put("flag", vehicle.get("flag"));
				if (CookieRead.getGroupIdFromSession() == 2) {
					columns.put("delBut", 1);
				} else {
					columns.put("delBut", 0);
				}
				row.add(columns);
			}
		} catch (Exception e) {
			System.out.println("acb version error message" + e.getMessage());
		} finally {
			Base.close();
		}
		return row;
	}

	public static List<Map> GetVehicle_Listing(Vehicle veh) throws SQLException {
		System.out.println("GetVehicle_Listing");
		List<Map> row = new ArrayList<Map>();
		Base.open();
		try {
			String sql = "select vv.id,v.vehiclename,v.status,group_concat(DISTINCT(vv.versionname)) as versionname from vehicle as v INNER JOIN "
					+ "vehicle_and_model_mapping as vmm ON vmm.vehicle_id=v.id INNER JOIN vehicleversion as vv ON "
					+ "vv.id=vmm.vehicleversion_id group by v.vehiclename order by v.id desc";

			row = Base.findAll(sql);
		} catch (Exception e) {
			System.out.println("acb version error message" + e.getMessage());
		} finally {
			Base.close();
		}
		return row;
	}

	public static List<Map> GetVehicleModel_Listing(VehicleModel veh) throws SQLException {
		System.out.println("GetVehicleModel_Listing");
		List<Map> row = new ArrayList<>();
		Base.open();
		try {
			String sql = "select m.modelname,m.status,group_concat(DISTINCT(v.vehiclename)) as vehiclename,"
					+ "group_concat(DISTINCT(vv.versionname)) as versionname from vehiclemodel as m INNER JOIN "
					+ "vehicle_and_model_mapping as vmm ON vmm.model_id=m.id INNER JOIN vehicle as v ON "
					+ "v.id=vmm.vehicle_id INNER JOIN vehicleversion as vv ON vv.id=vmm.vehicleversion_id "
					+ "group by m.modelname order by m.id desc";
			row = Base.findAll(sql);
		} catch (Exception e) {
			System.out.println("acb version error message" + e.getMessage());
		} finally {
			Base.close();
		}
		return row;
	}

	public static List<Map<String, Object>> LoadVehicleVersion(String filter) throws SQLException {
		System.out.println("LoadVehicleVersion");
		List<Map<String, Object>> row = new ArrayList<>();
		Base.open();
		try {
			String sql;
			if (filter.equals("active")) {
				sql = "SELECT v.id,v.versionname,v.status FROM vehicleversion v WHERE v.flag=1 AND v.status=1";
			} else {
				sql = "SELECT v.id,v.versionname,v.status FROM vehicleversion v";
			}
			LazyList<VehicleVersion> vehicleVersion = VehicleVersion.findBySQL(sql);
			row = vehicleVersion.toMaps();
			System.out.println("row_data" + row);
		} catch (Exception e) {
			System.out.println("acb version error message" + e.getMessage());
		} finally {
			Base.close();
		}
		return row;
	}

	public static List<Map> LoadPreviousVehicleversionData(VehicleVersion vver) throws SQLException {
		System.out.println("LoadPreviousVehicleversionData");
		List<Map> row = new ArrayList<>();
		Base.open();
		try {
			String sql = "SELECT CAST(versionname as CHAR(100)) as versionname, v.id as vehicle_id, GROUP_CONCAT( DISTINCT (v.vehiclename) ) "
					+ "AS vehiclename, GROUP_CONCAT( DISTINCT (vm.modelname) ) AS modelname,GROUP_CONCAT( DISTINCT (vm.id) ) AS model_id,"
					+ "GROUP_CONCAT( vmm.id ) AS vehicle_mapping_id, vv.status,vv.flag "
					+ "FROM vehicle_and_model_mapping AS vmm INNER JOIN vehicle AS v ON v.id = vmm.vehicle_id "
					+ "INNER JOIN vehicleversion AS vv ON vv.id = vmm.vehicleversion_id INNER JOIN vehiclemodel AS vm "
					+ "ON vm.id = vmm.model_id where vmm.vehicleversion_id=" + vver.getVVId()
					+ " GROUP BY vmm.vehicleversion_id, vmm.vehicle_id";
			// String sql = "select * from vehiclemodel where modelname = '" +
			// v.getModelname().trim() + "'";
			row = Base.findAll(sql);
		} catch (Exception e) {
			System.out.println("acb version error message" + e.getMessage());
		} finally {
			Base.close();
		}
		return row;
	}

	public static Map<String, Object> LoadVehicleModels_and_ACB(int vehver_id, int vehicle_id) throws SQLException {
		System.out.println("LoadVehicleModels_and_ACB");
		Base.open();
		Map<String, Object> columns3 = new HashMap<>();
		try {
			String acb_sql = "SELECT acb.id,CAST(acb.acb_versionname as CHAR(100)) AS acb_versionname FROM acbversion_group AS acbg INNER JOIN acbversion as acb ON acb.id=acbg.acbversion_id WHERE acbg.vehicleversion_id="
					+ vehver_id + " and acbg.vehicle_id=" + vehicle_id
					+ " AND acb.status=1 AND acb.flag=1 AND acb.features_fully_touchedstatus=1 GROUP BY acbg.acbversion_id ORDER BY acb.id DESC";
			List<Map> acb_row = new ArrayList<>();
			acb_row = Base.findAll(acb_sql);
//            columns3.put("ecu_list",row);
			columns3.put("acb_list", acb_row);
			System.out.println("columns" + columns3);
		} catch (Exception e) {
			System.out.println("LoadVehicleModels_and_ACB error message" + e.getMessage());
		} finally {
			Base.close();
		}
		return columns3;
	}

	public static List<Map<String, Object>> LoadModelPreviousVehicleversionStatus(ModelVersion m) throws SQLException {
		System.out.println("LoadModelPreviousVehicleversionStatus");
		Base.open();
		List<Map<String, Object>> row = new ArrayList<>();
		try {
			ModelVersion mv = ModelVersion.findById(m.getMVId());
			Map<String, Object> columns = new HashMap<>();
			columns.put("status", mv.getStatus());
			columns.put("flag", mv.getFlag());
			row.add(columns);
		} catch (Exception e) {
			System.out.println("Model version error message" + e.getMessage());
		} finally {
			Base.close();
		}
		return row;
	}

	public static Object[] insertModelVersion(ModelVersion mv) {
		Base.open();
		float versionname = 0;
		try {
			System.out.println("status_value" + mv.getStatus());
			System.out.println("flag_value" + mv.getFlag());
			if (mv.getOperation_status().equals("create")) {
				// String sql = "SELECT id, model_versionname FROM modelversion ORDER BY
				// model_versionname DESC LIMIT 1";
				LazyList<ModelVersion> mVersionNameList = ModelVersion.findAll().limit(1)
						.orderBy("model_versionname DESC");
				if (mVersionNameList.isEmpty()) {
					versionname = (float) 1.0;
				} else {
					versionname = (float) 1.0 + mVersionNameList.get(0).getVersionname();
				}
				mv.set("model_versionname", versionname);
				if (mv.saveIt()) {
					return new Object[] { mv.getId(), versionname };
				}
			} else {
				// String versionName = "SELECT model_versionname FROM modelversion WHERE id ="
				// + mv.getId();
				ModelVersion mvSelect = ModelVersion.findById(mv.getMVId());
				if (mvSelect != null) {
					versionname = (float) mvSelect.getVersionname();
				}
				System.out.println(
						"object_value_in_update" + mv.getId() + mv.getStatus() + mv.getCreated_or_updated_by());
				/*
				 * String sql = "UPDATE modelversion SET " +
				 * "status = ?, created_or_updated_by = ?, flag=?   WHERE id = ?";
				 */
				ModelVersion.update("status = ?, created_or_updated_by = ?, flag=?", "id = ?", mv.getStatus(),
						mv.getCreated_or_updated_by(), mv.getFlag(), mv.getMVId());
				return new Object[] { mv.getMVId(), versionname };
			}
		} catch (Exception e) {
			System.out.println("Model version error message" + e.getMessage());
			return new Object[] { 0, versionname };
		} finally {
			Base.close();
		}
		return new Object[] { 0, versionname };
	}

	public static int insertModelVersionGroup(ModelVersionGroup mg) {
		Base.open();
		int resultSet_count = 0;
		try {
			if (mg.getOperation_status().equals("update")) {
				System.out.println("update_if");
				String sql = "mg.modelversion_id= ? AND mg.vehicleversion_id=? AND mg.vehicle_id= ? AND mg.acbversion_id= ? AND mg.vehicle_and_model_mapping_id= ? AND mg.ecu_id= ?";
				LazyList<ModelVersionGroup> mvgList = ModelVersionGroup.where(sql, mg.getModelversion_id(),
						mg.getVehicleversion_id(), mg.getVehicle_id(), mg.getACBversion_id(),
						mg.getVehicle_and_model_mapping_id(), mg.getEcu_id());
				for (ModelVersionGroup mvg : mvgList) {
					System.out.println("while");
					if (mvg.getVariant_id() != mg.getVariant_id()) {
						System.out.println("if");
						ModelVersionGroup.update("variant_id = ?", "id = ?", mg.getVariant_id(), mvg.getMVGId());
					}
					GlobalDataStore.globalData.add(mvg.getMVGId());
				}
				resultSet_count = mvgList.size();
				System.out.println("getrow_count" + mvgList.size());
			}
			if (resultSet_count == 0) {
				if (mg.saveIt()) {
					GlobalDataStore.globalData.add((int) mg.getId());
				}
			}
			System.out.println("globalData" + GlobalDataStore.globalData);
			if (mg.getButton_type().equals("save")) {
				return temp_status;
			} else if (mg.getButton_type().equals("submit")) {
				return perm_status;
			}
		} catch (Exception e) {
			System.out.println("Model version error message" + e.getMessage());
			return 0;

		} finally {
			Base.close();
		}
		return 0;
	}

	public static List<Map<String, Object>> LoadModelVersion(String filter) throws SQLException {
		System.out.println("LoadModelVersion");
		Base.open();
		List<Map<String, Object>> row = new ArrayList<>();
		try {
			String sql;
			if (filter.equals("active")) {
				sql = "SELECT m.id,m.model_versionname,m.status FROM modelversion m WHERE m.flag=1 AND m.status=1";
			} else {
				sql = "SELECT m.id,m.model_versionname,m.status FROM modelversion m";
			}
			row = ModelVersion.findBySQL(sql).toMaps();
			System.out.println("row_data" + row);
		} catch (Exception e) {
			System.out.println("Model version error message" + e.getMessage());
		} finally {
			Base.close();
		}
		return row;
	}

	public static Map<String, Object> LoadACBDataForModelVersion(int vehver_id, int vehicle_id, int acbver_id)
			throws SQLException {
		System.out.println("LoadACBDataForModelVersion");
		Base.open();
		Map<String, Object> columns3 = new HashMap<>();
		try {
			String ecu_sql = "select CAST(ag.ecu_id as CHAR(100)) as eid, ecu.ecu_name as listitem,GROUP_CONCAT(DISTINCT(v.id)) as variant_id,GROUP_CONCAT(DISTINCT(v.variant_name)) as variant_name from acbversion_group as ag INNER JOIN engine_control_unit as ecu ON ecu.id=ag.ecu_id INNER JOIN "
					+ "ecu_and_variants_mapping as ev ON ev.ecu_id=ecu.id INNER JOIN variants as v ON v.id=ev.variant_id where ag.vehicleversion_id="
					+ vehver_id + " " + "AND ag.vehicle_id=" + vehicle_id + " AND ag.acbversion_id=" + acbver_id
					+ " GROUP by ag.ecu_id";
			System.out.println(ecu_sql);
			List<Map> row;
			row = Base.findAll(ecu_sql);
			columns3.put("ecu_list", row);
			System.out.println("columns" + columns3);
		} catch (Exception e) {
			System.out.println("LoadVehicleModels_and_ACB error message" + e.getMessage());
		} finally {
			Base.close();
		}
		return columns3;
	}

	public static Map<String, Object> LoadModelPreviousversionData(ModelVersion modelver) throws SQLException {
		System.out.println("LoadModelPreviousversionData");
		Base.open();
		Map<String, Object> columns_res = new HashMap<>();
		try {
			String modelversion_sql = "select mvg.vehicleversion_id as vehicleversion,mvg.vehicle_id as vehiclename,"
					+ "mvg.acbversion_id as acbversion " + "from modelversion_group as mvg where mvg.modelversion_id="
					+ modelver.getMVId() + " LIMIT 1";
			List<Map<String, Object>> row_mvg;
			row_mvg = ModelVersionGroup.findBySQL(modelversion_sql).toMaps();

			String model_sql = "select CAST(mvg.vehicle_and_model_mapping_id as CHAR(100)) as vmm_id,vm.modelname from modelversion_group as mvg "
					+ "inner join vehicle_and_model_mapping as vmm on vmm.id = mvg.vehicle_and_model_mapping_id INNER JOIN vehiclemodel as vm ON vm.id = vmm.model_id \n"
					+ "where mvg.modelversion_id=" + modelver.getMVId() + " group by mvg.vehicle_and_model_mapping_id";
			System.out.println("model_sql" + model_sql);
			List<Map> row_model;
			row_model = Base.findAll(model_sql);

			String ecu_sql = "select CAST(mvg.ecu_id as CHAR(100)) as eid, ecu.ecu_name as listitem,GROUP_CONCAT(DISTINCT(v.id)) as variant_id,GROUP_CONCAT(DISTINCT(v.variant_name)) as variant_name from modelversion_group as mvg INNER JOIN engine_control_unit as ecu ON ecu.id=mvg.ecu_id INNER JOIN "
					+ "ecu_and_variants_mapping as ev ON ev.ecu_id=ecu.id INNER JOIN variants as v ON v.id=ev.variant_id where mvg.modelversion_id="
					+ modelver.getMVId() + " GROUP by mvg.ecu_id ORDER BY mvg.ecu_id DESC";
			System.out.println("ecu_sql" + ecu_sql);
			List<Map> row;
			row = Base.findAll(ecu_sql);

			String mvglist_sql = "select mvg.ecu_id as ecu_id,mvg.vehicle_and_model_mapping_id as vmm_id,mvg.variant_id as variant_id from modelversion_group as mvg where mvg.modelversion_id="
					+ modelver.getMVId() + " ORDER BY mvg.id DESC";
			System.out.println("mvglist_sql" + mvglist_sql);
			List<Map<String, Object>> row_mvglist;
			row_mvglist = ModelVersionGroup.findBySQL(mvglist_sql).toMaps();

			String mv_status_sql = "select mv.status from modelversion mv where mv.id=" + modelver.getId();
			List<Map<String, Object>> row_st;
			row_st = ModelVersion.findBySQL(mv_status_sql).toMaps();

			columns_res.put("modelversion", row_mvg);
			columns_res.put("vehiclemodel_list", row_model);
			columns_res.put("ecu_list", row);
			columns_res.put("modeldata_list", row_mvglist);
			columns_res.put("modelversion_status", row_st);

		} catch (Exception e) {
			System.out.println("Load Model version error message" + e.getMessage());
		} finally {
			Base.close();
		}
		return columns_res;
	}

	public static List<Map> GetModelVersion_Listing() throws SQLException {
		System.out.println("GetModelVersion_Listing");
		Base.open();
		List<Map> row = new ArrayList<>();
		try {
			String sql = "SELECT mv.id as id,CAST(model_versionname as CHAR(100)) as model_versionname, CAST(versionname as CHAR(100)) as vehicle_versionname, "
					+ "GROUP_CONCAT( DISTINCT (acb.acb_versionname) ) AS acb_versionname, "
					+ "GROUP_CONCAT( DISTINCT (v.vehiclename) ) AS vehiclename, "
					+ "GROUP_CONCAT( DISTINCT (vm.modelname) ) AS models, "
					+ "GROUP_CONCAT( DISTINCT (ecu.ecu_name) ) AS ecu,"
					+ " mv.status, mv.flag FROM modelversion_group AS mvg "
					+ " INNER JOIN vehicle AS v ON v.id = mvg.vehicle_id INNER JOIN vehicleversion AS vv ON"
					+ " vv.id = mvg.vehicleversion_id INNER JOIN acbversion AS acb ON acb.id = mvg.acbversion_id"
					+ " INNER JOIN modelversion AS mv ON mv.id = mvg.modelversion_id"
					+ " INNER JOIN vehicle_and_model_mapping AS vmm ON vmm.id = mvg.vehicle_and_model_mapping_id"
					+ " INNER JOIN vehiclemodel AS vm ON vm.id = vmm.model_id"
					+ " INNER JOIN engine_control_unit AS ecu ON ecu.id = mvg.ecu_id"
					+ " GROUP BY mvg.modelversion_id DESC";
			row = Base.findAll(sql);
		} catch (Exception e) {
			System.out.println("Model version listing error message" + e.getMessage());
		} finally {
			Base.close();
		}
		return row;
	}

	public static Map<String, Object> GetVehMod_Dashboarddata() throws SQLException {
		System.out.println("GetVehMod_Dashboarddata");
		Base.open();
		Map<String, Object> columns = new HashMap<>();

		// Get Vehicle count
		// System.out.println("resultset_count" + veh_rs.getRow());
		columns.put("vehiclecount", Vehicle.count());

		// Get Model count
		// System.out.println("resultset_count" + mod_rs.getRow());
		columns.put("modelcount", VehicleModel.count());

		// Get Vehicle Versions count
		// System.out.println("resultset_count" + vehver_rs.getRow());
		columns.put("vehicleversion_count", VehicleVersion.count());

		// Get Model Versions count
		// System.out.println("resultset_count" + modver_rs.getRow());
		columns.put("modelversion_count", ModelVersion.count());

		Base.close();
		return columns;
	}

	public static void deleteVehicleModelMapping(int vehicleversion_id) throws SQLException {
		Base.open();
		// preparedStatement = connection.prepareStatement("delete from
		// vehicle_and_model_mapping where vehicleversion_id=" + vehicleversion_id + "
		// AND id NOT IN (" + StringUtils.join(GlobalDataStore.globalData, ',') + ")");
		LazyList<VehicleModelMapping> vmmDelList = VehicleModelMapping.where(
				"vehicleversion_id = ? AND id NOT IN (" + StringUtils.join(GlobalDataStore.globalData, ',') + ")",
				vehicleversion_id);
		for (VehicleModelMapping vehicleModelMapping : vmmDelList) {
			vehicleModelMapping.delete();
		}
		Base.close();

		GlobalDataStore.globalData.clear();
	}

	public static void deleteModelVersion_Group(int modelversion_id, String action_type) throws SQLException {
		Base.open();
		System.out.println("deletemodelversiongroup" + GlobalDataStore.globalData);
		System.out.println("action_type" + action_type);
		if (action_type.equals("update")) {
			/*
			 * preparedStatement = connection.
			 * prepareStatement("delete from modelversion_group where modelversion_id=" +
			 * modelversion_id + " AND id NOT IN (" +
			 * StringUtils.join(GlobalDataStore.globalData, ',') + ")");
			 */
			String deleteQuery = "DELETE from modelversion_group WHERE modelversion_id=" + modelversion_id
					+ " AND id NOT IN (" + StringUtils.join(GlobalDataStore.globalData, ',') + ")";
			ModelVersionGroup.delete(deleteQuery);
		}
		Base.close();
		GlobalDataStore.globalData.clear();
	}

	public static Object[] getVehicleModelId(String vehicleName, String modelName) {
		Base.open();
		int vehicle_id = 0, model_id = 0;
		try {
			// String fetch_vehicleId = "SELECT id FROM vehicle WHERE vehiclename = '" +
			// vehicleName + "'";
			Vehicle vehicle = Vehicle.findFirst("vehiclename = ?", vehicleName);
			vehicle_id = vehicle.getVId();

			// String fetch_modelId = "SELECT id FROM vehiclemodel WHERE modelname = '" +
			// modelName + "'";
			VehicleModel vm = VehicleModel.findFirst("modelname = ?", modelName);
			model_id = vm.getVMId();

			return new Object[] { vehicle_id, model_id };
		} catch (Exception e) {
			System.out.println("Error on Fetching Vehicle & Model Id" + e.getMessage());
			e.printStackTrace();
			return new Object[] { vehicle_id, model_id };

		} finally {
			Base.close();
		}
	}

	public static int getVehicleModelMappingId(Object[] obj) {
		Base.open();
		int vmm_id = 0;
		try {
			/*
			 * String fetch_vmmId =
			 * "SELECT id FROM vehicle_and_model_mapping WHERE vehicle_id = " + (int) obj[0]
			 * + " AND model_id = " + (int) obj[1];
			 */
			VehicleModelMapping vmm = VehicleModelMapping.findFirst("vehicle_id = ? AND model_id = ?", (int) obj[0],
					(int) obj[1]);
			vmm_id = vmm.getVMMId();

			return vmm_id;
		} catch (Exception e) {
			System.out.println("Error on Fetching Vehicle & Model Id" + e.getMessage());
			e.printStackTrace();
			return vmm_id;

		} finally {
			Base.close();
		}
	}

	public static int getVehicleModelMappingId(int vvId, Object[] obj) {
		Base.open();
		int vmm_id = 0;
		try {
			/*String fetch_vmmId = "SELECT id FROM vehicle_and_model_mapping WHERE vehicleversion_id = " + vvId
					+ " AND vehicle_id = " + (int) obj[0] + " AND model_id = " + (int) obj[1];*/
			VehicleModelMapping vmm = VehicleModelMapping.findFirst(
					"vehicleversion_id = ? AND vehicle_id = ? AND model_id = ?", vvId, (int) obj[0], (int) obj[1]);
			vmm_id = vmm.getVMMId();

			return vmm_id;
		} catch (Exception e) {
			System.out.println("Error on Fetching Vehicle & Model Id" + e.getMessage());
			e.printStackTrace();
			return vmm_id;

		} finally {
			Base.close();
		}
	}

	public static List<String> getVehicleModelList(int vehicleversion_id, int vehicle_id) {
		Base.open();
		List<String> modelList = new ArrayList<>();
		try {
			/*
			 * String fetch_modelId =
			 * "SELECT model_id FROM vehicle_and_model_mapping WHERE vehicleversion_id = " +
			 * vehicleversion_id + " AND vehicle_id = " + vehicle_id;
			 */
			// String fetch_modelList = "SELECT modelname FROM vehiclemodel WHERE id IN (" +
			// fetch_modelId + ")";

			LazyList<VehicleModelMapping> modelId_list = VehicleModelMapping
					.where("vehicleversion_id = ? AND vehicle_id = ?", vehicleversion_id, vehicle_id);
			List<String> model_id = new ArrayList<>();
			for (VehicleModelMapping vehicleModelMapping : modelId_list) {
				model_id.add(vehicleModelMapping.getModel_id() + "");
			}

			LazyList<VehicleModel> modelName_list = VehicleModel.where("id IN ('" + Util.join(model_id, "', '") + "')");
			for (VehicleModel vm : modelName_list) {
				modelList.add(vm.getModelname());
			}
		} catch (Exception e) {
			System.out.println("Error on Fetching Vehicle Model List" + e.getMessage());
			e.printStackTrace();
		} finally {
			Base.close();
		}
		return modelList;
	}

	public static int getVehicleModelMappingId(int vehicleversion_id, int vehicle_id, int model_id) {
		Base.open();
		int vmm_id = 0;
		try {
			/*
			 * String fetch_vmmId =
			 * "SELECT id FROM vehicle_and_model_mapping WHERE vehicle_id = " + vehicle_id +
			 * " AND model_id = " + model_id + " AND vehicleversion_id = " +
			 * vehicleversion_id;
			 */
			VehicleModelMapping vmm = VehicleModelMapping.findFirst(
					"vehicle_id = ? AND model_id = ? AND vehicleversion_id = ?", vehicle_id, model_id,
					vehicleversion_id);
			if (vmm != null) {
				vmm_id = vmm.getVMMId();
			}

			return vmm_id;
		} catch (Exception e) {
			System.out.println("Error on Fetching Vehicle & Model Id" + e.getMessage());
			e.printStackTrace();
			return vmm_id;

		} finally {
			Base.close();
		}
	}

	public static float getVehicleVersionNameFromId(int id) {
		Base.open();
		try {
			// String fetch_versionname = "SELECT versionname FROM vehicleversion WHERE id =
			// " + id;
			VehicleVersion vv = VehicleVersion.findById(id);
			if (vv != null) {
				return vv.getVersionname();
			}
		} catch (Exception e) {
			System.out.println("Error on Fetching Version Name " + e.getMessage());
			e.printStackTrace();
		} finally {
			Base.close();
		}
		return 0;
	}

	public static int getIdFromVehicleVersionName(float vehicleVersion) {
		Base.open();
		try {
			// String fetch_versionname = "SELECT id FROM vehicleversion WHERE versionname =
			// " + vehicleVersion;
			VehicleVersion vv = VehicleVersion.findFirst("versionname = ?", vehicleVersion);
			if (vv != null) {
				return vv.getVVId();
			}
		} catch (Exception e) {
			System.out.println("Error on Fetching Version Name ID " + e.getMessage());
			e.printStackTrace();
		} finally {
			Base.close();
		}
		return 0;
	}

	public static int getIdFromVehicleModalName(String modalName) {
		Base.open();
		try {
			// String fetch_modal_id = "SELECT id FROM vehiclemodel WHERE modelname = '" +
			// modalName + "'";
			VehicleModel vm = VehicleModel.findFirst("modelname = ?", modalName);
			if (vm != null) {
				return vm.getVMId();
			}
		} catch (Exception e) {
			System.out.println("Error on Fetching Modal Name ID " + e.getMessage());
			e.printStackTrace();
		} finally {
			Base.close();
		}
		return 0;
	}

	public static String getVehicleNameFromId(int id) {
		Base.open();
		try {
			// String fetch_vehiclename = "SELECT vehiclename FROM vehicle WHERE id = " +
			// id;
			Vehicle v = Vehicle.findById(id);
			if (v != null) {
				return v.getVehiclename();
			}
		} catch (Exception e) {
			System.out.println("Error on Fetching Vehicle Name " + e.getMessage());
			e.printStackTrace();
		} finally {
			Base.close();
		}
		return null;
	}

	public static String getIdFromVehicleName(String vehicleName) {
		Base.open();
		try {
			// String fetch_vehiclename = "SELECT id FROM vehicle WHERE vehiclename = '" +
			// vehicleName + "'";
			Vehicle v = Vehicle.findFirst("vehiclename = ?", vehicleName);
			if (v != null) {
				return v.getVId() + "";
			}
		} catch (Exception e) {
			System.out.println("Error on Fetching Vehicle Name Id" + e.getMessage());
			e.printStackTrace();
		} finally {
			Base.close();
		}
		return null;
	}
}

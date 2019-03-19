/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.import_file;

import com.controller.exception.ImportParseException;
import com.controller.ivn_engineer.Network_Signal_and_Ecu;
import com.model.acb_owner.ACBOwnerDB;
import com.model.ivn_engineer.IVNEngineerDB;
import com.model.ivn_supervisor.VehicleversionDB;
import com.model.pdb_owner.PDBVersionDB;
import com.model.system_owner.SystemOwnerDB;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author ETS-4
 */
public class ImportCSV {

    public static Object[] getDetailsFromCSV(String filePath) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(filePath));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.EXCEL);
        List<CSVRecord> csvRecord = csvParser.getRecords();

        JSONObject pdbObject = new JSONObject();
        //String veh_ver = csvRecord.get(0).get(1);
        pdbObject.put("vehicle-version", csvRecord.get(0).get(1));

        //To Find Modal Size of Vehicle 
        Map<String, Integer> name_size = new LinkedHashMap<>();
        String vehicle_name = "";
        for (int j = 2; j < csvRecord.get(0).size(); j++) {
            if (!csvRecord.get(0).get(j).isEmpty()) {
                vehicle_name = csvRecord.get(0).get(j);
                name_size.put(vehicle_name, 1);
            } else {
                name_size.put(vehicle_name, name_size.get(vehicle_name) + 1);
            }
        }

        JSONArray vehicle = new JSONArray();
        int column_size = 2;
        for (Map.Entry<String, Integer> entry : name_size.entrySet()) {
            JSONObject name_modal = new JSONObject();
            JSONArray modal = new JSONArray();
            name_modal.put("name", entry.getKey());
            for (int i = 1; i <= entry.getValue(); i++) {
                modal.add(csvRecord.get(1).get(column_size));
                column_size++;
            }
            name_modal.put("modal", modal);
            vehicle.add(name_modal);
        }
        pdbObject.put("vehicle", vehicle);

        //To Find Feature Size of Domain 
        Map<String, Integer> domain_size = new LinkedHashMap<>();
        String domain_name = "";
        for (int i = 2; i < csvRecord.size(); i++) {
            if (!csvRecord.get(i).get(0).isEmpty()) {
                domain_name = csvRecord.get(i).get(0);
                domain_size.put(domain_name, 1);
            } else {
                domain_size.put(domain_name, domain_size.get(domain_name) + 1);
            }
        }

        JSONArray domain_features = new JSONArray();
        column_size = 2;
        for (Map.Entry<String, Integer> entry : domain_size.entrySet()) {
            JSONObject dom_fea = new JSONObject();
            JSONArray features = new JSONArray();
            dom_fea.put("domain_name", entry.getKey());
            for (int i = 1; i <= entry.getValue(); i++) {
                features.add(csvRecord.get(column_size).get(1));
                column_size++;
            }
            dom_fea.put("feature", features);
            domain_features.add(dom_fea);
        }
        pdbObject.put("domain_feature", domain_features);
        return new Object[]{pdbObject, csvRecord};
        //System.out.print(pdbObject);
    }

    public static JSONObject addPDBversion_group(JSONObject pdbObject, List<CSVRecord> csvRecord) throws IOException, ImportParseException {
        try {
            JSONArray pdb_list = new JSONArray();
            float vVersionName = Float.parseFloat(pdbObject.get("vehicle-version").toString());
            JSONArray vehicle = (JSONArray) pdbObject.get("vehicle");
            JSONArray domain_features = (JSONArray) pdbObject.get("domain_feature");
            int column_size = 2;
            int row_size = 2;
            for (int v = 0; v < vehicle.size(); v++) {
                JSONObject each = new JSONObject();
                JSONArray listmain = new JSONArray();
                JSONObject eachVehicle = (JSONObject) vehicle.get(v);
                String v_name = (String) eachVehicle.get("name");
                JSONArray modal = (JSONArray) eachVehicle.get("modal");
                for (int d = 0; d < domain_features.size(); d++) {
                    JSONObject eachDomain = (JSONObject) domain_features.get(d);
                    String d_name = (String) eachDomain.get("domain_name");
                    JSONArray feature = (JSONArray) eachDomain.get("feature");
                    for (int f = 0; f < feature.size(); f++) {
                        String feature_name = feature.get(f).toString();
                        for (int m = 0; m < modal.size(); m++) {
                            String modal_name = modal.get(m).toString();
                            JSONObject list = new JSONObject();
                            int vvId = VehicleversionDB.getIdFromVehicleVersionName(vVersionName);
                            int vmm_id = VehicleversionDB.getVehicleModelMappingId(vvId,VehicleversionDB.getVehicleModelId(v_name, modal_name));
                            int dfm_id = PDBVersionDB.getDomainFeatureMappingId(PDBVersionDB.getDomainFeatureId(d_name, feature_name));
                            if (vmm_id == 0) {
                                throw new ImportParseException("Vehicle name " + v_name + " or Model name " + modal_name + " is Incorrect");
                            }
                            if (dfm_id == 0) {
                                throw new ImportParseException("Domain or Feature name is Incorrect");
                            }
                            list.put("vmm_id", vmm_id);
                            list.put("dfm_id", dfm_id);
                            list.put("status", csvRecord.get(row_size).get(column_size).toLowerCase());
                            listmain.add(list);
                            column_size++;
                        }
                        ++row_size;
                        column_size -= modal.size() - 1;
                        column_size--;
                    }
                }
                each.put(v_name, listmain);
                pdb_list.add(each);
                column_size += modal.size();
                row_size = 2;
            }
            pdbObject.put("pdbdata_list", pdb_list);
            return pdbObject;
        } catch (ImportParseException ipe) {
            throw ipe;
        }
    }

    public static JSONObject getSignalDetailsFromCSV(String filePath) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(filePath));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.EXCEL);
        List<CSVRecord> csvRecord = csvParser.getRecords();

        JSONObject signalObject = new JSONObject();

        // To Find Modal Size of Signal
        Map<String, Integer> signal_size = new LinkedHashMap<>();
        String signal_name = "";
        for (int i = 1; i < csvRecord.size(); i++) {
            if (!csvRecord.get(i).get(0).isEmpty()) {
                signal_name = csvRecord.get(i).get(0);
                signal_size.put(signal_name, 1);
            } else {
                signal_size.put(signal_name, signal_size.get(signal_name) + 1);
            }
        }

        JSONArray ivn_data = new JSONArray();
        int column_size = 1;
        for (Map.Entry<String, Integer> entry : signal_size.entrySet()) {
            JSONObject ivn_list = new JSONObject();
            JSONArray can = new JSONArray();
            JSONArray lin = new JSONArray();
            JSONArray hardware = new JSONArray();
            ivn_list.put("signal_name", entry.getKey());
            int limit = entry.getValue();
            outer:
            for (int i = 1; i < csvRecord.size(); i++) {
                for (int j = 1; j <= limit; j++) {
                    if (i == 1 && !csvRecord.get(column_size).get(i).isEmpty()) {
                        ivn_list.put("signal_alias", csvRecord.get(column_size).get(i));
                        continue outer;
                    } else if (i == 2 && !csvRecord.get(column_size).get(i).isEmpty()) {
                        ivn_list.put("signal_desc", csvRecord.get(column_size).get(i));
                        continue outer;
                    } else if (i == 3 && !csvRecord.get(column_size).get(i).isEmpty()) {
                        can.add(csvRecord.get(column_size).get(i));
                    } else if (i == 4 && !csvRecord.get(column_size).get(i).isEmpty()) {
                        lin.add(csvRecord.get(column_size).get(i));
                    } else if (i == 5 && !csvRecord.get(column_size).get(i).isEmpty()) {
                        hardware.add(csvRecord.get(column_size).get(i));
                    }
                    column_size++;
                }
                ivn_list.put("can", can);
                ivn_list.put("lin", lin);
                ivn_list.put("hardware", hardware);
                column_size -= limit;
            }
            ivn_data.add(ivn_list);
            column_size += limit;
        }
        signalObject.put("signal_data", ivn_data);
        //System.out.println("IVN "+ivnObject);
        return signalObject;
    }

    public static JSONObject getIVNDetailsFromCSV(String filePath) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(filePath));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.EXCEL);
        List<CSVRecord> csvRecord = csvParser.getRecords();

        JSONObject ivnObject = new JSONObject();
        ivnObject.put("button_type", "submit");

        //String veh_ver = csvRecord.get(0).get(1);
        float vehicle_version = Float.parseFloat(csvRecord.get(0).get(1));
        String vehicle_name = csvRecord.get(0).get(3);

        int vehicleversion_id = VehicleversionDB.getIdFromVehicleVersionName(vehicle_version);

        JSONObject ivnVersion = new JSONObject();
        ivnVersion.put("vehiclename", VehicleversionDB.getIdFromVehicleName(vehicle_name));
        ivnVersion.put("vehicleversion", vehicleversion_id + "");
        ivnVersion.put("status", false);
        ivnObject.put("ivnversion", ivnVersion);

        List<String> modalList = new ArrayList<>();
        for (int i = 1; i < csvRecord.get(2).size(); i++) {
            if (!csvRecord.get(2).get(i).equalsIgnoreCase("lin")) {
                modalList.add(csvRecord.get(2).get(i));
            } else {
                break;
            }
        }

        List<String> canList = new ArrayList<>();
        List<String> linList = new ArrayList<>();
        List<String> hardwareList = new ArrayList<>();
        List<String> signalList = new ArrayList<>();
        List<String> ecuList = new ArrayList<>();
        for (int i = 3; i < csvRecord.size(); i++) {
            int pointer = 0;
            if (!csvRecord.get(i).get(pointer).isEmpty()) {
                canList.add(csvRecord.get(i).get(pointer));
            }
            pointer += modalList.size() + 1;
            if (!csvRecord.get(i).get(pointer).isEmpty()) {
                linList.add(csvRecord.get(i).get(pointer));
            }
            pointer += modalList.size() + 1;
            if (!csvRecord.get(i).get(pointer).isEmpty()) {
                hardwareList.add(csvRecord.get(i).get(pointer));
            }
            pointer += modalList.size() + 1;
            if (!csvRecord.get(i).get(pointer).isEmpty()) {
                signalList.add(csvRecord.get(i).get(pointer));
            }
            pointer++;
            if (!csvRecord.get(i).get(pointer).isEmpty()) {
                ecuList.add(csvRecord.get(i).get(pointer));
            }
        }

        JSONObject ivndata_list = new JSONObject();
        JSONArray lin = new JSONArray();
        JSONArray can = new JSONArray();
        JSONArray ecu = new JSONArray();
        JSONArray signal = new JSONArray();
        JSONArray hardware = new JSONArray();

        int position = 1;
        for (int i = 3, c = 0; i < csvRecord.size(); i++, c++) {
            if (c < canList.size()) {
                for (int m = 0; m < modalList.size(); m++) {
                    Object[] vehicle_model_id = VehicleversionDB.getVehicleModelId(vehicle_name, modalList.get(m));
                    int vmm_id = VehicleversionDB.getVehicleModelMappingId(vehicleversion_id, (int) vehicle_model_id[0], (int) vehicle_model_id[1]);
                    if (csvRecord.get(i).get(position).equalsIgnoreCase("y")) {
                        int network = IVNEngineerDB.getIdTypeFromNetworkName(canList.get(c), "can");
                        JSONObject cObj = new JSONObject();
                        cObj.put("network_id", network + "");
                        cObj.put("vmm_id", vmm_id + "");
                        cObj.put("network_type", "can");
                        cObj.put("status", true);
                        can.add(cObj);
                    }
                    position++;
                }
            } else {
                position = 1;
                break;
            }
            position = 1;
        }

        position += modalList.size() + 1;
        for (int i = 3, l = 0; i < csvRecord.size(); i++, l++) {
            if (l < linList.size()) {
                for (int m = 0; m < modalList.size(); m++) {
                    Object[] vehicle_model_id = VehicleversionDB.getVehicleModelId(vehicle_name, modalList.get(m));
                    int vmm_id = VehicleversionDB.getVehicleModelMappingId(vehicleversion_id, (int) vehicle_model_id[0], (int) vehicle_model_id[1]);
                    if (csvRecord.get(i).get(position).equalsIgnoreCase("y")) {
                        int network = IVNEngineerDB.getIdTypeFromNetworkName(linList.get(l), "lin");
                        JSONObject lObj = new JSONObject();
                        lObj.put("network_id", network + "");
                        lObj.put("vmm_id", vmm_id + "");
                        lObj.put("network_type", "lin");
                        lObj.put("status", true);
                        lin.add(lObj);
                    }
                    position++;
                }
            } else {
                break;
            }
            position -= modalList.size();
        }

        position += modalList.size() + 1;
        for (int i = 3, h = 0; i < csvRecord.size(); i++, h++) {
            if (h < hardwareList.size()) {
                for (int m = 0; m < modalList.size(); m++) {
                    Object[] vehicle_model_id = VehicleversionDB.getVehicleModelId(vehicle_name, modalList.get(m));
                    int vmm_id = VehicleversionDB.getVehicleModelMappingId(vehicleversion_id, (int) vehicle_model_id[0], (int) vehicle_model_id[1]);
                    if (csvRecord.get(i).get(position).equalsIgnoreCase("y")) {
                        int network = IVNEngineerDB.getIdTypeFromNetworkName(hardwareList.get(h), "hardware");
                        JSONObject hObj = new JSONObject();
                        hObj.put("network_id", network + "");
                        hObj.put("vmm_id", vmm_id + "");
                        hObj.put("network_type", "hardware");
                        hObj.put("status", true);
                        hardware.add(hObj);
                    }
                    position++;
                }
            } else {
                break;
            }
            position -= modalList.size();
        }

        for (int s = 0; s < signalList.size(); s++) {
            signal.add(IVNEngineerDB.getIdFromSignalName(signalList.get(s)) + "");
        }

        for (int e = 0; e < ecuList.size(); e++) {
            ecu.add(ACBOwnerDB.getIdFromECU(ecuList.get(e)) + "");
        }

        ivndata_list.put("lin", lin);
        ivndata_list.put("can", can);
        ivndata_list.put("ecu", ecu);
        ivndata_list.put("signal", signal);
        ivndata_list.put("hardware", hardware);
        ivnObject.put("ivndata_list", ivndata_list);

        System.out.println("IVN Object " + ivnObject);
        return ivnObject;
    }

    public static JSONObject getACBDetailsFromCSV(String filePath) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(filePath));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.EXCEL);
        List<CSVRecord> csvRecord = csvParser.getRecords();

        JSONObject acbObject = new JSONObject();
        float vehicle_version = Float.parseFloat(csvRecord.get(0).get(1));
//        float vehicle_version = Float.parseFloat(vehicle_ver.substring(2, vehicle_ver.length()-1));
        String vehicle_name = csvRecord.get(0).get(3);
        //String pdb_ver = csvRecord.get(0).get(5);
        float pdb_version = Float.parseFloat(csvRecord.get(0).get(5));
//        float pdb_version = Float.parseFloat(pdb_ver.substring(2, pdb_ver.length()-1));
        //String ivn_ver = csvRecord.get(0).get(7);
        float ivn_version = Float.parseFloat(csvRecord.get(0).get(7));
//        float ivn_version = Float.parseFloat(ivn_ver.substring(2, ivn_ver.length()-1));

        JSONObject acbVersion = new JSONObject();
        int vehicleversion_id = VehicleversionDB.getIdFromVehicleVersionName(vehicle_version);
        int pdbversion_id = PDBVersionDB.getIdFromPDBVersionName(pdb_version);
        acbVersion.put("vehicleversion", vehicleversion_id + "");
        acbVersion.put("vehiclename", VehicleversionDB.getIdFromVehicleName(vehicle_name));
        acbVersion.put("pdbversion", pdbversion_id + "");
        acbVersion.put("ivnversion", IVNEngineerDB.getIdFromIVNVersionName(ivn_version) + "");
        acbVersion.put("status", false);

        acbObject.put("acbversion", acbVersion);
        acbObject.put("features_fully_touchedstatus", false);
        acbObject.put("button_type", "submit");

        List<Integer> col_size = new ArrayList<>();
        int size = 0;
        for (int i = 2; i < csvRecord.size(); i++) {
            if (i == 2) {
                size = 1;
            } else if (i + 1 == csvRecord.size() && !csvRecord.get(i).get(0).isEmpty()) {
                col_size.add(size);
                col_size.add(1);
            } else if (i + 1 == csvRecord.size() && csvRecord.get(i).get(0).isEmpty()) {
                col_size.add(size);
            } else if (!csvRecord.get(i).get(0).isEmpty()) {
                col_size.add(size);
                size = 1;
            } else {
                size++;
            }
        }

        List<String> modalList = new ArrayList<>();

        for (int i = 2; i < csvRecord.get(1).size(); i++) {
            if (!csvRecord.get(1).get(i).equals("ECU")) {
                modalList.add(csvRecord.get(1).get(i));
            } else {
                break;
            }
        }

        //int length = 2 + modalList.size() + 2 + modalList.size() + 1 + modalList.size();
        JSONArray acbdata_list = new JSONArray();
        JSONObject data = null;
        int inital_col = 2;
        for (int col = 0; col < col_size.size(); col++) {
            JSONArray cloned_data = new JSONArray();
            int dfm_id = 0;
            //rowbreak:
            for (int i = 0; i < col_size.get(col); i++) {
                if (i == 0) {
                    String ecu = csvRecord.get(inital_col).get(modalList.size() + 2);
                    System.out.println("ECU "+ecu);
                    if (!ecu.isEmpty()) {
                        System.out.println("ECU IN");
                        data = new JSONObject();
                        String domain = csvRecord.get(inital_col).get(0);
                        String features = csvRecord.get(inital_col).get(1);
                        dfm_id = PDBVersionDB.getDomainFeatureMappingId(PDBVersionDB.getDomainFeatureId(domain, features));
                        data.put("fid", dfm_id + "");
                        data.put("ecu", ACBOwnerDB.getIdFromECU(ecu) + "");
                    } else {
                        inital_col++;
                        data = null;
                        System.out.println("Break");
                        break;
                    }
                    JSONObject input = new JSONObject();
                    JSONArray inpArray = new JSONArray();
                    int inp_modal_size = modalList.size() + 4;
                    for (int in = 0; in < modalList.size(); in++) {
                        JSONObject inpObj = new JSONObject();
                        Object[] vehicle_model_id = VehicleversionDB.getVehicleModelId(vehicle_name, modalList.get(in));
                        int vmm_id = VehicleversionDB.getVehicleModelMappingId(vehicleversion_id, (int) vehicle_model_id[0], (int) vehicle_model_id[1]);
                        int pdbgroup_id = PDBVersionDB.getIdFromPDBVersionGroup(pdbversion_id, vmm_id, dfm_id);
                        Object[] inp_nt_data = IVNEngineerDB.getIdTypeFromNetworkName(csvRecord.get(inital_col).get(inp_modal_size));
                        inpObj.put("nt_id", inp_nt_data[0] + "");
                        inpObj.put("nt_type", inp_nt_data[1] + "");
                        inpObj.put("pdbgroup_id", pdbgroup_id + "");
                        inpObj.put("vmm_id", vmm_id + "");
                        inpArray.add(inpObj);
                        inp_modal_size++;
                    }
                    input.put("group_data", inpArray);
                    input.put("signal", IVNEngineerDB.getIdFromSignalName(csvRecord.get(inital_col).get(modalList.size() + 3)) + "");
                    input.put("signal_type", "input");
                    cloned_data.add(input);

                    JSONObject output = new JSONObject();
                    JSONArray outArray = new JSONArray();
                    int out_modal_size = modalList.size() * 2 + 5;
                    for (int ou = 0; ou < modalList.size(); ou++) {
                        JSONObject outObj = new JSONObject();
                        Object[] vehicle_model_id = VehicleversionDB.getVehicleModelId(vehicle_name, modalList.get(ou));
                        int vmm_id = VehicleversionDB.getVehicleModelMappingId(vehicleversion_id, (int) vehicle_model_id[0], (int) vehicle_model_id[1]);
                        int pdbgroup_id = PDBVersionDB.getIdFromPDBVersionGroup(pdbversion_id, vmm_id, dfm_id);
                        Object[] out_nt_data = IVNEngineerDB.getIdTypeFromNetworkName(csvRecord.get(inital_col).get(out_modal_size));
                        outObj.put("nt_id", out_nt_data[0] + "");
                        outObj.put("nt_type", out_nt_data[1] + "");
                        outObj.put("pdbgroup_id", pdbgroup_id + "");
                        outObj.put("vmm_id", vmm_id + "");
                        outArray.add(outObj);
                        out_modal_size++;
                    }
                    output.put("group_data", outArray);
                    output.put("signal", IVNEngineerDB.getIdFromSignalName(csvRecord.get(inital_col).get(modalList.size() * 2 + 4)) + "");
                    output.put("signal_type", "output");
                    cloned_data.add(output);
                    data.put("cloned_data", cloned_data);
                    inital_col++;
                }
            }
            if (data != null) {
                acbdata_list.add(data);
            }
        }
        acbObject.put("acbdata_list", acbdata_list);
        //System.out.println("ACB :" + acbObject);
        return acbObject;
    }

    public static JSONObject getModelVersionDetailsFromCSV(String filePath) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(filePath));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.EXCEL);
        List<CSVRecord> csvRecord = csvParser.getRecords();

        JSONObject modelVersionObject = new JSONObject();

        //String veh_ver = csvRecord.get(0).get(1);
        float vehicle_version = Float.parseFloat(csvRecord.get(0).get(1));
        String vehicle_name = csvRecord.get(0).get(3);
        //String ac_ver = csvRecord.get(0).get(5);
        float acb_version = Float.parseFloat(csvRecord.get(0).get(5));

        JSONObject modelversion = new JSONObject();
        int vehicleversion_id = VehicleversionDB.getIdFromVehicleVersionName(vehicle_version);
        int acbversion_id = ACBOwnerDB.getIdFromACBVersionName(acb_version);
        modelversion.put("vehicleversion", vehicleversion_id + "");
        modelversion.put("vehiclename", VehicleversionDB.getIdFromVehicleName(vehicle_name));
        modelversion.put("acbversion", acbversion_id + "");
        modelversion.put("status", false);

        modelVersionObject.put("modelversion", modelversion);
        modelVersionObject.put("button_type", "submit");

        List<String> ecuList = new ArrayList<>();
        for (int i = 1; i < csvRecord.get(2).size(); i++) {
            if (!csvRecord.get(1).get(i).isEmpty()) {
                ecuList.add(csvRecord.get(1).get(i));
            } else {
                break;
            }
        }

        JSONArray modeldata_list = new JSONArray();
        int initial_start = 1;
        for (int ecu = 0; ecu < ecuList.size(); ecu++) {
            int ecu_id = SystemOwnerDB.getIdFromECUName(ecuList.get(ecu));
            for (int i = 2; i < csvRecord.size(); i++) {
                JSONObject obj = new JSONObject();
                String modelName = csvRecord.get(i).get(0);
                int vmm_id = VehicleversionDB.getVehicleModelMappingId(vehicleversion_id, vehicleversion_id, VehicleversionDB.getIdFromVehicleModalName(modelName));
                int variant_id = SystemOwnerDB.getIdFromVariantName(csvRecord.get(i).get(initial_start));
                obj.put("vmm_id", vmm_id + "");
                obj.put("ecu_id", ecu_id + "");
                obj.put("variant_id", variant_id + "");
                modeldata_list.add(obj);
            }
            initial_start++;
        }

        modelVersionObject.put("modeldata_list", modeldata_list);
        return modelVersionObject;
    }

    public static JSONObject getSystemVersionDetailsFromCSV(String filePath) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(filePath));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.EXCEL);
        List<CSVRecord> csvRecord = csvParser.getRecords();

        JSONObject systemVersionObject = new JSONObject();

        //String veh_ver = csvRecord.get(0).get(1);
        float vehicle_version = Float.parseFloat(csvRecord.get(0).get(1));
        String vehicle_name = csvRecord.get(0).get(3);
        //String acb_ver = csvRecord.get(0).get(5);
        float acb_version = Float.parseFloat(csvRecord.get(0).get(5));
        String ecu = csvRecord.get(0).get(7);

        JSONObject systemversion = new JSONObject();
        int vehicleversion_id = VehicleversionDB.getIdFromVehicleVersionName(vehicle_version);
        int acbversion_id = ACBOwnerDB.getIdFromACBVersionName(acb_version);
        systemversion.put("vehicleversion", vehicleversion_id + "");
        systemversion.put("vehiclename", VehicleversionDB.getIdFromVehicleName(vehicle_name));
        systemversion.put("acbversion", acbversion_id + "");
        systemversion.put("ecu", SystemOwnerDB.getIdFromECUName(ecu) + "");
        systemversion.put("status", false);

        systemVersionObject.put("systemversion", systemversion);
        systemVersionObject.put("button_type", "submit");

        List<String> varList = new ArrayList<>();
        for (int i = 2; i < csvRecord.get(2).size(); i++) {
            if (!csvRecord.get(1).get(i).isEmpty()) {
                varList.add(csvRecord.get(1).get(i));
            } else {
                break;
            }
        }

        JSONArray systemdata_list = new JSONArray();
        int initial_start = 2;
        for (int var = 0; var < varList.size(); var++) {
            int variant_id = SystemOwnerDB.getIdFromVariantName(varList.get(var));
            for (int i = 2; i < csvRecord.size(); i++) {
                JSONObject obj = new JSONObject();
                String domainName = csvRecord.get(i).get(0);
                String featureName = csvRecord.get(i).get(1);
                int dfm_id = PDBVersionDB.getDomainFeatureMappingId(PDBVersionDB.getDomainFeatureId(domainName, featureName));
                obj.put("dfm_id", dfm_id + "");
                obj.put("variant_id", variant_id + "");
                obj.put("status", csvRecord.get(i).get(initial_start).toLowerCase());
                systemdata_list.add(obj);
            }
            initial_start++;
        }

        systemVersionObject.put("systemdata_list", systemdata_list);
        return systemVersionObject;
    }
}

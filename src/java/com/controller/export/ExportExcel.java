/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.export;

import com.model.acb_owner.ACBOwnerDB;
import com.model.acb_owner.ACBversion;
import com.model.ivn_engineer.IVNEngineerDB;
import com.model.ivn_supervisor.VehicleversionDB;
import com.model.pdb_owner.PDBVersionDB;
import com.model.system_owner.SystemOwnerDB;
import com.model.system_owner.Systemversion;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ETS-4
 */
public class ExportExcel {

    public static void systemReportExcel(int systemVersion_id, int ecu, File newFile) throws FileNotFoundException, IOException, SQLException {
        //Create blank workbook
        Workbook workbook = new XSSFWorkbook();

        Map<String, Object> loadSysver_data = SystemOwnerDB.LoadSystemPreviousversionData(new Systemversion(systemVersion_id));
        List<Map<String, Object>> systemVersion = (List<Map<String, Object>>) loadSysver_data.get("systemversion");
        List<Map<String, Object>> featureList = (List<Map<String, Object>>) loadSysver_data.get("feature_list");
        List<Map<String, Object>> ecu_variant_list = (List<Map<String, Object>>) loadSysver_data.get("ecu_variant_list");
        List<Map<String, Object>> systemdata_list = (List<Map<String, Object>>) loadSysver_data.get("systemdata_list");

        String vehicleVersion = VehicleversionDB.getVehicleVersionNameFromId(Integer.parseInt(systemVersion.get(0).get("vehicleversion").toString())) + "";
        int vehicleId = Integer.parseInt(systemVersion.get(0).get("vehiclename").toString());

        ACBversion acbver = new ACBversion(Integer.parseInt(systemVersion.get(0).get("acbversion").toString()));
        Map<String, Object> acbVersion_data = ACBOwnerDB.LoadACBPreviousVehicleversionData(acbver);
        List<Map<String, Object>> vehmod_map_result = (List<Map<String, Object>>) acbVersion_data.get("vehmod_map_result");
        List<Map<String, Object>> acb_inputsignal = (List<Map<String, Object>>) acbVersion_data.get("acb_inputsignal");
        List<Map<String, Object>> acb_outputsignal = (List<Map<String, Object>>) acbVersion_data.get("acb_outputsignal");
        List<Map<String, Object>> acbversion = (List<Map<String, Object>>) acbVersion_data.get("acbversion");
        Map<String, Object> pdb_map_result = (Map<String, Object>) acbVersion_data.get("pdb_map_result");
        List<Map<String, Object>> featuredetail_list = (List<Map<String, Object>>) pdb_map_result.get("featuredetail_list");

        //Create a new sheet
        Sheet sysVerSheet = workbook.createSheet(" System Version ");
        Sheet acbVerSheet = workbook.createSheet(" ACB Version ");

        Row headerDetails = sysVerSheet.createRow(0);
        headerDetails.createCell(0).setCellValue("System Version");
        headerDetails.createCell(1).setCellValue(SystemOwnerDB.getSystemVersionNameFromId(systemVersion_id));
        headerDetails.createCell(2).setCellValue("ACB Version");
        headerDetails.createCell(3).setCellValue(ACBOwnerDB.getACBVersionNameFromId(Integer.parseInt(systemVersion.get(0).get("acbversion").toString())) + "");
        headerDetails.createCell(4).setCellValue("Vehicle Version");
        headerDetails.createCell(5).setCellValue(vehicleVersion);
        headerDetails.createCell(6).setCellValue("Vehicle Name");
        headerDetails.createCell(7).setCellValue(VehicleversionDB.getVehicleNameFromId(vehicleId));

        int sys_rowcount = 2, initialSfea = 0;
        Row feature_version_details = sysVerSheet.createRow(sys_rowcount);
        feature_version_details.createCell(initialSfea++).setCellValue("Feature");

        List<String> ecu_variant_nameList = Arrays.asList(ecu_variant_list.get(0).get("variant_name").toString().split("\\s*,\\s*"));
        List<String> ecu_variant_idList = Arrays.asList(ecu_variant_list.get(0).get("variant_id").toString().split("\\s*,\\s*"));

        for (String vName : ecu_variant_nameList) {
            feature_version_details.createCell(initialSfea++).setCellValue(vName);
        }

        for (Map<String, Object> feature : featureList) {
            Row createFeature = sysVerSheet.createRow(++sys_rowcount);
            int initialCell = 0;
            createFeature.createCell(initialCell++).setCellValue(feature.get("featurename").toString());
            String dfm_id = feature.get("fid").toString();

            for (String vId : ecu_variant_idList) {
                List<Map<String, Object>> systemData = systemdata_list.stream()
                        .filter(m -> m.get("variant_id").toString().equals(vId) && m.get("dfm_id").toString().equals(dfm_id))
                        .collect(Collectors.toList());
                createFeature.createCell(initialCell++).setCellValue(systemData.get(0).get("status").toString());
            }
        }

        Row acbheaderDetails = acbVerSheet.createRow(0);
        acbheaderDetails.createCell(0).setCellValue("ACB Version");
        acbheaderDetails.createCell(1).setCellValue(ACBOwnerDB.getACBVersionNameFromId(Integer.parseInt(systemVersion.get(0).get("acbversion").toString())) + "");
        acbheaderDetails.createCell(2).setCellValue("ECU");
        acbheaderDetails.createCell(3).setCellValue(SystemOwnerDB.getECUNameFromId(ecu));
        acbheaderDetails.createCell(4).setCellValue("IVN Version");
        acbheaderDetails.createCell(5).setCellValue(IVNEngineerDB.getIVNVersionNameFromId(Integer.valueOf(acbversion.get(0).get("ivnversion").toString())) + "");
        acbheaderDetails.createCell(6).setCellValue("PDB Version");
        acbheaderDetails.createCell(7).setCellValue(PDBVersionDB.getPDBVersionNameFromId(Integer.valueOf(acbversion.get(0).get("pdbversion").toString())) + "");

        List<String> modelList = null;
        List<String> featureModelList = new ArrayList<>();
        featureModelList.add("Feature");
        featureModelList.add("Input Signal");
        featureModelList.add("Output Signal");

        for (Map<String, Object> vehicleModel : vehmod_map_result) {
            if (vehicleModel.get("vehicle_id").equals(vehicleId) && vehicleModel.get("versionname").equals(vehicleVersion)) {
                modelList = Arrays.asList(vehicleModel.get("modelname").toString().split("\\s*,\\s*"));
                break;
            }
        }

        int acb_rowcount = 2, acb_cell_initial = 0;
        Row feature_model_details = acbVerSheet.createRow(acb_rowcount);
        for (String aHeading : featureModelList) {
            feature_model_details.createCell(acb_cell_initial++).setCellValue(aHeading);
            for (String model : modelList) {
                feature_model_details.createCell(acb_cell_initial++).setCellValue(model);
            }
        }

        acb_rowcount++;
        for (Map<String, Object> feature : featuredetail_list) {
            Row feature_model_fill = acbVerSheet.createRow(acb_rowcount);
            String featureName = feature.get("featurename").toString();
            int featureId = PDBVersionDB.getFeatureId(featureName);
            List<String> statusList = Arrays.asList(feature.get("status").toString().split("\\s*,\\s*"));
            acb_cell_initial = 0;

            feature_model_fill.createCell(acb_cell_initial++).setCellValue(featureName);
            for (String status : statusList) {
                feature_model_fill.createCell(acb_cell_initial++).setCellValue(status);
            }

            List<Map<String, Object>> eachIFeatureList = acb_inputsignal.stream()
                    .filter(m -> (Integer.parseInt(m.get("fid").toString()) == featureId))
                    .collect(Collectors.toList());
            List<Object> inputSignalList = eachIFeatureList.stream().map(o -> o.get("input_signal_id"))
                    .distinct().collect(Collectors.toList());
            int inputSigLength = 1, newRowCellInp = modelList.size() + 1;
            List<Integer> inputSignalRowNoList = new ArrayList<>();

            for (Object inputSignal : inputSignalList) {
                List<Map<String, Object>> eachInpSig = acb_inputsignal.stream()
                        .filter(m -> m.get("input_signal_id").toString().equals(inputSignal) && (Integer.parseInt(m.get("fid").toString()) == featureId))
                        .collect(Collectors.toList());

                inputSignalRowNoList.add(acb_rowcount);
                if (inputSigLength == 1) {
                    feature_model_fill.createCell(acb_cell_initial++).setCellValue(IVNEngineerDB.getSignalNameFromId(Integer.valueOf(inputSignal.toString())));
                    for (Map<String, Object> network : eachInpSig) {
                        feature_model_fill.createCell(acb_cell_initial++).setCellValue(IVNEngineerDB.getNetworkNameFromId(Integer.valueOf(network.get("input_network_id").toString())));
                    }
                    inputSigLength++;
                    acb_rowcount++;
                } else if (inputSignalList.size() > 1 && inputSigLength <= inputSignalList.size()) {
                    Row inputSignalRow = acbVerSheet.createRow(acb_rowcount++);
                    inputSignalRow.createCell(newRowCellInp++).setCellValue(IVNEngineerDB.getSignalNameFromId(Integer.valueOf(inputSignal.toString())));
                    for (Map<String, Object> network : eachInpSig) {
                        inputSignalRow.createCell(newRowCellInp++).setCellValue(IVNEngineerDB.getNetworkNameFromId(Integer.valueOf(network.get("input_network_id").toString())));
                    }
                    inputSigLength++;
                }
            }

            List<Map<String, Object>> eachOFeatureList = acb_outputsignal.stream()
                    .filter(m -> (Integer.parseInt(m.get("fid").toString()) == featureId))
                    .collect(Collectors.toList());
            List<Object> ouputSignalList = eachOFeatureList.stream().map(o -> o.get("output_signal_id"))
                    .distinct().collect(Collectors.toList());
            int ouputSigLength = 1, newRowCellout = modelList.size() * 2 + 2, outputRowInitial = 0;

            for (Object outputSignal : ouputSignalList) {
                List<Map<String, Object>> eachOutSig = acb_outputsignal.stream()
                        .filter(m -> m.get("output_signal_id").toString().equals(outputSignal) && (Integer.parseInt(m.get("fid").toString()) == featureId))
                        .collect(Collectors.toList());
                Row outputSignalRow;

                if (outputRowInitial < inputSignalRowNoList.size()) {
                    outputSignalRow = acbVerSheet.getRow(inputSignalRowNoList.get(outputRowInitial++));
                } else {
                    outputSignalRow = acbVerSheet.createRow(acb_rowcount++);
                }

                if (ouputSigLength == 1) {
                    outputSignalRow.createCell(acb_cell_initial++).setCellValue(IVNEngineerDB.getSignalNameFromId(Integer.valueOf(outputSignal.toString())));
                    for (Map<String, Object> network : eachOutSig) {
                        outputSignalRow.createCell(acb_cell_initial++).setCellValue(IVNEngineerDB.getNetworkNameFromId(Integer.valueOf(network.get("output_network_id").toString())));
                    }
                    ouputSigLength++;
                } else if (ouputSignalList.size() > 1 && ouputSigLength <= ouputSignalList.size()) {
                    outputSignalRow.createCell(newRowCellout++).setCellValue(IVNEngineerDB.getSignalNameFromId(Integer.valueOf(outputSignal.toString())));
                    for (Map<String, Object> network : eachOutSig) {
                        outputSignalRow.createCell(newRowCellout++).setCellValue(IVNEngineerDB.getNetworkNameFromId(Integer.valueOf(network.get("output_network_id").toString())));
                    }
                    ouputSigLength++;
                }
            }

            acb_rowcount++;
        }

        if ((ecu_variant_nameList.size() + 1) >= 8) {
            IntStream.range(0, ecu_variant_nameList.size() + 1).forEach((columnIndex) -> sysVerSheet.autoSizeColumn(columnIndex));
        } else {
            IntStream.range(0, 7).forEach((columnIndex) -> sysVerSheet.autoSizeColumn(columnIndex));
        }

        if ((modelList.size() * 3 + 3) >= 8) {
            IntStream.range(0, modelList.size() * 3 + 3).forEach((columnIndex) -> acbVerSheet.autoSizeColumn(columnIndex));
        } else {
            IntStream.range(0, 7).forEach((columnIndex) -> acbVerSheet.autoSizeColumn(columnIndex));
        }

        //Write the workbook in file system
        FileOutputStream out = new FileOutputStream(newFile);

        workbook.write(out);
        out.close();
        System.out.println("Excel Exported successfully");
    }
}

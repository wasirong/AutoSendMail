package com.dhl.yxg.controllers;

import com.dhl.yxg.accessSqlUtil.DataBaseSearch;
import com.dhl.yxg.data.ExportData_412;
import com.dhl.yxg.data.ImportData_412;
import com.dhl.yxg.util.CreateWorkbook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Controller {

    @FXML
    public ComboBox MailTypeID;

    @FXML
    public DatePicker StartTimeID;

    @FXML
    public DatePicker EndTimeID;

    @FXML
    public Button CreateModelID;

    @FXML
    private void initialize() {
        ObservableList<String> observableList =
                FXCollections.observableArrayList(
                        "421出口数据",
                        "421进口数据",
                        "出口5i自助申报报告",
                        "货物14+在库天数",
                        "客联工作量统计");
        MailTypeID.setItems(observableList);
    }

    @FXML
    public void CreateEnclosure(ActionEvent actionEvent) {

        CreateWorkbook createWorkbook = new CreateWorkbook();

        DataBaseSearch dataBaseSearch = new DataBaseSearch();

        FileOutputStream out = null;

        XSSFWorkbook workbook = null;

        String m_currentType = MailTypeID.getSelectionModel().getSelectedItem().toString();

        File file = new File("C:\\Temp\\" + m_currentType + ".xlsx");

        if (file.exists()) {
            file.delete();
        }

        //将生成的Excel文件保存到本地
        try {
            switch (m_currentType) {
                case "421出口数据":
                    List<ExportData_412> dataListExport412 =
                            dataBaseSearch.GetExportData_412(StartTimeID.getValue().toString(), EndTimeID.getValue().toString());
                    workbook = createWorkbook.generateExcel_Export412(m_currentType, dataListExport412);
                    break;

                case "421进口数据":
                    List<ImportData_412> dataListImport412 =
                            dataBaseSearch.GetImportData_412(StartTimeID.getValue().toString(), EndTimeID.getValue().toString());
                    workbook = createWorkbook.generateExcel_Import412(m_currentType, dataListImport412);
                    break;

                case "出口5i自助申报报告":
                    break;

                case "货物14+在库天数":
                    break;

                case "客联工作量统计":
                    break;

            }

            out = new FileOutputStream(file);

            if (workbook != null)
            {
                //将工作薄写入文件输出流中
                workbook.write(out);

                //将工作薄写入文件输出流中
                workbook.write(out);
            }

            /* 文本文件输出流，释放资源 */
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

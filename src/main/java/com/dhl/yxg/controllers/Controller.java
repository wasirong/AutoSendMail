package com.dhl.yxg.controllers;

import com.dhl.yxg.accessSqlUtil.DataBaseSearch;
import com.dhl.yxg.data.ExportData_412;
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
//        DataBaseSearch dataBaseSearch = new DataBaseSearch();
//        ExportData_412 exportData_412 = dataBaseSearch.GetExportData_412(StartTimeID.getValue().toString(), EndTimeID.getValue().toString());
        File file = new File("C:\\Temp\\421出口数据.xlsx");

        if(file.exists())
        {
            file.delete();
        }

        XSSFWorkbook workbook;
    }
}

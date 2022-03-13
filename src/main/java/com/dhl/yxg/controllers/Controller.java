package com.dhl.yxg.controllers;

import com.dhl.yxg.accessSqlUtil.DataBaseSearch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

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
        DataBaseSearch dataBaseSearch = new DataBaseSearch();
        dataBaseSearch.GetExportData_412(StartTimeID.getValue().toString(), EndTimeID.getValue().toString());
    }
}

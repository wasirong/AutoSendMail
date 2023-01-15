package com.dhl.yxg.controllers;

import com.dhl.yxg.accessSqlUtil.DataBaseSearch;
import com.dhl.yxg.data.*;
import com.dhl.yxg.util.CreateWorkbook;
import com.dhl.yxg.util.GetConfig;
import com.dhl.yxg.util.SendEmailUtil;
import com.dhl.yxg.util.SendEmailUtilpsd;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import org.apache.commons.collections4.Get;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ini4j.Ini;
import org.ini4j.Wini;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

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
    public Button SendMailID;

    @FXML
    private Label ActionTarget;

    @FXML
    private void initialize() {
        ObservableList<String> observableList =
                FXCollections.observableArrayList(
                        "421出口数据",
                        "421进口数据",
                        "出口5i自助申报报告",
                        "换单放行数据",
                        "货物14+在库天数",
                        "客联工作量统计");
        MailTypeID.setItems(observableList);
    }

    EmailInfo emailInfo = new EmailInfo();

    @FXML
    public void CreateEnclosure(ActionEvent actionEvent) {
        try {

            ActionTarget.setText("文件作成开始");

            CreateWorkbook createWorkbook = new CreateWorkbook();

            DataBaseSearch dataBaseSearch = new DataBaseSearch();

            GetConfig getConfig = new GetConfig();

            FileOutputStream out = null;

            XSSFWorkbook workbook = null;

            String section = "";

            String m_currentType = MailTypeID.getSelectionModel().getSelectedItem().toString();

            emailInfo.setSubject(m_currentType);

            emailInfo.setFilePath("C:\\Temp\\" + m_currentType + ".xlsx");

            emailInfo.setHost(getConfig.GetConfigByKey("UserInfo", "host"));

            emailInfo.setPort(getConfig.GetConfigByKey("UserInfo", "port"));

            emailInfo.setFrom(getConfig.GetConfigByKey("UserInfo", "from"));

            emailInfo.setPassword(getConfig.GetConfigByKey("UserInfo", "password"));

            emailInfo.setNickname(getConfig.GetConfigByKey("UserInfo", "nickname"));

            emailInfo.setContent(getConfig.GetConfigByKey("UserInfo", "content"));

            File file = new File("C:\\Temp\\" + m_currentType + ".xlsx");

            if (file.exists()) {
                file.delete();
            }

            //将生成的Excel文件保存到本地

            switch (m_currentType) {
                case "421出口数据":
                    List<ExportData_412> dataListExport412 =
                            dataBaseSearch.GetExportData_412(StartTimeID.getValue().toString(), EndTimeID.getValue().toString());
                    workbook = createWorkbook.generateExcel_Export412(m_currentType, dataListExport412);

                    section = "Export412";

                    break;

                case "421进口数据":
                    List<ImportData_412> dataListImport412 =
                            dataBaseSearch.GetImportData_412(StartTimeID.getValue().toString(), EndTimeID.getValue().toString());
                    workbook = createWorkbook.generateExcel_Import412(m_currentType, dataListImport412);

                    section = "Import412";

                    break;

                case "出口5i自助申报报告":
                    List<Export_Report_5i> dataListExportReport_5i = dataBaseSearch.GetExportReport5i(StartTimeID.getValue().toString(), EndTimeID.getValue().toString());
                    workbook = createWorkbook.generateExcel_ExportReport_5i(m_currentType, dataListExportReport_5i);

                    section = "ExportReport_5i";

                    break;

                case "换单放行数据":
                    List<ReplacementReleaseData> dataListReplacementRelease = dataBaseSearch.GetReplacementRelease(StartTimeID.getValue().toString(), EndTimeID.getValue().toString());
                    workbook = createWorkbook.generateExcel_ReplacementRelease(m_currentType, dataListReplacementRelease);

                    section = "ReplacementRelease";

                    break;

                case "货物14+在库天数":
                    List<DaysOFGoodsInWarehouse> daysOFGoodsInWarehouses = dataBaseSearch.GetDaysOFGoodsInWarehouses(StartTimeID.getValue().toString(), EndTimeID.getValue().toString());
                    workbook = createWorkbook.generateExcel_DaysOFGoodsInWarehouse(m_currentType, daysOFGoodsInWarehouses);

                    section = "Export412";

                    break;

                case "客联工作量统计":
                    List<ReceivingOrderData> receivingOrderDataList = dataBaseSearch.GetReceivingOrderData(StartTimeID.getValue().toString(), EndTimeID.getValue().toString());

                    List<TransFerOrderData> transFerOrderDataList = dataBaseSearch.GetTransFerOrderData(StartTimeID.getValue().toString(), EndTimeID.getValue().toString());
/*
                    //region TestCode Receive Data
                    List<ReceivingOrderData> receivingOrderDataList = new ArrayList<ReceivingOrderData>();
                    receivingOrderDataList.add(new ReceivingOrderData("6830289292", "D", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8636972923", "B", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8286295894", "D", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8286295986", "D", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6376541872", "D", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8286295905", "D", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1762808633", "D", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8286295975", "D", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9859246515", "C", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2308246360", "D", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9859278542", "D", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8682392563", "D", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4995793283", "D", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3177002102", "D", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2124806666", "D", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1890620583", "L", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3818687213", "C", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2828247052", "D", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6830301520", "B", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3700096304", "D", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1890491831", "L", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8286295942", "D", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8682374691", "D", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4995794355", "D", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3147541596", "D", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3700057023", "D", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3382062202", "D", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1905339822", "D", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2996435735", "D", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9160733584", "L", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1890651055", "D", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5987897613", "D", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2229582961", "L", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1905088964", "C", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7951191754", "C", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3173617856", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7924470761", "D", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7766892954", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5590541725", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8514220271", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6109562966", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5874722420", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6283728496", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6973379895", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4606154280", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5379420605", "D", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2008659601", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4094271156", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2498643420", "D", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4606151200", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6830024725", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4606154711", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6830236291", "D", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6320246004", "D", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4606154770", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6320246586", "D", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2724455591", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4606154195", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8196826315", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5379426312", "D", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6973377924", "D", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4606154346", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6109565210", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8437158951", "C", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3700095615", "D", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4606154243", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4268580492", "D", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6325660456", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6109564764", "D", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8236793751", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6325660563", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2724455521", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4424390622", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4606154394", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1762863690", "B", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5410921283", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2515682260", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2327929682", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6320247485", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7302464993", "D", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4606154136", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4606154663", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4606154652", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7299045493", "D", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4606154733", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6320247614", "D", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6361378203", "D", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1764664366", "C", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1890353920", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6320247953", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7302465203", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8268149805", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8197129570", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4866263010", "D", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8236794510", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9160503984", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6109564683", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7897913951", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6819996363", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4565759612", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8731308821", "D", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8147063385", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7677328665", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9603202733", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6376912286", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2568936705", "D", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6376912441", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8300036065", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6196317971", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2486312603", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3700121331", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6362242036", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1262380512", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1557892232", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3381821461", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6273523804", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5874775336", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3186762773", "C", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9859004385", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9066896893", "C", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8528249951", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1762999383", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2308435695", "D", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3208465514", "L", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5411013064", "L", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4966007782", "L", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1320830490", "C", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8437122315", "P", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9160607643", "D", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7369151311", "L", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8437135313", "L", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4917785493", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1762919270", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8197069565", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4196513952", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8437125966", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8514209771", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3208415464", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6376602105", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4866156993", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4093998112", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6647691853", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8295197890", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9160533351", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3495793033", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9603193040", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5573217204", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5581544732", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5581595132", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2460268856", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6427807002", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8373065746", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6003902586", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4055612293", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5581536240", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9859206280", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2308373760", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1890793785", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1581046880", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5581551953", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6003826065", "P", "工作完成", "2022/2/24  8:35:59", "YUXCHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4095178404", "C", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4866190431", "C", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5699099195", "D", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5699099184", "D", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7579125304", "D", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5645732260", "D", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7402049454", "D", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1847439215", "D", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2308465913", "D", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8437162926", "C", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1762950394", "C", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1320830475", "D", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1905012056", "D", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3571740476", "D", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8197160974", "D", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2996421595", "C", "工作完成", "2022/2/24  8:35:59", "CHUNYLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8197010544", "C", "工作完成", "2022/2/24  8:35:59", "CHUNYLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2308379275", "D", "工作完成", "2022/2/24  8:35:59", "CHUNYLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8497600554", "D", "工作完成", "2022/2/24  8:35:59", "CHUNYLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5094651100", "B", "工作完成", "2022/2/24  8:35:59", "CHUNYLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4609798211", "C", "工作完成", "2022/2/24  8:35:59", "CHUNYLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1890621541", "L", "工作完成", "2022/2/24  8:35:59", "CHUNYLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2996432095", "D", "工作完成", "2022/2/24  8:35:59", "CHUNYLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3382067780", "L", "工作完成", "2022/2/24  8:35:59", "CHUNYLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3190022883", "D", "工作完成", "2022/2/24  8:35:59", "CHUNYLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8437047872", "D", "工作完成", "2022/2/24  8:35:59", "CHUNYLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1308270946", "D", "工作完成", "2022/2/24  8:35:59", "CHUNYLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2308369070", "D", "工作完成", "2022/2/24  8:35:59", "CHUNYLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8437151564", "D", "工作完成", "2022/2/24  8:35:59", "CHUNYLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2996421002", "D", "工作完成", "2022/2/24  8:35:59", "CHUNYLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9859000686", "D", "工作完成", "2022/2/24  8:35:59", "CHUNYLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2308371645", "D", "工作完成", "2022/2/24  8:35:59", "CHUNYLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3381716155", "C", "工作完成", "2022/2/24  8:35:59", "CHUNYLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1762782302", "D", "工作完成", "2022/2/24  8:35:59", "CHUNYLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7232553495", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705860964", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705267062", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705602200", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705465291", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705721550", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705432446", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705612136", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705613820", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705363264", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705668663", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705477810", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705650533", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705488726", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7766828193", "D", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2498522876", "C", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3208481776", "C", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8514202480", "D", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9160477292", "L", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3700063813", "C", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7157683400", "L", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8437123682", "D", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2865476740", "D", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705325766", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705307581", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705500081", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705428224", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705382175", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705373451", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705335721", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705797931", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7056335440", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7056349823", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705582622", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705711595", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4661360325", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705583996", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705535696", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705478591", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2612859325", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7232558992", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705324672", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705675873", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705482662", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705420174", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705667296", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9412239830", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7056333874", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705812454", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1831672242", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705705236", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7056308943", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9412240751", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705750084", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705744845", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705670122", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9705713942", "P", "工作完成", "2022/2/24  8:35:59", "PKHZHANGDLC", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2647374516", "D", "工作完成", "2022/2/24  8:35:59", "HANL", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8564482441", "Y", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6927517925", "D", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1343017141", "Y", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6927517391", "D", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6927518183", "D", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7967035053", "L", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4213440394", "Y", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6376856555", "L", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6927518205", "D", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6927517402", "D", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1588351645", "L", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6927518194", "C", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6927517365", "C", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7112874790", "L", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6927518555", "D", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8564481704", "Y", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6927517903", "D", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6927517413", "D", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6927517380", "D", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6927517914", "D", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2990569120", "L", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5410896945", "D", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2578375516", "L", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3190033394", "L", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6103481705", "C", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3782698673", "D", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1197141584", "C", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5094837915", "D", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5480443555", "L", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7921482726", "L", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3189785222", "B", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6206709460", "L", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3495918742", "L", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2308492465", "D", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2498703502", "L", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9160407572", "L", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1762778566", "L", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7443778053", "L", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3782698662", "D", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6103481716", "C", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1762885062", "L", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9859187122", "D", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4748950080", "L", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5987937933", "L", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4995791861", "L", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6213922875", "L", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8616229031", "L", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2498636921", "D", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1903163076", "C", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5590559870", "L", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1904920065", "D", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6273551841", "D", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3382075362", "C", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2498611496", "D", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8437009346", "D", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7698671492", "D", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7809375136", "L", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4213650394", "C", "工作完成", "2022/2/24  8:35:59", "WANGKAT", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3381979285", "C", "工作完成", "2022/2/24  8:35:59", "WANGKAT", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8437126460", "C", "工作完成", "2022/2/24  8:35:59", "WANGKAT", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1338006110", "D", "工作完成", "2022/2/24  8:35:59", "WANGKAT", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3381961925", "L", "工作完成", "2022/2/24  8:35:59", "WANGKAT", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5410969911", "C", "工作完成", "2022/2/24  8:35:59", "WANGKAT", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1904891542", "C", "工作完成", "2022/2/24  8:35:59", "WANGKAT", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7766780442", "L", "工作完成", "2022/2/24  8:35:59", "WANGKAT", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8197015536", "L", "工作完成", "2022/2/24  8:35:59", "WANGKAT", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9859249820", "L", "工作完成", "2022/2/24  8:35:59", "WANGKAT", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5721205593", "L", "工作完成", "2022/2/24  8:35:59", "WANGKAT", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4094149441", "C", "工作完成", "2022/2/24  8:35:59", "WANGKAT", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5375205415", "L", "工作完成", "2022/2/24  8:35:59", "WANGKAT", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5954579444", "D", "工作完成", "2022/2/24  8:35:59", "CHUNYULI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1890683410", "D", "工作完成", "2022/2/24  8:35:59", "CHUNYULI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9293753711", "L", "工作完成", "2022/2/24  8:35:59", "CHUNYULI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7010079274", "C", "工作完成", "2022/2/24  8:35:59", "CHUNYULI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1816539933", "D", "工作完成", "2022/2/24  8:35:59", "CHUNYULI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1925997570", "C", "工作完成", "2022/2/24  8:35:59", "CHUNYULI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5480437642", "C", "工作完成", "2022/2/24  8:35:59", "CHUNYULI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9160388974", "D", "工作完成", "2022/2/24  8:35:59", "CHUNYULI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1904965366", "D", "工作完成", "2022/2/24  8:35:59", "CHUNYULI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9160442233", "D", "工作完成", "2022/2/24  8:35:59", "CHUNYULI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2498597426", "C", "工作完成", "2022/2/24  8:35:59", "CHUNYULI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3769014830", "C", "工作完成", "2022/2/24  8:35:59", "CHUNYULI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3382005970", "D", "工作完成", "2022/2/24  8:35:59", "CHUNYULI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4462979463", "L", "工作完成", "2022/2/24  8:35:59", "CHUNYULI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3495842781", "B", "工作完成", "2022/2/24  8:35:59", "CHUNYULI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7766782155", "C", "工作完成", "2022/2/24  8:35:59", "CHUNYULI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4866202460", "C", "工作完成", "2022/2/24  8:35:59", "CHUNYULI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2989670644", "C", "工作完成", "2022/2/24  8:35:59", "CHUNYULI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5954579805", "L", "工作完成", "2022/2/24  8:35:59", "CHUNYULI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5573234494", "B", "工作完成", "2022/2/24  8:35:59", "CHUNYULI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1890660822", "D", "工作完成", "2022/2/24  8:35:59", "CHUNYULI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4094177743", "L", "工作完成", "2022/2/24  8:35:59", "CHUNYULI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2498681555", "L", "工作完成", "2022/2/24  8:35:59", "WANGKAT", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2926680632", "L", "工作完成", "2022/2/24  8:35:59", "WANGKAT", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8437126972", "L", "工作完成", "2022/2/24  8:35:59", "WANGKAT", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8436972935", "L", "工作完成", "2022/2/24  8:35:59", "WANGKAT", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3381857146", "D", "工作完成", "2022/2/24  8:35:59", "WANGKAT", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1890764772", "C", "工作完成", "2022/2/24  8:35:59", "WANGKAT", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9066800654", "L", "工作完成", "2022/2/24  8:35:59", "WANGKAT", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3381844804", "C", "工作完成", "2022/2/24  8:35:59", "WANGKAT", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1762824066", "C", "工作完成", "2022/2/24  8:35:59", "WANGKAT", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1762870432", "D", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4196423825", "C", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6376919824", "C", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3495805434", "C", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7077263701", "C", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6273540976", "D", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6965825436", "D", "工作完成", "2022/2/24  8:35:59", "SHUAIWAN", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6984201593", "D", "工作完成", "2022/2/24  8:35:59", "SHUAIWAN", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1890826324", "D", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1762961314", "B", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6567426471", "B", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6048877531", "D", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("7521849871", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5743535070", "D", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("9603172460", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("KE20220223", "D", "工作完成", "2022/2/24  8:35:59", "SHUAIWAN", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("KR20220223", "D", "工作完成", "2022/2/24  8:35:59", "SHUAIWAN", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("JL20220222", "D", "工作完成", "2022/2/24  8:35:59", "SHUAIWAN", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("KE20220222", "D", "工作完成", "2022/2/24  8:35:59", "SHUAIWAN", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("JL20220223", "D", "工作完成", "2022/2/24  8:35:59", "SHUAIWAN", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3769211364", "C", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4866302033", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5377422584", "D", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6320193563", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6208543622", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("2498690515", "Y", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("5743680445", "B", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("6927516971", "C", "工作完成", "2022/2/24  8:35:59", "CHMZHOU", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("4451421450", "X", "工作完成", "2022/2/24  8:35:59", "ZQHE", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1745693095", "X", "工作完成", "2022/2/24  8:35:59", "JUJIANG", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("3208586846", "L", "工作完成", "2022/2/24  8:35:59", "JINXLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8497551440", "C", "工作完成", "2022/2/24  8:35:59", "CHUNYLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8497551145", "D", "工作完成", "2022/2/24  8:35:59", "CHUNYLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("1741478583", "D", "工作完成", "2022/2/24  8:35:59", "CHUNYLI", "流程:CS理单"));
                    receivingOrderDataList.add(new ReceivingOrderData("8497551661", "C", "工作完成", "2022/2/24  8:35:59", "CHUNYLI", "流程:CS理单"));
                    //endregion

                    //region TestCode TransFerOrder Data
                    List<TransFerOrderData> transFerOrderDataList = new ArrayList<TransFerOrderData>();
                    transFerOrderDataList.add(new TransFerOrderData("6819932674", "P", "工作完成", "22:59", "YUYZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704456510", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704529693", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704603112", "P", "工作完成", "22:59", "RONGXSUI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8013747512", "P", "工作完成", "22:59", "YUYZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703286961", "P", "工作完成", "22:59", "RONGXSUI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705051193", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705575390", "P", "工作完成", "22:59", "RONGXSUI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704177615", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704900251", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7817961841", "C", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4420240716", "D", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5410984331", "D", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4420240716", "D", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5410984331", "D", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9160733584", "L", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1905088964", "C", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2229582961", "L", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704109262", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9607124376", "P", "工作完成", "22:59", "YUXCHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705465291", "P", "工作完成", "22:59", "PKHZHANGDLC", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705477810", "P", "工作完成", "22:59", "PKHZHANGDLC", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7232553495", "P", "工作完成", "22:59", "PKHZHANGDLC", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705612136", "P", "工作完成", "22:59", "PKHZHANGDLC", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705613820", "P", "工作完成", "22:59", "PKHZHANGDLC", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705363264", "P", "工作完成", "22:59", "PKHZHANGDLC", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705267062", "P", "工作完成", "22:59", "PKHZHANGDLC", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705668663", "P", "工作完成", "22:59", "PKHZHANGDLC", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705432446", "P", "工作完成", "22:59", "PKHZHANGDLC", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705721550", "P", "工作完成", "22:59", "PKHZHANGDLC", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705650533", "P", "工作完成", "22:59", "PKHZHANGDLC", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705488726", "P", "工作完成", "22:59", "PKHZHANGDLC", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705602200", "P", "工作完成", "22:59", "PKHZHANGDLC", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705860964", "P", "工作完成", "22:59", "PKHZHANGDLC", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8937997902", "L", "工作完成", "22:59", "DLJMA", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705180063", "P", "工作完成", "22:59", "YUYZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704032332", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6394497432", "C", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1695962645", "C", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3249432852", "P", "工作完成", "22:59", "YUXCHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7606433446", "P", "工作完成", "22:59", "YUXCHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704277671", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6611621391", "L", "工作完成", "22:59", "YUXCHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704756714", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704029985", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4684268864", "P", "工作完成", "22:59", "YUXCHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704103382", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7443778053", "L", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2498703502", "L", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3190033394", "L", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2990569120", "L", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5590559870", "L", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2578375516", "L", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6206709460", "L", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1762885062", "L", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5987937933", "L", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8616229031", "L", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5480443555", "L", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4995791861", "L", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7809375136", "L", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7123730795", "P", "工作完成", "22:59", "YUYZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704922080", "P", "工作完成", "22:59", "RONGXSUI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6853428326", "D", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9410702766", "D", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704690811", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9498552663", "D", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6819941472", "D", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7391133256", "D", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1340113714", "D", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8910118851", "D", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4634506240", "D", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7077454565", "D", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6175222966", "D", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1340132345", "D", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4984058881", "D", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9930503450", "P", "工作完成", "22:59", "YUYZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704713476", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704850330", "P", "工作完成", "22:59", "YUYZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8577581681", "D", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2498522876", "C", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1307991775", "Y", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703217996", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7912881874", "C", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3381961925", "L", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7766780442", "L", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703975105", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4609798211", "C", "工作完成", "22:59", "CHUNYLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4995793283", "D", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3287778056", "D", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1762808633", "D", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4995794355", "D", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2124806666", "D", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3700057023", "D", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9859249820", "L", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704932731", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4748950080", "L", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704240766", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9066896893", "C", "工作完成", "22:59", "CHUNYLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9160407572", "L", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3812123195", "C", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704554834", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8197015536", "L", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6780381156", "C", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5100677676", "C", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4866202460", "C", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704154095", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8437135313", "L", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3495918742", "L", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7369151311", "L", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703819101", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703311586", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1762950394", "C", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9160388974", "D", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704380626", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5699099195", "D", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7056318220", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9066800654", "L", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8436972935", "L", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4866190431", "C", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8437126972", "L", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6984201593", "D", "工作完成", "22:59", "SHUAIWAN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6965825436", "D", "工作完成", "22:59", "SHUAIWAN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1890491831", "L", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1890620583", "L", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1308270946", "D", "工作完成", "22:59", "CHUNYLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9787575954", "P", "工作完成", "22:59", "YUYZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2926680632", "L", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2498681555", "L", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4289212082", "P", "工作完成", "22:59", "DLJMA", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6927517306", "D", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6927516993", "D", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3504321026", "X", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3025113000", "X", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705326256", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1343017141", "Y", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8564482441", "Y", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4213440394", "Y", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8564481704", "Y", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3571740476", "D", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7112874790", "L", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6376856555", "L", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1588351645", "L", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7967035053", "L", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6611713124", "L", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2064957926", "L", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9859000686", "D", "工作完成", "22:59", "CHUNYLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2308465913", "D", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6561704380", "D", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7305553323", "D", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7523056144", "X", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1581667544", "X", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6033469304", "X", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6384501690", "X", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8437162926", "C", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705437044", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8437151564", "D", "工作完成", "22:59", "CHUNYLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7164925176", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4095178404", "C", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9766136951", "L", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6103481705", "C", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7232551896", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5699099184", "D", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2153942486", "P", "工作完成", "22:59", "YUYZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8697219580", "P", "工作完成", "22:59", "YUYZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7660901006", "D", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5410902954", "P", "工作完成", "22:59", "DLJMA", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4920651094", "P", "工作完成", "22:59", "DLJMA", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3208465514", "L", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4966007782", "L", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9859187122", "D", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5411013064", "L", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1833790884", "C", "工作完成", "22:59", "CHUNYLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1762778566", "L", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7921482726", "L", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9160477292", "L", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9073005900", "D", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6016303171", "D", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6318297635", "D", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2612848744", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5410899815", "B", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4034590372", "D", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9234760765", "D", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9073122380", "F", "工作完成", "22:59", "CHUNYLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2724455591", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6325660456", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6109565210", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6109564683", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2515682260", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4094271156", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6320247485", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6320247953", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4606154136", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5410921283", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2008659601", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9160503984", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4606154280", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2724455521", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6830024725", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8236793751", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4424390622", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4606154711", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4606154770", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7302465203", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4606154652", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7766892954", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8196826315", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2327929682", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4606154733", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7766709344", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6325660563", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4606154243", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3173617856", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8268149805", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4606154663", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4606154346", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4606151200", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4606153414", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4606154195", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4606154394", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8236794510", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8197129570", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4554796002", "Y", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8146800992", "D", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3700063813", "C", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3076273163", "L", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8514209771", "P", "工作完成", "22:59", "YUXCHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1925997570", "C", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9845740553", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8117438125", "D", "工作完成", "22:59", "CHUNYLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9293753711", "L", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2612848744", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1890660822", "D", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6588125633", "F", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5290525995", "L", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1890683410", "D", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7431506364", "R", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3402223064", "P", "工作完成", "22:59", "YUXCHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4420557002", "D", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6927514650", "D", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4171465642", "D", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6273551841", "D", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8437009346", "D", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6273540976", "D", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1617554632", "D", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4634511350", "D", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7164925633", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7766769404", "C", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7232545541", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9160442233", "D", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5410854886", "D", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3744333062", "C", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705192862", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4317723421", "D", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1910578456", "D", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2308435695", "D", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704503421", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7157683400", "L", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1816539933", "D", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705501820", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704912615", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1903239715", "D", "工作完成", "22:59", "CHUNYLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6376507056", "D", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6174525755", "D", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8437126460", "C", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7232503552", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7766782155", "C", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7232503320", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4420385336", "C", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5721205593", "L", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1461360552", "C", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2612822354", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3871240800", "P", "工作完成", "22:59", "YUYZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703944740", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1904891542", "C", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704090782", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9930503800", "P", "工作完成", "22:59", "YUYZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9930503472", "P", "工作完成", "22:59", "YUYZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703806162", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6014368743", "P", "工作完成", "22:59", "YUYZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6746486353", "P", "工作完成", "22:59", "YUYZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1761946653", "P", "工作完成", "22:59", "YUYZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6376912441", "P", "工作完成", "22:59", "YUYZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6376912286", "P", "工作完成", "22:59", "YUYZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705107580", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2856092912", "P", "工作完成", "22:59", "YUYZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7660744276", "P", "工作完成", "22:59", "YUYZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704558850", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9930503100", "P", "工作完成", "22:59", "YUYZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3372571193", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2612833672", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3372572280", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3372571801", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1655719273", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7232540814", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705353954", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3372571160", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7056235900", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704768743", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3372572291", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704949936", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7232541411", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704746505", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9845728513", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704586802", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2612833333", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704268534", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2612838981", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704762093", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704484731", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704929360", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704539633", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705104566", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705469200", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7056206382", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704112950", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704932145", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704911495", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704690785", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705096903", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704890801", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705297932", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705046492", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705105502", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704773956", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704924880", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704698986", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704919766", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704996022", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7164924303", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2612826731", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704840762", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7056196571", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704826342", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704793405", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705154384", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705072860", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704860266", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705070782", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704910283", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705099040", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704836643", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7056200664", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704107626", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703983995", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2612827081", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705060536", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704480962", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704656345", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704727373", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704919464", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704422604", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704331906", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704542820", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704034491", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704623913", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705086495", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704638845", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705074540", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704927223", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704181583", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704719673", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703896591", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703588823", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705038652", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705046750", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7056220581", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704782264", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703560624", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704676785", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703930703", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703602635", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705024932", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705053116", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704623596", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703959521", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703537583", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705141784", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704559432", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704561904", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703704010", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705001213", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703926610", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703312485", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704541302", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7056227581", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703595646", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704652694", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703745800", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705084255", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704516533", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704463786", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704226232", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705054693", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703834696", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704451072", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703936185", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704255341", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704529984", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704313754", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703637554", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704191696", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703966031", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704675002", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703985373", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704396763", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704389542", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704409190", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703708781", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703732953", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704269433", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7056167591", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703984463", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704551640", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704202620", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703811821", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704289932", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703978653", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704354984", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703794903", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704274635", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704577783", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704296545", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703781920", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703957675", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703647402", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703908745", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704153502", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704399342", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703961186", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704353772", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703803664", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704087212", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703956614", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704251653", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704151041", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704076885", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703887255", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704300664", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703925206", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704384465", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704076141", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704083896", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703952064", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704229662", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704187695", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704299511", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704499884", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704126320", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704675886", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704367341", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704303792", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704305144", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704581423", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704373626", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704302473", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704244141", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703805860", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704246506", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704486901", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704253565", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704206912", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9412231452", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704153476", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704293616", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8300036065", "P", "工作完成", "22:59", "YUYZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9412231452", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8617959674", "P", "工作完成", "22:59", "DLJMA", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5581595132", "P", "工作完成", "22:59", "DLJMA", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8209638242", "P", "工作完成", "22:59", "DLJMA", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1890793785", "P", "工作完成", "22:59", "DLJMA", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1559468153", "C", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1904920065", "D", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6959647984", "C", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5410969911", "C", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9859206280", "P", "工作完成", "22:59", "DLJMA", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704244141", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1762782302", "D", "工作完成", "22:59", "CHUNYLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1523307015", "L", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4213650394", "C", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2989670644", "C", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705099040", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5411012213", "C", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4565759612", "P", "工作完成", "22:59", "YUYZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7521934836", "D", "工作完成", "22:59", "CHUNYLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3381979285", "C", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1816538264", "D", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3744505951", "C", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705104566", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6587720613", "D", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3208404636", "D", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3382062202", "D", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7232509922", "P", "工作完成", "22:59", "RONGXSUI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6927517380", "D", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3700095615", "D", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5379426312", "D", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6320246586", "D", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7299045493", "D", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705418590", "P", "工作完成", "22:59", "RONGXSUI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4094149441", "C", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704723055", "P", "工作完成", "22:59", "RONGXSUI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7305361405", "P", "工作完成", "22:59", "YUYZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6103481716", "C", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2623204754", "D", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3190109344", "D", "工作完成", "22:59", "CHUNYLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9238684151", "D", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8575029680", "F", "工作完成", "22:59", "YUYZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705138251", "P", "工作完成", "22:59", "RONGXSUI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8244802532", "D", "工作完成", "22:59", "CHUNYLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5840076760", "B", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7478737173", "P", "工作完成", "22:59", "YUYZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704541302", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3414699562", "D", "工作完成", "22:59", "CHUNYLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704181583", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3782698673", "D", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705406826", "P", "工作完成", "22:59", "RONGXSUI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3414699621", "D", "工作完成", "22:59", "CHUNYLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704451072", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6927517402", "D", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6927518183", "D", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6927517925", "D", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6927517903", "D", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6830201324", "D", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6927518205", "D", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6927517914", "D", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6927518555", "D", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6927517413", "D", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9787024365", "R", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8286295942", "D", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6830289292", "D", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8286295986", "D", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8682374691", "D", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2828247052", "D", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8286295975", "D", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8682392563", "D", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704782264", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6927516982", "B", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6361378203", "D", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4213411182", "D", "工作完成", "22:59", "CHUNYLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2498597426", "C", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6927514635", "C", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4920719941", "C", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4980649623", "P", "工作完成", "22:59", "YUYZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5006961982", "R", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5375205415", "L", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6647605440", "L", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7056238405", "P", "工作完成", "22:59", "RONGXSUI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4980666880", "P", "工作完成", "22:59", "YUYZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5411011115", "C", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6376919824", "C", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703602635", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704373626", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6016206685", "P", "工作完成", "22:59", "YUXCHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4980870403", "L", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4196423825", "C", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704425231", "P", "工作完成", "22:59", "RONGXSUI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5954579805", "L", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3190022883", "D", "工作完成", "22:59", "CHUNYLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703803664", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704676450", "P", "工作完成", "22:59", "RONGXSUI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1903163076", "C", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703805860", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4462979463", "L", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703708781", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7232541411", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703834696", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2865476740", "D", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704405771", "P", "工作完成", "22:59", "RONGXSUI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703957675", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704244572", "P", "工作完成", "22:59", "RONGXSUI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7521734172", "D", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1890764772", "C", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2623204754", "D", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2865476740", "D", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705084690", "P", "工作完成", "22:59", "RONGXSUI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704300664", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704652694", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6048748543", "L", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5954579444", "D", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3188019925", "D", "工作完成", "22:59", "CHUNYLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1762824066", "C", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3782698662", "D", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3381844804", "C", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7766825043", "P", "工作完成", "22:59", "YUXCHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704676973", "P", "工作完成", "22:59", "RONGXSUI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703312485", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7237849824", "P", "工作完成", "22:59", "YUXCHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3402244683", "C", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705427966", "P", "工作完成", "22:59", "RONGXSUI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1656300063", "D", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703887255", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4917785493", "P", "工作完成", "22:59", "YUXCHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5840075931", "C", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704092145", "P", "工作完成", "22:59", "RONGXSUI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705072860", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6853888215", "D", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6973377924", "D", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3402315501", "D", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1890651055", "D", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2498643420", "D", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6174334003", "D", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2996435735", "D", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704577783", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6570118332", "P", "工作完成", "22:59", "YUXCHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704724551", "P", "工作完成", "22:59", "RONGXSUI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6223594134", "P", "工作完成", "22:59", "DLJMA", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704529984", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705503242", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5094778824", "D", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3540841032", "P", "工作完成", "22:59", "YUXCHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7164925176", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5684774222", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2612852642", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704556816", "P", "工作完成", "22:59", "RONGXSUI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8197069565", "P", "工作完成", "22:59", "YUXCHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704944115", "P", "工作完成", "22:59", "RONGXSUI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9818864016", "D", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7766637502", "C", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705037926", "P", "工作完成", "22:59", "RONGXSUI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705349522", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705392992", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705396761", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705256680", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704861880", "P", "工作完成", "22:59", "RONGXSUI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4328374643", "C", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3208336445", "L", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6720335845", "P", "工作完成", "22:59", "DLJMA", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6719666483", "P", "工作完成", "22:59", "DLJMA", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703835945", "P", "工作完成", "22:59", "RONGXSUI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704927223", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704623596", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3402318640", "D", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704690785", "P", "工作完成", "22:59", "HANL", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705182362", "P", "工作完成", "22:59", "RONGXSUI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704654411", "P", "工作完成", "22:59", "RONGXSUI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9845728793", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705590882", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704663043", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7164926230", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705427480", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704383765", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705318641", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705494050", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9703828411", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704948234", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7056299810", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705210255", "P", "工作完成", "22:59", "YUXIN", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6927516864", "D", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6927514333", "D", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9160533351", "P", "工作完成", "22:59", "YUXCHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3381857146", "D", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4213433140", "C", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9704709976", "P", "工作完成", "22:59", "RONGXSUI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2646979031", "P", "工作完成", "22:59", "DLJMA", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5743680445", "B", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7661006360", "C", "工作完成", "22:59", "CHMZHOU", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5695893313", "P", "工作完成", "22:59", "DLJMA", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3186762773", "C", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1461518774", "C", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("8447551906", "L", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9668443094", "F", "工作完成", "22:59", "YUXCHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1903216965", "D", "工作完成", "22:59", "CHUNYLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6959632746", "C", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4451335980", "C", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2989774620", "D", "工作完成", "22:59", "CHUNYLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7898465035", "P", "工作完成", "22:59", "DLJMA", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6109564764", "D", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6320246004", "D", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7302464993", "D", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1933526022", "D", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5379420605", "D", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9761534576", "P", "工作完成", "22:59", "DLJMA", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3208586846", "L", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9705113655", "P", "工作完成", "22:59", "DLJMA", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6320247614", "D", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6208543622", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9603172460", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4866302033", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2498690515", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7521849871", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6320193563", "Y", "工作完成", "22:59", "JINXLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2865725936", "C", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9410674011", "D", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("9161087401", "R", "工作完成", "22:59", "CHUNYULI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7983397030", "C", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7922670324", "D", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7913117612", "D", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5410896945", "D", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5734501953", "D", "工作完成", "22:59", "ZQHE", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7912967226", "D", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3382067780", "L", "工作完成", "22:59", "CHUNYLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1890621541", "L", "工作完成", "22:59", "CHUNYLI", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7600136143", "D", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2865690052", "C", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7254868622", "D", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7254758033", "D", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("3744499651", "L", "工作完成", "22:59", "WANGKAT", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("7254688652", "D", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("5270226010", "D", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("2365182912", "D", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("6427807002", "P", "工作完成", "22:59", "DLJMA", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("1320828062", "D", "工作完成", "22:59", "JUJIANG", "流程:cs未处理"));
                    transFerOrderDataList.add(new TransFerOrderData("4455943870", "P", "工作完成", "22:59", "DLJMA", "流程:cs未处理"));
                    //endregion
*/
                    Map<String, Integer> myMapReceivingOrder = new HashMap<String, Integer>();

                    Map<String, Integer> myMapTransFerOrder = new HashMap<String, Integer>();

                    for (ReceivingOrderData receivingOrderData : receivingOrderDataList) {
                        if (!myMapReceivingOrder.containsKey(receivingOrderData.getLastAccessUser())) {
                            myMapReceivingOrder.put(receivingOrderData.getLastAccessUser(), 1);
                        } else {
                            myMapReceivingOrder.replace(receivingOrderData.getLastAccessUser(), myMapReceivingOrder.get(receivingOrderData.getLastAccessUser()) + 1);
                        }
                    }

                    for (TransFerOrderData transFerOrderData : transFerOrderDataList) {
                        if (!myMapTransFerOrder.containsKey(transFerOrderData.getLastAccessUser())) {
                            myMapTransFerOrder.put(transFerOrderData.getLastAccessUser(), 1);
                        } else {
                            myMapTransFerOrder.replace(transFerOrderData.getLastAccessUser(), myMapTransFerOrder.get(transFerOrderData.getLastAccessUser()) + 1);
                        }
                    }

                    workbook = createWorkbook.generateExcel_Order(m_currentType, myMapReceivingOrder, myMapTransFerOrder, receivingOrderDataList, transFerOrderDataList);

                    section = "TransFerOrder";

                    break;
            }

            if (!section.equals("")) {
                emailInfo.setAddress(new GetConfig().GetConfigByKey(section, "mail"));
            }

            out = new FileOutputStream(file);

            if (workbook != null) {
                //将工作薄写入文件输出流中
                workbook.write(out);

                //将工作薄写入文件输出流中
                workbook.write(out);
            }

            /* 文本文件输出流，释放资源 */
            out.close();

            ActionTarget.setText("文件作成完了, 请检查文件是否正确!");
//            ActionTarget.setText("邮件发送开始");
//            SendEmailUtil sendEmailUtil = new SendEmailUtil();
//            ResultMessage rs = sendEmailUtil.sendEmail(emailInfo);
//            ActionTarget.setText(rs.toString());
        } catch (Exception e) {
            ActionTarget.setText("文件作成例外发生：" + e.getMessage());
        }
    }

    @FXML
    public void SendMailID(ActionEvent actionEvent) {
        try {

            ActionTarget.setText("邮件发送开始");

            String m_currentType = MailTypeID.getSelectionModel().getSelectedItem().toString();

            GetConfig getConfig = new GetConfig();

            emailInfo.setSubject(m_currentType);

            emailInfo.setFilePath("C:\\Temp\\" + m_currentType + ".xlsx");

            emailInfo.setHost(getConfig.GetConfigByKey("UserInfo", "host"));

            emailInfo.setPort(getConfig.GetConfigByKey("UserInfo", "port"));

            emailInfo.setFrom(getConfig.GetConfigByKey("UserInfo", "from"));

            emailInfo.setPassword(getConfig.GetConfigByKey("UserInfo", "password"));

            emailInfo.setNickname(getConfig.GetConfigByKey("UserInfo", "nickname"));

            emailInfo.setContent(getConfig.GetConfigByKey("UserInfo", "content"));

            ResultMessage rs;

            if (new GetConfig().GetConfigByKey("net", "network").equals("open")){
                SendEmailUtil sendEmailUtil = new SendEmailUtil();

                rs = sendEmailUtil.sendEmail(emailInfo);
            }else {
                SendEmailUtilpsd sendEmailUtilpsd = new SendEmailUtilpsd();
                rs = sendEmailUtilpsd.sendEmail(emailInfo);
            }

            ActionTarget.setText(rs.toString());

        } catch (Exception e) {
            ActionTarget.setText("例外发生：" + e.getMessage());
        }

    }
}

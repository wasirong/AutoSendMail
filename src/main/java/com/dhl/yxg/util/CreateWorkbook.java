package com.dhl.yxg.util;

import com.dhl.yxg.data.ExportData_412;
import com.dhl.yxg.data.Export_Report_5i;
import com.dhl.yxg.data.ImportData_412;
import org.apache.commons.math3.analysis.function.Exp;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.IOException;
import java.util.List;

public class CreateWorkbook {

    public XSSFWorkbook generateExcel_Export412(String sheetName, List<ExportData_412> objectList) throws IOException {
        //创建工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();

        //创建表单
        XSSFSheet sheet = genSheet(workbook, sheetName);

        //创建表单样式
        XSSFCellStyle titleStyle = genTitleStyle(workbook);//创建标题样式

        XSSFCellStyle contextStyle = genContextStyle(workbook);//创建文本样式

        //创建Excel
        genExcel_ExportData_412(sheet, titleStyle, contextStyle, objectList, sheetName);

        return workbook;
    }

    public XSSFWorkbook generateExcel_Import412(String sheetName, List<ImportData_412> objectList) throws IOException {

        //创建工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();

        //创建表单
        XSSFSheet sheet = genSheet(workbook, sheetName);

        //创建表单样式
        XSSFCellStyle titleStyle = genTitleStyle(workbook);//创建标题样式

        XSSFCellStyle contextStyle = genContextStyle(workbook);//创建文本样式

        genExcel_ImportData_412(sheet, titleStyle, contextStyle, objectList, sheetName);

        return workbook;
    }

    public XSSFWorkbook generateExcel_ExportReport_5i(String sheetName, List<Export_Report_5i> objectList) throws IOException {

        //创建工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();

        //创建表单
        XSSFSheet sheet = genSheet(workbook, sheetName);

        //创建表单样式
        XSSFCellStyle titleStyle = genTitleStyle(workbook);//创建标题样式

        XSSFCellStyle contextStyle = genContextStyle(workbook);//创建文本样式

        genExcel_ExportReport_5i(sheet, titleStyle, contextStyle, objectList, sheetName);

        return workbook;
    }

    //设置表单，并生成表单
    public XSSFSheet genSheet(XSSFWorkbook workbook, String sheetName) {
        //生成表单
        XSSFSheet sheet = workbook.createSheet(sheetName);

        //设置表单文本居中
        sheet.setHorizontallyCenter(true);
        sheet.setFitToPage(false);

        //打印时在底部右边显示文本页信息
        Footer footer = sheet.getFooter();
        footer.setRight("Page " + HeaderFooter.numPages() + " Of " + HeaderFooter.page());

        //打印时在头部右边显示Excel创建日期信息
        Header header = sheet.getHeader();
        header.setRight("Create Date " + HeaderFooter.date() + " " + HeaderFooter.time());

        //设置打印方式
        XSSFPrintSetup ps = sheet.getPrintSetup();
        ps.setLandscape(true); // true：横向打印，false：竖向打印 ，因为列数较多，推荐在打印时横向打印
        ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE); //打印尺寸大小设置为A4纸大小

        return sheet;
    }

    //生成标题样式
    public XSSFCellStyle genTitleStyle(XSSFWorkbook workbook) {

        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);

        //标题居中，没有边框，所以这里没有设置边框，设置标题文字样式
        XSSFFont titleFont = workbook.createFont();
        titleFont.setBold(true);//加粗
        titleFont.setFontHeight((short) 15);//文字尺寸
        titleFont.setFontHeightInPoints((short) 15);
        style.setFont(titleFont);

        return style;
    }

    //创建文本样式
    public XSSFCellStyle genContextStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);//文本水平居中显示
        style.setVerticalAlignment(VerticalAlignment.CENTER);//文本竖直居中显示
        style.setWrapText(false);//文本自动换行

        //生成Excel表单，需要给文本添加边框样式和颜色
        /*
             CellStyle.BORDER_DOUBLE      双边线
             CellStyle.BORDER_THIN        细边线
             CellStyle.BORDER_MEDIUM      中等边线
             CellStyle.BORDER_DASHED      虚线边线
             CellStyle.BORDER_HAIR        小圆点虚线边线
             CellStyle.BORDER_THICK       粗边线
         */
        style.setBorderBottom(BorderStyle.THIN);//设置文本边框
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        //set border color
        style.setTopBorderColor(new XSSFColor(java.awt.Color.BLACK));//设置文本边框颜色
        style.setBottomBorderColor(new XSSFColor(java.awt.Color.BLACK));
        style.setLeftBorderColor(new XSSFColor(java.awt.Color.BLACK));
        style.setRightBorderColor(new XSSFColor(java.awt.Color.BLACK));

        return style;
    }

    public void genExcel_ExportData_412(XSSFSheet sheet, XSSFCellStyle titleStyle, XSSFCellStyle contextStyle, List<ExportData_412> objList, String titleContent) {

        //根据Excel列名长度，指定列名宽度  Excel总共12列
        for (int i = 0; i < 12; i++) {
            if (i == 0) {
                sheet.setColumnWidth(i, 5400);
            } else if (i == 1) {
                sheet.setColumnWidth(i, 4800);
            } else if (i == 2 || i == 3 || i == 6) {
                sheet.setColumnWidth(i, 3200);
            } else if (i == 4 || i == 5) {
                sheet.setColumnWidth(i, 1600);
            } else if (i == 7 || i == 8) {
                sheet.setColumnWidth(i, 12800);
            } else {
                sheet.setColumnWidth(i, 4000);
            }
        }

        //设置标题位置
        sheet.addMergedRegion(new CellRangeAddress(
                0, //first row
                0, //last row
                0, //first column
                11 //last column
        ));

        XSSFRow row = sheet.createRow(0);//创建第一行，为标题，index从0开始
        XSSFCell cell;
        cell = row.createCell(0);//创建一列
        cell.setCellValue(titleContent);//标题
        cell.setCellStyle(titleStyle);//设置标题样式

        row = sheet.createRow(1);//创建第二行
        List<String> title = new ExportData_412().titleList();
        for (int k = 0; k < title.size(); k++) {
            cell = row.createCell(k);//创建第二行第一列
            cell.setCellValue(title.get(k));
            cell.setCellStyle(contextStyle);
        }

        for (int i = 0; i < objList.size(); i++) {
            ExportData_412 exportData_412 = (ExportData_412) (objList.get(i));
            row = sheet.createRow(i + 2);//创建第三行
            for (int j = 0; j < exportData_412.dataList().size(); j++) {
                cell = row.createCell(j);//创建第三行第一列
                cell.setCellValue(exportData_412.dataList().get(j));//第三行第一列的值
                cell.setCellStyle(contextStyle);
            }
        }
    }

    public void genExcel_ImportData_412(XSSFSheet sheet, XSSFCellStyle titleStyle, XSSFCellStyle contextStyle, List<ImportData_412> objList, String titleContent) {

        //根据Excel列名长度，指定列名宽度  Excel总共10列
        for (int i = 0; i < 14; i++) {
            if (i == 0) {
                sheet.setColumnWidth(i, 4800);
            } else if (i == 1) {
                sheet.setColumnWidth(i, 4800);
            } else if (i == 2) {
                sheet.setColumnWidth(i, 4800);
            } else if (i == 3) {
                sheet.setColumnWidth(i, 3200);
            } else if (i == 4) {
                sheet.setColumnWidth(i, 1600);
            } else if (i == 5) {
                sheet.setColumnWidth(i, 1600);
            } else if (i == 6) {
                sheet.setColumnWidth(i, 2800);
            } else if (i == 7) {
                sheet.setColumnWidth(i, 3200);
            } else if (i == 8) {
                sheet.setColumnWidth(i, 7200);
            } else if (i == 9) {
                sheet.setColumnWidth(i, 3200);
            } else if (i == 10) {
                sheet.setColumnWidth(i, 12800);
            } else if (i == 11) {
                sheet.setColumnWidth(i, 4800);
            } else if (i == 12) {
                sheet.setColumnWidth(i, 25600);
            } else {
                sheet.setColumnWidth(i, 6400);
            }
        }

        //设置标题位置
        sheet.addMergedRegion(new CellRangeAddress(
                0, //first row
                0, //last row
                0, //first column
                13 //last column
        ));

        XSSFRow row = sheet.createRow(0);//创建第一行，为标题，index从0开始
        XSSFCell cell;
        cell = row.createCell(0);//创建一列
        cell.setCellValue(titleContent);//标题
        cell.setCellStyle(titleStyle);//设置标题样式

        row = sheet.createRow(1);//创建第二行
        List<String> title = new ImportData_412().titleList();
        for (int k = 0; k < title.size(); k++) {
            cell = row.createCell(k);//创建第二行第一列
            cell.setCellValue(title.get(k));
            cell.setCellStyle(contextStyle);
        }

        for (int i = 0; i < objList.size(); i++) {
            ImportData_412 importData_412 = (ImportData_412) (objList.get(i));
            row = sheet.createRow(i + 2);//创建第三行
            for (int j = 0; j < importData_412.dataList().size(); j++) {
                cell = row.createCell(j);//创建第三行第一列
                cell.setCellValue(importData_412.dataList().get(j));//第三行第一列的值
                cell.setCellStyle(contextStyle);
            }
        }
    }

    public void genExcel_ExportReport_5i(XSSFSheet sheet, XSSFCellStyle titleStyle, XSSFCellStyle contextStyle, List<Export_Report_5i> objList, String titleContent) {

        //根据Excel列名长度，指定列名宽度  Excel总共9列
        for (int i = 0; i < 9; i++) {
            if (i == 0) {
                sheet.setColumnWidth(i, 4800);
            } else if (i == 1) {
                sheet.setColumnWidth(i, 12960);
            } else if (i == 2) {
                sheet.setColumnWidth(i, 4800);
            } else if (i == 3) {
                sheet.setColumnWidth(i, 4800);
            } else if (i == 4) {
                sheet.setColumnWidth(i, 9920);
            } else if (i == 5) {
                sheet.setColumnWidth(i, 9800);
            } else if (i == 6) {
                sheet.setColumnWidth(i, 34000);
            } else if (i == 7) {
                sheet.setColumnWidth(i, 31000);
            } else{
                sheet.setColumnWidth(i, 7200);
            }
        }

        //设置标题位置
        sheet.addMergedRegion(new CellRangeAddress(
                0, //first row
                0, //last row
                0, //first column
                9 //last column
        ));

        XSSFRow row = sheet.createRow(0);//创建第一行，为标题，index从0开始
        XSSFCell cell;
        cell = row.createCell(0);//创建一列
        cell.setCellValue(titleContent);//标题
        cell.setCellStyle(titleStyle);//设置标题样式

        row = sheet.createRow(1);//创建第二行
        List<String> title = new Export_Report_5i().titleList();
        for (int k = 0; k < title.size(); k++) {
            cell = row.createCell(k);//创建第二行第一列
            cell.setCellValue(title.get(k));
            cell.setCellStyle(contextStyle);
        }

        for (int i = 0; i < objList.size(); i++) {
            Export_Report_5i export_report_5i = (Export_Report_5i) (objList.get(i));
            row = sheet.createRow(i + 2);//创建第三行
            for (int j = 0; j < export_report_5i.dataList().size(); j++) {
                cell = row.createCell(j);//创建第三行第一列
                cell.setCellValue(export_report_5i.dataList().get(j));//第三行第一列的值
                cell.setCellStyle(contextStyle);
            }
        }
    }

}


























package com.dhl.yxg.accessSqlUtil;


import com.dhl.yxg.data.ExportData_412;
import com.dhl.yxg.data.Export_Report_5i;
import com.dhl.yxg.data.ImportData_412;
import com.dhl.yxg.util.WriteLogs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseSearch {

    //    Logger LOG = LoggerFactory.getLogger(DataBaseSearch.class);
    private static final String DBDRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String DBURL = "jdbc:sqlserver://23.156.5.120:1433;DatabaseName=nao_cdms_import";
    private static final String USER = "naouser";//数据库用户名
    private static final String PASSWORD = "naouser123";//数据库密码

    public List<ExportData_412> GetExportData_412(String startDate, String endData) {
        try {
//            String sql = "Select " +
//                    "ExportDate," +
//                    "nao_cdms_export.dbo.tmsShipmentWip.Mawb," +
//                    "nao_cdms_export.dbo.tmsShipmentWip.Hawb," +
//                    "ClearanceCategory," +
//                    "TotalPiece," +
//                    "TotalKgWeight," +
//                    "LocalDescription," +
//                    "ShipCompany," +
//                    "ShipAddr," +
//                    "ShipCity," +
//                    "ConsCountry," +
//                    "ConsTel " +
//                    "from nao_cdms_export.dbo.tmsShipmentWip " +
//                    "left join nao_cdms_export.dbo.LocalFlightWip " +
//                    "on nao_cdms_export.dbo.tmsShipmentWip.LocalFlightId = nao_cdms_export.dbo.LocalFlightWip.LocalFlightId " +
//                    "where " +
//                    "(GTW = 'DLC' and ExportDate between " + startDate + " and " + endData + " and LocalDescription like N'%书%' and ShipCountry = 'KP')" +
//                    "or (GTW = 'DLC' and ExportDate between " + startDate + " and " + endData + " and ShipTel = '0979123684')" +
//                    "or (GTW = 'DLC' and ExportDate between " + startDate + " and " + endData + " and ShipCity = 'DANDONG' and ShipAddr like N'%ZHENXING%')" +
//                    "or (GTW = 'DLC' and ExportDate between " + startDate + " and " + endData + " and ShipCity = 'YANBIAN')" +
//                    "or (GTW = 'DLC' and ExportDate between " + startDate + " and " + endData + " and ShipAddr like N'%YANHAI%')" +
//                    "or (GTW = 'DLC' and ExportDate between " + startDate + " and " + endData + " and ShipCompany like N'%Institute of Chemical Physics%')" +
//                    "or (GTW = 'DLC' and ExportDate between " + startDate + " and " + endData + " and ShipCompany like N'%Naval Academy%')";
//            System.out.println(sql);
            System.out.println("驱动程序开始加载");
            Class.forName(DBDRIVER);
            System.out.println("驱动程序已加载");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("驱动程序加载异常发生:" + e.getMessage());
        }

        ExportData_412 exportData_412 = new ExportData_412();

        Connection conn;
        Statement stmt;
        ResultSet rs;

        String sql = "Select " +
                "ExportDate," +
                "nao_cdms_export.dbo.tmsShipmentWip.Mawb," +
                "nao_cdms_export.dbo.tmsShipmentWip.Hawb," +
                "ClearanceCategory," +
                "TotalPiece," +
                "TotalKgWeight," +
                "LocalDescription," +
                "ShipCompany," +
                "ShipAddr," +
                "ShipCity," +
                "ConsCountry," +
                "ConsTel " +
                "from nao_cdms_export.dbo.tmsShipmentWip " +
                "left join nao_cdms_export.dbo.LocalFlightWip " +
                "on nao_cdms_export.dbo.tmsShipmentWip.LocalFlightId = nao_cdms_export.dbo.LocalFlightWip.LocalFlightId " +
                "where " +
                "(GTW = 'DLC' and ExportDate between '" + startDate + "' and '" + endData + "' and LocalDescription like N'%书%' and ShipCountry = 'KP')" +
                " or (GTW = 'DLC' and ExportDate between '" + startDate + "' and '" + endData + "' and ShipTel = '0979123684')" +
                " or (GTW = 'DLC' and ExportDate between '" + startDate + "' and '" + endData + "' and ShipCity = 'DANDONG' and ShipAddr like N'%ZHENXING%')" +
                " or (GTW = 'DLC' and ExportDate between '" + startDate + "' and '" + endData + "' and ShipCity = 'YANBIAN')" +
                " or (GTW = 'DLC' and ExportDate between '" + startDate + "' and '" + endData + "' and ShipAddr like N'%YANHAI%')" +
                " or (GTW = 'DLC' and ExportDate between '" + startDate + "' and '" + endData + "' and ShipCompany like N'%Institute of Chemical Physics%')" +
                " or (GTW = 'DLC' and ExportDate between '" + startDate + "' and '" + endData + "' and ShipCompany like N'%Naval Academy%')";

        List<ExportData_412> list = new ArrayList<ExportData_412>();

        try {
//            LOG.info("连接数据库");
            System.out.println("连接数据库");
            // 连接数据库
//            conn = DriverManager.getConnection(url, user, password);
            conn = DriverManager.getConnection(DBURL, USER, PASSWORD);
            System.out.println(conn);//输出数据库连接
            System.out.println("建立Statement对象");
            // 建立Statement对象
            stmt = conn.createStatement();
//            LOG.info("执行数据库查询语句 : " + sql);
            // 执行数据库查询语句
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // 本地航班日期
                String m_localFlightDate = rs.getString("ExportDate");
                exportData_412.setLocalFlightDate(m_localFlightDate);

                // 本地总运单号
                String m_localMasterWaybillNo = rs.getString("Mawb");
                exportData_412.setLocalMasterWaybillNo(m_localMasterWaybillNo);

                // 分运单号
                String m_shippingOrderNo = rs.getString("Hawb");
                exportData_412.setShippingOrderNo(m_shippingOrderNo);

                // 申报类别
                String m_declarationCategory = rs.getString("ClearanceCategory");
                exportData_412.setDeclarationCategory(m_declarationCategory);

                // 件数
                String m_pieces = rs.getString("TotalPiece");
                exportData_412.setPieces(m_pieces);

                // 重量
                String m_weight = rs.getString("TotalKgWeight");
                exportData_412.setWeight(m_weight);

                // 中文品名
                String m_chineseName = rs.getString("LocalDescription");
                exportData_412.setChineseName(m_chineseName);

                // 发件人公司
                String m_senderCompany = rs.getString("ShipCompany");
                exportData_412.setSenderCompany(m_senderCompany);

                // 发件人地址
                String m_senderAddress = rs.getString("ShipAddr");
                exportData_412.setSenderAddress(m_senderAddress);

                // 发件人城市
                String m_senderCity = rs.getString("ShipCity");
                exportData_412.setSenderCity(m_senderCity);

                // 收件人国家
                String m_recipientCountry = rs.getString("ConsCountry");
                exportData_412.setRecipientCountry(m_recipientCountry);

                // 收件人电话
                String m_recipientPhone = rs.getString("ConsTel");
                exportData_412.setRecipientPhone(m_recipientPhone);
//                WriteLogs wr = new WriteLogs();
//                wr.WriteLogToTXT(exportData_412.toString());
                list.add(exportData_412);

                exportData_412 = new ExportData_412();
            }
            if (rs != null) {
//                LOG.info("rs.close()");
                rs.close();
                rs = null;
            }
            if (stmt != null) {
//                LOG.info("stmt.close()");
                stmt.close();
                stmt = null;
            }
            if (conn != null) {
//                LOG.info("conn.close()");
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
//            LOG.info("数据库连接失败" + e.getMessage());
        } catch (Exception e) {
//            LOG.info("数据库连接异常发生" + e.getMessage());
        }
        return list;
    }

    public List<ImportData_412> GetImportData_412(String startDate, String endDate) {
        try {

            System.out.println("驱动程序开始加载");
            Class.forName(DBDRIVER);
            System.out.println("驱动程序已加载");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("驱动程序加载异常发生:" + e.getMessage());
        }

        ImportData_412 importData_412 = new ImportData_412();

        Connection conn;
        Statement stmt;
        ResultSet rs;

        String sql = "Select " +
                "ImportDate," +
                "nao_cdms_import.dbo.ShipmentWip.Mawb," +
                "nao_cdms_import.dbo.ShipmentWip.Hawb," +
                "ClearanceCategory," +
                "nao_cdms_import.dbo.ShipmentWip.TotalPiece," +
                "nao_cdms_import.dbo.ShipmentWip.TotalKgWeight," +
                "GTW," +
                "LocalDescription," +
                "ShipTel," +
                "ShipState," +
                "ConsCompany," +
                "ConsCity," +
                "ConsAddr," +
                "ConsTel " +
                "from nao_cdms_import.dbo.ShipmentWip " +
                "left join nao_cdms_import.dbo.EDIDeclaration " +
                "on nao_cdms_import.dbo.ShipmentWip.HawbId = nao_cdms_import.dbo.EDIDeclaration.HawbId " +
                "where " +
                "(LocalDescription like N'%书%' and ShipState = 'KP' and GTW = 'DLC' and ImportDate between '" + startDate + "' and '" + endDate + "' and nao_cdms_import.dbo.ShipmentWip.Mawb != 'DUMMY')" +
                " or (ShipTel = '0979123684' and GTW = 'DLC' and ImportDate between '" + startDate + "' and '" + endDate + "' and nao_cdms_import.dbo.ShipmentWip.Mawb != 'DUMMY')" +
                " or (ConsCity = 'DANDONG' and ConsAddr like N'%ZHENXING%' and GTW = 'DLC' and ImportDate between '" + startDate + "' and '" + endDate + "' and nao_cdms_import.dbo.ShipmentWip.Mawb != 'DUMMY')" +
                " or (ConsCity = 'YANBIAN' and GTW = 'DLC' and ImportDate between '" + startDate + "' and '" + endDate + "' and nao_cdms_import.dbo.ShipmentWip.Mawb != 'DUMMY')" +
                " or (ConsAddr like N'%YANHAI%' and GTW = 'DLC' and ImportDate between '" + startDate + "' and '" + endDate + "' and nao_cdms_import.dbo.ShipmentWip.Mawb != 'DUMMY')" +
                " or (ConsCompany like N'%Institute of Chemical Physics%' and GTW = 'DLC' and ImportDate between '" + startDate + "' and '" + endDate + "' and nao_cdms_import.dbo.ShipmentWip.Mawb != 'DUMMY')" +
                " or (ConsCompany like N'%Naval Academy%' and GTW = 'DLC' and ImportDate between '" + startDate + "' and '" + endDate + "' and nao_cdms_import.dbo.ShipmentWip.Mawb != 'DUMMY')";

        List<ImportData_412> list = new ArrayList<ImportData_412>();

        try {
//            LOG.info("连接数据库");
            System.out.println("连接数据库");
            // 连接数据库
//            conn = DriverManager.getConnection(url, user, password);
            conn = DriverManager.getConnection(DBURL, USER, PASSWORD);
            System.out.println(conn);//输出数据库连接
            System.out.println("建立Statement对象");
            // 建立Statement对象
            stmt = conn.createStatement();
//            LOG.info("执行数据库查询语句 : " + sql);
            // 执行数据库查询语句
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // 航班日期
                String m_flightDate = rs.getString("ImportDate");
                importData_412.setFlightDate(m_flightDate);

                // 总运单号
                String m_totalWaybillNo = rs.getString("Mawb");
                importData_412.setTotalWaybillNo(m_totalWaybillNo);

                // 分运单号
                String m_shippingOrderNo = rs.getString("Hawb");
                importData_412.setShippingOrderNo(m_shippingOrderNo);

                // 申报类别
                String m_declarationCategory = rs.getString("ClearanceCategory");
                importData_412.setDeclarationCategory(m_declarationCategory);

                // 件数
                String m_number = rs.getString("TotalPiece");
                importData_412.setNumber(m_number);

                // 重量
                String m_weight = rs.getString("TotalKgWeight");
                importData_412.setWeight(m_weight);

                // 目的地
                String m_destination = rs.getString("GTW");
                importData_412.setDestination(m_destination);

                // 中文品名
                String m_chineseName = rs.getString("LocalDescription");
                importData_412.setChineseName(m_chineseName);

                // 发件人电话
                String m_senderPhone = rs.getString("ShipTel");
                importData_412.setSenderPhone(m_senderPhone);

                // 发件人国家
                String m_sendingCountry = rs.getString("ShipState");
                importData_412.setSendingCountry(m_sendingCountry);

                // 收件人公司(EN)
                String m_recipientCompany_EN = rs.getString("ConsCompany");
                importData_412.setRecipientCompany_EN(m_recipientCompany_EN);

                // 收件人城市
                String m_recipientCity = rs.getString("ConsCity");
                importData_412.setRecipientCity(m_recipientCity);

                // 收件人地址
                String m_recipientAddress = rs.getString("ConsAddr");
                importData_412.setRecipientAddress(m_recipientAddress);

                // 收件人电话
                String m_recipientPhone = rs.getString("ConsTel");
                importData_412.setRecipientPhone(m_recipientPhone);

                list.add(importData_412);

                importData_412 = new ImportData_412();
            }
            rs.close();
            rs = null;
            stmt.close();
            stmt = null;
            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
//            LOG.info("数据库连接失败" + e.getMessage());
        } catch (Exception e) {
//            LOG.info("数据库连接异常发生" + e.getMessage());
        }
        return list;
    }

    public List<Export_Report_5i> GetExportReport5i(String startDate, String endDate) {
        try {
            System.out.println("驱动程序开始加载");
            Class.forName(DBDRIVER);
            System.out.println("驱动程序已加载");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("驱动程序加载异常发生:" + e.getMessage());
        }

        Export_Report_5i export_report_5i = new Export_Report_5i();

        Connection conn;
        Statement stmt;
        ResultSet rs;

        String sql = "Select " +
                "tmsShipmentWip.Hawb," +
                "ShipCompany," +
                "CodeTS," +
                "HsCode," +
                "GName," +
                "LocalDescription," +
                "GModel," +
                "UnitMeasure," +
                "ShipDate " +
                "from nao_cdms_export.dbo.ESBDeclShipmentDetail " +
                "left join nao_cdms_export.dbo.tmsShipmentWip " +
                "on ESBDeclShipmentDetail.Hawb = tmsShipmentWip.Hawb " +
                "left join nao_cdms_export.dbo.SimpleDeclarationItem " +
                "on tmsShipmentWip.HawbId = SimpleDeclarationItem.HawbId " +
                "where " +
                "GTW = 'DLC' and ShipDate between '" + startDate + "' and '" + endDate + "' " +
                "order by ShipDate asc";

        List<Export_Report_5i> list = new ArrayList<Export_Report_5i>();

        try {
//            LOG.info("连接数据库");
            System.out.println("连接数据库");
            // 连接数据库
//            conn = DriverManager.getConnection(url, user, password);
            conn = DriverManager.getConnection(DBURL, USER, PASSWORD);
            System.out.println(conn);//输出数据库连接
            System.out.println("建立Statement对象");
            // 建立Statement对象
            stmt = conn.createStatement();
//            LOG.info("执行数据库查询语句 : " + sql);
            // 执行数据库查询语句
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String m_hawb = rs.getString("Hawb");
                export_report_5i.setHawb(m_hawb);

                String m_shipCompany = rs.getString("ShipCompany");
                export_report_5i.setShipCompany(m_shipCompany);

                String m_codeTS = rs.getString("CodeTS");
                export_report_5i.setCodeTS(m_codeTS);

                String m_hsCode = rs.getString("HsCode");
                export_report_5i.setHsCode(m_hsCode);

                String m_gName = rs.getString("GName");
                export_report_5i.setGName(m_gName);

                String m_localDescription = rs.getString("LocalDescription");
                export_report_5i.setLocalDescription(m_localDescription);

                String m_gModel = rs.getString("GModel");
                export_report_5i.setGModel(m_gModel);

                String m_unitMeasure = rs.getString("UnitMeasure");
                export_report_5i.setUnitMeasure(m_unitMeasure);

                String m_shipDate = rs.getString("ShipDate");
                export_report_5i.setShipDate(m_shipDate);

                list.add(export_report_5i);

                export_report_5i = new Export_Report_5i();
            }
            rs.close();
            rs = null;
            stmt.close();
            stmt = null;
            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
//            LOG.info("数据库连接失败" + e.getMessage());
        } catch (Exception e) {
//            LOG.info("数据库连接异常发生" + e.getMessage());
        }

        return list;
    }
}

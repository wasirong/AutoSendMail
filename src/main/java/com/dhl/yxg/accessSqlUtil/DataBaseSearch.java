package com.dhl.yxg.accessSqlUtil;


import com.dhl.yxg.data.*;
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

    public List<ReplacementReleaseData> GetReplacementRelease(String startDate, String endDate) {
        try {
            System.out.println("驱动程序开始加载");
            Class.forName(DBDRIVER);
            System.out.println("驱动程序已加载");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("驱动程序加载异常发生:" + e.getMessage());
        }

        ReplacementReleaseData replacementReleaseData = new ReplacementReleaseData();

        Connection conn;
        Statement stmt;
        ResultSet rs;

        String sql = "Select " +
                "Hawb," +
                "Type," +
                "LastAccessDtm," +
                "LastAccessUser," +
                "nao_cdms_import.dbo.HawbLog.Comment " +
                "from nao_cdms_import.dbo.HawbLog " +
                "left join nao_cdms_import.dbo.ShipmentWip " +
                "on nao_cdms_import.dbo.ShipmentWip.HawbId = nao_cdms_import.dbo.HawbLog.HawbId " +
                "where " +
                "LastAccessUser = 'XIAOHGUO' " +
                "and nao_cdms_import.dbo.HawbLog.Comment like N'%E1-海关手工放行%' " +
                "and LastAccessDtm between '" + startDate + "' and '" + endDate + "' " +
                "order by LastAccessDtm asc";

        List<ReplacementReleaseData> list = new ArrayList<ReplacementReleaseData>();

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
                replacementReleaseData.setHawb(m_hawb);

                String m_type = rs.getString("Type");
                replacementReleaseData.setType(m_type);

                String m_lastAccessDtm = rs.getString("LastAccessDtm");
                replacementReleaseData.setLastAccessDtm(m_lastAccessDtm);

                String m_lastAccessUser = rs.getString("LastAccessUser");
                replacementReleaseData.setLastAccessUser(m_lastAccessUser);

                String m_comment = rs.getString("Comment");
                replacementReleaseData.setComment(m_comment);

                list.add(replacementReleaseData);

                replacementReleaseData = new ReplacementReleaseData();
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

    public List<DaysOFGoodsInWarehouse> GetDaysOFGoodsInWarehouses(String startDate, String endDate) {
        try {
            System.out.println("驱动程序开始加载");
            Class.forName(DBDRIVER);
            System.out.println("驱动程序已加载");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("驱动程序加载异常发生:" + e.getMessage());
        }

        DaysOFGoodsInWarehouse daysOFGoodsInWarehouse = new DaysOFGoodsInWarehouse();

        Connection conn;
        Statement stmt;
        ResultSet rs;

        String sql = "Select * from ( SELECT [bond_int_id], " +
                "[bond_vchar_gtw]," +
                "[bond_vchar_hawb]," +
                "[bond_date_first_report]," +
                "[bond_date_last_report]," +
                "[bond_vchar_location]," +
                "[bond_date_arrdate], " +
                "[bond_int_dayinbond], " +
                "[bond_vchar_cnee], " +
                "[bond_vchar_orig], " +
                "[bond_vchar_dest], " +
                "[bond_dou_weight], " +
                "[bond_int_pces], " +
                "[bond_int_scanned], " +
                "[bond_vchar_status], " +
                "[bond_vchar_br], " +
                "[bond_NO], " +
                "[bond_dttm_INSP], " +
                "[bond_dttm_LastManage], " +
                "[bond_LastManager], " +
                "[bond_LastCommentType], " +
                "[bond_color], " +
                "[bond_LastCommentType2], " +
                "[bond_LastCommentType_bkup] " +
                "from [db_GPMS].[dbo].[tb_bond_daily_data] where [bond_vchar_gtw] = 'DLC' AND [bond_date_last_report] BETWEEN ' " + startDate + "' AND '" + endDate + "' AND [bond_int_dayinbond] > 13 ) " +
                "A left join(select [HawbId]," +
                "[Hawb]," +
                "[CSUser] from (select L.[HawbId], " +
                "L.[Hawb], " +
                "L.[GTW], " +
                "L.[PayerAccount], " +
                "L.[CSUser], " +
                "L.[CreationDate], " +
                "ROW_NUMBER() OVER(PARTITION BY L.[Hawb] ORDER BY L.[CreationDate] DESC) AS [DtmBYHawbidRank] from (SELECT [HawbId], " +
                "[Hawb], " +
                "[GTW], " +
                "[PayerAccount], " +
                "[CSUser], " +
                "[CreationDate] " +
                "FROM [nao_cdms_import].[dbo].[ShipmentWip] WHERE [GTW] = 'DLC' union " +
                "select [HawbId], " +
                "[Hawb], " +
                "[GTW], " +
                "[PayerAccount], " +
                "[CSUser], " +
                "[CreationDate] " +
                "FROM [nao_cdms_import_his1].[dbo].[ShipmentWip]WHERE[GTW] = 'DLC' )L )M " +
                "WHERE M.[DtmBYHawbidRank]  = 1) B ON A.[bond_vchar_hawb]  COLLATE DATABASE_DEFAULT = B.[Hawb] COLLATE DATABASE_DEFAULT ";

        List<DaysOFGoodsInWarehouse> list = new ArrayList<DaysOFGoodsInWarehouse>();

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
                String m_bond_int_id = rs.getString("bond_int_id");
                daysOFGoodsInWarehouse.setBond_int_id(m_bond_int_id);

                String m_bond_vchar_gtw = rs.getString("bond_vchar_gtw");
                daysOFGoodsInWarehouse.setBond_vchar_gtw(m_bond_vchar_gtw);

                String m_bond_vchar_hawb = rs.getString("bond_vchar_hawb");
                daysOFGoodsInWarehouse.setBond_vchar_hawb(m_bond_vchar_hawb);

                String m_bond_date_first_report = rs.getString("bond_date_first_report");
                daysOFGoodsInWarehouse.setBond_date_first_report(m_bond_date_first_report);

                String m_bond_date_last_report = rs.getString("bond_date_last_report");
                daysOFGoodsInWarehouse.setBond_date_last_report(m_bond_date_last_report);

                String m_bond_vchar_location = rs.getString("bond_vchar_location");
                daysOFGoodsInWarehouse.setBond_vchar_location(m_bond_vchar_location);

                String m_bond_date_arrdate = rs.getString("bond_date_arrdate");
                daysOFGoodsInWarehouse.setBond_date_arrdate(m_bond_date_arrdate);

                String m_bond_int_dayinbond = rs.getString("bond_int_dayinbond");
                daysOFGoodsInWarehouse.setBond_int_dayinbond(m_bond_int_dayinbond);

                String m_bond_vchar_cnee = rs.getString("bond_vchar_cnee");
                daysOFGoodsInWarehouse.setBond_vchar_cnee(m_bond_vchar_cnee);

                String m_bond_vchar_orig = rs.getString("bond_vchar_orig");
                daysOFGoodsInWarehouse.setBond_vchar_orig(m_bond_vchar_orig);

                String m_bond_vchar_dest = rs.getString("bond_vchar_dest");
                daysOFGoodsInWarehouse.setBond_vchar_dest(m_bond_vchar_dest);

                String m_bond_dou_weight = rs.getString("bond_dou_weight");
                daysOFGoodsInWarehouse.setBond_dou_weight(m_bond_dou_weight);

                String m_bond_int_pces = rs.getString("bond_int_pces");
                daysOFGoodsInWarehouse.setBond_int_pces(m_bond_int_pces);

                String m_bond_int_scanned = rs.getString("bond_int_scanned");
                daysOFGoodsInWarehouse.setBond_int_scanned(m_bond_int_scanned);

                String m_bond_vchar_status = rs.getString("bond_vchar_status");
                daysOFGoodsInWarehouse.setBond_vchar_status(m_bond_vchar_status);

                String m_bond_vchar_br = rs.getString("bond_vchar_br");
                daysOFGoodsInWarehouse.setBond_vchar_br(m_bond_vchar_br);

                String m_bond_NO = rs.getString("bond_NO");
                daysOFGoodsInWarehouse.setBond_NO(m_bond_NO);

                String m_bond_dttm_INSP = rs.getString("bond_dttm_INSP");
                daysOFGoodsInWarehouse.setBond_dttm_INSP(m_bond_dttm_INSP);

                String m_bond_dttm_LastManage = rs.getString("bond_dttm_LastManage");
                daysOFGoodsInWarehouse.setBond_dttm_LastManage(m_bond_dttm_LastManage);

                String m_bond_LastManager = rs.getString("bond_LastManager");
                daysOFGoodsInWarehouse.setBond_LastManager(m_bond_LastManager);

                String m_bond_LastCommentType = rs.getString("bond_LastCommentType");
                daysOFGoodsInWarehouse.setBond_LastCommentType(m_bond_LastCommentType);

                String m_bond_color = rs.getString("bond_color");
                daysOFGoodsInWarehouse.setBond_color(m_bond_color);

                String m_bond_LastCommentType2 = rs.getString("bond_LastCommentType2");
                daysOFGoodsInWarehouse.setBond_LastCommentType2(m_bond_LastCommentType2);

                String m_bond_LastCommentType_bkup = rs.getString("bond_LastCommentType_bkup");
                daysOFGoodsInWarehouse.setBond_LastCommentType_bkup(m_bond_LastCommentType_bkup);

                String m_hawbId = rs.getString("HawbId");
                daysOFGoodsInWarehouse.setHawbId(m_hawbId);

                String m_hawb = rs.getString("Hawb");
                daysOFGoodsInWarehouse.setHawb(m_hawb);

                String m_CSUser = rs.getString("CSUser");
                daysOFGoodsInWarehouse.setCSUser(m_CSUser);

                list.add(daysOFGoodsInWarehouse);

                daysOFGoodsInWarehouse = new DaysOFGoodsInWarehouse();
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

    public List<ReceivingOrderData> GetReceivingOrderData(String startDate, String endDate) {
        try {
            System.out.println("驱动程序开始加载");
            Class.forName(DBDRIVER);
            System.out.println("驱动程序已加载");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("驱动程序加载异常发生:" + e.getMessage());
        }

        ReceivingOrderData receivingOrderData = new ReceivingOrderData();

        Connection conn;
        Statement stmt;
        ResultSet rs;

        String sql = "Select " +
                "Hawb, " +
                "ClearanceCategory, " +
                "Type," +
                "LastAccessDtm," +
                "LastAccessUser," +
                "nao_cdms_import.dbo.HawbLog.Comment " +
                "from nao_cdms_import.dbo.HawbLog " +
                "left join nao_cdms_import.dbo.ShipmentWip " +
                "on nao_cdms_import.dbo.ShipmentWip.HawbId = nao_cdms_import.dbo.HawbLog.HawbId " +
                "where " +
                "nao_cdms_import.dbo.HawbLog.Comment like N'%CS理单%' " +
                " and GTW = 'DLC' " +
                " and Type like N'%工作完成%' " +
                " and LastAccessDtm between '" + startDate + "' and '" + endDate + "'";

        List<ReceivingOrderData> list = new ArrayList<ReceivingOrderData>();

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
                receivingOrderData.setHawb(m_hawb);

                String m_clearanceCategory = rs.getString("ClearanceCategory");
                receivingOrderData.setClearanceCategory(m_clearanceCategory);

                String m_type = rs.getString("Type");
                receivingOrderData.setType(m_type);

                String m_lastAccessDtm = rs.getString("LastAccessDtm");
                receivingOrderData.setLastAccessDtm(m_lastAccessDtm);

                String m_lastAccessUser = rs.getString("LastAccessUser");
                receivingOrderData.setLastAccessUser(m_lastAccessUser);

                String m_comment = rs.getString("Comment");
                receivingOrderData.setComment(m_comment);

                list.add(receivingOrderData);

                receivingOrderData = new ReceivingOrderData();
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

    public List<TransFerOrderData> GetTransFerOrderData(String startDate, String endDate) {
        try {
            System.out.println("驱动程序开始加载");
            Class.forName(DBDRIVER);
            System.out.println("驱动程序已加载");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("驱动程序加载异常发生:" + e.getMessage());
        }

        TransFerOrderData transFerOrderData = new TransFerOrderData();

        Connection conn;
        Statement stmt;
        ResultSet rs;

        String sql = "Select " +
                "Hawb, " +
                "ClearanceCategory, " +
                "Type," +
                "LastAccessDtm," +
                "LastAccessUser," +
                "nao_cdms_import.dbo.HawbLog.Comment " +
                "from nao_cdms_import.dbo.HawbLog " +
                "left join nao_cdms_import.dbo.ShipmentWip " +
                "on nao_cdms_import.dbo.ShipmentWip.HawbId = nao_cdms_import.dbo.HawbLog.HawbId " +
                "where " +
                "nao_cdms_import.dbo.HawbLog.Comment like N'%CS未处理%'  " +
                " and GTW = 'DLC' " +
                " and Type like N'%工作完成%' " +
                " and LastAccessDtm between '" + startDate + "' and '" + endDate + "'";

        List<TransFerOrderData> list = new ArrayList<TransFerOrderData>();

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
                transFerOrderData.setHawb(m_hawb);

                String m_clearanceCategory = rs.getString("ClearanceCategory");
                transFerOrderData.setClearanceCategory(m_clearanceCategory);

                String m_type = rs.getString("Type");
                transFerOrderData.setType(m_type);

                String m_lastAccessDtm = rs.getString("LastAccessDtm");
                transFerOrderData.setLastAccessDtm(m_lastAccessDtm);

                String m_lastAccessUser = rs.getString("LastAccessUser");
                transFerOrderData.setLastAccessUser(m_lastAccessUser);

                String m_comment = rs.getString("Comment");
                transFerOrderData.setComment(m_comment);

                list.add(transFerOrderData);

                transFerOrderData = new TransFerOrderData();
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

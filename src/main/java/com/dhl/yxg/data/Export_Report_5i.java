package com.dhl.yxg.data;

import java.util.ArrayList;
import java.util.List;

public class Export_Report_5i {

    private String Hawb;

    private String ShipCompany;

    private String CodeTS;

    private String HsCode;

    private String GName;

    private String LocalDescription;

    private String GModel;

    private String UnitMeasure;

    private String ShipDate;

    public String getHawb() {
        return Hawb;
    }

    public void setHawb(String hawb) {
        Hawb = hawb;
    }

    public String getShipCompany() {
        return ShipCompany;
    }

    public void setShipCompany(String shipCompany) {
        ShipCompany = shipCompany;
    }

    public String getCodeTS() {
        return CodeTS;
    }

    public void setCodeTS(String codeTS) {
        CodeTS = codeTS;
    }

    public String getHsCode() {
        return HsCode;
    }

    public void setHsCode(String hsCode) {
        HsCode = hsCode;
    }

    public String getGName() {
        return GName;
    }

    public void setGName(String GName) {
        this.GName = GName;
    }

    public String getLocalDescription() {
        return LocalDescription;
    }

    public void setLocalDescription(String localDescription) {
        LocalDescription = localDescription;
    }

    public String getGModel() {
        return GModel;
    }

    public void setGModel(String GModel) {
        this.GModel = GModel;
    }

    public String getUnitMeasure() {
        return UnitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        UnitMeasure = unitMeasure;
    }

    public String getShipDate() {
        return ShipDate;
    }

    public void setShipDate(String shipDate) {
        ShipDate = shipDate;
    }

    public List<String> dataList(){
        List<String> list = new ArrayList<String>();
        list.add(Hawb);
        list.add(ShipCompany);
        list.add(CodeTS);
        list.add(HsCode);
        list.add(GName);
        list.add(LocalDescription);
        list.add(GModel);
        list.add(UnitMeasure);
        list.add(ShipDate);
        return list;
    }

    public List<String> titleList(){
        List<String> titleList = new ArrayList<String>();
        titleList.add("Hawb");
        titleList.add("ShipCompany");
        titleList.add("CodeTS");
        titleList.add("HsCode");
        titleList.add("GName");
        titleList.add("LocalDescription");
        titleList.add("GModel");
        titleList.add("UnitMeasure");
        titleList.add("ShipDate");
        return titleList;
    }
}

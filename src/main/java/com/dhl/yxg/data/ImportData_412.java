package com.dhl.yxg.data;

import java.util.ArrayList;
import java.util.List;

public class ImportData_412 {
    // 航班日期
    private String FlightDate;

    // 总运单号
    private String TotalWaybillNo;

    // 分运单号
    private String ShippingOrderNo;

    // 申报类别
    private String DeclarationCategory;

    // 件数
    private String Number;

    // 重量
    private String Weight;

    // 目的地
    private String Destination;

    // 中文品名
    private String ChineseName;

    // 发件人电话
    private String SenderPhone;

    // 发件国家
    private String SendingCountry;

    // 收件人公司(EN)
    private String RecipientCompany_EN;

    // 收件人城市
    private String RecipientCity;

    // 收件人地址
    private String RecipientAddress;

    // 收件人电话
    private String RecipientPhone;

    public String getFlightDate() {
        return FlightDate;
    }

    public void setFlightDate(String flightDate) {
        FlightDate = flightDate;
    }

    public String getTotalWaybillNo() {
        return TotalWaybillNo;
    }

    public void setTotalWaybillNo(String totalWaybillNo) {
        TotalWaybillNo = totalWaybillNo;
    }

    public String getShippingOrderNo() {
        return ShippingOrderNo;
    }

    public void setShippingOrderNo(String shippingOrderNo) {
        ShippingOrderNo = shippingOrderNo;
    }

    public String getDeclarationCategory() {
        return DeclarationCategory;
    }

    public void setDeclarationCategory(String declarationCategory) {
        DeclarationCategory = declarationCategory;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getChineseName() {
        return ChineseName;
    }

    public void setChineseName(String chineseName) {
        ChineseName = chineseName;
    }

    public String getSenderPhone() {
        return SenderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        SenderPhone = senderPhone;
    }

    public String getSendingCountry() {
        return SendingCountry;
    }

    public void setSendingCountry(String sendingCountry) {
        SendingCountry = sendingCountry;
    }

    public String getRecipientCompany_EN() {
        return RecipientCompany_EN;
    }

    public void setRecipientCompany_EN(String recipientCompany_EN) {
        RecipientCompany_EN = recipientCompany_EN;
    }

    public String getRecipientCity() {
        return RecipientCity;
    }

    public void setRecipientCity(String recipientCity) {
        RecipientCity = recipientCity;
    }

    public String getRecipientAddress() {
        return RecipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        RecipientAddress = recipientAddress;
    }

    public String getRecipientPhone() {
        return RecipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        RecipientPhone = recipientPhone;
    }

    public List<String> dataList(){
        List<String> list = new ArrayList<String>();
        list.add(FlightDate);
        list.add(TotalWaybillNo);
        list.add(ShippingOrderNo);
        list.add(DeclarationCategory);
        list.add(Number);
        list.add(Weight);
        list.add(Destination);
        list.add(ChineseName);
        list.add(SenderPhone);
        list.add(SendingCountry);
        list.add(RecipientCompany_EN);
        list.add(RecipientCity);
        list.add(RecipientAddress);
        list.add(RecipientPhone);
        return list;
    }

    public List<String> titleList(){
        List<String> titleList = new ArrayList<String>();
        titleList.add("航班日期");
        titleList.add("总运单号");
        titleList.add("分运单号");
        titleList.add("申报类别");
        titleList.add("件数");
        titleList.add("重量");
        titleList.add("目的地");
        titleList.add("中文品名");
        titleList.add("发件人电话");
        titleList.add("发件国家");
        titleList.add("收件人公司(EN)");
        titleList.add("收件人城市");
        titleList.add("收件人地址");
        titleList.add("收件人电话");
        return titleList;
    }
}

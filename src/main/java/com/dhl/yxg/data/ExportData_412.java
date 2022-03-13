package com.dhl.yxg.data;

public class ExportData_412 {

    // 本地航班日期
    private String LocalFlightDate;

    // 本地总运单号
    private String LocalMasterWaybillNo;

    // 分运单号
    private String ShippingOrderNo;

    // 申报类别
    private String DeclarationCategory;

    // 件数
    private String Pieces;

    // 重量
    private String Weight;

    // 中文品名
    private String ChineseName;

    // 发件人公司
    private String SenderCompany;

    // 发件人地址
    private String SenderAddress;

    // 发件人城市
    private String SenderCity;

    // 收件人国家
    private String RecipientCountry;

    // 收件人电话
    private String RecipientPhone;

    public String getLocalFlightDate() {
        return LocalFlightDate;
    }

    public void setLocalFlightDate(String localFlightDate) {
        LocalFlightDate = localFlightDate;
    }

    public String getLocalMasterWaybillNo() {
        return LocalMasterWaybillNo;
    }

    public void setLocalMasterWaybillNo(String localMasterWaybillNo) {
        LocalMasterWaybillNo = localMasterWaybillNo;
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

    public String getPieces() {
        return Pieces;
    }

    public void setPieces(String pieces) {
        Pieces = pieces;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getChineseName() {
        return ChineseName;
    }

    public void setChineseName(String chineseName) {
        ChineseName = chineseName;
    }

    public String getSenderCompany() {
        return SenderCompany;
    }

    public void setSenderCompany(String senderCompany) {
        SenderCompany = senderCompany;
    }

    public String getSenderAddress() {
        return SenderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        SenderAddress = senderAddress;
    }

    public String getSenderCity() {
        return SenderCity;
    }

    public void setSenderCity(String senderCity) {
        SenderCity = senderCity;
    }

    public String getRecipientCountry() {
        return RecipientCountry;
    }

    public void setRecipientCountry(String recipientCountry) {
        RecipientCountry = recipientCountry;
    }

    public String getRecipientPhone() {
        return RecipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        RecipientPhone = recipientPhone;
    }

    @Override
    public String toString() {
        return "ExportData_412{" +
                "LocalFlightDate='" + LocalFlightDate + '\'' +
                ", LocalMasterWaybillNo='" + LocalMasterWaybillNo + '\'' +
                ", ShippingOrderNo='" + ShippingOrderNo + '\'' +
                ", DeclarationCategory='" + DeclarationCategory + '\'' +
                ", Pieces='" + Pieces + '\'' +
                ", Weight='" + Weight + '\'' +
                ", ChineseName='" + ChineseName + '\'' +
                ", SenderCompany='" + SenderCompany + '\'' +
                ", SenderAddress='" + SenderAddress + '\'' +
                ", SenderCity='" + SenderCity + '\'' +
                ", RecipientCountry='" + RecipientCountry + '\'' +
                ", RecipientPhone='" + RecipientPhone + '\'' +
                '}';
    }
}

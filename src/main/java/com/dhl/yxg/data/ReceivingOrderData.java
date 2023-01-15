package com.dhl.yxg.data;

import java.util.ArrayList;
import java.util.List;

public class ReceivingOrderData {

    private String Hawb;

    private String ClearanceCategory;

    private String Type;

    private String LastAccessDtm;

    private String LastAccessUser;

    private String Comment;

    public ReceivingOrderData() {
    }

    public ReceivingOrderData(String hawb, String clearanceCategory, String type, String lastAccessDtm, String lastAccessUser, String comment) {
        Hawb = hawb;
        ClearanceCategory = clearanceCategory;
        Type = type;
        LastAccessDtm = lastAccessDtm;
        LastAccessUser = lastAccessUser;
        Comment = comment;
    }

    public String getHawb() {
        return Hawb;
    }

    public void setHawb(String hawb) {
        Hawb = hawb;
    }

    public String getClearanceCategory() {
        return ClearanceCategory;
    }

    public void setClearanceCategory(String clearanceCategory) {
        ClearanceCategory = clearanceCategory;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getLastAccessDtm() {
        return LastAccessDtm;
    }

    public void setLastAccessDtm(String lastAccessDtm) {
        LastAccessDtm = lastAccessDtm;
    }

    public String getLastAccessUser() {
        return LastAccessUser;
    }

    public void setLastAccessUser(String lastAccessUser) {
        LastAccessUser = lastAccessUser;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public List<String> dataList(){
        List<String> list = new ArrayList<String>();
        list.add(Hawb);
        list.add(ClearanceCategory);
        list.add(Type);
        list.add(LastAccessDtm);
        list.add(LastAccessUser);
        list.add(Comment);
        return list;
    }

    public List<String> titleList(){
        List<String> titleList = new ArrayList<String>();
        titleList.add("Hawb");
        titleList.add("ClearanceCategory");
        titleList.add("Type");
        titleList.add("LastAccessDtm");
        titleList.add("LastAccessUser");
        titleList.add("Comment");
        return titleList;
    }
}

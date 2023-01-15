package com.dhl.yxg.data;

public class ResultMessage {
    private String ok;
    private String error;

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        String res = "";
        if (ok != null && !ok.equals("")) {
            res = ok;
        }
        if (error != null && !error.equals("")) {
            res = error;
        }
        return res;
    }
}

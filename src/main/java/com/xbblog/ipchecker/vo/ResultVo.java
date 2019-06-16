package com.xbblog.ipchecker.vo;


public class ResultVo {

    private Boolean success;

    private ResultDataVo data;

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ResultDataVo getData() {
        return data;
    }

    public void setData(ResultDataVo data) {
        this.data = data;
    }
}

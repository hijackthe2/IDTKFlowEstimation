package com.example.idtk.model;


import java.util.List;

public class ReceiveModel {

    private String cmd;
    private List<String> data;
    private String flag;
    private String count;
    private String status;

    @Override
    public String toString(){
        return "ReceiveModel {cmd=" + cmd + ", data=" + data + ", flag=" + flag + ", count=" + count + ", status="
                + status + "}";
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

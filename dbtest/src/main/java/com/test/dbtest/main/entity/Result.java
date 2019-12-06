package com.test.dbtest.main.entity;

public class Result {
    private int problemid;
    private String userid;
    private int time;
    private String result="正在判题";

    public int getProblemid() {
        return problemid;
    }

    public void setProblemid(int problemid) {
        this.problemid = problemid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Result(int problemid, String userid, int time) {
        this.problemid = problemid;
        this.userid = userid;
        this.time = time;
        this.result = "正在判题";
    }
}

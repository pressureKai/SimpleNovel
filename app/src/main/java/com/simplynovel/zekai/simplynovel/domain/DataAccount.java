package com.simplynovel.zekai.simplynovel.domain;

/**
 * Created by 15082 on 2018/7/21.
 */

public class DataAccount {
    private String[] functionDes;
    private String[] rightDes;
    private int accountImgRes;

    public int getAccountImgRes() {
        return accountImgRes;
    }

    public void setAccountImgRes(int accountImgRes) {
        this.accountImgRes = accountImgRes;
    }

    public String[] getFunctionDes() {
        return functionDes;
    }

    public void setFunctionDes(String[] functionDes) {
        this.functionDes = functionDes;
    }

    public String[] getRightDes() {
        return rightDes;
    }

    public void setRightDes(String[] rightDes) {
        this.rightDes = rightDes;
    }
}

package com.smartxin.baselibrary.http.bean;

/**
 * Author: zhengwenxin
 * CreateDate  : 2021/7/23 11:31
 * Description: 网络请求基础返回公共类
 */
public class BaseResponseBean {
    /**
     * code:正确错误标志 200
     */
    private int errorCode;
    /**
     * msg:消息
     */
    private String errorMsg;

    public int getCode() {
        return errorCode;
    }

    public void setCode(int code) {
        this.errorCode = code;
    }

    public String getMsg() {
        return errorMsg;
    }

    public void setMsg(String msg) {
        this.errorMsg = msg;
    }

    public boolean isSuccess() {
        return 200 == getCode()  ||  0 == getCode();
    }

}

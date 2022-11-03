package com.smartxin.basemodule.bean;

import java.util.ArrayList;

public class ApiBaseLiveData<T> {

    private ArrayList<T> listData ;   //列表数据

    private boolean isEmpty;   //是否空数据

    private boolean isRefresh;   // 是否刷新

    private int page;  // 页码

    public ApiBaseLiveData(ArrayList<T> listData, boolean isEmpty,boolean isRefresh) {
        this.listData = listData;
        this.isEmpty = isEmpty;
        this.isRefresh = isRefresh;
    }

    public ArrayList<T> getListData() {
        return listData;
    }

    public void setListData(ArrayList<T> listData) {
        this.listData = listData;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public boolean isFirstEmpty() {
        return isRefresh && isEmpty;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}

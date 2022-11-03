package com.smartxin.basemodule.bean;

import com.smartxin.basemodule.constants.BaseConstants;

public class PageBean {
    //页码
    private int page = 1;

    //计数
    private int count = BaseConstants.PAGE_SIZE;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

package com.bobo.dto;

public class BasePage {
    //当前页，默认第一页
    protected int pageNum = 1;
    //每页显示的条数,默认每页3条
    protected int pageSize = 5;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}

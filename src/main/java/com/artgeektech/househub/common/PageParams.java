package com.artgeektech.househub.common;

/**
 * Created by guang on 7:48 AM 5/2/18.
 */
public class PageParams {
    private static final Integer DEFAULT_PAGE_SIZE = 2;
    private static final Integer DEFAULT_PAGE_NUM = 1;
    private Integer pageSize;
    private Integer pageNum;
    private Integer offset;
    private Integer limit;

    public static PageParams build(Integer pageSize, Integer pageNum){
        if (pageSize == null) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        if (pageNum == null) {
            pageNum = DEFAULT_PAGE_NUM;
        }
        return new PageParams(pageSize, pageNum);
    }

    public PageParams(){
        this(DEFAULT_PAGE_SIZE, DEFAULT_PAGE_NUM);
    }

    public PageParams(Integer pageSize, Integer pageNum){
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.offset = pageSize * (pageNum - 1);
        this.limit = pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}

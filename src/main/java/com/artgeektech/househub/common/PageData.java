package com.artgeektech.househub.common;

import java.util.List;

/**
 * Created by guang on 8:28 AM 5/2/18.
 */
public class PageData<T> {
    private List<T> list;
    private Pagination pagination;//pageNum, pageSize, page list

    public PageData(Pagination pagination, List<T> list){
        this.pagination = pagination;
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public static <T> PageData<T> buildPage(List<T> list, Long totalCount,
                                            Integer pageSize, Integer pageNum) {
        return new PageData<>(new Pagination(pageSize, pageNum, totalCount), list);
    }
}

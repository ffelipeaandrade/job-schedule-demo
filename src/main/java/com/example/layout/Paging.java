package com.example.layout;

import java.util.ArrayList;
import java.util.List;

public class Paging {

    private static final int PAGINATION_STEP = 3;

    private int pageSize;
    private int pageNumber;
    private List<PageItem> items = new ArrayList<>();

    public void addPageItems(int from, int to, int pageNumber) {
        for (int i = from; i < to; i++) {
            PageItem pi = new PageItem();
            pi.setIndex(i);
            items.add(pi);
        }
    }
    public static Paging of(int totalPages, int pageNumber, int pageSize) {
        Paging paging = new Paging();
        paging.setPageSize(pageSize);
        paging.setPageNumber(pageNumber);
        paging.addPageItems(1, totalPages + 1, pageNumber);
        return paging;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public List<PageItem> getItems() {
        return items;
    }

    public void setItems(List<PageItem> items) {
        this.items = items;
    }
}

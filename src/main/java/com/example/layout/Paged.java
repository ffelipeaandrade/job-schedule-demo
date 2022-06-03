package com.example.layout;

import org.springframework.data.domain.Page;

import java.util.Optional;

public class Paged<T> {

    private Page<T> page;

    private Paging paging;

    public Paged() {
    }

    public Paged(Page<T> postPage, Paging of) {
        this.page = postPage;
        this.paging = of;
    }

    public Page<T> getPage() {
        return page;
    }

    public void setPage(Page<T> page) {
        this.page = page;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }
}
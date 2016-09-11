package com.mmihaylov.rest.dto;

import java.util.List;

/* Page of some entities. */
public class PageOfEntitiesDto<T> {

    private int page;

    private int pageSize;

    private int numOfPages;

    private long numOfEntities;

    private List<T> entities;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getNumOfPages() {
        return numOfPages;
    }

    public void setNumOfPages(int numOfPages) {
        this.numOfPages = numOfPages;
    }

    public long getNumOfEntities() {
        return numOfEntities;
    }

    public void setNumOfEntities(long numOfEntities) {
        this.numOfEntities = numOfEntities;
    }

    public List<T> getEntities() {
        return entities;
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }
}

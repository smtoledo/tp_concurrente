package com.concurrente.trabajopractico.model;

import java.util.ArrayList;
import java.util.List;

public class WorkerResult {

    private long id;
    private String workerId;
    private double searchTime;
    private List<FileResult> filesSearch;
    private List<FileResult> filesSearch10plus;

    public WorkerResult() {
        this.filesSearch = new ArrayList<>();
        this.filesSearch10plus = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public double getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(double searchTime) {
        this.searchTime = searchTime;
    }

    public List<FileResult> getFilesSearch() {
        return filesSearch;
    }

    public void setFilesSearch(List<FileResult> filesSearch) {
        this.filesSearch = filesSearch;
    }

    public void addFilesSearch(FileResult filesSearch) {
        if (filesSearch.getOcurrencies()>10)
            this.filesSearch10plus.add(filesSearch);
        else
            this.filesSearch.add(filesSearch);
    }

    public List<FileResult> getFilesSearch10plus() {
        return filesSearch10plus;
    }

    public void setFilesSearch10plus(List<FileResult> filesSearch10plus) {
        this.filesSearch10plus = filesSearch10plus;
    }

    public void addFilesSearch10plus(FileResult filesSearch10plus) {
        this.filesSearch10plus.add(filesSearch10plus);
    }
}

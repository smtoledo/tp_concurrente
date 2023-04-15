package com.concurrente.trabajopractico.model;

import java.util.List;

public class Result {

    private long id;
    private String workerId;
    private Integer keywordsFound;
    private List<String> documentsAL10kw;
    private List<FileResult> filesResults;

    public Result() {
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

    public Integer getKeywordsFound() {
        return keywordsFound;
    }

    public void setKeywordsFound(Integer keywordsFound) {
        this.keywordsFound = keywordsFound;
    }

    public List<String> getDocumentsAL10kw() {
        return documentsAL10kw;
    }

    public void setDocumentsAL10kw(List<String> documentsAL10kw) {
        this.documentsAL10kw = documentsAL10kw;
    }

    public List<FileResult> getFilesResults() {
        return filesResults;
    }

    public void setFilesResults(List<FileResult> filesResults) {
        this.filesResults = filesResults;
    }

}

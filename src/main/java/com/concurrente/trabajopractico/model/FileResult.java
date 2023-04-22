package com.concurrente.trabajopractico.model;

public class FileResult {

    private long id;
    private String documentName;
    private Integer ocurrencies;
    private double searchTime;

    public FileResult() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Integer getOcurrencies() {
        return ocurrencies;
    }

    public void setOcurrencies(Integer ocurrencies) {
        this.ocurrencies = ocurrencies;
    }

    public double getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(double searchTime) {
        this.searchTime = searchTime;
    }

}

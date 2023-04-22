package com.concurrente.trabajopractico.model;

import java.util.List;

public class Result {

    private long id;
    private List<WorkerResult> workersResult;
    private double totalSearchTime;

    public Result() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<WorkerResult> getWorkersResult() {
        return workersResult;
    }

    public void setWorkersResult(List<WorkerResult> workersResult) {
        this.workersResult = workersResult;
    }

    public double getTotalSearchTime() {
        return totalSearchTime;
    }

    public void setTotalSearchTime(double totalSearchTime) {
        this.totalSearchTime = totalSearchTime;
    }

}

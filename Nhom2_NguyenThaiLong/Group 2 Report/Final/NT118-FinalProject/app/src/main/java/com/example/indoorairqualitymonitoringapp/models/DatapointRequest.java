package com.example.indoorairqualitymonitoringapp.models;

public class DatapointRequest {
    private Long fromTimestamp;
    private Long toTimestamp;

    private String fromTime;
    private String toTime;

    private String type = "";

    public DatapointRequest(Long fromTimestamp, Long toTimestamp) {
        this.fromTimestamp = fromTimestamp;
        this.toTimestamp = toTimestamp;
    }

    public DatapointRequest(String fromTime, String toTime) {
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    public Long getFromTimestamp() {
        return fromTimestamp;
    }

    public void setFromTimestamp(Long fromTimestamp) {
        this.fromTimestamp = fromTimestamp;
    }

    public Long getToTimestamp() {
        return toTimestamp;
    }

    public void setToTimestamp(Long toTimestamp) {
        this.toTimestamp = toTimestamp;
    }
}

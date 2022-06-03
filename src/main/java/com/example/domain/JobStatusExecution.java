package com.example.domain;

public enum JobStatusExecution {

    FAIL("fail"),
    SUCCESS("success");

    private final String status;

    private JobStatusExecution(final String status) {
        this.status = status;
    }

    public String getName() {
        return status;
    }
}

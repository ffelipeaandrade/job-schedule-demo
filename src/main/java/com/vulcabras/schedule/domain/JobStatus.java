package com.vulcabras.schedule.domain;

public enum JobStatus {

    SCHEDULED("scheduled"),
    PAUSED("paused");

    private final String status;

    private JobStatus(final String status) {
        this.status = status;
    }

    public String getName() {
        return status;
    }
}


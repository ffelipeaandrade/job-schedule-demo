package com.vulcabras.schedule.domain;

import java.io.Serializable;
import java.util.Objects;

public class JobRequest implements Serializable {

    private String id;
    private String name;
    private String group;
    private JobStatus status;
    private String cronExpression;
    private String description;
    private String urlTarget;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlTarget() {
        return urlTarget;
    }

    public void setUrlTarget(String urlTarget) {
        this.urlTarget = urlTarget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobRequest that = (JobRequest) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(group, that.group) && Objects.equals(status, that.status) && Objects.equals(cronExpression, that.cronExpression) && Objects.equals(description, that.description) && Objects.equals(urlTarget, that.urlTarget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, group, status, cronExpression, description, urlTarget);
    }

    @Override
    public String toString() {
        return "JobRequest{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", group='" + group + '\'' +
                ", status=" + status +
                ", cronExpression='" + cronExpression + '\'' +
                ", description='" + description + '\'' +
                ", urlTarget='" + urlTarget + '\'' +
                '}';
    }
}

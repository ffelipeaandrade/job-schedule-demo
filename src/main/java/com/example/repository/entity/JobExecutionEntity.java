package com.example.repository.entity;

import com.example.domain.JobStatusExecution;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "job_execution")
public class JobExecutionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String jobId;

    private String jobName;

    private String jobGroup;

    private LocalDateTime lastExecution;

    private JobStatusExecution statusExecution;

    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public LocalDateTime getLastExecution() {
        return lastExecution;
    }

    public void setLastExecution(LocalDateTime lastExecution) {
        this.lastExecution = lastExecution;
    }

    public JobStatusExecution getStatusExecution() {
        return statusExecution;
    }

    public void setStatusExecution(JobStatusExecution statusExecution) {
        this.statusExecution = statusExecution;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }
}

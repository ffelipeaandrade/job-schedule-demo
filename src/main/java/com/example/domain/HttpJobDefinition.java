package com.example.domain;

import com.example.repository.JobExecutionRepository;
import com.example.repository.entity.JobExecutionEntity;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

public class HttpJobDefinition extends JobDefinition {

    @Autowired
    private RestTemplate restTemplate;

    private String url;

    @Autowired
    JobExecutionRepository jobExecutionRepository;

    @Override
    public void executeJob(JobExecutionContext context) {

        JobExecutionEntity executionEntity = new JobExecutionEntity();
        executionEntity.setLastExecution(LocalDateTime.now());
        executionEntity.setJobId((String) context.getJobDetail().getJobDataMap().get("jobId"));
        executionEntity.setJobName((String) context.getJobDetail().getJobDataMap().get("jobName"));
        executionEntity.setJobGroup((String) context.getJobDetail().getJobDataMap().get("jobGroup"));

        try {
            String urlTarget =  (String) context.getJobDetail().getJobDataMap().get("urlTarget");
            restTemplate.getForEntity(urlTarget, Object.class);
            executionEntity.setStatusExecution(JobStatusExecution.SUCCESS);
            executionEntity.setMessage(HttpStatus.OK.name());
        } catch (HttpStatusCodeException e){
            logger.error("Erro na execução do Job: {}", context.getJobDetail().getKey());
            executionEntity.setStatusExecution(JobStatusExecution.FAIL);
            executionEntity.setMessage(e.getMessage());
        }
        jobExecutionRepository.save(executionEntity);
    }

    @Override
    public String jobClass() {
        return HttpJobDefinition.class.getName();
    }

    @Override
    public Class<? extends QuartzJobBean> instance() {
        return this.getClass();
    }
}

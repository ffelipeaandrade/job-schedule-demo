package com.example.domain;

import com.example.repository.JobExecutionRepository;
import com.example.repository.JobRepository;
import com.example.repository.entity.JobEntity;
import com.example.repository.entity.JobExecutionEntity;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class JobScheduleService {

    private Logger logger = LoggerFactory.getLogger(JobScheduleService.class);

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobExecutionRepository jobExecutionRepository;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private JobScheduleFactory jobScheduleFactory;

    @Transactional
    public void scheduleNewJob(JobRequest jobRequest) throws Exception {
        try {
            if (!StringUtils.hasText(jobRequest.getUrlTarget())){
                throw new Exception("Url Target can't be null");
            }
            jobRequest.setId(UUID.randomUUID().toString());
            schedulerFactoryBean.getScheduler().scheduleJob(this.createJob(jobRequest), this.createTrigger(jobRequest));
            JobEntity jobEntity = this.saveJob(jobRequest);
            logger.info("Job = [" + jobEntity.getName() + "]" + " created, with Id = [" + jobEntity.getIdJob() + "]");
        } catch (Exception e) {
            logger.error("Error to create job - {}", jobRequest.getName(), e);
            throw new Exception(e);
        }
    }

    public void deleteJob(String jobId) throws Exception {
        try {
            if (!jobRepository.existsByIdJob(jobId)){
                logger.error("Error to delete job - {}", jobId, "Job doens't exists to delete");
                throw new Exception("Job doens't exists to delete");
            }
            JobEntity jobEntity = jobRepository.findByIdJob(jobId);
            schedulerFactoryBean.getScheduler().deleteJob(new JobKey(jobEntity.getName(), jobEntity.getGroupName()));
            jobRepository.delete(jobEntity);
            logger.info("Job = [" + jobEntity.getName() + "]" + " deleted.");
        } catch (SchedulerException e) {
            logger.error("Failed to delete job - {}", jobId, e);
            throw new Exception("Failed to delete job", e);
        }
    }

    private JobEntity saveJob(JobRequest jobRequest) {
        JobEntity jobEntity = new JobEntity();
        jobEntity.setIdJob(jobRequest.getId());
        jobEntity.setName(jobRequest.getName());
        jobEntity.setDescription(jobRequest.getDescription());
        jobEntity.setCronExpression(jobRequest.getCronExpression());
        jobEntity.setCreationDate(LocalDateTime.now());
        jobEntity.setUrlTarget(jobRequest.getUrlTarget());
        jobEntity.setStatus(JobStatus.SCHEDULED);
        jobEntity.setGroupName(jobRequest.getGroup());
        jobRepository.save(jobEntity);
        return jobEntity;
    }

    private Trigger createTrigger(JobRequest jobRequest) {
        return jobScheduleFactory.createCronTrigger(jobRequest.getName(), new Date(),
                jobRequest.getCronExpression(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
    }

    private JobDetail createJob(JobRequest jobRequest) {
        return jobScheduleFactory.createJob(new HttpJobDefinition().instance(), context, jobRequest, this.getJobDataMap(jobRequest));
    }

    private JobDataMap getJobDataMap(JobRequest jobRequest) {
        JobDataMap jobData = new JobDataMap();
        jobData.put("urlTarget", jobRequest.getUrlTarget());
        jobData.put("jobName", jobRequest.getName());
        jobData.put("jobGroup", jobRequest.getGroup());
        jobData.put("jobId", jobRequest.getId());
        return jobData;
    }

    public List<JobEntity> listAllJobs() {
        return jobRepository.findAll();
    }

    public List<JobExecutionEntity> listAllExecutions() {
        return jobExecutionRepository.findAll();
    }

    public void removeAllExecutions() {
        jobExecutionRepository.deleteAllInBatch();
    }

    public Page<JobExecutionEntity> findAllPeageable(PageRequest page){
        return jobExecutionRepository.findAll(page);
    }
}

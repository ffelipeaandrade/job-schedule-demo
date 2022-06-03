package com.vulcabras.schedule.domain;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public abstract class JobDefinition extends QuartzJobBean {

    Logger logger = LoggerFactory.getLogger(JobDefinition.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        this.executeJob(context);
    }

    public abstract void executeJob(JobExecutionContext context);

    public abstract String jobClass();

    public abstract Class<? extends QuartzJobBean> instance();
}

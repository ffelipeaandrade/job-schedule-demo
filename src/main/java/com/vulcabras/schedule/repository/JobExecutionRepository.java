package com.vulcabras.schedule.repository;

import com.vulcabras.schedule.repository.entity.JobExecutionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobExecutionRepository extends JpaRepository<JobExecutionEntity, Long> {

    Page<JobExecutionEntity> findAll(Pageable page);

}

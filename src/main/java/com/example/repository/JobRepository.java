package com.example.repository;

import com.example.repository.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, Long> {

    public JobEntity findByIdJob(String idJob);

    public boolean existsByIdJob(String idJob);

}

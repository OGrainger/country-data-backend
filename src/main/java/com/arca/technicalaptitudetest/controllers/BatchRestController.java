package com.arca.technicalaptitudetest.controllers;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@org.springframework.web.bind.annotation.RestController

@Controller
public class BatchRestController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    Job job;

    @RequestMapping("/import")
    public ResponseEntity startImport() throws Exception {
        if (jobRepository.isJobInstanceExists("importCountryDataJob", new JobParameters())) {
            JobExecution jobExecution = jobRepository.getLastJobExecution("importCountryDataJob", new JobParameters());
            assert jobExecution != null;
            if (jobExecution.getStatus().equals(BatchStatus.STARTED)) {
                jobExecution.stop();
                return null;
            }
        }
        jobLauncher.run(job, new JobParameters());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    /*@RequestMapping("/import-info")
    public List<Long> getImportInfo() throws Exception {
        return jobOperator.getJobInstances("importCountryDataJob", 0, 50);
    }*/
}

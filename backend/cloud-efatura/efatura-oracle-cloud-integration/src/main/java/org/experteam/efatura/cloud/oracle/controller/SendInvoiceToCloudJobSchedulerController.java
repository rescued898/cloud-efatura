package org.experteam.efatura.cloud.oracle.controller;

import lombok.extern.slf4j.Slf4j;
import org.experteam.efatura.cloud.oracle.job.SendInvoiceToCloudJob;
import org.experteam.efatura.cloud.oracle.payload.CloudResponse;
import org.experteam.efatura.cloud.oracle.payload.SendInvoiceToCloudRequest;
import org.experteam.efatura.quartz.domain.SchedulerJobInfo;
import org.experteam.efatura.quartz.service.SchedulerService;
import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
public class SendInvoiceToCloudJobSchedulerController {
    @Autowired
    private SchedulerService schedulerService;

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/sendInvoiceToCloud")
    public ResponseEntity<CloudResponse> sendInvoiceToCloud(@RequestBody SendInvoiceToCloudRequest sendInvoiceRequest) {
        try {
            log.info("sendInvoiceRequest: " + sendInvoiceRequest.toString());
            log.info("UUID: " + sendInvoiceRequest.getUuid());

            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put(SendInvoiceToCloudJob.UUID, sendInvoiceRequest.getUuid());

            SchedulerJobInfo jobInfo = SchedulerJobInfo.builder()
                    .id(UUID.randomUUID().toString())
                    .jobClass("org.experteam.efatura.cloud.oracle.job.SendInvoiceToCloudJob")
                    .jobName("send-invoice-to-cloud")
                    .jobGroup("efatura-cloud-jobs")
                    .cronJob(false)
                    .noRepeat(true)
                    .jobDataMap(jobDataMap)
                    .build();

            schedulerService.scheduleNewJob(jobInfo);

            CloudResponse sendInvoiceResponse = new CloudResponse(true,
                    jobInfo.getJobName(), jobInfo.getJobGroup(), "Job Started Successfully!");
            return ResponseEntity.ok(sendInvoiceResponse);
        } catch (Exception ex) {
            log.error("Error starting send invoice job", ex);

            CloudResponse sendInvoiceResponse = new CloudResponse(false,
                    "Error starting send invoice. Please try later!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(sendInvoiceResponse);
        }
    }

}

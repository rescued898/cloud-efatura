package org.experteam.efatura.quartz;

import lombok.extern.slf4j.Slf4j;
import org.experteam.efatura.TestContextConfiguration;
import org.experteam.efatura.quartz.config.SchedulerConfig;
import org.experteam.efatura.quartz.domain.SchedulerJobInfo;
import org.experteam.efatura.quartz.service.SchedulerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestContextConfiguration.class)
@ContextConfiguration(classes = SchedulerConfig.class)
public class CloudInvoiceQuartzSimpleJobTests {

    private SchedulerJobInfo jobInfo;

    @Autowired
    private SchedulerService schedulerService;

    @Before
    public void setUp() {
        jobInfo = SchedulerJobInfo.builder()
                .id(UUID.randomUUID().toString())
                .jobClass("org.experteam.efatura.quartz.jobs.SendInvoiceToCloudJob")
                .jobName("send-new-invoices-simple")
                .jobGroup("efatura-cloud-jobs")
                .cronJob(false)
                .repeatTime(10000L)
                .build();
    }

    @Test
    public void testScheduleNewJob() throws InterruptedException, SchedulerException {
        schedulerService.scheduleNewJob(jobInfo);

        Thread.sleep(20000);
    }

    @After
    public void cleanUp() {
        boolean deleted = schedulerService.deleteJob(jobInfo);
        if (deleted) {
            log.info(jobInfo.getJobName() + " deleted.");
        } else {
            log.info(jobInfo.getJobName() + " cannot be deleted.");
        }
    }
}

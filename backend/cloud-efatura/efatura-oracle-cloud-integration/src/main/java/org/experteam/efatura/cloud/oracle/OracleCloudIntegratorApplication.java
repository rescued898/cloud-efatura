package org.experteam.efatura.cloud.oracle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Slf4j
@SpringBootApplication//(exclude={DataSourceAutoConfiguration.class})
@EnableMongoRepositories({"org.experteam.efatura.cloud.oracle.repository.mongodb"})
public class OracleCloudIntegratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(OracleCloudIntegratorApplication.class, args);
    }

    /*@Override
    public void run(String... args) throws Exception {
        jobInfo = SchedulerJobInfo.builder()
                .id(UUID.randomUUID().toString())
                .jobClass("org.experteam.efatura.cloud.oracle.job.GetIncomingInvoicesJob")
                .jobName("get-unprocessed-invoices-cron")
                .jobGroup("efatura-cloud-jobs")
                .cronJob(true)
                .cronExpression(cronExpression)
                .build();

        schedulerService.scheduleNewJob(jobInfo);
    }

    @PreDestroy
    public void doSomethingAfterStartup() {
        boolean deleted = schedulerService.deleteJob(jobInfo);
        if (deleted) {
            log.info(jobInfo.getJobName() + " deleted.");
        } else {
            log.info(jobInfo.getJobName() + " cannot be deleted.");
        }
    }*/
}

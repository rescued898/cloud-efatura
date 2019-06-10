package org.experteam.efatura.quartz.config;

import org.experteam.efatura.quartz.component.JobScheduleCreator;
import org.experteam.efatura.quartz.component.SchedulerJobFactory;
import org.experteam.efatura.quartz.service.SchedulerService;
import org.experteam.efatura.quartz.service.impl.SchedulerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Properties;

@Configuration
public class SchedulerConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${spring.quartz.properties.org.quartz.jobStore.class}")
    private String className;

    @Value("${spring.quartz.properties.org.quartz.jobStore.mongoUri}")
    private String mongoUri;

    @Value("${spring.quartz.properties.org.quartz.jobStore.dbName}")
    private String dbName;

    @Value("${spring.quartz.properties.org.quartz.jobStore.collectionPrefix}")
    private String collectionPrefix;

    @Value("${spring.quartz.properties.org.quartz.jobStore.isClustered}")
    private String isClustered;

    @Value("${spring.quartz.properties.org.quartz.scheduler.instanceName}")
    private String instanceName;

    @Value("${spring.quartz.properties.org.quartz.scheduler.instanceId}")
    private String instanceId;

    @Value("${spring.quartz.properties.org.quartz.threadPool.threadCount}")
    private String threadCount;

    //@Autowired
    //private QuartzProperties quartzProperties;

    @Bean
    public JobScheduleCreator schedulerJobFactoryBean() {
        JobScheduleCreator jobScheduleCreator = new JobScheduleCreator();
        return jobScheduleCreator;
    }

    @Bean
    public SchedulerService schedulerServiceBean() {
        SchedulerService schedulerService = new SchedulerServiceImpl();
        return schedulerService;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerJobFactory jobFactory = new SchedulerJobFactory();
        jobFactory.setApplicationContext(applicationContext);


        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        factory.setJobFactory(jobFactory);
        // factory.setAutoStartup(false);

        Properties quartzProperties = new Properties();

        quartzProperties.setProperty("org.quartz.jobStore.class", className);
        quartzProperties.setProperty("org.quartz.jobStore.mongoUri", mongoUri);
        quartzProperties.setProperty("org.quartz.jobStore.dbName", dbName);
        quartzProperties.setProperty("org.quartz.jobStore.collectionPrefix", collectionPrefix);
        quartzProperties.setProperty("org.quartz.jobStore.isClustered", isClustered);

        quartzProperties.setProperty("org.quartz.scheduler.instanceName", instanceName);
        quartzProperties.setProperty("org.quartz.scheduler.instanceId", instanceId);
        quartzProperties.setProperty("org.quartz.threadPool.threadCount", threadCount);

        factory.setQuartzProperties(quartzProperties);

        // factory.setDataSource(dataSource);

        return factory;
    }

}

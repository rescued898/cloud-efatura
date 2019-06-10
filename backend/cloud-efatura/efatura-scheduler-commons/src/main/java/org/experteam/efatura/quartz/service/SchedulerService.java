package org.experteam.efatura.quartz.service;


import org.experteam.efatura.quartz.domain.SchedulerJobInfo;
import org.quartz.SchedulerException;

public interface SchedulerService {

    // void startAllSchedulers();

    void scheduleNewJob(SchedulerJobInfo jobInfo) throws SchedulerException;

    void updateScheduleJob(SchedulerJobInfo jobInfo);

    boolean unScheduleJob(String jobName);

    boolean deleteJob(SchedulerJobInfo jobInfo);

    boolean pauseJob(SchedulerJobInfo jobInfo);

    boolean resumeJob(SchedulerJobInfo jobInfo);

    boolean startJobNow(SchedulerJobInfo jobInfo);

}

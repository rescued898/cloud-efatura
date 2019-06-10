package org.experteam.efatura.quartz.domain;

import lombok.Builder;
import lombok.Getter;
import org.quartz.JobDataMap;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Document
public class SchedulerJobInfo {
    @Id
    private String id;
    private String jobName;
    private String jobGroup;
    private String jobClass;
    private String cronExpression;
    private boolean noRepeat;
    private Long repeatTime;
    private Boolean cronJob;
    private JobDataMap jobDataMap;
}

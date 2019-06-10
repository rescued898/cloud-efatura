package org.experteam.efatura.repository;

import org.experteam.efatura.quartz.domain.SchedulerJobInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuartzSchedulerRepository extends MongoRepository<SchedulerJobInfo, String> {
}

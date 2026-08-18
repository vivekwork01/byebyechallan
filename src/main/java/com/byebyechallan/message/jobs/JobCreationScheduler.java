package com.byebyechallan.message.jobs;

import com.byebyechallan.entity.UserDocumentTEntity;
import com.byebyechallan.message.entity.CoreJobSchedule;
import com.byebyechallan.message.repository.JobScheduleRepository;
import com.byebyechallan.repository.UserDocumentRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobCreationScheduler {

  private final UserDocumentRepository userDocumentRepository;

  private final JobScheduleRepository jobScheduleRepository;


  private static final Logger LOGGER = LoggerFactory.getLogger(JobCreationScheduler.class);

  public JobCreationScheduler(UserDocumentRepository userDocumentRepository,
      JobScheduleRepository jobScheduleRepository) {
    this.userDocumentRepository = userDocumentRepository;
    this.jobScheduleRepository = jobScheduleRepository;
  }


  @Scheduled(fixedDelay = 10000)
  public void createScheduler() {
    LOGGER.info("JobCreationScheduler: createScheduler() method invoked at {}",
        System.currentTimeMillis());
    try {
      List<UserDocumentTEntity> expiringDocuments = userDocumentRepository.findAllExpiringInMonth(
          LocalDateTime.now().minusMonths(1), LocalDateTime.now());
      // Process the expiring documents as needed
      for (UserDocumentTEntity expiringDocument : expiringDocuments) {
        List<CoreJobSchedule> schedulers = expiringDocument.getScheduler();
        if (schedulers != null) {
          for (CoreJobSchedule coreJobSchedule : schedulers) {
            jobScheduleRepository.save(coreJobSchedule);
          }
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    LOGGER.info("JobCreationScheduler: createScheduler() method completed at {}",
        System.currentTimeMillis());
  }
}
package com.byebyechallan.message.jobs;


import com.byebyechallan.NotificationChannel;
import com.byebyechallan.message.dto.NotificationEvent;
import com.byebyechallan.message.entity.CoreJobSchedule;
import com.byebyechallan.message.publisher.NotificationPublisher;
import com.byebyechallan.message.repository.JobScheduleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobExecutionScheduler {

  private static final Logger LOGGER = LoggerFactory.getLogger(JobScheduleRepository.class);
  private static final ObjectMapper objectMapper = new ObjectMapper();

  private final JobScheduleRepository jobScheduleRepository;
  private final NotificationPublisher notificationPublisher;

  public JobExecutionScheduler(JobScheduleRepository jobScheduleRepository,
      NotificationPublisher notificationPublisher) {
    this.jobScheduleRepository = jobScheduleRepository;
    this.notificationPublisher = notificationPublisher;
  }


  @Scheduled(fixedDelay = 10000)
  public void runScheduledJobs() {
    LOGGER.info("Running scheduled jobs...");
    try {
      List<CoreJobSchedule> jobSchedules = jobScheduleRepository.findAllCreatedAndNotDeleted();
      for (CoreJobSchedule jobSchedule : jobSchedules) {
        if (NotificationChannel.EMAIL.equals(jobSchedule.getChannel())) {
          notificationPublisher.publishEmailNotification(
              objectMapper.readValue(jobSchedule.getNotificationEvent(), NotificationEvent.class));
        } else if (NotificationChannel.SMS.equals(jobSchedule.getChannel())) {
          notificationPublisher.publishSmsNotification(
              objectMapper.readValue(jobSchedule.getNotificationEvent(), NotificationEvent.class));
        } else if (NotificationChannel.WHATSAPP.equals(jobSchedule.getChannel())) {
          notificationPublisher.publishWhatsappNotification(
              objectMapper.readValue(jobSchedule.getNotificationEvent(), NotificationEvent.class));
        } else {
          LOGGER.warn("Unknown channel: {}", jobSchedule.getChannel());

        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}

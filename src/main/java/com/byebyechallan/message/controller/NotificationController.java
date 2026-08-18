package com.byebyechallan.message.controller;

import com.byebyechallan.message.dto.NotificationEvent;
import com.byebyechallan.message.publisher.NotificationPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

  private final NotificationPublisher notificationPublisher;


  public NotificationController(NotificationPublisher notificationPublisher) {
    this.notificationPublisher = notificationPublisher;
  }

  @PostMapping("/email")
  public void sendEmailNotification(@RequestBody NotificationEvent message) {
    notificationPublisher.publishEmailNotification(message);
  }

  @PostMapping("/sms")
  public void sendSmsNotification(@RequestBody NotificationEvent message) {
    notificationPublisher.publishSmsNotification(message);
  }

  @PostMapping("/whatsapp")
  public void sendWhatsappNotification(@RequestBody NotificationEvent message) {
    notificationPublisher.publishWhatsappNotification(message);
  }
}

package com.byebyechallan.message.publisher;

import com.byebyechallan.message.dto.NotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NotificationPublisher {

  private final RabbitTemplate rabbitTemplate;
  private final String exchange;
  private final String emailRoutingKey;
  private final String smsRoutingKey;
  private final String whatsappRoutingKey;


  private static final Logger LOGGER = LoggerFactory.getLogger(NotificationPublisher.class);


  public NotificationPublisher(RabbitTemplate rabbitTemplate,
      @Value("${rabbitmq.exchange}") String exchange,
      @Value("${rabbitmq.email.routing-key}") String emailRoutingKey,
      @Value("${rabbitmq.sms.routing-key}") String smsRoutingKey,
      @Value("${rabbitmq.whatsapp.routing-key}") String whatsappRoutingKey) {
    this.rabbitTemplate = rabbitTemplate;
    this.exchange = exchange;
    this.emailRoutingKey = emailRoutingKey;
    this.smsRoutingKey = smsRoutingKey;
    this.whatsappRoutingKey = whatsappRoutingKey;
  }


  public void publishEmailNotification(NotificationEvent message) {
    publish(exchange, emailRoutingKey, message);
  }

  public void publishSmsNotification(NotificationEvent message) {
    publish(exchange, smsRoutingKey, message);
  }

  public void publishWhatsappNotification(NotificationEvent message) {
    publish(exchange, whatsappRoutingKey, message);
  }

  public void publish(String exchange, String routingKey, NotificationEvent message) {
    rabbitTemplate.convertAndSend(exchange, routingKey, message);
    LOGGER.info("Notification sent to exchange: {}, routingKey: {}, message: {}", exchange,
        routingKey, message);
  }
}

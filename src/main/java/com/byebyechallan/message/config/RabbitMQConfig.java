package com.byebyechallan.message.config;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


  @Value("${rabbitmq.exchange}")
  private String exchange;

  @Value("${rabbitmq.sms.routing-key}")
  private String smsRoutingKey;

  @Value("${rabbitmq.sms.queue}")
  private String smsQueue;

  @Value("${rabbitmq.email.routing-key}")
  private String emailRoutingKey;

  @Value("${rabbitmq.email.queue}")
  private String emailQueue;

  @Value("${rabbitmq.whatsapp.routing-key}")
  private String whatsappRoutingKey;

  @Value("${rabbitmq.whatsapp.queue}")
  private String whatsappQueue;


  @Bean
  public Queue getSmsQueue() {
    return new Queue(smsQueue, true);
  }

  @Bean
  public Queue getEmailQueue() {
    return new Queue(emailQueue, true);
  }

  @Bean
  public Queue getWhatsappQueue() {
    return new Queue(whatsappQueue, true);
  }


  @Bean
  public TopicExchange topicExchange() {
    return new TopicExchange(exchange);
  }

  @Bean
  public Binding smsBinding() {
    return BindingBuilder
        .bind(getSmsQueue())
        .to(topicExchange())
        .with(smsRoutingKey);
  }

  @Bean
  public Binding emailBinding() {
    return BindingBuilder
        .bind(getEmailQueue())
        .to(topicExchange())
        .with(emailRoutingKey);
  }

  @Bean
  public Binding whatsappBinding() {
    return BindingBuilder
        .bind(getWhatsappQueue())
        .to(topicExchange())
        .with(whatsappRoutingKey);
  }

  @Bean
  public MessageConverter messageConverter() {
    return new JacksonJsonMessageConverter();
  }

  @Bean
  public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(messageConverter());
    return rabbitTemplate;
  }

}

package com.shopyOrderservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.shopybaseDomainService.dto.OrderEvent;

@Service
public class OrderProducer {
	
	private NewTopic topic;
	
	private static final Logger LOGGER=LoggerFactory.getLogger(OrderProducer.class);
	
	private KafkaTemplate<String,OrderEvent> kafkaTemplate;

	public OrderProducer(NewTopic topic, KafkaTemplate<String, OrderEvent> kafkaTemplate) {
		this.topic = topic;
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendMessage(OrderEvent ev) {
		LOGGER.info(String.format("Order event => %s", ev.toString()));
	
		// create message
		Message<OrderEvent> message=MessageBuilder
				.withPayload(ev)
				.setHeader(KafkaHeaders.TOPIC, topic.name())
				.build();
		kafkaTemplate.send(message);
	
	
	}

	
	
}

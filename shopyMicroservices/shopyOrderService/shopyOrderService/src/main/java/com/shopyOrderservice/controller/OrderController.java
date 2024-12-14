package com.shopyOrderservice.controller;

import org.apache.kafka.common.Uuid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopyOrderservice.kafka.OrderProducer;
import com.shopybaseDomainService.dto.Order;
import com.shopybaseDomainService.dto.OrderEvent;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
	
	
	private OrderProducer orderProducer;

	public OrderController(OrderProducer orderProducer) {
		this.orderProducer = orderProducer;
	}
	
	
	@PostMapping("/orders")
	public String placeOrder(@RequestBody Order o) {
		o.setOrderId(Uuid.randomUuid().toString());
		
		OrderEvent orderEvent=new OrderEvent();
		orderEvent.setStatus("PENDING");
		orderEvent.setMessage("order status is in pending status");
		orderEvent.setOrder(o);
		
		orderProducer.sendMessage(orderEvent);
		
		return "Order placed successfully";
		
	}

	
	
}

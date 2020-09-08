package com.wynk.assignment.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wynk.assignment.Requests.OrderRequests.OrderRequest;
import com.wynk.assignment.entities.DeliveryPerson;
import com.wynk.assignment.entities.OrderDetail;
import com.wynk.assignment.repositories.OrderRepository;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	@Autowired
	private OrderRepository orderRepository;

	@Value("${delivery.service.host}")
	private String host;

	@Value("${delivery.service.port}")
	private String port;

	@Autowired
	RestTemplate restTemplate;

	@Override
	public OrderDetail saveOrder(OrderRequest orderRequest) {
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setStatus("recieved");
		orderRepository.save(orderDetail);
		return orderDetail;
	}

	@Override
	public OrderDetail getOrderStatus(Integer orderId) {
		Optional<OrderDetail> orderDetail = orderRepository.findById(orderId);
		return orderDetail.get();
	}

	@Override
	public OrderDetail updateOrderStatus(Integer orderId, String status) {
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setOrderId(orderId);
		orderDetail.setStatus(status);
		orderRepository.save(orderDetail);
		return orderDetail;
	}

	@Override
	public List<DeliveryPerson> activeDeliverPerson() {
		// uri for delivery service
		String uri = host + ":" + port + "/" + "activeDeliverPerson";
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		ResponseEntity<List<DeliveryPerson>> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<DeliveryPerson>>() {
				});
		return responseEntity.getBody();
	}

}

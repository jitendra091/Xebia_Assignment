package com.wynk.assignment.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wynk.assignment.ExceptionHandler.OrderNotFoundException;
import com.wynk.assignment.Requests.OrderRequests.OrderRequest;
import com.wynk.assignment.Responses.OrderResponse;
import com.wynk.assignment.entities.DeliveryPerson;
import com.wynk.assignment.entities.OrderDetail;
import com.wynk.assignment.services.RestaurantService;

@RestController
@RequestMapping(value = "/restaurant")
public class RestaurantController {

	private static final Logger logger = LoggerFactory.getLogger(RestaurantController.class);
	@Autowired
	private RestaurantService restaurantService;

	/*
	 * endpoint for place new order
	 */
	@RequestMapping(value = "/placeOrder", method = RequestMethod.POST)
	public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest) {

		OrderDetail orderDetail = restaurantService.saveOrder(orderRequest);
		OrderResponse orderResponse = new OrderResponse();
		orderResponse.setOrderId(orderDetail.getOrderId());
		orderResponse.setStatus(orderDetail.getStatus());
		return new ResponseEntity<OrderResponse>(orderResponse, HttpStatus.OK);
	}

	/*
	 * endpoint for getting status of the order
	 */

	@RequestMapping(value = "/status/{orderId}", method = RequestMethod.GET)
	public ResponseEntity<String> placeOrder(@PathVariable("orderId") Integer orderId) {

		OrderDetail orderDetail = restaurantService.getOrderStatus(orderId);
		if (orderDetail == null) {
			logger.error("No any order found for orderId" + orderId);
			throw new OrderNotFoundException("Order for orderId: " + orderId + " not found");
		}
		return new ResponseEntity<String>(orderDetail.getStatus(), HttpStatus.OK);
	}

	/*
	 * endpoint to update status of the order.
	 * 
	 */

	@RequestMapping(value = "/status/{orderId}", method = RequestMethod.PATCH)
	public ResponseEntity<String> placeOrder(@PathVariable("orderId") Integer orderId,
			@RequestParam("status") String status) {
		OrderDetail orderDetail = restaurantService.updateOrderStatus(orderId, status);
		if (orderDetail == null) {
			logger.error("No any order found for orderId" + orderId);
			throw new OrderNotFoundException("Order for orderId: " + orderId + " not found");
		}
		return new ResponseEntity<String>(orderDetail.getStatus(), HttpStatus.OK);
	}

	/*
	 * endpoint to get the detail of all active delivery person
	 */
	@RequestMapping(value = "/activeDeliverPerson", method = RequestMethod.GET)
	public ResponseEntity<List<DeliveryPerson>> activeDeliverPerson() {
		List<DeliveryPerson> activeDeliveryPerson = restaurantService.activeDeliverPerson();
		return new ResponseEntity<List<DeliveryPerson>>(activeDeliveryPerson, HttpStatus.OK);
	}
}

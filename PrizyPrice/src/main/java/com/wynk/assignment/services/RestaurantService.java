package com.wynk.assignment.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wynk.assignment.Requests.OrderRequests.OrderRequest;
import com.wynk.assignment.entities.DeliveryPerson;
import com.wynk.assignment.entities.OrderDetail;

@Service
public interface RestaurantService {

	OrderDetail saveOrder(OrderRequest orderRequest);

	OrderDetail getOrderStatus(Integer orderId);

	OrderDetail updateOrderStatus(Integer orderId, String status);

	List<DeliveryPerson> activeDeliverPerson();

}

package com.wynk.assignment.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wynk.assignment.entities.OrderDetail;

@Repository
public interface OrderRepository extends CrudRepository<OrderDetail, Integer> {}

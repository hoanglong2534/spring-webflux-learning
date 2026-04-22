package com.webflux.demo.repository;

import com.webflux.demo.entity.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderRepository extends ReactiveCrudRepository<Order, Long> {
}

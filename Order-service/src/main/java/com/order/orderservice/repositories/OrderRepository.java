package com.order.orderservice.repositories;


import com.order.orderservice.model.Order;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order,Long> {
}

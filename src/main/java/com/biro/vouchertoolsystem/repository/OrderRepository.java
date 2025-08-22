package com.biro.vouchertoolsystem.repository;

import com.biro.vouchertoolsystem.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    Optional<Order> findById(Long orderId);
}

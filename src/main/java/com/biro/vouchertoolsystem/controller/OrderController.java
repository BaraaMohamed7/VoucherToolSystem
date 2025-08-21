package com.biro.vouchertoolsystem.controller;

import com.biro.vouchertoolsystem.Dtos.Request.OrderRequestDTO;
import com.biro.vouchertoolsystem.Dtos.Response.OrderVouchersResponseDTO;
import com.biro.vouchertoolsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
   private final OrderService  orderService;

   @Autowired
   public OrderController(OrderService orderService) {
       this.orderService = orderService;
   }

   @PostMapping("/new")
   public List<OrderVouchersResponseDTO> createNewOrder(@RequestBody OrderRequestDTO orderRequestDTO) throws Exception {
       return orderService.newOrder(orderRequestDTO.getProductId(), orderRequestDTO.getQuantity());
   }
}

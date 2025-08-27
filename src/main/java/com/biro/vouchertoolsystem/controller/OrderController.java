package com.biro.vouchertoolsystem.controller;

import com.biro.vouchertoolsystem.Dtos.Request.OrderRefundDTO;
import com.biro.vouchertoolsystem.Dtos.Request.OrderRequestDTO;
import com.biro.vouchertoolsystem.Dtos.Response.OrderResponseDTO;
import com.biro.vouchertoolsystem.Dtos.Response.OrderVouchersResponseDTO;
import com.biro.vouchertoolsystem.service.OrderService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
   private final OrderService  orderService;

   @Autowired
   public OrderController(OrderService orderService) {
       this.orderService = orderService;
   }

   @PostMapping
   public List<OrderVouchersResponseDTO> createNewOrder(@RequestBody OrderRequestDTO orderRequestDTO) throws Exception {
       return orderService.create(orderRequestDTO);
   }

   @PostMapping("/refund")
   public String refundOrder(@RequestBody OrderRefundDTO orderRefundDTO) throws Exception {
       return orderService.refund(orderRefundDTO);
   }

   @GetMapping
   public List<OrderResponseDTO> getAll() throws BadRequestException {
       return orderService.findAll();
   }

   @GetMapping("/{orderId}")
    public OrderResponseDTO getOrder(@PathVariable("orderId") Long orderId) throws Exception {
       return orderService.find(orderId);
   }
}

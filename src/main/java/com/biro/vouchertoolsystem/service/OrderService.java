package com.biro.vouchertoolsystem.service;

import com.biro.vouchertoolsystem.Dtos.Request.OrderRefundDTO;
import com.biro.vouchertoolsystem.Dtos.Request.OrderRequestDTO;
import com.biro.vouchertoolsystem.Dtos.Response.OrderResponseDTO;
import com.biro.vouchertoolsystem.Dtos.Response.OrderVouchersResponseDTO;
import com.biro.vouchertoolsystem.model.*;
import com.biro.vouchertoolsystem.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final VoucherRepository voucherRepository;
    private final VoucherBatchRepository voucherBatchRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;


    @Transactional
    public List<OrderVouchersResponseDTO> create(OrderRequestDTO requestDTO) throws Exception {
        User user = userRepository.findById(requestDTO.getUserId()).orElseThrow(() -> new BadRequestException("User not found"));
        Product product =  productRepository.findById(requestDTO.getProductId()).orElseThrow(() -> new BadRequestException("Product not found"));

        VoucherBatch batch = voucherBatchRepository.findVoucherBatchByProductId(requestDTO.getProductId()).orElseThrow(() -> new BadRequestException("Product not found"));
        Pageable limit = PageRequest.of(0, requestDTO.getQuantity());
        List<Long> availableVouchersByBatchId = voucherRepository.findAvailableVouchersByBatchId(batch.getId(), VoucherStatus.AVAILABLE, limit);
        if(availableVouchersByBatchId.size() <  requestDTO.getQuantity()) {
            throw new BadRequestException("No enough vouchers available");
        }

        Order newOrder = new Order();
        newOrder.setOrderStatus(OrderStatus.PENDING);
        newOrder.setUser(user);
        newOrder.setAmount(requestDTO.getQuantity() * product.getPriceAfter());
        Order savedOrder = orderRepository.save(newOrder);

        voucherRepository.updateVouchersStatusAndOrder(availableVouchersByBatchId, savedOrder.getId(), VoucherStatus.ASSIGNED);
        return voucherRepository.findVouchersByOrderId(savedOrder.getId()).stream().map(voucher->modelMapper.map(voucher, OrderVouchersResponseDTO.class)).toList();
    }

    @Transactional(readOnly = true)
    public OrderResponseDTO find(Long orderId) throws Exception {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new BadRequestException("Order not found"));
        return  modelMapper.map(order, OrderResponseDTO.class);
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> findAll() throws BadRequestException {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order->modelMapper.map(order, OrderResponseDTO.class)).toList();
    }

    @Transactional
    public String refund(OrderRefundDTO orderRefundDTO) throws BadRequestException {
        Order order = orderRepository.findById(orderRefundDTO.getOrderId()).orElseThrow((() -> new BadRequestException("Order Not Found")));
        List<Long> voucherIds = voucherRepository.findVouchersByOrderId(orderRefundDTO.getOrderId());
        voucherRepository.updateVouchersStatusAndOrder(voucherIds, order.getId(), VoucherStatus.REFUNDED);
        order.setOrderStatus(OrderStatus.REFUNDED);
        order.setUpdatedAt(new Date());
        orderRepository.save(order);
        return "Refund Successfully";
    }
}

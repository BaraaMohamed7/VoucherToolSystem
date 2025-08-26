package com.biro.vouchertoolsystem.service;

import com.biro.vouchertoolsystem.Dtos.Request.OrderRefundDTO;
import com.biro.vouchertoolsystem.Dtos.Request.OrderRequestDTO;
import com.biro.vouchertoolsystem.Dtos.Response.OrderResponseDTO;
import com.biro.vouchertoolsystem.Dtos.Response.OrderVouchersResponseDTO;
import com.biro.vouchertoolsystem.model.*;
import com.biro.vouchertoolsystem.repository.OrderRepository;
import com.biro.vouchertoolsystem.repository.UserRepository;
import com.biro.vouchertoolsystem.repository.VoucherBatchRepository;
import com.biro.vouchertoolsystem.repository.VoucherRepository;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final VoucherRepository voucherRepository;
    private final VoucherBatchRepository voucherBatchRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, VoucherRepository voucherRepository, VoucherBatchRepository voucherBatchRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.voucherRepository = voucherRepository;
        this.voucherBatchRepository = voucherBatchRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<OrderVouchersResponseDTO> newOrder(OrderRequestDTO requestDTO) throws Exception {
        VoucherBatch batch = voucherBatchRepository.findVoucherBatchByProductId(requestDTO.getProductId());
        Pageable limit = PageRequest.of(0, requestDTO.getQuantity());
        List<Long> availableVouchersByBatchId = voucherRepository.findAvailableVouchersByBatchId(batch.getId(), VoucherStatus.AVAILABLE, limit);
        if(availableVouchersByBatchId.isEmpty()) {
            throw new Exception("No Available Vouchers");
        }

        Order emptyOrder = new Order();
        emptyOrder.setOrderStatus(OrderStatus.Paid);
        emptyOrder.setUser(userRepository.findById(requestDTO.getUserId()).get());
        Order order = orderRepository.save(emptyOrder);
        batch.setTotalCount(batch.getTotalCount()-requestDTO.getQuantity());
        voucherBatchRepository.save(batch);
        voucherRepository.updateVouchersStatusAndOrder(availableVouchersByBatchId, order.getId(), VoucherStatus.ASSIGNED);
        return voucherRepository.findVouchersByOrderId(order.getId()).stream().map(voucher->modelMapper.map(voucher, OrderVouchersResponseDTO.class)).toList();
    }

    public OrderResponseDTO findById(Long orderId) throws Exception {
        Optional<Order> orderOptional= orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
        return  modelMapper.map(orderOptional.get(), OrderResponseDTO.class);
        } else {
            throw new BadRequestException("Order Not Found");
        }
    }

    public List<OrderResponseDTO> findAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order->modelMapper.map(order, OrderResponseDTO.class)).toList();
    }

    public String refundOrder(OrderRefundDTO orderRefundDTO) throws Exception {
        Optional<Order> orderOptional = Optional.ofNullable(orderRepository.findById(orderRefundDTO.getOrderId()).orElseThrow((() -> new BadRequestException("Order Not Found"))));
        List<Voucher> orderVouchers = voucherRepository.findVouchersByOrderId(orderRefundDTO.getOrderId());
        VoucherBatch batch = voucherBatchRepository.getVoucherBatchById(orderVouchers.get(0).getBatch().getId());
        for (Voucher voucher : orderVouchers) {
            voucher.setVoucherStatus(VoucherStatus.REFUNDED);
            voucherRepository.save(voucher);
            batch.setTotalCount(batch.getTotalCount() + 1);
        }
        voucherBatchRepository.save(batch);
        Order order=  orderOptional.get();
        order.setOrderStatus(OrderStatus.Refunded);
        order.setUpdatedAt(new Date());
        orderRepository.save(order);
        return "Refund Successfully";
    }
}

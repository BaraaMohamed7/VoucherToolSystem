package com.biro.vouchertoolsystem.service;

import com.biro.vouchertoolsystem.Dtos.Response.OrderResponseDTO;
import com.biro.vouchertoolsystem.Dtos.Response.OrderVouchersResponseDTO;
import com.biro.vouchertoolsystem.model.Order;
import com.biro.vouchertoolsystem.model.OrderStatus;
import com.biro.vouchertoolsystem.model.VoucherStatus;
import com.biro.vouchertoolsystem.repository.OrderRepository;
import com.biro.vouchertoolsystem.repository.VoucherBatchRepository;
import com.biro.vouchertoolsystem.repository.VoucherRepository;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final VoucherRepository voucherRepository;
    private final VoucherBatchRepository voucherBatchRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, VoucherRepository voucherRepository, VoucherBatchRepository voucherBatchRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.voucherRepository = voucherRepository;
        this.voucherBatchRepository = voucherBatchRepository;
        this.modelMapper = modelMapper;
    }

    public List<OrderVouchersResponseDTO> newOrder(Long productId, Integer quantity) throws Exception {
        Long batchId = voucherBatchRepository.findVoucherBatchByProductId(productId);
        Pageable limit = PageRequest.of(0, quantity);
        List<Long> availableVouchersByBatchId = voucherRepository.findAvailableVouchersByBatchId(batchId, VoucherStatus.AVAILABLE, limit);
        if(availableVouchersByBatchId.isEmpty()) {
            throw new Exception("No Available Vouchers");
        }

        Order emptyOrder = new Order();
        emptyOrder.setOrderStatus(OrderStatus.Paid);
        Order order = orderRepository.save(emptyOrder);
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

    public OrderResponseDTO updateOrder(Long orderId, OrderStatus orderStatus) throws Exception {
        Optional<Order> orderOptional= orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            if(orderStatus != null) {
                order.setOrderStatus(orderStatus);
            }
            return  modelMapper.map(orderRepository.save(order), OrderResponseDTO.class);
        } else  {
            throw new BadRequestException("Order Not Found");
        }
    }
}

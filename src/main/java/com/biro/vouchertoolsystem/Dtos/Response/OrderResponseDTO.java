package com.biro.vouchertoolsystem.Dtos.Response;

import com.biro.vouchertoolsystem.model.OrderStatus;
import com.biro.vouchertoolsystem.model.Voucher;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResponseDTO {
    private Long orderId;
    private OrderStatus orderStatus;
    private List<OrderVouchersResponseDTO> vouchers;
}

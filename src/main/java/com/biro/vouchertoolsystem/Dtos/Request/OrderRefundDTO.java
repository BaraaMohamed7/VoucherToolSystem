package com.biro.vouchertoolsystem.Dtos.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRefundDTO {
    private Long userId;
    private Long orderId;
}

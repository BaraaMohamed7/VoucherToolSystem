package com.biro.vouchertoolsystem.Dtos.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDTO {
    private Long userId;
    private Long productId;
    private Integer quantity;
}

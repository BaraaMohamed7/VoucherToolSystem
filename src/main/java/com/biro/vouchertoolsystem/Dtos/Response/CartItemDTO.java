package com.biro.vouchertoolsystem.Dtos.Response;

import com.biro.vouchertoolsystem.model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO {
    private Long productId;
    private Long quantity;
    private Double price;
}

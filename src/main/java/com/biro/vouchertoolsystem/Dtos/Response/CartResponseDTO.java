package com.biro.vouchertoolsystem.Dtos.Response;

import com.biro.vouchertoolsystem.model.CartItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartResponseDTO {
    private List<CartItemDTO> items;
    private Double total;
}

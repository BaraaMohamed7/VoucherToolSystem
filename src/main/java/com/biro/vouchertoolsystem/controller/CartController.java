package com.biro.vouchertoolsystem.controller;

import com.biro.vouchertoolsystem.Dtos.Request.CartItemRequestDTO;
import com.biro.vouchertoolsystem.Dtos.Response.CartResponseDTO;
import com.biro.vouchertoolsystem.service.CartService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public CartResponseDTO addToCart(@RequestBody CartItemRequestDTO item) throws BadRequestException {
        return cartService.addToCart(item.getUserId(), item.getProductId(), item.getQuantity());
    }

}

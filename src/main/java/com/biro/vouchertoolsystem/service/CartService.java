package com.biro.vouchertoolsystem.service;

import com.biro.vouchertoolsystem.Dtos.Response.CartResponseDTO;
import com.biro.vouchertoolsystem.model.Cart;
import com.biro.vouchertoolsystem.model.CartItem;
import com.biro.vouchertoolsystem.model.Product;
import com.biro.vouchertoolsystem.model.User;
import com.biro.vouchertoolsystem.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CartService
{
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final ModelMapper modelMapper;


    @Transactional
    public CartResponseDTO addToCart(Long userId, Long productId, Integer quantity) throws BadRequestException {
        User user = userRepository.findById(userId).orElseThrow(()->new BadRequestException("user not found"));
        Product product = productRepository.findById(productId).orElseThrow(()-> new  BadRequestException("Product not found"));
        Cart cart = cartRepository.findByUserId(userId).orElseGet(()->{
            Cart newCart = new Cart();
            newCart.setUser(user);
            return newCart;
        });
        cart = cartRepository.save(cart);

        Optional<CartItem> existingCartItemOptional = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);

        if (existingCartItemOptional.isPresent()) {
            CartItem existingCartItem = existingCartItemOptional.get();
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            existingCartItem.setPriceAfter(product.getPriceAfter() * existingCartItem.getQuantity());
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setPriceAfter(product.getPriceAfter() * quantity);
            cart.addItem(cartItem);
            cartItemRepository.save(cartItem);
        }

        Double total = cart.getItems().stream().mapToDouble(CartItem::getPriceAfter).sum();
        cart.setTotal(total);
        Cart updatedCart = cartRepository.save(cart);
        return modelMapper.map(updatedCart, CartResponseDTO.class);
    }
}
package com.eshop.eshop.service.impl;

import com.eshop.eshop.dto.AddToCartRequest;
import com.eshop.eshop.model.entity.Cart;
import com.eshop.eshop.model.entity.CartItem;
import com.eshop.eshop.model.entity.User;
import com.eshop.eshop.repository.CartItemRepository;
import com.eshop.eshop.repository.CartRepository;
import com.eshop.eshop.repository.UserRepository;
import com.eshop.eshop.service.CartService;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl  implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public CartServiceImpl(CartRepository cartRepository,
                           CartItemRepository cartItemRepository,
                           UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addToCart(AddToCartRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), request.getProductId())
                .orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        } else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProductId(request.getProductId());
            cartItem.setQuantity(request.getQuantity());
        }

        cartItemRepository.save(cartItem);
    }
}

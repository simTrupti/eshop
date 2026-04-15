package com.eshop.eshop.service;

import com.eshop.eshop.dto.LoginRequest;
import com.eshop.eshop.dto.RegisterRequest;
import com.eshop.eshop.model.entity.User;

import java.util.Optional;

public interface AuthService {

    void register(RegisterRequest request);

    public String login(LoginRequest request);


}

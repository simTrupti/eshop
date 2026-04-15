package com.eshop.eshop.service.impl;

import com.eshop.eshop.dto.LoginRequest;
import com.eshop.eshop.dto.RegisterRequest;
import com.eshop.eshop.model.entity.User;
import com.eshop.eshop.repository.UserRepository;
import com.eshop.eshop.service.AuthService;
import com.eshop.eshop.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

   @Override
    public void register(RegisterRequest request){

       if(userRepository.findByEmail(request.getEmail()).isPresent()){
           throw new RuntimeException("Email Already Exists");
       }

       User user = new User();
       user.setName(request.getName());
       user.setEmail(request.getEmail());
       user.setPassword(passwordEncoder.encode(request.getPassword()));
       user.setAddress(request.getAddress());
       user.setPhone(request.getPhone());

       userRepository.save(user);
    }

    @Override
    public String login(LoginRequest request){
//        Find user by email
//        2. If not found → error
//        3. Check password using passwordEncoder.matches()
//        4. If wrong → error
//        5. If correct → success

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not registered"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(user.getEmail());
    }



}

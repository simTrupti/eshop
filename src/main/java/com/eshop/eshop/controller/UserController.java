package com.eshop.eshop.controller;


import com.eshop.eshop.model.entity.User;
import com.eshop.eshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user){
            User createNewUser = userService.createUser(user);
            return createNewUser;
    }

    @GetMapping
    public List<User> getAllUser(){
      // List<User> newList= userService.getAllUsers();
      //  return newList;
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id){
        User user = userService.getUserById(id);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);

    }

}

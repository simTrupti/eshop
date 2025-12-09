package com.eshop.eshop.controller;


import com.eshop.eshop.model.entity.User;
import com.eshop.eshop.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private UserService userService;

    // ✅ Create User
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // ✅ Get All Users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());

    }

    // ✅ Get User By ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {

//        // Service throws IllegalArgumentException if user not found
//        User user = userService.getUserById(id);
//        return ResponseEntity.ok(user);

        return userService.getUserByIdOptional(id)
                .map(ResponseEntity::ok) // if present → 200 OK
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // if absent → 404
    }

    //Get UserNames sorted List
    @GetMapping("/sorted-names")
    public ResponseEntity<List<String>> getSortedUserNames(){
        return ResponseEntity.ok(userService.getSortedNames());

    }

    //get google email id list
    @GetMapping("/gmailid")
    public ResponseEntity<List<String>> grtGmailIds(){
        return  ResponseEntity.ok(userService.getGoogleEmailId());
    }

    

}

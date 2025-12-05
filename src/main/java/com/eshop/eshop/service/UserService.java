package com.eshop.eshop.service;

import com.eshop.eshop.model.entity.User;
//import com.eshop.eshop.util.IdGenerator;
import com.eshop.eshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    //List<User> users = new ArrayList<>();
    @Autowired
    private UserRepository userRepository;

   // private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // ✅ Java 8 Optional + Lambda style duplicate check
    public User createUser(User user) {

       //checks the duplicate
//       if(userRepository.findByEmail(user.getEmail()).isPresent()){
//           throw new IllegalArgumentException("Email already registered");
//       }

        userRepository.findByEmail(user.getEmail()).ifPresent(existingUser -> { throw new IllegalArgumentException("Email already registered");});


//       if (userRepository.findByPhone(user.getPhone()).isPresent()) {
//           throw new IllegalArgumentException("Phone number already in use");
//       }

        userRepository.findByPhone(user.getPhone()).ifPresent(existingUser -> { throw new IllegalArgumentException(" Phone number already in use ");});

      // user.setPassword(passwordEncoder.encode(user.getPassword()));



//user.setId(IdGenerator.generateId());
   // users.add(user);
   // return user;
       return  userRepository.save(user);

   }

   public List<User> getAllUsers(){
       //return  users;
       return userRepository.findAll();
   }

//    public User getUserById(Integer id) {
////        Optional<User> optionalUser = userRepository.findById(id);
////        return optionalUser.orElse(null);
//
//        return  userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found with id : " + id));
//}

    public Optional<User> getUserByIdOptional(Integer id){
        return userRepository.findById(id);
    }

}


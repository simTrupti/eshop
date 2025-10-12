package com.eshop.eshop.service;

import com.eshop.eshop.model.entity.User;
//import com.eshop.eshop.util.IdGenerator;
import com.eshop.eshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    //List<User> users = new ArrayList<>();
    @Autowired
    private UserRepository userRepository;


   public User createUser(User user){
//user.setId(IdGenerator.generateId());
   // users.add(user);
   // return user;
       return  userRepository.save(user);

   }

   public List<User> getAllUsers(){
       //return  users;
       return userRepository.findAll();
   }

   public User getUserById(int id){
//       for(User user: users) {
//           if (user.getId() == id) {
//               return user;
//           }
//
//       }
//       return null;
       Optional<User> optionalUser = userRepository.findById(id);
       return optionalUser.orElse(null);
   }









     // getAllUsers()
    //getUserById()


}

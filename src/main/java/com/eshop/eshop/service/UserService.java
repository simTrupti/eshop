package com.eshop.eshop.service;

import com.eshop.eshop.model.entity.User;
//import com.eshop.eshop.util.IdGenerator;
import com.eshop.eshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

   // private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> createUser(List<User> users) {

        users.forEach(user -> {

                    userRepository.findByEmail(user.getEmail())
                            .ifPresent(existingUser -> {
                                throw new IllegalArgumentException("Email already registered");
                            });

                    userRepository.findByPhone(user.getPhone())
                            .ifPresent(existingUser -> {
                                throw new IllegalArgumentException(" Phone number already in use ");
                            });


        });
      // user.setPassword(passwordEncoder.encode(user.getPassword()));

       return  userRepository.saveAll(users);

   }

   // Get all users
   public List<User> getAllUsers(){

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

    public List<String> getDesortedNames(){
        List<String> names = userRepository.findAllUserNames();

        names.sort( (name1, name2) -> name2.compareToIgnoreCase(name1));

        return names;
    }

    public List<String> getSortedNames(){
        List<String> names = userRepository.findAllUserNames();

        names.sort(String::compareToIgnoreCase);

        return names;
    }

    public List<String> getGoogleEmailId(){
        List<String> emails = userRepository.findAllEmail();

        List<String> emailid = emails.stream().filter(email -> email.endsWith("@gmail.com")).toList();

        return emailid;
    }

}


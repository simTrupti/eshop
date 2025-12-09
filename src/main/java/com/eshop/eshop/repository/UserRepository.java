package com.eshop.eshop.repository;

import com.eshop.eshop.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail (String email);

    Optional<User> findByPhone(String phone);

    @Query ( "SELECT u.name FROM User u")
    List<String> findAllUserNames();

    @Query("SELECT u.email FROM User u")
    List<String> findAllEmail();
}

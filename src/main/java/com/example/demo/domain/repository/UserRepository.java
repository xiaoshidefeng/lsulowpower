package com.example.demo.domain.repository;

import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

/**
 * Created by cw on 2017/7/19.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserEmail(String userEmail);
    User findByUserCode(String code);
    User findByUserToken(String token);
    ArrayList<User> findByDormNotNull();
    User findByUcode(String ucode);
}

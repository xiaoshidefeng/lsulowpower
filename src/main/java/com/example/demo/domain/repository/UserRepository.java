package com.example.demo.domain.repository;

import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by cw on 2017/7/19.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserEmail(String userEmail);
    User findByUserCode(String code);
}

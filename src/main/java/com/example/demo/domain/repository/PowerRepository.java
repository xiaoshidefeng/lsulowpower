package com.example.demo.domain.repository;

import com.example.demo.domain.Power;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by cw on 2017/7/20.
 */
public interface PowerRepository extends JpaRepository<Power, Integer> {

    Power findByDateNum(String dorm);
}

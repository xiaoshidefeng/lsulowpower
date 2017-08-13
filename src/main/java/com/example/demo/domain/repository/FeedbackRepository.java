package com.example.demo.domain.repository;

import com.example.demo.domain.Feedback;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by cw on 2017/8/12.
 */
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

}

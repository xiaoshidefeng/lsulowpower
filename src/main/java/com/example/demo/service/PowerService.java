package com.example.demo.service;

import com.example.demo.domain.Power;
import com.example.demo.utils.Result;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * Created by cw on 2017/7/20.
 */
public interface PowerService {
    List<Power> findAll();
    Result findByDorm(String dorm, BindingResult bindingResult);
}

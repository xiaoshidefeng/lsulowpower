package com.example.demo.service;

import com.example.demo.utils.Result;
import org.springframework.validation.BindingResult;

/**
 * Created by cw on 2017/7/20.
 */
public interface PowerService {
    Result findByDorm(String dorm, BindingResult bindingResult);
}

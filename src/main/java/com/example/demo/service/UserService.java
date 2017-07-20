package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.utils.Result;
import com.example.demo.utils.ResultUtil;
import org.springframework.validation.BindingResult;

/**
 * Created by cw on 2017/7/19.
 */
public interface UserService {
    Result registerUser(User user, BindingResult bindingResult);
    Result loginUser(User user, BindingResult bindingResult);
    Result userCheckMail(String email, String code);
    Result bindingDorm(String dorm, String token, BindingResult bindingResult);
}

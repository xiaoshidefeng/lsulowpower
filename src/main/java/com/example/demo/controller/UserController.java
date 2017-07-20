package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.Impl.UserServiceImpl;
import com.example.demo.service.UserService;
import com.example.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by cw on 2017/7/19.
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * 用户注册
     * @param user
     * @param bindingResult
     * @return
     */
    @PostMapping("/register")
    public Result userRegitser(@Valid User user, BindingResult bindingResult) {

        return userServiceImpl.registerUser(user, bindingResult);
    }

    /**
     * 邮箱验证
     * @param email
     * @param code
     * @return
     */
    @GetMapping("/email={email}/code={code}")
    public Result userCheckMail(@PathVariable("email") String email,
                                @PathVariable("code") String code) {

        return userServiceImpl.userCheckMail(email, code);
    }

    @PostMapping("/login")
    public Result userLogin(@Valid User user, BindingResult bindingResult) {
        return userServiceImpl.loginUser(user, bindingResult);
    }
}

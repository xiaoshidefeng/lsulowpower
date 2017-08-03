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

    /**
     * 用户登录
     * @param user
     * @param bindingResult
     * @return
     */
    @PostMapping("/login")
    public Result userLogin(@Valid User user, BindingResult bindingResult) {
        return userServiceImpl.loginUser(user, bindingResult);
    }

    /**
     * 绑定寝室
     * @param dorm
     * @param token
     * @param
     * @return
     */
    @PostMapping("/bindingDorm")
    public Result bindingDorm(@RequestParam("dorm") String dorm,
                              @RequestParam("token") String token,
                              @RequestParam("floor") String floor) {
        return userServiceImpl.bindingDorm(dorm, token, floor);
    }


    /**
     * 发送验证邮件
     * @param email
     * @return
     */
    @PostMapping("/forget_password")
    public Result forgetPassword(@RequestParam("email") String email) {
        return userServiceImpl.forgetPassword(email);
    }


    /**
     * 重置密码
     * @param email
     * @param confirmCode
     * @param newPassword
     * @return
     */
    @PostMapping("/reset_password")
    public Result resetPassword(@RequestParam("email") String email,
                                @RequestParam("confirmCode") String confirmCode,
                                @RequestParam("newPassword") String newPassword) {
        return  userServiceImpl.findBackPasswordByConfirmCode(email, confirmCode, newPassword);
    }
}

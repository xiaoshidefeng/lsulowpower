package com.example.demo.service.Impl;

import com.example.demo.domain.User;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.utils.CodeUtil;
import com.example.demo.utils.MailUtil;
import com.example.demo.utils.Result;
import com.example.demo.utils.ResultUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.servlet.ServletException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cw on 2017/7/19.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailUtil mailUtil;

    @Override
    public Result registerUser(User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            return ResultUtil.error(0, bindingResult.getFieldError().getDefaultMessage());
        } else if(user.getUserEmail() == null || user.getPassword() == null) {
            return ResultUtil.error(8, "非法输入");
        }
//        System.out.println("1111111111111111111" + user.getUserEmail());
        //正则表达式验证邮箱
        if(!user.getUserEmail().matches("^\\w+@(\\w+\\.)+\\w+$")) {
            return ResultUtil.error(10, "邮箱不合法");
        }
        if(userRepository.findByUserEmail(user.getUserEmail()) != null) {
            return ResultUtil.error(2, "邮箱已被注册");
        }

        //加盐
        String code = CodeUtil.generateCode(user.getUserEmail());
        String codepw = CodeUtil.generateCode(user.getPassword());
        user.setPassword(codepw);
        user.setUserState(0);
        user.setUserCode(code);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        user.setRegisterTime(df.format(new Date()));

        if(mailUtil.sendRegisterMail(user.getUserEmail(), code)) {
            return ResultUtil.success(userRepository.save(user));
        }
        return ResultUtil.error(4, "邮件发送失败");
    }

    @Transactional
    @Override
    public Result loginUser(User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            return ResultUtil.error(0, bindingResult.getFieldError().getDefaultMessage());
        }

        String jwtToken = "";

        if (user.getUserEmail() == null || user.getPassword() == null) {
            return ResultUtil.error(16, "输入为空");
        }

        String email = user.getUserEmail();

        User newuser = userRepository.findByUserEmail(email);

        if (newuser == null) {
            return ResultUtil.error(12, "用户不存在");
        }

        String password = CodeUtil.generateCode(user.getPassword());

        if (!password.equals(newuser.getPassword())) {
            return ResultUtil.error(14, "密码错误");
        }

        jwtToken = CodeUtil.generateToken(email);

        newuser.setUserToken(jwtToken);
        userRepository.save(newuser);
        return ResultUtil.success(jwtToken);
    }

    @Override
    public Result userCheckMail(String email, String code) {
        User user = userRepository.findByUserCode(code);
        if(user.getUserEmail() != null && user.getUserEmail().equals(email)) {
            user.setUserState(1);
            //TODO 关于时间的验证 待做
            userRepository.save(user);
            return ResultUtil.success();
        }
        return ResultUtil.error(6, "邮件验证失败");
    }

    @Override
    public Result bindingDorm(String dorm, String token) {
//        if (bindingResult.hasErrors()) {
//            System.out.println(bindingResult.getFieldError().getDefaultMessage());
//            return ResultUtil.error(0, bindingResult.getFieldError().getDefaultMessage());
//        }

        if(token == null) {
            return ResultUtil.error(20, "未登录");
        } else if(dorm == null) {
            return ResultUtil.error(24, "未填写寝室信息");
        }
        User user = userRepository.findByUserToken(token);
        if(user == null) {
            return ResultUtil.error(22, "登录信息查询失败");
        }

        user.setDorm(dorm);
        userRepository.save(user);
        return ResultUtil.success();
    }

}

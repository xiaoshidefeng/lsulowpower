package com.example.demo.service.Impl;

import com.example.demo.domain.User;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.utils.CodeUtil;
import com.example.demo.utils.MailUtil;
import com.example.demo.utils.Result;
import com.example.demo.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

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
            return ResultUtil.error(10, "非法输入");
        }
//        System.out.println("1111111111111111111" + user.getUserEmail());
        //正则表达式验证邮箱
        if(!user.getUserEmail().matches("^\\w+@(\\w+\\.)+\\w+$")) {
            return ResultUtil.error(12, "邮箱不合法");
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

    @Override
    public Result loginUser(User user, BindingResult bindingResult) {
        return null;
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

}

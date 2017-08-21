package com.example.demo.service.Impl;

import com.example.demo.domain.User;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.utils.*;

import com.example.demo.utils.Enums.ResultEnums;
import com.example.demo.utils.Exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private CheckLowPower checkLowPower;

    @Override
    public Result registerUser(User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            return ResultUtil.error(ResultEnums.UNKONW_ERROR);
        } else if(user.getUserEmail() == null || user.getPassword() == null ||
                user.getUserEmail().equals("") || user.getPassword().equals("")) {
            return ResultUtil.error(ResultEnums.ILLEGAL_INPUT);
        }

        //正则表达式验证邮箱
        if(!user.getUserEmail().matches("^\\w+@(\\w+\\.)+\\w+$")) {
            return ResultUtil.error(ResultEnums.MAIL_ILLEGAL);
        }
        if(userRepository.findByUserEmail(user.getUserEmail()) != null) {
            User user1 = userRepository.findByUserEmail(user.getUserEmail());
            int state = user1.getUserState();
            if(state == 1) {
                return ResultUtil.error(ResultEnums.MAIL_IS_REGISTE);
            }else {
                //注册但未激活
                if(mailUtil.sendRegisterMail(user1.getUserEmail(), user1.getUserCode())) {
                    return ResultUtil.success(userRepository.save(user1));
                }
                return ResultUtil.error(ResultEnums.MAIL_SEND_FAIL);

            }

        }

        //加盐
        String code = CodeUtil.generateCode(user.getUserEmail());
        String codepw = CodeUtil.generateCode(user.getPassword());
        user.setPassword(codepw);
        user.setUserState(0);
        user.setUserCode(code);
        user.setSendCount(0);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        user.setRegisterTime(df.format(new Date()));

        if(mailUtil.sendRegisterMail(user.getUserEmail(), code)) {
            return ResultUtil.success(userRepository.save(user));
        }
        return ResultUtil.error(ResultEnums.MAIL_SEND_FAIL);
    }

    @Transactional
    @Override
    public Result loginUser(User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            return ResultUtil.error(ResultEnums.UNKONW_ERROR);
        }

        String jwtToken = "";

        if (user.getUserEmail() == null || user.getPassword() == null ||
                user.getUserEmail().equals("") || user.getPassword().equals("")) {
            return ResultUtil.error(ResultEnums.INPUT_NULL);
        }

        String email = user.getUserEmail();

        User newuser = userRepository.findByUserEmail(email);

        if (newuser == null) {
            return ResultUtil.error(ResultEnums.USER_NOT_EXIST);
        }

        String password = CodeUtil.generateCode(user.getPassword());

        if (!password.equals(newuser.getPassword())) {
            return ResultUtil.error(ResultEnums.PASSWORD_ERROR);
        }

        if(newuser.getUserState() == 0) {
            return ResultUtil.error(ResultEnums.NOT_CONFIRM_MAIL);
        }

        jwtToken = CodeUtil.generateToken(email);

        newuser.setUserToken(jwtToken);
        userRepository.save(newuser);
        return ResultUtil.success(jwtToken);
    }

    @Override
    public Result userCheckMail(String email, String code) {
        User user = userRepository.findByUserCode(code);
        if(user == null) {
            return ResultUtil.error(ResultEnums.MAIL_CONFIRM_FAIL);
        }
        if(user.getUserEmail() != null && user.getUserEmail().equals(email)) {
            user.setUserState(1);
            //TODO 关于时间的验证 待做
            userRepository.save(user);
            return ResultUtil.success("邮箱验证成功，请返回并进行登录");
        }
        return ResultUtil.error(ResultEnums.MAIL_CONFIRM_FAIL);
    }

    @Override
    public Result bindingDorm(String dorm, String token, String floor) {

        if(token == null || token.equals("")) {
            return ResultUtil.error(ResultEnums.NOT_LOGIN);
        } else if(dorm == null || dorm.equals("") || floor.equals("")) {
            return ResultUtil.error(ResultEnums.NOT_INPUT_DORM_INFO);
        }
        User user = userRepository.findByUserToken(token);
        if(user == null) {
            return ResultUtil.error(ResultEnums.LOGIN_INFO_FIND_FAIL);
        }

        user.setDorm(dorm);
        user.setFloor(floor);
        userRepository.save(user);

        checkLowPower.checkLowPower(user);

        return ResultUtil.success();
    }

    @Override
    public Result forgetPassword(String email) {
        User user = userRepository.findByUserEmail(email);
        if(user == null) {
            return ResultUtil.error(ResultEnums.FINDBACK_PASSWORD_FAIL);
        }
        String randcode = CodeUtil.generateRandNum();
        user.setConfirmCode(randcode);
        userRepository.save(user);
        if(mailUtil.sendFindBackPasswordCodeMail(email, randcode)) {

            return ResultUtil.success("验证邮件发送成功");
        }

        return ResultUtil.error(ResultEnums.UNKONW_ERROR);
    }

    @Override
    public Result findBackPasswordByConfirmCode(String email, String code, String newPassword) {
        if(email.equals("") || code ==null || newPassword == null ||
                email.equals("") || code.equals("") || newPassword.equals("")) {
            return ResultUtil.error(ResultEnums.INPUT_NULL);
        }

        User user = userRepository.findByUserEmail(email);
        if(user == null) {
            return ResultUtil.error(ResultEnums.FINDBACK_PASSWORD_FAIL);
        }

        if(!user.getConfirmCode().equals(code)) {
            return ResultUtil.error(ResultEnums.CONFIRM_CODE_ERROR);
        }
        //加盐
        String codepw = CodeUtil.generateCode(newPassword);
        user.setPassword(codepw);
        userRepository.save(user);
        return ResultUtil.success();

    }

}

package com.example.demo.service.Impl;

import com.example.demo.domain.Feedback;
import com.example.demo.domain.User;
import com.example.demo.domain.repository.FeedbackRepository;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.service.FeedbackService;
import com.example.demo.utils.MailUtil;
import com.example.demo.utils.Result;
import com.example.demo.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

/**
 * Created by cw on 2017/8/12.
 */
@Service
public class FeedbackServiceImpl implements FeedbackService{

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailUtil mailUtil;

    @Override
    public Result doFeedback(Feedback feedback, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            return ResultUtil.error(0, bindingResult.getFieldError().getDefaultMessage());
        }
        if(feedback ==null || feedback.getFeedbackContent() == null || feedback.getFeedbackContent().equals("")) {
            return ResultUtil.error(16, "输入为空");
        }
        if(feedback.getFeedbackUser() == null || feedback.getFeedbackUser().equals("")) {
            return ResultUtil.error(20, "未登录");
        }
        User user = userRepository.findByUserEmail(feedback.getFeedbackUser());

        if(user == null) {
            return ResultUtil.error(20, "未登录");
        }


        if (mailUtil.sendFeedbackMail(feedback.getFeedbackContent(), feedback.getFeedbackUser(), feedback.getFeedbackTime())) {
            return ResultUtil.success(feedbackRepository.save(feedback));
        }else {
            return ResultUtil.error(4, "邮件送失败");
        }


    }
}

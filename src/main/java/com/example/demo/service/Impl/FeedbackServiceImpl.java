package com.example.demo.service.Impl;

import com.example.demo.domain.Feedback;
import com.example.demo.domain.User;
import com.example.demo.domain.repository.FeedbackRepository;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.service.FeedbackService;
import com.example.demo.utils.Enums.ResultEnums;
import com.example.demo.utils.IsNull;
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
public class FeedbackServiceImpl implements FeedbackService {

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
            return ResultUtil.error(ResultEnums.UNKONW_ERROR);
        }
        if (feedback == null || IsNull.isNullField(feedback.getFeedbackContent())) {
            return ResultUtil.error(ResultEnums.INPUT_NULL);
        }
        if (IsNull.isNullField(feedback.getFeedbackUser())) {
            return ResultUtil.error(ResultEnums.NOT_LOGIN);
        }
        User user = userRepository.findByUserEmail(feedback.getFeedbackUser());

        if (user == null) {
            return ResultUtil.error(ResultEnums.NOT_LOGIN);
        }


        if (mailUtil.sendFeedbackMail(feedback.getFeedbackContent(), feedback.getFeedbackUser(), feedback.getFeedbackTime())) {
            return ResultUtil.success(feedbackRepository.save(feedback));
        } else {
            return ResultUtil.error(ResultEnums.MAIL_SEND_FAIL);
        }


    }
}

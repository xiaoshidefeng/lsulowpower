package com.example.demo.controller;

import com.example.demo.domain.Feedback;
import com.example.demo.service.FeedbackService;
import com.example.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by cw on 2017/8/12.
 */
@RestController
@RequestMapping("/api")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/feedback")
    public Result doFeedback(@Valid Feedback feedback, BindingResult bindingResult) {
        return feedbackService.doFeedback(feedback, bindingResult);
    }
}

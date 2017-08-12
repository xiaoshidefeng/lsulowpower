package com.example.demo.service;

import com.example.demo.domain.Feedback;
import com.example.demo.utils.Result;
import org.springframework.validation.BindingResult;

/**
 * Created by cw on 2017/8/12.
 */
public interface FeedbackService {
    Result doFeedback(Feedback feedback, BindingResult bindingResult);
}

package com.example.demo.domain;

import javax.persistence.*;

/**
 * Created by cw on 2017/8/12.
 */
@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue
    @Column(name = "feedback_id")
    private int feedbackId;

    @Column(name = "feedback_content")
    private String feedbackContent;

    @Column(name = "feedback_time")
    private String feedbackTime;

    @Column(name = "feedback_user")
    private String feedbackUser;

    @Column(name = "feedback_img")
    private String feedbackImg;

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public String getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(String feedbackTime) {
        this.feedbackTime = feedbackTime;
    }

    public String getFeedbackUser() {
        return feedbackUser;
    }

    public void setFeedbackUser(String feedbackUser) {
        this.feedbackUser = feedbackUser;
    }

    public String getFeedbackImg() {
        return feedbackImg;
    }

    public void setFeedbackImg(String feedbackImg) {
        this.feedbackImg = feedbackImg;
    }
}

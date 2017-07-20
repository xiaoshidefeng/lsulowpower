package com.example.demo.service.SchedulingTask;

import com.example.demo.domain.Power;
import com.example.demo.domain.User;
import com.example.demo.domain.repository.PowerRepository;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cw on 2017/7/20.
 */
@Component
public class SchedulerTaskFindAndSend {
    private int count=0;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PowerRepository powerRepository;

    @Autowired
    private MailUtil mailUtil;

    private String dorm;

    private String email;

    @Scheduled(cron="*/30 * * * * ?")
    private void process(){
        System.out.println("this is scheduler task runing  "+(count++));

        ArrayList<User> userArrayList = userRepository.findByDormNotNull();

        for(int i = 0; i < userArrayList.size(); i++ ) {
            dorm = userArrayList.get(i).getDorm();
            Power power = powerRepository.findByDormNum(dorm);

            if(power != null) {
                String email = userArrayList.get(i).getUserEmail();
                String powerValue = power.getPowerNum();
                String dayTime = power.getDateNum();
                mailUtil.sendLowPowerMail(email, dorm, powerValue, dayTime);
            }
        }
    }
}

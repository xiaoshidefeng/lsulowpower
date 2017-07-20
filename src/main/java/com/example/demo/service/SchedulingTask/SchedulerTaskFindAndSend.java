package com.example.demo.service.SchedulingTask;

import com.example.demo.domain.Power;
import com.example.demo.domain.User;
import com.example.demo.domain.repository.PowerRepository;
import com.example.demo.domain.repository.UserRepository;
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

    private String dorm;

    @Scheduled(cron="*/60 * * * * ?")
    private void process(){
        System.out.println("this is scheduler task runing  "+(count++));

        ArrayList<User> userArrayList = userRepository.findByDormNotNull();
        List<Power> powerList = powerRepository.findAll();

        for(int i = 0; i < userArrayList.size(); i++ ) {
            dorm = userArrayList.get(i).getDorm().toString();
            for( int j = 0; j < powerList.size(); j++) {
                if(dorm.equals(powerList.get(j).getDormNum())) {
                    //TODO sendMail
                }
            }
        }


    }
}

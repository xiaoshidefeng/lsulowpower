package com.example.demo.service.SchedulingTask;

import com.example.demo.domain.Power;
import com.example.demo.domain.User;
import com.example.demo.domain.repository.PowerRepository;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.utils.IsNull;
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

    private String building;

    private String powerValue;
    private String dayTime;

    private String dayTimes = "2017-7-21";

    @Scheduled(cron="0 0 6,7,8,9,10,11,12,15,17,20 * * ?")
    private void process(){
        System.out.println("this is scheduler task runing  "+(count++));

        Power powerone = powerRepository.findByPowerId(1);
        if(!dayTimes.equals(powerone.getDateNum())) {
            //每天只发一次

            ArrayList<User> userArrayList = userRepository.findByDormNotNull();

            for(int i = 0; i < userArrayList.size(); i++ ) {
                User user = userArrayList.get(i);
                if (!user.getSendState()) {
                    continue;
                }
                email = user.getUserEmail();
                dorm = user.getDorm();
                building = user.getFloor();
                if(IsNull.isNullField(dorm, building)) {
                    List<Power> powerList = powerRepository.findByBuildingName(building);
                    if(powerList != null) {
                        for(int j =0; j < powerList.size(); j++) {
                            if(powerList.get(j).getDormNum().equals(dorm)) {
                                Power power = powerList.get(j);
                                powerValue = power.getPowerNum();
                                dayTime = power.getDateNum();

                                //发送邮件
                                mailUtil.sendLowPowerMail(email, dorm, powerValue, dayTime);
                                //发送次数加一
                                user.setSendCount(user.getSendCount() + 1);
                                userRepository.save(user);
                                break;

                            }
                        }
                    }

                }

            }

            dayTimes = powerone.getDateNum();
        }


    }
}

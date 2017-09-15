package com.example.demo.utils;

import com.example.demo.domain.Power;
import com.example.demo.domain.User;
import com.example.demo.repository.PowerRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by cw on 2017/7/24.
 */
@Component
public class CheckLowPower {

    @Autowired
    private PowerRepository powerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailUtil mailUtil;

    public boolean checkLowPower(User user) {
        String email = user.getUserEmail();
        String dorm = user.getDorm();
        String building = user.getFloor();
        List<Power> powerList = powerRepository.findByBuildingName(building);
        if (building != null && dorm != null && !building.equals("") && !dorm.equals("")) {
            if (powerList != null) {
                for (int j = 0; j < powerList.size(); j++) {
                    if (powerList.get(j).getDormNum().equals(dorm)) {
                        Power power = powerList.get(j);
                        String powerValue = power.getPowerNum();
                        String dayTime = power.getDateNum();

                        //发送邮件
                        mailUtil.sendLowPowerMail(email, dorm, powerValue, dayTime);
                        //发送次数加一
                        user.setSendCount(user.getSendCount() + 1);
                        userRepository.save(user);
                        return true;

                    }
                }
            }

        }

        return false;
    }
}

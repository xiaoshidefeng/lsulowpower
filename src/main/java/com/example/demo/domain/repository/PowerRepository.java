package com.example.demo.domain.repository;

import com.example.demo.domain.Power;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by cw on 2017/7/20.
 */
public interface PowerRepository extends JpaRepository<Power, Integer> {

    //    List<Power> findByDormNum(String dorm);
    List<Power> findByDormNumLike(String dorm);

    List<Power> findByBuildingName(String building);

    Power findByPowerId(Integer id);

}

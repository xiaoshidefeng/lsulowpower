package com.example.demo.repository;

import com.example.demo.domain.Power;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by cw on 2017/7/20.
 */
@CacheConfig(cacheNames = "powers")
public interface PowerRepository extends JpaRepository<Power, Integer> {

    @Cacheable
    List<Power> findAll();

    List<Power> findByDormNumLike(String dorm);

    List<Power> findByBuildingName(String building);

    Power findByPowerId(Integer id);

}

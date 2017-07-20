package com.example.demo.domain;

import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Created by cw on 2017/7/20.
 */
@Entity
@Table(name = "record")
public class Record {

    @Id
    @GeneratedValue
    @Column(name = "record_id")
    private Integer recordId;
}

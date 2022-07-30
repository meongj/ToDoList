package com.meongj.project.todolist.domain;

import lombok.Data;

import java.util.Date;

@Data
public class TaskVO {

    private int id;
    private String title;
    private String content;
    private String hashtag;
    private int complete;
    private String date;
    private String startTime;
    private String endTime;
    private String priority;
    private String leftTime; // 남은 시간
    private String flag; // 구분자
    private String check; // 체크여부
    private String color;

}

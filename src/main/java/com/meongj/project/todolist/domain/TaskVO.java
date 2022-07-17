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

}

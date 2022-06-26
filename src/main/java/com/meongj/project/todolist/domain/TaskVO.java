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
    private Date date;
    private Date startTime;
    private Date endTime;
    private String priority;
}

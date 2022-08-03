package com.meongj.project.todolist.controller;

import com.meongj.project.todolist.domain.TaskVO;
import com.meongj.project.todolist.service.TaskServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/todolist")
public class AjaxController {

    @Autowired
    private TaskServiceImpl taskServiceImpl;

    @PostMapping("/register")
    public int register(@RequestBody TaskVO taskVO) throws Exception {
        log.info("[register] TaskVO: " + taskVO);
        String flag = taskVO.getFlag();
        //yyyy-mm-dd hh:mm:00 (hh:mm)
        //오늘날짜
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        String today = simpleDateFormat.format(now);
        String getStartTime  = taskVO.getStartTime().replace(":","");
        String getEndTime = taskVO.getEndTime().replace(":","");
        taskVO.setStartTime(today.toString() + getStartTime + "00");
        taskVO.setEndTime(today.toString() + getEndTime + "00");

        int result = 0;
        if (flag.equals("add")) {
            result = taskServiceImpl.addTask(taskVO);
            if (result == 1) log.info("INSERT TASK SUCCESS!!");
        } else if (flag.equals("edit")) {
            result = taskServiceImpl.editTask(taskVO);
            if (result== 1) log.info("UPDATE TASK SUCCESS!!");
        }

        return result;
    }

    @PostMapping("/delete")
    public int deleteTask(@RequestBody TaskVO taskVO) throws Exception {
        int deleteFlag = taskServiceImpl.deleteTask(taskVO);
        if(deleteFlag == 1) log.info("DELETE TASK SUCCESS!!");

        return deleteFlag;
    }

    // 할 일 완료된 것 체크저장
    @PostMapping("/completeTask")
    public int completeTask(@RequestBody TaskVO taskVO) throws Exception {
        int result= taskServiceImpl.completeTask(taskVO);
        if (result == 1) log.info("CHECKBOX UPDATE SUCCESS");
        return result;
    }
}

package com.meongj.project.todolist.controller;

import com.meongj.project.todolist.domain.TaskVO;
import com.meongj.project.todolist.service.TaskService;
import com.meongj.project.todolist.service.TaskServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class MainController {

    @Autowired
    private TaskServiceImpl taskServiceImpl;

    @RequestMapping("/")
    public ModelAndView mainPage() throws Exception {
        ModelAndView mv = new ModelAndView("mainPage.html");
//        System.out.println("main!!!");
        List<TaskVO> arr = taskServiceImpl.getTaskList();
//        for (TaskVO vo : arr){
//            System.out.println(vo.getId());
//            System.out.println(vo.getComplete());
//            System.out.println(vo.getContent());
//            System.out.println(vo.getHashtag());
//            System.out.println(vo.getDate());
//            System.out.println(vo.getPriority());
//        }

        return mv;
    }


    @PostMapping("/todolist/register")
    public int register(@RequestBody TaskVO taskVO) throws Exception {
        System.out.println(taskVO.getTitle());
        System.out.println(taskVO.getContent());
        System.out.println(taskVO.getHashtag());
        System.out.println(taskVO.getStartTime());
        System.out.println(taskVO.getEndTime());
        System.out.println(taskVO.getPriority());

        //yyyy-mm-dd hh:mm:00 (hh:mm)
        //오늘날찌
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String today = simpleDateFormat.format(now);
        System.out.println("today="+today);
        String getStartTime  = taskVO.getStartTime().replace(":","");
        String getEndTime = taskVO.getEndTime().replace(":","");
        System.out.println(today.toString() + getStartTime + "00");
        taskVO.setStartTime(today.toString() + getStartTime + "00");
        taskVO.setEndTime(today.toString() + getEndTime + "00");

        System.out.println(taskVO);
        int insert_flag = taskServiceImpl.addTask(taskVO);
        if (insert_flag == 1) log.info("INSERT TASK SUCCESS!!");

        return insert_flag;
    }
}

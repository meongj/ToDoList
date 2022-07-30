package com.meongj.project.todolist.controller;

import com.meongj.project.todolist.domain.TaskVO;
import com.meongj.project.todolist.service.TaskServiceImpl;
import com.meongj.project.todolist.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class MainController {

    @Autowired
    private TaskServiceImpl taskServiceImpl;
    @Autowired
    private CommonUtil comnUtil;

    @RequestMapping("/")
    public ModelAndView mainPage() throws Exception {
        ModelAndView mv = new ModelAndView("index");

        // 오늘날짜 구하기
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        DateTimeFormatter formatter_week = DateTimeFormatter.ofPattern("E");
        DateTimeFormatter formatter_today = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String today = now.format(formatter);
        String dayOfWeek = now.format(formatter_week) + "요일";

        // 할일 리스트
        int taskTotal = taskServiceImpl.getTaskCnt();

        List<TaskVO> taskList = taskServiceImpl.getTaskList();
        for (int i=0; i<taskList.size(); i++) {
            String startTime = taskList.get(i).getStartTime();
            String endTime = taskList.get(i).getEndTime();

            String startTimeSplit = comnUtil.nullCheck(startTime.substring(11, 16));
            String endTimeSplit = comnUtil.nullCheck(endTime.substring(11, 16));

            // 오전, 오후 구분
            if (Integer.parseInt(startTimeSplit.replace(":","")) < 1200) taskList.get(i).setStartTime(startTimeSplit + " am");
            else taskList.get(i).setStartTime(startTimeSplit + " pm");

            if (Integer.parseInt(endTimeSplit.replace(":","")) < 1200) taskList.get(i).setEndTime(endTimeSplit + " am");
            else taskList.get(i).setEndTime(endTimeSplit + " pm");


            String todayDiff = now.format(formatter_today).toString();
//            System.out.println("가져온오늘날짜= "+ todayDiff);
//            System.out.println("가져온끝날짜=" + endTime);
           //Date format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(startTime);
            Date endformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(endTime);
//            System.out.println("끝날짜="+ endformat);
            Date todayFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(todayDiff);
//            System.out.println("오늘=" + todayFormat);

            long diffMin = (endformat.getTime() - todayFormat.getTime()) / 60000; //분 차이
           // long diffHor = (format2.getTime() - format1.getTime()) / 3600000; //시 차이
//            System.out.println("분차이=" + diffMin);
            StringBuffer leftTime = new StringBuffer();
            long hour = diffMin / 60;
            long min = diffMin % 60;

            if(diffMin <= 0) {
                leftTime.append("기한이 지난 할 일입니다.");
            } else {
                if (min == 0) leftTime.append(hour).append("h");
                else {
                    if (hour == 0) leftTime.append(min % 60).append("min");
                    else leftTime.append(hour).append("h ").append(min % 60).append("min");
                }
            }
            System.out.println("leftTime=" + leftTime);
            if (taskList.get(i).getComplete() == 1) {
                taskList.get(i).setLeftTime("완료된 할 일입니다.");
                taskList.get(i).setCheck("form-check-label text-decoration-line-through");
                taskList.get(i).setColor("text-danger");
            }
            else {
                taskList.get(i).setLeftTime(leftTime.toString());
                taskList.get(i).setCheck("form-check-label");
                taskList.get(i).setColor("text-muted");
            }

        }
        // 줄바꿈
        String nlString = System.getProperty("line.separator").toString();

        mv.addObject("today", today);
        mv.addObject("dayOfWeek", dayOfWeek);
        mv.addObject("taskTotal", taskTotal);
        mv.addObject("taskList" , taskList);
        mv.addObject("nlString", nlString);
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
        System.out.println(taskVO.getFlag());
        System.out.println("id="+taskVO.getId());
        String flag = taskVO.getFlag();
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

    @PostMapping("/todolist/delete")
    public int deleteTask(@RequestBody TaskVO taskVO) throws Exception {
        System.out.println("삭제");
        System.out.println(taskVO);
        int deleteFlag = taskServiceImpl.deleteTask(taskVO);
        System.out.println("delete="+deleteFlag);
        if(deleteFlag == 1) log.info("DELETE TASK SUCCESS!!");

        return deleteFlag;
    }

    // 할 일 완료된 것 체크저장
    @PostMapping("/todolist/completeTask")
    public int completeTask(@RequestBody TaskVO taskVO) throws Exception {
        System.out.println("id="+taskVO.getId());
        System.out.println("complete="+taskVO.getComplete());
        int result= taskServiceImpl.completeTask(taskVO);
        if (result == 1) log.info("CHECKBOX UPDATE SUCCESS");
        return result;
    }

}

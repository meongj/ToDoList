package com.meongj.project.todolist.controller;

import com.meongj.project.todolist.domain.TaskVO;
import com.meongj.project.todolist.service.TaskServiceImpl;
import com.meongj.project.todolist.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class MainController {

    @Autowired
    private TaskServiceImpl taskServiceImpl;
    @Autowired
    private CommonUtil comnUtil;

    @RequestMapping("/")
    public ModelAndView mainPage(
            @RequestParam(required = false) Integer complete,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String hashtag
            ) throws Exception {
        ModelAndView mv = new ModelAndView("index");

       HashMap<String, Object> map  = taskProcessList(complete, priority, hashtag);

        mv.addObject("today", map.get("today"));
        mv.addObject("dayOfWeek", map.get("dayOfWeek"));
        mv.addObject("taskTotal", map.get("taskTotal"));
        mv.addObject("taskList" , map.get("taskList"));
        mv.addObject("nlString", map.get("nlString"));
        return mv;
    }


    public HashMap<String, Object> taskProcessList(Integer complete, String priority, String hashtag) throws Exception {
        // 오늘날짜 구하기
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        DateTimeFormatter formatter_week = DateTimeFormatter.ofPattern("E");
        DateTimeFormatter formatter_today = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String today = now.format(formatter);
        String dayOfWeek = now.format(formatter_week) + "요일";

        // 할일 리스트
        int taskTotal = taskServiceImpl.getTaskCnt();

        TaskVO vo = new TaskVO();
        // 정렬 param 셋팅
        vo.setComplete((complete == null) ? 3 : complete); // default=3 (아무 선택 안 했을 경우)
        vo.setPriority((priority == null) ? "asc" : priority);// 기본 빠른 시간순 default=asc
        vo.setHashtag((hashtag == null) ? "" : hashtag); // 해쉬태그 검색시

        log.info("[param] complete="+complete+" priority="+priority +" hashtag="+hashtag);

        List<TaskVO> taskList = taskServiceImpl.getTaskList(vo);

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
            Date endFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime);
            Date todayFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(todayDiff);

            // getTime : ms 반환
            long diffMin = (endFormat.getTime() - todayFormat.getTime()) / 60000; //분 차이

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

        HashMap<String, Object> map = new HashMap();
        map.put("today", today);
        map.put("dayOfWeek", dayOfWeek);
        map.put("taskTotal", taskTotal);
        map.put("taskList" , taskList);
        map.put("nlString", nlString);

        return  map;
    }

    // 드롭다운 리스트 필터링 Ajax
    // tag 검색, 정렬
    @PostMapping("/todolist/filtering")
    public String filtering(Model model,
                            @RequestParam Map<String, Object> paramMap) throws Exception {

        HashMap<String, Object> resultMap = taskProcessList
                ( (Integer) Integer.parseInt(paramMap.get("complete").toString()),
                (String) paramMap.get("priority"), (String)paramMap.get("hashTag"));
        List<TaskVO> list = (List<TaskVO>) resultMap.get("taskList");
        // 줄바꿈
        String nlString = (String) resultMap.get("nlString");

        model.addAttribute("taskList", list);
        model.addAttribute("nlString", nlString);

        return "index :: #contentId";
    }

}

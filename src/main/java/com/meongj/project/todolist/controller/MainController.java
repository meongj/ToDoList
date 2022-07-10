package com.meongj.project.todolist.controller;

import com.meongj.project.todolist.domain.TaskVO;
import com.meongj.project.todolist.service.TaskService;
import com.meongj.project.todolist.service.TaskServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@Slf4j
public class MainController {

    @Autowired
    private TaskServiceImpl taskServiceImpl;

    @RequestMapping("/")
    public ModelAndView mainPage() throws Exception {
        ModelAndView mv = new ModelAndView("mainPage.html");
        System.out.println("main!!!");
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


    @ResponseBody
    @PostMapping("/todolist/register")
    public String register(TaskVO taskVO) {
        System.out.println(taskVO.getTitle());


        return "성공";
    }
}

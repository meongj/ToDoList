package com.meongj.project.todolist.controller;

import com.meongj.project.todolist.service.TaskService;
import com.meongj.project.todolist.service.TaskServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Slf4j
public class MainController {

    @Autowired
    private TaskServiceImpl taskServiceImpl;

    @RequestMapping("/")
    public ModelAndView mainPage() throws Exception {
        ModelAndView mv = new ModelAndView("mainPage.html");
        System.out.println("main!!!");

        return mv;
    }
}

package com.meongj.project.todolist.util;

import org.springframework.stereotype.Component;

@Component
public class CommonUtil {

    public String nullCheck(String str) {
        return (str == null) ? "" : str;
    }
}

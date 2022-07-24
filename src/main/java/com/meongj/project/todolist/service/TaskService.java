package com.meongj.project.todolist.service;

import com.meongj.project.todolist.domain.TaskVO;

import java.util.List;

public interface TaskService {
    // task 개수 출력
    public int getTaskCnt() throws Exception;
    //task list 가져오기
    public List<TaskVO> getTaskList() throws Exception;
    //task 추가
    public int addTask(TaskVO taskVO) throws Exception;

}

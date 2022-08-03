package com.meongj.project.todolist.service;

import com.meongj.project.todolist.domain.TaskVO;

import java.util.List;

public interface TaskService {
    // task 개수 출력
    public int getTaskCnt() throws Exception;
    //task list 가져오기
    public List<TaskVO> getTaskList(TaskVO taskVO) throws Exception;
    //task 추가
    public int addTask(TaskVO taskVO) throws Exception;
    //task 삭제
    public int deleteTask(TaskVO taskVO) throws Exception;
    //task 수정
    public int editTask(TaskVO taskVO) throws Exception;
    //할일 완료 체크
    public int completeTask(TaskVO taskVO) throws Exception;

}

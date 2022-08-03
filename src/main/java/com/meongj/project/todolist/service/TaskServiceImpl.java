package com.meongj.project.todolist.service;

import com.meongj.project.todolist.domain.TaskVO;
import com.meongj.project.todolist.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;


    @Override
    public int getTaskCnt() throws Exception {
        return taskRepository.getTaskCnt();
    }

    @Override
    public List<TaskVO> getTaskList(TaskVO taskVO) throws Exception {
        return taskRepository.getTaskList(taskVO);
    }

    @Override
    public int addTask(TaskVO taskVO) throws Exception {
        return taskRepository.addTask(taskVO);
    }

    @Override
    public int deleteTask(TaskVO taskVO) throws Exception {
        return taskRepository.deleteTask(taskVO);
    }

    @Override
    public int editTask(TaskVO taskVO) throws Exception {
        return taskRepository.editTask(taskVO);
    }

    @Override
    public int completeTask(TaskVO taskVO) throws Exception {
        return taskRepository.completeTask(taskVO);
    }
}

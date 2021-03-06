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
    public List<TaskVO> getTaskList() throws Exception {
        return taskRepository.getTaskList();
    }

    @Override
    public int addTask(TaskVO taskVO) throws Exception {
        return taskRepository.addTask(taskVO);
    }
}

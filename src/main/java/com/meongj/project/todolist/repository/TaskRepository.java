package com.meongj.project.todolist.repository;

import com.meongj.project.todolist.domain.TaskVO;
import com.meongj.project.todolist.service.TaskServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaskRepository {

    @Autowired
    protected SqlSessionTemplate sqlSession;

    public int getTaskCnt() throws Exception {
        return sqlSession.selectOne("taskMapper.getTaskCnt");
    }
    public List<TaskVO> getTaskList() throws Exception {
        return sqlSession.selectList("taskMapper.getTaskList");
    };

    public int addTask(TaskVO taskVO) throws Exception {
        return sqlSession.insert("taskMapper.addTask", taskVO);
    }

}

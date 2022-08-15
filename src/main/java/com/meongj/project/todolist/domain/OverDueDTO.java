package com.meongj.project.todolist.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OverDueDTO {
    private int count;
    private List<String> titles;

    public OverDueDTO() {
        titles = new ArrayList<>();
    }

    public void setTitle(String title) {
        titles.add(title);
    }
}

package com.example.sato_naoki.service;

import com.example.sato_naoki.controller.form.TaskForm;
import com.example.sato_naoki.repository.TaskRepository;
import com.example.sato_naoki.repository.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskrepository;
    public List<TaskForm> findAllTask() {
        List<Task> tasks = taskrepository.findAll();
        List<TaskForm> taskForms = setAllTaskForm(tasks);
        return taskForms;
    }

    public List<TaskForm> setAllTaskForm(List<Task> tasks) {
        List<TaskForm> taskForms = new ArrayList<>();

        for (int i = 0; i < tasks.size(); i++) {
            TaskForm taskForm = new TaskForm();
            Task task = tasks.get(i);
            taskForm.setId(task.getId());
            taskForm.setContent(task.getContent());
            taskForm.setStatus(task.getStatus());
            taskForm.setCreatedDate(task.getCreatedDate());
            taskForm.setUpdatedDate(task.getUpdatedDate());
            taskForm.setLimitedDate(task.getLimitedDate());
            taskForms.add(taskForm);
        }
        return taskForms;
    }

    public void deleteTask(Integer id) {
        taskrepository.deleteById(id);
    }
}

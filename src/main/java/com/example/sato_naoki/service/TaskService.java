package com.example.sato_naoki.service;

import com.example.sato_naoki.controller.form.TaskForm;
import com.example.sato_naoki.repository.TaskRepository;
import com.example.sato_naoki.repository.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskrepository;
    public List<TaskForm> findAllTask(String task, Integer status, LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime;
        LocalDateTime endDateTime;
        if (startDate == null) {
            startDateTime = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        } else {
            startDateTime = startDate.atStartOfDay();
        }
        if (endDate == null) {
            endDateTime = LocalDateTime.of(2100, 12, 31, 23, 59, 59);
        } else {
            endDateTime = endDate.atTime(23, 59, 59);
        }
        List<TaskForm> taskForms = new ArrayList<>();
        //taskやstatusがnullの場合を考える　最初の起動時はtaskはnullになる
        if (task == null && status == null) {
            List<Task> tasks = taskrepository.findByLimitedDateBetweenOrderByLimitedDateAsc(startDateTime, endDateTime);
            taskForms = setAllTaskForm(tasks);
        } else if (task == null && status != null) {
            List<Task> tasks = taskrepository.findByLimitedDateBetweenAndStatusOrderByLimitedDateAsc(startDateTime, endDateTime, status);
            taskForms = setAllTaskForm(tasks);
        } else if(status == null){
            List<Task> tasks = taskrepository.findByLimitedDateBetweenOrderByLimitedDateAsc(startDateTime, endDateTime);
            taskForms = setAllTaskForm(tasks);
        }else if (!task.isBlank() && status == null) {
            List<Task> tasks = taskrepository.findByLimitedDateBetweenAndContentOrderByLimitedDateAsc(startDateTime, endDateTime, task);
            taskForms = setAllTaskForm(tasks);
        } else if (task.isBlank() && status != null) {
            List<Task> tasks = taskrepository.findByLimitedDateBetweenAndStatusOrderByLimitedDateAsc(startDateTime, endDateTime, status);
            taskForms = setAllTaskForm(tasks);
        } else {
            List<Task> tasks = taskrepository.findBylimitedDateBetweenAndContentAndStatusOrderByLimitedDateAsc(startDateTime, endDateTime, task, status);
            taskForms = setAllTaskForm(tasks);
        }
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
            LocalDate localDate = task.getLimitedDate().toLocalDate();
            taskForm.setLimitedDate(localDate);
            taskForms.add(taskForm);
        }
        return taskForms;
    }

    public void deleteTask(Integer id) {
        taskrepository.deleteById(id);
    }

    //idからタスクをセットする
    public Task findTask(Integer id) {
        List<Task> tasks = new ArrayList<>();
        tasks.add(taskrepository.findById(id).orElse(null));
        return tasks.get(0);
    }
    public void saveStatus(Integer id, Integer status) {
        Task task = findTask(id);
        task.setStatus(status);
        LocalDateTime time = LocalDateTime.now();
        task.setUpdatedDate(time);
        taskrepository.save(task);
    }

    public void saveTask(TaskForm taskForm) {
        Task task = new Task();
        task.setContent(taskForm.getContent());
        task.setStatus(taskForm.getStatus());
        task.setCreatedDate(taskForm.getCreatedDate());
        task.setLimitedDate(taskForm.getLimitedDate().atStartOfDay());
        task.setUpdatedDate(taskForm.getUpdatedDate());
        taskrepository.save(task);
    }

    public TaskForm findTaskForm(Integer id) {
        Task task = findTask(id);
        TaskForm taskForm = new TaskForm();
        taskForm.setId(task.getId());
        taskForm.setContent(task.getContent());
        taskForm.setStatus(task.getStatus());
        taskForm.setCreatedDate(task.getCreatedDate());
        taskForm.setUpdatedDate(task.getUpdatedDate());
        LocalDate localDate = task.getLimitedDate().toLocalDate();
        taskForm.setLimitedDate(localDate);
        return taskForm;
    }
}

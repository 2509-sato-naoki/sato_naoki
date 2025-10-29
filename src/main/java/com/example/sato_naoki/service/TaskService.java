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

        //タスクがnullの場合
        //if分のネストが多い
        //if分の条件がおかしい
        //これ以外にもやり方はある
        //①specificationというクラスを使う方法がある←可能であればこっちをやってみる　SB推奨
        //動的な条件がある場合、specificationは便利なクラスとして用意されてるもの
        //②@Queryを使って従来のsqlを使う
        //上記の調べ方 springboot springJPA 条件　動的
        //検索の仕方　漠然ではなく、具体的に調べる　検索のキーワード、文言
//
//        if (task == null) {
//            if (status == null) {
//                List<Task> tasks = taskrepository.findByLimitedDateBetweenOrderByLimitedDateAsc(startDateTime, endDateTime);
//                taskForms = setAllTaskForm(tasks);
//            } else {
//                List<Task> tasks = taskrepository.findByLimitedDateBetweenAndStatusOrderByLimitedDateAsc(startDateTime, endDateTime, status);
//                taskForms = setAllTaskForm(tasks);
//            }
//        } else {
//            //タスクがnullではない場合（isblankも判断する）
//            if (task.isBlank() && status == null) {
//                List<Task> tasks = taskrepository.findByLimitedDateBetweenOrderByLimitedDateAsc(startDateTime, endDateTime);
//                taskForms = setAllTaskForm(tasks);
//            } else if (!task.isBlank() && status != null) {
//                List<Task> tasks = taskrepository.findBylimitedDateBetweenAndContentAndStatusOrderByLimitedDateAsc(startDateTime, endDateTime, task, status);
//                taskForms = setAllTaskForm(tasks);
//            } else if (task.isBlank() && status != null) {
//                List<Task> tasks = taskrepository.findByLimitedDateBetweenAndStatusOrderByLimitedDateAsc(startDateTime, endDateTime, status);
//                taskForms = setAllTaskForm(tasks);
//            } else if (!task.isBlank() && status == null) {
//                List<Task> tasks = taskrepository.findByLimitedDateBetweenAndContentOrderByLimitedDateAsc(startDateTime, endDateTime, task);
//                taskForms = setAllTaskForm(tasks);
//            }
//        }

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

    //既存のsaveTaskメソッドではタスク編集ができなかったので別にメソッドを作成　setIdの処理
    public void saveTaskEdit(TaskForm taskForm) {
        Task task = new Task();
        task.setId(taskForm.getId());
        task.setContent(taskForm.getContent());
        task.setStatus(taskForm.getStatus());
        task.setLimitedDate(taskForm.getLimitedDate().atStartOfDay());
        task.setCreatedDate(taskForm.getCreatedDate());
        task.setUpdatedDate(LocalDateTime.now());
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
        taskForm.setStatus(task.getStatus());
        LocalDate localDate = task.getLimitedDate().toLocalDate();
        taskForm.setLimitedDate(localDate);
        return taskForm;
    }
}

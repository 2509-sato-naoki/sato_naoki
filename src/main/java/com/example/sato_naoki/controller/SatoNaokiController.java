package com.example.sato_naoki.controller;

import com.example.sato_naoki.controller.form.TaskForm;
import com.example.sato_naoki.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SatoNaokiController {
    @Autowired
    TaskService taskService;

    @GetMapping
    public ModelAndView top(@RequestParam(required = false) String task,
                            @RequestParam(required = false) Integer status,
                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") LocalDate startDate,
                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") LocalDate  endDate) {
        ModelAndView mav = new ModelAndView();
        List<TaskForm> contentData = taskService.findAllTask(task, status, startDate, endDate);
        mav.setViewName("/top");
        mav.addObject("startDate", startDate);
        mav.addObject("endDate", endDate);
        mav.addObject("text", task);
        mav.addObject("status", status);
        mav.addObject("contents", contentData);
        return mav;
    }

    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteContent(@PathVariable Integer id) {
        taskService.deleteTask(id);
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/statusEdit/{id}")
    public ModelAndView statusEditContent(@PathVariable Integer id, @RequestParam(required = false) Integer status) {
        taskService.saveStatus(id, status);
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/addTask")
    public ModelAndView addTaskContent(@ModelAttribute("formModel") TaskForm taskForm) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/addTask");
//        TaskForm taskForm = new TaskForm();
        mav.addObject("formModel", taskForm);
        return mav;
    }

    @PostMapping("/addTask")
    public ModelAndView addTaskProcessContent(@ModelAttribute("formModel") @Validated TaskForm taskForm,
                                              BindingResult result, RedirectAttributes redirectAttributes) {
        List<String> errorMessages = new ArrayList<>();
        if (result.hasErrors()) {
            //この中に
            for (FieldError error : result.getFieldErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
        }
        //エラーメッセージの判定
        if (errorMessages.size() >= 1) {
            redirectAttributes.addFlashAttribute("errorMessages", errorMessages);
            redirectAttributes.addFlashAttribute("formModel", taskForm);
            // errorMessagesにresultの中身を格納する
            return new ModelAndView("redirect:/addTask");
        } else {
            taskForm.setStatus(1);
            taskForm.setUpdatedDate(LocalDateTime.now());
            taskForm.setCreatedDate(LocalDateTime.now());
            taskService.saveTask(taskForm);
            return new ModelAndView("redirect:/");
        }
    }

//    private void errorMessages(TaskForm taskForm, List<String> errorMessages) {
//        if (taskForm.getContent().isBlank()) {
//            errorMessages.add("タスクを入力してください");
//        } else if (taskForm.getContent().length() > 140) {
//            errorMessages.add("タスクは140文字以内で入力してください");
//        }
//
//        if (taskForm.getLimitedDate() == null) {
//            errorMessages.add("期限を設定してください");
//        } else if (taskForm.getLimitedDate().isBefore(LocalDate.now())) {
//            errorMessages.add("無効な日付です");
//        }
//    }

    //編集画面表示
    @GetMapping("/edit")
    public ModelAndView editContent(RedirectAttributes redirectAttributes,
                                    HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        //idチェック
        if (request.getParameter("id") != null && request.getParameter("id").matches("^[0-9]+$")) {
            int id = Integer.parseInt(request.getParameter("id"));
            TaskForm taskForm = taskService.findTaskForm(id);
            mav.addObject("formModel", taskForm);
            mav.setViewName("/edit");
            return mav;
        } else {
            redirectAttributes.addFlashAttribute("errorMessages", "不正なパラメータです");
            return new ModelAndView("redirect:/");
        }
    }

    //編集処理
    @PostMapping("/editTask")
    public ModelAndView editTaskContent(@ModelAttribute("formModel") @Validated TaskForm taskForm
    , BindingResult result, RedirectAttributes redirectAttributes) {
        //正規表現をここで行う
        List<String> errorMessages = new ArrayList<>();

        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
        }
        if (errorMessages.size() >= 1) {
            redirectAttributes.addFlashAttribute("errorMessages", errorMessages);
            redirectAttributes.addFlashAttribute("content", taskForm.getContent());
            redirectAttributes.addFlashAttribute("limitedDate", taskForm.getLimitedDate());
            return new ModelAndView("redirect:/edit?id=" + taskForm.getId());
        } else {
            taskService.saveTaskEdit(taskForm);
            return new ModelAndView("redirect:/");
        }

    }

}
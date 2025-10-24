package com.example.sato_naoki.controller;

import com.example.sato_naoki.controller.form.TaskForm;
import com.example.sato_naoki.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SatoNaokiController {
    @Autowired
    TaskService taskService;

    @GetMapping
    public ModelAndView top() {
        ModelAndView mav = new ModelAndView();
        List<TaskForm> contentData = taskService.findAllTask();
        mav.setViewName("/top");
        mav.addObject("contents", contentData);
        return mav;
    }

    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteContent(@PathVariable Integer id) {
        taskService.deleteTask(id);
        return new ModelAndView("redirect:/");
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.controllers;

import com.example.timesheetclient.models.JobHistory;
import com.example.timesheetclient.services.JobHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Lenovo-PC
 */
@Controller
@RequestMapping("/history")
public class JobHistoryController {
    
    private JobHistoryService jobHistoryService;
    
    @Autowired
    public JobHistoryController(JobHistoryService jobHistoryService) {
        this.jobHistoryService = jobHistoryService;
    }
    
    @GetMapping
    public String index(Model model) {
        model.addAttribute("histories", jobHistoryService.getAll());
        return "timesheet/job-history";
    }
    
    @PostMapping
    public String create(Model model,
            RedirectAttributes attributes) {
        jobHistoryService.create();
        attributes.addFlashAttribute("message", "Create Successed");
        return "redirect:/history";
    }
}

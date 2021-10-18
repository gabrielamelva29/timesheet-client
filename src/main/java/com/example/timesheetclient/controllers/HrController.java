/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.controllers;

import com.example.timesheetclient.models.JobHistory;
import com.example.timesheetclient.services.JobHistoryService;
import com.example.timesheetclient.services.JobService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Lenovo-PC
 */
@Controller
public class HrController {

    private JobHistoryService jobHistoryService;
    private JobService jobService;

    @Autowired
    public HrController(JobHistoryService jobHistoryService, JobService jobService) {
        this.jobHistoryService = jobHistoryService;
        this.jobService = jobService;
    }

    @GetMapping("/hr")
    public String index2(Model model, String month, Integer year) {
        model.addAttribute("list", jobHistoryService.findByHr());
        return "timesheet/approvement";
    }

    @PostMapping("/approved/{id}")
    public String approved(@PathVariable Integer id,
            Model model,
            RedirectAttributes attributes) {
        jobHistoryService.approved(id);
        return "redirect:/hr";
    }

    @PostMapping("/sent/{id}")
    public String sent(@PathVariable Integer id,
            Model model,
            RedirectAttributes attributes) {
        jobHistoryService.sent(id);
        return "redirect:/history";
    }

    @GetMapping("/search")
    public String year(JobHistory jobHistory,
            @RequestParam(value = "month", required = false) String month,
            @RequestParam(value = "year", required = false) Integer year,
            Model model,
            RedirectAttributes attributes) {
        List<JobHistory> j = jobHistoryService.findByYear(month, year);
        model.addAttribute("list", j);
        return "timesheet/approvement";
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.controllers;

import com.example.timesheetclient.models.Employee;
import com.example.timesheetclient.models.JobHistory;
import com.example.timesheetclient.services.EmployeeService;
import com.example.timesheetclient.services.JobHistoryService;
import com.example.timesheetclient.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private EmployeeService employeeService;
    private StatusService statusService;

    @Autowired
    public JobHistoryController(JobHistoryService jobHistoryService, EmployeeService employeeService, StatusService statusService) {
        this.jobHistoryService = jobHistoryService;
        this.employeeService = employeeService;
        this.statusService = statusService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("histories", jobHistoryService.getAll());
        return "timesheet/job-history";
    }

    @PostMapping("add")
    public String created(Model model, Employee employee,
            RedirectAttributes attributes) {
        if (jobHistoryService.getByMonth() == null) {
            statusService.counts(Integer.SIZE);
            JobHistory jobHistory = jobHistoryService.creates();
            JobHistory emp = jobHistoryService.getById(jobHistory.getId());
            model.addAttribute("history", emp);
            return "timesheet/add-form-employee";
        } else {
            return "redirect:/history?created=false";
        }
    }
}

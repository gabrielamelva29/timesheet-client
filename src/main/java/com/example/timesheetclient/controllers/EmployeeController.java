
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.controllers;

import com.example.timesheetclient.models.Employee;
import com.example.timesheetclient.models.Job;
import com.example.timesheetclient.models.JobHistory;
import com.example.timesheetclient.services.EmployeeService;
import com.example.timesheetclient.services.JobHistoryService;
import com.example.timesheetclient.services.JobService;
import com.example.timesheetclient.services.StatusService;
import java.util.ArrayList;
import static java.util.Comparator.comparing;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author gabri
 */
@Controller
//@RequestMapping("/history")
public class EmployeeController {

    private EmployeeService employeeService;
    private JobService jobService;
    private StatusService statusService;
    private JobHistoryService jobHistoryService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, JobService jobService, StatusService statusService, JobHistoryService jobHistoryService) {
        this.employeeService = employeeService;
        this.jobService = jobService;
        this.statusService = statusService;
        this.jobHistoryService = jobHistoryService;
    }

    public JobHistory idemp = null;

    
    //HISTORY ADD
    @GetMapping("/history")
    public String index(Model model) {
        model.addAttribute("histories", jobHistoryService.getAll());
        return "timesheet/job-history";
    }
    
    @PostMapping("/history/add")
    public String create(Model model,
            RedirectAttributes attributes) {
        jobHistoryService.create();
        return "redirect:/history";
    }

    //HR
    @GetMapping("/hr")
    public String index2(Model model) {
        model.addAttribute("histories", jobHistoryService.findByHr());
        return "timesheet/approvement";
    }
    
    @PostMapping("/approved/{id}")
    public String approved(@PathVariable Integer id,
            Model model,
            RedirectAttributes attributes) {
        jobHistoryService.approved(id);
        return "redirect:/history";
    }
    
    @PostMapping("/sent/{id}")
    public String sent(@PathVariable Integer id,
            Model model,
            RedirectAttributes attributes) {
        jobHistoryService.sent(id);
        return "redirect:/history";
    }

    //EMPLOYEE ADD
    @GetMapping("/history/{id}")
    public String update(@PathVariable Integer id, Employee employee, Model model) {
        model.addAttribute("bt", statusService.getById("BT"));
        model.addAttribute("p", statusService.getById("P"));
        model.addAttribute("pm", statusService.getById("PM"));
        model.addAttribute("s", statusService.getById("S"));
        model.addAttribute("v", statusService.getById("V"));
        model.addAttribute("x", statusService.getById("X"));
        model.addAttribute("history", jobHistoryService.getById(id));
        JobHistory jobHistory = jobHistoryService.getById(id);
        idemp = jobHistoryService.getById(id);
        model.addAttribute("jobs", jobService.findByEmployee(idemp.getEmployee().getId()));
        
        if (jobHistory.getEmployee() == null) {
            return "timesheet/add-form-employee";
        }
        model.addAttribute("employee", employeeService.getById(jobHistory.getEmployee().getId()));
        return "timesheet/update-form-employee";
    }

    @PostMapping("/history/{id}")
    public String create(@PathVariable Integer id,
            Employee employee,
            BindingResult result,
            Model model,
            RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "timesheet/add-form-employee";
        }
        employeeService.create(employee);
        attributes.addFlashAttribute("message", "Create Successed");
        return "redirect:/history/{id}";
    }

    //EMPLOYEE UPDATE
    @PutMapping("/history/{id}")
    public String update(@PathVariable Integer id,
            Employee employee,
            BindingResult result,
            Model model,
            RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "timesheet/update-form-employee";
        }
        JobHistory history = jobHistoryService.getById(id);
        employeeService.update(history.getEmployee().getId(),employee);
        attributes.addFlashAttribute("message", "Create Successed");
        return "redirect:/history/{id}";
    }

    //JOB ADD
    @GetMapping("/job/add/{id}")
    public String create(@PathVariable Integer id,
            Job job,
            Model model){
        model.addAttribute("statuses", statusService.getAll());
        model.addAttribute("employee", employeeService.getById(id));
        model.addAttribute("history", idemp.getId());
        return "timesheet/add-form-activity";
    }

    @PostMapping("/job/add/{id}/{ids}")
    public String create(Job job,
            @PathVariable Integer id,
            @PathVariable Integer ids,
            BindingResult result,
            Model model,
            RedirectAttributes attributes){
        System.out.println(job);
        if(result.hasErrors()){
            model.addAttribute("statuses", statusService.getAll());
            return "timesheet/add-form-activity";
        }
        jobService.create(job,id);
        attributes.addFlashAttribute("message", "Create Successed");
       return "redirect:/history/{ids}";
    }
    
    //JOB EDIT
    @GetMapping("/job/edit/{id}/{ids}")
    public String update(@PathVariable Integer id, JobHistory jobHistory,
            @PathVariable Integer ids,
            Model model) {
        model.addAttribute("job", jobService.getById(id));
        model.addAttribute("statuses", statusService.getAll());
        model.addAttribute("history", idemp.getId());
        return "timesheet/update-form-activity";
    }
    
    @PutMapping("/job/edits/{id}/{ids}")
    public String update(@PathVariable Integer id,
            @PathVariable Integer ids,
            Job job,
            BindingResult result,
            Model model,
            RedirectAttributes attributes) {
        
        if (result.hasErrors()) {
            model.addAttribute("statuses", statusService.getAll());
            return "timesheet/update-form-activity";
        }
        jobService.update(id, job);
        attributes.addFlashAttribute("message", "Update Successed");
        return "redirect:/history/{ids}";
    }
}

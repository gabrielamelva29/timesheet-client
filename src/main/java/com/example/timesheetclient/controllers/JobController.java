/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.controllers;

import com.example.timesheetclient.models.Employee;
import com.example.timesheetclient.models.Job;
import com.example.timesheetclient.services.EmployeeService;
import com.example.timesheetclient.services.JobService;
import com.example.timesheetclient.services.StatusService;
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
@RequestMapping("/job")
public class JobController {
   private EmployeeService employeeService;
    private JobService jobService;
    private StatusService statusService;

    @Autowired
    public JobController(EmployeeService employeeService, JobService jobService, StatusService statusService) {
        this.employeeService = employeeService;
        this.jobService = jobService;
        this.statusService = statusService;
    }

    @GetMapping
    public String index(Job job, Model model) {
//        model.addAttribute("jobs", jobService.getAll());
//        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("statuses", statusService.getAll());
        return "employee/add-form-activity";
    }

    @GetMapping("/add")
    public String create(Job job, Model model){
        model.addAttribute("statuses", statusService.getAll());
        return "employee/add-form-activity";
    }

    @PostMapping("/add")
    public String create(Job job, 
            BindingResult result,
            Model model,
            RedirectAttributes attributes){
        System.out.println(job);
        if(result.hasErrors()){
            return "employee/add-form-activity";
        }
        jobService.create(job);
        attributes.addFlashAttribute("message", "Create Successed");
        System.out.println("mantab");
       return "employee/add-form-activity";
    }
    

    @GetMapping("/edit/{id}")
    public String update(@PathVariable Integer id, Model model) {
        model.addAttribute("jobs", jobService.getById(id));
        model.addAttribute("employee", employeeService.getById(id));
        model.addAttribute("statuses", statusService.getAll());
        return "employee/update-form";
    }

    @PutMapping("/edit/{id}")
    public String update(@PathVariable Integer id,
            @Valid Job job,
            BindingResult result,
            Model model,
            RedirectAttributes attributes) {
        System.out.println(job.toString());
        if (result.hasErrors()) {
            model.addAttribute("employees", employeeService.getAll());
            model.addAttribute("statuses", statusService.getAll());
            return "job/update-form";
        }
        jobService.update(id, job);
        attributes.addFlashAttribute("message", "Update Successed");
        return "redirect:/employee?updated=true";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        jobService.delete(id);
        return "redirect:/job?deleted=true";
    }
}

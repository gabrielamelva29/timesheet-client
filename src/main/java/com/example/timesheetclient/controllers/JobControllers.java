/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.controllers;

import com.example.timesheetclient.models.Employee;
import com.example.timesheetclient.models.Job;
import com.example.timesheetclient.models.JobHistory;
import com.example.timesheetclient.models.ResponseModel;
import com.example.timesheetclient.services.EmployeeService;
import com.example.timesheetclient.services.JobHistoryService;
import com.example.timesheetclient.services.JobService;
import com.example.timesheetclient.services.StatusService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author gabri
 */
@Controller
@RequestMapping("/job")
public class JobControllers {

    private EmployeeService employeeService;
    private JobService jobService;
    private StatusService statusService;
    private JobHistoryService jobHistoryService;


    @Autowired
    public JobControllers(EmployeeService employeeService, JobService jobService, StatusService statusService, JobHistoryService jobHistoryService) {
        this.employeeService = employeeService;
        this.jobService = jobService;
        this.statusService = statusService;
        this.jobHistoryService = jobHistoryService;
    }

    @GetMapping
    public String index(Job job, Model model) {
        model.addAttribute("statuses", statusService.getAll());
        model.addAttribute("histories", jobHistoryService.getAll());
        return "timesheet/add-form-activity";
    }

//    @GetMapping("/add")
//    public String create(Job job, Model model){
//        model.addAttribute("statuses", statusService.getAll());
//        return "timesheet/add-form-activity";
//    }
//
//    @PostMapping("/add")
//    public String create(Job job, 
//            BindingResult result,
//            Model model,
//            RedirectAttributes attributes){
//        System.out.println(job);
//        if(result.hasErrors()){
//            model.addAttribute("statuses", statusService.getAll());
//            return "timesheet/add-form-activity";
//        }
//        jobService.create(job,id);
//        attributes.addFlashAttribute("message", "Create Successed");
//       return "redirect:/history";
//    }

//    @GetMapping("/edits/{id}/{ids}")
//    public String update(@PathVariable Integer id,JobHistory jobHistory,
//            @PathVariable Integer ids,
//            Model model) {
//        model.addAttribute("job", jobService.getById(id));
//        model.addAttribute("statuses", statusService.getAll());
//        System.out.println("iki");
//        System.out.println(jobHistoryService.getById(id));
//        model.addAttribute("history", 26);
//        return "timesheet/update-form-activity";
//    }
    
//    @PutMapping("/edits/{id}/{ids}")
//    public String update(@PathVariable Integer id,
//            @PathVariable Integer ids,
//            Job job,
//            BindingResult result,
//            Model model,
//            RedirectAttributes attributes) {
//        
//        if (result.hasErrors()) {
//            model.addAttribute("statuses", statusService.getAll());
//            return "timesheet/update-form-activity";
//        }
//        jobService.update(id, job);
//        attributes.addFlashAttribute("message", "Update Successed");
//        return "redirect:/history/{ids}";
//    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        jobService.delete(id);
        return "redirect:/history?deleted=true";
    }
}

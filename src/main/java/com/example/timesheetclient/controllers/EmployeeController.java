
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.controllers;

import com.example.timesheetclient.models.Employee;
import com.example.timesheetclient.services.EmployeeService;
import com.example.timesheetclient.services.JobService;
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
@RequestMapping("/employee")
public class EmployeeController {
    
    private EmployeeService employeeService;
    private JobService jobService;
    
    @Autowired
    public EmployeeController(EmployeeService employeeService, JobService jobService) {
        this.employeeService = employeeService;
        this.jobService = jobService;
    }
    
    @GetMapping
    public String index(Employee employee,Model model){
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("jobs", jobService.getAll());
        return "employee/add-form-employee";
    }

//    @GetMapping("/add")
//    public String create(Employee employee, Model model){
//        return "employee/add-form-employee";
//    }
    
    @PostMapping("/add")
    public String create(Employee employee, 
            BindingResult result,
            Model model,
            RedirectAttributes attributes){
        if(result.hasErrors()){
            return "employee/add-form-employee";
        }
        employeeService.create(employee);
        attributes.addFlashAttribute("message", "Create Successed");
       return "employee/add-form-employee";
    }
    
    @GetMapping("/edit/{id}")
    public String update(@PathVariable Integer id, Model model){
        model.addAttribute("employee", employeeService.getById(id));
        return "employee/update-form";
    }
    
    @PutMapping("/edit/{id}")
    public String update(@PathVariable Integer id,
            Employee employee,
            BindingResult result,
            Model model, 
            RedirectAttributes attributes){
        if(result.hasErrors()){
            return "employee/update-form";
        }
        employeeService.update(id,employee);
        attributes.addFlashAttribute("message", "Create Successed");
        return "redirect/employee";
    }
    
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        employeeService.delete(id);
        return "redirect:/employee";
    }
}


    
     
   
     
   
    
 
    
   
    
   
    
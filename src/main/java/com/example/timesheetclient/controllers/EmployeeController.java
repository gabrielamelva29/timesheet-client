
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.controllers;

import com.example.timesheetclient.models.Employee;
import com.example.timesheetclient.services.EmployeeService;
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
    
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    @GetMapping
    public String index(Employee employee,Model model ) {
        model.addAttribute("employees", employeeService.getAll());
        return "employee/layout";
    }
    
    @GetMapping("/add")
    public String create(Employee employee, Model model){
//        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("employees", new Employee());
            
        return "employee/add-form-employee";
    }
    
    @PostMapping("/add")
    public String create(@Valid Employee employee, BindingResult result, RedirectAttributes redirectAttributes, Model model){
        if (result.hasErrors()) {     
            model.addAttribute("employees", employeeService.getAll());
            model.addAttribute("employees", new Employee());

            return "employee/add-form-employee";         
        }
        
        return "redirect:/add-form-employee";
    }
    
    @PutMapping("/edit/{id}")
    public String update(@PathVariable Long id, @Valid Employee employee,
            BindingResult result, RedirectAttributes redirect, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("employees", employeeService.getAll());
      
            return "employee/update-form";
        }

        employeeService.update(id, employee);

        redirect.addFlashAttribute("message", "Employee updated");

        return "redirect:/add-form-employee";
    }
    
    @DeleteMapping("/{id}")
     public String delete(@PathVariable Long id) {
        employeeService.delete(id);
        return "redirect:/employee?deleted=true";
    }
}


    
     
   
     
   
    
 
    
   
    
   
    

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.controllers;

import com.example.timesheetclient.models.Employee;
import com.example.timesheetclient.services.EmployeeService;
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
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService employeeService;
    private JobService jobService;
    private StatusService statusService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, JobService jobService, StatusService statusService) {
        this.employeeService = employeeService;
        this.jobService = jobService;
        this.statusService = statusService;
    }

    @GetMapping
    public String index(Employee employee, Model model) {
        model.addAttribute("bt", statusService.getById("BT"));
        model.addAttribute("p", statusService.getById("P"));
        model.addAttribute("pm", statusService.getById("PM"));
        model.addAttribute("s", statusService.getById("S"));
        model.addAttribute("v", statusService.getById("V"));
        model.addAttribute("x", statusService.getById("X"));
        List<Employee> list = employeeService.getAll();

        if (list.isEmpty()) {
            model.addAttribute("employees", employeeService.getAll());
            model.addAttribute("jobs", jobService.getAll());
            return "timesheet/add-form-employee";

        } else {
            Employee emp = list.stream().max(comparing(Employee::getId)).get();
            emp.getId();
            model.addAttribute("employee", employeeService.getById(emp.getId()));
            model.addAttribute("jobs", jobService.getAll());
            return "timesheet/update-form-employee";
        }
    }

//    @GetMapping("/add")
//    public String create(Employee employee, Model model){
//        return "employee/add-form-employee";
//    }
    @PostMapping
    public String create(Employee employee,
            BindingResult result,
            Model model,
            RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "timesheet/add-form-employee";
        }
        employeeService.create(employee);
        attributes.addFlashAttribute("message", "Create Successed");
        return "redirect:/employee";
    }

    @GetMapping("/edit/{id}")
    public String update(@PathVariable Integer id, Model model) {
        model.addAttribute("employee", employeeService.getById(id));
        return "timesheet/update-form-employee";
    }

    @PutMapping
    public String update(Integer id,
            Employee employee,
            BindingResult result,
            //            RedirectAttributes attributes
            Model model) {
        if (result.hasErrors()) {
            return "timesheet/update-form-employee";
        }
        List<Employee> list = employeeService.getAll();
        Employee emp = list.stream().max(comparing(Employee::getId)).get();
        employeeService.update(emp.getId(), employee);
//        attributes.addFlashAttribute("message", "Create Successed");
        return "redirect:/employee";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        employeeService.delete(id);
        return "redirect:/employee";
    }
}

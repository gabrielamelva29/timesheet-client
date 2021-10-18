/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.controllers;

import com.example.timesheetclient.models.Status;
import com.example.timesheetclient.services.StatusService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author gabri
 */
@Controller
public class TimesheetController {
    
    private StatusService statusService;

    @Autowired
    public TimesheetController(StatusService statusService) {
        this.statusService = statusService;
    }
    
    @GetMapping("/")
    public String timesheet(){
        return "timesheet/Hello";
    }
    
    //EMPLOYEE ADD
    @GetMapping("/status/getAll")
    public @ResponseBody
    List<Status> getall(Model model) {
        model.addAttribute("status", statusService.getAll());
        return statusService.getAll();
    }

    
}

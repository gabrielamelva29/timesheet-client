/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author gabri
 */
@Controller
public class TimesheetController {
    
    @GetMapping("/")
    public String timesheet(){
        return "job/edit-form-job";
    }
    
}

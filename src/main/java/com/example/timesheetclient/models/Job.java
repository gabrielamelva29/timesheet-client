/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.models;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

/**
 *
 * @author gabri
 */
public class Job {
    @Nullable
    private Integer id;
    
    @NotEmpty(message = "please input date")
    private LocalDate date;
    
    @NotEmpty(message = "please input start time")
    private LocalTime startTime;
    
    @NotEmpty(message = "please input end time")
    private LocalTime endTime;
    
    @NotEmpty(message = "please input total hour")
    private String totalHour;
    
    @NotNull(message = "please select a activity")
    private String activity;
    
//    @NotNull(message = "please select a date")
//    private Employee employee;
//    
}

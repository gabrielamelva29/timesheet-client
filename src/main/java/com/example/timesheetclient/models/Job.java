/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.models;

import com.example.timesheetclient.dto.EmployeeDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Date;
import java.sql.Time;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Lenovo-PC
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Job {
    
    private Integer id;
    
    @NotEmpty(message = "Please input your Date")
    private String date;
    
    private String startTime;
    
    private String endTime;
    
    private String totalHour;
    
    @NotEmpty(message = "Please input your Activity")
    @Size(max = 2000, message = "Max character is 2000")
    private String activity;
    
    private EmployeeDto employee;
    
    private Status status;
}

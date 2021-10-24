/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Date;
import java.sql.Time;
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
    
//    @JsonFormat(pattern = "dd MMMM yyyy")
    private String date;
    
//    @JsonFormat(pattern = "HH:mm")
    private String startTime;
    
//    @JsonFormat(pattern = "HH:mm")
    private String endTime;
    
    private String totalHour;
    
    private String activity;
    
    private Employee employee;
    
    private Status status;
}

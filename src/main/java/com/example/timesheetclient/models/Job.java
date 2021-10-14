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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

/**
 *
 * @author gabri
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Job {
   
    private Integer id;
    
//    @JsonFormat(pattern = "dd-MMM-yyyy")
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

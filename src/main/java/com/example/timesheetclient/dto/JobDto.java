/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.dto;

import com.example.timesheetclient.models.Employee;
import com.example.timesheetclient.models.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;
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
public class JobDto {
    
    private Integer id;
    
    @JsonFormat(pattern = "dd MMMM yyyy")
    @NotEmpty(message = "Please input your Date")
    private Date date;
    
//    @JsonFormat(pattern = "HH:mm")
    private String startTime;
    
//    @JsonFormat(pattern = "HH:mm")
    private String endTime;
    
    private String totalHour;
    
    @NotEmpty(message = "Please input your Activity")
    @Size(max = 2000, message = "Max character is 2000")
    private String activity;
    
    private Employee employee;
    
    private Status status;
}

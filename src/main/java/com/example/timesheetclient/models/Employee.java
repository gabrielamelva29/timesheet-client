/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.models;

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
public class Employee {
    
    private Integer id;
    
    @NotEmpty(message = "Please input your Name")
    private String name;
    
    @NotNull(message = "Please input your Mii Id")
    private Integer miiId;
    
    @NotEmpty(message = "Please input your Project Name")
    private String projectName;
    
    @NotEmpty(message = "Please input your Divisi")
    private String divisi;
    
//    private JobHistory jobHistory;
}

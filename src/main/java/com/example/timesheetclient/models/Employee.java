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
    
    @Nullable
    private Integer id;
    
    @NotEmpty(message = "please input name")
    private String name;
    
    @NotEmpty(message = "please input miiId")
    private Integer miiId;
    
    @NotEmpty(message = "please input project Name")
    private String projectName;
    
    @NotEmpty(message = "please input divisi")
    private String divisi;
//    
//    @NotNull(message = "please select a date")
//    private Job date;
    
}

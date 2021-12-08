/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.models;

import java.sql.Date;
import java.time.Month;
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
public class JobHistory {
    
    private Integer id;
    
    private String month;
    
    private Integer year;
    
    private String status;
    
    private Date approveDate;
    
    private Employee employee;
    
    private HR hr;
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
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

    @NotEmpty(message = "Please input your Mii Id")
    private String miiId;

    @NotEmpty(message = "Please input your Project Name")
    private String projectName;

    @NotEmpty(message = "Please input your Divisi")
    private String divisi;

//    private JobHistory jobHistory;
}

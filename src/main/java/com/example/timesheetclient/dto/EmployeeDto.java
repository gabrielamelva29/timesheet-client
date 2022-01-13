/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.dto;

import com.example.timesheetclient.models.Division;
import com.example.timesheetclient.models.Division;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *
 * @author ASUS
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDto {
    private Integer id;

    @NotEmpty(message = "Please input your Name")
    private String name;

    @NotEmpty(message = "Please input your NIK")
    private String nik;

    private Division division;
}
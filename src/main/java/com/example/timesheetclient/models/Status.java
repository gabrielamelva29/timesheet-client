/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author gabri
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Status {
    private String id;
    private String name;
    private Integer count;
}

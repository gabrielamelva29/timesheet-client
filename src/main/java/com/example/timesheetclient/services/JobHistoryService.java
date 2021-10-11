/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.services;

import com.example.timesheetclient.models.JobHistory;
import com.example.timesheetclient.models.ResponseModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Lenovo-PC
 */
@Service
public class JobHistoryService {
    
    private RestTemplate restTemplate;
    
    @Value("${api.baseUrl}/jobHistory")
    private String url;

    public JobHistoryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public List<JobHistory> getAll(){
        ResponseEntity<List<JobHistory>> response =  restTemplate
                .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<JobHistory>>(){});
        return response.getBody();
    }
    
    public ResponseModel<JobHistory> create(){
        return new ResponseModel<>(restTemplate
                .postForObject(url, null, JobHistory.class), "Job Created");
    } 
}

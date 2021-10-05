/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.services;

import com.example.timesheetclient.models.Status;
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
public class StatusService {
    
    private RestTemplate restTemplate;
    
    @Value("${api.baseUrl}/status")
    private String url;

    public StatusService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public List<Status> getAll(){
        ResponseEntity<List<Status>> response =  restTemplate
                .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Status>>(){});
        return response.getBody();
    }
    
     public Status getById(Integer id){
        return restTemplate.getForObject(url+"/"+id, Status.class);
    }
}

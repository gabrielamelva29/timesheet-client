/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.services;

import com.example.timesheetclient.models.Job;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author gabri
 */
@Service
public class JobService {
    private RestTemplate restTemplate;
    
    @Value("${api.baseUrl}/job")
    private String url;

    public JobService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public List<Job> getAll(){
        ResponseEntity<List<Job>> response =  restTemplate
                .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Job>>(){});
        return response.getBody();
    }
    
     public Job getById(Integer id){
        return restTemplate.getForObject(url+"/"+id, Job.class);
    }
    
    public void create(Job job){
        restTemplate
                .postForObject(url, job, Job.class);
    }
    
    public void update(Integer id, Job job){
        restTemplate
                .put(url+"/"+id, job, Job.class);
    }
    
    public void delete(Integer id){
        restTemplate
                .delete(url+"/"+id, Job.class);
    }    
    
}

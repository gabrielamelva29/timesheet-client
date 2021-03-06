/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.services;

import com.example.timesheetclient.dto.JobDto;
import com.example.timesheetclient.models.Job;
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
    
    public List<JobDto> getAll(){
        ResponseEntity<List<JobDto>> response =  restTemplate
                .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<JobDto>>(){});
        return response.getBody();
    }
    
    public List<Job> findByEmployee(Integer id){
        ResponseEntity<List<Job>> response =  restTemplate
                .exchange(url+"/employee/"+id, HttpMethod.GET, null, new ParameterizedTypeReference<List<Job>>(){});
        return response.getBody();
    }
    
    public List<Job> getAllByDate(Integer idHistory, Integer idEmployee){
        ResponseEntity<List<Job>> response =  restTemplate
                .exchange(url+"/getAllByDate?idHistory="+idHistory+"&&idEmployee="+idEmployee, HttpMethod.GET, null, new ParameterizedTypeReference<List<Job>>(){});
        return response.getBody();
    }
    
     public Job getById(Integer id){
        return restTemplate.getForObject(url+"/"+id, Job.class);
    }
    
     public Job getByDate(String date, Integer id){
         return restTemplate.getForObject(url+"/date?date="+date+"&&id="+id, Job.class);
    }
    
     public Integer getByCountDate(Integer idHistory, Integer idEmployee){
         ResponseEntity<Integer> response =  restTemplate
                .exchange(url+"/periode?idHistory="+idHistory+"&&idEmployee="+idEmployee, HttpMethod.GET, null, new ParameterizedTypeReference<Integer>(){});
        return response.getBody();
    }
    
    public ResponseModel<Job> create(Job job, Integer id){
        return new ResponseModel<>(restTemplate
                .postForObject(url+"/"+id, job, Job.class), "Job Created");
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

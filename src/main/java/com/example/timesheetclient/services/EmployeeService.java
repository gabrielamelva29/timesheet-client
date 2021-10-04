/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.services;

import com.example.timesheetclient.models.Employee;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author gabri
 */
@Service
public class EmployeeService {
    private RestTemplate restTemplate;
    
    @Value("${api.baseUrl}/employee")
    private String url;

    @Autowired
    public EmployeeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
     
     public List<Employee> getAll(){         
         ResponseEntity<List<Employee>> response=restTemplate
                 .exchange(url, HttpMethod.GET, null, 
                         new ParameterizedTypeReference<List<Employee>>(){});
         
         return response.getBody();
     }
     
     public Employee getById(Long id){
          return restTemplate
                 .getForObject(url + "/" +id, Employee.class);         
     }
     
     public void create(Employee employee) {
        restTemplate
                .postForObject(url, employee, Employee.class);
    }

    public void update(Long id, Employee employee) {
        restTemplate
                .put(url + "/" + id, employee, Employee.class);
    }

    public void delete(Long id) {
        restTemplate
                .delete(url + "/" + id, Employee.class);
    }

    
}

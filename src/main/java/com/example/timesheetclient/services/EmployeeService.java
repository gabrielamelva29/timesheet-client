/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.services;

import com.example.timesheetclient.models.Employee;
import com.example.timesheetclient.models.ResponseModel;
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
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${api.baseUrl}/employee")
    private String url;
    
    @Autowired
    public EmployeeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public List<Employee> getAll() {
        ResponseEntity<List<Employee>> response =  restTemplate
                .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>(){});
    
        return response.getBody();
    }
    
    public Employee getById(Integer id){
        return restTemplate
                .getForObject(url + "/"+id, Employee.class);
    }
    
    public ResponseModel<Employee> create(Integer id,Employee employee){
        return new ResponseModel<>(restTemplate
                .postForObject(url + "/" + id, employee, Employee.class), "Employee Created");
    }
    
    public void update(Integer id, Employee employee){
        employee.setId(id);
        restTemplate.put(url + "/" + id, employee, Employee.class);
    }

    public void delete(Integer id){
        restTemplate.delete(url + "/" + id, String.class);
    }

    
}

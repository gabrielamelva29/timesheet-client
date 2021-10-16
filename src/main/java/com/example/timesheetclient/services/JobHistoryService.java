/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.services;

import com.example.timesheetclient.models.Job;
import com.example.timesheetclient.models.JobHistory;
import com.example.timesheetclient.models.ListJob;
import com.example.timesheetclient.models.Month;
import com.example.timesheetclient.models.ResponseModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Lenovo-PC
 */
@Service
public class JobHistoryService {

    private RestTemplate restTemplate;

    @Value("${api.baseUrl}/history")
    private String url;

    public JobHistoryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<JobHistory> getAll() {
        ResponseEntity<List<JobHistory>> response = restTemplate
                .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<JobHistory>>() {
                });
        return response.getBody();
    }

    public List<JobHistory> findByHr() {
        ResponseEntity<List<JobHistory>> response = restTemplate
                .exchange(url + "/hr", HttpMethod.GET, null, new ParameterizedTypeReference<List<JobHistory>>() {
                });
        return response.getBody();
    }

    public ResponseModel<JobHistory> create() {
        return new ResponseModel<>(restTemplate
                .postForObject(url, null, JobHistory.class), "Job Created");
    }

    public JobHistory getById(Integer id) {
        return restTemplate.getForObject(url + "/" + id, JobHistory.class);
    }

    public ResponseModel<JobHistory> approved(Integer id) {
        return new ResponseModel<>(restTemplate
                .postForObject(url + "/approved/" + id, null, JobHistory.class), "Approved");
    }

    public ResponseModel<JobHistory> sent(Integer id) {
        return new ResponseModel<>(restTemplate
                .postForObject(url + "/sent/" + id, null, JobHistory.class), "Sent");
    }

    public List<JobHistory> findByYear(String month, Integer year){
        ResponseEntity<List<JobHistory>> response =  restTemplate
                .exchange(url+"/year?month="+month+"&&year="+year, HttpMethod.GET, null, new ParameterizedTypeReference<List<JobHistory>>(){});
        return response.getBody();
    }

    public void excel(Integer id) {
        final RestTemplate restTemplate = new RestTemplate();

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        restTemplate
                .getForObject(url + "/download/excel/" + id, JobHistory.class);
    }

}

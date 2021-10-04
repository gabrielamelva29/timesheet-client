/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *
 * @author gabri
 */
@EnableWebSecurity
@Configuration
public class WebConfig extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity http) throws Exception{
       http
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "image/**").permitAll()
                .antMatchers("/")                
                .permitAll();
    
              
    }
}

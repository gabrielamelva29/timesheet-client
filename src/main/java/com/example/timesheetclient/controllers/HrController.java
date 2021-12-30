/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.controllers;

import com.example.timesheetclient.excel.ExportExcelAll;
import com.example.timesheetclient.models.JobHistory;
import com.example.timesheetclient.services.EmployeeService;
import com.example.timesheetclient.services.JobHistoryService;
import com.example.timesheetclient.services.JobService;
import com.example.timesheetclient.services.StatusService;
import java.io.IOException;
import java.time.YearMonth;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Lenovo-PC
 */
@Controller
public class HrController {

    private JobHistoryService jobHistoryService;
    private JobService jobService;
    private EmployeeService employeeService;
    private StatusService statusService;

    @Autowired
    public HrController(JobHistoryService jobHistoryService, JobService jobService,
            EmployeeService employeeService, StatusService statusService) {
        this.jobHistoryService = jobHistoryService;
        this.jobService = jobService;
        this.employeeService = employeeService;
        this.statusService = statusService;
    }

    @GetMapping("/hr")
    public String index2(Model model, String month, Integer year) {
        model.addAttribute("list", jobHistoryService.findByHr());
        return "timesheet/approvement";
    }

    @GetMapping("/getall")
    public @ResponseBody
    List<JobHistory> index(Model model, String month, Integer year) {
//        model.addAttribute("list", jobHistoryService.findByHr());
        return jobHistoryService.findByHr();
    }

    @PostMapping("/approved/{id}")
    public String approved(@PathVariable Integer id,
            Model model,
            RedirectAttributes attributes) {
        jobHistoryService.approved(id);
        return "redirect:/hr";
    }

    Integer days;
    @PostMapping("/sent/{id}")
    public String sent(@PathVariable Integer id,
            Model model,
            RedirectAttributes attributes) {
        int bulan, countDays;
        String periode;
        String[] monthname = {"", "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        JobHistory history = jobHistoryService.getById(id);
        for (int i = 0; i < 13; i++) {
            if (monthname[i].equals(history.getMonth())) {
                days = i;
            }
        }
        YearMonth yearMonthObject = YearMonth.of(history.getYear(), days);
            int daysInMonth = yearMonthObject.lengthOfMonth();
            periode = history.getMonth() + " " + history.getYear();
            countDays = jobService.getByCountDate(periode);
            System.out.println(countDays);
            if (daysInMonth <= countDays) {
                jobHistoryService.sent(id);
                return "redirect:/history";
            }else{
                return "redirect:/history?sent=false";
            }
    }

    String bulan;
    Integer tahun;
    @GetMapping("/search")
    public String year(JobHistory jobHistory,
            @RequestParam(value = "yearmonth", required = false) String yearmonth,
            Model model,
            RedirectAttributes attributes) {
        if (yearmonth.equals("")) {
            return "redirect:/hr";
        }
        String a[] = yearmonth.split("-");
        String[] monthname = {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        Integer year = Integer.valueOf(a[0]);
        String month = monthname[Integer.valueOf(a[1])];
        List<JobHistory> jobHistoryYear = jobHistoryService.findByYear(month, year);

        model.addAttribute("list", jobHistoryYear);
        model.addAttribute("yearmonth", yearmonth);
        model.addAttribute("month", month);
        model.addAttribute("year", year);
        bulan = month;
        tahun = year;
        return "timesheet/approvement";
    }

    // DOWNLOAD EXCEL
    @GetMapping("/history/download/excel")
    public void exportExcelAll(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=All Employee in " + bulan + " " + tahun + ".xlsx";
        response.setHeader(headerKey, headerValue);
        ExportExcelAll exportExcelAll = new ExportExcelAll(bulan, tahun, jobHistoryService, jobService, employeeService, statusService);
        exportExcelAll.export(response);
    }
}

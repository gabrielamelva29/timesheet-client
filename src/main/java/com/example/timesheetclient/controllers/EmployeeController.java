
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.controllers;

import com.example.timesheetclient.excel.ExportExcelAll;
import com.example.timesheetclient.excel.ExportExcel;
import com.example.timesheetclient.models.Employee;
import com.example.timesheetclient.models.Job;
import com.example.timesheetclient.models.JobHistory;
import com.example.timesheetclient.models.Month;
import com.example.timesheetclient.models.ResponseModel;
import com.example.timesheetclient.models.Status;
import com.example.timesheetclient.services.EmployeeService;
import com.example.timesheetclient.services.JobHistoryService;
import com.example.timesheetclient.services.JobService;
import com.example.timesheetclient.services.StatusService;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import static java.util.Comparator.comparing;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author gabri
 */
@Controller
public class EmployeeController {

    private EmployeeService employeeService;
    private JobService jobService;
    private StatusService statusService;
    private JobHistoryService jobHistoryService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, JobService jobService, StatusService statusService, JobHistoryService jobHistoryService) {
        this.employeeService = employeeService;
        this.jobService = jobService;
        this.statusService = statusService;
        this.jobHistoryService = jobHistoryService;
    }

    JobHistory idemp = null;

    //EMPLOYEE ADD
    @GetMapping("/history/{id}")
    public String update(@PathVariable Integer id, Employee employee, Model model) {
        model.addAttribute("history", jobHistoryService.getById(id));
        JobHistory jobHistory = jobHistoryService.getById(id);
        idemp = jobHistoryService.getById(id);

        if (jobHistory.getEmployee() == null) {
            statusService.counts(jobHistory.getId());
            return "timesheet/add-form-employee";
        }
        model.addAttribute("jobs", jobService.getAllByDate(id,jobHistory.getEmployee().getId()));
        model.addAttribute("employee", employeeService.getById(jobHistory.getEmployee().getId()));
        statusService.counts(jobHistory.getId());
        return "timesheet/update-form-employee";
    }

    @PostMapping("/history/{id}")
    public String create(@PathVariable Integer id,
            @Valid Employee employee,
            BindingResult result,
            Model model,
            RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("history", jobHistoryService.getById(id));
            return "timesheet/add-form-employee";
        }
        employeeService.create(id, employee);
        attributes.addFlashAttribute("message", "Create Successed");
        return "redirect:/history/{id}";
    }

    //EMPLOYEE UPDATE
    @PutMapping("/history/{id}")
    public String update(@PathVariable Integer id,
            @Valid Employee employee,
            BindingResult result,
            Model model,
            RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("history", jobHistoryService.getById(id));
            return "timesheet/update-form-employee";
        }
        JobHistory history = jobHistoryService.getById(id);
        employeeService.update(history.getEmployee().getId(), employee);
        attributes.addFlashAttribute("message", "Create Successed");
        return "redirect:/history/{id}";
    }

    //JOB GET ADD
    @GetMapping("/job/add/{id}")
    public String create(@PathVariable Integer id,
            Job job,
            Model model) {
        model.addAttribute("statuses", statusService.getAll());
        model.addAttribute("employee", employeeService.getById(id));
        model.addAttribute("history", idemp.getId());
        return "timesheet/add-form-activity";
    }

    //JOB GET EDIT
    @GetMapping("/job/edit/{id}/{ids}")
    public String update(@PathVariable Integer id,
            @PathVariable Integer ids,
            JobHistory jobHistory,
            Model model) {
        model.addAttribute("job", jobService.getById(ids));
        model.addAttribute("employee", employeeService.getById(id));
        model.addAttribute("statuses", statusService.getAll());
        model.addAttribute("history", idemp.getId());
        return "timesheet/update-form-activity";
    }

    // DOWNLOAD EXCEL
    @GetMapping("/history/download/excel/{id}")
    public void exportToExcel(HttpServletResponse response, @PathVariable("id") Integer id) throws IOException {
        response.setContentType("application/octet-stream");

        JobHistory jobHistory = jobHistoryService.getById(id);
        List<Job> listJobs = jobService.getAllByDate(jobHistory.getId(),jobHistory.getEmployee().getId());
        Employee employee = employeeService.getById(jobHistory.getEmployee().getId());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Employee " + employee.getName() + " "
                + jobHistory.getMonth() + " " + jobHistory.getYear() + ".xlsx";
        response.setHeader(headerKey, headerValue);

        ExportExcel exportExcel = new ExportExcel(listJobs, jobHistory, employee, statusService, employeeService);

        exportExcel.export(response);
    }
}

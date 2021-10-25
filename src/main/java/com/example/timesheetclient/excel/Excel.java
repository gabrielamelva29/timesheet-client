/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.excel;

import com.example.timesheetclient.models.Job;
import com.example.timesheetclient.models.JobHistory;
import com.example.timesheetclient.services.EmployeeService;
import com.example.timesheetclient.services.JobHistoryService;
import com.example.timesheetclient.services.JobService;
import com.example.timesheetclient.services.StatusService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author gabri
 */
@Controller
public class Excel {
    
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private String bulan;
    private Integer tahun;
    private JobService jobService;
    private EmployeeService employeeService;
    private StatusService statusService;
    private JobHistoryService jobHistoryService;

    @Autowired
    public Excel(JobService jobService, EmployeeService employeeService, StatusService statusService, JobHistoryService jobHistoryService) {
        this.jobService = jobService;
        this.employeeService = employeeService;
        this.statusService = statusService;
        this.jobHistoryService = jobHistoryService;
    }
    
    public Excel(String bulan, Integer tahun, JobHistoryService jobHistoryService, JobService jobService,
            EmployeeService employeeService, StatusService statusService) {
        this.bulan = bulan;
        this.tahun = tahun;
        this.jobHistoryService = jobHistoryService;
        this.jobService = jobService;
        this.employeeService = employeeService;
        this.statusService = statusService;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        List<JobHistory> listJobHistory = jobHistoryService.findByYear(bulan, tahun);
        for (JobHistory jobHistory1 : listJobHistory) {
            sheet = workbook.createSheet(jobHistory1.getEmployee().getName());
            Row row = sheet.createRow(6);
            CellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setBold(true);
            font.setFontHeight(16);
            style.setFont(font);
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);
            style.setAlignment(HorizontalAlignment.LEFT);

            CellStyle style2 = workbook.createCellStyle();
            style2.setFont(font);
            style2.setBorderBottom(BorderStyle.THIN);
            style2.setBorderLeft(BorderStyle.THIN);
            style2.setBorderRight(BorderStyle.THIN);
            style2.setBorderTop(BorderStyle.THIN);
            style2.setAlignment(HorizontalAlignment.CENTER);

            createCell(sheet.createRow(0), 0, "Name of Project", style);
            createCell(sheet.createRow(1), 0, "Unit/Division", style);
            createCell(sheet.createRow(2), 0, "Name", style);
            createCell(sheet.createRow(3), 0, "MII ID", style);
            createCell(sheet.createRow(4), 0, "Periode", style);
            createCell(row, 0, "Date", style2);
            createCell(row, 1, "Start Time", style2);
            createCell(row, 2, "End Time", style2);
            createCell(row, 3, "Total Hour", style2);
            createCell(row, 4, "Present", style2);
            createCell(row, 5, "Sick", style2);
            createCell(row, 6, "Business Trip", style2);
            createCell(row, 7, "Permit", style2);
            createCell(row, 8, "Vacation", style2);
            createCell(row, 9, "Not Working", style2);
            createCell(row, 10, "Activity", style2);

            CellStyle style3 = workbook.createCellStyle();
            XSSFFont font3 = workbook.createFont();
            font3.setFontHeight(14);
            style3.setFont(font3);
            style3.setAlignment(HorizontalAlignment.LEFT);

            createCell(sheet.getRow(0), 1, jobHistory1.getEmployee().getProjectName(), style3);
            createCell(sheet.getRow(1), 1, jobHistory1.getEmployee().getDivisi(), style3);
            createCell(sheet.getRow(2), 1, jobHistory1.getEmployee().getName(), style3);
            createCell(sheet.getRow(3), 1, jobHistory1.getEmployee().getMiiId(), style3);
            createCell(sheet.getRow(4), 1, jobHistory1.getMonth() + " " + jobHistory1.getYear(), style3);

            List<Job> listJobEmployee = jobService.findByEmployee(jobHistory1.getEmployee().getId());
            int rowCount = 7;
            for (Job job : listJobEmployee) {
                Row rows = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(rows, columnCount++, job.getDate().toString(), style3);
            createCell(rows, columnCount++, job.getStartTime() == null ? "-" : job.getStartTime().toString(), style3);
            createCell(rows, columnCount++, job.getEndTime() == null ? "-" : job.getEndTime().toString(), style3);
            createCell(rows, columnCount++, job.getTotalHour() == null ? "-" : job.getTotalHour(), style3);
            if (job.getStatus().getId().equals("P")) {
                createCell(rows, 4, job.getStatus().getName(), style3);
            }
            if (job.getStatus().getId().equals("S")) {
                createCell(rows, 5, job.getStatus().getName(), style3);
            }
            if (job.getStatus().getId().equals("BT")) {
                createCell(rows, 6, job.getStatus().getName(), style3);
            }
            if (job.getStatus().getId().equals("PM")) {
                createCell(rows, 7, job.getStatus().getName(), style3);
            }
            if (job.getStatus().getId().equals("V")) {
                createCell(rows, 8, job.getStatus().getName(), style3);
            }
            if (job.getStatus().getId().equals("X")) {
                createCell(rows, 9, job.getStatus().getName(), style3);
            }
            createCell(rows, 10, job.getActivity(), style3);
            }
        }
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}


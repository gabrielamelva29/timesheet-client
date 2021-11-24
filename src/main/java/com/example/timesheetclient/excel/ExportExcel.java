/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.excel;

import com.example.timesheetclient.models.Employee;
import com.example.timesheetclient.models.Job;
import com.example.timesheetclient.models.JobHistory;
import com.example.timesheetclient.models.Status;
import com.example.timesheetclient.services.EmployeeService;
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

/**
 *
 * @author Lenovo-PC
 */
public class ExportExcel {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Job> listJobs;
    private StatusService statusService;
    private JobHistory jobHistory;
    private Employee employee;
    private EmployeeService employeeService;

    public ExportExcel(List<Job> listJobs, JobHistory jobHistory, Employee employee, StatusService statusService, EmployeeService employeeService) {
        this.listJobs = listJobs;
        this.jobHistory = jobHistory;
        this.employee = employee;
        this.statusService = statusService;
        this.employeeService = employeeService;
    }

    public ExportExcel(List<Job> listJobs) {
        this.listJobs = listJobs;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet(employee.getName());
        Row row = sheet.createRow(6);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);

        CellStyle style2 = workbook.createCellStyle();
        style2.setFont(font);
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN);
        style2.setBorderRight(BorderStyle.THIN);
        style2.setBorderTop(BorderStyle.THIN);

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
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
//        for (Job job : listJobs) {
//            if (job.getActivity().length()>100) {
//                sheet.setColumnWidth(10, job.getActivity().length()+20000);
//                System.out.println(job.getActivity().length());
//            }
//        }
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

    private void writeDataLines() {
        int rowCount = 7;
        
        CellStyle style1 = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style1.setAlignment(HorizontalAlignment.LEFT);
        style1.setFont(font);

        CellStyle style = workbook.createCellStyle();
        font.setFontHeight(14);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setWrapText(true);
        

        //Employee
        createCell(sheet.getRow(0), 1, employee.getProjectName(), style1);
        createCell(sheet.getRow(1), 1, employee.getDivisi(), style1);
        createCell(sheet.getRow(2), 1, employee.getName(), style1);
        createCell(sheet.getRow(3), 1, employee.getMiiId(), style1);
        createCell(sheet.getRow(4), 1, jobHistory.getMonth() + " " + jobHistory.getYear(), style1);

        //List Jobs
        Integer a = null;
        for (Job job : listJobs) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            a = job.getEmployee().getId();
//            int a = job.getActivity().length()+8000;
//            row.setHeight((short)a);

            createCell(row, columnCount++, job.getDate().toString(), style);
            createCell(row, columnCount++, job.getStartTime() == null ? "-" : job.getStartTime().toString(), style);
            createCell(row, columnCount++, job.getEndTime() == null ? "-" : job.getEndTime().toString(), style);
            createCell(row, columnCount++, job.getTotalHour() == null ? "-" : job.getTotalHour(), style);
            if (job.getStatus().getId().equals("P")) {
                createCell(row, 4, "v", style);
                createCell(row, 5, "", style);
                createCell(row, 6, "", style);
                createCell(row, 7, "", style);
                createCell(row, 8, "", style);
                createCell(row, 9, "", style);
            }
            if (job.getStatus().getId().equals("S")) {
                createCell(row, 4, "", style);
                createCell(row, 5, "v", style);
                createCell(row, 6, "", style);
                createCell(row, 7, "", style);
                createCell(row, 8, "", style);
                createCell(row, 9, "", style);
            }
            if (job.getStatus().getId().equals("BT")) {
                createCell(row, 4, "", style);
                createCell(row, 5, "", style);
                createCell(row, 6, "v", style);
                createCell(row, 7, "", style);
                createCell(row, 8, "", style);
                createCell(row, 9, "", style);
            }
            if (job.getStatus().getId().equals("PM")) {
                createCell(row, 4, "", style);
                createCell(row, 5, "", style);
                createCell(row, 6, "", style);
                createCell(row, 7, "v", style);
                createCell(row, 8, "", style);
                createCell(row, 9, "", style);
            }
            if (job.getStatus().getId().equals("V")) {
                createCell(row, 4, "", style);
                createCell(row, 5, "", style);
                createCell(row, 6, "", style);
                createCell(row, 7, "", style);
                createCell(row, 8, "v", style);
                createCell(row, 9, "", style);
            }
            if (job.getStatus().getId().equals("X")) {
                createCell(row, 4, "", style);
                createCell(row, 5, "", style);
                createCell(row, 6, "", style);
                createCell(row, 7, "", style);
                createCell(row, 8, "", style);
                createCell(row, 9, "v", style);
            }
                createCell(row, 10, job.getActivity(), style);
            
        }

        Row rows = sheet.createRow(rowCount);
        int column = 0;
        //List Status
        rowCount++;
        employeeService.counts(a);
        List<Status> statuses = statusService.getAll();
        for (Status status : statuses) {

            createCell(rows, 0, "", style);
            createCell(rows, 1, "", style);
            createCell(rows, 2, "", style);
            createCell(rows, 3, "", style);
            if (status.getId().equals("P")) {
                createCell(rows, 4, status.getCount(), style);
            }
            if (status.getId().equals("S")) {
                createCell(rows, 5, status.getCount(), style);
            }
            if (status.getId().equals("BT")) {
                createCell(rows, 6, status.getCount(), style);
            }
            if (status.getId().equals("PM")) {
                createCell(rows, 7, status.getCount(), style);
            }
            if (status.getId().equals("V")) {
                createCell(rows, 8, status.getCount(), style);
            }
            if (status.getId().equals("X")) {
                createCell(rows, 9, status.getCount(), style);
            }
            createCell(rows, 10, "", style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        workbook = new XSSFWorkbook();
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}

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
import com.example.timesheetclient.services.JobService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
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
    private List<Status> statuses;
    private JobHistory jobHistory;
    private Employee employee;
    private JobService jobService;

    public ExportExcel(List<Job> listJobs, JobHistory jobHistory, Employee employee, List<Status> statuses) {
        this.listJobs = listJobs;
        this.jobHistory = jobHistory;
        this.employee = employee;
        this.statuses = statuses;
        
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

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);

        //Employee
        createCell(sheet.getRow(0), 1, employee.getProjectName(), style);
        createCell(sheet.getRow(1), 1, employee.getDivisi(), style);
        createCell(sheet.getRow(2), 1, employee.getName(), style);
        createCell(sheet.getRow(3), 1, employee.getMiiId(), style);
        createCell(sheet.getRow(4), 1, jobHistory.getMonth() + " " + jobHistory.getYear(), style);

        //List Jobs
        for (Job job : listJobs) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, job.getDate().toString(), style);
            createCell(row, columnCount++, job.getStartTime() == null ? "-" : job.getStartTime().toString(), style);
            createCell(row, columnCount++, job.getEndTime() == null ? "-" : job.getEndTime().toString(), style);
            createCell(row, columnCount++, job.getTotalHour() == null ? "-" : job.getTotalHour(), style);
            if (job.getStatus().getId().equals("P")) {
                createCell(row, 4, job.getStatus().getName(), style);
            }if (job.getStatus().getId().equals("S")) {
                createCell(row, 5, job.getStatus().getName(), style);
            }if (job.getStatus().getId().equals("BT")) {
                createCell(row, 6, job.getStatus().getName(), style);
            }if (job.getStatus().getId().equals("PM")) {
                createCell(row, 7, job.getStatus().getName(), style);
            }if (job.getStatus().getId().equals("V")) {
                createCell(row, 8, job.getStatus().getName(), style);
            }if (job.getStatus().getId().equals("X")) {
                createCell(row, 9, job.getStatus().getName(), style);
            }
            createCell(row, 10, job.getActivity(), style);
        }

        Row rows = sheet.createRow(rowCount);
        int column=0;
        //List Status
        rowCount++;
        for (Status status : statuses) {
            
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

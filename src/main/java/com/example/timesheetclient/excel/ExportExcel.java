/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timesheetclient.excel;

import com.example.timesheetclient.models.Job;
import com.example.timesheetclient.models.JobHistory;
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
    private JobHistory jobHistory;
    private JobService jobService;

    public ExportExcel(List<Job> listJobs, JobHistory jobHistory) {
        this.listJobs = listJobs;
        this.jobHistory = jobHistory;
        workbook = new XSSFWorkbook();
    }

    public ExportExcel(List<Job> listJobs) {
        this.listJobs = listJobs;
        workbook = new XSSFWorkbook();
    }
    
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Timesheet");
         
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
        createCell(row, 4, "Status", style2);
        createCell(row, 5, "Activity", style2);
    }
    
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
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
        
        for (Job job : listJobs) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(sheet.getRow(0), 1, job.getEmployee().getProjectName(), style);
            createCell(sheet.getRow(1), 1, job.getEmployee().getDivisi(), style);
            createCell(sheet.getRow(2), 1, job.getEmployee().getName(), style);
            createCell(sheet.getRow(3), 1, job.getEmployee().getMiiId(), style);
            createCell(sheet.getRow(4), 1, jobHistory.getMonth()+" "+jobHistory.getYear(), style);
            createCell(row, columnCount++, job.getDate().toString(), style);
            createCell(row, columnCount++, job.getStartTime().toString(), style);
            createCell(row, columnCount++, job.getEndTime().toString(), style);
            createCell(row, columnCount++, job.getTotalHour(), style);
            createCell(row, columnCount++, job.getStatus().getName(), style);
            createCell(row, columnCount++, job.getActivity(), style);
            sheet.autoSizeColumn(columnCount);
        }
    }
    
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
         
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
         
    }
}


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
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Lenovo-PC
 */
@Controller
public class ExportExcelAll {

  
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private String bulan;
    private Integer tahun;
    private JobService jobService;
    private EmployeeService employeeService;
    private StatusService statusService;
    private JobHistoryService jobHistoryService;

    @Autowired
    public ExportExcelAll(JobService jobService, EmployeeService employeeService, StatusService statusService, JobHistoryService jobHistoryService) {
        this.jobService = jobService;
        this.employeeService = employeeService;
        this.statusService = statusService;
        this.jobHistoryService = jobHistoryService;
    }

    public ExportExcelAll(String bulan, Integer tahun, JobHistoryService jobHistoryService, JobService jobService,
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
        int rowCountStatus = 7;
        List<JobHistory> listJobHistory = jobHistoryService.findByYear(bulan, tahun);
        for (JobHistory jobHistory1 : listJobHistory) {
            sheet = workbook.createSheet(jobHistory1.getEmployee().getName());
            Row row = sheet.createRow(5);
            row.setHeightInPoints(17.3f);
            Row row2 = sheet.createRow(6);
            row2.setHeightInPoints(17.3f);

            CellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setFontHeight(9);
            style.setFont(font);
            style.setAlignment(HorizontalAlignment.LEFT);
            style.setVerticalAlignment(VerticalAlignment.CENTER);

            CellStyle style0 = workbook.createCellStyle();
            XSSFFont font0 = workbook.createFont();
            font0.setFontName("Verdana");
            font0.setFontHeight(9);
            style0.setFont(font0);
            style0.setVerticalAlignment(VerticalAlignment.CENTER);

            CellStyle styleStatus = workbook.createCellStyle();
            XSSFFont fontStatus = workbook.createFont();
            fontStatus.setFontHeight(11);
            fontStatus.setBold(true);
            styleStatus.setFont(fontStatus);
            styleStatus.setBorderBottom(BorderStyle.THIN);
            styleStatus.setBorderLeft(BorderStyle.THIN);
            styleStatus.setBorderRight(BorderStyle.THIN);
            styleStatus.setBorderTop(BorderStyle.THIN);
            styleStatus.setAlignment(HorizontalAlignment.CENTER);
            styleStatus.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.index);
            styleStatus.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            CellStyle style1 = workbook.createCellStyle();
            XSSFFont font1 = workbook.createFont();
            font1.setFontHeight(9);
            font1.setBold(true);
            style1.setFont(font1);
            style1.setAlignment(HorizontalAlignment.LEFT);
            style1.setVerticalAlignment(VerticalAlignment.CENTER);

            CellStyle style2 = workbook.createCellStyle();
            XSSFFont font2 = workbook.createFont();
            font2.setFontHeight(9);
            font2.setBold(true);
            style2.setFont(font2);
            style2.setAlignment(HorizontalAlignment.CENTER);
            style2.setBorderBottom(BorderStyle.THIN);
            style2.setBorderLeft(BorderStyle.THIN);
            style2.setBorderRight(BorderStyle.THIN);
            style2.setBorderTop(BorderStyle.THIN);
            style2.setAlignment(HorizontalAlignment.CENTER);
            style2.setVerticalAlignment(VerticalAlignment.CENTER);
            style2.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.index);
            style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
            createCell(height(0), 0, "NAME of PROJECT", style);
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));
            createCell(height(1), 0, "UNIT/DIVISION", style);
            sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 1));
            createCell(height(2), 0, "NAME", style);
            sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 1));
            createCell(height(3), 0, "NIK", style);
            sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 1));
            createCell(height(4), 0, "PERIODE", style);

            sheet.addMergedRegion(new CellRangeAddress(5, 6, 0, 0));
            createCell(row, 0, "DATE", style2);
            createCell(row2, 0, "", style2);
            sheet.addMergedRegion(new CellRangeAddress(5, 5, 1, 2));
            createCell(row, 1, "WORKING HOUR", style2);
            createCell(row2, 1, "START", style2);
            createCell(row, 2, "", style2);
            createCell(row2, 2, "END", style2);
            sheet.addMergedRegion(new CellRangeAddress(5, 6, 3, 3));
            createCell(row, 3, "TOTAL HOUR", style2);
            sheet.addMergedRegion(new CellRangeAddress(5, 5, 4, 9));
            createCell(row, 4, "STATUS  ATTENDANCE", styleStatus);
            for (int i = 5; i < 10; i++) {
                createCell(row, i, "", style2);
            }
            createCell(row2, 4, "Present", style2);
            createCell(row2, 5, "Sick ", style2);
            createCell(row2, 6, "Business Trip", style2);
            createCell(row2, 7, "Permit", style2);
            createCell(row2, 8, "Vacation", style2);
            createCell(row2, 9, "Not Working", style2);
            sheet.addMergedRegion(new CellRangeAddress(5, 6, 10, 10));
            createCell(row, 10, "ACTIVITY", style2);
            createCell(row2, 10, "", style2);

            CellStyle style3 = workbook.createCellStyle();
            XSSFFont font3 = workbook.createFont();
            font3.setFontHeight(9);
            font3.setFontName("Calibri Light");
            font3.setBold(true);
            style3.setFont(font3);
            style3.setAlignment(HorizontalAlignment.LEFT);
            style3.setBorderBottom(BorderStyle.THIN);
            style3.setBorderLeft(BorderStyle.THIN);
            style3.setBorderRight(BorderStyle.THIN);
            style3.setBorderTop(BorderStyle.THIN);
            style3.setVerticalAlignment(VerticalAlignment.CENTER);
            style3.setAlignment(HorizontalAlignment.CENTER);
            style3.setWrapText(true);

            //Employee
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 6));
            createCell(sheet.getRow(0), 2, ": " + jobHistory1.getEmployee().getDivision().getProjectName(), style1);
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 4));
            createCell(sheet.getRow(1), 2, ": " + jobHistory1.getEmployee().getDivision().getDivisi(), style1);
            sheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 5));
            createCell(sheet.getRow(2), 2, ": " + jobHistory1.getEmployee().getName(), style1);
            createCell(sheet.getRow(3), 2, ": " + jobHistory1.getEmployee().getNik(), style1);
            sheet.addMergedRegion(new CellRangeAddress(4, 4, 2, 3));
            createCell(sheet.getRow(4), 2, ": " + jobHistory1.getMonth() + " " + jobHistory1.getYear(), style1);

            createCell(sheet.getRow(4), 6, "P = Present; S = Sick;  V = Vacation; BT = Business Trip; PM = Permit; X = Not Working Anymore", style0);

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
                    createCell(rows, 4, job.getStatus().getId(), style3);
                    createCell(rows, 5, "", style3);
                    createCell(rows, 6, "", style3);
                    createCell(rows, 7, "", style3);
                    createCell(rows, 8, "", style3);
                    createCell(rows, 9, "", style3);
                }
                if (job.getStatus().getId().equals("S")) {
                    createCell(rows, 4, "", style3);
                    createCell(rows, 5, job.getStatus().getId(), style3);
                    createCell(rows, 6, "", style3);
                    createCell(rows, 7, "", style3);
                    createCell(rows, 8, "", style3);
                    createCell(rows, 9, "", style3);
                }
                if (job.getStatus().getId().equals("BT")) {
                    createCell(rows, 4, "", style3);
                    createCell(rows, 5, "", style3);
                    createCell(rows, 6, job.getStatus().getId(), style3);
                    createCell(rows, 7, "", style3);
                    createCell(rows, 8, "", style3);
                    createCell(rows, 9, "", style3);
                }
                if (job.getStatus().getId().equals("PM")) {
                    createCell(rows, 4, "", style3);
                    createCell(rows, 5, "", style3);
                    createCell(rows, 6, "", style3);
                    createCell(rows, 7, job.getStatus().getId(), style3);
                    createCell(rows, 8, "", style3);
                    createCell(rows, 9, "", style3);
                }
                if (job.getStatus().getId().equals("V")) {
                    createCell(rows, 4, "", style3);
                    createCell(rows, 5, "", style3);
                    createCell(rows, 6, "", style3);
                    createCell(rows, 7, "", style3);
                    createCell(rows, 8, job.getStatus().getId(), style3);
                    createCell(rows, 9, "", style3);
                }
                if (job.getStatus().getId().equals("X")) {
                    createCell(rows, 4, "", style3);
                    createCell(rows, 5, "", style3);
                    createCell(rows, 6, "", style3);
                    createCell(rows, 7, "", style3);
                    createCell(rows, 8, "", style3);
                    createCell(rows, 9, job.getStatus().getId(), style3);
                }
                createCell(rows, 10, job.getActivity(), style3);
                rowCountStatus = rowCount;
            }

            statusService.counts(jobHistory1.getEmployee().getId());
            List<Status> Statuses = statusService.getAll();
            Row rows = sheet.createRow(rowCountStatus);
            rowCountStatus++;
            for (Status status : Statuses) {

                createCell(rows, 0, "", style3);
                createCell(rows, 1, "", style3);
                createCell(rows, 2, "", style3);
                createCell(rows, 3, "", style3);
                if (status.getId().equals("P")) {
                    createCell(rows, 4, status.getCount(), style3);
                }
                if (status.getId().equals("S")) {
                    createCell(rows, 5, status.getCount(), style3);
                }
                if (status.getId().equals("BT")) {
                    createCell(rows, 6, status.getCount(), style3);
                }
                if (status.getId().equals("PM")) {
                    createCell(rows, 7, status.getCount(), style3);
                }
                if (status.getId().equals("V")) {
                    createCell(rows, 8, status.getCount(), style3);
                }
                if (status.getId().equals("X")) {
                    createCell(rows, 9, status.getCount(), style3);
                }
                createCell(rows, 10, "", style3);
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

    private Row height(int rows) {
        Row row = sheet.createRow(rows);
        row.setHeightInPoints(17.3f);
        return row;
    }
    
    public void resizeColumn() {
        //width
        for (int i = 1; i < 9; i++) {
            sheet.setColumnWidth(i, 11 * 256);
        }
        sheet.setColumnWidth(10, 53 * 256);
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        resizeColumn();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
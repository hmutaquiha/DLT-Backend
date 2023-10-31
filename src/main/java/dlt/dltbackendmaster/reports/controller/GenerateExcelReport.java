package dlt.dltbackendmaster.reports.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

public class GenerateExcelReport {
    public static void main(String[] args) {
        try {
            // Compile the .jrxml template to a .jasper file
            JasperReport jasperReport = JasperCompileManager.compileReport("/home/francisco/git/DLT-Backend/src/main/resources/reports/SimpleReportTemplate.jrxml");

            // Create sample data
            List<ReportData> dataList = new ArrayList<>();
            dataList.add(new ReportData(1, "John"));
            dataList.add(new ReportData(2, "Alice"));
            dataList.add(new ReportData(3, "Bob"));

            // Convert data to a JRBeanCollectionDataSource
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);

            // Generate the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

            // Export the report to XLSX
            JRXlsxExporter exporter = new JRXlsxExporter();

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("target/reports/SampleReportWithBorders.xlsx"));

            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setOnePagePerSheet(true);
            configuration.setDetectCellType(true);
            configuration.setAutoFitPageHeight(true);
            configuration.setIgnoreGraphics(false);

            exporter.setConfiguration(configuration);

            exporter.exportReport();

            System.out.println("Report generated and exported to XLSX with borders successfully.");
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}


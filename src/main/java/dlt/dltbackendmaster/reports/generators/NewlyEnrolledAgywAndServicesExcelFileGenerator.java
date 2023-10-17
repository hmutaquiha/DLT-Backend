package dlt.dltbackendmaster.reports.generators;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import dlt.dltbackendmaster.reports.domain.NewlyEnrolledAgywAndServices;

/**
 * Excel File Exporter
 *  @author Francisco Macuacua
 */
public class NewlyEnrolledAgywAndServicesExcelFileGenerator {
	
	public byte[] exportExcelFile(List<NewlyEnrolledAgywAndServices> newlyEnrolledAgywAndServices, String[] headers) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // Create a new Sheet named "Contacts"
            Sheet sheet = workbook.createSheet("NewlyEnrolledAgywAndServices");

            // Create header row
            createHeaderRow(workbook, sheet, headers);

            // Create rows
            for (int i = 0; i < newlyEnrolledAgywAndServices.size(); i++) {
                // Row index equals i + 1 because the first row of Excel file is the header row.
                int rowIndex = i + 1;
                createNewRow(workbook, sheet, rowIndex, newlyEnrolledAgywAndServices.get(i));
            }

            // Adjusts 3 columns to set the width to fit the contents.
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            sheet.autoSizeColumn(6);
            sheet.autoSizeColumn(7);
            sheet.autoSizeColumn(8);
            sheet.autoSizeColumn(9);
            sheet.autoSizeColumn(10);
            sheet.autoSizeColumn(11);
            sheet.autoSizeColumn(12);
            sheet.autoSizeColumn(13);
            sheet.autoSizeColumn(14);
            sheet.autoSizeColumn(15);
            sheet.autoSizeColumn(16);
            sheet.autoSizeColumn(17);            
            sheet.autoSizeColumn(18);
            sheet.autoSizeColumn(19);
            sheet.autoSizeColumn(20);
            sheet.autoSizeColumn(21);
            sheet.autoSizeColumn(22);
            sheet.autoSizeColumn(23);            
            sheet.autoSizeColumn(24);
            sheet.autoSizeColumn(25);
            sheet.autoSizeColumn(26);
            sheet.autoSizeColumn(27);
            sheet.autoSizeColumn(28);
            sheet.autoSizeColumn(29);
            sheet.autoSizeColumn(30);
            sheet.autoSizeColumn(31);
            sheet.autoSizeColumn(32);
            sheet.autoSizeColumn(33);
            sheet.autoSizeColumn(34);
            sheet.autoSizeColumn(35);
            sheet.autoSizeColumn(36);
            sheet.autoSizeColumn(37);
            sheet.autoSizeColumn(38);          

            // Write to ByteArrayOutputStream
            workbook.write(outputStream);
            
            // Return the Excel data as a byte array
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0]; // Handle errors appropriately
        }
    }

    /**
     * Create header row
     * @param workbook the Workbook object
     * @param sheet the Sheet object
     * @param headers the headers text
     */
    private void createHeaderRow(Workbook workbook, Sheet sheet, String[] headers) {
        Row headerRow = sheet.createRow(0);
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setBorderTop(BorderStyle.THIN);
        headerCellStyle.setTopBorderColor(IndexedColors.BLACK.index);
        headerCellStyle.setBorderRight(BorderStyle.THIN);
        headerCellStyle.setRightBorderColor(IndexedColors.BLACK.index);
        headerCellStyle.setBorderBottom(BorderStyle.THIN);
        headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
        headerCellStyle.setBorderLeft(BorderStyle.THIN);
        headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.index);

        for(int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerCellStyle);
        }
    }

    /**
     * Create a new row
     * @param workbook the Workbook object
     * @param sheet the Sheet object
     * @param rowIndex the index of row to create
     * @param contact the Contact object which represent information to write to row.
     */
    private void createNewRow(Workbook workbook, Sheet sheet, int rowIndex, NewlyEnrolledAgywAndServices  newlyEnrolled) {
        Row row = sheet.createRow(rowIndex);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setTopBorderColor(IndexedColors.BLACK.index);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(IndexedColors.BLACK.index);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.index);

        Cell cell = row.createCell(0);
        cell.setCellValue(newlyEnrolled.getProvincia());

        cell = row.createCell(1);
        cell.setCellValue(newlyEnrolled.getDistrito());

        cell = row.createCell(2);
        cell.setCellValue(newlyEnrolled.getOnde_mora());
               
        cell = row.createCell(3);
        cell.setCellValue(newlyEnrolled.getPonto_entrada());
        
        cell = row.createCell(4);
        cell.setCellValue(newlyEnrolled.getOrganizacao());
        
        cell = row.createCell(5);
        cell.setCellValue(newlyEnrolled.getData_registo());
        
        cell = row.createCell(6);
        cell.setCellValue(newlyEnrolled.getRegistado_por());
        
        cell = row.createCell(7);
        cell.setCellValue(newlyEnrolled.getData_actualizacao());
        
        cell = row.createCell(8);
        cell.setCellValue(newlyEnrolled.getActualizado_por());
        
        cell = row.createCell(9);
        cell.setCellValue(newlyEnrolled.getNui());
        
        cell = row.createCell(10);
        cell.setCellValue(newlyEnrolled.getSexo());
        
        cell = row.createCell(11);
        cell.setCellValue(newlyEnrolled.getIdade_registo());
        
        cell = row.createCell(12);
        cell.setCellValue(newlyEnrolled.getIdade_actual());
        
        cell = row.createCell(13);
        cell.setCellValue(newlyEnrolled.getFaixa_registo());
        
        cell = row.createCell(14);
        cell.setCellValue(newlyEnrolled.getFaixa_actual());
        
        cell = row.createCell(15);
        cell.setCellValue(newlyEnrolled.getData_nascimento());
        
        cell = row.createCell(16);
        cell.setCellValue(newlyEnrolled.getAgyw_prev());
        
        cell = row.createCell(17);
        cell.setCellValue(newlyEnrolled.getCom_quem_mora());
        
        cell = row.createCell(18);
        cell.setCellValue(newlyEnrolled.getSustenta_casa());
        
        cell = row.createCell(19);
        cell.setCellValue(newlyEnrolled.getE_orfa());
        
        cell = row.createCell(20);
        cell.setCellValue(newlyEnrolled.getVai_escola());
        
        cell = row.createCell(21);
        cell.setCellValue(newlyEnrolled.getTem_deficiencia());
        
        cell = row.createCell(22);
        cell.setCellValue(newlyEnrolled.getTipo_deficiencia());
        
        cell = row.createCell(23);
        cell.setCellValue(newlyEnrolled.getFoi_casada());
        
        cell = row.createCell(24);
        cell.setCellValue(newlyEnrolled.getEsteve_gravida());
        
        cell = row.createCell(25);
        cell.setCellValue(newlyEnrolled.getTem_filhos());
        
        cell = row.createCell(26);
        cell.setCellValue(newlyEnrolled.getGravida_amamentar());
        
        cell = row.createCell(27);
        cell.setCellValue(newlyEnrolled.getTrabalha());
        
        cell = row.createCell(28);        
        cell.setCellValue(newlyEnrolled.getTeste_hiv());
        
        cell = row.createCell(29);
        cell.setCellValue(newlyEnrolled.getArea_servico());
        
        cell = row.createCell(30); 
        cell.setCellValue(newlyEnrolled.getA_servico());
        
        cell = row.createCell(31);
        cell.setCellValue(newlyEnrolled.getSub_servico());
        
        cell = row.createCell(32);
        cell.setCellValue(newlyEnrolled.getPacote_servico());
        
        cell = row.createCell(33);
        cell.setCellValue(newlyEnrolled.getPonto_entrada_servico());
        
        cell = row.createCell(34);
        cell.setCellValue(newlyEnrolled.getLocalizacao());
        
        cell = row.createCell(35);
        cell.setCellValue(newlyEnrolled.getData_servico());
        
        cell = row.createCell(36);
        cell.setCellValue(newlyEnrolled.getProvedor());
        
        cell = row.createCell(37);
        cell.setCellValue(newlyEnrolled.getObservacoes());
        
        cell = row.createCell(38);
        cell.setCellValue(newlyEnrolled.getServico_status());
        
        
        cell.setCellStyle(cellStyle);
    }

}
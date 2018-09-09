package com.hades.poi;


import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class TestExportExcel {

	public static void main(String[] args) throws IOException {
		
		SXSSFWorkbook wb = new SXSSFWorkbook();
		CreationHelper createHelper = wb.getCreationHelper();
		CellStyle hlink_style = wb.createCellStyle();
		Font hlink_font = wb.createFont();
		hlink_font.setUnderline(Font.U_SINGLE);
		hlink_font.setColor(IndexedColors.BLUE.getIndex());
		hlink_style.setFont(hlink_font);
		
		Sheet sheet = wb.createSheet("Hyperlinks");  
		
		Sheet sheet2 = wb.createSheet("Target Sheet");
		sheet2.createRow(0).createCell((short)0).setCellValue("Target Cell");  
		
		Cell cell = sheet.createRow(0).createCell((short)0);  
	    cell.setCellValue("Worksheet Link");  
	    Hyperlink hyperlink = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
	    hyperlink.setAddress("'Target Sheet'!A1");
	    cell.setCellStyle(hlink_style);
	    cell.setHyperlink(hyperlink);
	    
	    Cell cell1 = sheet.getRow(0).createCell(1);
	    cell1.setCellValue(1);
	    Hyperlink hyperlink1 = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
	    hyperlink1.setAddress("'Target Sheet'!A1");
	    cell1.setCellStyle(hlink_style);
	    cell1.setHyperlink(hyperlink1);
	    
	    Cell cell3 = sheet.createRow(1).createCell(0);
	    cell3.setCellValue("URL Link");
	    Hyperlink link2 = createHelper.createHyperlink(HyperlinkType.URL);
	    link2.setAddress("http://poi.apache.org/");
	    cell3.setCellStyle(hlink_style);
	    cell3.setHyperlink(link2);
	    
	    Cell cell4 = sheet.createRow(2).createCell(0);
	    cell4.setCellValue("File Link");
	    Hyperlink link3 = createHelper.createHyperlink(HyperlinkType.FILE);
	    link3.setAddress("link1.xls");
	    cell4.setCellStyle(hlink_style);
	    cell4.setHyperlink(link3);
	    
	    Cell cell5 = sheet.createRow(3).createCell(0);
	    cell5.setCellValue("Email Link");
	    Hyperlink link4 = createHelper.createHyperlink(HyperlinkType.EMAIL);
	    link4.setAddress("mailto:poi@apache.org?subject=Hyperlinks");
	    cell5.setCellStyle(hlink_style);
	    cell5.setHyperlink(link4);
	    
	    
	    FileOutputStream out = new FileOutputStream("D:\\hyperinks.xlsx");
	    wb.write(out);
	    out.close();
	}
}

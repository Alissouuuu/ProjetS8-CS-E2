package fr.esigelec.controllers;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import fr.esigelec.dao.ClassementCommuneDAO;
import fr.esigelec.dao.ClassementRegionDAO;
import fr.esigelec.models.ClassementCommune;
import fr.esigelec.models.ClassementRegion;

/**
 * Servlet implementation class ExportTableauExcel
 */
@WebServlet("/ExportTableauExcel")
public class ExportTableauExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClassementRegionDAO classementRegionDAO;
	private ClassementCommuneDAO classementCommuneDAO;
	@Resource(name="jdbc/club_sport")
	private DataSource dataSource;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExportTableauExcel() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		
		String choix = (String) StringEscapeUtils.escapeHtml4(request.getParameter("choix"));
		String enteteZone = null;
		if(choix.equals("commune")) {
			response.setHeader("Content-Disposition", "attachment; filename=\"classement_commune.xlsx\"");
			enteteZone = "Commune";
		}
			
		else if(choix.equals("region")) {
			response.setHeader("Content-Disposition", "attachment; filename=\"classement_region.xlsx\"");
			enteteZone = "Région";
		}
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFCellStyle styleHeader = workbook.createCellStyle();
		XSSFFont fontHeader = workbook.createFont();
		fontHeader.setFontName("Arial");
		fontHeader.setFontHeightInPoints((short)14);
		fontHeader.setBold(true);
		fontHeader.setColor(IndexedColors.DARK_BLUE.getIndex());
		styleHeader.setFont(fontHeader);
		styleHeader.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		styleHeader = borduresNoires(styleHeader);
		
		styleHeader.setAlignment(HorizontalAlignment.CENTER);
		styleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
		
		Sheet sheet = workbook.createSheet("Données");
		
		XSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style = borduresNoires(style);
		
		int rowNum = 1,colNum=1;
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellStyle(styleHeader);
		cell.setCellValue(enteteZone);
		cell = row.createCell(1);
		cell.setCellStyle(styleHeader);
		cell.setCellValue("Licenciés");
		cell = row.createCell(2);
		cell.setCellStyle(styleHeader);
		cell.setCellValue("Rang");
		if(choix.equals("region")) {
			classementRegionDAO = new ClassementRegionDAO(dataSource);
			ArrayList<ClassementRegion> classementRegion = classementRegionDAO.getClassementAll();
			for(int i=0;i<classementRegion.size();i++) {
				row = sheet.createRow(i+1);
				cell = row.createCell(0);
				cell.setCellStyle(style);
				cell.setCellValue(classementRegion.get(i).getRegion().getNom());
				cell = row.createCell(1);
				cell.setCellStyle(style);
				cell.setCellValue(classementRegion.get(i).getLicences());
				cell = row.createCell(2);
				cell.setCellStyle(style);
				cell.setCellValue(i+1);
			}
		}
		else if(choix.equals("commune")) {
			classementCommuneDAO = new ClassementCommuneDAO(dataSource);
			ArrayList<ClassementCommune> classementCommune = classementCommuneDAO.getClassementAll();
			for(int i=0;i<classementCommune.size();i++) {
				row = sheet.createRow(i+1);
				cell = row.createCell(0);
				cell.setCellStyle(style);
				cell.setCellValue(classementCommune.get(i).getCommune().getNom());
				cell = row.createCell(1);
				cell.setCellStyle(style);
				cell.setCellValue(classementCommune.get(i).getLicences());
				cell = row.createCell(2);
				cell.setCellStyle(style);
				cell.setCellValue(i+1);
			}
		}
		
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		
		
		workbook.write(response.getOutputStream());
		workbook.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private static XSSFCellStyle borduresNoires(XSSFCellStyle style) {
	    style.setBorderTop(BorderStyle.THIN);
	    style.setBorderBottom(BorderStyle.THIN);
	    style.setBorderLeft(BorderStyle.THIN);
	    style.setBorderRight(BorderStyle.THIN);
	    
	    short black = IndexedColors.BLACK.getIndex();
	    style.setTopBorderColor(black);
	    style.setBottomBorderColor(black);
	    style.setLeftBorderColor(black);
	    style.setRightBorderColor(black);
	    return style;
	}


}

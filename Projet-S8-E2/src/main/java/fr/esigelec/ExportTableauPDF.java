package fr.esigelec;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.Color;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Row;
import fr.esigelec.dao.ClassementCommuneDAO;
import fr.esigelec.dao.ClassementRegionDAO;
import fr.esigelec.models.ClassementCommune;
import fr.esigelec.models.ClassementRegion;

/**
 * Servlet implementation class ExportTableauPDF
 */
@WebServlet("/ExportTableauPDF")
public class ExportTableauPDF extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClassementRegionDAO classementRegionDAO;
	private ClassementCommuneDAO classementCommuneDAO;
	@Resource(name="jdbc/club_sport")
	private DataSource dataSource;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExportTableauPDF() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"classement_communes.pdf\"");
		
		try(PDDocument doc = new PDDocument()){
			PDPage page = new PDPage(PDRectangle.A4);
			doc.addPage(page);
			PDPageContentStream contentStream = new PDPageContentStream(doc,page);
			
			
			float margin = 50;
			float pageHeight = page.getMediaBox().getHeight();
			float yStart = pageHeight - margin ;
			float yStartNewPage = yStart;
			float bottomMargin = 50;
			float pageWidth = page.getMediaBox().getWidth();
			float tableWidth = pageWidth - (2*margin);
			float rowHeight = 20;
			Row<PDPage> row;
			
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
			contentStream.beginText();
			contentStream.newLineAtOffset(margin, yStart);
			String choix = (String) StringEscapeUtils.escapeHtml4(request.getParameter("choix"));
			if(choix.equals("commune"))
				contentStream.showText("Classement des communes en fonction des licenciés");
			else if(choix.equals("region"))
				contentStream.showText("Classement des régions en foncton des licenciés");
			contentStream.endText();
			
			yStart-=20;
			
			contentStream.setFont(PDType1Font.HELVETICA, 12);
			contentStream.beginText();
			contentStream.newLineAtOffset(margin, yStart);
			contentStream.showText("Auteur : Equipe 2");
			contentStream.endText();
			
			yStart -=20;
			
			contentStream.beginText();
			contentStream.newLineAtOffset(margin, yStart);
			contentStream.showText("Date de génération : "+LocalDate.now());
			contentStream.endText();
			
			yStart -=20;
			
			contentStream.setStrokingColor(Color.RED);
			contentStream.moveTo(margin, yStart);
			contentStream.lineTo(pageWidth - margin, yStart);
			contentStream.stroke();
			
			contentStream.close();
			yStart -=20;
			
			yStartNewPage = yStart;
			
			BaseTable table = new BaseTable(yStartNewPage,yStart,bottomMargin,tableWidth,margin,doc,page,true,true);
			Row<PDPage> header = table.createRow(rowHeight);
			if(choix.equals("commune")) {
				header.createCell(70,"Commune").setFont(PDType1Font.HELVETICA_BOLD);
				header.createCell(15,"Licenciés").setFont(PDType1Font.HELVETICA_BOLD);
				header.createCell(15,"Rang").setFont(PDType1Font.HELVETICA_BOLD);
				
				classementCommuneDAO = new ClassementCommuneDAO(dataSource);
				ArrayList<ClassementCommune> classementCommune = classementCommuneDAO.getClassement(1);
				for(int i=0;i<classementCommune.size();i++) {
					row = table.createRow(rowHeight);
					row.createCell(70,classementCommune.get(i).getCommune().getNom());
					row.createCell(15,String.valueOf(classementCommune.get(i).getLicences()));
					row.createCell(15,String.valueOf(i+1));					
				}

			}
			else if (choix.equals("region")) {
				header.createCell(70,"Région").setFont(PDType1Font.HELVETICA_BOLD);
				header.createCell(15,"Licenciés");
				header.createCell(15,"Rang");

				classementRegionDAO = new ClassementRegionDAO(dataSource);
				ArrayList<ClassementRegion> classementRegion = classementRegionDAO.getClassement(1);

			}
			table.draw();
			doc.save(response.getOutputStream());
			doc.close();
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

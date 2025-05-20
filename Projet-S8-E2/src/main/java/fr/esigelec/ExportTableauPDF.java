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
import be.quodlibet.boxable.Cell;
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
		
		
		try(PDDocument doc = new PDDocument()){
			PDPage page = new PDPage(PDRectangle.A4);
			doc.addPage(page);
			//PDPageContentStream contentStream = new PDPageContentStream(doc,page);
			
			
			float margin = 50;
			float pageHeight = page.getMediaBox().getHeight();
			float yStart = pageHeight - margin -90;
			float yStartNewPage = yStart;
			float bottomMargin = 50;
			float pageWidth = page.getMediaBox().getWidth();
			float tableWidth = pageWidth - (2*margin);
			float rowHeight = 20;
			Row<PDPage> row;
			
			/*contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
			contentStream.beginText();
			contentStream.newLineAtOffset(margin, yStart);*/
			String choix = (String) StringEscapeUtils.escapeHtml4(request.getParameter("choix"));
			String titrePage = null;
			if(choix.equals("commune")) {
				response.setHeader("Content-Disposition", "attachment; filename=\"classement_communes.pdf\"");
				titrePage = "Classement des communes en fonction des licenciés";
			}
				
			else if(choix.equals("region")) {
				response.setHeader("Content-Disposition", "attachment; filename=\"classement_regions.pdf\"");
				titrePage = "Classement des régions en fonction des licenciés";
			}
				
			
			/*contentStream.showText(titrePage);
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
			yStart -=20;*/
			
			yStartNewPage = yStart;
			
			BaseTable table = new BaseTable(yStartNewPage,yStart,bottomMargin,tableWidth,margin,doc,page,true,true);
			Row<PDPage> header = table.createRow(rowHeight+5);
			Cell<PDPage> cellNom,cellLicences,cellRang;
			
			if(choix.equals("commune")) {
				
				cellNom = header.createCell(70,"Commune");
				cellNom.setFont(PDType1Font.HELVETICA_BOLD);
				cellNom.setFontSize(11);
				cellNom.setFillColor(Color.LIGHT_GRAY);
				
				cellLicences = header.createCell(15,"Licenciés");
				cellLicences.setFont(PDType1Font.HELVETICA_BOLD);
				cellLicences.setFontSize(11);
				cellLicences.setFillColor(Color.LIGHT_GRAY);
				
				cellRang = header.createCell(15,"Rang");
				cellRang.setFont(PDType1Font.HELVETICA_BOLD);
				cellRang.setFontSize(11);
				cellRang.setFillColor(Color.LIGHT_GRAY);
				
				classementCommuneDAO = new ClassementCommuneDAO(dataSource);
				ArrayList<ClassementCommune> classementCommune = classementCommuneDAO.getClassementAll();
				for(int i=0;i<classementCommune.size();i++) {
					row = table.createRow(rowHeight);
					row.createCell(70,classementCommune.get(i).getCommune().getNom());
					row.createCell(15,String.valueOf(classementCommune.get(i).getLicences()));
					row.createCell(15,String.valueOf(i+1));					
				}

			}
			else if (choix.equals("region")) {		
				cellNom = header.createCell(70,"Région");
				cellNom.setFont(PDType1Font.HELVETICA_BOLD);
				cellNom.setFontSize(11);
				cellNom.setFillColor(Color.LIGHT_GRAY);
				
				cellLicences = header.createCell(15,"Licenciés");
				cellLicences.setFont(PDType1Font.HELVETICA_BOLD);
				cellLicences.setFontSize(11);
				cellLicences.setFillColor(Color.LIGHT_GRAY);
				
				cellRang = header.createCell(15,"Rang");
				cellRang.setFont(PDType1Font.HELVETICA_BOLD);
				cellRang.setFontSize(11);
				cellRang.setFillColor(Color.LIGHT_GRAY);
				
				classementRegionDAO = new ClassementRegionDAO(dataSource);
				ArrayList<ClassementRegion> classementRegion = classementRegionDAO.getClassementAll();
				for(int i=0;i<classementRegion.size();i++) {
					row = table.createRow(rowHeight);
					row.createCell(70,classementRegion.get(i).getRegion().getNom());
					row.createCell(15,String.valueOf(classementRegion.get(i).getLicences()));
					row.createCell(15,String.valueOf(i+1));					
				}


			}
			table.draw();
			yStart = pageHeight - margin ;
			int cpt=0;
			int max = doc.getNumberOfPages();
			String maxString =String.valueOf(max);
			for(PDPage chaquePage : doc.getPages()) {
				cpt++;
				PDPageContentStream stream = new PDPageContentStream(doc,chaquePage,PDPageContentStream.AppendMode.APPEND,true);
				
				stream.setFont(PDType1Font.HELVETICA_BOLD, 18);
				stream.beginText();
				stream.newLineAtOffset(margin, yStart);
				stream.showText(titrePage);
				stream.endText();
				
				stream.setFont(PDType1Font.HELVETICA, 12);
				stream.beginText();
				stream.newLineAtOffset(margin, yStart-20);
				stream.showText("Auteur : Equipe 2");
				stream.endText();
				
				stream.beginText();
				stream.newLineAtOffset(margin, yStart-40);
				stream.showText("Date de génération : "+LocalDate.now());
				stream.endText();
				
				
				stream.setStrokingColor(Color.RED);
				stream.moveTo(margin, yStart-60);
				stream.lineTo(pageWidth - margin, yStart-60);
				stream.stroke();
				
				stream.setStrokingColor(Color.RED);
				stream.moveTo(margin, bottomMargin);
				stream.lineTo(pageWidth - margin, bottomMargin);
				stream.stroke();
				
				stream.beginText();
				stream.newLineAtOffset(tableWidth - margin, bottomMargin-20);
				stream.showText(String.valueOf(cpt)+"/"+maxString);
				stream.endText();
				
				stream.close();
			}
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

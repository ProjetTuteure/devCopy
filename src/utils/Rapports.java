package utils;

import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Rapports {

	  private static Font headFont = new Font(Font.FontFamily.TIMES_ROMAN, 40,
	      Font.BOLD);
	  private static Font tableFont = new Font(Font.FontFamily.TIMES_ROMAN, 8,
	      Font.NORMAL);
	  private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
	      Font.BOLD);

	  public void GenerateMaterielParc(){
	    try {
	      String FILE = Constante.CHEMIN_RAPPORTS+"MaterielParc.pdf";
	      Document document = new Document();
	      PdfWriter.getInstance(document, new FileOutputStream(FILE));
	      document.open();
	      
	      document.addTitle("Rapport");
		  document.addAuthor("ADAM SAS");
		  document.addCreator("ADAM SAS");
		  
		  Paragraph preface = new Paragraph();

		  Paragraph titre=new Paragraph("Au rapport", headFont);
		  titre.setAlignment(Element.ALIGN_CENTER);
		  preface.add(titre);

		  addEmptyLine(preface, 1);

		  Paragraph description=new Paragraph("Liste des biens du parc informatique de ADAM SAS",smallBold);
		  description.setAlignment(Element.ALIGN_CENTER);
		  preface.add(description);

		  addEmptyLine(preface, 3);

		  document.add(preface);
		  
		  Paragraph paragraph=new Paragraph("",tableFont);

		  // add a table
		  createTableMaterielParc(paragraph);

		  document.add(paragraph);
		  
	      document.close();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }


	  private static void createTableMaterielParc(Paragraph paragraph) throws DocumentException {
	    PdfPTable table = new PdfPTable(7);

	    table.setWidths(new int[]{12,10,10,5,13,10,8});
	    table.setHorizontalAlignment(Element.ALIGN_CENTER);
	    
	    PdfPCell c1 = new PdfPCell(new Phrase("N° Série"));
	    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    table.addCell(c1);
	    
	    c1 = new PdfPCell(new Phrase("Nom"));
	    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    table.addCell(c1);

	    c1 = new PdfPCell(new Phrase("N° Immo"));
	    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    table.addCell(c1);

	    c1 = new PdfPCell(new Phrase("Type"));
	    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    table.addCell(c1);
	    
	    c1 = new PdfPCell(new Phrase("Etat"));
	    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    table.addCell(c1);
	    
	    c1 = new PdfPCell(new Phrase("Fin garantie"));
	    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    table.addCell(c1);
	    
	    c1 = new PdfPCell(new Phrase("Site"));
	    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    table.addCell(c1);
	    
	    table.setHeaderRows(1);
	    
	    PdfPCell cell=new PdfPCell();
  	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    for(int i=0;i<50;i++){
	    	cell.setPhrase(new Phrase("PC-Martine"));
	    	table.addCell(cell);
	    	
	    	cell.setPhrase(new Phrase("123soleil"));
	    	table.addCell(cell);
	    	
	    	cell.setPhrase(new Phrase("1IMMO"));
	    	table.addCell(cell);
	
	    	cell.setPhrase(new Phrase("PC"));
	    	table.addCell(cell);
	    	
	    	cell.setPhrase(new Phrase("EN_MARCHE"));
	    	table.addCell(cell);
	    	
	    	cell.setPhrase(new Phrase("12/05/2015"));
	    	table.addCell(cell);
	    	
	    	cell.setPhrase(new Phrase("Brive"));
	    	table.addCell(cell);
	    }
	    paragraph.add(table);

	  }
	  
	  private static void addEmptyLine(Paragraph paragraph, int number) {
	    for (int i = 0; i < number; i++) {
	      paragraph.add(new Paragraph(" "));
	    }
	  } 
}

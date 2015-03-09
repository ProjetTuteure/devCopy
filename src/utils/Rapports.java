package utils;

import gpi.exception.ConnexionBDException;
import gpi.metier.RapportsDAO;

import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Rapports {

	  private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 40,
	      Font.BOLD);
	  private static Font headFont = new Font(Font.FontFamily.TIMES_ROMAN, 25,
		      Font.BOLD);
	  private static Font cellFont = new Font(Font.FontFamily.TIMES_ROMAN, 8,
	      Font.NORMAL);
	  private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
	      Font.BOLD);

	  
	  
	  public void GenerateEtat(){
		  try {
		      String FILE = Constante.CHEMIN_RAPPORTS+"Etat.pdf";
		      Document document = new Document();
		      PdfWriter.getInstance(document, new FileOutputStream(FILE));
		      document.open();
		      
		      document.addTitle("Rapport");
			  document.addAuthor("ADAM SAS");
			  document.addCreator("ADAM SAS");
			  
			  Image image=Image.getInstance("src/sources/images/ADAM_RAPPORT.png");
			  document.add(image);
			  
			  Paragraph preface = new Paragraph();

			  Paragraph titre=new Paragraph("Materiel par état", titleFont);
			  titre.setAlignment(Element.ALIGN_CENTER);
			  preface.add(titre);

			  addEmptyLine(preface, 1);

			  Paragraph description=new Paragraph("Liste des biens du parc informatique de ADAM SAS selon leur état",smallBold);
			  description.setAlignment(Element.ALIGN_CENTER);
			  preface.add(description);

			  addEmptyLine(preface, 3);

			  document.add(preface);
			  

			  Paragraph paragraph=new Paragraph("En fonctionnement",headFont);
			  addEmptyLine(paragraph, 2);
			  // add a table
			  createTableFinGarantie(paragraph,1);
			  document.add(paragraph);
			  document.newPage();
			  
			  Paragraph paragraph2=new Paragraph("En panne",headFont);
			  addEmptyLine(paragraph2, 2);
			  // add a table
			  createTableFinGarantie(paragraph2,2);
			  document.add(paragraph2);
			  document.newPage();
			  
			  Paragraph paragraph3=new Paragraph("Hors service",headFont);
			  addEmptyLine(paragraph3, 2);
			  // add a table
			  createTableFinGarantie(paragraph3,3);
			  document.add(paragraph3);
			  
		      document.close();
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		  }
	  
	  private static void createTableEtat(Paragraph paragraph,int chapter) throws DocumentException {
		    PdfPTable table = new PdfPTable(7);

		    table.setWidths(new int[]{9,9,8,12,12,8,10});
		    table.setHorizontalAlignment(Element.ALIGN_CENTER);
		    
		    PdfPCell c1 = new PdfPCell(new Phrase("Nom"));
		    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    table.addCell(c1);

		    c1 = new PdfPCell(new Phrase("N° Immo"));
		    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    table.addCell(c1);

		    c1 = new PdfPCell(new Phrase("Type"));
		    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    table.addCell(c1);
		    
		    c1 = new PdfPCell(new Phrase("Dans l'etat depuis le"));
		    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    table.addCell(c1);
		    
		    c1 = new PdfPCell(new Phrase("Fin garantie"));
		    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    table.addCell(c1);
		    
		    c1 = new PdfPCell(new Phrase("Site"));
		    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    table.addCell(c1);
		    
		    c1 = new PdfPCell(new Phrase("Dernière maintenance"));
		    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    table.addCell(c1);
		    
		    table.setHeaderRows(1);
		    
		    RapportsDAO rapportsDAO = new RapportsDAO();
		    String[][] rapport=null;
		    try {
				rapport=rapportsDAO.getRapportEtat(chapter);
			} catch (ConnexionBDException e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			}
		    
		    
		    
		    PdfPCell cell=new PdfPCell();
		    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		    boolean pair;
		    BaseColor color;
		    for(int i=0;i<rapport.length;i++){
		    	pair=(i%2==0)?true:false;
		    	color=pair?BaseColor.LIGHT_GRAY:BaseColor.WHITE;
		    	
		    	cell.setPhrase(new Phrase(rapport[i][0],cellFont));
		    	cell.setBackgroundColor(color);
		    	table.addCell(cell);
		    	
		    	cell.setPhrase(new Phrase(rapport[i][1],cellFont));
		    	table.addCell(cell);
		    	
		    	cell.setPhrase(new Phrase(rapport[i][2],cellFont));
		    	table.addCell(cell);
		
		    	cell.setPhrase(new Phrase(rapport[i][3],cellFont));
		    	table.addCell(cell);
		    	
		    	cell.setPhrase(new Phrase(rapport[i][4],cellFont));
		    	table.addCell(cell);
		    	
		    	cell.setPhrase(new Phrase(rapport[i][5],cellFont));
		    	table.addCell(cell);
		    	
		    	cell.setPhrase(new Phrase(rapport[i][6],cellFont));
		    	table.addCell(cell);

		    }
		    paragraph.add(table);
		  }
	  
	  
	  
	  
	  
	  public void GenerateFinGarantie(){
		  try {
		      String FILE = Constante.CHEMIN_RAPPORTS+"FinGarantie.pdf";
		      Document document = new Document();
		      PdfWriter.getInstance(document, new FileOutputStream(FILE));
		      document.open();
		      
		      document.addTitle("Rapport");
			  document.addAuthor("ADAM SAS");
			  document.addCreator("ADAM SAS");
			  
			  Image image=Image.getInstance("src/sources/images/ADAM_RAPPORT.png");
			  document.add(image);
			  
			  Paragraph preface = new Paragraph();

			  Paragraph titre=new Paragraph("Materiel en fin de garantie", titleFont);
			  titre.setAlignment(Element.ALIGN_CENTER);
			  preface.add(titre);

			  addEmptyLine(preface, 1);

			  Paragraph description=new Paragraph("Liste des biens du parc informatique de ADAM SAS selon la fin de leur garantie",smallBold);
			  description.setAlignment(Element.ALIGN_CENTER);
			  preface.add(description);

			  addEmptyLine(preface, 3);

			  document.add(preface);
			  

			  Paragraph paragraph=new Paragraph("Expiration dans moins d'un mois",headFont);
			  addEmptyLine(paragraph, 2);
			  // add a table
			  createTableFinGarantie(paragraph,1);
			  document.add(paragraph);
			  document.newPage();
			  
			  Paragraph paragraph2=new Paragraph("Expiration entre un et deux mois",headFont);
			  addEmptyLine(paragraph2, 2);
			  // add a table
			  createTableFinGarantie(paragraph2,2);
			  document.add(paragraph2);
			  document.newPage();
			  
			  Paragraph paragraph3=new Paragraph("Expiration entre deux et trois mois",headFont);
			  addEmptyLine(paragraph3, 2);
			  // add a table
			  createTableFinGarantie(paragraph3,3);
			  document.add(paragraph3);
			  
		      document.close();
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		  }
	  
	  private static void createTableFinGarantie(Paragraph paragraph,int chapter) throws DocumentException {
		    PdfPTable table = new PdfPTable(8);

		    table.setWidths(new int[]{9,9,8,12,12,8,10,8});
		    table.setHorizontalAlignment(Element.ALIGN_CENTER);
		    
		    PdfPCell c1 = new PdfPCell(new Phrase("Nom"));
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
		    
		    c1 = new PdfPCell(new Phrase("Revendeur"));
		    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    table.addCell(c1);
		    
		    c1 = new PdfPCell(new Phrase("Date d'achat"));
		    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    table.addCell(c1);
		    
		    c1 = new PdfPCell(new Phrase("Fin garantie"));
		    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    table.addCell(c1);
		    
		    c1 = new PdfPCell(new Phrase("Site"));
		    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    table.addCell(c1);
		    
		    table.setHeaderRows(1);
		    
		    RapportsDAO rapportsDAO = new RapportsDAO();
		    String[][] rapport=null;
		    try {
				rapport=rapportsDAO.getRapportFinGarantie(chapter);
			} catch (ConnexionBDException e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			}
		    
		    
		    
		    PdfPCell cell=new PdfPCell();
		    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		    boolean pair;
		    BaseColor color;
		    for(int i=0;i<rapport.length;i++){
		    	pair=(i%2==0)?true:false;
		    	color=pair?BaseColor.LIGHT_GRAY:BaseColor.WHITE;
		    	
		    	cell.setPhrase(new Phrase(rapport[i][0],cellFont));
		    	cell.setBackgroundColor(color);
		    	table.addCell(cell);
		    	
		    	cell.setPhrase(new Phrase(rapport[i][1],cellFont));
		    	table.addCell(cell);
		    	
		    	cell.setPhrase(new Phrase(rapport[i][2],cellFont));
		    	table.addCell(cell);
		
		    	cell.setPhrase(new Phrase(rapport[i][3],cellFont));
		    	table.addCell(cell);
		    	
		    	cell.setPhrase(new Phrase(rapport[i][4],cellFont));
		    	table.addCell(cell);
		    	
		    	cell.setPhrase(new Phrase(rapport[i][5],cellFont));
		    	table.addCell(cell);
		    	
		    	cell.setPhrase(new Phrase(rapport[i][6],cellFont));
		    	table.addCell(cell);
		    	
		    	cell.setPhrase(new Phrase(rapport[i][7],cellFont));
		    	table.addCell(cell);
		    }
		    paragraph.add(table);
		  }
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
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
		  Image image=Image.getInstance("src/sources/images/ADAM_RAPPORT.png");
		  document.add(image);
		  
		  Paragraph titre=new Paragraph("Au rapport", titleFont);
		  titre.setAlignment(Element.ALIGN_CENTER);
		  preface.add(titre);

		  addEmptyLine(preface, 1);

		  Paragraph description=new Paragraph("Liste des biens du parc informatique de ADAM SAS",smallBold);
		  description.setAlignment(Element.ALIGN_CENTER);
		  preface.add(description);

		  addEmptyLine(preface, 3);

		  document.add(preface);
		  
		  Paragraph paragraph=new Paragraph("");

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

	    table.setWidths(new int[]{12,9,10,5,13,11,8});
	    table.setHorizontalAlignment(Element.ALIGN_CENTER);
	    
	    PdfPCell c1 = new PdfPCell(new Phrase("Nom"));
	    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    table.addCell(c1);
	    
	    c1 = new PdfPCell(new Phrase("N° Série"));
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
	    
	    RapportsDAO rapportsDAO = new RapportsDAO();
	    String[][] rapport=null;
	    try {
			rapport=rapportsDAO.getRapportMaterielParc();
		} catch (ConnexionBDException e) {
			// TODO Auto-generated catch block
			Popup.getInstance().afficherPopup(e.getMessage());
		}
	    
	    PdfPCell cell=new PdfPCell();
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    boolean pair;
	    BaseColor color;
	    for(int i=0;i<rapport.length;i++){
	    	pair=(i%2==0)?true:false;
	    	color=pair?BaseColor.LIGHT_GRAY:BaseColor.WHITE;
	    	
	    	cell.setPhrase(new Phrase(rapport[i][0],cellFont));
	    	cell.setBackgroundColor(color);
	    	table.addCell(cell);
	    	
	    	cell.setPhrase(new Phrase(rapport[i][1],cellFont));
	    	table.addCell(cell);
	    	
	    	cell.setPhrase(new Phrase(rapport[i][2],cellFont));
	    	table.addCell(cell);
	
	    	cell.setPhrase(new Phrase(rapport[i][3],cellFont));
	    	table.addCell(cell);
	    	
	    	cell.setPhrase(new Phrase(rapport[i][4],cellFont));
	    	table.addCell(cell);
	    	
	    	cell.setPhrase(new Phrase(rapport[i][5],cellFont));
	    	table.addCell(cell);
	    	
	    	cell.setPhrase(new Phrase(rapport[i][6],cellFont));
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

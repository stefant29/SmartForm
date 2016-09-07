//
//import java.io.File;
//import java.io.FileOutputStream;
//
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//
//public class CreateDocument 
//{
//	public static void main(String[] args)throws Exception 
//	{
//		//Blank Document
//		XWPFDocument document= new XWPFDocument(); 
//		//Write the Document in file system
//		FileOutputStream out = new FileOutputStream(
//				new File("createdocument.docx"));
//		document.write(out);
//		document.createParagraph();
//		out.close();
//		System.out.println(
//				"createdocument.docx written successully");
//	}
//}

import java.io.File;

/*
import java.io.File;
import java.io.FileOutputStream;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class CreateDocument 
{
	public static void main(String[] args)throws Exception 
	{
		//Blank Document
		XWPFDocument document= new XWPFDocument(); 
		//Write the Document in file system
		FileOutputStream out = new FileOutputStream(
				new File("createparagraph.docx"));

		//create Paragraph
		XWPFParagraph paragraph = document.createParagraph();	
		XWPFRun run=paragraph.createRun();
		run.setText("At tutorialspoint.com, we strive hard to " +
				"provide quality tutorials for self-learning " +
				"purpose in the domains of Academics, Information " +
				"Technology, Management and Computer Programming Languages.");
		document.write(out);
		out.close();
		System.out.println("createparagraph.docx written successfully");
	}
}
*/

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class CreateDocument 
{
   public static void main(String[] args)throws Exception 
   {
   XWPFDocument docx = new XWPFDocument(
   new FileInputStream("comanda.docx"));
   //using XWPFWordExtractor Class
   XWPFWordExtractor we = new XWPFWordExtractor(docx);
   
   String textDinFisier = we.getText(); 
   we.close();
   System.out.println("input: " + textDinFisier);
   System.out.println();
   
   String[] afterSplit = textDinFisier.split("E-mail:");
   
   String nume = "Stefan";
   String textToFile = afterSplit[0] + "E-mail: " + nume + afterSplit[1];

   
//   docx.getRange().replaceText("Location", "USA");
   
   
   ////////////////////write to file
   XWPFDocument document= new XWPFDocument(); 
	//Write the Document in file system
	FileOutputStream out = new FileOutputStream(
			new File("createparagraph.docx"));

	//create Paragraph
	XWPFParagraph paragraph = document.createParagraph();	
	XWPFRun run=paragraph.createRun();
	run.setText(textToFile);
	document.write(out);
	out.close();
	System.out.println("createparagraph.docx written successfully");
	document.close();
   }
}
package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class SmartForm {
	public static String fileName = "SmartForm.docx";
	
	
	public SmartForm() {
	}
	public SmartForm(String name) {
		fileName = name;
	}

	// Metoda ce salveaza documentul Word:
	public static void saveWord(String filePath, XWPFDocument docx) throws FileNotFoundException, IOException {
		FileOutputStream outContent = null;
		try {
			outContent = new FileOutputStream(filePath);
			System.out.println(filePath);
			docx.write(outContent);
		} finally {
			outContent.close();
		}
	}
}

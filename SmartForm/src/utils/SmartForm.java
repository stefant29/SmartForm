package utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class SmartForm {
	// Method used for saving the Word document:
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

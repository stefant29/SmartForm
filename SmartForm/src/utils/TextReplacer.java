package utils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class TextReplacer {
	private String searchValue;
	private String replacement;
	private boolean italic, bold, underline;

	public TextReplacer(String searchValue, String replacement) {
		this.searchValue = searchValue;
		this.replacement = replacement;
	}

	public TextReplacer(String searchValue, String replacement, boolean italic, boolean bold, boolean underline) {
		this.searchValue = searchValue;
		this.replacement = replacement;
		this.italic = italic;
		this.bold = bold;
		this.underline = underline;
	}

	public XWPFDocument replace(XWPFDocument document) {
		List<XWPFParagraph> paragraphs = document.getParagraphs();
		for (XWPFParagraph xwpfParagraph : paragraphs) {
			replace(xwpfParagraph);
		}
		return document;
	}

	private void replace(XWPFParagraph paragraph) {
		if (hasReplaceableItem(paragraph.getText())) {
			String replacedText = StringUtils.replace(paragraph.getText(), searchValue, replacement);
			removeAllRuns(paragraph);
			insertReplacementRuns(paragraph, replacedText);
		}
	}

	private void insertReplacementRuns(XWPFParagraph paragraph, String replacedText) {
		String[] replacementTextSplitOnCarriageReturn = StringUtils.split(replacedText, "\n");

		for (int j = 0; j < replacementTextSplitOnCarriageReturn.length; j++) {
			String part = replacementTextSplitOnCarriageReturn[j];

			XWPFRun newRun = paragraph.insertNewRun(j);
			newRun.setText(part);
			if(italic)
				newRun.setItalic(true);
			if(underline)
				newRun.setUnderline(UnderlinePatterns.SINGLE);
			if(bold)
				newRun.setBold(true);
			if (j + 1 < replacementTextSplitOnCarriageReturn.length) {
				newRun.addCarriageReturn();
			}
		}
	}

	private void removeAllRuns(XWPFParagraph paragraph) {
		int size = paragraph.getRuns().size();
		for (int i = 0; i < size; i++) {
			paragraph.removeRun(0);
		}
	}

	private boolean hasReplaceableItem(String runText) {
		return StringUtils.contains(runText, searchValue);
	}
}
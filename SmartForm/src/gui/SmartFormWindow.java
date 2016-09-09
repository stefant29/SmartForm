package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import utils.Autocomplete;
import utils.SmartForm;
import utils.TextReplacer;
import utils.Autocomplete.CommitAction;

public class SmartFormWindow extends JFrame {

	private static ArrayList<String> numeInput = null;
	private static ArrayList<String> prenumeInput = null;
	private static ArrayList<String> emailInput = null;

	private static void readChacheData() {
		try (BufferedReader br = new BufferedReader(new FileReader("autocomplete_cache.txt"))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				// get the name of the line ex: Nume, Prenume, E-mail
				String[] splitInput = line.split(": ");
				// get the inputs from that line ex: Stefan, Raphael etc.
				String[] input = splitInput[1].split(", ");

				System.out.println("inputtype: " + splitInput[0]);
				if (splitInput[0].equals("Nume")) {
					numeInput = new ArrayList<String>(Arrays.asList(input));
				} else if (splitInput[0].equals("Prenume")) {
					prenumeInput = new ArrayList<String>(Arrays.asList(input));
				} else if (splitInput[0].equals("E-mail")) {
					emailInput = new ArrayList<String>(Arrays.asList(input));
				} else {
					// if the cache file contains input not known
					System.out.println("Input type not known: " + splitInput[1]);
				}

				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String everything = sb.toString();
			System.out.println("text: " + everything);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < numeInput.size(); i++) {
			System.out.println(
					"____" + numeInput.get(i) + "____" + prenumeInput.get(i) + "_____" + emailInput.get(i) + "____");
		}
	}

	public static void main(String[] args) throws MalformedURLException {
		// First read data for autocomplete fields:
		// readChacheData();
		final String COMMIT_ACTION = "commit";

		// GUI stuff:
		// Creating the main window:
		SmartFormWindow window = new SmartFormWindow();
		// Setting the window's dimensions:
		Dimension dim = new Dimension(600, 400);
		window.setSize(dim);
		// Setting the window's title:
		window.setTitle("SmartForm");
		// Setting the window's icon:
		ImageIcon icon = new ImageIcon("lionIcon.png");
		window.setIconImage(icon.getImage());
		// Setting the window's background:
		Background background = new Background("ubuntu.png", dim);
		background.setSize(400, 200);
		background.repaint();

		// Numar inmatriculare:
		FlowLayout plateNoLayout = new FlowLayout();
		plateNoLayout.setAlignment(FlowLayout.LEFT);
		plateNoLayout.setHgap(20);
		JPanel plateNoPanel = new JPanel(plateNoLayout);
		JTextArea plateNoText = new JTextArea("Numar inmatriculare: ");
		Font plateNoFont = new Font("Verdana", Font.BOLD, 22);
		plateNoText.setFont(plateNoFont);
		plateNoText.setForeground(Color.YELLOW);
		plateNoText.setOpaque(false);
		plateNoText.setEditable(false);
		JTextField plateNoBox = new JTextField(20);
		plateNoPanel.add(plateNoText);
		plateNoPanel.add(plateNoBox);
		plateNoPanel.setSize(30, 20);
		plateNoPanel.setOpaque(false);
		// ---> Start of autocomplete code for plateNoBox:
		// Without this, cursor always leaves text field
		plateNoBox.setFocusTraversalKeysEnabled(false);
		// Keywords for autocomplete:
		ArrayList<String> plateNoBoxKeywords = new ArrayList<String>(5);
		plateNoBoxKeywords.add("B");
		plateNoBoxKeywords.add("BV");
		plateNoBoxKeywords.add("300");
		plateNoBoxKeywords.add("RAF");
		plateNoBoxKeywords.add("autoreactor");
		plateNoBoxKeywords.add("Stackabuse");
		plateNoBoxKeywords.add("java");
		Autocomplete autoComplete = new Autocomplete(plateNoBox, plateNoBoxKeywords);
		plateNoBox.getDocument().addDocumentListener(autoComplete);
		// Maps the tab key to the commit action, which finishes the
		// autocomplete when given a suggestion:
		plateNoBox.getInputMap().put(KeyStroke.getKeyStroke("TAB"), COMMIT_ACTION);
		plateNoBox.getActionMap().put(COMMIT_ACTION, autoComplete.new CommitAction());
		// <--- end of autocomplete for plateNoBox

		// Data incarcare:
		FlowLayout loadingDateLayout = new FlowLayout();
		loadingDateLayout.setAlignment(FlowLayout.LEFT);
		loadingDateLayout.setHgap(20);
		JPanel loadingDatePanel = new JPanel(loadingDateLayout);
		JTextArea loadingDateText = new JTextArea("Data incarcare: ");
		Font loadingDateFont = new Font("Verdana", Font.BOLD, 22);
		loadingDateText.setFont(loadingDateFont);
		loadingDateText.setForeground(Color.YELLOW);
		loadingDateText.setOpaque(false);
		loadingDateText.setEditable(false);
		JTextField loadingDateBox = new JTextField(20);
		loadingDatePanel.add(loadingDateText);
		loadingDatePanel.add(loadingDateBox);
		loadingDatePanel.setSize(30, 20);
		loadingDatePanel.setOpaque(false);

		// Adresa incarcare:
		FlowLayout loadingAdressLayout = new FlowLayout();
		loadingAdressLayout.setAlignment(FlowLayout.LEFT);
		loadingAdressLayout.setHgap(20);
		JPanel loadingAdressPanel = new JPanel(loadingAdressLayout);
		JTextArea loadingAdressText = new JTextArea("Adresa incarcare: ");
		Font loadingAdressFont = new Font("Verdana", Font.BOLD, 22);
		loadingAdressText.setFont(loadingAdressFont);
		loadingAdressText.setForeground(Color.YELLOW);
		loadingAdressText.setOpaque(false);
		loadingAdressText.setEditable(false);
		JTextField loadingAdressBox = new JTextField(20);
		loadingAdressPanel.add(loadingAdressText);
		loadingAdressPanel.add(loadingAdressBox);
		loadingAdressPanel.setSize(30, 20);
		loadingAdressPanel.setOpaque(false);

		// Referinta incarcare:
		FlowLayout refLayout = new FlowLayout();
		refLayout.setAlignment(FlowLayout.LEFT);
		refLayout.setHgap(20);
		JPanel refPanel = new JPanel(loadingAdressLayout);
		JTextArea refText = new JTextArea("Referinta incarcare: ");
		Font refFont = new Font("Verdana", Font.BOLD, 22);
		refText.setFont(refFont);
		refText.setForeground(Color.YELLOW);
		refText.setOpaque(false);
		refText.setEditable(false);
		JTextField refBox = new JTextField(20);
		refPanel.add(refText);
		refPanel.add(refBox);
		refPanel.setSize(30, 20);
		refPanel.setOpaque(false);

		// Data descarcare:
		FlowLayout unloadingDateLayout = new FlowLayout();
		unloadingDateLayout.setAlignment(FlowLayout.LEFT);
		unloadingDateLayout.setHgap(20);
		JPanel unloadingDatePanel = new JPanel(unloadingDateLayout);
		JTextArea unloadingDateText = new JTextArea("Data descarcare: ");
		Font unloadingDateFont = new Font("Verdana", Font.BOLD, 22);
		unloadingDateText.setFont(unloadingDateFont);
		unloadingDateText.setForeground(Color.YELLOW);
		unloadingDateText.setOpaque(false);
		unloadingDateText.setEditable(false);
		JTextField unloadingDateBox = new JTextField(20);
		unloadingDatePanel.add(unloadingDateText);
		unloadingDatePanel.add(unloadingDateBox);
		unloadingDatePanel.setSize(30, 20);
		unloadingDatePanel.setOpaque(false);

		// Adresa descarcare:
		FlowLayout unloadingAdressLayout = new FlowLayout();
		unloadingAdressLayout.setAlignment(FlowLayout.LEFT);
		unloadingAdressLayout.setHgap(20);
		JPanel unloadingAdressPanel = new JPanel(unloadingAdressLayout);
		JTextArea unloadingAdressText = new JTextArea("Adresa descarcare: ");
		Font unloadingAdressFont = new Font("Verdana", Font.BOLD, 22);
		unloadingAdressText.setFont(unloadingAdressFont);
		unloadingAdressText.setForeground(Color.YELLOW);
		unloadingAdressText.setOpaque(false);
		unloadingAdressText.setEditable(false);
		JTextField unloadingAdressBox = new JTextField(20);
		unloadingAdressPanel.add(unloadingAdressText);
		unloadingAdressPanel.add(unloadingAdressBox);
		unloadingAdressPanel.setSize(30, 20);
		unloadingAdressPanel.setOpaque(false);

		// Salvare:
		FlowLayout saveLayout = new FlowLayout();
		saveLayout.setAlignment(FlowLayout.LEFT);
		saveLayout.setHgap(20);
		JPanel savePanel = new JPanel(saveLayout);
		JTextArea savetxt = new JTextArea("Salveaza: ");
		Font saveFont = new Font("Verdana", Font.BOLD, 22);
		savetxt.setFont(saveFont);
		savetxt.setForeground(Color.YELLOW);
		savetxt.setOpaque(false);
		savetxt.setEditable(false);
		JButton saveButton = new JButton("Save");
		saveButton.setHorizontalTextPosition(SwingConstants.CENTER);
		saveButton.setVerticalTextPosition(SwingConstants.CENTER);
		savePanel.add(savetxt);
		savePanel.add(saveButton);
		savePanel.setOpaque(false);

		background.add(plateNoPanel);
		background.add(loadingDatePanel);
		background.add(loadingAdressPanel);
		background.add(refPanel);
		background.add(unloadingDatePanel);
		background.add(unloadingAdressPanel);
		background.add(savePanel);

		// Setam layout-ul:
		GridLayout layout = new GridLayout();
		// Setam alinierea la stanga:
		layout.setColumns(2);
		layout.setRows(7);
		// Setam marginile de orizontale si verticale:
		layout.setHgap(30);
		layout.setVgap(30);
		background.setLayout(layout);
		window.add(background);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);

		/// BACKEND
		SmartForm smartForm = new SmartForm();

		// Avem mai multe chestii de completat:
		TextReplacer r = new TextReplacer("Data incarcare:", "Data incarcare: 23");
		TextReplacer r1 = new TextReplacer("Adresa incarcare:", "Adresa incarcare: Strada Abatajului, numar 8");

		System.out.println("We are starting!");
		XWPFDocument docx = null;
		try {
			// Cream fisierul Word docx:
			docx = new XWPFDocument(new FileInputStream(smartForm.fileName));
			// Apelam metoda de replace pentru fiecare chestie de modificat:
			// putem face o lista eventual, cumva sa nu mai apelam succesiv
			r.replace(docx);
			r1.replace(docx);
			// Salvam documentul:
			// putem folosi acelasi nume ca la deschidere, adica sa "modificam"
			// fisierul:
			SmartForm.saveWord(SmartForm.fileName, docx);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				docx.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("We are done!");
		////// end of BACKEND
	}
}

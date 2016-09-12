package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import utils.Autocomplete;
import utils.SmartForm;
import utils.TextReplacer;
import utils.Autocomplete.CommitAction;

public class SmartFormWindow extends JFrame {
	private static ArrayList<String> numarInmatriculare = null;
	private static ArrayList<String> dataIncarcare = null;
	private static ArrayList<String> adresaIncarcare = null;
	private static ArrayList<String> referintaIncarcare = null;
	private static ArrayList<String> dataDescarcare = null;
	private static ArrayList<String> adresaDescarcare = null;

	private static void readCacheData() {
		try (BufferedReader br = new BufferedReader(new FileReader("autocomplete_cache.txt"))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				// get the name of the line ex: Nume, Prenume, E-mail
				String[] splitInput = line.split(": ");
				// get the inputs from that line ex: Stefan, Raphael etc.
				String[] input = splitInput[1].split("% ");

				if (splitInput[0].equals("numarInmatriculare")) {
					numarInmatriculare = new ArrayList<String>(Arrays.asList(input));
				} else if (splitInput[0].equals("dataIncarcare")) {
					dataIncarcare = new ArrayList<String>(Arrays.asList(input));
				} else if (splitInput[0].equals("adresaIncarcare")) {
					adresaIncarcare = new ArrayList<String>(Arrays.asList(input));
				} else if (splitInput[0].equals("referintaIncarcare")) {
					referintaIncarcare = new ArrayList<String>(Arrays.asList(input));
				} else if (splitInput[0].equals("dataDescarcare")) {
					dataDescarcare = new ArrayList<String>(Arrays.asList(input));
				} else if (splitInput[0].equals("adresaDescarcare")) {
					adresaDescarcare = new ArrayList<String>(Arrays.asList(input));
				} else {
					// if the cache file contains input not known
					System.out.println("Input type not known: " + splitInput[1]);
				}

				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String everything = sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < numarInmatriculare.size(); i++) 
			System.out.print(numarInmatriculare.get(i) + "\t");
		System.out.println();
		
		for (int i = 0; i < dataIncarcare.size(); i++) 
			System.out.print(dataIncarcare.get(i) + "\t");
		System.out.println();
		
		for (int i = 0; i < adresaIncarcare.size(); i++) 
			System.out.print(adresaIncarcare.get(i) + "\t");
		System.out.println();
		
		for (int i = 0; i < referintaIncarcare.size(); i++) 
			System.out.print(referintaIncarcare.get(i) + "\t");
		System.out.println();
		
		for (int i = 0; i < dataDescarcare.size(); i++) 
			System.out.print(dataDescarcare.get(i) + "\t");
		System.out.println();
		
		for (int i = 0; i < adresaDescarcare.size(); i++) 
			System.out.print(adresaDescarcare.get(i) + "\t");
		System.out.println();
	}

	/**
	 * Write specific data to file:
	 * 
	 * @param name
	 *            - type of data
	 * @param list
	 *            - list containting data
	 * @param writer
	 *            - writer used
	 * @throws IOException
	 */
	private static void writeSpecificData(String name, ArrayList<String> list, BufferedWriter writer)
			throws IOException {
		writer.write(name);
		if (list.size() == 0) {
			return;
		} else
			writer.write(list.get(0) + "%");
		for (int i = 1; i < list.size() - 1; i++)
			writer.write(" " + list.get(i) + "%");
		writer.write(" " + list.get(list.size() - 1) + "\n");
	}

	/**
	 * Save the inputs back to the cache file
	 */
	private static void saveCacheData() {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter("autocomplete_cache.txt"));
			writeSpecificData("numarInmatriculare: ", numarInmatriculare, writer);
			writeSpecificData("dataIncarcare: ", dataIncarcare, writer);
			writeSpecificData("adresaIncarcare: ", adresaIncarcare, writer);
			writeSpecificData("referintaIncarcare: ", referintaIncarcare, writer);
			writeSpecificData("dataDescarcare: ", dataDescarcare, writer);
			writeSpecificData("adresaDescarcare: ", adresaDescarcare, writer);
		} catch (IOException e) {
			System.err.println("write to cache file exeption");
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
				System.err.println("failed to close cache file");
			}
		}
	}

	/**
	 * Add new inputs to lists
	 * @param inputString - input to add to list
	 * @param list - list of elements where the string should be added
	 */
	private static void addToList(String inputString, ArrayList<String> list) {
		String[] split = inputString.split(" ");
		for (int i = 0; i < split.length; i++) {
			if (!list.contains(split[i]) && !split[i].equals("")) {
				list.add(split[i]);
			}
		}
	}

	public static void main(String[] args) throws MalformedURLException {
		// First read data for autocomplete fields:
		readCacheData();
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
		Autocomplete autoComplete = new Autocomplete(plateNoBox, numarInmatriculare);
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
		// ---> Start of autocomplete code for loadingDateBox:
		// Without this, cursor always leaves text field
		loadingDateBox.setFocusTraversalKeysEnabled(false);
		Autocomplete autoComplete2 = new Autocomplete(loadingDateBox, dataIncarcare);
		loadingDateBox.getDocument().addDocumentListener(autoComplete2);
		// Maps the tab key to the commit action, which finishes the
		// autocomplete when given a suggestion:
		loadingDateBox.getInputMap().put(KeyStroke.getKeyStroke("TAB"), COMMIT_ACTION);
		loadingDateBox.getActionMap().put(COMMIT_ACTION, autoComplete2.new CommitAction());
		// <--- end of autocomplete for loadingDateBox

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
		// ---> Start of autocomplete code for loadingAdressBox:
		// Without this, cursor always leaves text field
		loadingAdressBox.setFocusTraversalKeysEnabled(false);
		Autocomplete autoComplete3 = new Autocomplete(loadingAdressBox, adresaIncarcare);
		loadingAdressBox.getDocument().addDocumentListener(autoComplete3);
		// Maps the tab key to the commit action, which finishes the
		// autocomplete when given a suggestion:
		loadingAdressBox.getInputMap().put(KeyStroke.getKeyStroke("TAB"), COMMIT_ACTION);
		loadingAdressBox.getActionMap().put(COMMIT_ACTION, autoComplete3.new CommitAction());
		// <--- end of autocomplete for loadingAdressBox

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
		// ---> Start of autocomplete code for refBox:
		// Without this, cursor always leaves text field
		refBox.setFocusTraversalKeysEnabled(false);
		Autocomplete autoComplete4 = new Autocomplete(refBox, referintaIncarcare);
		refBox.getDocument().addDocumentListener(autoComplete4);
		// Maps the tab key to the commit action, which finishes the
		// autocomplete when given a suggestion:
		refBox.getInputMap().put(KeyStroke.getKeyStroke("TAB"), COMMIT_ACTION);
		refBox.getActionMap().put(COMMIT_ACTION, autoComplete4.new CommitAction());
		// <--- end of autocomplete for refBox

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
		// ---> Start of autocomplete code for unloadingDateBox:
		// Without this, cursor always leaves text field
		unloadingDateBox.setFocusTraversalKeysEnabled(false);
		Autocomplete autoComplete5 = new Autocomplete(unloadingDateBox, dataDescarcare);
		unloadingDateBox.getDocument().addDocumentListener(autoComplete5);
		// Maps the tab key to the commit action, which finishes the
		// autocomplete when given a suggestion:
		unloadingDateBox.getInputMap().put(KeyStroke.getKeyStroke("TAB"), COMMIT_ACTION);
		unloadingDateBox.getActionMap().put(COMMIT_ACTION, autoComplete5.new CommitAction());
		// <--- end of autocomplete for unloadingDateBox

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
		// ---> Start of autocomplete code for unloadingAdressBox:
		// Without this, cursor always leaves text field
		unloadingAdressBox.setFocusTraversalKeysEnabled(false);
		Autocomplete autoComplete6 = new Autocomplete(unloadingAdressBox, adresaDescarcare);
		unloadingAdressBox.getDocument().addDocumentListener(autoComplete6);
		// Maps the tab key to the commit action, which finishes the
		// autocomplete when given a suggestion:
		unloadingAdressBox.getInputMap().put(KeyStroke.getKeyStroke("TAB"), COMMIT_ACTION);
		unloadingAdressBox.getActionMap().put(COMMIT_ACTION, autoComplete6.new CommitAction());
		// <--- end of autocomplete for unloadingAdressBox

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
		saveButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Execute this when the Save button is pressed:
				// We need to get the content of text boxes and complete the
				// Word doc with it:
				String plateNo = plateNoBox.getText();
				String loadingDate = loadingDateBox.getText();
				String loadingAdress = loadingAdressBox.getText();
				String ref = refBox.getText();
				String unloadingDate = unloadingDateBox.getText();
				String unloadingAdress = unloadingAdressBox.getText();

				// Get the dialog reply:
				int reply = JOptionPane.showConfirmDialog(null, "Sunteti sigur ca valorile introduse sunt corecte?",
						"Confirm", JOptionPane.YES_NO_OPTION);
				// If the user is sure and want to proceed:
				if (reply == JOptionPane.YES_OPTION) {
					System.out.println(
							"YES button has been pressed, so we can proceed on completing and saving the Word document");
					// Setting the default saving location to Desktop:
					String userDir = System.getProperty("user.home");
					JFileChooser fileChooser = new JFileChooser(userDir + "/Desktop");
					// Setting the saving extension to .docx format:
					fileChooser.setAcceptAllFileFilterUsed(false);
					fileChooser.setMultiSelectionEnabled(false);
					FileNameExtensionFilter docxFilter = new FileNameExtensionFilter("Word document (.docx)", ".docx");
					fileChooser.setFileFilter(docxFilter);
					// Get the "Save location" dialog response:
					int reply2 = fileChooser.showSaveDialog(null);
					// If the Save button is pressed:
					if (reply2 == JFileChooser.APPROVE_OPTION) {
						// We are building the file path:
						String fileName = fileChooser.getSelectedFile().getName() + docxFilter.getExtensions()[0];
						String savingDir = fileChooser.getCurrentDirectory().toString();
						String filePath = savingDir + "\\" + fileName;
						// We are creating and saving an empty docx file:
						XWPFDocument docx = null;
						try {
							// Cream fisierul Word docx:
							docx = new XWPFDocument();
							// Salvam documentul:
							SmartForm.saveWord(filePath, docx);
							
							// save the new inputs to current lists:
							addToList(plateNo, numarInmatriculare);
							addToList(loadingDate, dataIncarcare);
							addToList(loadingAdress, adresaIncarcare);
							addToList(ref, referintaIncarcare);
							addToList(unloadingDate, dataDescarcare);
							addToList(unloadingAdress, adresaDescarcare);
							
							// save the lists to the cache file
							saveCacheData();

							System.out.println("Saved");
						} catch (Exception e2) {
							// TODO: handle exception
							e2.printStackTrace();
						}
					}
					if (reply == JFileChooser.CANCEL_OPTION) {
						System.out.println("You pressed the Cancel button!");
					}
				}
				System.out.println("You clicked the Save button");
			}
		});

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

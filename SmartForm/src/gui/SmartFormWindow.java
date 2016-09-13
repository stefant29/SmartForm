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
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
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

public class SmartFormWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private final static String docxName = "model_comanda.docx";
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
	 * 
	 * @param inputString
	 *            - input to add to list
	 * @param list
	 *            - list of elements where the string should be added
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
		Dimension dim = new Dimension(600, 700);
		window.setSize(dim);
		// Setting the window's title:
		window.setTitle("SmartForm");
		// Setting the window's icon:
		ImageIcon icon = new ImageIcon("lionIcon.png");
		window.setIconImage(icon.getImage());
		// Setting the window's background:
		Background background = new Background("ubuntu.png", dim);
		background.setSize(600, 700);
		background.repaint();

		// Numar comanda:
		FlowLayout noOrderLayout = new FlowLayout();
		noOrderLayout.setAlignment(FlowLayout.LEFT);
		noOrderLayout.setHgap(20);
		JPanel noOrderPanel = new JPanel(noOrderLayout);
		JTextArea noOrderText = new JTextArea("Numar comanda: ");
		Font noOrderFont = new Font("Verdana", Font.BOLD, 22);
		noOrderText.setFont(noOrderFont);
		noOrderText.setForeground(Color.YELLOW);
		noOrderText.setOpaque(false);
		noOrderText.setEditable(false);
		JTextField noOrderBox = new JTextField(20);
		noOrderBox.setAlignmentX(RIGHT_ALIGNMENT);
		noOrderPanel.add(noOrderText);
		noOrderPanel.add(noOrderBox);
		noOrderPanel.setSize(30, 20);
		noOrderPanel.setOpaque(false);

		// Din data:
		FlowLayout dateLayout = new FlowLayout();
		dateLayout.setAlignment(FlowLayout.LEFT);
		dateLayout.setHgap(20);
		JPanel datePanel = new JPanel(dateLayout);
		JTextArea dateText = new JTextArea("Din data: ");
		Font dateFont = new Font("Verdana", Font.BOLD, 22);
		dateText.setFont(dateFont);
		dateText.setForeground(Color.YELLOW);
		dateText.setOpaque(false);
		dateText.setEditable(false);
		JTextField dateBox = new JTextField(20);
		dateBox.setAlignmentX(RIGHT_ALIGNMENT);
		datePanel.add(dateText);
		datePanel.add(dateBox);
		datePanel.setSize(30, 20);
		datePanel.setOpaque(false);

		// Nume transportator:
		FlowLayout transporteNameLayout = new FlowLayout();
		transporteNameLayout.setAlignment(FlowLayout.LEFT);
		transporteNameLayout.setHgap(20);
		JPanel transporteNamePanel = new JPanel(transporteNameLayout);
		JTextArea transporteNameText = new JTextArea("Nume transportator: ");
		Font transporteNameFont = new Font("Verdana", Font.BOLD, 22);
		transporteNameText.setFont(transporteNameFont);
		transporteNameText.setForeground(Color.YELLOW);
		transporteNameText.setOpaque(false);
		transporteNameText.setEditable(false);
		JTextField transporteNameBox = new JTextField(20);
		transporteNameBox.setAlignmentX(RIGHT_ALIGNMENT);
		transporteNamePanel.add(transporteNameText);
		transporteNamePanel.add(transporteNameBox);
		transporteNamePanel.setSize(30, 20);
		transporteNamePanel.setOpaque(false);

		// In atentia:
		FlowLayout contactPersonLayout = new FlowLayout();
		contactPersonLayout.setAlignment(FlowLayout.LEFT);
		contactPersonLayout.setHgap(20);
		JPanel contactPersonPanel = new JPanel(contactPersonLayout);
		JTextArea contactPersonText = new JTextArea("In atentia: ");
		Font contactPersonFont = new Font("Verdana", Font.BOLD, 22);
		contactPersonText.setFont(contactPersonFont);
		contactPersonText.setForeground(Color.YELLOW);
		contactPersonText.setOpaque(false);
		contactPersonText.setEditable(false);
		JTextField contactPersonBox = new JTextField(20);
		contactPersonBox.setAlignmentX(RIGHT_ALIGNMENT);
		contactPersonPanel.add(contactPersonText);
		contactPersonPanel.add(contactPersonBox);
		contactPersonPanel.setSize(30, 20);
		contactPersonPanel.setOpaque(false);

		// Tip marfa:
		FlowLayout goodsTypeLayout = new FlowLayout();
		goodsTypeLayout.setAlignment(FlowLayout.LEFT);
		goodsTypeLayout.setHgap(20);
		JPanel goodsTypePanel = new JPanel(goodsTypeLayout);
		JTextArea goodsTypeText = new JTextArea("Tip marfa: ");
		Font goodsTypeFont = new Font("Verdana", Font.BOLD, 22);
		goodsTypeText.setFont(goodsTypeFont);
		goodsTypeText.setForeground(Color.YELLOW);
		goodsTypeText.setOpaque(false);
		goodsTypeText.setEditable(false);
		JTextField goodsTypeBox = new JTextField(20);
		goodsTypeBox.setAlignmentX(RIGHT_ALIGNMENT);
		goodsTypePanel.add(goodsTypeText);
		goodsTypePanel.add(goodsTypeBox);
		goodsTypePanel.setSize(30, 20);
		goodsTypePanel.setOpaque(false);

		// Pret transport:
		FlowLayout priceLayout = new FlowLayout();
		priceLayout.setAlignment(FlowLayout.LEFT);
		priceLayout.setHgap(20);
		JPanel pricePanel = new JPanel(priceLayout);
		JTextArea priceText = new JTextArea("Pret transport: ");
		Font priceFont = new Font("Verdana", Font.BOLD, 22);
		priceText.setFont(priceFont);
		priceText.setForeground(Color.YELLOW);
		priceText.setOpaque(false);
		priceText.setEditable(false);
		JTextField priceBox = new JTextField(20);
		priceBox.setAlignmentX(RIGHT_ALIGNMENT);
		pricePanel.add(priceText);
		pricePanel.add(priceBox);
		pricePanel.setSize(30, 20);
		pricePanel.setOpaque(false);

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
				String loadingAdress = loadingAdressBox.getText();
				String unloadingAdress = unloadingAdressBox.getText();
				String ref = refBox.getText();
				String loadingDate = loadingDateBox.getText();
				String unloadingDate = unloadingDateBox.getText();
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
						// We are creating and saving the docx file:
						XWPFDocument docx = null;
						// We are completing the docx file:
						TextReplacer rep1 = new TextReplacer("Nr. inmatriculare:", "Nr. inmatriculare: " + plateNo);
						TextReplacer rep2 = new TextReplacer("Data incarcare:", "Data incarcare: " + loadingDate);
						TextReplacer rep3 = new TextReplacer("Adresa incarcare:", "Adresa incarcare: " + loadingAdress);
						TextReplacer rep4 = new TextReplacer("Ref incarcare:", "Ref incarcare: " + ref);
						TextReplacer rep5 = new TextReplacer("Data de descarcare:",
								"Data de descarcare: " + unloadingDate);
						TextReplacer rep6 = new TextReplacer("Adresa de descarcare:",
								"Adresa de descarcare: " + unloadingAdress);
						System.out.println("1: " + plateNo);
						System.out.println("2: " + loadingDate);
						System.out.println("3: " + loadingAdress);
						System.out.println("4: " + ref);
						System.out.println("5: " + unloadingDate);
						System.out.println("6: " + unloadingAdress);
						try {
							// Cream fisierul Word docx:
							docx = new XWPFDocument(new FileInputStream(SmartFormWindow.docxName));
							rep1.replace(docx);
							rep2.replace(docx);
							rep3.replace(docx);
							rep4.replace(docx);
							rep5.replace(docx);
							rep6.replace(docx);
							// Salvam documentul:
							SmartForm.saveWord(filePath, docx);
							System.out.println("Saved done!");
							// Save the new inputs to current lists:
							addToList(plateNo, numarInmatriculare);
							addToList(loadingDate, dataIncarcare);
							addToList(loadingAdress, adresaIncarcare);
							addToList(ref, referintaIncarcare);
							addToList(unloadingDate, dataDescarcare);
							addToList(unloadingAdress, adresaDescarcare);

							autoComplete.refreshList(numarInmatriculare);
							autoComplete2.refreshList(dataIncarcare);
							autoComplete3.refreshList(adresaIncarcare);
							autoComplete4.refreshList(referintaIncarcare);
							autoComplete5.refreshList(dataDescarcare);
							autoComplete6.refreshList(adresaDescarcare);

							// save the lists to the cache file
							saveCacheData();

							System.out.println("Saved");
						} catch (Exception err) {
							err.printStackTrace();
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
		background.add(noOrderPanel);
		background.add(datePanel);
		background.add(transporteNamePanel);
		background.add(contactPersonPanel);
		background.add(plateNoPanel);
		background.add(goodsTypePanel);
		background.add(loadingDatePanel);
		background.add(loadingAdressPanel);
		background.add(refPanel);
		background.add(unloadingDatePanel);
		background.add(unloadingAdressPanel);
		background.add(pricePanel);
		background.add(savePanel);
		// Setam layout-ul:
		GridLayout layout = new GridLayout();
		// Setam alinierea la stanga:
		layout.setColumns(1);
		layout.setRows(13);
		// Setam marginile de orizontale si verticale:
		layout.setHgap(30);
		layout.setVgap(5);
		background.setLayout(layout);
		window.add(background);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
}

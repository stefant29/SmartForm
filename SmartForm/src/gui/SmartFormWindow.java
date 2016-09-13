package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

	private static ArrayList<String> numarComanda = null;
	private static ArrayList<String> dinData = null;
	private static ArrayList<String> numeTransportator = null;
	private static ArrayList<String> inAtentia = null;
	private static ArrayList<String> numarInmatriculare = null;
	private static ArrayList<String> tipMarfa = null;
	private static ArrayList<String> dataIncarcare = null;
	private static ArrayList<String> adresaIncarcare = null;
	private static ArrayList<String> referintaIncarcare = null;
	private static ArrayList<String> dataDescarcare = null;
	private static ArrayList<String> adresaDescarcare = null;
	private static ArrayList<String> pretTransport = null;

	private static void readCacheData() {
		try (BufferedReader br = new BufferedReader(new FileReader("autocomplete_cache.txt"))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				String[] splitInput = line.split(": ");
				String[] input = splitInput[1].split("% ");

				if (splitInput[0].equals("numarComanda")) {
					numarComanda = new ArrayList<String>(Arrays.asList(input));
				} else if (splitInput[0].equals("dinData")) {
					dinData = new ArrayList<String>(Arrays.asList(input));
				} else if (splitInput[0].equals("numeTransportator")) {
					numeTransportator = new ArrayList<String>(Arrays.asList(input));
				} else if (splitInput[0].equals("inAtentia")) {
					inAtentia = new ArrayList<String>(Arrays.asList(input));
				} else if (splitInput[0].equals("numarInmatriculare")) {
					numarInmatriculare = new ArrayList<String>(Arrays.asList(input));
				} else if (splitInput[0].equals("tipMarfa")) {
					tipMarfa = new ArrayList<String>(Arrays.asList(input));
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
				} else if (splitInput[0].equals("pretTransport")) {
					pretTransport = new ArrayList<String>(Arrays.asList(input));
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

		for (int i = 0; i < numarComanda.size(); i++)
			System.out.print(numarComanda.get(i) + "\t");
		System.out.println();

		for (int i = 0; i < dinData.size(); i++)
			System.out.print(dinData.get(i) + "\t");
		System.out.println();

		for (int i = 0; i < numeTransportator.size(); i++)
			System.out.print(numeTransportator.get(i) + "\t");
		System.out.println();

		for (int i = 0; i < inAtentia.size(); i++)
			System.out.print(inAtentia.get(i) + "\t");
		System.out.println();

		for (int i = 0; i < numarInmatriculare.size(); i++)
			System.out.print(numarInmatriculare.get(i) + "\t");
		System.out.println();

		for (int i = 0; i < tipMarfa.size(); i++)
			System.out.print(tipMarfa.get(i) + "\t");
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

		for (int i = 0; i < pretTransport.size(); i++)
			System.out.print(pretTransport.get(i) + "\t");
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
			writeSpecificData("numarComanda: ", numarComanda, writer);
			writeSpecificData("dinData: ", dinData, writer);
			writeSpecificData("numeTransportator: ", numeTransportator, writer);
			writeSpecificData("inAtentia: ", inAtentia, writer);
			writeSpecificData("numarInmatriculare: ", numarInmatriculare, writer);
			writeSpecificData("tipMarfa: ", tipMarfa, writer);
			writeSpecificData("dataIncarcare: ", dataIncarcare, writer);
			writeSpecificData("adresaIncarcare: ", adresaIncarcare, writer);
			writeSpecificData("referintaIncarcare: ", referintaIncarcare, writer);
			writeSpecificData("dataDescarcare: ", dataDescarcare, writer);
			writeSpecificData("adresaDescarcare: ", adresaDescarcare, writer);
			writeSpecificData("pretTransport: ", pretTransport, writer);
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
		noOrderText.setHighlighter(null);
		noOrderText.setPreferredSize(new Dimension(280, 30));
		JTextField noOrderBox = new JTextField(20);
		noOrderBox.setAlignmentX(RIGHT_ALIGNMENT);
		JButton clearNoOrderButton = new JButton("X");
		clearNoOrderButton.setMargin(new Insets(0, 0, 0, 0));
		clearNoOrderButton.setPreferredSize(new Dimension(22, 22));
		clearNoOrderButton.setFocusable(false);
		clearNoOrderButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				noOrderBox.setText("");
			}
		});
		noOrderPanel.add(noOrderText);
		noOrderPanel.add(noOrderBox);
		noOrderPanel.add(clearNoOrderButton);
		noOrderPanel.setSize(30, 20);
		noOrderPanel.setOpaque(false);
		// ---> Start of autocomplete code for noOrderBox:
		// Without this, cursor always leaves text field
		noOrderBox.setFocusTraversalKeysEnabled(false);
		Autocomplete autoComplete12 = new Autocomplete(noOrderBox, numarComanda);
		noOrderBox.getDocument().addDocumentListener(autoComplete12);
		// Maps the tab key to the commit action, which finishes the
		// autocomplete when given a suggestion:
		noOrderBox.getInputMap().put(KeyStroke.getKeyStroke("TAB"), COMMIT_ACTION);
		noOrderBox.getActionMap().put(COMMIT_ACTION, autoComplete12.new CommitAction());
		// <--- end of autocomplete for noOrderBox

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
		dateText.setHighlighter(null);
		dateText.setPreferredSize(new Dimension(280, 30));
		JTextField dateBox = new JTextField(20);
		dateBox.setAlignmentX(RIGHT_ALIGNMENT);
		JButton clearDateButton = new JButton("X");
		clearDateButton.setMargin(new Insets(0, 0, 0, 0));
		clearDateButton.setPreferredSize(new Dimension(22, 22));
		clearDateButton.setFocusable(false);
		clearDateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dateBox.setText("");
			}
		});
		datePanel.add(dateText);
		datePanel.add(dateBox);
		datePanel.add(clearDateButton);
		datePanel.setSize(30, 20);
		datePanel.setOpaque(false);
		// ---> Start of autocomplete code for dateBox:
		// Without this, cursor always leaves text field
		dateBox.setFocusTraversalKeysEnabled(false);
		Autocomplete autoComplete11 = new Autocomplete(dateBox, dinData);
		dateBox.getDocument().addDocumentListener(autoComplete11);
		// Maps the tab key to the commit action, which finishes the
		// autocomplete when given a suggestion:
		dateBox.getInputMap().put(KeyStroke.getKeyStroke("TAB"), COMMIT_ACTION);
		dateBox.getActionMap().put(COMMIT_ACTION, autoComplete11.new CommitAction());
		// <--- end of autocomplete for dateBox

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
		transporteNameText.setHighlighter(null);
		transporteNameText.setPreferredSize(new Dimension(280, 30));
		JTextField transporteNameBox = new JTextField(20);
		transporteNameBox.setAlignmentX(RIGHT_ALIGNMENT);
		JButton clearTransporterNameButton = new JButton("X");
		clearTransporterNameButton.setMargin(new Insets(0, 0, 0, 0));
		clearTransporterNameButton.setPreferredSize(new Dimension(22, 22));
		clearTransporterNameButton.setFocusable(false);
		clearTransporterNameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				transporteNameBox.setText("");
			}
		});
		transporteNamePanel.add(transporteNameText);
		transporteNamePanel.add(transporteNameBox);
		transporteNamePanel.add(clearTransporterNameButton);
		transporteNamePanel.setSize(30, 20);
		transporteNamePanel.setOpaque(false);
		// ---> Start of autocomplete code for transporteNameBox:
		// Without this, cursor always leaves text field
		transporteNameBox.setFocusTraversalKeysEnabled(false);
		Autocomplete autoComplete10 = new Autocomplete(transporteNameBox, numeTransportator);
		transporteNameBox.getDocument().addDocumentListener(autoComplete10);
		// Maps the tab key to the commit action, which finishes the
		// autocomplete when given a suggestion:
		transporteNameBox.getInputMap().put(KeyStroke.getKeyStroke("TAB"), COMMIT_ACTION);
		transporteNameBox.getActionMap().put(COMMIT_ACTION, autoComplete10.new CommitAction());
		// <--- end of autocomplete for contactPersonBox

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
		contactPersonText.setHighlighter(null);
		contactPersonText.setPreferredSize(new Dimension(280, 30));
		JTextField contactPersonBox = new JTextField(20);
		contactPersonBox.setAlignmentX(RIGHT_ALIGNMENT);
		JButton clearContactPersonButton = new JButton("X");
		clearContactPersonButton.setMargin(new Insets(0, 0, 0, 0));
		clearContactPersonButton.setPreferredSize(new Dimension(22, 22));
		clearContactPersonButton.setFocusable(false);
		clearContactPersonButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				contactPersonBox.setText("");
			}
		});
		contactPersonPanel.add(contactPersonText);
		contactPersonPanel.add(contactPersonBox);
		contactPersonPanel.add(clearContactPersonButton);
		contactPersonPanel.setSize(30, 20);
		contactPersonPanel.setOpaque(false);
		// ---> Start of autocomplete code for contactPersonBox:
		// Without this, cursor always leaves text field
		contactPersonBox.setFocusTraversalKeysEnabled(false);
		Autocomplete autoComplete9 = new Autocomplete(contactPersonBox, inAtentia);
		contactPersonBox.getDocument().addDocumentListener(autoComplete9);
		// Maps the tab key to the commit action, which finishes the
		// autocomplete when given a suggestion:
		contactPersonBox.getInputMap().put(KeyStroke.getKeyStroke("TAB"), COMMIT_ACTION);
		contactPersonBox.getActionMap().put(COMMIT_ACTION, autoComplete9.new CommitAction());
		// <--- end of autocomplete for contactPersonBox

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
		goodsTypeText.setHighlighter(null);
		goodsTypeText.setPreferredSize(new Dimension(280, 30));
		JTextField goodsTypeBox = new JTextField(20);
		goodsTypeBox.setAlignmentX(RIGHT_ALIGNMENT);
		JButton clearGoodsTypeButton = new JButton("X");
		clearGoodsTypeButton.setMargin(new Insets(0, 0, 0, 0));
		clearGoodsTypeButton.setPreferredSize(new Dimension(22, 22));
		clearGoodsTypeButton.setFocusable(false);
		clearGoodsTypeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				goodsTypeBox.setText("");
			}
		});
		goodsTypePanel.add(goodsTypeText);
		goodsTypePanel.add(goodsTypeBox);
		goodsTypePanel.add(clearGoodsTypeButton);
		goodsTypePanel.setSize(30, 20);
		goodsTypePanel.setOpaque(false);
		// ---> Start of autocomplete code for goodsTypeBox:
		// Without this, cursor always leaves text field
		goodsTypeBox.setFocusTraversalKeysEnabled(false);
		Autocomplete autoComplete8 = new Autocomplete(goodsTypeBox, tipMarfa);
		goodsTypeBox.getDocument().addDocumentListener(autoComplete8);
		// Maps the tab key to the commit action, which finishes the
		// autocomplete when given a suggestion:
		goodsTypeBox.getInputMap().put(KeyStroke.getKeyStroke("TAB"), COMMIT_ACTION);
		goodsTypeBox.getActionMap().put(COMMIT_ACTION, autoComplete8.new CommitAction());
		// <--- end of autocomplete for goodsTypeBox

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
		priceText.setHighlighter(null);
		priceText.setPreferredSize(new Dimension(280, 30));
		JTextField priceBox = new JTextField(20);
		priceBox.setAlignmentX(RIGHT_ALIGNMENT);
		JButton clearPriceButton = new JButton("X");
		clearPriceButton.setMargin(new Insets(0, 0, 0, 0));
		clearPriceButton.setPreferredSize(new Dimension(22, 22));
		clearPriceButton.setFocusable(false);
		clearPriceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				priceBox.setText("");
			}
		});
		pricePanel.add(priceText);
		pricePanel.add(priceBox);
		pricePanel.add(clearPriceButton);
		pricePanel.setSize(30, 20);
		pricePanel.setOpaque(false);
		// ---> Start of autocomplete code for priceBox:
		// Without this, cursor always leaves text field
		priceBox.setFocusTraversalKeysEnabled(false);
		Autocomplete autoComplete7 = new Autocomplete(priceBox, pretTransport);
		priceBox.getDocument().addDocumentListener(autoComplete7);
		// Maps the tab key to the commit action, which finishes the
		// autocomplete when given a suggestion:
		priceBox.getInputMap().put(KeyStroke.getKeyStroke("TAB"), COMMIT_ACTION);
		priceBox.getActionMap().put(COMMIT_ACTION, autoComplete7.new CommitAction());
		// <--- end of autocomplete for priceBox

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
		plateNoText.setHighlighter(null);
		plateNoText.setPreferredSize(new Dimension(280, 30));
		JTextField plateNoBox = new JTextField(20);
		JButton r2 = new JButton("X");
		r2.setMargin(new Insets(0, 0, 0, 0));
		r2.setPreferredSize(new Dimension(22, 22));
		r2.setFocusable(false);
		r2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				plateNoBox.setText("");
			}
		});
		plateNoPanel.add(plateNoText);
		plateNoPanel.add(plateNoBox);
		plateNoPanel.add(r2);
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
		loadingDateText.setHighlighter(null);
		loadingDateText.setPreferredSize(new Dimension(280, 30));
		JTextField loadingDateBox = new JTextField(20);
		JButton clearLoadingDateButton = new JButton("X");
		clearLoadingDateButton.setMargin(new Insets(0, 0, 0, 0));
		clearLoadingDateButton.setPreferredSize(new Dimension(22, 22));
		clearLoadingDateButton.setFocusable(false);
		clearLoadingDateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadingDateBox.setText("");
			}
		});
		loadingDatePanel.add(loadingDateText);
		loadingDatePanel.add(loadingDateBox);
		loadingDatePanel.add(clearLoadingDateButton);
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
		loadingAdressText.setHighlighter(null);
		loadingAdressText.setPreferredSize(new Dimension(280, 30));
		JTextField loadingAdressBox = new JTextField(20);
		JButton clearLoadingAdressButton = new JButton("X");
		clearLoadingAdressButton.setMargin(new Insets(0, 0, 0, 0));
		clearLoadingAdressButton.setPreferredSize(new Dimension(22, 22));
		clearLoadingAdressButton.setFocusable(false);
		clearLoadingAdressButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadingAdressBox.setText("");
			}
		});
		loadingAdressPanel.add(loadingAdressText);
		loadingAdressPanel.add(loadingAdressBox);
		loadingAdressPanel.add(clearLoadingAdressButton);
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
		refText.setHighlighter(null);
		refText.setPreferredSize(new Dimension(280, 30));
		JTextField refBox = new JTextField(20);
		JButton clearRefButton = new JButton("X");
		clearRefButton.setMargin(new Insets(0, 0, 0, 0));
		clearRefButton.setPreferredSize(new Dimension(22, 22));
		clearRefButton.setFocusable(false);
		clearRefButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refBox.setText("");
			}
		});
		refPanel.add(refText);
		refPanel.add(refBox);
		refPanel.add(clearRefButton);
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
		unloadingDateText.setHighlighter(null);
		unloadingDateText.setPreferredSize(new Dimension(280, 30));
		JTextField unloadingDateBox = new JTextField(20);
		JButton clearUnloadingDateButton = new JButton("X");
		clearUnloadingDateButton.setMargin(new Insets(0, 0, 0, 0));
		clearUnloadingDateButton.setPreferredSize(new Dimension(22, 22));
		clearUnloadingDateButton.setFocusable(false);
		clearUnloadingDateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				unloadingDateBox.setText("");
			}
		});
		unloadingDatePanel.add(unloadingDateText);
		unloadingDatePanel.add(unloadingDateBox);
		unloadingDatePanel.add(clearUnloadingDateButton);
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
		unloadingAdressText.setHighlighter(null);
		unloadingAdressText.setPreferredSize(new Dimension(280, 30));
		//unloadingAdressText.setPreferredSize(new Dimension(290, 30));
		JTextField unloadingAdressBox = new JTextField(20);
		JButton clearUnloadingAdressButton = new JButton("X");
		clearUnloadingAdressButton.setMargin(new Insets(0, 0, 0, 0));
		clearUnloadingAdressButton.setPreferredSize(new Dimension(22, 22));
		clearUnloadingAdressButton.setFocusable(false);
		clearUnloadingAdressButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				unloadingAdressBox.setText("");
			}
		});
		unloadingAdressPanel.add(unloadingAdressText);
		unloadingAdressPanel.add(unloadingAdressBox);
		unloadingAdressPanel.add(clearUnloadingAdressButton);
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
		savetxt.setHighlighter(null);
		JButton saveButton = new JButton("Save");
		saveButton.setHorizontalTextPosition(SwingConstants.CENTER);
		saveButton.setVerticalTextPosition(SwingConstants.CENTER);
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Execute this when the Save button is pressed:
				// We need to get the content of text boxes and complete the
				// Word doc with it:
				String orderNo = noOrderBox.getText();
				String date = dateBox.getText();
				String transporterName = transporteNameBox.getText();
				String contactName = contactPersonBox.getText();
				String plateNo = plateNoBox.getText();
				String goodsType = goodsTypeBox.getText();
				String loadingDate = loadingDateBox.getText();
				String loadingAdress = loadingAdressBox.getText();
				String ref = refBox.getText();
				String unloadingDate = unloadingDateBox.getText();
				String unloadingAdress = unloadingAdressBox.getText();
				String price = priceBox.getText();
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
						TextReplacer rep1 = new TextReplacer("COMANDA NR.:", "COMANDA NR.: " + orderNo);
						TextReplacer rep2 = new TextReplacer("DINR:", "DIN: " + date);
						TextReplacer rep3 = new TextReplacer("CATRER:", "CATRE: " + transporterName);
						TextReplacer rep4 = new TextReplacer("In atentia:", "In atentia: " + contactName);
						TextReplacer rep5 = new TextReplacer("Nr. inmatriculare:", "Nr. inmatriculare: " + plateNo);
						TextReplacer rep6 = new TextReplacer("Marfa:", "Marfa: " + goodsType);
						TextReplacer rep7 = new TextReplacer("Data incarcare:", "Data incarcare: " + loadingDate);
						TextReplacer rep8 = new TextReplacer("Adresa incarcare:", "Adresa incarcare: " + loadingAdress);
						TextReplacer rep9 = new TextReplacer("Ref incarcare:", "Ref incarcare: " + ref);
						TextReplacer rep10 = new TextReplacer("Data de descarcare:",
								"Data de descarcare: " + unloadingDate);
						TextReplacer rep11 = new TextReplacer("Adresa de descarcare:",
								"Adresa de descarcare: " + unloadingAdress);
						TextReplacer rep12 = new TextReplacer("Pret transport:", "Pret transport: " + price, true, true, true);
						try {
							// Cream fisierul Word docx:
							docx = new XWPFDocument(new FileInputStream(SmartFormWindow.docxName));
							rep1.replace(docx);
							rep2.replace(docx);
							rep3.replace(docx);
							rep4.replace(docx);
							rep5.replace(docx);
							rep6.replace(docx);
							rep7.replace(docx);
							rep8.replace(docx);
							rep9.replace(docx);
							rep10.replace(docx);
							rep11.replace(docx);
							rep12.replace(docx);
							// Salvam documentul:
							SmartForm.saveWord(filePath, docx);
							System.out.println("Saved done!");

							// Save the new inputs to current lists:
							addToList(noOrderBox.getText(), numarComanda);
							addToList(dateBox.getText(), dinData);
							addToList(transporteNameBox.getText(), numeTransportator);
							addToList(contactPersonBox.getText(), inAtentia);
							addToList(plateNo, numarInmatriculare);
							addToList(goodsTypeBox.getText(), tipMarfa);
							addToList(loadingDate, dataIncarcare);
							addToList(loadingAdress, adresaIncarcare);
							addToList(ref, referintaIncarcare);
							addToList(unloadingDate, dataDescarcare);
							addToList(priceBox.getText(), pretTransport);

							autoComplete12.refreshList(numarComanda);
							autoComplete11.refreshList(dinData);
							autoComplete10.refreshList(numeTransportator);
							autoComplete9.refreshList(inAtentia);
							autoComplete.refreshList(numarInmatriculare);
							autoComplete8.refreshList(tipMarfa);
							autoComplete2.refreshList(dataIncarcare);
							autoComplete3.refreshList(adresaIncarcare);
							autoComplete4.refreshList(referintaIncarcare);
							autoComplete5.refreshList(dataDescarcare);
							autoComplete6.refreshList(adresaDescarcare);
							autoComplete7.refreshList(pretTransport);

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
		JButton clearAllButton = new JButton("Clear all");
		clearAllButton.setHorizontalTextPosition(SwingConstants.CENTER);
		clearAllButton.setVerticalTextPosition(SwingConstants.CENTER);
		clearAllButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				noOrderBox.setText("");
				dateBox.setText("");
				transporteNameBox.setText("");
				contactPersonBox.setText("");
				plateNoBox.setText("");
				goodsTypeBox.setText("");
				loadingDateBox.setText("");
				loadingAdressBox.setText("");
				refBox.setText("");
				unloadingDateBox.setText("");
				unloadingAdressBox.setText("");
				priceBox.setText("");
			}
		});
		savePanel.add(savetxt);
		savePanel.add(saveButton);
		savePanel.add(clearAllButton);
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

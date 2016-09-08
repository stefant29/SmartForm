import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MyWindow extends JFrame {
	public static void main(String[] args) throws MalformedURLException {
		MyWindow win = new MyWindow();
		Dimension dim = new Dimension(600, 400);
		win.setSize(dim);
		win.setTitle("MyWindow is right here now!");
		URL iconURL = new URL("file:///C:/Users/Raphael/Desktop/leu.png");
		ImageIcon icon = new ImageIcon(iconURL);
		win.setIconImage(icon.getImage());
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
		JTextField plateNoBox = new JTextField(20);
		plateNoPanel.add(plateNoText);
		plateNoPanel.add(plateNoBox);
		plateNoPanel.setSize(30, 20);
		plateNoPanel.setOpaque(false);

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
		JButton b1 = new JButton("Feed it!");
		savePanel.add(savetxt);
		savePanel.add(b1);
		savePanel.setOpaque(false);
		
		background.add(plateNoPanel);
		background.add(loadingDatePanel);
		background.add(loadingAdressPanel);
		background.add(refPanel);
		background.add(unloadingDatePanel);
		background.add(unloadingAdressPanel);
		background.add(savePanel);
		// background.add(txt);
		// background.add(b1);
		// Setam layout-ul:
		GridLayout layout = new GridLayout();
		// Setam alinierea la stanga:
		layout.setColumns(2);
		layout.setRows(7);
		// Setam marginile de orizontale si verticale:
		layout.setHgap(30);
		layout.setVgap(30);
		background.setLayout(layout);
		win.add(background);
		win.setResizable(false);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setVisible(true);

	}
}
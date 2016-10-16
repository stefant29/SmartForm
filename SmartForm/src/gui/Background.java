package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class Background extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image backgroundImage = null;
	private Dimension backgroundSize;

	public Image getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(Image backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	Background(String filename, Dimension backgroundSize) {
		this.backgroundSize = backgroundSize;
		MediaTracker mt = new MediaTracker(this);
		backgroundImage = Toolkit.getDefaultToolkit().getImage(filename);
		mt.addImage(backgroundImage, 0);
		try {
			mt.waitForAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, backgroundSize.width, backgroundSize.height, null);
	}
}
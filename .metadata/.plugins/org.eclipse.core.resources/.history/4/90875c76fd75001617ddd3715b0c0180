package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Background extends JPanel {
	Image backgroundImage = null;
	Dimension backgroundSize;

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
		int imwidth = backgroundImage.getWidth(null);
		int imheight = backgroundImage.getHeight(null);

		g.drawImage(backgroundImage, 0, 0, backgroundSize.width, backgroundSize.height, null);
	}
}
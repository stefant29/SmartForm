import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame {
	Main() {
		add(new ContentPanel());
		setSize(500, 300);
	}

	public static void main(String[] args) {
		Main jrframe = new Main();
		jrframe.setVisible(true);
	}

}

class ContentPanel extends JPanel {
	Image bgimage = null;

	ContentPanel() {
		MediaTracker mt = new MediaTracker(this);
		bgimage = Toolkit.getDefaultToolkit().getImage("file:///C:/Users/Raphael/Desktop/leu.png");
		mt.addImage(bgimage, 0);
		try {
			mt.waitForAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int imwidth = bgimage.getWidth(null);
		int imheight = bgimage.getHeight(null);
		g.drawImage(bgimage, 1, 1, null);
	}
}
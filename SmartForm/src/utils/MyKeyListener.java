package utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;

import javax.swing.JTextField;

public class MyKeyListener implements KeyListener {
	Autocomplete autoComplete = null;
	JTextField textField = null;

	public MyKeyListener() {
	}

	public MyKeyListener(Autocomplete autoComplete, JTextField textField) {
		this.autoComplete = autoComplete;
		this.textField = textField;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		boolean knownLetter = true;

		String prefix = String.valueOf(e.getKeyChar());
		int n = Collections.binarySearch(autoComplete.keywords, prefix);
		if (n < 0 && -n <= autoComplete.keywords.size()) {
			String match = autoComplete.keywords.get(-n - 1);
			if (match.startsWith(prefix))
				knownLetter = true;
			else
				knownLetter = false;
		} else
			knownLetter = false;

		if (e.getKeyCode() != 9 && e.getKeyCode() != 8 && e.getKeyCode() != 32 && knownLetter)
			textField.setFocusTraversalKeysEnabled(false);
		else
			textField.setFocusTraversalKeysEnabled(true);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}

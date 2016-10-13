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

		String text = autoComplete.textField.getText();

		int w;
		for (w = text.length() - 1; w >= 0; w--)
			if (text.charAt(w) == ' ')
				break;

		String text2 = text.substring(w + 1);
		if (text2.length() == 0)
			text2 = String.valueOf(e.getKeyChar());
		else
			text2 = text2 + String.valueOf(e.getKeyChar());

		String prefix = String.valueOf(e.getKeyChar());
		int n = Collections.binarySearch(autoComplete.keywords, prefix);
		if (n < 0 && -n <= autoComplete.keywords.size()) {
			String match = autoComplete.keywords.get(-n - 1);
			if (match.startsWith(text2))
				knownLetter = true;

			else
				knownLetter = false;
		} else
			knownLetter = false;

		if (e.getKeyCode() != 9 && e.getKeyCode() != 8 && e.getKeyCode() != 32 && knownLetter) 
			textField.setFocusTraversalKeysEnabled(false);	// la apasare TAB autocompleteaza si scrie un spatiu
		else 
			textField.setFocusTraversalKeysEnabled(true);	// la apasarea TAB trece la campul urmator
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}

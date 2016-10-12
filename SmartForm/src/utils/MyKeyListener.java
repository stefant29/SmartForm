package utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;

import javax.swing.JTextField;

public class MyKeyListener implements KeyListener {
	Autocomplete autoComplete = null;
	JTextField textField = null;
	
	public MyKeyListener() {}

	public MyKeyListener(Autocomplete autoComplete, JTextField textField) {
		this.autoComplete = autoComplete;
		this.textField = textField;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		boolean knownLetter = true;

		System.out.println(autoComplete.keywords);
		String prefix = String.valueOf(e.getKeyChar());
		System.out.println("input: " + prefix);
		int n = Collections.binarySearch(autoComplete.keywords, prefix);
		if (n < 0 && -n <= autoComplete.keywords.size()) {
			String match = autoComplete.keywords.get(-n - 1);
			System.out.println("match: " + match);
			System.out.println("StartsWith: " + match.startsWith(prefix));
			if (match.startsWith(prefix)) {
				System.out.println("GOOD");
				knownLetter = true;
			} else {
				System.out.println("BAD");
				knownLetter = false;
			}
		} else {
			knownLetter = false;
			System.out.println("Not Found");
		}

		if (e.getKeyCode() != 9 && e.getKeyCode() != 8 && e.getKeyCode() != 32 && knownLetter) {
			System.out.println("set to false");
			textField.setFocusTraversalKeysEnabled(false);
		} else {
			System.out.println("set to true");
			textField.setFocusTraversalKeysEnabled(true);
		}

		System.out.println();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

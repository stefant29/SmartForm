package utils;

import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class Autocomplete implements DocumentListener {

	public static enum Mode {
		INSERT, COMPLETION
	};

	public JTextField textField;
	public List<String> keywords;
	public Mode mode = null;

	public Autocomplete(JTextField textField, List<String> keywords) {
		this.textField = textField;
		this.keywords = keywords;
		Collections.sort(keywords);
	}

	public void refreshList(List<String> keywords) {
		this.keywords = keywords;
		Collections.sort(keywords);
	}

	@Override
	public void changedUpdate(DocumentEvent ev) {
	}

	@Override
	public void removeUpdate(DocumentEvent ev) {
	}

	@Override
	public void insertUpdate(DocumentEvent ev) {
		if (ev.getLength() != 1)
			return;

		int pos = ev.getOffset();

		String content = null;
		try {
			content = textField.getText(0, pos + 1);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		// Find where the word starts
		int w;
		for (w = pos; w >= 0; w--) {
			if (!Character.isLetter(content.charAt(w)) && !Character.isDigit(content.charAt(w))) {
				// break if there is a non letter or non digit
				break;
			}
		}
		String prefix = content.substring(w + 1);
		// System.out.println("prefix: " + prefix);
		int n = Collections.binarySearch(keywords, prefix);
		if (n < 0 && -n <= keywords.size() && prefix.length() != 0) {
			String match = keywords.get(-n - 1);
			if (match.startsWith(prefix)) {
				textField.setFocusTraversalKeysEnabled(false);
				mode = Mode.COMPLETION;
				// A completion is found
				String completion = match.substring(pos - w);
				// We cannot modify Document from within notification,
				// so we submit a task that does the change later
				SwingUtilities.invokeLater(new CompletionTask(completion, pos + 1));
			}
		} else {
			textField.setFocusTraversalKeysEnabled(true);
			// Nothing found
			mode = Mode.INSERT;
		}
	}

	public class CommitAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5794543109646743416L;

		@Override
		public void actionPerformed(ActionEvent ev) {
			if (mode == Mode.COMPLETION) {
				int pos = textField.getSelectionEnd();
				StringBuffer sb = new StringBuffer(textField.getText());
				sb.insert(pos, " ");
				textField.setText(sb.toString());
				textField.setCaretPosition(pos + 1);
				mode = Mode.INSERT;
				textField.setFocusTraversalKeysEnabled(true); 
			} else {
				textField.replaceSelection("\t");
				textField.setFocusTraversalKeysEnabled(false);
			}
		}
	}

	private class CompletionTask implements Runnable {
		private String completion;
		private int position;

		CompletionTask(String completion, int position) {
			this.completion = completion;
			this.position = position;
		}

		public void run() {
			StringBuffer sb = new StringBuffer(textField.getText());
			sb.insert(position, completion);
			textField.setText(sb.toString());
			textField.setCaretPosition(position + completion.length());
			textField.moveCaretPosition(position);
			mode = Mode.COMPLETION;
		}
	}
}
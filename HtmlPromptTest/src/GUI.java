import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import javax.swing.JEditorPane;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;

public class GUI extends JFrame {

	private static final long serialVersionUID = 8513036072350768291L;
	
	private JPanel contentPane;
	private JButton insertBtn, clearBtn, saveBtn, openBtn;
	private JCheckBox activateFixChk;
	private JEditorPane editor;
	private InputHandler inputHandler;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1236, 812);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		editor = new JEditorPane();
		editor.setEditable(false);
		scrollPane = new JScrollPane(editor);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		final HTMLEditorKit kit = new HTMLEditorKit();
		editor.setEditorKit(kit);
		editor.getCaret().setVisible(true);

		editor.addKeyListener(new EditorKeyAdapter());

		// add some styles to the html
		final StyleSheet styleSheet = kit.getStyleSheet();
		styleSheet.addRule("body {color:#000; font-family:times; margin: 4px; }");
		styleSheet.addRule("h1 {color: blue;}");
		styleSheet.addRule("h2 {color: #ff0000;}");
		styleSheet.addRule("pre {font : 10px monaco; color : black; background-color : #fafafa; }");

		// create a document, set it on the jeditorpane, then add the html
		final Document doc = kit.createDefaultDocument();
		editor.setDocument(doc);
		inputHandler = new InputHandler(editor);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(3, 0, 0, 0));

		insertBtn = new JButton(new InsertAction());
		insertBtn.setText("Insert");
		panel.add(insertBtn);

		clearBtn = new JButton(new ClearAction());
		clearBtn.setText("Clear");
		panel.add(clearBtn);

		saveBtn = new JButton(new SaveAction(editor));
		saveBtn.setText("Save");
		panel.add(saveBtn);

		openBtn = new JButton(new LoadAction(editor));
		openBtn.setText("Open");
		panel.add(openBtn);

		activateFixChk = new JCheckBox("Activate Fix");
		panel.add(activateFixChk);
		activateFixChk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inputHandler.setReplaceDiv(activateFixChk.isSelected());
			}
		});
	}

	private class EditorKeyAdapter extends KeyAdapter {

		@Override
		public void keyTyped(final KeyEvent e) {
			System.out.println("e :" + e);
		}

		@Override
		public void keyReleased(final KeyEvent e) {
		}

		@Override
		public void keyPressed(final KeyEvent e) {
			System.out.println("Before: " + editor.getCaretPosition());
			if (e.getKeyCode() >= 48 && e.getKeyCode() <= 122) {
				inputHandler.appendToCommand(String.valueOf(e.getKeyChar()));
			}

			if (e.getKeyCode() == 10) {
				insertBtn.doClick();
			}
			System.out.println(": " + editor.getCaretPosition());

		}
	}

	private class InsertAction extends AbstractAction {

		private static final long serialVersionUID = 4395762403832483425L;

		InsertAction() {
			putValue(NAME, "Insert");
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			inputHandler.createCommandLine();
		}

	}

	private class ClearAction extends AbstractAction {

		private static final long serialVersionUID = -4014202894031129588L;

		ClearAction() {
			putValue(NAME, "Clear");
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			inputHandler.setNewHtmlContent();
		}

	}

	private class SaveAction extends AbstractAction {
		
		private static final long serialVersionUID = 7723721210466813695L;
		
		private final JEditorPane jEditorPane;

		SaveAction(final JEditorPane jEditorPane) {
			putValue(NAME, "Save");
			this.jEditorPane = jEditorPane;
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			// save here
			try (FileOutputStream out = new FileOutputStream(new File(
					"HtmlEditorKitTest.html"))) {
				final HTMLDocument htmlDocument = (HTMLDocument) jEditorPane
						.getDocument();
				jEditorPane.getEditorKit().write(out, htmlDocument, 0,
						htmlDocument.getLength() + 1);
				jEditorPane.getCaret().setVisible(true);

			} catch (final IOException | BadLocationException e1) {
				e1.printStackTrace();
			}
		}

	}

	private class LoadAction extends AbstractAction {

		private static final long serialVersionUID = -5490710352899649618L;
		
		private final JEditorPane jEditorPane;

		LoadAction(final JEditorPane jEditorPane) {
			putValue(NAME, "Load");
			this.jEditorPane = jEditorPane;
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			// load here
			try {
				final File file = new File("HtmlEditorKitTest.html");
				jEditorPane.setPage(file.toURI().toURL());
				jEditorPane.getCaret().setVisible(true);
				inputHandler = new InputHandler(jEditorPane);
				jEditorPane.getCaret().setVisible(true);
			} catch (final IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
}

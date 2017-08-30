
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.html.HTMLDocument;


public class InputHandler {
    private Element body;

    private Element cmdElement;

    private final HTMLDocument htmlDocument;

    private static final String PROMPT = "<font size=\"5\">[>_]  </font>";

    private String line = "";

    private final JEditorPane jEditorPane;

    private boolean replacep;

    public InputHandler(final JEditorPane jEditorPane) {
        this.jEditorPane = jEditorPane;
        this.htmlDocument = (HTMLDocument) jEditorPane.getDocument();
    }

    public void setReplaceDiv(final boolean replacep) {
        this.replacep = replacep;
    }

    public void createCommandLine() {
        if (replacep) {
            createCommandLineComplete();
        } else {
            createCommandLineLegacy();
        }
    }

    /**
     * Append a string to the existing command line
     *
     * @param input
     * @param replacep
     */
    public void appendToCommand(final String input) {
        line += input;
        if (replacep) {
            insertCommandComplete(line);
        } else {
            insertCommandLegacy(line);
        }
    }

    /**
     * This method creates a command p element and inserts a prompt in to the new command p
     * element.
     */
    private void createCommandLineLegacy() {
        // creates a new command line
        body = ElementUtils.getBodyElement(htmlDocument);
        System.out.println("body :" + body);

        line = PROMPT;

        try {
            htmlDocument.insertBeforeEnd(body, "<div bgColor=\"yellow\" type=\"COMMAND_LINE\" align=\"left\">" + line + "</div>");

            cmdElement = ElementUtils.getLastCommandLine(htmlDocument);
            System.out.println("cmdElement :" + cmdElement);

            htmlDocument.setInnerHTML(cmdElement, line);

        } catch (BadLocationException | IOException e) {
            e.printStackTrace();
        }

        jEditorPane.getCaret().setVisible(true);
        jEditorPane.requestFocusInWindow();

    }

    /**
     * This method creates a command p element including a prompt.
     */
    private void createCommandLineComplete() {
        // creates a new command line
        body = ElementUtils.getBodyElement(htmlDocument);
        System.out.println("body :" + body);

        line = PROMPT;

        try {
            htmlDocument.insertBeforeEnd(body, "<div bgColor=\"yellow\" type=\"COMMAND_LINE\" align=\"left\">" + line + "</div>");

            cmdElement = ElementUtils.getLastCommandLine(htmlDocument);
            System.out.println("cmdElement :" + cmdElement);

        } catch (BadLocationException | IOException e) {
            e.printStackTrace();
        }

        jEditorPane.getCaret().setVisible(true);
        jEditorPane.requestFocusInWindow();

    }

    private void insertCommandLegacy(final String localLine) {
        try {
            htmlDocument.setInnerHTML(cmdElement, localLine);
            System.err.println(cmdElement +" - " + localLine);
        } catch (BadLocationException | IOException e) {
            e.printStackTrace();
        }
        jEditorPane.getCaret().setVisible(true);
        jEditorPane.setCaretPosition(jEditorPane.getDocument().getLength());

    }

    private void insertCommandComplete(final String localLine) {
        try {
            if (cmdElement != null) {
                htmlDocument.removeElement(cmdElement);
            }

            htmlDocument.insertBeforeEnd(body, "<div bgColor=\"yellow\" type=\"COMMAND_LINE\" align=\"left\">" + localLine + "</div>");

            cmdElement = ElementUtils.getLastCommandLine(htmlDocument);
            System.err.println(cmdElement +" - " + localLine);

        } catch (BadLocationException | IOException e) {
            e.printStackTrace();
        }
        jEditorPane.getCaret().setVisible(true);
        jEditorPane.setCaretPosition(jEditorPane.getDocument().getLength());

    }

    public void setNewHtmlContent() {
        final String htmlString = "<html>" + "<head>" + "</head>" + "<body>" + " </body>" + "</html>";
        jEditorPane.setText(htmlString);
    }

}

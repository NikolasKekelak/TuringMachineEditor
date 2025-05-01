package Command;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import java.awt.*;

class LineNumberingTextArea extends JTextArea {
    private JTextPane textPane;

    public LineNumberingTextArea(JTextPane textPane) {
        super("1");
        this.textPane = textPane;
        setBackground(Color.LIGHT_GRAY);
        setFont(new Font("Monospaced", Font.PLAIN, 14));
        setEditable(false);

        textPane.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateLineNumbers();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateLineNumbers();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateLineNumbers();
            }

            private void updateLineNumbers() {
                updateLineNumbersForTextPane();
            }
        });
    }

    private void updateLineNumbersForTextPane() {
        int lines = getLineCount(textPane);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= lines; i++) {
            sb.append(i).append("\n");
        }
        setText(sb.toString());
    }

    private int getLineCount(JTextPane textPane) {
        int lineCount = 0;
        Element root = textPane.getDocument().getDefaultRootElement();
        if (root != null) {
            lineCount = root.getElementCount();
        }
        return lineCount;
    }
}

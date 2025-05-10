package Editor.Automatas.TuringMachine;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class TuringMachineValidator {

    private static final Color ERROR_COLOR = new Color(255, 100, 150); // soft red/pink

    public static void validate(JTextPane editorPane) {
        StyledDocument doc = editorPane.getStyledDocument();
        removeAllTooltips(editorPane); // optional, resets last session

        try {
            String text = doc.getText(0, doc.getLength());
            String[] lines = text.split("\n");
            int offset = 0;

            for (String line : lines) {
                String trimmed = line.trim();

                if (trimmed.startsWith("tape") && !line.contains("=")) {
                    markError(doc, offset, line.length(), "Missing '=' in tape definition");
                } else if (trimmed.startsWith("#set") && (!line.contains("=") || !line.contains("{") || !line.contains("}"))) {
                    markError(doc, offset, line.length(), "Set definition must contain '=', '{' and '}'");
                } else if (trimmed.startsWith("f(")) {
                    if (!line.contains("=") || !line.contains("(") || !line.contains(")")) {
                        markError(doc, offset, line.length(), "Malformed transition rule: expect f(...) = (...)");
                    }
                }

                offset += line.length() + 1;
            }

        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private static void markError(StyledDocument doc, int offset, int length, String message) {
        Style style = doc.addStyle("error", null);
        StyleConstants.setUnderline(style, true);
        StyleConstants.setForeground(style, ERROR_COLOR);
        doc.setCharacterAttributes(offset, length, style, true);
        // Tooltip isn't native here – handled elsewhere if needed
    }

    private static void removeAllTooltips(JTextPane pane) {
        // Clear logic (you can reset attributes here if you cache original styles)
    }
}

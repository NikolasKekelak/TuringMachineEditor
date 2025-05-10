package Editor.Automatas.TuringMachine;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TuringMachineValidator {

    private final JTextPane editorPane;
    private final Highlighter highlighter;
    private final ArrayList<Object> highlights = new ArrayList<>();

    public TuringMachineValidator(JTextPane editorPane) {
        this.editorPane = editorPane;
        this.highlighter = editorPane.getHighlighter();
    }

    public void validate() {
        // Clear existing highlights
        for (Object tag : highlights) highlighter.removeHighlight(tag);
        highlights.clear();

        String text = editorPane.getText();
        String[] lines = text.split("\n");
        int offset = 0;

        for (String line : lines) {
            String trimmed = line.trim();
            String error = null;

            if (trimmed.startsWith("tape")) {
                if (!trimmed.contains("=") || !trimmed.contains("{")) {
                    error = "Tape declaration missing '=' or '{...}'";
                }
            } else if (trimmed.startsWith("#set")) {
                if (!trimmed.contains("=") || !trimmed.contains("{") || !trimmed.contains("}")) {
                    error = "Set declaration malformed. Expected format: #set = {a, b, ...}";
                }
            } else if (trimmed.startsWith("f(")) {
                if (!trimmed.contains("=") || !trimmed.contains(")") || !trimmed.contains("(")) {
                    error = "Transition rule malformed. Expected format: f(state, symbol) = (...)";
                }
            }

            if (error != null) {
                try {
                    int start = offset;
                    int end = offset + line.length();
                    Object tag = highlighter.addHighlight(start, end, new DefaultHighlighter.DefaultHighlightPainter(Color.PINK));
                    highlights.add(tag);
                    editorPane.setToolTipText(error); // Optional: You may want custom hover logic per line
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }

            offset += line.length() + 1;
        }
    }
}

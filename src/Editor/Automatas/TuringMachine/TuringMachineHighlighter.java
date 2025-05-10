package Editor.Automatas.TuringMachine;

import Editor.Engine.SyntaxHighlighter;
import Editor.Editor.EditorTheme;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class TuringMachineHighlighter implements SyntaxHighlighter {

    private Style defaultStyle;
    private Style keywordStyle;
    private Style commentStyle;
    private Style setStyle;
    private Style stateStyle;
    private EditorTheme theme;

    @Override
    public void setTheme(EditorTheme theme) {
        this.theme = theme;
    }

    @Override
    public void applyTheme(EditorTheme theme) {
        this.theme = theme;
    }

    @Override
    public void highlight(JTextPane editorPane) {
        highlightRange(editorPane, 0, editorPane.getDocument().getLength());
    }

    @Override
    public void highlightRange(JTextPane editorPane, int offset, int length) {
        StyledDocument doc = editorPane.getStyledDocument();
        setupStyles(editorPane);

        try {
            String text = doc.getText(offset, length);
            doc.setCharacterAttributes(offset, length, defaultStyle, true); // Reset to default

            String[] lines = text.split("\n");
            int lineOffset = offset;

            for (String line : lines) {
                String trimmed = line.trim();

                // % comment
                if (trimmed.startsWith("%")) {
                    doc.setCharacterAttributes(lineOffset, line.length(), commentStyle, true);
                }

                // tape NAME = {...}
                else if (trimmed.matches("^tape\\s+\\w+\\s*=\\s*\\{[^}]*\\}")) {
                    int tapeIdx = line.indexOf("tape");
                    doc.setCharacterAttributes(lineOffset + tapeIdx, 4, keywordStyle, true);

                    int eqIdx = line.indexOf("=");
                    if (eqIdx != -1)
                        doc.setCharacterAttributes(lineOffset + eqIdx, 1, keywordStyle, true);

                    int lbrace = line.indexOf("{");
                    int rbrace = line.indexOf("}");
                    if (lbrace != -1)
                        doc.setCharacterAttributes(lineOffset + lbrace, 1, keywordStyle, true);
                    if (rbrace != -1)
                        doc.setCharacterAttributes(lineOffset + rbrace, 1, keywordStyle, true);
                }

                // #set = {...}
                else if (trimmed.matches("^#set\\s*=\\s*\\{[^}]*\\}")) {
                    int hashIdx = line.indexOf("#");
                    doc.setCharacterAttributes(lineOffset + hashIdx, 1, setStyle, true);

                    int setIdx = line.indexOf("set");
                    doc.setCharacterAttributes(lineOffset + setIdx, 3, setStyle, true);

                    int eqIdx = line.indexOf("=");
                    if (eqIdx != -1)
                        doc.setCharacterAttributes(lineOffset + eqIdx, 1, keywordStyle, true);

                    int lbrace = line.indexOf("{");
                    int rbrace = line.indexOf("}");
                    if (lbrace != -1)
                        doc.setCharacterAttributes(lineOffset + lbrace, 1, keywordStyle, true);
                    if (rbrace != -1)
                        doc.setCharacterAttributes(lineOffset + rbrace, 1, keywordStyle, true);
                }

                // rule: f(...) = (...)
                else if (trimmed.matches("^f\\s*\\([^)]*\\)\\s*=\\s*\\([^)]*\\)")) {
                    int fIdx = line.indexOf("f");
                    doc.setCharacterAttributes(lineOffset + fIdx, 1, keywordStyle, true);

                    int lpar1 = line.indexOf("(");
                    int rpar1 = line.indexOf(")");
                    if (lpar1 != -1)
                        doc.setCharacterAttributes(lineOffset + lpar1, 1, keywordStyle, true);
                    if (rpar1 != -1)
                        doc.setCharacterAttributes(lineOffset + rpar1, 1, keywordStyle, true);

                    int eqIdx = line.indexOf("=", rpar1);
                    if (eqIdx != -1)
                        doc.setCharacterAttributes(lineOffset + eqIdx, 1, keywordStyle, true);

                    int lpar2 = line.indexOf("(", eqIdx);
                    int rpar2 = line.lastIndexOf(")");
                    if (lpar2 != -1)
                        doc.setCharacterAttributes(lineOffset + lpar2, 1, keywordStyle, true);
                    if (rpar2 != -1)
                        doc.setCharacterAttributes(lineOffset + rpar2, 1, keywordStyle, true);
                }

                lineOffset += line.length() + 1;
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }



    private void highlightKeywordLine(StyledDocument doc, String line, int offset) throws BadLocationException {
        String[] tokens = line.split("\\s+");

        if (tokens.length > 0) {
            // Highlight first token (e.g., "tape", "f", etc.)
            doc.setCharacterAttributes(offset, tokens[0].length(), keywordStyle, true);

            int cursor = offset + tokens[0].length();
            for (int i = 1; i < tokens.length; i++) {
                cursor += 1; // space
                if (tokens[i].matches("q\\d+")) {
                    doc.setCharacterAttributes(cursor, tokens[i].length(), stateStyle, true);
                }
                cursor += tokens[i].length();
            }
        }
    }

    private void setupStyles(JTextPane pane) {
        if (theme == null) {
            System.err.println("[Highlighter] Theme not set. Skipping style setup.");
            return;
        }

        StyledDocument doc = pane.getStyledDocument();

        defaultStyle = doc.addStyle("default", null);
        StyleConstants.setForeground(defaultStyle, theme.editorForeground);

        keywordStyle = doc.addStyle("keyword", null);
        StyleConstants.setBold(keywordStyle, true);
        StyleConstants.setForeground(keywordStyle, theme.keywordColor);

        commentStyle = doc.addStyle("comment", null);
        StyleConstants.setForeground(commentStyle, theme.commentColor);

        setStyle = doc.addStyle("set", null);
        StyleConstants.setForeground(setStyle, theme.setColor);

        stateStyle = doc.addStyle("state", null);
        StyleConstants.setForeground(stateStyle, theme.stateColor);
    }
}

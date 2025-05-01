package Command.TuringMachine;

import Command.Engine.SyntaxHighlighter;
import Command.Editor.EditorTheme;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class TuringMachineHighlighter implements SyntaxHighlighter {

    private EditorTheme theme;
    @Override
    public void setTheme(EditorTheme theme){
        this.theme = theme;
    }

    @Override
    public void highlight(JTextPane editorPane) {
        highlightRange(editorPane, 0, editorPane.getDocument().getLength());
    }

    @Override
    public void highlightRange(JTextPane editorPane, int offset, int length) {
        try {
            StyledDocument doc = editorPane.getStyledDocument();
            String text = doc.getText(offset, length);

            Style defaultStyle = editorPane.getStyle("default");
            Style keywordStyle = editorPane.getStyle("keyword");

            doc.setCharacterAttributes(offset, length, defaultStyle, true);

            String[] keywords = {"tape", "f", "set", "state"};

            for (String keyword : keywords) {
                int index = text.indexOf(keyword);
                while (index >= 0) {
                    boolean before = (index == 0 || !Character.isLetterOrDigit(text.charAt(index - 1)));
                    boolean after = (index + keyword.length() == text.length() || !Character.isLetterOrDigit(text.charAt(index + keyword.length())));
                    if (before && after) {
                        doc.setCharacterAttributes(offset + index, keyword.length(), keywordStyle, true);
                    }
                    index = text.indexOf(keyword, index + 1);
                }
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }



}

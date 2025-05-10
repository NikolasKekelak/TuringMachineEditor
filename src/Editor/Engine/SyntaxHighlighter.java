package Editor.Engine;

import Editor.Editor.EditorTheme;

import javax.swing.*;

public interface SyntaxHighlighter {
    void setTheme(EditorTheme theme);
    void highlight(JTextPane area);
    void highlightRange(JTextPane editorPane, int offset, int length);
    void applyTheme(EditorTheme theme);
}

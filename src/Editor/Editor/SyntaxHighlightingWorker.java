package Editor.Editor;

import Editor.Engine.SyntaxHighlighter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SyntaxHighlightingWorker {

    private final JTextPane editorPane;
    private final SyntaxHighlighter highlighter;
    private final Timer debounceTimer;

    public SyntaxHighlightingWorker(JTextPane editorPane, SyntaxHighlighter highlighter) {
        this.editorPane = editorPane;
        this.highlighter = highlighter;

        // Debounce: triggers highlight 150ms after last change
        this.debounceTimer = new Timer(150, e -> {
            new Thread(() -> {
                try {
                    String text = editorPane.getText(); // heavy call in background
                    SwingUtilities.invokeLater(() -> {
                        highlighter.highlight(editorPane); // safe on EDT
                    });
                } catch (Exception ignored) {}
            }).start();
        });
        this.debounceTimer.setRepeats(false);

        attachListeners();
    }

    private void attachListeners() {
        editorPane.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { debounce(); }
            @Override public void removeUpdate(DocumentEvent e) { debounce(); }
            @Override public void changedUpdate(DocumentEvent e) { debounce(); }

            private void debounce() {
                debounceTimer.restart();
            }
        });
    }

    public void forceHighlight() {
        highlighter.highlight(editorPane);
    }
}

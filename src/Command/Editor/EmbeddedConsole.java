package Command.Editor;

import Command.TuringEditorUI;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EmbeddedConsole {
    private final JTextPane consoleArea = new JTextPane();
    private final JTextField inputField = new JTextField();
    private final JScrollPane scrollPane = new JScrollPane(consoleArea);
    private final JPanel consolePanel = new JPanel(new BorderLayout());
    private final StyledDocument doc = consoleArea.getStyledDocument();
    private final Container parent;
    private final TuringEditorUI editor;

    private boolean visible = false;

    public EmbeddedConsole(JComponent parentComponent, TuringEditorUI editor) {
        this.editor = editor;
        this.parent = parentComponent;

        // Console text area
        consoleArea.setEditable(false);
        consoleArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        consoleArea.setBackground(Color.BLACK);
        consoleArea.setForeground(Color.WHITE);
        consoleArea.setCaretColor(Color.WHITE);
        scrollPane.setPreferredSize(new Dimension(600, 150));

        // Input field
        inputField.setFont(new Font("Monospaced", Font.PLAIN, 12));
        inputField.setBackground(Color.DARK_GRAY);
        inputField.setForeground(Color.WHITE);
        inputField.setCaretColor(Color.WHITE);

        // Input handler
        inputField.addActionListener(e -> {
            String text = inputField.getText().trim();
            if (!text.isEmpty()) {
                log("> " + text, Color.GRAY);
                String[] args = text.split("\\s+");
                try {
                    command(args);
                } catch (Exception ex) {
                    log("Error: " + ex.getMessage(), Color.RED);
                }
                inputField.setText("");
            }
        });

        // Layout
        consolePanel.add(scrollPane, BorderLayout.CENTER);
        consolePanel.add(inputField, BorderLayout.SOUTH);
        consolePanel.setVisible(false); // hidden by default

        // Keyboard toggle
        KeyStroke toggleKey = KeyStroke.getKeyStroke("control L");
        parentComponent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(toggleKey, "toggleConsole");
        parentComponent.getActionMap().put("toggleConsole", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggle();
            }
        });
    }

    public JComponent getComponent() {
        return consolePanel;
    }

    public void toggle() {
        visible = !visible;
        consolePanel.setVisible(visible);

        consolePanel.revalidate();
        consolePanel.repaint();

        if (parent != null) {
            parent.revalidate();
            parent.repaint();
        }

        if (visible) {
            SwingUtilities.invokeLater(() -> inputField.requestFocusInWindow());
        }
    }

    public void log(String message) {
        log(message, Color.WHITE);
    }

    public void log(String message, Color color) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);

        try {
            doc.insertString(doc.getLength(), message + "\n", aset);
            consoleArea.setCaretPosition(doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        consoleArea.setText("");
    }

    public boolean isVisible() {
        return visible;
    }

    public void command(String[] arguments) {
        switch (arguments[0]) {
            case "switchto" -> editor.changeType(arguments);
            case "compile" -> editor.compile();
            case "play" -> editor.play();
            case "step" -> editor.step(Integer.parseInt(arguments[1]));
            case "reset" -> {
                editor.stop();
                editor.reset();
            }
            case "replay" ->{
                editor.stop();
                editor.reset();
                editor.play();
            }
            case "stop" -> editor.stop();
            case "guide" ->editor.guide();
            case "console" -> consoleCommand(arguments);
            case "theme" -> {
                if (arguments.length == 1) {
                    log(" Available themes: ", Color.CYAN);
                    for (String name : editor.getThemes()) {
                        ConsoleLogger.info("  " + name);
                    }
                } else if (arguments.length == 2) {
                    editor.changeTheme(arguments[1]);
                } else if (arguments.length == 3) {
                    editor.changeTheme(arguments[1] + " " + arguments[2]);
                }
            }
            case "help" -> {
                log("Available commands:", Color.CYAN);
                log("  switchto [turingmachine|abacus]", Color.CYAN);
                log("  console [clear]", Color.CYAN);
                log("  play,clear,reset,replay,stop", Color.CYAN);
                log("  help", Color.CYAN);
            }
            default -> throw new IllegalArgumentException("Unknown command: " + arguments[0]);
        }
    }

    public void consoleCommand(String[] arguments){
        switch(arguments[1]){
            case "clear" -> clear();

            default -> throw new IllegalArgumentException("Unknown console command: " + arguments[1]);
        }
    }
}

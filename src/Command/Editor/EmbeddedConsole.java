package Command.Editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EmbeddedConsole {
    private final JTextArea consoleArea = new JTextArea();
    private final JScrollPane scrollPane = new JScrollPane(consoleArea);
    private boolean visible = false;


    public EmbeddedConsole(JComponent parentComponent) {
        consoleArea.setEditable(false);
        consoleArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        scrollPane.setPreferredSize(new Dimension(600, 150));
        scrollPane.setVisible(false); // hidden by default

        // Add key binding for Ctrl + ~
        KeyStroke toggleKey = KeyStroke.getKeyStroke("control L");
        parentComponent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(toggleKey, "toggleConsole");
        parentComponent.getActionMap().put("toggleConsole", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggle();
            }
        });
    }

    public JScrollPane getComponent() {
        return scrollPane;
    }

    public void toggle() {
        visible = !visible;
        scrollPane.setVisible(visible);
        scrollPane.revalidate();
        scrollPane.repaint();
    }

    public void log(String message) {
        consoleArea.append(message + "\n");
        consoleArea.setCaretPosition(consoleArea.getDocument().getLength());
    }

    public void clear() {
        consoleArea.setText("");
    }

    public boolean isVisible() {
        return visible;
    }
}

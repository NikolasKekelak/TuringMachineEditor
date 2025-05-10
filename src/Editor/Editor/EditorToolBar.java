package Editor.Editor;

import Editor.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EditorToolBar extends JToolBar {
    private final JComboBox<String> themeSelector;
    private final JSlider speedSlider;
    private final JButton compileButton, stepButton, resetButton, playButton, stopButton, saveButton, loadButton;
    private final JButton guideButton;

    public EditorToolBar(EditorTheme theme, ActionListener actionHandler, ThemeManager themeManager) {
        setFloatable(false);
        setBackground(theme.toolbarBackground);
        setPreferredSize(new Dimension(100, 45));

        compileButton = createButton("Compile", theme, actionHandler);
        stepButton = createButton("Step", theme, actionHandler);
        resetButton = createButton("Reset", theme, actionHandler);
        playButton = createButton("Play", theme, actionHandler);
        stopButton = createButton("Stop", theme, actionHandler);
        saveButton = createButton("Save", theme, actionHandler);
        loadButton = createButton("Open", theme, actionHandler);
        guideButton = createButton("Guide", theme, actionHandler);
        speedSlider = new JSlider(10, 1000, 100);
        speedSlider.setPreferredSize(new Dimension(100, 30));

        themeSelector = new JComboBox<>(themeManager.getThemeNames().toArray(new String[0]));
        themeSelector.setPreferredSize(new Dimension(75, 30));
        styleComboBox(themeSelector, theme);

        add(themeSelector);
        add(compileButton);
        add(stepButton);
        add(resetButton);
        add(playButton);
        add(stopButton);
        add(new JLabel("Speed:"));
        add(speedSlider);
        add(saveButton);
        add(loadButton);
        add(guideButton);
    }

    private JButton createButton(String text, EditorTheme theme, ActionListener actionHandler) {
        JButton button = new JButton(text);
        button.setBackground(theme.buttonBackground);
        button.setForeground(theme.buttonForeground);
        button.setBorder(BorderFactory.createLineBorder(theme.buttonBorderColor));
        button.setFocusable(false);
        button.setFont(theme.editorFont);
        button.addActionListener(actionHandler);

        button.setPreferredSize(new Dimension(90, 35));
        return button;
    }

    private void styleComboBox(JComboBox<String> comboBox, EditorTheme theme) {
        comboBox.setBackground(theme.buttonBackground);
        comboBox.setForeground(theme.buttonForeground);
        comboBox.setFont(theme.editorFont);
    }

    public JComboBox<String> getThemeSelector() {
        return themeSelector;
    }

    public JSlider getSpeedSlider() {
        return speedSlider;
    }

    public void refreshTheme(EditorTheme newTheme) {
        applyTheme(newTheme);
    }

    private void applyTheme(EditorTheme theme) {
        setBackground(theme.toolbarBackground);

        // Update all buttons
        for (Component comp : getComponents()) {
            if (comp instanceof JButton button) {
                button.setBackground(theme.buttonBackground);
                button.setForeground(theme.buttonForeground);
                button.setFont(theme.editorFont);
                button.setBorder(BorderFactory.createLineBorder(theme.buttonBorderColor));
            } else if (comp instanceof JLabel label) {
                label.setForeground(theme.editorForeground);
                label.setFont(theme.editorFont);
            } else if (comp instanceof JComboBox<?> comboBox) {
                comboBox.setBackground(theme.buttonBackground);
                comboBox.setForeground(theme.buttonForeground);
                comboBox.setFont(theme.editorFont);
                comboBox.setBorder(BorderFactory.createLineBorder(theme.buttonBorderColor)); // NEW
            } else if (comp instanceof JSlider slider) {
                slider.setBackground(theme.toolbarBackground);
                slider.setForeground(theme.buttonForeground); // NEW
                slider.setBorder(BorderFactory.createLineBorder(theme.buttonBorderColor)); // NEW
            }
        }

        revalidate();
        repaint();
    }


}


package Command;

import Command.Editor.*;
import Command.Engine.AutomatonRenderer;
import Command.Engine.AutomatonType;
import Command.Engine.AutomatonEngine;
import Command.Engine.SyntaxHighlighter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TuringEditorUI extends JFrame implements ActionListener {

    private JScrollPane editorScroll;
    private JScrollPane tapeScroll;
    private boolean isRunning = false;
    public final EmbeddedConsole console;


    private final ThemeManager themeManager = new ThemeManager();
    private final EditorTheme theme = new EditorTheme();
    private final EditorMemory editorMemory = new EditorMemory();
    private final JTextPane editorArea = new JTextPane();
    private final JPanel tapePanel = new JPanel();
    private final JPanel controlPanel = new JPanel(new BorderLayout());
    private final EditorToolBar toolbar;

    private final EditorSessionManager sessionManager = new EditorSessionManager();

    AutomatonType type;

    private List<List<String>> lastCompiledProgram = null;
    private String lastEditorText = "";
    private Timer autoRunTimer;
    private int autoRunDelay = 100;

    public TuringEditorUI(String name, AutomatonEngine engine, AutomatonRenderer render, SyntaxHighlighter syntaxHighlighter) {
        super("Turing Machine Editor");
        type = new AutomatonType(name, engine,render,syntaxHighlighter);
        this.console = new EmbeddedConsole(editorArea);
        ConsoleLogger.log = console::log;
        this.toolbar = new EditorToolBar(theme, this, themeManager);
        toolbar.getSpeedSlider().addChangeListener(e -> {
            int delay = toolbar.getSpeedSlider().getValue();
            setAutoRunDelay(delay);
        });
        setupUI();
    }

    private void setupUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);

        type.syntaxHighlighter.setTheme(this.theme);

        editorArea.setFont(theme.editorFont);
        editorScroll = new JScrollPane(editorArea);
        editorScroll.setRowHeaderView(new LineNumberingTextArea(editorArea));
        editorMemory.attach(editorArea);


        editorScroll.getVerticalScrollBar().setUI(new ThemedScrollBarUI(theme));
        editorScroll.getHorizontalScrollBar().setUI(new ThemedScrollBarUI(theme));

        tapePanel.setLayout(new BoxLayout(tapePanel, BoxLayout.Y_AXIS));
        tapeScroll = new JScrollPane(tapePanel);
        tapeScroll.getVerticalScrollBar().setUnitIncrement(16);
        tapeScroll.getVerticalScrollBar().setUI(new ThemedScrollBarUI(theme));
        tapeScroll.getHorizontalScrollBar().setUI(new ThemedScrollBarUI(theme));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, editorScroll, tapeScroll);
        splitPane.setDividerLocation(600);
        splitPane.setDividerSize(8);

        controlPanel.add(toolbar, BorderLayout.NORTH);
        controlPanel.add(splitPane, BorderLayout.CENTER);

        toolbar.getThemeSelector().addActionListener(e -> {
            String selected = (String) toolbar.getThemeSelector().getSelectedItem();
            changeTheme(selected);
        });

        sessionManager.loadSession();
        editorArea.setText(sessionManager.getLastEditorText());
        toolbar.getThemeSelector().setSelectedItem(sessionManager.getLastTheme());
        changeTheme(sessionManager.getLastTheme());

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                sessionManager.saveSession(editorArea.getText(), (String) toolbar.getThemeSelector().getSelectedItem());
                System.exit(0);
            }
        });


        add(controlPanel);
        controlPanel.add(console.getComponent(), BorderLayout.SOUTH);
        applyTheme();
        setVisible(true);
    }

    public void changeTheme(String name) {
        EditorTheme loadedTheme = themeManager.getTheme(name);
        if (loadedTheme != null) {
            this.theme.copyFrom(loadedTheme);
            applyTheme();
        }
    }

    private void applyTheme() {
        editorArea.setBackground(theme.editorBackground);
        editorArea.setForeground(theme.editorForeground);
        editorArea.setCaretColor(theme.editorForeground);
        editorArea.setFont(theme.editorFont);

        tapePanel.setBackground(theme.panelBackground);
        controlPanel.setBackground(theme.panelBackground);
        getContentPane().setBackground(theme.background);

        if (editorScroll != null) {
            editorScroll.getVerticalScrollBar().setUI(new ThemedScrollBarUI(theme));
            editorScroll.getHorizontalScrollBar().setUI(new ThemedScrollBarUI(theme));
        }
        if (tapeScroll != null) {
            tapeScroll.getVerticalScrollBar().setUI(new ThemedScrollBarUI(theme));
            tapeScroll.getHorizontalScrollBar().setUI(new ThemedScrollBarUI(theme));
        }

        toolbar.refreshTheme(theme);

        updateTapePanel();
        revalidate();
        repaint();
    }

    public void setAutoRunDelay(int delay) {
        this.autoRunDelay = delay;
    }

    private void onCompile(ActionEvent e) {
        try {
            String code = editorArea.getText();
            type.engine.compile(code);
            lastEditorText = code;
            updateTapePanel();
            console.log("Compiled successfully.");
            JOptionPane.showMessageDialog(this, "Compilation successful.", "Compiled", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Compilation failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onStep(ActionEvent e) {
        try {
            if (!type.engine.step()) {
                System.out.println("HALTED: " + type.engine.getCurrentStatus());
                autoRunTimer.stop();
                isRunning = false;
                JOptionPane.showMessageDialog(this, "Machine has halted.");
            } else {
                System.out.println("STEP: " + type.engine.getCurrentStatus());
            }
            updateTapePanel();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Step failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onReset(ActionEvent e) {
        try {
            type.engine.reset();
            editorArea.setText(lastEditorText);
            updateTapePanel();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Reset failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onPlay(ActionEvent e) {
        autoRunDelay = toolbar.getSpeedSlider().getValue();

        if (autoRunTimer != null && autoRunTimer.isRunning()) {
            autoRunTimer.stop();
            isRunning = false;
        } else {
            isRunning = true;
            autoRunTimer = new Timer(autoRunDelay, evt -> {
                try {
                    if (!type.engine.step()) {
                        autoRunTimer.stop();
                        isRunning = false;
                        JOptionPane.showMessageDialog(this, "Machine has halted.");
                    }
                    updateTapePanel();
                } catch (Exception ex) {
                    autoRunTimer.stop();
                    isRunning = false;
                    JOptionPane.showMessageDialog(this, "Auto-run error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            autoRunTimer.start();
        }
    }

    private void onGuide() {
        if (type.engine != null) {
            String guideText = type.engine.getGuide();

            JTextArea textArea = new JTextArea(guideText);
            textArea.setEditable(false);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            JScrollPane scrollPane = new JScrollPane(textArea);

            JDialog guideDialog = new JDialog(this, "Automaton Guide", true);
            guideDialog.getContentPane().add(scrollPane);
            guideDialog.setSize(600, 400);
            guideDialog.setLocationRelativeTo(this);
            guideDialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "No engine loaded.", "Guide", JOptionPane.WARNING_MESSAGE);
        }
    }


    private void updateTapePanel() {
        type.renderer.updateTapePanel(tapePanel, type.engine, theme);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Compile" -> onCompile(e);
            case "Step" -> onStep(e);
            case "Reset" -> onReset(e);
            case "Play" -> onPlay(e);
            case "Stop" -> { if (autoRunTimer != null) autoRunTimer.stop(); }
            case "Save" -> EditorFileManager.saveToFile(this, editorArea);
            case "Open" -> EditorFileManager.loadFromFile(this, editorArea);
            case "Guide" -> onGuide();
        }
    }
}

package Editor;

import Editor.Abacus.AbacusMachine;
import Editor.Abacus.AbacusRender;
import Editor.Automatas.TuringMachine.TuringMachineValidator;
import Editor.Editor.*;
import Editor.Engine.*;
import Editor.Automatas.TuringMachine.TapePanelRender.DefaultTapePanelUpdater;
import Editor.Automatas.TuringMachine.TuringMachine;
import Editor.Automatas.TuringMachine.TuringMachineHighlighter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

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
    private SyntaxHighlightingWorker highlighterWorker;

    private final EditorSessionManager sessionManager = new EditorSessionManager();

    private AutomatonType type;

    private String lastEditorText = "";
    private Timer autoRunTimer;
    private int autoRunDelay = 100;

    public TuringEditorUI(String name) {
        super("Turing Machine Editor");
        changeType(new String[]{"",name});
        this.console = new EmbeddedConsole((JComponent) getContentPane(),this);
        ConsoleLogger.log = console::log;
        ConsoleLogger.logColor = console::log;

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

        // Set theme first!
        changeTheme(sessionManager.getLastTheme()); // This internally calls setTheme + applyTheme

        /*
        // Now safe to use highlighter
        type.syntaxHighlighter.setTheme(this.theme);
        type.syntaxHighlighter.applyTheme(this.theme);
        highlighterWorker = new SyntaxHighlightingWorker(editorArea, type.syntaxHighlighter);
        highlighterWorker.forceHighlight();

        highlighterWorker = new SyntaxHighlightingWorker(editorArea, type.syntaxHighlighter);
        highlighterWorker.forceHighlight();
        */

        toolbar.getThemeSelector().setSelectedItem(sessionManager.getLastTheme());
        changeTheme(sessionManager.getLastTheme());

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                sessionManager.saveSession(editorArea.getText(), (String) toolbar.getThemeSelector().getSelectedItem());
                themeManager.saveAllThemesToJson();
                System.exit(0);
            }
        });


        add(controlPanel);
        controlPanel.add(console.getComponent(), BorderLayout.SOUTH);
        applyTheme();

        setUndecorated(true);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(new CustomTitleBar(this, theme, "Turing Machine Editor"));
        topPanel.add(toolbar);
        controlPanel.add(topPanel, BorderLayout.NORTH);

        setVisible(true);
    }

    public void changeTheme(String name) {
        EditorTheme loadedTheme = themeManager.getTheme(name);
        if (loadedTheme != null) {
            this.theme.copyFrom(loadedTheme);
            applyTheme();

            if (type != null && type.syntaxHighlighter != null) {
                type.syntaxHighlighter.setTheme(this.theme);
                type.syntaxHighlighter.applyTheme(this.theme);
                if (highlighterWorker != null) {
                    highlighterWorker.forceHighlight();
                }
            }
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
        //highlighterWorker.forceHighlight()

        TuringMachineValidator.validate(editorArea);
        try {
            String code = editorArea.getText();
            type.engine.compile(code);
            lastEditorText = code;
            updateTapePanel();
            ConsoleLogger.success("Compiled successfully.");
        } catch (Exception ex) {
            ConsoleLogger.error(ex.getMessage());
            if(!console.isVisible())
                console.toggle();
        }
    }

    private void onStep(ActionEvent e) {
        try {
            if (!type.engine.step()) {
                autoRunTimer.stop();
                isRunning = false;
                ConsoleLogger.success("Machine has halted.");
            }
            updateTapePanel();
        } catch (Exception ex) {
            ConsoleLogger.error("Step failed: " + ex.getMessage());
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
                        ConsoleLogger.success("Machine has halted.");
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


    public void changeType(String[] arguments) {
        AutomatonTypeBundle bundle = switch (arguments[1]) {
            case "turingmachine" -> new AutomatonTypeBundle(
                    "TuringMachine",
                    new TuringMachine(0),
                    new DefaultTapePanelUpdater(),
                    new TuringMachineHighlighter()
            );
            case "abacus" -> new AutomatonTypeBundle(
                    "Abacus",
                    new AbacusMachine(),
                    new AbacusRender(),
                    new TuringMachineHighlighter() // You may want to swap this for an AbacusHighlighter later
            );
            default ->  throw new IllegalStateException("Unknown engine type: " + arguments[1]);
        };
        ConsoleLogger.success("Automaton Type switch succesful to: " + bundle.name);
        this.type = new AutomatonType(bundle.name, bundle.engine, bundle.renderer, bundle.highlighter);
    }

    public void compile() {
        onCompile(null);
    }

    public void play() {
        onPlay(null);
    }

    public void reset() {
        onReset(null);
    }
    public void step(int count){
        while(count!=0){
            onStep(null);
            count--;
        }
    }

    public void guide (){
        onGuide();
    }

    public void stop(){ if (autoRunTimer != null) autoRunTimer.stop(); }

    public Set<String> getThemes(){
        return themeManager.getThemeNames();
    }
}

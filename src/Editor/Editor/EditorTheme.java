package Editor.Editor;

import java.awt.*;

public class EditorTheme {

    public String name= "Cozy Pink";

    public Color background;
    public Color panelBackground;
    public Color editorBackground;
    public Color editorForeground;
    public Color tapeBackground;
    public Color selectedTapeBackground;
    public Color selectedTapeBorderColor;
    public Font editorFont;
    public Color toolbarBackground;
    public Color buttonBackground;
    public Color buttonForeground;
    public Color buttonBorderColor;
    public Color cellBorderColor;
    public Color scrollbarThumb;
    public Color scrollbarTrack;
    public Color keywordColor;
    public Color commentColor;
    public Color setColor;
    public Color stateColor;

    public Color tapeBorderColor;
    public Color tapeTextColor;
    public Color selectedCellBackground;
    public Color selectedCellForeground;
    public Color currentStateTextColor;

    public EditorTheme() {
        setDefault();
    }


    public void setDefault() {
        background = new Color(240, 240, 240);
        panelBackground = new Color(230, 230, 230);
        editorBackground = new Color(255, 255, 255);
        editorForeground = Color.BLACK;
        tapeBackground = new Color(255, 255, 255);
        selectedTapeBackground = new Color(220, 220, 220);
        selectedTapeBorderColor = new Color(0, 120, 215);

        toolbarBackground = new Color(230, 230, 230);
        buttonBackground = new Color(245, 245, 245);
        buttonForeground = Color.BLACK;
        buttonBorderColor = new Color(180, 180, 180);

        tapeBorderColor = new Color(150, 150, 150); // <- NEW
        tapeTextColor = Color.BLACK; // <- NEW
        selectedCellBackground = new Color(200, 230, 255); // <- NEW
        selectedCellForeground = Color.BLACK; // <- NEW
        currentStateTextColor = Color.BLACK; // <- NEW

        cellBorderColor = new Color(150, 150, 150);
        editorFont = new Font("Monospaced", Font.PLAIN, 14);

        scrollbarThumb = new Color(120, 120, 120);
        scrollbarTrack = new Color(200, 200, 200);
        keywordColor = new Color(200, 120, 50);  // orange-ish
        commentColor = new Color(100, 255, 100); // light green
        setColor = new Color(80, 160, 255);      // light blue
        stateColor = new Color(255, 200, 0);     // yellow  // Default is dark theme
    }

    public void copyFrom(EditorTheme other) {
        this.background = other.background;
        this.panelBackground = other.panelBackground;
        this.editorBackground = other.editorBackground;
        this.editorForeground = other.editorForeground;
        this.tapeBackground = other.tapeBackground;
        this.selectedTapeBackground = other.selectedTapeBackground;
        this.selectedTapeBorderColor = other.selectedTapeBorderColor;
        this.toolbarBackground = other.toolbarBackground;
        this.buttonBackground = other.buttonBackground;
        this.buttonForeground = other.buttonForeground;
        this.buttonBorderColor = other.buttonBorderColor;
        this.cellBorderColor = other.cellBorderColor;
        this.scrollbarThumb = other.scrollbarThumb;
        this.scrollbarTrack = other.scrollbarTrack;

        this.tapeBorderColor = other.tapeBorderColor;
        this.tapeTextColor = other.tapeTextColor;
        this.selectedCellBackground = other.selectedCellBackground;
        this.selectedCellForeground = other.selectedCellForeground;
        this.currentStateTextColor = other.currentStateTextColor;


        this.keywordColor = other.keywordColor;
        this.commentColor = other.commentColor;
        this.setColor     = other.setColor;
        this.stateColor   = other.stateColor;

        this.editorFont = other.editorFont; // optionally clone font if needed
    }



}

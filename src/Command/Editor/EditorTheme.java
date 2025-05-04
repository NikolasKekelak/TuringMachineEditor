package Command.Editor;

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


    public Color tapeBorderColor;
    public Color tapeTextColor;
    public Color selectedCellBackground;
    public Color selectedCellForeground;
    public Color currentStateTextColor;

    public EditorTheme() {
        setDefault();  // Default is dark theme
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
    }

    public void setToxicPink() {
        background = new Color(30, 0, 30); // Dark pink/purple background
        panelBackground = new Color(50, 0, 50);
        editorBackground = new Color(70, 0, 70);
        editorForeground = new Color(255, 182, 193); // Light pink text

        tapeBackground = new Color(90, 0, 90);
        selectedTapeBackground = new Color(120, 0, 120);
        selectedTapeBorderColor = new Color(255, 20, 147); // Hot pink border

        toolbarBackground = new Color(50, 0, 50);
        buttonBackground = new Color(90, 0, 90);
        buttonForeground = new Color(255, 105, 180); // Lighter pink
        buttonBorderColor = new Color(255, 20, 147); // Same hot pink

        tapeBorderColor = new Color(180, 0, 90); // More intense purple
        tapeTextColor = new Color(255, 182, 193); // Light pink text on tape
        selectedCellBackground = new Color(255, 20, 147); // Bright toxic pink
        selectedCellForeground = Color.WHITE; // White text inside selected cell
        currentStateTextColor = new Color(255, 105, 180); // Soft toxic text color

        cellBorderColor = new Color(255, 20, 147); // Toxic cell border
        editorFont = new Font("Consolas", Font.BOLD, 14);

        scrollbarThumb = new Color(255, 20, 147);
        scrollbarTrack = new Color(50, 0, 50);
    }


    public void setDark() {
        background = new Color(43, 43, 43);
        panelBackground = new Color(33, 33, 33);
        editorBackground = new Color(43, 43, 43);
        editorForeground = new Color(187, 187, 187);
        tapeBackground = new Color(43, 43, 43);
        selectedTapeBackground = new Color(50, 50, 50);
        selectedTapeBorderColor = new Color(0, 200, 0);

        toolbarBackground = new Color(33, 33, 33);
        buttonBackground = new Color(60, 63, 65);
        buttonForeground = new Color(187, 187, 187);
        buttonBorderColor = new Color(77, 77, 77);

        tapeBorderColor = new Color(90, 90, 90); // <- NEW
        tapeTextColor = new Color(220, 220, 220); // <- NEW
        selectedCellBackground = new Color(100, 255, 100); // <- NEW
        selectedCellForeground = Color.BLACK; // <- NEW
        currentStateTextColor = new Color(0, 255, 0); // <- NEW

        cellBorderColor = new Color(120, 120, 120);
        editorFont = new Font("Consolas", Font.PLAIN, 14);

        scrollbarThumb = new Color(90, 90, 90);
        scrollbarTrack = new Color(45, 45, 45);
    }



    public void setSolarizedDark() {
        background = new Color(0x002B36);
        panelBackground = new Color(0x073642);
        editorBackground = new Color(0x002B36);
        editorForeground = new Color(0x839496);
        tapeBackground = new Color(0x002B36);
        selectedTapeBackground = new Color(0x073642);
        selectedTapeBorderColor = new Color(0x586E75);

        toolbarBackground = new Color(0x073642);
        buttonBackground = new Color(0x586E75);
        buttonForeground = new Color(0xFDF6E3);
        buttonBorderColor = new Color(0x586E75);

        tapeBorderColor = new Color(0x586E75); // <- NEW
        tapeTextColor = new Color(0x839496); // <- NEW
        selectedCellBackground = new Color(0x586E75); // <- NEW
        selectedCellForeground = new Color(0xFDF6E3); // <- NEW
        currentStateTextColor = new Color(0x839496); // <- NEW

        cellBorderColor = new Color(0x586E75);
        editorFont = new Font("Consolas", Font.PLAIN, 14);

        scrollbarThumb = new Color(90, 90, 90);
        scrollbarTrack = new Color(45, 45, 45);
    }

    public void setLight() {
        background = new Color(255, 255, 255);
        panelBackground = new Color(245, 245, 245);
        editorBackground = new Color(255, 255, 255);
        editorForeground = Color.BLACK;
        tapeBackground = new Color(255, 255, 255);
        selectedTapeBackground = new Color(230, 230, 230);
        selectedTapeBorderColor = new Color(0, 100, 200);

        toolbarBackground = new Color(245, 245, 245);
        buttonBackground = new Color(250, 250, 250);
        buttonForeground = Color.BLACK;
        buttonBorderColor = new Color(200, 200, 200);

        tapeBorderColor = new Color(180, 180, 180);
        tapeTextColor = Color.BLACK;
        selectedCellBackground = new Color(220, 240, 255);
        selectedCellForeground = Color.BLACK;
        currentStateTextColor = new Color(0, 0, 0);

        cellBorderColor = new Color(180, 180, 180);
        editorFont = new Font("Monospaced", Font.PLAIN, 14);

        scrollbarThumb = new Color(150, 150, 150);
        scrollbarTrack = new Color(220, 220, 220);
    }

    public void setHighContrast() {
        background = Color.BLACK;
        panelBackground = new Color(30, 30, 30);
        editorBackground = Color.BLACK;
        editorForeground = Color.WHITE;
        tapeBackground = Color.BLACK;
        selectedTapeBackground = new Color(50, 50, 50);
        selectedTapeBorderColor = Color.YELLOW;

        toolbarBackground = new Color(30, 30, 30);
        buttonBackground = new Color(60, 60, 60);
        buttonForeground = Color.WHITE;
        buttonBorderColor = Color.GRAY;

        tapeBorderColor = Color.GRAY;
        tapeTextColor = Color.WHITE;
        selectedCellBackground = Color.YELLOW;
        selectedCellForeground = Color.BLACK;
        currentStateTextColor = Color.YELLOW;

        cellBorderColor = Color.GRAY;
        editorFont = new Font("Monospaced", Font.BOLD, 15);

        scrollbarThumb = Color.GRAY;
        scrollbarTrack = new Color(50, 50, 50);
    }

    public void setCozyPink() {
        this.name = "Cozy Pink";
        background = new Color(255, 240, 245); // Light, soft pink
        panelBackground = new Color(250, 235, 240); // Slightly darker soft pink
        editorBackground = new Color(255, 250, 250); // Very light pink/off-white
        editorForeground = new Color(139, 0, 139); // Dark magenta/purple for contrast

        tapeBackground = new Color(255, 250, 250);
        selectedTapeBackground = new Color(255, 228, 225); // Misty Rose
        selectedTapeBorderColor = new Color(219, 112, 147); // Pale Violet Red

        toolbarBackground = new Color(250, 235, 240);
        buttonBackground = new Color(255, 230, 235); // Light pink
        buttonForeground = new Color(171, 0, 171); // Medium magenta/purple
        buttonBorderColor = new Color(219, 112, 147);

        tapeBorderColor = new Color(238, 130, 238); // Violet
        tapeTextColor = new Color(139, 0, 139);
        selectedCellBackground = new Color(255, 182, 193); // Light pink
        selectedCellForeground = Color.WHITE;
        currentStateTextColor = new Color(171, 0, 171);

        cellBorderColor = new Color(238, 130, 238);
        editorFont = new Font("Monospaced", Font.PLAIN, 14);

        scrollbarThumb = new Color(219, 112, 147);
        scrollbarTrack = new Color(250, 235, 240);
    }

    public void setMonochromeDark() {
        background = new Color(30, 30, 30);
        panelBackground = new Color(45, 45, 45);
        editorBackground = new Color(30, 30, 30);
        editorForeground = new Color(200, 200, 200);
        tapeBackground = new Color(30, 30, 30);
        selectedTapeBackground = new Color(60, 60, 60);
        selectedTapeBorderColor = new Color(150, 150, 150);

        toolbarBackground = new Color(45, 45, 45);
        buttonBackground = new Color(70, 70, 70);
        buttonForeground = new Color(200, 200, 200);
        buttonBorderColor = new Color(90, 90, 90);

        tapeBorderColor = new Color(90, 90, 90);
        tapeTextColor = new Color(200, 200, 200);
        selectedCellBackground = new Color(120, 120, 120);
        selectedCellForeground = new Color(200, 200, 200);
        currentStateTextColor = new Color(150, 150, 150);

        cellBorderColor = new Color(90, 90, 90);
        editorFont = new Font("Monospaced", Font.PLAIN, 14);

        scrollbarThumb = new Color(90, 90, 90);
        scrollbarTrack = new Color(60, 60, 60);
    }

    public void setOceanBlue() {
        background = new Color(240, 248, 255); // Alice Blue
        panelBackground = new Color(224, 238, 255); // Light Sky Blue (tinted)
        editorBackground = new Color(255, 255, 255);
        editorForeground = new Color(0, 0, 139); // Dark Blue

        tapeBackground = new Color(255, 255, 255);
        selectedTapeBackground = new Color(173, 216, 230); // Light Blue
        selectedTapeBorderColor = new Color(65, 105, 225); // Royal Blue

        toolbarBackground = new Color(224, 238, 255);
        buttonBackground = new Color(176, 196, 222); // Light Steel Blue
        buttonForeground = new Color(0, 0, 139);
        buttonBorderColor = new Color(70, 130, 180); // Steel Blue

        tapeBorderColor = new Color(70, 130, 180);
        tapeTextColor = new Color(0, 0, 0);
        selectedCellBackground = new Color(135, 206, 235); // Sky Blue
        selectedCellForeground = Color.BLACK;
        currentStateTextColor = new Color(0, 0, 139);

        cellBorderColor = new Color(70, 130, 180);
        editorFont = new Font("Monospaced", Font.PLAIN, 14);

        scrollbarThumb = new Color(100, 149, 237); // Cornflower Blue
        scrollbarTrack = new Color(200, 220, 255);
    }

    public void setForestGreen() {
        background = new Color(34, 49, 34);
        panelBackground = new Color(44, 64, 44);
        editorBackground = new Color(34, 49, 34);
        editorForeground = new Color(180, 255, 180);

        tapeBackground = new Color(44, 64, 44);
        selectedTapeBackground = new Color(60, 90, 60);
        selectedTapeBorderColor = new Color(80, 150, 80);

        toolbarBackground = new Color(44, 64, 44);
        buttonBackground = new Color(60, 90, 60);
        buttonForeground = new Color(180, 255, 180);
        buttonBorderColor = new Color(80, 150, 80);

        tapeBorderColor = new Color(80, 150, 80);
        tapeTextColor = new Color(180, 255, 180);
        selectedCellBackground = new Color(80, 150, 80);
        selectedCellForeground = Color.BLACK;
        currentStateTextColor = new Color(180, 255, 180);

        cellBorderColor = new Color(80, 150, 80);
        editorFont = new Font("Monospaced", Font.PLAIN, 14);

        scrollbarThumb = new Color(80, 150, 80);
        scrollbarTrack = new Color(44, 64, 44);
    }


    public void setRetroTerminal() {
        background = Color.BLACK;
        panelBackground = Color.BLACK;
        editorBackground = Color.BLACK;
        editorForeground = new Color(0, 255, 0); // Bright green

        tapeBackground = Color.BLACK;
        selectedTapeBackground = new Color(10, 10, 10);
        selectedTapeBorderColor = new Color(0, 255, 0);

        toolbarBackground = Color.BLACK;
        buttonBackground = Color.BLACK;
        buttonForeground = new Color(0, 255, 0);
        buttonBorderColor = new Color(0, 255, 0);

        tapeBorderColor = new Color(0, 255, 0);
        tapeTextColor = new Color(0, 255, 0);
        selectedCellBackground = new Color(0, 255, 0);
        selectedCellForeground = Color.BLACK;
        currentStateTextColor = new Color(0, 255, 0);

        cellBorderColor = new Color(0, 255, 0);
        editorFont = new Font("Courier New", Font.PLAIN, 14);

        scrollbarThumb = new Color(0, 255, 0);
        scrollbarTrack = Color.BLACK;
    }

    public void setBloodyRed() {
        background = new Color(20, 0, 0);         // Very dark reddish black
        panelBackground = new Color(30, 0, 0);    // Slightly lighter dark red
        editorBackground = new Color(40, 0, 0);   // Deep dark red
        editorForeground = new Color(255, 50, 50); // Bright blood red text

        tapeBackground = new Color(30, 0, 0);
        selectedTapeBackground = new Color(70, 0, 0);
        selectedTapeBorderColor = new Color(255, 0, 0); // Bright red border

        toolbarBackground = new Color(30, 0, 0);
        buttonBackground = new Color(60, 0, 0);
        buttonForeground = new Color(255, 80, 80);
        buttonBorderColor = new Color(255, 0, 0);

        tapeBorderColor = new Color(180, 0, 0);
        tapeTextColor = new Color(255, 80, 80);
        selectedCellBackground = new Color(255, 0, 0);
        selectedCellForeground = Color.WHITE;
        currentStateTextColor = new Color(255, 80, 80);

        cellBorderColor = new Color(180, 0, 0);
        editorFont = new Font("Consolas", Font.BOLD, 14);

        scrollbarThumb = new Color(255, 0, 0);
        scrollbarTrack = new Color(30, 0, 0);
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

        this.editorFont = other.editorFont; // optionally clone font if needed
    }

    public void setGoldenSunset() {
        background = new Color(25, 20, 10); // Deep brown-black
        panelBackground = new Color(40, 30, 15);
        editorBackground = new Color(50, 35, 20);
        editorForeground = new Color(255, 215, 0); // Golden text

        tapeBackground = new Color(60, 40, 25);
        selectedTapeBackground = new Color(100, 80, 20);
        selectedTapeBorderColor = new Color(255, 215, 0); // Gold border

        toolbarBackground = new Color(45, 30, 15);
        buttonBackground = new Color(70, 50, 25);
        buttonForeground = new Color(255, 223, 90); // Soft gold
        buttonBorderColor = new Color(255, 200, 0);

        tapeBorderColor = new Color(160, 120, 40);
        tapeTextColor = new Color(255, 215, 0); // Golden tape text
        selectedCellBackground = new Color(255, 215, 0); // Highlight gold
        selectedCellForeground = new Color(20, 10, 5); // Dark text inside gold

        currentStateTextColor = new Color(255, 230, 120); // Light gold

        cellBorderColor = new Color(255, 200, 0);
        editorFont = new Font("JetBrains Mono", Font.BOLD, 14);

        scrollbarThumb = new Color(255, 200, 0);
        scrollbarTrack = new Color(50, 30, 10);
    }


}

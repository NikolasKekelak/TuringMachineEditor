package Command;

import Command.Editor.EditorTheme;

import java.awt.*;
import java.io.*;
import java.util.*;

public class ThemeManager {
    private static final String THEMES_DIR = "themes";

    private final Map<String, EditorTheme> themes = new LinkedHashMap<>();

    public ThemeManager() {
        loadBuiltInThemes();
        loadUserThemes();
    }

    private void loadBuiltInThemes() {
        EditorTheme def = new EditorTheme(); def.setDefault(); themes.put("Default", def);
        EditorTheme dark = new EditorTheme(); dark.setDark(); themes.put("Dark", dark);
        EditorTheme solar = new EditorTheme(); solar.setSolarizedDark(); themes.put("Solarized Dark", solar);
        EditorTheme toxic = new EditorTheme(); toxic.setToxicPink(); themes.put("Toxic Pink", toxic);
        EditorTheme light = new EditorTheme(); light.setLight(); themes.put("Light", light);
        EditorTheme contrast = new EditorTheme(); contrast.setHighContrast(); themes.put("High Contrast", contrast);
        EditorTheme cozy = new EditorTheme(); cozy.setCozyPink(); themes.put("Cozy Pink", cozy);
        EditorTheme mono = new EditorTheme(); mono.setMonochromeDark(); themes.put("Monochrome Dark", mono);
        EditorTheme ocean = new EditorTheme(); ocean.setOceanBlue(); themes.put("Ocean Blue", ocean);
        EditorTheme forest = new EditorTheme(); forest.setForestGreen(); themes.put("Forest Green", forest);
        EditorTheme retro = new EditorTheme(); retro.setRetroTerminal(); themes.put("Retro Terminal", retro);
        EditorTheme bloody = new EditorTheme(); bloody.setBloodyRed(); themes.put("Bloody Red", bloody);
        EditorTheme golden = new EditorTheme(); golden.setGoldenSunset(); themes.put("Golden Sunset", golden);

    }

    private void loadUserThemes() {
        File dir = new File(THEMES_DIR);
        if (!dir.exists()) dir.mkdirs();

        for (File file : Objects.requireNonNull(dir.listFiles((d, name) -> name.endsWith(".json")))) {
            try {
                EditorTheme theme = parseJsonTheme(file);
                if (theme != null) {
                    themes.put(file.getName().replace(".json", ""), theme);
                }
            } catch (Exception e) {
                System.err.println("Failed to load theme from " + file.getName() + ": " + e.getMessage());
            }
        }
    }

    private EditorTheme parseJsonTheme(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        EditorTheme theme = new EditorTheme();
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.startsWith("\"")) {
                String[] parts = line.replace("\"", "").split(":");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String val = parts[1].trim().replace(",", "").replace("\"", "");
                    applyField(theme, key, val);
                }
            }
        }
        return theme;
    }

    private void applyField(EditorTheme theme, String key, String value) {
        try {
            Color color = Color.decode(value);
            switch (key) {
                case "background" -> theme.background = color;
                case "panelBackground" -> theme.panelBackground = color;
                case "editorBackground" -> theme.editorBackground = color;
                case "editorForeground" -> theme.editorForeground = color;
                case "toolbarBackground" -> theme.toolbarBackground = color;
                case "buttonBackground" -> theme.buttonBackground = color;
                case "buttonForeground" -> theme.buttonForeground = color;
                case "buttonBorderColor" -> theme.buttonBorderColor = color;
                case "selectedTapeBackground" -> theme.selectedTapeBackground = color;
                case "selectedTapeBorderColor" -> theme.selectedTapeBorderColor = color;
                case "tapeBackground" -> theme.tapeBackground = color;
                case "tapeBorderColor" -> theme.tapeBorderColor = color;
                case "tapeTextColor" -> theme.tapeTextColor = color;
                case "selectedCellBackground" -> theme.selectedCellBackground = color;
                case "selectedCellForeground" -> theme.selectedCellForeground = color;
                case "currentStateTextColor" -> theme.currentStateTextColor = color;
                case "cellBorderColor" -> theme.cellBorderColor = color;
                case "scrollbarThumb" -> theme.scrollbarThumb = color;
                case "scrollbarTrack" -> theme.scrollbarTrack = color;
            }
        } catch (Exception ignored) {
        }
    }

    public void saveTheme(EditorTheme theme, String name) {
        try (PrintWriter out = new PrintWriter(new FileWriter(THEMES_DIR + "/" + name + ".json"))) {
            out.println("{");
            out.println("  \"background\": \"" + toHex(theme.background) + "\",");
            out.println("  \"panelBackground\": \"" + toHex(theme.panelBackground) + "\",");
            out.println("  \"editorBackground\": \"" + toHex(theme.editorBackground) + "\",");
            out.println("  \"editorForeground\": \"" + toHex(theme.editorForeground) + "\",");
            out.println("  \"toolbarBackground\": \"" + toHex(theme.toolbarBackground) + "\",");
            out.println("  \"buttonBackground\": \"" + toHex(theme.buttonBackground) + "\",");
            out.println("  \"buttonForeground\": \"" + toHex(theme.buttonForeground) + "\",");
            out.println("  \"buttonBorderColor\": \"" + toHex(theme.buttonBorderColor) + "\",");
            out.println("  \"selectedTapeBackground\": \"" + toHex(theme.selectedTapeBackground) + "\",");
            out.println("  \"selectedTapeBorderColor\": \"" + toHex(theme.selectedTapeBorderColor) + "\",");
            out.println("  \"tapeBackground\": \"" + toHex(theme.tapeBackground) + "\",");
            out.println("  \"tapeBorderColor\": \"" + toHex(theme.tapeBorderColor) + "\",");
            out.println("  \"tapeTextColor\": \"" + toHex(theme.tapeTextColor) + "\",");
            out.println("  \"selectedCellBackground\": \"" + toHex(theme.selectedCellBackground) + "\",");
            out.println("  \"selectedCellForeground\": \"" + toHex(theme.selectedCellForeground) + "\",");
            out.println("  \"currentStateTextColor\": \"" + toHex(theme.currentStateTextColor) + "\",");
            out.println("  \"cellBorderColor\": \"" + toHex(theme.cellBorderColor) + "\",");
            out.println("  \"scrollbarThumb\": \"" + toHex(theme.scrollbarThumb) + "\",");
            out.println("  \"scrollbarTrack\": \"" + toHex(theme.scrollbarTrack) + "\"");
            out.println("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String toHex(Color c) {
        return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
    }

    public Set<String> getThemeNames() {
        return themes.keySet();
    }

    public EditorTheme getTheme(String name) {
        return themes.get(name);
    }
}

package Editor.Editor;

import java.io.*;
import java.util.Properties;

public class EditorSessionManager {
    private static final String SESSION_FILE = "editor_session.properties";

    private String lastEditorText = "";
    private String lastTheme = "Default";

    public void loadSession() {
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(SESSION_FILE)) {
            props.load(in);
            lastEditorText = props.getProperty("editorText", "");
            lastTheme = props.getProperty("theme", "Default");
        } catch (IOException e) {
            System.out.println("No previous session found.");
        }
    }

    public void saveSession(String editorText, String themeName) {
        Properties props = new Properties();
        props.setProperty("editorText", editorText);
        props.setProperty("theme", themeName);

        try (FileOutputStream out = new FileOutputStream(SESSION_FILE)) {
            props.store(out, "Editor Session Data");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLastEditorText() {
        return lastEditorText;
    }

    public String getLastTheme() {
        return lastTheme;
    }
}

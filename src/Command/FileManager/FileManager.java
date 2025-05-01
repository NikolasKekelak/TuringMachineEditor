package Command.FileManager;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private List<File> recentFiles = new ArrayList<>();

    public void saveToFile(JFrame parent, JTextArea editor) { /*...*/ }
    public void loadFromFile(JFrame parent, JTextArea editor) { /*...*/ }
    public void addRecentFile(File file) { /*...*/ }
    public List<File> getRecentFiles() { return recentFiles; }
    public void loadRecentFiles(){};  // load from disk
    public void saveRecentFiles(){};  // save to disk
}

package Editor.Editor;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.io.*;

public class EditorFileManager {

    public static void saveToFile(JFrame parent, JTextPane editorArea) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save As");

        if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(editorArea.getText());
                JOptionPane.showMessageDialog(parent, "Saved successfully.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(parent, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void loadFromFile(JFrame parent, JTextPane editorPane) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open File");

        if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                editorPane.setText(null); // Clear the JTextPane
                Document doc = editorPane.getDocument();
                String line;
                while ((line = reader.readLine()) != null) {
                    doc.insertString(doc.getLength(), line + "\n", null); // Use insertString
                }
                JOptionPane.showMessageDialog(parent, "File loaded successfully.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(parent, "Error loading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (BadLocationException e) {
                JOptionPane.showMessageDialog(parent, "Error loading file (BadLocation): " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

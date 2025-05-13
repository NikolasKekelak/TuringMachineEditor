import Editor.TuringEditorUI;

import javax.swing.*;


class Main{
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new TuringEditorUI(
                "turingmachine"
        ));
    }
}
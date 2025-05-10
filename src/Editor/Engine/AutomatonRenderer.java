package Editor.Engine;

import Editor.Editor.EditorTheme;

import javax.swing.*;

public interface AutomatonRenderer {
    void updateTapePanel(JPanel tapePanel, AutomatonEngine machine, EditorTheme theme);
}

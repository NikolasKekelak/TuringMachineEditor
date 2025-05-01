package Command.Engine;

import Command.Editor.EditorTheme;
import Command.TuringMachine.TuringMachine;

import javax.swing.*;

public interface AutomatonRenderer {
    void updateTapePanel(JPanel tapePanel, AutomatonEngine machine, EditorTheme theme);
}

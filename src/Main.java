import javax.swing.*;

import Command.TuringMachine.TapePanelRender.DefaultTapePanelUpdater;
import Command.TuringEditorUI;
import Command.TuringMachine.TapePanelRender.FancyTapeRenderer;
import Command.TuringMachine.TuringMachine;
import Command.TuringMachine.TuringMachineHighlighter;

class Main{
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new TuringEditorUI(
                "TuringMachine",
                new TuringMachine(0),
                new FancyTapeRenderer(),
                new TuringMachineHighlighter()
        ));
    }
}
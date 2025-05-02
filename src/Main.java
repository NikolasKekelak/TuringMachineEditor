import javax.swing.*;

import Command.Abacus.AbacusMachine;
import Command.Abacus.AbacusRender;
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
                "turingmachine"

        ));
    }
}
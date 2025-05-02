package Command.Abacus;

import Command.Editor.EditorTheme;
import Command.Engine.AutomatonEngine;
import Command.Engine.AutomatonRenderer;

import javax.swing.*;
import java.util.Map;

public class AbacusRender implements AutomatonRenderer {

    @Override
    public void updateTapePanel(JPanel tapePanel, AutomatonEngine engine, EditorTheme theme) {
        if (!(engine instanceof AbacusMachine abacus)) return;

        tapePanel.removeAll();
        tapePanel.setLayout(new BoxLayout(tapePanel, BoxLayout.Y_AXIS));

        Map<Integer, Integer> registers = abacus.getRegisters();

        for (int reg : registers.keySet().stream().sorted().toList()) {
            int value = registers.get(reg);

            JPanel row = new JPanel();
            row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));

            JLabel label = new JLabel("Register " + reg + ": " + value + "  ");
            row.add(label);

            JLabel upperBead = new JLabel(value == 0 ? "⬆️" : "⬇️");
            row.add(upperBead);

            row.add(new JLabel("  ")); // spacing

            for (int i = 0; i < value; i++) {
                JLabel bead = new JLabel("🟠");  // simple bead
                row.add(bead);
            }

            tapePanel.add(row);
        }

        tapePanel.revalidate();
        tapePanel.repaint();
    }

}

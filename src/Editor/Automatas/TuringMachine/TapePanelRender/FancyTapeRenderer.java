package Editor.Automatas.TuringMachine.TapePanelRender;

import Editor.Editor.EditorTheme;
import Editor.Engine.AutomatonEngine;
import Editor.Engine.AutomatonRenderer;
import Editor.Automatas.TuringMachine.Tape;
import Editor.Automatas.TuringMachine.TuringMachine;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class FancyTapeRenderer implements AutomatonRenderer {

    private final Map<String, JScrollPane> tapeScrollPanes = new HashMap<>();
    private final Map<String, JPanel> tapeRows = new HashMap<>();
    private JLabel overlayLabel = null;
    private JLayeredPane layeredPane; // Add this field

    @Override
    public void updateTapePanel(JPanel tapePanel, AutomatonEngine engine, EditorTheme theme) {
        if (!(engine instanceof TuringMachine tm)) return;

        // Store the JLayeredPane
        Component c = tapePanel;
        while (c != null && !(c instanceof JLayeredPane)) {
            c = c.getParent();
        }
        if (c instanceof JLayeredPane) {
            this.layeredPane = (JLayeredPane) c;
        }

        tapePanel.setLayout(new BoxLayout(tapePanel, BoxLayout.Y_AXIS));
        tapePanel.removeAll();

        JLabel stateLabel = new JLabel("Current State: " + tm.getCurrentState());
        stateLabel.setFont(theme.editorFont);
        stateLabel.setForeground(theme.currentStateTextColor);
        stateLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        stateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        tapePanel.add(stateLabel);

        String currentTapeName = tm.getCurrentTapeName();

        for (Map.Entry<String, Tape> entry : tm.getTapes().entrySet()) {
            String tapeName = entry.getKey();
            Tape tape = entry.getValue();

            JLabel tapeTitle = new JLabel("Tape: " + tapeName);
            tapeTitle.setFont(theme.editorFont);
            tapeTitle.setForeground(theme.tapeTextColor);
            tapeTitle.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0));
            tapeTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
            tapePanel.add(tapeTitle);

            JPanel tapeRow = tapeRows.computeIfAbsent(tapeName, k -> new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5)));
            tapeRow.removeAll();

            if (tapeName.equals(currentTapeName)) {
                tapeRow.setBackground(theme.selectedTapeBackground);
                tapeRow.setBorder(BorderFactory.createLineBorder(theme.selectedTapeBorderColor, 3));
            } else {
                tapeRow.setBackground(theme.tapeBackground);
                tapeRow.setBorder(BorderFactory.createLineBorder(theme.tapeBorderColor));
            }

            int center = tape.getHeadPosition();
            int start = Math.max(center - 200, 0);
            int end = Math.min(center + 200, tape.length());

            for (int i = start; i < end; i++) {
                JLabel cell = new JLabel(tape.getAt(i), SwingConstants.CENTER);
                cell.setPreferredSize(new Dimension(50, 30));
                cell.setFont(theme.editorFont);
                cell.setBorder(BorderFactory.createLineBorder(theme.cellBorderColor));
                if (i == center) {
                    cell.setBackground(theme.selectedCellBackground);
                    cell.setForeground(theme.selectedCellForeground);
                } else {
                    cell.setBackground(theme.tapeBackground);
                    cell.setForeground(theme.tapeTextColor);
                }
                cell.setOpaque(true);
                tapeRow.add(cell);
            }

            JScrollPane tapeScroll = tapeScrollPanes.computeIfAbsent(tapeName, k -> {
                JScrollPane scrollPane = new JScrollPane(tapeRow,
                        JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scrollPane.setPreferredSize(new Dimension(800, 60));
                scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
                scrollPane.setBorder(null);
                scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
                return scrollPane;
            });

            tapePanel.add(tapeScroll);
            tapePanel.add(Box.createRigidArea(new Dimension(0, 20)));

            SwingUtilities.invokeLater(() -> {
                SwingUtilities.invokeLater(() -> {
                    if (tapeRow.getComponentCount() > 0) {
                        int centerCellIndex = center - start;
                        if (centerCellIndex >= 0 && centerCellIndex < tapeRow.getComponentCount()) {
                            Component centerCell = tapeRow.getComponent(centerCellIndex);
                            int cellX = centerCell.getX();
                            int viewWidth = tapeScroll.getViewport().getWidth();
                            int scrollTo = Math.max(0, cellX - viewWidth / 2 + centerCell.getWidth() / 2);
                            tapeScroll.getHorizontalScrollBar().setValue(scrollTo);
                        }
                    }
                });
            });
        }

        // 🎨 Add theme-specific floating overlay if available
        if (layeredPane != null) { // Use the stored layeredPane
            if (overlayLabel != null) {
                layeredPane.remove(overlayLabel);
            }

            switch (theme.name.toLowerCase()) {
                case "cozy pink":
                    overlayLabel = new JLabel(new ImageIcon(getClass().getResource("/assets/cat.png")));
                    break;
                case "chill blue":
                    overlayLabel = new JLabel(new ImageIcon(getClass().getResource("/assets/fish.png")));
                    break;
                case "forest green":
                    overlayLabel = new JLabel(new ImageIcon(getClass().getResource("/assets/tree.png")));
                    break;
                default:
                    overlayLabel = null;
            }

            if (overlayLabel != null) {
                //  Define the position here, in the bottom right corner of tapePanel.
                int x = layeredPane.getWidth() - overlayLabel.getPreferredSize().width +300;
                int y = layeredPane.getHeight() - overlayLabel.getPreferredSize().height +300;
                overlayLabel.setBounds(x, y, overlayLabel.getPreferredSize().width, overlayLabel.getPreferredSize().height);
                layeredPane.add(overlayLabel, JLayeredPane.DEFAULT_LAYER);
                layeredPane.revalidate();
                layeredPane.repaint();
            }
        }

        tapePanel.revalidate();
        tapePanel.repaint();
    }
}


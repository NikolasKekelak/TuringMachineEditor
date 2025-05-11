package Editor.Editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomTitleBar extends JPanel {
    private Point initialClick;

    public CustomTitleBar(JFrame frame, EditorTheme theme, String titleText) {
        setLayout(new BorderLayout());
        setBackground(new Color(43, 43, 43)); // IntelliJ-like gray

        JLabel title = new JLabel("  " + titleText);
        title.setForeground(Color.LIGHT_GRAY);
        title.setFont(new Font("Dialog", Font.BOLD, 13));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 2));
        buttons.setOpaque(false);
        buttons.add(createMinimizeButton(frame));
        buttons.add(createMaximizeRestoreButton(frame));
        buttons.add(createCloseButton(frame));

        add(title, BorderLayout.WEST);
        add(buttons, BorderLayout.EAST);

        // Drag behavior
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                int thisX = frame.getLocation().x;
                int thisY = frame.getLocation().y;
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;
                frame.setLocation(thisX + xMoved, thisY + yMoved);
            }
        });

        setPreferredSize(new Dimension(frame.getWidth(), 32));
    }

    private JButton createMinimizeButton(JFrame frame) {
        return createIconButton(g -> {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(10, 18, 12, 2);
        }, () -> frame.setState(JFrame.ICONIFIED));
    }

    private JButton createMaximizeRestoreButton(JFrame frame) {
        return createIconButton(g -> {
            g.setColor(Color.LIGHT_GRAY);
            g.drawRect(9, 9, 14, 14);
        }, () -> {
            if (frame.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                frame.setExtendedState(JFrame.NORMAL);
            } else {
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });
    }

    private JButton createCloseButton(JFrame frame) {
        return createIconButton(g -> {
            g.setColor(Color.LIGHT_GRAY);
            g.drawLine(10, 10, 20, 20);
            g.drawLine(10, 20, 20, 10);
        }, frame::dispose);
    }

    private JButton createIconButton(PaintIcon painter, Runnable action) {
        final int[] alpha = {0};
        Timer fadeInTimer = new Timer(15, null);
        Timer fadeOutTimer = new Timer(15, null);

        JButton button = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                if (alpha[0] > 0) {
                    g2d.setColor(new Color(255, 255, 255, alpha[0]));
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
                g2d.dispose();
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                painter.paint(g2);
            }
        };

        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(34, 30));

        fadeInTimer.addActionListener(e -> {
            alpha[0] = Math.min(alpha[0] + 10, 60);
            button.repaint();
            if (alpha[0] >= 60) fadeInTimer.stop();
        });

        fadeOutTimer.addActionListener(e -> {
            alpha[0] = Math.max(alpha[0] - 10, 0);
            button.repaint();
            if (alpha[0] <= 0) fadeOutTimer.stop();
        });

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                fadeOutTimer.stop();
                fadeInTimer.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                fadeInTimer.stop();
                fadeOutTimer.start();
            }
        });

        button.addActionListener(e -> action.run());

        return button;
    }




    @FunctionalInterface
    private interface PaintIcon {
        void paint(Graphics2D g);
    }
}

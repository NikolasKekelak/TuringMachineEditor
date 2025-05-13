package Editor.Editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowEvent;

public class CustomTitleBar extends JPanel {
    private Point initialClick;
    private boolean isMaximized = false;
    private Rectangle savedBounds;

    public CustomTitleBar(JFrame frame, EditorTheme theme, String titleText) {
        setLayout(new BorderLayout());
        setBackground(new Color(43, 43, 43)); // Darker title bar
        setPreferredSize(new Dimension(frame.getWidth(), 48)); // Thicker

        JLabel title = new JLabel("  " + titleText);
        title.setForeground(Color.LIGHT_GRAY);
        title.setFont(new Font("Dialog", Font.BOLD, 16));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 4));
        buttons.setOpaque(false);

        buttons.add(createWindowButton("\u2013", frame, theme, false, () -> frame.setState(JFrame.ICONIFIED)));
        buttons.add(createWindowButton("\u25A1", frame, theme, false, () -> {
            if (isMaximized) {
                frame.setBounds(savedBounds);
                isMaximized = false;
            } else {
                savedBounds = frame.getBounds();
                Rectangle screenBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
                frame.setBounds(screenBounds);
                isMaximized = true;
            }
        }));
        buttons.add(createWindowButton("\u2715", frame, theme, true, () ->
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING))));

        add(title, BorderLayout.WEST);
        add(buttons, BorderLayout.EAST);

        // Drag functionality
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (!isMaximized) {
                    int xMoved = e.getX() - initialClick.x;
                    int yMoved = e.getY() - initialClick.y;
                    frame.setLocation(frame.getX() + xMoved, frame.getY() + yMoved);
                }
            }
        });
    }

    private JButton createWindowButton(String symbol, JFrame frame, EditorTheme theme, boolean isClose, Runnable onClick) {
        JButton button = new JButton(symbol);
        button.setFont(new Font("Dialog", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(50, 40));
        button.setForeground(Color.LIGHT_GRAY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);

        Color baseColor = new Color(43, 43, 43);
        Color hoverColor = isClose ? new Color(180, 50, 50) : new Color(70, 70, 70);

        Timer hoverTimer = new Timer(15, null);
        button.addMouseListener(new MouseAdapter() {
            boolean hovering = false;
            float blend = 0f;
            {
                hoverTimer.addActionListener(e -> {
                    if (hovering && blend < 1f) blend += 0.1f;
                    else if (!hovering && blend > 0f) blend -= 0.1f;

                    Color blendColor = blendColors(baseColor, hoverColor, blend);
                    button.setBackground(blendColor);
                    button.setOpaque(blend > 0);
                    button.repaint();

                    if (blend <= 0f || blend >= 1f) hoverTimer.stop();
                });
            }

            public void mouseEntered(MouseEvent e) {
                hovering = true;
                hoverTimer.start();
            }

            public void mouseExited(MouseEvent e) {
                hovering = false;
                hoverTimer.start();
            }
        });

        button.addActionListener(e -> onClick.run());
        return button;
    }

    private Color blendColors(Color base, Color target, float ratio) {
        int r = (int) (base.getRed() * (1 - ratio) + target.getRed() * ratio);
        int g = (int) (base.getGreen() * (1 - ratio) + target.getGreen() * ratio);
        int b = (int) (base.getBlue() * (1 - ratio) + target.getBlue() * ratio);
        return new Color(r, g, b);
    }
}

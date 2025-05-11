package Editor.Editor;

import Editor.Editor.EditorTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomTitleBar extends JPanel {
    private Point initialClick;

    public CustomTitleBar(JFrame frame, EditorTheme theme, String titleText) {
        setLayout(new BorderLayout());
        setBackground(new Color(60, 63, 65)); // IntelliJ-like grey
        setPreferredSize(new Dimension(frame.getWidth(), 30));

        JLabel title = new JLabel("  " + titleText);
        title.setForeground(Color.WHITE); // Title text in white
        title.setFont(new Font("Dialog", Font.BOLD, 14));

        JButton minimizeBtn = createButton("—");
        minimizeBtn.addActionListener(e -> frame.setState(JFrame.ICONIFIED));

        JButton closeBtn = createButton("✕");
        closeBtn.addActionListener(e -> System.exit(0));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttons.setOpaque(false);
        buttons.add(minimizeBtn);
        buttons.add(closeBtn);

        add(title, BorderLayout.WEST);
        add(buttons, BorderLayout.EAST);

        // Enable window dragging
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
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setForeground(Color.LIGHT_GRAY);
        button.setFont(new Font("Dialog", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(45, 28));
        return button;
    }
}

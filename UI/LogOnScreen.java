import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LogOnScreen extends JPanel {

    private JTextField username;
    private Game game;

    public LogOnScreen() {
        setLookAndFeel();
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.ipady = 5;
        JLabel welcome = new JLabel("2048");
        welcome.setHorizontalAlignment(JLabel.CENTER);

        add(welcome, constraints);

        constraints.ipady = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0.3;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        final JLabel userLbl = new JLabel("Username:");
        add(userLbl, constraints);

        constraints.weightx = 0.8;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        username = new JTextField();
        add(username, constraints);

        constraints.gridwidth = 2;
        constraints.gridy = 3;

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> {
            if (!username.getText().isEmpty()) {
                game.onLogin(username.getText());
            }
        });
        username.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginBtn.doClick();
                }
            }
        });
        add(loginBtn, constraints);
        loginBtn.setFocusable(true);
        loginBtn.requestFocus(true);
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void registerToLogin(Game game) {
        this.game = game;
    }
}

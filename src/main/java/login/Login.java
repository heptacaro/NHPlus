package login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {
    JButton button1;
    JPanel newPanel;
    JLabel userLabel, passLabel;
    final JTextField textField1, textField2;

    Login() {
        userLabel = new JLabel();
        userLabel.setText("Benutzername");

        textField1 = new JTextField(15);

        passLabel = new JLabel();
        passLabel.setText("Passwort");

        textField2 = new JPasswordField(15);

        button1 = new JButton("Einloggen");

        newPanel = new JPanel(new GridLayout(3, 1));
        newPanel.add(userLabel);
        newPanel.add(textField1);
        newPanel.add(passLabel);
        newPanel.add(textField2);
        newPanel.add(button1);

        add(newPanel, BorderLayout.CENTER);

        button1.addActionListener(this);
        setTitle("LOGIN");
    }

    public void actionPerformed(ActionEvent ae) {
        String userValue = textField1.getText();
        String passValue = textField2.getText();

        if (userValue.equals("TestNutzer") && passValue.equals("test")) {
            StartPage start = new StartPage();
            start.setVisible(true);
            JLabel welcome_label = new JLabel("Willkommen " + userValue);
            start.getContentPane().add(welcome_label);
        } else {
            System.out.println("Bitte geben Sie einen validen Benutzernamen und das korrekte Passwort ein.");
        }
    }

}


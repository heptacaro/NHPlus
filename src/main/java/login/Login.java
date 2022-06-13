package login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener
    {
        //initialize button, panel, label, and text field
        JButton button1;
        JPanel newPanel;
        JLabel userLabel, passLabel;
        final JTextField  textField1, textField2;

        //calling constructor
        Login()
        {
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

            //set border to panel
            add(newPanel, BorderLayout.CENTER);

            //perform action on button click
            button1.addActionListener(this);
            setTitle("LOGIN");
        }

        public void actionPerformed(ActionEvent ae)
        {
            String userValue = textField1.getText();
            String passValue = textField2.getText();

            if (userValue.equals("TestNutzer") && passValue.equals("test")) {
                StartPage start = new StartPage();
                start.setVisible(true);
                JLabel welcome_label = new JLabel("Willkommen "+userValue);
                start.getContentPane().add(welcome_label);
            }
            else {
                System.out.println("Bitte geben Sie einen validen Benutzernamen und das korrekte Passwort ein.");
            }
        }

    }


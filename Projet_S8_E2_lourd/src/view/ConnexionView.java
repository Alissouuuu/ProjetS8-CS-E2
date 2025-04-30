package view;


import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import dao.DAOUtilisateur;
import model.Utilisateur;

import java.awt.*;
import java.awt.event.*;

public class ConnexionView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldLogin;
    private JPasswordField passwordField;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ConnexionView frame = new ConnexionView();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ConnexionView() {
        setTitle("Connexion Administrateur");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 815, 510);

        // Custom Panel qui affiche l'image de fond redimensionnée
        contentPane = new BackgroundPanel(new ImageIcon(ConnexionView.class.getResource("/resources/recuperation-sportif.jpg")).getImage());
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null); // On garde layout null pour placer manuellement mais adapté

        JPanel panel = new JPanel();
        panel.setBackground(new Color(220, 230, 240, 180)); // Fond semi-transparent
        panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        panel.setBounds(50, 50, 400, 300);
        panel.setLayout(null);
        contentPane.add(panel);

        JLabel LabelConAdmin = new JLabel("Connexion Administrateur");
        LabelConAdmin.setFont(new Font("Times New Roman", Font.BOLD, 24));
        LabelConAdmin.setBounds(50, 10, 300, 30);
        panel.add(LabelConAdmin);

        JLabel LabelLogin = new JLabel("Login :");
        LabelLogin.setFont(new Font("Calibri", Font.PLAIN, 20));
        LabelLogin.setBounds(30, 70, 100, 25);
        panel.add(LabelLogin);

        textFieldLogin = new JTextField();
        textFieldLogin.setBounds(140, 70, 200, 30);
        panel.add(textFieldLogin);
        textFieldLogin.setColumns(10);

        JLabel LabelPassword = new JLabel("Mot de passe :");
        LabelPassword.setFont(new Font("Calibri", Font.PLAIN, 20));
        LabelPassword.setBounds(30, 120, 150, 25);
        panel.add(LabelPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(140, 120, 200, 30);
        panel.add(passwordField);

        JButton ButtonSeConnecter = new JButton("Se connecter");
        ButtonSeConnecter.setFont(new Font("Calibri", Font.PLAIN, 20));
        ButtonSeConnecter.setBounds(100, 200, 200, 40);
        panel.add(ButtonSeConnecter);

        JLabel LabelMsgErreur = new JLabel("");
        LabelMsgErreur.setFont(new Font("Arial", Font.BOLD, 14));
        LabelMsgErreur.setForeground(Color.RED);
        LabelMsgErreur.setBounds(20, 250, 360, 25);
        panel.add(LabelMsgErreur);

        ButtonSeConnecter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String email = textFieldLogin.getText();
            	String password = new String(passwordField.getPassword());

            	Utilisateur user = DAOUtilisateur.connexion(email, password);
            	if (user != null) {
            		if(user.getRole()==1) {
	            	    dispose();
	            	    new AdminDashboardView(user.getNomComplet()).setVisible(true);
            		}else {
            			LabelMsgErreur.setText("Identifiants incorrects");
   
            		}
            	} else {
            	    LabelMsgErreur.setText("Identifiants incorrects");
            	}

            }
        });
    }

    // Classe interne pour gérer l'image de fond qui s'adapte
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(Image image) {
            this.backgroundImage = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

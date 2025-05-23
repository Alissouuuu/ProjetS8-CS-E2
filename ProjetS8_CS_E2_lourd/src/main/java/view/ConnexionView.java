package view;

import java.awt.EventQueue;
import utils.NavigationHelper;
import utils.Session;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.mindrot.jbcrypt.BCrypt;

import dao.DAOUtilisateur;
import dao.LogConnexionDAO;
import model.Utilisateur;

import java.awt.*;
import java.awt.event.*;

public class ConnexionView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldLogin;
    private JPasswordField passwordField;

    public static void main(String[] args) {
    	
    	
    	BCrypt b=new BCrypt();
    	
    	
        EventQueue.invokeLater(() -> {
            try {
                ConnexionView frame = new ConnexionView();
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ConnexionView() {
        setTitle("Connexion Administrateur");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 815, 510);

        contentPane = new BackgroundPanel(new ImageIcon(ConnexionView.class.getResource("/resources/recuperation-sportif.jpg")).getImage());
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(220, 230, 240, 180));
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

        //  Listener modifié
        ButtonSeConnecter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = textFieldLogin.getText();
                String password = new String(passwordField.getPassword());

                Utilisateur user = DAOUtilisateur.connexion(email, password);
                System.out.println("🔍 Résultat de DAOUtilisateur.connexion : " + (user != null ? "OK" : "null"));

                int userId = (user != null) ? user.getId() : DAOUtilisateur.getUserIdByEmail(email);
                System.out.println("userId récupéré : " + userId);

                boolean success = (user != null && user.getRole() == 1);
                System.out.println("Connexion réussie ? " + success);

                LogConnexionDAO.enregistrerConnexion(userId, success, ConnexionView.this);

                if (success) {
                    Session.setUtilisateur(user);
                    System.out.println(" Connexion réussie, ouverture du dashboard...");
                    dispose();
                    NavigationHelper.afficherFenetre(ConnexionView.this, new AdminDashboardView());
                } else {
                    LabelMsgErreur.setText("Identifiants incorrects");
                    System.out.println("Connexion échouée. Message affiché à l'utilisateur.");
                }
            }
        });
    }

    // Classe interne pour gérer l'image de fond
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

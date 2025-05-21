package view;

import dao.DAOUtilisateur;

import utils.Session;
import utils.NavigationHelper;
import model.Utilisateur;
import org.mindrot.jbcrypt.BCrypt;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import dao.LogAdminDAO;
import model.LogAdmin;

public class UserCreationView extends JFrame {

    private JTextField nomField;
    private JTextField prenomField;
    private JTextField emailField;
    private JComboBox<String> fonctionComboBox;
    private JPasswordField motDePasseField;
    private JComboBox<String> roleComboBox;
    private JButton validerButton;
    private JButton annulerButton;

    public UserCreationView() {
        setTitle("Créer un utilisateur");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/resources/FondCreateUser.png"));
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage.getImage());
        backgroundPanel.setLayout(new GridBagLayout());
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(backgroundPanel);

        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(255, 255, 255, 200));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Champ Nom
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nom :"), gbc);
        gbc.gridx = 1;
        nomField = new JTextField();
        panel.add(nomField, gbc);

        // Champ Prénom
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Prénom :"), gbc);
        gbc.gridx = 1;
        prenomField = new JTextField();
        panel.add(prenomField, gbc);

        // Champ Email
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Email :"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField();
        panel.add(emailField, gbc);

        // Champ Mot de passe
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Mot de passe :"), gbc);
        gbc.gridx = 1;
        motDePasseField = new JPasswordField();
        panel.add(motDePasseField, gbc);

        // Champ Fonction
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Fonction :"), gbc);
        gbc.gridx = 1;

        String[] fonctions = {
        	    "Annimateur", "Assistant", "Bénévole", "Coach", "Maire", "Président de club", "Président de fédération","Superviseur de plateforme", "Trésorier"
        	};
    	fonctionComboBox = new JComboBox<>(fonctions);
    	panel.add(fonctionComboBox, gbc);
    	

        // Champ Rôle
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Rôle :"), gbc);
        gbc.gridx = 1;
        roleComboBox = new JComboBox<String>(new String[] {
            "administrateur", "élus", "acteur du monde sportif"
        });
        panel.add(roleComboBox, gbc);
        
     // Forçage automatique de la fonction si rôle = administrateur
        roleComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedRole = (String) roleComboBox.getSelectedItem();
                if ("administrateur".equalsIgnoreCase(selectedRole)) {
                    fonctionComboBox.setSelectedItem("Superviseur de plateforme");
                    fonctionComboBox.setEnabled(false);
                } else {
                    fonctionComboBox.setEnabled(true);
                }
            }
        });


        // Boutons
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(255, 255, 255, 160));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        buttonPanel.setOpaque(false);
        validerButton = new JButton("Valider");
        annulerButton = new JButton("Annuler");

        buttonPanel.add(validerButton);
        buttonPanel.add(annulerButton);
        panel.add(buttonPanel, gbc);

        backgroundPanel.add(panel);

        // Listeners
        validerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                creerUtilisateur();
            }
        });

        annulerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	NavigationHelper.afficherFenetre(UserCreationView.this, new UserListView());
            }
        });
    }

    private void creerUtilisateur() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String fonction = (String) fonctionComboBox.getSelectedItem();
        String motDePasse = new String(motDePasseField.getPassword());
        String roleStr = (String) roleComboBox.getSelectedItem();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || motDePasse.isEmpty() || fonction.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!estEmailValide(email)) {
            JOptionPane.showMessageDialog(this, "Format d'email invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DAOUtilisateur dao = new DAOUtilisateur();
        if (dao.emailExiste(email)) {
            JOptionPane.showMessageDialog(this, "Cet email est déjà utilisé.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String hashedPassword = BCrypt.hashpw(motDePasse, BCrypt.gensalt());
        int role = DAOUtilisateur.convertirRoleEnInt(roleStr);

        int newId = dao.addUser(nom, prenom, email, hashedPassword, role, fonction);

        // Récupération de l’admin connecté
        Utilisateur admin = Session.getUtilisateur();

        // Construction du log
        String nouvelleValeur = nom + " " + prenom + " - " + email + " - " + fonction;
        LogAdmin log = new LogAdmin(
            0,
            admin,
            "AJOUT",
            "Utilisateur",
            newId != -1 ? newId : 0,
            null,
            nouvelleValeur,
            LogAdminDAO.getIpLocale(),
            java.time.LocalDateTime.now(),
            newId != -1
        );

        LogAdminDAO.enregistrerLog(log, this); //Enregistrement du log

        //  Message utilisateur
        if (newId != -1) {
            JOptionPane.showMessageDialog(this, "Utilisateur créé avec succès (ID : " + newId + ")");
            NavigationHelper.afficherFenetre(UserCreationView.this, new UserListView());
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la création.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }


    private boolean estEmailValide(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserCreationView frame = new UserCreationView();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // plein écran
            frame.setVisible(true);
        });
    }

}
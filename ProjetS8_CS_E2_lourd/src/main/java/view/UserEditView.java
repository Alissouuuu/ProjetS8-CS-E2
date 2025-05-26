package view;

import dao.DAOUtilisateur;

import model.Utilisateur;
import utils.NavigationHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import dao.LogAdminDAO;
import model.LogAdmin;

public class UserEditView extends JFrame {
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField emailField;
    private JComboBox<String> fonctionComboBox;
    private JComboBox<String> roleComboBox;
    private JButton saveButton;
    private JButton cancelButton;

    private Utilisateur utilisateur;

    public UserEditView(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;

        setTitle("Modifier utilisateur");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/resources/FondEditUser.png"));
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
        nomField = new JTextField(utilisateur.getNom());
        panel.add(nomField, gbc);

        // Champ Prénom
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Prénom :"), gbc);

        gbc.gridx = 1;
        prenomField = new JTextField(utilisateur.getPrenom());
        panel.add(prenomField, gbc);

        // Champ Email
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Email :"), gbc);

        gbc.gridx = 1;
        emailField = new JTextField(utilisateur.getEmail());
        panel.add(emailField, gbc);

        // Champ Fonction
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Fonction :"), gbc);

        gbc.gridx = 1;
        
        String[] fonctions = {
        	    "Annimateur", "Assistant", "Bénévole", "Coach", "Maire", "Président de club", "Président de fédération","Superviseur de plateforme", "Trésorier"
        	};
    	fonctionComboBox = new JComboBox<>(fonctions);
    	fonctionComboBox.setSelectedItem(utilisateur.getFonction()); // préselectionne la bonne fonction
    	panel.add(fonctionComboBox, gbc);


        // Champ Rôle
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Rôle :"), gbc);

        gbc.gridx = 1;
        roleComboBox = new JComboBox<String>(new String[] {
            "administrateur", "élus", "acteur du monde sportif"
        });
        roleComboBox.setSelectedItem(DAOUtilisateur.convertirIntEnRole(utilisateur.getRole()));
        panel.add(roleComboBox, gbc);
        
        // Vérification initiale au chargement de la vue
        String selectedRole = (String) roleComboBox.getSelectedItem();
        if ("administrateur".equalsIgnoreCase(selectedRole)) {
            fonctionComboBox.setSelectedItem("Superviseur de plateforme");
            fonctionComboBox.setEnabled(false);
        }

        
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
        saveButton = new JButton("Enregistrer");
        cancelButton = new JButton("Annuler");

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, gbc);

        backgroundPanel.add(panel);

        // Listeners
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveModifications();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	NavigationHelper.afficherFenetre(UserEditView.this, new UserListView());
            }
        });
    }

    private void saveModifications() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String fonction = (String) fonctionComboBox.getSelectedItem();
        String roleStr = (String) roleComboBox.getSelectedItem();
        int role = convertirRoleEnInt(roleStr);

        // Validation email
        if (!estEmailValide(email)) {
            JOptionPane.showMessageDialog(this, "Format d'email invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }


        //  Vérification email en base AVANT modification
        DAOUtilisateur dao = new DAOUtilisateur();
        
        if (!utilisateur.getEmail().equals(email) && dao.emailExiste(email)) {
            JOptionPane.showMessageDialog(this, "Cet email est déjà utilisé par un autre utilisateur.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //  Création de l'objet modifié
        Utilisateur updated = new Utilisateur(
            utilisateur.getId(), nom, prenom, email, role, fonction
        );

        // Mise à jour
        boolean success = dao.updateUser(updated);
        
       


        // 🔍 Enregistrement du log
        Utilisateur admin = utils.Session.getUtilisateur();

        String ancienneValeur = utilisateur.getNomComplet() + " - " + utilisateur.getEmail() + " - " + utilisateur.getFonction();
        String nouvelleValeur = updated.getNomComplet() + " - " + updated.getEmail() + " - " + updated.getFonction();

        model.LogAdmin log = new model.LogAdmin(
            0,
            admin,
            "MODIFICATION",
            "Utilisateur",
            utilisateur.getId(),
            ancienneValeur,
            nouvelleValeur,
            LogAdminDAO.getIpLocale(),
            java.time.LocalDateTime.now(),
            success
        );

        LogAdminDAO.enregistrerLog(log, this);

        // 🧾 Message pour l’admin
        if (success) {
            JOptionPane.showMessageDialog(this, "Utilisateur mis à jour.");
            NavigationHelper.afficherFenetre(UserEditView.this, new UserListView());
        } else {
            JOptionPane.showMessageDialog(this, "Échec de la mise à jour.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean estEmailValide(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    private int convertirRoleEnInt(String role) {
        if (role == null) return 0;
        switch (role.toLowerCase()) {
            case "administrateur": return 1;
            case "élus": return 2;
            case "acteur du monde sportif": return 3;
            default: return 0;
        }
    }

}

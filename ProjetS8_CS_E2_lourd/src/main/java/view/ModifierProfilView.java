package view;

import model.Utilisateur;
import dao.DAOUtilisateur;
import utils.NavigationHelper;

import javax.swing.*;
import java.awt.*;

public class ModifierProfilView extends JFrame {

    private JTextField nomField, prenomField, fonctionField;
    private Utilisateur utilisateur;

    public ModifierProfilView(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;

        setTitle("Modifier mes informations");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Fond dégradé
        GradientPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Titre
        JLabel titre = new JLabel("Modifier mes informations", SwingConstants.CENTER);
        titre.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titre.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        mainPanel.add(titre, BorderLayout.NORTH);

        // Formulaire
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        formPanel.setOpaque(false);

        formPanel.add(new JLabel("Nom :"));
        nomField = new JTextField(utilisateur.getNom());
        formPanel.add(nomField);

        formPanel.add(new JLabel("Prénom :"));
        prenomField = new JTextField(utilisateur.getPrenom());
        formPanel.add(prenomField);

        formPanel.add(new JLabel("Fonction :"));
        fonctionField = new JTextField(utilisateur.getFonction());
        formPanel.add(fonctionField);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Boutons
        JButton saveButton = new JButton("Enregistrer");
        JButton cancelButton = new JButton("Annuler");

        styleButton(saveButton, new Color(33, 150, 243));
        styleButton(cancelButton, new Color(120, 144, 156));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        // Actions
        saveButton.addActionListener(e -> enregistrerModifications());
        cancelButton.addActionListener(e -> {
            dispose();
            NavigationHelper.afficherFenetre(this, new MonProfilView());
        });
    }

    private void enregistrerModifications() {
        utilisateur.setNom(nomField.getText());
        utilisateur.setPrenom(prenomField.getText());
        utilisateur.setFonction(fonctionField.getText());

        DAOUtilisateur dao = new DAOUtilisateur();
        boolean success = dao.updateUser(utilisateur);

        if (success) {
            JOptionPane.showMessageDialog(this, "Modifications enregistrées.");
            dispose();
            NavigationHelper.afficherFenetre(this, new MonProfilView());
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(160, 35));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorder(BorderFactory.createLineBorder(bgColor.darker()));
    }
}

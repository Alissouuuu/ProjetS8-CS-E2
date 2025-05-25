package view;

import model.Utilisateur;
import utils.NavigationHelper;
import utils.Session;
import view.ChangerMotDePasseView;
import view.ModifierProfilView;

import javax.swing.*;
import java.awt.*;

public class MonProfilView extends JFrame {

    public MonProfilView() {
        setTitle("Mon Profil");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}

        Utilisateur user = Session.getUtilisateur();
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Aucun utilisateur connecté.");
            dispose();
            return;
        }

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font valueFont = new Font("Segoe UI", Font.BOLD, 14);

        // Titre
        JLabel titre = new JLabel("Mon Profil", SwingConstants.CENTER);
        titre.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titre.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        // Carte des infos utilisateur
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

        addRow(card, "Nom :", user.getNom(), labelFont, valueFont);
        addRow(card, "Prénom :", user.getPrenom(), labelFont, valueFont);
        addRow(card, "Email :", user.getEmail(), labelFont, valueFont);
        addRow(card, "Fonction :", user.getFonction(), labelFont, valueFont);
        addRow(card, "Rôle :", String.valueOf(user.getRole()), labelFont, valueFont);

        // Boutons
        JButton btnEditInfos = new JButton("Modifier mes infos");
        JButton btnEditMdp = new JButton("Changer mon mot de passe");
        JButton btnRetour = new JButton("Retour");

        styleButton(btnEditInfos, new Color(33, 150, 243));
        styleButton(btnEditMdp, new Color(120, 144, 156));
        styleButton(btnRetour, new Color(220, 53, 69)); // rouge doux

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.setPreferredSize(new Dimension(600, 90));

        JPanel innerButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        innerButtons.setOpaque(false);
        innerButtons.add(btnEditInfos);
        innerButtons.add(btnEditMdp);
        buttonPanel.add(innerButtons);
        
        JPanel retourPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        retourPanel.setOpaque(false);
        retourPanel.add(btnRetour);
        buttonPanel.add(retourPanel);

        // Panel principal avec fond dégradé
        GradientPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(titre, BorderLayout.NORTH);
        mainPanel.add(card, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        // Actions
        btnEditInfos.addActionListener(e ->
                NavigationHelper.afficherFenetre(this, new ModifierProfilView(user))
        );

        btnEditMdp.addActionListener(e ->
                NavigationHelper.afficherFenetre(this, new ChangerMotDePasseView(user))
        );
        
        btnRetour.addActionListener(e -> {
            dispose();
            NavigationHelper.afficherFenetre(this, new AdminDashboardView());
        });
    }

    private void addRow(JPanel container, String label, String value, Font labelFont, Font valueFont) {
        JPanel rowPanel = new JPanel(new BorderLayout());
        rowPanel.setBackground(Color.WHITE);
        rowPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));

        JLabel lbl = new JLabel(label);
        lbl.setFont(labelFont);
        lbl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel val = new JLabel(value);
        val.setFont(valueFont);
        val.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        rowPanel.add(lbl, BorderLayout.WEST);
        rowPanel.add(val, BorderLayout.CENTER);

        container.add(rowPanel);
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(180, 40));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(bgColor.darker()));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MonProfilView().setVisible(true));
    }
}

package view;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboardView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AdminDashboardView frame = new AdminDashboardView("Admin");
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AdminDashboardView(String nomAdmin) {
        setTitle("Dashboard Administrateur");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 550);

        contentPane = new BackgroundPanel(new ImageIcon(AdminDashboardView.class.getResource("/resources/FontAdmin.png")).getImage());
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        // Panel pour le message de bienvenue
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setOpaque(false);
        JLabel lblTitre = new JLabel("Bienvenue sur ton tableau de bord administrateur " + nomAdmin, SwingConstants.CENTER);
        lblTitre.setFont(new Font("Times New Roman", Font.BOLD, 28));
        lblTitre.setForeground(Color.WHITE);
        titlePanel.add(lblTitre);

        // Panel pour le bouton déconnexion (en haut à droite)
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.setOpaque(false);
        JButton btnDeconnexion = new JButton("Déconnexion");
        btnDeconnexion.setFont(new Font("Calibri", Font.PLAIN, 16));
        btnDeconnexion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                ConnexionView.main(null);
            }
        });
        logoutPanel.add(btnDeconnexion);

        // Panel combiné nord avec disposition verticale
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        northPanel.setOpaque(false);
        northPanel.add(logoutPanel);
        northPanel.add(titlePanel);
        
        contentPane.add(northPanel, BorderLayout.NORTH);

        // Centre avec les 3 boutons principaux
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        contentPane.add(centerPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        // Bouton Gérer Utilisateurs
        JButton btnGererUtilisateurs = new JButton("Gérer les utilisateurs");
        btnGererUtilisateurs.setFont(new Font("Calibri", Font.PLAIN, 20));
        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/Gerer_Users.png"));
        Image img = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        btnGererUtilisateurs.setIcon(new ImageIcon(img));
        btnGererUtilisateurs.setHorizontalTextPosition(SwingConstants.CENTER);
        btnGererUtilisateurs.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnGererUtilisateurs.setPreferredSize(new Dimension(220, 130));
        btnGererUtilisateurs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Ouverture de la gestion des utilisateurs");
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(btnGererUtilisateurs, gbc);

        // Bouton Voir Historique
        JButton btnVoirHistorique = new JButton("<html><center>Voir l'historique<br>des connexions</center></html>");
        btnVoirHistorique.setFont(new Font("Calibri", Font.PLAIN, 20));
        ImageIcon iconHistorique = new ImageIcon(getClass().getResource("/resources/Historique.png"));
        Image imgHistorique = iconHistorique.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        btnVoirHistorique.setIcon(new ImageIcon(imgHistorique));
        btnVoirHistorique.setHorizontalTextPosition(SwingConstants.CENTER);
        btnVoirHistorique.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnVoirHistorique.setPreferredSize(new Dimension(220, 130));
        btnVoirHistorique.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Ouverture de l'historique des connexions");
            }
        });
        gbc.gridx = 1;
        centerPanel.add(btnVoirHistorique, gbc);

        // Bouton Recherche de clubs
        JButton btnRechercheClubs = new JButton("<html><center>Rechercher<br>un club</center></html>");
        btnRechercheClubs.setFont(new Font("Calibri", Font.PLAIN, 20));
        btnRechercheClubs.setPreferredSize(new Dimension(220, 130));
        ImageIcon iconRechClubs = new ImageIcon(getClass().getResource("/resources/RechClubs.png"));
        Image imgRechClubs = iconRechClubs.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        btnRechercheClubs.setIcon(new ImageIcon(imgRechClubs));
        btnRechercheClubs.setHorizontalTextPosition(SwingConstants.CENTER);
        btnRechercheClubs.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnRechercheClubs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 dispose();
                 new ClubSearchView().setVisible(true);
            }
        });
        gbc.gridx = 2;
        centerPanel.add(btnRechercheClubs, gbc);
    }
}

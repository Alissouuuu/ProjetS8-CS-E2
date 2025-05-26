package view;

import java.awt.EventQueue;


import utils.NavigationHelper;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import utils.Session;
import model.Utilisateur;


public class AdminDashboardView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AdminDashboardView frame = new AdminDashboardView();
                    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AdminDashboardView() {
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
        Utilisateur user = Session.getUtilisateur();
        if (user == null) {
            System.out.println("Aucun utilisateur dans la session !");
        } else {
            System.out.println("Session récupérée pour : " + user.getNom() + " " + user.getPrenom());
        }

        String nomAdmin = (user != null && user.getNomComplet() != null) ? user.getNomComplet() : "Admin";
        JLabel lblTitre = new JLabel("Bienvenue sur ton tableau de bord administrateur " + nomAdmin, SwingConstants.CENTER);
        lblTitre.setFont(new Font("Calibri", Font.BOLD, 28));
        lblTitre.setForeground(Color.WHITE);
        titlePanel.add(lblTitre);

        // Panel pour le bouton déconnexion (en haut à droite)
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.setOpaque(false);
        JButton btnDeconnexion = new JButton("Déconnexion");
        btnDeconnexion.setFont(new Font("Calibri", Font.PLAIN, 16));
        btnDeconnexion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Session.clear(); //  on vide la session
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
            	NavigationHelper.afficherFenetre(AdminDashboardView.this, new UserListView());
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
            	NavigationHelper.afficherFenetre(AdminDashboardView.this, new HistoriqueConnexionView());

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
            	NavigationHelper.afficherFenetre(AdminDashboardView.this, new ClubSearchView());
                 
            }
            
            
        });
        gbc.gridx = 2;
        centerPanel.add(btnRechercheClubs, gbc);
        

     // Nouveau bouton : Gestion des notifications
        JButton btnNotifications = new JButton("<html><center>Gestion<br>des notifications</center></html>");
        btnNotifications.setFont(new Font("Calibri", Font.PLAIN, 20));
        btnNotifications.setPreferredSize(new Dimension(220, 130));
        ImageIcon iconNotif = new ImageIcon(getClass().getResource("/resources/notification.png")); // ← Mets une icône ici si tu en as une
        Image imgNotif = iconNotif.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        btnNotifications.setIcon(new ImageIcon(imgNotif));
        btnNotifications.setHorizontalTextPosition(SwingConstants.CENTER);
        btnNotifications.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnNotifications.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	NavigationHelper.afficherFenetre(AdminDashboardView.this, new MessageListView());
                
            }
        });
        gbc.gridx = 0;
        gbc.gridy = -1;
        centerPanel.add(btnNotifications, gbc);

        // Nouveau bouton : Validation d'inscription
        JButton btnValidation = new JButton("<html><center>Validation<br>d'inscription</center></html>");
        btnValidation.setFont(new Font("Calibri", Font.PLAIN, 20));
        btnValidation.setPreferredSize(new Dimension(220, 130));
        ImageIcon iconValid = new ImageIcon(getClass().getResource("/resources/Validation.png")); // ← Mets une icône ici aussi
        Image imgValid = iconValid.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        btnValidation.setIcon(new ImageIcon(imgValid));
        btnValidation.setHorizontalTextPosition(SwingConstants.CENTER);
        btnValidation.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnValidation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	NavigationHelper.afficherFenetre(AdminDashboardView.this, new ValidationInscriptionView());
            }
        });
        gbc.gridx = 1;
        centerPanel.add(btnValidation, gbc);

     // Nouveau bouton : Gérer les événements
        JButton btnGererEvenements = new JButton("<html><center>Gérer<br>les événements</center></html>");
        btnGererEvenements.setFont(new Font("Calibri", Font.PLAIN, 20));
        btnGererEvenements.setPreferredSize(new Dimension(220, 130));
        ImageIcon iconEvent = new ImageIcon(getClass().getResource("/resources/Evenements.png")); // ← Mets une icône ici aussi
        Image imgEvent = iconEvent.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        btnGererEvenements.setIcon(new ImageIcon(imgEvent));
        btnGererEvenements.setHorizontalTextPosition(SwingConstants.CENTER);
        btnGererEvenements.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnGererEvenements.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	NavigationHelper.afficherFenetre(AdminDashboardView.this, new EvenementListView());
            }
        });
        gbc.gridx = 2; // Mets une colonne différente si besoin
        centerPanel.add(btnGererEvenements, gbc);

     // Bouton Historique des actions admin
        JButton btnHistoriqueActions = new JButton("<html><center>Historique<br>actions admin</center></html>");
        btnHistoriqueActions.setFont(new Font("Calibri", Font.PLAIN, 20));
        btnHistoriqueActions.setPreferredSize(new Dimension(220, 130));

        // Icône
        ImageIcon iconHistoriqueAdmin = new ImageIcon(getClass().getResource("/resources/HistoriqueAdmin.png"));
        Image imgHistoriqueAdmin = iconHistoriqueAdmin.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        btnHistoriqueActions.setIcon(new ImageIcon(imgHistoriqueAdmin));
        btnHistoriqueActions.setHorizontalTextPosition(SwingConstants.CENTER);
        btnHistoriqueActions.setVerticalTextPosition(SwingConstants.BOTTOM);

        // Action
        btnHistoriqueActions.addActionListener(e -> {
            NavigationHelper.afficherFenetre(AdminDashboardView.this, new HistoriqueAdminView());
        });

        gbc.gridx = 3; // 4ᵉ colonne
        gbc.gridy = 0;
        centerPanel.add(btnHistoriqueActions, gbc);

     // Bouton mon profil
        JButton btnProfil = new JButton("<html><center>Voir<br>mon profil</center></html>");
        btnProfil.setFont(new Font("Calibri", Font.PLAIN, 20));
        btnProfil.setPreferredSize(new Dimension(220, 130));

        // Icône (assure-toi que le fichier existe dans /resources/)
        ImageIcon iconEvenement = new ImageIcon(getClass().getResource("/resources/Profil.png")); // <-- adapte le nom
        Image imgEvenement = iconEvenement.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        btnProfil.setIcon(new ImageIcon(imgEvenement));
        btnProfil.setHorizontalTextPosition(SwingConstants.CENTER);
        btnProfil.setVerticalTextPosition(SwingConstants.BOTTOM);

        // Action : ouvre la vue 
        btnProfil.addActionListener(e -> {
            NavigationHelper.afficherFenetre(AdminDashboardView.this, new MonProfilView());
        });

        gbc.gridx = 3;
        gbc.gridy = -1; // nouvelle ligne !
        centerPanel.add(btnProfil, gbc);


    }
}

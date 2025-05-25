package view;

import dao.DAOUtilisateur;
import model.Utilisateur;
import utils.NavigationHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import model.LogAdmin;
import dao.LogAdminDAO;
public class ValidationInscriptionView extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nomField;
    private JComboBox<String> fonctionComboBox;
    private JButton validerButton;
    private JButton voirPieceButton;
    private JButton backButton;
    private JButton resetButton;

    public ValidationInscriptionView() {
        setTitle("Validation des Inscriptions");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/resources/FondValidation.png"));
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage.getImage());
        backgroundPanel.setLayout(new GridBagLayout());
        setContentPane(backgroundPanel);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10)) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(255, 255, 255, 220));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        mainPanel.setOpaque(false);
        mainPanel.setPreferredSize(new Dimension(950, 600));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel des filtres
        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setOpaque(true);
        filterPanel.setBackground(new Color(173, 216, 230, 190));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filtres"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        filterPanel.add(new JLabel("Nom :"), gbc);
        gbc.gridx = 1;
        nomField = new JTextField(10);
        filterPanel.add(nomField, gbc);

        gbc.gridx = 2;
        filterPanel.add(new JLabel("Fonction :"), gbc);
        gbc.gridx = 3;
        String[] fonctions = {
            "", "Annimateur", "Assistant", "Bénévole", "Coach", "Maire",
            "Président de club", "Président de fédération", "Superviseur de plateforme", "Trésorier"
        };
        fonctionComboBox = new JComboBox<>(fonctions);
        fonctionComboBox.setPreferredSize(new Dimension(140, 25));
        filterPanel.add(fonctionComboBox, gbc);

        gbc.gridx = 4;
        JButton searchButton = new JButton("Rechercher");
        filterPanel.add(searchButton, gbc);

        gbc.gridx = 5;
        resetButton = new JButton("Réinitialiser");
        filterPanel.add(resetButton, gbc);

        mainPanel.add(filterPanel, BorderLayout.NORTH);

        // Tableau
        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"ID", "Nom", "Prénom", "Email", "Fonction"}) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);
        table.setCellSelectionEnabled(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setSelectionBackground(new Color(173, 216, 230));
        table.setSelectionForeground(Color.BLACK);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.setRowHeight(32);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.setOpaque(false);
        ((DefaultTableCellRenderer) table.getDefaultRenderer(Object.class)).setOpaque(true);

        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(true);
        buttonPanel.setBackground(new Color(255, 153, 153, 150));

        voirPieceButton = new JButton("Voir Pièce justificative");
        validerButton = new JButton("Valider l'inscription");
        backButton = new JButton("Retour");

        voirPieceButton.setEnabled(false);
        validerButton.setEnabled(false);

        buttonPanel.add(voirPieceButton);
        buttonPanel.add(validerButton);
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        backgroundPanel.add(mainPanel);

        // Listeners
        searchButton.addActionListener(e -> chargerUtilisateurs());

        resetButton.addActionListener(e -> {
            nomField.setText("");
            fonctionComboBox.setSelectedIndex(0);
            chargerUtilisateurs();
        });

        voirPieceButton.addActionListener(e -> {
        	int row = table.getSelectedRow();
            if (row == -1) return;
            int userId = (int) tableModel.getValueAt(row, 0);
            NavigationHelper.afficherFenetre(ValidationInscriptionView.this, new VoirPieceJustificativeView(userId));
        });

        validerButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            int id = (int) tableModel.getValueAt(row, 0);
            DAOUtilisateur dao = new DAOUtilisateur();
            
            boolean success = dao.validerInscription(id);

            Utilisateur admin = utils.Session.getUtilisateur();
            Utilisateur utilisateurCible = dao.findById(id); 

            LogAdmin log = new LogAdmin(
                0,
                admin,
                "VALIDATION",
                "Utilisateur",
                id,
                "non validé",
                "validé",
                LogAdminDAO.getIpLocale(),
                java.time.LocalDateTime.now(),
                success
            );

            if (utilisateurCible != null) {
                log.setNomCible(utilisateurCible.getNom() + " " + utilisateurCible.getPrenom());
            }

            LogAdminDAO.enregistrerLog(log, this);

            chargerUtilisateurs();
        });


        backButton.addActionListener(e -> NavigationHelper.retourDashboard(this));

        table.getSelectionModel().addListSelectionListener(e -> {
            boolean selected = table.getSelectedRow() != -1;
            voirPieceButton.setEnabled(selected);
            validerButton.setEnabled(selected);
        });

        chargerUtilisateurs();
    }

    private void chargerUtilisateurs() {
        String nom = nomField.getText();
        String fonction = (String) fonctionComboBox.getSelectedItem();

        DAOUtilisateur dao = new DAOUtilisateur();
        List<Utilisateur> utilisateurs = dao.findInscriptionsAValider(nom, fonction);

        tableModel.setRowCount(0);
        for (Utilisateur u : utilisateurs) {
            tableModel.addRow(new Object[]{
                u.getId(), u.getNom(), u.getPrenom(), u.getEmail(), u.getFonction()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ValidationInscriptionView frame = new ValidationInscriptionView();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        });
    }
}

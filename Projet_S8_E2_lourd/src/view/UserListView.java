package view;

import utils.NavigationHelper;


import dao.DAOUtilisateur;
import model.Utilisateur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class UserListView extends JFrame {

    private JTextField nomField;
    private JTextField fonctionField;
    private JComboBox<String> roleComboBox;
    private JTable userTable;
    private JButton searchButton;
    private JButton addButton;
    private JButton backButton;
    private JButton editButton;
    private JButton deleteButton;

    public UserListView() {
        setTitle("Gestion des utilisateurs");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/resources/FondUserList.png"));
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage.getImage());
        backgroundPanel.setLayout(new BorderLayout(10, 10));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(backgroundPanel);

        // Panel des filtres
        JPanel filterPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filtres"));
        filterPanel.setOpaque(true);
        filterPanel.setBackground(new Color(173, 216, 230, 190));

        nomField = new JTextField();
        fonctionField = new JTextField();

        DAOUtilisateur dao = new DAOUtilisateur();
        List<String> roles = dao.findAllRoleLabels();
        roles.add(0, "Tous");
        roleComboBox = new JComboBox<String>(roles.toArray(new String[0]));

        filterPanel.add(new JLabel("Nom :"));
        filterPanel.add(nomField);
        filterPanel.add(new JLabel("Rôle :"));
        filterPanel.add(roleComboBox);
        filterPanel.add(new JLabel("Fonction :"));
        filterPanel.add(fonctionField);

        searchButton = new JButton("Rechercher");
        filterPanel.add(new JLabel());
        filterPanel.add(searchButton);

        backgroundPanel.add(filterPanel, BorderLayout.NORTH);

        // Tableau des utilisateurs
        userTable = new JTable(new DefaultTableModel(
                new Object[][] {},
                new String[] { "ID", "Nom", "Prénom", "Email", "Rôle", "Fonction" }
        )) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        
        /*---------------- Mise à jour---------------------------------------------------- */
        
        userTable.setRowSelectionAllowed(true);           // Active la sélection de lignes
        userTable.setColumnSelectionAllowed(false);          // Désactive la sélection de colonnes
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);      // Une seule ligne à la fois

        userTable.setSelectionBackground(new Color(173, 216, 230));
        userTable.setSelectionForeground(Color.BLACK);
        
        userTable.getColumnModel().getColumn(0).setMinWidth(0);
        userTable.getColumnModel().getColumn(0).setMaxWidth(0);
        userTable.getColumnModel().getColumn(0).setPreferredWidth(0);

        userTable.setOpaque(false);
        ((DefaultTableCellRenderer) userTable.getDefaultRenderer(Object.class)).setOpaque(true);

        /*------------------------------------------------------------------------------*/
        
        
        userTable.getSelectionModel().addListSelectionListener(event -> {
            boolean selected = userTable.getSelectedRow() != -1;
            editButton.setEnabled(selected);
            deleteButton.setEnabled(selected);
        });

        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel tableBackground = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(255, 255, 255, 200));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        tableBackground.setOpaque(false);
        tableBackground.add(scrollPane, BorderLayout.CENTER);
        backgroundPanel.add(tableBackground, BorderLayout.CENTER);

        // Bas de page
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(true);
        bottomPanel.setBackground(new Color(255, 153, 153, 150));

        addButton = new JButton("Ajouter");
        backButton = new JButton("Retour");
        editButton = new JButton("Modifier");
        deleteButton = new JButton("Supprimer");

        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        bottomPanel.add(editButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(addButton);
        bottomPanel.add(backButton);
        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Listeners
        searchButton.addActionListener(e -> chargerUtilisateursFiltres());

        addButton.addActionListener(e -> {
        	NavigationHelper.afficherFenetre(this, new UserCreationView());
        });

        backButton.addActionListener(e -> {
        	NavigationHelper.retourDashboard(UserListView.this);

        });

        editButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow != -1) {
                int modelIndex = userTable.convertRowIndexToModel(selectedRow);
                DefaultTableModel model = (DefaultTableModel) userTable.getModel();

                int id = (int) model.getValueAt(modelIndex, 0);
                String nom = (String) model.getValueAt(modelIndex, 1);
                String prenom = (String) model.getValueAt(modelIndex, 2);
                String email = (String) model.getValueAt(modelIndex, 3);
                String roleStr = (String) model.getValueAt(modelIndex, 4);
                String fonction = (String) model.getValueAt(modelIndex, 5);

                int role = DAOUtilisateur.convertirRoleEnInt(roleStr);

                Utilisateur utilisateur = new Utilisateur(id, nom, prenom, email, role, fonction);
                NavigationHelper.afficherFenetre(this, new UserEditView(utilisateur));

            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow != -1) {
                int modelIndex = userTable.convertRowIndexToModel(selectedRow);
                DefaultTableModel model = (DefaultTableModel) userTable.getModel();

                int id = (int) model.getValueAt(modelIndex, 0);
                String nom = (String) model.getValueAt(modelIndex, 1);
                String prenom = (String) model.getValueAt(modelIndex, 2);

                int confirm = JOptionPane.showConfirmDialog(UserListView.this,
                        "Voulez-vous vraiment supprimer l'utilisateur : " + prenom + " " + nom + " ?",
                        "Confirmation", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    DAOUtilisateur daoUser = new DAOUtilisateur();
                    boolean success = daoUser.deleteUser(id);
                    if (success) {
                        JOptionPane.showMessageDialog(UserListView.this, "Utilisateur supprimé.");
                        chargerUtilisateursFiltres();
                    } else {
                        JOptionPane.showMessageDialog(UserListView.this, "Erreur lors de la suppression.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Chargement initial
        chargerUtilisateursFiltres();
    }

    private void chargerUtilisateursFiltres() {
        String nom = nomField.getText();
        String fonction = fonctionField.getText();
        String role = (String) roleComboBox.getSelectedItem();
        if (role.equals("Tous")) {
            role = "";
        }

        DAOUtilisateur dao = new DAOUtilisateur();
        List<Utilisateur> utilisateurs = dao.findUser(nom, role, fonction);

        DefaultTableModel model = (DefaultTableModel) userTable.getModel();
        model.setRowCount(0);
        for (Utilisateur u : utilisateurs) {
            model.addRow(new Object[] {
                    u.getId(),
                    u.getNom(),
                    u.getPrenom(),
                    u.getEmail(),
                    DAOUtilisateur.convertirIntEnRole(u.getRole()),
                    u.getFonction()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserListView frame = new UserListView();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        });
    }

}
package view;

import model.Club;

import view.BackgroundPanel;
import view.AdminDashboardView;
import utils.NavigationHelper;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dao.ClubDAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class ClubSearchView extends JFrame {
    private JComboBox<String> federationComboBox;
    private JComboBox<String> departementComboBox;
    private JTextField cityField;
    private JComboBox<String> regionComboBox;
    private JSpinner radiusSpinner;
    private JTable resultsTable;
    private JButton searchButton;
    private JButton backButton;

    public ClubSearchView() {
        setTitle("Recherche de clubs");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/resources/FondRechClubs.png"));
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage.getImage());
        backgroundPanel.setLayout(new BorderLayout(10, 10));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(backgroundPanel);
        getContentPane().setBackground(new Color(0, 0, 0, 0));

        JPanel filterPanel = new JPanel(new BorderLayout());
        filterPanel.setOpaque(false);
        filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel labelPanel = new JPanel(new GridLayout(1, 5, 10, 0));
        labelPanel.setOpaque(true);
        labelPanel.setBackground(new Color(255, 255, 255, 200));
        labelPanel.add(new JLabel("Fédération :", SwingConstants.LEFT));
        labelPanel.add(new JLabel("Commune :", SwingConstants.LEFT));
        labelPanel.add(new JLabel("Département :", SwingConstants.LEFT));
        labelPanel.add(new JLabel("Région :", SwingConstants.LEFT));
        labelPanel.add(new JLabel("Rayon (km) :", SwingConstants.LEFT));

        JPanel fieldPanel = new JPanel(new GridLayout(1, 5, 10, 0));
        fieldPanel.setOpaque(true);
        fieldPanel.setBackground(new Color(255, 255, 255, 200));

        // Chargement dynamique des fédérations depuis la base
        ClubDAO clubDAO = new ClubDAO();
        List<String> federations = clubDAO.findAllFederationNames();
        federations.add(0, "Toutes");
        federationComboBox = new JComboBox<>(federations.toArray(new String[0]));
        
        List<String> regions = clubDAO.findAllRegionNames();
        regions.add(0, "Toutes");
        regionComboBox = new JComboBox<>(regions.toArray(new String[0]));

        List<String> departement = clubDAO.findAllDepartementNames();
        departement.add(0, "Toutes");
        departementComboBox = new JComboBox<>(departement.toArray(new String[0]));
        
        /*à expliquer */
        
        cityField = new JTextField();
        radiusSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 2000, 1));
        radiusSpinner.setEnabled(false); // Désactivé par défaut
        
        cityField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void updateSpinnerState() {
                String text = cityField.getText().trim();
                radiusSpinner.setEnabled(!text.isEmpty());
            }

            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateSpinnerState();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateSpinnerState();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateSpinnerState();
            }
        });
        
        /*--------------------*/
        

        fieldPanel.add(federationComboBox);
        fieldPanel.add(cityField);
        fieldPanel.add(departementComboBox);
        fieldPanel.add(regionComboBox);
        fieldPanel.add(radiusSpinner);

        JPanel searchButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchButtonPanel.setOpaque(true);
        searchButtonPanel.setBackground(new Color(255, 255, 255, 200));
        searchButton = new JButton("Rechercher");
        searchButtonPanel.add(searchButton);

        filterPanel.add(labelPanel, BorderLayout.NORTH);
        filterPanel.add(fieldPanel, BorderLayout.CENTER);
        filterPanel.add(searchButtonPanel, BorderLayout.EAST);

        backgroundPanel.add(filterPanel, BorderLayout.NORTH);

        resultsTable = new JTable(new DefaultTableModel(
        	    new Object[][] {},
        	    new String[] {"Nom", "Commune", "Département", "Région", "Fédération"}
        	)) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        resultsTable.setOpaque(false);
        ((DefaultTableCellRenderer) resultsTable.getDefaultRenderer(Object.class)).setOpaque(false);
        resultsTable.setShowGrid(true);
        resultsTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(resultsTable);
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

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);
        backButton =  new JButton("Retour");
        bottomPanel.add(backButton);
        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	NavigationHelper.retourDashboard(ClubSearchView.this);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String federation = (String) federationComboBox.getSelectedItem();
                String departement = (String) departementComboBox.getSelectedItem();
                String commune = cityField.getText();
                String region = (String) regionComboBox.getSelectedItem();
                int rayon = (int) radiusSpinner.getValue();

                List<Club> clubs = clubDAO.findByFilters(federation, commune, region, departement, rayon);

                DefaultTableModel model = (DefaultTableModel) resultsTable.getModel();
                model.setRowCount(0);

                for (Club club : clubs) {
                    model.addRow(new Object[]{
                        club.getNom(),
                        club.getCommune(),
                        club.getDepartement(),
                        club.getRegion(),
                        club.getFederation()
                    });
                }

                resultsTable.revalidate();
                resultsTable.repaint();
            }
        });
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	ClubSearchView frame = new ClubSearchView();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        });
    }

  
}

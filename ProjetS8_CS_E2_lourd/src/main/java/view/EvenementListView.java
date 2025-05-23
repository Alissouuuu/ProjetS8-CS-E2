package view;

import dao.DAOEvenement;
import model.Evenement;
import utils.NavigationHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EvenementListView extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton backButton;
    private JButton detailsButton;
    private JSpinner dateSpinner;
    private JSpinner timeSpinner;
    private JButton searchButton;
    private JButton resetButton;
    private List<Evenement> evenements;

    public EvenementListView() {
        setTitle("Liste des événements");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/resources/FondEvenement.png"));
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage.getImage());
        backgroundPanel.setLayout(new BorderLayout(10, 10));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(backgroundPanel);

        JLabel title = new JLabel("Liste des Événements");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setOpaque(true);
        titlePanel.setBackground(new Color(255, 255, 255, 200));
        titlePanel.add(title);
        backgroundPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        filterPanel.setOpaque(true);
        filterPanel.setBackground(new Color(255, 255, 255, 200));

        filterPanel.add(new JLabel("Date :"));
        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
        filterPanel.add(dateSpinner);

        filterPanel.add(new JLabel("Heure ≥ :"));
        timeSpinner = new JSpinner(new SpinnerDateModel());
        timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "HH:mm"));
        filterPanel.add(timeSpinner);

        searchButton = new JButton("Rechercher");
        resetButton = new JButton("Réinitialiser");
        filterPanel.add(searchButton);
        filterPanel.add(Box.createHorizontalStrut(640));
        filterPanel.add(resetButton);

        backgroundPanel.add(filterPanel, BorderLayout.BEFORE_FIRST_LINE);

        searchButton.addActionListener(e -> chargerEvenementsFiltres());
        resetButton.addActionListener(e -> {
            dateSpinner.setValue(new java.util.Date());
            timeSpinner.setValue(new java.util.Date());
            chargerEvenements();
        });

        String[] columnNames = {
            "Type", "Date", "Heure", "Lieu", "Participants Max", "Responsable", "Club"
        };

        tableModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setSelectionBackground(new Color(173, 216, 230));
        table.setSelectionForeground(Color.BLACK);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.setRowHeight(32);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.setOpaque(false);
        ((DefaultTableCellRenderer) table.getDefaultRenderer(Object.class)).setOpaque(true);

        table.getSelectionModel().addListSelectionListener(e -> {
            boolean selected = table.getSelectedRow() != -1;
            detailsButton.setEnabled(selected);
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel tableContainer = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(255, 255, 255, 200));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        tableContainer.setOpaque(false);
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        backgroundPanel.add(tableContainer, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false);
        detailsButton = new JButton("Voir détails");
        detailsButton.setEnabled(false);
        leftPanel.add(detailsButton);
        bottomPanel.add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        backButton = new JButton("Retour");
        rightPanel.add(backButton);
        bottomPanel.add(rightPanel, BorderLayout.EAST);

        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> NavigationHelper.retourDashboard(this));

        detailsButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            Evenement evt = evenements.get(row); // ✅ récupère l'objet avec email
            NavigationHelper.afficherFenetre(this, new EvenementDetailsView(evt));
            
        });

        chargerEvenements();
    }

    private void chargerEvenementsFiltres() {
        java.util.Date selectedDate = (java.util.Date) dateSpinner.getValue();
        java.util.Date selectedTime = (java.util.Date) timeSpinner.getValue();

        java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());
        java.sql.Time sqlTime = new java.sql.Time(selectedTime.getTime());

        DAOEvenement dao = new DAOEvenement();
        evenements = dao.findByDateAndHeure(sqlDate.toLocalDate(), sqlTime.toLocalTime());

        tableModel.setRowCount(0);
        for (Evenement evt : evenements) {
            tableModel.addRow(new Object[]{
                evt.getType(),
                evt.getDate(),
                evt.getHeure(),
                evt.getLieu(),
                evt.getNbrMax(),
                evt.getNomResponsable(),
                evt.getNomClub() != null ? evt.getNomClub() : "-"
            });
        }
    }

    private void chargerEvenements() {
        DAOEvenement dao = new DAOEvenement();
        evenements = dao.getAllEvenementsAvecNoms();

        tableModel.setRowCount(0);
        for (Evenement evt : evenements) {
            tableModel.addRow(new Object[]{
                evt.getType(),
                evt.getDate(),
                evt.getHeure(),
                evt.getLieu(),
                evt.getNbrMax(),
                evt.getNomResponsable(),
                evt.getNomClub() != null ? evt.getNomClub() : "-"
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EvenementListView frame = new EvenementListView();
            frame.setVisible(true);
        });
    }
}

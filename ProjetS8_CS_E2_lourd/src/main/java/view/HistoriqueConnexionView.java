package view;

import dao.LogConnexionDAO;

import model.LogConnexion;
import utils.NavigationHelper;
import org.jdatepicker.impl.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class HistoriqueConnexionView extends JFrame {

    private JTable logTable;
    private JButton retourButton, appliquerFiltresButton;
    private JComboBox<String> triComboBox;
    private JTextField searchField;
    private JDatePickerImpl datePickerDebut, datePickerFin;

    public HistoriqueConnexionView() {
        setTitle("Historique des connexions");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/resources/FondUserList.png"));
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage.getImage());
        backgroundPanel.setLayout(new BorderLayout(10, 10));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(backgroundPanel);

        JPanel triPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        triPanel.setOpaque(false);

        JLabel triLabel = new JLabel("Trier par :");
        triLabel.setForeground(Color.WHITE);
        triLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        // ComboBox sans valeur par défaut
        triComboBox = new JComboBox<>();
        triComboBox.addItem(""); // Aucune sélection par défaut
        triComboBox.addItem("Date (récent)");
        triComboBox.addItem("Date (ancien)");
        triComboBox.addItem("Succès");
        triComboBox.addItem("Échec");

        JLabel searchLabel = new JLabel("Nom :");
        searchLabel.setForeground(Color.WHITE);
        searchField = new JTextField(10);
        appliquerFiltresButton = new JButton("Appliquer les filtres");

        JLabel duLabel = new JLabel("Du :");
        duLabel.setForeground(Color.WHITE);
        JLabel auLabel = new JLabel("Au :");
        auLabel.setForeground(Color.WHITE);
        datePickerDebut = createDatePicker();
        datePickerFin = createDatePicker();

        triPanel.add(triLabel);
        triPanel.add(triComboBox);
        triPanel.add(searchLabel);
        triPanel.add(searchField);
        triPanel.add(duLabel);
        triPanel.add(datePickerDebut);
        triPanel.add(auLabel);
        triPanel.add(datePickerFin);
        triPanel.add(appliquerFiltresButton);

        backgroundPanel.add(triPanel, BorderLayout.NORTH);

        logTable = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Nom", "Prénom", "Email", "Fonction", "Date", "Heure", "Adresse IP", "Statut"}
        )) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JScrollPane scrollPane = new JScrollPane(logTable);
        logTable.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        retourButton = new JButton("Retour");
        bottomPanel.add(retourButton);
        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);

        retourButton.addActionListener((ActionEvent e) -> NavigationHelper.retourDashboard(this));

        appliquerFiltresButton.addActionListener(e -> chargerLogs());

        chargerLogs(); 
    }

    private JDatePickerImpl createDatePicker() {
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Aujourd'hui");
        p.put("text.month", "Mois");
        p.put("text.year", "Année");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        return new JDatePickerImpl(datePanel, new DateComponentFormatter());
    }

    private LocalDateTime getDateFromPicker(JDatePickerImpl picker, boolean endOfDay) {
        Date selected = (Date) picker.getModel().getValue();
        if (selected == null) return null;
        LocalDate localDate = selected.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return endOfDay ? localDate.atTime(23, 59, 59) : localDate.atStartOfDay();
    }

    private void chargerLogs() {
        String critere = (String) triComboBox.getSelectedItem();
        if (critere != null && critere.trim().isEmpty()) {
            critere = null; // Pas de tri si rien sélectionné
        }

        String nomRecherche = searchField.getText().trim();
        LocalDateTime dateDebut = getDateFromPicker(datePickerDebut, false);
        LocalDateTime dateFin = getDateFromPicker(datePickerFin, true);

        LogConnexionDAO dao = new LogConnexionDAO();
        List<LogConnexion> logs = dao.findLogsSortedBy(critere, nomRecherche, dateDebut, dateFin);

        if (logs.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucun résultat trouvé pour les filtres appliqués.", "Résultat vide", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        afficherLogsDansTable(logs);
    }

    private void afficherLogsDansTable(List<LogConnexion> logs) {
        DefaultTableModel model = (DefaultTableModel) logTable.getModel();
        model.setRowCount(0);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        for (LogConnexion log : logs) {
            model.addRow(new Object[]{
                    log.getUtilisateur().getNom(),
                    log.getUtilisateur().getPrenom(),
                    log.getUtilisateur().getEmail(),
                    log.getUtilisateur().getFonction(),
                    log.getDateConnexion().format(dateFormatter),
                    log.getDateConnexion().format(heureFormatter),
                    log.getAdresseIp(),
                    log.getStatut()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HistoriqueConnexionView frame = new HistoriqueConnexionView();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        });
    }
}


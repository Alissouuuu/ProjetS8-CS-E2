package view;

import dao.DAOMessage;
import model.Message;
import model.Utilisateur;
import utils.NavigationHelper;
import utils.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class MessageListView extends JFrame {

    private JTable messageTable;
    private JButton retourButton;
    private JButton msgButton;
    private JButton filtrerButton;
    private JButton toutAfficherButton;
    private JSpinner dateSpinner;

    public MessageListView() {
        setTitle("Messages envoyés");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/resources/FondUserList.png"));
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage.getImage());
        backgroundPanel.setLayout(new BorderLayout(10, 10));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(backgroundPanel);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setOpaque(true);
        filterPanel.setBackground(new Color(173, 216, 230, 190));
        filterPanel.add(new JLabel("Filtrer par date :"));

        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
        filterPanel.add(dateSpinner);

        filtrerButton = new JButton("Filtrer");
        toutAfficherButton = new JButton("Tout afficher");
        filterPanel.add(filtrerButton);
        filterPanel.add(toutAfficherButton);

        backgroundPanel.add(filterPanel, BorderLayout.NORTH);

        messageTable = new JTable(new DefaultTableModel(
                new Object[][] {},
                new String[]{"Date", "Expéditeur", "Sujet", "Destinataire", "Contenu"}
        )) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        messageTable.setRowSelectionAllowed(true);
        messageTable.setColumnSelectionAllowed(false);
        messageTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        messageTable.setSelectionBackground(new Color(173, 216, 230));
        messageTable.setSelectionForeground(Color.BLACK);
        messageTable.setOpaque(false);
        ((DefaultTableCellRenderer) messageTable.getDefaultRenderer(Object.class)).setOpaque(true);

        JScrollPane scrollPane = new JScrollPane(messageTable);
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

        retourButton = new JButton("Retour");
        msgButton = new JButton("Envoyer un Message");
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(true);
        bottomPanel.setBackground(new Color(255, 153, 153, 150));
        bottomPanel.add(msgButton);
        bottomPanel.add(retourButton);
        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);

        retourButton.addActionListener(e -> NavigationHelper.retourDashboard(this));

        msgButton.addActionListener(e -> {
            Utilisateur utilisateurActif = Session.getUtilisateur();
            if (utilisateurActif != null) {
                NavigationHelper.afficherFenetre(this, new MessageSendView(utilisateurActif));
            } else {
                JOptionPane.showMessageDialog(this, "Aucun utilisateur connecté.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });



        filtrerButton.addActionListener(e -> filtrerMessagesParDate());
        toutAfficherButton.addActionListener(e -> chargerTousLesMessages());

        chargerTousLesMessages();
    }

    private void filtrerMessagesParDate() {
        java.util.Date selectedDate = (java.util.Date) dateSpinner.getValue();
        LocalDate localDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        chargerMessages(localDate);
    }

    private void chargerTousLesMessages() {
        chargerMessages(null);
    }

    private void chargerMessages(LocalDate dateFilter) {
        DAOMessage dao = new DAOMessage();
        List<Message> messages = dao.getAllMessages();

        if (dateFilter != null) {
            messages = messages.stream()
                    .filter(m -> m.getDateEnvoi().toLocalDate().equals(dateFilter))
                    .collect(Collectors.toList());
        }

        DefaultTableModel model = (DefaultTableModel) messageTable.getModel();
        model.setRowCount(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (Message msg : messages) {
            model.addRow(new Object[] {
                    msg.getDateEnvoi().format(formatter),
                    msg.getExpediteur(),
                    msg.getSujet(),
                    msg.getDestinataire(),
                    msg.getContenu()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MessageListView frame = new MessageListView();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        });
    }
}

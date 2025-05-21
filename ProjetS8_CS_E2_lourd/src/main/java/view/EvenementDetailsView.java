package view;

import model.Evenement;
import model.Utilisateur;
import utils.NavigationHelper;
import utils.Session;
import model.Utilisateur;

import javax.swing.*;
import java.awt.*;

public class EvenementDetailsView extends JFrame {

    public EvenementDetailsView(Evenement evenement) {
        setTitle("Détails de l'événement");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/resources/FondDetailEvenement.png"));
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage.getImage());
        backgroundPanel.setLayout(new BorderLayout(20, 0));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));
        setContentPane(backgroundPanel);

        // Intermediate semi-transparent container panel
        JPanel contentWrapper = new JPanel(new GridBagLayout()) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(255, 255, 255, 200));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        contentWrapper.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;

        // Left side info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel header = new JLabel("Détails de l'événement");
        header.setFont(new Font("SansSerif", Font.BOLD, 26));
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        infoPanel.add(header);

        addCard(infoPanel, "Type", evenement.getType());
        addCard(infoPanel, "Date", evenement.getDate().toString());
        addCard(infoPanel, "Heure", evenement.getHeure().toString());
        addCard(infoPanel, "Lieu", evenement.getLieu());
        addCard(infoPanel, "Participants Max", String.valueOf(evenement.getNbrMax()));
        addCard(infoPanel, "Responsable", evenement.getNomResponsable());
        addCard(infoPanel, "Club", evenement.getNomClub() != null ? evenement.getNomClub() : "-");

        contentWrapper.add(infoPanel, gbc);

        // Right side description panel
        gbc.gridx = 1;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(0, 0, 0, 0);

        JPanel descriptionPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(255, 255, 255, 180));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        descriptionPanel.setLayout(new BorderLayout());
        descriptionPanel.setOpaque(false);
        descriptionPanel.setBorder(BorderFactory.createTitledBorder("Description"));

        JTextArea descriptionArea = new JTextArea(evenement.getDescription() != null ? evenement.getDescription() : "-");
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setEditable(false);
        descriptionArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        descriptionArea.setOpaque(false);

        JScrollPane descriptionScroll = new JScrollPane(descriptionArea);
        descriptionScroll.setOpaque(false);
        descriptionScroll.getViewport().setOpaque(false);
        descriptionScroll.setBorder(null);
        descriptionPanel.add(descriptionScroll, BorderLayout.CENTER);

        descriptionPanel.setPreferredSize(new Dimension(300, 400));

        contentWrapper.add(descriptionPanel, gbc);

        backgroundPanel.add(contentWrapper, BorderLayout.CENTER);

        // Bottom buttons panel with two sides
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JPanel leftButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftButtons.setOpaque(false);
        JButton messageButton = new JButton("Envoyer un message");
        leftButtons.add(messageButton);

        JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightButtons.setOpaque(false);
        JButton backButton = new JButton("Retour");
        rightButtons.add(backButton);

        bottomPanel.add(leftButtons, BorderLayout.WEST);
        bottomPanel.add(rightButtons, BorderLayout.EAST);

        backButton.addActionListener(e -> {
        
        dispose();
        NavigationHelper.afficherFenetre(this, new EvenementListView());
        });
        messageButton.addActionListener(e -> {
            
                NavigationHelper.afficherFenetre(this, new MessageSendFromEventView(evenement));
           
        });


        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addCard(JPanel container, String label, String value) {
        JPanel card = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(255, 255, 255, 180));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        card.setLayout(new BorderLayout());
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 15));

        JLabel val = new JLabel(value);
        val.setFont(new Font("SansSerif", Font.PLAIN, 15));
        val.setHorizontalAlignment(SwingConstants.RIGHT);

        card.add(lbl, BorderLayout.WEST);
        card.add(val, BorderLayout.EAST);

        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(700, 60));
        container.add(Box.createVerticalStrut(10));
        container.add(card);
    }
}

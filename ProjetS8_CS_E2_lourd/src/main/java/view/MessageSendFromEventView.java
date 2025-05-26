package view;

import dao.DAOMessage;
import utils.MailSender;

import dao.DAOUtilisateur;
import model.Utilisateur;
import model.Evenement;
import model.Message;
import utils.NavigationHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;

public class MessageSendFromEventView extends JFrame {
    private JTextField sujetField;
    private JTextArea contenuArea;
    private JTextField destinataireField;
    private JButton envoyerButton;
    private JButton annulerButton;
    private Utilisateur expediteur;

    public MessageSendFromEventView(Evenement evenement, Utilisateur expediteur) {
    	this.expediteur = expediteur;
        setTitle("Envoyer un message");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/resources/FondMessageSend.png"));
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage.getImage());
        backgroundPanel.setLayout(new BorderLayout(10, 10));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(backgroundPanel);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(255, 255, 255, 200));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2d.dispose();
                super.paintComponent(g);
            }
        };

        mainPanel.setPreferredSize(new Dimension(700, 400));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel fieldsPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        fieldsPanel.setOpaque(false);

        fieldsPanel.add(new JLabel("Sujet :"));
        sujetField = new JTextField();
        fieldsPanel.add(sujetField);

        fieldsPanel.add(new JLabel("Destinataire :"));
        destinataireField = new JTextField(evenement.getEmail());
        destinataireField.setEditable(false);
        destinataireField.setBackground(Color.LIGHT_GRAY);
        fieldsPanel.add(destinataireField);

        mainPanel.add(fieldsPanel, BorderLayout.NORTH);

        contenuArea = new JTextArea(10, 40);
        contenuArea.setLineWrap(true);
        contenuArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(contenuArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(255, 255, 255, 160));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        buttonPanel.setOpaque(false);
        envoyerButton = new JButton("Envoyer");
        annulerButton = new JButton("Annuler");

        buttonPanel.add(envoyerButton);
        buttonPanel.add(annulerButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        wrapper.add(mainPanel);

        backgroundPanel.add(wrapper, BorderLayout.CENTER);

        envoyerButton.addActionListener((ActionEvent e) -> {
            String sujet = sujetField.getText();
            String contenu = contenuArea.getText();
            String destinataire = destinataireField.getText();

            if (sujet.isEmpty() || contenu.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

         // Envoi réel de l’e-mail
            boolean ok = utils.MailSender.envoyerEmail(sujet, contenu, destinataire);

            if (!ok) {
                JOptionPane.showMessageDialog(this, "Échec de l'envoi de l'e-mail à : " + destinataire, "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Si envoi OK, on enregistre le message dans la base
            DAOMessage dao = new DAOMessage();
            Message msg = new Message(sujet, contenu, destinataire, expediteur.getNomComplet(), LocalDateTime.now());
            dao.envoyerMessage(msg);

            JOptionPane.showMessageDialog(this, "Message envoyé avec succès.");
            dispose();
            NavigationHelper.afficherFenetre(this, new MessageListView());

        });

        annulerButton.addActionListener((ActionEvent e) -> {
            dispose();
            NavigationHelper.afficherFenetre(this, new EvenementListView());
        });
    }
}

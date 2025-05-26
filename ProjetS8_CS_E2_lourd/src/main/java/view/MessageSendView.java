package view;

import dao.DAOMessage;
import dao.DAOUtilisateur;
import model.Message;
import model.Utilisateur;
import utils.NavigationHelper;
import view.BackgroundPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class MessageSendView extends JFrame {
    private JTextField sujetField;
    private JTextArea contenuArea;
    private JTextField destinataireField;
    private JButton envoyerButton;
    private JButton annulerButton;
    private Utilisateur expediteur;

    public MessageSendView(Utilisateur expediteur) {
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

        fieldsPanel.add(new JLabel("Destinataires (email, séparés par ;) :"));
        destinataireField = new JTextField();
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

        //panel pour recentrer-------------------------------------
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false); // pour laisser apparaître le fond
        wrapper.add(mainPanel);

        backgroundPanel.add(wrapper, BorderLayout.CENTER);

        // end panel -----------------------------------------------

        envoyerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sujet = sujetField.getText();
                String contenu = contenuArea.getText();
                String[] destinataires = destinataireField.getText().split(";");

                if (sujet.isEmpty() || contenu.isEmpty() || destinataires.length == 0) {
                    JOptionPane.showMessageDialog(MessageSendView.this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                DAOMessage dao = new DAOMessage();
                DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
                LocalDateTime now = LocalDateTime.now();

                for (String rawEmail : destinataires) {
                    String email = rawEmail.trim();

                    if (!estEmailValide(email)) {
                        JOptionPane.showMessageDialog(MessageSendView.this, "Email invalide : " + email, "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!daoUtilisateur.emailExiste(email)) {
                        JOptionPane.showMessageDialog(MessageSendView.this, "Aucun utilisateur avec l'email : " + email, "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    System.out.println("Sujet : " + sujet);
                    System.out.println("Contenu : " + contenu);
                    System.out.println("Destinataire : " + email);


                 // Envoi de l'e-mail
                    boolean ok = utils.MailSender.envoyerEmail(sujet, contenu, email);

                    if (!ok) {
                        JOptionPane.showMessageDialog(MessageSendView.this, "Échec de l'envoi à : " + email, "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Sauvegarde dans la base uniquement si envoi OK
                    Message msg = new Message(sujet, contenu, email, expediteur.getNomComplet(), now);
                    dao.envoyerMessage(msg);

                }

                JOptionPane.showMessageDialog(MessageSendView.this, "Message envoyé avec succès.");
                dispose();
                NavigationHelper.afficherFenetre(MessageSendView.this, new MessageListView());
            }
        });

        annulerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                NavigationHelper.afficherFenetre(MessageSendView.this, new MessageListView());
            }
        });
        
        
    }
    
 // Constructeur avec destinataire pré-rempli
    public MessageSendView(Utilisateur expediteur, String emailDestinataire) {
        this(expediteur); // Appel du constructeur principal
        destinataireField.setText(emailDestinataire); // Préremplir le champ
        destinataireField.setEditable(false); // Désactive l'édition du champ
        destinataireField.setBackground(new Color(230, 230, 230));
    }

    
    private boolean estEmailValide(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }


   
}

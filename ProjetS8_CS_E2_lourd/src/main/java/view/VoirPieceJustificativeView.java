package view;

import dao.DAOUtilisateur;
import utils.NavigationHelper;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;

import java.io.InputStream;
import java.net.URLConnection;
import model.LogAdmin;
import model.Utilisateur;
import dao.LogAdminDAO;
public class VoirPieceJustificativeView extends JFrame {

    private JLabel imageLabel;
    private JButton retourButton, telechargerButton, envoyerMessageButton;
    
    
    private String detectMimeType(byte[] data) {
        try (InputStream is = new java.io.ByteArrayInputStream(data)) {
            return URLConnection.guessContentTypeFromStream(is);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public VoirPieceJustificativeView(int userId) {
        setTitle("Pièce Justificative");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        imageLabel = new JLabel("Chargement...");
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        JScrollPane scrollPane = new JScrollPane(imageLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        retourButton = new JButton("Retour");
        telechargerButton = new JButton("Télécharger");
        envoyerMessageButton = new JButton("Envoyer un message");

        buttonPanel.add(envoyerMessageButton);
        buttonPanel.add(telechargerButton);
        buttonPanel.add(retourButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        chargerEtAfficher(userId);
    }

    private void chargerEtAfficher(int userId) {
        DAOUtilisateur dao = new DAOUtilisateur();
        byte[] data = dao.getPieceJustificative(userId);
     //  Admin en session
        Utilisateur admin = utils.Session.getUtilisateur();

        // Création du log
        Utilisateur cible = dao.findById(userId); 
        model.LogAdmin log = new model.LogAdmin(
            0,
            admin,
            "consultation",
            "pièce justificative",
            userId,
            null,
            null,
            LogAdminDAO.getIpLocale(),
            java.time.LocalDateTime.now(),
            (data != null && data.length > 0)
        );

        // Enregistrement
        if (cible != null) {
            log.setNomCible(cible.getNom() + " " + cible.getPrenom());
        }
        LogAdminDAO.enregistrerLog(log, this);


        if (data != null && data.length > 0) {
            String mimeType = detectMimeType(data);

            if (mimeType != null && mimeType.startsWith("image")) {
                // Cas image (jpg, png, etc.)
                ImageIcon icon = new ImageIcon(data);
                Image scaledImage = icon.getImage().getScaledInstance(400, -1, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));
                imageLabel.setText("");

                telechargerButton.setEnabled(true);
                envoyerMessageButton.setEnabled(true);

            } else if (mimeType != null && mimeType.equals("application/pdf")) {
                // Cas PDF
                imageLabel.setText("PDF détecté. Cliquez sur 'Télécharger' ou 'Ouvrir'.");
                imageLabel.setIcon(null);

                JButton ouvrirPdfButton = new JButton("Ouvrir");
                ouvrirPdfButton.addActionListener(e -> {
                    try {
                        File tempFile = File.createTempFile("piece_", ".pdf");
                        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                            fos.write(data);
                        }
                        Desktop.getDesktop().open(tempFile);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erreur lors de l'ouverture du PDF.");
                    }
                });
                ((JPanel) getContentPane().getComponent(1)).add(ouvrirPdfButton); // Ajoute à la fin du panel de boutons

                telechargerButton.setEnabled(true);
                envoyerMessageButton.setEnabled(true);

            } else {
                imageLabel.setText("Format de fichier non supporté.");
                imageLabel.setIcon(null);
                telechargerButton.setEnabled(true);
                envoyerMessageButton.setEnabled(true);
            }

        } else {
            imageLabel.setText("Aucune pièce justificative disponible.");
            imageLabel.setIcon(null);
            telechargerButton.setVisible(false);
        }

        retourButton.addActionListener(e -> {
        	NavigationHelper.afficherFenetre(this, new ValidationInscriptionView());
        });
        

        envoyerMessageButton.addActionListener(e -> {
            //NavigationHelper.afficherFenetre(this, new MessageSendView());
        });

        telechargerButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File("piece_justificative"));
            int option = fileChooser.showSaveDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(data);
                    JOptionPane.showMessageDialog(this, "Téléchargement réussi.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors du téléchargement.");
                }
            }
        });
    }

    
}

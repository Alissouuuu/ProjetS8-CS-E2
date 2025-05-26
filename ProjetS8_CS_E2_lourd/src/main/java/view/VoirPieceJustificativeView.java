package view;


import dao.DAOUtilisateur;

import model.Utilisateur;
import utils.NavigationHelper;
import utils.Session;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLConnection;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import java.awt.image.BufferedImage;


public class VoirPieceJustificativeView extends JFrame {

	private DisplayPanel displayPanel;
    private JButton retourButton, telechargerButton, envoyerMessageButton;
    private byte[] data;
    private String emailDestinataire;


    private String detectMimeType(byte[] data) {
        try (InputStream is = new java.io.ByteArrayInputStream(data)) {
            return URLConnection.guessContentTypeFromStream(is);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public VoirPieceJustificativeView(int userId, String email) {
    	this.emailDestinataire = email;
        setTitle("Pièce Justificative");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/resources/VoirPieces.png"));
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage.getImage());
        backgroundPanel.setLayout(new GridBagLayout());
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
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
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setPreferredSize(new Dimension(700, 600));


        displayPanel = new DisplayPanel();
        displayPanel.setPreferredSize(new Dimension(600, 600));

        JScrollPane scrollPane = new JScrollPane(displayPanel);


        
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(255, 153, 153, 150));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        buttonPanel.setOpaque(false);

        retourButton = new JButton("Retour");
        telechargerButton = new JButton("Télécharger");
        envoyerMessageButton = new JButton("Envoyer un message");

        buttonPanel.add(envoyerMessageButton);
        buttonPanel.add(telechargerButton);
        buttonPanel.add(retourButton);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        backgroundPanel.add(mainPanel);

        chargerEtAfficher(userId);
    }
    
    
    
    class DisplayPanel extends JPanel {
        private ImageIcon image;
        private String message;

        public void setContent(ImageIcon image) {
            this.image = image;
            this.message = null;
            repaint();
        }

        public void setContent(String message) {
            this.image = null;
            this.message = message;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (image != null) {
            	int panelWidth = getWidth();
            	int imageWidth = image.getIconWidth();
            	int imageHeight = image.getIconHeight();

            	double scale = (double) panelWidth / imageWidth;
            	int scaledHeight = (int) (imageHeight * scale);

            	g.drawImage(image.getImage(), 0, 0, panelWidth, scaledHeight, this);

            } else if (message != null) {
                g.setFont(new Font("Arial", Font.BOLD, 16));
                g.setColor(Color.BLACK);
                g.drawString(message, 20, 30);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            if (image != null) {
                int panelWidth = getWidth() > 0 ? getWidth() : 600;
                int imageWidth = image.getIconWidth();
                int imageHeight = image.getIconHeight();
                double scale = (double) panelWidth / imageWidth;
                int scaledHeight = (int) (imageHeight * scale);
                return new Dimension(panelWidth, scaledHeight);
            }
            return super.getPreferredSize();
        }
    }

    
    

    private boolean isPdf(byte[] data) {
        return data != null && data.length >= 4 &&
               data[0] == '%' && data[1] == 'P' && data[2] == 'D' && data[3] == 'F';
    }
    
    private String getExtensionFromMimeType(String mimeType) {
        if (mimeType == null) return "";
        if (mimeType.equals("application/pdf")) return ".pdf";
        if (mimeType.startsWith("image/")) return ".jpg";
        return ""; // fallback
    }


    

    private void chargerEtAfficher(int userId) {
        DAOUtilisateur dao = new DAOUtilisateur();
        data = dao.getPieceJustificative(userId);

        if (data != null && data.length > 0) {
            String mimeType = detectMimeType(data);
            System.out.println("MIME type détecté : " + mimeType);


            try {
                if (mimeType != null && mimeType.startsWith("image")) {
                    ImageIcon icon = new ImageIcon(data);
                    Image scaled = icon.getImage().getScaledInstance(-1, 600, Image.SCALE_SMOOTH);
                    displayPanel.setContent(new ImageIcon(scaled));

                } else if (isPdf(data)) {
                	
                	try (InputStream inputStream = new java.io.ByteArrayInputStream(data)) {
                	    PDDocument document = PDDocument.load(inputStream);

                	    PDFRenderer renderer = new PDFRenderer(document);
                	    BufferedImage image = renderer.renderImageWithDPI(0, 800); // qualité très nette


                	    document.close();

                	    displayPanel.setContent(new ImageIcon(image));
                	}


                } else {
                    displayPanel.setContent("Format non supporté.");
                }

                telechargerButton.setEnabled(true);
                envoyerMessageButton.setEnabled(true);

            } catch (Exception e) {
                displayPanel.setContent("Erreur lors de l'affichage.");
                e.printStackTrace();
            }

        } else {
            displayPanel.setContent("Aucune pièce justificative disponible.");
            telechargerButton.setVisible(false);
            envoyerMessageButton.setEnabled(false);
        }


        retourButton.addActionListener(e -> NavigationHelper.afficherFenetre(this, new ValidationInscriptionView()));

        envoyerMessageButton.addActionListener(e -> {
            Utilisateur utilisateurActif = Session.getUtilisateur();
            if (utilisateurActif != null) {
                NavigationHelper.afficherFenetre(VoirPieceJustificativeView.this,
                    new MessageSendView(utilisateurActif, emailDestinataire));
            } else {
                JOptionPane.showMessageDialog(this, "Aucun utilisateur connecté.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });




        telechargerButton.addActionListener(e -> {
            String extension = getExtensionFromMimeType(detectMimeType(data));
            if (extension.isEmpty()) {
                extension = isPdf(data) ? ".pdf" : ".jpg"; // fallback
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File("piece_justificative" + extension));

            int option = fileChooser.showSaveDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                // Ajoute l'extension s'il manque
                if (!file.getName().toLowerCase().endsWith(extension)) {
                    file = new File(file.getAbsolutePath() + extension);
                }

                // 💡 Génère automatiquement un nom disponible s'il y a conflit
                int count = 1;
                String baseName = file.getAbsolutePath().replaceAll("\\.[^.]+$", ""); // retire l'extension
                while (file.exists()) {
                    file = new File(baseName + "(" + count + ")" + extension);
                    count++;
                }

                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(data);
                    JOptionPane.showMessageDialog(this, "Téléchargement réussi sous le nom :\n" + file.getName());

                    // ✅ Ouvre le dossier de destination
                    Desktop.getDesktop().open(file.getParentFile());

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors du téléchargement : " + ex.getMessage());
                    ex.printStackTrace();
                }

            }
        });

        
        


    }
}

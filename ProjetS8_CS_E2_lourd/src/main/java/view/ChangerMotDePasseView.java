package view;

import dao.DAOUtilisateur;
import model.Utilisateur;
import utils.NavigationHelper;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;

public class ChangerMotDePasseView extends JFrame {

    private JPasswordField ancienField, nouveauField, confirmerField;
    private Utilisateur utilisateur;

    public ChangerMotDePasseView(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;

        setTitle("Changer le mot de passe");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Ancien mot de passe :"));
        ancienField = new JPasswordField();
        panel.add(ancienField);

        panel.add(new JLabel("Nouveau mot de passe :"));
        nouveauField = new JPasswordField();
        panel.add(nouveauField);

        panel.add(new JLabel("Confirmer le mot de passe :"));
        confirmerField = new JPasswordField();
        panel.add(confirmerField);

        JButton validerButton = new JButton("Valider");
        JButton annulerButton = new JButton("Annuler");
        panel.add(validerButton);
        panel.add(annulerButton);

        add(panel);

        validerButton.addActionListener(e -> changerMotDePasse());
        annulerButton.addActionListener(e -> {
            dispose();
            NavigationHelper.afficherFenetre(this, new MonProfilView());
        });
    }

    private void changerMotDePasse() {
        String ancien = new String(ancienField.getPassword());
        String nouveau = new String(nouveauField.getPassword());
        String confirmer = new String(confirmerField.getPassword());

        if (!nouveau.equals(confirmer)) {
            JOptionPane.showMessageDialog(this, "Les mots de passe ne correspondent pas.");
            return;
        }

        DAOUtilisateur dao = new DAOUtilisateur();
        if (!dao.verifierMotDePasse(utilisateur.getEmail(), ancien)) {
            JOptionPane.showMessageDialog(this, "Ancien mot de passe incorrect.");
            return;
        }

        String hashed = BCrypt.hashpw(nouveau, BCrypt.gensalt());
        boolean success = dao.mettreAJourMotDePasse(utilisateur.getId(), hashed);

        if (success) {
            JOptionPane.showMessageDialog(this, "Mot de passe mis à jour !");
            dispose();
            NavigationHelper.afficherFenetre(this, new MonProfilView());
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}

package utils;

import javax.swing.*;
import view.AdminDashboardView;

public class NavigationHelper {

    /**
     * Ferme la fenêtre actuelle et affiche la nouvelle en plein écran.
     * @param fenetreActuelle La fenêtre en cours (this)
     * @param nouvelleFenetre La nouvelle instance de JFrame à afficher
     */
    public static void afficherFenetre(JFrame fenetreActuelle, JFrame nouvelleFenetre) {
        if (fenetreActuelle != null) {
            fenetreActuelle.dispose();
        }
        nouvelleFenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);
        nouvelleFenetre.setVisible(true);
    }
    
    
    public static void retourDashboard(JFrame currentFrame) {
        afficherFenetre(currentFrame, new AdminDashboardView());
    }

}





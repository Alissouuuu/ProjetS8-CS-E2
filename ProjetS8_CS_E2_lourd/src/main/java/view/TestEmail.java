package view;

public class TestEmail {
    public static void main(String[] args) {
        String sujet = "Test depuis Java";
        String contenu = "Ceci est un test d'envoi d'e-mail via JavaMail.";
        String destinataire = "kamgar916@gmail.com"; // mets ici une vraie adresse

        boolean ok = utils.MailSender.envoyerEmail(sujet, contenu, destinataire);

        if (ok) {
            System.out.println("✅ Message envoyé avec succès !");
        } else {
            System.out.println("❌ Échec de l'envoi.");
        }
    }
}


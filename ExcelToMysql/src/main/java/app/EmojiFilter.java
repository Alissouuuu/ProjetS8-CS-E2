package app;
import java.util.regex.*;

public class EmojiFilter {
    // Fonction pour remplacer les emojis ou autres caractères non supportés
    public static String filterEmoji(String input) {
        
        // Expression régulière pour détecter les emojis et caractères non UTF-8 supportés
        String regex = "[\\x{10000}-\\x{10FFFF}]";  // Plage des caractères emojis (Unicode)
      
        // Remplacer tous les caractères invalides par "nom inconnu"
        return input.replaceAll(regex, " emoji ");
    }
    
    /*public static void main(String[] args) {
        String textWithEmojis = "ahhl🍋yhy";
        String cleanedText = filterInvalidCharacters(textWithEmojis);
        
        // Afficher le texte nettoyé
        System.out.println("Texte nettoyé : " + cleanedText);
    }*/
}

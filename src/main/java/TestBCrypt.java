import org.mindrot.jbcrypt.BCrypt;

public class TestBCrypt {
    public static void main(String[] args) {
        String salt = BCrypt.gensalt();
        System.out.println("Salt généré : " + salt);
    }
}

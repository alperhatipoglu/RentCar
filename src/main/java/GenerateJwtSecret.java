import java.util.Base64;
import java.security.SecureRandom;

public class GenerateJwtSecret {
    public static void main(String[] args) {
        byte[] randomBytes = new byte[32];
        new SecureRandom().nextBytes(randomBytes);

        String base64Secret = Base64.getEncoder().encodeToString(randomBytes);
        System.out.println("Generated JWT Secret: " + base64Secret);
    }
}

package cryptoapp;

import java.util.Base64;
import java.util.Random;

public class RSAEncryptionGUI {

    // إيجاد المعكوس الضربي لـ e modulo phi
    public static int modInverse(int e, int phi) {
        for (int d = 1; d < phi; d++) {
            if ((e * d) % phi == 1)
                return d;
        }
        return -1; // لم يتم العثور على معكوس
    }

    // توليد قيمة باستخدام LCG
    public static int generateLCG(int seed, int a, int c, int m, int count) {
        int x = seed;
        for (int i = 0; i < count; i++) {
            x = (a * x + c) % m;
        }
        return x;
    }

    // توليد مفتاح خاص LCG-based صحيح
    public static int generateValidLCGKey(int e, int phi) {
        Random random = new Random();
        int d = -1;
        int attempts = 0;
        int maxTries = 1000;

        while (d == -1 && attempts < maxTries) {
            int seed = random.nextInt(phi - 1) + 1; // seed عشوائي من 1 إلى φ-1
            int a = 3 + random.nextInt(10);         // a عشوائي
            int c = 1 + random.nextInt(10);         // c عشوائي
            int candidate = generateLCG(seed, a, c, phi, 1);

            if ((e * candidate) % phi == 1) {
                d = candidate;
            }

            attempts++;
        }

        return d != -1 ? d : modInverse(e, phi); // fallback to traditional d if not found
    }

    public static String encrypt(String message, int e, int n) {
        StringBuilder encryptedMessage = new StringBuilder();
        for (char character : message.toCharArray()) {
            int encryptedChar = (int) Math.pow(character, e) % n;
            encryptedMessage.append(encryptedChar).append(" ");
        }
        return encryptedMessage.toString().trim();
    }

    public static String decrypt(String encryptedMessage, int d, int n) {
        StringBuilder decryptedMessage = new StringBuilder();
        String[] encryptedChars = encryptedMessage.split(" ");
        for (String encryptedChar : encryptedChars) {
            int decryptedChar = (int) Math.pow(Integer.parseInt(encryptedChar), d) % n;
            decryptedMessage.append((char) decryptedChar);
        }
        return decryptedMessage.toString();
    }

    public static String encodeBase64(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    public static String decodeBase64(String input) {
        byte[] decodedBytes = Base64.getDecoder().decode(input);
        return new String(decodedBytes);
    }

    public static void main(String[] args) {
        int p = 61, q = 53;
        int n = p * q;
        int phi = (p - 1) * (q - 1);
        int e = 17;

        // d بالطريقة التقليدية
        int d = modInverse(e, phi);
        System.out.println("Private Key (traditional): (" + n + ", " + d + ")");

        // d عشوائي باستخدام LCG بشرط (e * d) % φ == 1
        int lcgD = generateValidLCGKey(e, phi);
        System.out.println("Private Key (LCG-based): (" + n + ", " + lcgD + ")");

        String message = "Hello!";
        String encryptedMessage = encrypt(message, e, n);
        System.out.println("Encrypted Message: " + encryptedMessage);

        String base64EncryptedMessage = encodeBase64(encryptedMessage);
        System.out.println("Encrypted Message (Base64): " + base64EncryptedMessage);

        String decryptedBase64Message = decodeBase64(base64EncryptedMessage);
        System.out.println("Decrypted Message (Base64): " + decryptedBase64Message);

        String decryptedMessage = decrypt(decryptedBase64Message, d, n);
        System.out.println("Decrypted Message (traditional): " + decryptedMessage);

        String decryptedMessageLCG = decrypt(decryptedBase64Message, lcgD, n);
        System.out.println("Decrypted Message (LCG-based): " + decryptedMessageLCG);
    }
}

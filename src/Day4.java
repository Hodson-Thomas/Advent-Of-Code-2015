import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Day4 {
    static String key = "iwrupvqb";

    public static int part1() throws NoSuchAlgorithmException {
        int i = 0;
        MessageDigest md = MessageDigest.getInstance("MD5");
        while (true) {
            md.update((key + i).getBytes());
            if (byteToHexadecimal(md.digest()).startsWith("00000"))
                return i;
            i++;
        }
    }

    public static int part2() throws NoSuchAlgorithmException {
        int i = 0;
        MessageDigest md = MessageDigest.getInstance("MD5");
        while (true) {
            md.update((key + i).getBytes());
            if (byteToHexadecimal(md.digest()).startsWith("000000"))
                return i;
            i++;
        }
    }

    static String byteToHexadecimal(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;


public class Day5 {
    public static int part1(String filePath) throws IOException {
        File file = new File(filePath);
        String line;
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                if (isStringNice1(line)) count++;
            }
        }

        return count;
    }

    public static int part2(String filePath) throws IOException {
        File file = new File(filePath);
        String line;
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                if (isStringNice2(line)) count++;
            }
        }

        return count;
    }


    static boolean isStringNice1(String string) {
        int vowelCount = 0;
        boolean twiceLetter = false;
        char previous = '\0';
        for (char c : string.toCharArray()) {
            if (isCharVowel(c)) vowelCount++;
            twiceLetter = twiceLetter || previous == c;
            if (
                (previous == 'a' && c == 'b') ||
                (previous == 'c' && c == 'd') ||
                (previous == 'p' && c == 'q') ||
                (previous == 'x' && c == 'y')
            ) return false;
            previous = c;
        }
        return vowelCount >= 3 && twiceLetter;
    }

    static boolean isStringNice2(String string) {
        Pattern pairPattern = Pattern.compile("([a-z][a-z]).*\\1");
        Pattern repeatPattern = Pattern.compile("([a-z])[a-z]\\1");
        return pairPattern.matcher(string).find() && repeatPattern.matcher(string).find();
    }

    static boolean isCharVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }
}

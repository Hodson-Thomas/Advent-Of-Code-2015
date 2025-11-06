import java.io.*;

public class Day8 {
    public static int part1(String filePath) throws IOException {
        File file = new File(filePath);
        String line;
        int literal = 0;
        int stored = 0;
        char c;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                literal += line.length();
                for (int i = 1; i < line.length() - 1; i++) {
                    stored++;
                    if (line.charAt(i) != '\\')
                        continue;
                    i++;
                    c = line.charAt(i);
                    if (c == '\\' || c == '"')
                        continue;
                    i += 2;
                }
            }
        }

        return literal - stored;
    }

    public static int part2(String filePath) throws IOException {
        File file = new File(filePath);
        String line;
        int origin = 0;
        int reworked = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                origin += line.length();
                line = line.replace("\\", "\\\\").replace("\"", "\\\"");
                reworked += line.length() + 2;
            }
        }

        return reworked - origin;
    }
}

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Day2 {
    public static int part1(String filePath) throws IOException {
        File file = new File(filePath);
        String line;
        String[] split;
        int total = 0;
        int width, length, height;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                split = line.trim().split("x");
                if (split.length != 3) continue;
                width = Integer.parseInt(split[0]);
                length = Integer.parseInt(split[1]);
                height = Integer.parseInt(split[2]);
                total += (width * length + width * height + length * height) * 2 +
                        Math.min(width * length, Math.min(width * height, length * height));
            }
        }

        return total;
    }

    public static int part2(String filePath) throws IOException {
        File file = new File(filePath);
        String line;
        String[] split;
        int total = 0;
        int width, length, height;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                split = line.trim().split("x");
                if (split.length != 3) continue;
                width = Integer.parseInt(split[0]);
                length = Integer.parseInt(split[1]);
                height = Integer.parseInt(split[2]);
                total += Math.min(width * 2 + length * 2, Math.min(width * 2 + height * 2, length * 2 + height * 2))
                    + width * length * height;
            }
        }

        return total;
    }
}

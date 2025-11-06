import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Day3 {
    public static int part1(String filePath) throws IOException {
        File file = new File(filePath);
        int charCode;
        Coordinates position = new Coordinates(0, 0);
        Set<Coordinates> set = new HashSet<Coordinates>();
        set.add(position);
        try (FileReader reader = new FileReader(file.getAbsolutePath())) {
            while ((charCode = reader.read()) != -1) {
                position = move(position, (char)charCode);
                set.add(position);
            }
        }

        return set.toArray().length;
    }

    public static int part2(String filePath) throws IOException {
        File file = new File(filePath);
        int charCode;
        int i = 0;
        Coordinates santaPosition = new Coordinates(0, 0);
        Coordinates robotPosition = new Coordinates(0, 0);
        Set<Coordinates> set = new HashSet<Coordinates>();
        set.add(santaPosition);
        try (FileReader reader = new FileReader(file.getAbsolutePath())) {
            while ((charCode = reader.read()) != -1) {
                if (i % 2 == 1) {
                    robotPosition = move(robotPosition, (char)charCode);
                    set.add(robotPosition);
                } else {
                    santaPosition = move(santaPosition, (char)charCode);
                    set.add(santaPosition);
                }
                i++;
            }
        }

        return set.toArray().length;
    }


    private static Coordinates move(Coordinates from, char instruction) {
        return switch (instruction) {
            case '<' -> new Coordinates(from.x - 1, from.y);
            case '>' -> new Coordinates(from.x + 1, from.y);
            case '^' -> new Coordinates(from.x, from.y - 1);
            case 'v' -> new Coordinates(from.x, from.y + 1);
            default -> throw new IllegalArgumentException("Invalid instruction: " + instruction);
        };
    }

    static class Coordinates {
        Integer x;
        Integer y;

        Coordinates(Integer x, Integer y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Coordinates other = (Coordinates) obj;
            return Objects.equals(x, other.x) && Objects.equals(y, other.y);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}

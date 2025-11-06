import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Day14 {
    public static int part1(String fileName) throws IOException {
        return parseInput(fileName)
                .stream()
                .map(reindeer -> reindeer.getDistance(2503))
                .max(Integer::compare)
                .orElse(0);
    }

    public static int part2(String fileName) throws IOException {
        var reindeer = parseInput(fileName);
        int max;
        for (int seconds = 0; seconds < 2503; seconds++) {
            max = 0;
            for (var rd : reindeer) {
                rd.move();
                max = Math.max(max, rd.distance);
            }
            for (var rd : reindeer)
                if (rd.distance == max)
                    rd.score++;
        }

        return reindeer.stream().map(rdr -> rdr.score).max(Integer::compare).orElse(0);
    }

    static List<Reindeer> parseInput(String fileName) throws IOException {
        File file = new File(fileName);
        String line;
        String[] split;
        List<Reindeer> reindeer = new ArrayList<Reindeer>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                split = line.split(" ");
                reindeer.add(new Reindeer(Integer.parseInt(split[3]), Integer.parseInt(split[6]), Integer.parseInt(split[13])));
            }
        }
        return reindeer;
    }

    static class Reindeer {
        public int speed;
        public int flyDuration;
        public int restDuration;

        private int seconds = 0;
        public int score = 0;
        public int distance = 0;

        public Reindeer(int speed, int flyDuration, int restDuration) {
            this.speed = speed;
            this.flyDuration = flyDuration;
            this.restDuration = restDuration;
        }

        public int getDistance(int seconds) {
            int complete_cycles = (int) Math.floor((double) seconds / (this.flyDuration + this.restDuration));
            int remaining_time = Math.min(this.flyDuration, seconds % (this.flyDuration + this.restDuration));

            return complete_cycles * this.flyDuration * speed + remaining_time * speed;
        }

        public void move() {
            if (seconds < this.flyDuration) distance += speed;
            seconds = (seconds + 1) % (this.flyDuration + this.restDuration);
        }
    }
}

import java.io.*;

public class Day6 {
    public static int part1(String filePath) throws IOException {
        File file = new File(filePath);
        String line;
        String[] split, coords1, coords2;
        Action action;
        boolean[][] lights = new boolean[1000][1000];
        int x1, x2, y1, y2;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                split = line.split(" ");
                if (line.startsWith("toggle")) {
                    action = Action.Toggle;
                    coords1 = split[1].split(",");
                    coords2 = split[3].split(",");
                    x1 = Integer.parseInt(coords1[0]);
                    y1 = Integer.parseInt(coords1[1]);
                    x2 = Integer.parseInt(coords2[0]);
                    y2 = Integer.parseInt(coords2[1]);
                }
                else  {
                    action = line.startsWith("turn on") ? Action.TurnOn : Action.TurnOff;
                    coords1 = split[2].split(",");
                    coords2 = split[4].split(",");
                    x1 = Integer.parseInt(coords1[0]);
                    y1 = Integer.parseInt(coords1[1]);
                    x2 = Integer.parseInt(coords2[0]);
                    y2 = Integer.parseInt(coords2[1]);
                }
                updateLights1(lights, Math.min(x1, x2), Math.min(y1, y2), Math.max(x1, x2), Math.max(y1, y2), action);
            }
        }

        int count = 0;
        for (boolean[] row : lights)
            for (boolean light : row)
                if (light)
                    count++;

        return count;
    }

    public static int part2(String filePath) throws IOException {
        File file = new File(filePath);
        String line;
        String[] split, coords1, coords2;
        Action action;
        int[][] lights = new int[1000][1000];
        int x1, x2, y1, y2;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                split = line.split(" ");
                if (line.startsWith("toggle")) {
                    action = Action.Toggle;
                    coords1 = split[1].split(",");
                    coords2 = split[3].split(",");
                    x1 = Integer.parseInt(coords1[0]);
                    y1 = Integer.parseInt(coords1[1]);
                    x2 = Integer.parseInt(coords2[0]);
                    y2 = Integer.parseInt(coords2[1]);
                }
                else  {
                    action = line.startsWith("turn on") ? Action.TurnOn : Action.TurnOff;
                    coords1 = split[2].split(",");
                    coords2 = split[4].split(",");
                    x1 = Integer.parseInt(coords1[0]);
                    y1 = Integer.parseInt(coords1[1]);
                    x2 = Integer.parseInt(coords2[0]);
                    y2 = Integer.parseInt(coords2[1]);
                }
                updateLights2(lights, Math.min(x1, x2), Math.min(y1, y2), Math.max(x1, x2), Math.max(y1, y2), action);
            }
        }

        int brightness = 0;
        for (int[] row : lights)
            for (int light : row)
                brightness += light;

        return brightness;
    }

    static void updateLights1(boolean[][] lights, int x1, int y1, int x2, int y2, Action action) {
        for (int y = y1; y <= y2; y++) {
            for (int x = x1; x <= x2; x++) {
                switch (action) {
                    case TurnOn:
                        lights[y][x] = true;
                        break;
                    case TurnOff:
                        lights[y][x] = false;
                        break;
                    case Toggle:
                        lights[y][x] = !lights[y][x];
                        break;
                }
            }
        }
    }

    static void updateLights2(int[][] lights, int x1, int y1, int x2, int y2, Action action) {
        for (int y = y1; y <= y2; y++) {
            for (int x = x1; x <= x2; x++) {
                switch (action) {
                    case TurnOn:
                        lights[y][x]++;
                        break;
                    case TurnOff:
                        if (lights[y][x] > 0)
                            lights[y][x]--;
                        break;
                    case Toggle:
                        lights[y][x] += 2;
                        break;
                }
            }
        }
    }

    enum Action {
        TurnOn,
        TurnOff,
        Toggle
    }
}

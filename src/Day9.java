import java.io.*;
import java.util.*;

public class Day9 {
    public static int part1(String filePath) throws IOException {
        HashMap<Tuple, Integer> weights = new HashMap<>();
        HashMap<String, List<String>> travels = new HashMap<>();
        parseInput(filePath, weights, travels);

        List<List<String>> allPaths = findAllPaths(travels);
        int min = Integer.MAX_VALUE, temp;
        for (List<String> path : allPaths) {
            temp = 0;
            for (int i = 0; i < path.size() - 1; i++)
                for (Tuple tuple : weights.keySet())
                    if (tuple.isMatch(path.get(i), path.get(i + 1))) temp += weights.get(tuple);
            if (min > temp) min = temp;
        }
        return min;
    }

    public static int part2(String filePath) throws IOException {
        HashMap<Tuple, Integer> weights = new HashMap<>();
        HashMap<String, List<String>> travels = new HashMap<>();
        parseInput(filePath, weights, travels);

        List<List<String>> allPaths = findAllPaths(travels);
        int min = Integer.MIN_VALUE, temp;
        for (List<String> path : allPaths) {
            temp = 0;
            for (int i = 0; i < path.size() - 1; i++)
                for (Tuple tuple : weights.keySet())
                    if (tuple.isMatch(path.get(i), path.get(i + 1))) temp += weights.get(tuple);
            if (min < temp) min = temp;
        }
        return min;
    }


    static void parseInput(
            String filePath,
            HashMap<Tuple, Integer> weights,
            HashMap<String, List<String>> travels
    ) throws IOException {
        File file = new File(filePath);
        String line;
        String[] split;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                line = line.strip();
                if (line.isEmpty()) continue;
                split = line.split(" ");
                weights.put(new Tuple(split[0], split[2]), Integer.parseInt(split[4]));
                if (!travels.containsKey(split[0])) {
                    travels.put(split[0], new ArrayList<>());
                }
                if (!travels.containsKey(split[2])) {
                    travels.put(split[2], new ArrayList<>());
                }
                travels.get(split[0]).add(split[2]);
                travels.get(split[2]).add(split[0]);
            }
        }
    }

    static List<List<String>> findAllPaths(HashMap<String, List<String>> graph) {
        List<List<String>> result = new ArrayList<>();
        if (graph.isEmpty()) return result;

        for (String start : graph.keySet()) {
            Set<String> visited = new HashSet<>();
            List<String> path = new ArrayList<>();
            path.add(start);
            visited.add(start);
            backtrack(graph, start, visited, path, result);
        }
        return result;
    }

    static void backtrack(
        HashMap<String, List<String>> graph,
        String current,
        Set<String> visited,
        List<String> path,
        List<List<String>> result
    ) {
        if (visited.size() == graph.size()) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (String neighbor : graph.getOrDefault(current, new ArrayList<>())) {
            if (!visited.contains(neighbor)) {
                visited.add(neighbor);
                path.add(neighbor);
                backtrack(graph, neighbor, visited, path, result);
                path.removeLast();
                visited.remove(neighbor);
            }
        }
    }

    record Tuple(String cityA, String cityB) {
        public boolean isMatch(String city1, String city2) {
            return (cityA.equals(city1) && cityB.equals(city2)) || (cityB.equals(city1) && cityA.equals(city2));
        }
    }
}

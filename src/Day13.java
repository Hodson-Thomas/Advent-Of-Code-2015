import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day13 {
    public static int part1(String fileName) throws IOException {
        var map = parseInput(fileName);
        var permutations = getPermutations(map.keySet().toArray(String[]::new));
        int max = 0;
        for (String[] permutation : permutations)
            max = Math.max(max, evaluate(map, permutation));

        return max;
    }

    public static int part2(String fileName) throws IOException {
        var map = parseInput(fileName);
        String[] keys = map.keySet().toArray(String[]::new);
        map.put("Me", new HashMap<>());
        for (var key : keys) {
            map.get(key).put("Me", 0);
            map.get("Me").put(key, 0);
        }
        var permutations = getPermutations(map.keySet().toArray(String[]::new));
        int max = 0;
        for (String[] permutation : permutations)
            max = Math.max(max, evaluate(map, permutation));

        return max;
    }

    static int evaluate(HashMap<String, HashMap<String, Integer>> map, String[] table) {
        int score = 0;
        int previous, next;
        for (int i = 0; i < table.length; i++) {
            previous = (i - 1) < 0 ? table.length - 1 : i - 1;
            next = (i + 1) % table.length;
            score += map.get(table[i]).get(table[previous]) + map.get(table[i]).get(table[next]);
        }
        return score;
    }

    static HashMap<String, HashMap<String, Integer>> parseInput(String filePath) throws IOException {
        File file = new File(filePath);
        String line;
        String[] split;
        int sign;
        HashMap<String, HashMap<String, Integer>> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                split = line.split(" ");
                sign = split[2].equals("gain") ? 1 : -1;
                if (!map.containsKey(split[0])) {
                    map.put(split[0], new HashMap<>());
                }
                map.get(split[0]).put(split[10].substring(0, split[10].length() - 1), Integer.parseInt(split[3]) * sign);
            }
        }
        return map;
    }

    static List<String[]> getPermutations(String[] array) {
        List<String[]> result = new ArrayList<>();
        permute(array, 0, result);
        return result;
    }

    static void permute(String[] array, int start, List<String[]> result) {
        if (start == array.length - 1) {
            result.add(array.clone());
            return;
        }

        for (int i = start; i < array.length; i++) {
            swap(array, start, i);
            permute(array, start + 1, result);
            swap(array, start, i);
        }
    }

    static void swap(String[] array, int i, int j) {
        String temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}

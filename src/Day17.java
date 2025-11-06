import java.util.*;
import java.io.*;

public class Day17 {
    static int TARGET = 150;

    public static int part1(String filePath) throws IOException {
        return findCombinations(parseInput(filePath), TARGET).size();
    }

    public static int part2(String filePath) throws IOException {
        List<List<Integer>> combinations = findCombinations(parseInput(filePath), 150);
        int minSize = Integer.MAX_VALUE;
        for (List<Integer> combination : combinations)
            minSize = Math.min(minSize, combination.size());
        int count = 0;
        for (List<Integer> combination : combinations)
            if (combination.size() == minSize)
                count++;
        return count;
    }

    static List<Integer> parseInput(String filePath) throws IOException {
        String line;
        List<Integer> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                list.add(Integer.parseInt(line));
            }
        }
        return list;
    }

    static List<List<Integer>> findCombinations(List<Integer> nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> currentCombination = new ArrayList<>();

        Collections.sort(nums);

        backtrack(nums, target, 0, currentCombination, result);

        return result;
    }

    static void backtrack(
        List<Integer> nums, int target, int start,
        List<Integer> currentCombination, List<List<Integer>> result
    ) {
        if (target == 0) {
            result.add(new ArrayList<>(currentCombination));
            return;
        }

        for (int i = start; i < nums.size(); i++) {
            int num = nums.get(i);
            if (num > target)
                break;

            currentCombination.add(num);
            backtrack(nums, target - num, i + 1, currentCombination, result);
            currentCombination.removeLast();
        }
    }
}

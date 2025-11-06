import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Day24 {
    public static long part1(String filePath) throws IOException {
        List<Long> packages = parseInput(filePath);
        long maxWeight = packages.stream().reduce(Long::sum).orElse(0L) / 3;

        Combination combination = new Combination(packages, maxWeight);
        combination.generateCombinations(0, new ArrayList<>());
        return combination.getBestQuantumEntanglement();
    }

    public static long part2(String filePath) throws IOException {
        List<Long> packages = parseInput(filePath);
        long maxWeight = packages.stream().reduce(Long::sum).orElse(0L) / 4;

        Combination combination = new Combination(packages, maxWeight);
        combination.generateCombinations(0, new ArrayList<>());
        return combination.getBestQuantumEntanglement();
    }

    static List<Long> parseInput(String filePath) throws IOException {
        File file = new File(filePath);
        String line;
        List<Long> list = new ArrayList<Long>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                list.add(Long.parseLong(line));
            }
        }
        return list;
    }

    static class Combination {
        List<List<Long>> result = new ArrayList<>();
        int minLength = Integer.MAX_VALUE;
        final List<Long> input;
        final long target;

        public Combination(List<Long> input, long target) {
            this.input = input;
            this.target = target;
        }

        void generateCombinations(int start, List<Long> current) {
            long currentSum = current.stream().mapToLong(Long::longValue).sum();

            if (currentSum == target) {
                if (current.size() < minLength) minLength = current.size();
                result.add(new ArrayList<>(current));
                return;
            }

            if (currentSum > target || current.size() >= minLength) return;

            for (int i = start; i < input.size(); i++) {
                current.add(input.get(i));
                generateCombinations(i + 1, current);
                current.removeLast();
            }
        }


        long getBestQuantumEntanglement() {
            long min = Long.MAX_VALUE, prod, sum;
            for (List<Long> comb : result) {
                if (comb.size() != minLength) continue;
                prod = 1;
                for (Long i : comb) prod *= i;
                if (prod < min) min = prod;
            }
            return min;
        }
    }
}

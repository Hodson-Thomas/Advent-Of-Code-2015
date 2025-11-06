import java.util.ArrayList;
import java.util.List;

public class Day20 {
    static int TARGET = 33100000;

    public static Tuple solve() {
        Integer part1 = null;
        Integer part2 = null;
        int part1Sum = 0, part2Sum = 0;
        List<Integer> divisors;
        int house = 0;
        while (part2 == null) {
            house += 2;
            divisors = getDivisors(house);
            if (part1 == null)
                part1Sum = 10 * divisors.stream().reduce(0, Integer::sum);

            if (part2 == null) {
                part2Sum = 0;
                for (Integer divisor : divisors)
                    if (house / divisor <= 50)
                        part2Sum += divisor;
                part2Sum *= 11;
            }

            if (part1 == null && part1Sum >= TARGET)
                part1 = house;
            if (part2 == null && part2Sum >= TARGET)
                part2 = house;
        }
        return new Tuple(part1, part2);
    }

    static List<Integer> getDivisors(int house) {
        List<Integer> divisors = new ArrayList<>();
        for (int i = 1; i <= Math.ceil(Math.sqrt(house)); i++) {
            if (house % i != 0) continue;
            divisors.add(i);
            if (house != i * i) divisors.add(house / i);
        }
        return divisors;
    }

    record Tuple(Integer a, Integer b) {}
}

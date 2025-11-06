import java.util.*;
import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Day19 {
    public static int part1(String fileName) throws IOException {
        Data data = parseInput(fileName);
        Set<String> strings = new HashSet<String>();
        Pattern p;
        Matcher m;
        for (String key : data.rules.keySet()) {
            for (String pattern : data.rules.get(key)) {
                p = Pattern.compile(key);
                m = p.matcher(data.formula);
                while (m.find()) {
                    strings.add(transform(
                            data.formula,
                            key,
                            pattern,
                            m.start()
                    ));
                }
            }
        }

        return strings.size();
    }

    // Solution from : https://dmatrix.dev/posts/advent-of-code-year-2015-day-19/
    public static int part2(String fileName) throws IOException {
        Data data = parseInput(fileName);
        return countUpper(data.formula) - count(data.formula, "Rn") - count(data.formula, "Ar") - 2 * count(data.formula, "Y") - 1;
    }

    static int countUpper(String string) {
        int count = 0;
        for (char c : string.toCharArray())
            if (Character.isUpperCase(c))
                count++;
        return count;
    }

    static int count(String string, String pattern) {
        int count = 0;
        for (
                int index = string.indexOf(pattern);
                index >= 0;
                index = string.indexOf(pattern, index + pattern.length()),
                        ++count
        ) {
        }
        return count;
    }


    static String transform(String origin, String old, String replace, int index) {
        StringBuffer sb = new StringBuffer(origin);
        sb.replace(index, index + old.length(), replace);
        return sb.toString();
    }

    static Data parseInput(String fileName) throws IOException {
        HashMap<String, List<String>> map = new HashMap<>();
        String formula = "";
        File file = new File(fileName);
        String line;
        String[] split;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                split = line.split(" => ");
                if (split.length == 1) {
                    formula = split[0];
                    continue;
                }
                if (map.containsKey(split[0])) {
                    map.get(split[0]).add(split[1]);
                } else {
                    map.put(split[0], new ArrayList<>());
                    map.get(split[0]).add(split[1]);
                }
            }
        }

        return new Data(map, formula);
    }


    static class Data {
        HashMap<String, List<String>> rules;
        String formula;

        public Data(HashMap<String, List<String>> rules, String formula) {
            this.rules = rules;
            this.formula = formula;
        }
    }
}

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Day10 {
    static String INPUT = "1113222113";

    public static void main(String[] args) {
        System.out.println(part1());
    }

    public static int part1() {
        String input = INPUT;
        for (int i = 0; i < 40; i++)
            input = look(group(input));
        return input.length();
    }

    public static int part2() {
        String input = INPUT;
        for (int i = 0; i < 50; i++)
            input = look(group(input));
        return input.length();
    }

    static List<String> group(String input) {
        char[] separated = input.toCharArray();
        List<String> grouped = new ArrayList<>();
        grouped.add("" + separated[0]);
        String temp;

        for (int i = 1; i < separated.length; i++) {
            if (separated[i] == separated[i - 1]){
                temp = grouped.getLast();
                grouped.removeLast();
                grouped.add(temp + separated[i]);
            } else {
                grouped.add("" + separated[i]);
            }
        }

        return grouped;
    }


    static String look(List<String> groups) {
        String say = "";
        for (String group : groups)
            say += group.length() + group.charAt(0);
        return say;
    }
}

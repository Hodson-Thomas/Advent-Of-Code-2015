public class Day11 {
    static String INPUT1 = "hxbxwxba";
    static String INPUT2 = "hxbxxyzz";
    static String CHARS = "abcdefghjkmnpqrstuvwxyz";

    public static String part1() { return solve(INPUT1); }

    public static String part2() { return solve(INPUT2); }

    public static String solve(String initial) {
        String password = initial;
        int i = 0;
        do {

            isPasswordValid(password);
            password = transformPassword(password);
            i++;
        }
        while (!isPasswordValid(password));
        System.out.println(i);
        return password;
    }

    static String[] CONSECUTIVE_PATTERNS = {
        "abc", "bcd", "cde", "def", "efg", "fgh", "pqr", "qrs", "rst", "stu", "tuv", "uvw", "vwx", "wxy", "xyz"
    };

    static String transformPassword(String password) {
        char[] chars = password.toCharArray();
        boolean carry = true;
        int index;
        for (int i = chars.length - 1; i >= 0; i--) {
            if (!carry) break;
            index = CHARS.indexOf(chars[i]) + 1;
            carry = index >= CHARS.length();
            chars[i] = CHARS.charAt(index % CHARS.length());
        }
        return new String(chars);
    }

    static boolean isPasswordValid(String password) {
        if (password.contains("i") || password.contains("o") || password.contains("l")) return false;
        if (!containsPair(password)) return false;
        for (String pattern : CONSECUTIVE_PATTERNS)
            if (password.contains(pattern)) return true;
        return false;
    }

    static boolean containsPair(String password) {
        char previous = password.charAt(0);
        for (int i = 1; i < password.length() - 2; i++) {
            if (previous != password.charAt(i)) {
                previous = password.charAt(i);
                continue;
            }
            for (int j = i + 1; j < password.length() - 1; j++) {
                if (password.charAt(j) == password.charAt(j + 1) && password.charAt(j) != previous)
                    return true;
            }
            previous = password.charAt(i);
        }
        return false;
    }
}

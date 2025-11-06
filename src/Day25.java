public class Day25 {

    static long ROW = 2947;
    static long COL = 3029;
    static long INITIAL_CODE = 20151125;

    public static long part1(long initialCode, long row, long col) {
        long code = initialCode;
        for (int i = 1; i < computeNumberPassword(row, col); i++)
            code = getNewCode(code);
        return code;
    }

    static long computeNumberPassword(long row, long col) {
        long startRow = row * (row - 1) / 2 + 1;
        long shift = ((col + row) * (col + row - 1) / 2 ) - (row * (row + 1) / 2);
        return startRow + shift;
    }

    static long getNewCode(long previousCode) {
        return (previousCode * 252533) % 33554393;
    }
}

import java.io.*;

public class Day18 {
    public static int part1(String fileName) throws IOException {
        boolean[][] board = parseInput(fileName);
        for (int i = 0; i < 100; i++)
            board = transform(board);
        int count = 0;
        for (boolean[] row : board)
            for (boolean b : row)
                if (b) count++;
        return count;
    }

    public static int part2(String fileName) throws IOException {
        boolean[][] board = parseInput(fileName);
        board[0][0] = true;
        board[0][99] = true;
        board[99][0] = true;
        board[99][99] = true;
        for (int i = 0; i < 100; i++) {
            board = transform(board);
            board[0][0] = true;
            board[0][99] = true;
            board[99][0] = true;
            board[99][99] = true;
        }
        int count = 0;
        for (boolean[] row : board)
            for (boolean b : row)
                if (b) count++;
        return count;
    }

    static boolean[][] transform(boolean[][] board) {
        boolean[][] newBoard = new boolean[board.length][board[0].length];
        int alive = 0;
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                alive = countLightsOnAround(board, row, col);
                if (board[row][col] && (alive == 2 || alive == 3)) {
                    newBoard[row][col] = true;
                } else newBoard[row][col] = !board[row][col] && alive == 3;
            }
        }
        return newBoard;
    }

    static int countLightsOnAround(boolean[][] board, int row, int col) {
        int count = 0;
        for (int r = Math.max(row - 1, 0); r <= Math.min(row + 1, board.length - 1); r++) {
            for (int c = Math.max(col - 1, 0); c <= Math.min(col + 1, board[0].length - 1); c++) {
                if (r == row && c == col) continue;
                if (board[r][c]) count++;
            }
        }
        return count;
    }

    static boolean[][] parseInput(String fileName) throws IOException {
        File file = new File(fileName);
        String line;
        int i = 0;
        boolean[][] board = new boolean[100][100];
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                for (int j = 0; j < line.length(); j++)
                    board[i][j] = line.charAt(j) == '#';
                i++;
            }
        }
        return board;
    }
}

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Day1 {
    public static int part1(String filePath) throws IOException {
        File file = new File(filePath);
        int result = 0;
        int charCode;
        try (FileReader reader = new FileReader(file.getAbsolutePath())) {
            while ((charCode = reader.read()) != -1) {
                if ((char)charCode == '(') result++;
                else if ((char)charCode == ')') result--;
            }
        }

        return result;
    }

    public static int part2(String filePath) throws IOException {
        File file = new File(filePath);
        int position = 1;
        int result = 0;
        int charCode;
        try (FileReader reader = new FileReader(file.getAbsolutePath())) {
            while ((charCode = reader.read()) != -1) {
                if ((char)charCode == '(') result++;
                else if ((char)charCode == ')') result--;
                if (result == -1)
                    return position;
                position++;
            }
        }

        return -1;
    }
}



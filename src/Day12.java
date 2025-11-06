import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Day12 {
    public static int part1(String filePath) throws Exception {
        return parseInput(filePath).getIntCount();
    }

    public static int part2(String filePath) throws Exception {
        return parseInput(filePath).getIntCountWithoutRed();
    }

    static JValue parseInput(String filePath) throws Exception {
        return new Parser(filePath).parseValue();
    }

    static abstract class JValue {
        public abstract int getIntCount();

        public abstract int getIntCountWithoutRed();

        public abstract void print();
    }

    static class JObject extends JValue {
        public HashMap<String, JValue> content = new HashMap<>();

        @Override
        public int getIntCount() {
            int sum = 0;
            for (String key : content.keySet())
                sum += content.get(key).getIntCount();
            return sum;
        }

        @Override
        public int getIntCountWithoutRed() {
            int sum = 0;
            for (String key : content.keySet()) {
                if (content.get(key) instanceof JString str && Objects.equals(str.content, "red"))
                    return 0;
                sum += content.get(key).getIntCountWithoutRed();
            }

            return sum;
        }

        @Override
        public void print() {
            System.out.print("{");
            for (String key : content.keySet()) {
                System.out.print("\"" + key  + "\":");
                content.get(key).print();
                System.out.print(",");
            }
            System.out.print("}");
        }
    }

    static class JArray extends JValue {
        public List<JValue> content = new ArrayList<>();

        @Override
        public int getIntCount() {
            int sum = 0;
            for (JValue value : content)
                sum += value.getIntCount();
            return sum;
        }

        @Override
        public int getIntCountWithoutRed() {
            int sum = 0;
            for (JValue value : content)
                sum += value.getIntCountWithoutRed();
            return sum;
        }

        @Override
        public void print() {
            System.out.print("[");
            for (JValue value : content) {
                value.print();
                System.out.print(", ");
            }
            System.out.print("]");
        }
    }

    static class JString extends JValue {
        String content = "";

        @Override
        public int getIntCount() {
            return 0;
        }

        @Override
        public int getIntCountWithoutRed() {
            return 0;
        }

        @Override
        public void print() {
            System.out.print("\"" + content + "\"");
        }
    }

    static class JInteger extends JValue {
        Integer value = 0;

        @Override
        public int getIntCount() {
            return value;
        }

        @Override
        public int getIntCountWithoutRed() {
            return value;
        }

        @Override
        public void print() {
            System.out.print(value);
        }
    }

    static class Parser {
        final FileReader fileReader;
        Character lastConsumed;
        int line = 0;
        int position = 0;

        public Parser(String fileName) throws FileNotFoundException {
            File file = new File(fileName);
            fileReader = new FileReader(file.getAbsolutePath());
        }

        public Character getNextChar() throws IOException {
            int code = fileReader.read();
            if (code == -1) return null;
            lastConsumed = (char) code;
            if (lastConsumed == '\n') {
                line++;
                position = 0;
            } else
                position++;
            return this.lastConsumed;
        }

        public Character getNextNonWhitespaceChar() throws IOException {
            Character c = getNextChar();
            while (c != null) {
                if (!Character.isWhitespace(c)) break;
                c = getNextChar();
            }
            return c;
        }

        public int charAsInt(Character character) throws Exception {
            return switch (character) {
                case '1' -> 1;
                case '2' -> 2;
                case '3' -> 3;
                case '4' -> 4;
                case '5' -> 5;
                case '6' -> 6;
                case '7' -> 7;
                case '8' -> 8;
                case '9' -> 9;
                case '0' -> 0;
                default -> throw new Exception(getErrorMessage("Can not parse char"));
            };
        }

        JInteger parseInteger() throws Exception {
            JInteger integer = new JInteger();
            Character character;
            boolean isNegative = false;
            if (this.lastConsumed == null)
                throw new Exception(getErrorMessage("Reach end of file while parsing string."));
            if (this.lastConsumed == '-')
                isNegative = true;
            else if (Character.isDigit(this.lastConsumed))
                integer.value = charAsInt(this.lastConsumed);
            else
                throw new Exception(getErrorMessage("Expected digit or '-' or '-' but found " + this.lastConsumed));
            while ((character = this.getNextChar()) != null) {
                if (Character.isDigit(character))
                    integer.value = integer.value * 10 + charAsInt(character);
                else {
                    integer.value *= isNegative ? -1 : 1;
                    return integer;
                }
            }
            throw new Exception(getErrorMessage("Reach EOF while parsing int"));
        }

        JString parseString() throws Exception {
            JString string = new JString();
            Character character;
            while ((character = this.getNextChar()) != null) {
                if (character == '"')
                    return string;
                string.content += character;
            }
            throw new Exception(getErrorMessage("Reach end of file while parsing string."));
        }

        JArray parseArray() throws Exception {
            JArray array = new JArray();
            Character character;
            JValue value;
            while (true) {
                value = parseValue();
                array.content.add(value);
                if (!(value instanceof JArray)) {
                    if (this.lastConsumed == ']') return array;
                    if (this.lastConsumed == ',') continue;
                }
                character = this.getNextNonWhitespaceChar();
                if (character == ']') return array;
                if (character == ',') continue;
                throw new Exception(getErrorMessage("Expected ',' or ']' but found " + this.lastConsumed));
            }
        }

        JObject parseObject() throws Exception {
            JObject object = new JObject();
            Character character;
            String key;
            while (true) {
                character = getNextNonWhitespaceChar();
                if (character == null)
                    throw new Exception(getErrorMessage("Reach EOF while parsing object key"));
                if (character != '"')
                    throw new Exception(getErrorMessage("Expected '\"' got " + character));
                key = parseString().content;
                if (object.content.containsKey(key))
                    throw new Exception(getErrorMessage("The key " + key + " is already defined."));
                character = this.getNextNonWhitespaceChar();
                if (character == null) throw new Exception(getErrorMessage("Reach EOF while parsing object"));
                if (character != ':') throw new Exception(getErrorMessage("Expected ':' got " + character));

                JValue value = parseValue();
                object.content.put(key, value);
                if (!(value instanceof JObject)) {
                    if (this.lastConsumed == '}') return object;
                    if (this.lastConsumed == ',') continue;
                }
                character = this.getNextNonWhitespaceChar();
                if (character == '}') return object;
                if (character == ',') continue;
                throw new Exception(getErrorMessage("Expected ',' or '}' but found " + this.lastConsumed));
            }
        }

        JValue parseValue() throws Exception {
            Character character = this.getNextNonWhitespaceChar();
            if (character == null)
                throw new Exception(getErrorMessage("Reached EOF while parsing JValue."));
            if (character == '{') return parseObject();
            if (character == '[') return parseArray();
            if (character == '"') return parseString();
            if (Character.isDigit(character) || character == '-')
                return parseInteger();
            throw new IllegalStateException(getErrorMessage("Unexpected value + (" + character + ")"));
        }

        String getErrorMessage(String message) {
            return "Line " + line + " Position " + position + " : " + message;
        }
    }
}

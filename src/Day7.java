import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Day7 {

    public static int part1(String filePath) throws IOException {
        File file = new File(filePath);
        String line;
        HashMap<String, Short> map = new HashMap<>();
        ArrayList<Command> commands = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                commands.add(parseLine(line.trim()));
            }
        }
        while (!commands.isEmpty() && !map.containsKey("a")) {
            System.out.println(commands.size());
            for (int i = 0; i < commands.size(); i++) {
                if (commands.get(i) instanceof Gate gate) {
                    if (!gate.canBeEvaluated(map)) continue;
                    map.put(gate.output, gate.eval(map));
                    commands.remove(i);
                    break;
                } else if (commands.get(i) instanceof Init init) {
                    map.put(init.var, init.val);
                    commands.remove(i);
                    break;
                }
            }
        }

        return map.get("a") & 0xffff;
    }

    static Command parseLine(String line) {
        System.out.println(line);
        String[] parts = line.split(" ");
        if (line.contains("AND"))
            return new Gate(CommandType.And, parts[0], parts[2], parts[4]);
        else if (line.contains("OR"))
            return new Gate(CommandType.Or, parts[0], parts[2], parts[4]);
        else if (line.contains("LSHIFT"))
            return new Gate(CommandType.LShift, parts[0], parts[2], parts[4]);
        else if (line.contains("RSHIFT"))
            return new Gate(CommandType.RShift, parts[0], parts[2], parts[4]);
        else if (line.contains("NOT"))
            return new Gate(CommandType.Not, parts[1], "", parts[3]);
        else if (Character.isLetter(line.charAt(0)))
            return new Gate(CommandType.Assign, parts[0], "", parts[2]);
        return new Init(parts[2], Short.parseShort(parts[0]));
    }

    enum CommandType {
        And,
        Or,
        Not,
        RShift,
        LShift,
        Assign
    }

    static abstract class Command {
    }

    static class Gate extends Command {
        String left;
        String right;
        CommandType type;
        String output;

        public Gate(CommandType type, String left, String right, String output) {
            this.type = type;
            this.left = left;
            this.right = right;
            this.output = output;
        }

        public Short eval(HashMap<String, Short> map) {
            Short left = map.containsKey(this.left) ? map.get(this.left) : Short.parseShort(this.left);
            Short right = 0;
            if (this.type != CommandType.Assign && this.type != CommandType.Not)
                right = map.containsKey(this.right) ? map.get(this.right) : Short.parseShort(this.right);

            return (short) switch (this.type) {
                case And -> left & right;
                case Or -> left | right;
                case Not -> ~left;
                case RShift -> left >> right;
                case LShift -> left << right;
                case Assign -> left;
            };
        }

        public boolean canBeEvaluated(HashMap<String, Short> map) {
            if (this.type == CommandType.Not || this.type == CommandType.Assign) {
                if (map.containsKey(this.left)) return true;
                try {
                    Short.parseShort(this.left);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
            if (map.containsKey(this.left) && map.containsKey(right)) return true;
            else if (map.containsKey(this.left)) {
                try {
                    Short.parseShort(this.right);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            } else if (map.containsKey(this.right)) {
                try {
                    Short.parseShort(this.left);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            } else {
                try {
                    Short.parseShort(this.left);
                    Short.parseShort(this.right);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
    }

    static class Init extends Command {
        String var;
        Short val;

        public Init(String var, Short val) {
            this.var = var;
            this.val = val;
        }
    }
}

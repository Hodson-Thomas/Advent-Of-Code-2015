import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Day23 {
    public static int part1(String filePath) throws IOException {
        return solve(parseInput(filePath), 0, 0);
    }

    public static int part2(String filePath) throws IOException {
        return solve(parseInput(filePath), 1, 0);
    }

    static int solve(List<Instruction> program, int initialA, int initialB) {
        int a = initialA, b = initialB;
        int cursor = 0;
        Instruction instruction;
        while (cursor >= 0 && cursor < program.size()) {
            instruction = program.get(cursor);
            switch (instruction.op) {
                case HLF -> {
                    if (instruction.register.equals("a")) a /= 2;
                    else b /= 2;
                }
                case TPL -> {
                    if (instruction.register.equals("a")) a *= 3;
                    else b *= 3;
                }
                case INC -> {
                    if (instruction.register.equals("a")) a++;
                    else b++;
                }
                case JMP -> {
                    cursor += instruction.offset;
                    continue;
                }
                case JIE -> {
                    if (
                            (instruction.register.equals("a") && a % 2 == 0) ||
                                    (instruction.register.equals("b") && b % 2 == 0)
                    ) {
                        cursor += instruction.offset;
                        continue;
                    }
                }
                case JIO -> {
                    if (
                            (instruction.register.equals("a") && a == 1) ||
                                    (instruction.register.equals("b") && b == 1)
                    ) {
                        cursor += instruction.offset;
                        continue;
                    }
                }
            }
            cursor++;
        }

        return b;
    }

    static List<Instruction> parseInput(String filePath) throws IOException {
        File file = new File(filePath);
        String line;
        String[] split;
        List<Instruction> instructions = new ArrayList<Instruction>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            while ((line = br.readLine()) != null) {
                line = line.trim().replace(",", "");
                if (line.isEmpty()) continue;
                split = line.split(" ");
                switch (split[0]) {
                    case "hlf" -> instructions.add(new Instruction(Instructions.HLF, split[1], 0));
                    case "tpl" -> instructions.add(new Instruction(Instructions.TPL, split[1], 0));
                    case "inc" -> instructions.add(new Instruction(Instructions.INC, split[1], 0));
                    case "jmp" -> instructions.add(new Instruction(Instructions.JMP, "", Integer.parseInt(split[1])));
                    case "jie" ->
                            instructions.add(new Instruction(Instructions.JIE, split[1], Integer.parseInt(split[2])));
                    case "jio" ->
                            instructions.add(new Instruction(Instructions.JIO, split[1], Integer.parseInt(split[2])));
                }
            }
            return instructions;
        }
    }

    enum Instructions {
        HLF,
        TPL,
        INC,
        JMP,
        JIE,
        JIO
    }

    static class Instruction {
        Instructions op;
        String register;
        int offset;

        public Instruction(Instructions op, String register, int offset) {
            this.op = op;
            this.register = register;
            this.offset = offset;
        }
    }
}

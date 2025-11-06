import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Day16 {
    static int part1(String fileName) throws IOException {
        List<Sue> sues = parseInput(fileName);
        Sue present = new Sue(null, 3, 7, 2, 3, 0, 0, 5, 3, 2, 1);
        Sue sue = (Sue) sues.stream().filter(s -> s.matchPart1(present)).toArray()[0];
        return sue.number;
    }

    static int part2(String fileName) throws IOException {
        List<Sue> sues = parseInput(fileName);
        Sue present = new Sue(null, 3, 7, 2, 3, 0, 0, 5, 3, 2, 1);
        Sue sue = (Sue) sues.stream().filter(s -> s.matchPart2(present)).toArray()[0];
        return sue.number;
    }

    static List<Sue> parseInput(String fileName) throws IOException {
        File file = new File(fileName);
        String line;
        String[] split, split2;
        List<Sue> sues = new ArrayList<Sue>();
        Sue temp;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                line = line.replaceAll(",", "");
                split = line.split(" ");
                temp = new Sue(Integer.parseInt(split[1].substring(0, split[1].length()-1)));
                for (int i = 2; i < split.length; i++) {
                    if (split[i].equals("children:")) {
                        temp.children = Integer.parseInt(split[i+1]);
                        i++;
                    }
                    else if (split[i].equals("cats:")) {
                        temp.cats = Integer.parseInt(split[i+1]);
                        i++;
                    }
                    else if (split[i].equals("samoyeds:")) {
                        temp.samoyeds = Integer.parseInt(split[i+1]);
                        i++;
                    }
                    else if (split[i].equals("pomeranians:")) {
                        temp.pomeranians = Integer.parseInt(split[i+1]);
                        i++;
                    }
                    else if (split[i].equals("akitas:")) {
                        temp.akitas = Integer.parseInt(split[i+1]);
                        i++;
                    }
                    else if (split[i].equals("vizslas:")) {
                        temp.vizslas = Integer.parseInt(split[i+1]);
                        i++;
                    }
                    else if (split[i].equals("goldfish:")) {
                        temp.goldfish = Integer.parseInt(split[i+1]);
                        i++;
                    }
                    else if (split[i].equals("trees:")) {
                        temp.trees = Integer.parseInt(split[i+1]);
                        i++;
                    }
                    else if (split[i].equals("cars:")) {
                        temp.cars = Integer.parseInt(split[i+1]);
                        i++;
                    }
                    else if (split[i].equals("perfumes:")) {
                        temp.perfumes = Integer.parseInt(split[i+1]);
                        i++;
                    }
                }
                sues.add(temp);
            }
        }

        return sues;
    }


    static class Sue {
        public Integer number;
        public Integer children;
        public Integer cats;
        public Integer samoyeds;
        public Integer pomeranians;
        public Integer akitas;
        public Integer vizslas;
        public Integer goldfish;
        public Integer trees;
        public Integer cars;
        public Integer perfumes;

        public Sue(
            Integer number, Integer children, Integer cats, Integer samoyeds, Integer pomeranians, Integer akitas,
            Integer vizslas, Integer goldfish, Integer trees, Integer cars, Integer perfumes
        ) {
            this.number = number;
            this.children = children;
            this.cats = cats;
            this.samoyeds = samoyeds;
            this.pomeranians = pomeranians;
            this.akitas = akitas;
            this.vizslas = vizslas;
            this.goldfish = goldfish;
            this.trees = trees;
            this.cars = cars;
            this.perfumes = perfumes;
        }

        public Sue(Integer number) {
            this.number = number;
            this.children = null;
            this.cats = null;
            this.samoyeds = null;
            this.pomeranians = null;
            this.akitas = null;
            this.vizslas = null;
            this.goldfish = null;
            this.trees = null;
            this.cars = null;
            this.perfumes = null;
        }

        public boolean matchPart1(Sue present) {
            if (this.children != null && !this.children.equals(present.children)) return false;
            if (this.cats != null && !this.cats.equals(present.cats)) return false;
            if (this.samoyeds != null && !this.samoyeds.equals(present.samoyeds)) return false;
            if (this.pomeranians != null && !this.pomeranians.equals(present.pomeranians)) return false;
            if (this.akitas != null && !this.akitas.equals(present.akitas)) return false;
            if (this.vizslas != null && !this.vizslas.equals(present.vizslas)) return false;
            if (this.goldfish != null && !this.goldfish.equals(present.goldfish)) return false;
            if (this.trees != null && !this.trees.equals(present.trees)) return false;
            if (this.cars != null && !this.cars.equals(present.cars)) return false;
            if (this.perfumes != null && !this.perfumes.equals(present.perfumes)) return false;
            return true;
        }

        public boolean matchPart2(Sue present) {
            if (this.children != null && !this.children.equals(present.children)) return false;
            if (this.cats != null && this.cats <= present.cats) return false;
            if (this.samoyeds != null && !this.samoyeds.equals(present.samoyeds)) return false;
            if (this.pomeranians != null && this.pomeranians >= present.pomeranians) return false;
            if (this.akitas != null && !this.akitas.equals(present.akitas)) return false;
            if (this.vizslas != null && !this.vizslas.equals(present.vizslas)) return false;
            if (this.goldfish != null && this.goldfish >= present.goldfish) return false;
            if (this.trees != null && this.trees <= present.trees) return false;
            if (this.cars != null && !this.cars.equals(present.cars)) return false;
            if (this.perfumes != null && !this.perfumes.equals(present.perfumes)) return false;
            return true;
        }
    }
}

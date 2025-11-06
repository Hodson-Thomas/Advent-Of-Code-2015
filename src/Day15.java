public class Day15 {
    static int TOTAL = 100;
    static int TARGET = 500;

    static Ingredient FROSTING = new Ingredient(4, -2, 0, 0, 5);
    static Ingredient CANDY = new Ingredient(0, 5, -1, 0, 8);
    static Ingredient BUTTERSCOTCH = new Ingredient(-1, 0, 5, 0, 6);
    static Ingredient SUGAR = new Ingredient(0, 0, -2, 2, 1);

    public static int part1() {
        int max = 0;
        for (int frosting = 0; frosting <= TOTAL; frosting++) {
            for (int candy = 0; candy <= TOTAL - frosting; candy++) {
                for (int butterscotch = 0; butterscotch <= TOTAL - frosting - candy; butterscotch++) {
                    max = Math.max(max, getScore(frosting, candy, butterscotch, TOTAL - frosting - candy - butterscotch));
                }
            }
        }
        return max;
    }

    public static int part2() {
        int max = 0;
        for (int frosting = 0; frosting <= TOTAL; frosting++) {
            for (int candy = 0; candy <= TOTAL - frosting; candy++) {
                for (int butterscotch = 0; butterscotch <= TOTAL - frosting - candy; butterscotch++) {
                    if (isTargetedCalories(frosting, candy, butterscotch, TOTAL - frosting - candy - butterscotch))
                        max = Math.max(max, getScore(frosting, candy, butterscotch, TOTAL - frosting - candy - butterscotch));
                }
            }
        }
        return max;
    }

    static int getScore(int frosting, int candy, int butterscotch, int sugar) {
        int capacity = frosting * FROSTING.capacity + candy * CANDY.capacity
                + butterscotch * BUTTERSCOTCH.capacity + sugar * SUGAR.capacity;
        int durability = frosting * FROSTING.durability + candy * CANDY.durability
                + butterscotch * BUTTERSCOTCH.durability + sugar * SUGAR.durability;
        int flavor = frosting * FROSTING.flavor + candy * CANDY.flavor
                + butterscotch * BUTTERSCOTCH.flavor + sugar * SUGAR.flavor;
        int texture = frosting * FROSTING.texture + candy * CANDY.texture
                + butterscotch * BUTTERSCOTCH.texture + sugar * SUGAR.texture;
        return Math.max(0, capacity) * Math.max(0, flavor) * Math.max(0, texture) * Math.max(0, durability);
    }

    static boolean isTargetedCalories(int frosting, int candy, int butterscotch, int sugar) {
        return frosting * FROSTING.calories + candy * CANDY.calories
                + butterscotch * BUTTERSCOTCH.calories + sugar * SUGAR.calories == TARGET;
     }

    static class Ingredient {
        public int capacity;
        public int durability;
        public int flavor;
        public int texture;
        public int calories;

        public Ingredient(int capacity, int durability, int flavor, int texture, int calories) {
            this.capacity = capacity;
            this.durability = durability;
            this.flavor = flavor;
            this.texture = texture;
            this.calories = calories;
        }
    }
}

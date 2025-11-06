public class Day21 {

    static Item[] WEAPONS = {
        new Item(8, 4, 0),
        new Item(10, 5, 0),
        new Item(25, 6, 0),
        new Item(40, 7, 0),
        new Item(74, 8, 0),
    };

    static Item[] ARMORS = {
        new Item(0, 0, 0),
        new Item(13, 0, 1),
        new Item(31, 0, 2),
        new Item(53, 0, 3),
        new Item(75, 0, 4),
        new Item(102, 0, 5),
    };

    static Item[] RINGS = {
        new Item(0, 0, 0),
        new Item(25, 1, 0),
        new Item(50, 2, 0),
        new Item(100, 3, 0),
        new Item(20, 0, 1),
        new Item(40, 0, 2),
        new Item(80, 0, 3),
    };

    static Boss BOSS = new Boss(100, 8, 2);

    static int HEALTH = 100;

    public static int part1() {
        int min = Integer.MAX_VALUE;
        for (Item item : WEAPONS) {
            for (Item value : ARMORS) {
                for (int ring1 = 0; ring1 < RINGS.length; ring1++) {
                    for (int ring2 = 0; ring2 < RINGS.length; ring2++) {
                        if (ring1 == ring2 && ring1 != 0) continue;
                        if (!isSetupValid(
                                RINGS[ring1].damage + RINGS[ring2].damage + item.damage,
                                RINGS[ring1].armor + RINGS[ring2].armor + value.armor
                        )) continue;
                        min = Math.min(min, RINGS[ring1].cost + RINGS[ring2].cost + value.cost + item.cost);
                    }
                }
            }
        }
        return min;
    }

    public static int part2() {
        int max = 0;
        for (Item item : WEAPONS) {
            for (Item value : ARMORS) {
                for (int ring1 = 0; ring1 < RINGS.length; ring1++) {
                    for (int ring2 = 0; ring2 < RINGS.length; ring2++) {
                        if (ring1 == ring2 && ring1 != 0) continue;
                        if (isSetupValid(
                                RINGS[ring1].damage + RINGS[ring2].damage + item.damage,
                                RINGS[ring1].armor + RINGS[ring2].armor + value.armor
                        )) continue;
                        max = Math.max(max, RINGS[ring1].cost + RINGS[ring2].cost + value.cost + item.cost);
                    }
                }
            }
        }
        return max;
    }

    static boolean isSetupValid(int damages, int armor) {
        int playerHealth = HEALTH;
        int bossHealth = BOSS.health;
        int playerDamages = Math.max(1, damages - BOSS.armor);
        int bossDamages = Math.max(1, BOSS.damage - armor);
        while (playerHealth >= 0) {
            bossHealth -= playerDamages;
            if (bossHealth <= 0) return true;
            playerHealth -= bossDamages;
        }
        return false;
    }

    record Boss(int health, int damage, int armor) {}

    record Item(int cost, int damage, int armor) {}
}

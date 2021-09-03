package controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

public class DunGen {

    /*
    Generation Method 1: <--- probably scrapping this
    Let Starting Room be the root "node"
    Exits to the starting room point towards its children
    Exits in the children point to their children, etc.
    Figure out a stopping condition for generation
            (depth based RNG? higher = more likely to stop until a limit?)
    PROBLEM: Room generation will not create cycles
            (can probably figure this out later)
        - Will need to figure out how to generate cycles spanning multiple branches.
                Maybe tree isn't the best structure?
        - Connections have to make sense. Can't have rooms on opposite ends of the dungeon connected

    Generation Method 2: <--- might be best for pathing to boss room
    Have an n by n array where each cell represents a Room
    Start at (a, b) and navigate to (c, d)
    Use A* search to find path between start and end
    Edit heuristic weights or costs to create more "dungeony" paths

    Generation Method 3: <--- currently using this one
    Have an n by n array where each cell represents a Room
    Start with a random point on each of the sides of the array
    Use A* search to navigate from the four side points to the start
    Vary weights and costs accordingly

    Figure out how to associate clicking the exit button with moving to another
    room. Also how to indicate the room change visually in the scene.
     */

    private final int seed;
    private final int height;
    private final int rows;
    private final int cols;
    private final char[][][] dungeon;
    private final double[][] costs;
    private final Random rng;
    private final Point start;
    private final List<Point> entrances;
    private final List<Point> exits;

    public DunGen(int height, int rows, int cols, int r, int c, int seed) {
        this.height = height;
        this.rows = rows;
        this.cols = cols;
        dungeon = new char[height][rows][cols];
        costs = new double[rows][cols];
        entrances = new ArrayList<Point>();
        exits = new ArrayList<Point>();

        start = new Point(r, c);

        this.seed = seed;
        rng = new Random(seed);
    }

    /*
    Modify cost distribution to change dungeon structure.
    TOAD: Modify to create more "dungeony" weight distribution
     */
    private void initCosts() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                costs[i][j] = rng.nextDouble();
                // costs[i][j] = rng.nextDouble() - 0.10; // May lead to negative cycles
            }
        }
    }

    public char[][][] generateDungeon() {
        initCosts();
        generatePointToEdge(start, 0);
        /*for (int h = 1; h < height - 1; h++) {
            TOAD: Point to point generation from {entrances} to random {exits}
            New cost array every floor
        }*/
        if (height > 1) {
            initCosts();
            for (Point p : entrances) {
                generatePointToPoint(p, start, height - 1);
            }
        }

        dungeon[0][start.r][start.c] = '$';
        dungeon[height - 1][start.r][start.c] = '#';
        return dungeon;
    }

    public void generatePointToEdge(Point from, int h) {
        exits.add(new Point(0, (int) (rng.nextDouble() * (cols - 2)) + 1));
        exits.add(new Point(rows - 1, (int) (rng.nextDouble() * (cols - 2)) + 1));
        exits.add(new Point((int) (rng.nextDouble() * (rows - 2)) + 1, 0));
        exits.add(new Point((int) (rng.nextDouble() * (rows - 2)) + 1, cols - 1));

        for (int i = 0; i < exits.size(); i++) {
            generatePointToPoint(from, exits.get(i), h);
        }

        entrances.addAll(exits);
        exits.clear();
    }

    private void generatePointToPoint(Point from, Point to, int h) {
        PriorityQueue<Point> queue = new PriorityQueue<Point>(new PointComparator());
        queue.add(from);
        costs[from.r][from.c] = 1000;
        boolean[][] closed = new boolean[rows][cols];

        while (!queue.isEmpty()) {
            Point p = queue.poll();
            int r = p.r;
            int c = p.c;
            double g = p.g;
            dungeon[h][r][c] = '*';
            closed[r][c] = true;
            if (isExit(r, c, to)) {
                break;
            } else {
                if (r > 0 && !closed[r - 1][c]) {
                    queue.add(new Point(r - 1, c, g + costs[r - 1][c], calcH(r - 1, c, to)));
                }
                if (r < rows - 1 && !closed[r + 1][c]) {
                    queue.add(new Point(r + 1, c, g + costs[r + 1][c], calcH(r + 1, c, to)));
                }
                if (c > 0 && !closed[r][c - 1]) {
                    queue.add(new Point(r, c - 1, g + costs[r][c - 1], calcH(r, c - 1, to)));
                }
                if (c < cols - 1 && !closed[r][c + 1]) {
                    queue.add(new Point(r, c + 1, g + costs[r][c + 1], calcH(r, c + 1, to)));
                }
            }
        }

        dungeon[h][to.r][to.c] = '!';
    }

    /*
    Checks whether or not current point is the exit
     */
    private boolean isExit(int r, int c, Point p) {
        return r == p.r && c == p.c;
    }

    /*
    Modify the heuristic function to get different pathing behavior
    Currently set to Manhattan distance.
     */
    private double calcH(int r, int c, Point p) {
        return Math.abs(r - p.r) + Math.abs(c - p.c);
    }

    public void printDungeon() {
        for (int h = 0; h < height; h++) {
            System.out.println("Level " + h);
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (dungeon[h][r][c] == '\0') {
                        System.out.print(". ");
                    } else {
                        System.out.print(dungeon[h][r][c] + " ");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {

        int seed = (int) (Math.random() * 1000);
        System.out.println("Seed: " + seed);

        DunGen rn = new DunGen(2, 9, 9,
            4, 4, seed);
        //  RoomNav rn = new RoomNav(15, 15,
        //          7, 7, seed); // bigger room (more blobby if hweight is low)

        rn.generateDungeon();
        rn.printDungeon();

    }


    static class Point {

        private final int r;
        private final int c;
        private final double f;
        private final double g;
        private final double h;

        // Heuristic weight. Increase for less branching, decrease for more.
        private static final double HEURISTIC_WEIGHT = 0.33;

        public Point(int r, int c) {
            this.r = r;
            this.c = c;
            g = 0;
            h = 0;
            f = 0;
        }

        public Point(int r, int c, double g, double h) {
            this.r = r;
            this.c = c;
            this.g = g;
            this.h = h;
            f = g + HEURISTIC_WEIGHT * h;
        }

        public boolean equals(Point p) {
            return r == p.r && c == p.c;
        }

        public String toString() {
            return "(" + r + ", " + c + ")";
        }
    }

    class PointComparator implements Comparator<Point> {
        @Override
        public int compare(Point p1, Point p2) {
            return Double.compare(p1.f, p2.f);
        }
    }

}

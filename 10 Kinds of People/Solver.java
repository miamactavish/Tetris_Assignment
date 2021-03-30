import java.util.ArrayList;

public class Solver {

    final boolean DRAW_MODE = false;

    // Cell represents one individual cell of
    // our map
    public class Cell {
        int row;
        int col;

        int value;

        boolean visited = false;
        boolean reachable = false;

        public Cell(int r, int c, int v) {
            row = r;
            col = c;
            value = v;
        }

        public boolean equals(Cell other) {
            if (this.row == other.row && this.col == other.col) {
                return true;
            }
            return false;
        }

        // Automatic toString() method
        public String toString() {
            return ("Cell at row: " + row + ", column: " + col);
        }
    }

    Cell[][] map;
    Cell start;
    Cell goal;

    int num_rows;
    int num_cols;

    ArrayList<Cell> reachableCells;

    MyGraphics graphics;

    public Solver(int[][] m) {
        
        num_rows = m.length;
        num_cols = m[0].length;
        map = new Cell[num_rows][num_cols];

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                map[i][j] = new Cell(i, j, m[i][j]);
            }
        }

        reachableCells = new ArrayList<Cell>();
        graphics = new MyGraphics(this, DRAW_MODE);
    }

    public void solve(int r1, int c1, int r2, int c2) 
    {
        start = map[r1 - 1][c1 - 1];
        goal = map[r2 - 1][c2 - 1];
        
        Cell current = start;
        current.reachable = true;

        boolean finished = false;
        boolean solvable = false;

        graphics.updateBoard();

        // Check for an edge case - start and goal cells are the same cell
        if (start.equals(goal)) {
            finished = true;
            solvable = true;
        }

        while (!finished) {

            // take the current cell and mark all of its neighbors (not including diagonal) as "reachable"

            // Mark the north neighbor
            markNeighbor(current, -1, 0);
            // South neighbor
            markNeighbor(current, 1, 0);
            // East neighbor
            markNeighbor(current, 0, 1);
            // West neighbor
            markNeighbor(current, 0, -1);

            // Also tag the current cell as "visited"
            current.visited = true;
            graphics.updateBoard();

            //System.out.println(reachableCells);

            // Go to one of the neighbors marked as reachable, and make that the current cell
            current = getNeighbor(current);

            // repeat these steps until:
            // There are no more reachable cells left to visit
            // (This means we've already traversed the entire reachable region from the starting cell)
            if (current == null) {
                finished = true;
            }
            // We reach the goal cell
            else if (current.equals(goal)) {
                finished = true;
                solvable = true;
            }
        }

        if (solvable) {
            if (goal.value == 0) {
                //System.out.println("binary");
            }
            else {
                //System.out.println("decimal");
            }
        }
        else {
            //System.out.println("neither");
        }
    }

    public Cell getNeighbor(Cell current) {
        if (reachableCells.size() >= 1) {
            // Set the most recently added reachable cell as our new current cell
            return reachableCells.remove(reachableCells.size() - 1);
        }
        return null;
    }

    public void markNeighbor(Cell current, int rowInc, int colInc) {
        // Make sure this neighboring cell is within the bounds of the map
        if (current.row + rowInc < 0 || current.row + rowInc >= num_rows) {
            return;
        }
        if (current.col + colInc < 0 || current.col + colInc >= num_cols) {
            return;
        }
        
        Cell c = map[current.row + rowInc][current.col + colInc];

        // Make sure both cells are part of the same region (both 0's or both 1's)
        if (c.value == current.value && !c.visited) {
            c.reachable = true;
            reachableCells.add(c);
            graphics.updateBoard();
        }
    }

    Cell get(int row, int col) {
        return map[row][col];
    }

    void clear() {
        
        reachableCells.clear();

        for (Cell[] row : map) {
            for (Cell cell : row) {
                cell.visited = false;
                cell.reachable = false;
            }
        }
    }
}

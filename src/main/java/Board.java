import java.util.ArrayList;
import java.util.Random;

public class Board {
    private Cell[][] grid;
    private ArrayList<Ship> ships;
    private int totalHits = 0;
    private int totalMisses = 0;

    public Board() {
        grid = new Cell[10][10];
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                grid[r][c] = new Cell(r, c);
            }
        }
        ships = new ArrayList<>();
        placeShips();
    }

    public Cell getCell(int r, int c) {
        return grid[r][c];
    }

    public int getTotalHits() { return totalHits; }
    public int getTotalMisses() { return totalMisses; }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    private void placeShips() {
        int[] sizes = {5, 4, 3, 3, 2};
        Random rand = new Random();

        for (int size : sizes) {
            boolean placed = false;
            while (!placed) {
                boolean horizontal = rand.nextBoolean();
                int row = rand.nextInt(10);
                int col = rand.nextInt(10);
                if (canPlaceShip(row, col, size, horizontal)) {
                    Ship s = new Ship(size);
                    for (int i = 0; i < size; i++) {
                        int r = row + (horizontal ? 0 : i);
                        int c = col + (horizontal ? i : 0);
                        grid[r][c].setShip(s);
                        s.addCell(grid[r][c]);
                    }
                    ships.add(s);
                    placed = true;
                }
            }
        }
    }

    private boolean canPlaceShip(int row, int col, int size, boolean horizontal) {
        if (horizontal)
        {
            if (col + size > 10) return false;
            for (int i = 0; i < size; i++) {
                if (grid[row][col + i].hasShip()) return false;
            }
        }
        else
        {
            if (row + size > 10) return false;
            for (int i = 0; i < size; i++) {
                if (grid[row + i][col].hasShip()) return false;
            }
        }
        return true;
    }

    public FireState fireAt(int row, int col) {
        Cell target = grid[row][col];
        if (target.getState() != CellState.BLANK) return null;

        if (target.hasShip())
        {
            target.setState(CellState.HIT);
            totalHits++;

            Ship hitShip = target.getShip();
            if (hitShip.isSunk()) {
                if (allSunk())
                {
                    return FireState.WIN;
                }
                return FireState.SUNK;
            }
            return FireState.HIT;
        }
        else
        {
            target.setState(CellState.MISS);
            totalMisses++;
            return FireState.MISS;
        }
    }

    private boolean allSunk() {
        for (Ship s : ships) {
            if (!s.isSunk()) return false;
        }
        return true;
    }
}


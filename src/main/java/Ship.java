import java.util.ArrayList;

public class Ship {

    private int size;
    private ArrayList<Cell> cells;

    public Ship(int size)
    {
        this.size = size;
        this.cells = new ArrayList<>();
    }

    public void addCell(Cell c)
    {
        cells.add(c);
    }

    public boolean isSunk()
    {
        for (Cell c : cells)
        {
            if (c.getState() != CellState.HIT)
            {
                return false;
            }
        }
        return true;
    }

    public int getSize()
    {
        return size;
    }
}


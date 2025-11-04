public class Cell {
    private int row;
    private int col;
    private CellState state;
    private Ship ship;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.state = CellState.BLANK;
        this.ship = null;
    }
    public boolean hasShip()
    {
        return ship != null;
    }

    public void setShip(Ship ship)
    {
        this.ship = ship;
    }

    public Ship getShip()
    {
        return ship;
    }

    public CellState getState()
    {
        return state;
    }

    public void setState(CellState state)
    {
        this.state = state;
    }
}

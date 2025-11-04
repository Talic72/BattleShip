public class BSGame {
    private Board board;
    private int missCounter = 0;
    private int strikeCounter = 0;

    public BSGame() {
        board = new Board();
    }

    public Board getBoard() {
        return board;
    }

    public String handleFire(int row, int col) {
        FireState result = board.fireAt(row, col);
        if (result == null) return "Already fired";

        if (result == FireState.HIT || result == FireState.SUNK || result == FireState.WIN)
        {
            missCounter = 0;
        }
        else if (result == FireState.MISS)
        {
            missCounter++;
            if (missCounter >= 5)
            {
                strikeCounter++;
                missCounter = 0;
            }
        }

        if (strikeCounter >= 3) {
            return "LOSS";
        }

        return result.toString();
    }

    public int getTotalMisses() { return board.getTotalMisses(); }
    public int getTotalHits() { return board.getTotalHits(); }
    public int getMissCounter() { return missCounter; }
    public int getStrikeCounter() { return strikeCounter; }
}
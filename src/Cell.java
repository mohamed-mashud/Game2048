class Cell {
    private int row;
    private int col;

    Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {return row;}
    public int getCol() {return col;}

    public void setRow(int row) {
        this.row = row;
    }
    public void setCol(int col) {
        this.col = col;
    }
}

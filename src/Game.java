import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private final String[][] board;
    private final int BOARD_SIZE;
    private final int WINING_NUMBER;
    private boolean quitByUser;

    Game() {
        BOARD_SIZE = 4;
        WINING_NUMBER = 2048;
        board = new String[BOARD_SIZE][BOARD_SIZE];
        populateBoard();
    }


    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        // fills the first value for the game
        fillARandomCell();fillARandomCell();
        System.out.println("here");
        System.out.println(this);
        while(true) {
            System.out.println("""
                    Moves:
                        0. Move the board left
                        1. Move the board right
                        2. Move the board top
                        3. Move the board bottom
                        4. Exit
                    """);
            int direction = scanner.nextInt();
            boolean move = moveBoard(direction);
            System.out.println(this);
            if(!move) {
                if(!quitByUser)
                    System.out.println("Game Over -_- ");
                else
                    System.out.println("Exiting.....");
                break;
            }
        }
    }

    private boolean moveBoard(int direction) {
        switch (direction) {
            case 0 -> moveBoardLeft();
            case 1 -> moveBoardRight();
            case 2 -> moveBoardTop();
            case 3 -> moveBoardBottom();
            case 4 -> {
                quitByUser = true;
                return false;
            }
            default -> System.out.println("Invalid direction");
        }
        return fillARandomCell();
    }

    private String getFormatedValue(String value) {
        int n = value.length();
        int winingNumberLength = getNumberLength(WINING_NUMBER);
        int spacesToAdd        = winingNumberLength - n;
        int spacesAtFront      = spacesToAdd / 2;
        int spacesAtBack       = spacesToAdd - spacesAtFront - 1;
        return " ".repeat(Math.max(0, spacesAtFront)) +
                value + " ".repeat(Math.max(0, spacesAtBack));
    }

    private void moveBoardLeft() {
        for(int i = 0; i < BOARD_SIZE; i++) {
            String[] currRow = board[i];
            for(int j = 0; j < BOARD_SIZE; j++) {
                String currValue = currRow[j];
                if(currValue.equals(getHyphen()))   continue;
                for (int k = j + 1; k < BOARD_SIZE; k++) {
                    if(currRow[k].equals(getHyphen()))   continue;

                    if(currRow[k].equals(currValue)) {
                        currRow[j] = getNextValue(currRow[j]);
                        currRow[k] = getHyphen();
                    }
                    break;
                }
            }

            moveNumbersLeft(currRow);
        }
    }

    private void moveNumbersLeft(String[] row) {
        int write = 0;
        for(int read = 0; read < BOARD_SIZE; read++)
            if(!row[read].equals(getHyphen()))
                row[write++] = row[read];

        while(write < BOARD_SIZE)       row[write++] = getHyphen();
    }

    private void moveBoardRight() {
        for(int i = 0; i < BOARD_SIZE; i++) {
            String[] currRow = board[i];
            for (int j = BOARD_SIZE - 1; j > 0; j--) {
                String currValue = currRow[j];
                if(currValue.equals(getHyphen()))   continue;
                for(int k = j - 1; k >= 0; k--) {
                    if(currRow[k].equals(getHyphen()))    continue;

                    if(currRow[k].equals(currValue)) {
                        currRow[j] = getNextValue(currRow[j]);
                        currRow[k] = getHyphen();
                    }
                    break;
                }
            }
            moveNumberRight(board[i]);
        }
    }

    private void moveBoardLeft(String[] currCol) {
        for(int j = 0; j < BOARD_SIZE; j++) {
            String currValue = currCol[j];
            if(currValue.equals(getHyphen()))   continue;
            for (int k = j + 1; k < BOARD_SIZE; k++) {
                if(currCol[k].equals(getHyphen()))   continue;

                if(currCol[k].equals(currValue)) {
                    currCol[j] = getNextValue(currCol[j]);
                    currCol[k] = getHyphen();
                }
                break;
            }
        }

        moveNumbersLeft(currCol);
    }

    private void moveNumberRight(String[] row) {
        int write = BOARD_SIZE - 1;

        for (int read = BOARD_SIZE - 1; read >= 0; read--)
            if (!row[read].equals(getHyphen()))
                row[write--] = row[read];

        while (write >= 0)     row[write--] = getHyphen();
    }


    private void moveBoardTop() {
        for (int j = 0; j < BOARD_SIZE; j++) {
            String[] currCol = new String[BOARD_SIZE];
            for (int i = 0; i < BOARD_SIZE - 1; i++) {
                currCol[i] = board[i][j];
            }

            moveBoardLeft(currCol);
        }
    }

    private void moveBoardBottom() {
        for (int j = 0; j < BOARD_SIZE; j++){
            for(int i = BOARD_SIZE - 1; i >= 0; i--) {

            }
        }
    }

    private boolean fillARandomCell() {
        ArrayList<Cell> emptyCells = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++)
            for (int j = 0; j < BOARD_SIZE; j++)
                if(board[i][j].equals(getHyphen()))
                    emptyCells.add(new Cell(i, j));

        if(emptyCells.isEmpty())    return false;

        int randomIndex = (int) (Math.random() * emptyCells.size());
        int randomRow = emptyCells.get(randomIndex).getRow();
        int randomCol = emptyCells.get(randomIndex).getCol();
        System.out.println(randomRow + "  " +randomCol + "  " + board[randomRow][randomCol]);
        board[randomRow][randomCol] = getFormatedValue("2");
        return true;
    }

    private void populateBoard() {
        for(int i = 0; i < BOARD_SIZE; i++)
            for(int j = 0; j < BOARD_SIZE; j++)
                board[i][j] = "2";
        board[0] = new String[] {
                "8", "4", getHyphen(), "4"
        };
    }

    private int getNumberLength(int number) {
//        System.out.println(number + " " + ((int)Math.log10(number) + 1));
        return (int)Math.log10(number) + 1;
    }

    private String getHyphen() {
        int totalLength = getNumberLength(WINING_NUMBER);
        int spaceAtFront = totalLength / 2;
        int spacesAtBack = totalLength - spaceAtFront - 1;
        if(spacesAtBack == -1 || spaceAtFront == -1)
            System.out.println("form getHyphen" + spacesAtBack + " " + spaceAtFront);
        return " ".repeat(spaceAtFront) + "-" + " ".repeat(spacesAtBack);
    }

    private String getNextValue(String value) {
        return getFormatedValue(
                String.valueOf(
                        Integer.parseInt(value.trim()) * 2
                ));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------\n");
        // TODO : MAKE THE FORMATING LOOK GUD.
        for(String[] row : board) {
            for(String value : row) {
                sb.append(getFormatedValue(value));
            }
            sb.append("\n");
        }
        sb.append("-------------------------");
        return sb.toString();
    }
}

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
    private final String[][] board;
    private final int BOARD_SIZE;
    private final int WINING_NUMBER;
    private boolean quitByUser;
    private boolean won;
    private static Game game = null;

    private Game() {
        BOARD_SIZE = 4;
        WINING_NUMBER = 2048;
        board = new String[BOARD_SIZE][BOARD_SIZE];
        populateBoard();
    }

    public static synchronized Game getInstance() {
        if(game == null)    game = new Game();
        return game;
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        int direction;
        // fills two values for the starting game
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
            try {
                direction = scanner.nextInt();
            } catch(InputMismatchException e) {
                System.out.println("Input Mismatch, Re-Enter");
                scanner.nextLine(); // add the input mismatch to buffer
                continue;
            }
            boolean move = moveBoard(direction);
            if(quitByUser) {
                System.out.println("Exiting.....");
                break;
            }
            System.out.println(this);
            if(won) {
                System.out.println("Congratulations, You are the first person to won " +
                                    "the game made by Masood...  Here is a gift 🍫\n" +
                                    "PS: you cant eat it cuz its digital");
                break;
            }
            if(!move) {
                System.out.println("Game Over -_- ");
                break;
            }
        }
    }

    /*
    * Moves the board based on the user input
    * returns true, if there is an empty cell
    * left to fill after the move.
    * */
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

    /*
    * Returns a formatted String based on
    * the WINING_NUMBER length
    *  */
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

    private void moveBoardRight(String[] currCol) {
        for (int j = BOARD_SIZE - 1; j > 0; j--) {
            String currValue = currCol[j];
            if(currValue.equals(getHyphen()))   continue;
            for(int k = j - 1; k >= 0; k--) {
                if(currCol[k].equals(getHyphen()))    continue;

                if(currCol[k].equals(currValue)) {
                    currCol[j] = getNextValue(currCol[j]);
                    currCol[k] = getHyphen();
                }
                break;
            }
        }
        moveNumberRight(currCol);
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
            for (int i = 0; i < BOARD_SIZE; i++) {
                currCol[i] = board[i][j];
            }
//            System.out.println(Arrays.toString(currCol));
            moveBoardLeft(currCol);
//            System.out.println("After \n" + Arrays.toString(currCol));
            for(int i = 0; i < BOARD_SIZE; i++) {
                board[i][j] = currCol[i];
            }
        }
    }

    private void moveBoardBottom() {
        for (int j = 0; j < BOARD_SIZE; j++){
            String[] currCol = new String[BOARD_SIZE];
            for(int i = 0; i < BOARD_SIZE; i++) {
                currCol[i] = board[i][j];
            }

            moveBoardRight(currCol);

            for(int i = 0; i < BOARD_SIZE; i++) {
                board[i][j] = currCol[i];
            }
        }
    }

    /*
        return true if there is an empty cell else returns false
        TODO: ADD PROBABILITY FOR OCCURRENCE OF 2 AND 4
     */
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
        System.out.println("Empty Cell : " + randomRow + "  " + randomCol + "  " + board[randomRow][randomCol]);
        board[randomRow][randomCol] = getFormatedValue("2");
        return true;
    }

    /*
        generate hyphen spaces for all the cells
    */
    private void populateBoard() {
        for(int i = 0; i < BOARD_SIZE; i++)
            for(int j = 0; j < BOARD_SIZE; j++)
                board[i][j] = getHyphen();
    }

    private int getNumberLength(int number) {
        int spaceAround = 2;
        return (int)Math.log10(number) + 1 + spaceAround;
    }

    private String getHyphen() {
        int winingNumberLength = getNumberLength(WINING_NUMBER);
        int spacesToAdd        = winingNumberLength - 1;
        int spacesAtFront      = spacesToAdd / 2;
        int spacesAtBack       = spacesToAdd - spacesAtFront - 1;
        return " ".repeat(Math.max(0, spacesAtFront)) +
                "-" + " ".repeat(Math.max(0, spacesAtBack));
    }

    private String getNextValue(String value) {
        return getFormatedValue(
                String.valueOf(
                        Integer.parseInt(value.trim()) * 2
                ));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-".repeat((getNumberLength(WINING_NUMBER) + 4) * 2)).append("\n");
        for(String[] row : board) {
            for(String value : row) {
                if(value.equals(getFormatedValue(String.valueOf(WINING_NUMBER)))) {
                    won = true;
                }
                sb.append(value);
            }
            sb.append("\n");
        }
        sb.append("-".repeat((getNumberLength(WINING_NUMBER) + 4) * 2));
        return sb.toString();
    }
}

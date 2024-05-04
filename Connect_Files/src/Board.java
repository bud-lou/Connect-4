import java.util.Arrays;
import java.util.Scanner;

public class Board {
    /** Player 1's Color */
    public static final String yellow = "\u001B[34m";
    
    /** Player 2's Color */
    public static final String blue = "\u001B[33m";

    /** Reset Color */
    public static final String white = "\u001B[37m";
    
    /** Number of Columns in Board */
    public static final int NUM_COLS = 7;

    /** Number of Rows in Board */
    public static final int NUM_ROWS = 6;

    /** Number of Consecutive Tokens Needed To Win */
    public static final int NUM_CONSECUTIVE = 4;

    /** Array of Each Player's Color */
    public static String[] colors = {yellow, blue};

    /** Current Player's Turn - Starts on player 1 */
    public enum Players {
        /** Player 1 and their index */
        ONE(0), 
        /** Player 2 and their index */
        TWO(1);
        
        // Assign number value to these enumerator options
        int playerIndex;
        /**
         * Setter method for players
         * @param playerIndex index of player in names/color arrays
         */
        private Players (int playerIndex) {
            this.playerIndex = playerIndex;
        }
        
        /**
         * Getter for value of players
         * @return 
         */
        public int getPlayerIndex() {
            return this.playerIndex;
        }
    }
    public static Players currentPlayer = Players.ONE;

    /** Array of All Row Objects */
    private static Row[] rows;

    /** Player 1 Name */
    private static String name1;

    /** Player 2 Name */
    private static String name2;

    /** Array of Player's Names */
    private static String[] names = new String[2];

    /** Array of Player's Scores */
    private static int[] scores = new int[2];

    /** Index of Column Player Wants to Add To */
    private static int indexOfColumnToAddTo;

    /**
     * Starts the program.
     * 
     * @param args from console
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        askNumPlayers(scan);
        initializeBoard();
        while (!fourConsecutive()) {
            newTurn(scan);
        }
        //Print winning board
        printBoard();
        System.out.println("\nGame won :3");

        //Close scanner
        scan.close();
    }

    /**
     * This method figures out if there are 1 or 2 players
     * and sets their names
     * 
     * @param scan Scanner for user input
     */
    public static void askNumPlayers(Scanner scan) {
        //Prompt user from console
        System.out.print("\nWould you like to take turns or play against a CPU (enter t or c): ");
        //Validate input
        String response = "";
        if (scan.hasNext()) {
            response = scan.next().toLowerCase();
        }
        while (!response.equals("t") && !response.equals("c")) {
            System.out.print("Invalid input. Try again: ");
            response = scan.next().toLowerCase();
        } 
        //Get player 1's name
        System.out.print("\nEnter Player 1's Name: ");
        name1 = scan.next();
        //Figure out player 2's name
        if (response.equals("c")) {
            name2 = "CPU";
        } else {
            System.out.print("\nEnter Player 2's Name: " );
            name2 = scan.next();
        }
        //Set array of player's names
        names[Players.ONE.getPlayerIndex()] = name1;
        names[Players.TWO.getPlayerIndex()] = name2;
    }

    /**
     * Creates a 7 by 6 board and initializes row objects
     */
    public static void initializeBoard() {
        //Set up and fill array of columns
        rows = new Row[NUM_ROWS];
        for (int i = 0; i < NUM_ROWS; i++) {
            rows[i] = new Row();
        }
    }

    /**
     * Prints the current state of the board to the console
     * plus their scores on the side
     */
    public static void printBoard() {
        System.out.println();
        //Print number heading
        for (int i = 1; i <= NUM_COLS; i++) {
            System.out.print(" " + i);
        }
        System.out.println();
        //Print each row
        for (int i = 0; i < rows.length; i++) {
            System.out.print(rows[i].toString());
            //Print side stats
            if (i == 0) {
                System.out.print("\t\t" + colors[Players.ONE.getPlayerIndex()] + name1 + 
                                 "'s" + white + " Score: " + scores[Players.ONE.getPlayerIndex()]);
            } else if (i == 2) {
                System.out.print("\t\t" + colors[Players.TWO.getPlayerIndex()] + name2 + 
                                 "'s" + white + " Score: " + scores[Players.TWO.getPlayerIndex()]);
            } 
            System.out.println();
        }
        //Print bottom edge of board
        System.out.print(" ");
        for (int i = 0; i < NUM_COLS * 2 - 1; i++) {
            System.out.print("-");
        }
    }

    /**
     * 
     * 
     * @param scan
     */
    public static void newTurn(Scanner scan) {
        final int INVALID_ROW = 13;
        //Print current state of the board
        printBoard(); 
        //Declare who's turn it is
        String playerName = names[currentPlayer.getPlayerIndex()];
        String playerColor = colors[currentPlayer.getPlayerIndex()];
        //Prompt player for column choice
        System.out.print("\n" + playerColor + playerName + "'s" + white + " Turn. Enter column: ");
        while (!scan.hasNextInt() || !validate(scan.next())) {
            //System.out.println("x");
            System.out.print("Invalid column. Try again: ");
            //Get rid of current input if necessary
            if (!scan.hasNextInt()) {
                scan.next();
            }
        }
        //rows[columnToAddTo - 1].addToken(columnToAddTo);
        int rowToAddTo = INVALID_ROW;
        //Traverse through rows to see which row token should go in
        for (int i = NUM_ROWS - 1; i >= 0; i--) {
            if (rows[i].isColumnEmpty(indexOfColumnToAddTo)) {
                //figure out index of empty row
                rowToAddTo = i;
                //stop looking through rows
                break;
            }
        }
        //Report back to user if column is full
        if (rowToAddTo == INVALID_ROW) {
            System.out.println("Column is full :(");
        } else {
            //Add token to column
            rows[rowToAddTo].addToken(indexOfColumnToAddTo, currentPlayer.getPlayerIndex());
        }
        //Set up to be next player's turn
        switch (currentPlayer) {
            case ONE: 
                currentPlayer = Players.TWO;
                break;
            default:
                currentPlayer = Players.ONE;
        }

    }

    /**
     * Determines whether or not the column entered is valid (1-7)
     * If it's valid, set's columnToAddTo
     * 
     * @return whether or not the column is valid
     */
    public static boolean validate(String columnNum) {
        //Number is between 1 and 7
        for (int i = 1; i <= NUM_COLS; i++) {
            String num = "" + i;
            if (columnNum.equals(num)) {
                indexOfColumnToAddTo = Integer.parseInt(columnNum) - 1;
                return true;
            }
        }
        //Number is out of appropriate range
        return false;
    }

    /**
     * 
     * 
     * @return
     */
    public static boolean fourConsecutive() {
        //Check for four vertical
        for (int col = 0; col < NUM_COLS; col++) {
            int numXs = 0;
            int numOs = 0;
            for (Row row: rows) {
                if (row.columnContents(col) == 'X') {  //increment x's and reset o's
                    numXs++;
                    numOs = 0;
                } else if (row.columnContents(col) == 'O') {  //increment o's and reset x's
                    numOs++;
                    numXs = 0;
                }
                //Check if four in a row has been achieved
                if (numOs == NUM_CONSECUTIVE) {
                    scores[Players.ONE.getPlayerIndex()]++;
                    return true;
                } else if (numXs == NUM_CONSECUTIVE) {
                    scores[Players.TWO.getPlayerIndex()]++;
                    return true;
                }
            }
        }
        //Check for four horizontal
        for (Row row : rows) {
            int winnersIndex = row.checkHorizontalWin();
            if (winnersIndex != -1) {
                scores[winnersIndex]++;
                return true;
            }
        }
        //Check for diagonal
        if (checkDiagonal(leftToRight) || checkDiagonal(rightToLeft)) {
            //scores updated inside checkDiagonal method
            return true;
        }
        //No consecutive tokens
        return false;
    }

    /**
     * 
     * 
     * @param direction
     * @return
     */
    public static boolean checkDiagonal(String direction) {
        //Check for diagonal
        /**
         * 00
         * 11
         * 22
         * 33
         * 
         * 10
         * 21
         * 32
         * 43
         * 
         * ... 
         * 
         * 30
         * 41
         * 52
         * 63
         * 
         * downwards :: rows (0-3) -> cols (0-3(0-3)) -> rows (0-3)
         * 
         * 
         */
        for (int row = 0; row < NUM_CONSECUTIVE; row++) {
            int numOs = 0;
            int numXs = 0;

        }
        //No diagonal
        return false;
    }
}
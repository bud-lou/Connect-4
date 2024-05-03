import java.util.Scanner;

public class Board {
    /** Player 1's Color */
    public static final String color1 = "\u001B[34m";
    
    /** Player 2's Color */
    public static final String color2 = "\u001B[33m";

    /** Reset Color */
    public static final String white = "\u001B[37m";
    
    /** Number of Columns in Board */
    public static final int NUM_COLS = 7;

    /** Number of Rows in Board */
    public static final int NUM_ROWS = 6;

    /** Array of All Row Objects */
    private static Row[] rows;

    /** Player 1 Score */
    private static int score1;

    /** Player 2 Score */
    private static int score2;

    /** Player 1 Name */
    private static String name1;

    /** Player 2 Name */
    private static String name2;

    /**
     * Starts the program.
     * 
     * @param args from console
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        askNumPlayers(scan);
        initializeBoard();
        printBoard();

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
        System.out.print("\nWould you like to take turns or play against a CPU (t or c): ");
        //Validate input
        char response = '\0';
        if (scan.hasNext()) {
            response = Character.toLowerCase(scan.next().charAt(0));
        }
        while (response != 't' && response != 'c') {
            System.out.print("Invalid input. Try again: ");
            response = scan.next().charAt(0);
        } 
        //Get player 1's name
        System.out.print("\nEnter Player 1's Name: ");
        name1 = scan.next();
        //Figure out player 2's name
        if (response == 'c') {
            name2 = "CPU";
        } else {
            System.out.print("\nEnter Player 2's Name: " );
            name2 = scan.next();
        }
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
                System.out.print("\t\t" + color1 + name1 + "'s" + white + " Score: " + score1);
            } else if (i == 2) {
                System.out.print("\t\t" + color2 + name2 + "'s" + white + " Score: " + score2);
            } 
            System.out.println();
        }
        //Print bottom edge of board
        System.out.print(" ");
        for (int i = 0; i < NUM_COLS * 2 - 1; i++) {
            System.out.print("-");
        }
    }
}
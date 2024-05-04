import java.util.Scanner;
import java.util.Arrays;

public class Row {
    /** Array of Each column */
    char[] columns;

    /** 
     * Column constructor method
     */
    public Row() {
        columns = new char[Board.NUM_COLS];
        for (int i = 0; i < columns.length; i++) {
            columns[i] = '*';
        }
    }

    /**
     * Returns a string method of the array that displays
     * it as a board
     */
    public String toString() {
        //Turn array to string
        String rowVisual = Arrays.toString(columns);
        //Add borders
        String rowWithEdges = "|" + rowVisual.substring(1, rowVisual.length() - 1) + "|";
        //Remove commas
        String rowNoCommas = rowWithEdges.replace(',', '\0');
        //Add colors to player's pieces
        String rowFinalized = "";
        Scanner stringSearch = new Scanner(rowNoCommas);
        while (stringSearch.hasNext()) {
            String nextChar = stringSearch.next();
            if (nextChar.indexOf("O") != -1) {  //write in blue
                //Add left border if necessary
                if (nextChar.charAt(0) == '|') {
                    rowFinalized += "|";
                    nextChar = nextChar.substring(1);
                }
                rowFinalized += Board.colors[Board.Players.ONE.getPlayerIndex()] + "O" + 
                                Board.white + nextChar.substring(1);
            } else if (nextChar.indexOf("X") != -1) {  //write in gold
                //Add left border if necessary
                if (nextChar.charAt(0) == '|') {
                    rowFinalized += "|";
                    nextChar = nextChar.substring(1);
                }
                rowFinalized += Board.colors[Board.Players.TWO.getPlayerIndex()] + "O" + 
                                Board.white  + nextChar.substring(1);
            } else {  //write in white
                rowFinalized += nextChar;
            }
            rowFinalized += " ";
        }
        rowFinalized += "\b"; //Backspace char
        //Close scanner
        stringSearch.close();
        
        return rowFinalized;
    }

    /**
     * Adds a token to the specified column - o for player 1, x for player 2
     * 
     * @returns whether or not the token was successfully added
     */
    public void addToken(int colNum, int playerIndex) {
        //Place token
        if (playerIndex == 0) {  //Player 1's token
            columns[colNum] = 'O';
        } else {    //Player 2's token
            columns[colNum] = 'X';
        }
        
    }

    /**
     * Checks whether or not there's space to add a token in the column
     * 
     * @returns whether or not there is space available
     */
    public int getFirstRowAvailable() {
        final int NO_ROWS_AVAILABLE = 13;
        for (char col : columns) {
            if (col == '*') {
                return col;
            }
        }
        return NO_ROWS_AVAILABLE;
    }

    public boolean isColumnEmpty(int colNum) {
        return columns[colNum] == '*';
    }

    public char columnContents(int colNum) {
        return columns[colNum];
    }

    public int checkHorizontalWin() {
        //Traverse through each column and check if there consecutive tokens
        int numXs = 0;
        int numOs = 0;
        for (char col : columns) {
            if (col == 'X') {          //increment x's and reset o's
                numXs++;
                numOs = 0;
            } else if (col == 'O') {   //increment o's and reset x's
                numOs++;
                numXs = 0;
            }
            //Check if either has reached four in a row
            if (numOs == Board.NUM_CONSECUTIVE) {
                return Board.Players.ONE.getPlayerIndex();
            } else if (numXs == Board.NUM_CONSECUTIVE) {
                return Board.Players.TWO.getPlayerIndex();
            }
        }
        //No four consutive horizontal tokens
        return -1;
    }
    
}

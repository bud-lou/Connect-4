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
            int indexOfPlayer1Token = nextChar.indexOf(Board.tokens[Board.Players.ONE.getPlayerIndex()]);
            int indexOfPlayer2Token = nextChar.indexOf(Board.tokens[Board.Players.TWO.getPlayerIndex()]);
            // Figure out if there's a token or empty space
            if (indexOfPlayer1Token != -1 || indexOfPlayer2Token != -1) {
                //Determine who's token it is
                Board.Players tokenOwner = indexOfPlayer2Token == -1 ? Board.Players.ONE : Board.Players.TWO;
                //Add left border if necessary
                if (nextChar.charAt(0) == '|') {
                    rowFinalized += "|";
                    nextChar = nextChar.substring(1);
                }
                rowFinalized += Board.colors[tokenOwner.getPlayerIndex()] + "O" + 
                                Board.white + nextChar.substring(1);
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
        columns[colNum] = Board.tokens[playerIndex];
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
            if (col == Board.tokens[Board.Players.ONE.getPlayerIndex()]) {          //increment x's and reset o's
                numOs++;
                numXs = 0;
            } else if (col == Board.tokens[Board.Players.TWO.getPlayerIndex()]) {   //increment o's and reset x's
                numXs++;
                numOs = 0;
            } else {                   //reset both to 0
                numXs = 0;
                numOs = 0;
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

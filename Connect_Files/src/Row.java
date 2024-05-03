import java.util.Arrays;

public class Row {
    /** Array of Each column */
    char[] row;

    /** 
     * Column constructor method
     */
    public Row() {
        row = new char[Board.NUM_COLS];
        for (int i = 0; i < row.length; i++) {
            row[i] = '*';
        }
    }

    /**
     * Returns a string method of the array that displays
     * it as a board
     */
    public String toString() {
        String rowVisual = Arrays.toString(row);
        String rowWithEdges = "|" + rowVisual.substring(1, rowVisual.length() - 1) + "|";
        String rowNoCommas = rowWithEdges.replace(',', '\0');
        return rowNoCommas;
    }
    
}

package edu.harvard.cscie98.sample_code;

public class Queens {

  public static void main(final String[] args) {
    final QueensBoard board = solveQueens(8);
    System.out.println(board.toString());
  }

  private static QueensBoard solveQueens(final int n) {
    final QueensBoard board = new QueensBoard(n);
    if (placeQueen(board, 0)) {
      return board;
    }
    return null;
  }

  private static boolean placeQueen(final QueensBoard board, final int column) {
    if (column == board.n) {
      return true;
    }
    for (int row = 0; row < board.n; row++) {
      board.set(column, row, true);
      if (board.check()) {
        if (placeQueen(board, column + 1)) {
          return true;
        }
      }
      board.set(column, row, false);
    }
    return false;
  }
}

class QueensBoard {
  private final QueensRow[] board;
  int n;

  public QueensBoard(final int n) {
    this.n = n;
    board = new QueensRow[n];
    for (int i = 0; i < n; i++) {
      board[i] = new QueensRow(n);
      for (int j = 0; j < n; j++) {
        board[i].set(j, false);
      }
    }
  }

  public int getPosition(final int currentColumn) {
    for (int i = 0; i < n; i++) {
      if (board[currentColumn].get(i)) {
        return i;
      }
    }
    return -1;
  }

  public boolean check() {
    for (int column = 0; column < n; column++) {
      final int row = getPosition(column);
      if (row != -1) {
        if (findThreatenedSquares(column, row)) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean findThreatenedSquares(final int column, final int row) {
    for (int i = 0; i < n; i++) {
      if (i != column && board[i].get(row)) {
        return true;
      }
    }
    for (int i = 0; i < n; i++) {
      final int offset = column - i;
      int diagonal = row + offset;
      if (offset != 0 && diagonal >= 0 && diagonal < n
          && board[i].get(diagonal)) {
        return true;
      }
      diagonal = row - offset;
      if (offset != 0 && diagonal >= 0 && diagonal < n
          && board[i].get(diagonal)) {
        return true;
      }
    }
    return false;
  }

  public void set(final int column, final int row, final Boolean value) {
    board[column].set(row, value);
  }

  @Override
  public String toString() {
    String s = "";
    for (int row = 0; row < n; row++) {
      s += line();
      for (int col = 0; col < n; col++) {
        if (board[col].get(row)) {
          s += "| \u265B ";
        } else {
          s += "|   ";
        }
      }
      s += "|\n";
    }
    s += line();
    return s;
  }

  private String line() {
    String s = "";
    for (int i = 0; i < n; i++) {
      s += "____";
    }
    return s + "_\n";
  }

}

class QueensRow {

  private final Boolean[] row;

  public QueensRow(final int n) {
    this.row = new Boolean[n];
  }

  public Boolean get(final int i) {
    return row[i];
  }

  public void set(final int i, final boolean val) {
    row[i] = val;
  }
}
import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) {
        ConsoleInputProvider input = new ConsoleInputProvider();
        Game game = new Game(input);
        game.start();
    }
}

class Game {
    private final Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private final ConsoleInputProvider input;
    private final WinChecker winChecker;

    public Game(ConsoleInputProvider input) {
        this.input = input;
        this.board = new Board(3);
        this.winChecker = new WinChecker();
        initPlayers();
    }

    private void initPlayers() {
        System.out.println("Enter Player 1 Name:");
        String p1 = input.getInput();

        System.out.println("Enter Player 2 Name:");
        String p2 = input.getInput();

        player1 = new Player(p1, Symbol.X);
        player2 = new Player(p2, Symbol.O);

        currentPlayer = player1;
    }

    public void start() {
        while (!board.isFull()) {
            board.print();

            System.out.println(currentPlayer.name() + " enter move (row,col):");
            String move = input.getInput();

            if (!processMove(move)) continue;

            if (winChecker.checkWin(board, currentPlayer.symbol())) {
                board.print();
                System.out.println(currentPlayer.name() + " wins!");
                return;
            }

            switchPlayer();
        }

        System.out.println("Game Tie!");
    }

    private boolean processMove(String inputStr) {
        try {
            String[] parts = inputStr.split(",");
            int row = Integer.parseInt(parts[0].trim());
            int col = Integer.parseInt(parts[1].trim());

            return board.place(row, col, currentPlayer.symbol());
        } catch (Exception e) {
            System.out.println("Invalid input. Use row,col");
            return false;
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }
}

class Board {
    private final Symbol[][] grid;
    private final int size;

    public Board(int size) {
        this.size = size;
        grid = new Symbol[size][size];
    }

    public boolean place(int row, int col, Symbol symbol) {
        if (!isValid(row, col)) {
            System.out.println("Invalid move!");
            return false;
        }

        grid[row][col] = symbol;
        return true;
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && col >= 0 &&
                row < size && col < size &&
                grid[row][col] == null;
    }

    public Symbol[][] getGrid() {
        return grid;
    }

    public boolean isFull() {
        for (Symbol[] row : grid)
            for (Symbol cell : row)
                if (cell == null) return false;
        return true;
    }

    public void print() {
        for (Symbol[] row : grid) {
            for (Symbol cell : row) {
                System.out.print((cell == null ? "-" : cell) + " ");
            }
            System.out.println();
        }
    }
}

class WinChecker {

    public boolean checkWin(Board board, Symbol symbol) {
        Symbol[][] grid = board.getGrid();
        int size = grid.length;

        // Rows & Columns
        for (int i = 0; i < size; i++) {
            if (checkRow(grid, i, symbol) || checkCol(grid, i, symbol))
                return true;
        }

        // Diagonals
        return checkMainDiagonal(grid, symbol) ||
                checkAntiDiagonal(grid, symbol);
    }

    private boolean checkRow(Symbol[][] grid, int row, Symbol symbol) {
        for (int col = 0; col < grid.length; col++) {
            if (grid[row][col] != symbol) return false;
        }
        return true;
    }

    private boolean checkCol(Symbol[][] grid, int col, Symbol symbol) {
        for (Symbol[] symbols : grid) {
            if (symbols[col] != symbol) return false;
        }
        return true;
    }

    private boolean checkMainDiagonal(Symbol[][] grid, Symbol symbol) {
        for (int i = 0; i < grid.length; i++) {
            if (grid[i][i] != symbol) return false;
        }
        return true;
    }

    private boolean checkAntiDiagonal(Symbol[][] grid, Symbol symbol) {
        int size = grid.length;
        for (int i = 0; i < size; i++) {
            if (grid[i][size - i - 1] != symbol) return false;
        }
        return true;
    }
}

class Player{
    String name;
    Symbol symbol;

    public Player(String p1, Symbol symbol) {
        name = p1;
        this.symbol = symbol;
    }

    public Symbol symbol() {
        return symbol;
    }

    public String name() {
        return name;
    }
}

enum Symbol {
    X, O
}

class ConsoleInputProvider  {
    private final Scanner scanner = new Scanner(System.in);

    public String getInput() {
        return scanner.nextLine();
    }
}
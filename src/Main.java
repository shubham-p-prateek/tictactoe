import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        playGame();
    }

    public static void playGame() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Player 1 Name");
        String player1 = scanner.nextLine();
        System.out.println("Enter Player 2 Name");
        String player2 = scanner.nextLine();

        System.out.println("Select symbol for Player 1 \n Press 1 for 1 \n Press 2 for 2");
        int selection = scanner.nextInt();
        scanner.nextLine();

        if (selection != 1 && selection != 2) {
            throw new IllegalArgumentException("Invalid Selection");
        }

        int player2Selection = selection == 1 ? 2 : 1;

        int count = 0;
        int[][] tictac = new int[3][3];

        boolean player1Tern = true;
        while (count < 9) {
            String currentPlayer = player1Tern ? player1 : player2;
            System.out.println("Enter " + currentPlayer + " Choice Coordinates");

            String input = scanner.nextLine();
            String[] coords = input.split(",");

            if (coords.length != 2) {
                System.out.println("Invalid format. Use row,col");
                continue;
            }


            int i, j;
            try {
                i = Integer.parseInt(coords[0].trim());
                j = Integer.parseInt(coords[1].trim());
            } catch (NumberFormatException e) {
                System.out.println("Coordinates must be numbers.");
                continue;
            }
            //CheckIfAlready occupied
            if (i > 2 || i < 0 || j > 2 || j < 0) {
                System.out.println("Invalid value entered. Use row,col in within range 0-2");
                continue;
            }
            if (tictac[i][j] == 1 || tictac[i][j] == 2) {
                System.out.println("Select Correct Coords");
                continue;
            }
            tictac[i][j] = player1Tern ? selection : player2Selection;
            if (winOrTie(tictac,currentPlayer)) {
                return;
            }

            player1Tern = !player1Tern;
            count++;
        }

        System.out.println("Game Tie!!!");
        scanner.close();

    }

    public static boolean winOrTie(int[][] tic, String player) {

        for (int[] ints : tic) {
            System.out.println(Arrays.toString(ints));
        }
        for (int i = 0; i < 3; i++) {

            if (tic[i][0] != 0 && tic[i][1] != 0 && tic[i][2] != 0) {
                if ((tic[i][0] == tic[i][1]) && (tic[i][1] == tic[i][2])) {
                    System.out.println(player + " win");
                    return true;
                }
            }
        }


        for (int j = 0; j < 3; j++) {

            if (tic[0][j] != 0 && tic[1][j] != 0 && tic[2][j] != 0) {
                if ((tic[0][j] == tic[1][j]) && (tic[1][j] == tic[2][j])) {
                    System.out.println(player + " win");
                    return true;
                }
            }
        }

        if (tic[0][0] != 0 && tic[1][1] != 0 && tic[2][2] != 0) {
            if ((tic[0][0] == tic[1][1]) && (tic[1][1] == tic[2][2])) {
                System.out.println(player + " win");
                return true;
            }
        }
        if (tic[0][2] != 0 && tic[1][1] != 0 && tic[2][0] != 0) {
            if ((tic[0][2] == tic[1][1]) && (tic[1][1] == tic[2][0])) {
                System.out.println(player + " win");
                return true;
            }
        }
        return false;
    }
}
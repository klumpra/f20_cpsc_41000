package edu.lewisu.cs.klumpra.textgames;
import java.util.Random;
import java.util.Scanner;
public class GuessMyNumber {
    public static void welcome() {
        System.out.println("*****************************");
        System.out.println("       GUESS MY NUMBER");
        System.out.println("*****************************");
    }
    public static void main(String[] args) {
        welcome();
        Random rnd = new Random();            // how we generate random numbers
        Scanner sc = new Scanner(System.in);  // how we talk with the user
        String playAgain;   // controls whether they want to keep playing
        int target, guess;
        do {
            target = 1 + rnd.nextInt(10);
            do {
                System.out.print("Guess the number: ");
                guess = sc.nextInt();
            } while (guess != target);
            System.out.println("Congratulations.");
            System.out.print("Want to play again? ");
            playAgain = sc.next().trim().toUpperCase();
        } while (playAgain.equalsIgnoreCase("Y"));
    }
}

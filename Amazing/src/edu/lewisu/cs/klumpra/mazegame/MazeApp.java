package edu.lewisu.cs.klumpra.mazegame;
import java.util.Scanner;
public class MazeApp {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter name of file: ");
		String fname = sc.nextLine();
		Building bldg = BuildingBuilder.buildFromFile(fname);
		GameManager gm = new GameManager(bldg);
		gm.play(bldg);
	}
}

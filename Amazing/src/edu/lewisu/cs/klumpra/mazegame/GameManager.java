package edu.lewisu.cs.klumpra.mazegame;
import java.util.Scanner;

public class GameManager {
	private Room position;  // where the player is right now
	private Building building;   // where we are traveling through
	public GameManager(Building bldg) {
		building = bldg;
	}
	/**
	 * returns the outcome of moving in direction dir
	 * @param dir - N, S, E, or W
	 * @return a string that describes what happened - might embellish
	 */
	public String move(String dir) {
		Room newPosition = building.getRoom(position, dir);
		if (newPosition == null) {
			return "You can't move in that direction.";
		} else {
			position = building.getRoom(position,dir);
			return "You are now in room " + position.getName();
		}
	}
	/**
	 * Updates the player's position based on the player's keyboard entires.
	 * @param bldg - remove in future versions b/c we already have one associated
	 */
	public void play(Building bldg) {
		Scanner sc = new Scanner(System.in);
		position = bldg.findRoomByName("entrance");  // must have a room called this
		if (position == null) {
			System.out.println("Invalid starting position. One of the rooms must be named 'entrance'.");
		} else {
			String dir = "";
			while (!dir.equals("Q")) {
				System.out.print("Enter direction (N, S, E, W, or Q to quit): ");
				dir = sc.nextLine().toUpperCase().trim();
				if (!dir.equals("Q")) {
					System.out.println(move(dir));
				}
			}
		}
	}
}

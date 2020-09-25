package edu.lewisu.cs.klumpra.mazegame;

import java.util.ArrayList;

public class Building {
	private String name;
	private ArrayList<Room> rooms;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Room> getRooms() {
		return rooms;
	}
	public void setRooms(ArrayList<Room> rooms) {
		this.rooms = rooms;
	}
	public Building() {
		name = "";
		rooms = new ArrayList<Room>();
	}
	public Building(String name) {
		this();
		this.name = name;
	}
	/**
	 * returns the room in a particular direction of a starting room
	 * @param rm - where we are starting from
	 * @param dir - direction to looking from the starting room
	 * @return the room in that direction or null
	 */
	public Room getRoom(Room rm, String dir) {
		return rm.getNeighbor(dir);
	}
	/**
	 * adds rm to the building with no neighbors
	 * @param rm the room to add as an isolated entity
	 */
	public void addRoom(Room rm) {
		rooms.add(rm);
	}
	/**
	 * adds rm connected to another room in a particular direction
	 * @param startFrom - the starting room
	 * @param dir - the direction from the starting room where to add rm
	 * @param newRoomName - the new room name
	 * @param desc - the new room description
	 */
	public void addRoom(String startFrom, String dir, String newRoomName, String desc) {
		Room startRoom = findRoomByName(startFrom);
		if (startRoom != null) {
			startRoom.setNeighbor(dir, new Room(newRoomName,desc));
		}
	}
	/**
	 * establish a connection to a pre-existing room that has a particular name
	 * @param room - the room to start from
	 * @param dir - where to add the new connection
	 * @param name - the name of the exsiting room to add there
	 */
	public void setNeighborByName(Room room, String dir, String name) {
		room.setNeighbor(dir, findRoomByName(name));
	}
	/**
	 * fully connects the room to roms in other directions
	 * @param room - the room we'll trying to connect neighbors to
	 * @param north - name of room to set on north
	 * @param south
	 * @param east
	 * @param west
	 */
	public void setNeighborsByName(Room room, String north, String south, String east, String west) {
		setNeighborByName(room,"N",north);
		setNeighborByName(room,"S",south);
		setNeighborByName(room,"E",east);
		setNeighborByName(room,"W",west);
	}
	public Room findRoomByName(String name) {
		for (Room rm : rooms) {
			if (rm.matchByName(name)) {
				return rm;
			}
		}
		return null;
	}
	@Override
	public String toString() {
		String result = "";
		for (Room rm : rooms) {
			result = result + rm.toString() + "\n";
		}
		return result;
	}
}

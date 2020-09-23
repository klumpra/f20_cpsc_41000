package edu.lewisu.cs.klumpra.mazegame;
import java.util.LinkedHashMap;

public class Room {
	private String name;
	private String description;
	private LinkedHashMap<String, Room> neighbors;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LinkedHashMap<String,Room> getNeighbors() {
		return neighbors;
	}
	public Room getNeighbor(String dir) {
		return neighbors.get(dir);
	}
	public void setNeighbor(String dir, Room rm) {
		neighbors.put(dir, rm);
	}
	public String getNeighborsAsString() {
		return String.format("%s\t%s\t%s\t%s",getNeighborName("N"),
				getNeighborName("S"),getNeighborName("E"),getNeighborName("W"));
	}
	@Override
	public boolean equals(Object other) {
		Room otherRoom = (Room)other;
		return name.equalsIgnoreCase(otherRoom.getName());
	}
	public boolean matchByName(String otherName) {
		return name.equalsIgnoreCase(otherName);
	}
	public Room() {
		name = "";
		description = "";
		neighbors = new LinkedHashMap<String,Room>();
	}
	public Room(String name, String desc) {
		this();
		this.name = name;
		this.description = desc;
	}
	public String getNeighborName(String dir) {
		dir = dir.toUpperCase().trim();
		Room rm = getNeighbor(dir);
		if (rm == null) {
			return "";
		} else {
			return rm.getName();
		}
	}
	@Override
	public String toString() {
		return String.format("%s\t%s\t%s", name, description, getNeighborsAsString());
	}
}

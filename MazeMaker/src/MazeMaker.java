import java.util.LinkedHashMap;
import java.util.Scanner;
import java.io.*;

public class MazeMaker {
    public static LinkedHashMap<String,String> roomCodes;

    public static String getRoomName(String[][] grid, LinkedHashMap<String,String> codes,
    		int row, int col, int rowCount, int colCount) {
        if (row < 0) {
            return "null";
        } else if (row >= rowCount) {
            return "null";
        } else if (col < 0) {
            return "null";
        } else if (col >= colCount) {
            return "null";
        } else {
            if (grid[row][col].equals("")) {
                return "null";
            } else {
                return codes.get(grid[row][col]).split("\t")[0];
            }
        }
    }
    /**
     * transforms the data into the format our BuildingBuilder class can read
     * @param grid - the 2-dimensional array of room ids and where they lie
     * @param rowCount - horizontal dim of the grid
     * @param colCount - vertical dim of the grid
     * @param codes - linked hash map of id's and room name + descriptions
     * @return String that can be written to a file that BuildingBuilder can read
     */
    public static String encodeMap(String[][] grid, int rowCount, int colCount, 
    		LinkedHashMap<String,String> codes) {
        String roomString;
        String north,south,east,west;
        String theCode;
        String result = "";
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {
            	theCode = grid[row][col].trim();
            	if (codes.containsKey(theCode)) {
	                roomString = codes.get(grid[row][col]); // name + descriptions
	                north = getRoomName(grid,codes,row-1,col,rowCount,colCount);
	                south = getRoomName(grid,codes,row+1,col,rowCount,colCount);
	                east = getRoomName(grid,codes,row,col+1,rowCount,colCount);
	                west = getRoomName(grid,codes,row,col-1,rowCount,colCount);
	                result = result + String.format("%s\t%s\t%s\t%s\t%s\n",roomString,north,south,east,west);
            	}
            }
        }
        return result;
    }
    public static void main(String[] args) {
        roomCodes = new LinkedHashMap<String,String>(); // store the room names and descriptions
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter name of maze definition file: ");
        String fname = sc.nextLine();
        String line;
        boolean reachedMap = false;
        String[] parts;
        int rowCount=0, colCount=0;
        String code, name, desc;
        String[][] grid;
        try {
            Scanner fsc = new Scanner(new File(fname));
            while (fsc.hasNextLine() && !reachedMap) {
                line = fsc.nextLine();
                parts = line.split("\t");
                if (parts[0].equalsIgnoreCase("MAPDEF")) {
                    reachedMap = true;
                    rowCount = Integer.parseInt(parts[1]);
                    colCount = Integer.parseInt(parts[2]);
                } else {
                    code = parts[0].trim();
                    name = parts[1].trim();
                    desc = parts[2].trim();
                    roomCodes.put(code,String.format("%s\t%s",name,desc));
                }
            }
            grid = new String[rowCount][colCount];
            for (int row = 0; row < rowCount; row++) {
                line = fsc.nextLine()+" ";
                parts = line.split("\t");
                for (int col = 0; col < colCount; col++) {
                    grid[row][col] = parts[col].strip();
                }
            }
            String encoding = encodeMap(grid,rowCount,colCount,roomCodes);
//            System.out.println(encoding);
            System.out.print("Enter name of output file: "); // will the description will be written
            String out = sc.nextLine();
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(out))));
            pw.println(encoding);
            pw.close();
        } catch (Exception ex) {
            System.out.println("Couldn't read file.");
            ex.printStackTrace();
        }
    }
}

import java.util.LinkedHashMap;

public class NamesAndAges {
	public static void main(String[] args) {
		LinkedHashMap<String,Integer> namesAndAges = new
				LinkedHashMap<String,Integer>();
		namesAndAges.put("Ray", 49);
		namesAndAges.put("Karen", 45);
		namesAndAges.put("Conor",19);
		System.out.println(namesAndAges.get("Karen"));
		if (namesAndAges.containsKey("Lauren")) {
			System.out.println(namesAndAges.get("Lauren"));
		} else {
			System.out.println("not found");
		}
		LinkedHashMap<String,Room> neighbors;
		neighbors.put("N",someRoom);
	}
	
}

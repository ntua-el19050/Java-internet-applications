package dev;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class ProgramMain {

	public static void main(String[] args) throws Exception {

		System.out.println(" >> ProgramMain: BEGIN");
		
		// Get User Input
		System.out.print("Provide your Data: ");
		final String str = getUserInput();
		System.out.println("Input given: \"" + str + "\" Length: " + str.length() );
		
		// A mapping between a key (character) and a value (number of occurrences).
		// HashMap class is an implementation of the Map interface
		final Map<Character, Integer> charCountMap = new HashMap<>();
		
		// Process User Input - Find Distinct Characters & Number of occurrences
		for (int i = 0; i < str.length(); i++) {
			
			// Character of the given String at position i
			char c = str.charAt(i);
			
			// Check and Update Map
			if (!charCountMap.containsKey(c)) {
				charCountMap.put(c, 1);
			} else {
				int count = charCountMap.get(c);
				charCountMap.put(c, count+1);
			}
		}
		
		// Organize (by character name) and Present Characters and their Frequency
		final List<Entry<Character,Integer>> entryList = new ArrayList<>(charCountMap.entrySet());
		
		// Sort Entries by Character (Entry/Key) name
		Collections.sort(entryList, new EntryComparator());
		
		// Present List (of Entries) Data
		System.out.println("There are " + entryList.size()  + " distinct character.");
		for (Entry<Character, Integer> entry : entryList) {
			Character c = entry.getKey();
			Integer n = entry.getValue();
			System.out.println( " - '" + c + "' : " + n);
		}
		
		System.out.println(" >> ProgramMain: END ");
		
	}
	
	/**
	 * @return A String with the user input
	 * 		
	 * @throws IOException
	 */
	public static String getUserInput() throws IOException {
		
		// InputStreamReader: Reads bytes from Standard Input and Decodes them into characters
		// BufferedReader: Buffers the characters - Enable efficient reading of text data
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 

		// Read a line of text
		final String line = reader.readLine(); 
        
		return line;
	}

}

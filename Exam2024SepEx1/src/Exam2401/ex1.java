package Exam2401;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

public class ex1 {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println(" >> ProgramMain: BEGIN");
		/*
		FileInputStream file = new FileInputStream("text.txt");
		final BufferedReader rd = new BufferedReader(new InputStreamReader(file));
		String res;
		int i = 0;
		*/
		
		// Get User Input
		System.out.print("Provide your Data: ");
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 

		// Read a line of text
		final String text = reader.readLine(); 
		reader.close();
		
		String[] myStrings = text.split("\\.");
		
		List<Object> result = new ArrayList<>();
		
		int i = 0;
		
		for (String s: myStrings) {
			result.add(s.trim());
			System.out.println(s.trim());
		}
		
		FileWriter writer = new FileWriter("text.txt");
		// FileWriter writer1 = new FileWriter("text1.txt");
		/*
		for (String s: myStrings) {
			if (i == 0 || i == myStrings.length-1) {
				s.trim();
			}
			if (!s.trim().equals("") && i != myStrings.length-1) {
				writer.write(s + "\n");
			}
			else if(i == myStrings.length) {
				writer.write(s);
			}
		}
		*/
		for (Object o: result) {
			String s = o.toString();
			s.trim();
			if (!s.trim().equals("")) {
				writer.write(s + "\n");
			}
		}
		
		writer.close();
		
		/*
		FileInputStream file1 = new FileInputStream("text.txt");
		final BufferedReader rd1 = new BufferedReader(new InputStreamReader(file1));
		i = 0;
		
		while ((res = rd1.readLine()) !=null) {
			 i++;
	            final int lineNumber = i;  // For passing line number to thread
	            final String lineContent = res;  // For passing line content to thread

	            // Create a new thread to count characters
	            Thread t = new Thread(() -> {
	                int charCount = lineContent.replaceAll(" ", "").length();  // Remove spaces and count characters
	                System.out.println("Thread <" + lineNumber + ">: Line Characters: " + charCount);
	            });

	            // Set the name of the thread and start it
	            t.setName("Thread-" + lineNumber);
	            t.start();
		}
		
		file1.close();
		rd1.close();
		*/
	}

}

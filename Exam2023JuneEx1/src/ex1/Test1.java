package ex1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test1 {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println(" >> ProgramMain: BEGIN");
		
		// Get User Input
		System.out.print("Provide your Data: ");
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 

		// Read a line of text
		final String text = reader.readLine(); 
		reader.close();
		
		
		FileWriter writer = new FileWriter("text.txt");
		writer.write(text);
		writer.close();
		
		FileInputStream file = new FileInputStream("text.txt");
		final BufferedReader rd = new BufferedReader(new InputStreamReader(file));
		final String res = rd.readLine();
		
		System.out.println(res);
		file.close();
		rd.close();
		
	}

}

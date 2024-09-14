package ex124;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class ex124 {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println(" >> ProgramMain: BEGIN");
		
		FileInputStream file = new FileInputStream("text.txt");
		final BufferedReader rd = new BufferedReader(new InputStreamReader(file));
		String res;
		int i = 0;
		
		while ((res = rd.readLine()) !=null) {
			i++;
			System.out.println("Γραμμή " + i + " : " + res);
		}
		
		file.close();
		rd.close();
		
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
		
	}

}

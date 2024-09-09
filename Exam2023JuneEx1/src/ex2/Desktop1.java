package ex2;

import java.io.*;


public class Desktop1 {

	public static void main(String[] args) {
		
		
		try{
			System.out.println("Program Starts");
			final BufferedReader rt = new BufferedReader(new InputStreamReader(System.in));
			
			final FileOutputStream file1 = new FileOutputStream("text.txt");
			final BufferedWriter out1 = new BufferedWriter(new OutputStreamWriter(file1));
			
			String input = rt.readLine();
			System.out.println("Input Given is:" + input);
			out1.write(input);
			
			out1.close();
			rt.close();
			
			final FileInputStream file2 = new FileInputStream("text.txt");
			final BufferedReader rf = new BufferedReader(new InputStreamReader(file2));
			
			String inputFile = rf.readLine();
			
			System.out.println(inputFile);
			
			rf.close();
			
			System.out.println("Program Ends");
		}
		
		catch (Exception ex) {
			ex.getStackTrace();
		}
	}

}

package ex24ex3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import ex24ex3.ExamDbConnector;

public class ex2 {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println(" >> ProgramMain: BEGIN");
		
		FileInputStream file = new FileInputStream("text.txt");
		final BufferedReader rd = new BufferedReader(new InputStreamReader(file));
		String res;
	
		List<Object> myList = new ArrayList<>();
		
		while ((res = rd.readLine()) !=null) {
			myList.add(res);
		}
		int j = 0;
		for (Object o: myList) {
			String s = o.toString();
			System.out.println("Length of Line " + ++j + ": " + s.length());
		}
		int i = 0;
		for (Object o: myList) {
			 i++;
            final int lineNumber = i;  
            final String s = o.toString();  

            // Create a new thread to count characters
            Thread t = new Thread(() -> {
            	String s1 = s;
                int charCount = s1.replaceAll(" ", "").length();
                char[] chars = s.toCharArray();

                int letters = 0;
                int numbers = 0;
                int words = 0;
                boolean help2 = true;
                for (char c: chars) {
                	int help = (int)c;
                	
                	if (help >= 65 && help<=122) {
                		letters++;
                		help2 = true;
                	}
                	if (help >= 48 && help<=57) {
                		numbers++;
                		help2 = true;
                	}
                	if(help==32 && help2) {
                		words++;
                		help2 = false;
                	}
                	else {
                		help2 = true;
                	}
                }
                words = words + 1;
                System.out.println("Thrd <" + lineNumber + ">: #chars: " + charCount + " #letters: " + letters + " #numbers: " + numbers + " #words: " + words + " -- " + s);
            
                try {
        			
        			final ExamDbConnector db = new ExamDbConnector();
        			db.openDbConnection();
        			int lines = db.recordData(s,charCount,letters,numbers, words);
        			System.out.println(lines);
        			if (lines > 0) {
        				System.out.println("Thrd <" + lineNumber + ">: DB storage :: success");
        			}
        			else {
        				System.out.println("Thrd <" + lineNumber + ">: DB storage :: failure");
        			}
        			db.closeDbConnection();
        			
        		} catch (Throwable T) {
        			
        			final String errMsg = "Thrd <" + lineNumber + ">: DB storage :: failure" + T.getMessage() + " cannot insert properly !";
        			System.out.println(errMsg);
        			T.printStackTrace();
        			
        		}
            
            });

            // Set the name of the thread and start it
            t.setName("Thread-" + lineNumber);
            t.start();
            
            
		}
		
		file.close();
		rd.close();
	}
}
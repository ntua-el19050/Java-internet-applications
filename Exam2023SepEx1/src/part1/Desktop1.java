package part1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;



public class Desktop1 {

	public static void main(String[] args) throws Exception {
		
		System.out.println(" >> ProgramMain: BEGIN");
		
		// Get User Input
		System.out.print("Provide your Data: ");
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 

		// Read a line of text
		final String Username = reader.readLine();
		final String Password = reader.readLine(); 
		reader.close();
		
		
		FileWriter writer = new FileWriter("text.txt");
		
		byte[] array = Username.getBytes();
		for(int i = 0; i< Username.length(); i++) {
			writer.write((char)array[i]);
			writer.write("\n");
		}		
		writer.close();
		
		Data data = new Data(Username,Password);
		
		FileOutputStream file = new FileOutputStream("object");
 	   	ObjectOutputStream output = new ObjectOutputStream(file);		// Creates an ObjectOutputStream

 	   
 	   output.writeObject(data);
 	   output.flush();           
	   output.close();
 	   
	   
	   FileInputStream f = new FileInputStream("object");
	   ObjectInputStream input = new ObjectInputStream(f);				// Creates an ObjectInputStream
	
		Data data2 = (Data) input.readObject();									// Reads the object
		
		//output.close();
		input.close();
		f.close();
		
		System.out.println("Username: " + data2.Username);
		System.out.println("Password " + data2.Password);
		
	}

}


class Data implements Serializable {
	
	private static final long serialVersionUID = -7996291219151891528L;
	
	/*transient*/ String Username;
	 transient String Password;
	
	public Data(String username, String password) {
		this.Username = username;
		this.Password = password;
	}
}

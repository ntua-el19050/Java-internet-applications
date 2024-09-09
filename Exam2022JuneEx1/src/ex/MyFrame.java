package ex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Font;




public class MyFrame extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private int rad = 10;
	ArrayList<myCircle> myCircles;
	Canvas canvas;
	JButton clear;
	JButton save;
	JButton sr;
	JTextField tf1;
	
	private static boolean colorSwitch = true;  // button
	
	String filename = "diskdata";
	File mySerFile = new File(filename);
	
	public MyFrame() {
		
		super("Growing Disks");
		this.setSize(700, 700);
		
		JPanel np = new JPanel();
		sr = new JButton("Set Radius");
		sr.addActionListener(this);
		tf1 = new JTextField(10);
		tf1.setFont(new Font("Times New Roman",Font.ITALIC,19));
		np.add(sr);
		np.add(tf1);
		this.getContentPane().add(np,BorderLayout.NORTH);
		
		canvas = new Canvas();
		addCircle();
		this.getContentPane().add(canvas,BorderLayout.CENTER);
		
		save = new JButton("Save Canvas");
		save.addActionListener(this);
		clear = new JButton("Clear Canvas");
		clear.addActionListener(this);
		JPanel sp = new JPanel();
		sp.add(save);
		sp.add(clear);
		this.getContentPane().add(sp,BorderLayout.SOUTH);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		
		Runnable hello = new DisplayMessage("Hello");
		Thread thread1 = new Thread(hello);
		thread1.setName("Hello");
		thread1.start();
		
		try {
			thread1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Runnable newcircle = new NewCircle();
		Thread thread2= new Thread(newcircle);
		thread2.setName("New circle");
		thread2.start();
		
		
		
		System.out.println("Starting \"Timer\" thread...");
		new Timer().schedule(new TimerTask() {
			 @Override
			 public void run() {
				System.out.println("Timer running");
				if (colorSwitch)  {
					 clear.setBackground(Color.RED);
					 colorSwitch = false;
				} else {
		  			 clear.setBackground(Color.GREEN);
		  			 colorSwitch = true;
		  	 	}
			}
		},0, 4000);
		
		
		
	}
	
	// Listeners
	public void actionPerformed(ActionEvent ev) {
		
		if (ev.getSource() == sr) {
			
			System.out.println("\n**** Set Radius CLICKED: ****\n");
			
			rad = Integer.parseInt(tf1.getText());
			repaint();
			JFrame warning = new JFrame("Radius Update");
			JOptionPane.showMessageDialog(warning, "Successfully Updated Radius");
			
		} else if (ev.getSource() == save){

			System.out.println("\n**** SAVE CLICKED: Saving circles... ****\n");	
			saveList();
		}
		else {
			if(colorSwitch) {
				removeCircles();
				rad=10;
				repaint();	
			}else {
				JFrame warning = new JFrame("Warning");
				JOptionPane.showMessageDialog(warning, "Clear button is red!");
			}
		}
		
		
    }
	
	public void addCircle() {
	    
	    int x = (int )(Math.random() * 560);
	    int y = (int )(Math.random() * 360);
	    //int rad=10;
	    myCircles.add(new myCircle(x, y, rad));
	}
	
	
	public void removeCircles() {
		myCircles.clear();
	}
	
	
	class myCircle implements Serializable {

		private static final long serialVersionUID = 1L;
		
		int x;
	    int y;
	    int rad1;

	    public myCircle(int x, int y, int rad) {
	        this.x = (int )(Math.random() * 560);
	        this.y = (int )(Math.random() * 360);											// Similarly on the y axis
	        this.rad1=rad;
	    }

	    
	    public int getX() {
	        return x;
	    }

	    public int getY() {
	        return y;
	    }
	    
	    // "Formatter"
	    @Override
	    public String toString() {
	        return "myCircle{" +
	                "X='" + x + '\'' +
	                ",Y='" + y + '\'' +
	                ", RAD='" + rad1 +'\''+
	                "}";
	    }
	}
	
	class NewCircle implements Runnable {
		 
		public NewCircle() {
			//this.message = message;
		}
		
		public void run() {
			
			while(true) {
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {e.printStackTrace();}
				
				//System.out.println(message);
				      
			    rad = rad + 2; 
				addCircle();
				canvas.repaint();
				if (mySerFile.exists() && !mySerFile.isDirectory())
					readList();
					
			
			}
		
		}
	
	 }
	
	
	@SuppressWarnings("serial")
	// Define inner class Canvas, which is a JPanel used for custom drawing
	class Canvas extends JPanel implements Runnable {
		
		//private String message;
		
		public Canvas() {
			myCircles = new ArrayList<>();
			//this.message = message;
		}
					
		// Draw a circle
		public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Graphics2D g2 = (Graphics2D) g;
	   	        
	        for (myCircle circle: myCircles) {
	            int x = circle.getX();
	            int y = circle.getY();
	       
	           Ellipse2D ellipse = new Ellipse2D.Double(x-rad, y-rad, 2*rad, 2*rad);
		       g2.fill(ellipse); 
	        }
	    }
	
	    
		@Override
	    public void run() {
			
			while(true) {
				
				//collision();
				repaint();								// Refresh the JFrame, callback paintComponent()
		       // System.out.println(message);
		
				try {
					Thread.sleep(100); 					//Time for next repaint
				} catch (InterruptedException e) {System.out.println("interrupted");}
			
			}   
		}
	
	}
	
	
	// For thread1 ("hello")
		class DisplayMessage implements Runnable {
			 
			 private String message;

			 public DisplayMessage(String message) {
			    this.message = message;
			 }

			 public void run() {
			
			    
			    	System.out.println("Starting hello thread…");
			    	try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {e.printStackTrace();}
			    	
			       System.out.println();
			       System.out.println(message);
			    }
		 }
		
		private void saveList() {
			 try {
				 FileOutputStream writeData = new FileOutputStream(mySerFile);
				 ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
				 
				 writeStream.writeObject(myCircles);
				 writeStream.flush();
				 writeStream.close();
			} catch (IOException e) {e.printStackTrace();}
		}
		
		private void readList() {
			 
			 try{

			 	FileInputStream readData = new FileInputStream(mySerFile);
				ObjectInputStream readStream = new ObjectInputStream(readData);
				 
				@SuppressWarnings("unchecked")
				ArrayList<myCircle> readSavedCircles = (ArrayList<myCircle>) readStream.readObject();
				readStream.close();
				
				System.out.println(readSavedCircles);  //.toString());
				
			} catch (IOException | ClassNotFoundException e) {e.printStackTrace();}
			 
		}
	
}

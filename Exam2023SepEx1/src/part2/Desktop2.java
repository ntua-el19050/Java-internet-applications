package part2;



class MyThread implements Runnable {
	 
    @Override
    public void run() {
    	Thread t = Thread.currentThread();
        System.out.println(
        	"Thread \""+t.getName());
        System.out.println(
        	"Thread \""+t.getName()+"\" ended");    
    }
}


public class Desktop2 {


	public static void main(String[] args) {
		
		
		Thread th1 = new Thread(new MyThread(), "th1");
		th1.start();
		try {
			th1.join();
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Thread th2 = new Thread(new MyThread(), "th2");
		th2.start();
		try {
			th2.join();
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Thread th3 = new Thread(new MyThread(), "th3");
		th3.start();
		try {
			th3.join();
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("Main Program Ends");
		}

}

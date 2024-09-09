

//class Waiter implements Runnable{
//	
//	private String lock;
//	
//	public Waiter(String l) {
//		this.lock = l;
//	}
//	
//	@Override
//	public void run() {
//		 String name = Thread.currentThread().getName();
//	            try {
//	            	synchronized (lock) {
//	                System.out.println(name + " Started");
//	                lock.wait();
//	                //msg.wait(200); // Max time to wait
//	                System.out.println(name + " notified");
//	            	}
//	            }
//	            
//	            catch(InterruptedException e){
//	                e.printStackTrace();
//	            }
//		
//	     }
//}
//
//
//class Notifier implements Runnable{
//	
//	private String lock;
//	public Notifier(String l) {
//		this.lock = l;
//	}
//	@Override
//	public void run() {
//		 String name = Thread.currentThread().getName();
//	            	
//					System.out.println(name + " Started");
//					synchronized (lock) {
//					lock.notify();
//					}
//					//msg.wait(200); // Max time to wait
//					System.out.println(name + " Ends");
//	                
//		
//	     }
//}
//
//
//
//public class Desktop2 {
//
//	public static void main(String[] args) {
//		
//		System.out.println("Program Starts");
//		String lock = "lock";
//		
//		Waiter waiter1 = new Waiter(lock);
//		Waiter waiter2 = new Waiter(lock);
//		Waiter waiter3 = new Waiter(lock);
//		
//		Notifier notifier1 = new Notifier(lock);
//		Notifier notifier2 = new Notifier(lock);
//		Notifier notifier3 = new Notifier(lock);
//		
//		Thread tw1 = new Thread(waiter1,"Waiter1");
//		Thread tw2 = new Thread(waiter2,"Waiter2");
//		Thread tw3 = new Thread(waiter3,"Waiter3");
//		
//		Thread tn1 = new Thread(notifier1,"Notifier1");
//		Thread tn2 = new Thread(notifier2,"Notifier2");
//		Thread tn3 = new Thread(notifier3,"Notifier3");
//		
//		tw1.start();
//		tw2.start();
//		tw3.start();
//		
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		tn1.start();
//		tn2.start();
//		tn3.start();
//		
//		try {
//			tw1.join();
//			tw2.join();
//			tw3.join();
//			tn1.join();
//			tn2.join();
//			tn3.join();
//			
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println("Program Ends");
//		
//		
//	}
//
//}

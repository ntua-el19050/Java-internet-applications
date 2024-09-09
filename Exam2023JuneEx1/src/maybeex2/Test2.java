package maybeex2;

import java.time.LocalTime;


/**A simple program that demonstrates the use of wait(), notify(), and notifyAll() methods in threads.*/
	
class Message {
	
	private String msg;
	    
	public Message(String str) {
		this.msg=str;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String str) {
		this.msg=str;
	}

}

class Waiter implements Runnable {
		
		private Message msg;
		
		public Waiter(Message m){
			this.msg=m;
		}

	    @Override
	    public void run() {
	        String name = Thread.currentThread().getName();
	        synchronized (msg) {
	            try {
	                System.out.println(LocalTime.now()+": "+name+" waiting to get notified");
	                msg.wait();
	                //msg.wait(200); // Max time to wait
	            }
	            
	            catch(InterruptedException e){
	                e.printStackTrace();
	            }
	            
	            System.out.println(LocalTime.now()+": "+name+" thread got notified");
	            //process the message now
	            //System.out.println(LocalTime.now()+": "+name+" processed: "+msg.getMsg());
	        }
	    }

}

class Notifier implements Runnable {

    private Message msg;
    
    public Notifier(Message msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(LocalTime.now()+": "+name+" started");
        try {
            Thread.sleep(2000);
            synchronized (msg) {
                msg.setMsg(name+": Notifier work done");
                msg.notify();
//                msg.notify();
            }
        }
        
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
}
public class Test2 {

	public static void main(String[] args) {
		
		Message msg = new Message("Run");
	      
        Waiter waiter1 = new Waiter(msg);
        Thread t1 = new Thread(waiter1,"waiter1");
        t1.start();
        
        Waiter waiter2 = new Waiter(msg);
        Thread t2 = new Thread(waiter2, "waiter2");
        t2.start();
        
        Waiter waiter3 = new Waiter(msg);
        Thread t3 = new Thread(waiter3, "waiter3");
        t3.start();
        
        Notifier notifier = new Notifier(msg);
        Thread t4 = new Thread(notifier, "notifier1");
        Thread t5 = new Thread(notifier, "notifier2");
        Thread t6 = new Thread(notifier, "notifier3");
        t4.start();
        t5.start();
        t6.start();
        
        try {
			t1.join();
			t2.join();
	        t3.join();
	        t4.join();
	        t5.join();
	        t6.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        System.out.println("Main Program Ends");
	}

}

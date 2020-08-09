//package housie;
/*
have shared array
recieve from players.(maybe another list for the winners. this is what is checked)
*/

import java.util.*;

public class Moderator implements Runnable{
	
	int major[];
	private List<Integer> shared;
	private List<Integer> screams;
	private List<Integer> running;
	int num ;
	int proc;
	
	
	
	public Moderator(List<Integer> shared, List<Integer> screams, List<Integer> running, int proc) {
		 
		this.shared = shared;
		this.screams = screams;
		this.running = running;
		this.proc = proc;
		this.major = new int[10];
		this.num = 0;
		Random rand = new Random();
		int upperb = 51;
		for(int i =0; i<10; i++) {
			major[i] = rand.nextInt(upperb);
		}
		
	}
	
	public void run() {
		
		for(int i =0; i<10; i++) {
			System.out.println(this.major[i] + "for thread of moderator");
		}
		this.kickstart();
		return;
	}
	
	public void kickstart() {
		
			//lock something
			//remove previous values
		while(num<10) {
			synchronized(shared) {
				if(!shared.isEmpty()) {
					this.shared.remove(0);
				}
				if(!shared.isEmpty()) {
					this.shared.remove(0);
				}
				
				//add new values
				this.shared.add(num);
				this.shared.add(this.major[num]);
				
				System.out.println(major[num]);
				//num++;
				//loose the lock
				//wait(1000); find multi-threading
			}
			try {
				Thread.sleep(10);
			}
			catch(Exception e){
				System.out.println(e + "is exception in Moderator");
			}
			while(running.size() < ((this.num * this.proc) + this.proc) && screams.isEmpty()) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println(e + "is exception in Moderator");
				}
			};
			synchronized(screams) {
				if(!screams.isEmpty()) {
					this.show_ans();
					return;
				}
				else {
					
					num++;
				}
			}
		}
		System.out.println("no body beats me");
		
	}
	
	public void show_ans() {
		
		synchronized(screams) {
			this.shared.add(11);
			
		}
		
		this.print_ans();
	}
	
	public void print_ans() {
		System.out.println(this.screams.get(0) +" is process num "+this.shared.get(0) + " is element index "+ this.shared.get(1) +" is last value");
	}

}

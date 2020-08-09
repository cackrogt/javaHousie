//package housie;

import java.util.*;

public class The_Game {
	
	
	
	public static void main(String[] args) {
		//so that only one process can at a time read it.
		List<Integer> screams  = Collections.synchronizedList(new ArrayList<Integer>());
		List<Integer> shared  = Collections.synchronizedList(new ArrayList<Integer>());
		List<Integer> running  = Collections.synchronizedList(new ArrayList<Integer>());
		
		int n = 5; //this is the number of processes; take as input
		Moderator M = new Moderator(shared,screams,running,n );
		
		Player[] players = new Player[n];
		for(int i =0; i<n; i++) {
			players[i] = new Player(i,shared,screams,running);
		}
		Thread[] threads = new Thread[n];
		
		for(int i =0; i<n; i++) {
			threads[i] = new Thread(players[i]);
		}
		 
		Thread Mod = new Thread(M);
		
		for(int i =0; i<n; i++) {
			threads[i].start();
		}
		
		Mod.start();
		
	}
	
}

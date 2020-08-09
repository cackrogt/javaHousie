//package housie;

/*
The player is supposed to :(assume we share a list)

	we give it a name as well, so the constructor accepts a name (say)
	have a count of the the number of strikes
	Have own list:2d array, of rows n, columns 2 is enough.(ok maybe arraylist, but later);
	
    read the shared list:
    	this would mean that it knows which number on the list its supposed to read
    
    	
    check its own list:
    	should mark each element as read, to take care of the redundancy problem
    should mark its own list:
    	2d array, of rows n, columns 2 is enough.
    
    
    should scream if 3 of its list are struck:(will come back to this)
    	there is supposed to be a mechanism to check if 2 or more players have bingo at the same time
    	
    
*/
import java.util.*; 

public class Player implements Runnable {
	private int name;
	private int count;
	private int num;
	private int[][] my_list;
	private List<Integer> shared;
	private List<Integer> screams;
	private List<Integer> running;
	//public Moderator M;
	
	public Player(int name, List<Integer> shared, List<Integer> screams, List<Integer> running) {
		this.name = name;
		this.count = 0;
		this.num = 0;
		this.my_list = new int[10][2];
		this.shared = shared;
		this.screams = screams;
		this.running = running;
		Random rand = new Random();
		int upperb = 51;
		for(int i =0; i<10; i++) {
			my_list[i][0] = rand.nextInt(upperb);
			my_list[i][1] = 0;
		}
		
	}
	
	public void run() {
		
		for(int i =0; i<10; i++) {
			System.out.println(this.my_list[i][0] + "for thread number" + this.name);
		}
		this.read_shared();
		return;
	}
	
	private void read_shared() {
		int flag = 0;
		while(true) {
			synchronized(shared){
			
			if(num<10) {
				
				if(shared.isEmpty() || this.shared.size() < 2) {
					try {
						Thread.sleep(10);
					}
					catch(Exception e){
						System.out.println(e + "is exception in Moderator");
					}
					continue;
				}
				if(this.shared.get(0) == num ){
					num++;
					//int lag = 0;// lag is for the case when count is 3;
					for(int i =0; i<10; i++) {
						
						if(my_list[i][0] == this.shared.get(1) && my_list[i][1] == 0){
							count++;
							my_list[i][1] = -1;
							if(count == 3) {
								this.scream();
								flag = -1;
								try {
									Thread.sleep(50);
								}
								catch(Exception e){
									System.out.println(e + "is exception in Moderator");
								}
								
								return;
								
							}
							break;//so that only one of this threads my_list element is struck once;
						}
						}
						if(this.shared.get(0) == 11 && this.shared.get(1) == 51) {
							flag = -1;
							return;
						}
						synchronized(running) {
							this.running.add(this.name);
						}
						try {
							Thread.sleep(10);
						}
						catch(Exception e){
							System.out.println(e + "is exception in Moderator");
						}
					
					}
					else if(this.shared.size() >2) {
						return;
					}
				
					
				}
			
			else {
				//end thread should be inserted here;
				//this.end();
				flag = -1;
			}
			}
			if(flag == -1) {
				break;
			}
		}
	}
	
	private void scream() {
		synchronized(screams) {
			this.screams.add(this.name);
		}
	}
	
}

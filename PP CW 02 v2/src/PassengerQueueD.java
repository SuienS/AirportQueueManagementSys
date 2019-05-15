
public class PassengerQueueD {

	final static int qSize = 20;
	static int first0=0;
	static int first1=0;
	static int last0=0;
	static int last1=0;
	static int maxSize0=0;
	static int maxSize1=0;
	static int maxWait0=0;
	static int maxWait1=0;
	static int queueLength0=0;
	static int queueLength1=0;
	static Passenger[] passengerQueue0 = new Passenger[qSize];
	static Passenger[] passengerQueue1 = new Passenger[qSize];

	public static void add(int qNo,Passenger pObj){
		if(qNo==0){
			if(!isFull0()){
				if(last0==20){
					last0=0;
				}
				passengerQueue0[last0]=pObj;
				last0++;     
			}else{
				System.err.println("Queue is full");
			}
			
		}else if(qNo==1){
			if(!isFull1()){
				if(last1==20){
					last1=0;
				}
				passengerQueue1[last1]=pObj;
				last1++;     
			}else{
				System.err.println("Queue is full");
			}
		}
		
		

	}
	public static void remove0(){
		if(!isEmpty0()){
			if(first0==20){
				first0=0;
			}
			passengerQueue0[first0]=null;
			first0++;    
		}
	}
	
	
	public static void display0(){
		System.out.println("----------------------");
		System.out.println("-------Queue 01-------");
		System.out.println("----------------------");

		int length=qLength0();
		int index=first0;
		for(int i=1;i<=length;i++){
			if(index==20){
				index=0;
			}
 			System.out.println(i+"| "+passengerQueue0[index].getfName()+" "+passengerQueue0[index].getsName());
			System.out.println("------------------------");
			index++;
		}
		
		
		
	}

	public static boolean isEmpty0(){
		boolean empty=false;
		if(qLength0()==0){
			empty=true;
		}
		return empty;
	}

	public static boolean isFull0(){
		boolean full=false;
		if(qLength0()==20){
			full=true;
		}
		
		return full;
	}

	public static int qLength0(){
		queueLength0=0;
		for(Passenger pas:passengerQueue0){
			if(pas!=null){
				queueLength0++;
			}
		}
		return queueLength0;
	}
	
	
	
	
	
	
	
	public static void remove1(){
		if(!isEmpty1()){
			if(first1==20){
				first1=0;
			}
			passengerQueue1[first1]=null;
			first1++;    
		}
	}
	
	
	public static void display1(){
		System.out.println("----------------------");
		System.out.println("-------Queue 02-------");
		System.out.println("----------------------");
		
		int length=qLength1();
		int index=first1;
		for(int i=1;i<=length;i++){
			if(index==20){
				index=0;
			}
 			System.out.println(i+"| "+passengerQueue1[index].getfName()+" "+passengerQueue1[index].getsName());
			System.out.println("------------------------");
			index++;
		}
		
		
		
	}

	public static boolean isEmpty1(){
		boolean empty=false;
		if(qLength1()==0){
			empty=true;
		}
		return empty;
	}

	public static boolean isFull1(){
		boolean full=false;
		if(qLength1()==20){
			full=true;
		}
		
		return full;
	}

	public static int qLength1(){
		queueLength1=0;
		for(Passenger pas:passengerQueue1){
			if(pas!=null){
				queueLength1++;
			}
		}
		return queueLength1;
	}

	
	
	
	
	
}

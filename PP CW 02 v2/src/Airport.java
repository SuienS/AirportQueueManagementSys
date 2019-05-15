import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Airport {

	static boolean added=false;
	static boolean simulated=false;

	static ArrayList<Passenger> passengerArray = new ArrayList<Passenger>();
	static int count0=0;
	static int count1=0;
	static int totDelay0=0;
	static double totWait0=0;
	static int totDelay1=0;
	static int totDelay=0;
	static double totWait1=0;
	static double totWait=0;

	public static void main(String[] args) {
		menu();
		System.out.println();

	}
	
	
	public static void menu(){
		System.out.println();
		System.out.println("            Main Menu");
		System.out.println("    --------------------------");
		System.out.println("A: Add a passenger to the queue");
		System.out.println("V: View all passengers in the queue");
		System.out.println("D: Delete passenger from the queue");
		System.out.println("S: Store Added passenger data in to file");
		System.out.println("L: Load passenger data from file");
		System.out.println("R: Run boarding process simulation");
		System.out.println("or Press any other to exit");
		
		Scanner scn = new Scanner(System.in);
		String in;
		
		do{
			  in = scn.nextLine().toUpperCase();
			  if( passengerArray.size()==0 && ( in.equals("V")||in.equals("D")||in.equals("S")||in.equals("R") ) ){
					System.err.println("No passengers were loaded or added to execute the process you selected ");
					System.out.println();
					System.out.println("Choose A or L or Press any other to exit");
			  }
		}while( passengerArray.size()==0 && ( in.equals("V")||in.equals("D")||in.equals("S")||in.equals("R") ) );
		runFunc(in);
		
	}
	
	
	public static void runFunc(String choice){
		Scanner input = new Scanner(System.in);
		String in;
		
		switch(choice){
		
			case "A": String sName,fName;
					  added=true;
					  simulated=true;
					  do{
						  System.out.println("Enter Passenger Surname:");
						  sName=input.nextLine();
					  }while( !isValid(0,sName)  );
					  
					  do{	
						  System.out.println("Enter Passenger First Name:");
						  fName=input.nextLine();
					  }while(!isValid(0,fName));
					  
				  
					  add(sName,fName,dieRoll(3));
				  
					  System.out.println("Your record was successfully recorded!");
					  confirm();
				  
				  
			break;
		case "V": 	System.out.println("=========================");
					System.out.println(" Passengers in the Queue");
					System.out.println("=========================");
					PassengerQueueD.display0();
				   confirm();
		    break;
		 	
		  case "D": if(PassengerQueueD.isEmpty0()){
			  			System.err.println("Queue is empty");
			  			confirm();
		  			}
			  		String qNo,passenger;
			  		
		  			PassengerQueueD.display0();
		  			PassengerQueueD.display1();
		  			System.out.println("Select Queue number:");
		  			qNo=input.nextLine();
		  			int qNum=Integer.parseInt(qNo);
		  			if(qNum==1){
			  			PassengerQueueD.display0();
		  			}else if(qNum==2){
		  				PassengerQueueD.display1();
		  			}else{
		  				System.err.println("Error");
		  				confirm();
		  			}
		  			do{	
		  				System.out.println("Enter passenger Number you want to delete:");
		  				passenger=input.nextLine();
		  			}while(!isValid(1,passenger));
		  			
		  			int pNum=Integer.parseInt(passenger);
		  			delete(qNum,pNum-1);
					confirm();
			 break;
			 
		  	  
		    case "S" : save();
		    		   confirm();
		      break;
		  	  
		    case "L" : if(added){
		    				System.out.println("Your pre added data will be overwritten");
		    				simulated=false;
		    			}
		    			loadData();
 		                confirm();
 		    break;  
 		    
		    case "R" :if(simulated&&!added){
		    			System.out.println("Your data will be re-simulated");
		    		  }
		    		  if(added){
		    			  getSummary();
		    		  }else{
		    			  simulation();
		    		  }
		    		  reportProd();
		    		  confirm();

		    break;
		  	 
		  
		
		    default:System.out.println("Exiting...");
		    			System.exit(0);
		}
		
		
	}
	
	
	
	
	
	public static void simulation() {
		added=false;
		simulated=true;
		totDelay0=0;
		count0=0;
		count1=0;
		totDelay1=0;
		int gettinCount=0;
		int total=0;
		int Indx=0;
		int dieDelay=0;
		int leftSpace=0;
		
		while(total<passengerArray.size()){
			
			Indx=total;
			gettinCount=dieRoll(1);
			leftSpace=(20-PassengerQueueD.qLength0());
			if( leftSpace>=gettinCount ){
				
				total+=gettinCount;
				
				for(int i=Indx; i<total ;i++){
					if(i >= passengerArray.size()){
						break;
					}
					dieDelay=dieRoll(3);
					
					if(totDelay0>totDelay1){
						totDelay1+=dieDelay;
						totDelay+=dieDelay;
						totWait1+=totDelay1;
						totWait+=totDelay;
						passengerArray.get(i).setSecondsProccessing(dieDelay);				
						passengerArray.get(i).setSecondsInQueue(totDelay1);
						passengerArray.get(i).setqNo(1);
						PassengerQueueD.add(1,passengerArray.get(i));
						count1++;
						
					}else{
						totDelay0+=dieDelay;
						totDelay+=dieDelay;
						totWait0+=totDelay0;
						totWait+=totDelay;
						passengerArray.get(i).setSecondsProccessing(dieDelay);				
						passengerArray.get(i).setSecondsInQueue(totDelay0);
						passengerArray.get(i).setqNo(0);
						PassengerQueueD.add(0,passengerArray.get(i));
						count0++;
						
					}
					
					
					if( PassengerQueueD.qLength0()>PassengerQueueD.maxSize0 ){
						PassengerQueueD.maxSize0=PassengerQueueD.qLength0();
					}
					
					if( PassengerQueueD.qLength1()>PassengerQueueD.maxSize1 ){
						PassengerQueueD.maxSize1=PassengerQueueD.qLength1();
					}
					
				}				
				PassengerQueueD.remove0();
				PassengerQueueD.remove1();	
			}else{
				PassengerQueueD.remove0();
				PassengerQueueD.remove1();
			}
					
		}
		
		getSummary();
	}
	
	
	public static void getSummary(){
		String name;
		int wTime;
		int pDelay;
		int min0=18;
		int min1=18;
		double avgD0,avgD1,avgP0,avgP1;
		System.out.printf("%-27s%-20s%-20s\n","================","===================","===================");
		System.out.printf("%-27s%-20s%-20s\n"," Passenger Name ","  Waiting Time(s)  ","Proccessing Time(s)");
		System.out.printf("%-27s%-20s%-20s\n","================","===================","===================");
		if(added){
			totWait=0;
			totWait0=0;
			totWait1=0;
			totDelay=0;
			totDelay0=0;
			totDelay1=0;
			min0=PassengerQueueD.passengerQueue0[0].getSecondsInQueue();
			min1=PassengerQueueD.passengerQueue1[0].getSecondsInQueue();
			System.out.println("------------------------------------------------------------");
			System.out.println("----------------Passenger Boarding Queue 01-----------------");
			System.out.println("------------------------------------------------------------");
			for(Passenger pas:PassengerQueueD.passengerQueue0){
				if(pas==null){
					break;
				}
				name=pas.getfName()+" "+pas.getsName();
				wTime=pas.getSecondsInQueue();	
				pDelay=pas.getSecondsProccessing();
				
				totDelay+=pDelay;
				totWait+=totDelay;
				totWait0+=wTime;
				totDelay0+=pDelay;
				System.out.printf("%-27s %-20d %-20d\n", name,wTime,pDelay);
			}
			PassengerQueueD.maxSize0=PassengerQueueD.qLength0();
			System.out.println("------------------------------------------------------------");
			System.out.println("----------------Passenger Boarding Queue 02-----------------");
			System.out.println("------------------------------------------------------------");
			for(Passenger pas:PassengerQueueD.passengerQueue1){
				if(pas==null){
					break;
				}
				name=pas.getfName()+" "+pas.getsName();
				wTime=pas.getSecondsInQueue();	
				pDelay=pas.getSecondsProccessing();

				totDelay+=pDelay;
				totWait+=totDelay;
				totWait1+=wTime;
				totDelay1+=pDelay;
				System.out.printf("%-27s %-20d %-20d\n", name,wTime,pDelay);
			}
			PassengerQueueD.maxSize1=PassengerQueueD.qLength1();
			
			avgD0=totWait0/(double)PassengerQueueD.maxSize0;
			avgP0=totDelay0/(double)PassengerQueueD.maxSize0;
			avgD1=totWait1/(double)PassengerQueueD.maxSize1;
			avgP1=totDelay1/(double)PassengerQueueD.maxSize1;
		}else{
			avgD0=totWait0/(double)count0;
			avgP0=totDelay0/(double)count0;
			avgD1=totWait1/(double)count1;
			avgP1=totDelay1/(double)count1;
			System.out.println("------------------------------------------------------------");
			System.out.println("----------------Passenger Boarding Queue 01-----------------");
			System.out.println("------------------------------------------------------------");
		    for(Passenger pas:passengerArray){
		    	if(pas.getqNo()==0){
		    		
		    		name=pas.getfName()+" "+pas.getsName();
					wTime=pas.getSecondsInQueue();	
					if(min0>wTime){
		    			min0=wTime;
		    		}
					pDelay=pas.getSecondsProccessing();
					System.out.printf("%-27s %-20d %-20d\n", name,wTime,pDelay);
		    	}
			}
		    System.out.println("------------------------------------------------------------");
			System.out.println("----------------Passenger Boarding Queue 02-----------------");
			System.out.println("------------------------------------------------------------");
		    for(Passenger pas:passengerArray){
		    	if(pas.getqNo()==1){
		    		name=pas.getfName()+" "+pas.getsName();
					wTime=pas.getSecondsInQueue();
					if(min1>wTime){
		    			min1=wTime;
		    		}
					pDelay=pas.getSecondsProccessing();
					System.out.printf("%-27s %-20d %-20d\n", name,wTime,pDelay);
		    	}
			}
		}
		
		
		
		
		System.out.printf("%-75s\n","=================================================================");
		System.out.printf("%-75s\n","            Summary and Comparison of the Passenger Queues");
		System.out.printf("%-75s\n","=================================================================");
		System.out.printf("%-38s%-30s%-30s\n","   ","Queue No.1","Queue No.2");
		System.out.printf("%-38s%-30s%-30s\n","   ","----------","----------");
		System.out.printf("%-38s%-30d%-30d\n","Max Sizes queues attained:-",PassengerQueueD.maxSize0,PassengerQueueD.maxSize1);
		System.out.printf("%-38s%-30.1f%-30.1f\n","Avg proccessing times(s):-",avgP0,avgP1);
		System.out.printf("%-38s%-30d%-30d\n","Max waiting times(s):-",totDelay0,totDelay1);
		System.out.printf("%-38s%-30d%-30d\n","Min waiting times(s):-",min0,min1);
		System.out.printf("%-38s%-30.1f%-30.1f\n","Avg waiting times(s):-",avgD0,avgD1);
		
		double avgD=totWait/(double)(count0+count1);
		
		int maxSize=PassengerQueueD.maxSize0;
		double avgPFnl=(avgP0+avgP1)*0.5;
		double avgDFnl=(avgD0+avgD1)*0.5;
		int totDelaySFnl=totDelay0+totDelay1;
		double avgP=(totDelaySFnl)/(double)(count0+count1);
		int totDelayFnl=totDelay0;
		int min=min0;
		
		System.out.printf("%-90s\n","================================================================================");
		System.out.printf("%-90s\n","        Comparison of the Passenger Single and Dual Queue implementation");
		System.out.printf("%-90s\n","================================================================================");
		System.out.printf("%-38s%-30s%-30s\n","   ","Single Queue"," Dual Queue");
		System.out.printf("%-38s%-30s%-30s\n","   ","------------","------------");
		if(maxSize<PassengerQueueD.maxSize1){
			maxSize=PassengerQueueD.maxSize1;
		}
		if(totDelayFnl<totDelay1){
			totDelayFnl=totDelay1;
		}
		if(min>min1){
			min=min1;
		}
		String max;
		if(count0+count1>20){
			max="18-20";
		}else{
			max=Integer.toString(count0+count1);
		}
		System.out.printf("%-40s%-30s%-30d\n","Max Size queue attained:-",max,maxSize);
		System.out.printf("%-40s%-30.1f%-30.1f\n","Avg proccessing time per passenger(s):-",avgP,avgPFnl);
		System.out.printf("%-40s%-30d%-30d\n","Max waiting time(s):-",totDelaySFnl,totDelayFnl);
		System.out.printf("%-40s%-30d%-30d\n","Min waiting time(s):-",min0,min);
		System.out.printf("%-40s%-30.1f%-30.1f\n","Avg waiting time(s):-",avgD,avgDFnl);
	    System.out.println("-----------------------------------------------------------------------------------");
		System.out.printf("%-60s%-30.1f\n","Avg waiting time Difference(s):-",avgD-avgDFnl);
	    System.out.println("-----------------------------------------------------------------------------------");

		
	}
	
	
	
	
	


	public static void add(String sName,String fName,int wait){
		if(totDelay0>totDelay1){
			count1++;
			Passenger passenger = new Passenger();
			passenger.setsName(sName);
			passenger.setfName(fName);
			passenger.setSecondsProccessing(wait);
			passenger.setqNo(1);
			totDelay1+=wait;
			passenger.setSecondsInQueue(totDelay1);
			passengerArray.add(passenger);
			PassengerQueueD.add(1,passenger);
			System.out.println("Added to queue No.2");

		}else{
			count0++;
			Passenger passenger = new Passenger();
			passenger.setsName(sName);
			passenger.setfName(fName);
			passenger.setSecondsProccessing(wait);
			passenger.setqNo(0);
			totDelay0+=wait;
			passenger.setSecondsInQueue(totDelay0);
			passengerArray.add(passenger);
			PassengerQueueD.add(0,passenger);
			System.out.println("Added to queue No.1");

		}
	}
	
	
	public static void delete(int qNo,int num){
		
		if(qNo==1){
			count0--;
			int index=PassengerQueueD.first0+num;
			if(index>19){
				index-=20;
			}
			int delay=PassengerQueueD.passengerQueue0[index].getSecondsProccessing();
			int nxt=index+1;
			for(int i=index;;i++){
				if(i==20){
					i=0;
				}
				if(nxt==20){
					nxt=0;
				}
				if(PassengerQueueD.passengerQueue0[nxt]==null){
					PassengerQueueD.maxWait0=PassengerQueueD.passengerQueue0[i-1].getSecondsInQueue();
					PassengerQueueD.passengerQueue0[i]=null;
					break;
				}
				PassengerQueueD.passengerQueue0[i].setfName(PassengerQueueD.passengerQueue0[nxt].getfName());
				PassengerQueueD.passengerQueue0[i].setsName(PassengerQueueD.passengerQueue0[nxt].getsName());
				PassengerQueueD.passengerQueue0[i].setSecondsInQueue(PassengerQueueD.passengerQueue0[nxt].getSecondsInQueue()-delay);
				PassengerQueueD.passengerQueue0[i].setSecondsProccessing(PassengerQueueD.passengerQueue0[nxt].getSecondsProccessing());
				nxt++;
			}
			PassengerQueueD.last0--;
			System.out.println("Successfully Deleted No."+(num+1)+" passenger of the queue No."+qNo);
		}else{
			count1--;
			int index=PassengerQueueD.first1+num;
			if(index>19){
				index-=20;
			}
			int delay=PassengerQueueD.passengerQueue1[index].getSecondsInQueue();
			int nxt=index+1;
			for(int i=index;;i++){
				if(i==20){
					i=0;
				}
				if(nxt==20){
					nxt=0;
				}
				if(PassengerQueueD.passengerQueue1[nxt]==null){
					PassengerQueueD.maxWait1=PassengerQueueD.passengerQueue1[i-1].getSecondsInQueue();
					PassengerQueueD.passengerQueue1[i]=null;
					break;
				}
				PassengerQueueD.passengerQueue1[i].setfName(PassengerQueueD.passengerQueue1[nxt].getfName());
				PassengerQueueD.passengerQueue1[i].setsName(PassengerQueueD.passengerQueue1[nxt].getsName());
				PassengerQueueD.passengerQueue1[i].setSecondsInQueue(PassengerQueueD.passengerQueue1[nxt].getSecondsInQueue()-delay);
				PassengerQueueD.passengerQueue1[i].setSecondsProccessing(PassengerQueueD.passengerQueue1[nxt].getSecondsProccessing());
				nxt++;
			}
			PassengerQueueD.last1--;
			System.out.println("Successfully Deleted No."+(num+1)+" passenger of the queue No."+qNo);
		}
	}
	
	
	
	
	public static int dieRoll(int count){
		int num=0;
		for(int i=0;i<count;i++){
			num+=(int)(Math.random()*6) + 1;
		}
		return num;
	} 

	
	
	
	public static void reportProd(){
		
		 try {
			 
		    PrintWriter writer = new PrintWriter(new File("report.dat"));
		    String name;
			int wTime;
			int pDelay;
			int min0=18;
			int min1=18;
			double avgD0,avgD1,avgP0,avgP1;
			writer.printf("%-27s%-20s%-20s\n","================","===================","===================");
			writer.printf("%-27s%-20s%-20s\n"," Passenger Name ","  Waiting Time(s)  ","Proccessing Time(s)");
			writer.printf("%-27s%-20s%-20s\n","================","===================","===================");
			if(added){
				totWait=0;
				totWait0=0;
				totWait1=0;
				totDelay=0;
				totDelay0=0;
				totDelay1=0;
				min0=PassengerQueueD.passengerQueue0[0].getSecondsInQueue();
				min1=PassengerQueueD.passengerQueue1[0].getSecondsInQueue();
				writer.println("------------------------------------------------------------");
				writer.println("----------------Passenger Boarding Queue 01-----------------");
				writer.println("------------------------------------------------------------");
				for(Passenger pas:PassengerQueueD.passengerQueue0){
					if(pas==null){
						break;
					}
					name=pas.getfName()+" "+pas.getsName();
					wTime=pas.getSecondsInQueue();	
					pDelay=pas.getSecondsProccessing();

					totDelay+=pDelay;
					totWait+=totDelay;
					totWait0+=wTime;
					totDelay0+=pDelay;
					writer.printf("%-27s %-20d %-20d\n", name,wTime,pDelay);
				}
				PassengerQueueD.maxSize0=PassengerQueueD.qLength0();
				writer.println("------------------------------------------------------------");
				writer.println("----------------Passenger Boarding Queue 02-----------------");
				writer.println("------------------------------------------------------------");
				for(Passenger pas:PassengerQueueD.passengerQueue1){
					if(pas==null){
						break;
					}
					name=pas.getfName()+" "+pas.getsName();
					wTime=pas.getSecondsInQueue();	
					pDelay=pas.getSecondsProccessing();
					
					totWait+=totDelay;
					totDelay+=pDelay;
					totWait1+=wTime;
					totDelay1+=pDelay;
					writer.printf("%-27s %-20d %-20d\n", name,wTime,pDelay);
				}
				PassengerQueueD.maxSize1=PassengerQueueD.qLength1();
				
				avgD0=totWait0/(double)PassengerQueueD.maxSize0;
				avgP0=totDelay0/(double)PassengerQueueD.maxSize0;
				avgD1=totWait1/(double)PassengerQueueD.maxSize1;
				avgP1=totDelay1/(double)PassengerQueueD.maxSize1;
			}else{
				avgD0=totWait0/(double)count0;
				avgP0=totDelay0/(double)count0;
				avgD1=totWait1/(double)count1;
				avgP1=totDelay1/(double)count1;
				writer.println("------------------------------------------------------------");
				writer.println("----------------Passenger Boarding Queue 01-----------------");
				writer.println("------------------------------------------------------------");
			    for(Passenger pas:passengerArray){
			    	if(pas.getqNo()==0){
			    		
			    		name=pas.getfName()+" "+pas.getsName();
						wTime=pas.getSecondsInQueue();	
						if(min0>wTime){
			    			min0=wTime;
			    		}
						pDelay=pas.getSecondsProccessing();
						writer.printf("%-27s %-20d %-20d\n", name,wTime,pDelay);
			    	}
				}
			    writer.println("------------------------------------------------------------");
			    writer.println("----------------Passenger Boarding Queue 02-----------------");
			    writer.println("------------------------------------------------------------");
			    for(Passenger pas:passengerArray){
			    	if(pas.getqNo()==1){
			    		name=pas.getfName()+" "+pas.getsName();
						wTime=pas.getSecondsInQueue();
						if(min1>wTime){
			    			min1=wTime;
			    		}
						pDelay=pas.getSecondsProccessing();
						writer.printf("%-27s %-20d %-20d\n", name,wTime,pDelay);
			    	}
				}
			}
			
			
			
			
			writer.printf("%-75s\n","=================================================================");
			writer.printf("%-75s\n","            Summary and Comparison of the Passenger Queues");
			writer.printf("%-75s\n","=================================================================");
			writer.printf("%-38s%-30s%-30s\n","   ","Queue No.1","Queue No.2");
			writer.printf("%-38s%-30s%-30s\n","   ","----------","----------");
			writer.printf("%-38s%-30d%-30d\n","Max Sizes queues attained:-",PassengerQueueD.maxSize0,PassengerQueueD.maxSize1);
			writer.printf("%-38s%-30.1f%-30.1f\n","Avg proccessing times(s):-",avgP0,avgP1);
			writer.printf("%-38s%-30d%-30d\n","Max waiting times(s):-",totDelay0,totDelay1);
			writer.printf("%-38s%-30d%-30d\n","Min waiting times(s):-",min0,min1);
			writer.printf("%-38s%-30.1f%-30.1f\n","Avg waiting times(s):-",avgD0,avgD1);
			
			double avgD=totWait/(double)(count0+count1);
			
			int maxSize=PassengerQueueD.maxSize0;
			double avgPFnl=(avgP0+avgP1)*0.5;
			double avgDFnl=(avgD0+avgD1)*0.5;
			int totDelaySFnl=totDelay0+totDelay1;
			double avgP=(totDelaySFnl)/(double)(count0+count1);
			int totDelayFnl=totDelay0;
			int min=min0;
			
			writer.printf("%-90s\n","================================================================================");
			writer.printf("%-90s\n","        Comparison of the Passenger Single and Dual Queue implementation");
			writer.printf("%-90s\n","================================================================================");
			writer.printf("%-38s%-30s%-30s\n","   ","Single Queue"," Dual Queue");
			writer.printf("%-38s%-30s%-30s\n","   ","------------","------------");
			if(maxSize<PassengerQueueD.maxSize1){
				maxSize=PassengerQueueD.maxSize1;
			}
			if(totDelayFnl<totDelay1){
				totDelayFnl=totDelay1;
			}
			if(min>min1){
				min=min1;
			}
			//case 2iiii
			writer.printf("%-40s%-30s%-30d\n","Max Size queue attained:-","18 - 20",maxSize);
			writer.printf("%-40s%-30.1f%-30.1f\n","Avg proccessing time per passenger(s):-",avgP,avgPFnl);
			writer.printf("%-40s%-30d%-30d\n","Max waiting time(s):-",totDelaySFnl,totDelayFnl);
			writer.printf("%-40s%-30d%-30d\n","Min waiting time(s):-",min0,min);
			writer.printf("%-40s%-30.1f%-30.1f\n","Avg waiting time(s):-",avgD,avgDFnl);
			writer.println("-----------------------------------------------------------------------------------");
			writer.printf("%-60s%-30.1f\n","Avg waiting time Difference(s):-",avgD-avgDFnl);
			writer.println("-----------------------------------------------------------------------------------");

		    
		    writer.flush();  
		    writer.close(); 
		    System.out.println("Your data has been also saved to report.dat");

		   } catch (FileNotFoundException e) {      
		     e.printStackTrace();
		   }
	}
	
	public static void save(){
		String name;
		int wTime;
		int pDelay;
		int qNo;
		
		 try {
			 
		    PrintWriter writer = new PrintWriter(new File("passengerData.txt"));
			writer.printf("%-27s%-15s%-20s%-20s\n","================","===============","===================","===================");
			writer.printf("%-27s%-15s%-20s%-20s\n"," Passenger Name ","    Queue No","  Waiting Time(s)  ","Proccessing Time(s)");
			writer.printf("%-27s%-15s%-20s%-20s\n","================","===============","===================","===================");
		    for(Passenger pas:passengerArray){
				name=pas.getfName()+" "+pas.getsName();
				wTime=pas.getSecondsInQueue();	
				pDelay=pas.getSecondsProccessing();
				qNo=pas.getqNo();
				
				writer.printf("%-27s %-15d %-20d %-20d\n", name,qNo+1,wTime,pDelay);
			}
		    
		    writer.printf("%-65s\n","=======================================================");
		   
		    writer.flush();  
		    writer.close(); 
		    System.out.println("Your passenger data has been saved to passengerData.txt");

		   } catch (FileNotFoundException e) {      
		     e.printStackTrace();
		   }
	}
	
	
	public static void loadData(){
		added=false;
		 try {

			 Scanner scan = new Scanner(new File("passengers.dat"));

				//Retrieve
				        for(int i=0;scan.hasNextLine() ;i++){
				        	
				        	String line = scan.nextLine();
				        	
				        	String[] data = line.split(" ");
				        	Passenger pObj=new Passenger();
				        	pObj.setfName(data[0]);
				        	pObj.setsName(data[1]);
				        	passengerArray.add(pObj);
				        	System.out.println(pObj.getfName()+" "+pObj.getsName());
				        }
			        System.out.println("Above passengers were loaded to the program succesfully");
				    scan.close(); 

		  } catch (FileNotFoundException e) {
			  System.err.println("passengers.dat not found");
			     e.printStackTrace();
		  }   


		}
	
	
	
	
	public static void confirm(){
		Scanner scn = new Scanner(System.in);
		
		System.out.println("Enter Y to go to menu or any to exit");
		String in=scn.nextLine();
		if(in.toUpperCase().equals("Y")){
			menu();
		}else{
			System.out.println("Exiting....");
			System.exit(0);
		}
		
		  
	}
	
	
	public static boolean isValid(int x,String in){
		boolean validity = true;
		switch(x){
			case 0:if(!in.matches("[a-zA-Z]+")){
				   	   System.err.println("Enter a valid name ");
				   	   validity=false;
				   }
				break;
			case 1:if(!in.matches("[0-9]+")){
				       validity=false;
					   System.err.println("Enter a valid number ");
				   }else if(Integer.parseInt(in)>PassengerQueueD.qLength0()){
			           validity=false;
					   System.err.println("Enter number in the valid range");
				   }
				break;
			default:
		
		}
		
		
		return validity;
		
	}
	
}

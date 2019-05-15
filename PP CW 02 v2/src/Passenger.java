
public class Passenger {
	private String sName,fName;
	private int secondsInQueue=-1;
	private int secondsProccessing=-1;
	private int qNo=-1;

	
	
	
	public String getsName() {
		return sName;
	}
	public void setsName(String sName) {
		this.sName = sName;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	
	public int getSecondsInQueue() {
		return secondsInQueue;
	}
	public void setSecondsInQueue(int secondsInQueue) {
		this.secondsInQueue = secondsInQueue;
	}

	public int getSecondsProccessing() {
		return secondsProccessing;
	}
	public void setSecondsProccessing(int secondsProccessing) {
		this.secondsProccessing = secondsProccessing;
	}
	public int getqNo() {
		return qNo;
	}
	public void setqNo(int qNo) {
		this.qNo = qNo;
	}
	
	
}
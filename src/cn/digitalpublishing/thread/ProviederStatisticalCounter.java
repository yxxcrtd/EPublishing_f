package cn.digitalpublishing.thread;

public class ProviederStatisticalCounter {

	
	private int count = 0;
	
	public synchronized void countDown(){
		if(count==1){
			count--;
		}
	}
	
	public int getCount(){
		return count;
	}
	
	public synchronized void countAdd(){
		if(count==0){
			count++;
		}
	}


}

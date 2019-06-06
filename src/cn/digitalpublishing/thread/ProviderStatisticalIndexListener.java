package cn.digitalpublishing.thread;

public class ProviderStatisticalIndexListener {

	private ProviederStatisticalCounter counter = null;
	
	private int i = 0;
	
	private String msg="";
	
	public ProviderStatisticalIndexListener(){
		counter = new ProviederStatisticalCounter();
	}
	
	/**
	 * 执行PDA处理
	 */
	public void executeHandle(){
			if(counter.getCount()==0){
//				System.out.println("开启出版社统计处理线程"+i+"！");
				msg="";
				i++;
				counter.countAdd();
				Thread t= new ProviderStatisticalIndexProcess(counter);  
				t.start();
			}else{
				if(msg.trim().length()==0){
					msg="开启出版社统计处理线程正在处理，等待......";
//					System.out.println(msg);
				}
			}
	}

}

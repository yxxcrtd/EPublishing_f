package cn.digitalpublishing.thread;


public class AlertsListener {
	
	
	
	private AlertsCounter counter = null;
	
	private int i = 0;
	
	private String msg="";
	
	public AlertsListener(){
		counter = new AlertsCounter();
	}
	
	/**
	 * 执行邮件发送
	 */
	public void executeAlerts(){
			if(counter.getCount()==0){
				System.out.println("开启订阅提醒处理线程"+i+"！");
				msg="";
				i++;
				counter.countAdd();
				Thread t = new AlertsProcess(counter);   
				t.start();
			}else{
				if(msg.trim().length()==0){
					msg="有订阅提醒正在处理，等待......";
					System.out.println(msg);
				}
			}
	}

}

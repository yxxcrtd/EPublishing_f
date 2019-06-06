package cn.digitalpublishing.thread;

public class ExpirationReminderListener {
	
	private ExpirationReminderCounter counter = null;
	
	private int i = 0;
	
	private String msg="";
	
	public ExpirationReminderListener(){
		counter = new ExpirationReminderCounter();
	}
	
	/**
	 * 执行邮件发送
	 */
	public void executeAlerts(){
			if(counter.getCount()==0){
				System.out.println("开启订阅到期提醒处理线程"+i+"！");
				msg="";
				i++;
				counter.countAdd();
				Thread t = new ExpirationReminderProcess(counter);   
				t.start();
			}else{
				if(msg.trim().length()==0){
					msg="有订阅到期提醒正在处理，等待......";
					System.out.println(msg);
				}
			}
	}
}

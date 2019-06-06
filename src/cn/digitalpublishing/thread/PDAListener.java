package cn.digitalpublishing.thread;

public class PDAListener {

	private PDACounter counter = null;

	private int i = 0;

	private String msg = "";

	public PDAListener() {
		counter = new PDACounter();
	}

	/**
	 * 执行PDA处理
	 */
	public void executeHandle() {
		if (counter.getCount() == 0) {
			// System.out.println("开启PDA处理线程"+i+"！");
			msg = "";
			i++;
			counter.countAdd();
			Thread t = new PDAProcess(counter);
			t.start();
		} else {
			if (msg.trim().length() == 0) {
				msg = "有PDA信息正在处理，等待......";
//				System.out.println(msg);
			}
		}
	}

}

package cn.digitalpublishing.util.web;

public class RandomCodeUtil {
	
	private static final String pool="123456789abcdefghijklmnopqrstuvwxzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public static String generateRandomCode(int length) {
		// 定义验证码的字符表 
		String chars = pool;
		char[] rands = new char[length];
		for (int i = 0; i < length; i++) {
			int rand = (int) (Math.random() * chars.length());
			rands[i] = chars.charAt(rand);
		}
		return String.valueOf(rands);
	}
	//随机生成一位字母
	public static String generateRandomNumber(int length){
		// 定义验证码的字符表 
		
		char[] ziMu={'A','B','C','D','E','F','G','H','I','J','K','L','M',
				'N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		boolean[] rands=new boolean[ziMu.length];
		char suiJi='1';
		int s;
		for (int i = 0; i < length; i++) {
			do{
				s=(int)(Math.random()*ziMu.length);
			}while(rands[s]);
			suiJi=ziMu[s];
			rands[s]=true;
			
		}
	
		return String.valueOf(suiJi);
	}
	
	public static void main(String[] args){
		//System.out.println(RandomCodeUtil.generateRandomCode(8));
		String s=RandomCodeUtil.generateRandomNumber(1);
		System.out.println(s);
	}

}

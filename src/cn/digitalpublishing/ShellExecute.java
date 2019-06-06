package cn.digitalpublishing;

import java.io.File;
import java.io.InputStream;

public class ShellExecute {

		/*
	　　* args[0] : shell 命令 如"ls" 或"ls -1";
	　　* args[1] : 命令执行路径 如"/" ;
	　　*/
	public String execute(String[] cmmand,String directory)throws Exception{
		String result = "" ;
		try {
			ProcessBuilder builder = new ProcessBuilder(cmmand);
			if ( directory != null )
				builder.directory ( new File ( directory ) ) ;
				builder.redirectErrorStream (true) ;
				Process process = builder.start ( ) ;
				//得到命令执行后的结果
				InputStream is = process.getInputStream ( ) ;
				byte[] buffer = new byte[1024] ;
				while ( is.read(buffer) != -1 ) {
					result = result + new String (buffer) ;
				}
				is.close ( ) ;
		} catch (Exception e1 ) {
			e1.printStackTrace ( ) ;
		}
		return result ;
	}
}

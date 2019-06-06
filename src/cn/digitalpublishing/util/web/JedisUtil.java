package cn.digitalpublishing.util.web;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Redis 工具类
 */
public class JedisUtil {

	private static Map<String, JedisPool> map = new HashMap<String, JedisPool>();

	/**
	 * Constructor
	 */
	public JedisUtil() {

	}

	// 获取连接池
	private static JedisPool getRedisPool(String ip, int port) {
		ResourceBundle bundle = ResourceBundle.getBundle("redis");
		String key = ip + ":" + port;
		JedisPool pool = null;
		//		if (!map.containsKey(key)) {
		//			JedisPoolConfig config = new JedisPoolConfig();
		//			config.setMaxActive(RedisConfig.getMaxactive());
		//			config.setMaxIdle(RedisConfig.getMaxidle());
		//			config.setMaxWait(RedisConfig.getMaxwait());
		//			config.setTestOnBorrow(true);
		//			config.setTestOnReturn(true);
		//			try {
		//				// 如遇到：java.net.SocketTimeoutException: Read timed out exception的异常信息请尝试在构造JedisPool的时候设置自己的超时值。JedisPool默认的超时时间是2秒(单位毫秒)
		//				pool = new JedisPool(config, ip, port, RedisConfig.getTimeout());
		//				map.put(key, pool);
		//			} catch (Exception e) {
		//				e.printStackTrace();
		//			}
		//		} else {
		//			pool = map.get(key);
		//		}
		return pool;
	}

	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.0.121");
		System.out.println(jedis.get("key1"));

		//		ResourceBundle bundle = ResourceBundle.getBundle("redis");
		//		System.out.println(bundle.getString("redis1.ip"));
	}

}

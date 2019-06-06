package cn.digitalpublishing.redis.dao;

import java.util.List;

import cn.digitalpublishing.redis.po.Book;

public interface BookDao {

	/**
	 * 访问计数器
	 */
	Long incr(String keyId);

	/**
	 * 获取字符串列表
	 */
	List<String> getList(String keyId);

	/**
	 * 通过 key 追加 value 值
	 */
	Long append(String key, String value);

	/**
	 * 设置 key 的过期时间
	 */
	boolean expire(String key, long value);

	/**
	 * 通过 key 获取对象
	 */
	Book get(String keyId);
	
	boolean setValueByKey(String key, String value);

	String getStringByKey(String keyId);

	/**
	 * 往 List 中 插入 字符串（用户Id、图书Id）
	 */
	Long lpush(String key, String value);

	/**
	 * 插入有序集合
	 */
	boolean zadd(String key, String value);

	/**
	 * 插入有序集合(key, orderby, value)
	 */
	boolean zadd(String key, long i, String value);

	/**
	 * 获取有序集合
	 */
	List<String> getSet(String keyId);

	/** 
	 * 添加
	 */
	boolean add(Book book);

	/**
	 * 批量新增 使用pipeline方式
	 */
	boolean add(List<Book> list);

	/**
	 * 删除
	 */
	void delete(String key);

	/**
	 * 删除多个
	 */
	void delete(List<String> keys);

	/**
	 * 修改
	 */
	boolean update(Book book);

}

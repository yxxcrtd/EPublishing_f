package cn.digitalpublishing.redis.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

import cn.digitalpublishing.redis.dao.BookDao;
import cn.digitalpublishing.redis.dao.RedisDao;
import cn.digitalpublishing.redis.po.Book;

/**
 * 图书 Redis 的实现
 */
public class BookDaoImpl extends RedisDao<String, Book>implements BookDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.digitalpublishing.redis.dao.BookDao#incr(java.lang.String)
	 */
	@Override
	public Long incr(final String keyId) {
		Long result = redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				return connection.incr(serializer.serialize(keyId));
			}
		});
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.digitalpublishing.redis.dao.BookDao#getList(java.lang.String)
	 */
	@Override
	public List<String> getList(final String key) {
		final List<String> list = new ArrayList<String>();
		redisTemplate.execute(new RedisCallback<List<String>>() {
			public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] keyId = serializer.serialize(key);
				List<byte[]> l = connection.lRange(keyId, 0, -1);
				for (byte[] s : l) {
					list.add(new String(s));
				}
				return list;
			}
		});
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.digitalpublishing.redis.dao.BookDao#append(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean expire(final String key, final long value) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] keyId = serializer.serialize(key);
				return connection.expire(keyId, value);
			}
		});
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.digitalpublishing.redis.dao.BookDao#append(java.lang.String, java.lang.String)
	 */
	@Override
	public Long append(final String key, final String value) {
		Long result = redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] keyId = serializer.serialize(key);
				byte[] name = serializer.serialize(value);
				return connection.append(keyId, name);
			}
		});
		return result;
	}

	@Override
	public Long lpush(final String key, final String value) {
		Long result = redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] keyId = serializer.serialize(key);
				byte[] name = serializer.serialize(value);
				return connection.lPush(keyId, name);
			}
		});
		return result;
	}

	@Override
	public boolean zadd(final String key, final String value) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] keyId = serializer.serialize(key);
				byte[] name = serializer.serialize(value);
				return connection.zAdd(keyId, 0, name);
			}
		});
		return result;
	}

	@Override
	public boolean zadd(final String key, final long i, final String value) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] keyId = serializer.serialize(key);
				byte[] name = serializer.serialize(value);
				return connection.zAdd(keyId, i, name);
			}
		});
		return result;
	}

	@Override
	public List<String> getSet(final String key) {
		final List<String> list = new ArrayList<String>();
		redisTemplate.execute(new RedisCallback<List<String>>() {
			public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] keyId = serializer.serialize(key);
				Set<byte[]> l = connection.zRange(keyId, 0, -1);
				for (byte[] s : l) {
					list.add(new String(s));
				}
				return list;
			}
		});
		return list;
	}

	@Override
	public boolean add(final Book book) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] key = serializer.serialize(book.getId());
				byte[] name = serializer.serialize(book.getTitle());
				return connection.setNX(key, name);
			}
		});
		return result;
	}

	@Override
	public boolean add(final List<Book> list) {
		Assert.notEmpty(list);
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				for (Book book : list) {
					byte[] key = serializer.serialize(book.getId());
					byte[] name = serializer.serialize(book.getTitle());
					connection.setNX(key, name);
				}
				return true;
			}
		}, false, true);
		return result;
	}

	@Override
	public void delete(String key) {
		List<String> list = new ArrayList<String>();
		list.add(key);
		delete(list);
	}

	@Override
	public void delete(List<String> keys) {
		redisTemplate.delete(keys);
	}

	@Override
	public boolean update(final Book book) {
		String key = book.getId();
		if (get(key) == null) {
			throw new NullPointerException("数据不存在, key = " + key);
		}
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] key = serializer.serialize(book.getId());
				byte[] name = serializer.serialize(book.getTitle());
				connection.set(key, name);
				return true;
			}
		});
		return result;
	}

	@Override
	public Book get(final String keyId) {
		Book result = redisTemplate.execute(new RedisCallback<Book>() {
			public Book doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] key = serializer.serialize(keyId);
				byte[] value = connection.get(key);
				if (value == null) {
					return null;
				}
				String name = serializer.deserialize(value);
				return new Book(keyId, name, 0);
			}
		});
		return result;
	}

	@Override
	public boolean setValueByKey(final String key, final String value) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] k = serializer.serialize(key);
				byte[] v = serializer.serialize(value);
				connection.set(k, v);
				return true;
			}
		});
		return result;
	}

	@Override
	public String getStringByKey(final String keyId) {
		String result = redisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] key = serializer.serialize(keyId);
				byte[] value = connection.get(key);
				if (value == null) {
					return null;
				}
				return serializer.deserialize(value);
			}
		});
		return result;
	}

}

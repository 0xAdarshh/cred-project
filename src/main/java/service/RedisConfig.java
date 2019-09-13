package service;


import java.time.Duration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

//This class is used to setup the Redis configuration
public class RedisConfig {
	
	public static final String REDIS_HOST = "localhost";
	public static final int REDIS_PORT = 6379;
	public static final int REDIS_ENTRY_EXPIRY_IN_SECONDS = 3600;
	private static JedisPool jedisPool;
	
	
	public static void initCache() {
		final JedisPoolConfig poolConfig = buildPoolConfig();
		jedisPool = new JedisPool(poolConfig, "localhost");
	}

	private static JedisPoolConfig buildPoolConfig() {
		    final JedisPoolConfig poolConfig = new JedisPoolConfig();
		    poolConfig.setMaxTotal(128);
		    poolConfig.setMaxIdle(128);
		    poolConfig.setMinIdle(16);
		    poolConfig.setTestOnBorrow(true);
		    poolConfig.setTestOnReturn(true);
		    poolConfig.setTestWhileIdle(true);
		    poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
		    poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
		    poolConfig.setNumTestsPerEvictionRun(3);
		    poolConfig.setBlockWhenExhausted(true);
		    return poolConfig;
	}
	
	static Jedis jedis;

	public static JedisPool getJedisPool() {
	    initCache();
	    return jedisPool;
	}
	  
	public static boolean isCacheAvailable() {
		initCache();
		try {
			jedis = jedisPool.getResource();
		    // Is connected
		    return true;
		} catch (JedisConnectionException e) {
			// Not connected
		    return false;
		}
	}
	  
	public static void destroyCache() {
		initCache();
		jedis = jedisPool.getResource();
		jedis.flushAll();
	}

}

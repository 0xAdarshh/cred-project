package service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import model.geodata;
import redis.clients.jedis.Jedis;

public class GeocodeServiceTest {
	
	@Autowired
	GeocodeService geocodeservice;

	@Before
	public void setUp() throws Exception {
		RedisConfig.destroyCache();
	}

	@After
	public void tearDown() throws Exception {
		RedisConfig.destroyCache();
	}

	@Test
	public void testGetgeocode() {
		
		Jedis jedis = RedisConfig.getJedisPool().getResource();
		
		//Call twice first from API then second call should get data from cache
		geodata g1 = geocodeservice.getgeocode("Delhi");
		geodata g2 = geocodeservice.getgeocode("Delhi");
		
		assertEquals("28.7040592", g2.getLatitude());
		assertEquals("77.10249019999999", g2.getLatitude());
		assertEquals("CACHE", g2.getLatitude());
	}

}

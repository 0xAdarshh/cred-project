package service;

import java.io.IOException;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.geodata;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class GeocodeService {

	public geodata getgeocode(String query) {
		
	JedisPool jedispool = RedisConfig.getJedisPool();
    Jedis jedis = jedispool.getResource();
    geodata g = new geodata();
    
    Set<String> names = jedis.keys(query);
    
    
    if (names.size() == 0) {		//Check if Cache is available if yes
    	
    	//get data from API and store it in Cache for future API calls

    	//Call the Google GeoCode RestApi 
    	RestTemplate restTemplate = new RestTemplate();
    	String tempquery = query.replaceAll(",","+");
    	String ResourceUrl = "https://maps.googleapis.com/maps/api/geocode/json?address="+tempquery+"&key=AIzaSyBC1mXeLL9uJrgXQiv3CQDJyt92ivDNPs0";
    	ResponseEntity<String> response = restTemplate.getForEntity(ResourceUrl, String.class);	
    	
    	//Jackson is used to read the JSON response given by Google API
    	ObjectMapper mapper = new ObjectMapper();
    	JsonNode root;
    	try {
    		root = mapper.readTree(response.getBody());
    		JsonNode results = root.path("results");
    		
    		for (JsonNode result:results) {
    			
    			JsonNode loc = result.path("geometry").path("location");
    			
    			String lat = loc.path("lat").toString();	//latitude
    			String long1 = loc.path("lng").toString();	//longitude
    			
    			String json = "{\"latitude\":\""+lat+"\",\"longitude\":\""+long1+"\",\"source\":\"CACHE\"}";	//store JSON as string in Cache
    			
    			jedis.set(query,json);						//store key -> value pair in cache
    			
    			g.setLatitude(lat);
    			g.setLongitude(long1);
    			g.setSource("API");
    		}
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    }else {
    	//if data available get it from Cache and Deserialize JSON string -> object  (i.e Geodata)
    	String geojson = jedis.get(query);
    	
    	try {
            g = new ObjectMapper().readValue(geojson, geodata.class);	//Deserialize using Objectmapper
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
	return g;	//Return Final GeoData i.e. Latitude,Longitude,Source
	}
	
}

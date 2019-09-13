# GEOCODE API
Project Assignment for CRED Internship 2020

**Description** - Geocoding is the process of converting the address like (Indiranagar, Bangalore) into geographic coordinates (like latitude: 12.978369 and longitude: 77.640836).  
Example: When we enter: Bangalore  
Result:  
● Latitude: 12.971599  
● Longitude: 77.594563  
  
**Framework** - Spring Boot  
**Architecture** - Model - View - Controller - Service  
**In Memory Caching** - Redis  
**Library** -  Jackson , Jedis (Redis)  

**Flow of Control -**  
```
1. GeoCodeApplication (main function - [src/main/java/demo/Geocode1Application.java])
2. GeocodeController (Controller - [src/main/java/controller/GeocodeController.java])
3. GeocodeService (Service Layer - [src/main/java/service/GeocodeService.java])  
*Data Transfer Object (DTO) - (geodata - model layer - [src/main/java/model/geodata.java])*  
```

**Caching -**  
```
As we know it is expensive to call the third party API repeatedly we need to reduce the number of API calls  
to Google Geocode API. Hence, if we get multiple requests asking for geo coordinates for same city we need to  
avoid network call to Google API and serve the request from cache.  
```

API Contract

API - 
Response - 

*SOURCE FIELD IN API RESPONSE IS TO IDENTIFY THE RESPONSE SOURCE i.e API CALL OR CACHE* 

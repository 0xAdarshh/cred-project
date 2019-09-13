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
```
API - http://localhost:8080/apicall/geocode?locname=mysore  
Response (for first call) - {"latitude":"12.2958104","longitude":"76.6393805","source":"API"}  
Response (for subsequent calls) - {"latitude":"12.2958104","longitude":"76.6393805","source":"CACHE"}  

*SOURCE field in API response is to identify the response source i.e API call or CACHE* 
```

First Call (Source API)

Second Call (Source CACHE)

Server Log

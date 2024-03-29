package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.GeocodeService;
import model.geodata;

@RestController
@RequestMapping("/apicall")
public class GeocodeController {
	
	@Autowired
	GeocodeService gs;
	
	//API REQUEST [http://localhost:8080/apicall/geocode?locname=cityname] gets mapped to this function
	@GetMapping("/geocode")
	  public geodata getGeoData(
	      @RequestParam("locname") String locname) {

	    geodata gd = gs.getgeocode(locname);

	    return gd;
	  };
}

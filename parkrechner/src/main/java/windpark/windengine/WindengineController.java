package windpark.windengine;

import org.springframework.web.bind.annotation.RestController;

import windpark.model.WindengineData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class WindengineController {

    /*@Autowired
    private WindengineService service;*/
	
    @RequestMapping("/")
    public String windengineMain() {
    	String mainPage = "This is the windengine application! (DEZSYS_GK72_WINDPARK) <br/><br/>" +
                          "<a href='http://localhost:8080/windengine/001/data'>Link to windengine/001/data</a><br/>" +
                          "<a href='http://localhost:8080/windengine/001/transfer'>Link to windengine/001/transfer</a><br/>";
        return mainPage;
    }
    
    @RequestMapping("/windengine/{windengineID}/data")
    public String windengineData( @PathVariable String windengineID ) {
        return "Hallo1";
    }

    @RequestMapping("/windengine/{windengineID}/transfer")
    public String windengineTransfer( @PathVariable String windengineID ) {
        return "Hallo2" + windengineID + System.currentTimeMillis();
    }
}
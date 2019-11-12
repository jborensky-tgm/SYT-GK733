package windpark.windengine;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import windpark.model.WindengineData;
import windpark.parkrechner.Parkrechner;

import java.util.LinkedList;

@RestController
public class WindengineController {

    @Autowired
    private Parkrechner service;

    @RequestMapping("/")
    public String windengineMain() {
        return service.mainPage();
    }
    
    @RequestMapping("/windengineGood")
    public String windengineDataGood() {
        return service.ausgabe();
    }

    @RequestMapping("/windengineBetter")
    public String windengineDataBetter() {
        return service.ausgabeBetter();
    }
}
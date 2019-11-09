package windpark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import windpark.parkrechner.Parkrechner;

import java.util.LinkedList;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
public class Gk72Application {
	public static void main(String[] args) {
		run(Gk72Application.class, args);
		LinkedList<String> we = new LinkedList<>();
		we.add("001");
		we.add("002");
		Parkrechner p1 = new Parkrechner(we);
	}
}

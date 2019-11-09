package windpark.parkrechner;

import java.util.LinkedList;

//Beschreibung, wie man die Exception abfangen würde, wenn es möglich wäre!!

public class MainParkrechner {
    public static void main(String[] args) {
            LinkedList<String> we = new LinkedList<>();
            we.add("001");
            we.add("002");
            Parkrechner p1 = new Parkrechner(we);
            /*while (true) {
                p1.empfangen();
                if (p1.getLogs().size() >= 4) break;
            }
            p1.getEmpfaenger().end();
            */
    }
}

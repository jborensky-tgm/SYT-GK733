public class mainParkrechner {
    public static void main(String[] args) {
        parkrechner p1 = new parkrechner();
        while (true) {
            p1.empfangen();
            if (p1.getLogs().size() >= 4) break;
        }
        p1.getEmpfaenger().end();
    }
}

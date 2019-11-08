import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Map;
import java.util.TreeMap;


public class parkrechner {

    private int windpark;

    public TreeMap<LOG, JSONObject> getLogs() {
        return logs;
    }

    public void setLogs(TreeMap<LOG, JSONObject> logs) {
        this.logs = logs;
    }

    private TreeMap<LOG, JSONObject> logs;

    public JMSChatReceiver getEmpfaenger() {
        return empfaenger;
    }

    public void setEmpfaenger(JMSChatReceiver empfaenger) {
        this.empfaenger = empfaenger;
    }

    private JMSChatReceiver empfaenger;
    private JSONObject aktJSON;

    public parkrechner() {
        this.logs = new TreeMap<>();
        this.empfaenger = new JMSChatReceiver();
    }

    public JSONObject generateJSON(String s) {
        JSONParser p = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) p.parse(s);
        } catch (ParseException e) {
            System.out.println("Die Syntax des gesendeten String ist nicht valide.");
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void empfangen() {
        System.out.println("Hallo");
        String nachricht = this.empfaenger.receive();
        if (nachricht!=null) {
            System.out.println("Hallo");
            this.aktJSON = generateJSON(nachricht);
            //eventuell den Timestamp der Empfangszeit
            String ts = (String) this.aktJSON.get("timestamp");
            String id = (String) this.aktJSON.get("windegineID");
            this.logs.put(new LOG(ts, id), this.aktJSON);

            //Test-Output
            for (Map.Entry<LOG, JSONObject> entry : this.logs.entrySet()) {
                LOG key = entry.getKey();
                JSONObject value = entry.getValue();
                System.out.println(key.getTime() + ", ID: " + key.getID() + "\n" + value);
                System.out.println("");
            }
        }
    }
}

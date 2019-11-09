package windpark.parkrechner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Parkrechner {

    private int windpark;

    public TreeMap<LOG, JSONObject> getLogs() {
        return logs;
    }

    public void setLogs(TreeMap<LOG, JSONObject> logs) {
        this.logs = logs;
    }

    private TreeMap<LOG, JSONObject> logs;

    public LinkedList<JMSChatReceiver> getEmpfaenger() {
        return empfaenger;
    }

    public void setEmpfaenger(LinkedList<JMSChatReceiver> empfaenger) {
        this.empfaenger = empfaenger;
    }

    private LinkedList<JMSChatReceiver> empfaenger;
    private JSONObject aktJSON;
    private LinkedList<String> windengines;
    private HashSet<LOG> loged;

    public Parkrechner(LinkedList<String> we) {
        this.logs = new TreeMap<>();
        this.loged = new HashSet<>();
        this.windengines = we;
        this.empfaenger = new LinkedList<>();
        for(String elem : windengines) {
            System.out.println(elem);
            this.empfaenger.add(new JMSChatReceiver(this,elem));
        }
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

    public void empfangen(String nachricht) {
        if (nachricht!=null) {
            this.aktJSON = generateJSON(nachricht);
            //eventuell den Timestamp der Empfangszeit
            String ts = (String) this.aktJSON.get("timestamp");
            String id = (String) this.aktJSON.get("windegineID");
            this.logs.put(new LOG(ts, id), this.aktJSON);
            this.loged.add(new LOG(ts, id));
            this.log(this.aktJSON);

            //Test-Output
            for (Map.Entry<LOG, JSONObject> entry : this.logs.entrySet()) {
                LOG key = entry.getKey();
                JSONObject value = entry.getValue();
                System.out.println(key.getTime() + ", ID: " + key.getID() + "\n" + value);
            }

            System.out.println();
        }
    }

    public void log(JSONObject json) {
        String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        File file = new File("parkrechner.log");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file, true);
            String info = String.format("%s [Windengine: %s] INFO - Windengine Data=%s", s, json.get("windegineID"), json);
            fr.write(info);
            fr.write("\r\n");
            fr.write("\r\n");
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void terminate() {
        for(JMSChatReceiver c1 : this.empfaenger) {
            c1.end();
        }
    }
}

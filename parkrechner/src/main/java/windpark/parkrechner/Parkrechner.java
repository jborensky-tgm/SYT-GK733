package windpark.parkrechner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
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

    /*public Parkrechner(LinkedList<String> we) {
        this.logs = new TreeMap<>();
        this.loged = new HashSet<>();
        this.windengines = we;
        this.empfaenger = new LinkedList<>();
        for(String elem : windengines) {
            System.out.println(elem);
            this.empfaenger.add(new JMSChatReceiver(this,elem));
        }
    }*/

    public Parkrechner() {
        LinkedList<String> we = new LinkedList<>();
        we.add("001");
        we.add("002");
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
            System.out.println(id);
            this.logs.put(new LOG(ts, id), this.aktJSON);
            this.loged.add(new LOG(ts, id));
            this.log(this.aktJSON);
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

    public String ausgabe() {
        String ausgabe = "";
        String format = "<!DOCTYPE>\n" +
                "<html>\n" +
                "<head>\n" +
                "</head>\n" +
                "<body>\n" +
                "<pre id=\"json\"></pre>\n" +
                "<script>\n" +
                "\tvar data = {%s}\n" +
                "    document.getElementById(\"json\").innerHTML = JSON.stringify(data, null, 4);\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>\n";
        for (LOG key : this.logs.keySet()) {
            JSONObject value = this.logs.get(key);
            ausgabe += String.format("\"%s\": %s,", key.getID() + ", " + key.getTime(), value);
            System.out.println(key.getID());
        }
        String output = String.format(format, ausgabe);
        return output;
    }

    public String ausgabeBetter() {
        String format = "<!DOCTYPE>\n" +
                "<html>\n" +
                "<head>\n" +
                "</head>\n" +
                "<body>\n" +
                "<pre id=\"json\"></pre>\n" +
                "<script>\n" +
                "\tvar data = {%s}\n" +
                "    document.getElementById(\"json\").innerHTML = JSON.stringify(data, null, 4);\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>\n";
        HashSet<String> hilfe = new HashSet<>();
        for (LOG key : this.logs.keySet()) {
            hilfe.add(key.getID());
        }
        String out = "";
        System.out.println(hilfe);
        int i = 0;
        for (String s: hilfe) {
            if (i>0) {
                out += ",\"" + s + "\": {";
            } else {
                out += "\"" + s + "\": {";
            }
            String ausgabe = "";
            for (LOG key : this.logs.keySet()) {
                if (key.getID().equals(s)) {
                    JSONObject value = this.logs.get(key);
                    ausgabe += String.format("\"%s\": %s,", key.getTime(), value);
                }
            }
            System.out.println(ausgabe);
            out += ausgabe;
            out += "}";
            i++;
        }
        String output = String.format(format, out);
        output = output.replace("},}", "}}");
        return output;
    }

    public String mainPage() {
        String ausgabe =  "This is the windengine application! (DEZSYS_GK72_WINDPARK) <br/><br/>" + "<a href='http://localhost:8080/windengineGood'>Link to all windenginesGood</a><br/>" + "<a href='http://localhost:8080/windengineBetter'>Link to all windenginesBetter</a><br/>";
        return ausgabe;
    }
}

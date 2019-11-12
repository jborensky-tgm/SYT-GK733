import model.WindengineData;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import windengine.WindengineSimulation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Windkraftanlage {

    private String id;
    private final String leer = " ";
    private JMSChatSender sender;

    public Windkraftanlage(String id) {
        this.id = id;
        this.sender = new JMSChatSender(this,this.id);
    }

    public void senden() {
        WindengineSimulation simulation = new WindengineSimulation();
        WindengineData data = simulation.getData(this.id);
        JSONObject json = job(this.id,timeS(),data.getWindspeed(),data.getUnitWindspeed(),data.getTemperature(),data.getUnitTemperature(),data.getPower(), data.getUnitPower(),data.getBlindpower(),data.getUnitBlindpower(), data.getRotationspeed(), data.getUnitRotationspeed(), data.getBladeposition(),data.getUnitBladeposition());
        String test = data.toString();
        System.out.println(test);
        this.sender.send(json.toJSONString());
    }

    public long timeL() {
        return System.currentTimeMillis();
    }
    public String timeS() {
        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date resultdate = new Date(yourmilliseconds);
        return sdf.format(resultdate);
    }

    public JSONObject job(String weID, String tS, Double ws, String uWs, Double t, String uT, Double p, String uP, Double bp, String uBp, Double rs, String uRs, Double blp, String uBlp) {
        JSONObject obj = new JSONObject();

        obj.put("windegineID", weID);
        obj.put("timestamp", tS);
        obj.put("windspeed", new Double(ws));
        obj.put("unitWindspeed", uWs);
        obj.put("temperature", new Double(t));
        obj.put("unitTemperature", uT);
        obj.put("power", new Double(p));
        obj.put("unitPower", uP);
        obj.put("blindpower", new Double(bp));
        obj.put("unitBlindpower", uBp);
        obj.put("rotationspeed", new Double(rs));
        obj.put("unitRotationspeed", uRs);
        obj.put("bladeposition", new Double(bp));
        obj.put("unitBladeposition", uBp);

        return obj;
    }

    public void log(String json, boolean ok) {
        String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        File file = new File(String.format("windkraftanlage-ID%s.log",this.id));
        String info = null;
        JSONParser p = new JSONParser();
        JSONObject jobject = null;
        try {
            jobject = (JSONObject) p.parse(json);
            info = String.format("%s [Windengine: %s] INFO - Windengine sending Data='Windegine Info: ID = %s, timestamp = %s, windspeed = %f'", s, this.id, jobject.get("windegineID"), jobject.get("timestamp"), jobject.get("windspeed"));
        } catch (ParseException e) {
            System.out.println("Die Syntax des gesendeten String ist nicht valide.");
            e.printStackTrace();
        }
        if (ok) {
            info += String.format("\r\nSUCCESS - Message wurde gesendet. [%s]", s);
        } else {
            info += String.format("\r\nFAILED - Message wurde nicht gesendet. [%s]", s);
        }

        FileWriter fr = null;
        try {
            fr = new FileWriter(file, true);
            fr.write(info);
            fr.write("\r\n");
            fr.write("\r\n");
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void terminate() {
        this.sender.end();
    }
}

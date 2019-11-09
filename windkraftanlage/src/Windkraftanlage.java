import model.WindengineData;
import org.json.simple.JSONObject;
import windengine.WindengineSimulation;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Windkraftanlage {

    private String id;
    private final String leer = " ";
    private JMSChatSender sender;

    public Windkraftanlage(String id) {
        this.id = id;
        this.sender = new JMSChatSender(this.id);
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

    public void terminate() {
        this.sender.end();
    }
}

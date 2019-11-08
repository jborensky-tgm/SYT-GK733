import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class windkraftanlage {

    private String id;
    private final String leer = " ";
    private JMSChatSender sender;

    public windkraftanlage(String id) {
        this.id = id;
        this.sender = new JMSChatSender(this.id);
    }

    public void senden() {
        JSONObject json = job(this.id,timeS(),21.19,"kmH",21.19,"C",42.31, "kwH",126.53,"kwH", 126.53, "uM", 30.0,"grad");
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

    public static JSONObject job(String weID, String tS, Double ws, String uWs, Double t, String uT, Double p, String uP, Double bp, String uBp, Double rs, String uRs, Double blp, String uBlp) {
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

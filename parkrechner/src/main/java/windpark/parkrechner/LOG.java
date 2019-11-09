package windpark.parkrechner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LOG implements Comparable<LOG>{
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    private String time;
    private String ID;

    public LOG (String time, String id) {
        this.time = time;
        this.ID = id;
    }

    @Override
    public int compareTo(LOG o) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date timeAkt = sdf.parse(this.time);
            Date timeV = sdf.parse(o.getTime());
            long t1 = timeAkt.getTime();
            long t2 = timeV.getTime();
            if (t1 < t2) {
                return  -1;
            } else if (t1 > t2){
                return  1;
            };
        } catch (ParseException e) {
            System.out.println("Die Syntax des gesendeten String ist nicht valide.");
            e.printStackTrace();
        }
        return 0;
    }
}

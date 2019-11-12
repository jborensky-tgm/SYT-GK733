public class MainWindkraftanlage {
    public static void main(String[] args) {
        Windkraftanlage w1 = new Windkraftanlage("001");
        Windkraftanlage w2 = new Windkraftanlage("002");
        for (int i = 0; i<3; i++) {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            w1.senden();
            w2.senden();
        }
        //w1.terminate();
    }
}

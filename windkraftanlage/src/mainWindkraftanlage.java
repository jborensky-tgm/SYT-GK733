public class mainWindkraftanlage {
    public static void main(String[] args) {
        windkraftanlage w1 = new windkraftanlage("1");
        for (int i = 0; i<4; i++) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            w1.senden();
        }
        //w1.terminate();
    }
}

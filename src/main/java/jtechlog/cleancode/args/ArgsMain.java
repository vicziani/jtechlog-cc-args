package jtechlog.cleancode.args;

public class ArgsMain {

    public static void main(String[] args) {
        try {
            Args arg = new Args("l,p#,d*", args);
            boolean logging = arg.getBoolean('l');
            int port = arg.getInt('p');
            String directory = arg.getString('d');
            executeApplication(logging, port, directory);
        }
        catch (ArgsException ae) {
            System.out.printf("Argument error: %s\n", ae.getMessage());
        }
    }

    private static void executeApplication(boolean logging, int port, String directory) {
        System.out.printf("logging is %s, port:%d, directory:%s\n",logging, port, directory);
    }
}

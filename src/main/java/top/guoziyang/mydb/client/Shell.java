package top.guoziyang.mydb.client;

import java.util.Scanner;
import java.util.logging.Logger;

public class Shell {
    private static final Logger logger = Logger.getLogger(Shell.class.getName());

    private Client client;

    public Shell(Client client) {
        this.client = client;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        try {
            while(true) {
                System.out.print(":> ");
                String statStr = sc.nextLine();
                if("exit".equals(statStr) || "quit".equals(statStr)) {
                    break;
                }
                try {
                    byte[] res = client.execute(statStr.getBytes());
                    logger.info(new String(res));
                } catch(Exception e) {
                    logger.info(e.getMessage());
                }

            }
        } finally {
            sc.close();
            client.close();
        }
    }
}

package Administrare;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Audit {

    public Audit() {

    }

    private static final String AUDIT_LOG_FILE = "audit.csv";

    public void log(String operation, String tableName, String fields) {
        String timestamp = getTimestamp();
        String logEntry = String.format("%s - %s - %s - %s", timestamp, operation, tableName, fields);
        writeLog(logEntry);
    }

    private String getTimestamp() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    private void writeLog(String logEntry) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(AUDIT_LOG_FILE, true));
            writer.println(logEntry);
            writer.close();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}

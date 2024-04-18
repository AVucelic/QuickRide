package ConnectivityLayers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DLException extends Exception {
    Exception exception;

    public DLException(Exception e) {
        // source for setting initial messages:
        // https://stackoverflow.com/questions/30542846/java-default-exception-messages
        super("Unable to complete operation. Please contact the administrator.");
        log(e);
    }

    public DLException(Exception e, String[] additionalMessage) {
        super("Unable to complete operation. Please contact the administrator.");
        logAdditional(e, additionalMessage);
    }

    private void log(Exception e) {
        FileWriter fw;
        try {
            fw = new FileWriter(new File("./logs.txt"));
            PrintWriter pw = new PrintWriter(fw);

            // source on how to get current time :
            // https://www.javatpoint.com/java-get-current-date
            DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            pw.write("Time: " + formater.format(now) + "\n");
            pw.write("Exception: " + e.getClass().getName() + "\n");
            pw.write("Error message: " + e.getMessage() + "\n\n");
            pw.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    private void logAdditional(Exception e, String[] message) {
        PrintWriter pw;
        FileWriter fw;

        try {
            fw = new FileWriter(new File("./logs.txt"));
            pw = new PrintWriter(fw);

            // how to get exception type source:
            // https://stackoverflow.com/questions/32519410/get-exception-instance-class-name
            pw.write("Exception type: " + e.getClass().getCanonicalName() + "\n");
            if (e instanceof SQLException) {
                SQLException sqle = (SQLException) e;
                sqlExceptionDetails(sqle, pw);
            }
            for (int i = 0; i < message.length; i++) {
                pw.write("Additional info: \n");
                pw.write(message[i] + " \n");
            }

            log(e);
            pw.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    private void sqlExceptionDetails(SQLException sql, PrintWriter pw) {
        pw.write("Sql Exception State Code: " + sql.getSQLState() + "\n");
        pw.write("Sql Error Code: " + sql.getErrorCode() + "\n");
        pw.write("Sql Exception description: " + sql.getMessage() + "\n");
        pw.write(
                "Reason: Please check Documentation form more details. Search up the error code, state code and refer to your sqlException description. \n");

    }

}
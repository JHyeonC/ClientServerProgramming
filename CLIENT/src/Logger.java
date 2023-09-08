import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Logger implements Closeable {
   private FileWriter fileWriter;
   public Logger(String filename) {
      File tmpFile = new File(filename);
      try {
         this.fileWriter = new FileWriter(tmpFile, true);
      } catch (IOException e) {
         throw new RuntimeException("File is a directory or file could not be created or does not exist", e);
      }
   }

   public void log(String message) {
      try {
         fileWriter.write(message);
         fileWriter.write("\n");
         fileWriter.flush();
      } catch (IOException e) {
         throw new RuntimeException("I/O error occurred", e);
      }
   }
   
   public void setLogMessage(String studentId, StackTraceElement command) {
		  Timestamp timestamp = new Timestamp(System.currentTimeMillis()); 
	      String message = String.format("(%s, %s, %s)", studentId, command, timestamp);
	      log(message);
	   }
   
   @Override
   public void close() throws IOException {
      fileWriter.close();
   }
}
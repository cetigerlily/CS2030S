import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

/**
 * This program finds different ways one can travel by bus (with a bit 
 * of walking) from one bus stop to another.
 *
 * @author Celeste Cheah
 * @version CS2030S AY21/22 Semester 2, Lab 8
 */

public class Main {
  /**
   * The program read a sequence of (id, search string) from either
   * standard input or a file.  If an invalid filename is given, the 
   * program would quit with an error message.
   * @param args Command line arguments
   */
  public static void main(String[] args) {
    Instant start = Instant.now();
    try {
      Scanner sc = createScanner(args);
      ArrayList<CompletableFuture<String>> resultList = new ArrayList<>();

      while (sc.hasNext()) {  
        BusStop srcId = new BusStop(sc.next());
        String searchString = sc.next();
        boolean filler = true;

        CompletableFuture<String> result = BusSg.findBusServicesBetween(srcId, searchString)
            .thenComposeAsync((x) -> x.description());
        filler = resultList.add(result);
      }
      sc.close();

      int num = resultList.size();
      CompletableFuture<?>[] arrayType = new CompletableFuture<?>[num];
      
      CompletableFuture<?>[] results = resultList.toArray(arrayType);
      CompletableFuture.allOf(results).join();

      for (int i = 0; i < num; i += 1) {
        results[i].thenAccept((x) -> System.out.println(x));
      }

    } catch (FileNotFoundException exception) {
      System.err.println("Unable to open file " + args[0] + " " + exception);
    }
    Instant stop = Instant.now();
    System.out.printf("Took %,dms\n", Duration.between(start, stop).toMillis());
  }

  /**
   * Create and return a scanner. If a command line argument is given,
   * treat the argument as a file and open a scanner on the file. Else,
   * create a scanner that reads from standard input.
   *
   * @param args The arguments provided for simulation.
   * @return A scanner.
   * @throws FileNotFoundException Throws if filename given is not valid.
   */
  private static Scanner createScanner(String[] args) throws FileNotFoundException {
    // Read from stdin if no filename is given, otherwise read from the
    // given file.
    if (args.length == 0) {
      // If there is no argument, read from standard input.
      return new Scanner(System.in);
    } 
    // Else read from file
    FileReader fileReader = new FileReader(args[0]);
    return new Scanner(fileReader);
  }
}

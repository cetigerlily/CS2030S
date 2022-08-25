import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Encapsulates the result of a query: for a bus stop and a search string,
 * it stores a map of bus services that servce stops with matching name.
 * e.g., stop 12345, name "MRT", with map contains:
 *    96: 11223 "Clementi MRT"
 *    95: 22334 "Beuno Vista MRT"
 *
 * @author Celeste Cheah
 * @version CS2030S AY21/22 Semester 2, Lab 8
 */

class BusRoutes {
  final BusStop stop;
  final String name;
  final Map<BusService, CompletableFuture<Set<BusStop>>> services;

  /**
   * Constructor for creating a bus route.
   * @param stop The first bus stop.
   * @param name The second bus stop.
   * @param services The set of bus services between the two stops.
   */
  BusRoutes(BusStop stop, String name, 
      Map<BusService, CompletableFuture<Set<BusStop>>> services) {
    this.stop = stop;
    this.name = name;
    this.services = services;
  }

  /**
   * Return a string describing the bus route.
   * @return The first line contains the query information:
   *     bus stop id and search string. The remaining line contains 
   *     the bus services and matching stops served.
   */
  public CompletableFuture<String> description() {
    CompletableFuture<String> description = CompletableFuture.completedFuture(
        "Search for: " + this.stop + " <-> " + this.name + ":\n" + "From " + this.stop + "\n");

    for (BusService service : services.keySet()) {
      CompletableFuture<String> temp = this.services.get(service).thenApplyAsync((x) ->
          describeService(service, x));
      description = description.thenCombineAsync(temp, (x, y) -> x + y);
    }
    return description;
  }

  /**
   * Return a string representation a bus service and its matching stops.
   * @param service A bus service
   * @param stops A set of bus stops served by the service
   * @return The first line contains the query information:
   *     bus stop id and search string. The remaining line contains 
   *     the bus services and matching stops served.
   */
  public String describeService(BusService service, Set<BusStop> stops) {
    if (stops.isEmpty()) {
      return "";
    } 
    return stops.stream()
        .filter(stop -> stop != this.stop) 
        .reduce("- Can take " + service + " to:\n",
            (str, stop) -> str += "  - " + stop + "\n",
            (str1, str2) -> str1 + str2);
  }
}

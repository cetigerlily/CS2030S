class EventDepart extends Event {
  public boolean[] available;
  public int customerId;


  public Queue q;

  public EventDepart(double time, int customerId) { // has to be able to take in queue inputs
    super(time);
    this.customerId = customerId;
  }

  @Override
  public String toString() {
    String str = "";
    str = String.format(": C%d departed", this.customerId);
    return super.toString() + str;
  }  

  // have to add in an indication that the queue shortens and the first customer can now be served

  @Override
  public Event[] simulate() { 
    // we have two cases: person left so would go to next c or first c in q if there's a q

    if (q.isEmpty()) { // there's no q
      return new Event[] { };
    } else { // there is a q, find a new counter
        int counter = -1;

        for (int i = 0; i < this.available.length; i += 1) {
          if (this.available[i]) {
            counter = i;
            break;
          }
        }

        if (counter != -1) { // there is a counter - there should be because someone just left
          return new Event[] {
            new EventBegin(this.getTime(), available, customerId, counter, this.serviceTime) 
          };
        } else { } // won't reach here because there is a counter
      } 
  }
}

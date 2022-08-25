class Shop /* implements Comparable<T> */ {
  // variables
  public Counter counter;
  public Counter[] counters;

  private Queue queue;
  private int queueMaxLength;

  // constructor
  public Shop(int numCounters, int queueMaxLength) {
    Counter[] counters = new Counter[numCounters];
    for (int i = 0; i < numCounters; i += 1) {
      counters[i] = new Counter();
    }
    this.queue = new Queue(queueMaxLength);
  }
   
  // methods
  public boolean queueIsFull() {
    return queue.isFull();
  }

  @SuppressWarnings("unchecked")
  /* We can use the SuppressWarnings here because the queue will cause an unchecked error.
     However, we know that the type passed through is Customer c and it is correct. 
     Hence, we can use the SuppressWarnings.
   */
  public boolean hold(Customer c) {
    return queue.enq(c);
  }
  
  public Customer release() {
    return (Customer) queue.deq();
  }
  
  public boolean getCounterAvailibility() {
    return counter.counterAvailibility;
  }

  public int getCounterId() {
    return counter.counterId;
  }
  
  public int getAvailableCounter() {
    int counter = 0;

    for (int i = 0; i < counters.length; i += 1) {
      if (getCounterAvailibility()) {
        counter = getCounterId();
        break;
      } else {
       counter = -1;
      }
    }
    return counter; /* it will return the counter id of the available counter */
  }

}

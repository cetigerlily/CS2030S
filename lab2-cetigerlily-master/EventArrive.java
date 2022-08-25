class EventArrive extends Event { 
  public boolean[] available;
  public int customerId;
  public double serviceTime;

  public int m;
  public Queue q;

  public EventArrive(double time, boolean[] available, int customerId, double serviceTime) { // have to change ShopSimulation to accomodate for the extra parameters
    super(time);
    this.available = available;
    this.customerId = customerId;
    this.serviceTime = serviceTime;
    this.m = m; // max length of queue
    this.q = q; // entrance queue
  } 

  @Override
  public String toString() {
    String str = "";
    String qs = q.toString();
    str = String.format(": C%d arrived", this.customerId) + qs;
    return super.toString() + str;
  }

  @Override
  public Event[] simulate() {
    Queue q = new Queue(m);
    int l = q.length(); // length of the queue
   
    int counter = -1;

    if (q.isEmpty()) { // there is no q
      int count = -1;
    
      for (int i = 0; i < this.available.length; i += 1) { // finding if there is an available counter
        if (this.available[i]) {
          counter = i;
          break;
        } 
      }

      if (counter == -1) { // counter not found, c joins q
        String msg = "";
        msg = String.format("C%d", this.customerId);

        boolean b = q.enq(msg);
        // add that customer joined the q
        
        System.out.println("%f: C%d joined queue" + q.toString());

        return new Event[] { 
          new EventArrive(this.getTime(), this.available, this.customerId, this.serviceTime) 
        };
      // start event again to find whether or not there is a new counter
      
      } else { // counter is found
        return new Event[] {
          new EventBegin(this.getTime(), this.available, this.customerId, counter, this.serviceTime) 
        };
      }

    } else if (!q.isFull()) { // there is a q, but it's NOT full - less than m
        String msg = "";
        msg = String.format("C%d", this.customerId);  

        boolean b = q.enq(msg);
        return new Event[] { 
          new EventArrive(this.getTime(), this.available, this.customerId, this.serviceTime) 
        };

    } else { // when the q is more than m - c has to leave
      return new Event[] {
        new EventDepart(this.getTime(), this.customerId)
      };

    }
  }
}

class EventArrive extends Event { 
  public boolean[] available;
  public int customerId;
  public double serviceTime;

  public EventArrive(double time, boolean[] available, int customerId, double serviceTime) {
    super(time);
    this.available = available;
    this.customerId = customerId;
    this.serviceTime = serviceTime;
  } 

  @Override
  public String toString() {
    String str = "";
    str = String.format(": Customer %d arrives", this.customerId);
    return super.toString() + str;
  }

  @Override
  public Event[] simulate() {
    int counter = -1;
    
    for (int i = 0; i < this.available.length; i += 1) {
      if (this.available[i]) {
        counter = i;
        break;
      } 
    }

    if (counter == -1) {
      return new Event[] {
        new EventDepart(this.getTime(), this.customerId) 
      };
    } else {
      return new Event[] {
        new EventBegin(this.getTime(), this.available, this.customerId, counter, this.serviceTime) 
      };
    }

  }

}

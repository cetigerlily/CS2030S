class EventEnd extends Event {
  public boolean[] available;
  public int customerId;
  public int counterId;

  public EventEnd(double time, boolean[] available, int customerId, int counterId) {
    super(time);
    this.available = available;
    this.customerId = customerId; 
    this.counterId = counterId;
  }

  @Override
  public String toString() {
    String str = "";
    str = String.format(": C%d service done (by S%d)", this.customerId, this.counterId);
    return super.toString() + str;
  }

  @Override
  public Event[] simulate() {
    this.available[this.counterId] = true;
    return new Event[] {
      new EventDepart(this.getTime(), this.customerId) 
    };
  }
}

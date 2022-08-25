class EventBegin extends Event {
  public boolean[] available;
  public int customerId;
  public int counterId;
  public double serviceTime;

  public EventBegin(double time, boolean[] available, int customerId, int counterId, double serviceTime) {
    super(time);
    this.available = available;
    this.customerId = customerId;
    this.counterId = counterId;
    this.serviceTime = serviceTime;
  }

  @Override
  public String toString() {
    String str = "";
    str = String.format(": C%d service begin (by S%d)", this.customerId, this.counterId);
    return super.toString() + str;
  }

  @Override
  public Event[] simulate() {
    this.available[this.counterId] = false;
    double endTime = this.getTime() + this.serviceTime;
    
    return new Event[] {
      new EventEnd(endTime, this.available, this.customerId, this.counterId)
    };
  }
}

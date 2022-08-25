class EventDepart extends Event {
  public int customerId;

  public EventDepart(double time, int customerId) {
    super(time);
    this.customerId = customerId;
  }

  @Override
  public String toString() {
    String str = "";
    str = String.format(": Customer %d departed", this.customerId);
    return super.toString() + str;
  }

  @Override
  public Event[] simulate() {
    return new Event[] { };
  }
}
